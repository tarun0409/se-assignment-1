package javaargs.functional;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringArgumentTest {

	@Test
	public void insertAndGetStringValue() {
		StringArgument stringArgument = new StringArgument();
		String value = "Hello";
		stringArgument.setValue(value);
		assertSame(value, stringArgument.getValue());
	}

}
