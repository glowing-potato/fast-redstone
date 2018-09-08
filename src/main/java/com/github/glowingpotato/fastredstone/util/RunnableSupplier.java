package com.github.glowingpotato.fastredstone.util;

import java.util.function.Supplier;

public class RunnableSupplier implements Runnable, Supplier<Object> {
	private Runnable impl;

	@Override
	public Object get() {
		run();
		return null;
	}

	@Override
	public void run() {
		if (impl == null) {
			throw new RuntimeException(new NoSuchMethodException("RunnableSupplier.run() was not overridden."));
		}
		impl.run();
	}

	public RunnableSupplier(Runnable impl) {
		if (impl == null) {
			throw new IllegalArgumentException("Runnable implementation cannot be null.");
		}
		this.impl = impl;
	}

	protected RunnableSupplier() {
	}
}
