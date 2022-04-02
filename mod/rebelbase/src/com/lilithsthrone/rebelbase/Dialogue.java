package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.Scene;
import com.lilithsthrone.game.dialogue.AbstractDialogueFlagValue;
import com.lilithsthrone.game.dialogue.places.submission.BatCaverns;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;

import static com.lilithsthrone.world.WorldType.BAT_CAVERNS;
import static com.lilithsthrone.world.places.PlaceType.BAT_CAVERN_DARK;
import static com.lilithsthrone.world.places.PlaceType.BAT_CAVERN_LIGHT;

import java.util.List;

public enum Dialogue implements Scene {
	ENTRANCE_HANDLE,
	DOOR_OPENED,
	ENTRANCE_EXTERIOR,
	DISCOVERED,
	DOOR_NO_PASS,
	PASSWORD_ONE,
	PASSWORD_TWO,
	PASSWORD_SEARCH_FAILED,
	PASSWORD_SILLY,
	;

	@Override
	public String getAuthor() {
		return "DSG";
	}

	@Override
	public String getLabel() {
		switch(this) {
		case ENTRANCE_HANDLE:
		case DISCOVERED:
		case DOOR_NO_PASS:
			return "Strange Handle";
		case DOOR_OPENED:
			return "Hidden Doorway";
		case ENTRANCE_EXTERIOR:
			return "Hidden Cave";
		case PASSWORD_ONE:
			return "Journal Fragment";
		case PASSWORD_TWO:
			return "Another Journal Fragment";
		case PASSWORD_SEARCH_FAILED:
			return "No Luck";
		case PASSWORD_SILLY:
			return "The Value of Time";
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getContent() {
		switch(this) {
		case ENTRANCE_HANDLE:
			return BatCaverns.CAVERN_DARK.getContent()
			+ UtilText.parseFromXMLFile("places/submission/batCaverns","REBEL_BASE_ENTRANCE_HANDLE");
		case DOOR_OPENED:
			return UtilText.parseFromXMLFile("places/submission/batCaverns","REBEL_BASE_DOOR_OPENED");
		case ENTRANCE_EXTERIOR:
			return BatCaverns.CAVERN_DARK.getContent()
			+ UtilText.parseFromXMLFile(
					"places/submission/batCaverns",
					Main.game.getPlayer().isQuestFailed(QuestLine.SIDE)
							|| Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE)
					? "REBEL_BASE_ENTRANCE_EXTERIOR_COLLAPSED"
					: "REBEL_BASE_ENTRANCE_EXTERIOR");
		case DISCOVERED:
			return UtilText.parseFromXMLFile("places/submission/batCaverns","REBEL_BASE_DISCOVERED");
		case DOOR_NO_PASS:
			return UtilText.parseFromXMLFile("places/submission/batCaverns","REBEL_BASE_DOOR_NO_PASS");
		case PASSWORD_ONE:
			return UtilText.parseFromXMLFile(
					"places/submission/batCaverns",
					Main.game.getPlayer().getLocationPlace().getPlaceType()==BAT_CAVERN_LIGHT
					? "REBEL_BASE_DOOR_PASS_LIGHT"
					: "REBEL_BASE_DOOR_PASS_DARK")
			+ UtilText.parseFromXMLFile("places/submission/batCaverns","REBEL_BASE_DOOR_PASS_ONE");
		case PASSWORD_TWO:
			return UtilText.parseFromXMLFile(
					"places/submission/batCaverns",
					Main.game.getPlayer().getLocationPlace().getPlaceType()==BAT_CAVERN_LIGHT
					? "REBEL_BASE_DOOR_PASS_LIGHT"
					: "REBEL_BASE_DOOR_PASS_DARK")
			+ UtilText.parseFromXMLFile("places/submission/batCaverns","REBEL_BASE_DOOR_PASS_TWO");
		case PASSWORD_SEARCH_FAILED:
			return UtilText.parseFromXMLFile("places/submission/batCaverns","REBEL_BASE_DOOR_PASS_SEARCH_FAIL");
		case PASSWORD_SILLY:
			return UtilText.parseFromXMLFile("places/submission/batCaverns","REBEL_BASE_DOOR_PASS_SILLY");
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ResponseTab> getResponses() {
		switch(this) {
		case ENTRANCE_HANDLE:
		{
			var r = BatCaverns.CAVERN_DARK.getResponses();
			var t = r.get(0);
			if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_PART_ONE)) {
				r.set(0,new ResponseTab(t.title,null,
						new Response("Pull the handle","What could possibly go wrong?",Dialogue.DOOR_NO_PASS)));
				r.get(0).response.addAll(t.response.subList(1,t.response.size()));
			}
			else if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_PART_TWO)) {
				r.set(0,new ResponseTab(t.title,null,
						new Response("Pull the handle", "The handle won't budge. Looks like you really do need the password.", null)));
				r.get(0).response.addAll(t.response.subList(1,t.response.size()));
			}
			else if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_COMPLETE)) {
				r.set(0,new ResponseTab(t.title,null,
						new Response("Pull the handle", "You don't have the complete password!", null)));
				r.get(0).response.addAll(t.response.subList(1,t.response.size()));
			}
			else if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.EXPLORATION)) {
				r.set(0,new ResponseTab(t.title,null,
						new Response(
								"Pull the handle",
								"You have the complete password now and can therefore try to pull the handle again... if you really want to.",
								DOOR_OPENED) {
							@Override
							public void effects() {
								Main.game.getPlayerCell().getPlace().setPlaceType(Place.ENTRANCE_EXTERIOR);
								Main.game.getPlayerCell().getPlace().setName(Place.ENTRANCE_EXTERIOR.getName());
							}
						}));
				r.get(0).response.addAll(t.response.subList(1,t.response.size()));
			}
			return r;
		}
		case DOOR_OPENED:
			return List.of(new ResponseTab("",null,
					new Response(
						"Enter",
						"This cave is not a natural formation. Someone built it, so it must lead somewhere.",
						Place.ENTRANCE.getDialogue(false)) {
						@Override
						public void effects() {
							Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.EXPLORATION));
							Main.game.getPlayer().setLocation(World.WORLD,Place.ENTRANCE);
						}
					},
					Response.back("Don't enter", "No telling what's in that cave...")));
		case ENTRANCE_EXTERIOR:
		if(!Main.game.getPlayer().isQuestFailed(QuestLine.SIDE)
				&& !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.SIDE,Quest.EXPLORATION)) {
			return List.of(new ResponseTab("",null,new Response(
					"Enter",
					"This cave is not a natural formation. Someone built it, so it must lead somewhere.",
					Place.ENTRANCE.getDialogue(false)) {
				@Override
				public void effects() {
					Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.EXPLORATION));
					Main.game.getPlayer().setLocation(World.WORLD,Place.ENTRANCE);
				}
			}));
		}
		return BatCaverns.CAVERN_DARK.getResponses();
		case DISCOVERED:
		return List.of(new ResponseTab("",null,
				new Response("Pull the handle","What could possibly go wrong?",DOOR_NO_PASS),
				new Response("Leave it alone","Nothing good ever came of pulling strange handles in caves.",Place.ENTRANCE_HANDLE.getDialogue(false))));
		case DOOR_NO_PASS:
		return List.of(new ResponseTab("",null,
				new Response("Pull harder","The handle won't budge. Looks like you really do need the password.",null),
				new Response("Leave it alone","Go look for the password instead.",Place.ENTRANCE_HANDLE.getDialogue(false))));
		case PASSWORD_ONE:
		return List.of(new ResponseTab("",null,
				Response.back("Continue","This is only one part of the password, you need to find the other")));
		case PASSWORD_TWO:
		return List.of(new ResponseTab("",null,
				Response.back("Continue","You've found both parts of the password, you can head back to the mysterious handle when you're ready.")));
		case PASSWORD_SILLY:
		return List.of(new ResponseTab("",null,
				new Response.Back("Continue","You win. Hooray.") {
					@Override
					public void effects() {
						Main.game.getWorlds().get(BAT_CAVERNS).getCell(Place.ENTRANCE_HANDLE).getPlace().setPlaceType(Place.ENTRANCE_EXTERIOR);
						Main.game.getWorlds().get(BAT_CAVERNS).getCell(Place.ENTRANCE_EXTERIOR).getPlace().setName(Place.ENTRANCE_EXTERIOR.getName());
					}
				}));
		}
		return List.of(new ResponseTab(""));
	}

	@Override
	public void applyPreParsingEffects() {
		switch(this) {
		case DOOR_OPENED:
			Main.game.getPlayerCell().getPlace().setPlaceType(Place.ENTRANCE_EXTERIOR);
			Main.game.getPlayerCell().getPlace().setName(Place.ENTRANCE_EXTERIOR.getName());
			break;
		case DOOR_NO_PASS:
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.PASSWORD_PART_ONE));
			break;
		case DISCOVERED:
			Main.game.getPlayerCell().getPlace().setPlaceType(Place.ENTRANCE_HANDLE);
			Main.game.getPlayerCell().getPlace().setName(Place.ENTRANCE_HANDLE.getName());
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.HANDLE_REFUSED));
			break;
		case PASSWORD_ONE:
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.PASSWORD_PART_TWO));
			Main.game.getDialogueFlags().setFlag(
					Main.game.getPlayer().getLocationPlace().getPlaceType()==BAT_CAVERN_DARK
							|| Main.game.getPlayer().getLocationPlace().getPlaceType()==Place.ENTRANCE_HANDLE
					? darkPassFound
					: lightPassFound,
					true);
			break;
		case PASSWORD_TWO:
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.PASSWORD_COMPLETE));
			break;
		case PASSWORD_SILLY:
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.SIDE_UTIL_COMPLETE));
			break;
		}
	}

	@Override
	public int getSecondsPassed() {
		switch(this) {
		case PASSWORD_ONE:
		case PASSWORD_TWO:
		case PASSWORD_SEARCH_FAILED:
		case PASSWORD_SILLY:
			return 60;
		default:
			return 30;
		}
	}

	@Override
	public boolean isTravelDisabled() {
		switch(this) {
		case DISCOVERED:
		case DOOR_NO_PASS:
		case PASSWORD_ONE:
		case PASSWORD_TWO:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean isContinuesDialogue() {
		switch(this) {
		case DOOR_NO_PASS:
		case PASSWORD_SEARCH_FAILED:
			return true;
		default:
			return false;
		}
	}

	static final AbstractDialogueFlagValue darkPassFound = new AbstractDialogueFlagValue();
	static final AbstractDialogueFlagValue lightPassFound = new AbstractDialogueFlagValue();
	static final AbstractDialogueFlagValue insaneSurvivorEncountered = new AbstractDialogueFlagValue();
	static final AbstractDialogueFlagValue elleCostumeEncountered = new AbstractDialogueFlagValue();
}
