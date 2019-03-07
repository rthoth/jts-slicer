package com.github.rthoth.slicer;

import com.insightfullogic.lambdabehave.JunitSuiteRunner;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

import static com.github.rthoth.slicer.JTSHelper.createSequence;
import static com.insightfullogic.lambdabehave.Suite.describe;

@RunWith(JunitSuiteRunner.class)
public class CoordinateSequenceWindowSpec {{

	CoordinateSequence original = createSequence(0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 0, 0);

	describe("A forwarded window", it -> {
		it
			.uses(new CoordinateSequenceWindow.Forward(original, 1, 4))
			.toShow("", (expect, value) -> {
				expect.that(value.getCoordinate(0)).is(new Coordinate(1, 1));
				expect.that(value.getCoordinate(4)).is(new Coordinate(5, 5));
			});
	});
}}
