package com.github.glowingpotato.fastredstone.blocks;

import java.util.Random;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Output extends FastRedstone {

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		BlockPos north = pos.north();
		if (worldIn.getBlockState(north).getBlock() instanceof BlockRedstoneWire) {
			worldIn.setBlockState(north, worldIn.getBlockState(north).withProperty(BlockRedstoneWire.POWER, 15));
		}
		BlockPos south = pos.south();
		if (worldIn.getBlockState(south).getBlock() instanceof BlockRedstoneWire) {
			worldIn.setBlockState(south, worldIn.getBlockState(south).withProperty(BlockRedstoneWire.POWER, 15));
		}
		BlockPos east = pos.east();
		if (worldIn.getBlockState(east).getBlock() instanceof BlockRedstoneWire) {
			worldIn.setBlockState(east, worldIn.getBlockState(east).withProperty(BlockRedstoneWire.POWER, 15));
		}
		BlockPos west = pos.west();
		if (worldIn.getBlockState(west).getBlock() instanceof BlockRedstoneWire) {
			worldIn.setBlockState(west, worldIn.getBlockState(west).withProperty(BlockRedstoneWire.POWER, 15));
		}
	}

}
