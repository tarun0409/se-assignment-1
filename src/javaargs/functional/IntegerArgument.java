package javaargs.functional;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntegerArgument 
	implements Argument {
	
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(
		int value) {
		this.value = value;
	}
	
	public void setIntegerArgument( 
		int value,
		Consumer<Integer> setArgument) {
		setArgument.accept(value);
	}
			
	public int getIntegerArgument(
		Supplier<Integer> getValue) {
		return getValue.get();
	}
}
