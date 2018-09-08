package com.github.glowingpotato.fastredstone.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.glowingpotato.fastredstone.util.SyncAccessController;

public final class DAG {
	private List<Vertex> vertices;
	private List<Edge> edges;
	SyncAccessController access;

	public Collection<Vertex> getVertices() {
		return access.read(() -> Collections.unmodifiableCollection(vertices));
	}

	public Collection<Edge> getEdges() {
		return access.read(() -> Collections.unmodifiableCollection(edges));
	}

	public Vertex createVertex() {
		return new Vertex(this);
	}

	public Edge createEdge(Vertex source, Vertex sink) {
		return new Edge(source, sink);
	}

	void addVertex(Vertex vertex) {
		vertices.add(vertex);
	}

	void removeVertex(Vertex vertex) {
		vertices.remove(vertex);
	}

	void addEdge(Edge edge) {
		edges.add(edge);
	}

	void removeEdge(Edge edge) {
		edges.remove(edge);
	}

	@Override
	public int hashCode() {
		return access.read(() -> {
			final int prime = 31;
			int result = 1;
			result = prime * result + vertices.size();
			Map<Vertex, Integer> map = new HashMap<>();
			int i = 0;
			for (Vertex vertex : vertices) {
				map.put(vertex, ++i);
			}
			for (Edge edge : edges) {
				result = prime * result + map.get(edge.getSource());
				result = prime * result + map.get(edge.getSink());
			}
			return result;
		});
	}

	public DAG() {
		vertices = new LinkedList<>();
		edges = new LinkedList<>();
		access = new SyncAccessController();
	}
}
