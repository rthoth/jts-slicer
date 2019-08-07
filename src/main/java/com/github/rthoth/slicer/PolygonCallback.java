package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.TopologyException;

public class PolygonCallback implements Grid.TraverseCallback {

	enum Orientation {
		CLOCKWISE, COUNTERCLOCKWISE
	}

	private final CoordinateSequence sequence;
	private Coordinate last;
	private double area = 0D;

	public PolygonCallback(CoordinateSequence sequence) {
		this.sequence = sequence;
	}

	@Override
	public void apply(Coordinate coordinate, int index) {
		if (index != 0) {
			area += last.getX() * coordinate.getY() - last.getY() * coordinate.getX();
			last = coordinate;
		} else {
			last = coordinate;
		}
	}

	public Orientation getOrientation() {
		if (area > 0D)
			return Orientation.COUNTERCLOCKWISE;
		else if (area < 0D)
			return Orientation.CLOCKWISE;
		else
			throw new TopologyException("Area is zero!");
	}
}
