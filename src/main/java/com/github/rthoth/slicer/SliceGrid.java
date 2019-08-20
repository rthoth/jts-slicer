package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.pcollections.PSequence;

public abstract class SliceGrid<T extends Geometry> {

	protected final T geometry;
	protected final PSequence<Guide.X> x;
	protected final PSequence<Guide.Y> y;
	protected final Grid grid;
	protected final Order order;

	protected SliceGrid(T geometry, PSequence<Guide.X> x, PSequence<Guide.Y> y, Order order) {
		this.geometry = geometry;
		this.x = x;
		this.y = y;
		this.order = order;
		grid = new Grid(x, y, order);
	}

	protected GeometryFactory getGeometryFactory() {
		return geometry.getFactory();
	}
}
