package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.PSequence;

import java.util.LinkedList;

public class GridCell {


	private final Cell<?> cell;

	private final LinkedList<Event> events = new LinkedList<>();
	private final Event.Factory eventFactory;
	private int lastPosition;
	private Coordinate lastCoordinate;
	private Location firstLocation;
	private int lastIndex;
	private Location lastLocation;

	public GridCell(Event.Factory eventFactory, Cell<?> cell) {
		this.eventFactory = eventFactory;
		this.cell = cell;
	}

	public void first(Coordinate coordinate, int index) {
		events.clear();

		lastCoordinate = coordinate;
		lastIndex = index;
		lastPosition = cell.position(coordinate);
		firstLocation = Location.of(lastPosition);
		lastLocation = firstLocation;
	}

	public void check(Coordinate coordinate, int index) {
		final int currentPosition = cell.position(coordinate);
		final Location currentLocation = Location.of(currentPosition);

		if (currentPosition != lastPosition) {

			if (firstLocation == Location.BORDER && currentLocation != Location.BORDER) {
				firstLocation = currentLocation;
			}

			check(coordinate, index, currentPosition, currentLocation);
		}
	}

	private void check(Coordinate coordinate, int index, int position, Location location) {
		final int product = lastPosition * position;
		final Event last = events.peekLast();

		switch (product) {
			case 2: // same side: outside -> border, border -> outside

		}
	}

	public PSequence<Event> last(Coordinate coordinate, int index) {
		throw new UnsupportedOperationException();
	}
}
