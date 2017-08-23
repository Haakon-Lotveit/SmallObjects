package no.smalltypes.telephone.norway;

import static java.lang.Long.parseLong;

import no.smalltypes.telephone.IllegalPhoneNumberException;

/*
 * This basically says what type of number something is.
 * Look at all these classes we might need. Jesus christ.
 * When I was a kid I was taught that phone numbers had 8 digits, with an exception for 001, 002, 003. then they switched to 110, 112 and 113.
 * Now this madness has encroached on our once fair phonebooks.
 */
public enum NorwegianPhonenumberType {
	Landline, Machine2Machine, Machine2MachineCellular, Cellphone, GlobalTitle, EmergencyNumber, GreaterGood, NonGeographicTollFree,
	NonGeographic5Digit, OperatorSpecific, DirectoryService, NotAPhoneNumber;

	/**
	 * This attempts to classify a given phone number as a Norwegian phone number.
	 * This makes creating classes that specialize NorwegianPhoneNumber easier, by removing some of the validation burden, and allowing better error messaging.
	 * @param terseRepresentation A terse representation of a number, and with the exception of the optional international prefix ("+47"), only digits.
	 * @return the enumerated type that best fits the input. If no "real type" can be found, it will return NotAPhoneNumber. If the input string is not numeric (excepting a +47 prefix) NotAPhoneNumber will be returned instead.
	 */
	public static NorwegianPhonenumberType classify(String terseRepresentation) {
		String normalizedNumber = terseRepresentation.startsWith("+47")? terseRepresentation.substring(3) : terseRepresentation;		
		long numerical = 0L;
		try {
			parseLong(normalizedNumber, 10);
		}
		catch(NumberFormatException nfe) {
			return NotAPhoneNumber;
		}

		// This is a normal landline phone number
		if(normalizedNumber.length() == 8 &&
				!normalizedNumber.startsWith("4") &&
				!normalizedNumber.startsWith("58") &&
				!normalizedNumber.startsWith("59") &&
				!normalizedNumber.startsWith("9") &&
 				!normalizedNumber.startsWith("8")) {
			return Landline;
		}

		// 58 00 00 00 00 00 to 58 99 99 99 99 99 are reserved for machine to machine communication. This would give 10^10 addresses, and hence more than an uint32.
		// That's right, every ipv4 address could have been hooked to a phone number and you'd still have more than half of them unassigned.
		// Which is kind of cool. You could also map 100 machines to every normal phone number and still have a lot of free numbers. 
		// (So if I had the number 55 66 77 88, I could get the robot numbers 58 55 66 77 88 XX.) I wonder if that was the original plan?
		// This is something I wish I knew more about. Sounds like a cool seventies futuretech thingie.
		// Anyway, this is something that I wish I knew more about.
		if(normalizedNumber.length() == 12 && normalizedNumber.startsWith("58")) {
			return Machine2Machine;
		}

		if(normalizedNumber.length() == 8 && normalizedNumber.startsWith("59")) {
			return Machine2MachineCellular;
		}
		if(normalizedNumber.length() == 8 && (normalizedNumber.startsWith("4") || normalizedNumber.startsWith("9")) && numerical < 999_00_000) {
			return Cellphone;
		}

		if(normalizedNumber.length() == 8 && numerical >= 999_00_000) {
			return GlobalTitle;
		}

		// This means that it's an emergency service. 110, 112 and 113 are the standards, but all these numbers are reserved, and hence possible numbers.
		// Or rather, 100 - 189 inclusive is for emergency numbers and 190-199 are operator specific.
		if(normalizedNumber.length() == 3 && numerical >= 100 && numerical < 190) {
			return EmergencyNumber;
		}

		// This is for harmonized services of social value. Yes, that's a EU bureaucrat term. How did you know?
		if(normalizedNumber.length() == 6 && normalizedNumber.startsWith("116")) {
			return GreaterGood;
		}

		// Non Geographic 800-number. TODO:
		if(normalizedNumber.length() == 8 && normalizedNumber.startsWith("8")) {
			return NonGeographicTollFree;
		}

		// This is the type of numbers beholden to non-geographically attached landlines. Usually taxi services. My hometown has 07000 for example.
		if(normalizedNumber.length() == 5 && normalizedNumber.startsWith("0") && normalizedNumber.charAt(1) != '1') {
			return NonGeographic5Digit;
		}

		if(normalizedNumber.length() == 3 && numerical >= 190 && numerical <= 199) {
			return OperatorSpecific;
		}

		// Special case for deaf text telephone for emergencies.
		// I have NOT been able to dig up what the rules for this number is, and it's driving me crazy.
		if(normalizedNumber.equals("1412")) {
			return EmergencyNumber;
		}
		
		// This is for directory services, like 1880 and 1881.
		if(normalizedNumber.length() == 4 && numerical >= 1850 && numerical <= 1899) {
			return DirectoryService;
		}

		return NotAPhoneNumber;

	}
	
	/**
	 * Checks that a terse representation of a phone number represents the type it should represent.
	 * @param terseRepresentation
	 * @param expectedType
	 */
	public static void assertStringRepresentsCorrectPhoneNumberOrThrow(String terseRepresentation, NorwegianPhonenumberType expectedType) {
		NorwegianPhonenumberType type = classify(terseRepresentation);
		if(type != expectedType) {
			throw new IllegalPhoneNumberException("Wrong type of phone number: Got " + type + " but expected " + expectedType);
		}
	}
}

