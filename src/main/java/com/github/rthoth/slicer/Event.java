package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

public abstract class Event {

	private final CoordinateSequence sequence;
	private final int index;
	private final Coordinate coordinate;
	private final int position;

	public Event(CoordinateSequence sequence, int index, Coordinate coordinate, int position) {
		this.sequence = sequence;
		this.index = index;
		this.coordinate = coordinate;
		this.position = position;
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

		public CrossStop newCrossStop(int index, Coordinate coordinate, int position) {
			return new CrossStop(sequence, index, coordinate, position);
		}
	}

	public static class In extends Event {

		public In(CoordinateSequence sequence, int index, Coordinate coordinate, int position) {
			super(sequence, index, coordinate, position);
		}
	}

	public static class Out extends Event {

		public Out(CoordinateSequence sequence, int index, Coordinate coordinate, int position) {
			super(sequence, index, coordinate, position);
		}
	}

	public static class CrossStop extends Event {

		public CrossStop(CoordinateSequence sequence, int index, Coordinate coordinate, int position) {
			super(sequence, index, coordinate, position);
		}
	}
}
