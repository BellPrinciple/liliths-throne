package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractLegType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.FootStructure;
import com.lilithsthrone.game.character.body.valueEnums.LegConfiguration;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.3.1
 * @author Innoxia
 */
public interface LegType extends BodyPartTypeInterface {

	FootType getFootType();

	FootStructure getDefaultFootStructure(LegConfiguration legConfiguration);

	String getFootNameSingular(GameCharacter gc);

	String getFootNamePlural(GameCharacter gc);

	String getFootDescriptor(GameCharacter gc);

	String getToeNameSingular(GameCharacter gc);

	String getToeNamePlural(GameCharacter gc);

	String getToeDescriptor(GameCharacter gc);

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	/**
	 * Applies the related leg configuration transformation for this leg type, and returns a description of the changes.<br/><br/>
	 *
	 * <b>When overriding, consider:</b><br/>
	 * Ass.class (type)<br/>
	 * BreastCrotch.class (type)<br/>
	 * Tail.class (type)<br/>
	 * Tentacle.class (type)<br/>
	 * Penis.class (type, size, cloaca)<br/>
	 * Vagina.class (type, capacity, cloaca)<br/>
	 * @param character
	 * Is being transformed.
	 * @param legConfiguration
	 * To be applied.
	 * @param applyEffects
	 * Whether the transformative effects should be applied. Pass in false to get the transformation description without applying any of the actual effects.
	 * @param applyFullEffects
	 * Pass in true if you want the additional transformations to include attribute changes (such as penis resizing, vagina capacity resetting, etc.).
	 * @return
	 * A description of the transformation.
	 */
	String applyLegConfigurationTransformation(GameCharacter character, LegConfiguration legConfiguration, boolean applyEffects, boolean applyFullEffects);

	/**
	 * For use in modifying bodies without an attached character. Outside of the Subspecies class, you should probably always be calling the version of this method that takes in a GameCharacter.
	 *
	 * @param body
	 * To be modified.
	 * @param legConfiguration
	 * To be applied.
	 * @param applyFullEffects
	 * Pass in true if you want the additional transformations to include attribute changes (such as penis resizing, vagina capacity resetting, etc.).
	 */
	void applyLegConfigurationTransformation(Body body, LegConfiguration legConfiguration, boolean applyFullEffects);

	List<LegConfiguration> getAllowedLegConfigurations();

	/**
	 * @param legConfiguration The configuration to check transformation availability of.
	 * @return True if this configuration is allowed for this LegType.
	 */
	boolean isLegConfigurationAvailable(LegConfiguration legConfiguration);

	boolean hasSpinneret();

	TentacleType getTentacleType();

	default boolean isLegsReplacedByTentacles() {
		return getTentacleType()!=TentacleType.NONE;
	}

	int getTentacleCount();

	/**
	 * @return By default, LegTypes return a modification of 0, but if a LegType requires a modifier, then this can be overridden and its effects will be handled alongside LegConfiguration's getLandSpeedModifier().
	 */
	default int getLandSpeedModifier() {
		return 0;
	}

	/**
	 * @return By default, LegTypes return a modification of 0, but if a LegType requires a modifier, then this can be overridden and its effects will be handled alongside LegConfiguration's getWaterSpeedModifier().
	 */
	default int getWaterSpeedModifier() {
		return 0;
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return gc==null || gc.getLegCount()>1;
	}

	@Override
	default TFModifier getTFModifier() {
		return getTFTypeModifier(LegType.getLegTypes(getRace()));
	}

	public static AbstractLegType HUMAN = new Special(BodyCoveringType.HUMAN,
			Race.HUMAN,
			FootStructure.PLANTIGRADE,
			FootType.HUMANOID,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine"),
			Util.newArrayListOfValues("feminine"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"They rapidly shift into normal-looking human legs, complete with human feet.<br/>"
				+ "[npc.She] now [npc.has] [style.boldHuman(human legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs and feet are human, and are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)].",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL), false) {
	};
	
