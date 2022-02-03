package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractTesticleType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.8.8
 * @author Innoxia
 */
public interface TesticleType extends BodyPartTypeInterface {

	FluidType getFluidType();

	boolean isInternal();

	@Override
	default String getDeterminer(GameCharacter gc) {
		if(gc.getTesticleCount()==2) {
			return "a pair of";
		} else if(gc.getTesticleCount()==3) {
			return "a trio of";
		} else {
			return Util.intToString(gc.getTesticleCount());
		}
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return true;
	}

	public static AbstractTesticleType NONE = new Special(null, Race.NONE, FluidType.CUM_HUMAN, false) {
	};

	public static AbstractTesticleType DILDO = new Special(BodyCoveringType.table.of("RUBBER_MAIN_SKIN"), Race.NONE, FluidType.CUM_HUMAN, false) {
	};
	
	public static AbstractTesticleType HUMAN = new Special(BodyCoveringType.PENIS, Race.HUMAN, FluidType.CUM_HUMAN, false) {
	};

	public static AbstractTesticleType ANGEL = new Special(BodyCoveringType.PENIS, Race.ANGEL, FluidType.CUM_ANGEL, false) {
	};

	public static AbstractTesticleType DEMON_COMMON = new Special(BodyCoveringType.PENIS, Race.DEMON, FluidType.CUM_DEMON, false) {
	};

	public static AbstractTesticleType BOVINE = new Special(BodyCoveringType.BOVINE_FUR, Race.COW_MORPH, FluidType.CUM_COW_MORPH, false) {
	};
	
	public static AbstractTesticleType CANINE = new Special(BodyCoveringType.CANINE_FUR, Race.DOG_MORPH, FluidType.CUM_DOG_MORPH, false) {
	};
	
	public static AbstractTesticleType LUPINE = new Special(BodyCoveringType.LYCAN_FUR, Race.WOLF_MORPH, FluidType.CUM_WOLF_MORPH, false) {
	};
	
	public static AbstractTesticleType FOX_MORPH = new Special(BodyCoveringType.FOX_FUR, Race.FOX_MORPH, FluidType.CUM_FOX_MORPH, false) {
	};

	public static AbstractTesticleType FELINE = new Special(BodyCoveringType.FELINE_FUR, Race.CAT_MORPH, FluidType.CUM_CAT_MORPH, false) {
	};

	public static AbstractTesticleType ALLIGATOR_MORPH = new Special(BodyCoveringType.PENIS, Race.ALLIGATOR_MORPH, FluidType.CUM_ALLIGATOR_MORPH, true) {
	};

	public static AbstractTesticleType EQUINE = new Special(BodyCoveringType.PENIS, Race.HORSE_MORPH, FluidType.CUM_HORSE_MORPH, false) {
	};

	public static AbstractTesticleType REINDEER_MORPH = new Special(BodyCoveringType.PENIS, Race.REINDEER_MORPH, FluidType.CUM_REINDEER_MORPH, false) {
	};

	public static AbstractTesticleType AVIAN = new Special(BodyCoveringType.PENIS, Race.HARPY, FluidType.CUM_HARPY, true) {
	};
	
	public static AbstractTesticleType SQUIRREL = new Special(BodyCoveringType.SQUIRREL_FUR, Race.SQUIRREL_MORPH, FluidType.CUM_SQUIRREL_MORPH, false) {
	};

	public static AbstractTesticleType RAT_MORPH = new Special(BodyCoveringType.PENIS, Race.RAT_MORPH, FluidType.CUM_RAT_MORPH, false) {
	};
	
	public static AbstractTesticleType RABBIT_MORPH = new Special(BodyCoveringType.PENIS, Race.RABBIT_MORPH, FluidType.CUM_RABBIT_MORPH, false) {
	};

	public static AbstractTesticleType BAT_MORPH = new Special(BodyCoveringType.PENIS, Race.BAT_MORPH, FluidType.CUM_BAT_MORPH, false) {
	};

	class Special extends AbstractTesticleType {

		private String id;

		public Special(BodyCoveringType coveringType, Race race, FluidType fluidType, boolean internal, List<String> names, List<String> namesPlural, List<String> descriptors) {
			super(coveringType, race, fluidType, internal, names, namesPlural, descriptors);
		}

		public Special(BodyCoveringType skinType, Race race, FluidType fluidType, boolean internal) {
			super(skinType, race, fluidType, internal);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(TesticleType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<TesticleType> table = new TypeTable<>(
		s->s,
		TesticleType.class,
		AbstractTesticleType.class,
		"testicle",
		(f,n,a,m)->new AbstractTesticleType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	static TesticleType getTesticleTypeFromId(String id) {
		return table.of(id);
	}

	static String getIdFromTesticleType(TesticleType testicleType) {
		return testicleType.getId();
	}

	static List<TesticleType> getAllTesticleTypes() {
		return table.listByRace();
	}
	
	static List<TesticleType> getTesticleTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
	
}