package com.github.glowingpotato.fastredstone.simulator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Vertex;

class IOMappingTest {
	@Test
	void testGetVertex() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		IOMapping map = new IOMapping(v1);
		Assertions.assertSame(v1, map.getVertex());
	}

	@Test
	void testSetVertex() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		Vertex v2 = dag.createVertex();
		IOMapping map = new IOMapping(v1);
		map.setVertex(v2);
		Assertions.assertSame(v2, map.getVertex());
	}

	@Test
	void testGetSetValue() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		IOMapping map = new IOMapping(v1);
		map.setValue(false);
		Assertions.assertFalse(map.getValue());
		map.setValue(true);
		Assertions.assertTrue(map.getValue());
	}

	@Test
	void testConstructor() {
		DAG dag = new DAG();
		Vertex v1 = dag.createVertex();
		IOMapping map = new IOMapping(v1, false);
		Assertions.assertFalse(map.getValue());
		map = new IOMapping(v1, true);
		Assertions.assertTrue(map.getValue());
		Assertions.assertThrows(IllegalArgumentException.class, () -> new IOMapping(null));
	}
}
