package com.lilithsthrone.game.dialogue.encounters;

/**
 * @since 0.1.69.9
 * @version 0.3.21
 * @author Innoxia, DSG
 */
public class EncounterType {

	public static final EncounterType SPECIAL_DOMINION_CULTIST = new EncounterType();

	public static final EncounterType SLAVE_USES_YOU = new EncounterType(true);
	public static final EncounterType SLAVE_USING_OTHER_SLAVE = new EncounterType(false);
	
	// Dominion:

	public static final EncounterType DOMINION_STREET_FIND_HAPPINESS = new EncounterType(); // Kinariu
	public static final EncounterType DOMINION_STREET_RENTAL_MOMMY = new EncounterType();
	public static final EncounterType DOMINION_STREET_PILL_HANDOUT = new EncounterType();

	public static final EncounterType DOMINION_FIND_ITEM = new EncounterType();
	public static final EncounterType DOMINION_FIND_CLOTHING = new EncounterType();
	public static final EncounterType DOMINION_FIND_WEAPON = new EncounterType();

	public static final EncounterType DOMINION_ALLEY_ENFORCERS = new EncounterType();
	public static final EncounterType DOMINION_ALLEY_ATTACK = new EncounterType(true);
	public static final EncounterType DOMINION_STORM_ATTACK = new EncounterType(true);

	public static final EncounterType DOMINION_EXPRESS_CENTAUR = new EncounterType();

	public static final EncounterType WES_QUEST_START = new EncounterType();
	
	
	// Harpy nests:

	public static final EncounterType HARPY_NEST_ATTACK = new EncounterType(true);
//	HARPY_NEST_ATTACK_STORM(true);
public static final EncounterType HARPY_NEST_FIND_ITEM = new EncounterType();
	
	
	// Submission:

	public static final EncounterType SUBMISSION_TUNNEL_ATTACK = new EncounterType(true);
	public static final EncounterType SUBMISSION_FIND_ITEM = new EncounterType();

	public static final EncounterType BAT_CAVERN_LURKER_ATTACK = new EncounterType(true);
	public static final EncounterType BAT_CAVERN_SLIME_ATTACK= new EncounterType(true);
	public static final EncounterType BAT_CAVERN_FIND_ITEM = new EncounterType();

	public static final EncounterType REBEL_BASE_INSANE_SURVIVOR_ATTACK = new EncounterType();

	public static final EncounterType VENGAR_CAPTIVE_SERVE = new EncounterType();
	public static final EncounterType VENGAR_CAPTIVE_GROPED = new EncounterType();
	public static final EncounterType VENGAR_CAPTIVE_VENGAR_FUCK = new EncounterType();
	public static final EncounterType VENGAR_CAPTIVE_RAT_FUCK = new EncounterType();
	public static final EncounterType VENGAR_CAPTIVE_ORAL_UNDER_TABLE = new EncounterType();
	public static final EncounterType VENGAR_CAPTIVE_GROUP_SEX = new EncounterType();

	public static final EncounterType VENGAR_CAPTIVE_CLEAN_ROOM = new EncounterType();
	public static final EncounterType VENGAR_CAPTIVE_SHADOW_SILENCE_DOMINATE = new EncounterType();
	public static final EncounterType VENGAR_CAPTIVE_ROOM_BARRED = new EncounterType();

	private final boolean opportunistic;
	
	public EncounterType() {
		opportunistic = false;
	}
	
	public EncounterType(boolean opportunistic) {
		this.opportunistic = opportunistic;
	}

	public boolean isOpportunistic() {
		return opportunistic;
	}
}
