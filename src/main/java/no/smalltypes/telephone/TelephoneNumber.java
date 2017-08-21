package no.smalltypes.telephone;

public interface TelephoneNumber {
	public String parseable();
	public String internationalPrettyPrinted();
	public String localPrettyPrinted();
	public String country();
	public String internationalCode();
}
