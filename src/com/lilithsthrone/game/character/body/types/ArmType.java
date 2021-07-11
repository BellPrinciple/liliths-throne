package com.lilithsthrone.game.character.body.types;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithsthrone.game.character.body.abstractTypes.AbstractArmType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.race.AbstractRace;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * Contains static instances of AbstractArmType.
 * 
 * @since 0.1.0
 * @version 0.3
 * @author Innoxia
 */
public class ArmType {

	public static AbstractArmType HUMAN = new AbstractArmType(BodyCoveringType.HUMAN,
			Race.HUMAN,
			"arm",
			"arms",
			List.of(""),
			List.of(""),
			"hand",
			"hands",
			List.of(""),
			List.of("soft", "feminine"),
			"finger",
			"fingers",
			List.of(""),
			List.of("soft", "feminine"),
			"Thankfully, the transformation only lasts a matter of moments, leaving [npc.herHim] with normal-looking human arms, complete with human hands.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldHuman(human arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] normal human arms and hands, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)].") {
	};

	public static AbstractArmType ANGEL = new AbstractArmType(BodyCoveringType.HUMAN,
			Race.ANGEL,
			"arm",
			"arms",
			List.of("delicate"),
			List.of("delicate"),
			"hand",
			"hands",
			List.of("delicate", "soft"),
			List.of("delicate", "soft", "feminine"),
			"finger",
			"fingers",
			List.of("delicate", "soft"),
			List.of("delicate", "soft", "feminine"),
			"Within a matter of moments, they've changed into slender, human-like arms, complete with human-like hands."
				+ " Despite their somewhat-normal appearance, they have a subtle, alluring quality to them that reveals their true angelic nature.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldAngel(angelic arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription]",
			"[npc.She] [npc.has] [npc.armRows] human-like arms and hands, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)].") {
	};

	public static AbstractArmType DEMON_COMMON = new AbstractArmType(BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			"arm",
			"arms",
			List.of("flawless"),
			List.of("flawless"),
			"hand",
			"hands",
			List.of(""),
			List.of("delicate", "soft", "feminine"),
			"finger",
			"fingers",
			List.of(""),
			List.of("delicate", "soft", "feminine"),
			"Within a matter of moments, they've changed into slender, human-like arms, complete with human-like hands."
				+ " Despite their somewhat-normal appearance, they have a subtle, alluring quality to them that reveals their true demonic nature.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldDemon(demonic arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] slender human-looking arms and hands, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)].") {
	};

