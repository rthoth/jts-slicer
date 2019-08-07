package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.pcollections.PSequence;

public abstract class SliceGrid<T extends Geometry> {

	protected final T geometry;
	protected final PSequence<Guide.X> x;
	protected final PSequence<Guide.Y> y;
	protected final Grid<SliceCell> grid;

	protected SliceGrid(T geometry, PSequence<Guide.X> x, PSequence<Guide.Y> y, Grid.CellFactory<SliceCell> cellFactory) {
		this.geometry = geometry;
		this.x = x;
		this.y = y;
		grid = new Grid<>(x, y, cellFactory);
	}

	protected GeometryFactory getGeometryFactory() {
		return geometry.getFactory();
	}
}
