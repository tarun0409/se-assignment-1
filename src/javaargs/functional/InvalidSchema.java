package javaargs.functional;

public class InvalidSchema 
	extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public InvalidSchema() {
		super();
	}
	
	public InvalidSchema(
		String message) {
		super(message);
	}

}
