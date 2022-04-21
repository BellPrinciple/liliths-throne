package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.Scene;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.Cell;
import com.lilithsthrone.world.WorldRegion;
import com.lilithsthrone.world.places.Darkness;
import com.lilithsthrone.world.places.PlaceType;
import com.lilithsthrone.world.places.PlaceUpgrade;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public enum Place implements PlaceType {
	ENTRANCE_EXTERIOR,
	ENTRANCE_HANDLE,
	ENTRANCE,
	CORRIDOR,
	SLEEPING_AREA,
	SLEEPING_AREA_SEARCHED,
	COMMON_AREA,
	COMMON_AREA_SEARCHED,
	ARMORY,
	ARMORY_SEARCHED,
	CAVED_IN_ROOM;

	private String svgString;

	@Override
	public String getId() {
		switch(this) {
		case ENTRANCE_EXTERIOR:
		case ENTRANCE_HANDLE:
			return "BAT_CAVERNS_REBEL_BASE_"+name();
		default:
			return "REBEL_BASE_"+name();
		}
	}

	@Override
	public WorldRegion getWorldRegion() {
		return WorldRegion.SUBMISSION;
	}

	@Override
	public String getName() {
		switch(this) {
		case ENTRANCE_EXTERIOR:
			return "Hidden Cave Entrance";
		case ENTRANCE_HANDLE:
			return "Strange Handle";
		case ENTRANCE:
			return "Entrance";
		case CORRIDOR:
			return "Corridor";
		case SLEEPING_AREA:
		case SLEEPING_AREA_SEARCHED:
			return "Abandoned Sleeping Area";
		case COMMON_AREA:
		case COMMON_AREA_SEARCHED:
			return "Abandoned Common Area";
		case ARMORY:
		case ARMORY_SEARCHED:
			return "A Partly Caved-in Room";
		case CAVED_IN_ROOM:
			return "A Mostly Caved-in Room";
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTooltipDescription() {
		switch(this) {
		case ENTRANCE_EXTERIOR:
			return "The entrance to a mysterious artificial cave, formerly concealed behind a tight-fitting stone door.";
		case ENTRANCE_HANDLE:
			return "A strange handle juts out from the rock.";
		case ENTRANCE:
			return "The only way in or out of the cave is cleverly concealed behind a tight-fitting stone door.";
		case CORRIDOR:
			return "An artificial cave lined with questionable wooden supports.";
		case SLEEPING_AREA:
		case SLEEPING_AREA_SEARCHED:
			return "A long abandoned room full of long abandoned beds.";
		case COMMON_AREA:
		case COMMON_AREA_SEARCHED:
			return "The sparsely furnished ruins of a common area.";
		case ARMORY:
		case ARMORY_SEARCHED:
			return "A room partly filled with rubble.";
		case CAVED_IN_ROOM:
			return "A room with nothing but rubble in it.";
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Colour getColour() {
		switch(this) {
		case ENTRANCE:
		case ENTRANCE_EXTERIOR:
			return PresetColour.BASE_RED;
		case CORRIDOR:
			return PresetColour.BASE_BLACK;
		case SLEEPING_AREA:
			return PresetColour.BASE_BLUE;
		case ENTRANCE_HANDLE:
		case SLEEPING_AREA_SEARCHED:
		case COMMON_AREA_SEARCHED:
		case ARMORY_SEARCHED:
			return PresetColour.BASE_GREY;
		case COMMON_AREA:
			return PresetColour.BASE_ORANGE;
		case ARMORY:
			return PresetColour.BASE_GREEN;
		case CAVED_IN_ROOM:
			return PresetColour.BASE_GREY_DARK;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public com.lilithsthrone.game.dialogue.encounters.Encounter getEncounterType() {
		switch(this) {
		case ENTRANCE_EXTERIOR:
		case ENTRANCE_HANDLE:
			return com.lilithsthrone.game.dialogue.encounters.Encounter.BAT_CAVERN;
		case SLEEPING_AREA:
			return Encounter.INSANE_SURVIVOR;
		default:
			return null;
		}
	}

	@Override
	public Scene getDialogue(Cell cell, boolean withRandomEncounter, boolean forceEncounter) {
		if(withRandomEncounter) {
			var encounterType = getEncounterType();
			if(encounterType!=null) {
				Scene dn = encounterType.getRandomEncounter(forceEncounter);
				if(dn != null)
					return dn;
			}
		}
		switch(this) {
		case ENTRANCE_EXTERIOR:
			return Dialogue.ENTRANCE_EXTERIOR;
		case ENTRANCE_HANDLE:
			return Dialogue.ENTRANCE_HANDLE;
		case ENTRANCE:
			return Dialogue.ENTRANCE;
		case CORRIDOR:
			return Dialogue.CORRIDOR;
		case SLEEPING_AREA:
			return Dialogue.SLEEPING_AREA;
		case SLEEPING_AREA_SEARCHED:
			return Dialogue.SLEEPING_AREA_SEARCHED;
		case COMMON_AREA:
			return Dialogue.COMMON_AREA;
		case COMMON_AREA_SEARCHED:
			return RebelBase.REBEL_BASE_COMMON_AREA_SEARCHED;
		case ARMORY:
			return RebelBase.REBEL_BASE_ARMORY;
		case ARMORY_SEARCHED:
			return RebelBase.REBEL_BASE_ARMORY_SEARCHED;
		case CAVED_IN_ROOM:
			return RebelBase.REBEL_BASE_CAVED_IN_ROOM;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Darkness getDarkness() {
		return Darkness.ALWAYS_DARK;
	}

	@Override
	public String getSVGString(Set<PlaceUpgrade> upgrades) {
		if(svgString != null)
			return svgString;
		String path = getSvgPath();
		if(path == null)
			return null;
		try(InputStream s = Place.class.getResourceAsStream("/com/lilithsthrone/rebelbase/"+path)) {
			return svgString = Util.inputStreamToString(s);
		}
		catch(IOException x) {
			x.printStackTrace();
			return null;
		}
	}

	@Override
	public String getVirginityLossDescription() {
		switch(this) {
		case ENTRANCE_EXTERIOR:
			return "beside the entrance to a mysterious artificial cave";
		case ENTRANCE_HANDLE:
			return "in the Bat Caverns";
		default:
			return "in a mysterious artificial cave";
		}
	}

	private String getSvgPath() {
		switch(this) {
		case ENTRANCE_EXTERIOR:
		case ENTRANCE_HANDLE:
		case ENTRANCE:
			return "entrance";
		case CORRIDOR:
			return null;
		case SLEEPING_AREA:
		case SLEEPING_AREA_SEARCHED:
			return "cache1";
		case COMMON_AREA:
		case COMMON_AREA_SEARCHED:
			return "cache2";
		case ARMORY:
		case ARMORY_SEARCHED:
			return "cache3";
		case CAVED_IN_ROOM:
			return "cavein";
		}
		throw new UnsupportedOperationException();
	}
}
