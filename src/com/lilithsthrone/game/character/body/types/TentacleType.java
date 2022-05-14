package com.lilithsthrone.game.character.body.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractTentacleType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.body.valueEnums.PenetrationGirth;
import com.lilithsthrone.game.character.race.AbstractRace;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.2.8
 * @version 0.3.8.9
 * @author Innoxia
 */
public interface TentacleType extends BodyPartTypeInterface {

	int getDefaultGirth();

	float getDefaultLengthAsPercentageOfHeight();

	String getTentacleTipNameSingular(GameCharacter gc);

	String getTentacleTipNamePlural(GameCharacter gc);

	String getTentacleTipDescriptor(GameCharacter gc);

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	/**
	 * @return
	 * A description of this tentacle's girth, based on the TYPE tag and the owner's girth.
	 */
	default String getGirthDescription(GameCharacter owner) {
		StringBuilder sb = new StringBuilder();

		if(getTags().contains(BodyPartTag.TAIL_TYPE_FUR)) {
			if(owner.getTentacleCount()>1) {
				sb.append(UtilText.parse(owner, " [npc.Her] [npc.tentacles] are"));
			} else {
				sb.append(UtilText.parse(owner, " [npc.Her] [npc.tentacle] is"));
			}
			switch(owner.getTentacleGirth()) {
			case ZERO_THIN:
				sb.append(UtilText.parse(owner, " very thin and severely lacking in fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case ONE_SLENDER:
				sb.append(UtilText.parse(owner, " slender and lacking in fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case TWO_NARROW:
				sb.append(UtilText.parse(owner, " quite narrow and a little lacking in fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case THREE_AVERAGE:
				sb.append(UtilText.parse(owner, " of an average thickness and fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case FOUR_GIRTHY:
				sb.append(UtilText.parse(owner, " quite big and fluffy in proportion to the rest of [npc.her] body."));
				break;
			case FIVE_THICK:
				sb.append(UtilText.parse(owner, " very big and fluffy in proportion to the rest of [npc.her] body."));
				break;
			case SIX_CHUBBY:
				sb.append(UtilText.parse(owner, " incredibly thick and fluffy in proportion to the rest of [npc.her] body."));
				break;
			case SEVEN_FAT:
				sb.append(UtilText.parse(owner, " extremely thick and fluffy in proportion to the rest of [npc.her] body."));
				break;
			}

		} else {
			if(owner.getTentacleCount()>1) {
				sb.append(UtilText.parse(owner, " [npc.Her] [npc.tentacles] are"));
			} else {
				sb.append(UtilText.parse(owner, " [npc.Her] [npc.tentacle] is"));
			}
			switch(owner.getTentacleGirth()) {
			case ZERO_THIN:
				sb.append(UtilText.parse(owner, " very thin in proportion to the rest of [npc.her] body."));
				break;
			case ONE_SLENDER:
				sb.append(UtilText.parse(owner, " slender in proportion to the rest of [npc.her] body."));
				break;
			case TWO_NARROW:
				sb.append(UtilText.parse(owner, " quite narrow in proportion to the rest of [npc.her] body."));
				break;
			case THREE_AVERAGE:
				sb.append(UtilText.parse(owner, " of an average thickness in proportion to the rest of [npc.her] body."));
				break;
			case FOUR_GIRTHY:
				sb.append(UtilText.parse(owner, " quite thick in proportion to the rest of [npc.her] body."));
				break;
			case FIVE_THICK:
				sb.append(UtilText.parse(owner, " very thick in proportion to the rest of [npc.her] body."));
				break;
			case SIX_CHUBBY:
				sb.append(UtilText.parse(owner, " incredibly thick and girthy in proportion to the rest of [npc.her] body."));
				break;
			case SEVEN_FAT:
				sb.append(UtilText.parse(owner, " extremely thick and girthy in proportion to the rest of [npc.her] body."));
				break;
			}
		}
		return sb.toString();
	}

	default String getGirthDescriptor(GameCharacter owner) {
		return getGirthDescriptor(owner.getTentacleGirth());
	}

	default String getGirthDescriptor(PenetrationGirth girth) {
		if(this.getTags().contains(BodyPartTag.TAIL_TYPE_FUR)) {
			switch(girth) {
			case ZERO_THIN:
				return "thin";
			case ONE_SLENDER:
				return "slender";
			case TWO_NARROW:
				return "narrow";
			case THREE_AVERAGE:
				return "fluffy";
			case FOUR_GIRTHY:
				return "very-fluffy";
			case FIVE_THICK:
				return "extra-fluffy";
			case SIX_CHUBBY:
				return "extremely-fluffy";
			case SEVEN_FAT:
				return "unbelievably-fluffy";
			}
		} else {
			switch(girth) {
			case ZERO_THIN:
				return "thin";
			case ONE_SLENDER:
				return "slender";
			case TWO_NARROW:
				return "narrow";
			case THREE_AVERAGE:
				return "average";
			case FOUR_GIRTHY:
				return "thick";
			case FIVE_THICK:
				return "extra-thick";
			case SIX_CHUBBY:
				return "extremely-thick";
			case SEVEN_FAT:
				return "unbelievably-thick";
			}
		}
		return girth.getName();
	}

	default String getGirthTransformationDescription(GameCharacter owner, boolean positive) {
		String tentacleText = "[npc.a_tentacleGirth] [npc.tentacle]";
		if(owner.getTentacleCount()>1) {
			tentacleText = "[npc.tentacleGirth] [npc.tentacles]";
		}

		if(positive) {
			return UtilText.parse(owner,
			"<p>"
			+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] [npc.tentacles]."
			+ " Without any further warning of what's to come, [npc.her]"
			+(owner.getTentacleCount()>1
			?" [npc.tentacles] suddenly [style.boldGrow(grow thicker)]."
			:" [npc.tentacle] suddenly [style.boldGrow(grows thicker)].")
			+ "<br/>"
			+ "[npc.She] now [npc.has] [style.boldSex("+tentacleText+")]!"
			+ "</p>");

		} else {
			return UtilText.parse(owner,
			"<p>"
			+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] [npc.tentacles]."
			+ " Without any further warning of what's to come, [npc.her]"
			+(owner.getTentacleCount()>1
			?" [npc.tentacles] suddenly [style.boldShrink(shrink down)]."
			:" [npc.tentacle] suddenly [style.boldShrink(shrinks down)].")
			+ "<br/>"
			+ "[npc.She] now [npc.has] [style.boldSex("+tentacleText+")]!"
			+ "</p>");
		}
	}

	default String getLengthTransformationDescription(GameCharacter owner, boolean positive) {
		String heightPercentageDescription = " (length is "+((int)(owner.getTentacleLengthAsPercentageOfHeight()*100))+"% of [npc.namePos] height)";
		if(positive) {
			return UtilText.parse(owner,
			"<p>"
			+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the [npc.tentacleTip] of each of [npc.her] [npc.tentacles]."
			+ " Without any further warning of what's to come, [npc.her] [npc.tentacles] suddenly [style.boldGrow(grow longer)]."
			+ "<br/>"
			+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.tentacleLength] [npc.tentacles])]"+heightPercentageDescription+"!"
			+ "</p>");

		} else {
			return UtilText.parse(owner,
			"<p>"
			+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the [npc.tentacleTip] of each of [npc.her] [npc.tentacles]."
			+ " Without any further warning of what's to come, [npc.her] [npc.tentacles] suddenly [style.boldShrink(shorten)]."
			+ "<br/>"
			+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.tentacleLength] [npc.tentacles])]"+heightPercentageDescription+"!"
			+ "</p>");
		}
	}

