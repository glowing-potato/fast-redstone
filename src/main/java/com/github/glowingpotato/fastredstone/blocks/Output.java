package com.github.glowingpotato.fastredstone.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class Output extends FastRedstone {

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 15;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

}
