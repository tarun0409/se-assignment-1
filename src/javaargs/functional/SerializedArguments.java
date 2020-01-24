package javaargs.functional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SerializedArguments {
	
	private HashMap<Character,Argument> elementIdToArgumentMap = new HashMap<Character, Argument>();
	private HashMap<Constants.ArgumentType, Consumer<LoadedArgument>> argumentTypeToStoreFunctionMap = new HashMap<Constants.ArgumentType, Consumer<LoadedArgument>>();
	private Schema schema;
	private ArgumentList argumentList;
	
	public SerializedArguments(String schemaString, String argumentListString) {
		schema = new Schema(schemaString);
		argumentList = new ArgumentList(argumentListString);
		fillArgumentTypeToStoreFunctionMap();
		Set<Character> elementIds = schema.getAllElementIds();
		elementIds.stream().map(elementId -> setArgumentTypeAndArgumentValues(elementId))
						   .map(loadedArgument -> setValidateAndStoreFunction(loadedArgument))
						   .forEach(loadedFunction -> validateAndStore(loadedFunction, loadedFunction.getStoreFunction()));
	}
	
	Function<ArrayList<String>, HashMap<String,String>> convertArrayStringToMap = stringArray -> {
		HashMap<String,String> mapObject = new HashMap<String,String>();
		stringArray.forEach(entryString -> {
			String[] mapEntry = getMapEntries(entryString, string -> string.split(":"));
			mapObject.put(mapEntry[0], mapEntry[1]);
		});
		return mapObject;
	};
	
	private void validateAndStore(
		LoadedArgument loadedArgument,
		Consumer<LoadedArgument> validateAndStoreFunction) {
		validateAndStoreFunction.accept(loadedArgument);
	}
	
	Consumer<LoadedArgument> validateAndStoreBooleanArgument = loadedArgument -> {
		BooleanArgument booleanArgument = new BooleanArgument();
		booleanArgument.setValue(getBooleanArgument(loadedArgument.getValueStrings(), array -> array.isEmpty()));
		setArgument(loadedArgument.getElementId(), booleanArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> validateAndStoreIntegerArgument = loadedArgument -> {
		IntegerArgument integerArgument = new IntegerArgument();
		integerArgument.setValue(getIntegerArgument(loadedArgument.getValueStrings(), array -> Integer.parseInt(array.get(0))));
		setArgument(loadedArgument.getElementId(), integerArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> validateAndStoreDoubleArgument = loadedArgument -> {
		DoubleArgument doubleArgument = new DoubleArgument();
		doubleArgument.setValue(getDoubleArgument(loadedArgument.getValueStrings(), array -> Double.parseDouble(array.get(0))));
		setArgument(loadedArgument.getElementId(), doubleArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> validateAndStoreStringArgument = loadedArgument -> {
		StringArgument stringArgument = new StringArgument();
		stringArgument.setValue(getStringArgument(loadedArgument.getValueStrings(), array -> array.get(0)));
		setArgument(loadedArgument.getElementId(), stringArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> validateAndStoreStringArrayArgument = loadedArgument -> {
		StringArrayArgument stringArrayArgument = new StringArrayArgument();
		stringArrayArgument.setValue(loadedArgument.getValueStrings());
		setArgument(loadedArgument.getElementId(), stringArrayArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> validateAndStoreMapArgument = loadedArgument -> {
		MapArgument mapArrayArgument = new MapArgument();
		mapArrayArgument.setValue(getMapArgument(loadedArgument.getValueStrings(), convertArrayStringToMap));
		setArgument(loadedArgument.getElementId(), mapArrayArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	private LoadedArgument setValidateAndStoreFunction(
		LoadedArgument loadedArgument) {
		loadedArgument.setStoreFunction(argumentTypeToStoreFunctionMap.get(loadedArgument.getArgumentType()));
		return loadedArgument;
	}
	
	private boolean getBooleanArgument(
		ArrayList<String> valuesArray,
		Predicate<ArrayList<String>> isValuesArrayEmpty) {
		return isValuesArrayEmpty.negate().test(valuesArray);
	}
	
	private int getIntegerArgument(
		ArrayList<String> valuesArray,
		Function<ArrayList<String>, Integer> getInteger) {
		return getInteger.apply(valuesArray);
	}
	
	private double getDoubleArgument(
		ArrayList<String> valuesArray,
		Function<ArrayList<String>, Double> getDouble) {
		return getDouble.apply(valuesArray);
	}
	
	private String getStringArgument(
		ArrayList<String> valuesArray,
		Function<ArrayList<String>,String> getString) {
		return getString.apply(valuesArray);
	}
	
	private HashMap<String,String> getMapArgument(
		ArrayList<String> valuesArray,
		Function<ArrayList<String>, HashMap<String,String>> getMap) {
		return getMap.apply(valuesArray);
	}
	
	private String[] getMapEntries(
		String string,
		Function<String,String[]> splitStringWithColon) {
		return splitStringWithColon.apply(string);
	}
	
	private void setArgument(
		char elementId,
		Argument argument,
		BiConsumer<Character, Argument> mapElementIdToArgument) {
		mapElementIdToArgument.accept(elementId, argument);
	}
	
	private void fillArgumentTypeToStoreFunctionMap() {
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.BOOLEAN, validateAndStoreBooleanArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.INTEGER, validateAndStoreIntegerArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.DOUBLE, validateAndStoreDoubleArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.STRING, validateAndStoreStringArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.STRING_ARRAY, validateAndStoreStringArrayArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.MAP, validateAndStoreMapArgument);
	}
	
	private LoadedArgument setArgumentTypeAndArgumentValues(
		char elementId) {
		LoadedArgument loadedArgument = new LoadedArgument();
		loadedArgument.setElementId(elementId);
		loadedArgument.setArgumentType(schema.getArgumentType(elementId));
		loadedArgument.setValueStrings(argumentList.getArgumentValues(elementId));
		return loadedArgument;
	}
	
	public boolean getBooleanValue(char elementId) {
		BooleanArgument booleanArgument = (BooleanArgument)elementIdToArgumentMap.get(elementId);
		return booleanArgument.getValue();
	}
	
	public int getIntegerValue(char elementId) {
		IntegerArgument integerArgument = (IntegerArgument)elementIdToArgumentMap.get(elementId);
		return integerArgument.getValue();
	}
	
	public double getDoubleValue(char elementId) {
		DoubleArgument doubleArgument = (DoubleArgument)elementIdToArgumentMap.get(elementId);
		return doubleArgument.getValue();
	}
	
	public String getStringValue(char elementId) {
		StringArgument stringArgument = (StringArgument)elementIdToArgumentMap.get(elementId);
		return stringArgument.getValue();
	}
	
	public ArrayList<String> getStringArrayValue(char elementId) {
		StringArrayArgument stringArrayArgument = (StringArrayArgument)elementIdToArgumentMap.get(elementId);
		return stringArrayArgument.getValue();
	}
	
	public HashMap<String,String> getMapValue(char elementId) {
		MapArgument mapArgument = (MapArgument)elementIdToArgumentMap.get(elementId);
		return mapArgument.getValue();
	}
}
