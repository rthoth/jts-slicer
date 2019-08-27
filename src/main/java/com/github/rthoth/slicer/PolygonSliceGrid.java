package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Polygon;
import org.pcollections.PSequence;

public class PolygonSliceGrid extends SliceGrid<Polygon> {

	public PolygonSliceGrid(Polygon polygon, PSequence<Guide.X> x, PSequence<Guide.Y> y, Order order) {
		super(polygon, x, y, order);
		PolygonCallback callback = new PolygonCallback();
		PolygonCropper cropper = new PolygonCropper();

		grid.traverse(polygon.getExteriorRing().getCoordinateSequence(), callback, cropper);
	}
}
