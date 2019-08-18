package com.github.rthoth.slicer;

import org.locationtech.jts.geom.LineString;
import org.pcollections.PSequence;

public class LineStringSliceGrid extends SliceGrid<LineString> {

	public LineStringSliceGrid(LineString geometry, PSequence<Guide.X> x, PSequence<Guide.Y> y, Order order) {
		super(geometry, x, y, order);
	}
}
