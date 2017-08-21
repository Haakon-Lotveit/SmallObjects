package no.smalltypes.telephone;

public class IllegalPhoneNumberException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public IllegalPhoneNumberException(String message) { super(message); }
	public IllegalPhoneNumberException(Throwable cause) { super(cause); }
	public IllegalPhoneNumberException(String message, Throwable cause) { super(message, cause); }
}
