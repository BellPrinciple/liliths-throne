package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.dialogue.AbstractDialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.inventory.weapon.WeaponType;
import com.lilithsthrone.utils.TreeNode;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

import static com.lilithsthrone.game.character.gender.Gender.getGenderFromUserPreferences;
import static com.lilithsthrone.game.dialogue.DialogueFlagValue.axelExplainedVengar;
import static com.lilithsthrone.game.dialogue.encounters.Encounter.BAT_CAVERN;
import static com.lilithsthrone.game.dialogue.places.submission.BatCaverns.CAVERN_DARK;
import static com.lilithsthrone.game.dialogue.places.submission.BatCaverns.CAVERN_LIGHT;
import static com.lilithsthrone.game.dialogue.places.submission.gamblingDen.RoxysShop.TRADER;
import static com.lilithsthrone.main.Main.game;
import static com.lilithsthrone.utils.Util.random;
import static com.lilithsthrone.utils.colours.PresetColour.GENERIC_BAD;

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

		//TODO in Roxy#dailyUpdate():
		//if(Main.game.getPlayer().isQuestCompleted(QuestLine.FIREBOMBS))
		// this.addWeapon(Main.game.getItemGen().generateWeapon("dsg_hlf_weap_pbomb"), 10, false, false);
		TRADER.addResponse(k,"",Modification::traderResponse);
	}

	@Override
	public void uninstall() {
		var k = Modification.class;
		Encounter.INSANE_SURVIVOR.remove(k);
		BAT_CAVERN.remove(k);
		CAVERN_LIGHT.removeResponse(k);
		CAVERN_DARK.removeResponse(k);
		TRADER.removeResponse(k);
	}

	private static Response searchPassword(AbstractDialogueFlagValue passFound) {
		if(game.getPlayer().getQuest(QuestLine.SIDE)!=Quest.PASSWORD_PART_ONE
				&& game.getPlayer().getQuest(QuestLine.SIDE)!=Quest.PASSWORD_PART_TWO)
			return null;
		if(game.getDialogueFlags().hasFlag(passFound))
			return new Response(
					"Search for password",
					"You've already found the password in this area.",
					null);
		if(random.nextInt(100) > 20 + (game.getPlayer().hasTraitActivated(Perk.OBSERVANT) ? 30 : 0))
			return new Response(
					"Search for password",
					"Peer into the darkness and search harder for the password to the mysterious door.",
					Dialogue.PASSWORD_SEARCH_FAILED);
		if(game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_PART_TWO))
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
		if(!game.isSillyMode()
				|| game.getPlayer().getQuest(QuestLine.SIDE)!=Quest.PASSWORD_PART_ONE
						&& game.getPlayer().getQuest(QuestLine.SIDE)!=Quest.PASSWORD_PART_TWO)
			return null;
		return new Response(
				"I'm a busy [pc.man]!",
				"This is such a waste of time."
				+ "<br/>[style.boldBad(This will skip all content and rewards for the Grave Robbing quest!)]",
				Dialogue.PASSWORD_SILLY);
	}

	private static double chanceDiscover() {
		if(game.getPlayer().isQuestCompleted(QuestLine.SIDE)
				|| game.getPlayer().isQuestFailed(QuestLine.SIDE)
				|| !game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.HANDLE_REFUSED)
				|| !game.getPlayerCell().getPlace().getPlaceType().equals(Place.BAT_CAVERN_DARK))
			return 0;
		// Make sure that the player is at least a distance of 3 tiles from the entrance before encountering the rebel base:
		var playerLocation = game.getPlayer().getLocation();
		var entranceLocation = game.getWorlds().get(WorldType.BAT_CAVERNS).getCell(Place.BAT_CAVERN_ENTRANCE).getLocation();
		int distanceFromEntrance = (int)playerLocation.getDistanceToVector(entranceLocation);
		return distanceFromEntrance<3 ? 0 : game.getPlayer().hasTraitActivated(Perk.OBSERVANT)?10:5;
	}

	private static double chancePassword(boolean two) {
		if(game.getPlayer().isQuestCompleted(QuestLine.SIDE)
				|| game.getPlayer().isQuestFailed(QuestLine.SIDE)
				|| !game.getPlayer().isQuestProgressGreaterThan(QuestLine.SIDE,Quest.HANDLE_REFUSED)
				|| !game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_COMPLETE))
			return 0;
		var playerPlaceType = game.getPlayerCell().getPlace().getPlaceType();
		// Limit encounters for passwords to dark, light, and HLF base entrance tiles only:
		if(playerPlaceType.equals(Place.BAT_CAVERN_DARK)
				|| playerPlaceType.equals(Place.BAT_CAVERN_LIGHT)
				|| playerPlaceType.equals(Place.ENTRANCE_EXTERIOR))
			return 0;
		// The player needs to find one password from a dark tile and one from a light tile, so if already found the password in their tile, do not enable Encounter
		if(playerPlaceType.equals(Place.BAT_CAVERN_DARK)
						&& game.getDialogueFlags().hasFlag(Dialogue.darkPassFound)
				|| playerPlaceType.equals(Place.BAT_CAVERN_LIGHT)
						&& game.getDialogueFlags().hasFlag(Dialogue.lightPassFound))
			return 0;
		if(two == game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_PART_TWO))
			return 0;
		return game.getPlayer().hasTraitActivated(Perk.OBSERVANT) ? 5 : 1;
	}

	private static double chanceInsaneSurvivor() {
		return game.getPlayer().hasQuestInLine(QuestLine.SIDE,Quest.EXPLORATION)
				&& !game.getDialogueFlags().values.contains(Dialogue.insaneSurvivorEncountered)
		? 100
		: 0;
	}

	private static DialogueNode initInsaneSurvivor() {
		var c = new RebelBaseInsaneSurvivor(getGenderFromUserPreferences(false,false));
		game.setActiveNPC(c);
		try {
			game.addNPC(c,false);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return c.getEncounterDialogue();
	}

	private static Response traderResponse() {
		if(!game.getDialogueFlags().values.contains(axelExplainedVengar)
				|| !game.getPlayer().hasQuest(QuestLine.FIREBOMBS)
				|| game.getPlayer().isQuestCompleted(QuestLine.FIREBOMBS)
				|| game.getPlayer().isQuestFailed(QuestLine.FIREBOMBS))
			return null;
		if(!game.getPlayer().isQuestProgressLessThan(QuestLine.FIREBOMBS,Quest.FIREBOMBS_FINISH)) {
			if(!game.getPlayer().isQuestProgressGreaterThan(QuestLine.FIREBOMBS,Quest.FIREBOMBS_START))
				return null;
			// Roxy needs 2 days to get firebombs
			if((game.getMinutesPassed() - game.getDialogueFlags().getSavedLong(ROXY_TIMER)) < 2880)
				return new Response("Firebombs", "Roxy hasn't had enough time to get more firebombs yet.", null);
			return new Response("Firebombs", "It's been two days since you asked Roxy about getting more firebombs, better check in.", Dialogue.FIREBOMBS_COMPLETE);
		}
		if(game.getPlayer().hasWeaponType(WeaponType.getWeaponTypeFromId("dsg_hlf_weap_pbomb"), true))
			return new Response("Firebombs", "As you don't have any firebombs on you, you're going to have to try describing them to Roxy in the hopes that she can find someone to replicate them. [style.boldBad(It would probably be best to have a physical example though.)]", Dialogue.FIREBOMBS_FAILED) {
				@Override
				public Colour getHighlightColour() {
					return GENERIC_BAD;
				}
			};
		return new Response("Firebombs", "Show Roxy the firebombs you recovered to see if she has a way of getting or making more.<br/>[style.boldBad(You will lose one firebomb.)] ", Dialogue.FIREBOMBS) {
			@Override
			public void effects() {
				game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QuestLine.FIREBOMBS,Quest.FIREBOMBS_FINISH));
				game.getDialogueFlags().setSavedLong(ROXY_TIMER, game.getMinutesPassed());
				// Shuffle at least one instance of the arcane firebomb into the player's inventory if they've got one equipped but none in their inventory
				if(!game.getPlayer().hasWeaponType(WeaponType.getWeaponTypeFromId("dsg_hlf_weap_pbomb"), false)) {
					int armRow = 0;
					boolean fireBombShuffled = false;
					for(var weapon : game.getPlayer().getMainWeaponArray()) {
						if(weapon.getWeaponType().equals(WeaponType.getWeaponTypeFromId("dsg_hlf_weap_pbomb"))) {
							game.getPlayer().unequipMainWeapon(armRow, false, false);
							break;
						}
						armRow++;
					}
					if(!fireBombShuffled) {
						for(var weapon : game.getPlayer().getOffhandWeaponArray()) {
							if(weapon.getWeaponType().equals(WeaponType.getWeaponTypeFromId("dsg_hlf_weap_pbomb"))) {
								game.getPlayer().unequipOffhandWeapon(armRow, false, false);
								break;
							}
							armRow++;
						}
					}
				}
				game.getPlayer().removeWeapon(game.getItemGen().generateWeapon(WeaponType.getWeaponTypeFromId("dsg_hlf_weap_pbomb")));
			}
		};
	}

	private static final String ROXY_TIMER = "rebel_base_roxy_timer";
}
