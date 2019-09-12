package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;

@SuppressWarnings("unused")
public abstract class Cell<G extends Guide<?>> {

	public abstract int positionOf(Coordinate coordinate);

	public abstract Coordinate intersection(Coordinate _1, Coordinate _2, int guide);

	public static class Lower<G extends Guide<?>> extends Cell<G> {

		private final G upper;

		public Lower(G upper) {
			this.upper = upper;
		}

		@Override
		public int positionOf(Coordinate coordinate) {
			switch (upper.positionOf(coordinate)) {
				case 1:
					return 2;
				case -1:
					return 0;
				default:
					return 1;
			}
		}

		@Override
		public Coordinate intersection(Coordinate _1, Coordinate _2, int guide) {
			if (guide > 0)
				return upper.intersection(_1, _2);
			else
				throw new IllegalArgumentException();
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
		public int positionOf(Coordinate coordinate) {
			switch (lower.positionOf(coordinate)) {
				case -1:
					return -2;
				case 0:
					return -1;
			}

			switch (upper.positionOf(coordinate)) {
				case 1:
					return 2;
				case 0:
					return 1;
			}

			return 0;
		}

		@Override
		public Coordinate intersection(Coordinate _1, Coordinate _2, int guide) {
			if (guide < 0)
				return lower.intersection(_1, _2);
			else if (guide > 0)
				return upper.intersection(_1, _2);

			throw new IllegalArgumentException(String.valueOf(guide));
		}
	}

	public static class Upper<G extends Guide<?>> extends Cell<G> {

		private final G lower;

		public Upper(G lower) {
			this.lower = lower;
		}

		@Override
		public int positionOf(Coordinate coordinate) {
			switch (lower.positionOf(coordinate)) {
				case -1:
					return -2;

				case 1:
					return 0;

				default:
					return -1;
			}
		}

		@Override
		public Coordinate intersection(Coordinate _1, Coordinate _2, int guide) {
			if (guide < 0)
				return lower.intersection(_1, _2);
			else
				throw new IllegalArgumentException(String.valueOf(guide));
		}
	}
}
