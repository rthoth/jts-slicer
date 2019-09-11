package com.github.rthoth.slicer;

import com.github.rthoth.slicer.Sequences.Seq;
import org.pcollections.PSequence;

public class Slice<S extends Seq, I> {

	public Slice(PSequence<Event> events, S seq, I info) {
		this.events = events;
		this.seq = seq;
		this.info = info;
	}
}
