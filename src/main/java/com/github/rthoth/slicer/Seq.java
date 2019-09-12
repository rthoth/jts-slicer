package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

public abstract class Seq {

	private final boolean closed;
	private final int index;

	public Seq(int index, boolean closed) {
		this.closed = closed;
		this.index = index;
	}

	public boolean isClosed() {
		return closed;
	}

	public abstract Coordinate getCoordinate(int index);

	public abstract int size();

	public static class Wrapper extends Seq {

		private final CoordinateSequence underlying;

		public Wrapper(CoordinateSequence underlying, int index, boolean closed) {
			super(index, closed);
			this.underlying = underlying;
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
