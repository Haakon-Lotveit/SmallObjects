package no.smalltypes.norwegian.bank;

import java.util.Arrays;

/**
 * This is a small class to demonstrate some very simple usage of the kontonummer class.
 * Since the class used is simple, the demonstration will also be simple.
 */
public class KontunummerDemonstration {
	public static void main(String[] args) {
		// This is a valid kontonummer. But do we really want to use it as a string? 
		// No! This gives us little safety, and we're not using the type system to guard against errors.
		String kontonummer = "12345678903";
		
		// I mean, here be murder and mayhem: How do you know that this isn't a valid number? Do you validate in your head?
		String invalidKontonummer = "12345678902";
		
		// This on the other hand is better: Now you can tell your interfaces to accept Kontonummer instead of String, which is much better and safer.
		Kontonummer theRightThing = Kontonummer.parseDigitString(kontonummer);
		System.out.println(theRightThing);
		
		// I mean, if we tried to do this with the invalid one we'd get this:
		try {
			Kontonummer catastrophe = Kontonummer.parseDigitString(invalidKontonummer);
			System.out.println(catastrophe);
		}
		catch(InvalidKontonummerException e) {
			// note that there are multiple exceptions, but this is the root one for "anything" that can go wrong.
			System.out.println("Tried to parse out an invalid account number!");
			System.out.println(e);
		}
		
		// Of course you don't have to try to parse it to see if it's valid.
		System.out.println("Is the checksum digit valid? " + Kontonummer.validateChecksum(invalidKontonummer));
		// Shows you if the checksum is correct, and:
		System.out.println("Correct digit: " + Kontonummer.calculateChecksumDigit(invalidKontonummer));
		// will show you the correct one. Exceptions are not for control flow, and thus you have tools on hand to avoid using them as such.
		
		// Do you need to print it to your users? You can get both the dot and space formats as standard.
		System.out.println(theRightThing.getSpaceFormat());
		System.out.println(theRightThing.getDotFormat());
		
		// You can of course get it back as a parseable format:
		System.out.println(theRightThing.getDigits());
		
		
		// Do you need to store the number somewhere, and you want to restore it? You *could* serialize it and store it or something but why?
		// Just store it as char(11), and parse it back out. Easy Peasy Lemon Squeezy:
		String saved = theRightThing.getDigits(); // Just the 11 digits
		
		Kontonummer restored = Kontonummer.parseDigitString(saved);
		
		// and as you can see they're both equal:
		System.out.println(restored.equals(theRightThing));
		
		// Although digits is useful for serialization/deserialization, it's not that useful for debugging, so toString is a bit different.
		// (but if this is a bad idea, I'll relent and change it.)
		System.out.println(theRightThing);
		
		// There is some rudimentary support for custom formats. If you just want to take the three parts in order, and format them,
		// you can use applyFormat. Say you need to have underscores between numbers and surrounding it with brackets:
		System.out.println(theRightThing.applyFormat("[%s_%s_%s]"));
		// You need to use precisely three %s directives, and no other directives.
		// the underlying data is stored as three strings, and this informs how you can format it.
		// If you need to do something more advanced you can always get the separate parts, like if you need the bank registry number:
		System.out.println("Bank registry number: " + theRightThing.getBankRegistryNumber()); // This is the first part.
		// And you can always parse the numbers to ints.
		
	}
}
