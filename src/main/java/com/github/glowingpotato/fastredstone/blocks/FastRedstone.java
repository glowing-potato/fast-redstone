package com.github.glowingpotato.fastredstone.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;
import com.github.glowingpotato.fastredstone.simulator.ISimulator;
import com.github.glowingpotato.fastredstone.simulator.slow.SlowSimulator;
import com.github.glowingpotato.fastredstone.util.KeyValuePair;
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
		Collection<IOMapping> inputs = new ArrayList<IOMapping>();
		Collection<DelayMapping> delays = new ArrayList<DelayMapping>();
		Collection<IOMapping> outputs = new ArrayList<IOMapping>();
		buildGraph(WireGraphData.get(world).getGraph(), world, pos, new ArrayList<BlockPos>(), inputs, delays, outputs, WireGraphData.get(world).getVertexMapping());
		simulator.simulate(inputs, delays, outputs);
	}

	private void buildGraph(DAG dag, World world, BlockPos pos, ArrayList<BlockPos> visited, Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs,
			HashMap<Vertex, KeyValuePair<BlockPos, Boolean>> vertexMapping) {

		// array to hold paths (0 = up, 1 = down, 2 = north, 3 = south, 4 = east, 5 =
		// west)
		EnumFacing[] paths = new EnumFacing[6];
		BlockPos curpos = pos;
		int directions = 0;
		while (true) {

			Block blockAt = world.getBlockState(curpos).getBlock();
			if (blockAt instanceof Delayer) {
				//if (world.getBlockState(curpos).getValue(Delayer.FACING) == EnumFacing)
			}

			if (!visited.contains(curpos.up()) && (paths[0] = world.getBlockState(curpos.up()).getBlock() instanceof FastRedstone ? EnumFacing.UP : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.down()) && (paths[1] = world.getBlockState(curpos.down()).getBlock() instanceof FastRedstone ? EnumFacing.DOWN : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.north()) && (paths[2] = world.getBlockState(curpos.north()).getBlock() instanceof FastRedstone ? EnumFacing.NORTH : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.south()) && (paths[3] = world.getBlockState(curpos.south()).getBlock() instanceof FastRedstone ? EnumFacing.SOUTH : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.east()) && (paths[4] = world.getBlockState(curpos.east()).getBlock() instanceof FastRedstone ? EnumFacing.EAST : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.west()) && (paths[5] = world.getBlockState(curpos.west()).getBlock() instanceof FastRedstone ? EnumFacing.WEST : null) != null) {
				directions++;
			}

			if (directions > 1) {
				for (int i = 0; i < paths.length; i++) {
					if (paths[i] != null) {
						visited.add(curpos);
						buildGraph(dag, world, curpos.offset(paths[i]), visited, inputs, delays, outputs, vertexMapping);
					}
				}
			} else if (directions == 0) {
				break;
			} else {
				for (int i = 0; i < paths.length; i++) {
					if (paths[i] != null) {
						visited.add(curpos);
						curpos = curpos.offset(paths[i]);
						break;
					}
				}
			}
		}

	}

}
