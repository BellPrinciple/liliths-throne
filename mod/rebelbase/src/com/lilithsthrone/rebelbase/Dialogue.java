package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.Scene;
import com.lilithsthrone.game.character.PlayerCharacter;
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
		PlayerCharacter pc = Main.game.getPlayer();
		switch(this) {
		case ENTRANCE_HANDLE:
			return BatCaverns.CAVERN_DARK.getContent()
			+ "<p>"
			+ "Once again you find yourself passing by the wall onto which the strange door handle is attached."
			+ " If you didn't already know it was there, it'd be almost impossible to find, as it's only given away by its barely visible lichen-lit shadow."
			+ (pc.isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_COMPLETE)
			? " As you're not yet in possession of the required password, trying to pull the handle again will undoubtedly prove to be a fruitless endeavour..."
			: " As you're now in possession of the password, you could try pulling the handle again...")
			+ "</p>";
		case DOOR_OPENED:
			return UtilText.parse("<p>"
			+ "Armed with the complete password, you [pc.step] forwards and grab the handle once more."
			+ " Just as before, the series of glowing letters light up across the wall in front of you, spelling out, [style.glowBlueLight('Fiat Justitia?')]"
			+ "</p>"
			+ "<p>"
			+ (pc.isMute()
			? "Being unable to talk, you trace one of your [pc.fingers] over the wall so as to spell out the words 'Ruat Caelum'."
			: "[pc.speech(Ruat Caelum,)] you answer"
			+ (pc.isConfident() || pc.isBrave()
			? " in your supremely-confident voice."
			: pc.isCowardly()
			? " in a cowardly tone."
			: " in the most confident voice you can muster."))
			+ " The handle suddenly loosens and you pull it with all your might."
			+ " The ground starts to vibrate as you pull, and before you know it a section of the cavern wall has swung open, revealing the entrance to another cave!"
			+ " You can just about see some clusters of glowing mushrooms inside, but nothing else."
			+ "</p>");
		case ENTRANCE_EXTERIOR:
			return BatCaverns.CAVERN_DARK.getContent()
			+	"<p>"
			+ (pc.isQuestFailed(QuestLine.SIDE)
						|| pc.isQuestCompleted(QuestLine.SIDE)
				? "Once again you find yourself passing by the caved-in entrance to the once-hidden cave."
				+ " As there's no way back inside without digging through several hundred tonnes of rock, this section of the Bat Caverns is no longer any more interesting than any other."
				: "Once again you find yourself passing by the entrance to the once-hidden cave."
				+ " Nobody seems to have passed by this way since you were last here, or if they did then they decided not to explore the cave,"
				+ " as by looking through the still-open doorway you can just about see a layer of completely undisturbed glowing mushrooms inside, but nothing else.")
			+ "</p>";
		case DISCOVERED:
			return UtilText.parse("<p>"
			+ (pc.hasLegs()
			? "With the weight of each of your footsteps"
			: "With the weight of your [pc.legs]")
			+ " causing the carpet of bioluminescent lichen to light up and just about illuminate your way, you continue [pc.walking] through the otherwise dark caverns."
			+ " By a stroke of exceptional luck, as you pass by what at first appears to be yet another featureless cave wall,"
			+ " you just so happen to notice a sliver of a shadow that's being cast onto it by the dim light of the surrounding bioluminescent fungi."
			+ " While a shadow ordinarily wouldn't be enough to catch your interest, this one has a suspiciously straight edge to it."
			+ " With your curiosity piqued, you take a moment to investigate,"
			+ " and after approaching it you can see that this shadow is being cast by a well-hidden door handle that's just barely jutting out from the rock."
			+ "</p>"
			+ "<p>"
			+ "Between the dim light and the thick layer of dust on it, you can't tell how old this handle is or how long it's been here."
			+ " Briefly glancing around, you can see no obvious seams or hinges that might suggest the presence of a door."
			+ (pc.isCowardly()
			? " Your cowardly mind immediately jumps to the conclusion that this handle might activate some sort of trap."
			+ " Nervously gulping, you fret to yourself about whether or not to try pulling it..."
			: pc.isBrave()
			? " Although a part of you recognises the fact that this handle might activate some sort of trap, you're far too brave to let such a possibility deter you from pulling it, aren't you?"
			: " Although this handle might activate some sort of trap, you're not sure whether you can leave such an extraordinary find behind without at least trying to pull it...")
			+ "</p>");
		case DOOR_NO_PASS:
			return UtilText.parse("<p>"
			+ "Taking your chances, you grab the handle."
			+ " To your surprise, the moment in which you make contact with what turns out to be cold metal,"
			+ " a series of glowing, light-blue letters materialise across the cavern wall before you, spelling out the question [style.glowBlueLight('Fiat Justitia?')]"
			+ "</p>"
			+ "<p>"
			+ "Steeling yourself for whatever might happen next, you proceed to give the handle an experimental tug, but much to your disappointment it remains fixed in place."
			+ " It looks like the wall wants an answer to its question, and since you're not sure what the correct response would be there's nothing more that you can do at the moment."
			+ " Letting go of the handle, you watch as the glowing letters rapidly fade away and return to their invisible, dormant state."
			+ "</p>"
			+ "<p>"
			+ "[pc.Stepping] away from the handle, you think to yourself that surely by carrying out a thorough search of the vicinity will reveal some clues..."
			+ "</p>");
		case PASSWORD_ONE:
		case PASSWORD_TWO:
			return "<p>"
			+ UtilText.parse(
					pc.getLocationPlace().getPlaceType()==BAT_CAVERN_LIGHT
					? "[pc.Walking] through the bioluminescent forest, you find yourself passing by an absolutely gigantic specimen of mushroom-tree,"
					+ " as as you do so, you just so happen to see something glinting by its base."
					+ " Taking a closer look, you push a small patch of mushrooms aside to see that the source of the reflected light is a piece of laminated paper."
					: "As you carefully navigate the myriad obstacles offered by the twisting passages of the cavern, you suddenly almost slip on something beneath your feet."
					+ (pc.isFeminine() ? " Sighing" : " Grumbling")
					+ " to yourself about the responsible disposal of slippery objects in a damp cave, you find the cause of your near-faceplant to be a piece of laminated paper.")
			+ "</p>"
			+ "<p>"
			+ UtilText.parse(this==PASSWORD_ONE
				? "Picking up the paper to take a closer look, you see that it appears be a page from a journal of some sort that someone laminated after it had already been torn out."
				+ " Even more strangely, someone tore it in half after going through all the trouble of laminating it."
				+ " Water has gotten inside the seal and rendered most of the page unreadable but you can still make out an entry:"
				+ "</p>"
				+ "<p align=right>"
				+ "<i>"
				+ "1st July 1990"
				+ "</i>"
				+ "</p>"
				+ "<p>"
				+ "<i>"
				+ "Under Astrea's command, we have successfully broken through into the Bat Caverns."
				+ " At last, a way into the wretched heart of Dominion!"
				+ " And not a moment too soon, if news from the other fronts is to be believed."
				+ " A small hideout hidden in the cave wall will provide us a staging area for the operations to come, and in any case, the cool damp is a welcome relief from the sweltering heat above."
				+ " The door has been enchanted to only open for those who know the password:</br><center>RUAT C</center>"
				+ "</i>"
				+ "</p>"
				+ "<p>"
				+ "Annoyingly, the rest of the password has been torn away. You'll have to find the half of the page which matches up with this one in order to complete the password."
				+ (Main.game.getDialogueFlags().hasFlag(darkPassFound)
					? " You get the feeling that this missing half won't be found in these dark tunnels."
					+ " Perhaps you should head into another part of the Bat Caverns and look in a place that's a little brighter?"
					: " You get the feeling that this missing half won't be found in this bioluminescent forest."
					+ " Perhaps you should head into another part of the Bat Caverns and look in a place that's a little darker?")
				: "Immediately recognising it as the other half of the torn journal page, you thank your lucky stars for this miraculously good stroke of fortune and quickly pick it up."
				+ " Like its counterpart, this laminated sheet has been heavily damaged and is also barely legible,"
				+ " yet after brushing off the layer of muck which was obscuring most of the text, you breathe a sigh of relief as you see that the second part of the password can still be read:"
				+ "</p>"
				+ "<p align=center>"
				+ "<i>"
				+ "AELUM"
				+ "</i>"
				+ "</p>"
				+ "<p align=right>"
				+ "<i>"
				+ "3rd July 1990"
				+ "</i>"
				+ "</p>"
				+ "<p>"
				+ "<i>"
				+ "Damn Enforcers were waiting for us in the canals!"
				+ " And we've lost one of our lead fighters."
				+ " She didn't know the password or the exact location of the hideout but it's obvious she broke under interrogation."
				+ " We found footprints that weren't ours outside this morning."
				+ " This is going to be much harder than we imagined."
				+ "</i>"
				+ "</p>"
				+ "<p>"
				+ "Putting together the two pages, you see that the password is <b>RUAT CAELUM</b>.")
				+ "</p>";
		case PASSWORD_SEARCH_FAILED:
			return "<p>"
			+ "You carefully examine every nook and cranny of the area around you."
			+ " Despite your best efforts, your search turns up nothing."
			+ "</p>";
		case PASSWORD_SILLY:
			return UtilText.parse("<p>"
			+ "You rage at having been given yet another pointless side quest."
			+ " [pc.speech(Can't they see I have things to see and people to do?)] you rant at nobody in particular,"
			+ " [pc.speech(I bet there's not even anybody to fuck behind that stupid door!)]"
			+ "</p>"
			+ "<p>"
			+ "As if in reply to your humble cry of anguish, a gust of wind howls through the caverns."
			+ " You suddenly feel more accomplished but also terribly unsatisfied."
			+ "</p>");
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
