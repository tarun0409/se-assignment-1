package javaargs.functional;

import static org.junit.Assert.*;

import org.junit.Test;

public class DoubleArgumentTest {

	@Test
	public void insertAndGetValue() {
		DoubleArgument doubleArgument = new DoubleArgument();
		doubleArgument.setValue(1);
		assertEquals(1, doubleArgument.getValue(),0);
	}

}
