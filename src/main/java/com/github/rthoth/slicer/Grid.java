package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.Empty;
import org.pcollections.PSequence;
import org.pcollections.PVector;

import static com.github.rthoth.slicer.Util.toVector;

public class Grid {

	public interface Callback<I> {

		void check(Coordinate coordinate, int i);

		void first(Coordinate coordinate);

		I last(Coordinate coordinate, int i);
	}

	public interface Cropper<I> {

		PSequence<Event> crop(PSequence<Event> events, CoordinateSequence sequence, I info);
	}

	private final PVector<Cell<Guide.X>> xAxis;
	private final PVector<Cell<Guide.Y>> yAxis;
	private final Order order;

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
		this.yAxis = yAxis;
	}

	public <I> void traverse(CoordinateSequence sequence, Callback<I> callback, Cropper<I> cropper) {
		switch (order) {
			case X_Y:
				traverse(xAxis, yAxis, sequence, callback, cropper);
				break;
			case Y_X:
				traverse(yAxis, xAxis, sequence, callback, cropper);
				break;
			case AUTOMATIC:
				if (xAxis.size() > yAxis.size())
					traverse(xAxis, yAxis, sequence, callback, cropper);
				else
					traverse(yAxis, xAxis, sequence, callback, cropper);
		}
	}

	private <I> void traverse(PSequence<? extends Cell<?>> _1, PSequence<? extends Cell<?>> _2,
														CoordinateSequence sequence, Callback<I> callback, Cropper<I> cropper) {
		if (sequence.size() > 1) {
			Step1Result<I> result1 = step1(_1, sequence, callback, cropper);
		} else if (sequence.size() == 1) {
		} else {
		}
	}

	private <I> Step1Result<I> step1(PSequence<? extends Cell<?>> cells,
																	 CoordinateSequence sequence, Callback<I> callback, Cropper<I> cropper) {

		Event.Factory eventFactory = new Event.Factory(sequence);

		PVector<GridCell> gridCells = cells.stream()
			.map(c -> new GridCell(eventFactory, c)).collect(toVector());

		Coordinate first = sequence.getCoordinate(0);
		callback.first(first);

		for (GridCell gridCell : gridCells)
			gridCell.first(first, 0);

		final int lastIndex = sequence.size() - 1;
		for (int i = 1; i < lastIndex; i++) {
			final Coordinate coordinate = sequence.getCoordinate(i);
			callback.check(coordinate, i);
			for (GridCell gridCell : gridCells)
				gridCell.check(coordinate, i);
		}

		final Coordinate last = sequence.getCoordinate(lastIndex);
		I info = callback.last(last, lastIndex);
		PVector<PSequence<Event>> events = gridCells.stream()
			.map(gridCell -> cropper.crop(gridCell.last(last, lastIndex), sequence, info))
			.collect(toVector());

		return new Step1Result<>(info, events);
	}

	public static class Step1Result<I> extends T2<I, PSequence<PSequence<Event>>> {

		public Step1Result(I _1, PSequence<PSequence<Event>> _2) {
			super(_1, _2);
		}
	}
}
