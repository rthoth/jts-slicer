package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.Empty;
import org.pcollections.PSequence;
import org.pcollections.PStack;
import org.pcollections.PVector;

import static com.github.rthoth.slicer.Util.toVector;

public class Grid {

	private final PVector<Cell<Guide.X>> xAxis;
	private final PVector<Cell<Guide.Y>> yAxix;
	private final Order order;

	public interface Callback<I> {
		void check(Coordinate coordinate, int i);

		void first(Coordinate coordinate);

		I last(Coordinate coordinate, int i);
	}

	public interface Cropper<I> {
		PVector<CoordinateSequence> crop(PStack<Event> events);
	}

	public Grid(PSequence<Guide.X> x, PSequence<Guide.Y> y, Order order) {
		this.order = order;
		PVector<Cell<Guide.X>> xAxis = Empty.vector();
		PVector<Cell<Guide.Y>> yAxis = Empty.vector();

		if (x.size() > 0) {
			xAxis = xAxis.plus(new Cell.Lower<>(x.get(0).getLess()));

			for (int i = 1, l = x.size(); i < l; i++) {
				xAxis = xAxis.plus(new Cell.Middle<>(x.get(i - 1).getLess(), x.get(i).getGreater()));
			}

			xAxis = xAxis.plus(new Cell.Upper<>(x.get(x.size() - 1).getGreater()));
		}

		if (y.size() > 0) {
			yAxis = yAxis.plus(new Cell.Lower<>(y.get(0).getLess()));

			for (int i = 1, l = y.size(); i < l; i++) {
				yAxis = yAxis.plus(new Cell.Middle<>(y.get(i - 1).getLess(), y.get(i).getGreater()));
			}

			yAxis = yAxis.plus(new Cell.Upper<>(y.get(y.size() - 1).getGreater()));
		}

		this.xAxis = xAxis;
		this.yAxix = yAxis;
	}

	public <I> void traverse(CoordinateSequence sequence, Callback<I> callback) {
		switch (order) {
			case X_Y:
				traverse(xAxis, yAxix, sequence, callback);
			case Y_X:
				traverse(yAxix, xAxis, sequence, callback);
		}
	}

	private <I> void traverse(PVector<? extends Cell<?>> _1, PVector<? extends Cell<?>> _2,
														CoordinateSequence sequence, Callback<I> callback) {
		if (sequence.size() > 0) {
			step1(_1, sequence, callback);

		} else {

		}
	}

	private <I> void step1(PVector<? extends Cell<?>> cells,
												 CoordinateSequence sequence, Callback<I> callback) {

		PVector<GridCell> gridCells = toVector(cells.stream().map(GridCell::new));

		Coordinate first = sequence.getCoordinate(0);
		callback.first(first);
		for (GridCell cell : gridCells)
			cell.first(first);

		int lastIndex = sequence.size() - 1;

		for (int i = 1; i < lastIndex; i++) {
			Coordinate coordinate = sequence.getCoordinate(i);
			callback.check(coordinate, i);
			for (GridCell cell : gridCells)
				cell.check(coordinate, i);
		}

		Coordinate last = sequence.getCoordinate(lastIndex);
		I info = callback.last(last, lastIndex);

		PVector<IntersectionSet> sequences = toVector(gridCells.stream()
			.map(g -> g.last(last, lastIndex)));

		return T2.of(info, toVector(gridCells.stream().map(x -> x.events)));
	}

	public static class GridCell {

		private final Cell<?> cell;
		private PVector<Event> events = Empty.vector();

		public GridCell(Cell<?> cell) {
			this.cell = cell;
		}

		public void first(Coordinate coordinate) {

		}

		public void check(Coordinate coordinate, int i) {

		}

		public PStack<Event> last(Coordinate coordinate, int i) {

		}
	}

	public static class Event {
	}
}
