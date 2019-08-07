package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;

public class Position {

	public static final Position ZERO = new Position(0, 0);
	private final int x;
	private final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vector minus(Position other) {
		return new Vector(x - other.x, y - other.y);
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}

	public static Position of(Coordinate coordinate, Guide.X xmin, Guide.X xmax, Guide.Y ymin, Guide.Y ymax) {
		int x = 0, y = 0;

		if (xmin != null && xmax != null) {
			x = between(coordinate, xmin, xmax);
		} else if (xmin != null) {
			x = greater(coordinate, xmin);
		} else if (xmax != null) {
			x = less(coordinate, xmax);
		}

		if (ymin != null && ymax != null) {
			y = between(coordinate, ymin, ymax);
		} else if (ymin != null) {
			y = greater(coordinate, ymin);
		} else if (ymax != null) {
			y = less(coordinate, ymax);
		}

		return new Position(x, y);
	}

	public static <G extends Guide<?>> int between(Coordinate coordinate, Guide<G> min, Guide<G> max) {
		switch (min.compareTo(coordinate)) {
			case 1:
				return -2;
			case 0:
				return -1;
		}

		switch (max.compareTo(coordinate)) {
			case -1:
				return 2;
			case 0:
				return 1;
		}

		return 0;
	}

	public static int greater(Coordinate coordinate, Guide<?> max) {
		switch (max.compareTo(coordinate)) {
			case -1:
				return 0;
			case 1:
				return -2;
			default:
				return -1;
		}
	}

	public static int less(Coordinate coordinate, Guide<?> min) {
		switch (min.compareTo(coordinate)) {
			case 1:
				return 0;
			case -1:
				return 2;
			default:
				return 1;
		}
	}
}
