package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractNippleType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

import static com.lilithsthrone.game.dialogue.utils.UtilText.returnStringAtRandom;

/**
 * @since 0.1.83
 * @version 0.3.8.2
 * @author Innoxia
 */
public interface NippleType extends BodyPartTypeInterface {

	default List<OrificeModifier> getDefaultRacialOrificeModifiers() {
		return List.of();
	}

	@Override
	default BodyCoveringType getBodyCoveringType(Body body) {
		return BodyCoveringType.NIPPLES;
	}

	@Override
	default String getDeterminer(GameCharacter gc) {
		return gc.getBreastRows()==1 ? "a pair of" : gc.getBreastRows()==2 ? "two pairs of" : "three pairs of";
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return true;
	}

	@Override
	default String getNameSingular(GameCharacter gc) {
		switch(gc.getNippleShape()) {
		case LIPS:
			return returnStringAtRandom("lipple", "nipple-lip");
		case INVERTED:
		case NORMAL:
			if(gc.hasBreasts()) {
				return returnStringAtRandom("nipple", "teat");
			} else {
				return "nipple";
			}
		case VAGINA:
			return returnStringAtRandom("nipple-cunt", "nipple-pussy");
		}
		return "";
	}

	@Override
	default String getNamePlural(GameCharacter gc) {
		switch(gc.getNippleShape()) {
		case LIPS:
			return  returnStringAtRandom("lipples", "nipple-lips");
		case INVERTED:
		case NORMAL:
			if(gc.hasBreasts()) {
				return returnStringAtRandom("nipples", "teats");
			} else {
				return "nipples";
			}
		case VAGINA:
			return returnStringAtRandom("nipple-cunts", "nipple-pussies");
		}
		return "";
	}

	default String getNameCrotchSingular(GameCharacter c) {
		switch(c.getNippleCrotchShape()) {
		case LIPS:
			return returnStringAtRandom("lipple", "nipple-lip");
		case INVERTED:
		case NORMAL:
			return returnStringAtRandom("nipple", c.hasBreasts()?"teat":"");
		case VAGINA:
			return returnStringAtRandom("nipple-cunt", "nipple-pussy");
		}
		return "";
	}

	default String getNameCrotchPlural(GameCharacter c) {
		switch(c.getNippleCrotchShape()) {
		case LIPS:
			return returnStringAtRandom("lipples", "nipple-lips");
		case INVERTED:
		case NORMAL:
			return returnStringAtRandom("nipples", c.hasBreasts()?"teats":"");
		case VAGINA:
			return returnStringAtRandom("nipple-cunts", "nipple-pussies");
		}
		return "";
	}

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

		public Special(BodyCoveringType coveringType, Race race, List<String> descriptorsMasculine, List<String> descriptorsFeminine, List<OrificeModifier> defaultRacialOrificeModifiers) {
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
	
	TypeTable<NippleType> table = new TypeTable<>(
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

	static NippleType getNippleTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP") || id.equals("DEMON_COMMON")) {
			return "DEMON";
		}
		
		return id;
	}

	static String getIdFromNippleType(NippleType nippleType) {
		return nippleType.getId();
	}

	static List<NippleType> getAllNippleTypes() {
		return table.listByRace();
	}
	
	static List<NippleType> getNippleTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
}