package com.github.rthoth.slicer;

public class PolygonCellFactory {

	public static final Grid.CellFactory<SliceCell> SLICE_FACTORY = new Grid.CellFactory<SliceCell>() {

		@Override
		public SliceCell southWest(Guide.X x0, Guide.Y y0) {
			return new PolygonSliceCell.SouthWest(x0, y0);
		}

		@Override
		public SliceCell south(Guide.X x0, Guide.X x1, Guide.Y y0) {
			return new PolygonSliceCell.South(x0, x1, y0);
		}

		@Override
		public SliceCell southEast(Guide.X x0, Guide.Y y0) {
			return new PolygonSliceCell.SouthEast(x0, y0);
		}

		@Override
		public SliceCell centerWest(Guide.X x0, Guide.Y y0, Guide.Y y1) {
			return new PolygonSliceCell.CenterWest(x0, y0, y1);
		}

		@Override
		public SliceCell center(Guide.X x0, Guide.X x1, Guide.Y y0, Guide.Y y1) {
			return new PolygonSliceCell.Center(x0, x1, y0, y1);
		}

		@Override
		public SliceCell centerEast(Guide.X x0, Guide.Y y0, Guide.Y y1) {
			return new PolygonSliceCell.CenterEast(x0, y0, y1);
		}

		@Override
		public SliceCell northWest(Guide.X x0, Guide.Y y0) {
			return new PolygonSliceCell.NorthWest(x0, y0);
		}

		@Override
		public SliceCell north(Guide.X x0, Guide.X x1, Guide.Y y0) {
			return new PolygonSliceCell.North(x0, x1, y0);
		}

		@Override
		public SliceCell northEast(Guide.X x0, Guide.Y y0) {
			return new PolygonSliceCell.NorthEast(x0, y0);
		}

		@Override
		public SliceCell verticalWest(Guide.X x0) {
			return new PolygonSliceCell.VerticalWest(x0);
		}

		@Override
		public SliceCell vertical(Guide.X x0, Guide.X x1) {
			return new PolygonSliceCell.Vertical(x0, x1);
		}

		@Override
		public SliceCell verticalEast(Guide.X x0) {
			return new PolygonSliceCell.VerticalEast(x0);
		}

		@Override
		public SliceCell horizontalSouth(Guide.Y y0) {
			return new PolygonSliceCell.HorizontalSouth(y0);
		}

		@Override
		public SliceCell horizontal(Guide.Y y0, Guide.Y y1) {
			return new PolygonSliceCell.Horizontal(y0, y1);
		}

		@Override
		public SliceCell horizontalNorth(Guide.Y y0) {
			return new PolygonSliceCell.HorizontalNorth(y0);
		}
	};
}
