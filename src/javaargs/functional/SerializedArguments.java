package javaargs.functional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SerializedArguments {
	
	private HashMap<Character,Argument> elementIdToArgumentMap = new HashMap<Character, Argument>();
	private HashMap<Constants.ArgumentType, Consumer<LoadedArgument>> argumentTypeToStoreFunctionMap = new HashMap<Constants.ArgumentType, Consumer<LoadedArgument>>();
	private Schema schema;
	private ArgumentList argumentList;
	private Set<Character> elementIds;
	
	BiFunction<String[],HashMap<String,String>,HashMap<String,String>> insertMapEntry = (mapEntry, mapObject) -> {
		mapObject.put(mapEntry[0], mapEntry[1]);
		return mapObject;
	};
	
	Function<ArrayList<String>, HashMap<String,String>> convertArrayStringToMap = stringArray -> {
		HashMap<String,String> mapObject = new HashMap<String,String>();
		stringArray.forEach(entryString -> insertMapEntryIntoMapObject(getMapEntries(entryString, string -> string.split(":")), mapObject, insertMapEntry));
		return mapObject;
	};
	
	Consumer<LoadedArgument> setAndStoreBooleanArgument = loadedArgument -> {
		BooleanArgument booleanArgument = new BooleanArgument();
		booleanArgument.setValue(getBooleanArgument(loadedArgument.getValueStrings(), array -> array.isEmpty()));
		setArgument(loadedArgument.getElementId(), booleanArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> setAndStoreIntegerArgument = loadedArgument -> {
		IntegerArgument integerArgument = new IntegerArgument();
		integerArgument.setValue(getIntegerArgument(loadedArgument.getValueStrings(), parseIntegerSafely(array -> Integer.parseInt(array.get(0)))));
		setArgument(loadedArgument.getElementId(), integerArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> setAndStoreDoubleArgument = loadedArgument -> {
		DoubleArgument doubleArgument = new DoubleArgument();
		doubleArgument.setValue(getDoubleArgument(loadedArgument.getValueStrings(), parseDoubleSafely(array -> Double.parseDouble(array.get(0)))));
		setArgument(loadedArgument.getElementId(), doubleArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> setAndStoreStringArgument = loadedArgument -> {
		StringArgument stringArgument = new StringArgument();
		stringArgument.setValue(getStringArgument(loadedArgument.getValueStrings(), extractStringSafely(array -> array.get(0))));
		setArgument(loadedArgument.getElementId(), stringArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> setAndStoreStringArrayArgument = loadedArgument -> {
		StringArrayArgument stringArrayArgument = new StringArrayArgument();
		stringArrayArgument.setValue(loadedArgument.getValueStrings());
		setArgument(loadedArgument.getElementId(), stringArrayArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	Consumer<LoadedArgument> setAndStoreMapArgument = loadedArgument -> {
		MapArgument mapArrayArgument = new MapArgument();
		mapArrayArgument.setValue(getMapArgument(loadedArgument.getValueStrings(), convertArrayStringToMap));
		setArgument(loadedArgument.getElementId(), mapArrayArgument, (key,value) -> elementIdToArgumentMap.put(key, value));
	};
	
	public SerializedArguments(
		String schemaString, 
		String argumentListString) {
		getLoadedArgumentAttributes(schemaString, argumentListString);
		fillArgumentTypeToStoreFunctionMap();
		mapElementIdToArgumentObject();
	}
	
	private HashMap<String,String> insertMapEntryIntoMapObject(
		String[] mapEntry,
		HashMap<String,String> mapObject,
		BiFunction<String[], HashMap<String,String>, HashMap<String,String>> insertIntoMap) {
		return insertIntoMap.apply(mapEntry, mapObject);
	}
	
	private void mapElementIdToArgumentObject() {
		elementIds.stream().map(elementId -> setArgumentTypeAndArgumentValues(elementId))
		   .map(loadedArgument -> updateSetAndStoreFunction(loadedArgument))
		   .forEach(loadedArgument -> validateAndStoreArgument(loadedArgument, loadedArgument.getStoreFunction()));
	}
	
	private void getLoadedArgumentAttributes(String schemaString, String argumentListString) {
		schema = new Schema(schemaString);
		argumentList = new ArgumentList(argumentListString);
		elementIds = getElementIds(() -> schema.getAllElementIds());
	}
	
	private Set<Character> getElementIds(
		Supplier<Set<Character>> getElementIdFromSchemaObject) {
		return getElementIdFromSchemaObject.get();
	}
	
	private void validateAndStoreArgument(
		LoadedArgument loadedArgument,
		Consumer<LoadedArgument> validateAndStoreFunction) {
		validateAndStoreFunction.accept(loadedArgument);
	}
	
	private LoadedArgument updateSetAndStoreFunction(
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
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.BOOLEAN, setAndStoreBooleanArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.INTEGER, setAndStoreIntegerArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.DOUBLE, setAndStoreDoubleArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.STRING, setAndStoreStringArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.STRING_ARRAY, setAndStoreStringArrayArgument);
		argumentTypeToStoreFunctionMap.put(Constants.ArgumentType.MAP, setAndStoreMapArgument);
	}
	
	private LoadedArgument setArgumentTypeAndArgumentValues(
		char elementId) {
		LoadedArgument loadedArgument = new LoadedArgument();
		loadedArgument.setElementId(elementId);
		loadedArgument.setArgumentType(schema.getArgumentType(elementId));
		loadedArgument.setValueStrings(argumentList.getArgumentValues(elementId));
		return loadedArgument;
	}
	
	private boolean argumentIsNotAvailable(
		char elementId) {
		return !elementIdToArgumentMap.containsKey(elementId);
	}
	
	private Function<ArrayList<String>, Integer> parseIntegerSafely(
		Function<ArrayList<String>, Integer> parseInteger) {
		try {
			return array -> parseInteger.apply(array);
		}
		catch(NumberFormatException ex) {
			throw new ArgumentTypeMismatch();
		}
		catch(IndexOutOfBoundsException ex) {
			throw new InvalidArgumentList();
		}
	}
	
	private Function<ArrayList<String>, Double> parseDoubleSafely(
		Function<ArrayList<String>, Double> parseDouble) {
		try {
			return array -> parseDouble.apply(array);
		}
		catch(NumberFormatException ex) {
			throw new ArgumentTypeMismatch();
		}
		catch(IndexOutOfBoundsException ex) {
			throw new InvalidArgumentList();
		}
	}
	private Function<ArrayList<String>, String> extractStringSafely(
		Function<ArrayList<String>, String> extractString) {
		try {
			return array -> extractString.apply(array);
		}
		catch(IndexOutOfBoundsException ex) {
			throw new InvalidArgumentList();
		}
	}
	
	
	public boolean getBooleanValue(
		char elementId) {
		if(argumentIsNotAvailable(elementId)) throw new ArgumentUnavailable();
		return ((BooleanArgument)elementIdToArgumentMap.get(elementId)).getValue();
	}
	
	public int getIntegerValue(
		char elementId) {
		if(argumentIsNotAvailable(elementId)) throw new ArgumentUnavailable();
		return ((IntegerArgument)elementIdToArgumentMap.get(elementId)).getValue();
	}
	
	public double getDoubleValue(
		char elementId) {
		if(argumentIsNotAvailable(elementId)) throw new ArgumentUnavailable();
		return ((DoubleArgument)elementIdToArgumentMap.get(elementId)).getValue();
	}
	
	public String getStringValue(
		char elementId) {
		if(argumentIsNotAvailable(elementId)) throw new ArgumentUnavailable();
		return ((StringArgument)elementIdToArgumentMap.get(elementId)).getValue();
	}
	
	public ArrayList<String> getStringArrayValue(
		char elementId) {
		if(argumentIsNotAvailable(elementId)) throw new ArgumentUnavailable();
		return ((StringArrayArgument)elementIdToArgumentMap.get(elementId)).getValue();
	}
	
	public HashMap<String,String> getMapValue(
		char elementId) {
		if(argumentIsNotAvailable(elementId)) throw new ArgumentUnavailable();
		return ((MapArgument)elementIdToArgumentMap.get(elementId)).getValue();
	}
}
