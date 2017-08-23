package no.smalltypes.telephone.norway;

import no.smalltypes.telephone.AbstractTelephoneNumber;
import no.smalltypes.telephone.IllegalPhoneNumberException;

public abstract class NorwegianPhoneNumber extends AbstractTelephoneNumber {

	public static final String COUNTRY = "NORWAY";

	public NorwegianPhoneNumber(String normalizedNumber, String localPrettyPrintedNumber) {
		super("+47" + normalizedNumber, localPrettyPrintedNumber, String.format("+47 %s", localPrettyPrintedNumber), COUNTRY);
	}

	protected static String possiblyNorwegianNumberOrThrow(final String maybeNumber) {
		// If the number starts with the international prefix, chop it off, and continue:
		String normalizedNumber = maybeNumber.startsWith("+47")? maybeNumber.substring(3) : maybeNumber;

		// If this is all digits, we're happy.
		try {
			onlyAsciiDigitsOrThrow(normalizedNumber);
		}
		catch(IllegalPhoneNumberException ipne) {
			String errorMessage = String.format("After checking for international prefix for Norwegian phone numbers, the number '%s' had been reduced to '%s', which contained non-digit characters", maybeNumber, normalizedNumber);
			throw new IllegalPhoneNumberException(errorMessage);
		}

		// TODO: It seems a bit strange to classify it here. a smaller preliminary test might be better.
		if(NorwegianPhonenumberType.classify(normalizedNumber) == NorwegianPhonenumberType.NotAPhoneNumber) {
			throw new IllegalPhoneNumberException(String.format("Phone number '%s' is not a valid Norwegian phone number of any sort", maybeNumber));
		}
		
		return normalizedNumber;
	}
}
