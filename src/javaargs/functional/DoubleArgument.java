package javaargs.functional;

public class DoubleArgument
	implements Argument {
	
	private double value;

	public double getValue() {
		return value;
	}

	public void setValue(
		double value) {
		this.value = value;
	}
}
