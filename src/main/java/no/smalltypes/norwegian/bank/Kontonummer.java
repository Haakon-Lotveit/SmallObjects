package no.smalltypes.norwegian.bank;

import static java.lang.Character.getNumericValue;
import static java.lang.String.format;
import static java.util.Objects.isNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;

/**
 * "Kontonummer" is the account number. The number will identify an account uniquely, and is split into 3+1 parts:
 *  The format works like this: xxxxyyzzzzc, in total eleven digits where:
 *  <ul>
 *  <li>xxxx is the Bank Registry number, and identifies the bank and its department/branch</li>
 *  <li>yy is the account group. This used to be relatively standardized but this is no longer the case. Do not use it for such means.</li>
 *  <li>zzzz is the account number. This is a running number for the account.</li>
 *  <li>c is the countrol number, which is calculated using modulus 11, weighted with 5432765432. I have no idea why this number was chosen.</li>
 *  </ul>
 *  Hence the number of accounts available per branch is 10^(2+4), or 10k. This is a great system for the old days when you had local branches in the countryside and multiple branches in the cities.
 *  
 *  Today, not so much, and there is some discussion about updating the standard.
 *  
 *  Since the concepts of accounts etc. in Norwegian is a geographically specialized thing, code in this class may feature Norwanglish. You've been warned.
 */
@EqualsAndHashCode
public class Kontonummer {
	private static final Pattern kontoPattern = Pattern.compile("(\\d{4})(\\d{2})(\\d{5})"); 
	private static final int ACCOUNT_NUMBER_LENGTH = 11;

	private static final String PARSEABLE_FORMAT = "%s%s%s";
	private static final String SPACE_FORMAT = "%s %s %s";
	private static final String DOT_FORMAT = "%s.%s.%s";

	private final String bankRegistryNumber;
	private final String accountGroup;
	private final String accountNumber;

	protected Kontonummer(String digits) {
		if(isNull(digits)) {
			throw new NullPointerException("null is not a valid string to create a Kontonummer from");
		}
		Matcher matcher = kontoPattern.matcher(digits);

		if(!matcher.matches()) {
			throw new InvalidKontonummerFormatException("Invalid account number (does not match the format): " + digits);
		}

		validChecksumOrThrow(digits);

		this.bankRegistryNumber = matcher.group(1);
		this.accountGroup = matcher.group(2);
		this.accountNumber = matcher.group(3);
	}


	/**
	 * Creates a new Account Number based on the digits presented.
	 * @param digits 11 digits, no other characters allowed.
	 * @return an account number
	 */
	public static Kontonummer parseDigitString(String digits) {
		return new Kontonummer(digits);
	}

	/**
	 * Applies a formatting String to three String arguments.
	 * This is used by the other formatters, and you might also like to use it if you need it.
	 * However, this is final because it needs to be consistent with the parser. Do keep this in mind.
	 * @param format a format string with three %s arguments and ONLY three %s arguments.
	 * @return a formatted string of the account number.
	 */
	public final String applyFormat(final String format) {
		return format(format, bankRegistryNumber, accountGroup, accountNumber);
	}

	public final String getDigits() {
		return applyFormat(PARSEABLE_FORMAT);			
	}

	public String getDotFormat() {
		return applyFormat(DOT_FORMAT);
	}

	public String getSpaceFormat() {
		return applyFormat(SPACE_FORMAT);
	}

	public String toString() {
		return format("Kontonummer[%s]", getDotFormat());
	}

	/**
	 * The bank register number is a 4 digit number that uniquely identifies a bank/branch.
	 * The data is stored with Bankplassregisteret (Bank place registry), which has information about the bank's name, 
	 * {@link no.smalltypes.norwegian.brreg.OrganisasjonsNummer}, bankregisternumber, expediting offices, and Swift-codes.
	 * The registry is published both as a book and as a spreadsheet.
	 * If you need to have this type of information, consider visiting <a href="https://www.finansnorge.no/om-finans-norge/publikasjoner/bankplassregisteret/">Finance Norway (Norwegian)</a>
	 * and grab a copy. It's not free, so data from it is not going to be published here.
	 * 
	 * @return the bank registry number as a four digit string.
	 */
	public String getBankRegistryNumber() {
		return bankRegistryNumber;
	}
	
