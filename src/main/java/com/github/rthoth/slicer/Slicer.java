package com.github.rthoth.slicer;

import com.github.rthoth.slicer.Guide.X;
import com.github.rthoth.slicer.Guide.Y;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.pcollections.PSequence;

import java.util.stream.Collectors;

import static com.github.rthoth.slicer.Util.toSequence;
import static com.github.rthoth.slicer.Util.toVector;

public class Slicer {

	public final static double DEFAULT_OFFSET = 1e-8;

	private final PSequence<X> x;
	private final PSequence<Y> y;

	public Slicer(double[] x, double[] y) {
		this(x, y, DEFAULT_OFFSET, 0D);
	}

	public Slicer(double[] x, double[] y, double offset, double extrusion) {
		this(toSequence(x, offset, extrusion, X::new), toSequence(y, offset, extrusion, Y::new));
	}

	private Slicer(PSequence<X> x, PSequence<Y> y) {
		this.x = x;
		this.y = y;
	}

	@SuppressWarnings("unused")
	public Slicer extrude(double extrusion) {
		return new Slicer(
			x.stream().map(x -> x.extrude(extrusion)).collect(toVector()),
			y.stream().map(y -> y.extrude(extrusion)).collect(toVector())
		);
	}

	public <T extends Geometry> SliceGrid<T> apply(T geometry) {
		return apply(geometry, Order.AUTOMATIC);
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
