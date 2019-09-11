package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.PSequence;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Sequences<S extends Sequences.Seq> implements Iterable<S> {

	private final PSequence<S> seqs;

	public Sequences(PSequence<S> seqs) {
		this.seqs = seqs;
	}

	@Override
	public Iterator<S> iterator() {
		return seqs.iterator();
	}

	@Override
	public void forEach(Consumer<? super S> action) {
		seqs.forEach(action);
	}

	@Override
	public Spliterator<S> spliterator() {
		return seqs.spliterator();
	}

	public static abstract class Seq {

		private final boolean closed;

		public Seq(boolean closed) {
			this.closed = closed;
		}

		public boolean isClosed() {
			return closed;
		}

		public abstract Coordinate getCoordinate(int index);

		public abstract int size();
	}

	public static class Wrapper extends Seq {

		private final CoordinateSequence underlying;
		private final int index;

		public Wrapper(CoordinateSequence underlying, int index, boolean closed) {
			super(closed);
			this.underlying = underlying;
			this.index = index;
		}

		@Override
		public Coordinate getCoordinate(int index) {
			return underlying.getCoordinate(index);
		}

		@Override
		public int size() {
			return underlying.size();
		}
	}
}
