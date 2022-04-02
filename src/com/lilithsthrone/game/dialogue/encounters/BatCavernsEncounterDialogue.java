package com.lilithsthrone.game.dialogue.encounters;

import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.world.places.PlaceType;

/**
 * @since 0.2.4
 * @version 0.3.21
 * @author Innoxia, DSG
 */
public class BatCavernsEncounterDialogue {

	public static final DialogueNode FIND_ITEM = new DialogueNode("Discarded Item", "", true) {
		@Override
		public int getSecondsPassed() {
			return 2*60;
		}
		@Override
		public String getContent() {
			if(Main.game.getPlayer().getLocationPlace().getPlaceType().equals(PlaceType.BAT_CAVERN_SLIME_QUEEN_LAIR) || Main.game.getPlayer().getLocationPlace().getPlaceType().equals(PlaceType.BAT_CAVERN_LIGHT)) {
				if(((AbstractItem) AbstractEncounter.getRandomItem()).getItemType()==ItemType.MUSHROOM) {
					return UtilText.parseFromXMLFile("places/submission/batCaverns", "FIND_MUSHROOMS_LIGHT")
							+ "<p style='text-align:center;'>"
								+ "<b>"+ AbstractEncounter.getRandomItem().getDisplayName(true)+ "</b>"
							+ "</p>";
					
				} else {
					return UtilText.parseFromXMLFile("places/submission/batCaverns", "FIND_ITEM_LIGHT")
							+ "<p style='text-align:center;'>"
								+ "<b>"+ AbstractEncounter.getRandomItem().getDisplayName(true)+ "</b>"
							+ "</p>";
				}
				
			} else {
				if(((AbstractItem) AbstractEncounter.getRandomItem()).getItemType()==ItemType.MUSHROOM) {
					return UtilText.parseFromXMLFile("places/submission/batCaverns", "FIND_MUSHROOMS_DARK")
							+ "<p style='text-align:center;'>"
								+ "<b>"+ AbstractEncounter.getRandomItem().getDisplayName(true)+ "</b>"
							+ "</p>";
					
				} else {
					return UtilText.parseFromXMLFile("places/submission/batCaverns", "FIND_ITEM_DARK")
							+ "<p style='text-align:center;'>"
								+ "<b>"+ AbstractEncounter.getRandomItem().getDisplayName(true)+ "</b>"
							+ "</p>";
				}
			}
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response.Back("Take", "Add the " + AbstractEncounter.getRandomItem().getName() + " to your inventory."){
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().addItem((AbstractItem) AbstractEncounter.getRandomItem(), true, true));
					}
				};
				
			} else if (index == 2) {
				return Response.back("Leave", "Leave the " + AbstractEncounter.getRandomItem().getName() + " on the floor.");
				
			} else {
				return null;
			}
		}
	};
}
