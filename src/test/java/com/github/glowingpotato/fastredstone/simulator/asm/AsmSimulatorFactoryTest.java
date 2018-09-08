package com.github.glowingpotato.fastredstone.simulator.asm;

import org.junit.jupiter.api.BeforeAll;

import com.github.glowingpotato.fastredstone.simulator.SimulatorTest;

class AsmSimulatorFactoryTest extends SimulatorTest {
	@BeforeAll
	void setup() {
		sim = new AsmSimulatorFactory();
	}
}
