package com.github.rthoth.slicer;

import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.PStack;
import org.pcollections.PVector;

public class PolygonCropper implements Grid.Cropper<PolygonCallback.Orientation> {

	@Override
	public PVector<CoordinateSequence> crop(PStack<Grid.Event> events, CoordinateSequence sequence,
																					PolygonCallback.Orientation info) {
		return null;
	}
}
