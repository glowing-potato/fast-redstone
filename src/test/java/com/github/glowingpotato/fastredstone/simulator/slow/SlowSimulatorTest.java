package com.github.glowingpotato.fastredstone.simulator.slow;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;

@TestInstance(Lifecycle.PER_CLASS)
class SlowSimulatorTest {
	private SlowSimulator sim;

	@BeforeAll
	void setup() {
		sim = new SlowSimulator();
	}

	@AfterAll
	void cleanup() {
		sim = null;
	}

	static int[] __(int... a) {
		return a;
	}

	static boolean[] __(boolean... a) {
		return a;
	}

	static boolean[][] __(boolean[]... a) {
		return a;
	}

	void test(int[] edges, int[] inputs, int[] delays, int[] outputs, SimulatorTestFunc func) {
		DAG dag = new DAG();
		int numVerts = 0;
		for (int v : edges) {
			if (numVerts < v) {
				numVerts = v;
			}
		}
		++numVerts;
		Vertex[] v = new Vertex[numVerts];
		for (int i = 0; i < numVerts; ++i) {
			v[i] = dag.createVertex();
		}
		Assertions.assertSame(0, edges.length % 2);
		for (int i = 0; i < edges.length; i += 2) {
			dag.createEdge(v[edges[i]], v[edges[i + 1]]);
		}
		IOMapping[] inputsa = new IOMapping[inputs.length];
		Assertions.assertSame(0, delays.length % 2);
		DelayMapping[] delaysa = new DelayMapping[delays.length / 2];
		IOMapping[] outputsa = new IOMapping[outputs.length];
		for (int i = 0; i < inputs.length; ++i) {
			inputsa[i] = new IOMapping(v[inputs[i]]);
		}
		for (int i = 0; i < delays.length; i += 2) {
			delaysa[i / 2] = new DelayMapping(v[delays[i]], v[delays[i + 1]]);
		}
		for (int i = 0; i < outputs.length; ++i) {
			outputsa[i] = new IOMapping(v[outputs[i]]);
		}
		Collection<IOMapping> inputsc = Arrays.asList(inputsa);
		Collection<DelayMapping> delaysc = Arrays.asList(delaysa);
		Collection<IOMapping> outputsc = Arrays.asList(outputsa);
		func.test(inputsa, delaysa, outputsa, () -> sim.simulate(inputsc, delaysc, outputsc));
	}

	void test(int[] edges, int[] inputs, int[] outputs, boolean[][] truthTable) {
		test(edges, inputs, new int[0], outputs, (in, delay, out, sim) -> {
			for (int i = 0; i < truthTable.length; ++i) {
				Assertions.assertSame(inputs.length + outputs.length, truthTable[i].length);
				for (int j = 0; j < inputs.length; ++j) {
					in[j].setValue(truthTable[i][j]);
				}
				sim.run();
				for (int j = 0; j < outputs.length; ++j) {
					Assertions.assertSame(truthTable[i][j + inputs.length], out[j].getValue());
				}
			}
		});
	}

	@Test
	void testNotGate() {
		test(__(0, 1, 1, 2), __(0), __(2), __(__(false, true), __(true, false)));
	}

	@Test
	void testAndGate() {
		test(__(0, 2, 1, 3, 2, 4, 3, 4, 4, 5), __(0, 1), __(5),
				__(__(false, false, false), __(false, true, false), __(true, false, false), __(true, true, true)));
	}
}
