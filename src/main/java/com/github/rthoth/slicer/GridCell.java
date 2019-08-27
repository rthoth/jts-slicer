package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.PSequence;

import java.util.LinkedList;

public class GridCell {


	private final CoordinateSequence sequence;
	private final Cell<?> cell;

	private final LinkedList<Event> events = new LinkedList<>();
	private int firstPosition;
	private int lastPosition;
	private Coordinate lastCoordinate;
	private int absFirstPosition;

	public GridCell(CoordinateSequence sequence, Cell<?> cell) {
		this.sequence = sequence;
		this.cell = cell;
	}

	public void first(Coordinate coordinate, int index) {
		events.clear();

		firstPosition = cell.position(coordinate);
		absFirstPosition = Math.abs(firstPosition);

		lastPosition = firstPosition;
		lastCoordinate = coordinate;
	}

	public void check(Coordinate coordinate, int index) {
		final int currentPosition = cell.position(coordinate);

		if (absFirstPosition == 1 && Math.abs(currentPosition) != 1) {
			absFirstPosition = Math.abs(currentPosition);
			firstPosition = currentPosition;
		}

		if (currentPosition != lastPosition)
			check(coordinate, index, currentPosition);
	}

	private void check(Coordinate coordinate, int index, int currentPosition) {
		
	}

	public PSequence<Event> last(Coordinate coordinate, int index) {
		throw new UnsupportedOperationException();
	}
}
