package javaargs.functional;

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
}
