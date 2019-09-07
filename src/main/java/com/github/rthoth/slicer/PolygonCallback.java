package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;

import static com.github.rthoth.slicer.PolygonCallback.Orientation.CLOCKWISE;
import static com.github.rthoth.slicer.PolygonCallback.Orientation.COUNTERCLOCKWISE;

public class PolygonCallback implements Grid.Callback<PolygonCallback.Orientation> {

	enum Orientation {
		CLOCKWISE, COUNTERCLOCKWISE
	}

	private Coordinate previousCoordinate;
	private double area = 0D;

	@Override
	public void check(Coordinate coordinate, int index) {
		area += previousCoordinate.getX() * coordinate.getY() - previousCoordinate.getY() * coordinate.getX();
		previousCoordinate = coordinate;
	}

	@Override
	public void first(Coordinate coordinate) {
		previousCoordinate = coordinate;
	}

	@Override
	public Orientation last(Coordinate coordinate, int index) {
		check(coordinate, index);
		if (area != 0D)
			return area > 0D ? COUNTERCLOCKWISE : CLOCKWISE;
		else
			throw new UnsupportedOperationException();
	}
}
