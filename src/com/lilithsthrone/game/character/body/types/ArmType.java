package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractArmType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.clothing.BodyPartClothingBlock;
import com.lilithsthrone.utils.Util;

/**
 * Contains static instances of AbstractArmType.
 * 
 * @since 0.1.0
 * @version 0.3
 * @author Innoxia
 */
public interface ArmType extends BodyPartTypeInterface {

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	default boolean isUnderarmHairAllowed() {
		return getRace().getRacialClass().isAnthroHair();
	}

	default boolean allowsFlight() {
		return false;
	}

	default String getHandsNameSingular(GameCharacter c) {
		return "hand";
	}

	default String getHandsNamePlural(GameCharacter c) {
		return "hands";
	}

	default String getHandsDescriptor(GameCharacter c) {
		return getDescriptor(c);
	}

	default String getFingersNameSingular(GameCharacter c) {
		return "finger";
	}

	default String getFingersNamePlural(GameCharacter c) {
		return "fingers";
	}

	default String getFingersDescriptor(GameCharacter c) {
		return getHandsDescriptor(c);
	}

	@Override
	default boolean isDefaultPlural(GameCharacter c) {
		return true;
	}

	@Override
	default String getDeterminer(GameCharacter c) {
		int armRows = c.getArmRows();
		return armRows == 1 ? "a pair of" : Util.intToString(armRows)+" pairs of";
	}

	@Override
	default String getNameSingular(GameCharacter c) {
		return "arm";
	}

	@Override
	default String getNamePlural(GameCharacter c) {
		return "arms";
	}

	@Override
	default String getDescriptor(GameCharacter c) {
		return "";
	}

	@Override
	default BodyPartClothingBlock getBodyPartClothingBlock() {
		if(getTags().contains(BodyPartTag.ARM_WINGS_FEATHERED)) {
			return new BodyPartClothingBlock(
				List.of(
					InventorySlot.HAND,
					InventorySlot.WRIST,
					InventorySlot.TORSO_OVER,
					InventorySlot.TORSO_UNDER),
				Race.HARPY,
				"Due to the fact that [npc.nameHasFull] bird-like wings instead of arms, only specialist clothing can be worn in this slot.",
				List.of(
					ItemTag.FITS_FEATHERED_ARM_WINGS,
					ItemTag.FITS_FEATHERED_ARM_WINGS_EXCLUSIVE,
					ItemTag.FITS_ARM_WINGS,
					ItemTag.FITS_ARM_WINGS_EXCLUSIVE
				));
		}
		if(getTags().contains(BodyPartTag.ARM_WINGS_LEATHERY)) {
			return new BodyPartClothingBlock(
				List.of(
					InventorySlot.HAND,
					InventorySlot.WRIST,
					InventorySlot.TORSO_OVER,
					InventorySlot.TORSO_UNDER),
				Race.BAT_MORPH,
				"Due to the fact that [npc.nameHasFull] leathery wings instead of arms, only specialist clothing can be worn in this slot.",
				List.of(
					ItemTag.FITS_LEATHERY_ARM_WINGS,
					ItemTag.FITS_LEATHERY_ARM_WINGS_EXCLUSIVE,
					ItemTag.FITS_ARM_WINGS,
					ItemTag.FITS_ARM_WINGS_EXCLUSIVE
				));
		}
		return null;
	}

	public static AbstractArmType HUMAN = new Special(BodyCoveringType.HUMAN,
			Race.HUMAN,
			"arm",
			"arms",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"hand",
			"hands",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("soft", "feminine"),
			"finger",
			"fingers",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("soft", "feminine"),
			"Thankfully, the transformation only lasts a matter of moments, leaving [npc.herHim] with normal-looking human arms, complete with human hands.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldHuman(human arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] normal human arms and hands, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)].") {
	};

