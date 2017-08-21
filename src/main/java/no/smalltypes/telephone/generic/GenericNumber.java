package no.smalltypes.telephone.generic;

import lombok.EqualsAndHashCode;
import no.smalltypes.telephone.AbstractTelephoneNumber;

@EqualsAndHashCode(callSuper = true)
public class GenericNumber extends AbstractTelephoneNumber {

	public GenericNumber(String terseNumber, String localPrettyPrintedNumber, String internationalPrettyPrintedNumber, String country) {
		super(terseNumber, localPrettyPrintedNumber, internationalPrettyPrintedNumber, country);
	}
}
