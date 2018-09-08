package com.github.glowingpotato.fastredstone.simulator.slow;

import org.junit.jupiter.api.BeforeAll;

import com.github.glowingpotato.fastredstone.simulator.SimulatorTest;

class SlowSimulatorTest extends SimulatorTest {
	@BeforeAll
	void setup() {
		sim = new SlowSimulator();
	}
}
