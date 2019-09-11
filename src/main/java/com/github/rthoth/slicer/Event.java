package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;

public abstract class Event {

	protected final Sequences.Seq seq;
	protected final int index;
	protected final int position;
	protected final Location location;

	public Event(Sequences.Seq seq, int index, int position) {
		this.seq = seq;
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

		private final Sequences.Seq sequence;

		public Factory(Sequences.Seq sequence) {
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

		public In(Sequences.Seq seq, int index, Coordinate coordinate, int position) {
			super(seq, index, position);
			this.coordinate = coordinate;
		}

		@Override
		public Coordinate getCoordinate() {
			return coordinate != null ? coordinate : seq.getCoordinate(index);
		}

		@Override
		public String toString() {
			return "In(" + index + ", " + getCoordinate() + ")";
		}
	}

	public static class Out extends Event {

		private final Coordinate coordinate;

		public Out(Sequences.Seq seq, int index, Coordinate coordinate, int position) {
			super(seq, index, position);
			this.coordinate = coordinate;
		}

		@Override
		public Coordinate getCoordinate() {
			return coordinate != null ? coordinate : seq.getCoordinate(index);
		}

		@Override
		public String toString() {
			return "Out(" + index + ", " + getCoordinate() + ")";
		}
	}
}
