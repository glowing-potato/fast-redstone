package com.github.glowingpotato.fastredstone.pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;
import com.github.glowingpotato.fastredstone.util.KeyValuePair;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PathFinderImpl implements IPathFinder {

	@Override
	public CircuitMapping buildPath(IWorldProxy proxy, BlockPos pos) {
		DAG graph = new DAG();
		Collection<IOMapping> inputs = new ArrayList<IOMapping>();
		Collection<DelayMapping> delays = new ArrayList<DelayMapping>();
		Collection<IOMapping> outputs = new ArrayList<IOMapping>();
		HashMap<BlockPos, Vertex> inputMap = new HashMap<BlockPos, Vertex>();
		HashMap<BlockPos, KeyValuePair<Vertex, Vertex>> delayMap = new HashMap<BlockPos, KeyValuePair<Vertex, Vertex>>();
		HashMap<BlockPos, Vertex> outputMap = new HashMap<BlockPos, Vertex>();

		findSubPath(proxy, pos, new ArrayList<BlockPos>(), graph, inputs, delays, outputs, inputMap, delayMap, outputMap);

		for (IOMapping input : inputs) {
			for (DelayMapping delay : delays) {
				graph.createEdge(input.getVertex(), delay.getSink());
			}
		}
		for (IOMapping output : outputs) {
			for (DelayMapping delay : delays) {
				graph.createEdge(delay.getSource(), output.getVertex());
			}
		}
		for (IOMapping input : inputs) {
			for (IOMapping output : outputs) {
				graph.createEdge(input.getVertex(), output.getVertex());
			}
		}

		return new CircuitMapping(graph, inputs, delays, outputs);

	}

	private void findSubPath(IWorldProxy proxy, BlockPos pos, ArrayList<BlockPos> visited, DAG graph, Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs, HashMap<BlockPos, Vertex> inputMap,
			HashMap<BlockPos, KeyValuePair<Vertex, Vertex>> delayMap, HashMap<BlockPos, Vertex> outputMap) {
		EnumFacing[] paths = new EnumFacing[6];
		BlockPos curpos = pos;
		BlockPos prevpos = pos;
		int directions = 0;
		while (true) {
			visited.add(curpos);

			if (!visited.contains(curpos.up()) && (paths[0] = proxy.getWireType(curpos.up()) != null ? EnumFacing.UP : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.down()) && (paths[1] = proxy.getWireType(curpos.down()) != null ? EnumFacing.DOWN : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.north()) && (paths[2] = proxy.getWireType(curpos.north()) != null ? EnumFacing.NORTH : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.south()) && (paths[3] = proxy.getWireType(curpos.south()) != null ? EnumFacing.SOUTH : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.east()) && (paths[4] = proxy.getWireType(curpos.east()) != null ? EnumFacing.EAST : null) != null) {
				directions++;
			}
			if (!visited.contains(curpos.west()) && (paths[5] = proxy.getWireType(curpos.west()) != null ? EnumFacing.WEST : null) != null) {
				directions++;
			}

			WireType wireType = proxy.getWireType(curpos);
			switch (wireType) {
			case DELAYER:
				if (proxy.getWireDirection(curpos) == getBlockDirection(prevpos, curpos)) {
					Vertex sink = graph.createVertex();
					delays.add(new DelayMapping(null, sink));
				} else if (proxy.getWireDirection(curpos) == getBlockDirection(prevpos, curpos).getOpposite()) {
					Vertex source = graph.createVertex();
					delays.add(new DelayMapping(source, null));
				}
				return;
			case INPUT:
				Vertex input = graph.createVertex();
				inputs.add(new IOMapping(input));
				return;
			case INVERTER:
				Vertex inverter = graph.createVertex();
				return;
			case OUTPUT:
				Vertex output = graph.createVertex();
				outputs.add(new IOMapping(output));
				return;
			case WIRE:
				break;
			default:
				break;

			}

			if (directions > 1) {
				for (int i = 0; i < paths.length; i++) {
					if (paths[i] != null) {
						findSubPath(proxy, curpos.offset(paths[i]), visited, graph, inputs, delays, outputs, inputMap, delayMap, outputMap);
					}
				}
			} else if (directions == 0) {
				return;
			} else {
				for (int i = 0; i < paths.length; i++) {
					if (paths[i] != null) {
						visited.add(curpos);
						curpos = curpos.offset(paths[i]);
						break;
					}
				}
			}

			prevpos = curpos;
			directions = 0;
		}
	}

	private EnumFacing getBlockDirection(BlockPos src, BlockPos target) {
		if (src.up().equals(target)) {
			return EnumFacing.UP;
		} else if (src.up().equals(target)) {
			return EnumFacing.DOWN;
		} else if (src.up().equals(target)) {
			return EnumFacing.NORTH;
		} else if (src.up().equals(target)) {
			return EnumFacing.SOUTH;
		} else if (src.up().equals(target)) {
			return EnumFacing.EAST;
		} else if (src.up().equals(target)) {
			return EnumFacing.WEST;
		}
		return null;
	}

}
