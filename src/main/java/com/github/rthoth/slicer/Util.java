package com.github.rthoth.slicer;

import org.pcollections.Empty;
import org.pcollections.PVector;

import java.util.stream.Stream;

public class Util {

	@SuppressWarnings("Convert2MethodRef")
	public static <T> PVector<T> toVector(Stream<T> stream) {
		return stream.reduce(Empty.vector(), (vector, element) -> vector.plus(element), (v1, v2) -> v1.plusAll(v2));
	}
}
