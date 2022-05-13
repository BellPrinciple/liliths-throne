package com.lilithsthrone.game.character.body.valueEnums;

import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Anus;
import com.lilithsthrone.game.character.body.Ass;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.BodyPartInterface;
import com.lilithsthrone.game.character.body.BreastCrotch;
import com.lilithsthrone.game.character.body.Clitoris;
import com.lilithsthrone.game.character.body.Leg;
import com.lilithsthrone.game.character.body.Penis;
import com.lilithsthrone.game.character.body.Tail;
import com.lilithsthrone.game.character.body.Tentacle;
import com.lilithsthrone.game.character.body.Testicle;
import com.lilithsthrone.game.character.body.Vagina;
import com.lilithsthrone.game.character.body.types.LegType;
import com.lilithsthrone.game.character.body.types.WingType;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.clothing.BodyPartClothingBlock;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;

import static com.lilithsthrone.game.dialogue.PronounUtility.nameHave;
import static com.lilithsthrone.game.dialogue.PronounUtility.they;
import static com.lilithsthrone.game.dialogue.utils.UtilText.generateSingularDeterminer;

/**
 * @since 0.3.1
 * @version 0.4.0
 * @author Innoxia
 */
public enum LegConfiguration {
	
	/**
	 * This LegConfiguration is the standard for humans, demons, angels, and the vast majority of animal-morphs.
	 */
	BIPEDAL,
	
	/**
	 * This LegConfiguration is available for almost every mammalian race, with some notable exceptions being humans, demons, and angels.
	 */
	QUADRUPEDAL,

	/**
	 * This LegConfiguration is available for snakes and eels.
	 */
	TAIL_LONG,

	/**
	 * This LegConfiguration is available for fish.
	 */
	TAIL,

	/**
	 * This LegConfiguration is available for spiders and scorpions.
	 */
	ARACHNID,

	/**
	 * This LegConfiguration is available for octopuses and squids.<br/>
	 * <br/>
	 * <i>Below the thunders of the upper deep;<br/>
	 * Far far beneath in the abysmal sea,<br/>
	 * His ancient, dreamless, uninvaded sleep<br/>
	 * The Kraken sleepeth: faintest sunlights flee<br/>
	 * About his shadowy sides; above him swell<br/>
	 * Huge sponges of millennial growth and height;<br/>
	 * And far away into the sickly light,<br/>
	 * From many a wondrous grot and secret cell<br/>
	 * Unnumber'd and enormous polypi<br/>
	 * Winnow with giant arms the slumbering green.<br/>
	 * There hath he lain for ages, and will lie<br/>
	 * Battening upon huge seaworms in his sleep,<br/>
	 * Until the latter fire shall heat the deep;<br/>
	 * Then once by man and angels to be seen,<br/>
	 * In roaring he shall rise and on the surface die.</i>
	 */
	CEPHALOPOD,
	

	/**
	 * This LegConfiguration is a 'tauric' configuration for bird races.
	 */
	AVIAN,

	/**
	 * This LegConfiguration is a configuration for feral biped-ish races with wings instead of forelegs.<br/>
	 * <b>This should only ever be used for ferals!</b>
	 */
	WINGED_BIPED,
	;

	public static LegConfiguration getValueFromString(String configuration) {
		if(configuration.equalsIgnoreCase("TAUR")) {
			configuration = "QUADRUPEDAL";
		}
		return LegConfiguration.valueOf(configuration);
	}
	
	/**
	 * @return A list of BodyPartInterface classes which are considered to be fully animalistic as part of this LegConfiguration. e.g. A taur's Tail, Ass, BreastCrotch, Penis, and Vagina are all animalistic.
	 */
	public List<Class<?extends BodyPartInterface>> getFeralParts() {
		return switch(this) {
			case BIPEDAL -> List.of();
			case QUADRUPEDAL,AVIAN,WINGED_BIPED -> List.of(Ass.class,Anus.class,BreastCrotch.class,Leg.class,Tail.class,Tentacle.class,Penis.class,Testicle.class,Vagina.class,Clitoris.class);
			case TAIL_LONG,TAIL,ARACHNID,CEPHALOPOD -> List.of(Ass.class,Anus.class,Leg.class,Penis.class,Testicle.class,Vagina.class,Clitoris.class);
		};
	}
	
