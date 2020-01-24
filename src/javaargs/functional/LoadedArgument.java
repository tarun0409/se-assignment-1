package javaargs.functional;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LoadedArgument {
	private char elementId;
	private Constants.ArgumentType argumentType;
	private ArrayList<String> valueStrings;
	private Consumer<LoadedArgument> storeFunction;
	public Constants.ArgumentType getArgumentType() {
		return argumentType;
	}
	public void setArgumentType(Constants.ArgumentType argumentType) {
		this.argumentType = argumentType;
	}
	public ArrayList<String> getValueStrings() {
		return valueStrings;
	}
	public void setValueStrings(ArrayList<String> valueStrings) {
		this.valueStrings = valueStrings;
	}
	public void setElementId(char elementId) {
		this.elementId = elementId;
	}
	public char getElementId() {
		return this.elementId;
	}
	public Consumer<LoadedArgument> getStoreFunction() {
		return storeFunction;
	}
	public void setStoreFunction(Consumer<LoadedArgument> storeFunction) {
		this.storeFunction = storeFunction;
	}
}
