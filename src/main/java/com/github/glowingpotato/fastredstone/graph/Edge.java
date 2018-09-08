package com.github.glowingpotato.fastredstone.graph;

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
		dag.removeEdge(this);
		source.removeSourcedEdge(this);
		sink.removeSunkEdge(this);
	}

	public Edge(Vertex source, Vertex sink) {
		dag = source.getDag();
		this.source = source;
		this.sink = sink;
		if (sink.getDag() != dag) {
			throw new IllegalArgumentException("An edge cannot connect two vertices on different graphs");
		}
		dag.addEdge(this);
		source.addSourcedEdge(this);
		sink.addSunkEdge(this);
	}
}
