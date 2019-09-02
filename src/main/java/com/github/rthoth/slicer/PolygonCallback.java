package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.TopologyException;

import static com.github.rthoth.slicer.PolygonCallback.Orientation.CLOCKWISE;
import static com.github.rthoth.slicer.PolygonCallback.Orientation.COUNTERCLOCKWISE;

public class PolygonCallback implements Grid.Callback<PolygonCallback.Orientation> {

	private Coordinate lastCoordinate;

	enum Orientation {
		CLOCKWISE, COUNTERCLOCKWISE
	}

	private Coordinate last;
	private double area = 0D;

	@Override
	public void check(Coordinate coordinate, int i) {
		area += lastCoordinate.getX() * coordinate.getY() - lastCoordinate.getY() * coordinate.getX();
		lastCoordinate = coordinate;
	}

	@Override
	public void first(Coordinate coordinate) {
		lastCoordinate = coordinate;
	}

	@Override
	public Orientation last(Coordinate coordinate, int i) {
		check(coordinate, i);
		return area > 0D ? COUNTERCLOCKWISE : CLOCKWISE;
	}
//	@Override
//	public void first(Coordinate coordinate, int index) {
//		if (index != 0) {
//			area += last.getX() * coordinate.getY() - last.getY() * coordinate.getX();
//			last = coordinate;
//		} else {
//			last = coordinate;
//		}
//	}
//
//	public Orientation getOrientation() {
//		if (area > 0D)
//			return Orientation.COUNTERCLOCKWISE;
//		else if (area < 0D)
//			return Orientation.CLOCKWISE;
//		else
//			throw new TopologyException("Area is zero!");
//	}
}
