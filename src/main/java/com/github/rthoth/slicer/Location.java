package com.github.rthoth.slicer;

public enum Location {
	INSIDE, OUTSIDE, BORDER;

	public static Location of(int position) {
		switch (position) {
			case -2:
			case 2:
				return OUTSIDE;

			case 0:
				return INSIDE;

			case -1:
			case 1:
				return BORDER;

			default:
				throw new IllegalArgumentException(String.valueOf(position));
		}
	}
}