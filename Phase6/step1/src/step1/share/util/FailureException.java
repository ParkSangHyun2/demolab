package step1.share.util;

public class FailureException extends RuntimeException {
	//
	private static final long serialVersionUID = -2928374655498766178L;
	
	public FailureException(String message) {
		super(message);
	}
}
