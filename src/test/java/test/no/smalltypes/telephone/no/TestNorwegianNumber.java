package test.no.smalltypes.telephone.no;

import static no.smalltypes.telephone.no.NorwegianLandlineNumber.relaxedParser;
import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

import no.smalltypes.telephone.TelephoneNumber;

public class TestNorwegianNumber {

	@Test
	public void testInvariants() {
		TelephoneNumber number = relaxedParser("11223344");
		assertEquals("+47", number.countryCode());
		assertEquals(Locale.forLanguageTag("NO-no"), number.getLocale());
	}

}
