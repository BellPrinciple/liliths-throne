package com.lilithsthrone.world;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.Map.entry;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.places.PlaceType;

/**
 * @since 0.1.0
 * @version 0.3.7.2
 * @author Innoxia
 */
public class WorldType {
	
	// Dominion:
	
	public static AbstractWorldType WORLD_MAP = new AbstractWorldType(WorldRegion.MISC,
			"Lilith's Realm",
			PresetColour.BASE_TAN,
			true,
			true,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/global/world_map.png", null, null, Map.ofEntries(
					entry(new Color(0x61997e), PlaceType.WORLD_MAP_THICK_JUNGLE), // thick jungle
					entry(new Color(0x81cca8), PlaceType.WORLD_MAP_JUNGLE), // jungle
					entry(new Color(0xb377b0), PlaceType.WORLD_MAP_JUNGLE_CITY), // jungle city
					
					entry(new Color(0x696969), PlaceType.WORLD_MAP_FOOTHILLS), // foothills
					entry(new Color(0xc1c1c1), PlaceType.WORLD_MAP_MOUNTAINS), // low mountains
					entry(new Color(0xe0e0e0), PlaceType.WORLD_MAP_SNOWY_MOUNTAINS), // snowy mountains
					
					entry(new Color(0xffffff), PlaceType.WORLD_MAP_SNOWY_VALLEY), // snowy valley
					entry(new Color(0xadffff), PlaceType.WORLD_MAP_GLACIAL_LAKE), // glacial lake
					
					entry(new Color(0x8500ff), PlaceType.WORLD_MAP_DOMINION), // dominion

					entry(new Color(0xcbf1d5), PlaceType.WORLD_MAP_GRASSLANDS), // wild grasslands
					entry(new Color(0xe2ffd7), PlaceType.WORLD_MAP_FIELDS), // foloi fields
					entry(new Color(0xb4c490), PlaceType.WORLD_MAP_FOREST), // forest
					entry(new Color(0xd544ae), PlaceType.WORLD_MAP_FIELDS_CITY), // Elis
					
					entry(new Color(0x98c488), PlaceType.WORLD_MAP_YOUKO_FOREST), // shinrin highland
					
					entry(new Color(0x62e6d3), PlaceType.WORLD_MAP_WILD_RIVER), // dangerous river
					entry(new Color(0xa7fce8), PlaceType.WORLD_MAP_RIVER), // river
					
					entry(new Color(0xc4fcff), PlaceType.WORLD_MAP_SEA), // endless sea
					entry(new Color(0x8264b0), PlaceType.WORLD_MAP_SEA_CITY), // sea city
					
					entry(new Color(0xebffc4), PlaceType.WORLD_MAP_ARID_GRASSLAND), // arid grassland
					entry(new Color(0xd3e6b0), PlaceType.WORLD_MAP_ARID_SAVANNAH), // savannah
					
					entry(new Color(0xffefc4), PlaceType.WORLD_MAP_DESERT), // desert
					entry(new Color(0xffce4a), PlaceType.WORLD_MAP_SAND_DUNES), // sand dunes
					entry(new Color(0xd5445e), PlaceType.WORLD_MAP_DESERT_CITY), // desert city
					
					entry(new Color(0xff8100), PlaceType.WORLD_MAP_VOLCANO), // volcano
					entry(new Color(0x3b3b3b), PlaceType.WORLD_MAP_LAVA_FLOWS) // lava flows
					)) {
		@Override
		public boolean isDiscoveredOnStart() {
			return true;
		}
	};
	
	
	public static AbstractWorldType DOMINION = new AbstractWorldType(WorldRegion.DOMINION,
			"Dominion",
			PresetColour.BASE_PURPLE,
			true,
			true,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/dominion.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.DOMINION_PLAZA, Map.ofEntries(
					entry(new Color(0xFFFFFF), PlaceType.GENERIC_IMPASSABLE),
					
					entry(new Color(0x808080), PlaceType.DOMINION_STREET),
					entry(new Color(0x404040), PlaceType.DOMINION_BOULEVARD),
					entry(new Color(0x808000), PlaceType.DOMINION_EXIT_TO_SUBMISSION),
					
					entry(new Color(0xbf8000), PlaceType.DOMINION_BACK_ALLEYS_SAFE),
					entry(new Color(0xc10000), PlaceType.DOMINION_BACK_ALLEYS),
					entry(new Color(0x5b0000), PlaceType.DOMINION_DARK_ALLEYS),
					entry(new Color(0x40b4ff), PlaceType.DOMINION_ALLEYS_CANAL_CROSSING),
					
					entry(new Color(0x0080ff), PlaceType.DOMINION_CANAL),
					entry(new Color(0x004080), PlaceType.DOMINION_CANAL_END),
					
					entry(new Color(0x000000), PlaceType.DOMINION_DEMON_HOME_GATE),
					entry(new Color(0xff80ff), PlaceType.DOMINION_DEMON_HOME),
					entry(new Color(0xff9100), PlaceType.DOMINION_DEMON_HOME_ARTHUR),
					entry(new Color(0x8000ff), PlaceType.DOMINION_CITY_HALL),
					entry(new Color(0xff00ff), PlaceType.DOMINION_LILITHS_TOWER),
					
					entry(new Color(0x8080ff), PlaceType.DOMINION_EXIT_WEST),
					entry(new Color(0xff4a00), PlaceType.DOMINION_EXIT_NORTH),
					entry(new Color(0x008040), PlaceType.DOMINION_EXIT_EAST),
					entry(new Color(0xffff80), PlaceType.DOMINION_EXIT_SOUTH),
					
					entry(new Color(0x008080), PlaceType.DOMINION_STREET_HARPY_NESTS),
					entry(new Color(0x00ff80), PlaceType.DOMINION_HARPY_NESTS_ENTRANCE),
					
					entry(new Color(0x004000), PlaceType.DOMINION_PLAZA),
					entry(new Color(0x00ffff), PlaceType.DOMINION_AUNTS_HOME),
					entry(new Color(0xffff00), PlaceType.DOMINION_SHOPPING_ARCADE),
					entry(new Color(0x0000ff), PlaceType.DOMINION_ENFORCER_HQ),
					entry(new Color(0x000080), PlaceType.DOMINION_NIGHTLIFE_DISTRICT),
					entry(new Color(0xff0000), PlaceType.DOMINION_SLAVER_ALLEY),
					entry(new Color(0x4bff00), PlaceType.DOMINION_PARK),
					entry(new Color(0xff4000), PlaceType.DOMINION_RED_LIGHT_DISTRICT),
					entry(new Color(0xffbf00), PlaceType.DOMINION_HOME_IMPROVEMENT),
					entry(new Color(0xff0080), PlaceType.DOMINION_WAREHOUSES))) {
	};
	
	public static AbstractWorldType EMPTY = new AbstractWorldType(WorldRegion.MISC,
			"Empty (Holding world)",
			PresetColour.BASE_BROWN,
			false,
			true,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/empty.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.GENERIC_EMPTY_TILE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0xff0000),PlaceType.GENERIC_EMPTY_TILE,new Color(0xffff00),PlaceType.GENERIC_HOLDING_CELL,new Color(0x0080ff),PlaceType.GENERIC_MUSEUM)) {
	};

	public static AbstractWorldType MUSEUM = new AbstractWorldType(WorldRegion.OLD_WORLD,
			"Museum",
			PresetColour.BASE_BROWN,
			false,
			true,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/prologue/museum.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.MUSEUM_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0xff0000),PlaceType.MUSEUM_ENTRANCE,new Color(0x8000ff),PlaceType.MUSEUM_CROWDS,new Color(0x0080ff),PlaceType.MUSEUM_OFFICE,new Color(0xffff00),PlaceType.MUSEUM_STAGE,new Color(0xff8000),PlaceType.MUSEUM_ROOM,new Color(0x00ff00),PlaceType.MUSEUM_STAIRS,new Color(0x808080),PlaceType.MUSEUM_LOBBY)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};

	public static AbstractWorldType MUSEUM_LOST = new AbstractWorldType(WorldRegion.OLD_WORLD,
			"Museum",
			PresetColour.BASE_BROWN,
			false,
			true,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/prologue/museum_lost.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.MUSEUM_MIRROR, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0xff8000),PlaceType.MUSEUM_ROOM,new Color(0x00ff00),PlaceType.MUSEUM_MIRROR,new Color(0x808080),PlaceType.MUSEUM_CORRIDOR)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType LILAYAS_HOUSE_GROUND_FLOOR = new AbstractWorldType(WorldRegion.DOMINION,
			"Lilaya's Home GF",
			PresetColour.BASE_BLUE_LIGHT,
			true,
			false,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/lilayasHome/lilayas_home_ground_floor.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.LILAYA_HOME_ENTRANCE_HALL, Map.ofEntries(
					entry(new Color(0xFFFFFF), PlaceType.GENERIC_IMPASSABLE),
					entry(new Color(0x808080), PlaceType.LILAYA_HOME_CORRIDOR),
					entry(new Color(0xff0000), PlaceType.LILAYA_HOME_ENTRANCE_HALL),
					entry(new Color(0x008000), PlaceType.LILAYA_HOME_GARDEN),
					entry(new Color(0xff8000), PlaceType.LILAYA_HOME_LAB),
					entry(new Color(0xffff00), PlaceType.LILAYA_HOME_BIRTHING_ROOM),
					entry(new Color(0xff80ff), PlaceType.LILAYA_HOME_KITCHEN),
					entry(new Color(0x00ffff), PlaceType.LILAYA_HOME_LIBRARY),
					entry(new Color(0x8000ff), PlaceType.LILAYA_HOME_FOUNTAIN),
					entry(new Color(0xff0080), PlaceType.LILAYA_HOME_ROOM_GARDEN_GROUND_FLOOR),
					entry(new Color(0xff00ff), PlaceType.LILAYA_HOME_ROOM_WINDOW_GROUND_FLOOR),
					entry(new Color(0x00ff00), PlaceType.LILAYA_HOME_STAIR_UP),
					entry(new Color(0x00ff80), PlaceType.LILAYA_HOME_STAIR_UP_SECONDARY))){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType LILAYAS_HOUSE_FIRST_FLOOR = new AbstractWorldType(WorldRegion.DOMINION,
			"Lilaya's Home 1F",
			PresetColour.BASE_BLUE_LIGHT,
			true,
			false,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/lilayasHome/lilayas_home_first_floor.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.LILAYA_HOME_STAIR_DOWN, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.LILAYA_HOME_CORRIDOR,new Color(0xff00ff),PlaceType.LILAYA_HOME_ROOM_WINDOW_FIRST_FLOOR,new Color(0xff0080),PlaceType.LILAYA_HOME_ROOM_GARDEN_FIRST_FLOOR,new Color(0xff80ff),PlaceType.LILAYA_HOME_ROOM_LILAYA,new Color(0x0080ff),PlaceType.LILAYA_HOME_ROOM_ROSE,new Color(0x00ffff),PlaceType.LILAYA_HOME_ROOM_PLAYER,new Color(0xff0000),PlaceType.LILAYA_HOME_STAIR_DOWN,new Color(0xff8000),PlaceType.LILAYA_HOME_STAIR_DOWN_SECONDARY)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType ZARANIX_HOUSE_FIRST_FLOOR = new AbstractWorldType(WorldRegion.DOMINION,
			"Zaranix's Home 1F",
			PresetColour.BASE_CRIMSON,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/dominion/zaranixHome/first_floor.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.ZARANIX_FF_STAIRS, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.ZARANIX_FF_CORRIDOR,new Color(0x00ff00),PlaceType.ZARANIX_FF_STAIRS,new Color(0xff80ff),PlaceType.ZARANIX_FF_OFFICE,new Color(0xff00ff),PlaceType.ZARANIX_FF_ROOM,new Color(0x8000ff),PlaceType.ZARANIX_FF_MAID)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "You can't have sex while in Zaranix's house!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType ZARANIX_HOUSE_GROUND_FLOOR = new AbstractWorldType(WorldRegion.DOMINION,
			"Zaranix's Home GF",
			PresetColour.BASE_CRIMSON,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/dominion/zaranixHome/ground_floor.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.ZARANIX_GF_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.ZARANIX_GF_CORRIDOR,new Color(0x00ff00),PlaceType.ZARANIX_GF_STAIRS,new Color(0xff0000),PlaceType.ZARANIX_GF_ENTRANCE,new Color(0xffff00),PlaceType.ZARANIX_GF_LOUNGE,new Color(0xff00ff),PlaceType.ZARANIX_GF_ROOM,new Color(0x8000ff),PlaceType.ZARANIX_GF_MAID,new Color(0x00ffff),PlaceType.ZARANIX_GF_GARDEN_ROOM,new Color(0x008000),PlaceType.ZARANIX_GF_GARDEN,new Color(0xff8000),PlaceType.ZARANIX_GF_GARDEN_ENTRY)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "You can't have sex while in Zaranix's house!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};

	public static AbstractWorldType HARPY_NEST = new AbstractWorldType(WorldRegion.HARPY_NESTS,
			"Harpy Nests",
			PresetColour.BASE_CRIMSON,
			true,
			true,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/harpyNests/harpyNests.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.HARPY_NESTS_ENTRANCE_ENFORCER_POST, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.HARPY_NESTS_WALKWAYS,new Color(0x404040),PlaceType.HARPY_NESTS_WALKWAYS_BRIDGE,new Color(0x00ff80),PlaceType.HARPY_NESTS_ENTRANCE_ENFORCER_POST,new Color(0xff0000),PlaceType.HARPY_NESTS_HARPY_NEST_RED,new Color(0xff00ff),PlaceType.HARPY_NESTS_HARPY_NEST_PINK,new Color(0xffff00),PlaceType.HARPY_NESTS_HARPY_NEST_YELLOW,new Color(0xff9100),PlaceType.HARPY_NESTS_HELENAS_NEST)) {
	};
	
	public static AbstractWorldType SLAVER_ALLEY = new AbstractWorldType(WorldRegion.DOMINION,
			"Slaver Alley",
			PresetColour.BASE_RED,
			true,
			true,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/slaverAlley/slaverAlley.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.SLAVER_ALLEY_ENTRANCE, Map.ofEntries(
					entry(new Color(0xFFFFFF), PlaceType.GENERIC_IMPASSABLE),
					entry(new Color(0x808080), PlaceType.SLAVER_ALLEY_PATH),
					entry(new Color(0xff0000), PlaceType.SLAVER_ALLEY_ENTRANCE),
					
					entry(new Color(0xff80ff), PlaceType.SLAVER_ALLEY_STALL_FEMALES),
					entry(new Color(0x0080ff), PlaceType.SLAVER_ALLEY_STALL_MALES),
					
					entry(new Color(0xff8000), PlaceType.SLAVER_ALLEY_STALL_ANAL),
					entry(new Color(0xff00ff), PlaceType.SLAVER_ALLEY_STALL_VAGINAL),
					entry(new Color(0xff8080), PlaceType.SLAVER_ALLEY_STALL_ORAL),
					entry(new Color(0x404040), PlaceType.SLAVER_ALLEY_STATUE),
					
					entry(new Color(0x21bfc5), PlaceType.SLAVER_ALLEY_MARKET_STALL_EXCLUSIVE),
					entry(new Color(0x004080), PlaceType.SLAVER_ALLEY_MARKET_STALL_BULK),
					entry(new Color(0x008080), PlaceType.SLAVER_ALLEY_CAFE),
					
					entry(new Color(0xbfff00), PlaceType.SLAVER_ALLEY_BOUNTY_HUNTERS),
					
					entry(new Color(0x0000ff), PlaceType.SLAVER_ALLEY_SLAVERY_ADMINISTRATION),
					entry(new Color(0xff0080), PlaceType.SLAVER_ALLEY_SCARLETTS_SHOP),
					
					entry(new Color(0xffff00), PlaceType.SLAVER_ALLEY_AUCTIONING_BLOCK),
					entry(new Color(0x00ff00), PlaceType.SLAVER_ALLEY_PUBLIC_STOCKS))) {
	};
	
	public static AbstractWorldType BOUNTY_HUNTER_LODGE = new AbstractWorldType(WorldRegion.DOMINION,
			"The Rusty Collar",
			PresetColour.BASE_COPPER,
			false,
			false,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/slaverAlley/bountyHunterLodge/bountyHunterLodge.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.BOUNTY_HUNTER_LODGE_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.BOUNTY_HUNTER_LODGE_FLOOR,new Color(0xff0000),PlaceType.BOUNTY_HUNTER_LODGE_ENTRANCE,new Color(0xff8000),PlaceType.BOUNTY_HUNTER_LODGE_BOUNTY_BOARD,new Color(0x00ff00),PlaceType.BOUNTY_HUNTER_LODGE_BAR,new Color(0xffff00),PlaceType.BOUNTY_HUNTER_LODGE_SEATING,new Color(0x00ffff),PlaceType.BOUNTY_HUNTER_LODGE_STAIRS)) {
	};
	
	public static AbstractWorldType BOUNTY_HUNTER_LODGE_UPSTAIRS = new AbstractWorldType(WorldRegion.DOMINION,
			"The Rusty Collar (Upstairs)",
			PresetColour.BASE_COPPER,
			false,
			false,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/slaverAlley/bountyHunterLodge/bountyHunterLodgeUpstairs.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.BOUNTY_HUNTER_LODGE_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.BOUNTY_HUNTER_LODGE_UPSTAIRS_CORRIDOR,new Color(0xffff00),PlaceType.BOUNTY_HUNTER_LODGE_UPSTAIRS_ROOM,new Color(0xff8000),PlaceType.BOUNTY_HUNTER_LODGE_UPSTAIRS_ROOM_DOBERMANNS,new Color(0xff80ff),PlaceType.BOUNTY_HUNTER_LODGE_UPSTAIRS_ROOM_SHADOW_SILENCE,new Color(0x00ffff),PlaceType.BOUNTY_HUNTER_LODGE_UPSTAIRS_STAIRS)) {
	};
	
	public static AbstractWorldType SHOPPING_ARCADE = new AbstractWorldType(WorldRegion.DOMINION,
			"Shopping Arcade",
			PresetColour.BASE_YELLOW,
			true,
			true,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/shoppingArcade/shoppingArcade.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.SHOPPING_ARCADE_ENTRANCE, Map.ofEntries(
					entry(new Color(0xFFFFFF), PlaceType.GENERIC_IMPASSABLE),
					entry(new Color(0x808080), PlaceType.SHOPPING_ARCADE_PATH),
					entry(new Color(0xff0000), PlaceType.SHOPPING_ARCADE_ENTRANCE),
					entry(new Color(0x00ffff), PlaceType.SHOPPING_ARCADE_RALPHS_SHOP),
					entry(new Color(0xffff00), PlaceType.SHOPPING_ARCADE_NYANS_SHOP),
					entry(new Color(0x0080ff), PlaceType.SHOPPING_ARCADE_VICKYS_SHOP),
					entry(new Color(0x8000ff), PlaceType.SHOPPING_ARCADE_PIXS_GYM),
					entry(new Color(0xff8000), PlaceType.SHOPPING_ARCADE_KATES_SHOP),
					entry(new Color(0xff00ff), PlaceType.SHOPPING_ARCADE_GENERIC_SHOP),
					entry(new Color(0x008000), PlaceType.SHOPPING_ARCADE_ASHLEYS_SHOP),
					entry(new Color(0x00ff00), PlaceType.SHOPPING_ARCADE_RESTAURANT),
					entry(new Color(0x808000), PlaceType.SHOPPING_ARCADE_ANTIQUES),
					entry(new Color(0xff8080), PlaceType.SHOPPING_ARCADE_TOILETS)
					)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			if((character != null) && !character.getLocationPlace().getPlaceType().equals(PlaceType.SHOPPING_ARCADE_PATH)) {
				return "This isn't a suitable place in which to be having sex!";
			}
			return "";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType TEXTILES_WAREHOUSE = new AbstractWorldType(WorldRegion.DOMINION,
			"Kay's Textiles",
			PresetColour.GENERIC_ARCANE,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/dominion/textilesWarehouse/textilesWarehouse.png",
			PlaceType.WORLD_MAP_DOMINION, PlaceType.TEXTILE_WAREHOUSE_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.TEXTILE_WAREHOUSE_CORRIDOR,new Color(0xff0000),PlaceType.TEXTILE_WAREHOUSE_ENTRANCE,new Color(0xff00ff),PlaceType.TEXTILE_WAREHOUSE_STORAGE_ROOM,new Color(0xff8000),PlaceType.TEXTILE_WAREHOUSE_ENCHANTING,new Color(0x00ffff),PlaceType.TEXTILE_WAREHOUSE_OVERSEER_STATION,new Color(0x00ff00),PlaceType.TEXTILE_WAREHOUSE_OFFICE)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType ENFORCER_HQ = new AbstractWorldType(WorldRegion.DOMINION,
			"Enforcer HQ",
			PresetColour.BASE_BLUE,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/dominion/enforcerHQ/enforcerHQ.png",
			PlaceType.WORLD_MAP_DOMINION, PlaceType.ENFORCER_HQ_ENTRANCE, Map.ofEntries(
					entry(new Color(0xFFFFFF), PlaceType.GENERIC_IMPASSABLE),
					entry(new Color(0x808080), PlaceType.ENFORCER_HQ_CORRIDOR),
					entry(new Color(0xb9b9b9), PlaceType.ENFORCER_HQ_CELLS_CORRIDOR),
					entry(new Color(0x00ff00), PlaceType.ENFORCER_HQ_STAIRS),
					
					entry(new Color(0xff0000), PlaceType.ENFORCER_HQ_ENTRANCE),
					entry(new Color(0x8000ff), PlaceType.ENFORCER_HQ_WAITING_AREA),
					entry(new Color(0x0080ff), PlaceType.ENFORCER_HQ_RECEPTION_DESK),
					
					entry(new Color(0xffff00), PlaceType.ENFORCER_HQ_GUARDED_DOOR),
					entry(new Color(0x808000), PlaceType.ENFORCER_HQ_REQUISITIONS_DOOR),
					entry(new Color(0xff0080), PlaceType.ENFORCER_HQ_LOCKED_DOOR),
					entry(new Color(0x800080), PlaceType.ENFORCER_HQ_LOCKED_DOOR_EDGE),
					
					entry(new Color(0xff8000), PlaceType.ENFORCER_HQ_BRAXS_OFFICE),
					entry(new Color(0x00ffff), PlaceType.ENFORCER_HQ_OFFICE),
					entry(new Color(0xff8080), PlaceType.ENFORCER_HQ_CELLS_OFFICE),
					entry(new Color(0x3b3b3b), PlaceType.ENFORCER_HQ_CELL),

					entry(new Color(0xff4000), PlaceType.ENFORCER_HQ_ENFORCER_ENTRANCE),
					entry(new Color(0x80ff80), PlaceType.ENFORCER_HQ_REQUISITIONS),
					entry(new Color(0xff00ff), PlaceType.ENFORCER_HQ_OFFICE_QUARTERMASTER)
					)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "You can't have sex in the Enforcer HQ!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};

	public static AbstractWorldType ENFORCER_WAREHOUSE = new AbstractWorldType(WorldRegion.DOMINION,
			"SWORD Warehouse",
			PresetColour.BASE_BLUE,
			false,
			false,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/dominion/enforcerWarehouse/enforcerWarehouse.png",
			PlaceType.WORLD_MAP_DOMINION, PlaceType.ENFORCER_WAREHOUSE_ENTRANCE, Map.ofEntries(
					entry(new Color(0xFFFFFF), PlaceType.GENERIC_IMPASSABLE),
					entry(new Color(0xff0000), PlaceType.ENFORCER_WAREHOUSE_ENTRANCE),
					entry(new Color(0x808080), PlaceType.ENFORCER_WAREHOUSE_CORRIDOR),
					entry(new Color(0xff8080), PlaceType.ENFORCER_WAREHOUSE_CLAIRE_WARNING),

					entry(new Color(0x404040), PlaceType.ENFORCER_WAREHOUSE_ENCLOSURE),
					entry(new Color(0x00ff00), PlaceType.ENFORCER_WAREHOUSE_ENCLOSURE_TELEPORT_PADS),
					entry(new Color(0x00ffff), PlaceType.ENFORCER_WAREHOUSE_ENCLOSURE_TELEPORT_SHELVING),
					
					entry(new Color(0xff0080), PlaceType.ENFORCER_WAREHOUSE_ENFORCER_GUARD_POST),
					
					entry(new Color(0xff8000), PlaceType.ENFORCER_WAREHOUSE_CRATES),
					entry(new Color(0xffff00), PlaceType.ENFORCER_WAREHOUSE_CRATES_ARK),
					entry(new Color(0xff00ff), PlaceType.ENFORCER_WAREHOUSE_CRATES_LUST_WEAPON),
					entry(new Color(0x8000ff), PlaceType.ENFORCER_WAREHOUSE_CRATES_SPELL_BOOK))) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "You can't have sex in such a dangerous place!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType CITY_HALL = new AbstractWorldType(WorldRegion.DOMINION,
			"City Hall",
			PresetColour.BASE_PURPLE,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/dominion/cityHall/city_hall.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.CITY_HALL_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.CITY_HALL_CORRIDOR,new Color(0xff0000),PlaceType.CITY_HALL_ENTRANCE,new Color(0xffff00),PlaceType.CITY_HALL_INFORMATION_DESK,new Color(0x8000ff),PlaceType.CITY_HALL_WAITING_AREA,new Color(0xff8000),PlaceType.CITY_HALL_OFFICE,new Color(0x00ff00),PlaceType.CITY_HALL_STAIRS,new Color(0xff0080),PlaceType.CITY_HALL_BUREAU_OF_DEMOGRAPHICS,new Color(0xff00ff),PlaceType.CITY_HALL_ARCHIVES,new Color(0xffff80),PlaceType.CITY_HALL_BUREAU_OF_PROPERTY_RIGHTS_AND_COMMERCE)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	

	public static AbstractWorldType HOME_IMPROVEMENTS = new AbstractWorldType(WorldRegion.DOMINION,
			"Argus's DIY Depot",
			PresetColour.BASE_ORANGE,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/dominion/homeImprovements/homeImprovements.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.HOME_IMPROVEMENTS_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.HOME_IMPROVEMENTS_CORRIDOR,new Color(0x00ff00),PlaceType.HOME_IMPROVEMENTS_ENTRANCE,new Color(0xffff00),PlaceType.HOME_IMPROVEMENTS_SHELVING_PREMIUM,new Color(0xff80ff),PlaceType.HOME_IMPROVEMENTS_SHELVING_STANDARD,new Color(0xff8000),PlaceType.HOME_IMPROVEMENTS_BUILDING_SUPPLIES,new Color(0xff0080),PlaceType.HOME_IMPROVEMENTS_OFFICE,new Color(0x00ffff),PlaceType.HOME_IMPROVEMENTS_TOILETS)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
	};

	
	public static AbstractWorldType DOMINION_EXPRESS = new AbstractWorldType(WorldRegion.DOMINION,
			"Dominion Express",
			PresetColour.BASE_BROWN,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/dominion/dominionExpress/dominionExpress.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.HOME_IMPROVEMENTS_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.DOMINION_EXPRESS_CORRIDOR,new Color(0xff0000),PlaceType.DOMINION_EXPRESS_EXIT,new Color(0xff00ff),PlaceType.DOMINION_EXPRESS_STORAGE,new Color(0x00ffff),PlaceType.DOMINION_EXPRESS_OFFICE,new Color(0x0000ff),PlaceType.DOMINION_EXPRESS_FILLY_STATION,new Color(0xffff00),PlaceType.DOMINION_EXPRESS_OFFICE_STABLE,new Color(0xff8000),PlaceType.DOMINION_EXPRESS_STABLES)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	
	public static AbstractWorldType ANGELS_KISS_GROUND_FLOOR = new AbstractWorldType(WorldRegion.DOMINION,
			"Angel's Kiss GF",
			PresetColour.BASE_MAGENTA,
			false,
			false,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/angelsKiss/angelsKissGroundFloor.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.ANGELS_KISS_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.ANGELS_KISS_CORRIDOR,new Color(0x00ff00),PlaceType.ANGELS_KISS_ENTRANCE,new Color(0xff0000),PlaceType.ANGELS_KISS_STAIRCASE_UP,new Color(0x00ffff),PlaceType.ANGELS_KISS_OFFICE,new Color(0xff00ff),PlaceType.ANGELS_KISS_BEDROOM)) {
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType ANGELS_KISS_FIRST_FLOOR = new AbstractWorldType(WorldRegion.DOMINION,
			"Angel's Kiss 1F",
			PresetColour.BASE_MAGENTA,
			false,
			false,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/dominion/angelsKiss/angelsKissFirstFloor.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.ANGELS_KISS_STAIRCASE_DOWN, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.ANGELS_KISS_CORRIDOR,new Color(0xff0000),PlaceType.ANGELS_KISS_STAIRCASE_DOWN,new Color(0xff00ff),PlaceType.ANGELS_KISS_BEDROOM,new Color(0xffff00),PlaceType.ANGELS_KISS_BEDROOM_BUNNY,new Color(0xff8000),PlaceType.ANGELS_KISS_BEDROOM_LOPPY)) {
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType NIGHTLIFE_CLUB = new AbstractWorldType(WorldRegion.DOMINION,
			"The Watering Hole",
			PresetColour.BASE_BLUE,
			false,
			false,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/dominion/nightLife/wateringHole.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.WATERING_HOLE_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x00ff00),PlaceType.WATERING_HOLE_ENTRANCE,new Color(0x808080),PlaceType.WATERING_HOLE_MAIN_AREA,new Color(0x0080ff),PlaceType.WATERING_HOLE_SEATING_AREA,new Color(0xff00ff),PlaceType.WATERING_HOLE_VIP_AREA,new Color(0xff8000),PlaceType.WATERING_HOLE_BAR,new Color(0xffff00),PlaceType.WATERING_HOLE_DANCE_FLOOR,new Color(0xff0000),PlaceType.WATERING_HOLE_TOILETS)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
	};
	
	public static AbstractWorldType DADDYS_APARTMENT = new AbstractWorldType(WorldRegion.DOMINION,
			"Daddy's apartment",
			PresetColour.RACE_DEMON,
			false,
			false,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/dominion/daddy/apartment.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.DOMINION_DEMON_HOME_DADDY, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x00ff00),PlaceType.DADDY_APARTMENT_ENTRANCE,new Color(0xffff00),PlaceType.DADDY_APARTMENT_LOUNGE,new Color(0x00ffff),PlaceType.DADDY_APARTMENT_KITCHEN,new Color(0xff00ff),PlaceType.DADDY_APARTMENT_BEDROOM)){
		@Override
		public String getName() {
			return UtilText.parse("[daddy.NamePos] apartment");
		}
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "You can't have sex while in [daddy.namePos] apartment!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType HELENAS_APARTMENT = new AbstractWorldType(WorldRegion.DOMINION,
			"Helena's apartment",
			PresetColour.BASE_GOLD,
			false,
			false,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/dominion/helenaApartment/apartment.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.DOMINION_HELENA_HOTEL, Map.ofEntries(
					entry(new Color(0xFFFFFF), PlaceType.GENERIC_IMPASSABLE),
					
					entry(new Color(0x808080), PlaceType.HELENA_APARTMENT_HALLWAY),
					entry(new Color(0x00ffff), PlaceType.HELENA_APARTMENT_BALCONY),
					
					entry(new Color(0x00ff00), PlaceType.HELENA_APARTMENT_ENTRANCE),
					entry(new Color(0xff00ff), PlaceType.HELENA_APARTMENT_HELENA_BEDROOM),
					entry(new Color(0xff0080), PlaceType.HELENA_APARTMENT_SCARLETT_BEDROOM),
					entry(new Color(0xff80ff), PlaceType.HELENA_APARTMENT_BEDROOM),
					entry(new Color(0x0000ff), PlaceType.HELENA_APARTMENT_BATHROOM),
					entry(new Color(0x8080ff), PlaceType.HELENA_APARTMENT_OFFICE),
					entry(new Color(0xff8000), PlaceType.HELENA_APARTMENT_KITCHEN),
					entry(new Color(0x008000), PlaceType.HELENA_APARTMENT_DINING_ROOM),
					entry(new Color(0xffff00), PlaceType.HELENA_APARTMENT_LOUNGE),
					entry(new Color(0x8000ff), PlaceType.HELENA_APARTMENT_HOT_TUB)
					)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "You can't have sex while in Helena's apartment!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType NYANS_APARTMENT = new AbstractWorldType(WorldRegion.DOMINION,
			"Nyan's apartment",
			PresetColour.BASE_PINK_LIGHT,
			false,
			false,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/dominion/nyanApartment/apartment.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.DOMINION_NYAN_APARTMENT, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.NYAN_APARTMENT_HALLWAY,new Color(0x00ff00),PlaceType.NYAN_APARTMENT_ENTRANCE,new Color(0xff8000),PlaceType.NYAN_APARTMENT_DINING_ROOM,new Color(0xffff00),PlaceType.NYAN_APARTMENT_KITCHEN,new Color(0x0000ff),PlaceType.NYAN_APARTMENT_BATHROOM,new Color(0x00ff80),PlaceType.NYAN_APARTMENT_LOUNGE,new Color(0xff00ff),PlaceType.NYAN_APARTMENT_SPARE_BEDROOM,new Color(0xff0000),PlaceType.NYAN_APARTMENT_NYAN_BEDROOM,new Color(0x00ffff),PlaceType.NYAN_APARTMENT_ENSUITE)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "You can't have sex while in Nyan's apartment!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	// Other:

	public static AbstractWorldType SUBMISSION = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Submission",
			PresetColour.BASE_GREEN,
			true,
			true,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/submission/submission.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.SUBMISSION_ENTRANCE, Map.ofEntries(
					entry(new Color(0xFFFFFF), PlaceType.GENERIC_IMPASSABLE),

					entry(new Color(0x808000), PlaceType.SUBMISSION_ENTRANCE),
					
					entry(new Color(0x808080), PlaceType.SUBMISSION_WALKWAYS),
					entry(new Color(0xc10000), PlaceType.SUBMISSION_TUNNELS),
					
					entry(new Color(0x008080), PlaceType.SUBMISSION_BAT_CAVERNS),
					entry(new Color(0xff9100), PlaceType.SUBMISSION_RAT_WARREN),
					entry(new Color(0xffff00), PlaceType.SUBMISSION_GAMBLING_DEN),
					
					entry(new Color(0xff00ff), PlaceType.SUBMISSION_LILIN_PALACE),
					entry(new Color(0x000000), PlaceType.SUBMISSION_LILIN_PALACE_GATE),
					entry(new Color(0x404040), PlaceType.SUBMISSION_LILIN_PALACE_CAVERN),
					
					entry(new Color(0x004fc9), PlaceType.SUBMISSION_IMP_FORTRESS_ALPHA),
					entry(new Color(0x658cc9), PlaceType.SUBMISSION_IMP_TUNNELS_ALPHA),
					
					entry(new Color(0x6928c9), PlaceType.SUBMISSION_IMP_FORTRESS_DEMON),
					entry(new Color(0x8d65c9), PlaceType.SUBMISSION_IMP_TUNNELS_DEMON),
					
					entry(new Color(0xa228c9), PlaceType.SUBMISSION_IMP_FORTRESS_FEMALES),
					entry(new Color(0xb065c9), PlaceType.SUBMISSION_IMP_TUNNELS_FEMALES),
					
					entry(new Color(0x0096c9), PlaceType.SUBMISSION_IMP_FORTRESS_MALES),
					entry(new Color(0x65b0c9), PlaceType.SUBMISSION_IMP_TUNNELS_MALES))) {
	};


	public static AbstractWorldType LYSSIETH_PALACE = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Lyssieth's Palace",
			PresetColour.BASE_PURPLE,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/submission/lyssiethsPalace/groundFloor.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.LYSSIETH_PALACE_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.LYSSIETH_PALACE_CORRIDOR,new Color(0x404040),PlaceType.LYSSIETH_PALACE_WINDOWS,new Color(0x00ff00),PlaceType.LYSSIETH_PALACE_ENTRANCE,new Color(0xff80ff),PlaceType.LYSSIETH_PALACE_ROOM,new Color(0xff8000),PlaceType.LYSSIETH_PALACE_HALL,new Color(0x8000ff),PlaceType.LYSSIETH_PALACE_OFFICE,new Color(0xff0080),PlaceType.LYSSIETH_PALACE_SIREN_OFFICE,new Color(0xff0000),PlaceType.LYSSIETH_PALACE_STAIRS_1,new Color(0x0000ff),PlaceType.LYSSIETH_PALACE_STAIRS_2)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "You can't have sex while in Lyssieth's Palace!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType IMP_FORTRESS_ALPHA = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Imp Fortress A",
			PresetColour.BASE_CRIMSON,
			false,
			true,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/submission/impFortress/fortress1Map.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.FORTRESS_ALPHA_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.FORTRESS_ALPHA_COURTYARD,new Color(0x00ff00),PlaceType.FORTRESS_ALPHA_ENTRANCE,new Color(0xff8000),PlaceType.FORTRESS_ALPHA_KEEP)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
	};

	public static AbstractWorldType IMP_FORTRESS_DEMON = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Imp Citadel",
			PresetColour.BASE_PURPLE,
			false,
			true,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/submission/impFortress/fortress2Map.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.FORTRESS_DEMON_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.FORTRESS_DEMON_COURTYARD,new Color(0x00ff00),PlaceType.FORTRESS_DEMON_ENTRANCE,new Color(0x65b0c9),PlaceType.FORTRESS_DEMON_WELL,new Color(0xff8000),PlaceType.FORTRESS_DEMON_KEEP,new Color(0x8000ff),PlaceType.FORTRESS_DEMON_CELLS,new Color(0x80ff00),PlaceType.FORTRESS_LAB,new Color(0xff00ff),PlaceType.FORTRESS_DEMON_TREASURY)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
	};

	public static AbstractWorldType IMP_FORTRESS_FEMALES = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Imp Fortress F",
			PresetColour.BASE_PINK,
			false,
			true,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/submission/impFortress/fortress3Map.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.FORTRESS_FEMALES_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.FORTRESS_FEMALES_COURTYARD,new Color(0x00ff00),PlaceType.FORTRESS_FEMALES_ENTRANCE,new Color(0xff8000),PlaceType.FORTRESS_FEMALES_KEEP)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
	};

	public static AbstractWorldType IMP_FORTRESS_MALES = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Imp Fortress M",
			PresetColour.BASE_BLUE,
			false,
			true,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/submission/impFortress/fortress4Map.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.FORTRESS_MALES_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.FORTRESS_MALES_COURTYARD,new Color(0x00ff00),PlaceType.FORTRESS_MALES_ENTRANCE,new Color(0xff8000),PlaceType.FORTRESS_MALES_KEEP)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
	};

	public static AbstractWorldType BAT_CAVERNS = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Bat Caverns",
			PresetColour.BASE_BLACK,
			true,
			true,
			TeleportPermissions.BOTH,
			"/com/lilithsthrone/res/map/submission/batCaverns/batCaverns.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.BAT_CAVERN_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x00ff00),PlaceType.BAT_CAVERN_ENTRANCE,new Color(0x008080),PlaceType.BAT_CAVERN_DARK,new Color(0x808080),PlaceType.BAT_CAVERN_LIGHT,new Color(0x0080ff),PlaceType.BAT_CAVERN_RIVER,new Color(0x40b4ff),PlaceType.BAT_CAVERN_RIVER_CROSSING,new Color(0x004080),PlaceType.BAT_CAVERN_RIVER_END,new Color(0xff80ff),PlaceType.BAT_CAVERN_SLIME_QUEEN_LAIR)) {
	};

	public static AbstractWorldType SLIME_QUEENS_LAIR_GROUND_FLOOR = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Slime Queen's Tower GF",
			PresetColour.BASE_PINK,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/submission/slimeQueensLair/slimeQueensLairGroundFloor.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.SLIME_QUEENS_LAIR_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.SLIME_QUEENS_LAIR_CORRIDOR,new Color(0x00ff00),PlaceType.SLIME_QUEENS_LAIR_ENTRANCE,new Color(0xff0000),PlaceType.SLIME_QUEENS_LAIR_STAIRS_UP,new Color(0xff8000),PlaceType.SLIME_QUEENS_LAIR_STORAGE_VATS,new Color(0x40b4ff),PlaceType.SLIME_QUEENS_LAIR_ROOM,new Color(0xff80ff),PlaceType.SLIME_QUEENS_LAIR_ENTRANCE_GUARDS,new Color(0xffff00),PlaceType.SLIME_QUEENS_LAIR_SLIME_QUEEN)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
	};

	public static AbstractWorldType SLIME_QUEENS_LAIR_FIRST_FLOOR = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Slime Queen's Tower 1F",
			PresetColour.BASE_PINK,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/submission/slimeQueensLair/slimeQueensLairFirstFloor.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.SLIME_QUEENS_LAIR_STAIRS_DOWN, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.SLIME_QUEENS_LAIR_CORRIDOR,new Color(0x00ff00),PlaceType.SLIME_QUEENS_LAIR_STAIRS_DOWN,new Color(0x40b4ff),PlaceType.SLIME_QUEENS_LAIR_ROOM,new Color(0xff00ff),PlaceType.SLIME_QUEENS_LAIR_ROYAL_GUARD,new Color(0xffff00),PlaceType.SLIME_QUEENS_LAIR_SLIME_QUEEN)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
	};

	public static AbstractWorldType GAMBLING_DEN = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Gambling Den",
			PresetColour.BASE_GOLD,
			false,
			false,
			TeleportPermissions.OUTGOING_ONLY,
			"/com/lilithsthrone/res/map/submission/gamblingDen/gamblingDen.png", PlaceType.WORLD_MAP_DOMINION, PlaceType.GAMBLING_DEN_ENTRANCE, Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0x808080),PlaceType.GAMBLING_DEN_CORRIDOR,new Color(0x00ff00),PlaceType.GAMBLING_DEN_ENTRANCE,new Color(0x4bb1d0),PlaceType.GAMBLING_DEN_OFFICE,new Color(0xffff00),PlaceType.GAMBLING_DEN_TRADER,new Color(0x0080ff),PlaceType.GAMBLING_DEN_GAMBLING,new Color(0xff80ff),PlaceType.GAMBLING_DEN_PREGNANCY,new Color(0xff00ff),PlaceType.GAMBLING_DEN_FUTA_PREGNANCY,new Color(0xff8000),PlaceType.GAMBLING_DEN_PREGNANCY_ROULETTE)){
		@Override
		public boolean isRevealedOnStart() {
			return true;
		}
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	public static AbstractWorldType RAT_WARRENS = new AbstractWorldType(WorldRegion.SUBMISSION,
			"Rat Warrens",
			PresetColour.BASE_BROWN,
			false,
			false,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/submission/ratWarrens/ratWarrens.png",
			PlaceType.WORLD_MAP_DOMINION,
			PlaceType.RAT_WARRENS_ENTRANCE, Map.ofEntries(
					entry(new Color(0xFFFFFF), PlaceType.GENERIC_IMPASSABLE),
					entry(new Color(0x808080), PlaceType.RAT_WARRENS_CORRIDOR_LEFT),
					entry(new Color(0xb9b9b9), PlaceType.RAT_WARRENS_CORRIDOR),
					entry(new Color(0x3b3b3b), PlaceType.RAT_WARRENS_CORRIDOR_RIGHT),
					entry(new Color(0x00ff00), PlaceType.RAT_WARRENS_ENTRANCE),

					entry(new Color(0x00ffff), PlaceType.RAT_WARRENS_CHECKPOINT_LEFT),
					entry(new Color(0x80ffff), PlaceType.RAT_WARRENS_CHECKPOINT_RIGHT),
					
					entry(new Color(0xff8080), PlaceType.RAT_WARRENS_DORMITORY_LEFT),
					entry(new Color(0xff8000), PlaceType.RAT_WARRENS_DORMITORY_RIGHT),
					
					entry(new Color(0x0000ff), PlaceType.RAT_WARRENS_DICE_DEN),
					entry(new Color(0xffff00), PlaceType.RAT_WARRENS_MILKING_ROOM),
					entry(new Color(0xffbf00), PlaceType.RAT_WARRENS_MILKING_STORAGE),
					entry(new Color(0x8000ff), PlaceType.RAT_WARRENS_VENGARS_HALL),
					entry(new Color(0x800080), PlaceType.RAT_WARRENS_PRIVATE_BEDCHAMBERS))) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "This isn't a suitable place in which to be having sex!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
    public static AbstractWorldType REBEL_BASE = new AbstractWorldType(WorldRegion.SUBMISSION,
    		"Mysterious Cave",
			PresetColour.BASE_GREY,
			false,
			false,
			TeleportPermissions.NONE,
			"/com/lilithsthrone/res/map/submission/rebelBase/rebelBase.png",
			PlaceType.WORLD_MAP_DOMINION,
			PlaceType.REBEL_BASE_ENTRANCE,
			Map.of(new Color(0xFFFFFF),PlaceType.GENERIC_IMPASSABLE,new Color(0xed1c24),PlaceType.REBEL_BASE_ENTRANCE,new Color(0x22b14c),PlaceType.REBEL_BASE_CORRIDOR,new Color(0xf8941d),PlaceType.REBEL_BASE_SLEEPING_AREA,new Color(0x662d91),PlaceType.REBEL_BASE_COMMON_AREA,new Color(0x6dd0f7),PlaceType.REBEL_BASE_ARMORY,new Color(0x3f48cc),PlaceType.REBEL_BASE_CAVED_IN_ROOM)) {
		@Override
		public String getSexBlockedReason(GameCharacter character) {
			return "A structurally unsound cave is hardly the place for sex!";
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	};
	
	private static List<AbstractWorldType> allWorldTypes = new ArrayList<>();
	private static Map<AbstractWorldType, String> worldToIdMap = new HashMap<>();
	private static Map<String, AbstractWorldType> idToWorldMap = new HashMap<>();

	public static List<AbstractWorldType> getAllWorldTypes() {
		return new ArrayList<>(allWorldTypes);
	}
	
	public static AbstractWorldType getWorldTypeFromId(String id) {
		id.replaceAll("SEWERS", "SUBMISSION");
		if(id.equals("SUPPLIER_DEN")) {
			return TEXTILES_WAREHOUSE;
		}
		id = Util.getClosestStringMatch(id, idToWorldMap.keySet());
		return idToWorldMap.get(id);
	}

	public static String getIdFromWorldType(AbstractWorldType placeType) {
		return worldToIdMap.get(placeType);
	}
	
	static {
		// Modded world types:
		
		Map<String, Map<String, File>> moddedFilesMap = Util.getExternalModFilesById("/maps", null, "worldType");
		for(Entry<String, Map<String, File>> entry : moddedFilesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				try {
					AbstractWorldType worldType = new AbstractWorldType(innerEntry.getValue(), entry.getKey(), true) {};
					allWorldTypes.add(worldType);
					worldToIdMap.put(worldType, innerEntry.getKey());
					idToWorldMap.put(innerEntry.getKey(), worldType);
//					System.out.println("modded WT: "+innerEntry.getKey());
				} catch(Exception ex) {
					System.err.println("Loading modded world type failed at 'WorldType'. File path: "+innerEntry.getValue().getAbsolutePath());
					System.err.println("Actual exception: ");
					ex.printStackTrace(System.err);
				}
			}
		}
		
		// External res world types:
		
		Map<String, Map<String, File>> filesMap = Util.getExternalFilesById("res/maps", null, "worldType");
		for(Entry<String, Map<String, File>> entry : filesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				try {
					AbstractWorldType worldType = new AbstractWorldType(innerEntry.getValue(), entry.getKey(), false) {};
					String id = "innoxia_"+innerEntry.getKey().replace("_worldType", "");
					allWorldTypes.add(worldType);
					worldToIdMap.put(worldType, id);
					idToWorldMap.put(id, worldType);
//					System.out.println("res WT: "+innerEntry.getKey());
				} catch(Exception ex) {
					System.err.println("Loading world type failed at 'WorldType'. File path: "+innerEntry.getValue().getAbsolutePath());
					System.err.println("Actual exception: ");
					ex.printStackTrace(System.err);
				}
			}
		}
		
		// Hard-coded world types (all those up above):
		
		Field[] fields = WorldType.class.getFields();
		
		for(Field f : fields) {
			if(AbstractWorldType.class.isAssignableFrom(f.getType())) {
				AbstractWorldType worldType;
				try {
					worldType = ((AbstractWorldType) f.get(null));

					worldToIdMap.put(worldType, f.getName());
					idToWorldMap.put(f.getName(), worldType);
					allWorldTypes.add(worldType);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
