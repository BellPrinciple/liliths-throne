package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractBreastType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.BreastShape;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.3.8.2
 * @author Innoxia
 */
public interface BreastType extends BodyPartTypeInterface {

	NippleType getNippleType();

	FluidType getFluidType();

	default String getCrotchNameSingular(GameCharacter gc) {
		return UtilText.returnStringAtRandom("crotch-breast", "crotch-boob", "crotch-boob", "crotch-boob", "crotch-tit");
	}

	default String getCrotchNamePlural(GameCharacter gc) {
		return UtilText.returnStringAtRandom("crotch-breasts", "crotch-boobs", "crotch-boobs", "crotch-boobs", "crotch-tits");
	}

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	String getTransformationCrotchDescription(GameCharacter owner);

	String getBodyCrotchDescription(GameCharacter owner);

	@Override
	default String getDeterminer(GameCharacter gc) {
		if(gc.getBreastCrotchShape()== BreastShape.UDDERS) {
			return "a set of";
		}
		if(gc.getBreastRows()==1) {
			return "a pair of";
		} else {
			return Util.intToString(gc.getBreastRows())+" pairs of";
		}
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return true;
	}

	// Only used for when lacking crotch breasts:
	public static AbstractBreastType NONE = new AbstractBreastType(BodyCoveringType.HUMAN,
			Race.NONE,
			NippleType.HUMAN,
			FluidType.MILK_HUMAN,
			"breast",
			"breasts",
			"[npc.She] no longer [npc.has] [style.boldShrink([npc.crotchBoobs])]!",
			""){
	};
	
