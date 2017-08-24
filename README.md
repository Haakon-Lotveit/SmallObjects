# Smalltypes - Because writing this stuff by hand is annoying.

## Why have smalltypes?
Smalltypes is meant to provide those little convenience classes that we all should be using. We all deep down know that we shouldn't do things like ``String email;`` but we do it anyway: We don't have these things baked in. We have some of this in the Java EE XML stuff, which don't get me wrong, is great. But we really should have javax.util.Email or something. Something that threw up on you if you tried to pass it an invalid string. Something that let you say that this isn't just text, it's an email. Which means something.

It's not just email of course. You also have things like phone numbers. Phonenumbers aren't strings. They certainly aren't ints or longs. (Although you could represent them quite cleanly as longs, not all longs will be valid phone numbers.) They're their own beast. The amount of variation you'll see is staggering. Want to call a Norwegian robot number? They can have 3 digits (emergency services) up to 12 (Machine to Machine communication).

Validating these numbers are a pain. You could also concievably want to restrict the types of phone numbers of people who want to register at your site to say, cell-phones and landlines.
Smalltypes provides classes, helpers etc. to do all this for you. You can now just sit back and enjoy getting useful classes of phone numbers and their subtypes.

## What smalltypes are there?

Right now there are:

## Phone numbers (with specializing classes for Norwegian phone numbers)

This gives you validation, subtyping, and classifying of Norwegian phone numbers as well as generic phone numbers that are not covered. If you want to add your own country to this list, patches are more than welcome. The current solution does not pretend that it knows everything about a phone number, such as where it's from, but although this information could in somecases be added. (Landlines have something akin to area codes in them.)

## Norwegian bank account numbers.

This gives you validation and formatting. You can use the dotted format for the good old BBBB.GG.NNNNC, or the space format for BBBB GG NNNNC.

## Norwegian Organization Numbers (Organisasjonsnummer fra Brønnøysund). (WIP)

This does not give you anything yet as it's not ready to be added. It's what's going in next.


