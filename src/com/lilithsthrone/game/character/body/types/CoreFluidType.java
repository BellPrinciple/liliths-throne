package com.lilithsthrone.game.character.body.types;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.valueEnums.FluidFlavour;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.character.body.valueEnums.FluidTypeBase;
import com.lilithsthrone.game.character.race.CoreRace;
import com.lilithsthrone.utils.Util;

import java.util.List;

public enum CoreFluidType implements FluidType {
	// Cum:
	CUM_HUMAN,
	CUM_ANGEL,
	CUM_DEMON,
	CUM_DOG_MORPH,
	CUM_WOLF_MORPH,
	CUM_FOX_MORPH,
	CUM_CAT_MORPH,
	CUM_SQUIRREL_MORPH,
	CUM_RAT_MORPH,
	CUM_RABBIT_MORPH,
	CUM_BAT_MORPH,
	CUM_ALLIGATOR_MORPH,
	CUM_HORSE_MORPH,
	CUM_REINDEER_MORPH,
	CUM_COW_MORPH,
	CUM_HARPY,
	// Girl cum:
	GIRL_CUM_HUMAN,
	GIRL_CUM_ANGEL,
	GIRL_CUM_DEMON,
	GIRL_CUM_DOG_MORPH,
	GIRL_CUM_WOLF_MORPH,
	GIRL_CUM_FOX_MORPH,
	GIRL_CUM_CAT_MORPH,
	GIRL_CUM_SQUIRREL_MORPH,
	GIRL_CUM_RAT_MORPH,
	GIRL_CUM_RABBIT_MORPH,
	GIRL_CUM_BAT_MORPH,
	GIRL_CUM_ALLIGATOR_MORPH,
	GIRL_CUM_HORSE_MORPH,
	GIRL_CUM_REINDEER_MORPH,
	GIRL_CUM_COW_MORPH,
	GIRL_CUM_HARPY,
	// Milks:
	MILK_HUMAN,
	MILK_ANGEL,
	MILK_DEMON,
	MILK_DOG_MORPH,
	MILK_WOLF_MORPH,
	MILK_FOX_MORPH,
	MILK_CAT_MORPH,
	MILK_SQUIRREL_MORPH,
	MILK_RAT_MORPH,
	MILK_RABBIT_MORPH,
	MILK_BAT_MORPH,
	MILK_ALLIGATOR_MORPH,
	MILK_HORSE_MORPH,
	MILK_REINDEER_MORPH,
	MILK_COW_MORPH,
	MILK_HARPY,
	;

	@Override
	public final FluidTypeBase getBaseType() {
		return switch(this) {
			case CUM_HUMAN, CUM_ANGEL, CUM_DEMON,
					CUM_DOG_MORPH, CUM_WOLF_MORPH, CUM_FOX_MORPH,
					CUM_CAT_MORPH, CUM_SQUIRREL_MORPH, CUM_RAT_MORPH,
					CUM_RABBIT_MORPH, CUM_BAT_MORPH, CUM_ALLIGATOR_MORPH,
					CUM_HORSE_MORPH, CUM_REINDEER_MORPH, CUM_COW_MORPH,
					CUM_HARPY -> FluidTypeBase.CUM;
			case GIRL_CUM_HUMAN, GIRL_CUM_ANGEL, GIRL_CUM_DEMON,
					GIRL_CUM_DOG_MORPH, GIRL_CUM_WOLF_MORPH, GIRL_CUM_FOX_MORPH,
					GIRL_CUM_CAT_MORPH, GIRL_CUM_SQUIRREL_MORPH, GIRL_CUM_RAT_MORPH,
					GIRL_CUM_RABBIT_MORPH, GIRL_CUM_BAT_MORPH, GIRL_CUM_ALLIGATOR_MORPH,
					GIRL_CUM_HORSE_MORPH, GIRL_CUM_REINDEER_MORPH, GIRL_CUM_COW_MORPH,
					GIRL_CUM_HARPY -> FluidTypeBase.GIRLCUM;
			case MILK_HUMAN, MILK_ANGEL, MILK_DEMON,
					MILK_DOG_MORPH, MILK_WOLF_MORPH, MILK_FOX_MORPH,
					MILK_CAT_MORPH, MILK_SQUIRREL_MORPH, MILK_RAT_MORPH,
					MILK_RABBIT_MORPH, MILK_BAT_MORPH, MILK_ALLIGATOR_MORPH,
					MILK_HORSE_MORPH, MILK_REINDEER_MORPH, MILK_COW_MORPH,
					MILK_HARPY -> FluidTypeBase.MILK;
		};
	}

	@Override
	public final FluidFlavour getFlavour() {
		return switch(getBaseType()) {
			case CUM -> FluidFlavour.CUM;
			case GIRLCUM -> FluidFlavour.GIRL_CUM;
			case MILK -> FluidFlavour.MILK;
		};
	}

	@Override
	public List<FluidModifier> getDefaultFluidModifiers() {
		return switch(getBaseType()) {
			case CUM -> switch(this) {
				case CUM_WOLF_MORPH, CUM_HORSE_MORPH, CUM_REINDEER_MORPH, CUM_COW_MORPH -> List.of(
						FluidModifier.MUSKY,
						FluidModifier.STICKY,
						FluidModifier.SLIMY);
				default -> List.of(
						FluidModifier.STICKY,
						FluidModifier.SLIMY);
			};
			case GIRLCUM -> List.of(
					FluidModifier.SLIMY);
			case MILK -> List.of();
		};
	}

