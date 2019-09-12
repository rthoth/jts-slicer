package com.github.rthoth.slicer;

import org.pcollections.PSequence;
import org.pcollections.PVector;
import org.pcollections.TreePVector;

public class EventSet<I> {

	public static class Item<I> {
		private final I info;
		private final Seq seq;
		private final PSequence<PSequence<Event>> events;

		public Item(I info, Seq seq, PVector<PSequence<Event>> events) {
			this.info = info;
			this.seq = seq;
			this.events = events;
		}
	}

	private final PSequence<Item<I>> items;

	public EventSet(PSequence<Item<I>> items) {
		this.items = items;
	}

	public EventSet(I info, Seq seq, PVector<PSequence<Event>> events) {
		this(TreePVector.singleton(new Item<>(info, seq, events)));
	}

	public EventSet<I> merge(EventSet<I> other) {
		return new EventSet<>(items.plusAll(other.items));
	}
}
