package com.github.glowingpotato.fastredstone.pathfinding;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IWorldProxy {

	public WireType getWireType(BlockPos pos);

	public EnumFacing getWireDirection(BlockPos pos);

}
