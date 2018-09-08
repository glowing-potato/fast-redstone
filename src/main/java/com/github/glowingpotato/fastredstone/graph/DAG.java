package com.github.glowingpotato.fastredstone.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class DAG {
	private List<Vertex> vertices;
	private List<Edge> edges;

	public Collection<Vertex> getVertices() {
		return Collections.unmodifiableCollection(vertices);
	}

	public Collection<Edge> getEdges() {
		return Collections.unmodifiableCollection(edges);
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
	
	public DAG() {
		vertices = new LinkedList<>();
		edges = new LinkedList<>();
	}
}
