package no.smalltypes.telephone;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class AbstractTelephoneNumber implements TelephoneNumber {

	private final String parseableNumber;
	private final String localNumber;
	private final String i18nNumber;

	private final String country;

	public AbstractTelephoneNumber(String terseNumber, String localPrettyPrintedNumber, String internationalPrettyPrintedNumber, String country) {
		this.parseableNumber = terseNumber;
		this.localNumber = localPrettyPrintedNumber;
		this.i18nNumber = internationalPrettyPrintedNumber;
		this.country = country;
	}

	// A possible test. Remember that there are countries where they may not use ASCII-numbers. Arab numerals exist.
	protected static void onlyAsciiDigitsOrThrow(String digits) {
		for(char c : digits.toCharArray()) {
			// There might at some point be a charset used where this is needed, so...
			switch (c) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				break;
			default:
				throw new IllegalArgumentException(String.format("In phonenumber <%s>, char '%c% is not an ASCII digit", digits, c));
			}
		}
	}

	public String parseable() {
		return parseableNumber;
	}

	public String internationalPrettyPrinted() {
		return i18nNumber;
	}

	public String localPrettyPrinted() {
		return localNumber;
	}

	public String country() {
		return country;
	}

	public String internationalCode() {
		// TODO Auto-generated method stub
		return null;
	}

	protected static boolean onlyAsciiDigitsOrFalse(String digits) {
		for(char c : digits.toCharArray()) {
			switch (c) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				break;
			default:
				return false;
			}
		}
		return true;
	}
}
