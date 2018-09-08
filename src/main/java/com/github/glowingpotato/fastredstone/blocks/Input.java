package com.github.glowingpotato.fastredstone.blocks;

import net.minecraft.block.state.IBlockState;

public class Input extends FastRedstone {

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

}
