package com.github.glowingpotato.fastredstone.util;

import java.util.concurrent.Semaphore;
import java.util.function.Supplier;

public class SyncAccessController {
	private static final int READ_PERMITS = Integer.MAX_VALUE;
	private Semaphore readSemaphore;
	private Semaphore writeSemaphore;

	public void read(Runnable func) {
		read((Supplier<Object>) new RunnableSupplier(func));
	}

	public <T> T read(Supplier<T> func) {
		try {
			if (writeSemaphore.availablePermits() == 0) {
				for (StackTraceElement frame : Thread.currentThread().getStackTrace()) {
					if (frame.getClassName() == SyncAccessController.class.getName()
							&& frame.getMethodName() == "write") {
						return func.get();
					}
				}
				writeSemaphore.acquire();
				writeSemaphore.release();
			}
			if (!readSemaphore.tryAcquire()) {
				throw new IllegalStateException("There are too many concurrent reads to keep track of.");
			}
			try {
				return func.get();
			} finally {
				readSemaphore.release();
			}
		} catch (InterruptedException ex) {
			return func.get();
		}
	}

	public void write(Runnable func) {
		write((Supplier<Object>) new RunnableSupplier(func));
	}

	public <T> T write(Supplier<T> func) {
		try {
			if (writeSemaphore.availablePermits() == 0) {
				for (StackTraceElement frame : Thread.currentThread().getStackTrace()) {
					if (frame.getClassName() == SyncAccessController.class.getName()
							&& frame.getMethodName() == "write") {
						return func.get();
					}
				}
			}
			writeSemaphore.acquire();
			try {
				readSemaphore.acquire(READ_PERMITS);
				try {
					return func.get();
				} finally {
					readSemaphore.release(READ_PERMITS);
				}
			} finally {
				writeSemaphore.release();
			}
		} catch (InterruptedException ex) {
			return func.get();
		}
	}

	public SyncAccessController() {
		readSemaphore = new Semaphore(READ_PERMITS);
		writeSemaphore = new Semaphore(1);
	}
}
