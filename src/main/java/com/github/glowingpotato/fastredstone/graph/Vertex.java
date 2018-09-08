package com.github.glowingpotato.fastredstone.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Vertex {
	private DAG dag;
	private List<Edge> sourcedEdges;
	private List<Edge> sunkEdges;

	public DAG getDag() {
		return dag;
	}

	public Collection<Edge> getSourcedEdges() {
		return dag.access.read(() -> Collections.unmodifiableCollection(sourcedEdges));
	}

	public Collection<Edge> getSunkEdges() {
		return dag.access.read(() -> Collections.unmodifiableCollection(sunkEdges));
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
		dag.access.write(() -> {
			dag.removeVertex(this);
			for (Edge edge : sourcedEdges.toArray(new Edge[0])) {
				edge.remove();
			}
			for (Edge edge : sunkEdges.toArray(new Edge[0])) {
				edge.remove();
			}
		});
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		Map<Vertex, Integer> map = new HashMap<>();
		int i = 0;
		for (Vertex vertex : getDag().getVertices()) {
			map.put(vertex, ++i);
		}
		for (Edge edge : getSourcedEdges()) {
			result = prime * result + 3 * map.get(edge.getSink());
		}
		for (Edge edge : getSunkEdges()) {
			result = prime * result + 5 * map.get(edge.getSource());
		}
		return result;
	}

	public Vertex(DAG dag) {
		if (dag == null) {
			throw new IllegalArgumentException("The DAG cannot be null.");
		}
		this.dag = dag;
		sourcedEdges = new LinkedList<>();
		sunkEdges = new LinkedList<>();
		dag.access.write(() -> dag.addVertex(this));
	}
}
