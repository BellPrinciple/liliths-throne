package com.lilithsthrone.game.character.body.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractWingType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.WingSize;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.3.8.2
 * @author Innoxia
 */
public interface WingType extends BodyPartTypeInterface {

	boolean allowsFlight();

	boolean isGeneric();

	WingSize getMinimumSize();

	WingSize getMaximumSize();

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return true;
	}

	@Override
	default String getDeterminer(GameCharacter gc) {
		return "a pair of";
	}

	@Override
	default TFModifier getTFModifier() {
		return getTFTypeModifier(WingType.getWingTypes(getRace()));
	}

	// If any more wing types are added, check to see that the potion TFs still work. (5 types is currently the maximum.)
	
	public static final AbstractWingType NONE = new Special(
			null,
			Race.NONE,
			false,
			"none",
			"wing",
			"wings",
			new ArrayList<>(),
			new ArrayList<>(),
			"#IF(npc.getLegConfiguration().isWingsOnLegConfiguration())"
					+ "With a strong tugging sensation, [npc.her] [npc.wings] shrink away and disappear into the sides of [npc.her] [npc.legConfiguration] body."
			+ "#ELSE"
				+ "With a strong tugging sensation, [npc.her] [npc.wings] shrink away and disappear into [npc.her] back."
			+ "#ENDIF"
			+ "<br/>[npc.Name] now [npc.has] [style.boldTfGeneric(no wings)].",
			"") {
		@Override
		public TFModifier getTFModifier() {
			return TFModifier.REMOVAL;
		}
	};

	// Angels:
	
	public static final AbstractWingType ANGEL = new Special(
			BodyCoveringType.ANGEL_FEATHER,
			Race.ANGEL,
			true,
			"angelic feathered",
			"wing",
			"wings",
			Util.newArrayListOfValues("angelic", "feathered"),
			Util.newArrayListOfValues("angelic", "feathered"),
			"#IF(npc.getLegConfiguration().isWingsOnLegConfiguration())"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], feathered, angelic wings push out from the sides of [npc.her] [npc.legConfiguration] body."
			+ "#ELSE"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], feathered, angelic wings push out from [npc.her] shoulder blades."
			+ "#ENDIF"
			+ "<br/>"
			+ "[npc.Name] now [npc.has] [style.boldAngel(angelic, feathered wings)].",
			"[npc.sheHasFull] a pair of [npc.wingSize], feathered, angelic wings, which are [npc.materialDescriptor] [npc.wingFullDescription(true)].") {
	};

	// Demons:
	
	public static final AbstractWingType DEMON_COMMON = new Special(
			BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			true,
			"demonic leathery",
			"wing",
			"wings",
			Util.newArrayListOfValues("demonic", "leathery"),
			Util.newArrayListOfValues("demonic", "leathery"),
			"#IF(npc.getLegConfiguration().isWingsOnLegConfiguration())"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], leathery, demonic wings push out from the sides of [npc.her] [npc.legConfiguration] body."
			+ "#ELSE"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], leathery, demonic wings push out from [npc.her] shoulder blades."
			+ "#ENDIF"
			+ "<br/>"
			+ "#IF(npc.isShortStature())"
				+ "[npc.Name] now [npc.has] [style.boldImp(impish, leathery wings)]."
			+ "#ELSE"
				+ "[npc.Name] now [npc.has] [style.boldDemon(demonic, leathery wings)]."
			+ "#ENDIF",
			"[npc.sheHasFull] a pair of [npc.wingSize], leathery, demonic wings, which are [npc.materialDescriptor] [npc.wingFullDescription(true)].") {
	};

	public static final AbstractWingType DEMON_FEATHERED = new Special(
			BodyCoveringType.DEMON_FEATHER,
			Race.DEMON,
			true,
			"demonic feathered",
			"wing",
			"wings",
			Util.newArrayListOfValues("demonic", "feathered"),
			Util.newArrayListOfValues("demonic", "feathered"),
			"#IF(npc.getLegConfiguration().isWingsOnLegConfiguration())"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], feathered, demonic wings push out from the sides of [npc.her] [npc.legConfiguration] body."
			+ "#ELSE"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], feathered, demonic wings push out from [npc.her] shoulder blades."
			+ "#ENDIF"
			+ "<br/>"
			+ "#IF(npc.isShortStature())"
				+ "[npc.Name] now [npc.has] [style.boldImp(impish, feathered wings)]."
			+ "#ELSE"
				+ "[npc.Name] now [npc.has] [style.boldDemon(demonic, feathered wings)]."
			+ "#ENDIF",
			"[npc.sheHasFull] a pair of [npc.wingSize], feathered, demonic wings, which are [npc.materialDescriptor] [npc.wingFullDescription(true)].") {
	};
	
	// Generic:

	public static final AbstractWingType LEATHERY = new Special(
			BodyCoveringType.WING_LEATHER,
			Race.NONE,
			true,
			"leathery",
			"wing",
			"wings",
			Util.newArrayListOfValues("leathery"),
			Util.newArrayListOfValues("leathery"),
			"#IF(npc.getLegConfiguration().isWingsOnLegConfiguration())"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], leathery wings push out from the sides of [npc.her] [npc.legConfiguration] body."
			+ "#ELSE"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], leathery wings push out from [npc.her] shoulder blades."
			+ "#ENDIF"
			+ "<br/>"
			+ "[npc.Name] now [npc.has] [style.boldTfGeneric(leathery wings)].",
			"[npc.sheHasFull] a pair of [npc.wingSize], leathery wings, which are [npc.materialDescriptor] [npc.wingFullDescription(true)].") {
		@Override
		public boolean isGeneric() {
			return true;
		}
	};

	public static final AbstractWingType FEATHERED = new Special(
			BodyCoveringType.FEATHERS,
			Race.NONE,
			true,
			"feathered",
			"wing",
			"wings",
			Util.newArrayListOfValues("feathered"),
			Util.newArrayListOfValues("feathered"),
			"#IF(npc.getLegConfiguration().isWingsOnLegConfiguration())"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], feathered wings push out from the sides of [npc.her] [npc.legConfiguration] body."
			+ "#ELSE"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], feathered wings push out from [npc.her] shoulder blades."
			+ "#ENDIF"
			+ "<br/>"
			+ "[npc.Name] now [npc.has] [style.boldTfGeneric(feathered wings)].",
			"[npc.sheHasFull] a pair of [npc.wingSize], feathered wings, which are [npc.materialDescriptor] [npc.wingFullDescription(true)].") {
		@Override
		public boolean isGeneric() {
			return true;
		}
	};

	public static final AbstractWingType INSECT = new Special(
			BodyCoveringType.WING_CHITIN,
			Race.NONE,
			true,
			"insect",
			"wing",
			"wings",
			Util.newArrayListOfValues("chitinous"),
			Util.newArrayListOfValues("chitinous"),
			"#IF(npc.getLegConfiguration().isWingsOnLegConfiguration())"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], insect-like wings push out from the sides of [npc.her] [npc.legConfiguration] body."
			+ "#ELSE"
				+ "[npc.She] [npc.verb(bite)] [npc.her] [npc.lip] to try and suppress an unexpected moan of pleasure as a pair of [npc.wingSize], insect-like wings push out from [npc.her] shoulder blades."
			+ "#ENDIF"
			+ "<br/>"
			+ "[npc.Name] now [npc.has] [style.boldTfGeneric(chitinous, insect-like wings)].",
			"[npc.sheHasFull] a pair of [npc.wingSize], insect-like wings, which are [npc.materialDescriptor] [npc.wingFullDescription(true)].") {
		@Override
		public boolean isGeneric() {
			return true;
		}
	};

	class Special extends AbstractWingType {

		private String id;

		public Special(BodyCoveringType coveringType, Race race, boolean allowsFlight, String transformationName, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String wingTransformationDescription, String wingBodyDescription) {
			super(coveringType, race, allowsFlight, transformationName, name, namePlural, descriptorsMasculine, descriptorsFeminine, wingTransformationDescription, wingBodyDescription);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(LegType.class.getFields())
			.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
			.findAny().orElseThrow().getName());
		}
	}

	TypeTable<WingType> table = new TypeTable<>(
		WingType::sanitize,
		WingType.class,
		AbstractWingType.class,
		"wing",
		(f,n,a,m)->new AbstractWingType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	static WingType getWingTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		return switch(id) {
			case "IMP" -> "DEMON_COMMON";
			case "PEGASUS" -> "FEATHERED";
			default -> id;
		};
	}

	static String getIdFromWingType(WingType wingType) {
		return wingType.getId();
	}

	static List<WingType> getAllWingTypes() {
		return table.listByRace();
	}

	static List<WingType> getWingTypes(Race r) {
		return table.of(r).orElseGet(()->table.listByRace().stream()
			.filter(WingType::isGeneric)
			.collect(Collectors.toList()));
	}
}
