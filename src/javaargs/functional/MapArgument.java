package javaargs.functional;

import java.util.HashMap;

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
}
