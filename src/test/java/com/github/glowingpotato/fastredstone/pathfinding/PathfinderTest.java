package com.github.glowingpotato.fastredstone.pathfinding;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Vertex;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

@TestInstance(Lifecycle.PER_CLASS)
public class PathfinderTest {

	private final HashMap<BlockPos, WireType> inputOutputCircuit;
	private final HashMap<BlockPos, EnumFacing> inputOutputFacings;
	private final DAG inputOutputDAG;

	public PathfinderTest() {
		inputOutputCircuit = new HashMap<>();
		inputOutputCircuit.put(new BlockPos(-1, 64, 0), WireType.INPUT);
		inputOutputCircuit.put(new BlockPos(0, 64, 0), WireType.WIRE);
		inputOutputCircuit.put(new BlockPos(1, 64, 0), WireType.OUTPUT);

		inputOutputFacings = new HashMap<>();

		inputOutputDAG = new DAG();
		Vertex v1 = inputOutputDAG.createVertex();
		Vertex v2 = inputOutputDAG.createVertex();
		inputOutputDAG.createEdge(v1, v2);
	}

	@Test
	public void testInputOutputCircuit() {
		IPathFinder pathfinder = new PathFinderImpl();
		CircuitMapping mapping = pathfinder.buildPath(new MockWorldProxy(inputOutputCircuit, inputOutputFacings), new BlockPos(0, 64, 0));

		System.out.println(mapping.getGraph().getVertices().size());
		System.out.println(mapping.getGraph().getEdges().size());

		Assertions.assertTrue(inputOutputDAG.isIsomorphic(mapping.getGraph()));

	}

}
