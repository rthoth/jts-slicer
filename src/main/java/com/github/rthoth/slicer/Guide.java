package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;

public abstract class Guide<G extends Guide> implements Comparable<Coordinate> {

	protected final double position;
	protected final double offset;

	public Guide(double position, double offset) {
		this.position = position;
		this.offset = offset;
	}

	public static class X extends Guide<X> {
		public X(double x, double offset) {
			super(x, offset);
		}

		@Override
		public int compareTo(Coordinate coordinate) {
			return Math.abs(coordinate.getX() - position) > offset ? Double.compare(position, coordinate.getX()) : 0;
		}
	}

	public static class Y extends Guide<Y> {

		public Y(double y, double offset) {
			super(y, offset);
		}

		@Override
		public int compareTo(Coordinate coordinate) {
			return Math.abs(coordinate.getY() - position) > offset ? Double.compare(position, coordinate.getY()) : 0;
		}
	}
}
