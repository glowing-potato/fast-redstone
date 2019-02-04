package com.github.glowingpotato.fastredstone.blocks;

import java.util.Random;

import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.pathfinding.CircuitMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;
import com.github.glowingpotato.fastredstone.tileentity.OutputTileEntity;
import com.github.glowingpotato.fastredstone.world.WireGraphData;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Output extends FastRedstone {

	public static final PropertyBool STATE = PropertyBool.create("powered");

	public Output() {
		// setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STATE, false));
	}

	@Override
	public int tickRate(World worldIn) {
		return 2;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new OutputTileEntity();
	}

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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}

	public void updateState(World worldIn, BlockPos pos) {

		// return true;
	}

	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(STATE) ? 15 : 0;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		System.out.println("Updating");
		WireGraphData data = WireGraphData.get(worldIn);
		Vertex output = data.getMapping().getOutputMap().get(pos);
		int value = 0;
		for (IOMapping iom : data.getMapping().getOutputs()) {
			if (output.equals(iom.getVertex())) {
				value = iom.getValue() ? 15 : 0;
				break;
			}
		}

		BlockPos north = pos.north();
		if (worldIn.getBlockState(north).getBlock() instanceof BlockRedstoneWire) {
			worldIn.setBlockState(north, worldIn.getBlockState(north).withProperty(BlockRedstoneWire.POWER, value));
		}
		BlockPos south = pos.south();
		if (worldIn.getBlockState(south).getBlock() instanceof BlockRedstoneWire) {
			worldIn.setBlockState(south, worldIn.getBlockState(south).withProperty(BlockRedstoneWire.POWER, value));
		}
		BlockPos east = pos.east();
		if (worldIn.getBlockState(east).getBlock() instanceof BlockRedstoneWire) {
			worldIn.setBlockState(east, worldIn.getBlockState(east).withProperty(BlockRedstoneWire.POWER, value));
		}
		BlockPos west = pos.west();
		if (worldIn.getBlockState(west).getBlock() instanceof BlockRedstoneWire) {
			worldIn.setBlockState(west, worldIn.getBlockState(west).withProperty(BlockRedstoneWire.POWER, value));
		}
	}

	@Override
	public void addToMapping(BlockPos pos, CircuitMapping mapping) {
		Vertex vertex = mapping.getGraph().createVertex();
		mapping.getOutputMap().put(pos, vertex);
		mapping.getOutputs().add(new IOMapping(vertex));
	}

	@Override
	public void removeFromMapping(BlockPos pos, CircuitMapping mapping) {
		mapping.getOutputMap().remove(pos);
		for (IOMapping map : mapping.getOutputs()) {
			if (map.getVertex().equals(mapping.getOutputMap().get(pos))) {
				mapping.getOutputs().remove(map);
			}
		}
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STATE, meta == 0 ? false : true);
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(STATE) ? 1 : 0;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STATE });
	}

}
