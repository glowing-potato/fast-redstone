package com.github.glowingpotato.fastredstone.pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;
import com.github.glowingpotato.fastredstone.util.KeyValuePair;

import net.minecraft.util.math.BlockPos;

public class CircuitMapping {

	private final DAG graph;
	private final Collection<IOMapping> inputs;
	private final Collection<DelayMapping> delays;
	private final Collection<IOMapping> outputs;
	private final HashMap<BlockPos, Vertex> inputMap;
	private final HashMap<BlockPos, KeyValuePair<Vertex, Vertex>> delayMap;
	private final HashMap<BlockPos, Vertex> outputMap;
	private final HashMap<BlockPos, Vertex> inverterInputMap;
	private final HashMap<BlockPos, Vertex> inverterOutputMap;

	/*
	 * public CircuitMapping(DAG graph, Collection<IOMapping> inputs,
	 * Collection<DelayMapping> delays, Collection<IOMapping> outputs) { this.graph
	 * = graph; this.inputs = inputs; this.delays = delays; this.outputs = outputs;
	 * }
	 */
	public CircuitMapping() {
		graph = new DAG();
		inputs = new ArrayList<IOMapping>();
		delays = new ArrayList<DelayMapping>();
		outputs = new ArrayList<IOMapping>();
		inputMap = new HashMap<BlockPos, Vertex>();
		delayMap = new HashMap<BlockPos, KeyValuePair<Vertex, Vertex>>();
		outputMap = new HashMap<BlockPos, Vertex>();
		inverterInputMap = new HashMap<BlockPos, Vertex>();
		inverterOutputMap = new HashMap<BlockPos, Vertex>();
	}

	public CircuitMapping(DAG graph, Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs, HashMap<BlockPos, Vertex> inputMap, HashMap<BlockPos, KeyValuePair<Vertex, Vertex>> delayMap,
			HashMap<BlockPos, Vertex> outputMap, HashMap<BlockPos, Vertex> inverterInputMap, HashMap<BlockPos, Vertex> inverterOutputMap) {
		super();
		this.graph = graph;
		this.inputs = inputs;
		this.delays = delays;
		this.outputs = outputs;
		this.inputMap = inputMap;
		this.delayMap = delayMap;
		this.outputMap = outputMap;
		this.inverterInputMap = inverterInputMap;
		this.inverterOutputMap = inverterOutputMap;
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

	public HashMap<BlockPos, Vertex> getInputMap() {
		return inputMap;
	}

	public HashMap<BlockPos, KeyValuePair<Vertex, Vertex>> getDelayMap() {
		return delayMap;
	}

	public HashMap<BlockPos, Vertex> getOutputMap() {
		return outputMap;
	}

	public HashMap<BlockPos, Vertex> getInverterInputMap() {
		return inverterInputMap;
	}

	public HashMap<BlockPos, Vertex> getInverterOutputMap() {
		return inverterOutputMap;
	}

}
