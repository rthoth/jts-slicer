package com.github.rthoth.slicer;

import com.github.rthoth.slicer.Sequences.Seq;
import org.locationtech.jts.geom.Polygon;
import org.pcollections.Empty;
import org.pcollections.PSequence;
import org.pcollections.PVector;

public class PolygonSliceGrid extends SliceGrid<Polygon> {

	public static Sequences<Seq> sequencesOf(Polygon polygon) {
		PVector<Seq> seqs = Empty.vector();
		seqs = seqs.plus(new Sequences.Wrapper(polygon.getExteriorRing().getCoordinateSequence(), 0, true));

		for (int i = 0, j = 1; i < polygon.getNumInteriorRing(); i++, j++) {
			seqs = seqs.plus(new Sequences.Wrapper(polygon.getInteriorRingN(i).getCoordinateSequence(), j, true));
		}

		return new Sequences<>(seqs);
	}

	public PolygonSliceGrid(Polygon polygon, PSequence<Guide.X> x, PSequence<Guide.Y> y, Order order) {
		super(polygon, x, y, order);
		PolygonCallback callback = new PolygonCallback();
		PolygonCropper cropper = new PolygonCropper();

		grid.traverse(sequencesOf(polygon), callback, cropper);
	}
}
