package com.lilithsthrone.game.dialogue;

import com.lilithsthrone.game.character.GameCharacter;

public interface PronounUtility {

	/**
	 * I.e. {@code "you"}, {@code "Lilith"}, {@code "the woman"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective form.
	 */
	static String name(GameCharacter c) {
		return c.isPlayer() ? "you" : c.isPlayerKnowsName() ? c.getName(true) : c.getName("the");
	}

	/**
	 * I.e. {@code "You"}, {@code "Lilith"}, {@code "The woman"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective form.
	 */
	static String Name(GameCharacter c) {
		return c.isPlayer() ? "You" : c.isPlayerKnowsName() ? c.getName(true) : c.getName("The");
	}

	/**
	 * I.e. {@code "you"}, {@code "she"}, {@code "he"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun.
	 */
	static String they(GameCharacter c) {
		return c.isPlayer() ? "you" : c.isFeminine() ? "she" : "he";
	}

	/**
	 * I.e. {@code "You"}, {@code "She"}, {@code "He"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String They(GameCharacter c) {
		return c.isPlayer() ? "You" : c.isFeminine() ? "She" : "He";
	}

	/**
	 * I.e. {@code "you"}, {@code "her"}, {@code "him"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting objective pronoun.
	 */
	static String them(GameCharacter c) {
		return c.isPlayer() ? "you" : c.isFeminine() ? "her" : "him";
	}

	/**
	 * I.e. {@code "your"}, {@code "her"}, {@code "his"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting possessive pronoun.
	 */
	static String their(GameCharacter c) {
		return c.isPlayer() ? "your" : c.isFeminine() ? "her" : "his";
	}

	/**
	 * I.e. {@code "Your"}, {@code "Her"}, {@code "His"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting possessive pronoun.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String Their(GameCharacter c) {
		return c.isPlayer() ? "Your" : c.isFeminine() ? "Her" : "His";
	}

	/**
	 * I.e. {@code "your"}, {@code "Lilith's"}, {@code "the woman's"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting possessive pronoun.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String namePos(GameCharacter c) {
		return c.isPlayer() ? "your" : (c.isPlayerKnowsName() ? c.getName(true) : c.getName("the")) + "'s";
	}

	/**
	 * I.e. {@code "Your"}, {@code "Lilith's"}, {@code "The woman's"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting possessive pronoun.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String NamePos(GameCharacter c) {
		return c.isPlayer() ? "Your" : (c.isPlayerKnowsName() ? c.getName(true) : c.getName("The")) + "'s";
	}

	/**
	 * I.e. {@code "yours"}, {@code "hers"}, {@code "his"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting possessive pronoun.
	 */
	static String theirs(GameCharacter c) {
		return c.isPlayer() ? "yours" : c.isFeminine() ? "hers" : "his";
	}

	/**
	 * I.e. {@code "yourself"}, {@code "herself"}, {@code "himself"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting reflective pronoun.
	 */
	static String themself(GameCharacter c) {
		return c.isPlayer() ? "yourself" : c.isFeminine() ? "herself" : "himself";
	}

	/**
	 * I.e. {@code "are"}, {@code "is"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting form of <em>to be</em>.
	 */
	static String are(GameCharacter c) {
		return c.isPlayer() ? "are" : "is";
	}

	/**
	 * I.e. {@code "you do"}, {@code "Lilith does"}, {@code "the woman does"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective form and form of <em>to do</em>.
	 */
	static String nameAre(GameCharacter c) {
		return c.isPlayer() ? "you are" : (c.isPlayerKnowsName() ? c.getName(true) : c.getName("the")) + " is";
	}

	/**
	 * I.e. {@code "You do"}, {@code "Lilith does"}, {@code "The woman does"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective form and form of <em>to do</em>.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String NameAre(GameCharacter c) {
		return c.isPlayer() ? "You are" : (c.isPlayerKnowsName() ? c.getName(true) : c.getName("The")) + " is";
	}

	/**
	 * I.e. {@code "you are"}, {@code "she is"}, {@code "he is"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and full form of <em>to be</em>.
	 */
	static String theyAre(GameCharacter c) {
		return c.isPlayer() ? "you are" : c.isFeminine() ? "she is" : "he is";
	}

	/**
	 * I.e. {@code "You are"}, {@code "She is"}, {@code "He is"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and full form of <em>they are</em>.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String TheyAre(GameCharacter c) {
		return c.isPlayer() ? "You are" : c.isFeminine() ? "She is" : "He is";
	}

	/**
	 * I.e. {@code "you're"}, {@code "she's"}, {@code "he's"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and contracted form of <em>to be</em>.
	 */
	static String theyRe(GameCharacter c) {
		return c.isPlayer() ? "you're" : c.isFeminine() ? "she's" : "he's";
	}

