package com.lilithsthrone.game.character.body.types;

import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractFluidType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.FluidFlavour;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.character.body.valueEnums.FluidTypeBase;
import com.lilithsthrone.game.character.race.AbstractRace;
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
	default AbstractBodyCoveringType getBodyCoveringType(Body body) {
		return getBaseType().getCoveringType();
	}

	// Cum:
	
	 public static AbstractFluidType CUM_HUMAN = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.HUMAN,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

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

	public static AbstractFluidType CUM_ANGEL = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.ANGEL,
			Util.newArrayListOfValues("angel-"),
			Util.newArrayListOfValues("angel-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_DEMON = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.DEMON,
			Util.newArrayListOfValues("demon-", "demonic-"),
			Util.newArrayListOfValues("demon-", "demonic-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_DOG_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.DOG_MORPH,
			Util.newArrayListOfValues("dog-", "canine-"),
			Util.newArrayListOfValues("bitch-", "canine-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_WOLF_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.WOLF_MORPH,
			Util.newArrayListOfValues("wolf-", "lupine-"),
			Util.newArrayListOfValues("wolf-", "lupine-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.MUSKY,
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_FOX_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.FOX_MORPH,
			Util.newArrayListOfValues("fox-", "vulpine-"),
			Util.newArrayListOfValues("vixen-", "vulpine-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_CAT_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.CAT_MORPH,
			Util.newArrayListOfValues("cat-", "feline-"),
			Util.newArrayListOfValues("cat-", "feline-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_SQUIRREL_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.SQUIRREL_MORPH,
			Util.newArrayListOfValues("squirrel-", "rodent-"),
			Util.newArrayListOfValues("squirrel-", "rodent-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_RAT_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.RAT_MORPH,
			Util.newArrayListOfValues("rat-", "rodent-"),
			Util.newArrayListOfValues("rat-", "rodent-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_RABBIT_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.RABBIT_MORPH,
			Util.newArrayListOfValues("rabbit-"),
			Util.newArrayListOfValues("rabbit-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_BAT_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.BAT_MORPH,
			Util.newArrayListOfValues("bat-"),
			Util.newArrayListOfValues("bat-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_ALLIGATOR_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.ALLIGATOR_MORPH,
			Util.newArrayListOfValues("alligator-", "reptilian-"),
			Util.newArrayListOfValues("alligator-", "reptilian-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_HORSE_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.HORSE_MORPH,
			Util.newArrayListOfValues("stallion-", "horse-", "equine-"),
			Util.newArrayListOfValues("mare-", "horse-", "equine-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.MUSKY,
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_REINDEER_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.REINDEER_MORPH,
			Util.newArrayListOfValues("reindeer-"),
			Util.newArrayListOfValues("reindeer-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.MUSKY,
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_COW_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.COW_MORPH,
			Util.newArrayListOfValues("bull-", "bovine-"),
			Util.newArrayListOfValues("cow-", "bovine-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.MUSKY,
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_HARPY = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.HARPY,
			Util.newArrayListOfValues("harpy-", "avian-"),
			Util.newArrayListOfValues("harpy-", "avian-"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	// Girl cum:
	
	 public static AbstractFluidType GIRL_CUM_HUMAN = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.HUMAN,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

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

	public static AbstractFluidType GIRL_CUM_ANGEL = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.ANGEL,
			null,
			null,
			Util.newArrayListOfValues("angelic"),
			Util.newArrayListOfValues("angelic"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_DEMON = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.DEMON,
			null,
			null,
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_DOG_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.DOG_MORPH,
			null,
			null,
			Util.newArrayListOfValues("canine"),
			Util.newArrayListOfValues("canine"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_WOLF_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.WOLF_MORPH,
			null,
			null,
			Util.newArrayListOfValues("lupine"),
			Util.newArrayListOfValues("lupine"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_FOX_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.FOX_MORPH,
			null,
			null,
			Util.newArrayListOfValues("vulpine"),
			Util.newArrayListOfValues("vulpine"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_CAT_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.CAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("feline"),
			Util.newArrayListOfValues("feline"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_SQUIRREL_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.SQUIRREL_MORPH,
			null,
			null,
			Util.newArrayListOfValues("squirrel"),
			Util.newArrayListOfValues("squirrel"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_RAT_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.RAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("rat"),
			Util.newArrayListOfValues("rat"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_RABBIT_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.RABBIT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("rabbit"),
			Util.newArrayListOfValues("rabbit"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_BAT_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.BAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("bat"),
			Util.newArrayListOfValues("bat"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_ALLIGATOR_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.ALLIGATOR_MORPH,
			null,
			null,
			Util.newArrayListOfValues("alligator"),
			Util.newArrayListOfValues("alligator"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_HORSE_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.HORSE_MORPH,
			null,
			null,
			Util.newArrayListOfValues("equine"),
			Util.newArrayListOfValues("equine"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_REINDEER_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.REINDEER_MORPH,
			null,
			null,
			Util.newArrayListOfValues("reindeer"),
			Util.newArrayListOfValues("reindeer"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_COW_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.COW_MORPH,
			null,
			null,
			Util.newArrayListOfValues("bovine"),
			Util.newArrayListOfValues("bovine"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_HARPY = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.HARPY,
			null,
			null,
			Util.newArrayListOfValues("avian"),
			Util.newArrayListOfValues("avian"),
			Util.newArrayListOfValues(
					FluidModifier.SLIMY)) {
	};

	// Milks:
	
	 public static AbstractFluidType MILK_HUMAN = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.HUMAN,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()) {
	};

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

	public static AbstractFluidType MILK_ANGEL = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.ANGEL,
			null,
			null,
			Util.newArrayListOfValues("angelic"),
			Util.newArrayListOfValues("angelic"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_COW_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.COW_MORPH,
			null,
			null,
			Util.newArrayListOfValues("bovine"),
			Util.newArrayListOfValues("bovine"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_DEMON = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.DEMON,
			null,
			null,
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_DOG_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.DOG_MORPH,
			null,
			null,
			Util.newArrayListOfValues("canine"),
			Util.newArrayListOfValues("canine"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_WOLF_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.WOLF_MORPH,
			null,
			null,
			Util.newArrayListOfValues("lupine"),
			Util.newArrayListOfValues("lupine"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_FOX_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.FOX_MORPH,
			null,
			null,
			Util.newArrayListOfValues("vulpine"),
			Util.newArrayListOfValues("vulpine"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_CAT_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.CAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("feline"),
			Util.newArrayListOfValues("feline"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_SQUIRREL_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.SQUIRREL_MORPH,
			null,
			null,
			Util.newArrayListOfValues("squirrel"),
			Util.newArrayListOfValues("squirrel"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_RAT_MORPH = new AbstractFluidType(FluidTypeBase.MILK, // I don't get it. Everyone loves rats, but they don't wanna drink the rats' milk?
			FluidFlavour.MILK,
			Race.RAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("rat"),
			Util.newArrayListOfValues("rat"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_RABBIT_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.RABBIT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("rabbit"),
			Util.newArrayListOfValues("rabbit"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_BAT_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.BAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("bat"),
			Util.newArrayListOfValues("bat"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_ALLIGATOR_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.ALLIGATOR_MORPH,
			null,
			null,
			Util.newArrayListOfValues("alligator"),
			Util.newArrayListOfValues("alligator"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_HORSE_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.HORSE_MORPH,
			null,
			null,
			Util.newArrayListOfValues("equine"),
			Util.newArrayListOfValues("equine"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_REINDEER_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.REINDEER_MORPH,
			null,
			null,
			Util.newArrayListOfValues("reindeer"),
			Util.newArrayListOfValues("reindeer"),
			Util.newArrayListOfValues()) {
	};

	 public static AbstractFluidType MILK_HARPY = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.HARPY,
			null,
			null,
			Util.newArrayListOfValues("avian"),
			Util.newArrayListOfValues("avian"),
			Util.newArrayListOfValues()) {
	};

	TypeTable<AbstractFluidType> table = new TypeTable<>(
		FluidType::sanitize,
		FluidType.class,
		AbstractFluidType.class,
		"fluid",
		(f,n,a,m)->{
			AbstractFluidType t = new AbstractFluidType(f,a,m) {};
			t.id = n;
			return t;
		});
	//TODO remove this
	Object initializer = new Object() {{
		for(java.lang.reflect.Field field : FluidType.class.getDeclaredFields()) {
			try {
				if(java.lang.reflect.Modifier.isStatic(field.getModifiers()) && field.get(null) instanceof AbstractFluidType t) {
					t.id = field.getName();
				}
			}
			catch(IllegalAccessException ignore) {}
		}
	}};

	public static AbstractFluidType getFluidTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("CUM_IMP")) {
			id = "CUM_DEMON";
			
		} else if(id.equals("GIRL_CUM_IMP")) {
			id = "GIRL_CUM_DEMON";
			
		} else if(id.equals("MILK_IMP")) {
			id = "MILK_DEMON";
			
		} else if(id.equals("MILK_DEMON_COMMON")) {
			id = "MILK_DEMON";
		}
		
		return id;
	}

	public static String getIdFromFluidType(AbstractFluidType fluidType) {
		return fluidType.getId();
	}
	
	public static List<AbstractFluidType> getAllFluidTypes() {
		return table.listByRace();
	}

	public static List<AbstractFluidType> getFluidTypes(AbstractRace r) {
		return table.of(r).orElse(List.of());
	}
}