import static org.junit.Assert.*;

import java.util.Locale;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class NorwegianTelephoneTesting {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNorwegianLocalIsAvailable() {
		 Locale l = Locale.forLanguageTag("no-NO");
		 System.out.println(l.getLanguage());
		 System.out.println(l.getCountry());
		 System.out.println(l);
	}

}
