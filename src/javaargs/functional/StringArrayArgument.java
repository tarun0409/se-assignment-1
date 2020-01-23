package javaargs.functional;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StringArrayArgument
	implements Argument {
	
	private ArrayList<String> value;

	public ArrayList<String> getValue() {
		return value;
	}

	public void setValue(
		ArrayList<String> value) {
		this.value = value;
	}
	
	public void setStringArrayArgument( 
		ArrayList<String> value,
		Consumer<ArrayList<String>> setArgument) {
		setArgument.accept(value);
	}
					
	public ArrayList<String> getStringArrayArgument(
		Supplier<ArrayList<String>> getValue) {
		return getValue.get();
	}
}