	public static AbstractLegType ANGEL = new Special(BodyCoveringType.ANGEL,
			Race.ANGEL,
			FootStructure.PLANTIGRADE,
			FootType.HUMANOID,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "radiant", "angelic"),
			Util.newArrayListOfValues("feminine", "radiant", "angelic"),
			Util.newArrayListOfValues("angelic"),
			Util.newArrayListOfValues("angelic"),
			Util.newArrayListOfValues("angelic"),
			Util.newArrayListOfValues("angelic"),
			"They quickly shift into a pair of smooth, slender legs, and [npc.she] [npc.verb(let)] out a gasp as a layer of flawless, angelic skin rapidly grows to cover them."
				+ " As they finish transforming, [npc.she] almost [npc.verb(lose)] [npc.her] balance as the bones in [npc.her] feet start to shift and rearrange themselves."
				+ " After a moment, they've transformed into slender, human-like feet, ending in soft, delicate toes.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldAngel(angelic legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs and feet are human in shape, but are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)].",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL), false) {
	};

	public static AbstractLegType DEMON_COMMON = new Special(BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			FootStructure.PLANTIGRADE,
			FootType.HUMANOID,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "demonic"),
			Util.newArrayListOfValues("feminine", "flawless", "demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"-",
			"[npc.Her] legs and feet are human in shape, but are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)].",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL), false) {
		@Override
		public String getTransformationDescription(GameCharacter owner) {
			if (!owner.isShortStature()) {
				return UtilText.parse(owner,
						" They quickly shift into a pair of smooth, slender legs, and [npc.she] [npc.verb(let)] out a gasp as a layer of flawless, demonic skin rapidly grows to cover them."
						+ " As they finish transforming, [npc.she] almost [npc.verb(lose)] [npc.her] balance as the bones in [npc.her] feet start to shift and rearrange themselves."
						+ " After a moment, they've transformed into slender human-like feet, ending in soft, delicate toes.<br/>"
						+ "[npc.Name] now [npc.has] [style.boldDemon(demonic legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription]."
					+ "</p>");
			} else {
				return UtilText.parse(owner,
						" They quickly shift into a pair of smooth, slender legs, and [npc.she] [npc.verb(let)] out a gasp as a layer of flawless, impish skin rapidly grows to cover them."
						+ " As they finish transforming, [npc.she] almost [npc.verb(lose)] [npc.her] balance as the bones in [npc.her] feet start to shift and rearrange themselves."
						+ " After a moment, they've transformed into slender human-like feet, ending in soft, delicate toes.<br/>"
						+ "[npc.Name] now [npc.has] [style.boldImp(impish legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription]."
					+ "</p>");
			}
		}
	};

	public static AbstractLegType DEMON_HOOFED = new Special(BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			FootStructure.UNGULIGRADE,
			FootType.HOOFS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "demonic"),
			Util.newArrayListOfValues("feminine", "flawless", "demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"-",
			"[npc.Her] demonic legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into hard hoofs.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL), false) {
		@Override
		public String getTransformationDescription(GameCharacter owner) {
			if (!owner.isShortStature()) {
				return UtilText.parse(owner,
							" They quickly shift into a pair of smooth, slender legs, and [npc.name] [npc.verb(let)] out a gasp as a layer of flawless, demonic skin rapidly grows to cover them."
							+ " As [npc.her] new skin spreads down to the ends of [npc.her] toes, they suddenly push together, and [npc.she] [npc.verb(let)] out a cry as a thick, hoof-like nail grows in their place,"
								+ " quickly transforming to turn [npc.her] feet into hard, demonic hoofs."
							+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new skin smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
							+ "[npc.NameIsFull] left with [style.boldDemon(demonic legs and hoofed feet)], which are [npc.materialDescriptor] [npc.legFullDescription]."
						+ "</p>");
			} else {
				return UtilText.parse(owner,
						" They quickly shift into a pair of smooth, slender legs, and [npc.name] [npc.verb(let)] out a gasp as a layer of flawless, demonic skin rapidly grows to cover them."
						+ " As [npc.her] new skin spreads down to the ends of [npc.her] toes, they suddenly push together, and [npc.she] [npc.verb(let)] out a cry as a thick, hoof-like nail grows in their place,"
							+ " quickly transforming to turn [npc.her] feet into hard, demonic hoofs."
						+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new skin smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
						+ "[npc.NameIsFull] left with [style.boldImp(impish legs and hoofed feet)], which are [npc.materialDescriptor] [npc.legFullDescription]."
					+ "</p>");
			}
		}
		@Override
		public String getTransformName() {
			return "demonic-hoofed";
		}
	};

	public static AbstractLegType DEMON_HORSE_HOOFED = new Special(BodyCoveringType.HORSE_HAIR,
			Race.DEMON,
			FootStructure.UNGULIGRADE,
			FootType.HOOFS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "hair-coated", "demonic-horse"),
			Util.newArrayListOfValues("feminine", "hair-coated", "demonic-horse"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"-",
			"[npc.Her] demonic, horse-like legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into hard hoofs.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
		@Override
		public String getTransformationDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"A layer of short, horse-like hair quickly grows over [npc.her] demonic legs as they shift into a new form."
					+ " As this hair spreads down to the ends of [npc.her] toes, they suddenly push together, and [npc.she] [npc.verb(let)] out a cry as a thick, hoof-like nail grows in their place,"
						+ " before quickly transforming to turn [npc.her] feet into hard, demonic hoofs."
					+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new hair smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh."
					+ "<br/>[npc.Name] now [npc.has]"
						+ (!owner.isShortStature()
							?" [style.boldDemon(animalistic, demonic legs with hoofed feet)], which are [npc.materialDescriptor] [npc.legFullDescription]."
							:"[style.boldImp(animalistic, impish legs with hoofed feet)], which are [npc.materialDescriptor] [npc.legFullDescription]."));
		}
		@Override
		public String getTransformName() {
			return "demonic-horse";
		}
	};

	public static AbstractLegType DEMON_SNAKE = new Special(BodyCoveringType.SNAKE_SCALES,
			Race.DEMON,
			FootStructure.NONE,
			FootType.NONE,
			"a",
			"tail",
			"tails",
			Util.newArrayListOfValues("masculine", "scaly", "demonic-snake"),
			Util.newArrayListOfValues("feminine", "scaly", "demonic-snake"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"-",
			"[npc.Her] demonic, snake-like lower body is [npc.materialCompositionDescriptor] [npc.legFullDescription(true)].",
			Util.newArrayListOfValues(
					LegConfiguration.TAIL_LONG),
			false) {
		@Override
		public boolean isDefaultPlural(GameCharacter gc) {
			return false;
		}
		@Override
		public String getTransformationDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"A layer of smooth scales quickly grow over [npc.her] demonic legs as they take on a snake-like appearance."
					+ " Quickly coming to an end, the transformation leaves [npc.name] with [npc.her] new scales smoothly transitioning into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh."
					+ "<br/>[npc.Name] now [npc.has]"
						+ (!owner.isShortStature()
							?" [style.boldDemon(demonic, snake-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."
							:"[style.boldImp(impish, snake-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."));
		}
		@Override
		public String getTransformName() {
			return "demonic-snake";
		}
		@Override
		public boolean isAvailableForSelfTransformMenu(GameCharacter gc) {
			return gc.hasPerkAnywhereInTree(Perk.POWER_OF_LYXIAS_6); //TODO?
		}
	};

	public static AbstractLegType DEMON_SPIDER = new Special(BodyCoveringType.SPIDER_CHITIN,
			Race.DEMON,
			FootStructure.DIGITIGRADE,
			FootType.ARACHNID,
			"a",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "arachnid", "demonic-spider"),
			Util.newArrayListOfValues("feminine", "arachnid", "demonic-spider"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"-",
			"[npc.Her] demonic, spider-like lower body is [npc.materialCompositionDescriptor] [npc.legFullDescription(true)].",
			Util.newArrayListOfValues(
					LegConfiguration.ARACHNID),
			true) {
		@Override
		public String getTransformationDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"A layer of smooth chitin quickly grows over [npc.her] demonic [npc.legs] as they take on a spider-like appearance."
					+ " Quickly coming to an end, the transformation leaves [npc.name] with [npc.her] new chitin smoothly transitioning into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh."
					+ "<br/>[npc.Name] now [npc.has]"
						+ (!owner.isShortStature()
							?" [style.boldDemon(demonic, spider-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."
							:"[style.boldImp(impish, spider-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."));
		}
		@Override
		public String getTransformName() {
			return "demonic-spider";
		}
		@Override
		public boolean isAvailableForSelfTransformMenu(GameCharacter gc) {
			return gc.hasPerkAnywhereInTree(Perk.POWER_OF_LUNETTE_5); //TODO?
		}
	};

	public static AbstractLegType DEMON_OCTOPUS = new Special(BodyCoveringType.OCTOPUS_SKIN,
			Race.DEMON,
			FootStructure.TENTACLED,
			FootType.TENTACLE,
			"a",
			"tentacle",
			"tentacles",
			Util.newArrayListOfValues("masculine", "cephalopod", "demonic-octopus"),
			Util.newArrayListOfValues("feminine", "cephalopod", "demonic-octopus"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"-",
			"[npc.Her] demonic, octopus-like lower body is [npc.materialCompositionDescriptor] [npc.legFullDescription(true)].",
			Util.newArrayListOfValues(
					LegConfiguration.CEPHALOPOD),
			false) {
		@Override
		public String getTransformationDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"A layer of smooth skin quickly grows over [npc.her] demonic [npc.legs] as they alarmingly take on a tentacle-like appearance."
					+ " Quickly coming to an end, the powerful transformation leaves [npc.name] with [npc.her] new skin smoothly transitioning into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh."
					+ "<br/>[npc.Name] now [npc.has]"
						+ (!owner.isShortStature()
							?" [style.boldDemon(demonic, octopus-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."
							:"[style.boldImp(impish, octopus-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."));
		}
		@Override
		public String getTransformName() {
			return "demonic-octopus";
		}
		@Override
		public TentacleType getTentacleType() {
			return TentacleType.LEG_DEMON_OCTOPUS;
		}
		public int getTentacleCount() {
			return 8;
		}
		@Override
		public boolean isAvailableForSelfTransformMenu(GameCharacter gc) {
			return gc.hasPerkAnywhereInTree(Perk.POWER_OF_LIRECEA_1); //TODO?
		}
	};

	public static AbstractLegType DEMON_FISH = new Special(BodyCoveringType.FISH_SCALES,
			Race.DEMON,
			FootStructure.NONE,
			FootType.NONE,
			"a",
			"tail",
			"tails",
			Util.newArrayListOfValues("masculine", "scaly", "demonic-fish"),
			Util.newArrayListOfValues("feminine", "scaly", "demonic-fish"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"-",
			"[npc.Her] demonic, fish-like lower body is [npc.materialCompositionDescriptor] [npc.legFullDescription(true)].",
			Util.newArrayListOfValues(
					LegConfiguration.TAIL),
			false) {
		@Override
		public boolean isDefaultPlural(GameCharacter gc) {
			return gc.hasStatusEffect(StatusEffect.AQUATIC_NEGATIVE);
		}
		@Override
		public String getNameSingular(GameCharacter gc) {
			if(gc.hasStatusEffect(StatusEffect.AQUATIC_NEGATIVE)) {
				return "leg";
			} else {
				return "tail";
			}
		}
		@Override
		public String getNamePlural(GameCharacter gc) {
			if(gc.hasStatusEffect(StatusEffect.AQUATIC_NEGATIVE)) {
				return "legs";
			} else {
				return "tails";
			}
		}
		@Override
		public String getTransformationDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"A layer of smooth scales quickly grow over [npc.her] demonic legs as they take on a fish-like appearance."
					+ " Quickly coming to an end, the transformation leaves [npc.name] with [npc.her] new scales smoothly transitioning into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh."
					+ "<br/>[npc.Name] now [npc.has]"
						+ (!owner.isShortStature()
							?" [style.boldDemon(demonic, fish-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."
							:"[style.boldImp(impish, fish-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."));
		}
		@Override
		public String getTransformName() {
			return "demonic-fish";
		}
		@Override
		public boolean isAvailableForSelfTransformMenu(GameCharacter gc) {
			return gc.hasPerkAnywhereInTree(Perk.POWER_OF_LIRECEA_1); //TODO?
		}
	};

	public static AbstractLegType DEMON_EAGLE = new Special(BodyCoveringType.FEATHERS,
			Race.DEMON,
			FootStructure.DIGITIGRADE,
			FootType.TALONS,
			"a",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "clawed", "anthropomorphic, bird-like"),
			Util.newArrayListOfValues("feminine", "clawed", "anthropomorphic, bird-like"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"-",
			"[npc.Her] demonic, bird-like lower body is [npc.materialCompositionDescriptor] [npc.legFullDescription(true)].",
			Util.newArrayListOfValues(
					LegConfiguration.AVIAN),
			false) {
		@Override
		public String getTransformationDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"A layer of feathers quickly grows over [npc.her] demonic [npc.legs] as they take on a bird-like appearance."
					+ " Quickly coming to an end, the transformation leaves [npc.name] with [npc.her] new feathers smoothly transitioning into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh."
					+ "<br/>[npc.Name] now [npc.has]"
						+ (!owner.isShortStature()
							?" [style.boldDemon(demonic, bird-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."
							:"[style.boldImp(impish, bird-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription]."));
		}
		@Override
		public String getTransformName() {
			return "demonic-eagle";
		}
		@Override
		public boolean isAvailableForSelfTransformMenu(GameCharacter gc) {
			return gc.hasPerkAnywhereInTree(Perk.POWER_OF_LISOPHIA_7); //TODO?
		}
	};
	
	public static AbstractLegType COW_MORPH = new Special(BodyCoveringType.BOVINE_FUR,
			Race.COW_MORPH,
			FootStructure.UNGULIGRADE,
			FootType.HOOFS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "hoofed", "furry", "fur-coated", "anthropomorphic, cow-like"),
			Util.newArrayListOfValues("feminine", "hoofed", "furry", "fur-coated", "anthropomorphic, cow-like"),
			Util.newArrayListOfValues("cow-like", "bovine"),
			Util.newArrayListOfValues("cow-like", "bovine"),
			Util.newArrayListOfValues("cow-like", "bovine"),
			Util.newArrayListOfValues("cow-like", "bovine"),
			"A layer of short, cow-like hair quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, they suddenly push together, and [npc.she] [npc.verb(let)] out a cry as a thick, hoof-like nail grows in their place,"
					+ " before quickly transforming to turn [npc.her] feet into cow-like hoofs."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldCowMorph(cow-like legs and hoofed feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic cow-like hoofs.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType DOG_MORPH = new Special(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			FootStructure.DIGITIGRADE,
			FootType.PAWS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "furry", "fur-coated", "anthropomorphic, dog-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "anthropomorphic, dog-like"),
			Util.newArrayListOfValues("dog-like", "canine"),
			Util.newArrayListOfValues("dog-like", "canine"),
			Util.newArrayListOfValues("dog-like", "canine"),
			Util.newArrayListOfValues("dog-like", "canine"),
			"A layer of dog-like fur quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, [npc.her] toenails thicken into little blunt claws, and leathery pads grow to cover [npc.her] soles, leaving [npc.herHim] with paw-like feet."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldDogMorph(dog-like legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic dog-like paws, complete with little blunt claws and leathery pads.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType WOLF_MORPH = new Special(BodyCoveringType.LYCAN_FUR,
			Race.WOLF_MORPH,
			FootStructure.DIGITIGRADE,
			FootType.PAWS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "furry", "fur-coated", "anthropomorphic, wolf-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "anthropomorphic, wolf-like"),
			Util.newArrayListOfValues("wolf-like", "lupine"),
			Util.newArrayListOfValues("wolf-like", "lupine"),
			Util.newArrayListOfValues("wolf-like", "lupine"),
			Util.newArrayListOfValues("wolf-like", "lupine"),
			"A layer of wolf-like fur quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, [npc.her] toenails thicken into sharp claws, and tough leathery pads grow to cover [npc.her] soles, leaving [npc.herHim] with paw-like feet."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldWolfMorph(wolf-like legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic wolf-like paws, complete with sharp claws and tough leathery pads.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType FOX_MORPH = new Special(BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			FootStructure.DIGITIGRADE,
			FootType.PAWS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "furry", "fur-coated", "anthropomorphic, fox-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "anthropomorphic, fox-like"),
			Util.newArrayListOfValues("fox-like", "vulpine"),
			Util.newArrayListOfValues("fox-like", "vulpine"),
			Util.newArrayListOfValues("fox-like", "vulpine"),
			Util.newArrayListOfValues("fox-like", "vulpine"),
			"A layer of fox-like fur quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, [npc.her] toenails thicken into sharp claws, and little pads grow to cover [npc.her] soles, leaving [npc.herHim] with paw-like feet."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.</br>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldFoxMorph(fox-like legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic fox-like paws, complete with sharp claws and tough leathery pads.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType SQUIRREL_MORPH = new Special(BodyCoveringType.SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			FootStructure.PLANTIGRADE,
			FootType.PAWS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "furry", "fur-coated", "anthropomorphic, squirrel-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "anthropomorphic, squirrel-like"),
			Util.newArrayListOfValues("squirrel-like"),
			Util.newArrayListOfValues("squirrel-like"),
			Util.newArrayListOfValues("squirrel-like"),
			Util.newArrayListOfValues("squirrel-like"),
			"A layer of squirrel-like fur quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, [npc.her] toenails thicken into sharp claws, and little pink pads grow to cover [npc.her] soles, leaving [npc.herHim] with paw-like feet."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldSquirrelMorph(squirrel-like legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic squirrel-like paws, complete with claws and pink pads.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType RAT_MORPH = new Special(BodyCoveringType.RAT_FUR,
			Race.RAT_MORPH,
			FootStructure.PLANTIGRADE,
			FootType.PAWS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "furry", "fur-coated", "anthropomorphic, rat-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "anthropomorphic, rat-like"),
			Util.newArrayListOfValues("rat-like"),
			Util.newArrayListOfValues("rat-like"),
			Util.newArrayListOfValues("rat-like"),
			Util.newArrayListOfValues("rat-like"),
			"A layer of rat-like fur quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, [npc.her] toenails thicken into sharp claws, and little pink pads grow to cover [npc.her] soles, leaving [npc.herHim] with paw-like feet."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldRatMorph(rat-like legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic rat-like paws, complete with claws and leathery pads.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType RABBIT_MORPH = new Special(BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			FootStructure.PLANTIGRADE,
			FootType.PAWS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "furry", "fur-coated", "anthropomorphic, rabbit-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "anthropomorphic, rabbit-like"),
			Util.newArrayListOfValues("rabbit-like"),
			Util.newArrayListOfValues("rabbit-like"),
			Util.newArrayListOfValues("rabbit-like"),
			Util.newArrayListOfValues("rabbit-like"),
			"A layer of rabbit-like fur quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, [npc.her] toenails thicken into blunt claws, and soft little pads grow to cover [npc.her] soles, leaving [npc.herHim] with long, paw-like feet."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldRabbitMorph(rabbit-like legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into long, anthropomorphic, rabbit-like paws, complete with blunt claws and soft pads.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType BAT_MORPH = new Special(BodyCoveringType.BAT_FUR,
			Race.BAT_MORPH,
			FootStructure.DIGITIGRADE,
			FootType.PAWS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "furry", "fur-coated", "anthropomorphic, bat-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "anthropomorphic, bat-like"),
			Util.newArrayListOfValues("bat-like"),
			Util.newArrayListOfValues("bat-like"),
			Util.newArrayListOfValues("bat-like"),
			Util.newArrayListOfValues("bat-like"),
			"A layer of bat-like fur quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, [npc.her] toenails thicken into sharp claws, and little pink pads grow to cover [npc.her] soles, leaving [npc.herHim] with paw-like feet."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldBatMorph(bat-like legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic bat-like paws, complete with claws and leathery pads.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL), false) {
	};
	
	public static AbstractLegType CAT_MORPH = new Special(BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			FootStructure.DIGITIGRADE,
			FootType.PAWS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "furry", "fur-coated", "anthropomorphic, cat-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "anthropomorphic, cat-like"),
			Util.newArrayListOfValues("cat-like", "feline"),
			Util.newArrayListOfValues("cat-like", "feline"),
			Util.newArrayListOfValues("cat-like", "feline"),
			Util.newArrayListOfValues("cat-like", "feline"),
			"A layer of cat-like fur quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, [npc.her] toenails thicken into sharp, retractable claws, and little pink pads grow to cover [npc.her] soles, leaving [npc.herHim] with paw-like feet."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldCatMorph(cat-like legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic cat-like paws, complete with retractable claws and pink pads.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType ALLIGATOR_MORPH = new Special(BodyCoveringType.ALLIGATOR_SCALES,
			Race.ALLIGATOR_MORPH,
			FootStructure.PLANTIGRADE,
			FootType.HUMANOID,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "scaly", "reptilian", "anthropomorphic, alligator-like"),
			Util.newArrayListOfValues("feminine", "scaly", "reptilian", "anthropomorphic, alligator-like"),
			Util.newArrayListOfValues("alligator-like", "scaly", "reptilian"),
			Util.newArrayListOfValues("alligator-like", "scaly", "reptilian"),
			Util.newArrayListOfValues("alligator-like", "scaly", "reptilian"),
			Util.newArrayListOfValues("alligator-like", "scaly", "reptilian"),
			"A layer of alligator-like scales quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new scales spread down to the ends of [npc.her] toes, [npc.her] toenails thicken into sharp claws, and little scales grow to cover [npc.her] soles, leaving [npc.herHim] with alligator-like feet."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new scales smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldGatorMorph(alligator-like legs and feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic alligator-like feet, complete with sharp claws.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType HORSE_MORPH = new Special(BodyCoveringType.HORSE_HAIR,
			Race.HORSE_MORPH,
			FootStructure.UNGULIGRADE,
			FootType.HOOFS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "hair-coated", "anthropomorphic, horse-like"),
			Util.newArrayListOfValues("feminine", "hair-coated", "anthropomorphic, horse-like"),
			Util.newArrayListOfValues("horse-like", "equine"),
			Util.newArrayListOfValues("horse-like", "equine"),
			Util.newArrayListOfValues("horse-like", "equine"),
			Util.newArrayListOfValues("horse-like", "equine"),
			"A layer of short, horse-like hair quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, they suddenly push together, and [npc.she] [npc.verb(let)] out a cry as a thick, hoof-like nail grows in their place,"
					+ " before quickly transforming to turn [npc.her] feet into horse-like hoofs."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new hair smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldHorseMorph(horse-like legs and hoofed feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic horse-like hoofs.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};

//	public static AbstractLegType HORSE_FISH = new Special(BodyCoveringType.FISH_SCALES,
//			Race.HORSE_MORPH,
//			FootStructure.PLANTIGRADE, // FootStructure and Type is for when legs are grown on land.
//			FootType.HUMANOID,
//			"a",
//			"tail",
//			"tails",
//			Util.newArrayListOfValues("masculine", "scaly", "fish"),
//			Util.newArrayListOfValues("feminine", "scaly", "fish"),
//			Util.newArrayListOfValues("horse"),
//			Util.newArrayListOfValues("horse"),
//			Util.newArrayListOfValues("horse"),
//			Util.newArrayListOfValues("horse"),
//			"-",
//			"[npc.Her] fish-like lower body is [npc.materialCompositionDescriptor] [npc.legFullDescription(true)].",
//			Util.newArrayListOfValues(
//					LegConfiguration.TAIL),
//			false) {
//		@Override
//		public boolean isDefaultPlural(GameCharacter gc) {
//			return gc.hasStatusEffect(StatusEffect.AQUATIC_NEGATIVE);
//		}
//		@Override
//		public String getNameSingular(GameCharacter gc) {
//			if(gc.hasStatusEffect(StatusEffect.AQUATIC_NEGATIVE)) {
//				return "leg";
//			} else {
//				return "tail";
//			}
//		}
//		@Override
//		public String getNamePlural(GameCharacter gc) {
//			if(gc.hasStatusEffect(StatusEffect.AQUATIC_NEGATIVE)) {
//				return "legs";
//			} else {
//				return "tails";
//			}
//		}
//		@Override
//		public String getTransformationDescription(GameCharacter owner) {
//			return UtilText.parse(owner,
//					"A layer of smooth scales quickly grow over [npc.her] legs as they take on a fish-like appearance."
//					+ " Quickly coming to an end, the transformation leaves [npc.name] with [npc.her] new scales smoothly transitioning into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh."
//					+ "<br/>[npc.Name] now [npc.has] [style.boldHorseMorph(fish-like legs)], which are [npc.materialDescriptor] [npc.legFullDescription].");
//		}
//		@Override
//		public String getTransformName() {
//			return "hippocampus";
//		}
//	};
	
	public static AbstractLegType REINDEER_MORPH = new Special(BodyCoveringType.REINDEER_FUR,
			Race.REINDEER_MORPH,
			FootStructure.UNGULIGRADE,
			FootType.HOOFS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "fur-coated", "anthropomorphic, reindeer-like"),
			Util.newArrayListOfValues("feminine", "fur-coated", "anthropomorphic, reindeer-like"),
			Util.newArrayListOfValues("reindeer-like"),
			Util.newArrayListOfValues("reindeer-like"),
			Util.newArrayListOfValues("reindeer-like"),
			Util.newArrayListOfValues("reindeer-like"),
			"A layer of furry, reindeer-like hair quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new fur spreads down to the ends of [npc.her] toes, they suddenly push together, and [npc.she] [npc.verb(let)] out a cry as a crescent-shaped, cloven hoof grows in their place,"
					+ " before quickly transforming to turn [npc.her] feet into reindeer-like hoofs."
				+ " As the transformation ends, [npc.she] [npc.verb(see)] that [npc.her] new fur smoothly transitions into the [npc.skin] covering the rest of [npc.her] body at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldReindeerMorph(reindeer-like legs and hoofed feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], and [npc.her] feet are formed into anthropomorphic reindeer-like hoofs.",
			Util.newArrayListOfValues(
					LegConfiguration.BIPEDAL,
					LegConfiguration.QUADRUPEDAL), false) {
	};
	
	public static AbstractLegType HARPY = new Special(BodyCoveringType.HARPY_SKIN,
			Race.HARPY,
			FootStructure.DIGITIGRADE,
			FootType.TALONS,
			"a pair of",
			"leg",
			"legs",
			Util.newArrayListOfValues("masculine", "clawed", "anthropomorphic, bird-like"),
			Util.newArrayListOfValues("feminine", "clawed", "anthropomorphic, bird-like"),
			Util.newArrayListOfValues("bird-like"),
			Util.newArrayListOfValues("bird-like"),
			Util.newArrayListOfValues("bird-like"),
			Util.newArrayListOfValues("bird-like"),
			"A layer of scaly, bird-like leather quickly grows over [npc.her] legs as they shift into a new form."
				+ " As [npc.her] new leathery skin spreads down to the ends of [npc.her] toes, [npc.her] feet start to undergo an extreme transformation."
				+ " [npc.Her] toes combine together and re-shape themselves into three forward-facing talons, as a fourth, thumb-like talon branches out behind them."
				+ " As the transformation ends, this leathery skin sharply transitions into [npc.her] body's [npc.skinColour] [npc.skin] at [npc.her] upper-thigh.<br/>"
				+ "[npc.Name] now [npc.has] anthropomorphic, [style.boldHarpy(bird-like legs and talons in place of feet)], which are [npc.materialDescriptor] [npc.legFullDescription].",
			"[npc.Her] bird-like legs are [npc.materialCompositionDescriptor] [npc.legFullDescription(true)], which sharply transitions into [npc.her] body's [npc.skinColour] [npc.skin] at [npc.her] upper-thigh."
				+ " At the end of each of [npc.her] [npc.legs], [npc.she] [npc.has] sharp, bird-like talons.",
				Util.newArrayListOfValues(
						LegConfiguration.BIPEDAL,
						LegConfiguration.AVIAN), false) {
	};

	class Special extends AbstractLegType {

		private String id;

		public Special(BodyCoveringType coveringType, Race race, FootStructure defaultFootStructure, FootType footType, String determiner, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, List<String> footDescriptorsMasculine, List<String> footDescriptorsFeminine, List<String> toeDescriptorsMasculine, List<String> toeDescriptorsFeminine, String legTransformationDescription, String legBodyDescription, List<LegConfiguration> allowedLegConfigurations, boolean spinneret) {
			super(coveringType, race, defaultFootStructure, footType, determiner, name, namePlural, descriptorsMasculine, descriptorsFeminine, footDescriptorsMasculine, footDescriptorsFeminine, toeDescriptorsMasculine, toeDescriptorsFeminine, legTransformationDescription, legBodyDescription, allowedLegConfigurations, spinneret);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(LegType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<LegType> table = new TypeTable<>(
		LegType::sanitize,
		LegType.class,
		AbstractLegType.class,
		"leg",
		(f,n,a,m)->new AbstractLegType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	static LegType getLegTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP")) {
			return "DEMON_COMMON";
		}
		if(id.equals("LYCAN")) {
			return "WOLF_MORPH";
		}

		return id;
	}

	static String getIdFromLegType(LegType legType) {
		return legType.getId();
	}

	static List<LegType> getAllLegTypes() {
		return table.listByRace();
	}

	static List<LegType> getLegTypes(Race r) {
		return table.of(r).orElse(List.of());
	}

}