package com.github.rthoth.slicer;

import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Polygon;
import org.pcollections.PSequence;

public class PolygonSliceGrid extends SliceGrid<Polygon> {

	public PolygonSliceGrid(Polygon polygon, PSequence<Guide.X> x, PSequence<Guide.Y> y) {
		super(polygon, x, y, PolygonCellFactory.SLICE_FACTORY);
		PolygonCallback callback = new PolygonCallback(polygon.getExteriorRing().getCoordinateSequence());
		grid.traverse(polygon.getExteriorRing().getCoordinateSequence(), callback);
		callback.getOrientation();

		for (int i = 0, l = polygon.getNumInteriorRing(); i < l; i++) {
			CoordinateSequence sequence = polygon.getInteriorRingN(i).getCoordinateSequence();
			callback = new PolygonCallback(sequence);
			grid.traverse(sequence, callback);
			callback.getOrientation();
		}
		
	}
}
