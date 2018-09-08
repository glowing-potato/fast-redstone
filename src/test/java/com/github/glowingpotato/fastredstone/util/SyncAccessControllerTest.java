package com.github.glowingpotato.fastredstone.util;

import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.opentest4j.AssertionFailedError;

@TestInstance(Lifecycle.PER_CLASS)
class SyncAccessControllerTest {
	private static final long SLEEP_PERIOD = 10;
	private static final long ORDERING_SLEEP_PERIOD = 2;
	private static final long TIMING_CHECKS = 10;
	private SyncAccessController obj;

	@BeforeAll
	void setup() {
		obj = new SyncAccessController();
	}

	@AfterAll
	void cleanup() {
		obj = null;
	}

	private static void concurrent(UnstableRunnable... funcs) {
		Thread[] threads = new Thread[funcs.length];
		AssertionFailedError[] error = new AssertionFailedError[1];
		Semaphore semaphore = new Semaphore(0);
		for (int i = 0; i < funcs.length; ++i) {
			int j = i;
			threads[i] = new Thread(() -> {
				try {
					try {
						semaphore.acquire();
						funcs[j].run();
					} catch (Exception ex) {
						Assertions.fail(ex);
					}
				} catch (AssertionFailedError err) {
					error[0] = err;
				}
			});
			threads[i].start();
		}
		semaphore.release(Integer.MAX_VALUE);
		try {
			for (Thread thread : threads) {
				thread.join();
			}
		} catch (InterruptedException ex) {
			Assertions.fail(ex);
		}
	}

	@Test
	void testRead() {
		boolean[] res = new boolean[2];
		res[0] = false;
		res[1] = false;
		obj.read(() -> {
			res[0] = true;
		});
		obj.read(() -> res[1] = true);
		Assertions.assertTrue(res[0]);
		Assertions.assertTrue(res[1]);
	}

	@Test
	void testWrite() {
		boolean[] res = new boolean[2];
		res[0] = false;
		res[1] = false;
		obj.write(() -> {
			res[0] = true;
		});
		obj.write(() -> res[1] = true);
		Assertions.assertTrue(res[0]);
		Assertions.assertTrue(res[1]);
	}

	@Test
	void testConcurrentReads() {
		for (int i = 0; i < TIMING_CHECKS; ++i) {
			int[] res = new int[5];
			res[0] = 1;
			Semaphore semaphore = new Semaphore(1);
			concurrent(() -> {
				obj.read(() -> {
					try {
						semaphore.acquire();
						res[res[0]++] = 1;
						semaphore.release();
						Thread.sleep(SLEEP_PERIOD);
						semaphore.acquire();
						res[res[0]++] = 2;
						semaphore.release();
					} catch (InterruptedException ex) {
						Assertions.fail(ex);
					}
				});
			}, () -> {
				obj.read(() -> {
					try {
						semaphore.acquire();
						res[res[0]++] = 3;
						semaphore.release();
						Thread.sleep(SLEEP_PERIOD);
						semaphore.acquire();
						res[res[0]++] = 4;
						semaphore.release();
					} catch (InterruptedException ex) {
						Assertions.fail(ex);
					}
				});
			});
			Assertions.assertSame(5, res[0]);
			if (res[1] == 1) {
				Assertions.assertSame(1, res[1]);
				Assertions.assertSame(3, res[2]);
			} else {
				Assertions.assertSame(3, res[1]);
				Assertions.assertSame(1, res[2]);
			}
		}
	}

	@Test
	void testConcurrentWrites() {
		for (int i = 0; i < TIMING_CHECKS; ++i) {
			int[] res = new int[5];
			res[0] = 1;
			concurrent(() -> {
				obj.write(() -> {
					res[res[0]++] = 1;
					try {
						Thread.sleep(SLEEP_PERIOD);
					} catch (InterruptedException ex) {
						Assertions.fail(ex);
					}
					res[res[0]++] = 2;
				});
			}, () -> {
				obj.write(() -> {
					res[res[0]++] = 3;
					try {
						Thread.sleep(SLEEP_PERIOD);
					} catch (InterruptedException ex) {
						Assertions.fail(ex);
					}
					res[res[0]++] = 4;
				});
			});
			Assertions.assertSame(5, res[0]);
			if (res[1] == 1) {
				Assertions.assertSame(1, res[1]);
				Assertions.assertSame(2, res[2]);
				Assertions.assertSame(3, res[3]);
				Assertions.assertSame(4, res[4]);
			} else {
				Assertions.assertSame(3, res[1]);
				Assertions.assertSame(4, res[2]);
				Assertions.assertSame(1, res[3]);
				Assertions.assertSame(2, res[4]);
			}
		}
	}

	@Test
	void testRWLocks() {
		for (int i = 0; i < TIMING_CHECKS; ++i) {
			int[] res = new int[7];
			res[0] = 1;
			concurrent(() -> {
				obj.read(() -> {
					res[res[0]++] = 1;
					try {
						Thread.sleep(SLEEP_PERIOD);
					} catch (InterruptedException ex) {
						Assertions.fail(ex);
					}
					res[res[0]++] = 2;
				});
			}, () -> {
				Thread.sleep(ORDERING_SLEEP_PERIOD);
				obj.write(() -> {
					res[res[0]++] = 3;
					try {
						Thread.sleep(SLEEP_PERIOD);
					} catch (InterruptedException ex) {
						Assertions.fail(ex);
					}
					res[res[0]++] = 4;
				});
			}, () -> {
				Thread.sleep(2 * ORDERING_SLEEP_PERIOD);
				obj.read(() -> {
					res[res[0]++] = 5;
					try {
						Thread.sleep(SLEEP_PERIOD);
					} catch (InterruptedException ex) {
						Assertions.fail(ex);
					}
					res[res[0]++] = 6;
				});
			});
			Assertions.assertSame(7, res[0]);
			Assertions.assertSame(1, res[1]);
			Assertions.assertSame(2, res[2]);
			Assertions.assertSame(3, res[3]);
			Assertions.assertSame(4, res[4]);
			Assertions.assertSame(5, res[5]);
			Assertions.assertSame(6, res[6]);
		}
	}
}
