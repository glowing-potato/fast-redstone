package com.github.glowingpotato.fastredstone.blocks;

import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.pathfinding.CircuitMapping;

import net.minecraft.util.math.BlockPos;

public class Inverter extends OrientedFastRedstone {

	@Override
	public void addToMapping(BlockPos pos, CircuitMapping mapping) {
		Vertex sink = mapping.getGraph().createVertex();
		Vertex source = mapping.getGraph().createVertex();
		mapping.getInverterInputMap().put(pos, sink);
		mapping.getInverterOutputMap().put(pos, source);
	}

	@Override
	public void removeFromMapping(BlockPos pos, CircuitMapping mapping) {
		mapping.getInverterInputMap().remove(pos);
		mapping.getInverterOutputMap().remove(pos);
	}

}