	/**
	 * The account group was at one point standardized-ish between or at least internally in a bank.
	 * There is however, no actual standard for this, but this number is typically formatted as a separate number,
	 * and so you ask for it. Again, do not consider this as an identifier for account <i>type</i>.
	 * Today this is functionally just a part of the account number that is formatted differently for nostalgic reasons.
	 * @return the account group number, as a two digit string.
	 */
	public String getAccountGroup() {
		return accountGroup;
	}
	
	/**
	 * The account number is a running number completely under the control of the bank.
	 * Today, the account group is functionally a part of this number, but they're kept apart because in real life,
	 * they're formatted differently. The documentation from Finance Norway that talks about IBAN numbers will say that
	 * there are six digits in the account number. If you want THAT string, you want to call {@linkplain Kontonummer#getAccountGroup()},
	 * and append this string like this: <code>String iban = acct.getAccountGroup() + acct.getAccountNumber();</code>
	 * 
	 * It would be confusing regardless of how we implemented this, so we've chosen to stick to the Norwegian naming, translating it,
	 * and translating this gotcha.
	 * 
	 * @return the account number as a 4 digit string.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * This is an internal method used to verify that the digit string has a valid checksum.
	 * @param digits is an pre-verified string of digits.
	 */
	private static void validChecksumOrThrow(String digits) {
		int correctChecksum = calculateChecksumDigit(digits);
		int actualChecksum = getNumericValue(digits.charAt(ACCOUNT_NUMBER_LENGTH -1));

		// If we're about to throw an exception, then things will be slow. We thus have all the time in the world to create an
		// informative and hopefully helpful error message to go with the exception.
		if(actualChecksum != correctChecksum) {			
			String errorMsg = format("Incorrect checksum digit for account number '%s'. Was %d, but should have been %d", digits, actualChecksum, correctChecksum); 
			throw new InvalidKontonummerChecksumException(errorMsg);
		}
	}

	/**
	 * Validates an account number by checking that the checksum digit at the end is the correct checksum.
	 * @param accountNumber the non-null accountNumber that you want checked. digits only.
	 * @return true if it's a valid accountNumber with a correct checksum, otherwise, false. It will also return false if the string is not a valid account number.
	 * @throws NullPointerException if accountNumber is null. Null is not a valid argument for this method. Do not pass null. Do not collect $400.
	 */
	public static boolean validateChecksum(String accountNumber) {
		if(isNull(accountNumber)) {
			throw new NullPointerException("null is not a valid argument for this method");
		}

		if(accountNumber.length() != ACCOUNT_NUMBER_LENGTH) {
			return false;
		}
		else {
			return getNumericValue(accountNumber.charAt(ACCOUNT_NUMBER_LENGTH - 1)) == calculateChecksumDigit(accountNumber);
		}
	}

	/*
	 * The specification for the algorithm says that:
	 * The weights are the series of digits 2, 3, 4, 5, 6, 7
	 * and that this weighting is to be applied to every character in the input string,
	 * from the second to last character, backwards to the first character.
	 * Doing that algorithm in that precise way is not as neat as it should be.
	 * So we just calculate the 11 first repetitions, reverse it, and go front to back instead.
	 * This is mathematically equivalent (since addition is commutative)
	 * 
	 *  We do not check the input here. Assumption is that the caller WILL.
	 */
	/**
	 * Calculates the modulus11 control digit. 
	 * @param accountNumber the account number you want to create a control digit for. This string is not validated before the algorithm is run.
	 * @return the control digit between 0-9 inclusive.
	 */
	public static int calculateChecksumDigit(String accountNumber) {
		int[] weights = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
		int controlSum = 0;

		for(int i = 0; i < ACCOUNT_NUMBER_LENGTH - 1; ++i) {
			int num = getNumericValue(accountNumber.charAt(i));
			controlSum += num * weights[i];
		}

		return (11 - (controlSum % 11));
	}
}
