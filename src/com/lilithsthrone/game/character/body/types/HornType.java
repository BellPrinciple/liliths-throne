package com.lilithsthrone.game.character.body.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractHornType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.utils.Util;

/**
 * 
 * @since 0.1.0
 * @version 0.4
 * @author Innoxia
 */
public interface HornType extends BodyPartTypeInterface {

	boolean isGeneric();

	int getDefaultHornsPerRow();

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	@Override
	default String getDeterminer(GameCharacter gc) {
		if(gc.getHornRows()==1) {
			if(gc.getHornsPerRow()==1) {
				return "a solitary";
			} else if(gc.getHornsPerRow()==2) {
				return "a pair of";
			} else if(gc.getHornsPerRow()==3) {
				return "a trio of";
			} else {
				return "a quartet of";
			}

		} else {
			if(gc.getHornsPerRow()==1) {
				return Util.intToString(gc.getHornRows())+" vertically-aligned";
			} else if(gc.getHornsPerRow()==2) {
				return Util.intToString(gc.getHornRows())+" pairs of";
			} else if(gc.getHornsPerRow()==3) {
				return Util.intToString(gc.getHornRows())+" trios of";
			} else {
				return Util.intToString(gc.getHornRows())+" quartets of";
			}
		}
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return true;
	}

	@Override
	default String getName(GameCharacter gc){
		if(isDefaultPlural(gc) && (gc.getHornsPerRow()>1 || gc.getHornRows()>1)) {
			return getNamePlural(gc);
		} else {
			return getNameSingular(gc);
		}
	}

	@Override
	default TFModifier getTFModifier() {
		return getTFTypeModifier(HornType.getHornTypes(getRace(),false));
	}

	// If any more horn types are added, check to see that the potion TFs still work. (5 types is currently the maximum.)
	
	public static final AbstractHornType NONE = new Special(
			BodyCoveringType.HORN,
			Race.NONE,
			2,
			"none",
			"",
			"",
			new ArrayList<>(),
			new ArrayList<>(),
			"<br/>[npc.Name] now [npc.has] [style.boldTfGeneric(no horns)].",
			"") {
		@Override
		public TFModifier getTFModifier() {
			return TFModifier.NONE;
		}
	};

//	// Cows:
//	
//	public static final AbstractHornType BOVINE_CURVED = new Special(
//			BodyCoveringType.HORN,
//			Race.COW_MORPH,
//			2,
//			"curved",
//			"horn",
//			"horns",
//			Util.newArrayListOfValues("curved", "bovine"),
//			Util.newArrayListOfValues("curved", "bovine", "smooth"),
//			"slightly-curved #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF."
//					+ "<br/>[npc.Name] now [npc.has] [npc.hornsDeterminer] [style.boldTfGeneric(curved #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF)].",
//			"[npc.HornsDeterminer] [npc.hornSize], [npc.hornColour(true)], curved #IFnpc.getTotalHorns()==1#THEN[npc.horn] grows#ELSE[npc.horns] grow#ENDIF out of the #IFnpc.getHornsPerRow()==1#THENmiddle#ELSEupper sides#ENDIF of [npc.her] forehead.") {
//	};
//
//	public static final AbstractHornType BOVINE_STRAIGHT = new Special(
//			BodyCoveringType.HORN,
//			Race.COW_MORPH,
//			2,
//			"straight",
//			"horn",
//			"horns",
//			Util.newArrayListOfValues("straight", "bovine"),
//			Util.newArrayListOfValues("straight", "bovine", "smooth"),
//			"sleek, straight #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF."
//					+ "<br/>[npc.Name] now [npc.has] [npc.hornsDeterminer] [style.boldTfGeneric(straight #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF)].",
//			"[npc.HornsDeterminer] [npc.hornSize], [npc.hornColour(true)], straight #IFnpc.getTotalHorns()==1#THEN[npc.horn] grows#ELSE[npc.horns] grow#ENDIF out of the #IFnpc.getHornsPerRow()==1#THENmiddle#ELSEupper sides#ENDIF of [npc.her] forehead.") {
//	};

	// Reindeer:
	
