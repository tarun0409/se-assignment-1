package javaargs.functional;

import java.util.function.Consumer;
import java.util.function.Supplier;

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
	
	public void setDoubleArgument( 
		double value,
		Consumer<Double> setArgument) {
		setArgument.accept(value);
	}
				
	public double getDoubleArgument(
		Supplier<Double> getValue) {
		return getValue.get();
	}
}
