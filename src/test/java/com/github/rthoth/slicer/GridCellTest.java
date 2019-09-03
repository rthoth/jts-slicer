package com.github.rthoth.slicer;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.PSequence;

public class GridCellTest {

	private String sequence01 = "(6 14, 4 14, 2 12, 5 12, 5 10, 8 10, 6 8, 6 6, 8 4, 6 4, 4 4, 5 6, 3 6, 1 2, 1 16, 4 16, 6 14)";

	private GridCell newXMiddle(double x0, double x1, CoordinateSequence sequence) {
		Event.Factory evtFactory = new Event.Factory(sequence);
		return new GridCell(evtFactory, new Cell.Middle<>(new Guide.X(x0, Slicer.DEFAULT_OFFSET, 0), new Guide.X(x1, Slicer.DEFAULT_OFFSET, 0D)));
	}

	private PSequence<Event> traverse(CoordinateSequence sequence, GridCell cell) {
		if (sequence.size() > 0) {
			cell.first(sequence.getCoordinate(0), 0);
			int lastIndex = sequence.size() - 1;
			for (int i = 1; i < lastIndex; i++) {
				cell.check(sequence.getCoordinate(i), i);
			}

			return cell.last(sequence.getCoordinate(lastIndex), lastIndex);
		}

		return null;
	}

	@Test
	public void lower_01() {
		CoordinateSequence sequence = GeometryHelper.sequenceOf(sequence01);
		GridCell cell = newXMiddle(4, 6, sequence);
		PSequence<Event> events = traverse(sequence, cell);
	}
}
