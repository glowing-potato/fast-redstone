package com.github.glowingpotato.fastredstone.tileentity;

import com.github.glowingpotato.fastredstone.blocks.Output;
import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;
import com.github.glowingpotato.fastredstone.world.WireGraphData;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OutputTileEntity extends TileEntity implements ITickable {

	@Override
	public void update() {
		World worldIn = this.getWorld();
		BlockPos pos = this.getPos();

		// System.out.println("Updating");
		WireGraphData data = WireGraphData.get(worldIn);
		if (data.getMapping() == null) {
			return;
		}
		Vertex output = data.getMapping().getOutputMap().get(pos);
		boolean value = false;
		for (IOMapping iom : data.getMapping().getOutputs()) {
			if (iom.getVertex() != null && output.equals(iom.getVertex())) {
				value = iom.getValue();
				break;
			}
		}

		getWorld().setBlockState(pos, getWorld().getBlockState(pos).withProperty(Output.STATE, value));
		// int value = 15;

//		BlockPos north = pos.north();
//		if (worldIn.getBlockState(north).getBlock() instanceof BlockRedstoneWire) {
//			worldIn.setBlockState(north, worldIn.getBlockState(north).withProperty(BlockRedstoneWire.POWER, value));
//			//worldIn.getBlockState(north).getBlock().met
//			
//		}
//		BlockPos south = pos.south();
//		if (worldIn.getBlockState(south).getBlock() instanceof BlockRedstoneWire) {
//			worldIn.setBlockState(south, worldIn.getBlockState(south).withProperty(BlockRedstoneWire.POWER, value));
//		}
//		BlockPos east = pos.east();
//		if (worldIn.getBlockState(east).getBlock() instanceof BlockRedstoneWire) {
//			worldIn.setBlockState(east, worldIn.getBlockState(east).withProperty(BlockRedstoneWire.POWER, value));
//		}
//		BlockPos west = pos.west();
//		if (worldIn.getBlockState(west).getBlock() instanceof BlockRedstoneWire) {
//			worldIn.setBlockState(west, worldIn.getBlockState(west).withProperty(BlockRedstoneWire.POWER, value));
//		}
	}

}
