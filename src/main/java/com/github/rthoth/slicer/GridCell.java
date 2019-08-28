package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.PSequence;

import java.util.LinkedList;

import static com.github.rthoth.slicer.Location.*;

public class GridCell {


	private final CoordinateSequence sequence;
	private final Cell<?> cell;

	private final LinkedList<Event> events = new LinkedList<>();
	private int lastPosition;
	private Coordinate lastCoordinate;
	private Location firstLocation;
	private int lastIndex;
	private Location lastLocation;

	public GridCell(CoordinateSequence sequence, Cell<?> cell) {
		this.sequence = sequence;
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
			case 2: // same side: outside -> border or border -> outside
				if (location == OUTSIDE) {
					if (last != null && last.getLocation() == INSIDE) {
						events.removeLast();
					}
				} else {
					events.addLast(newEvent(index, null, position, INSIDE));
				}
				break;

			case 0: // border -> inside or outside -> inside or inside -> border or inside -> outside
				if (lastLocation == OUTSIDE) {
					events.addLast(newEvent(index, cell.intersection(lastCoordinate, coordinate), lastPosition, INSIDE));
				} else if (location == OUTSIDE) {
					events.addLast(newEvent(index, cell.intersection(lastCoordinate, coordinate), position, OUTSIDE));
				} else if (lastLocation == BORDER) {
					if (last != null) {
						if (last.getLocation() == INSIDE)
							events.removeLast();
					}
					events.addLast(newEvent(index, ));
				}


		}
	}

	private Event newEvent(int index, Coordinate coordinate, int position, Location location) {
		return new Event(sequence, index, coordinate, position, location);
	}

	public PSequence<Event> last(Coordinate coordinate, int index) {
		throw new UnsupportedOperationException();
	}
}
