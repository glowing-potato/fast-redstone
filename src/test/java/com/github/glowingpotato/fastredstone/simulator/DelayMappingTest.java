package com.github.glowingpotato.fastredstone.simulator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Vertex;

class DelayMappingTest {
	@Test
	void testGetDAG() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		DelayMapping map = new DelayMapping(v1, v2);
		Assertions.assertSame(dag, map.getDag());
	}

	@Test
	void testGetSource() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		DelayMapping map = new DelayMapping(v1, v2);
		Assertions.assertSame(v1, map.getSource());
	}

	@Test
	void testSetSource() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		Vertex v3 = dag.createVertex();
		DelayMapping map = new DelayMapping(v1, v2);
		map.setSource(v3);
		Assertions.assertSame(v3, map.getSource());
		Assertions.assertThrows(IllegalArgumentException.class, () -> map.setSource(v2));
		DAG dag2 = new DAG();
		Vertex v4 = dag2.createVertex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> map.setSource(v4));
	}

	@Test
	void testGetSink() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		DelayMapping map = new DelayMapping(v1, v2);
		Assertions.assertSame(v2, map.getSink());
	}

	@Test
	void testSetSink() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		Vertex v3 = dag.createVertex();
		DelayMapping map = new DelayMapping(v1, v2);
		map.setSink(v3);
		Assertions.assertSame(v3, map.getSink());
		Assertions.assertThrows(IllegalArgumentException.class, () -> map.setSink(v1));
		DAG dag2 = new DAG();
		Vertex v4 = dag2.createVertex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> map.setSink(v4));
	}

	@Test
	void testGetSetValue() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		DelayMapping map = new DelayMapping(v1, v2);
		map.setValue(false);
		Assertions.assertFalse(map.getValue());
		map.setValue(true);
		Assertions.assertTrue(map.getValue());
	}

	@Test
	void testConstructor() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		DelayMapping map = new DelayMapping(v1, v2, false);
		Assertions.assertFalse(map.getValue());
		map = new DelayMapping(v1, v2, true);
		Assertions.assertTrue(map.getValue());
		Assertions.assertThrows(IllegalArgumentException.class, () -> new DelayMapping(v1, v1));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new DelayMapping(v1, null));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new DelayMapping(null, v1));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new DelayMapping(null, null));
		DAG dag2 = new DAG();
		Vertex v3 = dag2.createVertex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> new DelayMapping(v1, v3));
	}
}