	/**
	 * @return true if this LegConfiguration removes the character's tail when applying its transformation.
	 */
	public boolean isTailLostOnInitialTF() {
		return switch(this) {
			case BIPEDAL,QUADRUPEDAL,AVIAN,WINGED_BIPED -> false;
			case TAIL_LONG,TAIL,ARACHNID,CEPHALOPOD -> true;
		};
	}
	
	/**
	 * @return true if this LegConfiguration prevents the character from growing a tail.
	 */
	public boolean isAbleToGrowTail() {
		return switch(this) {
			case TAIL_LONG,TAIL -> false;
			default -> true;
		};
	}
	
	public String getName() {
		return switch(this){
			case BIPEDAL->"bipedal";
			case QUADRUPEDAL->"quadrupedal";
			case TAIL_LONG->"serpent-tailed";
			case TAIL->"mer-tailed";
			case ARACHNID->"arachnid";
			case CEPHALOPOD->"cephalopod";
			case AVIAN->"avian";
			case WINGED_BIPED->"winged-biped";
		};
	}

	public String getMovementVerbPresentFirstPersonSingular() {
		return switch(this) {
			case TAIL_LONG -> "slither";
			case CEPHALOPOD -> "crawl";
			default -> "walk";
		};
	}

	public String getMovementVerbPresentThirdPersonSingular() {
		return switch(this) {
			case TAIL_LONG -> "slithers";
			case CEPHALOPOD -> "crawls";
			default -> "walks";
		};
	}

	public String getMovementVerbPresentParticiple() {
		return switch(this) {
			case TAIL_LONG -> "slithering";
			case CEPHALOPOD -> "crawling";
			default -> "walking";
		};
	}

	public String getMovementVerbPastParticiple() {
		return switch(this) {
			case TAIL_LONG -> "slithered";
			case CEPHALOPOD -> "crawled";
			default -> "walked";
		};
	}

	public String getIndividualMovementVerbPresentFirstPersonSingular() {
		return switch(this) {
			case TAIL_LONG,CEPHALOPOD -> "slide";
			default -> "step";
		};
	}

	public String getIndividualMovementVerbPresentThirdPersonSingular() {
		return switch(this) {
			case TAIL_LONG,CEPHALOPOD -> "slides";
			default -> "steps";
		};
	}

	public String getIndividualMovementVerbPresentParticiple() {
		return switch(this) {
			case TAIL_LONG,CEPHALOPOD -> "sliding";
			default -> "stepping";
		};
	}

	public String getIndividualMovementVerbPastParticiple() {
		return switch(this) {
			case TAIL_LONG,CEPHALOPOD -> "slid";
			default -> "stepped";
		};
	}
	
	public int getLandSpeedModifier() {
		return switch(this) {
			case QUADRUPEDAL -> -50;
			case TAIL_LONG -> 25;
			case ARACHNID -> -25;
			case CEPHALOPOD -> 50;
			default -> 0;
		};
	}

	public int getWaterSpeedModifier() {
		return switch(this) {
			case TAIL -> -95;
			case ARACHNID -> 100;
			case CEPHALOPOD -> -75;
			default -> 0;
		};
	}

	/**
	 * @param body The corresponding body.
	 * @return The minimum WingSize required for flight.
	 */
	public WingSize getMinimumWingSizeForFlight(Body body) {
		return body.isFeral() ? WingSize.THREE_LARGE : minimumWingSizeForFlight();
	}

	public boolean isWingsOnLegConfiguration() {
		return switch(this) {
			case QUADRUPEDAL,ARACHNID,AVIAN -> true;
			default -> false;
		};
	}

	/**
	 * @return true If this leg configuration has genitals in roughly the same place as on a biped.
	 */
	public boolean isBipedalPositionedGenitals() {
		return switch(this) {
			case QUADRUPEDAL,ARACHNID,AVIAN -> false;
			default -> true;
		};
	}

	/**
	 * @return true If this leg configuration has crotch-boobs in roughly the same place as on a biped.
	 */
	public boolean isBipedalPositionedCrotchBoobs() {
		return this!=QUADRUPEDAL;
	}

	public boolean isLargeGenitals() {
		return switch(this) {
			case QUADRUPEDAL,ARACHNID,AVIAN -> true;
			default -> false;
		};
	}

