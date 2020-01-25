package javaargs.functional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Schema {
	
	private HashMap<Character, Constants.ArgumentType> elementIdToArgumentTypeMap = new HashMap<Character, Constants.ArgumentType>();
	
	public Schema(
		String schemaString) {
		List<String> schemaElements = getSchemaElements(schemaString, (string) -> Arrays.asList(string.split(",")));
		schemaElements = schemaElements.stream().map(string -> string.trim()).filter(string -> !string.isEmpty()).collect(Collectors.toList());
		parseAndStoreSchemaElements(schemaElements);
	}
	
	public Set<Character> getAllElementIds() {
		return getKeysFromElementIdToArgumentTypeMap(()->elementIdToArgumentTypeMap.keySet());
	}
	
	private Set<Character> getKeysFromElementIdToArgumentTypeMap(
		Supplier<Set<Character>> getKeys) {
		return getKeys.get();
	}
	
	private Constants.ArgumentType getArgumentTypeFromElementId(
		char elementId,
		Function<Character,Constants.ArgumentType> retrieveArgumentType) {
		return retrieveArgumentType.apply(elementId);
	}
	
	private boolean schemaStringIsEmpty(
		String schemaString) {
		return schemaString.isEmpty();
	}
	
	private List<String> getSchemaElements(
		String schemaString,
		Function<String, List<String>> splitString) {
		if(schemaStringIsEmpty(schemaString)) throw new InvalidSchema();
		return splitString.apply(schemaString);
	}
	
	private void parseAndStoreSchemaElements(
		List<String> schemaElements) {
		Consumer<String> parseSchemaElements = (schemaElement) -> {
			char elementId = getElementId(schemaElement, string -> string.charAt(0));
			String typeString = getArgumentTypeString(schemaElement, string -> string.substring(1));
			Constants.ArgumentType argumentType = getArgumentTypeFromTypeString(typeString, key -> Constants.typeStringToArgumentTypeMap.get(key));
			mapElementIdToArgumentType(elementId, argumentType, (key, value) -> elementIdToArgumentTypeMap.put(key, value));
		};
		schemaElements.forEach(parseSchemaElements);
	}
	
	private char getElementId(
		String schemaElement,
		Function<String, Character> getFirstCharacter) {
		return getFirstCharacter.apply(schemaElement);
	}
	
	private String getArgumentTypeString(
		String schemaElement,
		Function<String, String> getTrailingString) {
		return getTrailingString.apply(schemaElement);
	}
	
	private Constants.ArgumentType getArgumentTypeFromTypeString(
		String typeString,
		Function<String, Constants.ArgumentType> retrieveArgumentTypeFromMap) {
		Constants.ArgumentType argumentType = retrieveArgumentTypeFromMap.apply(typeString);
		if(argumentType == null) throw new InvalidSchema();
		return argumentType;
	}
	
	private void mapElementIdToArgumentType(
		Character elementId,
		Constants.ArgumentType argumentType,
		BiConsumer<Character, Constants.ArgumentType> mapArgumentToType) {
		mapArgumentToType.accept(elementId, argumentType);
	}
	
	private boolean argumentIsNotAvailable(
		char elementId) {
		return !elementIdToArgumentTypeMap.containsKey(elementId);
	}
	
	public Constants.ArgumentType getArgumentType(
		char elementId) {
		if(argumentIsNotAvailable(elementId)) throw new InvalidSchema();
		return getArgumentTypeFromElementId(elementId, (key) -> elementIdToArgumentTypeMap.get(key));
	}

}
