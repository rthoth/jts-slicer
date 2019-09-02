package com.github.rthoth.slicer;

import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.PSequence;

public class PolygonCropper implements Grid.Cropper<PolygonCallback.Orientation> {

	@Override
	public PSequence<Event> crop(PSequence<Event> events, CoordinateSequence sequence, PolygonCallback.Orientation info) {
		return events;
	}
}
