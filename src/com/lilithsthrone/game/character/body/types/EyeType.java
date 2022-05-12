package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractEyeType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.body.valueEnums.EyeShape;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.7
 * @author Innoxia
 */
public interface EyeType extends BodyPartTypeInterface {

	int getDefaultPairCount();

	EyeShape getDefaultIrisShape();

	EyeShape getDefaultPupilShape();

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	@Override
	default String getDeterminer(GameCharacter gc) {
		if(gc.getEyePairs()==1) {
			return "a pair of";
		}
		return Util.intToString(gc.getEyePairs())+" pairs of";
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return true;
	}

	public static AbstractEyeType HUMAN = new Special(BodyCoveringType.EYE_HUMAN,
			Race.HUMAN,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"human",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			" By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into human eyes, with normally-proportioned irises and pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldHuman(human eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] normal, human eyes, with [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
	};

	public static AbstractEyeType ANGEL = new Special(BodyCoveringType.EYE_ANGEL,
			Race.ANGEL,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"angel",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			" By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into angelic eyes, with normally-proportioned irises and pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldAngel(angelic eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] angelic eyes, with [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
	};

	public static AbstractEyeType DEMON_COMMON = new Special(BodyCoveringType.EYE_DEMON_COMMON,
			Race.DEMON,
			1,
			EyeShape.ROUND,
			EyeShape.VERTICAL,
			"demonic",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"#IF(npc.isShortStature())"
				+ "By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into impish eyes, with vertical pupils and large irises."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldImp(impish eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)]."
			+ "#ELSE"
				+ "By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into demonic eyes, with vertical pupils and large irises."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldDemon(demonic eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)]."
			+ "#ENDIF",
			"[npc.SheHasFull] [npc.eyePairs] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF eyes, with [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType DEMON_OWL = new Special(BodyCoveringType.EYE_DEMON_COMMON,
			Race.DEMON,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"demonic-owl",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into demonic, owl-like eyes, which provide [npc.herHim] with excellent night vision."
				+ "<br/>[npc.Name] now [npc.has] [style.boldDemon(demonic-owl eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] demonic-owl eyes, with [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType CAT_MORPH = new Special(BodyCoveringType.EYE_FELINE,
			Race.CAT_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.VERTICAL,
			"cat",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into cat-like eyes, with large irises and vertical pupils."
					+ "<br/>"
					+ "[npc.Name] now [npc.has] [style.boldCatMorph(cat-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] cat-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType COW_MORPH = new Special(BodyCoveringType.EYE_COW_MORPH,
			Race.COW_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.HORIZONTAL,
			"cow",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into cow-like eyes, with large pupils and horizontal irises."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldCowMorph(cow-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] cow-like eyes, the irises and pupils of which are larger than a regular human's."
			+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType DOG_MORPH = new Special(BodyCoveringType.EYE_DOG_MORPH,
			Race.DOG_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"dog",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into dog-like eyes, with large pupils and irises."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldDogMorph(dog-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] dog-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType FOX_MORPH = new Special(BodyCoveringType.EYE_FOX_MORPH,
			Race.FOX_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.VERTICAL,
			"fox",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into fox-like eyes, with large irises and vertical pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldFoxMorph(fox-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] fox-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType WOLF_MORPH = new Special(BodyCoveringType.EYE_LYCAN,
			Race.WOLF_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"wolf",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into wolf-like eyes, with large irises and pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldWolfMorph(wolf-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] wolf-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType SQUIRREL_MORPH = new Special(BodyCoveringType.EYE_SQUIRREL,
			Race.SQUIRREL_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"squirrel",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into squirrel-like eyes, with large irises and pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldSquirrelMorph(squirrel-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] squirrel-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
	};

	public static AbstractEyeType RAT_MORPH = new Special(BodyCoveringType.EYE_RAT,
			Race.RAT_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"rat",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into rat-like eyes, with large irises and pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldRatMorph(rat-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] rat-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
	};

	public static AbstractEyeType RABBIT_MORPH = new Special(BodyCoveringType.EYE_RABBIT,
			Race.RABBIT_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"rabbit",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into rabbit-like eyes, with large irises and pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldRabbitMorph(rabbit-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] rabbit-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
	};

	public static AbstractEyeType BAT_MORPH = new Special(BodyCoveringType.EYE_BAT,
			Race.BAT_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"bat",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into bat-like eyes, with large irises and pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldBatMorph(bat-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] bat-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
	};

	public static AbstractEyeType ALLIGATOR_MORPH = new Special(BodyCoveringType.EYE_ALLIGATOR_MORPH,
			Race.ALLIGATOR_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.VERTICAL,
			"alligator",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into alligator-like eyes, with large irises and vertical pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldAlligatorMorph(alligator-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] alligator-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType HORSE_MORPH = new Special(BodyCoveringType.EYE_HORSE_MORPH,
			Race.HORSE_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.HORIZONTAL,
			"horse",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into horse-like eyes, with large irises and horizontal pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldHorseMorph(horse-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] horse-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType REINDEER_MORPH = new Special(BodyCoveringType.EYE_REINDEER_MORPH,
			Race.REINDEER_MORPH,
			1,
			EyeShape.ROUND,
			EyeShape.HORIZONTAL,
			"reindeer",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into reindeer-like eyes, with large irises and horizontal pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldReindeerMorph(reindeer-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] reindeer-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
		@Override
		public List<BodyPartTag> getTags() {
			return Util.newArrayListOfValues(BodyPartTag.NIGHT_VISION);
		}
	};

	public static AbstractEyeType HARPY = new Special(BodyCoveringType.EYE_HARPY,
			Race.HARPY,
			1,
			EyeShape.ROUND,
			EyeShape.ROUND,
			"harpy",
			"eye",
			"eyes",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"By the time [npc.she] hesitantly [npc.verb(open)] them again, they've changed into bird-like eyes, with large irises and pupils."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldHarpy(bird-like eyes)] with [style.boldGenericTF([npc.irisShape])], [npc.irisFullDescription(true)] and [style.boldGenericTF([npc.pupilShape])], [npc.pupilFullDescription(true)].",
			"[npc.SheHasFull] [npc.eyePairs] bird-like eyes, the irises and pupils of which are larger than a regular human's."
				+ " They have [npc.irisShape], [npc.irisColour(true)] irises, [npc.pupilShape], [npc.pupilColour(true)] pupils, and [npc.scleraColour(true)] sclerae.") {
	};
	
	class Special extends AbstractEyeType {

		private String id;

		public Special(BodyCoveringType coveringType, Race race, int defaultPairCount, EyeShape defaultIrisShape, EyeShape defaultPupilShape, String transformationName, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String eyeTransformationDescription, String eyeBodyDescription) {
			super(coveringType, race, defaultPairCount, defaultIrisShape, defaultPupilShape, transformationName, name, namePlural, descriptorsMasculine, descriptorsFeminine, eyeTransformationDescription, eyeBodyDescription);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(EyeType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<EyeType> table = new TypeTable<>(
		EyeType::sanitize,
		EyeType.class,
		AbstractEyeType.class,
		"eye",
		(f,n,a,m)->new AbstractEyeType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	static EyeType getEyeTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP")) {
			return "DEMON_COMMON";
		}
		if(id.equals("LYCAN")) {
			return "WOLF_MORPH";
		}
		return id;
	}

	@Deprecated
	static String getIdFromEyeType(EyeType eyeType) {
		return eyeType.getId();
	}

	@Deprecated
	static List<EyeType> getAllEyeTypes() {
		return table.listByRace();
	}
	
	@Deprecated
	static List<EyeType> getEyeTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
	
}