package no.smalltypes.telephone.norway;

import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.EmergencyNumber;
import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.assertStringRepresentsCorrectPhoneNumberOrThrow;

/**
 * While there are only three recognized numbers as of today (110, 112, 113), there is a range of numbers held off for these types of numbers.
 * The standard numbers are held as static final members.
 * There is also the text telephone number for deaf people at 1412, which I have not yet found any explanation for and is special-cased in the classifier.   
 */
public class NorwegianEmergencyNumber extends NorwegianPhoneNumber {

	public static NorwegianEmergencyNumber INTERNATIONAL_STANDARD = of("112");
	public static NorwegianEmergencyNumber FIRE = of("110");
	public static NorwegianEmergencyNumber POLICE = of("112");
	public static NorwegianEmergencyNumber MEDICAL = of("113");
	public static NorwegianEmergencyNumber EMERGENCY_NUMBER_FOR_DEAF_PEOPLE = of("1412");
	
	private NorwegianEmergencyNumber(String normalizedNumber) {
		super(normalizedNumber, normalizedNumber);
	}
	
	public static NorwegianEmergencyNumber of(String terseRepresentation) {
		String normalizedNumber = normalizeNorwegianNumberOrThrow(terseRepresentation);
		assertStringRepresentsCorrectPhoneNumberOrThrow(normalizedNumber, EmergencyNumber);
		
		return new NorwegianEmergencyNumber(normalizedNumber);
	}

}
