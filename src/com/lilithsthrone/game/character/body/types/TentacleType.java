package com.lilithsthrone.game.character.body.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractTentacleType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.body.valueEnums.PenetrationGirth;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.2.8
 * @version 0.3.8.9
 * @author Innoxia
 */
public interface TentacleType extends BodyPartTypeInterface {
	
	public static final AbstractTentacleType NONE = new Special(
			null,
			Race.NONE,
			PenetrationGirth.THREE_AVERAGE,
			0f,
			"none",
			"",
			"",
			"",
			"",
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

		public Special(AbstractBodyCoveringType coveringType, Race race, PenetrationGirth defaultGirth, float defaultLengthAsPercentageOfHeight, String transformationName, String determiner, String determinerPlural, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String tipName, String tipNamePlural, List<String> tipDescriptorsMasculine, List<String> tipDescriptorsFeminine, String tentacleTransformationDescription, String tentacleBodyDescription, List<BodyPartTag> tags) {
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

	@Deprecated
	public static AbstractTentacleType getTentacleTypeFromId(String id) {
		return table.of(id);
	}

	@Deprecated
	public static String getIdFromTentacleType(AbstractTentacleType tentacleType) {
		return tentacleType.getId();
	}

	@Deprecated
	public static List<AbstractTentacleType> getAllTentacleTypes() {
		return table.listByRace();
	}
	
	@Deprecated
	public static List<AbstractTentacleType> getTentacleTypes(Race r) {
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
