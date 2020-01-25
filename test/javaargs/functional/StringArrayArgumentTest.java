package javaargs.functional;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class StringArrayArgumentTest {

	@Test
	public void insertAndGetStringArrayValue() {
		StringArrayArgument stringArrayArgument = new StringArrayArgument();
		ArrayList<String> stringArray = new ArrayList<String>();
		stringArray.add("1");
		stringArray.add("string1");
		stringArray.add("3.14");
		stringArrayArgument.setValue(stringArray);
		assertSame(stringArray, stringArrayArgument.getValue());
	}

}
