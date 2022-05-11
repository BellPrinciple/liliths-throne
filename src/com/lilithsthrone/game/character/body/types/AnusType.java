package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractAnusType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.7
 * @author Innoxia
 */
public interface AnusType extends BodyPartTypeInterface {

	boolean isAssHairAllowed();

	List<OrificeModifier> getDefaultRacialOrificeModifiers();

	@Override
	default String getDeterminer(GameCharacter gc) {
		return "";
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return false;
	}

	public static AbstractAnusType HUMAN = new Special(BodyCoveringType.ANUS,
			Race.HUMAN,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType ANGEL = new Special(BodyCoveringType.ANUS,
			Race.ANGEL,
			null,
			null,
			Util.newArrayListOfValues("angelic", "perfect"),
			Util.newArrayListOfValues("angelic", "perfect"),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType DEMON_COMMON = new Special(BodyCoveringType.ANUS,
			Race.DEMON,
			null,
			null,
			Util.newArrayListOfValues("demonic", "irresistible"),
			Util.newArrayListOfValues("demonic", "irresistible"),
			Util.newArrayListOfValues(OrificeModifier.MUSCLE_CONTROL)){
	};
	
	public static AbstractAnusType COW_MORPH = new Special(BodyCoveringType.ANUS,
			Race.COW_MORPH,
			null,
			null,
			Util.newArrayListOfValues("cow-like", "bovine"),
			Util.newArrayListOfValues("cow-like", "bovine"),
			Util.newArrayListOfValues(OrificeModifier.PUFFY)){
	};
	
	public static AbstractAnusType DOG_MORPH = new Special(BodyCoveringType.ANUS,
			Race.DOG_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType FOX_MORPH = new Special(BodyCoveringType.ANUS,
			Race.FOX_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType SQUIRREL_MORPH = new Special(BodyCoveringType.ANUS,
			Race.SQUIRREL_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType RAT_MORPH = new Special(BodyCoveringType.ANUS,
			Race.RAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType RABBIT_MORPH = new Special(BodyCoveringType.ANUS,
			Race.RABBIT_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType BAT_MORPH = new Special(BodyCoveringType.ANUS,
			Race.BAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType WOLF_MORPH = new Special(BodyCoveringType.ANUS,
			Race.BAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType CAT_MORPH = new Special(BodyCoveringType.ANUS,
			Race.CAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType HORSE_MORPH = new Special(BodyCoveringType.ANUS,
			Race.HORSE_MORPH,
			null,
			null,
			Util.newArrayListOfValues("horse-like", "equine"),
			Util.newArrayListOfValues("horse-like", "equine"),
			Util.newArrayListOfValues(OrificeModifier.PUFFY)){
	};
	
	public static AbstractAnusType REINDEER_MORPH = new Special(BodyCoveringType.ANUS,
			Race.REINDEER_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(OrificeModifier.PUFFY)){
	};
	
	public static AbstractAnusType ALLIGATOR_MORPH = new Special(BodyCoveringType.ANUS,
			Race.ALLIGATOR_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
	};
	
	public static AbstractAnusType HARPY = new Special(BodyCoveringType.ANUS,
			Race.HARPY,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues()){
		@Override
		public String getId() {
			return "HARPY";
		}
	};


	class Special extends AbstractAnusType {

		private String id;

		Special(
				AbstractBodyCoveringType coveringType,
				Race race,
				List<String> names,
				List<String> namesPlural,
				List<String> descriptorsMasculine,
				List<String> descriptorsFeminine,
				List<OrificeModifier> defaultRacialOrificeModifiers) {
			super(coveringType,race,names,namesPlural,descriptorsMasculine,descriptorsFeminine,defaultRacialOrificeModifiers);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(AnusType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}
	
	TypeTable<AnusType> table = new TypeTable<>(
		AnusType::sanitize,
		AnusType.class,
		AbstractAnusType.class,
		"anus",
		(f,n,a,m)->new AbstractAnusType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	static AnusType getAnusTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP")) {
			return "DEMON_COMMON";
		}
		return id;
	}

	@Deprecated
	static String getIdFromAnusType(AnusType anusType) {
		return anusType.getId();
	}

	@Deprecated
	static List<AnusType> getAllAnusTypes() {
		return table.listByRace();
	}

	static List<AnusType> getAnusTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
	
}