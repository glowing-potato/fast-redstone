package com.github.glowingpotato.fastredstone.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Vertex {
	private DAG dag;
	private List<Edge> sourcedEdges;
	private List<Edge> sunkEdges;

	public DAG getDag() {
		return dag;
	}

	public Collection<Edge> getSourcedEdges() {
		return Collections.unmodifiableCollection(sourcedEdges);
	}

	public Collection<Edge> getSunkEdges() {
		return Collections.unmodifiableCollection(sunkEdges);
	}

	void addSourcedEdge(Edge edge) {
		sourcedEdges.add(edge);
	}

	void addSunkEdge(Edge edge) {
		sunkEdges.add(edge);
	}

	void removeSourcedEdge(Edge edge) {
		sourcedEdges.remove(edge);
	}

	void removeSunkEdge(Edge edge) {
		sunkEdges.remove(edge);
	}

	public void remove() {
		dag.removeVertex(this);
		sourcedEdges.forEach(edge -> edge.remove());
		sunkEdges.forEach(edge -> edge.remove());
	}

	public Vertex(DAG dag) {
		this.dag = dag;
		sourcedEdges = new LinkedList<>();
		sunkEdges = new LinkedList<>();
	}
}
