package com.github.rthoth.slicer;

import static java.lang.Math.abs;

public enum Side {
	OUTSIDE, INSIDE, BORDER;

	public static Side classify(Position position) {
		if (abs(position.getX()) == 2 || abs(position.getY()) == 2) {
			return OUTSIDE;
		} else if (position.getX() == 0 && position.getY() == 0) {
			return INSIDE;
		} else {
			return BORDER;
		}
	}
}
