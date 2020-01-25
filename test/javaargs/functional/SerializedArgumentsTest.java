package javaargs.functional;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class SerializedArgumentsTest {

	@Test
	public void createSerializedArgumentsObject() {
		try {
			@SuppressWarnings("unused")
			SerializedArguments sa = new SerializedArguments("f,s*,n#,a##,p[*]", 
					"-f -s Bob -n 1 -a 3.2 -p e1 -p e2 -p e3");
		}
		catch(InvalidSchema is) {
			fail("Valid schema was passed should not have thrown exception");
		}
		catch(InvalidArgumentList ial) {
			fail("Valid argument list was passed. Should not have thrown exception");
		}
	}
	
	@Test
	public void getBooleanArgumentAsTrue() {
		SerializedArguments sa = new SerializedArguments("f","-f");
		assertEquals(true, sa.getBooleanValue('f'));
	}
	
	@Test
	public void getBooleanArgumentAsFalse() {
		SerializedArguments sa = new SerializedArguments("f,s*","-s Bob");
		assertEquals(false, sa.getBooleanValue('f'));
	}
	
	@Test
	public void unavailableBooleanValue() {
		SerializedArguments sa = new SerializedArguments("s*","-s Bob");
		try {
			sa.getBooleanValue('f');
			fail("No boolean value was passed. Should have thrown an exception");
		}
		catch(ArgumentUnavailable au) {
			//success
		}
	}
	
	@Test
	public void getIntegerArgument() {
		SerializedArguments sa = new SerializedArguments("n#","-n 1");
		assertEquals(1, sa.getIntegerValue('n'));
	}
	
	@Test
	public void unavailableIntegerValue() {
		SerializedArguments sa = new SerializedArguments("s*","-s Bob");
		try {
			sa.getIntegerValue('n');
			fail("No integer value was passed. Should have thrown an exception");
		}
		catch(ArgumentUnavailable au) {
			//success
		}
	}
	
	@Test
	public void getDoubleArgument() {
		SerializedArguments sa = new SerializedArguments("a##","-a 3.2");
		assertEquals(3.2, sa.getDoubleValue('a'),0);
	}
	
	@Test
	public void unavailableDoubleValue() {
		SerializedArguments sa = new SerializedArguments("s*","-s Bob");
		try {
			sa.getIntegerValue('a');
			fail("No double value was passed. Should have thrown an exception");
		}
		catch(ArgumentUnavailable au) {
			//success
		}
	}
	
	@Test
	public void getStringArgument() {
		SerializedArguments sa = new SerializedArguments("s*","-s Bob");
		assertEquals("Bob", sa.getStringValue('s'));
	}
	
	@Test
	public void unavailableStringValue() {
		SerializedArguments sa = new SerializedArguments("a##","-a 3.2");
		try {
			sa.getStringValue('s');
			fail("No string value was passed. Should have thrown an exception");
		}
		catch(ArgumentUnavailable au) {
			//success
		}
	}
	
	@Test
	public void getStringArrayArgument() {
		SerializedArguments sa = new SerializedArguments("p[*]","-p e1 -p e2 -p e3");
		ArrayList<String> stringArray = new ArrayList<String>();
		stringArray.add("e1");
		stringArray.add("e2");
		stringArray.add("e3");
		assertEquals(stringArray, sa.getStringArrayValue('p'));
	}
	
	@Test
	public void unavailableStringArrayValue() {
		SerializedArguments sa = new SerializedArguments("a##","-a 3.2");
		try {
			sa.getStringArrayValue('p');
			fail("No string array value was passed. Should have thrown an exception");
		}
		catch(ArgumentUnavailable au) {
			//success
		}
	}
	
	@Test
	public void getMapArgument() {
		SerializedArguments sa = new SerializedArguments("q&","-q a:b -q c:d -q e:f");
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("a", "b");
		map.put("c", "d");
		map.put("e", "f");
		assertEquals(map, sa.getMapValue('q'));
	}
	
	@Test
	public void unavailableMapValue() {
		SerializedArguments sa = new SerializedArguments("a##","-a 3.2");
		try {
			sa.getMapValue('q');
			fail("No string array value was passed. Should have thrown an exception");
		}
		catch(ArgumentUnavailable au) {
			//success
		}
	}

}
