package com.github.rthoth.slicer;

import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Polygon;
import org.pcollections.Empty;
import org.pcollections.PSequence;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Sequences implements Iterable<Sequences.Seq> {

	private final PSequence<Seq> seqs;

	public Sequences(PSequence<Seq> seqs) {
		this.seqs = seqs;
	}

	@Override
	public Iterator<Seq> iterator() {
		return seqs.iterator();
	}

	@Override
	public void forEach(Consumer<? super Seq> action) {
		seqs.forEach(action);
	}

	@Override
	public Spliterator<Seq> spliterator() {
		return seqs.spliterator();
	}

	public static class Seq {

		private final CoordinateSequence sequence;
		private final int index;
		private final boolean closed;

		public Seq(CoordinateSequence sequence, int index, boolean closed) {
			this.sequence = sequence;
			this.index = index;
			this.closed = closed;
		}

		public CoordinateSequence getSequence() {
			return sequence;
		}

		public int getIndex() {
			return index;
		}

		public boolean isClosed() {
			return closed;
		}
	}
}
