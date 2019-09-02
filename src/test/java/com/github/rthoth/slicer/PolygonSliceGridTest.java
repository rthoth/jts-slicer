package com.github.rthoth.slicer;

import org.junit.jupiter.api.Test;
import org.pcollections.PVector;

import java.util.stream.DoubleStream;

import static com.github.rthoth.slicer.Util.toVector;

public class PolygonSliceGridTest extends AbstractTest {

	public static final String POLYGON_01 = "POLYGON ((6 14, 4 14, 2 12, 5 12, 5 10, 8 10, 6 8, 6 6, 8 4, 6 4, 4 4, 5 6, 3 6, 1 2, 1 16, 4 16, 6 14))";

	@Test
	public void matrix_3x3() {

		PVector<Guide.X> x = DoubleStream.of(4, 6).mapToObj(d -> new Guide.X(d, 1e-8, 0))
			.collect(toVector());
		PVector<Guide.Y> y = DoubleStream.of(4, 14).mapToObj(d -> new Guide.Y(d, 1e-8, 0))
			.collect(toVector());

		PolygonSliceGrid grid = new PolygonSliceGrid(GeometryHelper.geometryOf(POLYGON_01), x, y, Order.X_Y);
	}
}
