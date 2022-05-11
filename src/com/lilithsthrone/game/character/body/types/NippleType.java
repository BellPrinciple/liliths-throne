package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractNippleType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.8.2
 * @author Innoxia
 */
public interface NippleType extends BodyPartTypeInterface {

	List<OrificeModifier> getDefaultRacialOrificeModifiers();

	@Override
	default String getDeterminer(GameCharacter gc) {
		if(gc.getBreastRows()==1) {
			return "a pair of";
		} else if(gc.getBreastRows()==2) {
			return "two pairs of";
		} else {
			return "three pairs of";
		}
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return true;
	}

	@Override
	default String getNameSingular(GameCharacter gc) {
		switch(gc.getNippleShape()) {
		case LIPS:
			return  UtilText.returnStringAtRandom("lipple", "nipple-lip");
		case INVERTED:
		case NORMAL:
			if(gc.hasBreasts()) {
				return UtilText.returnStringAtRandom("nipple", "teat");
			} else {
				return "nipple";
			}
		case VAGINA:
			return UtilText.returnStringAtRandom("nipple-cunt", "nipple-pussy");
		}
		return "";
	}

	@Override
	default String getNamePlural(GameCharacter gc) {
		switch(gc.getNippleShape()) {
		case LIPS:
			return  UtilText.returnStringAtRandom("lipples", "nipple-lips");
		case INVERTED:
		case NORMAL:
			if(gc.hasBreasts()) {
				return UtilText.returnStringAtRandom("nipples", "teats");
			} else {
				return "nipples";
			}
		case VAGINA:
			return UtilText.returnStringAtRandom("nipple-cunts", "nipple-pussies");
		}
		return "";
	}

	default String getNameCrotchSingular(GameCharacter gc) {
		switch(gc.getNippleCrotchShape()) {
		case LIPS:
			return  UtilText.returnStringAtRandom("lipple", "nipple-lip");
		case INVERTED:
		case NORMAL:
			if(gc.hasBreasts()) {
				return UtilText.returnStringAtRandom("nipple", "teat");
			} else {
				return "nipple";
			}
		case VAGINA:
			return UtilText.returnStringAtRandom("nipple-cunt", "nipple-pussy");
		}
		return "";
	}

	default String getNameCrotchPlural(GameCharacter gc) {
		switch(gc.getNippleCrotchShape()) {
		case LIPS:
			return  UtilText.returnStringAtRandom("lipples", "nipple-lips");
		case INVERTED:
		case NORMAL:
			if(gc.hasBreasts()) {
				return UtilText.returnStringAtRandom("nipples", "teats");
			} else {
				return "nipples";
			}
		case VAGINA:
			return UtilText.returnStringAtRandom("nipple-cunts", "nipple-pussies");
		}
		return "";
	}

	NippleType HUMAN = new Special(BodyCoveringType.NIPPLES,
			Race.HUMAN,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType ANGEL = new Special(BodyCoveringType.NIPPLES,
			Race.ANGEL,
			Util.newArrayListOfValues("perfect", "flawless"),
			Util.newArrayListOfValues("perfect", "flawless"),
			Util.newArrayListOfValues()){
	};

	NippleType DEMON = new Special(BodyCoveringType.NIPPLES,
			Race.DEMON,
			Util.newArrayListOfValues("perfect", "flawless"),
			Util.newArrayListOfValues("perfect", "flawless"),
			Util.newArrayListOfValues()){
	};

	NippleType DOG_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.DOG_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType WOLF_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.WOLF_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType FOX_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.FOX_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType CAT_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.CAT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType COW_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.COW_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType SQUIRREL_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.SQUIRREL_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType RAT_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.RAT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType BAT_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.BAT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType RABBIT_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.RABBIT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType ALLIGATOR_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.ALLIGATOR_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType HORSE_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.HORSE_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType REINDEER_MORPH = new Special(BodyCoveringType.NIPPLES,
			Race.REINDEER_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};

	NippleType HARPY = new Special(BodyCoveringType.NIPPLES,
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

	@Deprecated
	static NippleType getNippleTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP") || id.equals("DEMON_COMMON")) {
			return "DEMON";
		}
		
		return id;
	}

	@Deprecated
	static String getIdFromNippleType(NippleType nippleType) {
		return nippleType.getId();
	}

	@Deprecated
	static List<NippleType> getAllNippleTypes() {
		return table.listByRace();
	}
	
	@Deprecated
	static List<NippleType> getNippleTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
}