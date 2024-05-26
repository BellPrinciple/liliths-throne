package com.lilithsthrone.game.character.fetishes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.attributes.CorruptionLevel;
import com.lilithsthrone.game.character.body.types.VaginaType;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.persona.SexualOrientation;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Table;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.1.?
 * @version 0.4.2
 * @author Innoxia, Maxis
 */
public interface Fetish {

	String getId();

	String getName(GameCharacter owner);

	String getShortDescriptor(GameCharacter target);

	String getDescription(GameCharacter target);

	String getFetishDesireDescription(GameCharacter target, FetishDesire desire);

	int getExperienceGainFromSexAction();

	int getRenderingPriority();

	List<String> getExtraEffects(GameCharacter owner);

	List<Colour> getColourShades();

	default List<Fetish> getFetishesForAutomaticUnlock() {
		return List.of();
	}

	default boolean isAvailable(GameCharacter character) {
		return true;
	}

	default List<String> getPerkRequirements(GameCharacter character) {
		return List.of();
	}

	default int getCost() {
		return 5;
	}

	default List<String> getModifiersAsStringList(GameCharacter owner) {
		return getExtraEffects(owner);
	}

	default Map<Attribute, Integer> getAttributeModifiers() {
		return Map.of();
	}

	default boolean isContentEnabled() {
		return true;
	}

	default Fetish getOpposite() {
		return null;
	}

	default boolean isTopFetish() {
		return false;
	}

	default String getAppliedFetishLevelEffectDescription(GameCharacter character) {
		return null;
	}

	default String applyPerkGained(GameCharacter character) {
		return "";
	}

	default String applyPerkLost(GameCharacter character){
		return "";
	}

	default Fetish getPreviousLevelPerk() {
		return null;
	}

	default Perk getNextLevelPerk() {
		return null;
	}

	default CorruptionLevel getAssociatedCorruptionLevel() {
		return CorruptionLevel.ZERO_PURE;
	}

	default Colour getColour() {
		return getColourShades().get(0);
	}

	default String getSVGString(GameCharacter owner) {
		return "";
	}

	default FetishPreference getFetishPreferenceDefault() {
		return FetishPreference.THREE_NEUTRAL;
	}

	// FETISHES:
	Fetish FETISH_ANAL_GIVING = Core.ANAL_GIVING;
	Fetish FETISH_ANAL_RECEIVING = Core.ANAL_RECEIVING;
	Fetish FETISH_VAGINAL_GIVING = Core.VAGINAL_GIVING;
	Fetish FETISH_VAGINAL_RECEIVING = Core.VAGINAL_RECEIVING;
	Fetish FETISH_ORAL_RECEIVING = Core.ORAL_RECEIVING;
	Fetish FETISH_ORAL_GIVING = Core.ORAL_GIVING;
	Fetish FETISH_BREASTS_OTHERS = Core.BREASTS_OTHERS;
	Fetish FETISH_BREASTS_SELF = Core.BREASTS_SELF;
	Fetish FETISH_LACTATION_OTHERS = Core.LACTATION_OTHERS;
	Fetish FETISH_LACTATION_SELF = Core.LACTATION_SELF;
	Fetish FETISH_LEG_LOVER = Core.LEG_LOVER;
	Fetish FETISH_STRUTTER = Core.STRUTTER;
	Fetish FETISH_FOOT_GIVING = Core.FOOT_GIVING;
	Fetish FETISH_FOOT_RECEIVING = Core.FOOT_RECEIVING;
	Fetish FETISH_ARMPIT_GIVING = Core.ARMPIT_GIVING;
	Fetish FETISH_ARMPIT_RECEIVING = Core.ARMPIT_RECEIVING;
	Fetish FETISH_PENIS_RECEIVING = Core.PENIS_RECEIVING;
	Fetish FETISH_PENIS_GIVING = Core.PENIS_GIVING;
	Fetish FETISH_CUM_STUD = Core.CUM_STUD;
	Fetish FETISH_CUM_ADDICT = Core.CUM_ADDICT;
	Fetish FETISH_DEFLOWERING = Core.DEFLOWERING;
	Fetish FETISH_PURE_VIRGIN = Core.PURE_VIRGIN;
	Fetish FETISH_MASTURBATION = Core.MASTURBATION;
	Fetish FETISH_PREGNANCY = Core.PREGNANCY;
	Fetish FETISH_IMPREGNATION = Core.IMPREGNATION;
	Fetish FETISH_TRANSFORMATION_GIVING = Core.TRANSFORMATION_GIVING;
	Fetish FETISH_TRANSFORMATION_RECEIVING = Core.TRANSFORMATION_RECEIVING;
	Fetish FETISH_KINK_GIVING = Core.KINK_GIVING;
	Fetish FETISH_KINK_RECEIVING = Core.KINK_RECEIVING;
	Fetish FETISH_DENIAL = Core.DENIAL;
	Fetish FETISH_DENIAL_SELF = Core.DENIAL_SELF;
	Fetish FETISH_DOMINANT = Core.DOMINANT;
	Fetish FETISH_SUBMISSIVE = Core.SUBMISSIVE;
	Fetish FETISH_INCEST = Core.INCEST;
	Fetish FETISH_SADIST = Core.SADIST;
	Fetish FETISH_MASOCHIST = Core.MASOCHIST;
	Fetish FETISH_NON_CON_DOM = Core.NON_CON_DOM;
	Fetish FETISH_NON_CON_SUB = Core.NON_CON_SUB;
	Fetish FETISH_BONDAGE_VICTIM = Core.BONDAGE_VICTIM;
	Fetish FETISH_BONDAGE_APPLIER = Core.BONDAGE_APPLIER;
	Fetish FETISH_EXHIBITIONIST = Core.EXHIBITIONIST;
	Fetish FETISH_VOYEURIST = Core.VOYEURIST;
	Fetish FETISH_BIMBO = Core.BIMBO;
	Fetish FETISH_CROSS_DRESSER = Core.CROSS_DRESSER;
	Fetish FETISH_SIZE_QUEEN = Core.SIZE_QUEEN;
	Fetish FETISH_SWITCH = Core.SWITCH;
	Fetish FETISH_BREEDER = Core.BREEDER;
	Fetish FETISH_SADOMASOCHIST = Core.SADOMASOCHIST;
	Fetish FETISH_LUSTY_MAIDEN = Core.LUSTY_MAIDEN;

