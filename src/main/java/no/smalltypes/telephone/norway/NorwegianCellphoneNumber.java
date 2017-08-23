package no.smalltypes.telephone.norway;

import static java.lang.String.format;
import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.Cellphone;
import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.assertStringRepresentsCorrectPhoneNumberOrThrow;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NorwegianCellphoneNumber extends NorwegianPhoneNumber {
	// I first want a group of 3 digits, then 2 digits and finally 3 digits.
	private static final Pattern pattern = Pattern.compile("(\\d{3})(\\d{2})(\\d{3})");
	
	private NorwegianCellphoneNumber(String terseNumber, String localPrettyPrintedNumber) {
		super(terseNumber, localPrettyPrintedNumber);
	}

	// I'm kinda proud of how DUMB this method is. There is absolutely NOTHING here that is surprising, unclear or in other ways hard to understand.
	public static NorwegianCellphoneNumber of(final String terseRepresentation) {
		String normalized = possiblyNorwegianNumberOrThrow(terseRepresentation);
		assertStringRepresentsCorrectPhoneNumberOrThrow(normalized, Cellphone);
		
		Matcher matcher = pattern.matcher(normalized); matcher.matches();
		
		String terseNumber = format("+47%s", normalized);
		String localPrettyPrintedNumber = format("%s %s %s", matcher.group(1), matcher.group(2), matcher.group(3));
		
		return new NorwegianCellphoneNumber(terseNumber, localPrettyPrintedNumber);
	}
}
