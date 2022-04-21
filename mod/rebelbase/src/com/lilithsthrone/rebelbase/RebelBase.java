package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.character.persona.Occupation;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

import java.util.List;

/**
 * @since 0.3.8.9
 * @version 0.3.21
 * @author DSG
 */
public class RebelBase {
	
	public static final DialogueNode REBEL_BASE_ENTRANCE = new DialogueNode("Cave Entrance", "", false) {
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
			return "<p>"
			+ "This area joins the hidden cave with the Bat Caverns outside."
			+ " The air is heavy but not unpleasant."
			+ " The way back was once hidden by a tight fitting stone panel and a clever arcane password lock, but you have since defeated these measures."
			+ " The remaining light from outside and the dim glow from the inside are just enough for you to find your way."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE_REBEL_BASE, Quest.REBEL_BASE_ESCAPE)) {
					return new Response("[style.colourBad(Exit)]",
							"This place looks seriously unstable, it could collapse at any moment. It might be best to leave whatever secrets are in here and leave while you still can."
									+ "<br/>[style.italicsBad(You will not be able to return to this area after leaving!)]",
							REBEL_BASE_COLLAPSE);
				} else {
					return new Response("[style.colourGood(Exit)]",
							"You've had a look through everything that you could find. It might be best to leave while you still can."
								+ "<br/><i>You will not be able to return to this area after leaving!</i>",
							REBEL_BASE_COLLAPSE);
				}
			}
			return null;
		};
	};
	
	public static final DialogueNode REBEL_BASE_COLLAPSE = new DialogueNode("Uh oh...", "", true) {
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
			return "<p>"
			+ "As you make your way back out, you hear an ominious cracking sound from deep within the hidden cave."
			+ " The rotting supports have finally given way!"
			+ " The cave's unsupported ceiling starts to collapse, bringing down a hundred thousand tonnes of rock with it."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("Run!", "Run for your life!", REBEL_BASE_ESCAPE) {
					@Override
					public void effects() {
						if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.SIDE,Quest.EXPLORATION)){
							Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE,Quest.SIDE_UTIL_COMPLETE));
						} else {
							Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestFailed(QuestLine.SIDE,Quest.FAILED));
						}
						Main.game.getPlayer().setLocation(WorldType.BAT_CAVERNS, Place.ENTRANCE_EXTERIOR);
						Main.game.getPlayerCell().getPlace().setPlaceType(PlaceType.BAT_CAVERN_DARK);
						Main.game.getPlayerCell().getPlace().setName(PlaceType.BAT_CAVERN_DARK.getName());
					}
				};
			}
			return null;
		};
	};
	
	public static final DialogueNode REBEL_BASE_ESCAPE = new DialogueNode("", "", false, true) {
		@Override
		public String getAuthor() {
			return "DSG";
		}
		@Override
		public int getSecondsPassed() {
			return 60;
		}
		@Override
		public String getContent() {
			return UtilText.parse("<p>"
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
		}
		@Override
		public List<ResponseTab> responses() {
			return PlaceType.BAT_CAVERN_DARK.getDialogue(false).getResponses();
		};
	};
	
	public static final DialogueNode REBEL_BASE_CORRIDOR = new DialogueNode("Artificial Cave", "", false) {
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
			return "<p>"
			+ "Although made of the same stone and afflicted with the same damp as the Bat Caverns,"
			+ " the wooden supports along the walls are clear signs that this cave is artificial."
			+ " Although you see what look like hooks for placing lanterns or torches nailed into each support,"
			+ " the sodden wood has been more or less taken over by bioluminescent mushrooms and wood ears, conveniently lighting the way."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			return null;
		};
	};
	
	public static final DialogueNode REBEL_BASE_SLEEPING_AREA = new DialogueNode("Abandoned Sleeping Area", "", false) {
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
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("Open footlockers", "Open the footlockers.", REBEL_BASE_SLEEPING_AREA_SEARCHED){
					@Override
					public void effects() {
							Main.game.getTextEndStringBuilder().append(UtilText.parse("<p>"
							+ "You rummage through the footlockers and find sets of uniforms, boots, and armbands of some sort that you don't recognize."
							+ "</p>"))
							.append(Main.game.getPlayer().addClothing(Main.game.getItemGen().generateClothing("dsg_hlf_equip_rbooniehat", false), 2, false, true))
							.append(Main.game.getPlayer().addClothing(Main.game.getItemGen().generateClothing("dsg_hlf_equip_rtunic", false), 2, false, true))
							.append(Main.game.getPlayer().addClothing(Main.game.getItemGen().generateClothing("dsg_hlf_equip_rtrousers", false), 2, false, true))
							.append(Main.game.getPlayer().addClothing(Main.game.getItemGen().generateClothing("dsg_hlf_equip_vcboots", false), 2, false, true))
							.append(Main.game.getPlayer().addClothing(Main.game.getItemGen().generateClothing("dsg_hlf_equip_rbrassard", false), 5, false, true));
							Main.game.getPlayerCell().getPlace().setPlaceType(Place.SLEEPING_AREA_SEARCHED);
							Main.game.getPlayerCell().getPlace().setName(Place.SLEEPING_AREA_SEARCHED.getName());
					}
				};
				
			} else if (index ==2) {
				return new Response("Read journal", "See what the journal contains.", REBEL_BASE_SLEEPING_AREA_JOURNAL_OPEN);
				
			} else {
				return null;
			}
		};
	};
		
	public static final DialogueNode REBEL_BASE_SLEEPING_AREA_JOURNAL_OPEN = new DialogueNode("Crumbling Journal", "", true) {
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
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1 && Main.game.getPlayerCell().getPlace().getPlaceType().equals(Place.SLEEPING_AREA)) {
				return new Response("Close", "You've seen enough.", REBEL_BASE_SLEEPING_AREA){
						@Override
						public void effects() {
							if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE_REBEL_BASE, Quest.REBEL_BASE_ESCAPE)) {
								Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE_REBEL_BASE, Quest.REBEL_BASE_ESCAPE));
							}
							
						}
				};
				
			} else if (index == 1 && Main.game.getPlayerCell().getPlace().getPlaceType().equals(Place.SLEEPING_AREA_SEARCHED)) {
				return new Response("Close", "You've seen enough.", REBEL_BASE_SLEEPING_AREA_SEARCHED) {
						@Override
						public void effects() {
							if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE_REBEL_BASE, Quest.REBEL_BASE_ESCAPE)) {
								Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE_REBEL_BASE, Quest.REBEL_BASE_ESCAPE));
							}
						}
				};
				
			} else {
				return null;
			}
		};
	};
	
	public static final DialogueNode REBEL_BASE_SLEEPING_AREA_SEARCHED = new DialogueNode("Abandoned Sleeping Area", "", false) {
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
			return "<p>"
			+ "From the bunk-beds scattered about the room, you guess that it was once used as a sleeping area."
			+ " Some of the beds have been knocked over and all are unusable rotting husks covered in bioluminescent mushrooms."
			+ " Piled high in the middle of the room are mysteriously stained pieces of fabric that might have been clothing at one point but are now home to yet more mushrooms."
			+ "</p>"
			+ "<p>"
			+ "The waterproof footlockers now sit empty and what's left would give even Rose a real challenge."
			+ " There is also what remains of someone's journal lying on one of the beds."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("Open footlockers", "You already opened the footlockers.", null);
				
			} else if (index ==2) {
				return new Response("Read journal", "See what the journal contains.", REBEL_BASE_SLEEPING_AREA_JOURNAL_OPEN);
				
			} else {
				return null;
			}
		};
	};
		
	public static final DialogueNode REBEL_BASE_COMMON_AREA = new DialogueNode("Abandoned Common Area", "", false) {
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
			return "<p>"
			+ "This room appears to have been some kind of common area,"
			+ " although the single table and a few chairs would have made for spartan accommodations even if they weren't covered in glowing lichen."
			+ " The table has been split in two, right down the middle, either intentionally or from too much weight."
			+ "</p>"
			+ "<p>"
			+ "Looking over to the far corner of the room, you see a heavy metal cabinet."
			+ " The paint is flaking off and there's a huge dent in it, but it might still contain something of worth."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("Open cabinet", "Open the metal cabinet.", COMMON_AREA_CACHE_OPEN) {
					@Override
					public void effects() {
							Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().addClothing(Main.game.getItemGen().generateClothing("dsg_hlf_equip_rwebbing", false), 3, false, true));
							Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().addClothing(Main.game.getItemGen().generateClothing("dsg_hlf_equip_sbandana", false), 1, false, true));
							Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().addClothing(Main.game.getItemGen().generateClothing("dsg_hlf_equip_rbandolier", false), 3, false, true));
							Main.game.getPlayerCell().getPlace().setPlaceType(Place.COMMON_AREA_SEARCHED);
							Main.game.getPlayerCell().getPlace().setName(Place.COMMON_AREA_SEARCHED.getName());
					}
				};
				
			} else {
				return null;
			}
		};
	};
	
	public static final DialogueNode COMMON_AREA_CACHE_OPEN = new DialogueNode("Abandoned Common Area", "", false) {
		@Override
		public String getAuthor() {
			return "DSG";
		}
		@Override
		public int getSecondsPassed() {
			return 2*60;
		}
		@Override
		public String getContent() {
			return UtilText.parse("<p>"
			+ "[pc.Walking] over to the cabinet, and by having to apply more than a little force, you mange to pull the door open and find within:"
			+ "</p>");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			return REBEL_BASE_COMMON_AREA_SEARCHED.getResponse(responseTab, index);
		};
	};
		
	public static final DialogueNode REBEL_BASE_COMMON_AREA_SEARCHED = new DialogueNode("Abandoned Common Area", "", false) {
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
			return "<p>"
			+ "This room appears to have been some kind of common area,"
			+ " although the single table and a few chairs would have made for spartan accommodations even if they weren't covered in glowing lichen."
			+ " The table has been split in two, right down the middle either intentionally or from too much weight."
			+ "</p>"
			+ "<p>"
			+ "The cabinet in the far corner is now empty and there isn't anything left worth taking."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("Open cabinet", "You've already opened the cabinet.", null);
			} else {
				return null;
			}
		};
	};
		
	public static final DialogueNode REBEL_BASE_ARMORY = new DialogueNode("Partly Caved-in Room", "", false) {
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
			return "<p>"
			+ "You can't be sure what this room was used for, given that half of it has caved in."
			+ " Poking out from rubble, there are two large metal containers."
			+ " The containers have burst open from the pressure exerted by the rubble but you see glimpses of plastic bags inside."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("Open bags", "Open the plastic bags.", ARMORY_CACHE_OPEN){
					@Override
					public void effects() {
							Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().addWeapon(Main.game.getItemGen().generateWeapon("dsg_hlf_weap_pbsmg"), 3, false, true))
							.append(Main.game.getPlayer().addWeapon(Main.game.getItemGen().generateWeapon("dsg_hlf_weap_pboltrifle"), 2, false, true))
							.append(Main.game.getPlayer().addWeapon(Main.game.getItemGen().generateWeapon("dsg_hlf_weap_pbrevolver"), 5, false, true))
							.append(Main.game.getPlayer().addWeapon(Main.game.getItemGen().generateWeapon("dsg_hlf_weap_gbshotgun"), 1, false, true))
							.append(Main.game.getPlayer().addWeapon(Main.game.getItemGen().generateWeapon("dsg_hlf_weap_pbomb"), 10, false, true))
							.append("<p>")
							.append(Main.game.getPlayer().getOccupation() == Occupation.SOLDIER
								? "Having seen quite a few in improvised weapons training,"
								: "Having seen them in use during riots on the news,")
							.append(" you instantly recognise the disposable firebombs as being an arcane parody of Molotov cocktails."
								+ " You don't have a way to get more and you doubt any honest shop would want to carry something so obviously illegal,"
								+ " but perhaps a shopkeep with less scruples has the means to provide you with a supply of them..."
								+ "</p>")
							.append(!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.axelExplainedVengar)
								? ""
								: UtilText.parse("<p>"
									+ "[roxy.NamePos] shop would probably be a good place to start."
									+ "</p>"))
							.append(Main.game.getPlayer().startQuest(QuestLine.SIDE_REBEL_BASE_FIREBOMBS));
							Main.game.getPlayerCell().getPlace().setPlaceType(Place.ARMORY_SEARCHED);
							Main.game.getPlayerCell().getPlace().setName(Place.ARMORY_SEARCHED.getName());
					}
				};
				
			} else {
				return null;
			}
		};
	};
	
	public static final DialogueNode ARMORY_CACHE_OPEN = new DialogueNode("Abandoned Common Area", "", false) {
		@Override
		public String getAuthor() {
			return "DSG";
		}
		@Override
		public int getSecondsPassed() {
			return 2*60;
		}
		@Override
		public String getContent() {
			return "<p>"
			+ "Approaching the rubble-entombed metal containers, you're able to shift aside some of the rocks and pull several plastic bags free from the debris."
			+ " Tearing these bags open, you find:"
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			return REBEL_BASE_ARMORY_SEARCHED.getResponse(responseTab, index);
		};
	};
	
	public static final DialogueNode REBEL_BASE_ARMORY_SEARCHED = new DialogueNode("Partly Caved-in Room", "", false) {
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
			return "<p>"
			+ "You can't be sure what this room was used for, given that half of it has caved in."
			+ "</p>"
			+ "<p>"
			+ "The metal containers and plastic bags are now empty, leaving nothing else but rubble in this room."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("Open bags", "There's nothing left in this rubble worth taking.", null);
			} else {
				return null;
			}
		};
	};
	
	public static final DialogueNode REBEL_BASE_CAVED_IN_ROOM = new DialogueNode("Caved-in Room", "", false) {
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
			return "<p>"
			+ "It's impossible to even hazard a guess as to what this room was once used for, as it's completely buried beneath hundreds of tonnes of rubble."
			+ " There's no sign of anything buried beneath the rock which almost completely fills this room,"
			+ " and so there's really little else to do but turn around and continue your search elsewhere..."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			return null;
		};
	};
}