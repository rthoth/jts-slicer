package com.github.rthoth.slicer;

public abstract class Cell<G extends Guide<?>> {

	public static class Lower<G extends Guide<?>> extends Cell<G> {

		private final G upper;

		public Lower(G upper) {
			this.upper = upper;
		}
	}

	public static class Middle<G extends Guide<?>> extends Cell<G> {

		private final G lower;
		private final G upper;

		public Middle(G lower, G upper) {
			this.lower = lower;
			this.upper = upper;
		}
	}

	public static class Upper<G extends Guide<?>> extends Cell<G> {

		private final G lower;

		public Upper(G lower) {
			this.lower = lower;
		}


	}
}