	/**
	 * I.e. {@code "You're"}, {@code "She's"}, {@code "He's"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and contracted form of <em>they are</em>.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String TheyRe(GameCharacter c) {
		return c.isPlayer() ? "You're" : c.isFeminine() ? "She's" : "He's";
	}

	/**
	 * I.e. {@code "do"}, {@code "does"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting form of <em>to do</em>.
	 */
	static String doVerb(GameCharacter c) {
		return c.isPlayer() ? "do" : "does";
	}

	/**
	 * I.e. {@code "you do"}, {@code "Lilith does"}, {@code "the woman does"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective form and form of <em>to do</em>.
	 */
	static String nameDo(GameCharacter c) {
		return c.isPlayer() ? "you do" : (c.isPlayerKnowsName() ? c.getName(true) : c.getName("the")) + " does";
	}

	/**
	 * I.e. {@code "You do"}, {@code "Lilith does"}, {@code "The woman does"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective form and form of <em>to do</em>.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String NameDo(GameCharacter c) {
		return c.isPlayer() ? "You do" : (c.isPlayerKnowsName() ? c.getName(true) : c.getName("The")) + " does";
	}

	/**
	 * I.e. {@code "you do"}, {@code "she does"}, {@code "he does"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and form of <em>to do</em>.
	 */
	static String theyDo(GameCharacter c) {
		return c.isPlayer() ? "you do" : c.isFeminine() ? "she does" : "he does";
	}

	/**
	 * I.e. {@code "you do"}, {@code "she does"}, {@code "he does"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and form of <em>to do</em>.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String TheyDo(GameCharacter c) {
		return c.isPlayer() ? "You do" : c.isFeminine() ? "She does" : "He does";
	}

	/**
	 * I.e. {@code "have"}, {@code "has"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting form of <em>to have</em>.
	 */
	static String have(GameCharacter c) {
		return c.isPlayer() ? "have" : "has";
	}

	/**
	 * I.e. {@code "you have"}, {@code "Lilith has"}, {@code "the woman has"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting name and full form of <em>to have</em>.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String nameHave(GameCharacter c) {
		return c.isPlayer() ? "you have" : (c.isPlayerKnowsName() ? c.getName(true) : c.getName("the")) + " has";
	}

	/**
	 * I.e. {@code "You have"}, {@code "Lilith has"}, {@code "The woman has"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting name and full form of <em>to have</em>.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String NameHave(GameCharacter c) {
		return c.isPlayer() ? "You have" : (c.isPlayerKnowsName() ? c.getName(true) : c.getName("The")) + " has";
	}

	/**
	 * I.e. {@code "you have"}, {@code "she has"}, {@code "he has"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and full form of <em>to have</em>.
	 */
	static String theyHave(GameCharacter c) {
		return c.isPlayer() ? "you have" : c.isFeminine() ? "she has" : "he has";
	}

	/**
	 * I.e. {@code "you have"}, {@code "she has"}, {@code "he has"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and full form of <em>to have</em>.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String TheyHave(GameCharacter c) {
		return c.isPlayer() ? "You have" : c.isFeminine() ? "She has" : "He has";
	}

	/**
	 * I.e. {@code "you've"}, {@code "she's"}, {@code "he's"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and contracted form of <em>to have</em>.
	 */
	static String theyVe(GameCharacter c) {
		return c.isPlayer() ? "you've" : c.isFeminine() ? "she's" : "he's";
	}

	/**
	 * I.e. {@code "You've"}, {@code "She's"}, {@code "He's"}, etc.
	 * @param c
	 * Character to refer to.
	 * @return
	 * Fitting subjective pronoun and contracted form of <em>to have</em>.
	 * @see com.lilithsthrone.utils.Util#capitaliseSentence(String)
	 */
	static String TheyVe(GameCharacter c) {
		return c.isPlayer() ? "You've" : c.isFeminine() ? "She's" : "He's";
	}

	/**
	 * Suffix to add to a regular verb, depending on who is performing the action.
	 * @param c
	 * Character performing the action.
	 * @return
	 * Either {@code ""} or {@code "s"}.
	 */
	static String s(GameCharacter c) {
		return c.isPlayer() ? "" : "es";
	}

	/**
	 * Suffix to add to a regular verb, depending on who is performing the action.
	 * @param c
	 * Character performing the action.
	 * @return
	 * Either {@code ""} or {@code "es"}.
	 */
	static String es(GameCharacter c) {
		return c.isPlayer() ? "" : "es";
	}
}
