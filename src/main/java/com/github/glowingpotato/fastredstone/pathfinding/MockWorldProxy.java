package com.github.glowingpotato.fastredstone.pathfinding;

import java.util.HashMap;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public final class MockWorldProxy implements IWorldProxy {

	private HashMap<BlockPos, WireType> circuit;
	private HashMap<BlockPos, EnumFacing> facings;

	public MockWorldProxy(HashMap<BlockPos, WireType> circuit, HashMap<BlockPos, EnumFacing> facings) {
		this.circuit = circuit;
		this.facings = facings;
	}

	@Override
	public WireType getWireType(BlockPos pos) {
		return circuit.get(pos);
	}

	@Override
	public EnumFacing getWireDirection(BlockPos pos) {
		switch (getWireType(pos)) {
		case DELAYER:
		case INVERTER:
			return facings.get(pos);
		case INPUT:
		case OUTPUT:
		case WIRE:
			return null;
		}
		return null;
	}

}