	public static AbstractBreastType HUMAN = new AbstractBreastType(BodyCoveringType.HUMAN,
			Race.HUMAN,
			NippleType.HUMAN,
			FluidType.MILK_HUMAN,
			"[npc.She] now [npc.has] [style.boldHuman(human)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldHuman(human milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] human, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldHuman(human)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldHuman(human milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] human, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType ANGEL = new AbstractBreastType(BodyCoveringType.ANGEL,
			Race.ANGEL,
			NippleType.ANGEL,
			FluidType.MILK_ANGEL,
			"[npc.She] now [npc.has] [style.boldAngel(angelic)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldAngel(angel milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] angelic, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldAngel(angelic)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldAngel(angel milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] angelic, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType DEMON_COMMON = new AbstractBreastType(BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			NippleType.DEMON,
			FluidType.MILK_DEMON,
			"[npc.She] now [npc.has] [style.boldDemon(demonic)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldDemon(demon milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] demonic, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldDemon(demonic)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldDemon(demon milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] demonic, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType DOG_MORPH = new AbstractBreastType(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			NippleType.DOG_MORPH,
			FluidType.MILK_DOG_MORPH, // Emergency backup supply. We're on the dog's milk.
			"[npc.She] now [npc.has] [style.boldDogMorph(canine)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldDogMorph(dog milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] canine, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldDogMorph(canine)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldDogMorph(dog milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] canine, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType WOLF_MORPH = new AbstractBreastType(BodyCoveringType.LYCAN_FUR,
			Race.WOLF_MORPH,
			NippleType.WOLF_MORPH,
			FluidType.MILK_WOLF_MORPH,
			"[npc.She] now [npc.has] [style.boldWolfMorph(lupine)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldWolfMorph(wolf milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] lupine, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldWolfMorph(lupine)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldWolfMorph(wolf milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] lupine, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType FOX_MORPH = new AbstractBreastType(BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			NippleType.FOX_MORPH,
			FluidType.MILK_FOX_MORPH,
			"[npc.She] now [npc.has] [style.boldFoxMorph(vulpine)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldFoxMorph(fox milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] vulpine, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldFoxMorph(vulpine)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldFoxMorph(fox milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] vulpine, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType COW_MORPH = new AbstractBreastType(BodyCoveringType.BOVINE_FUR,
			Race.COW_MORPH,
			NippleType.COW_MORPH,
			FluidType.MILK_COW_MORPH,
			"[npc.She] now [npc.has] [style.boldCowMorph(bovine)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldCowMorph(cow milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] bovine, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldCowMorph(bovine)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldCowMorph(cow milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] bovine, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType CAT_MORPH = new AbstractBreastType(BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			NippleType.CAT_MORPH,
			FluidType.MILK_CAT_MORPH,
			"[npc.She] now [npc.has] [style.boldCatMorph(feline)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldCatMorph(cat milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] feline, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldCatMorph(feline)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldCatMorph(cat milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] feline, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType SQUIRREL_MORPH = new AbstractBreastType(BodyCoveringType.SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			NippleType.SQUIRREL_MORPH,
			FluidType.MILK_SQUIRREL_MORPH,
			"[npc.She] now [npc.has] [style.boldSquirrelMorph(squirrel-like)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldSquirrelMorph(squirrel milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] squirrel-like, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldSquirrelMorph(squirrel-like)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldSquirrelMorph(squirrel milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] squirrel-like, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType RAT_MORPH = new AbstractBreastType(BodyCoveringType.RAT_FUR,
			Race.RAT_MORPH,
			NippleType.RAT_MORPH,
			FluidType.MILK_RAT_MORPH, // Rats?! I'm outraged! You promised me dog or higher!
			"[npc.She] now [npc.has] [style.boldRatMorph(rat-like)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldRatMorph(rat milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] rat-like, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldRatMorph(rat-like)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldRatMorph(rat milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] rat-like, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType RABBIT_MORPH = new AbstractBreastType(BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			NippleType.RABBIT_MORPH,
			FluidType.MILK_RABBIT_MORPH,
			"[npc.She] now [npc.has] [style.boldRabbitMorph(rabbit-like)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldRabbitMorph(rabbit milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] rabbit-like, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldRabbitMorph(rabbit-like)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldRabbitMorph(rabbit milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] rabbit-like, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType BAT_MORPH = new AbstractBreastType(BodyCoveringType.BAT_FUR,
			Race.BAT_MORPH,
			NippleType.BAT_MORPH,
			FluidType.MILK_BAT_MORPH,
			"[npc.She] now [npc.has] [style.boldBatMorph(bat-like)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldBatMorph(bat milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] bat-like, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldBatMorph(bat-like)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldBatMorph(bat milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] bat-like, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType ALLIGATOR_MORPH = new AbstractBreastType(BodyCoveringType.ALLIGATOR_SCALES,
			Race.ALLIGATOR_MORPH,
			NippleType.ALLIGATOR_MORPH,
			FluidType.MILK_ALLIGATOR_MORPH, // Hmm
			"[npc.She] now [npc.has] [style.boldGatorMorph(reptilian)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldGatorMorph(alligator milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] reptilian, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldGatorMorph(reptilian)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldGatorMorph(alligator milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] reptilian, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType HORSE_MORPH = new AbstractBreastType(BodyCoveringType.HORSE_HAIR,
			Race.HORSE_MORPH,
			NippleType.HORSE_MORPH,
			FluidType.MILK_HORSE_MORPH,
			"[npc.She] now [npc.has] [style.boldHorseMorph(equine)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldHorseMorph(horse milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] equine, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldHorseMorph(equine)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldHorseMorph(horse milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] equine, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType REINDEER_MORPH = new AbstractBreastType(BodyCoveringType.REINDEER_FUR,
			Race.REINDEER_MORPH,
			NippleType.REINDEER_MORPH,
			FluidType.MILK_REINDEER_MORPH,
			"[npc.She] now [npc.has] [style.boldReindeerMorph(reindeer-like)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldReindeerMorph(reindeer milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] reindeer-like, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldReindeerMorph(reindeer-like)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldReindeerMorph(reindeer milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] reindeer-like, [npc.crotchNipplesFullDescription]."){
	};
	
	public static AbstractBreastType HARPY = new AbstractBreastType(BodyCoveringType.FEATHERS,
			Race.HARPY,
			NippleType.HARPY,
			FluidType.MILK_HARPY, // hmm
			"[npc.She] now [npc.has] [style.boldHarpy(avian)], [npc.nipplesFullDescription], and when lactating, [npc.she] will produce [style.boldHarpy(harpy milk)].",
			"On each of [npc.her] [npc.breastSize] [npc.breasts], [npc.she] [npc.has] [npc.nipplesPerBreast] avian, [npc.nipplesFullDescription].",
			"[npc.She] now [npc.has] [style.boldHarpy(avian)], [npc.crotchNipplesFullDescription], and when lactating, [npc.she] will produce [style.boldHarpy(harpy milk)].",
			"On each of [npc.her] [npc.crotchBoobSize] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchNipplesPerBreast] avian, [npc.crotchNipplesFullDescription]."){
	};

	TypeTable<BreastType> table = new TypeTable<>(
			BreastType::sanitize,
			BreastType.class,
			AbstractBreastType.class,
			"breast",
			AbstractBreastType::new);

	static BreastType getBreastTypeFromId(String id) {
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

	static String getIdFromBreastType(BreastType breastType) {
		return breastType.getId();
	}

	static List<BreastType> getAllBreastTypes() {
		return table.listByRace();
	}

	static List<BreastType> getBreastTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
	
}