	@Override
	public String getNameSingular(GameCharacter c) {
		String suffix = FluidType.super.getNameSingular(c);
		if(getBaseType() != FluidTypeBase.CUM)
			return suffix;
		// 25% chance to return this '-' name.
		if(Math.random() >= .25f)
			return suffix;
		String prefix = switch(getRace()) {
			case HUMAN -> "";
			case ANGEL -> "angel-";
			case DEMON -> Util.randomItemFromValues("demon-", "demonic-");
			case DOG_MORPH -> Util.randomItemFromValues(c.isFeminine() ? "bitch-" : "dog-", "canine-");
			case WOLF_MORPH -> Util.randomItemFromValues("wolf-", "lupine-");
			case FOX_MORPH -> Util.randomItemFromValues(c.isFeminine() ? "vixen-" : "fox-", "vulpine-");
			case CAT_MORPH -> Util.randomItemFromValues("cat-", "feline-");
			case SQUIRREL_MORPH -> Util.randomItemFromValues("squirrel-", "rodent-");
			case RAT_MORPH -> Util.randomItemFromValues("rat-", "rodent-");
			case RABBIT_MORPH -> "rabbit-";
			case BAT_MORPH -> "bat-";
			case ALLIGATOR_MORPH -> Util.randomItemFromValues("alligator-", "reptilian-");
			case HORSE_MORPH -> Util.randomItemFromValues(c.isFeminine() ? "mare-" : "stallion-", "horse-", "equine-");
			case REINDEER_MORPH -> "reindeer-";
			case COW_MORPH -> Util.randomItemFromValues(c.isFeminine() ? "bull-" : "cow-", "bovine-");
			case HARPY -> Util.randomItemFromValues("harpy-", "avian-");
		};
		return prefix + suffix;
	}

	@Override
	public String getId() {
		return name();
	}

	@Override
	public String getDescriptor(GameCharacter gc) {
		if(getBaseType() == FluidTypeBase.CUM)
			return "";
		return switch(getRace()) {
			case HUMAN -> "";
			case ANGEL -> "angelic";
			case DEMON -> "demonic";
			case DOG_MORPH -> "canine";
			case WOLF_MORPH -> "lupine";
			case FOX_MORPH -> "vulpine";
			case CAT_MORPH -> "feline";
			case SQUIRREL_MORPH -> "squirrel";
			case RAT_MORPH -> "rat";
			case RABBIT_MORPH -> "rabbit";
			case BAT_MORPH -> "bat";
			case ALLIGATOR_MORPH -> "alligator";
			case HORSE_MORPH -> "equine";
			case REINDEER_MORPH -> "reindeer";
			case COW_MORPH -> "bovine";
			case HARPY -> "avian";
		};
	}

	@Override
	public final CoreRace getRace() {
		return switch(this) {
			case CUM_HUMAN, GIRL_CUM_HUMAN, MILK_HUMAN -> CoreRace.HUMAN;
			case CUM_ANGEL, GIRL_CUM_ANGEL, MILK_ANGEL -> CoreRace.ANGEL;
			case CUM_DEMON, GIRL_CUM_DEMON, MILK_DEMON -> CoreRace.DEMON;
			case CUM_DOG_MORPH, GIRL_CUM_DOG_MORPH, MILK_DOG_MORPH -> CoreRace.DOG_MORPH;
			case CUM_WOLF_MORPH, GIRL_CUM_WOLF_MORPH, MILK_WOLF_MORPH -> CoreRace.WOLF_MORPH;
			case CUM_FOX_MORPH, GIRL_CUM_FOX_MORPH, MILK_FOX_MORPH -> CoreRace.FOX_MORPH;
			case CUM_CAT_MORPH, GIRL_CUM_CAT_MORPH, MILK_CAT_MORPH -> CoreRace.CAT_MORPH;
			case CUM_SQUIRREL_MORPH, GIRL_CUM_SQUIRREL_MORPH, MILK_SQUIRREL_MORPH -> CoreRace.SQUIRREL_MORPH;
			case CUM_RAT_MORPH, GIRL_CUM_RAT_MORPH, MILK_RAT_MORPH -> CoreRace.RAT_MORPH;
			case CUM_RABBIT_MORPH, GIRL_CUM_RABBIT_MORPH, MILK_RABBIT_MORPH -> CoreRace.RABBIT_MORPH;
			case CUM_BAT_MORPH, GIRL_CUM_BAT_MORPH, MILK_BAT_MORPH -> CoreRace.BAT_MORPH;
			case CUM_ALLIGATOR_MORPH, GIRL_CUM_ALLIGATOR_MORPH, MILK_ALLIGATOR_MORPH -> CoreRace.ALLIGATOR_MORPH;
			case CUM_HORSE_MORPH, GIRL_CUM_HORSE_MORPH, MILK_HORSE_MORPH -> CoreRace.HORSE_MORPH;
			case CUM_REINDEER_MORPH, GIRL_CUM_REINDEER_MORPH, MILK_REINDEER_MORPH -> CoreRace.REINDEER_MORPH;
			case CUM_COW_MORPH, GIRL_CUM_COW_MORPH, MILK_COW_MORPH -> CoreRace.COW_MORPH;
			case CUM_HARPY, GIRL_CUM_HARPY, MILK_HARPY -> CoreRace.HARPY;
		};
	}


}
