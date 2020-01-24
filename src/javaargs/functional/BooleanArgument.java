package javaargs.functional;

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
	
}
