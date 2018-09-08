package com.github.glowingpotato.fastredstone.blocks;

import java.util.ArrayList;
import java.util.Collection;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;
import com.github.glowingpotato.fastredstone.simulator.ISimulator;
import com.github.glowingpotato.fastredstone.simulator.slow.SlowSimulator;
import com.github.glowingpotato.fastredstone.world.WireGraphData;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class FastRedstone extends Block {

	private ISimulator simulator = new SlowSimulator();

	public FastRedstone() {
		super(Material.CIRCUITS);
	}

	// @SideOnly(Side.SERVER)
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		simulate(world, pos);
	}

	// @SideOnly(Side.SERVER)
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		simulate(world, pos);
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return CreativeTabs.REDSTONE;
	}

	private void simulate(World world, BlockPos pos) {
		WireGraphData data = WireGraphData.get(world);
		buildGraph(data.getGraph(), world, pos, new ArrayList<BlockPos>(), data.getInputMapping(), data.getDelayMapping(), data.getOutputMapping());
		simulator.simulate(data.getInputMapping(), data.getDelayMapping(), data.getOutputMapping());
	}

	private void buildGraph(DAG dag, World world, BlockPos pos, ArrayList<BlockPos> visited, Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs) {

		// array to hold paths (0 = up, 1 = down, 2 = north, 3 = south, 4 = east, 5 =
		// west)

	}

}
