package com.github.glowingpotato.fastredstone.blocks;

import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.pathfinding.CircuitMapping;
import com.github.glowingpotato.fastredstone.util.KeyValuePair;

import net.minecraft.util.math.BlockPos;

public class Delayer extends OrientedFastRedstone {

	@Override
	public void addToMapping(BlockPos pos, CircuitMapping mapping) {
		mapping.getDelayMap().put(pos, new KeyValuePair<Vertex, Vertex>(mapping.getGraph().createVertex(), mapping.getGraph().createVertex()));
	}

	@Override
	public void removeFromMapping(BlockPos pos, CircuitMapping mapping) {
		mapping.getDelayMap().remove(pos);
	}

}
