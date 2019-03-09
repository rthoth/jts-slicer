package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequences;
import org.locationtech.jts.geom.Envelope;

public abstract class CoordinateSequenceWindow implements CoordinateSequence {


	protected final CoordinateSequence underlying;
	protected final int start;
	protected final int stop;
	protected final boolean closed;
	protected final int size;
	protected final int limit;

	public CoordinateSequenceWindow(CoordinateSequence underlying, int start, int stop, boolean closed) {
		this.underlying = underlying;
		this.start = start;
		this.stop = stop;
		this.closed = closed;
		this.limit = closed ? underlying.size() - 1 : underlying.size();
		this.size = computeSize();
	}

	protected abstract int computeSize();

	protected abstract int map(int index);

	public CoordinateSequence getUnderlying() {
		return underlying;
	}

	public int getStart() {
		return start;
	}

	public int getStop() {
		return stop;
	}

	public boolean isClosed() {
		return closed;
	}

	@Override
	public int getDimension() {
		return underlying.getDimension();
	}

	@Override
	public Coordinate getCoordinate(int index) {
		return underlying.getCoordinate(map(index));
	}

	@Override
	public Coordinate getCoordinateCopy(int index) {
		return underlying.getCoordinateCopy(map(index));
	}

	@Override
	public void getCoordinate(int index, Coordinate coord) {
		underlying.getCoordinate(map(index), coord);
	}

	@Override
	public double getX(int index) {
		return underlying.getX(map(index));
	}

	@Override
	public double getY(int index) {
		return underlying.getY(map(index));
	}

	@Override
	public double getOrdinate(int index, int ordinateIndex) {
		return underlying.getOrdinate(map(index), ordinateIndex);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void setOrdinate(int index, int ordinateIndex, double value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object clone() {
		return copy();
	}

	@Override
	public String toString() {
		return CoordinateSequences.toString(this);
	}

	public static class Forward extends CoordinateSequenceWindow {

		public Forward(CoordinateSequence underlying, int start, int stop, boolean closed) {
			super(underlying, start, stop, closed);
		}

		@Override
		protected int computeSize() {
			return (start <= stop) ? stop - start + 1 : limit - start + stop + 1;
		}

		@Override
		protected int map(int index) {
			if (index >= 0 && index < size)
				return (start + index) % limit;
			else
				throw new IndexOutOfBoundsException(String.format("%d is out of bound %d!", index, size));
		}

		@Override
		public Coordinate[] toCoordinateArray() {
			Coordinate[] ret = new Coordinate[size];
			for (int i = 0, index = start; i < size; i++, index = (index + 1) % limit) {
				ret[i] = underlying.getCoordinate(index);
			}

			return ret;
		}

		@Override
		public Envelope expandEnvelope(Envelope env) {
			int index = start;
			Envelope ret = new Envelope(env);
			for (int i = 0; i < size; i++, index = (index + 1) % limit) {
				ret.expandToInclude(underlying.getX(index), underlying.getY(index));
			}

			return ret;
		}

		@Override
		public CoordinateSequence copy() {
			return this;
		}
	}

	public static class Backward extends CoordinateSequenceWindow {

		public Backward(CoordinateSequence underlying, int start, int stop, boolean closed) {
			super(underlying, start, stop, closed);
		}

		@Override
		protected int computeSize() {
			return (stop <= start) ? start - stop + 1 : limit - stop + start + 1;
		}

		@Override
		protected int map(int index) {
			if (index >= 0 && index < size) {
				index = (start - index) % limit;
				return (index >= 0) ? index : (limit + index);
			} else
				throw new IndexOutOfBoundsException(String.format("%d is out of bound %d", index, size));
		}

		@Override
		public Coordinate[] toCoordinateArray() {
			Coordinate[] ret = new Coordinate[size];

			for (int i = 0, index = start; i < size; i++) {
				ret[i] = underlying.getCoordinate(index--);
				if (index < 0) {
					index = limit + index;
				}
			}

			return new Coordinate[0];
		}

		@Override
		public Envelope expandEnvelope(Envelope env) {
			Envelope ret = new Envelope(env);

			for (int i = 0, index = start; i < size; i++) {
				ret.expandToInclude(underlying.getX(index), underlying.getY(index--));
				if (index < 0) {
					index = limit + index;
				}
			}

			return ret;
		}

		@Override
		public CoordinateSequence copy() {
			return this;
		}
	}
}
