package no.smalltypes.norwegian.bank;

public class InvalidKontonummerChecksumException extends InvalidKontonummerException {
	private static final long serialVersionUID = 1L;

	public InvalidKontonummerChecksumException (String message) {
		super(message);
	}
	
	public InvalidKontonummerChecksumException (String message, Throwable cause) {
		super(message, cause);
	}
}
