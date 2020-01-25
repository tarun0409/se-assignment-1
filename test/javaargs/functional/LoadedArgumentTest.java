package javaargs.functional;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.junit.Test;

public class LoadedArgumentTest {

	@Test
	public void insertAndGetArgumentType() {
		LoadedArgument loadedArgument = new LoadedArgument();
		loadedArgument.setArgumentType(Constants.ArgumentType.INTEGER);
		assertEquals(Constants.ArgumentType.INTEGER, loadedArgument.getArgumentType());
	}
	
	@Test
	public void insertAndGetElementId() {
		LoadedArgument loadedArgument = new LoadedArgument();
		loadedArgument.setElementId('c');
		assertEquals('c', loadedArgument.getElementId());
	}
	
	@Test
	public void insertAndGetValueStrings() {
		LoadedArgument loadedArgument = new LoadedArgument();
		ArrayList<String> valueStrings = new ArrayList<String>();
		valueStrings.add("1");
		valueStrings.add("string1");
		valueStrings.add("3.14");
		loadedArgument.setValueStrings(valueStrings);
		assertSame(valueStrings, loadedArgument.getValueStrings());
	}
	
	@Test
	public void insertAndGetStoreFunction() {
		LoadedArgument loadedArgument = new LoadedArgument();
		Consumer<LoadedArgument> consumerObject = la -> {};
		loadedArgument.setStoreFunction(consumerObject);;
		assertSame(consumerObject, loadedArgument.getStoreFunction());
	}

}
