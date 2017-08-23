package no.smalltypes.telephone.norway;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import no.smalltypes.telephone.IllegalPhoneNumberException;

public final class NorwegianLandlineNumber extends NorwegianPhoneNumber {
	private static final Pattern pattern = Pattern.compile("(\\d{2})(\\d{2})(\\d{2})(\\d{2})");
	
	private NorwegianLandlineNumber(String terseNumber, String localPrettyPrintedNumber) {
		super(terseNumber, localPrettyPrintedNumber);
	}

	/**
	 *
	 * This is one of the preferred ways to create a phone number that's a Norwegian landline.
	 * Other methods should prefer to hand off a parsed string to this method. 
	 *  
	 * @param terseRepresentation This code expects a <i>terse representation</i> of the phone number. <br>
	 * This means that the string must be either:
	 * <ol>
	 * <li>  8 characters long, and all digits </li>
	 * <li> 11 characters long, and start with "+47"</li>
	 * </lo>
	 * In addition it must adhere to the Norwegian standards for landline numbers:
	 * It must start with 2, 3, 5, 6, or 7.<br> <br>
	 
	 * @return a ready-built phone number.
	 * @throws IllegalPhoneNumberException if there are any errors in building the phone number.
	 */
	public static NorwegianLandlineNumber of(final String terseRepresentation) {
		String normalized = normalizeNorwegianNumberOrThrow(terseRepresentation);
		NorwegianPhonenumberType.assertStringRepresentsCorrectPhoneNumberOrThrow(terseRepresentation, NorwegianPhonenumberType.Landline);
		
		Matcher matcher = pattern.matcher(normalized);
		matcher.matches();
		
		String localPrettyPrintedNumber = String.format(
				"%s %s %s %s",
				matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
		
		return new NorwegianLandlineNumber(normalized, localPrettyPrintedNumber);
	}
	
	/**
	 * This is an incredibly lazy parser. If will look for numbers or a +, and proceed from there, trying to pick up digits until it has enough,
	 * and then it will use the of-factory method. If the input is bad, the of method will complain.
	 * TODO: This should probably be moved to the NorwegianPhoneNumber class, do a switch on the enumerated type, and then build from there.
	 * We'd end up doing the classification twice, but that's okay. 
	 * @param text the text to be parsed.
	 * @return a NorwegianLandLineNumber consisting of the digits you passed in.
	 */
	public static NorwegianLandlineNumber relaxedParser(String text) {
		StringBuilder picker = new StringBuilder();

		// This makes subsequent processing simpler.
		for(char c : text.toCharArray()) {
			if("1234567890+".indexOf(c) != -1) {
				picker.append(c);
			}
		}
		
		if(picker.length() < 3) {
			throw new IllegalPhoneNumberException("There are less than 3 legal characters in '" + text + "'. Cannot create phone number");
		}
		
		// This is clunky, but it essentially removes the +47 prefix if it exists, and removes + chars that are still left.
		String digits = (picker.toString().startsWith("+47")? picker.substring(3) : picker.toString()).replace("+", "");
		if(digits.length() < 8) {
			throw new IllegalPhoneNumberException("There needs to be at least 8 legal digits for a landline, and '" + text + "' has only " + digits.length());
		}
		
		return of(digits.substring(0,8));
	}
}
