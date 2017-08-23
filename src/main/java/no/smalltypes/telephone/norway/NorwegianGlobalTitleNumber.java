package no.smalltypes.telephone.norway;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.String.format;
import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.*;

/**
 * This has to do with satellite telephony, and I'm not exactly an expert on this. But this is the block of numbers kept for this purpose.
 */
public class NorwegianGlobalTitleNumber extends NorwegianPhoneNumber {

	private static final Pattern pattern = Pattern.compile("(\\d{3})(\\d{2})(\\d{3})");
	
	private NorwegianGlobalTitleNumber(String normalizedNumber, String localPrettyPrintedNumber) {
		super(normalizedNumber, localPrettyPrintedNumber);
	}

	public static NorwegianGlobalTitleNumber of(String terseRepresentation) {
		String normalized = normalizeNorwegianNumberOrThrow(terseRepresentation);
		assertStringRepresentsCorrectPhoneNumberOrThrow(normalized, GlobalTitle);
		
		Matcher matcher = pattern.matcher(normalized); matcher.matches();
		
		String localPrettyPrintedNumber = format("%s %s %s", matcher.group(1), matcher.group(2), matcher.group(3));
		
		return new NorwegianGlobalTitleNumber(normalized, localPrettyPrintedNumber);
	}
}
