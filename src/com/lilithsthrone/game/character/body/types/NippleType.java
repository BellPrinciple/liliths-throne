package com.lilithsthrone.game.character.body.types;

import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractNippleType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;

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

	NippleType HUMAN = Special.HUMAN;

	NippleType ANGEL = Special.ANGEL;

	NippleType DEMON = Special.DEMON;

	NippleType DOG_MORPH = Special.DOG_MORPH;

	NippleType WOLF_MORPH = Special.WOLF_MORPH;

	NippleType FOX_MORPH = Special.FOX_MORPH;

	NippleType CAT_MORPH = Special.CAT_MORPH;

	NippleType COW_MORPH = Special.COW_MORPH;

	NippleType SQUIRREL_MORPH = Special.SQUIRREL_MORPH;

	NippleType RAT_MORPH = Special.RAT_MORPH;

	NippleType BAT_MORPH = Special.BAT_MORPH;

	NippleType RABBIT_MORPH = Special.RABBIT_MORPH;

	NippleType ALLIGATOR_MORPH = Special.ALLIGATOR_MORPH;

	NippleType HORSE_MORPH = Special.HORSE_MORPH;

	NippleType REINDEER_MORPH = Special.REINDEER_MORPH;

	NippleType HARPY = Special.HARPY;

	enum Special implements NippleType {
		HUMAN,
		ANGEL,
		DEMON,
		DOG_MORPH,
		WOLF_MORPH,
		FOX_MORPH,
		CAT_MORPH,
		COW_MORPH,
		SQUIRREL_MORPH,
		RAT_MORPH,
		BAT_MORPH,
		RABBIT_MORPH,
		ALLIGATOR_MORPH,
		HORSE_MORPH,
		REINDEER_MORPH,
		HARPY,
		;

		@Override
		public String getId() {
			return name();
		}

		@Override
		public AbstractBodyCoveringType getBodyCoveringType(Body body) {
			return BodyCoveringType.NIPPLES;
		}

		@Override
		public Race getRace() {
			switch(this) {
			case HUMAN: return Race.HUMAN;
			case ANGEL: return Race.ANGEL;
			case DEMON: return Race.DEMON;
			case DOG_MORPH: return Race.DOG_MORPH;
			case WOLF_MORPH: return Race.WOLF_MORPH;
			case FOX_MORPH: return Race.FOX_MORPH;
			case CAT_MORPH: return Race.CAT_MORPH;
			case COW_MORPH: return Race.COW_MORPH;
			case SQUIRREL_MORPH: return Race.SQUIRREL_MORPH;
			case RAT_MORPH: return Race.RAT_MORPH;
			case BAT_MORPH: return Race.BAT_MORPH;
			case RABBIT_MORPH: return Race.RABBIT_MORPH;
			case ALLIGATOR_MORPH: return Race.ALLIGATOR_MORPH;
			case HORSE_MORPH: return Race.HORSE_MORPH;
			case REINDEER_MORPH: return Race.REINDEER_MORPH;
			case HARPY: return Race.HARPY;
			}
			throw new IllegalStateException();
		}

		@Override
		public String getDescriptor(GameCharacter c) {
			switch(this) {
			case ANGEL:
			case DEMON:
				return UtilText.returnStringAtRandom("perfect", "flawless");
			default:
				return "";
			}
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
		Special.values(),
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