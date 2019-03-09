package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory;

import java.util.Arrays;
import java.util.stream.Stream;

public class JTSHelper {

	private static final PackedCoordinateSequenceFactory FACTORY = PackedCoordinateSequenceFactory.DOUBLE_FACTORY;

	public static CoordinateSequence createSequence(String coordinates) {
		double[] values = Stream.of(coordinates.split("\\s*,\\s*"))
			.flatMap(x -> Stream.of(x.split("\\s+")))
			.mapToDouble(Double::parseDouble).toArray();

		return FACTORY.create(values, 2);
	}

	public static Coordinate coordinate(double x, double y) {
		return new CoordinateXY(x, y);
	}
}
