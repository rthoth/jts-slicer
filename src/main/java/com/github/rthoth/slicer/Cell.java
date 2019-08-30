package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;

public abstract class Cell<G extends Guide<?>> {

	public abstract int position(Coordinate coordinate);

	public abstract Coordinate intersection(Coordinate _1, Coordinate _2);

	public abstract Coordinate intersection(Coordinate _1, Coordinate _2, int guide);

	public static class Lower<G extends Guide<?>> extends Cell<G> {

		private final G upper;

		public Lower(G upper) {
			this.upper = upper;
		}

		@Override
		public int position(Coordinate coordinate) {
			switch (upper.compareTo(coordinate)) {
				case 1:
					return 0;
				case 0:
					return 1;
				default:
					return 2;
			}
		}
	}

	public static class Middle<G extends Guide<?>> extends Cell<G> {

		private final G lower;
		private final G upper;

		public Middle(G lower, G upper) {
			this.lower = lower;
			this.upper = upper;
		}

		@Override
		public int position(Coordinate coordinate) {
			switch (lower.compareTo(coordinate)) {
				case 1:
					return -2;
				case 0:
					return -1;
			}

			switch (upper.compareTo(coordinate)) {
				case -1:
					return 2;
				case 0:
					return 1;
			}

			return 0;
		}
	}

	public static class Upper<G extends Guide<?>> extends Cell<G> {

		private final G lower;

		public Upper(G lower) {
			this.lower = lower;
		}

		@Override
		public int position(Coordinate coordinate) {
			return lower.compareTo(coordinate);
		}
	}
}
