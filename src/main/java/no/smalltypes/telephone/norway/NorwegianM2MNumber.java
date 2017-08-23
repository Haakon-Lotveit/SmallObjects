package no.smalltypes.telephone.norway;

import static java.lang.String.format;
import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.Machine2Machine;
import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.assertStringRepresentsCorrectPhoneNumberOrThrow;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NorwegianM2MNumber extends NorwegianPhoneNumber {

	private static final Pattern pattern = Pattern.compile("(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})");
	
	public NorwegianM2MNumber(String normalizedNumber, String localPrettyPrintedNumber) {
		super(normalizedNumber, localPrettyPrintedNumber);
	}

	public static NorwegianM2MNumber of(String terseRepresentation) {
		String normalized = normalizeNorwegianNumberOrThrow(terseRepresentation);
		assertStringRepresentsCorrectPhoneNumberOrThrow(normalized, Machine2Machine);
		
		Matcher matcher = pattern.matcher(normalized); matcher.matches();
		
		String localPrettyPrintedNumber = format("%s %s %s %s %s %s", matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6));
		
		return new NorwegianM2MNumber(normalized, localPrettyPrintedNumber);
	}
}
