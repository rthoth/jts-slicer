package com.github.rthoth.slicer;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class GeometryHelper {

	public static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

	public static <G extends Geometry> G geometryOf(String wkt) {
		try {
			return (G) new WKTReader(GEOMETRY_FACTORY).read(wkt);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static CoordinateSequence sequenceOf(String sequence) {
		assert sequence.startsWith("(") && sequence.endsWith(")");
		String inner = sequence.substring(1, sequence.length() - 1);
		LinkedList<Coordinate> buffer = new LinkedList<>();

		for (String coordinate : inner.split("\\s*,\\s*")) {
			String[] components = coordinate.split("\\s+");
			assert components.length == 2;
			buffer.addLast(new CoordinateXY(parseDouble(components[0]), parseDouble(components[1])));
		}

		return new CoordinateArraySequence(buffer.toArray(new Coordinate[0]));
	}
}
