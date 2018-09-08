package com.github.glowingpotato.fastredstone.simulator.slow;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.github.glowingpotato.fastredstone.graph.Edge;
import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;
import com.github.glowingpotato.fastredstone.simulator.ISimulator;

public class SlowSimulator implements ISimulator {
	@Override
	public void simulate(Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs) {
		Map<Vertex, Boolean> done = new HashMap<>();
		Set<Vertex> inProgress = new HashSet<>();
		for (IOMapping map : inputs) {
			done.put(map.getVertex(), map.getValue());
		}
		for (DelayMapping map : delays) {
			done.put(map.getSource(), map.getValue());
		}
		for (Vertex vertex : done.keySet()) {
			for (Edge edge : vertex.getSourcedEdges()) {
				inProgress.add(edge.getSink());
			}
		}
		Set<Vertex> newProgress = new HashSet<>();
		while (inProgress.size() > 0) {
			Iterator<Vertex> it = inProgress.iterator();
			mainLoop: while (it.hasNext()) {
				Vertex vertex = it.next();
				boolean value = false;
				for (Edge edge : vertex.getSunkEdges()) {
					Vertex source = edge.getSource();
					if (!done.containsKey(source)) {
						continue mainLoop;
					} else {
						value = value || done.get(source);
					}
				}
				done.put(vertex, !value);
				it.remove();
				for (Edge edge : vertex.getSourcedEdges()) {
					Vertex sink = edge.getSink();
					if (!done.containsKey(sink)) {
						newProgress.add(sink);
					}
				}
			}
			inProgress.addAll(newProgress);
			newProgress.clear();
		}
		for (DelayMapping map : delays) {
			map.setValue(!done.get(map.getSink()));
		}
		for (IOMapping map : outputs) {
			map.setValue(!done.get(map.getVertex()));
		}
	}
}
