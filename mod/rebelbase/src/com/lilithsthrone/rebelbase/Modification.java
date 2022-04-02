package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.dialogue.AbstractDialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.TreeNode;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Vector2i;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

import static com.lilithsthrone.game.dialogue.encounters.Encounter.BAT_CAVERN;
import static com.lilithsthrone.game.dialogue.places.submission.BatCaverns.CAVERN_DARK;
import static com.lilithsthrone.game.dialogue.places.submission.BatCaverns.CAVERN_LIGHT;

/**
 * Somewhere in the bat caverns beneath Dominion you may find a hidden vault,
 * a remainder of the world that once was.
 * @version 0.1
 */
public class Modification extends com.lilithsthrone.game.Modification {

	@Override
	public void install() {
		var k = Modification.class;
		CAVERN_DARK.addResponse(k,"",()->searchPassword(Dialogue.darkPassFound));
		CAVERN_DARK.addResponse(k,"",Modification::sillyPassword);
		CAVERN_LIGHT.addResponse(k,"",()->searchPassword(Dialogue.lightPassFound));
		CAVERN_LIGHT.addResponse(k,"",Modification::sillyPassword);
		BAT_CAVERN.add(k,Modification::chanceDiscover,()->Dialogue.DISCOVERED,false);
		BAT_CAVERN.add(k,()->chancePassword(false),()->Dialogue.PASSWORD_ONE,false);
		BAT_CAVERN.add(k,()->chancePassword(true),()->Dialogue.PASSWORD_TWO,false);
		Encounter.INSANE_SURVIVOR.add(k,Modification::chanceInsaneSurvivor,Modification::initInsaneSurvivor,false);
		for(var w: World.values())
			WorldType.table.add(w.getId(),w);
		for(var p: Place.values())
			PlaceType.table.add(p.getId(),p);
		for(var q: QuestLine.values())
			com.lilithsthrone.game.character.quests.QuestLine.table.add(q.getId(),q);

		var node1 = new TreeNode<com.lilithsthrone.game.character.quests.Quest>(Quest.HANDLE_REFUSED);
		QuestLine.SIDE.questTree.addChild(node1);
		var node2 = new TreeNode<com.lilithsthrone.game.character.quests.Quest>(Quest.PASSWORD_PART_ONE);
		node1.addChild(node2);
		node1 = new TreeNode<>(Quest.PASSWORD_PART_TWO);
		node2.addChild(node1);
		node2 = new TreeNode<>(Quest.PASSWORD_COMPLETE);
		node1.addChild(node2);
		node1 = new TreeNode<>(Quest.EXPLORATION);
		node2.addChild(node1);

		var nodeBranchA = new TreeNode<com.lilithsthrone.game.character.quests.Quest>(Quest.ESCAPE);
		node1.addChild(nodeBranchA);
		var nodeBranchB = new TreeNode<com.lilithsthrone.game.character.quests.Quest>(Quest.FAILED);
		node1.addChild(nodeBranchB);
		node2 = new TreeNode<>(Quest.SIDE_UTIL_COMPLETE);
		nodeBranchA.addChild(node2);

		node1 = new TreeNode<>(Quest.FIREBOMBS_START);
		QuestLine.FIREBOMBS.questTree.addChild(node1);
		nodeBranchA = new TreeNode<>(Quest.FIREBOMBS_FINISH);
		node1.addChild(nodeBranchA);
		nodeBranchB = new TreeNode<>(Quest.FIREBOMBS_FAILED);
		node1.addChild(nodeBranchB);
		node1 = new TreeNode<>(Quest.SIDE_UTIL_COMPLETE);
		nodeBranchA.addChild(node1);
	}

	@Override
	public void uninstall() {
		var k = Modification.class;
		Encounter.INSANE_SURVIVOR.remove(k);
		BAT_CAVERN.remove(k);
		CAVERN_LIGHT.removeResponse(k);
		CAVERN_DARK.removeResponse(k);
	}

