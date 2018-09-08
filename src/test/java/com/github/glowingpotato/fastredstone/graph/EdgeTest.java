package com.github.glowingpotato.fastredstone.graph;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EdgeTest {
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
	void testBridgingInputs() {
		dag.createEdge(v[1], v[0]);
	}

	@Test
	void testMultilayerEdge() {
		dag.createEdge(v[0], v[3]);
		dag.createEdge(v[4], v[8]);
		dag.createEdge(v[6], v[9]);
	}

	@Test
	void testCreate() {
		dag.createEdge(v[4], v[6]);
	}

	@Test
	void testBridgingInnerLayer() {
		dag.createEdge(v[3], v[4]);
	}

	@Test
	void testBridgingOutputs() {
		dag.createEdge(v[9], v[10]);
	}

	@Test
	void testBackwardsMultilayerEdge() {
		Assertions.assertThrows(GraphSolveException.class, () -> dag.createEdge(v[3], v[1]));
		Assertions.assertThrows(GraphSolveException.class, () -> dag.createEdge(v[9], v[3]));
	}

	@Test
	void testUndirectedEdge() {
		dag.createEdge(v[3], v[4]);
		Assertions.assertThrows(GraphSolveException.class, () -> dag.createEdge(v[4], v[3]));
	}

	@Test
	void testLoops() {
		Assertions.assertThrows(GraphSolveException.class, () -> dag.createEdge(v[7], v[7]));
	}

	@Test
	void testGetDag() {
		Assertions.assertFalse(dag.getEdges().isEmpty());
		for (Edge edge : dag.getEdges()) {
			Assertions.assertSame(dag, edge.getDag());
		}
	}

	@Test
	void testGetSource() {
		Assertions.assertFalse(dag.getEdges().isEmpty());
		for (Edge edge : dag.getEdges()) {
			Assertions.assertSame(edge.getSource(), edge.getSource());
			Assertions.assertNotNull(edge.getSource());
		}
	}

	@Test
	void testGetSink() {
		Assertions.assertFalse(dag.getEdges().isEmpty());
		for (Edge edge : dag.getEdges()) {
			Assertions.assertSame(edge.getSink(), edge.getSink());
			Assertions.assertNotNull(edge.getSink());
		}
	}

	@Test
	void testConstructorNulls() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Edge(null, v[0]));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Edge(v[0], null));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Edge(null, null));
	}

	@Test
	void testConstructorDAG() {
		DAG d2 = new DAG();
		Vertex v2 = d2.createVertex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Edge(v[0], v2));
	}

	@Test
	void testVertexLinks() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		Edge e = dag.createEdge(v1, v2);
		Assertions.assertSame(1, v1.getSourcedEdges().size());
		Assertions.assertSame(0, v1.getSunkEdges().size());
		Assertions.assertSame(0, v2.getSourcedEdges().size());
		Assertions.assertSame(1, v2.getSunkEdges().size());
		Assertions.assertSame(e, v1.getSourcedEdges().iterator().next());
		Assertions.assertSame(e, v2.getSunkEdges().iterator().next());
	}

	@Test
	void testRemove() {
		Assertions.assertSame(13, dag.getEdges().size());
		Edge edge = dag.getEdges().iterator().next();
		int oo = edge.getSource().getSourcedEdges().size();
		int oi = edge.getSource().getSunkEdges().size();
		int io = edge.getSink().getSourcedEdges().size();
		int ii = edge.getSink().getSunkEdges().size();
		edge.remove();
		Assertions.assertSame(12, dag.getEdges().size());
		Assertions.assertSame(oo - 1, edge.getSource().getSourcedEdges().size());
		Assertions.assertSame(oi, edge.getSource().getSunkEdges().size());
		Assertions.assertSame(io, edge.getSink().getSourcedEdges().size());
		Assertions.assertSame(ii - 1, edge.getSink().getSunkEdges().size());
	}
}
