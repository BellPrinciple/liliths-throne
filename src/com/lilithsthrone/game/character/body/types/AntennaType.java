package com.lilithsthrone.game.character.body.types;

import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractAntennaType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.7
 * @author Innoxia
 */
public interface AntennaType extends BodyPartTypeInterface {

	int getDefaultAntennaePerRow();

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return true;
	}

	@Override
	default String getDeterminer(GameCharacter gc) {
		if(gc.getAntennaRows()==1) {
			if(gc.getAntennaePerRow()==1) {
				return "a solitary";
			} else if(gc.getAntennaePerRow()==2) {
				return "a pair of";
			} else if(gc.getAntennaePerRow()==3) {
				return "a trio of";
			} else {
				return "a quartet of";
			}

		} else {
			if(gc.getAntennaePerRow()==1) {
				return Util.intToString(gc.getAntennaRows())+" vertically-aligned";
			} else if(gc.getAntennaePerRow()==2) {
				return Util.intToString(gc.getAntennaRows())+" pairs of";
			} else if(gc.getAntennaePerRow()==3) {
				return Util.intToString(gc.getAntennaRows())+" trios of";
			} else {
				return Util.intToString(gc.getAntennaRows())+" quartets of";
			}
		}
	}

	public static final AbstractAntennaType NONE = new AbstractAntennaType(
			BodyCoveringType.ANTENNA,
			Race.NONE,
			"none",
			"",
			"",
			new ArrayList<>(),
			new ArrayList<>(),
			"<br/>[npc.Name] now [npc.has] [style.boldTfGeneric(no antennae)].",
			"") {
			@Override
			public String getId() {
				return "NONE";
			}
	};

	TypeTable<AbstractAntennaType> table = new TypeTable<>(
		s->s,
		AntennaType.class,
		AbstractAntennaType.class,
		"antenna",
		(f,n,a,m)->new AbstractAntennaType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	public static AbstractAntennaType getAntennaTypeFromId(String id) {
		return table.of(id);
	}
	
	@Deprecated
	public static String getIdFromAntennaType(AbstractAntennaType antennaType) {
		return antennaType.getId();
	}

	@Deprecated
	public static List<AbstractAntennaType> getAllAntennaTypes() {
		return table.listByRace();
	}

	public static List<AbstractAntennaType> getAntennaTypes(Race r) {
		return table.of(r).orElse(List.of(NONE));
	}
}
