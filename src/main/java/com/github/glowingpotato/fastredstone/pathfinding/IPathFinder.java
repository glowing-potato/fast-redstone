package com.github.glowingpotato.fastredstone.pathfinding;

import net.minecraft.util.math.BlockPos;

public interface IPathFinder {

	public CircuitMapping buildPath(IWorldProxy proxy, BlockPos pos);

}
