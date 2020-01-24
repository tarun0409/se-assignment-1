package javaargs.functional;

import java.util.ArrayList;

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
}
