package no.smalltypes.telephone.norway;

import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.*;

public class NorwegianNonGeographic5DigitNumber extends NorwegianPhoneNumber {

	private NorwegianNonGeographic5DigitNumber(String normalizedNumber) {
		super(normalizedNumber, normalizedNumber);
	} 

	public static NorwegianNonGeographic5DigitNumber of(final String terseRepresentation) {
		final String normalizedNumber = normalizeNorwegianNumberOrThrow(terseRepresentation);
		assertStringRepresentsCorrectPhoneNumberOrThrow(normalizedNumber, NonGeographic5Digit);
		
		return new NorwegianNonGeographic5DigitNumber(normalizedNumber);
	}
	
}
