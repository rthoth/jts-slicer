package com.github.rthoth.slicer;

import org.junit.jupiter.api.Test;
import org.pcollections.PVector;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTest extends AbstractTest {

	@Test
	public void simpleTest() {
		PVector<String> vector = Util.toVector(Stream.of("1", "2", "3"));
		assertThat(vector).containsExactly("1", "2", "3");
	}
}