	public boolean isThighSexAvailable() {
		return switch(this) {
			case TAIL_LONG,TAIL,ARACHNID -> false;
			default -> true;
		};
	}
	
	public int getNumberOfLegs() {
		return switch(this) {
			case QUADRUPEDAL -> 4;
			case TAIL_LONG,TAIL -> 0;
			case ARACHNID,CEPHALOPOD -> 8;
			default -> 2;
		};
	}

	public List<FootStructure> getPermittedFootStructuresOverride() {
		return this!=ARACHNID ? List.of() : List.of(FootStructure.ARACHNOID);
	}
	
	public boolean isGenitalsExposed(GameCharacter character) {
		return switch(this) {
			// As genitals are beneath the arachnid body, they are not easily visible.
			// Genitals are under tentacles, so are not visible even when naked.
			case ARACHNID,CEPHALOPOD -> false;
			default -> true;
		};
	}

	public List<GenitalArrangement> getAvailableGenitalConfigurations() {
		return switch(this) {
			case BIPEDAL,QUADRUPEDAL,WINGED_BIPED -> List.of(
				GenitalArrangement.NORMAL,
				GenitalArrangement.CLOACA,
				GenitalArrangement.CLOACA_BEHIND);
			case TAIL_LONG,TAIL -> List.of(
				GenitalArrangement.CLOACA,
				// Shouldn't ever spawn by default, but give player the option
				GenitalArrangement.CLOACA_BEHIND);
			case ARACHNID -> List.of(
				GenitalArrangement.NORMAL);
			case CEPHALOPOD -> List.of(
					GenitalArrangement.CLOACA);
			case AVIAN -> List.of(
				GenitalArrangement.CLOACA_BEHIND);
		};
	}

	public String getGenericDescription() {
		return switch(this) {
			case BIPEDAL -> "The most common type of lower body; the character's legs and groin are in the same configuration as that of a regular human.";
			case QUADRUPEDAL -> "A configuration in which the character's legs and groin are replaced by the quadrupedal, feral body of the associated animal-morph, with their genitals shifting to be found in the same place as their animal equivalent."
				+ " The most common example of this is the 'centaur', in which the character's legs and groin are replaced by the body and genitals of a horse.";
			case TAIL_LONG -> "A configuration in which the character's legs and groin are replaced by an extremely long tail of the associated animal-morph, with their genitals shifting to be located within a cloaca."
				+ " The most common example of this is the 'lamia', in which the character's legs and groin are replaced by the body and genitals of a snake.";
			case TAIL -> "A configuration in which the character's legs and groin are replaced by a tail of the associated animal-morph, with their genitals shifting to be located within a cloaca."
				+ " The most common example of this is the 'mermaid', in which the character's legs and groin are replaced by the body and genitals of a fish.";
			case ARACHNID -> "A configuration in which the character's legs and groin are replaced by the eight-legged, feral body of the associated arachnid-morph, with their genitals shifting to be found in the same place as their animal equivalent."
				+ " The most common example of this is the 'arachne', in which the character's legs and groin are replaced by the body and genitals of a spider.";
			// I believe that "tentacled" is technically incorrect as a catch-all term for cephalopods, as octopuses have eight 'arms', while squids have eight arms plus two tentacles. Oh well.
			case CEPHALOPOD -> "A configuration in which the character's legs and groin are replaced by the tentacled, feral body of the associated cephalopod-morph, with their genitals shifting to be found in the same place as their animal equivalent."
				+ " The most common example of this is the 'kraken', in which the character's legs and groin are replaced by the body and genitals of a squid.";
			case AVIAN -> "A configuration in which the character's legs and groin are replaced by the avian body of the associated animal-morph, with their genitals shifting to be found in a rear-facing cloaca."
				+ " The most common example of this is the 'harpy-moa', in which a regular harpy's legs and groin are replaced by the feral body and genitals of a bird.";
			case WINGED_BIPED -> "A configuration in which the character's legs and groin are replaced by the body of the associated animal-morph, while their arm-wings are used in place of forelegs."
				+ " The most common examples of this are feral wyverns and feral bats, which both have wings instead of arms, and which use these arm-wings to walk on all fours.";
		};
	}

