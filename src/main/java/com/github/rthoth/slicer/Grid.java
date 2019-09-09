package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.Empty;
import org.pcollections.PSequence;
import org.pcollections.PVector;

import java.util.stream.StreamSupport;

import static com.github.rthoth.slicer.Util.toVector;

public class Grid {

	public interface Callback<I> {

		void check(Coordinate coordinate, int i);

		void first(Coordinate coordinate);

		I last(Coordinate coordinate, int i);
	}

	public interface Cropper<I> {
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

	public <I> Object traverse(Sequences sequences, Callback<I> callback, Cropper<I> cropper) {
		switch (order) {
			case X_Y:
				return traverse(sequences, callback, cropper, xAxis, yAxis);

			case Y_X:
				return traverse(sequences, callback, cropper, yAxis, xAxis);

			case AUTOMATIC:
				if (xAxis.size() >= yAxis.size())
					return traverse(sequences, callback, cropper, xAxis, yAxis);
				else
					return traverse(sequences, callback, cropper, yAxis, xAxis);

			default:
				throw new IllegalArgumentException();
		}
	}

	private <I> String traverse(Sequences sequences, Callback<I> callback, Cropper<I> cropper,
															PSequence<? extends Cell> _1, PSequence<? extends Cell> _2) {
		PVector<Result<I>> result1 = StreamSupport.stream(sequences.spliterator(), false)
			.map(seq -> step1(seq, _1, callback, cropper))
			.collect(toVector());

		crop(result1, cropper);

		return null;
	}

	private <I> Result<I> step1(Sequences.Seq seq, PSequence<? extends Cell> cells,
															Callback<I> callback, Cropper<I> cropper) {

		CoordinateSequence sequence = seq.getSequence();
		Event.Factory eventFactory = new Event.Factory(sequence);

		PVector<GridCell> gridCells = cells.stream()
			.map(cell -> new GridCell(eventFactory, cell)).collect(toVector());

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
			.map(gridCell -> gridCell.last(last, lastIndex, seq.isClosed()))
			.collect(toVector());

		return new Result<>(info, events, seq);
	}

	private <I> void crop(PSequence<Result<I>> results, Cropper<I> cropper) {

	}

	public static class Result<I> {

		private final I info;
		private final PSequence<PSequence<Event>> events;
		private final Sequences.Seq seq;

		public Result(I info, PSequence<PSequence<Event>> events, Sequences.Seq seq) {
			this.info = info;
			this.events = events;
			this.seq = seq;
		}
	}
}
