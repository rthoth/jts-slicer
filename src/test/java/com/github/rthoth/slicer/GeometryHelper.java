package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeometryHelper {

	public static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

	public static <G extends Geometry> G geometryOf(String wkt) {
		try {
			return (G) new WKTReader(GEOMETRY_FACTORY).read(wkt);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
