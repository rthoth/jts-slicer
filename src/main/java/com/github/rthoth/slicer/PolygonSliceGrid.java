package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Polygon;
import org.pcollections.PSequence;

public class PolygonSliceGrid extends SliceGrid<Polygon> {

	public PolygonSliceGrid(Polygon polygon, PSequence<Guide.X> x, PSequence<Guide.Y> y) {
		super(polygon, x, y);
		PolygonTraverseCallback callback = new PolygonTraverseCallback(polygon.getExteriorRing().getCoordinateSequence());
	}
}
