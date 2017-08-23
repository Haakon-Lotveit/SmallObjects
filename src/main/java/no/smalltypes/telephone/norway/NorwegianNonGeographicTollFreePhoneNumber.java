package no.smalltypes.telephone.norway;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static no.smalltypes.telephone.norway.NorwegianPhonenumberType.*;
import static java.lang.String.format;

/**
 * These are also known as "teletorg", "800-numbers" and "Don't fucking call these, they're absolute worthless pissgobble".
 * The exception of course being 815 493 00. Which is not what the Norwegian besides you singing it is thinking of (anymore).
 * 
 * Ahem. Anyway. They are commercial numbers that are not tied to a geographical location. If you move shop, you can keep your number.
 * There's more too it, but I'm not an expert on these types of numbers.  
 */
public class NorwegianNonGeographicTollFreePhoneNumber extends NorwegianPhoneNumber {

	private static final Pattern pattern = Pattern.compile("(\\s{3})(\\s{2})(\\s{3})");
	
	public NorwegianNonGeographicTollFreePhoneNumber(String terseNumber, String localPrettyPrintedNumber) {
		super(terseNumber, localPrettyPrintedNumber);
	}
	
	public static NorwegianNonGeographicTollFreePhoneNumber of(String terseRepresentation) {
		String normalized = possiblyNorwegianNumberOrThrow(terseRepresentation);
		assertStringRepresentsCorrectPhoneNumberOrThrow(normalized, NonGeographicTollFree);
		
		Matcher matcher = pattern.matcher(normalized); matcher.matches();
		
		String terseNumber = format("+47%s", normalized);
		String localPrettyPrintedNumber = format("%s %s %s", matcher.group(1), matcher.group(2), matcher.group(3));
				
		return new NorwegianNonGeographicTollFreePhoneNumber(terseNumber, localPrettyPrintedNumber);
	}
	
}
