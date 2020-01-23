package javaargs.functional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SerializedArguments {
	
	private HashMap<Character,Argument> elementIdToArgumentMap;
	private BiConsumer<Character, Argument> mapElementIdToArgument = (key, value) -> elementIdToArgumentMap.put(key, value);
	private Function<Character, Argument> retrieveArgumentValueFromElementId = key -> elementIdToArgumentMap.get(key);
	
	public SerializedArguments() {
		elementIdToArgumentMap = new HashMap<Character, Argument>();
	}
	
	public void setAndStoreBooleanArgument(
		char elementId,
		boolean booleanValue) {
		BooleanArgument booleanArgument = (BooleanArgument)getArgumentObject(() -> {return new BooleanArgument();});
		booleanArgument.setBooleanArgument(booleanValue, value -> booleanArgument.setValue(value));
		storeArgument(elementId, booleanArgument, mapElementIdToArgument);
	}
	
	public boolean getBooleanArgument(
		char elementId) {
		BooleanArgument booleanArgument = (BooleanArgument) retrieveArgumentObject(elementId, retrieveArgumentValueFromElementId);
		return booleanArgument.getBooleanArgument(() -> booleanArgument.getValue());
	}
	
	public void setAndStoreIntegerArgument(
		char elementId,
		int integerValue) {
		IntegerArgument integerArgument = (IntegerArgument)getArgumentObject(() -> {return new IntegerArgument();});
		integerArgument.setIntegerArgument(integerValue, value -> integerArgument.setValue(value));
		storeArgument(elementId, integerArgument, mapElementIdToArgument); 
	}
	
	public int getIntegerArgument(
		char elementId) {
		IntegerArgument integerArgument = (IntegerArgument) retrieveArgumentObject(elementId, retrieveArgumentValueFromElementId);
		return integerArgument.getIntegerArgument(() -> integerArgument.getValue());
	}
	
	public void setAndStoreDoubleArgument(
		char elementId,
		double doubleValue) {
		DoubleArgument doubleArgument = (DoubleArgument)getArgumentObject(() -> {return new DoubleArgument();});
		doubleArgument.setDoubleArgument(doubleValue, value -> doubleArgument.setValue(value));
		storeArgument(elementId, doubleArgument, mapElementIdToArgument); 
	}
		
	public double getDoubleArgument(
		char elementId) {
		DoubleArgument doubleArgument = (DoubleArgument) retrieveArgumentObject(elementId, retrieveArgumentValueFromElementId);
		return doubleArgument.getDoubleArgument(() -> doubleArgument.getValue());
	}
	
	public void setAndStoreStringArgument(
		char elementId,
		String stringValue) {
		StringArgument stringArgument = (StringArgument)getArgumentObject(() -> {return new StringArgument();});
		stringArgument.setStringArgument(stringValue, value -> stringArgument.setValue(value));
		storeArgument(elementId, stringArgument, mapElementIdToArgument); 
	}
			
	public String getStringArgument(
		char elementId) {
		StringArgument stringArgument = (StringArgument) retrieveArgumentObject(elementId, retrieveArgumentValueFromElementId);
		return stringArgument.getStringArgument(() -> stringArgument.getValue());
	}
	
	public void setAndStoreStringArrayArgument(
		char elementId,
		ArrayList<String> stringArrayValue) {
		StringArrayArgument stringArrayArgument = (StringArrayArgument)getArgumentObject(() -> {return new StringArrayArgument();});
		stringArrayArgument.setStringArrayArgument(stringArrayValue, value -> stringArrayArgument.setValue(value));
		storeArgument(elementId, stringArrayArgument, mapElementIdToArgument); 
	}
				
	public ArrayList<String> getStringArrayArgument(
		char elementId) {
		StringArrayArgument stringArrayArgument = (StringArrayArgument) retrieveArgumentObject(elementId, retrieveArgumentValueFromElementId);
		return stringArrayArgument.getStringArrayArgument(() -> stringArrayArgument.getValue());
	}
	
	public void setAndStoreMapArgument(
		char elementId,
		HashMap<String,String> mapValue) {
		MapArgument mapArgument = (MapArgument)getArgumentObject(() -> {return new StringArrayArgument();});
		mapArgument.setMapArgument(mapValue, value -> mapArgument.setValue(value));
		storeArgument(elementId, mapArgument, mapElementIdToArgument); 
	}
					
	public HashMap<String,String> getMapArgument(
		char elementId) {
		MapArgument mapArgument = (MapArgument) retrieveArgumentObject(elementId, retrieveArgumentValueFromElementId);
		return mapArgument.getMapArgument(() -> mapArgument.getValue());
	}
	
	private Argument getArgumentObject(
		Supplier<Argument> argumentObject) {
		return argumentObject.get();
	}
	
	private Argument retrieveArgumentObject(
		Character elementId,
		Function<Character, Argument> retrieveObject) {
		return retrieveObject.apply(elementId);
	}
	
	private void storeArgument(
		Character argumentId,
		Argument argument,
		BiConsumer<Character, Argument> store) {
		store.accept(argumentId, argument);
	}

}
