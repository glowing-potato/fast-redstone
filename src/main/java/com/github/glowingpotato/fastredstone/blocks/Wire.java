package com.github.glowingpotato.fastredstone.blocks;

import com.github.glowingpotato.fastredstone.pathfinding.CircuitMapping;

import net.minecraft.util.math.BlockPos;

public class Wire extends FastRedstone {

	@Override
	public void addToMapping(BlockPos pos, CircuitMapping mapping) {
		// not needed for wire
	}

	@Override
	public void removeFromMapping(BlockPos pos, CircuitMapping mapping) {
		// not needed for wire
	}

}
