package test.no.smalltypes.telephone.no;

import static no.smalltypes.telephone.norway.NorwegianLandlineNumber.relaxedParser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.Test;

import no.smalltypes.telephone.IllegalPhoneNumberException;
import no.smalltypes.telephone.TelephoneNumber;
import no.smalltypes.telephone.generic.GenericNumber;
import no.smalltypes.telephone.norway.NorwegianLandlineNumber;


public class TestNorwegianLandlineNumber {

	@Test
	public void testLaxParserBasic() {
		TelephoneNumber number = relaxedParser("tlf: 22 33 44 55");
		numberIs22334455(number);
	}
	
	@Test
	public void testLaxParserTerseAndPretty() {
		TelephoneNumber number = relaxedParser("Telefonnummeret til Stian er 22334455 eller noe");
		numberIs22334455(number);
	}
	
	@Test
	public void testLargeString() {
		TelephoneNumber number = relaxedParser("Telefonnummeret til Silje er 2233 44 55, hvis du kan ringe henne og avtale hadde det vært fint. Hun er ferdig på jobb ca. 16.45 de fleste dager.");
		numberIs22334455(number);		
	}
	
	
	@Test
	public void testManyDigits() {
		TelephoneNumber number = relaxedParser("2233445566778899");
		numberIs22334455(number);
	}

	@Test(expectedExceptions = IllegalPhoneNumberException.class)
	public void testLaxInsufficientDigits() {
		relaxedParser("2345678");
	}
	
	@Test(expectedExceptions = IllegalPhoneNumberException.class)
	public void testOfFactoryTooManyDigits() {
		NorwegianLandlineNumber.of("2345678901");
	}
	
	@Test(expectedExceptions = IllegalPhoneNumberException.class)
	public void testLaxParser8() {
		relaxedParser("81549300");
	}
	
	@Test(expectedExceptions = IllegalPhoneNumberException.class)
	public void testLaxParser4() {
		relaxedParser("40000000");
	}
	@Test(expectedExceptions = IllegalPhoneNumberException.class)
	public void testOfFactoryTooFewDigits() {
		NorwegianLandlineNumber.of("2345678");
	}
	
	@Test(expectedExceptions = IllegalPhoneNumberException.class)
	public void testOfFactory800Number() {
		NorwegianLandlineNumber.of("81549300");
	}
	
	@Test(expectedExceptions = IllegalPhoneNumberException.class)
	public void testOfFactoryCellphoneStartsWith4() {
		NorwegianLandlineNumber.of("40000000");
	}
	
	@Test(expectedExceptions = IllegalPhoneNumberException.class)
	public void testOfFactoryCellphoneStartsWith9() {
		NorwegianLandlineNumber.of("90000000");
	}
	
	@Test
	public  void testEquals() {
		TelephoneNumber number = NorwegianLandlineNumber.of("22334455");
		TelephoneNumber equal  = NorwegianLandlineNumber.of("22334455");
		TelephoneNumber unequalType = new GenericNumber(number.parseable(), number.localPrettyPrinted(), number.internationalPrettyPrinted(), NorwegianLandlineNumber.COUNTRY);
		TelephoneNumber unequalFields = NorwegianLandlineNumber.of("55667788");
		
		assertEquals(number, number); // If *this* ever fails...
		assertEquals(equal, number);
		assertNotEquals(unequalType, number);
		assertNotEquals(unequalFields, number);
		
	}
	
	private void numberIs22334455(TelephoneNumber number) {
		assertEquals("22 33 44 55", number.localPrettyPrinted());
		assertEquals("+47 22334455", number.parseable());
	}
	
}
