package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

public abstract class Event {

	private final CoordinateSequence sequence;
	private final int index;
	private final int position;
	private final Location location;

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
	}

	public static class Out extends Event {

		private final Coordinate coordinate;

		public Out(CoordinateSequence sequence, int index, Coordinate coordinate, int position) {
			super(sequence, index, position);
			this.coordinate = coordinate;
		}
	}
}
