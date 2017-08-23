package no.smalltypes.telephone.norway;

import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.*;

public class NorwegianDirectoryServiceNumber extends NorwegianPhoneNumber {

	public NorwegianDirectoryServiceNumber(String normalizedNumber) {
		super(normalizedNumber, normalizedNumber);
	}

	public static NorwegianDirectoryServiceNumber of(final String terseRepresentation) {
		final String normalizedNumber = normalizeNorwegianNumberOrThrow(terseRepresentation);
		assertStringRepresentsCorrectPhoneNumberOrThrow(normalizedNumber, DirectoryService);
		
		return new NorwegianDirectoryServiceNumber(normalizedNumber);
	}
}
