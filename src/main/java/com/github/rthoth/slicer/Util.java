package com.github.rthoth.slicer;

import org.pcollections.Empty;
import org.pcollections.PSequence;
import org.pcollections.PVector;

import java.util.Arrays;
import java.util.stream.Stream;

public class Util {

	interface TriDoubleFunction<R> {
		R apply(double t, double u, double v);
	}

	public static <T> PVector<T> toVector(Stream<T> stream) {
		return stream.reduce(Empty.vector(), PVector::plus, PVector::plusAll);
	}

	public static <G extends Guide<?>> PSequence<G> toSequence(double[] values, double offset, double extrusion, TriDoubleFunction<G> function) {
		return toVector(Arrays.stream(values).mapToObj(v -> function.apply(v, offset, extrusion)));
	}
}
