package javaargs.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ArgumentList {
	
	private HashMap<Character,ArrayList<String>> elementToValueStringMap = new HashMap<Character,ArrayList<String>>();
	
	public ArgumentList(
		String argumentString) {
		List<String> arguments = getArgumentList(argumentString, (string)->Arrays.asList(string.split(" ")));
		arguments = arguments.stream().map(string -> string.trim()).filter(string -> !string.isEmpty()).collect(Collectors.toList());
		parseArguments(arguments);
	}
	
	public Set<Character> getAllElementIds() {
		return getKeysFromElementIdToValueStringMap(()->elementToValueStringMap.keySet());
	}
	
	private Set<Character> getKeysFromElementIdToValueStringMap(
		Supplier<Set<Character>> getKeys) {
		return getKeys.get();
	}
	
	public ArrayList<String> getArgumentValues(char elementId) {
		return getValueArrayFromMap(elementId, key -> elementToValueStringMap.containsKey(key) ? elementToValueStringMap.get(key) : new ArrayList<String>());
	}
	
	private ArrayList<String> getValueArrayFromMap(
		char elementId,
		Function<Character, ArrayList<String>> getValueArray) {
		return getValueArray.apply(elementId);
	}
	
	
	private void parseArguments(
		List<String> arguments) {
		iterateAndParseArguments.accept(arguments, 0);
	}
	
	private BiConsumer<List<String>, Integer> iterateAndParseArguments = (stringArray, index) -> {
		if(isIndexOutOfRange(stringArray, index, (array,i) -> i >= array.size())) return;
		checkArgumentTypeAndInsertValue(stringArray, index, (array,i) -> isElementIdAndNotBooleanElement(array, i), (string1) -> isElementId(string1, (string2) -> string2.startsWith("-")));
		this.iterateAndParseArguments.accept(stringArray, index+1);
	};
	
	private void checkArgumentTypeAndInsertValue(
		List<String> stringArray,
		int index,
		BiPredicate<List<String>, Integer> isElementIdAndNotBooleanElement,
		Predicate<String> isBooleanElement) {
		char elementId = getElementId(stringArray.get(index), (string) -> string.length()>=2, (string) -> string.charAt(1));
		if(isElementIdAndNotBooleanElement.test(stringArray, index))
			insertElementIdAndArgumentValue(elementId, stringArray.get(index+1),(key) -> elementToValueStringMap.containsKey(key));
		else if(isBooleanElement.test(stringArray.get(index)))
			insertElementIdAndArgumentValue(elementId,"true",(key) -> elementToValueStringMap.containsKey(key));
	}
	
	private void insertElementIdAndArgumentValue(
		char elementId,
		String argumentValue,
		Predicate<Character> isElementIdInMap) {
		if(isElementIdInMap.negate().test(elementId))
			insertKeyInMap(elementId, (key) -> elementToValueStringMap.put(key, new ArrayList<String>()));
		mapArgumentValueToElementId(elementId, argumentValue, (key,value) -> elementToValueStringMap.get(key).add(value));
	}
	
	private List<String> getArgumentList(
		String argumentString,
		Function<String,List<String>> splitString) {
		return splitString.apply(argumentString);
	}
	
	private boolean isElementId(
		String argument,
		Predicate<String> beginsWithHyphen) {
		return beginsWithHyphen.test(argument);
	}
	
	private boolean isIndexOutOfRange(
		List<String> array,
		int index,
		BiPredicate<List<String>, Integer> indexLessThanArraySize) {
		return indexLessThanArraySize.test(array, index);
	}
	
	private boolean isElementIdAndNotBooleanElement(
		List<String> stringArray,
		int index) {
		return isElementId(stringArray.get(index), (string) -> string.startsWith("-"))
				&& isIndexOutOfRange(stringArray, index+1, (array,i) -> i < array.size())
				&& !isElementId(stringArray.get(index+1), (string) -> string.startsWith("-")); 
	}
	
	private void insertKeyInMap(
		Character key,
		Consumer<Character> initializeKey) {
		initializeKey.accept(key);
	}
	
	private char getElementId(
		String argument,
		Predicate<String> isStringLengthAtleastTwo,
		Function<String,Character> getSecondCharacter) {
		return isStringLengthAtleastTwo.test(argument) ? getSecondCharacter.apply(argument) : '\0';
	}
	
	private void mapArgumentValueToElementId(
		Character elementId,
		String value,
		BiConsumer<Character, String> addToValueArray) {
		addToValueArray.accept(elementId, value);
	}
	

}
