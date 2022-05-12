package com.lilithsthrone.game.character.body.types;

import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractAntennaType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.PronounUtility;
import com.lilithsthrone.utils.MarkupWriter;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.PresetColour;

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

	AntennaType NONE = Special.NONE;

	enum Special implements AntennaType {
		NONE,
		;

		@Override
		public String getId() {
			return name();
		}

		@Override
		public BodyCoveringType getBodyCoveringType(Body body) {
			return BodyCoveringType.ANTENNA;
		}

		@Override
		public Race getRace() {
			return Race.NONE;
		}

		@Override
		public String getTransformationNameOverride() {
			return "none";
		}

		@Override
		public String getNameSingular(GameCharacter gc) {
			return "";
		}

		@Override
		public String getNamePlural(GameCharacter gc) {
			return "";
		}

		@Override
		public int getDefaultAntennaePerRow() {
			return 2;
		}

		@Override
		public String getDescriptor(GameCharacter gc) {
			return null;
		}

		@Override
		public String getBodyDescription(GameCharacter owner) {
			return "";
		}

		@Override
		public String getTransformationDescription(GameCharacter owner) {
			return MarkupWriter.string()
			.br()
			.text(PronounUtility.Name(owner)," now ",PronounUtility.have(owner)," ")
			.bold(PresetColour.TRANSFORMATION_GENERIC,"no antennae")
			.text(".")
			.build();
		}
	}

	TypeTable<AntennaType> table = new TypeTable<>(
		s->s,
		Special.values(),
		"antenna",
		(f,n,a,m)->new AbstractAntennaType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	@Deprecated
	static AntennaType getAntennaTypeFromId(String id) {
		return table.of(id);
	}
	
	@Deprecated
	static String getIdFromAntennaType(AntennaType antennaType) {
		return antennaType.getId();
	}

	@Deprecated
	static List<AntennaType> getAllAntennaTypes() {
		return table.listByRace();
	}

	static List<AntennaType> getAntennaTypes(Race r) {
		return table.of(r).orElse(List.of(NONE));
	}
}
