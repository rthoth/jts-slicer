package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;

public abstract class Guide<G extends Guide> implements Comparable<Coordinate> {

	protected final double position;
	protected final double offset;
	protected final G less;
	protected final G greater;

	public Guide(double position, double offset, double extrusion) {
		this.position = position + extrusion;
		this.offset = offset;

		if (extrusion == 0D) {
			less = (G) this;
			greater = (G) this;
		} else {
			less = copy(position - extrusion);
			greater = copy(position + extrusion);
		}
	}

	public abstract G extrude(double extrusion);

	protected abstract G copy(double position);

	public static class X extends Guide<X> {

		public X(double x, double offset, double extrusion) {
			super(x, offset, extrusion);
		}

		@Override
		protected X copy(double position) {
			return new X(position, offset, 0D);
		}

		@Override
		public X extrude(double extrusion) {
			return new X(position, offset, extrusion);
		}

		@Override
		public int compareTo(Coordinate coordinate) {
			return Math.abs(coordinate.getX() - position) > offset ? Double.compare(position, coordinate.getX()) : 0;
		}
	}

	public static class Y extends Guide<Y> {

		public Y(double y, double offset, double extrusion) {
			super(y, offset, extrusion);
		}

		@Override
		protected Y copy(double position) {
			return new Y(position, offset, 0D);
		}

		@Override
		public Y extrude(double extrusion) {
			return new Y(position, offset, extrusion);
		}

		@Override
		public int compareTo(Coordinate coordinate) {
			return Math.abs(coordinate.getY() - position) > offset ? Double.compare(position, coordinate.getY()) : 0;
		}
	}
}
