package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.pcollections.PSequence;

import java.util.Arrays;

public class Slicer {

	public final static double DEFAULT_OFFSET = 1e-8;

	private final PSequence<Guide.X> x;
	private final PSequence<Guide.Y> y;

	public Slicer(double[] x, double[] y) {
		this(x, y, DEFAULT_OFFSET);
	}

	public Slicer(double[] x, double[] y, double offset) {
		this.x = Util.toVector(Arrays.stream(x).mapToObj(v -> new Guide.X(v, offset)));
		this.y = Util.toVector(Arrays.stream(y).mapToObj(v -> new Guide.Y(v, offset)));
	}

	public <T extends Geometry> SliceGrid<T> apply(T geometry) {
		return apply(geometry, Order.X_Y);
	}

	public <T extends Geometry> SliceGrid<T> apply(T geometry, Order order) {
		if (geometry instanceof Point) {
			return (SliceGrid<T>) new PointSliceGrid((Point) geometry, x, y, order);

		} else if (geometry instanceof Polygon) {
			return (SliceGrid<T>) new PolygonSliceGrid((Polygon) geometry, x, y, order);

		} else if (geometry instanceof LineString) {
			return (SliceGrid<T>) new LineStringSliceGrid((LineString) geometry, x, y, order);

		} else {
			throw new IllegalArgumentException();
		}
	}

}
