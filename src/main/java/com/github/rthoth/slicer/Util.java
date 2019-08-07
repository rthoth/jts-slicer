package com.github.rthoth.slicer;

import org.pcollections.Empty;
import org.pcollections.PVector;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Util {

	public static <T> PVector<T> toPVector(Stream<T> stream) {
		final AtomicReference<PVector<T>> vector = new AtomicReference<>(Empty.vector());

		stream.forEach(elem -> vector.set(vector.get().plus(elem)));

		return vector.get();
	}
}
