package no.smalltypes.telephone.norway;

import static java.lang.String.format;

import no.smalltypes.telephone.AbstractTelephoneNumber;
import no.smalltypes.telephone.IllegalPhoneNumberException;
public abstract class NorwegianPhoneNumber extends AbstractTelephoneNumber {

	public static final String COUNTRY = "NORWAY";

	public NorwegianPhoneNumber(String normalizedNumber, String localPrettyPrintedNumber) {
		super("+47 " + normalizedNumber, localPrettyPrintedNumber, format("+47 %s", localPrettyPrintedNumber), COUNTRY);
	}

	protected static String normalizeNorwegianNumberOrThrow(final String maybeNumber) {

		if(maybeNumber.length() < 3) {
			throw new IllegalPhoneNumberException(format("Phone number '%s' is too short to be a valid phone number. Must be at least 3 digits long.", maybeNumber));
		}
		
		// If the number starts with the international prefix, chop it off, and continue:
		String normalizedNumber = maybeNumber.startsWith("+47")? maybeNumber.substring(3) : maybeNumber;

		// If this is all digits, we're happy.
		try {
			onlyAsciiDigitsOrThrow(normalizedNumber);
		}
		catch(IllegalPhoneNumberException ipne) {
			String errorMessage = format("After checking for international prefix for Norwegian phone numbers, the number '%s' had been reduced to '%s', which contained non-digit characters", maybeNumber, normalizedNumber);
			throw new IllegalPhoneNumberException(errorMessage);
		}

		// TODO: It seems a bit strange to classify it here. a smaller preliminary test might be better.
		if(NorwegianPhonenumberType.classify(normalizedNumber) == NorwegianPhonenumberType.NotAPhoneNumber) {
			throw new IllegalPhoneNumberException(format("Phone number '%s' is not a valid Norwegian phone number of any sort", maybeNumber));
		}
		
		return normalizedNumber;
	}
	
	/**
	 * This is the main factory for Norwegian telephone numbers.
	 * You put in the string, and get out the number. If everything works.
	 */
	public static NorwegianPhoneNumber parse(String number) {
		int skipChars = 0;
		if(number.startsWith("+47")) {
			if(number.length() < 6) {
				throw new IllegalPhoneNumberException("Illegal phone number: " + number);
			}
			if(number.charAt(3) == ' ') {
				skipChars = 4;
			}
			else {
				skipChars = 3;
			}
		}
		else {
			if(number.length() < 3) {
				throw new IllegalPhoneNumberException("Illegal phone number:" + number);
			}
		}
		
		final String normalizedNumber = number.substring(skipChars);
		
		switch(NorwegianPhonenumberType.classify(normalizedNumber)) {
		case Cellphone:
			return NorwegianCellphoneNumber.of(normalizedNumber);
		case DirectoryService:
			return new NorwegianDirectoryServiceNumber(normalizedNumber);
		case EmergencyNumber:
			return NorwegianEmergencyNumber.of(normalizedNumber);
		case GlobalTitle:
			return NorwegianGlobalTitleNumber.of(normalizedNumber);
		case GreaterGood:
			return NorwegianGreaterGoodNumber.of(normalizedNumber);
		case Landline:
			return NorwegianLandlineNumber.of(normalizedNumber);
		case Machine2Machine:
			return NorwegianM2MNumber.of(normalizedNumber);
		case Machine2MachineCellular:
			return NorwegianM2MCellularNumber.of(normalizedNumber);
		case NonGeographic5Digit:
			return NorwegianNonGeographic5DigitNumber.of(normalizedNumber);
		case NonGeographicTollFree:
			return NorwegianNonGeographicTollFreePhoneNumber.of(normalizedNumber);
		case OperatorSpecific:
			return NorwegianOperatorSpecificNumber.of(normalizedNumber);
		case NotAPhoneNumber:
		default:
			String errorMsg = String.format("Couldn't parse phone number '%s' into a Norwegian phone number", number);
			throw new IllegalPhoneNumberException(errorMsg);
		}
		
	}
}
