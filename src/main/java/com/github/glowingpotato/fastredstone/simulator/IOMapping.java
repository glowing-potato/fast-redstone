package com.github.glowingpotato.fastredstone.simulator;

import com.github.glowingpotato.fastredstone.graph.Vertex;

import net.minecraft.util.math.BlockPos;

public class IOMapping {
	private Vertex vertex;
	private boolean value;

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException("Vertex cannot be null");
		}
		this.vertex = vertex;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public IOMapping(Vertex vertex, boolean value) {
		setVertex(vertex);
		setValue(value);
	}

	public IOMapping(Vertex vertex) {
		this(vertex, false);
	}
}
