package com.github.rthoth.slicer;

import org.junit.jupiter.api.Test;

public class PolygonSliceGridTest extends AbstractTest {

	public static final String POLYGON_01 = "POLYGON ((2 -8, 2 -2, 8 7, 4 8, -3 3, -5 1, 2 -8), (4 4, 1 -1, -2 2, 4 4))";

	@Test
	public void matrix_6x5() {
		Slicer slicer = new Slicer(new double[]{-4, -1, 2, 4, 6}, new double[]{-6, -2, 2, 6});
		slicer.apply(GeometryHelper.geometryOf(POLYGON_01));
	}
}
