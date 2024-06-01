package com.lilithsthrone.game.character.body.types;

import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractFluidType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.FluidFlavour;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.character.body.valueEnums.FluidTypeBase;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.8.2
 * @author Innoxia
 */
public interface FluidType extends BodyPartTypeInterface {

	FluidTypeBase getBaseType();

	FluidFlavour getFlavour();

	default List<FluidModifier> getDefaultFluidModifiers() {
		return List.of();
	}

	default float getValueModifier() {
		return 1f;
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return false;
	}

	@Override
	default String getDeterminer(GameCharacter gc) {
		return "some";
	}

	@Override
	default String getNameSingular(GameCharacter c) {
		return Util.randomItemFrom(getBaseType().getNames());
	}

	@Override
	default String getNamePlural(GameCharacter c) {
		return getNameSingular(c);
	}

	@Override
	default BodyCoveringType getBodyCoveringType(Body body) {
		return getBaseType().getCoveringType();
	}

	// Cum:

	CoreFluidType CUM_HUMAN = CoreFluidType.CUM_HUMAN;

	public static AbstractFluidType CUM_DOLL = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.DOLL,
			Util.newArrayListOfValues("doll-"),
			Util.newArrayListOfValues("doll-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY,
					FluidModifier.MINERAL_OIL)) {
		@Override
		public float getValueModifier() {
			return 0.25f;
		}
	};


	CoreFluidType CUM_ANGEL = CoreFluidType.CUM_ANGEL;

	CoreFluidType CUM_DEMON = CoreFluidType.CUM_DEMON;

	CoreFluidType CUM_DOG_MORPH = CoreFluidType.CUM_DOG_MORPH;

	CoreFluidType CUM_WOLF_MORPH = CoreFluidType.CUM_WOLF_MORPH;

	CoreFluidType CUM_FOX_MORPH = CoreFluidType.CUM_FOX_MORPH;

	CoreFluidType CUM_CAT_MORPH = CoreFluidType.CUM_CAT_MORPH;

	CoreFluidType CUM_SQUIRREL_MORPH = CoreFluidType.CUM_SQUIRREL_MORPH;

	CoreFluidType CUM_RAT_MORPH = CoreFluidType.CUM_RAT_MORPH;

	CoreFluidType CUM_RABBIT_MORPH = CoreFluidType.CUM_RABBIT_MORPH;

	CoreFluidType CUM_BAT_MORPH = CoreFluidType.CUM_BAT_MORPH;

	CoreFluidType CUM_ALLIGATOR_MORPH = CoreFluidType.CUM_ALLIGATOR_MORPH;

	CoreFluidType CUM_HORSE_MORPH = CoreFluidType.CUM_HORSE_MORPH;

	CoreFluidType CUM_REINDEER_MORPH = CoreFluidType.CUM_REINDEER_MORPH;

	CoreFluidType CUM_COW_MORPH = CoreFluidType.CUM_COW_MORPH;

	CoreFluidType CUM_HARPY = CoreFluidType.CUM_HARPY;

	// Girl cum:

	CoreFluidType GIRL_CUM_HUMAN = CoreFluidType.GIRL_CUM_HUMAN;

	public static AbstractFluidType GIRL_CUM_DOLL = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.DOLL,
			Util.newArrayListOfValues("doll-"),
			Util.newArrayListOfValues("doll-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY,
					FluidModifier.MINERAL_OIL)) {
		@Override
		public float getValueModifier() {
			return 0.25f;
		}
	};

	CoreFluidType GIRL_CUM_ANGEL = CoreFluidType.GIRL_CUM_ANGEL;

	CoreFluidType GIRL_CUM_DEMON = CoreFluidType.GIRL_CUM_DEMON;

	CoreFluidType GIRL_CUM_DOG_MORPH = CoreFluidType.GIRL_CUM_DOG_MORPH;

	CoreFluidType GIRL_CUM_WOLF_MORPH = CoreFluidType.GIRL_CUM_WOLF_MORPH;

	CoreFluidType GIRL_CUM_FOX_MORPH = CoreFluidType.GIRL_CUM_FOX_MORPH;

	CoreFluidType GIRL_CUM_CAT_MORPH = CoreFluidType.GIRL_CUM_CAT_MORPH;

	CoreFluidType GIRL_CUM_SQUIRREL_MORPH = CoreFluidType.GIRL_CUM_SQUIRREL_MORPH;

	CoreFluidType GIRL_CUM_RAT_MORPH = CoreFluidType.GIRL_CUM_RAT_MORPH;

	CoreFluidType GIRL_CUM_RABBIT_MORPH = CoreFluidType.GIRL_CUM_RABBIT_MORPH;

	CoreFluidType GIRL_CUM_BAT_MORPH = CoreFluidType.GIRL_CUM_BAT_MORPH;

	CoreFluidType GIRL_CUM_ALLIGATOR_MORPH = CoreFluidType.GIRL_CUM_ALLIGATOR_MORPH;

	CoreFluidType GIRL_CUM_HORSE_MORPH = CoreFluidType.GIRL_CUM_HORSE_MORPH;

	CoreFluidType GIRL_CUM_REINDEER_MORPH = CoreFluidType.GIRL_CUM_REINDEER_MORPH;

	CoreFluidType GIRL_CUM_COW_MORPH = CoreFluidType.GIRL_CUM_COW_MORPH;

	CoreFluidType GIRL_CUM_HARPY = CoreFluidType.GIRL_CUM_HARPY;

	// Milks:

	CoreFluidType MILK_HUMAN = CoreFluidType.MILK_HUMAN;

	public static AbstractFluidType MILK_DOLL = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.DOLL,
			Util.newArrayListOfValues("doll-"),
			Util.newArrayListOfValues("doll-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY,
					FluidModifier.MINERAL_OIL)) {
		@Override
		public float getValueModifier() {
			return 0.25f;
		}
	};

	CoreFluidType MILK_ANGEL = CoreFluidType.MILK_ANGEL;

	CoreFluidType MILK_COW_MORPH = CoreFluidType.MILK_COW_MORPH;

	CoreFluidType MILK_DEMON = CoreFluidType.MILK_DEMON;

	CoreFluidType MILK_DOG_MORPH = CoreFluidType.MILK_DOG_MORPH;

	CoreFluidType MILK_WOLF_MORPH = CoreFluidType.MILK_WOLF_MORPH;

	CoreFluidType MILK_FOX_MORPH = CoreFluidType.MILK_FOX_MORPH;

	CoreFluidType MILK_CAT_MORPH = CoreFluidType.MILK_CAT_MORPH;

	CoreFluidType MILK_SQUIRREL_MORPH = CoreFluidType.MILK_SQUIRREL_MORPH;

	CoreFluidType MILK_RAT_MORPH = CoreFluidType.MILK_RAT_MORPH;

	CoreFluidType MILK_RABBIT_MORPH = CoreFluidType.MILK_RABBIT_MORPH;

	CoreFluidType MILK_BAT_MORPH = CoreFluidType.MILK_BAT_MORPH;

	CoreFluidType MILK_ALLIGATOR_MORPH = CoreFluidType.MILK_ALLIGATOR_MORPH;

	CoreFluidType MILK_HORSE_MORPH = CoreFluidType.MILK_HORSE_MORPH;

	CoreFluidType MILK_REINDEER_MORPH = CoreFluidType.MILK_REINDEER_MORPH;

	CoreFluidType MILK_HARPY = CoreFluidType.MILK_HARPY;

	TypeTable<FluidType> table = new TypeTable<>(
			FluidType::sanitize,
		FluidType.class,
			AbstractFluidType.class,
			"fluid",
			AbstractFluidType::new);
	//TODO remove this
	Object initializer = new Object() {{
		for(CoreFluidType t : CoreFluidType.values())
			table.add(t.getId(), t);
	}};

	static FluidType getFluidTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		return switch(id) {
			case "CUM_IMP" -> "CUM_DEMON";
			case "GIRL_CUM_IMP" -> "GIRL_CUM_DEMON";
			case "MILK_IMP", "MILK_DEMON_COMMON" -> "MILK_DEMON";
			default -> id;
		};
	}

	static String getIdFromFluidType(FluidType fluidType) {
		return fluidType.getId();
	}

	static List<FluidType> getAllFluidTypes() {
		return table.listByRace();
	}

	static List<FluidType> getFluidTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
}