package com.github.rthoth.slicer;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.CoordinateSequence;
import org.pcollections.PSequence;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GridCellTest {

	private String sequence01 = "(6 14, 4 14, 2 12, 5 12, 5 10, 8 10, 6 8, 6 6, 8 4, 6 4, 4 4, 5 6, 3 6, 1 2, 1 16, 4 16, 6 14)";

	private <G extends Guide<?>> GridCell newMiddle(G lower, G upper, CoordinateSequence sequence) {
		Cell.Middle<G> cell = new Cell.Middle<>(lower, upper);
		return new GridCell(new Event.Factory(sequence), cell);
	}

	private <G extends Guide<?>> GridCell newLower(G upper, CoordinateSequence sequence) {
		Cell.Lower<G> cell = new Cell.Lower<>(upper);
		return new GridCell(new Event.Factory(sequence), cell);
	}

	private <G extends Guide<?>> GridCell newUpper(G lower, CoordinateSequence sequence) {
		Cell.Upper<G> cell = new Cell.Upper<>(lower);
		return new GridCell(new Event.Factory(sequence), cell);
	}

	private Guide.X x(double position) {
		return new Guide.X(position, Slicer.DEFAULT_OFFSET, 0);
	}

	private Guide.Y y(double position) {
		return new Guide.Y(position, Slicer.DEFAULT_OFFSET, 0);
	}

	private List<String> toList(PSequence<Event> events) {
		return events.stream().map(Event::toString).collect(Collectors.toList());
	}

	private Grid.Cropper<Object> newCropper(Consumer<PSequence<String>> consumer) {
		return new Grid.Cropper<Object>() {
			@Override
			public PSequence<Event> crop(PSequence<Event> events, Object info, Guide<?> guide) {
				consumer.accept(events.stream().map(Event::toString).collect(Util.toVector()));
				return events;
			}

			@Override
			public PSequence<Event> crop(PSequence<Event> events, Object info, Guide<?> lower, Guide<?> upper) {
				consumer.accept(events.stream().map(Event::toString).collect(Util.toVector()));
				return events;
			}
		};
	}

	private PSequence<Event> traverse(CoordinateSequence sequence, boolean closed, GridCell cell,
																		Consumer<PSequence<String>> consumer) {
		if (sequence.size() > 0) {
			cell.first(sequence.getCoordinate(0), 0);
			int lastIndex = sequence.size() - 1;
			for (int i = 1; i < lastIndex; i++) {
				cell.check(sequence.getCoordinate(i), i);
			}

			return cell.last(sequence.getCoordinate(lastIndex), lastIndex, closed, newCropper(consumer), null);
		}

		throw new UnsupportedOperationException();
	}

	@Test
	public void x_middle_01() {
		CoordinateSequence sequence = GeometryHelper.sequenceOf(sequence01);
		traverse(sequence, true, newMiddle(x(4), x(6), sequence), events -> assertThat(events)
			.containsOnly(
				"Out(1, (4.0, 14.0))",
				"In(3, (4.0, 12.0))",
				"Out(4, (6.0, 10.0))",
				"In(9, (6.0, 4.0))",
				"Out(11, (4.0, 6.0))",
				"In(15, (4.0, 16.0))"
			));
	}

	@Test
	public void x_lower_01() {
		CoordinateSequence sequence = GeometryHelper.sequenceOf(sequence01);
		traverse(sequence, true, newLower(x(4), sequence), events -> assertThat(events).containsOnly(
			"In(1, (4.0, 14.0))",
			"Out(2, (4.0, 12.0))",
			"In(12, (4.0, 6.0))",
			"Out(15, (4.0, 16.0))"
		));
	}

	@Test
	public void x_upper_01() {
		CoordinateSequence sequence = GeometryHelper.sequenceOf(sequence01);
		traverse(sequence, true, newUpper(x(6), sequence), events -> assertThat(events).containsOnly(
			"In(5, (6.0, 10.0))",
			"Out(6, (6.0, 8.0))",
			"In(7, (6.0, 6.0))",
			"Out(9, (6.0, 4.0))"
		));
	}

	@Test
	public void y_lower_01() {
		CoordinateSequence sequence = GeometryHelper.sequenceOf(sequence01);
		traverse(sequence, true, newLower(y(4), sequence), events -> assertThat(events).containsOnly(
			"In(13, (2.0, 4.0))",
			"Out(13, (1.0, 4.0))"
		));
	}

	@Test
	public void y_middle_01() {
		CoordinateSequence sequence = GeometryHelper.sequenceOf(sequence01);
		traverse(sequence, true, newMiddle(y(4), y(14), sequence), events -> assertThat(events).containsOnly(
			"In(1, (4.0, 14.0))",
			"Out(8, (8.0, 4.0))",
			"In(10, (4.0, 4.0))",
			"Out(12, (2.0, 4.0))",
			"In(14, (1.0, 4.0))",
			"Out(14, (1.0, 14.0))"
		));
	}

	@Test
	public void y_upper_01() {
		CoordinateSequence sequence = GeometryHelper.sequenceOf(sequence01);
		traverse(sequence, true, newUpper(y(14), sequence), events -> assertThat(events).containsOnly(
			"In(14, (1.0, 14.0))",
			"Out(16, (6.0, 14.0))"
		));
	}
}
