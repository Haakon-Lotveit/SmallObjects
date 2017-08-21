package test.no.smalltypes.telephone.no;

import static no.smalltypes.telephone.no.NorwegianLandlineNumber.of;
import static no.smalltypes.telephone.no.NorwegianLandlineNumber.relaxedParser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import no.smalltypes.telephone.IllegalPhoneNumberException;
import no.smalltypes.telephone.TelephoneNumber;
import no.smalltypes.telephone.generic.GenericNumber;
import no.smalltypes.telephone.no.NorwegianLandlineNumber;
public class TestNorwegianLandlineNumber {

	@Test
	public void testLaxParserBasic() {
		TelephoneNumber number = relaxedParser("tlf: 11 22 33 44");
		numberIs11223344(number);
	}
	
	@Test
	public void testLaxParserTerseAndPretty() {
		TelephoneNumber number = relaxedParser("Telefonnummeret til Stian er 11223344 eller noe");
		numberIs11223344(number);
	}
	
	@Test
	public void testLargeString() {
		TelephoneNumber number = relaxedParser("Telefonnummeret til Silje er 1122 33 44, hvis du kan ringe henne og avtale hadde det vært fint. Hun er ferdig på jobb ca. 16.45 de fleste dager.");
		numberIs11223344(number);		
	}
	
	@Test
	public void testManyDigits() {
		TelephoneNumber number = relaxedParser("112233445566778899");
		numberIs11223344(number);
	}

	@Test(expected = IllegalPhoneNumberException.class)
	public void testLaxInsufficientDigits() {
		relaxedParser("1234567");
	}
	
	@Test(expected = IllegalPhoneNumberException.class)
	public void testOfFactoryTooManyDigits() {
		of("123456789");
	}
	
	@Test(expected = IllegalPhoneNumberException.class)
	public void testLaxParser8() {
		relaxedParser("81549300");
	}
	
	@Test(expected = IllegalPhoneNumberException.class)
	public void testLaxParser4() {
		relaxedParser("40000000");
	}
	@Test(expected = IllegalPhoneNumberException.class)
	public void testOfFactoryTooFewDigits() {
		of("1234567");
	}
	
	@Test(expected = IllegalPhoneNumberException.class)
	public void testOfFactory800Number() {
		of("81549300");
	}
	
	@Test(expected = IllegalPhoneNumberException.class)
	public void testOfFactoryCellphoneStartsWith4() {
		of("40000000");
	}
	
	@Test(expected = IllegalPhoneNumberException.class)
	public void testOfFactoryCellphoneStartsWith9() {
		of("90000000");
	}
	
	@Test
	public  void testEquals() {
		TelephoneNumber number = NorwegianLandlineNumber.of("11223344");
		TelephoneNumber equal  = NorwegianLandlineNumber.of("11223344");
		TelephoneNumber unequalType = new GenericNumber(number.terseNumber(), number.prettyPrint(), number.countryCode(), number.getLocale());
		TelephoneNumber unequalFields = NorwegianLandlineNumber.of("55667788");
		
		assertEquals(number, number); // If *this* ever fails...
		assertEquals(equal, number);
		assertNotEquals(unequalType, number);
		assertNotEquals(unequalFields, number);
		
	}
	
	private void numberIs11223344(TelephoneNumber number) {
		assertEquals("11 22 33 44", number.prettyPrint());
		assertEquals("11223344", number.terseNumber());
	}
	
}
