package com.github.rthoth.slicer;

import org.pcollections.PSequence;

public class PolygonCropper implements Grid.Cropper<PolygonCallback.Orientation> {

	@Override
	public PSequence<Event> crop(PSequence<Event> events, PolygonCallback.Orientation info, Guide<?> guide) {
		return null;
	}

	@Override
	public PSequence<Event> crop(PSequence<Event> events, PolygonCallback.Orientation info, Guide<?> lower, Guide<?> upper) {
		return null;
	}
}
