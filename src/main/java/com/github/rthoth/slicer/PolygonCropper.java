package com.github.rthoth.slicer;

public class PolygonCropper implements Grid.Cropper<PolygonCallback.Orientation> {

	@Override
	public Sequences crop(SliceSet.Slice slice, SliceSet<PolygonCallback.Orientation> sliceSet, Guide<?> guide) {
		return null;
	}

	@Override
	public Sequences crop(SliceSet.Slice slice, SliceSet<PolygonCallback.Orientation> sliceSet, Guide<?> lower, Guide<?> upper) {
		return null;
	}
}