	public String getCrotchBoobLocationDescription() {
		return switch(this) {
			case BIPEDAL -> "Above [npc.her] groin, occupying the lower region of [npc.her] abdomen,";
			case QUADRUPEDAL -> "Down beneath the groin of [npc.her] feral body,";
			case TAIL_LONG,TAIL,CEPHALOPOD,AVIAN,WINGED_BIPED -> "Above [npc.her] groin, occupying the lower region of [npc.her] humanoid abdomen,";
			case ARACHNID -> "Occupying the lower region of [npc.her] humanoid abdomen,";
		};
	}

	public TFModifier getTFModifier() {
		return switch(this) {
			case BIPEDAL -> TFModifier.TF_MOD_LEG_CONFIG_BIPEDAL;
			case QUADRUPEDAL -> TFModifier.TF_MOD_LEG_CONFIG_TAUR;
			case TAIL_LONG -> TFModifier.TF_MOD_LEG_CONFIG_TAIL_LONG;
			case TAIL -> TFModifier.TF_MOD_LEG_CONFIG_TAIL;
			case ARACHNID -> TFModifier.TF_MOD_LEG_CONFIG_ARACHNID;
			case CEPHALOPOD -> TFModifier.TF_MOD_LEG_CONFIG_CEPHALOPOD;
			case AVIAN -> TFModifier.TF_MOD_LEG_CONFIG_AVIAN;
			case WINGED_BIPED -> TFModifier.TF_MOD_LEG_CONFIG_WINGED_BIPED;
		};
	}
	
	public void setLegsToDemon(GameCharacter character) {
		switch(this) {
		case BIPEDAL,WINGED_BIPED -> character.setLegType(LegType.DEMON_COMMON);
		case QUADRUPEDAL -> setLegsToAvailableDemonLegs(character,LegType.DEMON_HORSE_HOOFED);
		case TAIL_LONG -> setLegsToAvailableDemonLegs(character,LegType.DEMON_SNAKE);
		case TAIL -> setLegsToAvailableDemonLegs(character,LegType.DEMON_FISH);
		case ARACHNID -> setLegsToAvailableDemonLegs(character,LegType.DEMON_SPIDER);
		case CEPHALOPOD -> setLegsToAvailableDemonLegs(character,LegType.DEMON_OCTOPUS);
		case AVIAN -> setLegsToAvailableDemonLegs(character,LegType.DEMON_EAGLE);
		}
	}

	public void setLegsToAvailableDemonLegs(GameCharacter character, LegType legType) {
		this.setLegsToAvailableDemonLegs(character, legType, LegType.DEMON_COMMON);
	}

	public void setLegsToAvailableDemonLegs(GameCharacter character, LegType legType, LegType fallBackLegType) {
		character.setLegType(legType.isAvailableForSelfTransformMenu(character) ? legType : fallBackLegType);
	}

	public void setWingsToDemon(GameCharacter character) {
		character.setWingType(WingType.DEMON_COMMON);
		character.setWingSize(minimumWingSizeForFlight().getValue());
	}

