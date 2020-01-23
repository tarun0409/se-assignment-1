package javaargs.functional;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StringArgument 
	implements Argument {
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(
		String value) {
		this.value = value;
	}
	
	public void setStringArgument( 
		String value,
		Consumer<String> setArgument) {
		setArgument.accept(value);
	}
				
	public String getStringArgument(
		Supplier<String> getValue) {
		return getValue.get();
	}
}
