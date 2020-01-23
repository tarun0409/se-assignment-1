package javaargs.functional;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MapArgument
	implements Argument {
	
	private HashMap<String,String> value;

	public HashMap<String,String> getValue() {
		return value;
	}

	public void setValue(
			HashMap<String,String> value) {
		this.value = value;
	}
	
	public void setMapArgument( 
		HashMap<String,String> value,
		Consumer<HashMap<String,String>> setArgument) {
		setArgument.accept(value);
	}
					
	public HashMap<String,String> getMapArgument(
		Supplier<HashMap<String,String>> getValue) {
		return getValue.get();
	}
}
