package om.gov.moh.eab.exception;

public class AuthenticationFailedException extends Exception {
	public AuthenticationFailedException() {

	}

	public AuthenticationFailedException(String message) {
		super(message);
	}
}
