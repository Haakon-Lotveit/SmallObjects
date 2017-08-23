package no.smalltypes.telephone.norway;

import static java.lang.String.format;

import no.smalltypes.telephone.AbstractTelephoneNumber;
import no.smalltypes.telephone.IllegalPhoneNumberException;
public abstract class NorwegianPhoneNumber extends AbstractTelephoneNumber {

	public static final String COUNTRY = "NORWAY";

	public NorwegianPhoneNumber(String normalizedNumber, String localPrettyPrintedNumber) {
		super("+47" + normalizedNumber, localPrettyPrintedNumber, format("+47 %s", localPrettyPrintedNumber), COUNTRY);
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
}
