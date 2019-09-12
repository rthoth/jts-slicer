package com.github.rthoth.slicer;

import org.pcollections.PSequence;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Sequences<S extends Seq> implements Iterable<S> {

	private final PSequence<S> seqs;

	public Sequences(PSequence<S> seqs) {
		this.seqs = seqs;
	}

	@Override
	public Iterator<S> iterator() {
		return seqs.iterator();
	}

	@Override
	public void forEach(Consumer<? super S> action) {
		seqs.forEach(action);
	}

	@Override
	public Spliterator<S> spliterator() {
		return seqs.spliterator();
	}
}
