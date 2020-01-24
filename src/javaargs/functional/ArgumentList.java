package javaargs.functional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ArgumentList {
	
	HashMap<Character,ArrayList<String>> elementToValueStringMap = new HashMap<Character,ArrayList<String>>();
	
	public ArgumentList(
		String argumentString) {
		String[] arguments = getArgumentList(argumentString, (string)->string.split(" "));
		parseArguments(arguments);
	}
	
	public HashMap<Character,ArrayList<String>> getArgumentValues(char elementId) {
		return getElementToValueStringMap(() -> elementToValueStringMap);
	}
	
	private HashMap<Character, ArrayList<String>> getElementToValueStringMap(
		Supplier<HashMap<Character, ArrayList<String>>> returnMap) {
		return returnMap.get();
	}
	
	private void parseArguments(
		String[] arguments) {
		iterateAndParseArguments.accept(arguments, 0);
	}
	
	BiConsumer<String[], Integer> iterateAndParseArguments = (stringArray, index) -> {
		if(isIndexOutOfRange(stringArray, index, (array,i) -> i >= array.length))
			return;
		char elementId = getElementId(stringArray[index], (string) -> string.length()>=2, (string) -> string.charAt(1));
		if(isElementIdAndNotBooleanElement(stringArray, index))
			insertElementIdAndArgumentValue(elementId, stringArray[index+1],(key) -> elementToValueStringMap.containsKey(key));
		else if(isElementId(stringArray[index], (string) -> string.startsWith("-"))) 
			insertElementIdAndArgumentValue(elementId,"true",(key) -> elementToValueStringMap.containsKey(key));
		this.iterateAndParseArguments.accept(stringArray, index+1);
	};
	
	private void insertElementIdAndArgumentValue(
		char elementId,
		String argumentValue,
		Predicate<Character> isElementIdInMap) {
		if(isElementIdInMap.negate().test(elementId))
			insertKeyInMap(elementId, (key) -> elementToValueStringMap.put(key, new ArrayList<String>()));
		mapArgumentValueToElementId(elementId, argumentValue, (key,value) -> elementToValueStringMap.get(key).add(value));
	}
	
	private String[] getArgumentList(
		String argumentString,
		Function<String,String[]> splitString) {
		return splitString.apply(argumentString);
	}
	
	private boolean isElementId(
		String argument,
		Predicate<String> beginsWithHyphen) {
		return beginsWithHyphen.test(argument);
	}
	
	private boolean isIndexOutOfRange(
		String[] array,
		int index,
		BiPredicate<String[], Integer> indexLessThanArraySize) {
		return indexLessThanArraySize.test(array, index);
	}
	
	private boolean isElementIdAndNotBooleanElement(
		String[] stringArray,
		int index) {
		return isElementId(stringArray[index], (string) -> string.startsWith("-"))
				&& isIndexOutOfRange(stringArray, index+1, (array,i) -> i < array.length)
				&& !isElementId(stringArray[index+1], (string) -> string.startsWith("-")); 
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
