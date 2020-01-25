package javaargs.functional;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SchemaTest {

	@Test
	public void createSchemaObject() {
		try {
			@SuppressWarnings("unused")
			Schema schema = new Schema("f,s*,n#,a##,p[*]");
		}
		catch(InvalidSchema is) {
			fail("Valid schema string was passed. Exception should not have been thrown");
		}
	}
	
	@Test
	public void emptySchemaString() {
		try {
			@SuppressWarnings("unused")
			Schema schema = new Schema("");
			fail("Empty string was passed. Exception should have been thrown");
		}
		catch(InvalidSchema is) {
			//Success
		}
	}
	
	@Test
	public void invalidSchemaString() {
		try {
			@SuppressWarnings("unused")
			Schema schema = new Schema("f^,s*,n#,a##,p[*]");
			fail("Invalid symbol was passed. Exception should have been thrown");
		}
		catch(InvalidSchema is) {
			//Success
		}
	}
	
	@Test
	public void getAllElementIds() {
		Schema schema = new Schema("f,s*,n#,a##,p[*]");
		Set<Character> elementIds = new HashSet<Character>();
		elementIds.add('f');
		elementIds.add('s');
		elementIds.add('n');
		elementIds.add('a');
		elementIds.add('p');
		assertEquals(elementIds, schema.getAllElementIds());
	}
	
	@Test
	public void getBooleanArgumentType() {
		Schema schema = new Schema("f");
		assertEquals(Constants.ArgumentType.BOOLEAN, schema.getArgumentType('f'));
	}
	
	@Test
	public void getIntegerArgumentType() {
		Schema schema = new Schema("n#");
		assertEquals(Constants.ArgumentType.INTEGER, schema.getArgumentType('n'));
	}
	
	@Test
	public void getDoubleArgumentType() {
		Schema schema = new Schema("a##");
		assertEquals(Constants.ArgumentType.DOUBLE, schema.getArgumentType('a'));
	}
	
	@Test
	public void getStringArgumentType() {
		Schema schema = new Schema("s*");
		assertEquals(Constants.ArgumentType.STRING, schema.getArgumentType('s'));
	}
	
	@Test
	public void getStringArrayArgumentType() {
		Schema schema = new Schema("p[*]");
		assertEquals(Constants.ArgumentType.STRING_ARRAY, schema.getArgumentType('p'));
	}
	
	@Test
	public void getMapArgumentType() {
		Schema schema = new Schema("q&");
		assertEquals(Constants.ArgumentType.MAP, schema.getArgumentType('q'));
	}
}