	/**
	 * @return A list of BodyPartClothingBlock objects which define how this LegConfiguration is blocking InventorySlots. Returns null if it doesn't affect inventorySlots in any way.
	 */
	public List<BodyPartClothingBlock> getBodyPartClothingBlock(GameCharacter character) {
		if(character.isFeral()) {
			var tagsClothingFeral = switch(this) {
				case BIPEDAL,WINGED_BIPED -> null;
				case QUADRUPEDAL -> List.of(
					ItemTag.FITS_TAUR_BODY,
					ItemTag.FITS_FERAL_ALL_BODY,
					ItemTag.FITS_FERAL_QUADRUPED_BODY,
					ItemTag.ONLY_FITS_FERAL_ALL_BODY,
					ItemTag.ONLY_FITS_FERAL_QUADRUPED_BODY);
				case TAIL_LONG -> List.of(
					ItemTag.FITS_LONG_TAIL_BODY,
					ItemTag.FITS_FERAL_ALL_BODY,
					ItemTag.FITS_FERAL_LONG_TAIL_BODY,
					ItemTag.ONLY_FITS_FERAL_ALL_BODY,
					ItemTag.ONLY_FITS_FERAL_LONG_TAIL_BODY);
				case TAIL -> List.of(
					ItemTag.FITS_TAIL_BODY,
					ItemTag.FITS_FERAL_ALL_BODY,
					ItemTag.FITS_FERAL_TAIL_BODY,
					ItemTag.ONLY_FITS_FERAL_ALL_BODY,
					ItemTag.ONLY_FITS_FERAL_TAIL_BODY);
				case ARACHNID -> List.of(
					ItemTag.FITS_ARACHNID_BODY,
					ItemTag.FITS_FERAL_ALL_BODY,
					ItemTag.FITS_FERAL_ARACHNID_BODY,
					ItemTag.ONLY_FITS_FERAL_ALL_BODY,
					ItemTag.ONLY_FITS_FERAL_ARACHNID_BODY);
				case CEPHALOPOD -> List.of(
					ItemTag.FITS_CEPHALOPOD_BODY,
					ItemTag.FITS_FERAL_ALL_BODY,
					ItemTag.FITS_FERAL_CEPHALOPOD_BODY,
					ItemTag.ONLY_FITS_FERAL_ALL_BODY,
					ItemTag.ONLY_FITS_FERAL_CEPHALOPOD_BODY);
				case AVIAN -> List.of(
					ItemTag.FITS_AVIAN_BODY,
					ItemTag.FITS_FERAL_ALL_BODY,
					ItemTag.FITS_FERAL_AVIAN_BODY,
					ItemTag.ONLY_FITS_FERAL_ALL_BODY,
					ItemTag.ONLY_FITS_FERAL_AVIAN_BODY);
			};
			var tagsWeaponFeral = this==WINGED_BIPED
				? List.of(ItemTag.WEAPON_FERAL_EQUIPPABLE)
				: List.of(ItemTag.FITS_ARM_WINGS,ItemTag.FITS_ARM_WINGS_EXCLUSIVE);
			var blockWeaponFeral = new BodyPartClothingBlock(
				List.of(
					InventorySlot.WEAPON_MAIN_1,
					InventorySlot.WEAPON_MAIN_2,
					InventorySlot.WEAPON_MAIN_3,
					InventorySlot.WEAPON_OFFHAND_1,
					InventorySlot.WEAPON_OFFHAND_2,
					InventorySlot.WEAPON_OFFHAND_3),
				character.getLegType().getRace(),
				LegConfiguration::weaponBlockedFeral,
				tagsWeaponFeral);
			return switch(this) {
				case BIPEDAL -> null;
				// Tail races will never be feral, but just in case...
				case QUADRUPEDAL,TAIL_LONG,TAIL,ARACHNID,CEPHALOPOD,AVIAN -> List.of(
					new BodyPartClothingBlock(
						List.of(
							InventorySlot.TORSO_OVER,
							InventorySlot.TORSO_UNDER,
							InventorySlot.CHEST,
							InventorySlot.STOMACH,
							InventorySlot.HAND,
							InventorySlot.HIPS,
							InventorySlot.LEG,
							InventorySlot.FOOT,
							InventorySlot.SOCK,
							InventorySlot.GROIN),
						character.getLegType().getRace(),
						this::clothingBlockedFeral,
						tagsClothingFeral),
					blockWeaponFeral);
				case WINGED_BIPED -> List.of(blockWeaponFeral);
			};
		}
		var tagsClothing = switch(this) {
			case BIPEDAL,WINGED_BIPED -> null;
			case QUADRUPEDAL -> List.of(
				ItemTag.FITS_NON_BIPED_BODY_HUMANOID,
				ItemTag.FITS_TAUR_BODY);
			case TAIL_LONG -> List.of(
				ItemTag.FITS_NON_BIPED_BODY_HUMANOID,
				ItemTag.FITS_LONG_TAIL_BODY);
			case TAIL -> List.of(
				ItemTag.FITS_NON_BIPED_BODY_HUMANOID,
				ItemTag.FITS_TAIL_BODY);
			case ARACHNID -> List.of(
				ItemTag.FITS_NON_BIPED_BODY_HUMANOID,
				ItemTag.FITS_ARACHNID_BODY);
			case CEPHALOPOD -> List.of(
				ItemTag.FITS_NON_BIPED_BODY_HUMANOID,
				ItemTag.FITS_CEPHALOPOD_BODY);
			case AVIAN -> List.of(
				ItemTag.FITS_NON_BIPED_BODY_HUMANOID,
				ItemTag.FITS_AVIAN_BODY);
		};
		var blockLegGroin = new BodyPartClothingBlock(
			List.of(
				InventorySlot.LEG,
				InventorySlot.GROIN),
			character.getLegType().getRace(),
			this::clothingBlocked,
			tagsClothing);
		return switch(this) {
		case QUADRUPEDAL,TAIL_LONG,AVIAN -> List.of(blockLegGroin);
		// When in bipedal configuration, doesn't block any slots.
		case TAIL -> character.hasStatusEffect(StatusEffect.AQUATIC_POSITIVE) ? List.of(blockLegGroin) : null;
		case ARACHNID,CEPHALOPOD -> List.of(
				new BodyPartClothingBlock(
					List.of(
						InventorySlot.LEG,
						InventorySlot.GROIN,
//						InventorySlot.ANKLE,
						InventorySlot.FOOT,
						InventorySlot.SOCK),
					character.getLegType().getRace(),
					this::clothingBlocked,
					tagsClothing));
//for AVIAN:
//				new BodyPartClothingBlock(
//					List.of(
//						InventorySlot.FOOT,
//						InventorySlot.SOCK),
//					character.getLegType().getRace(),
//					this::clothingBlocked,
//					List.of(
//						ItemTag.FITS_TALONS_EXCLUSIVE,
//						ItemTag.FITS_TALONS))
			default -> null;
		};
	}

