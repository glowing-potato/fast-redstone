package com.github.glowingpotato.fastredstone.graph;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VertexTest {
	private DAG dag;
	private Vertex[] v;

	@BeforeEach
	void setup() {
		dag = new DAG();
		v = new Vertex[11];
		for (int i = 0; i < v.length; ++i) {
			v[i] = dag.createVertex();
		}
		int[] e = { 0, 2, 1, 2, 2, 3, 2, 4, 2, 5, 3, 6, 4, 7, 5, 7, 5, 10, 6, 8, 7, 10, 8, 9, 8, 10 };
		Assertions.assertSame(0, e.length % 2);
		for (int i = 0; i < e.length; i += 2) {
			dag.createEdge(v[e[i]], v[e[i + 1]]);
		}
	}

	@AfterEach
	void cleanup() {
		dag = null;
	}

	@Test
	void testGetDag() {
		Assertions.assertFalse(dag.getVertices().isEmpty());
		for (Vertex vertex : dag.getVertices()) {
			Assertions.assertSame(dag, vertex.getDag());
		}
	}

	@Test
	void testGetSourcedEdges() {
		Assertions.assertFalse(dag.getVertices().isEmpty());
		for (Vertex vertex : dag.getVertices()) {
			Assertions.assertNotSame(vertex.getSourcedEdges(), vertex.getSourcedEdges());
			Assertions.assertArrayEquals(vertex.getSourcedEdges().toArray(new Edge[0]),
					vertex.getSourcedEdges().toArray(new Edge[0]));
		}
	}

	@Test
	void testGetSunkEdges() {
		Assertions.assertFalse(dag.getVertices().isEmpty());
		for (Vertex vertex : dag.getVertices()) {
			Assertions.assertNotSame(vertex.getSunkEdges(), vertex.getSunkEdges());
			Assertions.assertArrayEquals(vertex.getSunkEdges().toArray(new Edge[0]),
					vertex.getSunkEdges().toArray(new Edge[0]));
		}
	}
	
	@Test
	void testRemove() {
		Assertions.assertSame(11, dag.getVertices().size());
		Assertions.assertSame(13, dag.getEdges().size());
		v[7].remove();
		Assertions.assertSame(10, dag.getVertices().size());
		Assertions.assertSame(10, dag.getEdges().size());
		Assertions.assertFalse(dag.getVertices().contains(v[7]));
		for (Edge edge : dag.getEdges()) {
			Assertions.assertNotEquals(v[7], edge.getSource());
			Assertions.assertNotEquals(v[7], edge.getSink());
		}
	}

	@Test
	void testConstructorNulls() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Vertex(null));
	}
}
