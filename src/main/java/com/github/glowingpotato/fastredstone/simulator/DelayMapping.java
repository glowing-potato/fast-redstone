package com.github.glowingpotato.fastredstone.simulator;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Vertex;

public class DelayMapping {
	private Vertex source;
	private Vertex sink;

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
		this.sink = sink;
	}

	public DelayMapping(Vertex source, Vertex sink) {
		setSource(source);
		setSink(sink);
	}
}
