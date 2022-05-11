package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractMouthType;
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
public interface MouthType extends BodyPartTypeInterface {

	TongueType getTongueType();

	@Override
	default String getDeterminer(GameCharacter gc) {
		return "";
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return false;
	}

	String getLipsNameSingular(GameCharacter gc);

	String getLipsNamePlural(GameCharacter gc);

	boolean isLipsDescriptorSizeAllowed(GameCharacter gc);

	List<String> getLipsDescriptors(GameCharacter gc);

	String getBodyDescription(GameCharacter owner);

	List<OrificeModifier> getDefaultRacialOrificeModifiers();

	public static AbstractMouthType HUMAN = new Special(Race.HUMAN, TongueType.HUMAN) {};

	public static AbstractMouthType ANGEL = new Special(Race.ANGEL, TongueType.ANGEL) {};

	public static AbstractMouthType DEMON_COMMON = new Special(Race.DEMON, TongueType.DEMON_COMMON) {};

	public static AbstractMouthType DOG_MORPH = new Special(Race.DOG_MORPH, TongueType.DOG_MORPH) {};

	public static AbstractMouthType WOLF_MORPH = new Special(Race.WOLF_MORPH, TongueType.WOLF_MORPH) {};

	public static AbstractMouthType FOX_MORPH = new Special(Race.FOX_MORPH, TongueType.FOX_MORPH) {};

	public static AbstractMouthType CAT_MORPH = new Special(Race.CAT_MORPH, TongueType.CAT_MORPH) {};

	public static AbstractMouthType COW_MORPH = new Special(Race.COW_MORPH, TongueType.COW_MORPH) {};

	public static AbstractMouthType SQUIRREL_MORPH = new Special(Race.SQUIRREL_MORPH, TongueType.SQUIRREL_MORPH) {};

	public static AbstractMouthType RAT_MORPH = new Special(Race.RAT_MORPH, TongueType.RAT_MORPH) {};

	public static AbstractMouthType RABBIT_MORPH = new Special(Race.RABBIT_MORPH, TongueType.RABBIT_MORPH) {};

	public static AbstractMouthType BAT_MORPH = new Special(Race.BAT_MORPH, TongueType.BAT_MORPH) {};

	public static AbstractMouthType ALLIGATOR_MORPH = new Special(Race.ALLIGATOR_MORPH, TongueType.ALLIGATOR_MORPH) {};

	public static AbstractMouthType HORSE_MORPH = new Special(Race.HORSE_MORPH, TongueType.HORSE_MORPH) {};

	public static AbstractMouthType REINDEER_MORPH = new Special(Race.REINDEER_MORPH, TongueType.REINDEER_MORPH) {};

	public static AbstractMouthType HARPY = new Special(BodyCoveringType.MOUTH,
			Race.HARPY,
			TongueType.HARPY,
			Util.newArrayListOfValues("beak"),
			Util.newArrayListOfValues("beaks"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("beak-lip"),
			Util.newArrayListOfValues("beak-lips"),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			null,
			Util.newArrayListOfValues()) {
		public boolean isLipsDescriptorSizeAllowed(GameCharacter gc) {
			return false;
		}
	};

	class Special extends AbstractMouthType {

		private String id;

		public Special(Race race, TongueType tongueType) {
			super(race, tongueType);
		}

		public Special(AbstractBodyCoveringType coveringType, Race race, TongueType tongueType, List<String> names, List<String> namesPlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, List<String> lipNames, List<String> lipNamesPlural, List<String> lipDescriptorsMasculine, List<String> lipDescriptorsFeminine, String mouthBodyDescription, List<OrificeModifier> defaultRacialOrificeModifiers) {
			super(coveringType, race, tongueType, names, namesPlural, descriptorsMasculine, descriptorsFeminine, lipNames, lipNamesPlural, lipDescriptorsMasculine, lipDescriptorsFeminine, mouthBodyDescription, defaultRacialOrificeModifiers);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(MouthType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<MouthType> table = new TypeTable<>(
		MouthType::sanitize,
		MouthType.class,
		AbstractMouthType.class,
		"mouth",
		(f,n,a,m)->new AbstractMouthType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	static MouthType getMouthTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP")) {
			return "DEMON_COMMON";
		}
		
		return id;
	}

	@Deprecated
	static String getIdFromMouthType(MouthType mouthType) {
		return mouthType.getId();
	}

	@Deprecated
	static List<MouthType> getAllMouthTypes() {
		return table.listByRace();
	}

	@Deprecated
	static List<MouthType> getMouthTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
	
}