package com.github.rthoth.slicer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.Empty;
import org.pcollections.PSequence;

public class Grid<C extends Cell> {

	public interface TraverseCallback {
		void apply(Coordinate coordinate, int index);
	}

	public interface CellFactory<C> {
		C southWest(Guide.X x0, Guide.Y y0);

		C south(Guide.X x0, Guide.X x1, Guide.Y y0);

		C southEast(Guide.X x0, Guide.Y y0);

		C centerWest(Guide.X x0, Guide.Y y0, Guide.Y y1);

		C center(Guide.X x0, Guide.X x1, Guide.Y y0, Guide.Y y1);

		C centerEast(Guide.X x0, Guide.Y y0, Guide.Y y1);

		C northWest(Guide.X x0, Guide.Y y0);

		C north(Guide.X x0, Guide.X x1, Guide.Y y0);

		C northEast(Guide.X x0, Guide.Y y0);

		C verticalWest(Guide.X x0);

		C vertical(Guide.X x0, Guide.X x1);

		C verticalEast(Guide.X x0);

		C horizontalSouth(Guide.Y y0);

		C horizontal(Guide.Y y0, Guide.Y y1);

		C horizontalNorth(Guide.Y y0);
	}

	private final PSequence<C> grid;
	private final int row;
	private final int col;


	public Grid(PSequence<Guide.X> x, PSequence<Guide.Y> y, CellFactory<C> factory) {

		PSequence<C> newGrid = Empty.vector();

		if (x.size() > 0 && y.size() > 0) {

			newGrid = newGrid.plus(factory.southWest(x.get(0), y.get(0)));

			for (int j = 1, l = x.size(); j < l; j++)
				newGrid = newGrid.plus(factory.south(x.get(j - 1), x.get(j), y.get(0)));

			newGrid = newGrid.plus(factory.southEast(x.get(x.size() - 1), y.get(0)));

			for (int i = 1, bi = 0, il = y.size(); i < il; i++, bi++) {

				newGrid = newGrid.plus(factory.centerWest(x.get(0), y.get(bi), y.get(i)));

				for (int j = 1, jl = x.size(); j < jl; j++)
					newGrid = newGrid.plus(factory.center(x.get(i - 1), x.get(i), y.get(bi), y.get(i)));

				newGrid = newGrid.plus(factory.centerEast(x.get(x.size() - 1), y.get(bi), y.get(i)));

			}

			newGrid = newGrid.plus(factory.northWest(x.get(0), y.get(y.size() - 1)));

			for (int j = 1, l = x.size(); j < l; j++)
				newGrid = newGrid.plus(factory.north(x.get(j - 1), x.get(j), y.get(y.size() - 1)));

			newGrid = newGrid.plus(factory.northEast(x.get(x.size() - 1), y.get(y.size() - 1)));

		} else if (x.size() > 0) {

			newGrid = newGrid.plus(factory.verticalWest(x.get(0)));

			for (int j = 1, l = x.size(); j < l; j++)
				newGrid = newGrid.plus(factory.vertical(x.get(j - 1), x.get(j)));

			newGrid = newGrid.plus(factory.verticalEast(x.get(x.size() - 1)));


		} else if (y.size() > 0) {

			newGrid = newGrid.plus(factory.horizontalSouth(y.get(0)));

			for (int i = 1, l = y.size(); i < l; i++)
				newGrid = newGrid.plus(factory.horizontal(y.get(i - 1), y.get(i)));

			newGrid = newGrid.plus(factory.horizontalNorth(y.get(y.size() - 1)));

		} else {
			throw new IllegalArgumentException();
		}

		row = y.size() + 1;
		col = x.size() + 1;

		grid = newGrid;
	}

	public void traverse(CoordinateSequence sequence, TraverseCallback callback) {
		if (sequence.size() > 0) {
			final Coordinate first = sequence.getCoordinate(0);
			for (Cell cell : grid)
				cell.begin(first);

			callback.apply(first, 0);

			for (int i = 1, l = sequence.size() - 1; i < l; i++) {
				final Coordinate coordinate = sequence.getCoordinate(i);
				for (Cell cell : grid)
					cell.check(coordinate, i);

				callback.apply(coordinate, i);
			}

			final int index = sequence.size() - 1;
			final Coordinate last = sequence.getCoordinate(index);
			for (Cell cell : grid)
				cell.end(last, index);

			callback.apply(last, sequence.size() - 1);
		} else {
			// TODO: Empty!!!!
		}
	}

}
