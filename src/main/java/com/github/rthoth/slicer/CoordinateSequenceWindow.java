package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;

public abstract class CoordinateSequenceWindow implements CoordinateSequence {

	protected final CoordinateSequence underlying;
	protected final int start;
	protected final int stop;
	protected final int size;
	protected final int limit;

	public CoordinateSequenceWindow(CoordinateSequence underlying, int start, int stop) {
		this.underlying = underlying;
		this.start = start;
		this.stop = stop;
		this.limit = underlying.size();
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

	@Override
	public int getDimension() {
		return underlying.getDimension();
	}

	@Override
	public int getMeasures() {
		return underlying.getMeasures();
	}

	@Override
	public boolean hasZ() {
		return underlying.hasZ();
	}

	@Override
	public boolean hasM() {
		return underlying.hasM();
	}

	@Override
	public Coordinate createCoordinate() {
		return underlying.createCoordinate();
	}

	@Override
	public Coordinate getCoordinate(int i) {
		return underlying.getCoordinate(map(i));
	}

	@Override
	public Coordinate getCoordinateCopy(int i) {
		return underlying.getCoordinateCopy(map(i));
	}

	@Override
	public void getCoordinate(int index, Coordinate coord) {
		underlying.getCoordinate(map(index), coord);
	}

	@Override
	public int size() {
		return size;
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
	public double getZ(int index) {
		return underlying.getZ(map(index));
	}

	@Override
	public double getM(int index) {
		return underlying.getM(map(index));
	}

	@Override
	public double getOrdinate(int index, int ordinateIndex) {
		return underlying.getOrdinate(map(index), ordinateIndex);
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
	public CoordinateSequence copy() {
		return null;
	}


	public static class Forward extends CoordinateSequenceWindow {

		public Forward(CoordinateSequence underlying, int start, int stop) {
			super(underlying, start, stop);
		}

		@Override
		protected int computeSize() {
			if (start <= stop)
				return stop - start + 1;
			else
				return limit - start + stop;
		}

		@Override
		protected int map(int index) {
			return (start + index) % limit;
		}

		@Override
		public Coordinate[] toCoordinateArray() {
			return new Coordinate[0];
		}

		@Override
		public Envelope expandEnvelope(Envelope env) {
			return null;
		}
	}

	public static class Backward extends CoordinateSequenceWindow {

		public Backward(CoordinateSequence underlying, int start, int stop) {
			super(underlying, start, stop);
		}

		@Override
		protected int computeSize() {
			if (stop <= start)
				return start - stop + 1;
			else
				return limit - stop + start;
		}

		@Override
		protected int map(int index) {
			index = start - index;
			return index >= 0 ? index : limit + index;
		}

		@Override
		public Coordinate[] toCoordinateArray() {
			return new Coordinate[0];
		}

		@Override
		public Envelope expandEnvelope(Envelope env) {
			return null;
		}
	}
}
