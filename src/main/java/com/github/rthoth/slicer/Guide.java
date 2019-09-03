package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXY;

public abstract class Guide<G extends Guide> {

	protected final double position;
	protected final double offset;
	private final G less;
	private final G greater;

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

	public G getLess() {
		return less;
	}

	public G getGreater() {
		return greater;
	}

	public abstract G extrude(double extrusion);

	protected abstract G copy(double position);

	public abstract Coordinate intersection(Coordinate a, Coordinate b);

	public abstract int positionOf(Coordinate coordinate);

	public abstract String toString();

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
		public int positionOf(Coordinate coordinate) {
			return Math.abs(coordinate.getX() - position) > offset ? Double.compare(coordinate.getX(), position) : 0;
		}

		@Override
		public Coordinate intersection(Coordinate a, Coordinate b) {
			final double dx = b.getX() - a.getX();
			if (dx != 0) {
				return new CoordinateXY(position, a.getY() + (position - a.getX()) * (b.getY() - a.getY()) / dx);
			} else
				throw new IllegalArgumentException();
		}

		@Override
		public String toString() {
			return "X(" + position + ")";
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
		public int positionOf(Coordinate coordinate) {
			return Math.abs(coordinate.getY() - position) > offset ? Double.compare(coordinate.getY(), position) : 0;
		}

		@Override
		public Coordinate intersection(Coordinate a, Coordinate b) {
			final double dy = b.getY() - a.getY();
			if (dy != 0)
				return new CoordinateXY(a.getX() + ((position - a.getY()) * (b.getX() - a.getX())) / dy, position);
			else
				throw new IllegalArgumentException();
		}

		@Override
		public String toString() {
			return "Y(" + position + ")";
		}
	}
}
