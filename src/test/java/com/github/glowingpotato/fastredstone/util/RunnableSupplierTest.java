package com.github.glowingpotato.fastredstone.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RunnableSupplierTest {
	@Test
	void testRunObject() {
		boolean[] res = new boolean[1];
		res[0] = false;
		RunnableSupplier obj = new RunnableSupplier(() -> res[0] = true);
		obj.run();
		Assertions.assertTrue(res[0]);
	}

	@Test
	void testRunOverride() {
		boolean[] res = new boolean[1];
		res[0] = false;
		RunnableSupplier obj = new RunnableSupplier() {
			@Override
			public void run() {
				res[0] = true;
			}
		};
		obj.run();
		Assertions.assertTrue(res[0]);
	}

	@Test
	void testGetObject() {
		boolean[] res = new boolean[1];
		res[0] = false;
		RunnableSupplier obj = new RunnableSupplier(() -> res[0] = true);
		obj.get();
		Assertions.assertTrue(res[0]);
	}

	@Test
	void testGetOverride() {
		boolean[] res = new boolean[1];
		res[0] = false;
		RunnableSupplier obj = new RunnableSupplier() {
			@Override
			public void run() {
				res[0] = true;
			}
		};
		obj.get();
		Assertions.assertTrue(res[0]);
	}

	@Test
	void testNullObject() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new RunnableSupplier(null));
	}

	@Test
	void testNullOverride() {
		RunnableSupplier obj = new RunnableSupplier() {
		};
		Assertions.assertThrows(RuntimeException.class, obj::run);
		Assertions.assertThrows(RuntimeException.class, obj::get);
	}
}
