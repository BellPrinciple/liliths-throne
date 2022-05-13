package com.lilithsthrone.game.combat.moves;

import java.util.List;

import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * Move type used in combat. Determines certain interactions, but doesn't have inherent power to itself.
 * 
 * @since 0.3.4
 * @version 0.3.4
 * @author Irbynx
 */
public enum CombatMoveType {
	ATTACK,
	DEFEND,
	TEASE,
	SPELL,
	POWER,
//    SKILL("Skill", PresetColour.GENERIC_GOOD),
	ATTACK_DEFEND,
	;

	public String getName() {
		return switch(this) {
			case ATTACK -> "Attack";
			case DEFEND -> "Defend";
			case TEASE -> "Tease";
			case SPELL,POWER -> "Spell";
			case ATTACK_DEFEND -> "Defensive Attack";
		};
	}

	public Colour getColour() {
		return switch(this) {
			case ATTACK -> PresetColour.DAMAGE_TYPE_PHYSICAL;
			case DEFEND,ATTACK_DEFEND -> PresetColour.SPELL_SCHOOL_WATER;
			case TEASE -> PresetColour.GENERIC_SEX;
			case SPELL,POWER -> PresetColour.GENERIC_ARCANE;
		};
	}

	public List<CombatMoveType> getCountsAsList() {
		return this!=ATTACK_DEFEND ? List.of(this) : List.of(CombatMoveType.ATTACK,CombatMoveType.DEFEND);
	}

	/**
	 * Returns true if the type can count as a different type. Use it for multitypes.
	 * @param moveTypeCompared Type to compare against
	 * @return
	 *
	 */
	public boolean countsAs(CombatMoveType moveTypeCompared) {
		return this == moveTypeCompared || getCountsAsList().contains(moveTypeCompared);
	}
}
