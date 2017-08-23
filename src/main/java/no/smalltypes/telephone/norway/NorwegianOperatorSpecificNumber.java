package no.smalltypes.telephone.norway;

import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.*;

import lombok.EqualsAndHashCode;
/**
 * An operator-specific number is a number that can lead to different places depending on operator.
 * This means that if someone gives you this number, be aware that it may or may not lead to the same place.
 */
@EqualsAndHashCode(callSuper=true)
public class NorwegianOperatorSpecificNumber extends NorwegianPhoneNumber {

	private final String operator;
	
	private NorwegianOperatorSpecificNumber(String normalizedNumber, String operator) {
		super(normalizedNumber, normalizedNumber);
		this.operator = operator;
	}

	public String operator() {
		return operator;
	}
	
	public static NorwegianOperatorSpecificNumber of(final String terseRepresentation) {
		return of(terseRepresentation, "UNKNOWN OPERATOR");
	}
	
	public static NorwegianOperatorSpecificNumber of(final String terseRepresentation, final String operator) {
		final String normalizedNumber = normalizeNorwegianNumberOrThrow(terseRepresentation);
		assertStringRepresentsCorrectPhoneNumberOrThrow(normalizedNumber, OperatorSpecific);
		
		return new NorwegianOperatorSpecificNumber(normalizedNumber, operator);
	}
}