	default boolean isPrehensile() {
		return getTags().contains(BodyPartTag.TAIL_PREHENSILE);
	}

	default boolean isSuitableForPenetration() {
		return isPrehensile() && getTags().contains(BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION);
	}

	default boolean isSuitableForSleepHugging() {
		return getTags().contains(BodyPartTag.TAIL_SLEEP_HUGGING);
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return false;
	}

	@Override
	default String getName(GameCharacter gc){
		if(isDefaultPlural(gc) || gc!=null && gc.getTentacleCount()!=1) {
			return getNamePlural(gc);
		} else {
			return getNameSingular(gc);
		}
	}

	@Override
	default TFModifier getTFModifier() {
		return getTFTypeModifier(TentacleType.getTentacleTypes(getRace()));
	}

	public static final AbstractTentacleType NONE = new Special(
			null,
			Race.NONE,
			PenetrationGirth.THREE_AVERAGE,
			0f,
			"none",
			"",
			"",
			"tentacle",
			"tentacles",
			Util.newArrayListOfValues(),
			Util.newArrayListOfValues(),
			"",
			"",
			Util.newArrayListOfValues(),
			Util.newArrayListOfValues(),
			"#IF(npc.getTentacleCount()==1)"
					+ " [npc.She] gasps as [npc.she] feels [npc.her] [npc.tentacle] shrinking down and disappearing into [npc.her] body."
				+ "#ELSE"
					+ " [npc.She] gasps as [npc.she] feels [npc.her] [npc.tentacles] shrinking down and disappearing into [npc.her] body."
				+ "#ENDIF"
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [style.boldTfGeneric(no tentacles)].",
			"[style.colourDisabled([npc.She] [npc.do] not have any tentacles.)]",
			Util.newArrayListOfValues()) {
		@Override
		public TFModifier getTFModifier() {
			return TFModifier.REMOVAL;
		}
	};
	
