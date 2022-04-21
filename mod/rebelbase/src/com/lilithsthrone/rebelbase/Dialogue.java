package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.Scene;
import com.lilithsthrone.game.dialogue.AbstractDialogueFlagValue;
import com.lilithsthrone.game.dialogue.responses.Response;

import static com.lilithsthrone.game.character.persona.Occupation.SOLDIER;
import static com.lilithsthrone.game.dialogue.DialogueFlagValue.axelExplainedVengar;
import static com.lilithsthrone.game.dialogue.places.submission.BatCaverns.CAVERN_DARK;
import static com.lilithsthrone.game.dialogue.places.submission.gamblingDen.RoxysShop.TRADER;
import static com.lilithsthrone.game.dialogue.utils.UtilText.parse;
import static com.lilithsthrone.main.Main.game;
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
	ENTRANCE,
	COLLAPSE,
	ESCAPE,
	CORRIDOR,
	SLEEPING_AREA,
	SLEEPING_AREA_JOURNAL_OPEN,
	SLEEPING_AREA_SEARCHED,
	COMMON_AREA,
	COMMON_AREA_CACHE_OPEN,
	COMMON_AREA_SEARCHED,
	ARMORY,
	ARMORY_CACHE_OPEN,
	ARMORY_SEARCHED,
	CAVED_IN_ROOM,
	FIREBOMBS,
	FIREBOMBS_COMPLETE,
	FIREBOMBS_FAILED,
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
		case ENTRANCE:
			return "Cave Entrance";
		case COLLAPSE:
			return "Uh oh...";
		case ESCAPE:
			return "";
		case CORRIDOR:
			return "Artificial Cave";
		case SLEEPING_AREA:
		case SLEEPING_AREA_SEARCHED:
			return "Abandoned Sleeping Area";
		case SLEEPING_AREA_JOURNAL_OPEN:
			return "Crumbling Journal";
		case COMMON_AREA:
		case COMMON_AREA_CACHE_OPEN:
		case COMMON_AREA_SEARCHED:
			return "Abandoned Common Area";
		case ARMORY:
		case ARMORY_CACHE_OPEN:
		case ARMORY_SEARCHED:
			return "Partly Caved-in Room";
		case CAVED_IN_ROOM:
			return "Caved-in Room";
		case FIREBOMBS:
		case FIREBOMBS_COMPLETE:
		case FIREBOMBS_FAILED:
			return "Roxy's Fun Box";
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getContent() {
		var pc = game.getPlayer();
		switch(this) {
		case ENTRANCE_HANDLE:
			return CAVERN_DARK.getContent()
			+ "<p>"
			+ "Once again you find yourself passing by the wall onto which the strange door handle is attached."
			+ " If you didn't already know it was there, it'd be almost impossible to find, as it's only given away by its barely visible lichen-lit shadow."
			+ (pc.isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_COMPLETE)
			? " As you're not yet in possession of the required password, trying to pull the handle again will undoubtedly prove to be a fruitless endeavour..."
			: " As you're now in possession of the password, you could try pulling the handle again...")
			+ "</p>";
		case DOOR_OPENED:
			return parse("<p>"
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
			return CAVERN_DARK.getContent()
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
			return parse("<p>"
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
			return parse("<p>"
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
			+ parse(
					pc.getLocationPlace().getPlaceType()==BAT_CAVERN_LIGHT
					? "[pc.Walking] through the bioluminescent forest, you find yourself passing by an absolutely gigantic specimen of mushroom-tree,"
					+ " as as you do so, you just so happen to see something glinting by its base."
					+ " Taking a closer look, you push a small patch of mushrooms aside to see that the source of the reflected light is a piece of laminated paper."
					: "As you carefully navigate the myriad obstacles offered by the twisting passages of the cavern, you suddenly almost slip on something beneath your feet."
					+ (pc.isFeminine() ? " Sighing" : " Grumbling")
					+ " to yourself about the responsible disposal of slippery objects in a damp cave, you find the cause of your near-faceplant to be a piece of laminated paper.")
			+ "</p>"
			+ "<p>"
			+ parse(this==PASSWORD_ONE
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
				+ (game.getDialogueFlags().hasFlag(darkPassFound)
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
			return parse("<p>"
			+ "You rage at having been given yet another pointless side quest."
			+ " [pc.speech(Can't they see I have things to see and people to do?)] you rant at nobody in particular,"
			+ " [pc.speech(I bet there's not even anybody to fuck behind that stupid door!)]"
			+ "</p>"
			+ "<p>"
			+ "As if in reply to your humble cry of anguish, a gust of wind howls through the caverns."
			+ " You suddenly feel more accomplished but also terribly unsatisfied."
			+ "</p>");
		case ENTRANCE:
			return "<p>"
			+ "This area joins the hidden cave with the Bat Caverns outside."
			+ " The air is heavy but not unpleasant."
			+ " The way back was once hidden by a tight fitting stone panel and a clever arcane password lock, but you have since defeated these measures."
			+ " The remaining light from outside and the dim glow from the inside are just enough for you to find your way."
			+ "</p>";
		case COLLAPSE:
			return "<p>"
			+ "As you make your way back out, you hear an ominious cracking sound from deep within the hidden cave."
			+ " The rotting supports have finally given way!"
			+ " The cave's unsupported ceiling starts to collapse, bringing down a hundred thousand tonnes of rock with it."
			+ "</p>";
		case ESCAPE:
			return parse("<p>"
			+ "You run as fast as your [pc.legs] will carry you and manage to reach safety just in time."
			+ " A thunderous boom echoes throughout the Bat Caverns,"
			+ " and you thankfully manage to dart to one side just in time to avoid the cloud of dust and debris which is expelled from out of the collapsing cave's entrance."
			+ "</p>"
			+ "<p>"
			+ "The sudden cave-in thankfully doesn't show any sign of spreading out into the Bat Caverns,"
			+ " and after a few moments have passed both the noise and vibrations have completely faded away."
			+ " All that's left to remind you of the cave's existence is the metal handle,"
			+ " which is lodged half-way up the huge pile of rubble which spews out of the place where the hidden door once sat."
			+ "</p>"
			+ "<p>"
			+ "By some sort of a miracle you've managed to escape completely unharmed, although there's now no way back into the hidden cave..."
			+ "</p>");
		case CORRIDOR:
			return "<p>"
			+ "Although made of the same stone and afflicted with the same damp as the Bat Caverns,"
			+ " the wooden supports along the walls are clear signs that this cave is artificial."
			+ " Although you see what look like hooks for placing lanterns or torches nailed into each support,"
			+ " the sodden wood has been more or less taken over by bioluminescent mushrooms and wood ears, conveniently lighting the way."
			+ "</p>";
		case SLEEPING_AREA:
			return "<p>"
			+ "From the bunkbeds scattered about the room, you guess that it was once used as a sleeping area."
			+ "Some of the beds have been knocked over and all are unusable rotting husks covered in bioluminescent mushrooms."
			+ "Piled high in the middle of the room are mysteriously stained pieces of fabric that might have been clothing at one point but are now home to yet more mushrooms."
			+ "</p>"
			+ "<p>"
			+ "You notice several waterproof footlockers next to some of the beds."
			+ "They're pretty beaten up, but they might still be sealed."
			+ "There is also what remains of someone's journal lying on one of the beds."
			+ "</p>";
		case SLEEPING_AREA_JOURNAL_OPEN:
			return "<p>"
			+ "You try to gingerly open the journal, but as you'd expect for paper that's spent who knows how long in a damp cave,"
			+ " most of the pages immediately crumble into illegible pieces."
			+ " There's no way you can remove this journal from its resting place without destroying it."
			+ " The light provided by the surrounding mushrooms is just enough for you to read some of the intact pages:"
			+ "</p>"
			+ "<p align=right>"
			+ "<i>"
			+ "8th August 1988"
			+ "</i>"
			+ "</p>"
			+ "<p>"
			+ "<i>"
			+ "The day of reckoning is here!"
			+ " At last the demons and their running dogs will answer for millennia of injustice."
			+ " Already we're driving the Enforcers back into their holes, and they'll never emerge from them if we can help it."
			+ " The adjutant would prefer that humans supplant the demons at the top, but that kind of thinking won't get us any allies among the non-humans."
			+ " The hearts and minds of the people are what we need to win."
			+ " We'll all crawl out from under the corpse of this corrupt society and then the Realm will be free!"
			+ "</i>"
			+ "</p>"
			+ "<p align=right>"
			+ "<i>"
			+ "4th November 1989"
			+ "</i>"
			+ "</p>"
			+ "<p>"
			+ "<i>"
			+ "We've kept Dominion under siege since our liberation began and have taken the outer boroughs,"
			+ " but the inner boroughs are a tough nut to crack even with our numbers advantage."
			+ " Command issued these collars imbued with angelic magic for interrogating prisoners as part of our initial assault kits and they've proven useful,"
			+ " but the adjutant has started taking advantage of how docile and compliant they make the wearer."
			+ " He's using some of the prisoners to relieve himself, or as he puts it, \"recreational procreation\"."
			+ " I don't agree with his methods, but I can't argue with the results."
			+ " More of his children swell our ranks every day, human and non-human."
			+ " They're definitely the most gung-ho of any of us, I guess because they know only what we've told them,"
			+ " and well...neither they nor anyone else need to know who their parents are or how they came to be."
			+ "</i>"
			+ "</p>"
			+ "<p align=right>"
			+ "<i>"
			+ "20th December 1989"
			+ "</i>"
			+ "</p>"
			+ "<p>"
			+ "<i>"
			+ "I always knew that our cause was just, but today a literal angel has returned to aid us!"
			+ " Of course I'd heard the rumors from the old guard that they helped get this whole rebellion started, but I never ever thought I'd see one."
			+ " Astrea is her name and I admit, she's more than just a pretty face."
			+ " She's just the shot in the arm that we needed and under her we've broken the Thinis salient at last!"
			+ " I am a little bit concerned though."
			+ " She and the adjutant agree on more things than I'd like, namely what our post-Lilith society should look like."
			+ " I think non-humans have earned their place at the table, why can't they see that?"
			+ " Haven't they fought just as hard against the demons as us?"
			+ " Haven't we all had enough of races stepping on each other?"
			+ " And, another thing."
			+ " A lot of the officers are starting to copy the adjutant's treatment of prisoners, to let off steam or so they say."
			+ " This is going to come back to bite us when we win."
			+ "</i>"
			+ "</p>"
			+ "<p align=right>"
			+ "<i>"
			+ "15th March 1991"
			+ "</i>"
			+ "</p>"
			+ "<p>"
			+ "<i>"
			+ "It...is not easy for me to put down on paper what I am thinking right now."
			+ " Last night during the block meeting, Astrea accused me and those who share my opinion on non-humans of being demon sympathizers."
			+ " And of course the loudest accuser was the adjutant, just because I don't participate in his...recruitment activities."
			+ " Well, it's true."
			+ " I don't slap and choke and rape prisoners even if almost all of the other officers do."
			+ " The collars do the work well enough."
			+ " There is something wrong with these \"angels\"."
			+ " Every night that Astrea and the adjutant spend together, he gets more animalistic in his hate for non-humans and in his lust for rape."
			+ " She is not an angel, she can't be."
			+ " And if she is, I want nothing to do with them."
			+ " We are being sent away from here to train insurgents, but I am not going."
			+ " The other accused and I have agreed, we're leaving this rebellion to start our own."
			+ " To what end, I don't know, but I can't be a part of this one, not anymore."
			+ "</i>"
			+ "</p>";
		case SLEEPING_AREA_SEARCHED:
			return "<p>"
			+ "From the bunk-beds scattered about the room, you guess that it was once used as a sleeping area."
			+ " Some of the beds have been knocked over and all are unusable rotting husks covered in bioluminescent mushrooms."
			+ " Piled high in the middle of the room are mysteriously stained pieces of fabric that might have been clothing at one point but are now home to yet more mushrooms."
			+ "</p>"
			+ "<p>"
			+ "The waterproof footlockers now sit empty and what's left would give even Rose a real challenge."
			+ " There is also what remains of someone's journal lying on one of the beds."
			+ "</p>";
		case COMMON_AREA:
			return "<p>"
			+ "This room appears to have been some kind of common area,"
			+ " although the single table and a few chairs would have made for spartan accommodations even if they weren't covered in glowing lichen."
			+ " The table has been split in two, right down the middle, either intentionally or from too much weight."
			+ "</p>"
			+ "<p>"
			+ "Looking over to the far corner of the room, you see a heavy metal cabinet."
			+ " The paint is flaking off and there's a huge dent in it, but it might still contain something of worth."
			+ "</p>";
		case COMMON_AREA_CACHE_OPEN:
			return parse("<p>"
			+ "[pc.Walking] over to the cabinet, and by having to apply more than a little force, you mange to pull the door open and find within:"
			+ "</p>");
		case COMMON_AREA_SEARCHED:
			return "<p>"
			+ "This room appears to have been some kind of common area,"
			+ " although the single table and a few chairs would have made for spartan accommodations even if they weren't covered in glowing lichen."
			+ " The table has been split in two, right down the middle either intentionally or from too much weight."
			+ "</p>"
			+ "<p>"
			+ "The cabinet in the far corner is now empty and there isn't anything left worth taking."
			+ "</p>";
		case ARMORY:
			return "<p>"
			+ "You can't be sure what this room was used for, given that half of it has caved in."
			+ " Poking out from rubble, there are two large metal containers."
			+ " The containers have burst open from the pressure exerted by the rubble but you see glimpses of plastic bags inside."
			+ "</p>";
		case ARMORY_CACHE_OPEN:
			return "<p>"
			+ "Approaching the rubble-entombed metal containers, you're able to shift aside some of the rocks and pull several plastic bags free from the debris."
			+ " Tearing these bags open, you find:"
			+ "</p>";
		case ARMORY_SEARCHED:
			return "<p>"
			+ "You can't be sure what this room was used for, given that half of it has caved in."
			+ "</p>"
			+ "<p>"
			+ "The metal containers and plastic bags are now empty, leaving nothing else but rubble in this room."
			+ "</p>";
		case CAVED_IN_ROOM:
			return "<p>"
			+ "It's impossible to even hazard a guess as to what this room was once used for, as it's completely buried beneath hundreds of tonnes of rubble."
			+ " There's no sign of anything buried beneath the rock which almost completely fills this room,"
			+ " and so there's really little else to do but turn around and continue your search elsewhere..."
			+ "</p>";
		case FIREBOMBS:
			return parse("<p>"
			+ "You think back to the arcane firebombs you got from the mysterious cave."
			+ " Surely Roxy still has some connections from her time with Vengar's gang? "
			+ " You bring out a firebomb and set it down on the counter."
			+ "</p>"
			+ "<p>"
			+ "[roxy.speech(What's that yer got there?)] Roxy asks, raising an eyebrow,"
			+ " [roxy.speech(The newest thing y'been jammin' up yer arse?)]"
			+ "</p>"
			+ "<p>"
			+ "Rolling your eyes as Roxy chuckles at her own crude joke, you explain what the firebomb is and what it does."
			+ " [roxy.speech(Yer mean it breaks when y'use et?"
			+ " 'urts resale value."
			+ " I'll take it off yer 'ands fer twenty an' not a fuckin' flame more.)]"
			+ "</p>"
			+ "<p>"
			+ "[pc.speech(I'm not interested in just pawning it off to you,)] you reply,"
			+ " [pc.speech(I don't have a way to get more of these."
			+ " I was wondering if you know anyone who does.)]"
			+ "</p>"
			+ "<p>"
			+ "Roxy frowns slightly at your question."
			+ " [roxy.speech(I might. I might not. Tha' fuck do I get out ta' this?)]"
			+ "</p>"
			+ "<p>"
			+ "[pc.speech(Nobody up in Dominion is going to carry something like this."
			+ " Think about it, exclusive goods mean more business.)]"
			+ "</p>"
			+ "<p>"
			+ "You tense up as Roxy grabs the firebomb and holds it perilously close to her lit cigarette while examining it."
			+ " After a brief pause, she puts it under the counter."
			+ " [roxy.speech(Yeah, alright, y'made yer fuckin' point. Gimme, say, two days an' I'll set somethin' up."
			+ " Now, y'gonna buy anythin' or are yer gonna stand there gawpin'?)]"
			+ "</p>");
		case FIREBOMBS_COMPLETE:
			return parse("<p>"
			+ "Roxy gestures at a wooden crate full of familiar rag-stuffed glass bottles on a nearby shelf when you ask her about the firebomb you gave her."
			+ " [roxy.speech(Y'blind? They's right there behind yer."
			+ " Jus' came in, perfect copies o' the one yer gave me."
			+ " Don't ask where I got 'em; all ya needs ta know is dat I's gonna be havin' ten o' dem fer sale every day.)]"
			+ "</p>"
			+ "<p>"
			+ "Pausing to take a deep drag on her cigarette, she rudely blows smoke all over you as she cackles,"
			+ " [roxy.speech(Now, y'gonna buy 'em or wot?)]"
			+ "</p>");
		case FIREBOMBS_FAILED:
			return parse("<p>"
			+ "Roxy is likely the only person who's going to be able to help you to get your [pc.hands] on more of those arcane firebombs."
			+ " Surely she still has some connections from her time with Vengar's gang?"
			+ " You ask the rat-girl if she's got any contacts who could make weapons of questionable legality."
			+ " Leaning back in her chair and grinning at you, she replies,"
			+ " [roxy.speech(Maybe I 'as, maybe I don't. Wot ya tryin' ta get yer grubby little mits on?)]"
			+ "</p>"
			+ "<p>"
			+ "As you explain to Roxy the nature of the firebombs which you're no longer in possession of,"
			+ " you watch in dismay as the rat-girl's expression turns to one of confusion and annoyance."
			+ " Despite giving her a clear description of the firebombs' properties, she shakes her head and snarls,"
			+ " [roxy.speech(Don't ya be wastin' me time with that shit."
			+ " I ain't gonna bother me friends by askin' 'em ta fill a bottle with some kind o' burnin' stuff."
			+ " An' besides, ain't nobody gonna buy weapons wot breaks when ya use 'em!"
			+ " Don't fuckin' ask me fer such stupid shit again.)]"
			+ "</p>"
			+ "<p>"
			+ "Taking a deep drag on her cigarette to help her calm down, the obnoxious rat-girl blows smoke all over you and sneers,"
			+ " [roxy.speech(Now, ya gonna buy sumthin', or fuck off?)]"
			+ "</p>"
			+ "<p>"
			+ "With your hopes of ever getting more firebombs dashed by Roxy's rude response, you wonder what to do now..."
			+ "</p>");
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ResponseTab> getResponses() {
		var pc = game.getPlayer();
		switch(this) {
		case ENTRANCE_HANDLE:
		{
			var r = CAVERN_DARK.getResponses();
			var t = r.get(0);
			if(pc.isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_PART_ONE)) {
				r.set(0,new ResponseTab(t.title,null,
						new Response("Pull the handle","What could possibly go wrong?",Dialogue.DOOR_NO_PASS)));
				r.get(0).response.addAll(t.response.subList(1,t.response.size()));
			}
			else if(pc.isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_PART_TWO)) {
				r.set(0,new ResponseTab(t.title,null,
						new Response("Pull the handle", "The handle won't budge. Looks like you really do need the password.", null)));
				r.get(0).response.addAll(t.response.subList(1,t.response.size()));
			}
			else if(pc.isQuestProgressLessThan(QuestLine.SIDE,Quest.PASSWORD_COMPLETE)) {
				r.set(0,new ResponseTab(t.title,null,
						new Response("Pull the handle", "You don't have the complete password!", null)));
				r.get(0).response.addAll(t.response.subList(1,t.response.size()));
			}
			else if(pc.isQuestProgressLessThan(QuestLine.SIDE,Quest.EXPLORATION)) {
				r.set(0,new ResponseTab(t.title,null,
						new Response(
								"Pull the handle",
								"You have the complete password now and can therefore try to pull the handle again... if you really want to.",
								DOOR_OPENED) {
							@Override
							public void effects() {
								game.getPlayerCell().getPlace().setPlaceType(Place.ENTRANCE_EXTERIOR);
								game.getPlayerCell().getPlace().setName(Place.ENTRANCE_EXTERIOR.getName());
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
							game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.EXPLORATION));
							game.getPlayer().setLocation(World.WORLD,Place.ENTRANCE);
						}
					},
					Response.back("Don't enter", "No telling what's in that cave...")));
		case ENTRANCE_EXTERIOR:
		if(!pc.isQuestFailed(QuestLine.SIDE)
				&& !pc.isQuestProgressGreaterThan(QuestLine.SIDE,Quest.EXPLORATION)) {
			return List.of(new ResponseTab("",null,new Response(
					"Enter",
					"This cave is not a natural formation. Someone built it, so it must lead somewhere.",
					Place.ENTRANCE.getDialogue(false)) {
				@Override
				public void effects() {
					game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.EXPLORATION));
					game.getPlayer().setLocation(World.WORLD,Place.ENTRANCE);
				}
			}));
		}
		return CAVERN_DARK.getResponses();
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
						game.getWorlds().get(BAT_CAVERNS).getCell(Place.ENTRANCE_HANDLE).getPlace().setPlaceType(Place.ENTRANCE_EXTERIOR);
						game.getWorlds().get(BAT_CAVERNS).getCell(Place.ENTRANCE_EXTERIOR).getPlace().setName(Place.ENTRANCE_EXTERIOR.getName());
					}
				}));
		case ENTRANCE:
		return List.of(new ResponseTab("",null,
			pc.isQuestProgressLessThan(QuestLine.SIDE, Quest.ESCAPE)
			? new Response("[style.colourBad(Exit)]",
				"This place looks seriously unstable, it could collapse at any moment."
				+ " It might be best to leave whatever secrets are in here and leave while you still can."
				+ "<br/>[style.italicsBad(You will not be able to return to this area after leaving!)]",
				COLLAPSE)
			: new Response("[style.colourGood(Exit)]",
				"You've had a look through everything that you could find."
				+ " It might be best to leave while you still can."
				+ "<br/><i>You will not be able to return to this area after leaving!</i>",
				COLLAPSE)));
		case COLLAPSE:
		return List.of(new ResponseTab("",null,
			new Response("Run!", "Run for your life!", ESCAPE) {
				@Override
				public void effects() {
					game.getTextEndStringBuilder().append(game.getPlayer().isQuestProgressGreaterThan(QuestLine.SIDE,Quest.EXPLORATION)
					? game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.SIDE_UTIL_COMPLETE)
					: game.getPlayer().setQuestFailed(QuestLine.SIDE,Quest.FAILED));
					game.getPlayer().setLocation(BAT_CAVERNS, Place.ENTRANCE_EXTERIOR);
					game.getPlayerCell().getPlace().setPlaceType(BAT_CAVERN_DARK);
					game.getPlayerCell().getPlace().setName(BAT_CAVERN_DARK.getName());
				}
			}));
		case ESCAPE:
			return BAT_CAVERN_DARK.getDialogue(false).getResponses();
		case SLEEPING_AREA:
		return List.of(new ResponseTab("",null,
				new Response("Open footlockers", "Open the footlockers.", SLEEPING_AREA_SEARCHED){
				@Override
				public void effects() {
					game.getTextEndStringBuilder().append(parse("<p>"
					+ "You rummage through the footlockers and find sets of uniforms, boots, and armbands of some sort that you don't recognize."
					+ "</p>"))
					.append(game.getPlayer().addClothing(game.getItemGen().generateClothing("dsg_hlf_equip_rbooniehat", false), 2, false, true))
					.append(game.getPlayer().addClothing(game.getItemGen().generateClothing("dsg_hlf_equip_rtunic", false), 2, false, true))
					.append(game.getPlayer().addClothing(game.getItemGen().generateClothing("dsg_hlf_equip_rtrousers", false), 2, false, true))
					.append(game.getPlayer().addClothing(game.getItemGen().generateClothing("dsg_hlf_equip_vcboots", false), 2, false, true))
					.append(game.getPlayer().addClothing(game.getItemGen().generateClothing("dsg_hlf_equip_rbrassard", false), 5, false, true));
					game.getPlayerCell().getPlace().setPlaceType(Place.SLEEPING_AREA_SEARCHED);
					game.getPlayerCell().getPlace().setName(Place.SLEEPING_AREA_SEARCHED.getName());
				}
			},
			new Response("Read journal", "See what the journal contains.", SLEEPING_AREA_JOURNAL_OPEN)));
		case SLEEPING_AREA_JOURNAL_OPEN:
		return List.of(new ResponseTab("",null,
			new Response.Back("Close", "You've seen enough.") {
				@Override
				public void effects() {
					if(game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE, Quest.ESCAPE)) {
						game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QuestLine.SIDE, Quest.ESCAPE));
					}
				}
			}));
		case SLEEPING_AREA_SEARCHED:
		return List.of(new ResponseTab("",null,
			new Response("Open footlockers", "You already opened the footlockers.", null),
			new Response("Read journal", "See what the journal contains.", SLEEPING_AREA_JOURNAL_OPEN)));
		case COMMON_AREA:
		return List.of(new ResponseTab("",null,
			new Response("Open cabinet", "Open the metal cabinet.", COMMON_AREA_CACHE_OPEN) {
				@Override
				public void effects() {
					game.getTextEndStringBuilder().append(game.getPlayer().addClothing(game.getItemGen().generateClothing("dsg_hlf_equip_rwebbing", false), 3, false, true))
					.append(game.getPlayer().addClothing(game.getItemGen().generateClothing("dsg_hlf_equip_sbandana", false), 1, false, true))
					.append(game.getPlayer().addClothing(game.getItemGen().generateClothing("dsg_hlf_equip_rbandolier", false), 3, false, true));
					game.getPlayerCell().getPlace().setPlaceType(Place.COMMON_AREA_SEARCHED);
					game.getPlayerCell().getPlace().setName(Place.COMMON_AREA_SEARCHED.getName());
				}
			}));
		case COMMON_AREA_CACHE_OPEN:
		return List.of(new ResponseTab("",null,
			new Response("Open cabinet", "You've already opened the cabinet.", null)));
		case ARMORY:
		case ARMORY_CACHE_OPEN:
		case ARMORY_SEARCHED:
		return List.of(new ResponseTab("",null,
			new Response("Open bags", "Open the plastic bags.", ARMORY_CACHE_OPEN){
			@Override
			public void effects() {
				game.getTextEndStringBuilder().append(game.getPlayer().addWeapon(game.getItemGen().generateWeapon("dsg_hlf_weap_pbsmg"), 3, false, true))
				.append(game.getPlayer().addWeapon(game.getItemGen().generateWeapon("dsg_hlf_weap_pboltrifle"), 2, false, true))
				.append(game.getPlayer().addWeapon(game.getItemGen().generateWeapon("dsg_hlf_weap_pbrevolver"), 5, false, true))
				.append(game.getPlayer().addWeapon(game.getItemGen().generateWeapon("dsg_hlf_weap_gbshotgun"), 1, false, true))
				.append(game.getPlayer().addWeapon(game.getItemGen().generateWeapon("dsg_hlf_weap_pbomb"), 10, false, true))
				.append("<p>")
				.append(game.getPlayer().getOccupation() == SOLDIER
				? "Having seen quite a few in improvised weapons training,"
				: "Having seen them in use during riots on the news,")
				.append(" you instantly recognise the disposable firebombs as being an arcane parody of Molotov cocktails."
				+ " You don't have a way to get more and you doubt any honest shop would want to carry something so obviously illegal,"
				+ " but perhaps a shopkeep with less scruples has the means to provide you with a supply of them..."
				+ "</p>")
				.append(!game.getDialogueFlags().hasFlag(axelExplainedVengar)
				? ""
				: parse("<p>"
				+ "[roxy.NamePos] shop would probably be a good place to start."
				+ "</p>"))
				.append(game.getPlayer().startQuest(QuestLine.FIREBOMBS));
				game.getPlayerCell().getPlace().setPlaceType(Place.ARMORY_SEARCHED);
				game.getPlayerCell().getPlace().setName(Place.ARMORY_SEARCHED.getName());
			}
		}));
		case FIREBOMBS:
		case FIREBOMBS_COMPLETE:
		case FIREBOMBS_FAILED:
		return TRADER.getResponses();
		}
		return List.of(new ResponseTab(""));
	}

	@Override
	public void applyPreParsingEffects() {
		switch(this) {
		case DOOR_OPENED:
			game.getPlayerCell().getPlace().setPlaceType(Place.ENTRANCE_EXTERIOR);
			game.getPlayerCell().getPlace().setName(Place.ENTRANCE_EXTERIOR.getName());
			break;
		case DOOR_NO_PASS:
			game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.PASSWORD_PART_ONE));
			break;
		case DISCOVERED:
			game.getPlayerCell().getPlace().setPlaceType(Place.ENTRANCE_HANDLE);
			game.getPlayerCell().getPlace().setName(Place.ENTRANCE_HANDLE.getName());
			game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.HANDLE_REFUSED));
			break;
		case PASSWORD_ONE:
			game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.PASSWORD_PART_TWO));
			game.getDialogueFlags().setFlag(
					game.getPlayer().getLocationPlace().getPlaceType()==BAT_CAVERN_DARK
							|| game.getPlayer().getLocationPlace().getPlaceType()==Place.ENTRANCE_HANDLE
					? darkPassFound
					: lightPassFound,
					true);
			break;
		case PASSWORD_TWO:
			game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.PASSWORD_COMPLETE));
			break;
		case PASSWORD_SILLY:
			game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.SIDE_UTIL_COMPLETE));
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
		case ESCAPE:
			return 60;
		case COMMON_AREA_CACHE_OPEN:
		case ARMORY_CACHE_OPEN:
			return 2*60;
		case FIREBOMBS:
		case FIREBOMBS_COMPLETE:
		case FIREBOMBS_FAILED:
			return 0;
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
		case COLLAPSE:
		case SLEEPING_AREA_JOURNAL_OPEN:
		case FIREBOMBS:
		case FIREBOMBS_COMPLETE:
		case FIREBOMBS_FAILED:
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
		case ESCAPE:
		case FIREBOMBS:
		case FIREBOMBS_COMPLETE:
		case FIREBOMBS_FAILED:
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
