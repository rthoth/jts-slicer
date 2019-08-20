package com.github.rthoth.slicer;

public class T2<_1, _2> {

	public final _1 _1;
	public final _2 _2;

	public T2(_1 _1, _2 _2) {
		this._1 = _1;
		this._2 = _2;
	}

	public static <_1, _2> T2<_1, _2> of(_1 _1, _2 _2) {
		return new T2<>(_1, _2);
	}
}
