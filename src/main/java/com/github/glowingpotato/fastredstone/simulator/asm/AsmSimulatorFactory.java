package com.github.glowingpotato.fastredstone.simulator.asm;

import java.util.Collection;

import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;
import com.github.glowingpotato.fastredstone.simulator.ISimulator;

public class AsmSimulatorFactory implements ISimulator {
	private AsmImplementation impl;

	int hashCodeModel(Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs) {
		final int prime = 31;
		int result = 1;
		result = prime * result + inputs.size();
		for (IOMapping map : inputs) {
			result = prime * result + map.getVertex().singleHashCode();
		}
		result = prime * result + delays.size();
		for (DelayMapping map : delays) {
			result = prime * result + map.getSource().singleHashCode();
			result = prime * result + map.getSink().singleHashCode();
		}
		result = prime * result + outputs.size();
		for (IOMapping map : outputs) {
			result = prime * result + map.getVertex().singleHashCode();
		}
		return result;
	}

	@Override
	public void simulate(Collection<IOMapping> inputs, Collection<DelayMapping> delays, Collection<IOMapping> outputs) {
		int modelHashCode = hashCodeModel(inputs, delays, outputs);
		boolean[] in = new boolean[inputs.size() + delays.size()];
		boolean[] out = new boolean[outputs.size() + delays.size()];
		int i = 0;
		for (IOMapping map : inputs) {
			in[i++] = map.getValue();
		}
		for (DelayMapping map : delays) {
			in[i++] = map.getValue();
		}
		if (impl == null || modelHashCode != impl.getModelHashCode()) {
			try {
				impl = new AsmImplementation(inputs, delays, outputs, modelHashCode);
			} catch (ReflectiveOperationException ex) {
				throw new RuntimeException(ex);
			}
		}
		impl.simulate(in, out);
		i = 0;
		for (IOMapping map : outputs) {
			map.setValue(out[i++]);
		}
		for (DelayMapping map : delays) {
			map.setValue(out[i++]);
		}
	}
}
