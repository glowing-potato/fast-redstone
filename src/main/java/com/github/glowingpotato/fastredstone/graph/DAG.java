package com.github.glowingpotato.fastredstone.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	private static boolean isIsomorphic(boolean[][] a, boolean[][] b, Set<Integer> mobile) {
		if (mobile.size() < 2) {
			for (int i = 0; i < a.length; ++i) {
				for (int j = 0; j < a.length; ++j) {
					if (a[i][j] != b[i][j]) {
						return false;
					}
				}
			}
			return true;
		} else {
			boolean tmp;
			for (int i : mobile) {
				for (int j : mobile) {
					if (i < j) {
						for (int k = 0; k < a.length; ++k) {
							if (k != i && k != j) {
								tmp = a[i][k];
								a[i][k] = a[j][k];
								a[j][k] = tmp;
								tmp = a[k][i];
								a[k][i] = a[k][j];
								a[k][j] = a[k][i];
							}
						}
						tmp = a[i][i];
						a[i][i] = a[j][j];
						a[j][j] = tmp;
						tmp = a[i][j];
						a[i][j] = a[j][i];
						a[j][i] = tmp;
						Set<Integer> nextMobile = new HashSet<>(mobile);
						nextMobile.remove(i);
						nextMobile.remove(j);
						if (isIsomorphic(a, b, nextMobile)) {
							return true;
						}
						for (int k = 0; k < a.length; ++k) {
							if (k != i && k != j) {
								tmp = a[i][k];
								a[i][k] = a[j][k];
								a[j][k] = tmp;
								tmp = a[k][i];
								a[k][i] = a[k][j];
								a[k][j] = a[k][i];
							}
						}
						tmp = a[i][i];
						a[i][i] = a[j][j];
						a[j][j] = tmp;
						tmp = a[i][j];
						a[i][j] = a[j][i];
						a[j][i] = tmp;
					}
				}
			}
		}
		return false;
	}

	public boolean isIsomorphic(DAG dag) {
		return access.read(() -> {
			return dag.access.read(() -> {
				if (dag.vertices.size() != vertices.size() || dag.edges.size() != edges.size()) {
					return false;
				}
				int s = vertices.size();
				Map<Vertex, Integer> mapA = new HashMap<>();
				Map<Vertex, Integer> mapB = new HashMap<>();
				int i = 0;
				for (Vertex vertex : vertices) {
					mapA.put(vertex, i++);
				}
				i = 0;
				for (Vertex vertex : dag.vertices) {
					mapB.put(vertex, i++);
				}
				boolean[][] a = new boolean[s][];
				boolean[][] b = new boolean[s][];
				for (i = 0; i < s; ++i) {
					a[i] = new boolean[s];
					b[i] = new boolean[s];
					for (int j = 0; j < s; ++j) {
						a[i][j] = false;
						b[i][j] = false;
					}
				}
				for (Edge edge : edges) {
					a[mapA.get(edge.getSource())][mapA.get(edge.getSink())] = true;
				}
				for (Edge edge : dag.edges) {
					b[mapB.get(edge.getSource())][mapB.get(edge.getSink())] = true;
				}
				Set<Integer> mobile = new HashSet<>();
				for (i = 0; i < s; ++i) {
					mobile.add(i);
				}
				return isIsomorphic(a, b, mobile);
			});
		});
	}

	public DAG() {
		vertices = new LinkedList<>();
		edges = new LinkedList<>();
		access = new SyncAccessController();
	}
}
