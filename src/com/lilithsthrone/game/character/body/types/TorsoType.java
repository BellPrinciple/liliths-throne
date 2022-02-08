package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractTorsoType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.8.8
 * @author Innoxia
 */
public interface TorsoType extends BodyPartTypeInterface {

	public static AbstractTorsoType HUMAN = new Special(BodyCoveringType.HUMAN,
			Race.HUMAN,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with human skin."
				+ "<br/>[npc.Name] now [npc.has] [style.boldHuman(human)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType DEMON_COMMON = new Special(BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with"
					+ "#IF(npc.isShortStature())"
						+ " impish"
					+ "#ELSE"
						+ " demonic"
					+ "#ENDIF"
					+ " skin."
				+ " It's far smoother than regular human skin, and the colour tones all over [npc.her] body have become perfectly balanced in order to help show off [npc.her] figure."
				+ "<br/>[npc.Name] now [npc.has]"
				+ "#IF(npc.isShortStature())"
					+ " [style.boldImp(impish)]"
				+ "#ELSE"
					+ " [style.boldDemon(demonic)]"
				+ "#ENDIF"
				+ ", [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType ANGEL = new Special(BodyCoveringType.ANGEL,
			Race.ANGEL,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with angelic skin."
				+ " It's far smoother than regular human skin, and the colour tones all over [npc.her] body have become perfectly balanced in order to help show off [npc.her] figure."
				+ "<br/>[npc.Name] now [npc.has] [style.boldAngel(angelic)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType COW_MORPH = new Special(BodyCoveringType.BOVINE_FUR,
			Race.COW_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with short, cow-like hair."
				+ " [npc.Her] new hair looks very sleek and helps to show off [npc.her] figure, although it's a little coarse to the touch."
				+ "<br/>[npc.Name] now [npc.has] [style.boldCowMorph(bovine)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType DOG_MORPH = new Special(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with dog-like fur."
				+ " [npc.Her] new fur follows the lines of [npc.her] figure and is quite smooth and pleasant to touch."
				+ "<br/>[npc.Name] now [npc.has] [style.boldDogMorph(canine)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType WOLF_MORPH = new Special(BodyCoveringType.LYCAN_FUR,
			Race.WOLF_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with wolf-like fur."
				+ " [npc.Her] new fur is a little shaggy around [npc.her] joints and is quite densely packed."
				+ "<br/>[npc.Name] now [npc.has] [style.boldWolfMorph(lupine)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};
	
	public static AbstractTorsoType FOX_MORPH = new Special(BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with fox-like fur."
				+ " [npc.Her] new fur is a little shaggy around [npc.her] joints and is quite densely packed."
				+ "</br>[npc.Name] now [npc.has] [style.boldFoxMorph(vulpine)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType CAT_MORPH = new Special(BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with cat-like fur."
				+ " [npc.Her] new fur follows the lines of [npc.her] figure and is extremely smooth and soft."
				+ "<br/>[npc.Name] now [npc.has] [style.boldCatMorph(feline)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType SQUIRREL_MORPH = new Special(BodyCoveringType.SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with squirrel-like fur."
				+ " [npc.Her] new fur follows the lines of [npc.her] figure and is extremely smooth and soft."
				+ "<br/>[npc.Name] now [npc.has] [style.boldSquirrelMorph(squirrel-like)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType RAT_MORPH = new Special(BodyCoveringType.RAT_FUR,
			Race.RAT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with rat-like fur."
				+ " [npc.Her] new fur follows the lines of [npc.her] figure and is a little coarse to the touch."
				+ "<br/>[npc.Name] now [npc.has] [style.boldRatMorph(rat-like)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType RABBIT_MORPH = new Special(BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with rabbit-like fur."
				+ " [npc.Her] new fur follows the lines of [npc.her] figure and is extremely smooth and soft."
				+ "<br/>[npc.Name] now [npc.has] [style.boldRabbitMorph(rabbit-like)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType BAT_MORPH = new Special(BodyCoveringType.BAT_FUR,
			Race.BAT_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with bat-like fur."
				+ " [npc.Her] new fur follows the lines of [npc.her] figure and is quite smooth and pleasant to touch."
				+ "<br/>[npc.Name] now [npc.has] [style.boldBatMorph(bat-like)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType ALLIGATOR_MORPH = new Special(BodyCoveringType.ALLIGATOR_SCALES,
			Race.ALLIGATOR_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with tough, overlapping scales."
				+ " [npc.Her] new scales follow the lines of [npc.her] figure, and, while being quite hard to the touch, are also very smooth when rubbed in the right direction."
				+ "<br/>[npc.Name] now [npc.has] [style.boldGatorMorph(reptile)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
		@Override
		public boolean isDefaultPlural(GameCharacter gc) {
			return true;
		}
	};

	public static AbstractTorsoType HORSE_MORPH = new Special(BodyCoveringType.HORSE_HAIR,
			Race.HORSE_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with short, horse-like hair."
				+ " [npc.Her] new hair looks very sleek, and helps to show off [npc.her] figure, although it's a little coarse to the touch."
				+ "<br/>[npc.Name] now [npc.has] [style.boldHorseMorph(equine)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType REINDEER_MORPH = new Special(BodyCoveringType.REINDEER_FUR,
			Race.REINDEER_MORPH,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with short, reindeer-like hair."
				+ " [npc.Her] new fur looks very sleek, and helps to show off [npc.her] figure, although it's a little coarse to the touch."
				+ "<br/>[npc.Name] now [npc.has] [style.boldReindeerMorph(reindeer)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
	};

	public static AbstractTorsoType HARPY = new Special(BodyCoveringType.FEATHERS,
			Race.HARPY,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"After just a few moments, the transformation comes to an end, and [npc.she] [npc.verb(let)] out a deep sigh as the itching finally stops, leaving [npc.her] torso covered with beautiful, overlapping feathers."
				+ " [npc.Her] new feathers follow the lines of [npc.her] figure, and are extremely smooth and soft to the touch."
				+ "<br/>[npc.Name] now [npc.has] [style.boldHarpy(avian)], [npc.skinFullDescription].",
			"[npc.Her] torso has [npc.a_femininity(true)] appearance, and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].") {
		@Override
		public boolean isDefaultPlural(GameCharacter gc) {
			return true;
		}
	};

	class Special extends AbstractTorsoType {

		private String id;

		public Special(AbstractBodyCoveringType coveringType, Race race, List<String> descriptorsFeminine, List<String> descriptorsMasculine, String skinTransformationDescription, String skinBodyDescription) {
			super(coveringType, race, descriptorsFeminine, descriptorsMasculine, skinTransformationDescription, skinBodyDescription);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(TorsoType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<AbstractTorsoType> table = new TypeTable<>(
		TorsoType::sanitize,
		TorsoType.class,
		AbstractTorsoType.class,
		"torso",
		(f,n,a,m)->new AbstractTorsoType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	public static AbstractTorsoType getTorsoTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		switch(id) {
			case "IMP": return "DEMON_COMMON";
			case "CANINE_FUR": return "DOG_MORPH";
			case "LYCAN_FUR": return "LYCAN";
			case "LYCAN": return "WOLF_MORPH";
			case "FELINE_FUR": return "CAT_MORPH";
			case "SQUIRREL_FUR": return "SQUIRREL_MORPH";
			case "HORSE_HAIR": return "HORSE_MORPH";
			case "SLIME": return "SLIME";
			case "FEATHERS": return "HARPY";
		}
		return id;
	}

	@Deprecated
	public static String getIdFromTorsoType(AbstractTorsoType torsoType) {
		return torsoType.getId();
	}

	@Deprecated
	public static List<AbstractTorsoType> getAllTorsoTypes() {
		return table.listByRace();
	}
	
	@Deprecated
	public static List<AbstractTorsoType> getTorsoTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
}