package com.github.rthoth.slicer;

import com.insightfullogic.lambdabehave.JunitSuiteRunner;

import static com.github.rthoth.slicer.JTSHelper.createSequence;
import static com.insightfullogic.lambdabehave.Suite.describe;

import org.junit.runner.RunWith;
import org.locationtech.jts.geom.CoordinateSequence;


@RunWith(JunitSuiteRunner.class)
public class BackwardCoordinateSequenceSpec {
	{

		CoordinateSequence original = createSequence("0 0 , 1 0 , 1 1 , 2 0 , 2 1 , 3 0 , 3 2 , 0 2 , 0 0");

		describe("A backward window", it -> {
			it.uses(new CoordinateSequenceWindow.Backward(original, 2, 6, true))
				.toShow("%s.size == 1", (expect, sequence) -> {
					expect.that(sequence.size()).is(5);
				})
				.toShow("%s.get(0) == original.get(2)", (expect, sequence) -> {
					expect.that(sequence.getCoordinate(0)).is(original.getCoordinate(2));
				})
				.toShow("%s.get(4) == original.get(6)", (expect, sequence) -> {
					expect.that(sequence.getCoordinate(4)).is(original.getCoordinate(6));
				});

			it.uses(new CoordinateSequenceWindow.Backward(original, 7, 1, true))
				.toShow("%s.size == 7", (expect, sequence) -> {
					expect.that(sequence.size()).is(7);
				})
				.toShow("%s.get(0) == original.get(7)", (expect, sequence) -> {
					expect.that(sequence.getCoordinate(0)).is(original.getCoordinate(7));
				})
				.toShow("%s.get(6) == original.get(1)", (expect, sequence) -> {
					expect.that(sequence.getCoordinate(6)).is(original.getCoordinate(1));
				});
		});
	}
}
