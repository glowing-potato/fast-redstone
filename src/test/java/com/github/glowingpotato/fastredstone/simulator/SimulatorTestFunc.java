package com.github.glowingpotato.fastredstone.simulator;

import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;

@FunctionalInterface
public interface SimulatorTestFunc {
	void test(IOMapping[] inputs, DelayMapping[] delays, IOMapping[] outputs, Runnable simulate);
}