	private static Response searchPassword(AbstractDialogueFlagValue passFound) {
		if(Main.game.getPlayer().getQuest(QuestLine.SIDE)!=Quest.PASSWORD_PART_ONE
				&& Main.game.getPlayer().getQuest(QuestLine.SIDE)!=Quest.PASSWORD_PART_TWO)
			return null;
		if(Main.game.getDialogueFlags().hasFlag(passFound))
			return new Response(
					"Search for password",
					"You've already found the password in this area.",
					null);
		if(Util.random.nextInt(100) > 20 + (Main.game.getPlayer().hasTraitActivated(Perk.OBSERVANT) ? 30 : 0))
			return new Response(
					"Search for password",
					"Peer into the darkness and search harder for the password to the mysterious door.",
					Dialogue.PASSWORD_SEARCH_FAILED);
		if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_PART_TWO))
			return new Response(
					"Search for password",
					"Peer into the darkness and search harder for the password to the mysterious door.",
					Dialogue.PASSWORD_ONE);
		return new Response(
				"Search for password",
				"Peer into the darkness and search harder for the rest of the password to the mysterious door.",
				Dialogue.PASSWORD_TWO);
	}

	private static Response sillyPassword() {
		if(!Main.game.isSillyMode()
				|| Main.game.getPlayer().getQuest(QuestLine.SIDE)!=Quest.PASSWORD_PART_ONE
						&& Main.game.getPlayer().getQuest(QuestLine.SIDE)!=Quest.PASSWORD_PART_TWO)
			return null;
		return new Response(
				"I'm a busy [pc.man]!",
				"This is such a waste of time."
				+ "<br/>[style.boldBad(This will skip all content and rewards for the Grave Robbing quest!)]",
				Dialogue.PASSWORD_SILLY);
	}

	private static double chanceDiscover() {
		if(Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE)
				|| Main.game.getPlayer().isQuestFailed(QuestLine.SIDE)
				|| !Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.HANDLE_REFUSED)
				|| !Main.game.getPlayerCell().getPlace().getPlaceType().equals(Place.BAT_CAVERN_DARK))
			return 0;
		// Make sure that the player is at least a distance of 3 tiles from the entrance before encountering the rebel base:
		Vector2i playerLocation = Main.game.getPlayer().getLocation();
		Vector2i entranceLocation = Main.game.getWorlds().get(WorldType.BAT_CAVERNS).getCell(Place.BAT_CAVERN_ENTRANCE).getLocation();
		int distanceFromEntrance = (int)playerLocation.getDistanceToVector(entranceLocation);
		return distanceFromEntrance<3 ? 0 : Main.game.getPlayer().hasTraitActivated(Perk.OBSERVANT)?10:5;
	}

	private static double chancePassword(boolean two) {
		if(Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE)
				|| Main.game.getPlayer().isQuestFailed(QuestLine.SIDE)
				|| !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.SIDE,Quest.HANDLE_REFUSED)
				|| !Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_COMPLETE))
			return 0;
		var playerPlaceType = Main.game.getPlayerCell().getPlace().getPlaceType();
		// Limit encounters for passwords to dark, light, and HLF base entrance tiles only:
		if(playerPlaceType.equals(Place.BAT_CAVERN_DARK)
				|| playerPlaceType.equals(Place.BAT_CAVERN_LIGHT)
				|| playerPlaceType.equals(Place.ENTRANCE_EXTERIOR))
			return 0;
		// The player needs to find one password from a dark tile and one from a light tile, so if already found the password in their tile, do not enable Encounter
		if(playerPlaceType.equals(Place.BAT_CAVERN_DARK)
						&& Main.game.getDialogueFlags().hasFlag(Dialogue.darkPassFound)
				|| playerPlaceType.equals(Place.BAT_CAVERN_LIGHT)
						&& Main.game.getDialogueFlags().hasFlag(Dialogue.lightPassFound))
			return 0;
		if(two == Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_PART_TWO))
			return 0;
		return Main.game.getPlayer().hasTraitActivated(Perk.OBSERVANT) ? 5 : 1;
	}

	private static double chanceInsaneSurvivor() {
		return Main.game.getPlayer().hasQuestInLine(QuestLine.SIDE,Quest.EXPLORATION)
				&& !Main.game.getDialogueFlags().values.contains(Dialogue.insaneSurvivorEncountered)
		? 100
		: 0;
	}

	private static DialogueNode initInsaneSurvivor() {
		var c = new RebelBaseInsaneSurvivor(Gender.getGenderFromUserPreferences(false,false));
		Main.game.setActiveNPC(c);
		try {
			Main.game.addNPC(c,false);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return c.getEncounterDialogue();
	}
}
