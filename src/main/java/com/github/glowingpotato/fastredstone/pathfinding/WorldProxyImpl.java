package com.github.glowingpotato.fastredstone.pathfinding;

import com.github.glowingpotato.fastredstone.blocks.Delayer;
import com.github.glowingpotato.fastredstone.blocks.Input;
import com.github.glowingpotato.fastredstone.blocks.Inverter;
import com.github.glowingpotato.fastredstone.blocks.OrientedFastRedstone;
import com.github.glowingpotato.fastredstone.blocks.Output;
import com.github.glowingpotato.fastredstone.blocks.Wire;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldProxyImpl implements IWorldProxy {

	private World world;

	public WorldProxyImpl(World world) {
		this.world = world;
	}

	@Override
	public WireType getWireType(BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof Wire) {
			return WireType.WIRE;
		} else if (world.getBlockState(pos).getBlock() instanceof Delayer) {
			return WireType.DELAYER;
		} else if (world.getBlockState(pos).getBlock() instanceof Inverter) {
			return WireType.INVERTER;
		} else if (world.getBlockState(pos).getBlock() instanceof Input) {
			return WireType.INPUT;
		} else if (world.getBlockState(pos).getBlock() instanceof Output) {
			return WireType.OUTPUT;
		}
		return null;
	}

	@Override
	public EnumFacing getWireDirection(BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof OrientedFastRedstone) {
			return world.getBlockState(pos).getValue(OrientedFastRedstone.FACING);
		}
		return null;
	}

}
