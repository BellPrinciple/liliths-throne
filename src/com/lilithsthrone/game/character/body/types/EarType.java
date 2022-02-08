package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractEarType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.4
 * @author Innoxia
 */
public interface EarType extends BodyPartTypeInterface {

	public static AbstractEarType HUMAN = new Special(BodyCoveringType.HUMAN,
			Race.HUMAN,
			"human",
			"ear",
			"ears",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("soft", "feminine"),
			"The hot itching feeling passes after a few moments, leaving [npc.herHim] with normal-looking human ears.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldHuman(human ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of normal, human ears, which are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)]#IF(npc.isPiercedEar()), and which have been pierced#ENDIF.") {
	};

	public static AbstractEarType ANGEL = new Special(BodyCoveringType.ANGEL,
			Race.ANGEL,
			"angel",
			"ear",
			"ears",
			Util.newArrayListOfValues("pointed", "delicate", "angelic"),
			Util.newArrayListOfValues("soft", "feminine", "pointed", "delicate", "angelic"),
			"The hot itching feeling passes after a few moments, leaving [npc.herHim] with delicate, humanoid ears, with long, pointed tips.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldAngel(pointed, angelic ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of pointed angelic ears, which are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)]#IF(npc.isPiercedEar()), and which have been pierced#ENDIF.") {
	};

	public static AbstractEarType DEMON_COMMON = new Special(BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			"demonic",
			"ear",
			"ears",
			Util.newArrayListOfValues("pointed", "demonic"),
			Util.newArrayListOfValues("soft", "feminine", "pointed", "demonic"),
			"The hot itching feeling passes after a few moments, leaving [npc.herHim] with delicate, humanoid ears, with long, pointed tips.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldDemon(pointed, demonic ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of pointed demonic ears, which are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)]#IF(npc.isPiercedEar()), and which have been pierced#ENDIF.") {
	};
	
