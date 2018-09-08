package com.github.glowingpotato.fastredstone.graph;

import java.util.ArrayList;
import java.util.HashSet;

public final class Edge {
	private DAG dag;
	private Vertex source;
	private Vertex sink;

	public DAG getDag() {
		return dag;
	}

	public Vertex getSource() {
		return source;
	}

	public Vertex getSink() {
		return sink;
	}

	public void remove() {
		dag.access.write(() -> {
			dag.removeEdge(this);
			source.removeSourcedEdge(this);
			sink.removeSunkEdge(this);
		});
	}

	public Edge(Vertex source, Vertex sink) {
		dag = source.getDag();
		dag.access.write(() -> {
			if (sink.getDag() != dag) {
				throw new IllegalArgumentException("An edge cannot connect two vertices on different graphs.");
			}
			if (Path.tryCreate(sink, source, new ArrayList<>(), new HashSet<>())) {
				throw new GraphSolveException("Adding this edge would create a cyclic graph.");
			}
			this.source = source;
			this.sink = sink;
			dag.addEdge(this);
			source.addSourcedEdge(this);
			sink.addSunkEdge(this);
		});
	}
}
