package javaargs.functional;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerArgumentTest {

	@Test
	public void insertAndGetValue() {
		IntegerArgument integerArgument = new IntegerArgument();
		integerArgument.setValue(1);
		assertEquals(1, integerArgument.getValue());
	}

}
