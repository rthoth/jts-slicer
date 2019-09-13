package com.github.rthoth.slicer;

import org.pcollections.Empty;
import org.pcollections.PMap;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import java.util.LinkedList;

public class SliceSet<I> {

	public static class Info<I> {

		private final Seq seq;
		private final I info;

		public Info(Seq seq, I info) {
			this.seq = seq;
			this.info = info;
		}

		public I getInfo() {
			return info;
		}

		public Seq getSeq() {
			return seq;
		}

	}

	public static class Slice {

		private final PSequence<PSequence<Event>> events;
		private final PSequence<Location> firstLocations;

		public Slice(Location firstLocations, PSequence<Event> events) {
			this(TreePVector.singleton(events), TreePVector.singleton(firstLocations));
		}

		public Slice(PSequence<PSequence<Event>> events, PSequence<Location> firstLocations) {
			assert events.size() == firstLocations.size();
			this.events = events;
			this.firstLocations = firstLocations;
		}

		public Slice merge(Slice other) {
			return new Slice(events.plusAll(other.events), firstLocations.plusAll(other.firstLocations));
		}
	}

	private PSequence<Info<I>> infos;
	private final PSequence<Slice> slices;

	private LazyProperty<PMap<Seq, Info<I>>> infoMap = new LazyProperty<>(() -> {
		PMap<Seq, Info<I>> ret = Empty.map();

		for (Info<I> info : infos) {
			ret = ret.plus(info.getSeq(), info);
		}

		return ret;
	});

	public SliceSet(PSequence<Info<I>> infos, PSequence<Slice> slices) {
		this.infos = infos;
		this.slices = slices;
	}

	public SliceSet(Seq seq, I info, PSequence<Slice> slices) {
		this(TreePVector.singleton(new Info<>(seq, info)), slices);
	}

	public PSequence<Info<I>> getInfos() {
		return infos;
	}

	public PSequence<Slice> getSlices() {
		return slices;
	}

	public SliceSet<I> merge(SliceSet<I> other) {
		return new SliceSet<>(infos.plusAll(other.infos), mergeSlices(other.slices));
	}

	private PSequence<Slice> mergeSlices(PSequence<Slice> other) {
		LinkedList<Slice> list = new LinkedList<>();
		if (slices.size() == other.size()) {
			for (int i = 0; i < slices.size(); i++) {
				Slice _1 = slices.get(i);
				Slice _2 = other.get(i);
				list.addLast(_1.merge(_2));
			}

			return TreePVector.from(list);
		} else
			throw new IllegalArgumentException();
	}
}
