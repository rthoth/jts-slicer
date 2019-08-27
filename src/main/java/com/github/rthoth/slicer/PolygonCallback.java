package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.TopologyException;

public class PolygonCallback implements Grid.Callback<PolygonCallback.Orientation> {

	enum Orientation {
		CLOCKWISE, COUNTERCLOCKWISE
	}

	private Coordinate last;
	private double area = 0D;

	@Override
	public void check(Coordinate coordinate, int i) {

	}

	@Override
	public void first(Coordinate coordinate) {

	}

	@Override
	public Orientation last(Coordinate coordinate, int i) {
		return null;
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