	public static AbstractEarType DOG_MORPH = new Special(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			"floppy dog",
			"ear",
			"ears",
			Util.newArrayListOfValues("floppy", "furry", "fur-coated", "dog-like"),
			Util.newArrayListOfValues("feminine", "floppy", "furry", "fur-coated", "dog-like"),
			"They quickly expand in size, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new dog-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldDog(floppy, dog-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of floppy,#IF(npc.isPiercedEar()) pierced,#ENDIF dog-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.EAR_HANDLES_IN_SEX);
		}
	};

	public static AbstractEarType DOG_MORPH_POINTED = new Special(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			"pointed dog",
			"ear",
			"ears",
			Util.newArrayListOfValues("pointed", "furry", "fur-coated", "dog-like"),
			Util.newArrayListOfValues("feminine", "pointed", "furry", "fur-coated", "dog-like"),
			"They quickly grow into upright points, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new dog-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldDog(pointed, dog-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of pointed,#IF(npc.isPiercedEar()) pierced,#ENDIF dog-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType DOG_MORPH_FOLDED = new Special(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			"folded dog",
			"ear",
			"ears",
			Util.newArrayListOfValues("folded", "furry", "fur-coated", "dog-like"),
			Util.newArrayListOfValues("feminine", "folded", "furry", "fur-coated", "dog-like"),
			"They quickly grow into upright points, before folding over at the top and shifting to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new dog-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldDog(folded, dog-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of folded,#IF(npc.isPiercedEar()) pierced,#ENDIF dog-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType WOLF_MORPH = new Special(BodyCoveringType.LYCAN_FUR,
			Race.WOLF_MORPH,
			"wolf",
			"ear",
			"ears",
			Util.newArrayListOfValues("furry", "fur-coated", "wolf-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "wolf-like"),
			"They quickly grow into large, upright points, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new wolf-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldWolf(large, wolf-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of upright,#IF(npc.isPiercedEar()) pierced,#ENDIF wolf-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType FOX_MORPH = new Special(BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			"fox",
			"ear",
			"ears",
			Util.newArrayListOfValues("pointed", "furry", "fur-coated", "fox-like"),
			Util.newArrayListOfValues("feminine", "pointed", "furry", "fur-coated", "fox-like"),
			"They quickly grow into large, upright points, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new fox-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldFox(pointed, fox-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of pointed,#IF(npc.isPiercedEar()) pierced,#ENDIF fox-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType FOX_MORPH_BIG = new Special(BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			"fennec fox",
			"ear",
			"ears",
			Util.newArrayListOfValues("pointed", "furry", "fur-coated", "large", "fennec-fox-like"),
			Util.newArrayListOfValues("feminine", "pointed", "furry", "fur-coated", "large", "fennec-fox-like"),
			"They quickly grow into massive, upright points, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new fox-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldFox(massive, fennec-fox-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of massive,#IF(npc.isPiercedEar()) pierced,#ENDIF fennec-fox-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.EAR_HANDLES_IN_SEX);
		}
	};

	public static AbstractEarType COW_MORPH = new Special(BodyCoveringType.BOVINE_FUR,
			Race.COW_MORPH,
			"cow",
			"ear",
			"ears",
			Util.newArrayListOfValues("furry", "fur-coated", "cow-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "cow-like"),
			"They quickly take on a distinctly bovine shape by growing out and narrowing down into long, slightly-folded ovals."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new cow-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldCow(cow-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of #IF(npc.isPiercedEar()) pierced,#ENDIF cow-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType CAT_MORPH = new Special(BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			"cat",
			"ear",
			"ears",
			Util.newArrayListOfValues("furry", "fur-coated", "cat-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "cat-like"),
			"They quickly grow into upright points, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new cat-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldCat(cat-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of #IF(npc.isPiercedEar()) pierced,#ENDIF cat-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType CAT_MORPH_TUFTED = new Special(BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			"tufted cat",
			"ear",
			"ears",
			Util.newArrayListOfValues("tufted", "furry", "fur-coated", "cat-like"),
			Util.newArrayListOfValues("feminine", "tufted", "furry", "fur-coated", "cat-like"),
			"They quickly grow into upright points, which are topped off with a small patch of sensitive fur, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new cat-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldCat(tufted, cat-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of tufted,#IF(npc.isPiercedEar()) pierced,#ENDIF cat-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType SQUIRREL_MORPH = new Special(BodyCoveringType.SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			"squirrel",
			"ear",
			"ears",
			Util.newArrayListOfValues("furry", "fur-coated", "squirrel-like"),
			Util.newArrayListOfValues("feminine", "furry", "fur-coated", "squirrel-like"),
			"They quickly grow into small, upright ovals, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new squirrel-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldSquirrel(squirrel-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of#IF(npc.isPiercedEar()) pierced,#ENDIF squirrel-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType RAT_MORPH = new Special(BodyCoveringType.RAT_FUR,
			Race.RAT_MORPH,
			"rat",
			"ear",
			"ears",
			Util.newArrayListOfValues("rat-like"),
			Util.newArrayListOfValues("feminine", "rat-like"),
			"They quickly grow into small, upright ovals, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new rat-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldRat(rat-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of #IF(npc.isPiercedEar()) pierced,#ENDIF rat-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType RABBIT_MORPH = new Special(BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			"upright rabbit",
			"ear",
			"ears",
			Util.newArrayListOfValues("upright", "furry", "fur-coated", "rabbit-like"),
			Util.newArrayListOfValues("feminine", "upright", "furry", "fur-coated", "rabbit-like"),
			"They quickly grow into large, upright points, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new rabbit-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldRabbit(upright, rabbit-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of upright,#IF(npc.isPiercedEar()) pierced,#ENDIF rabbit-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.EAR_HANDLES_IN_SEX);
		}
	};

	public static AbstractEarType RABBIT_MORPH_FLOPPY = new Special(BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			"floppy rabbit",
			"ear",
			"ears",
			Util.newArrayListOfValues("floppy", "furry", "fur-coated", "rabbit-like"),
			Util.newArrayListOfValues("feminine", "floppy", "furry", "fur-coated", "rabbit-like"),
			"They quickly grow into large, upright points, and shift to sit higher up than a normal pair of human ears would, before suddenly collapsing and flopping down on either side of [npc.her] head."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new rabbit-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldRabbit(floppy, rabbit-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of floppy,#IF(npc.isPiercedEar()) pierced,#ENDIF rabbit-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.EAR_HANDLES_IN_SEX);
		}
	};

	public static AbstractEarType BAT_MORPH = new Special(BodyCoveringType.BAT_FUR,
			Race.BAT_MORPH,
			"bat",
			"ear",
			"ears",
			Util.newArrayListOfValues("large", "bat-like"),
			Util.newArrayListOfValues("feminine", "large", "bat-like"),
			"They quickly grow into large, upright points, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] [npc.verb(discover)] that [npc.she] can easily twitch [npc.her] new bat-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldBat(large, bat-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of large,#IF(npc.isPiercedEar()) pierced,#ENDIF bat-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.ECHO_LOCATION);
		}
	};

	public static AbstractEarType HORSE_MORPH = new Special(BodyCoveringType.HORSE_HAIR,
			Race.HORSE_MORPH,
			"horse",
			"ear",
			"ears",
			Util.newArrayListOfValues("furry", "upright", "horse-like"),
			Util.newArrayListOfValues("feminine", "furry", "upright", "horse-like"),
			"They quickly grow into sturdy little upright points, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new horse-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldHorse(horse-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of upright,#IF(npc.isPiercedEar()) pierced,#ENDIF horse-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType HORSE_MORPH_UPRIGHT = new Special(BodyCoveringType.HORSE_HAIR,
			Race.HORSE_MORPH,
			"tall horse",
			"ear",
			"ears",
			Util.newArrayListOfValues("furry", "tall", "upright", "horse-like"),
			Util.newArrayListOfValues("feminine", "tall", "furry", "upright", "horse-like"),
			"They quickly grow into tall, upright points, and shift to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new horse-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldHorse(tall, horse-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of tall, upright,#IF(npc.isPiercedEar()) pierced,#ENDIF horse-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};
	
	public static AbstractEarType REINDEER_MORPH = new Special(BodyCoveringType.REINDEER_FUR,
			Race.REINDEER_MORPH,
			"reindeer",
			"ear",
			"ears",
			Util.newArrayListOfValues("furry", "reindeer-like"),
			Util.newArrayListOfValues("feminine", "furry", "reindeer-like"),
			"They quickly take on a distinctly reindeer-like shape by growing out and narrowing down into long, slightly-folded ovals, before shifting to sit higher up on [npc.her] head than a normal pair of human ears would."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] quickly grows to cover them,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, they're made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.she] experimentally [npc.verb(twitch)] [npc.her] new reindeer-like ears back and forth.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldReindeer(reindeer-like ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.She] [npc.has] a pair of#IF(npc.isPiercedEar()) pierced,#ENDIF reindeer-like ears, which are positioned high up on [npc.her] head and are [npc.materialCompositionDescriptor] [npc.earFullDescription(true)].") {
	};

	public static AbstractEarType ALLIGATOR_MORPH = new Special(BodyCoveringType.ALLIGATOR_SCALES,
			Race.ALLIGATOR_MORPH,
			"alligator",
			"ear",
			"ears",
			Util.newArrayListOfValues("scaled", "scale-covered", "alligator-like"),
			Util.newArrayListOfValues("feminine", "scaled", "scale-covered", "alligator-like"),
			"They quickly shrink down into little nubs as most of the external cartilage shifts down into the sides of [npc.her] head."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] grow to cover [npc.her] now-fully-internal ears,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, [npc.her] now-fully-internal ears are made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.sheIs] left with the ears of an alligator-morph.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldAlligator(internal, scale-covered alligator ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.Her] ears are an internal part of [npc.her] head, and are covered by a fan of [npc.earFullDescription(true)]."
				+ "#IF(npc.isPiercedEar()) They have been cleverly pierced so as to allow [npc.herHim] to wear ear-specific jewellery.#ENDIF") {
	};
	
	public static AbstractEarType HARPY = new Special(BodyCoveringType.FEATHERS,
			Race.HARPY,
			"harpy",
			"ear",
			"ears",
			Util.newArrayListOfValues("feathered", "feather-covered", "bird-like"),
			Util.newArrayListOfValues("feminine", "feathered", "feather-covered", "bird-like"),
			"They quickly shrink down into little nubs as most of the external cartilage shifts down into the sides of [npc.her] head."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
				+ " A layer of [npc.earFullDescriptionColour] grow to cover [npc.her] now-fully-internal ears,"
				+ "#ELSE"
				+ " Just like the rest of [npc.her] body, [npc.her] now-fully-internal ears are made out of [npc.earFullDescription],"
				+ "#ENDIF"
				+ " and as the transformation finishes, [npc.sheIs] left with a pair of beautifully-feathered harpy ears.<br/>"
				+ "[npc.Name] now [npc.has] [style.boldHarpy(internal, feather-covered harpy ears)], which are [npc.materialCompositionDescriptor] [npc.earFullDescription].",
			"[npc.Her] ears are an internal part of [npc.her] head, and are covered by a fan of [npc.earFullDescription(true)]."
				+ "#IF(npc.isPiercedEar()) They have been cleverly pierced so as to allow [npc.herHim] to wear ear-specific jewellery.#ENDIF") {
	};
	
	class Special extends AbstractEarType {

		private String id;

		public Special(AbstractBodyCoveringType coveringType, Race race, String transformationName, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String earTransformationDescription, String earBodyDescription) {
			super(coveringType, race, transformationName, name, namePlural, descriptorsMasculine, descriptorsFeminine, earTransformationDescription, earBodyDescription);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(EarType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<AbstractEarType> table = new TypeTable<>(
		EarType::sanitize,
		EarType.class,
		AbstractEarType.class,
		"ear",
		(f,n,a,m)->new AbstractEarType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	public static AbstractEarType getEarTypeFromId(String id) {
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

	@Deprecated
	public static String getIdFromEarType(AbstractEarType earType) {
		return earType.getId();
	}

	@Deprecated
	public static List<AbstractEarType> getAllEarTypes() {
		return table.listByRace();
	}

	@Deprecated
	public static List<AbstractEarType> getEarTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
}