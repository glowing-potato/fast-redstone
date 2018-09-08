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
	
	@Test
	void testNotGate() {
		DAG dag = new DAG();
		Vertex in1 = dag.createVertex();
		Vertex v1 = dag.createVertex();
		Vertex out1 = dag.createVertex();
		dag.createEdge(in1, v1);
		dag.createEdge(v1, out1);
		IOMapping inm1 = new IOMapping(in1, false);
		IOMapping outm1 = new IOMapping(out1, false);
		Collection<IOMapping> inputs = Arrays.asList(inm1);
		Collection<DelayMapping> delays = Arrays.asList();
		Collection<IOMapping> outputs = Arrays.asList(outm1);
		sim.simulate(inputs, delays, outputs);
		Assertions.assertTrue(outm1.getValue());
		inm1.setValue(true);
		sim.simulate(inputs, delays, outputs);
		Assertions.assertFalse(outm1.getValue());
	}
}