	public static final AbstractTentacleType DEMON_COMMON = new Special(
			BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			PenetrationGirth.ONE_SLENDER,
			1f,
			"demonic",
			"",
			"",
			"tentacle",
			"tentacles",
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"tip",
			"tips",
			Util.newArrayListOfValues("rounded"),
			Util.newArrayListOfValues("rounded"),
			"#IF(npc.getTentacleCount()==1)"
				+ " A demonic tentacle sprouts from [npc.her] back, rapidly growing in size until it's about [npc.tentacleLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] complete control over where it goes, allowing [npc.herHim] to use it like a third limb."
				+ "<br/>"
				+ "[npc.Name] now [npc.has]"
				+ "#IF(npc.isShortStature())"
					+ " an [style.boldImp(impish tentacle)]"
				+ "#ELSE"
					+ " a [style.boldDemon(demonic tentacle)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tentacleFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TentacleCount] demonic tentacles sprout from [npc.her] back, rapidly growing in size until they're each about [npc.tentacleLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] complete control over where they go, allowing [npc.herHim] to use them like extra limbs."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tentacleCount]"
				+ "#IF(npc.isShortStature())"
					+ " [style.boldImp(impish tentacles)]"
				+ "#ELSE"
					+ " [style.boldDemon(demonic tentacles)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tentacleFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from [npc.her] back, [npc.sheHasFull]"
				+ "#IF(npc.getTentacleCount()==1)"
					+ " a spaded, [npc.tentacleColour(true)] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tentacle, over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use it to grip and hold objects."
				+ "#ELSE"
					+ " [npc.tentacleCount] spaded, [npc.tentacleColour(true)] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tentacles, over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use them to grip and hold objects."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TAPERING_NONE)) {
	};
	
	public static final AbstractTentacleType LEG_DEMON_OCTOPUS = new Special(
			BodyCoveringType.OCTOPUS_SKIN,
			Race.DEMON,
			PenetrationGirth.FOUR_GIRTHY,
			2.5f,
			"demonic-octopus",
			"",
			"",
			"tentacle",
			"tentacles",
			Util.newArrayListOfValues("demonic"),
			Util.newArrayListOfValues("demonic"),
			"tip",
			"tips",
			Util.newArrayListOfValues("rounded"),
			Util.newArrayListOfValues("rounded"),
			"",
			"In place of legs, [npc.sheHasFull] [npc.tentacleCount] [npc.tentacleColour(true)], octopus-like tentacles, over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use them to grip and hold objects.",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TAPERING_NONE)) {
	};
	
	class Special extends AbstractTentacleType {

		private String id;

		public Special(AbstractBodyCoveringType coveringType, AbstractRace race, PenetrationGirth defaultGirth, float defaultLengthAsPercentageOfHeight, String transformationName, String determiner, String determinerPlural, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String tipName, String tipNamePlural, List<String> tipDescriptorsMasculine, List<String> tipDescriptorsFeminine, String tentacleTransformationDescription, String tentacleBodyDescription, List<BodyPartTag> tags) {
			super(coveringType, race, defaultGirth, defaultLengthAsPercentageOfHeight, transformationName, determiner, determinerPlural, name, namePlural, descriptorsMasculine, descriptorsFeminine, tipName, tipNamePlural, tipDescriptorsMasculine, tipDescriptorsFeminine, tentacleTransformationDescription, tentacleBodyDescription, tags);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(TentacleType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<AbstractTentacleType> table = new TypeTable<>(
		s->s,
		TentacleType.class,
		AbstractTentacleType.class,
		"tentacle",
		(f,n,a,m)->new AbstractTentacleType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	public static AbstractTentacleType getTentacleTypeFromId(String id) {
		return table.of(id);
	}

	public static String getIdFromTentacleType(AbstractTentacleType tentacleType) {
		return tentacleType.getId();
	}

	public static List<AbstractTentacleType> getAllTentacleTypes() {
		return table.listByRace();
	}
	
	public static List<AbstractTentacleType> getTentacleTypes(AbstractRace r) {
		return table.of(r).orElse(List.of(NONE));
	}
	
	public static List<AbstractTentacleType> getTentacleTypesSuitableForTransformation(List<AbstractTentacleType> options) {
		if (!options.contains(TentacleType.NONE)) {
			return options;
		}
		
		List<AbstractTentacleType> duplicatedOptions = new ArrayList<>(options);
		duplicatedOptions.remove(TentacleType.NONE);
		return duplicatedOptions;
	}
}
