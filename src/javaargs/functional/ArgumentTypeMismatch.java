package javaargs.functional;

public class ArgumentTypeMismatch extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ArgumentTypeMismatch() {
		super();
	}
	
	public ArgumentTypeMismatch(
		String message) {
		super(message);
	}

}
