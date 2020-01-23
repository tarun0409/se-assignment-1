package javaargs.functional;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BooleanArgument 
	implements Argument {
	
	private boolean value;

	public boolean getValue() {
		return value;
	}

	public void setValue(
		boolean value) {
		this.value = value;
	}
	
	public void setBooleanArgument( 
		boolean value,
		Consumer<Boolean> setArgument) {
		setArgument.accept(value);
	}
		
	public boolean getBooleanArgument(
		Supplier<Boolean> getValue) {
		return getValue.get();
	}
	
	
}
