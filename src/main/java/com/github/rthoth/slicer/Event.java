package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

public abstract class Event {

	protected final CoordinateSequence sequence;
	protected final int index;
	protected final int position;
	protected final Location location;

	public Event(CoordinateSequence sequence, int index, int position) {
		this.sequence = sequence;
		this.index = index;
		this.position = position;
		this.location = Location.of(position);
	}

	public Location getLocation() {
		return location;
	}

	public int getIndex() {
		return index;
	}

	public abstract Coordinate getCoordinate();

	public abstract String toString();

	public static class Factory {

		private final CoordinateSequence sequence;

		public Factory(CoordinateSequence sequence) {
			this.sequence = sequence;
		}

		public In newIn(int index, Coordinate coordinate, int position) {
			return new In(sequence, index, coordinate, position);
		}

		public Out newOut(int index, Coordinate coordinate, int position) {
			return new Out(sequence, index, coordinate, position);
		}

		public Coordinate getCoordinate(int index) {
			return sequence.getCoordinate(index);
		}
	}

	public static class In extends Event {

		private final Coordinate coordinate;

		public In(CoordinateSequence sequence, int index, Coordinate coordinate, int position) {
			super(sequence, index, position);
			this.coordinate = coordinate;
		}

		@Override
		public Coordinate getCoordinate() {
			return coordinate != null ? coordinate : sequence.getCoordinate(index);
		}

		@Override
		public String toString() {
			return "In(" + index + ", " + getCoordinate() + ")";
		}
	}

	public static class Out extends Event {

		private final Coordinate coordinate;

		public Out(CoordinateSequence sequence, int index, Coordinate coordinate, int position) {
			super(sequence, index, position);
			this.coordinate = coordinate;
		}

		@Override
		public Coordinate getCoordinate() {
			return coordinate != null ? coordinate : sequence.getCoordinate(index);
		}

		@Override
		public String toString() {
			return "Out(" + index + ", " + getCoordinate() + ")";
		}
	}
}
