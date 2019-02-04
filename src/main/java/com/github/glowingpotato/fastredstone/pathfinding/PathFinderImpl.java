package com.github.glowingpotato.fastredstone.pathfinding;

import java.util.ArrayList;

import com.github.glowingpotato.fastredstone.graph.Vertex;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PathFinderImpl implements IPathFinder {

//	@Override
//	public CircuitMapping buildPath(IWorldProxy proxy, BlockPos pos) {
//		DAG graph = new DAG();
//		Collection<IOMapping> inputs = new ArrayList<IOMapping>();
//		Collection<DelayMapping> delays = new ArrayList<DelayMapping>();
//		Collection<IOMapping> outputs = new ArrayList<IOMapping>();
//		HashMap<BlockPos, Vertex> inputMap = new HashMap<BlockPos, Vertex>();
//		HashMap<BlockPos, KeyValuePair<Vertex, Vertex>> delayMap = new HashMap<BlockPos, KeyValuePair<Vertex, Vertex>>();
//		HashMap<BlockPos, Vertex> outputMap = new HashMap<BlockPos, Vertex>();
//		HashMap<BlockPos, Vertex> inverterInputMap = new HashMap<BlockPos, Vertex>();
//		HashMap<BlockPos, Vertex> inverterOutputMap = new HashMap<BlockPos, Vertex>();
//
//		findSubPath(proxy, pos, new ArrayList<BlockPos>(), graph, inputs, delays, outputs, inputMap, delayMap, outputMap, inverterInputMap, inverterOutputMap);
//
//		for (IOMapping input : inputs) {
//			for (DelayMapping delay : delays) {
//				graph.createEdge(input.getVertex(), delay.getSink());
//			}
//		}
//		for (IOMapping output : outputs) {
//			for (DelayMapping delay : delays) {
//				graph.createEdge(delay.getSource(), output.getVertex());
//			}
//		}
//		for (IOMapping input : inputs) {
//			for (IOMapping output : outputs) {
//				graph.createEdge(input.getVertex(), output.getVertex());
//			}
//		}
//
//		return new CircuitMapping(graph, inputs, delays, outputs, inputMap, delayMap, outputMap, inverterInputMap, inverterOutputMap);
//
//	}
	@Override
	public void connectToNetwork(IWorldProxy proxy, CircuitMapping mapping, BlockPos pos) {

		boolean doPathfind = false;
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		switch (proxy.getWireType(pos)) {
		case DELAYER:
			break;
		case INPUT:
			// vertex = mapping.getGraph().createVertex();
			// visited = new ArrayList<BlockPos>();
			// mapping.getInputMap().put(pos, vertex);
			vertices.add(mapping.getInputMap().get(pos));
			doPathfind = true;
			break;
		case INVERTER:
			vertices.add(mapping.getInverterInputMap().get(pos));
			vertices.add(mapping.getInverterOutputMap().get(pos));
			doPathfind = true;
			break;
		case OUTPUT:
			// vertex = mapping.getGraph().createVertex();
			// visited = new ArrayList<BlockPos>();
			// mapping.getOutputMap().put(pos, vertex);
			vertices.add(mapping.getOutputMap().get(pos));
			doPathfind = true;
			break;
		case WIRE:
			return;
		default:
			break;
		}
		if (doPathfind) {
			ArrayList<BlockPos> visited = new ArrayList<BlockPos>();
			for (Vertex vertex : vertices) {
				pathfindToNetwork(proxy, vertex, mapping, visited, pos, pos.up());
				pathfindToNetwork(proxy, vertex, mapping, visited, pos, pos.down());
				pathfindToNetwork(proxy, vertex, mapping, visited, pos, pos.north());
				pathfindToNetwork(proxy, vertex, mapping, visited, pos, pos.south());
				pathfindToNetwork(proxy, vertex, mapping, visited, pos, pos.east());
				pathfindToNetwork(proxy, vertex, mapping, visited, pos, pos.west());
			}
		}
	}

	private void pathfindToNetwork(IWorldProxy proxy, Vertex source, CircuitMapping mapping, ArrayList<BlockPos> visited, BlockPos prevPos, BlockPos pos) {
		if (proxy.getWireType(pos) == null || visited.contains(pos)) {
			return;
		}
		visited.add(pos);
		System.out.println("Checking at " + pos.toString());
		switch (proxy.getWireType(pos)) {
		case DELAYER:
			// TODO implement
			break;
		case INPUT:

			break;
		case INVERTER:
			// TODO implement
			EnumFacing dir = proxy.getWireDirection(pos);
			switch (dir) {
			case EAST:
				if (pos.equals(prevPos.east())) {
					System.out.println("Connecting to inverter sink");
					Vertex vertex = mapping.getInverterInputMap().get(pos);
					mapping.getGraph().createEdge(source, vertex);
				} else if (pos.equals(prevPos.west())) {
					System.out.println("Connecting to inverter source");
					Vertex vertex = mapping.getInverterOutputMap().get(pos);
					mapping.getGraph().createEdge(vertex, source);
				}
				break;
			case NORTH:
				if (pos.equals(prevPos.north())) {
					System.out.println("Connecting to inverter sink");
					Vertex vertex = mapping.getInverterInputMap().get(pos);
					mapping.getGraph().createEdge(source, vertex);
				} else if (pos.equals(prevPos.south())) {
					System.out.println("Connecting to inverter source");
					Vertex vertex = mapping.getInverterOutputMap().get(pos);
					mapping.getGraph().createEdge(vertex, source);
				}
				break;
			case SOUTH:
				if (pos.equals(prevPos.south())) {
					System.out.println("Connecting to inverter sink");
					Vertex vertex = mapping.getInverterInputMap().get(pos);
					mapping.getGraph().createEdge(source, vertex);
				} else if (pos.equals(prevPos.north())) {
					System.out.println("Connecting to inverter source");
					Vertex vertex = mapping.getInverterOutputMap().get(pos);
					mapping.getGraph().createEdge(vertex, source);
				}
				break;
			case WEST:
				if (pos.equals(prevPos.west())) {
					System.out.println("Connecting to inverter sink");
					Vertex vertex = mapping.getInverterInputMap().get(pos);
					mapping.getGraph().createEdge(source, vertex);
				} else if (pos.equals(prevPos.east())) {
					System.out.println("Connecting to inverter source");
					Vertex vertex = mapping.getInverterOutputMap().get(pos);
					mapping.getGraph().createEdge(vertex, source);
				}
				break;
			default:
				System.out.println("Inverter can't have this orientation");
				break;

			}
			break;
		case OUTPUT:
			Vertex sink = mapping.getOutputMap().get(pos);
			mapping.getGraph().createEdge(source, sink);
			System.out.println("Found output at " + pos + ", connecting to source");
			break;
		case WIRE:
			pathfindToNetwork(proxy, source, mapping, visited, pos, pos.up());
			pathfindToNetwork(proxy, source, mapping, visited, pos, pos.down());
			pathfindToNetwork(proxy, source, mapping, visited, pos, pos.north());
			pathfindToNetwork(proxy, source, mapping, visited, pos, pos.south());
			pathfindToNetwork(proxy, source, mapping, visited, pos, pos.east());
			pathfindToNetwork(proxy, source, mapping, visited, pos, pos.west());
			break;
		default:
			break;

		}
	}
//	private void findSubPath(IWorldProxy proxy, BlockPos pos, ArrayList<BlockPos> visited, DAG graph, Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs, HashMap<BlockPos, Vertex> inputMap,
//			HashMap<BlockPos, KeyValuePair<Vertex, Vertex>> delayMap, HashMap<BlockPos, Vertex> outputMap, HashMap<BlockPos, Vertex> inverterInputMap, HashMap<BlockPos, Vertex> inverterOutputMap) {
//		EnumFacing[] paths = new EnumFacing[6];
//		BlockPos curpos = pos;
//		BlockPos prevpos = pos;
//		int directions = 0;
//		while (true) {
//			visited.add(curpos);
//
//			if (!visited.contains(curpos.up()) && (paths[0] = proxy.getWireType(curpos.up()) != null ? EnumFacing.UP : null) != null) {
//				directions++;
//			}
//			if (!visited.contains(curpos.down()) && (paths[1] = proxy.getWireType(curpos.down()) != null ? EnumFacing.DOWN : null) != null) {
//				directions++;
//			}
//			if (!visited.contains(curpos.north()) && (paths[2] = proxy.getWireType(curpos.north()) != null ? EnumFacing.NORTH : null) != null) {
//				directions++;
//			}
//			if (!visited.contains(curpos.south()) && (paths[3] = proxy.getWireType(curpos.south()) != null ? EnumFacing.SOUTH : null) != null) {
//				directions++;
//			}
//			if (!visited.contains(curpos.east()) && (paths[4] = proxy.getWireType(curpos.east()) != null ? EnumFacing.EAST : null) != null) {
//				directions++;
//			}
//			if (!visited.contains(curpos.west()) && (paths[5] = proxy.getWireType(curpos.west()) != null ? EnumFacing.WEST : null) != null) {
//				directions++;
//			}
//
//			System.out.println(proxy);
//			
//			WireType wireType = proxy.getWireType(curpos);
//			switch (wireType) {
//			case DELAYER:
//				if (proxy.getWireDirection(curpos) == getBlockDirection(prevpos, curpos)) {
//					Vertex sink = graph.createVertex();
//					delayMap.put(curpos, new KeyValuePair<>(sink, null));
//					delays.add(new DelayMapping(null, sink));
//				} else if (proxy.getWireDirection(curpos) == getBlockDirection(prevpos, curpos).getOpposite()) {
//					Vertex source = graph.createVertex();
//					delayMap.put(curpos, new KeyValuePair<>(null, source));
//					delays.add(new DelayMapping(source, null));
//				}
//				return;
//			case INPUT:
//				Vertex input = graph.createVertex();
//				inputMap.put(curpos, input);
//				inputs.add(new IOMapping(input));
//				return;
//			case INVERTER:
//				if (proxy.getWireDirection(curpos) == getBlockDirection(prevpos, curpos)) {
//					Vertex inverterOutput = graph.createVertex();
//					inverterOutputMap.put(curpos, inverterOutput);
//				} else if (proxy.getWireDirection(curpos) == getBlockDirection(prevpos, curpos).getOpposite()) {
//					Vertex inverterInput = graph.createVertex();
//					inverterInputMap.put(curpos, inverterInput);
//				}
//				return;
//			case OUTPUT:
//				Vertex output = graph.createVertex();
//				outputMap.put(curpos, output);
//				outputs.add(new IOMapping(output));
//				return;
//			case WIRE:
//				break;
//			default:
//				break;
//
//			}
//
//			if (directions > 1) {
//				for (int i = 0; i < paths.length; i++) {
//					if (paths[i] != null) {
//						findSubPath(proxy, curpos.offset(paths[i]), visited, graph, inputs, delays, outputs, inputMap, delayMap, outputMap, inverterInputMap, inverterOutputMap);
//					}
//				}
//			} else if (directions == 0) {
//				return;
//			} else {
//				for (int i = 0; i < paths.length; i++) {
//					if (paths[i] != null) {
//						visited.add(curpos);
//						curpos = curpos.offset(paths[i]);
//						break;
//					}
//				}
//			}
//
//			prevpos = curpos;
//			directions = 0;
//		}
//	}

	private EnumFacing getBlockDirection(BlockPos src, BlockPos target) {
		if (src.up().equals(target)) {
			return EnumFacing.UP;
		} else if (src.down().equals(target)) {
			return EnumFacing.DOWN;
		} else if (src.north().equals(target)) {
			return EnumFacing.NORTH;
		} else if (src.south().equals(target)) {
			return EnumFacing.SOUTH;
		} else if (src.east().equals(target)) {
			return EnumFacing.EAST;
		} else if (src.west().equals(target)) {
			return EnumFacing.WEST;
		}
		return EnumFacing.UP;
	}

}
