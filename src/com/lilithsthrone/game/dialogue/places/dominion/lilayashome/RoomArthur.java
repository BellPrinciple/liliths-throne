package com.lilithsthrone.game.dialogue.places.dominion.lilayashome;

import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.quests.Quest;
import com.lilithsthrone.game.character.quests.QuestLine;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.weapon.WeaponType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.places.PlaceType;

/**
 * @since 0.3.9
 * @version 0.3.9
 * @author Innoxia
 */
public class RoomArthur {

	private static int getDaysRemainingUntilArcaneLightningUnlocked() {
		return (int) (14 - (Main.game.getDayNumber() - Main.game.getDialogueFlags().getSavedLong("arthur_globe_day_start")));
	}
	
	public static final DialogueNode ROOM_ARTHUR_INSTALLATION = new DialogueNode("Arthur's Room", "", true) {
		@Override
		public int getSecondsPassed() {
			return 30*60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_INSTALLATION");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index == 1) {
				return new Response("Find Lyssieth", "If you ever want to find out what's going on, it looks like you'll have to agree to help.", ROOM_ARTHUR_INSTALLATION_AGREE_TO_CONVINCE_LYSSIETH){
					@Override
					public void effects() {
						Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.MAIN, Quest.MAIN_2_A_INTO_THE_DEPTHS));
					}
				};
			}
			return null;
		}
	};
	
	public static final DialogueNode ROOM_ARTHUR_INSTALLATION_AGREE_TO_CONVINCE_LYSSIETH = new DialogueNode("Arthur's Room", "", true) {
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_INSTALLATION_AGREE_TO_CONVINCE_LYSSIETH");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index == 1) {
				return new Response("Continue", "Allow Arthur to get on with his experiments.", ROOM_ARTHUR);
			}
			return null;
		}
	};
	
	public static final DialogueNode ROOM_ARTHUR = new DialogueNode("", "", false) {
		@Override
		public int getSecondsPassed() {
			return 30;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR");
		}
		@Override
		public String getResponseTabTitle(int index) {
			return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(responseTab==1) {
				return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
			}
			if(index == 1) {
				return new Response("Lyssieth", "Ask Arthur about Lilaya's mother, Lyssieth.", ROOM_ARTHUR_LYSSIETH);
				
			} else if(index == 2) {
				return new Response("Lilaya", "Ask Arthur about his past relationship with Lilaya.", ROOM_ARTHUR_LILAYA);
				
			}
			List<Response> additionalResponses = new ArrayList<>();

			// Arcane lightning quest:
			if(!Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE_ARCANE_LIGHTNING)) {
				if(!Main.game.getPlayer().hasQuest(QuestLine.SIDE_ARCANE_LIGHTNING) && Main.game.getPlayer().hasWeaponType(WeaponType.getWeaponTypeFromId("innoxia_lightningGlobe_lightning_globe"), true)) {
					additionalResponses.add(
							new Response("Lightning globe",
									"Show Arthur the lightning globe which you found in the Enforcer warehouse and ask him if there's any way to unlock the secrets of its power.",
									ROOM_ARTHUR_ARCANE_LIGHTNING_START) {
								@Override
								public void effects() {
									Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().startQuest(QuestLine.SIDE_ARCANE_LIGHTNING));
								}
								@Override
								public Colour getHighlightColour() {
									return PresetColour.QUEST_SIDE;
								}
							});
					
				} else if(Main.game.getPlayer().getQuest(QuestLine.SIDE_ARCANE_LIGHTNING) == Quest.LIGHTNING_SPELL_1_PAYMENT) {
					if(Main.game.getPlayer().getEssenceCount()<500) {
						additionalResponses.add(new Response("Give globe", "You need to have at least 500 essences absorbed into your aura before being able to do this!", null));
						
					} else {
						additionalResponses.add(new Response("Give globe",
								"Give Arthur the arcane lightning globe and allow him to extract the essences from your aura in order to unlock its secrets."
									+ "<br/>[style.italicsMinorBad(You will lose 500 essences from this!)]",
								ROOM_ARTHUR_ARCANE_LIGHTNING_PAYMENT));
					}
					
				} else if(Main.game.getPlayer().getQuest(QuestLine.SIDE_ARCANE_LIGHTNING) == Quest.LIGHTNING_SPELL_2_WAITING) {
					if(Main.game.getDayNumber() - Main.game.getDialogueFlags().getSavedLong("arthur_globe_day_start") < 14) {
						additionalResponses.add(new Response("Arcane lightning",
								"Arthur hasn't had enough time in which to unlock the secrets of the arcane lightning globe. Return"
									+ (getDaysRemainingUntilArcaneLightningUnlocked()==1
										?" tomorrow "
										:" in "+Util.intToString(getDaysRemainingUntilArcaneLightningUnlocked())+" days' time")
									+" to discover what he's managed to find out.",
								null));
						
					} else {
						additionalResponses.add(new Response("Arcane lightning",
								"Now that it's been two weeks since you gave Arthur your arcane lightning globe, you should ask him what secrets he's managed to unlock from it.",
								ROOM_ARTHUR_ARCANE_LIGHTNING_END));
					}
				}
			}
			
			for(int i=0 ; i<additionalResponses.size(); i++) {
				if(index==i+3) {
					return additionalResponses.get(i);
				}
			}
			
			return null;
		}
	};
	
	public static final DialogueNode ROOM_ARTHUR_LYSSIETH = new DialogueNode("", "", false) {
		@Override
		public int getSecondsPassed() {
			return 5 * 60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_LYSSIETH");
		}
		@Override
		protected List<ResponseTab> responses() {
			var r = ROOM_ARTHUR.getResponses();
			r.get(0).response.clear();
			r.get(0).response.set(1,new Response("Lyssieth", "You're already asking Arthur about Lyssieth.", null));
			return r;
		}
	};
	
	public static final DialogueNode ROOM_ARTHUR_LILAYA = new DialogueNode("", "", false) {
		@Override
		public int getSecondsPassed() {
			return 5 * 60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_LILAYA");
		}
		@Override
		protected List<ResponseTab> responses() {
			var r = ROOM_ARTHUR.getResponses();
			r.get(0).response.clear();
			r.get(0).response.set(2,new Response("Lilaya", "You're already asking Arthur about Lilaya.", null));
			return r;
		}
	};
	
	
	// Lightning globe:

	public static final DialogueNode ROOM_ARTHUR_ARCANE_LIGHTNING_START = new DialogueNode("", "", true, true) {
		@Override
		public int getSecondsPassed() {
			return 5 * 60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_ARCANE_LIGHTNING_START");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				if(Main.game.getPlayer().getEssenceCount()<500) {
					return new Response("Give globe", "You need to have at least 500 essences absorbed into your aura before being able to do this!", null);
					
				} else {
					return new Response("Give globe",
							"Give Arthur the arcane lightning globe and allow him to extract the essences from your aura in order to unlock its secrets."
									+ "<br/>[style.italicsMinorBad(You will lose 500 essences from this!)]",
							ROOM_ARTHUR_ARCANE_LIGHTNING_PAYMENT);
				}
				
			} else if(index==2) {
				return new Response("Later", "Tell Arthur that you'll think about it and perhaps return at another time to let him experiment on the globe.", LilayaHomeGeneric.CORRIDOR) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_ARCANE_LIGHTNING_START_LEAVE"));
						Main.game.getPlayer().setNearestLocation(Main.game.getPlayer().getWorldLocation(), PlaceType.LILAYA_HOME_CORRIDOR, false);
					}
				};
			}
			return null;
		}
	};

	public static final DialogueNode ROOM_ARTHUR_ARCANE_LIGHTNING_PAYMENT = new DialogueNode("", "", true, true) {
		@Override
		public void applyPreParsingEffects() {
			Main.game.getPlayer().removeWeaponTypeIntoVoid(WeaponType.getWeaponTypeFromId("innoxia_lightningGlobe_lightning_globe"), true, true, true);
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().incrementEssenceCount(-500, false));
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE_ARCANE_LIGHTNING, Quest.LIGHTNING_SPELL_2_WAITING));
			Main.game.getDialogueFlags().setSavedLong("arthur_globe_day_start", Main.game.getDayNumber());
		}
		@Override
		public int getSecondsPassed() {
			return 5 * 60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_ARCANE_LIGHTNING_PAYMENT");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Leave", "[pc.Step] out of Arthur's room and let him get on with his experiments.", LilayaHomeGeneric.CORRIDOR) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_ARCANE_LIGHTNING_PAYMENT_LEAVE"));
						Main.game.getPlayer().setNearestLocation(Main.game.getPlayer().getWorldLocation(), PlaceType.LILAYA_HOME_CORRIDOR, false);
					}
				};
			}
			return null;
		}
	};

	public static final DialogueNode ROOM_ARTHUR_ARCANE_LIGHTNING_END = new DialogueNode("", "", true, true) {
		@Override
		public int getSecondsPassed() {
			return 5 * 60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_ARCANE_LIGHTNING_END");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Learn spells", "Read over the papers and learn the two spells which Arthur was able to create.", ROOM_ARTHUR_ARCANE_LIGHTNING_END_FINAL);
			}
			return null;
		}
	};

	public static final DialogueNode ROOM_ARTHUR_ARCANE_LIGHTNING_END_FINAL = new DialogueNode("", "", true, true) {
		@Override
		public void applyPreParsingEffects() {
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().addWeapon(Main.game.getItemGen().generateWeapon("innoxia_lightningGlobe_lightning_globe_ring"), 1, false, true));
			Main.game.getPlayer().addSpell(Spell.ARCANE_CHAIN_LIGHTNING);
			Main.game.getPlayer().addSpell(Spell.ARCANE_LIGHTNING_SUPERBOLT);
			Main.game.getTextEndStringBuilder().append(
					"<p style='text-align:center;'>"
						+ "You have learned the following spells:"
						+ "<br/><b style='color:"+PresetColour.SPELL_SCHOOL_ARCANE.toWebHexString()+";'>"+Spell.ARCANE_CHAIN_LIGHTNING.getName()+"</b>"
						+ "<br/><b style='color:"+PresetColour.SPELL_SCHOOL_ARCANE.toWebHexString()+";'>"+Spell.ARCANE_LIGHTNING_SUPERBOLT.getName()+"</b>"
					+ "</p>");
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE_ARCANE_LIGHTNING, Quest.SIDE_UTIL_COMPLETE));
		}
		@Override
		public int getSecondsPassed() {
			return 60 * 60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_ARCANE_LIGHTNING_END_FINAL");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Continue", "Let Arthur continue with his other experiments.", LilayaHomeGeneric.CORRIDOR) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom", "ROOM_ARTHUR_ARCANE_LIGHTNING_END_FINAL_LEAVE"));
						Main.game.getPlayer().setNearestLocation(Main.game.getPlayer().getWorldLocation(), PlaceType.LILAYA_HOME_CORRIDOR, false);
					}
				};
			}
			return null;
		}
	};
}
