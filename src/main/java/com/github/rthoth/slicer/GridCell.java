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
	private int previousPosition;
	private Coordinate previousCoordinate;
	private Location firstLocation;
	private int previousIndex;
	private Location previousLocation;
	private Event candidate = null;
	private int firstIndex;

	public GridCell(Event.Factory evtFactory, Cell<?> cell) {
		this.evtFactory = evtFactory;
		this.cell = cell;
	}

	private void addCandidate() {
		if (candidate != null) {
			events.addLast(candidate);
			candidate = null;
		}
	}

	public void first(Coordinate coordinate, int index) {
		events.clear();

		previousCoordinate = coordinate;
		previousIndex = index;
		firstIndex = index;
		previousPosition = cell.positionOf(coordinate);
		firstLocation = Location.of(previousPosition);
		previousLocation = firstLocation;
	}

	public void check(Coordinate coordinate, int index) {
		final int currentPosition = cell.positionOf(coordinate);
		final Location currentLocation = Location.of(currentPosition);

		if (currentPosition != previousPosition) {
			if (firstLocation == Location.BORDER && currentLocation != Location.BORDER)
				firstLocation = currentLocation;

			check(coordinate, index, currentPosition, currentLocation);
			previousPosition = currentPosition;
			previousLocation = currentLocation;
		}

		previousIndex = index;
		previousCoordinate = coordinate;
	}

	private void check(Coordinate coordinate, int index, int position, Location location) {
		switch (previousPosition * position) {
			case 0: // outside->inside, border->inside, inside->border, inside->outside
				if (previousLocation == OUTSIDE) { // outside->inside
					events.addLast(evtFactory.newIn(index, cell.intersection(previousCoordinate, coordinate, previousPosition), previousPosition));
				} else if (location == OUTSIDE) { // inside->outside
					events.addLast(evtFactory.newOut(previousIndex, cell.intersection(previousCoordinate, coordinate, position), position));
				} else if (location == BORDER) { // inside->border
					candidate = evtFactory.newOut(index, null, position);
				} else { // border->inside
					if (candidate == null || candidate.getIndex() != previousIndex) {
						addCandidate();
						events.addLast(evtFactory.newIn(previousIndex, null, previousPosition));
					} else {
						candidate = null;
					}
				}
				break;

			case -4: // cross: outside->outside
				events.addLast(evtFactory.newIn(index, cell.intersection(previousCoordinate, coordinate, previousPosition), previousPosition));
				events.addLast(evtFactory.newOut(index, cell.intersection(previousCoordinate, coordinate, position), position));
				break;

			case -1: // cross: border->border
				if (candidate instanceof Event.Out && candidate.getIndex() != previousIndex) {
					candidate = null;
				} else {
					addCandidate();
					events.addLast(evtFactory.newIn(previousIndex, null, previousPosition));
				}

				candidate = evtFactory.newOut(index, null, position);
				break;

			case -2: // cross: outside->border, border->outside
				if (location == BORDER) { // outside->border
					events.addLast(evtFactory.newIn(index, cell.intersection(previousCoordinate, coordinate, previousPosition), previousPosition));
					candidate = evtFactory.newOut(index, null, position);
				} else { // border->outside
					if (candidate instanceof Event.Out && candidate.getIndex() != previousIndex) {
						addCandidate();
						events.addLast(evtFactory.newIn(previousIndex, null, previousPosition));
					} else {
						candidate = null;
					}
					events.addLast(evtFactory.newOut(index, null, position));
				}
				break;

			case 2: // same side: outside->border, border->outside
				if (location == OUTSIDE && candidate instanceof Event.Out) {
					addCandidate();
				}
				break;
		}
	}

	public <I> PSequence<Event> last(Coordinate coordinate, int index, boolean closed, Grid.Cropper<I> cropper, I info) {
		check(coordinate, index);

		if (candidate instanceof Event.Out && closed) {
			Event first = events.peekFirst();
			if (first instanceof Event.In && first.getIndex() == firstIndex) {
				events.removeFirst();
			} else {
				events.addLast(candidate);
			}
		}

		return cell.crop(TreePVector.from(events), cropper, info);
	}
}
