package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Point;
import org.pcollections.PSequence;

public class PointSliceGrid extends SliceGrid<Point> {

	public PointSliceGrid(Point point, PSequence<Guide.X> x, PSequence<Guide.Y> y) {
		super(point, x, y);
	}
}
