package com.lilithsthrone.game.dialogue.places.dominion.shoppingArcade;

import java.util.Map.Entry;

import com.lilithsthrone.game.character.npc.dominion.Vicky;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseTrade;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.inventory.weapon.AbstractWeapon;
import com.lilithsthrone.main.Main;

/**
 * @since 0.1.82
 * @version 0.3.7.9
 * @author Innoxia
 */
public class ArcaneArts {
	
	public static final DialogueNode EXTERIOR = new DialogueNode("Arcane Arts (Exterior)", "-", false) {

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts", "EXTERIOR");
		}

		@Override
		public String getResponseTabTitle(int index) {
			return ShoppingArcadeDialogue.getCoreResponseTab(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if(responseTab==0) {
				if (index == 1) {
					if(Main.game.isExtendedWorkTime()) {
						return new Response("Enter", "Step inside Arcane Arts.", SHOP_WEAPONS);
					} else {
						return new Response("Enter", "Arcane Arts is currently closed. You'll have to come back later if you want to do some shopping here.", null);
					}
				}
			}
			
			return ShoppingArcadeDialogue.getFastTravelResponses(responseTab, index);
		}
	};
	
	public static final DialogueNode SHOP_WEAPONS = new DialogueNode("Arcane Arts", "-", true) {

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts", "SHOP_WEAPONS");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new ResponseTrade("Weapons", "Walk over to the counter and see what weapons Vicky has in stock.", Main.game.getNpc(Vicky.class)) {
					@Override
					public void effects() {
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.vickyIntroduced, true);
						
						Main.game.getNpc(Vicky.class).clearNonEquippedInventory(false);
						
						for (Entry<AbstractWeapon, Integer> weapon : ((Vicky) Main.game.getNpc(Vicky.class)).getWeaponsForSale().entrySet()) {
							if(Main.game.getNpc(Vicky.class).isInventoryFull()) {
								break;
							}
							Main.game.getNpc(Vicky.class).addWeapon(weapon.getKey(), weapon.getValue(), false, false);
						}
					}
				};
				
			} else if (index == 2) {
				return new ResponseTrade("Potions & Spells", "Walk over to the counter and see what potions, essences, and spells Vicky has in stock.", Main.game.getNpc(Vicky.class)) {
					@Override
					public void effects() {
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.vickyIntroduced, true);

						Main.game.getNpc(Vicky.class).clearNonEquippedInventory(false);
						
						for (Entry<AbstractItem, Integer> item : ((Vicky) Main.game.getNpc(Vicky.class)).getItemsForSale().entrySet()) {
							if(Main.game.getNpc(Vicky.class).isInventoryFull()) {
								break;
							}
							Main.game.getNpc(Vicky.class).addItem(item.getKey(), item.getValue(), false, false);
						}
					}
				};
				
			} else if (index == 3) {
				if(((Vicky) Main.game.getNpc(Vicky.class)).getClothingForSale().isEmpty()) {
					return new Response("Clothing", "Vicky doesn't have any clothing in stock at the moment.", null);
				}
				return new ResponseTrade("Clothing", "Walk over to the counter and see what clothing Vicky has in stock.", Main.game.getNpc(Vicky.class)) {
					@Override
					public void effects() {
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.vickyIntroduced, true);

						Main.game.getNpc(Vicky.class).clearNonEquippedInventory(false);
						
						for (Entry<AbstractClothing, Integer> clothing : ((Vicky) Main.game.getNpc(Vicky.class)).getClothingForSale().entrySet()) {
							if(Main.game.getNpc(Vicky.class).isInventoryFull()) {
								break;
							}
							Main.game.getNpc(Vicky.class).addClothing(clothing.getKey(), clothing.getValue(), false, false);
						}
					}
				};
				
			} else if (index == 0) {
				return new Response("Leave", "Leave Arcane Arts and head back out into the arcade.", EXTERIOR) {
					@Override
					public void effects() {
						Main.game.setResponseTab(0);
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.vickyIntroduced, true);
					}
				};

			} else {
				return null;
			}
		}
	};
	
	
	public static final DialogueNode VICKY_POST_SEX = new DialogueNode("Arcane Arts", "-", true) {

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts", "VICKY_POST_SEX");
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return SHOP_WEAPONS.getResponse(responseTab, index);
		}
	};
	
	public static final DialogueNode VICKY_POST_SEX_RAPE = new DialogueNode("Arcane Arts", "-", true) {

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts", "VICKY_POST_SEX_RAPE");
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return SHOP_WEAPONS.getResponse(responseTab, index);
		}
	};
}