	public static final AbstractHornType REINDEER_RACK = new Special(
			BodyCoveringType.ANTLER,
			Race.REINDEER_MORPH,
			2,
			"multi-branched",
			"antler",
			"antlers",
			Util.newArrayListOfValues("multi-branched", "reindeer"),
			Util.newArrayListOfValues("multi-branched", "reindeer"),
			"branching, multi-pronged #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF."
					+ "<br/>[npc.Name] now [npc.has] [npc.hornsDeterminer] [style.boldTfGeneric(reindeer-like #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF)].",
			"[npc.HornsDeterminer] [npc.hornSize], [npc.hornColour(true)], multi-branched #IFnpc.getTotalHorns()==1#THEN[npc.horn] grows#ELSE[npc.horns] grow#ENDIF out of the #IFnpc.getHornsPerRow()==1#THENmiddle#ELSEupper sides#ENDIF of [npc.her] forehead.") {
	};
	
	// Horse:
	
	public static final AbstractHornType HORSE_STRAIGHT = new Special(
			BodyCoveringType.HORN,
			Race.HORSE_MORPH,
			1,
			"unicorn",
			"horn",
			"horns",
			Util.newArrayListOfValues("straight", "twirling", "unicorn"),
			Util.newArrayListOfValues("straight", "twirling", "unicorn"),
			"straight, twirling unicorn's #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF."
					+ "<br/>[npc.Name] now [npc.has] [npc.hornsDeterminer] [style.boldTfGeneric(unicorn #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF)].",
			"[npc.HornsDeterminer] [npc.hornSize], [npc.hornColour(true)], unicorn's #IFnpc.getTotalHorns()==1#THEN[npc.horn] grows#ELSE[npc.horns] grow#ENDIF out of the #IFnpc.getHornsPerRow()==1#THENmiddle#ELSEupper sides#ENDIF of [npc.her] forehead.") {
	};

	// Generic:
	
	public static final AbstractHornType CURLED = new Special(
			BodyCoveringType.HORN,
			Race.NONE,
			2,
			"curled",
			"horn",
			"horns",
			Util.newArrayListOfValues("curled"),
			Util.newArrayListOfValues("curled", "smooth"),
			"circular-curling #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF."
					+ "<br/>[npc.Name] now [npc.has] [npc.hornsDeterminer] [style.boldTfGeneric(curled #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF)].",
			"[npc.HornsDeterminer] [npc.hornSize], [npc.hornColour(true)], circular-curling #IFnpc.getTotalHorns()==1#THEN[npc.horn] grows#ELSE[npc.horns] grow#ENDIF out of the #IFnpc.getHornsPerRow()==1#THENmiddle#ELSEupper sides#ENDIF of [npc.her] forehead.") {
		@Override
		public boolean isGeneric() {
			return true;
		}
	};
	
	public static final AbstractHornType SPIRAL = new Special(
			BodyCoveringType.HORN,
			Race.NONE,
			2,
			"spiral", //Rasen no Chikara
			"horn",
			"horns",
			Util.newArrayListOfValues("spiral"),
			Util.newArrayListOfValues("spiral", "smooth"),
			"twisted, spiralling #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF."
					+ "<br/>[npc.Name] now [npc.has] [npc.hornsDeterminer] [style.boldTfGeneric(spiral #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF)].",
			"[npc.HornsDeterminer] [npc.hornSize], [npc.hornColour(true)], spiralling #IFnpc.getTotalHorns()==1#THEN[npc.horn] grows#ELSE[npc.horns] grow#ENDIF out of the #IFnpc.getHornsPerRow()==1#THENmiddle#ELSEupper sides#ENDIF of [npc.her] forehead.") {
		@Override
		public boolean isGeneric() {
			return true;
		}
	};
	
