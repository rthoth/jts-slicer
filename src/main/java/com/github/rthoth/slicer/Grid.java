package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.pcollections.Empty;
import org.pcollections.PSequence;
import org.pcollections.PVector;

public class Grid {

	private final PVector<Cell<Guide.X>> xAxis;
	private final PVector<Cell<Guide.Y>> yAxix;

	public interface TraverseCallback {
		void apply(Coordinate coordinate, int index);
	}

	public Grid(PSequence<Guide.X> x, PSequence<Guide.Y> y) {
		PVector<Cell<Guide.X>> xAxis = Empty.vector();
		PVector<Cell<Guide.Y>> yAxis = Empty.vector();

		if (x.size() > 0) {
			xAxis = xAxis.plus(new Cell.Lower<>(x.get(0)));

			for (int i = 1, l = x.size(); i < l; i++) {
				xAxis = xAxis.plus(new Cell.Middle<>(x.get(i - 1), x.get(i)));
			}

			xAxis = xAxis.plus(new Cell.Upper<>(x.get(x.size() - 1)));
		}

		if (y.size() > 0) {
			yAxis = yAxis.plus(new Cell.Lower<>(y.get(0)));

			for (int i = 1, l = y.size(); i < l; i++) {
				yAxis = yAxis.plus(new Cell.Middle<>(y.get(i - 1), y.get(i)));
			}

			yAxis = yAxis.plus(new Cell.Upper<>(y.get(y.size() - 1)));
		}

		this.xAxis = xAxis;
		this.yAxix = yAxis;
	}
}
