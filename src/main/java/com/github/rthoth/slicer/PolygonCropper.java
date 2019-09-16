package com.github.rthoth.slicer;

import org.pcollections.PSet;

public class PolygonCropper implements Grid.Cropper<PolygonCallback.Orientation> {
	@Override
	public PSet<SequenceSet> crop(SliceSet.Slice slice, SliceSet<PolygonCallback.Orientation> sliceSet, Guide<?> guide) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PSet<SequenceSet> crop(SliceSet.Slice slice, SliceSet<PolygonCallback.Orientation> sliceSet, Guide<?> lower, Guide<?> upper) {
		throw new UnsupportedOperationException();
	}
}
