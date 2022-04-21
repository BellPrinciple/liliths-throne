package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.character.persona.Occupation;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;

/**
 * @since 0.3.8.9
 * @version 0.3.21
 * @author DSG
 */
public class RebelBase {
	
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
				return new Response("Read journal", "See what the journal contains.", Dialogue.SLEEPING_AREA_JOURNAL_OPEN);
				
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