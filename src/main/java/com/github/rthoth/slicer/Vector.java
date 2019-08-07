package com.github.rthoth.slicer;

public class Vector {

	private final int i;
	private final int j;

	public Vector(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public double cross(Vector other) {
		return i * other.j - j * other.i;
	}

	public double length() {
		return Math.sqrt(i * i + j * j);
	}
}
