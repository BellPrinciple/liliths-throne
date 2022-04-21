package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseCombat;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;

/**
 * @since 0.3.21
 * @version 0.3.21
 * @author DSG
 */
public class RebelBaseInsaneSurvivorDialogue {
	
	private static NPC getAttacker() {
		return Main.game.getActiveNPC();
	}
	
	public static final DialogueNode INSANE_SURVIVOR_ATTACK = new DialogueNode("A Living Ghost", "A strange figure in the corner.", true) {
		@Override
		public String getAuthor() {
				return "DSG";
		}
		@Override
		public int getSecondsPassed() {
				return 30;
		}
		@Override
		public String getContent() {
			return UtilText.parse(getAttacker(), "<p>"
			+ "As you enter the room, you notice a ghostly figure crouched in the corner with [npc.her] back turned to you,"
			+ "mumbling something inaudibly and seemingly unaware of your presence."
			+ "While you deliberate whether or not to approach [npc.herHim], you must have made some sort of noise since [npc.she] suddenly jumps up and turns to face you."
			+ "A half-eaten glowing mushroom falls from [npc.her] emaciated hands to the floor."
			+ "</p>"
			+ "<p>"
			+ "You're not quite sure what to make of the stranger before you."
			+ "[npc.She] is clearly mostly human, and corporeal at that,"
			+ "but [npc.her] pale complexion and glowing skin can only mean [npc.sheHas] lived far from the sun's rays for a long time on a diet of nothing but the mushrooms growing in the cave."
			+ "[npc.Her] clothing appears to have been some kind of uniform but is so covered in patches and stains that you're not sure if you've seen the style before or not."
			+ "</p>"
			+ "<p>"
			+ "Whatever the case, [npc.her] initial shock wears off almost immediately and [npc.she] reflexively points some sort of submachine gun at you."
			+ "</p>"
			+ "<p>"
			+ "[npc.speech(Come back to get me, huh?)] [npc.she] asks mockingly,"
			+ "[npc.speech(You demonic fuckers always were shit at cleaning up after yourselves."
			+ "Well, you're not turning me into a <i>fucking animal!</i>"
			+ "And I won't be like you either, I'll die first!)]"
			+ "</p>"
			+ "<p>"
			+ "[npc.She]'s got you square in [npc.her] sights and [npc.her] crazed, wild-eyed, expression gives you the impression [npc.she] might attack you at any moment."
			+ "</p>");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new ResponseCombat("Fight", "[npc.She] seems determined to fight you. Oblige [npc.herHim].",
						getAttacker(), 
						Util.newHashMapOfValues(
						new Value<>(getAttacker(), UtilText.parse(getAttacker(), "[npc.speech(I'll fucking end you, demon scum!)]"))));
			}
			else if (index == 2) {
				return new Response("Talk", "You get the feeling [npc.she] is beyond reason but it might be worth a try.", INSANE_SURVIVOR_TALK_ATTEMPT);
			}
			else if (index == 3 && Main.game.getPlayer().getMoney() <= 0) {
				return new Response("Offer money", "Your empty pockets don't give you the means to try this.", null);
			}
			else if (index == 3) {
				return new Response("Offer money", "While [npc.she] probably isn't after money, a simple offer couldn't hurt...could it?", INSANE_SURVIVOR_BRIBE_ATTEMPT);
			}
			else if (index == 4) {
				return new Response("Surrender", "[npc.Her] kit might be ragged but it still looks serious. You might not be able to win this.", INSANE_SURVIVOR_SURRENDER_ATTEMPT);
			}
			else {
				return null;
			}
		}	 
	};
	
	public static final DialogueNode INSANE_SURVIVOR_TALK_ATTEMPT = new DialogueNode("Talk Attempt", "", true) {
		@Override
		public String getAuthor() {
				return "DSG";
		}
		@Override
		public int getSecondsPassed() {
				return 10;
		}
		@Override
		public String getContent() {
			return UtilText.parse(getAttacker(), "<p>"
			+ "You make no move to defend yourself and instead try to explain calmly that you're not "
			+ (Main.game.getPlayer().getRace() != Race.DEMON
			? "a demon, to which the unstable stranger seems to take offense."
			+ "</p>"
			+ "<p>"
			+ "[npc.speech(Do you think I'm a fucking idiot?"
			+ "Your aura says 'demon' or worse all over it."
			+ "Astrea already tried that bullshit, but that slut never fooled me, and neither will you.)]"
			: "with Lilith, to which the unstable stranger can only let out a bitter laugh."
			+ "</p>"
			+ "<p>"
			+ "[npc.speech(A demon with a fucking conscience?"
			+ "Didn't your whore of a mother tell you?"
			+ "You're a goddamn puppet and only Lilith can pull the strings!"
			+ "Even Astrea never tried something <i>that</i> stupid, that slippery little slut.)]")
			+ "</p>"
			+ "<p>"
			+ "Before you can say anything else, a round whizzes by your head."
			+ "It would seem your attempt at diplomacy is over."
			+ "</p>");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new ResponseCombat("Fight", "[npc.She] can't be reasoned with. Defend yourself!",
						getAttacker(), 
						Util.newHashMapOfValues(
						new Value<>(getAttacker(), UtilText.parse(getAttacker(), "[npc.speech(I'll fucking end you, demon scum!)]"))));
			}
			return null;
		}
	};
	
	public static final DialogueNode INSANE_SURVIVOR_BRIBE_ATTEMPT = new DialogueNode("Bribe Attempt", "", true) {
		@Override
		public String getAuthor() {
			return "DSG";
		}
		@Override
		public int getSecondsPassed() {
			return 10;
		}
		@Override
		public String getContent() {
			return UtilText.parse(getAttacker(), "<p>"
			+ "You make no move to defend yourself and instead calmly offer up a healthy sum of money in exchange for lowering [npc.her] weapon."
			+ "The unstable stranger blinks, as if not quite believing what you've just said, before letting out a bitter laugh."
			+ "</p>"
			+ "<p>"
			+ "[npc.speech(Is that some kind of fucking joke?"
			+ "Even <i>you</i> know we're not in this for the pay."
			+ "Angels' mercy, they really do send the idiots in first.)]"
			+ "</p>"
			+ "<p>"
			+ "Before you can say anything else, a round whizzes by your head."
			+ "It would seem your attempt at diplomacy is over."
			+ "</p>");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new ResponseCombat("Fight", "[npc.She] can't be bought. Defend yourself!",
						getAttacker(), 
						Util.newHashMapOfValues(
						new Value<>(getAttacker(), UtilText.parse(getAttacker(), "[npc.speech(I'll fucking end you, demon scum!)]"))));
			}
			return null;
		}
	};
	
	public static final DialogueNode INSANE_SURVIVOR_SURRENDER_ATTEMPT = new DialogueNode("Surrendered!", "Know when to fold 'em.", true) {
		@Override
		public String getAuthor() {
			return "DSG";
		}
		@Override
		public int getSecondsPassed() {
			return 10;
		}
		@Override
		public String getContent() {
			return UtilText.parse(getAttacker(), "<p>"
			+ "You make no move to defend yourself and instead raise your [pc.arms] in a gesture of surrender."
			+ "The unstable stranger smirks."
			+ "</p>"
			+ "<p>"
			+ "[npc.speech(Oldest trick in the goddamn book."
			+ "Nobody's falling for <i>that</i> anymore, demon."
			+ "Angels' mercy, they really do send the idiots in first."
			+ "At least have the fucking decency to <i>try</i> putting up a fight.)]"
			+ "</p>"
			+ "<p>"
			+ "Before you can do or say anything else, a round whizzes by your head."
			+ "It would seem your surrender has been rejected."
			+ "</p>");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new ResponseCombat("Defend yourself", "[npc.She] doesn't think your surrender is genuine, leaving you with little choice.",
						getAttacker(), 
						Util.newHashMapOfValues(
						new Value<>(getAttacker(), UtilText.parse(getAttacker(), "[npc.speech(I'll fucking end you, demon scum!)]"))));
			}
			return null;
		}
	};
	
	public static final DialogueNode INSANE_SURVIVOR_VICTORY = new DialogueNode("Victory!", "", true) {
		@Override
		public void applyPreParsingEffects() {
			Main.game.getTextStartStringBuilder().append(UtilText.parse(getAttacker(), "<p>"
			+ "The oddly dressed stranger collapses before you, panting."
			+ "It seems that even in [npc.her] state, [npc.she] is still susceptible to your aura."
			+ "</p>"
			+ "<p>"
			+ "[npc.speech(No! Not like this! It can't end here!)] [npc.she] says, almost sobbing out the words, [npc.speech(It won't!)]"
			+ "</p>"
			+ "<p>"
			+ "[npc.She] pulls out a strange looking vial filled with a clear liquid and with the last of [npc.her] strength, breaks it open."
			+ "As soon as the liquid comes into contact with the air, it turns into a cloud of thick, blinding smoke."
			+ "You think you can hear booted feet running off into distance but by the time the smoke clears, there is no one left in the room except for you."
			+ "</p>"));
			Main.game.banishNPC(getAttacker());
			Main.game.getDialogueFlags().values.add(Dialogue.insaneSurvivorEncountered);
		}
		@Override
		public String getAuthor() {
			return "DSG";
		}
		@Override
		public int getSecondsPassed() {
			return 30;
		}
		@Override
		public String getContent() {
			return "";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return Response.back("Continue", "There's no sign of the stranger anywhere. Might as well carry on your way...");
			}
			return null;
		}	   
	};
	
	public static final DialogueNode INSANE_SURVIVOR_DEFEATED = new DialogueNode("Defeated!", "", true) {
		@Override
		public void applyPreParsingEffects() {
			int money = Main.game.getPlayer().getMoney();
			Main.game.getTextStartStringBuilder().append(UtilText.parse(getAttacker(), "<p>"
			+ "You fall to your knees before the oddly dressed stranger."
			+ "[npc.She] wordlessly walks up to you and sends you into an inky unconsciousness with the butt of [npc.her] weapon..."
			+ "</p>"
			+ "<p>"
			+ "When you wake a good fifteen minutes later, there's no one left in the room except for you."
			+ "As you get up and dust yourself off, you notice that your pockets are "
			+ (money > 20000
			? "now feeling somewhat lighter"
			: money > 10000
			? "now feeling much lighter"
			: money > 5000
			? "now almost empty"
			: money > 0
			? "now totally empty"
			: "totally empty like they were before, but obviously rummaged through,")
			+ " and let out a sigh. Seems for all [npc.her] posturing, [npc.she] was perfectly happy to take your money after knocking you out..."
			+ "</p>"));
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().incrementMoney(-5000));
			Main.game.banishNPC(getAttacker());
			Main.game.getDialogueFlags().values.add(Dialogue.insaneSurvivorEncountered);
		}
		@Override
		public String getAuthor() {
			return "DSG";
		}
		@Override
		public int getSecondsPassed() {
			return 15*60;
		}
		@Override
		public String getContent() {
			return "";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return Response.back("Continue", "There's no sign of the stranger anywhere. Might as well carry on your way...");
			}
			return null;
		}  
	};
	
}
