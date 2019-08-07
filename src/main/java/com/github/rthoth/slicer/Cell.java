package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;

public interface Cell {

	void begin(Coordinate coordinate);

	void check(Coordinate coordinate, int index);

	void end(Coordinate coordinate, int index);
}