	public static final AbstractHornType CURVED = new Special(
			BodyCoveringType.HORN,
			Race.NONE,
			2,
			"curved",
			"horn",
			"horns",
			Util.newArrayListOfValues("curved"),
			Util.newArrayListOfValues("curved", "smooth"),
			"slightly-curved #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF."
					+ "<br/>[npc.Name] now [npc.has] [npc.hornsDeterminer] [style.boldTfGeneric(curved #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF)].",
			"[npc.HornsDeterminer] [npc.hornSize], [npc.hornColour(true)], curved #IFnpc.getTotalHorns()==1#THEN[npc.horn] grows#ELSE[npc.horns] grow#ENDIF out of the #IFnpc.getHornsPerRow()==1#THENmiddle#ELSEupper sides#ENDIF of [npc.her] forehead.") {
		@Override
		public boolean isGeneric() {
			return true;
		}
	};
	
	public static final AbstractHornType SWEPT_BACK = new Special(
			BodyCoveringType.HORN,
			Race.NONE,
			2,
			"swept-back",
			"horn",
			"horns",
			Util.newArrayListOfValues("swept-back"),
			Util.newArrayListOfValues("swept-back", "smooth"),
			"sleek #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF, before sweeping back and curving over [npc.her] head."
					+ "<br/>[npc.Name] now [npc.has] [npc.hornsDeterminer] [style.boldTfGeneric(swept-back #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF)].",
			"[npc.HornsDeterminer] [npc.hornSize], [npc.hornColour(true)], swept-back #IFnpc.getTotalHorns()==1#THEN[npc.horn] grows#ELSE[npc.horns] grow#ENDIF out of the #IFnpc.getHornsPerRow()==1#THENmiddle#ELSEupper sides#ENDIF of [npc.her] forehead.") {
		@Override
		public boolean isGeneric() {
			return true;
		}
	};
	
	public static final AbstractHornType STRAIGHT = new Special(
			BodyCoveringType.HORN,
			Race.NONE,
			2,
			"straight",
			"horn",
			"horns",
			Util.newArrayListOfValues("straight"),
			Util.newArrayListOfValues("straight", "smooth"),
			"sleek, straight horns."
					+ "<br/>[npc.Name] now [npc.has] [npc.hornsDeterminer] [style.boldTfGeneric(straight #IFnpc.getTotalHorns()==1#THEN[npc.horn]#ELSE[npc.horns]#ENDIF)].",
			"[npc.HornsDeterminer] [npc.hornSize], [npc.hornColour(true)], straight #IFnpc.getTotalHorns()==1#THEN[npc.horn] grows#ELSE[npc.horns] grow#ENDIF out of the #IFnpc.getHornsPerRow()==1#THENmiddle#ELSEupper sides#ENDIF of [npc.her] forehead.") {
		@Override
		public boolean isGeneric() {
			return true;
		}
	};
	
	class Special extends AbstractHornType {

		private String id;

		public Special(AbstractBodyCoveringType coveringType, Race race, int defaultHornsPerRow, String transformationName, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String hornTransformationDescription, String hornBodyDescription) {
			super(coveringType, race, defaultHornsPerRow, transformationName, name, namePlural, descriptorsMasculine, descriptorsFeminine, hornTransformationDescription, hornBodyDescription);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(HornType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<AbstractHornType> table = new TypeTable<>(
		HornType::sanitize,
		HornType.class,
		AbstractHornType.class,
		"horn",
		(f,n,a,m)->new AbstractHornType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	public static AbstractHornType getHornTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("BOVINE_CURVED")) {
			return "CURVED";
		} else if(id.equals("BOVINE_STRAIGHT")) {
			return "STRAIGHT";
		}
		return id;
	}

	@Deprecated
	public static String getIdFromHornType(AbstractHornType hornType) {
		return hornType.getId();
	}

	@Deprecated
	public static List<AbstractHornType> getAllHornTypes() {
		return table.listByRace();
	}
	
	/**
	 * 
	 * @param race The race whose available horn types are to be returned.
	 * @param retainNone Whether to leave HornType.NONE in the list (true) or remove it if it's present (false).
	 * @return A list of HornTypes which are available for this race to have <b>via transformation, not by default</b>. If you want to find out what HornTypes a race has by default, use their RacialBody's getHornTypes() method.
	 */
	public static List<AbstractHornType> getHornTypes(Race race, boolean retainNone) {
		var l = table.of(race).orElse(List.of());
		if(retainNone)
			return l;
		var a = new ArrayList<>(l);
		a.remove(NONE);
		return a;
	}
}
