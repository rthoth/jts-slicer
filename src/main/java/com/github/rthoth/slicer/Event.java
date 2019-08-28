package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

public class Event {

	private final Location location;
	private final CoordinateSequence sequence;
	private final int index;
	private final Coordinate coordinate;
	private final int position;

	public Event(CoordinateSequence sequence, int index, Coordinate coordinate, int position, Location location) {
		this.sequence = sequence;
		this.index = index;
		this.coordinate = coordinate;
		this.position = position;
		this.location = location;
	}


	public Location getLocation() {
		return location;
	}
}
