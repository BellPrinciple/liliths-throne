package com.lilithsthrone.game.character.race;

import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

import static com.lilithsthrone.main.Main.getProperties;
import static com.lilithsthrone.utils.Util.randomItemFromValues;

/**
 * @since 0.1.0
 * @version 0.2.8
 * @author Innoxia
 */
public enum RaceStage {
	/**No animal-morph parts whatsoever.<br/>
	 * <i>"Not furry"</i> by any standard.*/
	HUMAN,
	
	/**Some minor animal-morph parts.<br/>
	 * When used in GameCharacter's setBody() method, will grant <b>only</b> hair, ears, eyes, tail, horns, antenna, and wings (no genitalia).<br/>
	 * <i>"Not furry"</i> by most standards.*/
	PARTIAL,

	/**All minor animal-morph parts (including genitalia).<br/>
	 * <i>"Borderline furry"</i> by most standards.*/
	PARTIAL_FULL,

	/**All minor animal-morph parts (including genitalia), plus animal-morph arms and legs.<br/>
	 * <i>"Low-level furry"</i> by most standards.*/
	LESSER,

	/**All minor animal-morph parts, animal-morph arms and legs, and animal-morph skin and face.<br/>
	 * <i>"Furry"</i> by all standards.*/
	GREATER,

	FERAL;

	public String getName() {
		return switch(this) {
			case HUMAN->"";
			case PARTIAL->"partial";
			case PARTIAL_FULL->"minor";
			case LESSER->"lesser";
			case GREATER->"greater";
			case FERAL->"feral";
		};
	}
	
	public Colour getColour() {
		return switch(this) {
			case HUMAN->PresetColour.TRANSFORMATION_HUMAN;
			case PARTIAL->PresetColour.TRANSFORMATION_PARTIAL;
			case PARTIAL_FULL->PresetColour.TRANSFORMATION_PARTIAL_FULL;
			case LESSER->PresetColour.TRANSFORMATION_LESSER;
			case GREATER->PresetColour.TRANSFORMATION_GREATER;
			case FERAL->PresetColour.RACE_BESTIAL;
		};
	}
	
	public static RaceStage getRaceStageFromUserPreferences(Gender gender, Subspecies subspecies) {
		var p = getProperties();
		var preference = (gender.isFeminine() ? p.getSubspeciesFeminineFurryPreferencesMap() : p.getSubspeciesMasculineFurryPreferencesMap())
		.getOrDefault(subspecies,FurryPreference.HUMAN);

		return switch(preference) {
			case HUMAN->RaceStage.HUMAN;
			case MINIMUM->RaceStage.PARTIAL;
			case REDUCED->randomItemFromValues(RaceStage.PARTIAL,RaceStage.LESSER);
			case NORMAL->randomItemFromValues(RaceStage.PARTIAL, RaceStage.LESSER, RaceStage.GREATER);
			case MAXIMUM->RaceStage.GREATER;
		};
	}

	public boolean isAntennaFurry() {
		return compareTo(HUMAN) > 0;
	}
	public boolean isArmFurry() {
		return compareTo(PARTIAL_FULL) > 0;
	}
	public boolean isAssFurry() {
		return compareTo(PARTIAL) > 0;
	}
	public boolean isBreastFurry() {
		return compareTo(PARTIAL) > 0;
	}
	public boolean isEarFurry() {
		return compareTo(HUMAN) > 0;
	}
	public boolean isEyeFurry() {
		return compareTo(HUMAN) > 0;
	}
	public boolean isFaceFurry() {
		return compareTo(LESSER) > 0;
	}
	public boolean isHairFurry() {
		return compareTo(HUMAN) > 0;
	}
	public boolean isHornFurry() {
		return compareTo(HUMAN) > 0;
	}
	public boolean isLegFurry() {
		return compareTo(PARTIAL_FULL) > 0;
	}
	public boolean isPenisFurry() {
		return compareTo(PARTIAL) > 0;
	}
	public boolean isSkinFurry() {
		return compareTo(LESSER) > 0;
	}
	public boolean isTailFurry() {
		return compareTo(HUMAN) > 0;
	}
	public boolean isTentacleFurry() {
		return compareTo(LESSER) > 0;
	}
	public boolean isVaginaFurry() {
		return compareTo(PARTIAL) > 0;
	}
	public boolean isWingFurry() {
		return compareTo(HUMAN) > 0;
	}
}
