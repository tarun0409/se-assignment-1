package javaargs.functional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Schema {
	
	private HashMap<Character, Constants.ArgumentType> elementIdToArgumentTypeMap = new HashMap<Character, Constants.ArgumentType>();
	
	public Schema(
		String schemaString) {
		List<String> schemaElements = getSchemaElements(schemaString, (string) -> Arrays.asList(string.split(",")));
		parseAndStoreSchemaElements(schemaElements);
	}
	
	private List<String> getSchemaElements(
		String schemaString,
		Function<String, List<String>> splitString) {
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
		return retrieveArgumentTypeFromMap.apply(typeString);
	}
	
	private void mapElementIdToArgumentType(
		Character elementId,
		Constants.ArgumentType argumentType,
		BiConsumer<Character, Constants.ArgumentType> mapArgumentToType) {
		mapArgumentToType.accept(elementId, argumentType);
	}

}
