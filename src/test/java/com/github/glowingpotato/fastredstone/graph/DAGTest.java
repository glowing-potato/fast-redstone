package com.github.glowingpotato.fastredstone.graph;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DAGTest {
	private DAG dag;

	@BeforeEach
	void setup() {
		dag = new DAG();
	}

	@AfterEach
	void cleanup() {
		dag = null;
	}

	@Test
	void testGetVertices() {
		Assertions.assertNotSame(dag.getVertices(), dag.getVertices());
		Assertions.assertArrayEquals(dag.getVertices().toArray(new Vertex[0]),
				dag.getVertices().toArray(new Vertex[0]));
	}

	@Test
	void testGetEdges() {
		Assertions.assertNotSame(dag.getEdges(), dag.getEdges());
		Assertions.assertArrayEquals(dag.getEdges().toArray(new Edge[0]), dag.getEdges().toArray(new Edge[0]));
	}

	@Test
	void testCreateVertex() {
		Assertions.assertSame(0, dag.getVertices().size());
		Vertex v = dag.createVertex();
		Assertions.assertNotNull(v);
		Assertions.assertSame(dag, v.getDag());
		Assertions.assertSame(1, dag.getVertices().size());
		Assertions.assertSame(dag.getVertices().iterator().next(), v);
	}

	@Test
	void testCreateEdge() {
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		Assertions.assertSame(0, dag.getEdges().size());
		Edge e = dag.createEdge(v1, v2);
		Assertions.assertNotNull(e);
		Assertions.assertSame(dag, e.getDag());
		Assertions.assertSame(1, dag.getEdges().size());
		Assertions.assertSame(dag.getEdges().iterator().next(), e);
		Assertions.assertThrows(IllegalArgumentException.class, () -> dag.createEdge(null, v2));
		Assertions.assertThrows(IllegalArgumentException.class, () -> dag.createEdge(v1, null));
		Assertions.assertThrows(IllegalArgumentException.class, () -> dag.createEdge(null, null));
	}
}
