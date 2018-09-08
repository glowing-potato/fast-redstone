package com.github.glowingpotato.fastredstone.graph;

public class GraphSolveException extends RuntimeException {
	private static final long serialVersionUID = -7201973781179117481L;

	public GraphSolveException() {
	}

	public GraphSolveException(String message, Throwable cause) {
		super(message, cause);
	}

	public GraphSolveException(String message) {
		super(message);
	}

	public GraphSolveException(Throwable cause) {
		super(cause);
	}
}
