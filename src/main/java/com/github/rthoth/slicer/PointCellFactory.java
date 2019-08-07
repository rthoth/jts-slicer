package com.github.rthoth.slicer;

public class PointCellFactory {
	public static final Grid.CellFactory<SliceCell> SLICE_FACTORY = new Grid.CellFactory<SliceCell>() {
		@Override
		public SliceCell southWest(Guide.X x0, Guide.Y y0) {
			return null;
		}

		@Override
		public SliceCell south(Guide.X x0, Guide.X x1, Guide.Y y0) {
			return null;
		}

		@Override
		public SliceCell southEast(Guide.X x0, Guide.Y y0) {
			return null;
		}

		@Override
		public SliceCell centerWest(Guide.X x0, Guide.Y y0, Guide.Y y1) {
			return null;
		}

		@Override
		public SliceCell center(Guide.X x0, Guide.X x1, Guide.Y y0, Guide.Y y1) {
			return null;
		}

		@Override
		public SliceCell centerEast(Guide.X x0, Guide.Y y0, Guide.Y y1) {
			return null;
		}

		@Override
		public SliceCell northWest(Guide.X x0, Guide.Y y0) {
			return null;
		}

		@Override
		public SliceCell north(Guide.X x0, Guide.X x1, Guide.Y y0) {
			return null;
		}

		@Override
		public SliceCell northEast(Guide.X x0, Guide.Y y0) {
			return null;
		}

		@Override
		public SliceCell verticalWest(Guide.X x0) {
			return null;
		}

		@Override
		public SliceCell vertical(Guide.X x0, Guide.X x1) {
			return null;
		}

		@Override
		public SliceCell verticalEast(Guide.X x0) {
			return null;
		}

		@Override
		public SliceCell horizontalSouth(Guide.Y y0) {
			return null;
		}

		@Override
		public SliceCell horizontal(Guide.Y y0, Guide.Y y1) {
			return null;
		}

		@Override
		public SliceCell horizontalNorth(Guide.Y y0) {
			return null;
		}
	};
}
