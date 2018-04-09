package step1.share.util;

public class NoSuchYearlyDueException extends RuntimeException {
	//
	private static final long serialVersionUID = -118827455434526178L;
	
	public NoSuchYearlyDueException(String message) {
		super(message);
	}
}
