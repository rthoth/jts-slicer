package com.github.rthoth.slicer;

import com.sun.org.apache.bcel.internal.generic.ATHROW;

import java.util.function.Supplier;

public class LazyProperty<T> {

	private final Supplier<T> supplier;
	private boolean shouldCompute = true;
	private Throwable cause = null;
	private T value;

	public LazyProperty(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	public T get() {
		if (shouldCompute) {
			synchronized (this) {
				if (shouldCompute) {
					shouldCompute = false;

					try {
						value = supplier.get();
					} catch (Throwable cause) {
						this.cause = cause;
					}
				}
			}
		}

		if (cause == null)
			return value;
		else
			throw new IllegalStateException(cause);
	}
}
