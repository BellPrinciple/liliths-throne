package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.WorldRegion;
import com.lilithsthrone.world.places.PlaceType;

public enum World implements com.lilithsthrone.world.WorldType {
	WORLD;

	@Override
	public String getId() {
		return "REBEL_BASE";
	}

	@Override
	public WorldRegion getWorldRegion() {
		return WorldRegion.SUBMISSION;
	}

	@Override
	public String getName() {
		return "Mysterious Cave";
	}

	@Override
	public Colour getColour() {
		return PresetColour.BASE_GREY;
	}

	@Override
	public PlaceType getStandardPlace() {
		return PlaceType.WORLD_MAP_DOMINION;
	}

	@Override
	public PlaceType getGlobalMapLocation() {
		return null;
	}

	@Override
	public Place getEntryFromGlobalMapLocation() {
		return Place.ENTRANCE;
	}

	@Override
	public String getFileLocation() {
		return "/com/lilithsthrone/rebelbase/res/map.png";
	}

	@Override
	public PlaceType placeByColor(int color) {
		switch(color) {
		case 0xFFFFFF:
			return Place.GENERIC_IMPASSABLE;
		case 0xed1c24:
			return Place.ENTRANCE;
		case 0x22b14c:
			return Place.CORRIDOR;
		case 0xf8941d:
			return Place.SLEEPING_AREA;
		case 0x662d91:
			return Place.COMMON_AREA;
		case 0x6dd0f7:
			return Place.ARMORY;
		case 0x3f48cc:
			return Place.CAVED_IN_ROOM;
		default:
			return null;
		}
	}

	@Override
	public boolean isLoiteringEnabled() {
		return false;
	}

	@Override
	public boolean isFlightEnabled() {
		return false;
	}

	@Override
	public String getSexBlockedReason(GameCharacter character) {
		return null;
	}
}
