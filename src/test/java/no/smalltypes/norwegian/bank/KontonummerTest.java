package no.smalltypes.norwegian.bank;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.BeforeMethod;

public class KontonummerTest {
	Kontonummer candidate = null;
	private static final String accountNumber = "12345678903";
	private static final String dotFormattedNumber = "1234.56.78903";
	private static final String spaceFormattedNumber = "1234 56 78903";
	
  @BeforeMethod
  public void beforeMethod() {
	candidate = Kontonummer.parseDigitString(accountNumber);  
  }

  @Test
  public void getDigits() {
    assertEquals(candidate.getDigits(), accountNumber);
  }

  @Test
  public void getDotFormat() {
    assertEquals(candidate.getDotFormat(), dotFormattedNumber);
  }

  @Test
  public void getSpaceFormat() {
    assertEquals(candidate.getSpaceFormat(), spaceFormattedNumber);
  }

  @Test
  public void validateChecksum() {
    assertTrue(Kontonummer.validateChecksum(accountNumber));
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void testNPEWhenPassingNullToConstructor() {
	  Kontonummer.parseDigitString(null);
  }
  
  @Test(expectedExceptions = InvalidKontonummerChecksumException.class)
  public void testChecksumValidation() {
	  try {
		  Kontonummer.parseDigitString("12345678903");
	  }
	  catch(InvalidKontonummerChecksumException ikce) {
		  fail("An exception was thrown, but the String passed was a valid Kontonummer. Exception: " + ikce);
	  }
	  Kontonummer.parseDigitString("12345678902");
  }
  
  @Test(expectedExceptions = InvalidKontonummerFormatException.class)
  public void testEmptyString() {
	  Kontonummer.parseDigitString("");
  }
  @Test(expectedExceptions = InvalidKontonummerFormatException.class)
  public void testTooLongString() {	  
	  Kontonummer.parseDigitString("1234567890123");	  
  }
  @Test(expectedExceptions = InvalidKontonummerFormatException.class)
  public void testTooShortString() {
	  Kontonummer.parseDigitString("12345678");
  }
}
