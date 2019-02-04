package com.github.glowingpotato.fastredstone.blocks;

import java.util.HashMap;

import com.github.glowingpotato.fastredstone.pathfinding.CircuitMapping;
import com.github.glowingpotato.fastredstone.pathfinding.IPathFinder;
import com.github.glowingpotato.fastredstone.pathfinding.IWorldProxy;
import com.github.glowingpotato.fastredstone.pathfinding.PathFinderImpl;
import com.github.glowingpotato.fastredstone.pathfinding.WorldProxyImpl;
import com.github.glowingpotato.fastredstone.simulator.ISimulator;
import com.github.glowingpotato.fastredstone.simulator.slow.SlowSimulator;
import com.github.glowingpotato.fastredstone.world.WireGraphData;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class FastRedstone extends Block {

	private ISimulator simulator = new SlowSimulator();
	private IPathFinder pathfinder = new PathFinderImpl();
	private HashMap<World, IWorldProxy> proxies = new HashMap<World, IWorldProxy>();

	public FastRedstone() {
		super(Material.CIRCUITS);
	}

	public ISimulator getSimulator() {
		return simulator;
	}

	public abstract void addToMapping(BlockPos pos, CircuitMapping mapping);

	public abstract void removeFromMapping(BlockPos pos, CircuitMapping mapping);

	// @SideOnly(Side.SERVER)
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		CircuitMapping mapping = WireGraphData.get(world).getMapping();
		removeFromMapping(pos, mapping);

		try {
			simulate(world, pos);
		} catch (NullPointerException e) {
			System.out.println("Failed to simulate redstone!");
		}
	}

	// @SideOnly(Side.SERVER)
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		CircuitMapping mapping = WireGraphData.get(world).getMapping();
		addToMapping(pos, mapping);

		pathfinder.connectToNetwork(proxies.get(world), mapping, pos);

		simulate(world, pos);
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return CreativeTabs.REDSTONE;
	}

	private void simulate(World world, BlockPos pos) {
		WireGraphData data = WireGraphData.get(world);

		if (!proxies.containsKey(world)) {
			proxies.put(world, new WorldProxyImpl(world));
		}

		// data.setMapping(pathfinder.buildPath(proxies.get(world), pos));

		simulator.simulate(data.getMapping().getInputs(), data.getMapping().getDelays(), data.getMapping().getOutputs());

	}

}
