package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractHairType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.9.1
 * @author Innoxia
 */
public interface HairType extends BodyPartTypeInterface {

	default boolean isAbleToBeGrabbedInSex() {
		return getTags().contains(BodyPartTag.HAIR_HANDLES_IN_SEX);
	}

	/**
	 * @return
	 * Chance for this hair type to spawn with neck fluff, from 0->1.0 representing 0->100%
	 */
	double getNeckFluffChance();

	/**
	 * @return
	 * Neck fluff is only applied on spawn if the character is a greater morph.
	 */
	boolean isNeckFluffRequiresGreater();

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	@Override
	default TFModifier getTFModifier() {
		return getTFTypeModifier(HairType.getHairTypes(getRace()));
	}

	public static AbstractHairType HUMAN = new Special(BodyCoveringType.HAIR_HUMAN,
			Race.HUMAN,
			"human",
			"hair",
			"hairs",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("soft", "feminine"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with human-like hair.<br/>"
					+ "[npc.Name] now [npc.has] [npc.hairColour], [style.boldHuman(human hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], human hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType ANGEL = new Special(BodyCoveringType.HAIR_ANGEL,
			Race.ANGEL,
			"angelic",
			"hair",
			"hairs",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("silken", "soft", "feminine"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with silken, angelic hair.<br/>"
					+ "[npc.Name] now [npc.has] [npc.hairColour], [style.boldAngel(angelic hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], angelic hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};
	
	public static AbstractHairType DEMON = new Special(BodyCoveringType.HAIR_DEMON,
			Race.DEMON,
			"demonic",
			"hair",
			"hairs",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("silken", "soft", "feminine"),
			"#IF(npc.isShortStature())"
				+ "The transformation only lasts a matter of moments, leaving [npc.herHim] with silky, impish hair.<br/>"
					+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldImp(impish hair)]."
			+ "#ELSE"
				+ "The transformation only lasts a matter of moments, leaving [npc.herHim] with silken, demonic hair.<br/>"
					+ "[npc.Name] now [npc.has] [npc.hairColour], [style.boldDemon(demonic hair)]."
			+ "#ENDIF",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType DOG_MORPH = new Special(BodyCoveringType.HAIR_CANINE_FUR,
			Race.DOG_MORPH,
			"dog",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldDogMorph(dog hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], dog-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};
	
	public static AbstractHairType WOLF_MORPH = new Special(BodyCoveringType.HAIR_LYCAN_FUR, //TODO rename
			Race.WOLF_MORPH,
			"wolf",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldWolfMorph(wolf hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], wolf-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
		@Override
		public boolean isNeckFluffRequiresGreater() {
			return true;
		}
		@Override
		public double getNeckFluffChance() {
			return 0.25f;
		}
	};

	public static AbstractHairType FOX_MORPH = new Special(BodyCoveringType.HAIR_FOX_FUR,
			Race.FOX_MORPH,
			"fox",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldFoxMorph(fox hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], fox-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
		@Override
		public boolean isNeckFluffRequiresGreater() {
			return true;
		}
		@Override
		public double getNeckFluffChance() {
			return 0.25f;
		}
	};

	public static AbstractHairType CAT_MORPH = new Special(BodyCoveringType.HAIR_FELINE_FUR, //TODO change to cat
			Race.CAT_MORPH,
			"cat",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldCatMorph(cat hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], cat-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	//TODO should be PANTHER
	public static AbstractHairType CAT_MORPH_SIDEFLUFF = new Special(BodyCoveringType.HAIR_FELINE_FUR,
			Race.CAT_MORPH,
			"cat (sidefluff)",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldCatMorph(cat hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], cat-like hair, complete with soft, fuzzy fur on the sides of [npc.her] face",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType COW_MORPH = new Special(BodyCoveringType.HAIR_BOVINE_FUR, //TODO change to cow
			Race.COW_MORPH,
			"cow",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldCowMorph(cow hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], cow-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType ALLIGATOR_MORPH = new Special(BodyCoveringType.HAIR_SCALES_ALLIGATOR, //TODO change to hair
			Race.ALLIGATOR_MORPH,
			"alligator",
			"hair",
			"hairs",
			Util.newArrayListOfValues("coarse"),
			Util.newArrayListOfValues("coarse"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with coarse hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldAlligatorMorph(alligator hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], coarse alligator hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType SQUIRREL_MORPH = new Special(BodyCoveringType.HAIR_SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			"squirrel",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldSquirrelMorph(squirrel hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], squirrel-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType RAT_MORPH = new Special(BodyCoveringType.HAIR_RAT_FUR,
			Race.RAT_MORPH,
			"rat",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldRatMorph(rat hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], rat-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType RABBIT_MORPH = new Special(BodyCoveringType.HAIR_RABBIT_FUR,
			Race.RABBIT_MORPH,
			"rabbit",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldRabbitMorph(rabbit hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], rabbit-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType BAT_MORPH = new Special(BodyCoveringType.HAIR_BAT_FUR,
			Race.BAT_MORPH,
			"bat",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldBatMorph(bat hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], bat-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType HORSE_MORPH = new Special(BodyCoveringType.HAIR_HORSE_HAIR,
			Race.HORSE_MORPH,
			"horse",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldHorseMorph(horse hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], horse-like hair",
			Util.newArrayListOfValues(
					BodyPartTag.HAIR_HANDLES_IN_SEX,
					BodyPartTag.HAIR_NATURAL_MANE)) {
	};

	public static AbstractHairType REINDEER_MORPH = new Special(BodyCoveringType.HAIR_REINDEER_FUR,
			Race.REINDEER_MORPH,
			"reindeer",
			"hair",
			"hairs",
			Util.newArrayListOfValues("furry", "fur-like"),
			Util.newArrayListOfValues("furry", "fur-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with fur-like hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldReindeerMorph(reindeer hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], reindeer-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType HARPY = new Special(BodyCoveringType.HAIR_HARPY,
			Race.HARPY,
			"harpy",
			"head-feather",
			"head-feathers",
			Util.newArrayListOfValues("beautiful", "bird-like"),
			Util.newArrayListOfValues("beautiful", "bird-like"),
			"The transformation only lasts a matter of moments, leaving [npc.herHim] with a plume of feathers in place of hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour], bird-like [style.boldHarpy(harpy feathers)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], bird-like harpy feathers",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
		@Override
		public boolean isDefaultPlural(GameCharacter gc) {
			return true;
		}
		@Override
		public String getDeterminer(GameCharacter gc) {
			return "a plume of";
		}
	};

	class Special extends AbstractHairType {

		private String id;

		public Special(BodyCoveringType skinType, Race race, String transformationName, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String hairTransformationDescription, String hairBodyDescription, List<BodyPartTag> tags) {
			super(skinType, race, transformationName, name, namePlural, descriptorsMasculine, descriptorsFeminine, hairTransformationDescription, hairBodyDescription, tags);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(HairType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<HairType> table = new TypeTable<>(
		HairType::sanitize,
		HairType.class,
		AbstractHairType.class,
		"hair",
		(f,n,a,m)->new AbstractHairType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	static HairType getHairTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP") || id.equals("DEMON_COMMON")) {
			return "DEMON";
		}
		if(id.equals("LYCAN")) {
			return "WOLF_MORPH";
		}
		return id;
	}

	@Deprecated
	static String getIdFromHairType(HairType hairType) {
		return hairType.getId();
	}

	@Deprecated
	static List<HairType> getAllHairTypes() {
		return table.listByRace();
	}
	
	static List<HairType> getHairTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
}