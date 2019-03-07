package com.github.rthoth.slicer;

import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory;

public class JTSHelper {

	private static final PackedCoordinateSequenceFactory FACTORY = PackedCoordinateSequenceFactory.DOUBLE_FACTORY;

	public static CoordinateSequence createSequence(double... ordinates) {
		return FACTORY.create(ordinates, 2);
	}
}
