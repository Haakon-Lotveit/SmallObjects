package test.no.smalltypes.telephone.no;

import org.testng.annotations.Test;

import no.smalltypes.telephone.norway.NorwegianLandlineNumber;
import no.smalltypes.telephone.norway.NorwegianPhoneNumber;

public class TestNorwegianPhoneNumber {

	@Test
	public void createvalidLandlineNumbers() {
		String validNumber = "22334455";
		String parseableNumber = "+47 22334455";
		NorwegianPhoneNumber.parse(validNumber).parseable().equals(parseableNumber);
		NorwegianPhoneNumber.parse(parseableNumber).localPrettyPrinted().equals("22 33 44 55");
		
		NorwegianPhoneNumber.parse(validNumber).getClass().equals(NorwegianLandlineNumber.class);
		NorwegianPhoneNumber.parse(parseableNumber).getClass().equals(NorwegianLandlineNumber.class);
	}
	
	@Test
	public void illegalLandlineNumbers() {
		
	}
}
