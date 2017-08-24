package no.smalltypes.norwegian.bank;

public class InvalidKontonummerFormatException extends InvalidKontonummerException {
	private static final long serialVersionUID = 1L;

	public InvalidKontonummerFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidKontonummerFormatException(String message) {
		super(message);
	}
}
