package javaargs.functional;

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
}
