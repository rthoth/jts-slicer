package com.github.rthoth.slicer;

import com.github.rthoth.slicer.CoordinateSequenceWindow.*;
import com.insightfullogic.lambdabehave.JunitSuiteRunner;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.CoordinateSequence;

import static com.github.rthoth.slicer.JTSHelper.*;
import static com.insightfullogic.lambdabehave.Suite.describe;

@RunWith(JunitSuiteRunner.class)
public class ForwardCoordinateSequenceSpec {
	{

		CoordinateSequence original = createSequence("0 0 , 1 0 , 1 1 , 2 0 , 2 1 , 3 0 , 3 2 , 0 2 , 0 0");

		describe("A forward window", it -> {
			it.uses(new Forward(original, 1, 7, true))
				.toShow("%s.size == 7", (expect, sequence) -> {
					expect.that(sequence.size()).is(7);
				})
				.toShow("%s.get(0) == original.get(1)", (expect, sequence) -> {
					expect.that(sequence.getCoordinate(0)).is(original.getCoordinate(1));
				})
				.toShow("%s.get(6) == original.get(7)", (expect, sequence) -> {
					expect.that(sequence.getCoordinate(6)).is(original.getCoordinate(7));
				});

			it.uses(new Forward(original, 6, 2, true))
				.toShow("%s.size == 5", (expect, sequence) -> {
					expect.that(sequence.size()).is(5);
				})
				.toShow("%s.get(0) == original.get(6)", (expect, sequence) -> {
					expect.that(sequence.getCoordinate(0)).is(original.getCoordinate(6));
				})
				.toShow("%s.get(4) == original.get(2)", (expect, sequence) -> {
					expect.that(sequence.getCoordinate(4)).is(original.getCoordinate(2));
				});
		});
	}
}
