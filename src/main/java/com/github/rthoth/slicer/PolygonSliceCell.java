package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;

import java.util.LinkedList;

public abstract class PolygonSliceCell implements SliceCell {

	protected LinkedList<SliceEvent> events = new LinkedList<>();
	protected Position lastPosition;
	protected int lastIndex = 0;
	protected Side lastSide;

	@Override
	public void begin(Coordinate coordinate) {
		lastPosition = positionOf(coordinate);
		lastSide = Side.classify(lastPosition);
	}

	protected abstract Position positionOf(Coordinate coordinate);

	@Override
	public void check(Coordinate coordinate, int index) {
		Position currentPosition = positionOf(coordinate);
		Side currentSide = Side.classify(currentPosition);

		Vector v1 = currentPosition.minus(lastPosition);
		int distance = Math.abs((int) (v1.cross(Position.ZERO.minus(lastPosition)) / v1.length()));
		System.out.println(String.format("%d : %s -> %s", distance, lastPosition, currentPosition));

		if (currentSide == lastSide) {
			// TODO: Check if there is an intersection!
		} else if (currentSide != Side.BORDER && lastSide != Side.BORDER) {
			if (currentSide == Side.INSIDE) {
				// get in
			} else {
				// get out
			}
		} else if (currentSide == Side.BORDER) {
		} else {
		}
	}

	@Override
	public void end(Coordinate coordinate, int index) {

	}

	public static class SouthWest extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.Y y0;

		public SouthWest(Guide.X x0, Guide.Y y0) {
			this.x0 = x0;
			this.y0 = y0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, null, x0, null, y0);
		}
	}

	public static class South extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.X x1;
		private final Guide.Y y0;

		public South(Guide.X x0, Guide.X x1, Guide.Y y0) {
			this.x0 = x0;
			this.x1 = x1;
			this.y0 = y0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, x0, x1, null, y0);
		}
	}

	public static class SouthEast extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.Y y0;

		public SouthEast(Guide.X x0, Guide.Y y0) {
			this.x0 = x0;
			this.y0 = y0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, null, x0, null, y0);
		}
	}

	public static class CenterWest extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.Y y0;
		private final Guide.Y y1;

		public CenterWest(Guide.X x0, Guide.Y y0, Guide.Y y1) {
			this.x0 = x0;
			this.y0 = y0;
			this.y1 = y1;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, null, x0, y0, y1);
		}
	}

	public static class Center extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.X x1;
		private final Guide.Y y0;
		private final Guide.Y y1;

		public Center(Guide.X x0, Guide.X x1, Guide.Y y0, Guide.Y y1) {
			this.x0 = x0;
			this.x1 = x1;
			this.y0 = y0;
			this.y1 = y1;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, x0, x1, y0, y1);
		}
	}

	public static class CenterEast extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.Y y0;
		private final Guide.Y y1;

		public CenterEast(Guide.X x0, Guide.Y y0, Guide.Y y1) {
			this.x0 = x0;
			this.y0 = y0;
			this.y1 = y1;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, null, x0, y0, y1);
		}
	}

	public static class NorthWest extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.Y y0;

		public NorthWest(Guide.X x0, Guide.Y y0) {
			this.x0 = x0;
			this.y0 = y0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, null, x0, y0, null);
		}
	}

	public static class North extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.X x1;
		private final Guide.Y y0;

		public North(Guide.X x0, Guide.X x1, Guide.Y y0) {
			this.x0 = x0;
			this.x1 = x1;
			this.y0 = y0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, x0, x1, y0, null);
		}
	}

	public static class NorthEast extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.Y y0;

		public NorthEast(Guide.X x0, Guide.Y y0) {
			this.x0 = x0;
			this.y0 = y0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, x0, null, y0, null);
		}
	}

	public static class VerticalWest extends PolygonSliceCell {

		private final Guide.X x0;

		public VerticalWest(Guide.X x0) {
			this.x0 = x0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, null, x0, null, null);
		}
	}

	public static class Vertical extends PolygonSliceCell {

		private final Guide.X x0;
		private final Guide.X x1;

		public Vertical(Guide.X x0, Guide.X x1) {
			this.x0 = x0;
			this.x1 = x1;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, x0, x1, null, null);
		}
	}

	public static class VerticalEast extends PolygonSliceCell {

		private final Guide.X x0;

		public VerticalEast(Guide.X x0) {
			this.x0 = x0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, x0, null, null, null);
		}
	}

	public static class HorizontalSouth extends PolygonSliceCell {

		private final Guide.Y y0;

		public HorizontalSouth(Guide.Y y0) {
			this.y0 = y0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, null, null, null, y0);
		}
	}

	public static class Horizontal extends PolygonSliceCell {

		private final Guide.Y y0;
		private final Guide.Y y1;

		public Horizontal(Guide.Y y0, Guide.Y y1) {
			this.y0 = y0;
			this.y1 = y1;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, null, null, y0, y1);
		}
	}

	public static class HorizontalNorth extends PolygonSliceCell {

		private final Guide.Y y0;

		public HorizontalNorth(Guide.Y y0) {
			this.y0 = y0;
		}

		@Override
		protected Position positionOf(Coordinate coordinate) {
			return Position.of(coordinate, null, null, y0, null);
		}
	}
}
