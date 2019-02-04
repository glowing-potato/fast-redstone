package com.github.glowingpotato.fastredstone.blocks;

import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.pathfinding.CircuitMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;
import com.github.glowingpotato.fastredstone.world.WireGraphData;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Input extends FastRedstone {

	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		System.out.println("neighborChanged");
		if (!worldIn.isRemote) {
			// worldIn.isBlockPowered(pos)
			System.out.println("State changed");

			WireGraphData data = WireGraphData.get(worldIn);
			CircuitMapping mapping = data.getMapping();
			Vertex input = mapping.getInputMap().get(pos);
			for (IOMapping iom : mapping.getInputs()) {
				if (iom.getVertex().equals(input)) {
					IBlockState bs = worldIn.getBlockState(pos.north());
					iom.setValue(bs.getValue(BlockRedstoneWire.POWER) > 0);
					//iom.setValue(true);
					System.out.println(iom.getValue());
					break;
				}
			}
			getSimulator().simulate(mapping.getInputs(), mapping.getDelays(), mapping.getOutputs());
		}
	}

//	@Override
//	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
//		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
//		WireGraphData data = WireGraphData.get(worldIn);
//		CircuitMapping mapping = data.getMapping();
//		mapping.getInputMap().put(pos, mapping.getGraph().createVertex());
//	}

//	@Override
//	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
//		super.onBlockHarvested(worldIn, pos, state, player);
//		WireGraphData data = WireGraphData.get(worldIn);
//		CircuitMapping mapping = data.getMapping();
//		mapping.getInputMap().remove(pos);
//	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		if (world.getBlockState(pos).getBlock().equals(net.minecraft.init.Blocks.REDSTONE_WIRE)) {
			return true;
		}
		return false;
	}

	@Override
	public void addToMapping(BlockPos pos, CircuitMapping mapping) {
		Vertex vertex = mapping.getGraph().createVertex();
		mapping.getInputMap().put(pos, vertex);
		mapping.getInputs().add(new IOMapping(vertex));
	}

	@Override
	public void removeFromMapping(BlockPos pos, CircuitMapping mapping) {
		mapping.getInputMap().remove(pos);
		for (IOMapping map : mapping.getInputs()) {
			if (map.getVertex().equals(mapping.getInputMap().get(pos))) {
				mapping.getInputs().remove(map);
			}
		}
	}

}
