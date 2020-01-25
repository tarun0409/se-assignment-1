package javaargs.functional;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArgumentListTest {

	@Test
	public void createArgumentListObject() {
		try {
			@SuppressWarnings("unused")
			ArgumentList argumentList = new ArgumentList("-f -s Bob -n 1 -a 3.2 -p e1 -p e2 -p e3");
		}
		catch(InvalidArgumentList ial) {
			fail("Valid argument list was passed. Exception should not have been thrown");
		}
	}
	
	@Test
	public void emptyArgumentListString() {
		try {
			@SuppressWarnings("unused")
			ArgumentList argumentList = new ArgumentList("");
			fail("Empty argument list string was passed. Exception should have been thrown");
		}
		catch(InvalidArgumentList ial) {
			//Success
		}
	}
	
	@Test
	public void setBooleanArgument() {
		ArgumentList argumentList = new ArgumentList("-f");
		assertEquals("true", argumentList.getArgumentValues('f').get(0));
	}
	
	@Test
	public void setStringArgument() {
		ArgumentList argumentList = new ArgumentList("-s Bob");
		assertEquals("Bob", argumentList.getArgumentValues('s').get(0));
	}
	
	@Test
	public void setIntegerArgument() {
		ArgumentList argumentList = new ArgumentList("-n 1");
		assertEquals("1", argumentList.getArgumentValues('n').get(0));
	}
	
	@Test
	public void setDoubleArgument() {
		ArgumentList argumentList = new ArgumentList("-a 3.2");
		assertEquals("3.2", argumentList.getArgumentValues('a').get(0));
	}
	
	@Test
	public void setStringArrayArgument() {
		ArgumentList argumentList = new ArgumentList("-p e1 -p e2 -p e3");
		assertEquals("e1", argumentList.getArgumentValues('p').get(0));
		assertEquals("e2", argumentList.getArgumentValues('p').get(1));
		assertEquals("e3", argumentList.getArgumentValues('p').get(2));
	}
	
	@Test
	public void setMapArgument() {
		ArgumentList argumentList = new ArgumentList("-q a:b -q c:d -q e:f");
		assertEquals("a:b", argumentList.getArgumentValues('q').get(0));
		assertEquals("c:d", argumentList.getArgumentValues('q').get(1));
		assertEquals("e:f", argumentList.getArgumentValues('q').get(2));
	}
	
	@Test
	public void setMultipleArguments() {
		ArgumentList argumentList = new ArgumentList("-f -s Bob -n 1 -a 3.2 -p e1 -p e2 -p e3 -q a:b -q c:d -q e:f");
		assertEquals("true", argumentList.getArgumentValues('f').get(0));
		assertEquals("Bob", argumentList.getArgumentValues('s').get(0));
		assertEquals("1", argumentList.getArgumentValues('n').get(0));
		assertEquals("3.2", argumentList.getArgumentValues('a').get(0));
		assertEquals("e1", argumentList.getArgumentValues('p').get(0));
		assertEquals("a:b", argumentList.getArgumentValues('q').get(0));
	}

}
