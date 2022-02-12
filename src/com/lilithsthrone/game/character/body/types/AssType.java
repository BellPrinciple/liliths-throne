package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractAnusType;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractAssType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.3
 * @author Innoxia
 */
public interface AssType extends BodyPartTypeInterface {

	AbstractAnusType getAnusType();

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	@Override
	default String getDeterminer(GameCharacter gc) {
		return "";
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return false;
	}

	public static AbstractAssType HUMAN = new Special(BodyCoveringType.HUMAN,
			Race.HUMAN,
			AnusType.HUMAN,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldHuman(human)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a human, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType ANGEL = new Special(BodyCoveringType.ANGEL,
			Race.ANGEL,
			AnusType.ANGEL,
			null,
			null,
			Util.newArrayListOfValues("angelic"),
			Util.newArrayListOfValues("angelic", "perfect"),
			"[npc.She] now [npc.has] an [style.boldAngel(angelic)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] an angelic, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType DEMON_COMMON = new Special(BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			AnusType.DEMON_COMMON,
			null,
			null,
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic", "perfect"),
			"[npc.She] now [npc.has] a [style.boldDemon(demonic)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a demonic, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType DOG_MORPH = new Special(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			AnusType.DOG_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldDogMorph(canine)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a canine, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType COW_MORPH = new Special(BodyCoveringType.BOVINE_FUR,
			Race.COW_MORPH,
			AnusType.COW_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldCowMorph(bovine)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a bovine, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType SQUIRREL_MORPH = new Special(BodyCoveringType.SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			AnusType.SQUIRREL_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldSquirrelMorph(squirrel-like)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a squirrel-like, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType RAT_MORPH = new Special(BodyCoveringType.RAT_FUR,
			Race.RAT_MORPH,
			AnusType.RAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldRatMorph(rat-like)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a rat-like, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType RABBIT_MORPH = new Special(BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			AnusType.RABBIT_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldRabbitMorph(rabbit-like)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a rabbit-like, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType BAT_MORPH = new Special(BodyCoveringType.BAT_FUR,
			Race.BAT_MORPH,
			AnusType.BAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldBatMorph(bat-like)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a bat-like, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType ALLIGATOR_MORPH = new Special(BodyCoveringType.ALLIGATOR_SCALES,
			Race.ALLIGATOR_MORPH,
			AnusType.ALLIGATOR_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] an [style.boldGatorMorph(alligator-like)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] an alligator-like, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType WOLF_MORPH = new Special(BodyCoveringType.LYCAN_FUR,
			Race.WOLF_MORPH,
			AnusType.WOLF_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldWolfMorph(lupine)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a lupine, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType FOX_MORPH = new Special(BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			AnusType.FOX_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldFoxMorph(vulpine)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a vulpine, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType CAT_MORPH = new Special(BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			AnusType.CAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldCatMorph(feline)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a feline, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType HORSE_MORPH = new Special(BodyCoveringType.HORSE_HAIR,
			Race.HORSE_MORPH,
			AnusType.HORSE_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] an [style.boldHorseMorph(equine)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] an equine, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType REINDEER_MORPH = new Special(BodyCoveringType.REINDEER_FUR,
			Race.REINDEER_MORPH,
			AnusType.REINDEER_MORPH,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] a [style.boldReindeerMorph(reindeer-like)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] a reindeer-like, [npc.anusFullDescription(true)]"){
	};
	
	public static AbstractAssType HARPY = new Special(BodyCoveringType.FEATHERS,
			Race.HARPY,
			AnusType.HARPY,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] now [npc.has] an [style.boldHarpy(avian)], [npc.assholeFullDescription].",
			"[npc.SheHasFull] an avian, [npc.anusFullDescription(true)]"){
	};
	

	class Special extends AbstractAssType {

		private String id;

		public Special(AbstractBodyCoveringType coveringType, Race race, AbstractAnusType anusType, List<String> names, List<String> namesPlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String assTransformationDescription, String assBodyDescription) {
			super(coveringType, race, anusType, names, namesPlural, descriptorsMasculine, descriptorsFeminine, assTransformationDescription, assBodyDescription);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(AssType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<AbstractAssType> table = new TypeTable<>(
		AssType::sanitize,
		AssType.class,
		AbstractAssType.class,
		"ass",
		(f,n,a,m)->new AbstractAssType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	public static AbstractAssType getAssTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP")) {
			return "DEMON_COMMON";
		}

		return id;
	}

	@Deprecated
	public static String getIdFromAssType(AbstractAssType assType) {
		return assType.getId();
	}

	@Deprecated
	public static List<AbstractAssType> getAllAssTypes() {
		return table.listByRace();
	}
	
	public static List<AbstractAssType> getAssTypes(Race r) {
		return table.of(r).orElse(List.of());
	}

}