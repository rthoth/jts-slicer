package com.github.rthoth.slicer;

import org.pcollections.PSequence;

import java.util.stream.Stream;

public class SequenceSet {

	private final PSequence<Seq> seqs;

	public SequenceSet(PSequence<Seq> seqs) {
		this.seqs = seqs;
	}

	public Stream<Seq> stream() {
		return seqs.stream();
	}
}
