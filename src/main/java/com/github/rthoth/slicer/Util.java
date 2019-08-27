package com.github.rthoth.slicer;

import org.pcollections.PSequence;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Util {

	public static <T> Collector<T, LinkedList<T>, PVector<T>> toVector() {
		return new Collector<T, LinkedList<T>, PVector<T>>() {
			@Override
			public Supplier<LinkedList<T>> supplier() {
				return LinkedList::new;
			}

			@Override
			public BiConsumer<LinkedList<T>, T> accumulator() {
				return LinkedList::add;
			}

			@Override
			public BinaryOperator<LinkedList<T>> combiner() {
				return (l1, l2) -> {
					LinkedList<T> l = new LinkedList<>(l1);
					l.addAll(l2);
					return l;
				};
			}

			@Override
			public Function<LinkedList<T>, PVector<T>> finisher() {
				return TreePVector::from;
			}

			@Override
			public Set<Characteristics> characteristics() {
				return Collections.singleton(Characteristics.CONCURRENT);
			}
		};
	}

	interface TriDoubleFunction<R> {
		R apply(double t, double u, double v);
	}

	public static <G extends Guide<?>> PSequence<G> toSequence(double[] values, double offset, double extrusion, TriDoubleFunction<G> function) {
		return Arrays.stream(values).mapToObj(v -> function.apply(v, offset, extrusion)).collect(toVector());
	}
}
