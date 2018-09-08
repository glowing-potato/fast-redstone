package com.github.glowingpotato.fastredstone.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class Input extends FastRedstone {

	@Override
	public boolean canConnectRedstone(IBlockState p_canConnectRedstone_1_, IBlockAccess p_canConnectRedstone_2_, BlockPos p_canConnectRedstone_3_, EnumFacing p_canConnectRedstone_4_) {
		return true;
	}

}
