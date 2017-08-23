package no.smalltypes.telephone.norway;

import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.GreaterGood;
import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.assertStringRepresentsCorrectPhoneNumberOrThrow;

public class NorwegianGreaterGoodNumber extends NorwegianPhoneNumber {

	private NorwegianGreaterGoodNumber(String normalizedNumber) {
		super(normalizedNumber, normalizedNumber);
	}

	public static NorwegianGreaterGoodNumber of(String terseRepresentation) {
		String normalizedNumber = normalizeNorwegianNumberOrThrow(terseRepresentation);
		assertStringRepresentsCorrectPhoneNumberOrThrow(normalizedNumber, GreaterGood);
		
		return new NorwegianGreaterGoodNumber(normalizedNumber);
	}
}
