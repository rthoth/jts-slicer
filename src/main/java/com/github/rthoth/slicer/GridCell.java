package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import java.util.LinkedList;

import static com.github.rthoth.slicer.Location.*;

public class GridCell {


	private final Cell<?> cell;

	private final LinkedList<Event> events = new LinkedList<>();
	private final Event.Factory evtFactory;
	private int lastPosition;
	private Coordinate lastCoordinate;
	private Location firstLocation;
	private int lastIndex;
	private Location lastLocation;

	public GridCell(Event.Factory evtFactory, Cell<?> cell) {
		this.evtFactory = evtFactory;
		this.cell = cell;
	}

	public void first(Coordinate coordinate, int index) {
		events.clear();

		lastCoordinate = coordinate;
		lastIndex = index;
		lastPosition = cell.positionOf(coordinate);
		firstLocation = Location.of(lastPosition);
		lastLocation = firstLocation;
	}

	public void check(Coordinate coordinate, int index) {
		final int currentPosition = cell.positionOf(coordinate);
		final Location currentLocation = Location.of(currentPosition);

		if (currentPosition != lastPosition) {

			if (firstLocation == Location.BORDER && currentLocation != Location.BORDER) {
				firstLocation = currentLocation;
			}

			check(coordinate, index, currentPosition, currentLocation);
			lastPosition = currentPosition;
			lastLocation = currentLocation;
			lastCoordinate = coordinate;
			lastIndex = index;
		}
	}

	private void check(Coordinate coordinate, int index, int position, Location location) {
		final int product = lastPosition * position;
		final Event last = events.peekLast();

		switch (product) {
			case -4: // cross: outside->outside
				final Coordinate _1 = cell.intersection(lastCoordinate, coordinate, lastPosition);
				final Coordinate _2 = cell.intersection(lastCoordinate, coordinate, position);

				events.addLast(evtFactory.newIn(index, _1, lastPosition));
				events.addLast(evtFactory.newOut(index, _2, position));

				break;

			case 0: // outside->inside, border->inside, inside->outside, inside->border
				if (lastLocation == OUTSIDE) {
					events.addLast(evtFactory.newIn(index, cell.intersection(lastCoordinate, coordinate, lastPosition), position));
				} else if (location == OUTSIDE) {
					events.addLast(evtFactory.newOut(index, cell.intersection(lastCoordinate, coordinate, position), position));
				} else if (lastLocation == BORDER) {
					if (last == null || lastIndex != index - 1) {
						events.addLast(evtFactory.newIn(index, getCoordinate(index - 1), position));
					} else if (lastIndex == index - 1) {
						events.removeLast();
					}

				} else {
					events.addLast(evtFactory.newOut(index, null, position));
				}
				break;

			case -1: // border->border
				if (last != null && last.getIndex() == index - 1)
					events.removeLast();

				events.addLast(evtFactory.newOut(index, null, position));
				break;
		}
	}

	private Coordinate getCoordinate(int index) {
		return evtFactory.getCoordinate(index);
	}

	public PSequence<Event> last(Coordinate coordinate, int index) {
		check(coordinate, index);
		return TreePVector.from(events);
	}
}
