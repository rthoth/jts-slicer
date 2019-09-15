package com.github.rthoth.slicer;

import com.github.rthoth.slicer.SliceSet.Slice;
import org.locationtech.jts.geom.Coordinate;
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

		SequenceSet crop(Slice slice, SliceSet<I> sliceSet, Guide<?> guide);

		SequenceSet crop(Slice slice, SliceSet<I> sliceSet, Guide<?> lower, Guide<?> upper);
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

	public <I> Object traverse(SequenceSet sequenceSet, Callback<I> callback, Cropper<I> cropper) {
		switch (order) {
			case X_Y:
				return traverse(sequenceSet, callback, cropper, xAxis, yAxis);

			case Y_X:
				return traverse(sequenceSet, callback, cropper, yAxis, xAxis);

			case AUTOMATIC:
				if (xAxis.size() >= yAxis.size())
					return traverse(sequenceSet, callback, cropper, xAxis, yAxis);
				else
					return traverse(sequenceSet, callback, cropper, yAxis, xAxis);

			default:
				throw new IllegalArgumentException();
		}
	}

	private <I> String traverse(SequenceSet sequenceSet, Callback<I> callback, Cropper<I> cropper,
															PSequence<? extends Cell> _1, PSequence<? extends Cell> _2) {
		if (!_1.isEmpty()) {
			SliceSet<I> sliceSet = sequenceSet.stream()
				.map(seq -> computeSliceSet(seq, _1, callback))
				.reduce(SliceSet::merge).get();
		} else if (!_2.isEmpty()) {

		} else {

		}

		return null;
	}

	private <I> SliceSet<I> computeSliceSet(Seq seq, PSequence<? extends Cell> cells, Callback<I> callback) {

		Event.Factory evtFactory = new Event.Factory(seq);

		PVector<GridCell> gridCells = cells.stream()
			.map(cell -> new GridCell(evtFactory, cell)).collect(toVector());

		Coordinate first = seq.getCoordinate(0);
		callback.first(first);

		for (GridCell gridCell : gridCells)
			gridCell.first(first, 0);

		final int lastIndex = seq.size() - 1;

		for (int i = 1; i < lastIndex; i++) {
			final Coordinate coordinate = seq.getCoordinate(i);
			callback.check(coordinate, i);
			for (GridCell gridCell : gridCells)
				gridCell.check(coordinate, i);
		}

		final Coordinate last = seq.getCoordinate(lastIndex);
		I info = callback.last(last, lastIndex);

		PVector<Slice> slices = gridCells.stream()
			.map(gridCell -> new Slice(gridCell.getFirstLocation(), gridCell.last(last, lastIndex, seq.isClosed())))
			.collect(toVector());

		return new SliceSet<>(seq, info, slices);
	}
}