	public static AbstractArmType ANGEL = new Special(BodyCoveringType.HUMAN,
			Race.ANGEL,
			"arm",
			"arms",
			Util.newArrayListOfValues("delicate"),
			Util.newArrayListOfValues("delicate"),
			"hand",
			"hands",
			Util.newArrayListOfValues("delicate", "soft"),
			Util.newArrayListOfValues("delicate", "soft", "feminine"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("delicate", "soft"),
			Util.newArrayListOfValues("delicate", "soft", "feminine"),
			"Within a matter of moments, they've changed into slender, human-like arms, complete with human-like hands."
				+ " Despite their somewhat-normal appearance, they have a subtle, alluring quality to them that reveals their true angelic nature.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldAngel(angelic arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription]",
			"[npc.She] [npc.has] [npc.armRows] human-like arms and hands, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)].") {
	};

	public static AbstractArmType DEMON_COMMON = new Special(BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			"arm",
			"arms",
			Util.newArrayListOfValues("flawless"),
			Util.newArrayListOfValues("flawless"),
			"hand",
			"hands",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("delicate", "soft", "feminine"),
			"finger",
			"fingers",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("delicate", "soft", "feminine"),
			"Within a matter of moments, they've changed into slender, human-like arms, complete with human-like hands."
				+ " Despite their somewhat-normal appearance, they have a subtle, alluring quality to them that reveals their true demonic nature.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldDemon(demonic arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] slender human-looking arms and hands, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)].") {
	};

	public static AbstractArmType COW_MORPH = new Special(BodyCoveringType.BOVINE_FUR,
			Race.COW_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("furry", "fur-coated"),
			Util.newArrayListOfValues("furry", "fur-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("bovine"),
			Util.newArrayListOfValues("feminine", "bovine"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("bovine"),
			Util.newArrayListOfValues("feminine", "bovine"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new hair growing over the backs of [npc.her] hands as tough, hoof-like nails push out in place of regular, human-like ones."
				+ " Despite their appearance, [npc.sheIsFull] relieved to discover that [npc.her] hands have lost none of their dexterity."
				+ " As the transformation comes to an end, [npc.she] [npc.verb(see)] that at [npc.her] upper-biceps, [npc.her] hair smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "[npc.NameIsFull] left with anthropomorphic, [style.boldCowMorph(cow-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands, while human in shape, have tough little hoof-like nails.") {
	};

	public static AbstractArmType DOG_MORPH = new Special(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("furry", "fur-coated"),
			Util.newArrayListOfValues("furry", "fur-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("dog-like", "paw-like", "furry", "canine"),
			Util.newArrayListOfValues("soft", "feminine", "dog-like", "paw-like", "furry", "canine"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("padded", "canine"),
			Util.newArrayListOfValues("soft", "feminine", "padded", "canine"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as blunt, dog-like claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little leathery pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldDogMorph(dog-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, dog-like hands, complete with little blunt claws and leathery pads.") {
	};

	public static AbstractArmType WOLF_MORPH = new Special(BodyCoveringType.LYCAN_FUR,
			Race.WOLF_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("furry", "fur-coated"),
			Util.newArrayListOfValues("furry", "fur-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("wolf-like", "furry", "paw-like"),
			Util.newArrayListOfValues("soft", "feminine", "wolf-like", "furry", "paw-like"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("padded", "wolf-like"),
			Util.newArrayListOfValues("soft", "feminine", "padded", "wolf-like"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as sharp claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] tough leathery pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldWolfMorph(wolf-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, wolf-like hands, complete with sharp claws and tough leathery pads.") {
	};

	public static AbstractArmType FOX_MORPH = new Special(BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("furry", "fur-coated"),
			Util.newArrayListOfValues("furry", "fur-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("fox-like", "furry", "paw-like"),
			Util.newArrayListOfValues("soft", "feminine", "fox-like", "furry", "paw-like"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("padded", "fox-like"),
			Util.newArrayListOfValues("soft", "feminine", "padded", "fox-like"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as sharp claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.</br>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldFoxMorph(fox-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, fox-like hands, complete with sharp claws and tough leathery pads.") {
	};

	public static AbstractArmType CAT_MORPH = new Special(BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("furry", "fur-coated"),
			Util.newArrayListOfValues("furry", "fur-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("soft", "delicate", "cat-like", "paw-like", "furry", "feline"),
			Util.newArrayListOfValues("soft", "feminine", "cat-like", "paw-like", "furry", "feline"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("soft", "delicate", "padded", "feline"),
			Util.newArrayListOfValues("soft", "feminine", "padded", "feline"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] fur growing over the backs of [npc.her] hands as sharp, retractable claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little pink pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldCatMorph(cat-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, cat-like hands, complete with retractable claws and pink pads.") {
	};

	public static AbstractArmType HORSE_MORPH = new Special(BodyCoveringType.HORSE_HAIR,
			Race.HORSE_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("hair-coated"),
			Util.newArrayListOfValues("hair-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("equine"),
			Util.newArrayListOfValues("feminine", "equine"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("equine"),
			Util.newArrayListOfValues("feminine", "equine"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new hair growing over the backs of [npc.her] hands as tough, hoof-like nails push out in place of regular, human-like ones."
				+ " Despite their appearance, [npc.sheIs] relieved to discover that [npc.her] hands have lost none of their dexterity."
				+ " As the transformation comes to an end, [npc.she] [npc.verb(see)] that at [npc.her] upper-biceps, [npc.her] hair smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "[npc.NameIsFull] left with anthropomorphic, [style.boldHorseMorph(horse-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands, while human in shape, have tough little hoof-like nails.") {
	};

	public static AbstractArmType REINDEER_MORPH = new Special(BodyCoveringType.REINDEER_FUR,
			Race.REINDEER_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("hair-coated"),
			Util.newArrayListOfValues("hair-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("reindeer"),
			Util.newArrayListOfValues("feminine", "reindeer"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("reindeer"),
			Util.newArrayListOfValues("feminine", "reindeer"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as tough, hoof-like nails push out in place of regular, human-like ones."
				+ " Despite their appearance, [npc.sheIs] relieved to discover that [npc.her] hands have lost none of their dexterity."
				+ " As the transformation comes to an end, [npc.she] [npc.verb(see)] that at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "[npc.NameIsFull] left with anthropomorphic, [style.boldReindeerMorph(reindeer-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands, while human in shape, have tough little hoof-like nails.") {
	};

	public static AbstractArmType ALLIGATOR_MORPH = new Special(BodyCoveringType.ALLIGATOR_SCALES,
			Race.ALLIGATOR_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("scaled", "reptile-like"),
			Util.newArrayListOfValues("scaled", "reptile-like"),
			"hand",
			"hands",
			Util.newArrayListOfValues("scaled"),
			Util.newArrayListOfValues("feminine", "scaled"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("scaled"),
			Util.newArrayListOfValues("feminine", "scaled"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
				+ " [npc.she] [npc.verb(see)] [npc.her] new scales growing over the backs of [npc.her] hands as sharp claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little scales, and at [npc.her] upper-biceps, [npc.her] scales smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldGatorMorph(alligator-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, alligator-like hands, complete with little claws.") {
	};

	public static AbstractArmType SQUIRREL_MORPH = new Special(BodyCoveringType.SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("furry", "fur-coated"),
			Util.newArrayListOfValues("furry", "fur-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("soft", "squirrel-like", "claw-like", "furry", "rodent"),
			Util.newArrayListOfValues("soft", "feminine", "squirrel-like", "claw-like", "furry", "rodent"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("soft", "clawed", "rodent"),
			Util.newArrayListOfValues("soft", "feminine", "clawed", "rodent"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as sharp little claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little pink pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldSquirrelMorph(squirrel-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, squirrel-like hands, complete with claws.") {
	};

	public static AbstractArmType RAT_MORPH = new Special(BodyCoveringType.RAT_FUR,
			Race.RAT_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("furry", "fur-coated"),
			Util.newArrayListOfValues("furry", "fur-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("soft", "rat-like", "claw-like", "furry", "rodent"),
			Util.newArrayListOfValues("soft", "feminine", "rat-like", "claw-like", "furry", "rodent"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("soft", "rat-like", "claw-like", "furry", "rodent"),
			Util.newArrayListOfValues("soft", "feminine", "rat-like", "claw-like", "furry", "rodent"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] fur growing over the backs of [npc.her] hands as sharp little claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little pink pads, and at [npc.her] upper-biceps, [npc.her] new fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldRatMorph(rat-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, rat-like hands, complete with claws.") {
	};

	public static AbstractArmType RABBIT_MORPH = new Special(BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			"arm",
			"arms",
			Util.newArrayListOfValues("furry", "fur-coated"),
			Util.newArrayListOfValues("furry", "fur-coated"),
			"hand",
			"hands",
			Util.newArrayListOfValues("rabbit-like", "paw-like", "furry"),
			Util.newArrayListOfValues("soft", "feminine", "rabbit-like", "paw-like", "furry"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("rabbit-like", "paw-like", "furry"),
			Util.newArrayListOfValues("soft", "feminine", "rabbit-like", "paw-like", "furry"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as blunt, rabbit-like claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] soft little pads, and at [npc.her] upper-biceps, [npc.her] new fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldRabbitMorph(rabbit-like arms and paw-like hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, rabbit-like hands, complete with blunt little claws.") {
	};

	public static AbstractArmType BAT_MORPH = new Special(BodyCoveringType.BAT_SKIN,
			Race.BAT_MORPH,
			"wing",
			"wings",
			Util.newArrayListOfValues("bat-like"),
			Util.newArrayListOfValues("bat-like"),
			"hand",
			"hands",
			Util.newArrayListOfValues("bat-like"),
			Util.newArrayListOfValues("feminine", "bat-like"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("bat-like"),
			Util.newArrayListOfValues("soft", "feminine", "bat-like"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, staring at [npc.her] hands in shock,"
					+ " [npc.name] [npc.verb(watch)] [npc.her] fingers narrowing down and growing longer and longer as a tough membrane of skin starts to grow between them."
				+ " [npc.She] [npc.verb(cry)] out in alarm as [npc.she] [npc.verb(feel)] [npc.her] bones growing and snapping into a new form, and within moments,"
					+ " [npc.her] hands and arms have completely transformed into huge, bat-like wings."
				+ " Where [npc.her] hands once were, two of [npc.her] fingers have shrunk down into the middle-joint of [npc.her] new appendages,"
					+ " leaving [npc.herHim] with two small forefingers and an opposable thumb, each of which ends in a little claw."
				+ " Where [npc.her] new wings meet [npc.her] body at the shoulder, [npc.her] [npc.armFullDescription] smoothly covers the transition into the [npc.skin] that's covering the rest of [npc.her] torso.<br/>"
				+ "[npc.Name] now [npc.has] huge [style.boldBatMorph(bat-like wings)] in place of arms, which are [npc.materialDescriptor] [npc.armFullDescription].",
			"In place of arms and hands, [npc.she] [npc.has] [npc.armRows] huge bat-like wings, [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ "#IF(!npc.isFeral())"
				+ " Where [npc.her] hands should be, [npc.she] [npc.has] two forefingers and a thumb, each of which ends in a little blunt claw."
				+ " Although slightly less dexterous than a human hand, [npc.sheIs] still able to use [npc.her] digits to form a hand-like grip."
				+ "#ENDIF") {
		@Override
		public boolean allowsFlight() {
			return true;
		}
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.ARM_WINGS, BodyPartTag.ARM_WINGS_LEATHERY);
		}
	};

	public static AbstractArmType HARPY = new Special(BodyCoveringType.FEATHERS,
			Race.HARPY,
			"wing",
			"wings",
			Util.newArrayListOfValues("feathered", "bird-like"),
			Util.newArrayListOfValues("feathered", "bird-like"),
			"hand",
			"hands",
			Util.newArrayListOfValues("feathered"),
			Util.newArrayListOfValues("feminine", "feathered"),
			"finger",
			"fingers",
			Util.newArrayListOfValues("feathered"),
			Util.newArrayListOfValues("feminine", "feathered"),
			"Within a matter of moments, a thick layer of [npc.armFullDescription] quickly sprouts out all over them, and, looking down, [npc.she] [npc.verb(see)] [npc.her] new feathers growing over the backs of [npc.her] hands as well."
				+ " Just as [npc.she] thinks that the transformation has finished, [npc.she] [npc.verb(cry)] out in shock as [npc.her] bones grow and snap into a new form."
				+ " Thankfully, the transformation is quickly over, leaving [npc.herHim] with a pair of huge, feathered wings in place of arms."
				+ " Where [npc.her] hands once were, two of [npc.her] fingers have shrunk down into the middle-joint of [npc.her] appendages, leaving [npc.herHim] with two feathered forefingers and an opposable thumb,"
					+ " each of which ends in a blunt claw."
				+ " Where [npc.her] new wings meet [npc.her] body at the shoulder, [npc.her] feathers smoothly cover the transition into the [npc.skin] that's covering the rest of [npc.her] torso.<br/>"
				+ "[npc.Name] now [npc.has] huge [style.boldHarpy(harpy wings)] in place of arms, which are [npc.materialDescriptor] [npc.armFullDescription].",
			"In place of arms and hands, [npc.she] [npc.has] [npc.armRows] huge wings, which are [npc.materialCompositionDescriptor] beautiful [npc.armFullDescription(true)]."
				+ "#IF(!npc.isFeral())"
				+ " Where [npc.her] hands should be, [npc.she] [npc.has] two feathered forefingers and a thumb, each of which ends in a little blunt claw."
				+ " Although slightly less dexterous than a human hand, [npc.sheIs] still able to use [npc.her] digits to form a hand-like grip."
				+ "#ENDIF") {
		@Override
		public boolean allowsFlight() {
			return true;
		}
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.ARM_WINGS, BodyPartTag.ARM_WINGS_FEATHERED);
		}
	};
	

	class Special extends AbstractArmType {

		private String id;

		public Special(BodyCoveringType coveringType, Race race, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String handName, String handNamePlural, List<String> handDescriptorsMasculine, List<String> handDescriptorsFeminine, String fingerName, String fingerNamePlural, List<String> fingerDescriptorsMasculine, List<String> fingerDescriptorsFeminine, String armTransformationDescription, String armBodyDescription) {
			super(coveringType, race, name, namePlural, descriptorsMasculine, descriptorsFeminine, handName, handNamePlural, handDescriptorsMasculine, handDescriptorsFeminine, fingerName, fingerNamePlural, fingerDescriptorsMasculine, fingerDescriptorsFeminine, armTransformationDescription, armBodyDescription);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(ArmType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<ArmType> table = new TypeTable<>(
		ArmType::sanitize,
		ArmType.class,
		AbstractArmType.class,
		"arm",
		(f,n,a,m)->new AbstractArmType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	static ArmType getArmTypeFromId(String id) {
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

	static String getIdFromArmType(ArmType armType) {
		return armType.getId();
	}

	static List<ArmType> getAllArmTypes() {
		return table.listByRace();
	}
	
	static List<ArmType> getArmTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
	
}
