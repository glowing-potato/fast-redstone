package com.github.glowingpotato.fastredstone.pathfinding;

import java.util.Collection;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;

public class CircuitMapping {

	private final DAG graph;
	private final Collection<IOMapping> inputs;
	private final Collection<DelayMapping> delays;
	private final Collection<IOMapping> outputs;

	public CircuitMapping(DAG graph, Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs) {
		this.graph = graph;
		this.inputs = inputs;
		this.delays = delays;
		this.outputs = outputs;
	}

	public DAG getGraph() {
		return graph;
	}

	public Collection<IOMapping> getInputs() {
		return inputs;
	}

	public Collection<DelayMapping> getDelays() {
		return delays;
	}

	public Collection<IOMapping> getOutputs() {
		return outputs;
	}

}
