package com.github.glowingpotato.fastredstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class FastRedstone extends Block {

	public FastRedstone() {
		super(Material.CIRCUITS);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {

	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {

	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return CreativeTabs.REDSTONE;
	}

}
