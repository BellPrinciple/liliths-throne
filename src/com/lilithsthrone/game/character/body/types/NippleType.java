package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractNippleType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.8.2
 * @author Innoxia
 */
public interface NippleType extends BodyPartTypeInterface {

	public static AbstractNippleType HUMAN = new Special(BodyCoveringType.NIPPLES,
			Race.HUMAN,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType ANGEL = new Special(BodyCoveringType.NIPPLES,
			Race.ANGEL,
			Util.newArrayListOfValues("perfect", "flawless"),
			Util.newArrayListOfValues("perfect", "flawless"),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType DEMON = new Special(BodyCoveringType.NIPPLES,
			Race.DEMON,
			Util.newArrayListOfValues("perfect", "flawless"),
			Util.newArrayListOfValues("perfect", "flawless"),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType DOG_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.DOG_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType WOLF_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.WOLF_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType FOX_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.FOX_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType CAT_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.CAT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType COW_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.COW_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType SQUIRREL_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.SQUIRREL_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType RAT_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.RAT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType BAT_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.BAT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType RABBIT_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.RABBIT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType ALLIGATOR_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.ALLIGATOR_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType HORSE_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.HORSE_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType REINDEER_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.REINDEER_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	public static AbstractNippleType HARPY = new Special(BodyCoveringType.NIPPLES,
			Race.HARPY,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	class Special extends AbstractNippleType {

		private String id;

		public Special(AbstractBodyCoveringType coveringType, Race race, List<String> descriptorsMasculine, List<String> descriptorsFeminine, List<OrificeModifier> defaultRacialOrificeModifiers) {
			super(coveringType, race, descriptorsMasculine, descriptorsFeminine, defaultRacialOrificeModifiers);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(NippleType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}
//	/**
//	 * Use instead of <i>valueOf()</i>.
//	 */
//	public static NippleType getTypeFromString(String value) {
//		if(value.equals("IMP")) {
//			value = "DEMON_COMMON";
//		}
//		return valueOf(value);
//	}
	
	TypeTable<AbstractNippleType> table = new TypeTable<>(
		NippleType::sanitize,
		NippleType.class,
		AbstractNippleType.class,
		"nipple",
		(f,n,a,m)->new AbstractNippleType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	public static AbstractNippleType getNippleTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP") || id.equals("DEMON_COMMON")) {
			return "DEMON";
		}
		
		return id;
	}

	@Deprecated
	public static String getIdFromNippleType(AbstractNippleType nippleType) {
		return nippleType.getId();
	}

	@Deprecated
	public static List<AbstractNippleType> getAllNippleTypes() {
		return table.listByRace();
	}
	
	@Deprecated
	public static List<AbstractNippleType> getNippleTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
}