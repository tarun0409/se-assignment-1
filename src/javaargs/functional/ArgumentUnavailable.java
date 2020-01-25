package javaargs.functional;

public class ArgumentUnavailable 
	extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ArgumentUnavailable() {
		super();
	}
	
	public ArgumentUnavailable(
		String message) {
		super(message);
	}

}