	public String getSubspeciesStatusEffectBackgroundPath() {
		return switch(this) {
			case BIPEDAL -> "";
			case QUADRUPEDAL -> "statusEffects/race/raceBackgroundLegQuadrupedal";
			case TAIL_LONG -> "statusEffects/race/raceBackgroundLegTailLong";
			case TAIL -> "statusEffects/race/raceBackgroundLegTailShort";
			case ARACHNID -> "statusEffects/race/raceBackgroundLegArachnid";
			case CEPHALOPOD -> "statusEffects/race/raceBackgroundLegCephalopod";
			case AVIAN,WINGED_BIPED -> "statusEffects/race/raceBackgroundLegAvian";
		};
	}
	
	/**
	 * @return How many times longer a character's serpent tail is than their height.
	 */
	public static int getDefaultSerpentTailLengthMultiplier() {
		return 5;
	}

	private static String aLegRace(GameCharacter c) {
		return generateSingularDeterminer(c.getLegRace().getName(true))+" "+c.getLegRace().getName(true);
	}

	private String clothingBlocked(GameCharacter c) {
		String type = switch(this) {
			case BIPEDAL,WINGED_BIPED -> throw new UnsupportedOperationException();
			case QUADRUPEDAL -> "taur";
			case TAIL_LONG -> "long-tail";
			case TAIL -> "tail";
			case ARACHNID -> "arachnid";
			case CEPHALOPOD -> "cephalopod";
			case AVIAN -> "avian";
		};
		return "Due to the fact that "+nameHave(c)+" the lower body of "+aLegRace(c)+", only "+type+"-suitable clothing can be worn in this slot.";
	}

	private static String weaponBlockedFeral(GameCharacter c) {
		return "Due to the fact that "+nameHave(c)+" the feral body of "+aLegRace(c)+", "+they(c)+" cannot wield regular weapons!";
	}

	private String clothingBlockedFeral(GameCharacter c) {
		String type = switch(this) {
			case BIPEDAL,WINGED_BIPED -> throw new UnsupportedOperationException();
			case QUADRUPEDAL -> "quadrupedal-taurs or quadrupedal-ferals";
			case TAIL_LONG -> "long-tails or long-tail-ferals";
			case TAIL -> "tails or tail-ferals";
			case ARACHNID -> "arachnids or arachnid-ferals";
			case CEPHALOPOD -> "cephalopods or cephalopod-ferals";
			case AVIAN -> "avians or avian-ferals";
		};
		return "Due to the fact that "+nameHave(c)+" the feral body of "+aLegRace(c)+", only clothing suitable for "+type+" can be worn in this slot.";
	}

	private WingSize minimumWingSizeForFlight() {
		return switch(this) {
			case QUADRUPEDAL,TAIL_LONG,ARACHNID -> WingSize.FOUR_HUGE;
			default -> WingSize.THREE_LARGE;
		};
	}
}
