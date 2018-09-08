package com.github.glowingpotato.fastredstone.simulator;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Vertex;

import net.minecraft.util.math.BlockPos;

public class DelayMapping {

	private Vertex source;
	private Vertex sink;
	private boolean value;

	public DAG getDag() {
		return source.getDag();
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		if (source == null) {
			throw new IllegalArgumentException("Source vertex cannot be null.");
		}
		if (sink != null && sink.getDag() != source.getDag()) {
			throw new IllegalArgumentException("Source and sink vertices must both be on the same DAG.");
		}
		if (source == sink) {
			throw new IllegalArgumentException("Source and sink vertices cannot be the same.");
		}
		this.source = source;
	}

	public Vertex getSink() {
		return sink;
	}

	public void setSink(Vertex sink) {
		if (sink == null) {
			throw new IllegalArgumentException("Sink vertex cannot be null.");
		}
		if (source != null && source.getDag() != sink.getDag()) {
			throw new IllegalArgumentException("Source and sink vertices must both be on the same DAG.");
		}
		if (source == sink) {
			throw new IllegalArgumentException("Source and sink vertices cannot be the same.");
		}
		this.sink = sink;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public DelayMapping(Vertex source, Vertex sink, boolean value) {
		setSource(source);
		setSink(sink);
		setValue(value);
	}

	public DelayMapping(Vertex source, Vertex sink) {
		this(source, sink, false);
	}
}
