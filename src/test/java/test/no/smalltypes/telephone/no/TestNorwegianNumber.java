package test.no.smalltypes.telephone.no;

import static no.smalltypes.telephone.norway.NorwegianLandlineNumber.relaxedParser;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import no.smalltypes.telephone.TelephoneNumber;
import no.smalltypes.telephone.norway.NorwegianPhoneNumber;

public class TestNorwegianNumber {

	@Test
	public void testInvariants() {
		TelephoneNumber number = relaxedParser("22334455");
		assertEquals(number.country(), NorwegianPhoneNumber.COUNTRY);
	}

}