	enum Core implements Fetish {

	// Sex types:

	ANAL_GIVING(60,
			"anal",
			"performing anal",
			"fetish_anal_giving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to perform sex actions on their partner's ass.";

			}
			else if(owner.isPlayer()) {
				return "You absolutely love giving anal sex. Using your partner's ass, claiming their asshole as your property, it all drives you crazy with lust!";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for giving anal sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "performing anal sex actions");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "anal tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.TWO_HORNY;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isAnalContentEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_ANAL_RECEIVING; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	ANAL_RECEIVING(60,
			"buttslut",
			"receiving anal",
			"fetish_anal_receiving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to be on the receiving end of anal sex actions.";

			}
			else if(owner.isPlayer()) {
				return "You absolutely love receiving anal sex. The thought of getting your ass pounded like a good little buttslut drives you crazy with lust!";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for receiving anal sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "receiving any anal attention");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "buttslut tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.TWO_HORNY;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isAnalContentEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_ANAL_GIVING; }
	},

	VAGINAL_GIVING(60,
			"vaginal",
			"performing vaginal",
			"fetish_vaginal_giving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to perform sex actions on their partner's pussy.";

			}
			else if(owner.isPlayer()) {
				return "Although typically one of the most vanilla of all sexual acts, your love for performing vaginal sex has turned into a complete obsession.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has an extreme obsession with performing vaginal sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "performing vaginal sex actions");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "pussy worship tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_VAGINAL_RECEIVING; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	VAGINAL_RECEIVING(60,
			"pussy slut",
			"receiving vaginal",
			"fetish_vaginal_receiving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to have their pussy used.";

			}
			else if(owner.isPlayer()) {
				return "Although typically one of the most vanilla of all sexual acts, your love for receiving all forms of vaginal sex has turned into a complete obsession.";
			}
			else {
				return UtilText.parse(owner, "[npc.Name] has an extreme obsession with receiving vaginal sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "receiving any vaginal attention");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "pussy slut tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_VAGINAL_GIVING; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	ORAL_RECEIVING(60,
			"oral",
			"receiving oral",
			"fetish_oral_receiving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to be on the receiving end of oral sex actions.";

			}
			else if(owner.isPlayer()) {
				return "You absolutely love receiving oral sex. Guiding your partner's head down between your legs is your favourite activity, and you're always eager for someone's lips and tongue to bring you to orgasm.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for receiving oral sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "receiving oral sex");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "oral tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_ORAL_GIVING; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	ORAL_GIVING(60,
			"oral performer",
			"giving oral",
			"fetish_oral_giving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to perform oral sex on their partner.";

			}
			else if(owner.isPlayer()) {
				return "You absolutely love giving oral sex. Going down between your partner's legs is your favourite activity, and you're always ready and eager to use your mouth to bring them to orgasm!";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for giving oral sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "performing oral sex");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "oral performer tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_ORAL_RECEIVING; }
	},

	BREASTS_OTHERS(60,
			"breasts lover",
			"others' breasts",
			"fetish_breasts_others",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to use their partner's breasts.";

			}
			else if(owner.isPlayer()) {
				return "You have an obsession with breasts. Either big or small, if someone's got a pair (or more) of tits, you're going to be finding a way to use them.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for other's breasts.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "playing with others' breasts");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "breasts lover tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_BREASTS_SELF; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	BREASTS_SELF(60,
			"breasts",
			"self breast play",
			"fetish_breasts_self",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to have their breasts used by others.";

			}
			else if(owner.isPlayer()) {
				return "You have an obsession with your breasts. You love nothing more than pleasuring your partners with them, or showing them off to your many admirers.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for using [npc.her] breasts.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "having your [npc.breasts] touched and fondled");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "breasts tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_BREASTS_OTHERS; }
	},

	LACTATION_OTHERS(60,
			"milk lover",
			"being breast-fed",
			"fetish_lactation_others",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for their partner to be lactating.";

			}
			else if(owner.isPlayer()) {
				return "You have an obsession with being breast-fed. You love nothing more than to be suckling on someone's engorged, milky teats.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for being breast-fed.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "being breast-fed");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "milk-lover tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isLactationContentEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_LACTATION_SELF; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	LACTATION_SELF(60,
			"lactation",
			"lactating",
			"fetish_lactation_self",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to have lactating breasts.";

			}
			else if(owner.isPlayer()) {
				return "You have an obsession with lactating. You love nothing more than the full, satisfying feeling of having your breasts milked.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for lactating.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "having your [npc.breasts] milked");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "lactation tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isLactationContentEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_LACTATION_OTHERS; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	LEG_LOVER(60,
			"leg lover",
			"partner's legs",
			"fetish_leg_lover",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to perform sex actions using their legs.";

			}
			else if(owner.isPlayer()) {
				return "You absolutely love legs. Using a partner's legs or thighs in sex is the ultimate turn-on for you.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for using other people's legs and thighs.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "others' legs");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "leg lover tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_STRUTTER; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	STRUTTER(60,
			"strutter",
			"having legs used",
			"fetish_strutter",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to have their legs used in a sexual manner.";

			}
			else if(owner.isPlayer()) {
				return "You absolutely love showing off your legs. Using your legs or thighs in sex is the ultimate turn-on for you.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for using [npc.her] legs or thighs in sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "using your thighs and legs in sex");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "strutter tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_LEG_LOVER; }
	},


	FOOT_GIVING(60,
			"dominant foot",
			"using feet",
			"fetish_foot_giving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to use their feet during sex.";

			}
			else if(owner.isPlayer()) {
				return "You absolutely love using your feet in sex. Getting your partner to worship and perform sexual acts on your feet and toes turns you on like nothing else.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for using [npc.her] feet in sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "using your feet in sex");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "dominant foot tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isFootContentEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_FOOT_RECEIVING; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	FOOT_RECEIVING(60,
			"submissive foot",
			"using partner's feet",
			"fetish_foot_receiving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to use their partner's feet during sex.";

			}
			else if(owner.isPlayer()) {
				return "You absolutely love feet. Getting your partner to use their feet and toes in sex is the ultimate turn-on for you.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for using other people's feet.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "others' feet");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "submissive foot tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isFootContentEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_FOOT_GIVING; }
	},

	ARMPIT_GIVING(60,
			"armpit lover",
			"performing armpit",
			"fetish_armpit_giving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to perform sex actions on their partner's armpits.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] [npc.verb(love)] nothing more than to pleasure [npc.her] partner's armpits, and even prefers it to penetrative sexual acts.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "performing armpit sex actions");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "armpit worship tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isArmpitContentEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_ARMPIT_RECEIVING; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	ARMPIT_RECEIVING(60,
			"armpit slut",
			"receiving armpit",
			"fetish_armpit_receiving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to have their armpits used.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] [npc.verb(love)] nothing more than to have [npc.her] armpits sexually serviced by [npc.her] partners, and even prefers it to penetrative sexual acts.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "receiving any armpit attention");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "armpit slut tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isArmpitContentEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_ARMPIT_GIVING; }
	},

	PENIS_GIVING(60,
			"cock stud",
			"using their cock",
			"fetish_dick_dealer",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to use their cock.";

			}
			else if(owner.isPlayer()) {
				return "You are obsessed with penetrative sex. Thrusting your cock into any available orifice is all you can think about...";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for using [npc.her] cock.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "using your cock");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "cock stud tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.TWO_HORNY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_PENIS_RECEIVING; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	PENIS_RECEIVING(60,
			"cock addict",
			"others' cocks",
			"fetish_cock_addict",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for servicing cocks.";

			}
			else if(owner.isPlayer()) {
				return "You are hopelessly addicted to cock. Large, small, fat, thin, you really don't care about the looks, just so long as it's pumping in and out of one of your holes...";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] is hopelessly addicted to cock.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "others' cocks");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "cock addict tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.TWO_HORNY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_PENIS_GIVING; }
	},

	CUM_STUD(60,
			"cum stud",
			"cumming",
			"fetish_cum",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to cum on or in their partner.";

			}
			else {
				return UtilText.parse(owner,
						"[npc.NameHasFull] a particular obsession with cumming. Pumping any and all orifices full of cum is what [npc.she] [npc.verb(love)] the most, but spurting it all over someone's body is also more than acceptable.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "any form of self-focused cum play");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "cum stud tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_CUM_ADDICT; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	CUM_ADDICT(60,
			"cum addict",
			"cum-play",
			"fetish_cum_addict",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for swallowing or being covered in cum.";

			}
			else if(owner.isPlayer()) {
				return "You are hopelessly addicted to cum. You really don't care who's providing it; all you want is for your mouth to be full of delicious, salty seed..."
						+ " Letting it slide around over your tongue, savouring every moment... Mmm... Cum really is the best...";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for cum.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "others' cum");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "cum addict tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_CUM_STUD; }
	},

	DEFLOWERING(60,
			"deflowering",
			"deflowering",
			"fetish_deflowering",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to take others' virginities.";

			}
			else if(owner.isPlayer()) {
				return "You love nothing more than claiming an innocent maiden's virginity. Although breaking in a soon-to-be slut's pussy is your favourite, you still enjoy being the first to fuck a person's ass, nipples, or throat.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for deflowering. [npc.She] loves being the one to break a girl's hymen, but also enjoys being the first to fuck a person's ass, nipples, or throat.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "taking virginities");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.FOUR_LUSTFUL;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_PURE_VIRGIN; }

		@Override
		public boolean isTopFetish() { return true; }
	},

	PURE_VIRGIN(60,
			"vaginal virginity",
			"retaining vaginal virginity",
			"fetish_virginity",
			FetishExperience.BASE_VERY_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for retaining and prizing their vaginal virginity.";

			}
			else if(owner.hasVagina()) {
				return UtilText.parse(owner, "[npc.Name] [npc.verb(prize)] [npc.her] vaginal virginity above anything else in the world. If [npc.she] [npc.was] ever to lose it, [npc.she] [npc.do]n't know how [npc.she]'d cope...");

			}
			else {
				if(owner.hasFetish(PURE_VIRGIN)) {
					return UtilText.parse(owner, "Although [npc.name] currently [npc.do]n't have a vagina, [npc.she] [npc.verb(know)] that if [npc.she] [npc.was] ever to have one, [npc.she]'d prize its virginity above anything else in the world.");

				}
				else {
					return UtilText.parse(owner, "As [npc.name] [npc.do]n't have a vagina, [npc.she] can't fetishise keeping its virginity...");
				}
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "holding onto your virginity");
		}

		@Override
		public boolean isAvailable(GameCharacter character) {
			return character.getVaginaType() != VaginaType.NONE && character.isVaginaVirgin();
		}

		@Override
		public List<String> getPerkRequirements(GameCharacter character) {
			String requiresVagina = character.getVaginaType() == VaginaType.NONE
					? "[style.colourBad(Requires vagina)]" : "[style.colourGood(Requires vagina)]";
			String requiresVaginalVirginity = character.isVaginaVirgin()
					? "[style.colourGood(Requires vaginal virginity)]" : "[style.colourBad(Requires vaginal virginity)]";
			return List.of(requiresVagina, requiresVaginalVirginity);
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ZERO_PURE;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_DEFLOWERING; }
	},

	MASTURBATION(60,
			"masturbation",
			"masturbating",
			"fetish_masturbation",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for masturbating.";

			}
			else {
				return UtilText.parse(owner, "Using [npc.her] [npc.fingers] to get either [npc.herself] or [npc.her] partners to climax is one of [npc.namePos] favourite things to do during sex.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "masturbating");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}
	},


	// SPANKING("spanking", "You love the idea of spanking or being
	// spanked during sex."),

	// Effects:
	
	IMPREGNATION(60,
			"impregnation",
			"impregnating",
			"fetish_impregnation",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for impregnating other people.";

			}
			else if(owner.isPlayer()) {
				return "You often find yourself fantasising about filling fertile wombs with your seed, and the idea of breeding your sexual partner like an animal drives you crazy with lust.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for impregnating [npc.her] partner during sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "impregnating others");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "virility tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.TWO_HORNY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_PREGNANCY; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	PREGNANCY(60,
			"pregnancy",
			"being pregnant",
			"fetish_pregnancy",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for being pregnant.";

			}
			else if(owner.isPlayer()) {
				return "You often find yourself fantasising about being impregnated, and the idea of being bred like an animal drives you crazy with lust.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for being impregnated.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "being pregnant");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "fertility tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.TWO_HORNY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_IMPREGNATION; }
	},

	TRANSFORMATION_GIVING(60,
			"transformer",
			"transforming others",
			"fetish_transformation_giving",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for transforming others.";

			}
			else if(owner.isPlayer()) {
				return "You love the idea of transforming others. Watching their bodies change, either voluntarily or otherwise, is a massive turn-on for you.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] loves transforming others. Watching their bodies change, either voluntarily or otherwise, is a massive turn-on for [npc.herHim].");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "transforming others");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.FOUR_LUSTFUL;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_TRANSFORMATION_RECEIVING; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	TRANSFORMATION_RECEIVING(60,
			"test subject",
			"being transformed",
			"fetish_transformation_receiving",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire to be transformed by others.";

			}
			else if(owner.isPlayer()) {
				return "You love the idea of being transformed. Having your body parts changed, either voluntarily or otherwise, is a massive turn-on for you.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] loves being transformed. Having [npc.her] body parts changed, either voluntarily or otherwise, is a massive turn-on for [npc.herHim].");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "the idea of being transformed");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.TWO_HORNY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_TRANSFORMATION_GIVING; }
	},

	KINK_GIVING(60,
			"kink advocate",
			"giving others fetishes",
//			"fetish_transformation_giving",
			"fetish_kink_giving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for giving new fetishes to other people.";

			}
			else if(owner.isPlayer()) {
				return "The idea of giving people new fetishes, either voluntarily or otherwise, is a massive turn-on for you.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] loves giving others new fetishes. Watching them enjoy perverse new things, either voluntarily or otherwise, is a massive turn-on for [npc.herHim].");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "giving others new fetishes");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.FOUR_LUSTFUL;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_KINK_RECEIVING; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	KINK_RECEIVING(60,
			"kink curious",
			"gaining fetishes",
//			"fetish_transformation_receiving",
			"fetish_kink_receiving",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for gaining new fetishes.";

			}
			else if(owner.isPlayer()) {
				return "You love the idea of developing new fetishes. Gaining perverse joy from new things, either voluntarily or otherwise, is a massive turn-on for you.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] loves developing new fetishes. Gaining perverse joy from new things, either voluntarily or otherwise, is a massive turn-on for [npc.herHim].");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "the idea of being given new fetishes");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_KINK_GIVING; }
	},
	
	// Behaviour (organised roughly in active/passive pairs):


	DENIAL(60,
			"orgasm denier",
			"denying orgasms",
			"fetish_denial",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for denying their partner's orgasms.";

			}
			else if(owner.isPlayer()) {
				return "Either by teasing them with your body, or preventing them from orgasming, you love denying your partners during sex.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for orgasm denial.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "denying orgasms");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_DENIAL_SELF; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	DENIAL_SELF(60,
			"self-denial",
			"being denied",
			"fetish_denial_self",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for having their orgasms denied.";

			}
			else if(owner.isPlayer()) {
				return "You love edging and having your orgasms denied.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for having [npc.her] orgasms denied.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "having your orgasms denied");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_DENIAL; }
	},

	DOMINANT(60,
			"dominant",
			"acting dominantly",
			"fetish_dominant",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for being the dominant partner in sex.";

			}
			else if(owner.isPlayer()) {
				return "You love being the dominant partner during sex, and you know just how to show your partners who's in charge.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for being the dominant partner in sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "being the dominant partner");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "dominant tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_SUBMISSIVE; }

		@Override
		public boolean isTopFetish() { return true; }
	},

	SUBMISSIVE(60,
			"submissive",
			"acting submissively",
			"fetish_submissive",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for being the submissive partner in sex.";

			}
			else if(owner.isPlayer()) {
				return "You love being the submissive partner during sex. You'll do anything to show your submission, and will happily let your partner do whatever they want with you.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for being the submissive partner in sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "being the submissive partner");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "submissive tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_DOMINANT; }
	},

	INCEST(60,
			"incest",
			"incestuous sex",
			"fetish_incest",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for having sex with their relatives.";

			}
			else if(owner.isPlayer()) {
				if(owner.getSexualOrientation() == SexualOrientation.ANDROPHILIC) {
					return "You always did have the hots for your male cousin...";
				}
				else {
					return "You always did have the hots for your aunt Lily, as well as your female cousin...";
				}

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for incestuous sex.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "having sex with your relatives");
		}
		@Override
		public String getAppliedFetishLevelEffectDescription(GameCharacter character) {
			return getAppliedFetishAttackLevelEffectDescription(character, this, "incest tease");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.FIVE_CORRUPT;
		}
		@Override
		public boolean isContentEnabled() { return Main.game.isIncestEnabled(); }
	},

	SADIST(60,
			"sadist",
			"inflicting pain",
			"fetish_sadist",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for abusing others.";

			}
			else if(owner.isPlayer()) {
				return "You love dishing out pain and humiliation, and causing others to suffer sends you absolutely wild with lust.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for dealing out pain and humiliation.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "inflicting pain and humiliation on others");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}
		@Override
		public Fetish getOpposite() { return Fetish.FETISH_MASOCHIST; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	MASOCHIST(60,
			"masochist",
			"pain and humiliation",
			"fetish_masochist",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner==null) {
				return "This fetish relates to a person's desire for being abused.";

			} else {
				return UtilText.parse(owner, "[npc.Name] [npc.verb(get)] extremely turned on when subjected to painful or humiliating experiences."
					+ " [npc.She] [npc.verb(find)] [npc.herself] getting aroused whenever [npc.her] orifices are stretched out or penetrated too deeply.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "pain and humiliation");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}
		@Override
		public Fetish getOpposite() { return Fetish.FETISH_SADIST; }
	},

	NON_CON_DOM(60,
			"non-consent",
			"raping",
			"fetish_noncon_dom",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for raping others.";

			}
			else if(owner.isPlayer()) {
				return "You love nothing more than when someone's being forced, against their will, to have sex. The more they're struggling, the more you get turned on...";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for non-consensual encounters. The more [npc.her] victim struggles, the more [npc.she] gets turned on...");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "having sex with an unwilling partner");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.FIVE_CORRUPT;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isNonConEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_NON_CON_SUB; }
		@Override
		public boolean isTopFetish() { return true; }
	},

	NON_CON_SUB(60,
			"unwilling fuck-toy",
			"being raped",
			"fetish_noncon_sub",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for being raped by others.";

			}
			else if(owner.isPlayer()) {
				return "You love nothing more than when you're forced, against your will, to have sex with someone. Struggling and pleading to be released turns you on like nothing else...";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for being the victim in non-consensual encounters. Struggling and pleading to be released turns [npc.herHim] on like nothing else...");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "being forced to have sex against your will");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.FOUR_LUSTFUL;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isNonConEnabled(); }

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_NON_CON_DOM; }
	},

	BONDAGE_APPLIER(60,
			"bondage applier",
			"applying bondage",
			"fetish_bondage_applier",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for locking others into sealed clothing.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] [npc.verb(love)] binding people up in clothing that they're unable to remove, and then taking advantage of their immobility...");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "others wearing sealed clothing");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_BONDAGE_VICTIM; }

		@Override
		public boolean isTopFetish() { return true; }
	},

	BONDAGE_VICTIM(60,
			"bondage bitch",
			"being bound",
			"fetish_bondage_victim",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for wearing sealed clothing.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] [npc.verb(love)] being bound up in clothing that [npc.sheIs] unable to remove, leaving [npc.herHim] at the mercy of others...");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "wearing sealed clothing");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_BONDAGE_APPLIER; }
	},

	EXHIBITIONIST(60,
			"exhibitionist",
			"exposing themself",
			"fetish_exhibitionist",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getShortDescriptor(GameCharacter target) {
			if(target == null) {
				return "exposing themself";
			}
			return UtilText.parse(target, "exposing [npc.herself]");
		}

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for showing off their body and having others watch them having sex.";

			}
			else if(owner.isPlayer()) {
				return "You love showing off your body, and the act of parading your naked form in public places turns you on like nothing else.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for exhibiting [npc.her] body.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "exposing yourself to others");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_VOYEURIST; }
	},

	VOYEURIST(60,
			"voyeurist",
			"watching others",
			"fetish_voyeurist",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for watching others having sex.";

			}
			else if(owner.isPlayer()) {
				return "You love watching other people... Especially while they're doing something naughty...";
			}
			else {
				return UtilText.parse(owner, "[npc.Name] has a fetish for watching other people...");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "watching others");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public Fetish getOpposite() { return Fetish.FETISH_EXHIBITIONIST; }

		@Override
		public boolean isTopFetish() { return true; }
	},

	BIMBO(60,
			"bimbo",
			"being a bimbo",
			null,
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getShortDescriptor(GameCharacter target) {
			if(target == null || target.isFeminine()) {
				return "being a bimbo";
			}
			else {
				return "being a bro";
			}
		}

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for acting like a bimbo.";

			}
			else if(owner.isFeminine()) {
				return UtilText.parse(owner,
						"[npc.NameIsFull] obsessed with the idea of acting like a complete bimbo."
								+ " It's gotten to the point where no matter how intelligent [npc.she] might actually be, [npc.she] can't imagine [npc.herself] as anything other than a ditzy airhead.");
			}
			else {
				return UtilText.parse(owner,
						"[npc.NameIsFull] obsessed with the idea of acting like a dopey surfer bro."
								+ " It's gotten to the point where no matter how intelligent [npc.she] might actually be, [npc.she] can't imagine [npc.herself] as anything other than an airheaded meathead.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			if(target == null || target.isFeminine()) {
				return getGenericFetishDesireDescription(target, desire, "acting like a bimbo");
			}
			else {
				return getGenericFetishDesireDescription(target, desire, "acting like a bro");
			}
		}

		@Override
		public List<String> getExtraEffects(GameCharacter owner) {
			if(owner == null || owner.isFeminine()) {
				return Util.newArrayListOfValues("<span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>Talk like a bimbo</span>");
			}
			else {
				return Util.newArrayListOfValues("<span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>Talk like a bro</span>");
			}
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public String getSVGString(GameCharacter owner) {
			if(owner == null || owner.isFeminine()) {
				return bimboString;
			}
			else {
				return broString;
			}
		}

		@Override
		public FetishPreference getFetishPreferenceDefault() {
			return FetishPreference.TWO_DISLIKE;
		}
	},

	CROSS_DRESSER(60,
			"cross dressing",
			"cross dressing",
			"fetish_cross_dresser",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for wearing clothes that are either too feminine or too masculine for them.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] [npc.verb(love)] wearing all manner of different clothing, and [npc.she] [npc.do]n't care if it's considered by others to be too masculine or feminine for [npc.her] body.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "wearing clothing more suited to the opposite gender");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.ONE_VANILLA;
		}

		@Override
		public FetishPreference getFetishPreferenceDefault() {
			return FetishPreference.TWO_DISLIKE;
		}
	},

	SIZE_QUEEN(60,
			"size queen",
			"deep penetrations",
			"fetish_size_queen",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for having their partners as well-hung as possible.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] [npc.verb(prefer)] [npc.her] partners to be extremely well-endowed, and [npc.verb(love)] to feel them as deep inside of [npc.herHim] as physically possible.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "taking large insertions");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}

		@Override
		public boolean isContentEnabled() { return Main.game.isPenetrationLimitationsEnabled(); }
	},
	
	// Derived fetishes:

	SWITCH(60,
			"switch",
			"being a switch",
			"fetish_switch",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for being either the dominant or submissive partner in sex.";

			}
			else if(owner.isPlayer()) {
				return "You're perfectly happy with switching between dom and sub as the situation calls for it.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] is happy to play as either the dom or sub during sex.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "switching between dom and sub");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}
	},

	BREEDER(60,
			"breeder",
			"breeding",
			"fetish_breeder",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for both being pregnant and impregnating others.";

			}
			else if(owner.isPlayer()) {
				return "You have a dream. A dream of a world in which everyone is pregnant, including yourself!";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] wants nothing more than to share [npc.her] love of pregnancies with everyone [npc.she] meets.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "anything to do with reproduction");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}
	},

	SADOMASOCHIST(60,
			"sadomasochist",
			"sadomasochism",
			"fetish_sadomasochist",
			FetishExperience.BASE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {

		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for abusing others and being abused in turn.";

			}
			else if(owner.isPlayer()) {
				return "You don't care whether you're on the giving or receiving end; if there's pain and humiliation involved, you're up for anything.";

			}
			else {
				return UtilText.parse(owner, "[npc.Name] loves pain and humiliation in all of its forms.");
			}
		}

		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "all forms of pain and humiliation");
		}

		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.THREE_DIRTY;
		}
	},

	LUSTY_MAIDEN(60,
			"lusty maiden",
			"lusty maiden",
			"fetish_lusty_maiden",
			FetishExperience.BASE_RARE_EXPERIENCE_GAIN,
			null,
			null,
			null,
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner == null) {
				return "This fetish relates to a person's desire for retaining their vaginal virginity while using their ass, breasts, or mouth to get their partners to climax.";

			}
			else if(owner.isPlayer()) {
				return "You are the ultimate tease, seducing and pleasuring others with your ass, mouth, breasts, and even the promise of your pussy,"
						+ " but you'll never actually allow anyone to penetrate your feminine sex and take your precious virginity.";
			}
			else {
				return UtilText.parse(owner, "[npc.Name] loves to pleasure others with [npc.her] ass, mouth, breasts, and even the promise of [npc.her] pussy,"
						+ " but [npc.she]'ll never actually allow anyone to penetrate [npc.her] feminine sex and take [npc.her] precious virginity.");
			}
		}
		@Override
		public String getFetishDesireDescription(GameCharacter target, FetishDesire desire) {
			return getGenericFetishDesireDescription(target, desire, "avoiding vaginal sex");
		}
		@Override
		public CorruptionLevel getAssociatedCorruptionLevel() {
			return CorruptionLevel.TWO_HORNY;
		}
	};
		private final int renderingPriority;
		private final String name;
		private final String shortDescriptor;
		private final int experienceGainFromSexAction;
		private final String SVGString;
		private static final List<String> perkRequirementsList = new ArrayList<>();
		private static final String bimboString = loadSVGString("fetish_bimbo", "FETISH_BIMBO", List.of(PresetColour.BASE_PINK));
		private static final String broString = loadSVGString("fetish_bro", "FETISH_BRO", List.of(PresetColour.BASE_BLUE));
		Core(
				int renderingPriority,
				String name,
				String shortDescriptor,
				String pathName,
				FetishExperience experienceGainFromSexAction,
				Void colourShades,
				Void attributeModifiers,
				Void extraEffects,
				Void fetishesForAutomaticUnlock) {
			this.renderingPriority = renderingPriority;
			this.name = name;
			this.shortDescriptor = shortDescriptor;
			this.experienceGainFromSexAction = experienceGainFromSexAction.getExperience();
			SVGString = pathName==null || pathName.isEmpty() ? "" : loadSVGString(pathName, getId(), getColourShades());
		}
		@Override
		public final String getId() {
			return "FETISH_" + name();
		}
		@Override
		public final String getName(GameCharacter owner) {
			if(this == BIMBO)
				return owner == null || owner.isFeminine() ? "bimbo" : "bro";
			return name;
		}
		@Override
		public String getShortDescriptor(GameCharacter target) {
			return shortDescriptor;
		}
		@Override
		public int getExperienceGainFromSexAction() {
			return experienceGainFromSexAction;
		}
		@Override
		public List<String> getModifiersAsStringList(GameCharacter owner) {
			List<String> modList = new ArrayList<>();
			for(Map.Entry<Attribute, Integer> e : getAttributeModifiers().entrySet())
				modList.add(String.format("<b>%+d</b> <b style='color:%s;'>%s</b>",
						e.getValue(),
						e.getKey().getColour().toWebHexString(),
						Util.capitaliseSentence(e.getKey().getAbbreviatedName())));
			modList.addAll(getExtraEffects(owner));
			return modList;
		}
		@Override
		public Map<Attribute, Integer> getAttributeModifiers() {
			return switch(this) {
				case IMPREGNATION -> Map.of(
						Attribute.VIRILITY, 5);
				case PREGNANCY -> Map.of(
						Attribute.FERTILITY, 5);
				case DENIAL -> Map.of(
						Attribute.RESISTANCE_PHYSICAL, 1,
						Attribute.RESISTANCE_LUST, 2);
				case DOMINANT -> Map.of(
						Attribute.MANA_MAXIMUM, 5);
				case SUBMISSIVE -> Map.of(
						Attribute.MAJOR_PHYSIQUE, 2);
				case SADIST -> Map.of(
						Attribute.DAMAGE_PHYSICAL, 5);
				case MASOCHIST -> Map.of(
						Attribute.RESISTANCE_PHYSICAL, 2);
				case BIMBO -> Map.of(
						Attribute.DAMAGE_LUST, 10);
				case SIZE_QUEEN -> Map.of(
						Attribute.RESISTANCE_PHYSICAL, 1);
				case SWITCH -> Map.of(
						Attribute.MAJOR_PHYSIQUE, 5);
				case BREEDER -> Map.of(
						Attribute.FERTILITY, 25,
						Attribute.VIRILITY, 25);
				case SADOMASOCHIST -> Map.of(
						Attribute.RESISTANCE_PHYSICAL, 3,
						Attribute.DAMAGE_PHYSICAL, 10);
				default -> Map.of();
			};
		}
		@Override
		public int getRenderingPriority() {
			return renderingPriority;
		}
		@Override
		public List<String> getExtraEffects(GameCharacter owner) {
			return switch(this) {
				case ANAL_GIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>anal tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>buttslut tease</span>");
				case ANAL_RECEIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>buttslut tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>anal tease</span>");
				case VAGINAL_GIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>pussy worship tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>pussy slut tease</span>");
				case VAGINAL_RECEIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>pussy slut tease</span> (Requires vagina)",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>pussy worship tease</span>");
				case ORAL_RECEIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>oral tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>oral performer tease</span>");
				case ORAL_GIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>oral performer tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>oral tease</span>");
				case BREASTS_OTHERS -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>breasts lover tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>breasts tease</span>");
				case BREASTS_SELF -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>breasts tease</span> (Requires breasts)",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>breasts lover tease</span>");
				case LACTATION_OTHERS -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>milk-lover tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>lactation tease</span>");
				case LACTATION_SELF -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>lactation tease</span> (Requires breasts)",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>milk-lover tease</span>");
				case LEG_LOVER -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>leg lover tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>strutter tease</span>");
				case STRUTTER -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>strutter tease</span> (Requires legs)",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>leg lover tease</span>");
				case FOOT_GIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>dominant foot tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>submissive foot tease</span>");
				case FOOT_RECEIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>submissive foot tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>dominant foot tease</span>");
				case ARMPIT_GIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>armpit worship tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>armpit slut tease</span>");
				case ARMPIT_RECEIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>armpit slut tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>armpit worship tease</span>");
				case PENIS_GIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>cock stud tease</span> (Requires penis)",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>cock addict tease</span>");
				case PENIS_RECEIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>cock addict tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>cock stud tease</span>");
				case CUM_STUD -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>cum stud tease</span> (Requires penis)",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>cum addict tease</span>");
				case CUM_ADDICT -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>cum addict tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>cum stud tease</span>");
				case DEFLOWERING -> List.of(
						"Gain <span style='color:" + PresetColour.GENERIC_EXPERIENCE.toWebHexString() + ";'>xp</span> from <span style='color:" + PresetColour.GENERIC_ARCANE.toWebHexString() + ";'>taking virginities</span>");
				case PURE_VIRGIN -> List.of(
						"[style.colourGood(Gain)] [style.colourExcellent(Pure Virgin)] status effect",
						"[style.colourBad(Suffer)] [style.colourTerrible(Broken Virgin)] status effect");
				case MASTURBATION -> List.of(
						"<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>No special abilities</span>");
				case IMPREGNATION -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>virility tease</span> (Requires penis)",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>fertility tease</span>");
				case PREGNANCY -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>fertility tease</span> (Requires vagina)",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>virility tease</span>");
				case TRANSFORMATION_GIVING -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Halves cost of all potion making</span>");
				case TRANSFORMATION_RECEIVING -> List.of(
						"[style.boldGood(Increases potency)] <span style='color:" + PresetColour.GENERIC_ARCANE.toWebHexString() + ";'>of receiving forced transformations</span>",
						"[style.boldBad(Disables)] ability to spit out TF potions");
				case KINK_GIVING -> List.of(
						// Unclear what extra effects this fetish should provide, other than triggering forced fetishes
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Enjoy making others try new things!</span>");
				case KINK_RECEIVING -> List.of(
						// Unclear what extra effects this fetish should provide, other than not taking corruption from receiving forced fetishes
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Removes corruption gain when a fetish is forced on you.</span>");
				case DOMINANT -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>dominant tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>submissive tease</span>");
				case SUBMISSIVE -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Unlocks</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>submissive tease</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Weak to</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>dominant tease</span>");
				case INCEST -> List.of(
						"Unlocks <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>incest tease</span>");
				case SADIST -> List.of(
						"[style.boldExcellent(Unlocks)] sadistic [style.colourSex(sex actions)]",
						"[style.boldExcellent(+5%)] to all [style.colourHealth(" + Attribute.HEALTH_MAXIMUM.getName() + " damage)]",
						"10% of all inflicted",
						"[style.colourHealth(" + Attribute.HEALTH_MAXIMUM.getName() + " damage)] is dealt",
						"back to you as <span style='color:" + Attribute.DAMAGE_LUST.getColour().toWebHexString() + ";'>lust damage</span>",
						"[style.boldArcane(+1 essence)] when critically",
						" hitting enemies");
				case MASOCHIST -> List.of(
						"[style.boldSex(Enjoys)] [style.boldTerrible(painful sex actions)]",
						"25% of all incoming",
						"<span style='color:"+ PresetColour.ATTRIBUTE_HEALTH.toWebHexString()+ ";'>"+Attribute.HEALTH_MAXIMUM.getName()+" damage</span>"+ " is converted",
						" to <span style='color:"+ Attribute.DAMAGE_LUST.getColour().toWebHexString()+ ";'>lust damage</span>",
						"[style.boldArcane(+1 essence)] when you're",
						" critically hit");
				case NON_CON_DOM -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Increases</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>arousal gain when partner is resisting sex</span>");
				case NON_CON_SUB -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Increases</span> <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>arousal gain when you are resisting sex</span>");
				case BONDAGE_APPLIER -> List.of(
						"[style.colourGood(Removes essence cost for sealing, servitude, and enslavement enchantments)]");
				case BONDAGE_VICTIM -> List.of(
						"[style.colourTerrible(5x cost)] to [style.colourSeal(unseal)] self-worn clothing",
						"BDSM set bonus applies [style.colourGood(positive effects)]");
				case EXHIBITIONIST -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Replaces</span> <span style='color:" + PresetColour.GENERIC_ARCANE.toWebHexString() + ";'>exposed status effects</span>"
								+ " <span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>with beneficial versions</span>");
				case VOYEURIST -> List.of(
						"<span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>Arousal boost</span> while watching sex scenes");
				case BIMBO -> List.of(
						"<span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>Talk like a bimbo</span>");
				case CROSS_DRESSER -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Immune to clothing femininity status effects</span>");
				case SIZE_QUEEN -> List.of(
						"[style.colourGood(Enjoys)] [style.colourSex(being stretched)]",
						"Treats [style.colourSex('uncomfortably deep')] insertions as being [style.colourGood('comfortable')]");
				case LUSTY_MAIDEN -> List.of(
						"<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Empowers</span> <span style='color:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + ";'>'pure virgin'</span>",
						"<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Amplifies</span> <span style='color:" + PresetColour.GENERIC_ARCANE.toWebHexString() + ";'>'broken virgin'</span>");
				default -> List.of();
			};
		}
		@Override
		public final List<Colour> getColourShades() {
			return switch(this) {
				case CUM_ADDICT -> List.of(PresetColour.CLOTHING_WHITE);
				case BONDAGE_APPLIER, BONDAGE_VICTIM -> List.of(PresetColour.CLOTHING_BLACK_STEEL, PresetColour.CLOTHING_GOLD, PresetColour.CLOTHING_GOLD);
				case SIZE_QUEEN -> List.of(PresetColour.BASE_YELLOW, PresetColour.BASE_PINK);
				default -> List.of(PresetColour.GENERIC_ARCANE);
			};
		}
		@Override
		public String getSVGString(GameCharacter owner) {
			return SVGString;
		}
		@Override
		public final List<Fetish> getFetishesForAutomaticUnlock() {
			return switch(this) {
				case SWITCH -> List.of(
						DOMINANT,
						SUBMISSIVE);
				case BREEDER -> List.of(
						PREGNANCY,
						IMPREGNATION);
				case SADOMASOCHIST -> List.of(
						SADIST,
						MASOCHIST);
				case LUSTY_MAIDEN -> Arrays.asList(
						PURE_VIRGIN,
						Main.game.isAnalContentEnabled() ? ANAL_RECEIVING : null,
						ORAL_GIVING,
						BREASTS_SELF);
				default -> List.of();
			};
		}
		private static String loadSVGString(String name, String id, List<Colour> colourShades) {
			try(InputStream is = Fetish.class.getResourceAsStream("/com/lilithsthrone/res/fetishes/" + name + ".svg")) {
				if(is==null) {
					System.err.println("Error! Fetish icon file does not exist (Trying to read from '"+name+"')!");
				}
				return SvgUtil.colourReplacement(id, colourShades, null, Util.inputStreamToString(is));
			} catch(IOException e) {
				e.printStackTrace();
			}
			return "";
		}
	}

	// Helper methods:

	private static String getAppliedFetishAttackLevelEffectDescription(GameCharacter character, Fetish fetish, String fetishAttackName) {
		FetishLevel level = character.getFetishLevel(fetish);
		return "+"+level.getBonusTeaseDamage()+" base damage to "+fetishAttackName;
	}

	// Access methods:

	Table<Fetish> table = new Table<>(s->s) {{
		for(Core c : Core.values())
			add(c.getId(),c);
	}};

	/**
	 * @param id Will be in the format of: 'innoxia_maid'.
	 */
	static Fetish getFetishFromId(String id) {
		return table.of(id);
	}

	static String getIdFromFetish(Fetish fetish) {
		return fetish.getId();
	}

	static List<Fetish> getAllFetishes() {
		return table.list();
	}

	static int getExperienceGainFromTakingVaginalVirginity(GameCharacter owner) {
		return owner.getLevel()*2;
	}

	static int getExperienceGainFromTakingOtherVirginity(GameCharacter owner) {
		return owner.getLevel();
	}

	private static String getGenericFetishDesireDescription(GameCharacter target, FetishDesire desire, String descriptor) {
		String s = switch(desire) {
			case ZERO_HATE -> "You absolutely hate ";
			case ONE_DISLIKE -> "You don't like ";
			case TWO_NEUTRAL -> "You are indifferent to ";
			case THREE_LIKE -> "You like ";
			case FOUR_LOVE -> "You love ";
		};
		return UtilText.parse(target, s + descriptor + ".");
	}
}
