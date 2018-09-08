package com.github.glowingpotato.fastredstone.simulator;

import java.util.Collection;

public interface ISimulator {
	void simulate(Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs);
}