	public static AbstractArmType COW_MORPH = new AbstractArmType(BodyCoveringType.BOVINE_FUR,
			Race.COW_MORPH,
			"arm",
			"arms",
			List.of("furry", "fur-coated"),
			List.of("furry", "fur-coated"),
			"hand",
			"hands",
			List.of("bovine"),
			List.of("feminine", "bovine"),
			"finger",
			"fingers",
			List.of("bovine"),
			List.of("feminine", "bovine"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new hair growing over the backs of [npc.her] hands as tough, hoof-like nails push out in place of regular, human-like ones."
				+ " Despite their appearance, [npc.sheIsFull] relieved to discover that [npc.her] hands have lost none of their dexterity."
				+ " As the transformation comes to an end, [npc.she] [npc.verb(see)] that at [npc.her] upper-biceps, [npc.her] hair smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "[npc.NameIsFull] left with anthropomorphic, [style.boldCowMorph(cow-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands, while human in shape, have tough little hoof-like nails.") {
	};

	public static AbstractArmType DOG_MORPH = new AbstractArmType(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			"arm",
			"arms",
			List.of("furry", "fur-coated"),
			List.of("furry", "fur-coated"),
			"hand",
			"hands",
			List.of("dog-like", "paw-like", "furry", "canine"),
			List.of("soft", "feminine", "dog-like", "paw-like", "furry", "canine"),
			"finger",
			"fingers",
			List.of("padded", "canine"),
			List.of("soft", "feminine", "padded", "canine"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as blunt, dog-like claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little leathery pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldDogMorph(dog-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, dog-like hands, complete with little blunt claws and leathery pads.") {
	};

	public static AbstractArmType WOLF_MORPH = new AbstractArmType(BodyCoveringType.LYCAN_FUR,
			Race.WOLF_MORPH,
			"arm",
			"arms",
			List.of("furry", "fur-coated"),
			List.of("furry", "fur-coated"),
			"hand",
			"hands",
			List.of("wolf-like", "furry", "paw-like"),
			List.of("soft", "feminine", "wolf-like", "furry", "paw-like"),
			"finger",
			"fingers",
			List.of("padded", "wolf-like"),
			List.of("soft", "feminine", "padded", "wolf-like"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as sharp claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] tough leathery pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldWolfMorph(wolf-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, wolf-like hands, complete with sharp claws and tough leathery pads.") {
	};

	public static AbstractArmType FOX_MORPH = new AbstractArmType(BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			"arm",
			"arms",
			List.of("furry", "fur-coated"),
			List.of("furry", "fur-coated"),
			"hand",
			"hands",
			List.of("fox-like", "furry", "paw-like"),
			List.of("soft", "feminine", "fox-like", "furry", "paw-like"),
			"finger",
			"fingers",
			List.of("padded", "fox-like"),
			List.of("soft", "feminine", "padded", "fox-like"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as sharp claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.</br>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldFoxMorph(fox-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, fox-like hands, complete with sharp claws and tough leathery pads.") {
	};

	public static AbstractArmType CAT_MORPH = new AbstractArmType(BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			"arm",
			"arms",
			List.of("furry", "fur-coated"),
			List.of("furry", "fur-coated"),
			"hand",
			"hands",
			List.of("soft", "delicate", "cat-like", "paw-like", "furry", "feline"),
			List.of("soft", "feminine", "cat-like", "paw-like", "furry", "feline"),
			"finger",
			"fingers",
			List.of("soft", "delicate", "padded", "feline"),
			List.of("soft", "feminine", "padded", "feline"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] fur growing over the backs of [npc.her] hands as sharp, retractable claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little pink pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldCatMorph(cat-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, cat-like hands, complete with retractable claws and pink pads.") {
	};

	public static AbstractArmType HORSE_MORPH = new AbstractArmType(BodyCoveringType.HORSE_HAIR,
			Race.HORSE_MORPH,
			"arm",
			"arms",
			List.of("hair-coated"),
			List.of("hair-coated"),
			"hand",
			"hands",
			List.of("equine"),
			List.of("feminine", "equine"),
			"finger",
			"fingers",
			List.of("equine"),
			List.of("feminine", "equine"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new hair growing over the backs of [npc.her] hands as tough, hoof-like nails push out in place of regular, human-like ones."
				+ " Despite their appearance, [npc.sheIs] relieved to discover that [npc.her] hands have lost none of their dexterity."
				+ " As the transformation comes to an end, [npc.she] [npc.verb(see)] that at [npc.her] upper-biceps, [npc.her] hair smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "[npc.NameIsFull] left with anthropomorphic, [style.boldHorseMorph(horse-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands, while human in shape, have tough little hoof-like nails.") {
	};

	public static AbstractArmType REINDEER_MORPH = new AbstractArmType(BodyCoveringType.REINDEER_FUR,
			Race.REINDEER_MORPH,
			"arm",
			"arms",
			List.of("hair-coated"),
			List.of("hair-coated"),
			"hand",
			"hands",
			List.of("reindeer"),
			List.of("feminine", "reindeer"),
			"finger",
			"fingers",
			List.of("reindeer"),
			List.of("feminine", "reindeer"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as tough, hoof-like nails push out in place of regular, human-like ones."
				+ " Despite their appearance, [npc.sheIs] relieved to discover that [npc.her] hands have lost none of their dexterity."
				+ " As the transformation comes to an end, [npc.she] [npc.verb(see)] that at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "[npc.NameIsFull] left with anthropomorphic, [style.boldReindeerMorph(reindeer-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands, while human in shape, have tough little hoof-like nails.") {
	};

	public static AbstractArmType ALLIGATOR_MORPH = new AbstractArmType(BodyCoveringType.ALLIGATOR_SCALES,
			Race.ALLIGATOR_MORPH,
			"arm",
			"arms",
			List.of("scaled", "reptile-like"),
			List.of("scaled", "reptile-like"),
			"hand",
			"hands",
			List.of("scaled"),
			List.of("feminine", "scaled"),
			"finger",
			"fingers",
			List.of("scaled"),
			List.of("feminine", "scaled"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
				+ " [npc.she] [npc.verb(see)] [npc.her] new scales growing over the backs of [npc.her] hands as sharp claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little scales, and at [npc.her] upper-biceps, [npc.her] scales smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldGatorMorph(alligator-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, alligator-like hands, complete with little claws.") {
	};

	public static AbstractArmType SQUIRREL_MORPH = new AbstractArmType(BodyCoveringType.SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			"arm",
			"arms",
			List.of("furry", "fur-coated"),
			List.of("furry", "fur-coated"),
			"hand",
			"hands",
			List.of("soft", "squirrel-like", "claw-like", "furry", "rodent"),
			List.of("soft", "feminine", "squirrel-like", "claw-like", "furry", "rodent"),
			"finger",
			"fingers",
			List.of("soft", "clawed", "rodent"),
			List.of("soft", "feminine", "clawed", "rodent"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as sharp little claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little pink pads, and at [npc.her] upper-biceps, [npc.her] fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldSquirrelMorph(squirrel-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, squirrel-like hands, complete with claws.") {
	};

	public static AbstractArmType RAT_MORPH = new AbstractArmType(BodyCoveringType.RAT_FUR,
			Race.RAT_MORPH,
			"arm",
			"arms",
			List.of("furry", "fur-coated"),
			List.of("furry", "fur-coated"),
			"hand",
			"hands",
			List.of("soft", "rat-like", "claw-like", "furry", "rodent"),
			List.of("soft", "feminine", "rat-like", "claw-like", "furry", "rodent"),
			"finger",
			"fingers",
			List.of("soft", "rat-like", "claw-like", "furry", "rodent"),
			List.of("soft", "feminine", "rat-like", "claw-like", "furry", "rodent"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] fur growing over the backs of [npc.her] hands as sharp little claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] little pink pads, and at [npc.her] upper-biceps, [npc.her] new fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldRatMorph(rat-like arms and hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, rat-like hands, complete with claws.") {
	};

	public static AbstractArmType RABBIT_MORPH = new AbstractArmType(BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			"arm",
			"arms",
			List.of("furry", "fur-coated"),
			List.of("furry", "fur-coated"),
			"hand",
			"hands",
			List.of("rabbit-like", "paw-like", "furry"),
			List.of("soft", "feminine", "rabbit-like", "paw-like", "furry"),
			"finger",
			"fingers",
			List.of("rabbit-like", "paw-like", "furry"),
			List.of("soft", "feminine", "rabbit-like", "paw-like", "furry"),
			"Within a matter of moments, a layer of [npc.armFullDescription] has quickly grown over them, and, looking down,"
					+ " [npc.she] [npc.verb(see)] [npc.her] new fur growing over the backs of [npc.her] hands as blunt, rabbit-like claws push out to replace [npc.her] fingernails."
				+ " [npc.Her] palms rapidly transform to be [npc.materialDescriptor] soft little pads, and at [npc.her] upper-biceps, [npc.her] new fur smoothly transitions into the [npc.skin] that's covering the rest of [npc.her] body.<br/>"
				+ "As the transformation comes to an end, [npc.nameIsFull] left with anthropomorphic, [style.boldRabbitMorph(rabbit-like arms and paw-like hands)], which are [npc.materialDescriptor] [npc.armFullDescription].",
			"[npc.She] [npc.has] [npc.armRows] arms, which are [npc.materialCompositionDescriptor] [npc.armFullDescription(true)]."
				+ " [npc.Her] hands are formed into anthropomorphic, rabbit-like hands, complete with blunt little claws.") {
	};

	public static AbstractArmType BAT_MORPH = new AbstractArmType(BodyCoveringType.BAT_SKIN,
			Race.BAT_MORPH,
			"wing",
			"wings",
			List.of("bat-like"),
			List.of("bat-like"),
			"hand",
			"hands",
			List.of("bat-like"),
			List.of("feminine", "bat-like"),
			"finger",
			"fingers",
			List.of("bat-like"),
			List.of("soft", "feminine", "bat-like"),
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
			return List.of(BodyPartTag.ARM_WINGS, BodyPartTag.ARM_WINGS_LEATHERY);
		}
	};

	public static AbstractArmType HARPY = new AbstractArmType(BodyCoveringType.FEATHERS,
			Race.HARPY,
			"wing",
			"wings",
			List.of("feathered", "bird-like"),
			List.of("feathered", "bird-like"),
			"hand",
			"hands",
			List.of("feathered"),
			List.of("feminine", "feathered"),
			"finger",
			"fingers",
			List.of("feathered"),
			List.of("feminine", "feathered"),
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
			return List.of(BodyPartTag.ARM_WINGS, BodyPartTag.ARM_WINGS_FEATHERED);
		}
	};
	
	
	private static List<AbstractArmType> allArmTypes;
	private static Map<AbstractArmType, String> armToIdMap = new HashMap<>();
	private static Map<String, AbstractArmType> idToArmMap = new HashMap<>();
	
	static {
		allArmTypes = new ArrayList<>();

		// Modded types:
		
		Map<String, Map<String, File>> moddedFilesMap = Util.getExternalModFilesById("/race", "bodyParts", null);
		for(Entry<String, Map<String, File>> entry : moddedFilesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				if(Util.getXmlRootElementName(innerEntry.getValue()).equals("arm")) {
					try {
						AbstractArmType type = new AbstractArmType(innerEntry.getValue(), entry.getKey(), true) {};
						String id = innerEntry.getKey().replaceAll("bodyParts_", "");
						allArmTypes.add(type);
						armToIdMap.put(type, id);
						idToArmMap.put(id, type);
					} catch(Exception ex) {
						ex.printStackTrace(System.err);
					}
				}
			}
		}
		
		// External res types:
		
		Map<String, Map<String, File>> filesMap = Util.getExternalFilesById("res/race", "bodyParts", null);
		for(Entry<String, Map<String, File>> entry : filesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				if(Util.getXmlRootElementName(innerEntry.getValue()).equals("arm")) {
					try {
						AbstractArmType type = new AbstractArmType(innerEntry.getValue(), entry.getKey(), false) {};
						String id = innerEntry.getKey().replaceAll("bodyParts_", "");
						allArmTypes.add(type);
						armToIdMap.put(type, id);
						idToArmMap.put(id, type);
					} catch(Exception ex) {
						ex.printStackTrace(System.err);
					}
				}
			}
		}
		
		// Add in hard-coded arm types:
		
		Field[] fields = ArmType.class.getFields();
		
		for(Field f : fields){
			if (AbstractArmType.class.isAssignableFrom(f.getType())) {
				
				AbstractArmType ct;
				try {
					ct = ((AbstractArmType) f.get(null));

					armToIdMap.put(ct, f.getName());
					idToArmMap.put(f.getName(), ct);
					
					allArmTypes.add(ct);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		Collections.sort(allArmTypes, (t1, t2)->
			t1.getRace()==Race.NONE
				?-1
				:(t2.getRace()==Race.NONE
					?1
					:t1.getRace().getName(false).compareTo(t2.getRace().getName(false))));
	}
	
	public static AbstractArmType getArmTypeFromId(String id) {
		if(id.equals("IMP")) {
			return ArmType.DEMON_COMMON;
		}
		if(id.equals("LYCAN")) {
			return ArmType.WOLF_MORPH;
		}

		id = Util.getClosestStringMatch(id, idToArmMap.keySet());
		return idToArmMap.get(id);
	}
	
	public static String getIdFromArmType(AbstractArmType armType) {
		return armToIdMap.get(armType);
	}
	
	public static List<AbstractArmType> getAllArmTypes() {
		return allArmTypes;
	}
	
	private static Map<AbstractRace, List<AbstractArmType>> typesMap = new HashMap<>();
	
	public static List<AbstractArmType> getArmTypes(AbstractRace r) {
		if(typesMap.containsKey(r)) {
			return typesMap.get(r);
		}
		
		List<AbstractArmType> types = new ArrayList<>();
		for(AbstractArmType type : ArmType.getAllArmTypes()) {
			if(type.getRace()==r) {
				types.add(type);
			}
		}
		typesMap.put(r, types);
		return types;
	}
	
}
