package com.github.rthoth.slicer;

import org.junit.jupiter.api.Test;
import org.pcollections.PVector;

import java.util.stream.DoubleStream;

import static com.github.rthoth.slicer.Slicer.DEFAULT_OFFSET;
import static com.github.rthoth.slicer.Util.toVector;

public class PolygonSliceGridTest extends AbstractTest {

	public static final String POLYGON_01 = "POLYGON ((6 14, 4 14, 2 12, 5 12, 5 10, 8 10, 6 8, 6 6, 8 4, 6 4, 4 4, 5 6, 3 6, 1 2, 1 16, 4 16, 6 14))";

	public static final String POLYGON_02 = "POLYGON ((8 5, 6 3, -2 2, 6 4, 4 5, -9 4, -6 3, -6 2, -4 2, -4 -2, -6 -2, -6 -3, -9 -4, 4 -5, 6 -4, -2 -2, 6 -3, 8 -5, 10 0, 8 5), (3 1, -3 0, 3 -1, 7 -3, 6 0, 7 3, 3 1), (3 4, -4 4, -5 3, 3 4), (-5 -3, 3 -4, -4 -4, -5 -3), (8 3, 9 0, 8 -3, 7 0, 8 3))";

	@Test
	public void matrix_3x3() {

		PVector<Guide.X> x = DoubleStream.of(4, 6).mapToObj(d -> new Guide.X(d, 1e-8, 0))
			.collect(toVector());
		PVector<Guide.Y> y = DoubleStream.of(4, 14).mapToObj(d -> new Guide.Y(d, 1e-8, 0))
			.collect(toVector());

		PolygonSliceGrid grid = new PolygonSliceGrid(GeometryHelper.geometryOf(POLYGON_01), x, y, Order.X_Y);
	}

	@Test
	public void matrix_3x5() {
		PVector<Guide.X> x = DoubleStream
			.of(-6, -2, 2, 6)
			.mapToObj(d -> new Guide.X(d, DEFAULT_OFFSET, 0))
			.collect(toVector());

		PVector<Guide.Y> y = DoubleStream
			.of(-2, 2)
			.mapToObj(d -> new Guide.Y(d, DEFAULT_OFFSET, 0))
			.collect(toVector());

		PolygonSliceGrid grid = new PolygonSliceGrid(GeometryHelper.geometryOf(POLYGON_02), x, y, Order.X_Y);
	}
}
