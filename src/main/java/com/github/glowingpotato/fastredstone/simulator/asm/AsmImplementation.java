package com.github.glowingpotato.fastredstone.simulator.asm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.github.glowingpotato.fastredstone.graph.DAG;
import com.github.glowingpotato.fastredstone.graph.Edge;
import com.github.glowingpotato.fastredstone.graph.Vertex;
import com.github.glowingpotato.fastredstone.simulator.DelayMapping;
import com.github.glowingpotato.fastredstone.simulator.IOMapping;

public class AsmImplementation extends ClassLoader {
	private static int nonce = 1;
	private int modelHashCode;
	private IAsmedFunc obj;

	public int getModelHashCode() {
		return modelHashCode;
	}

	public void simulate(boolean[] inputs, boolean[] outputs) {
		obj.simulate(inputs, outputs);
	}

	private void writeBytecode(Collection<IOMapping> inputs, Collection<DelayMapping> delays,
			Collection<IOMapping> outputs, MethodVisitor mv) {
		Set<Vertex> done = new HashSet<>();
		for (IOMapping map : inputs) {
			done.add(map.getVertex());
		}
		for (DelayMapping map : delays) {
			done.add(map.getSource());
		}
		DAG dag = done.iterator().next().getDag();
		int i = 0;
		Map<Vertex, Integer> arrayMap = new HashMap<>();
		for (Vertex v : dag.getVertices()) {
			arrayMap.put(v, i++);
		}
		mv.visitLdcInsn(i);
		mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BOOLEAN);
		mv.visitVarInsn(Opcodes.ASTORE, 3);
		i = 0;
		for (IOMapping map : inputs) {
			mv.visitVarInsn(Opcodes.ALOAD, 3);
			mv.visitLdcInsn(arrayMap.get(map.getVertex()));
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitLdcInsn(i++);
			mv.visitInsn(Opcodes.BALOAD);
			mv.visitInsn(Opcodes.BASTORE);
		}
		for (DelayMapping map : delays) {
			mv.visitVarInsn(Opcodes.ALOAD, 3);
			mv.visitLdcInsn(arrayMap.get(map.getSource()));
			mv.visitVarInsn(Opcodes.ALOAD, 1);
			mv.visitLdcInsn(i++);
			mv.visitInsn(Opcodes.BALOAD);
			mv.visitInsn(Opcodes.BASTORE);
		}
		Set<Vertex> inProgress = new HashSet<>();
		for (Vertex vertex : done) {
			for (Edge edge : vertex.getSourcedEdges()) {
				inProgress.add(edge.getSink());
			}
		}
		Set<Vertex> newProgress = new HashSet<>();
		Set<Vertex> vertexSources = new HashSet<>(); 
		while (inProgress.size() > 0) {
			Iterator<Vertex> it = inProgress.iterator();
			mainLoop: while (it.hasNext()) {
				Vertex vertex = it.next();
				vertexSources.clear();
				for (Edge edge : vertex.getSunkEdges()) {
					Vertex source = edge.getSource();
					if (!done.contains(source)) {
						continue mainLoop;
					} else {
						vertexSources.add(source);
					}
				}
				mv.visitVarInsn(Opcodes.ALOAD, 3);
				mv.visitLdcInsn(arrayMap.get(vertex));
				for (Vertex v : vertexSources) {
					mv.visitVarInsn(Opcodes.ALOAD, 3);
					mv.visitLdcInsn(arrayMap.get(v));
					mv.visitInsn(Opcodes.BALOAD);
				}
				for (i = 1; i < vertexSources.size(); ++i) {
					mv.visitInsn(Opcodes.IOR);
				}
				mv.visitLdcInsn(true);
				mv.visitInsn(Opcodes.IXOR);
				mv.visitInsn(Opcodes.BASTORE);
				done.add(vertex);
				it.remove();
				for (Edge edge : vertex.getSourcedEdges()) {
					Vertex sink = edge.getSink();
					if (!done.contains(sink)) {
						newProgress.add(sink);
					}
				}
			}
			inProgress.addAll(newProgress);
			newProgress.clear();
		}
		i = 0;
		for (IOMapping map : outputs) {
			mv.visitVarInsn(Opcodes.ALOAD, 2);
			mv.visitLdcInsn(i++);
			mv.visitVarInsn(Opcodes.ALOAD, 3);
			mv.visitLdcInsn(arrayMap.get(map.getVertex()));
			mv.visitInsn(Opcodes.BALOAD);
			mv.visitLdcInsn(true);
			mv.visitInsn(Opcodes.IXOR);
			mv.visitInsn(Opcodes.BASTORE);
		}
		for (DelayMapping map : delays) {
			mv.visitVarInsn(Opcodes.ALOAD, 2);
			mv.visitLdcInsn(i++);
			mv.visitVarInsn(Opcodes.ALOAD, 3);
			mv.visitLdcInsn(arrayMap.get(map.getSink()));
			mv.visitInsn(Opcodes.BALOAD);
			mv.visitLdcInsn(true);
			mv.visitInsn(Opcodes.IXOR);
			mv.visitInsn(Opcodes.BASTORE);
		}
	}

	public AsmImplementation(Collection<IOMapping> inputs, Collection<DelayMapping> delays,
			Collection<IOMapping> outputs, int modelHashCode) throws ReflectiveOperationException {
		this.modelHashCode = modelHashCode;
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		String className = String.format("%s.Dynamic%d", getClass().getPackage().getName(), nonce++);
		cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className.replace('.', '/'), null, "java/lang/Object",
				new String[] { IAsmedFunc.class.getName().replace('.', '/') });
		MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "simulate", "([Z[Z)V", null, null);
		mv.visitCode();
		writeBytecode(inputs, delays, outputs, mv);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		cw.visitEnd();
		byte[] buf = cw.toByteArray();
		Class<?> cls = defineClass(className, buf, 0, buf.length);
		obj = (IAsmedFunc) cls.newInstance();
	}
}
