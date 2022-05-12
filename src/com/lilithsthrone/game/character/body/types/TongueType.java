package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractTongueType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.TongueModifier;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.3.7
 * @author Innoxia
 */
public interface TongueType extends BodyPartTypeInterface {

	int getDefaultLength();

	String getBodyDescription(GameCharacter owner);

	List<TongueModifier> getDefaultRacialTongueModifiers();

	@Override
	default String getDeterminer(GameCharacter gc) {
		return "";
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return false;
	}

	public static AbstractTongueType HUMAN = new Special(BodyCoveringType.TONGUE,
			Race.HUMAN,
			3,
			"tongue",
			"tongues",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)] [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues()) {
	};
	
	public static AbstractTongueType ANGEL = new Special(BodyCoveringType.TONGUE,
			Race.ANGEL,
			3,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("angelic"),
			Util.newArrayListOfValues("angelic"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)] [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues()) {
	};
	
	public static AbstractTongueType DEMON_COMMON = new Special(BodyCoveringType.TONGUE,
			Race.DEMON,
			6,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)] [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues()) {
	};
	
	public static AbstractTongueType DOG_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.DOG_MORPH,
			8,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("dog-like"),
			Util.newArrayListOfValues("dog-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], dog-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues(
					TongueModifier.WIDE,
					TongueModifier.FLAT)) {
	};
	
	public static AbstractTongueType WOLF_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.WOLF_MORPH,
			8,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("wolf-like"),
			Util.newArrayListOfValues("wolf-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], wolf-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues(
					TongueModifier.WIDE,
					TongueModifier.FLAT)) {
	};
	
	public static AbstractTongueType FOX_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.FOX_MORPH,
			6,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("fox-like"),
			Util.newArrayListOfValues("fox-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], fox-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues(
					TongueModifier.FLAT)) {
	};
	
	public static AbstractTongueType CAT_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.CAT_MORPH,
			6,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("cat-like"),
			Util.newArrayListOfValues("cat-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], cat-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues(
					TongueModifier.FLAT)) {
	};
	
	public static AbstractTongueType COW_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.COW_MORPH,
			12,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("cow-like"),
			Util.newArrayListOfValues("cow-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], cow-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues(
					TongueModifier.STRONG)) {
	};
	
	public static AbstractTongueType ALLIGATOR_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.ALLIGATOR_MORPH,
			6,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("alligator-like"),
			Util.newArrayListOfValues("alligator-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], alligator-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues(
					TongueModifier.STRONG)) {
	};
	
	public static AbstractTongueType HORSE_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.HORSE_MORPH,
			8,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("horse-like"),
			Util.newArrayListOfValues("horse-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], horse-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues(
					TongueModifier.STRONG)) {
	};
	
	public static AbstractTongueType REINDEER_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.REINDEER_MORPH,
			8,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("reindeer-like"),
			Util.newArrayListOfValues("reindeer-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], reindeer-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues(
					TongueModifier.STRONG)) {
	};
	
	public static AbstractTongueType HARPY = new Special(BodyCoveringType.TONGUE,
			Race.HARPY,
			6,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("bird-like"),
			Util.newArrayListOfValues("bird-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], bird-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues(
					TongueModifier.FLAT)) {
	};
	
	public static AbstractTongueType SQUIRREL_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.SQUIRREL_MORPH,
			6,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("squirrel-like"),
			Util.newArrayListOfValues("squirrel-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], squirrel-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues()) {
	};
	
	public static AbstractTongueType RAT_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.RAT_MORPH,
			6,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("rat-like"),
			Util.newArrayListOfValues("rat-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], rat-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues()) {
	};
	
	public static AbstractTongueType RABBIT_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.RABBIT_MORPH,
			6,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("rabbit-like"),
			Util.newArrayListOfValues("rabbit-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], rabbit-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues()) {
	};
	
	public static AbstractTongueType BAT_MORPH = new Special(BodyCoveringType.TONGUE,
			Race.BAT_MORPH,
			6,
			"tongue",
			"tongues",
			Util.newArrayListOfValues("bat-like"),
			Util.newArrayListOfValues("bat-like"),
			" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)], bat-like [npc.tongue]#IF(npc.isPiercedTongue()), which has been pierced#ENDIF.",
			Util.newArrayListOfValues()) {
	};
	
	class Special extends AbstractTongueType {

		private String id;

		public Special(BodyCoveringType coveringType, Race race, int defaultLength, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String tongueBodyDescription, List<TongueModifier> defaultRacialTongueModifiers) {
			super(coveringType, race, defaultLength, name, namePlural, descriptorsMasculine, descriptorsFeminine, tongueBodyDescription, defaultRacialTongueModifiers);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(TongueType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<TongueType> table = new TypeTable<>(
		TongueType::sanitize,
		TongueType.class,
		AbstractTongueType.class,
		"tongue",
		(f,n,a,m)->new AbstractTongueType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	static TongueType getTongueTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP")) {
			return "DEMON_COMMON";
		}
		if(id.equals("LYCAN")) {
			return "WOLF_MORPH";
		}
		if(id.equals("TENGU")) {
			return "HARPY";
		}
		
		return id;
	}

	@Deprecated
	static String getIdFromTongueType(TongueType tongueType) {
		return tongueType.getId();
	}

	@Deprecated
	static List<TongueType> getAllTongueTypes() {
		return table.listByRace();
	}
	
	@Deprecated
	static List<TongueType> getTongueTypes(Race r) {
		return table.of(r).orElse(List.of());
	}

}