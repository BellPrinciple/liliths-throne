package com.lilithsthrone.game.dialogue.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.CoverableArea;
import com.lilithsthrone.game.character.body.types.LegType;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.quests.QuestLine;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.combat.moves.CombatMove;
import com.lilithsthrone.game.combat.spells.SpellSchool;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.DialogueNodeType;
import com.lilithsthrone.game.dialogue.companions.SlaveDialogue;
import com.lilithsthrone.game.dialogue.eventLog.EventLogEntry;
import com.lilithsthrone.game.dialogue.eventLog.EventLogEntryEncyclopediaUnlock;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithsthrone.game.dialogue.story.CharacterCreation;
import com.lilithsthrone.game.inventory.AbstractCoreItem;
import com.lilithsthrone.game.inventory.ColourReplacement;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.Rarity;
import com.lilithsthrone.game.inventory.ShopTransaction;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.inventory.clothing.BlockedParts;
import com.lilithsthrone.game.inventory.clothing.DisplacementType;
import com.lilithsthrone.game.inventory.clothing.Sticker;
import com.lilithsthrone.game.inventory.clothing.StickerCategory;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.game.inventory.enchanting.TFPotency;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.game.inventory.weapon.AbstractWeapon;
import com.lilithsthrone.game.sex.sexActions.SexActionUtility;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.rendering.Pattern;
import com.lilithsthrone.rendering.RenderingEngine;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.utils.comparators.ClothingZLayerComparator;

/**
 * @since 0.1.0
 * @version 0.3.9.5
 * @author Innoxia
 */
public class InventoryDialogue {
	
	private static final int IDENTIFICATION_PRICE = 400;
	private static final int IDENTIFICATION_ESSENCE_PRICE = 3;
	
	private static AbstractItem item;
	private static AbstractClothing clothing;
	private static AbstractWeapon weapon;
	private static InventorySlot weaponSlot;
	private static GameCharacter owner;
	
	private static NPC inventoryNPC;
	private static InventoryInteraction interactionType;

	private static StringBuilder inventorySB = new StringBuilder();

	private static boolean buyback;

	private static int buyBackPrice;
	private static int buyBackIndex;

	public static DamageType damageTypePreview;
	
	public static List<Colour> dyePreviews;
	public static String dyePreviewPattern;
	public static List<Colour> dyePreviewPatterns;
	
	public static Map<StickerCategory, Sticker> dyePreviewStickers;

	public static Map<String, String> getDyePreviewStickersAsStrings() {
		Map<String, String> stickerIds = new HashMap<>();
		for(Entry<StickerCategory, Sticker> entry : dyePreviewStickers.entrySet()) {
			stickerIds.put(entry.getKey().getId(), entry.getValue().getId());
		}
		return stickerIds;
	}
	
	private static void resetClothingDyeColours() {
		dyePreviews = new ArrayList<>();
		dyePreviews.addAll(clothing.getColours());
		
		dyePreviewPattern = clothing.getPattern();

		dyePreviewPatterns = new ArrayList<>();
		dyePreviewPatterns.addAll(clothing.getPatternColours());
		
		dyePreviewStickers = new HashMap<>(clothing.getStickersAsObjects());
	}

	private static void resetWeaponDyeColours() {
		dyePreviews = new ArrayList<>();
		dyePreviews.addAll(weapon.getColours());
		
		damageTypePreview = weapon.getDamageType();
	}
	
	private static String inventoryView() {
		inventorySB = new StringBuilder();
		
		inventorySB.append(RenderingEngine.ENGINE.getInventoryPanel(inventoryNPC, buyback));
		
		return inventorySB.toString();
	}

	private static void equipAll(GameCharacter character) {
		List<AbstractClothing> zlayerClothing = new ArrayList<>(character.getAllClothingInInventory().keySet());
		zlayerClothing.removeIf((c) -> c.isEnchantmentKnown() && c.isSealed());
		zlayerClothing.sort(new ClothingZLayerComparator().reversed());
		Set<InventorySlot> slotsTaken = new HashSet<>();

		for(AbstractClothing c : character.getClothingCurrentlyEquipped()) {
			slotsTaken.add(c.getSlotEquippedTo());
		}

		for(AbstractClothing c : zlayerClothing) {
			if(!slotsTaken.contains(c.getClothingType().getEquipSlots().get(0))) {
				Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>"+character.equipClothingFromInventory(c, true, character, character)+"</p>");
				slotsTaken.add(c.getClothingType().getEquipSlots().get(0));
			}
		}
	}

	private static String unequipAll(GameCharacter character) {
		StringBuilder sb = new StringBuilder();
		
//		for(int i=0; i<character.getArmRows(); i++) {
//			sb.append(character.unequipMainWeapon(i, false, character.isPlayer()));
//			sb.append(character.unequipOffhandWeapon(i, false, character.isPlayer()));
//		}
		
		List<AbstractClothing> zlayerClothing = new ArrayList<>(character.getClothingCurrentlyEquipped());
		zlayerClothing.sort(new ClothingZLayerComparator());
		
		for(AbstractClothing c : zlayerClothing) { 
			if((!Main.game.isInSex() || (!c.getSlotEquippedTo().isJewellery() && !c.isCondom())) && !c.isMilkingEquipment()) {
				if (c.isDiscardedOnUnequip(null)) {
					character.unequipClothingIntoVoid(c, true, Main.game.getPlayer());
				} else {
					if(Main.game.isInNewWorld()) {
						character.unequipClothingIntoInventory(c, true, Main.game.getPlayer());
					} else {
						character.unequipClothingOntoFloor(c, true, Main.game.getPlayer());
					}
				}
				sb.append("<p style='text-align:center;'>"+character.getUnequipDescription()+"</p>");
			}
		}
		
		return sb.toString();
	}
	
	private static String getEnchantmentNotDiscoveredText(String item) {
		StringBuilder sb = new StringBuilder();
		sb.append("You have not discovered how to enchant ");
		sb.append(item);
		sb.append(" yet...");
		sb.append("<br/>");
		if(Main.game.getPlayer().hasQuest(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)) {
			sb.append("[style.italicsArcane(You should go and talk to Lilaya about essences...)]");
		} else {
			sb.append("[style.italicsArcane(You need to absorb an essence after combat or sex, or via purchasing one, to discover more about enchanting...)]");
		}
		return sb.toString();
	}
	
	private static String getClothingBlockingRemovalText(GameCharacter equipTarget, String equipVerb) {
		StringBuilder sb = new StringBuilder();
		sb.append("You can't ");
		sb.append(equipVerb);
		sb.append(" the ");
		sb.append(clothing.getName());
		sb.append(", as ");
		sb.append(UtilText.parse(equipTarget, "[npc.namePos] "));
		sb.append(equipTarget.getBlockingClothing().getName());
		sb.append((equipTarget.getBlockingClothing().getClothingType().isPlural()?" are":" is"));
		sb.append(" blocking you from doing so!");
		return sb.toString();
	}
	
	/**
	 * The main DialogueNode. From here, the player can gain access to all parts
	 * of their inventory.
	 */
	public static final DialogueNode INVENTORY_MENU = new DialogueNode("Inventory", "Return to inventory menu.", true) {
		@Override
		public String getLabel() {
			if(!Main.game.isInNewWorld()) {
				return "Evening's Attire";
			}
			
			if (Main.game.getDialogueFlags().values.contains(DialogueFlagValue.quickTrade) && !Main.game.isInSex() && !Main.game.isInCombat()) {
				return "Inventory (Quick-Manage is <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</b>)";
			} else {
				return "Inventory";
			}
		}

		@Override
		public String getHeaderContent() {
			return inventoryView();
		}

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			
			if(inventoryNPC!=null && interactionType==InventoryInteraction.TRADING) {
				if(!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.removeTraderDescription)) {
					UtilText.nodeContentSB.append(inventoryNPC.getTraderDescription());
				}
				
			} else if(interactionType==InventoryInteraction.CHARACTER_CREATION) {
				return CharacterCreation.getCheckingClothingDescription();
			}
			
			return UtilText.nodeContentSB.toString();
		}

		public String getResponseTabTitle(int index) {
			return getGeneralResponseTabTitle(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return getCloseInventoryResponse();
			}
			
			if(responseTab==1) {
				if(item!=null) {
					return ITEM_INVENTORY.getResponse(responseTab, index);
					
				} else if(clothing!=null) {
					if(Main.game.getPlayer().getClothingCurrentlyEquipped().contains(clothing) || (inventoryNPC!=null && inventoryNPC.getClothingCurrentlyEquipped().contains(clothing))) {
						return CLOTHING_EQUIPPED.getResponse(responseTab, index);
					} else {
						return CLOTHING_INVENTORY.getResponse(responseTab, index);
					}
					
				} else if(weapon!=null) {
					if(Main.game.getPlayer().hasWeaponEquipped(weapon)
							|| (inventoryNPC!=null && inventoryNPC.hasWeaponEquipped(weapon))) {
						return WEAPON_EQUIPPED.getResponse(responseTab, index);
					} else {
						return WEAPON_INVENTORY.getResponse(responseTab, index);
					}
					
				} else {
					return null;
				}
			}

			StringBuilder responseSB = new StringBuilder();
			switch(interactionType) {
				case COMBAT:
					if(index == 1) {
						return new Response("Take all", "You can't do this during combat!", null);

					} else if (index == 2) {
						return new Response("Displace all", "You cannot do this while fighting someone!", null);

					} else if (index == 3) {
						return new Response("Replace all", "You cannot do this while fighting someone!", null);
						
					} else if (index == 4) {
						return new Response("Unequip all", "You cannot do this while fighting someone!", null);

					} else if (index == 5) {
						return new Response("Equip all", "You cannot do this while fighting someone!", null);

					} else if(index == 6) {
						if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
							return new Response("Store all", "You can't do this during combat!", null);
						}
						return new Response("Drop all", "You can't do this during combat!", null);

					} else if(index==11) {
						if(Main.game.getPlayer().getUnlockKeyMap().isEmpty()) {
							return new Response("Keys", "You do not currently own any keys which are used for unlocking certain items of clothing.", null);
							
						} else if(Main.game.getCurrentDialogueNode()==INVENTORY_MENU_KEYS) {
							return new Response("Keys", "You are already viewing a list of all the keys that you own which are used for unlocking certain items of clothing.", null);
							
						} else {
							return new Response("Keys", "View a list of all the keys that you currently own which are used for unlocking certain items of clothing.", INVENTORY_MENU_KEYS);
						}
						
					} else {
						return null;
					}
					
				case FULL_MANAGEMENT:
					if (index == 1) {
						if(inventoryNPC == null ) {
							if((Main.game.getPlayerCell().getInventory().getInventorySlotsTaken()==0 && !Main.game.getPlayerCell().getInventory().isAnyQuestItemPresent())
									|| Main.game.isInCombat()
									|| Main.game.isInSex()) {
								return new Response("Take all", "Pick up everything on the ground.", null);

							} else {
								return new Response("Take all", "Pick up everything on the ground.", INVENTORY_MENU){
									@Override
									public void effects(){
										for(Entry<AbstractItem, Integer> entry : new HashMap<>(Main.game.getPlayerCell().getInventory().getAllItemsInInventory()).entrySet()) {
											Main.game.getPlayer().addItem(entry.getKey(), entry.getValue(), true, true);
										}
										for(Entry<AbstractWeapon, Integer> entry : new HashMap<>(Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory()).entrySet()) {
											Main.game.getPlayer().addWeapon(entry.getKey(), entry.getValue(), true, true);
										}
										for(Entry<AbstractClothing, Integer> entry : new HashMap<>(Main.game.getPlayerCell().getInventory().getAllClothingInInventory()).entrySet()) {
											Main.game.getPlayer().addClothing(entry.getKey(), entry.getValue(), true, true);
										}
									}
								};
							}

						} else {
							if(inventoryNPC.getInventorySlotsTaken()==0 || Main.game.isInCombat() || Main.game.isInSex()) {
								return new Response("Take all", UtilText.parse(inventoryNPC, "Take everything from [npc.namePos] inventory."), null);

							} else {
								return new Response("Take all", UtilText.parse(inventoryNPC, "Take everything from [npc.namePos] inventory."), INVENTORY_MENU){
									@Override
									public void effects(){
										for(Entry<AbstractItem, Integer> entry : new HashMap<>(inventoryNPC.getAllItemsInInventory()).entrySet()) {
											if(!Main.game.getPlayer().isInventoryFull()
													|| Main.game.getPlayer().hasItem(entry.getKey())
													|| entry.getKey().getRarity()==Rarity.QUEST) {
												inventoryNPC.removeItem(entry.getKey(), entry.getValue());
												Main.game.getPlayer().addItem(entry.getKey(), entry.getValue(), true, true);
											}
										}
										for(Entry<AbstractClothing, Integer> entry : new HashMap<>(inventoryNPC.getAllClothingInInventory()).entrySet()) {
											if(!Main.game.getPlayer().isInventoryFull()
													|| Main.game.getPlayer().hasClothing(entry.getKey())
													|| entry.getKey().getRarity()==Rarity.QUEST) {
												inventoryNPC.removeClothing(entry.getKey(), entry.getValue());
												Main.game.getPlayer().addClothing(entry.getKey(), entry.getValue(), true, true);
											}
										}
										for(Entry<AbstractWeapon, Integer> entry : new HashMap<>(inventoryNPC.getAllWeaponsInInventory()).entrySet()) {
											if(!Main.game.getPlayer().isInventoryFull()
													|| Main.game.getPlayer().hasWeapon(entry.getKey())
													|| entry.getKey().getRarity()==Rarity.QUEST) {
												inventoryNPC.removeWeapon(entry.getKey(), entry.getValue());
												Main.game.getPlayer().addWeapon(entry.getKey(), entry.getValue(), true, true);
											}
										}
									}
								};
							}
						}

					} else if (index == 2) {
						if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()) {
							return new Response("Displace all", "You aren't wearing any clothing, so there's nothing to displace!", null);

						} else {
							return new Response("Displace all", "Displace as much of your clothing as possible.", INVENTORY_MENU){
								@Override
								public void effects(){
									for(AbstractClothing c : Main.game.getPlayer().getClothingCurrentlyEquipped()) {
										for(BlockedParts bp : c.getBlockedPartsMap(Main.game.getPlayer(), c.getSlotEquippedTo())) {
											if(bp.displacementType != DisplacementType.REMOVE_OR_EQUIP) {
												Main.game.getPlayer().isAbleToBeDisplaced(c, bp.displacementType, true, true, Main.game.getPlayer());
												Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>"+Main.game.getPlayer().getDisplaceDescription()+"</p>");
											}
										}
									}
								}
							};
						}

					} else if (index == 3) {
						if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()) {
							return new Response("Replace all", "You aren't wearing any clothing, so there's nothing to replace!", null);

						} else {
							return new Response("Replace all", "Replace as much of your clothing as possible.", INVENTORY_MENU){
								@Override
								public void effects(){

									List<AbstractClothing> zlayerClothing = new ArrayList<>(Main.game.getPlayer().getClothingCurrentlyEquipped());
									zlayerClothing.sort(new ClothingZLayerComparator().reversed());

									for(AbstractClothing c : zlayerClothing) {
										for(BlockedParts bp : c.getBlockedPartsMap(Main.game.getPlayer(), c.getSlotEquippedTo())) {
											if(bp.displacementType != DisplacementType.REMOVE_OR_EQUIP) {
												Main.game.getPlayer().isAbleToBeReplaced(c, bp.displacementType, true, true, Main.game.getPlayer());
												Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>"+Main.game.getPlayer().getReplaceDescription()+"</p>");
											}
										}
									}
								}
							};
						}

					} else if (index == 4) {
						if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()) {
							return new Response("Unequip all", "You aren't wearing any clothing, so there's nothing to remove!", null);

						} else {
							return new Response("Unequip all", "Remove as much of your clothing as possible.", INVENTORY_MENU){
								@Override
								public void effects(){
									Main.game.getTextEndStringBuilder().append(unequipAll(Main.game.getPlayer()));
								}
							};
						}

					} else if (index == 5) {
						if(Main.game.getPlayer().getAllClothingInInventory().isEmpty()) {
							return new Response("Equip all", "You don't have any clothing, so there's nothing to equip!", null);

						} else {
							return new Response("Equip all", "Equip as much of the clothing in your inventory as possible.", INVENTORY_MENU){
								@Override
								public void effects(){
									equipAll(Main.game.getPlayer());
								}
							};
						}

					} else if(index == 6) {
						if(Main.game.getPlayer().getInventorySlotsTaken()==0) {
							return new Response(
									!Main.game.getPlayer().getLocationPlace().isItemsDisappear()
										?"Store all"
										:"Drop all",
									!Main.game.getPlayer().getLocationPlace().isItemsDisappear()
										?"You have nothing in your inventory to store..."
										:"You have nothing in your inventory to drop...",
											null);
						}
						return new Response(
								!Main.game.getPlayer().getLocationPlace().isItemsDisappear()
									?"Store all"
									:"Drop all",
								!Main.game.getPlayer().getLocationPlace().isItemsDisappear()
									?"Store everything from your inventory in this location."
									:"Drop everything from your inventory onto the ground.",
										INVENTORY_MENU) {
							@Override
							public void effects() {
								for(Entry<AbstractItem, Integer> i : new HashSet<>(Main.game.getPlayer().getAllItemsInInventory().entrySet())) {
									if(i.getKey().getItemType().isAbleToBeDropped()) {
										dropItems(Main.game.getPlayer(), i.getKey(), i.getValue());
									}
								}
								for(Entry<AbstractWeapon, Integer> w : new HashSet<>(Main.game.getPlayer().getAllWeaponsInInventory().entrySet())) {
									if(w.getKey().getWeaponType().isAbleToBeDropped()) {
										dropWeapons(Main.game.getPlayer(), w.getKey(), w.getValue());
									}
								}
								for(Entry<AbstractClothing, Integer> c : new HashSet<>(Main.game.getPlayer().getAllClothingInInventory().entrySet())) {
									if(c.getKey().getClothingType().isAbleToBeDropped()) {
										dropClothing(Main.game.getPlayer(), c.getKey(), c.getValue());
									}
								}
							}
						};

					} else if (index == 7 && inventoryNPC != null) {
						if(inventoryNPC.getClothingCurrentlyEquipped().isEmpty()) {
							return new Response(UtilText.parse(inventoryNPC,"Displace all ([npc.HerHim])"),
									UtilText.parse(inventoryNPC, "[npc.Name] isn't wearing any clothing, so there's nothing to displace!"),
									null);

						} else {
							return new Response(UtilText.parse(inventoryNPC,"Displace all ([npc.HerHim])"),
									UtilText.parse(inventoryNPC, "Displace as much of [npc.namePos] clothing as possible."),
									INVENTORY_MENU){
								@Override
								public void effects(){
									for(AbstractClothing c : inventoryNPC.getClothingCurrentlyEquipped()) {
										for(BlockedParts bp : c.getBlockedPartsMap(inventoryNPC, c.getSlotEquippedTo())) {
											if(bp.displacementType != DisplacementType.REMOVE_OR_EQUIP) {
												inventoryNPC.isAbleToBeDisplaced(c, bp.displacementType, true, true, Main.game.getPlayer());
												Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>"+inventoryNPC.getDisplaceDescription()+"</p>");
											}
										}
									}
								}
							};
						}

					} else if (index == 8 && inventoryNPC != null) {
						if(inventoryNPC.getClothingCurrentlyEquipped().isEmpty()) {
							return new Response(UtilText.parse(inventoryNPC,"Replace all ([npc.HerHim])"),
									UtilText.parse(inventoryNPC, "[npc.Name] isn't wearing any clothing, so there's nothing to replace!"),
									null);

						} else {
							return new Response(UtilText.parse(inventoryNPC,"Replace all ([npc.HerHim])"),
									UtilText.parse(inventoryNPC, "Replace as much of [npc.namePos] clothing as possible."),
									INVENTORY_MENU){
								@Override
								public void effects(){

									List<AbstractClothing> zlayerClothing = new ArrayList<>(inventoryNPC.getClothingCurrentlyEquipped());
									zlayerClothing.sort(new ClothingZLayerComparator().reversed());

									for(AbstractClothing c : zlayerClothing) {
										for(BlockedParts bp : c.getBlockedPartsMap(inventoryNPC, c.getSlotEquippedTo())) {
											if(bp.displacementType != DisplacementType.REMOVE_OR_EQUIP) {
												inventoryNPC.isAbleToBeReplaced(c, bp.displacementType, true, true, Main.game.getPlayer());
												Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>"+inventoryNPC.getReplaceDescription()+"</p>");
											}
										}
									}
								}
							};
						}

					} else if (index == 9 && inventoryNPC != null) {
						if(inventoryNPC.getClothingCurrentlyEquipped().isEmpty()) {
							return new Response(UtilText.parse(inventoryNPC, "Unequip all ([npc.HerHim])"),
									UtilText.parse(inventoryNPC, "[npc.Name] isn't wearing any clothing, so there's nothing to remove!"),
									null);

						} else {
							return new Response(UtilText.parse(inventoryNPC, "Unequip all ([npc.HerHim])"),
									UtilText.parse(inventoryNPC, "Remove as much of [npc.namePos] clothing as possible."),
									INVENTORY_MENU){
								@Override
								public void effects(){
									Main.game.getTextEndStringBuilder().append(unequipAll(inventoryNPC));
								}
							};
						}

					} else if (index == 10 && !Main.game.isInSex() && !Main.game.isInCombat()) {
						return getQuickTradeResponse();

					} else if(index==11) {
						if(Main.game.getPlayer().getUnlockKeyMap().isEmpty()) {
							return new Response("Keys", "You do not currently own any keys which are used for unlocking certain items of clothing.", null);
							
						} else if(Main.game.getCurrentDialogueNode()==INVENTORY_MENU_KEYS) {
							return new Response("Keys", "You are already viewing a list of all the keys that you own which are used for unlocking certain items of clothing.", null);
							
						} else {
							return new Response("Keys", "View a list of all the keys that you currently own which are used for unlocking certain items of clothing.", INVENTORY_MENU_KEYS);
						}
						
					} else {
						return null;
					}
				case CHARACTER_CREATION:
					if (index == 1) {
						if(Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.NIPPLES)
								|| Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.ANUS)
								|| Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.PENIS)
								|| Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.VAGINA)
								|| (Main.game.getPlayer().getClothingInSlot(InventorySlot.FOOT)==null && Main.game.getPlayer().getLegType().equals(LegType.HUMAN))) {
							return new Response("To the stage", "You need to be wearing clothing that covers your body, as well as a pair of shoes.", null);
							
						} else {
							return new Response("To the stage", "You're ready to approach the stage now.", CharacterCreation.CHOOSE_BACKGROUND) {
								@Override
								public int getSecondsPassed() {
									return CharacterCreation.TIME_TO_BACKGROUND;
								}
								@Override
								public void effects() {
									CharacterCreation.moveNPCIntoPlayerTile();
								}
							};
						}
						
					} else if(index == 2){
						if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()){
							return new Response("Unequip all", "You're currently naked, there's nothing to be unequipped.", null);
						}
						else{
							return new Response("Unequip all", "Remove as much of your clothing as possible.", INVENTORY_MENU){
								@Override
								public void effects(){
									Main.game.getTextEndStringBuilder().append(unequipAll(Main.game.getPlayer()));
								}
							};
						}

					} else {
						return null;
					}
					
				case TRADING:
					if (index == 1) {
						if(inventoryNPC != null ||Main.game.getPlayerCell().getInventory().getInventorySlotsTaken()==0 || Main.game.isInCombat() || Main.game.isInSex()) {
							return new Response("Take all", "Pick up everything on the ground.", null);

						} else {
							return new Response("Take all", "Pick up everything on the ground.", INVENTORY_MENU){
								@Override
								public void effects(){
									for(Entry<AbstractItem, Integer> entry : new HashMap<>(Main.game.getPlayerCell().getInventory().getAllItemsInInventory()).entrySet()) {
										Main.game.getPlayer().addItem(entry.getKey(), entry.getValue(), true, true);
									}
									for(Entry<AbstractWeapon, Integer> entry : new HashMap<>(Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory()).entrySet()) {
										Main.game.getPlayer().addWeapon(entry.getKey(), entry.getValue(), true, true);
									}
									for(Entry<AbstractClothing, Integer> entry : new HashMap<>(Main.game.getPlayerCell().getInventory().getAllClothingInInventory()).entrySet()) {
										Main.game.getPlayer().addClothing(entry.getKey(), entry.getValue(), true, true);
									}
								}
							};
						}

					} else if (index == 2) {
						if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()) {
							return new Response("Displace all", "You aren't wearing any clothing, so there's nothing to displace!", null);

						} else {
							return new Response("Displace all", "Displace as much of your clothing as possible.", INVENTORY_MENU){
								@Override
								public void effects(){
									for(AbstractClothing c : Main.game.getPlayer().getClothingCurrentlyEquipped()) {
										for(BlockedParts bp : c.getBlockedPartsMap(Main.game.getPlayer(), c.getSlotEquippedTo())) {
											if(bp.displacementType != DisplacementType.REMOVE_OR_EQUIP) {
												Main.game.getPlayer().isAbleToBeDisplaced(c, bp.displacementType, true, true, Main.game.getPlayer());
												Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>"+Main.game.getPlayer().getDisplaceDescription()+"</p>");
											}
										}
									}
								}
							};
						}

					} else if (index == 3) {
						if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()) {
							return new Response("Replace all", "You aren't wearing any clothing, so there's nothing to replace!", null);

						} else {
							return new Response("Replace all", "Replace as much of your clothing as possible.", INVENTORY_MENU){
								@Override
								public void effects(){

									List<AbstractClothing> zlayerClothing = new ArrayList<>(Main.game.getPlayer().getClothingCurrentlyEquipped());
									zlayerClothing.sort(new ClothingZLayerComparator().reversed());

									for(AbstractClothing c : zlayerClothing) {
										for(BlockedParts bp : c.getBlockedPartsMap(Main.game.getPlayer(), c.getSlotEquippedTo())) {
											if(bp.displacementType != DisplacementType.REMOVE_OR_EQUIP) {
												Main.game.getPlayer().isAbleToBeReplaced(c, bp.displacementType, true, true, Main.game.getPlayer());
												Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>"+Main.game.getPlayer().getReplaceDescription()+"</p>");
											}
										}
									}
								}
							};
						}

					} else if (index == 4) {
						if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()) {
							return new Response("Unequip all", "You aren't wearing any clothing, so there's nothing to remove!", null);

						} else {
							return new Response("Unequip all", "Remove as much of your clothing as possible.", INVENTORY_MENU){
								@Override
								public void effects(){
									Main.game.getTextEndStringBuilder().append(unequipAll(Main.game.getPlayer()));
								}
							};
						}

					} else if (index == 5) {
						if(Main.game.getPlayer().getAllClothingInInventory().isEmpty()) {
							return new Response("Equip all", "You don't have any clothing, so there's nothing to equip!", null);

						} else {
							return new Response("Equip all", "Equip as much of the clothing in your inventory as possible.", INVENTORY_MENU){
								@Override
								public void effects(){
									equipAll(Main.game.getPlayer());
								}
							};
						}

					} else if(index == 6) {
						if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
							return new Response("Store all", "You can't do this while trading with someone...", null);
						}
						return new Response("Drop all", "You can't do this while trading with someone...", null);

					} else if (index == 9 && inventoryNPC!=null) {
						return getBuybackResponse();

					} else if (index == 10 && !Main.game.isInSex() && !Main.game.isInCombat()) {
						return getQuickTradeResponse();

					} else if(index==11) {
						if(Main.game.getPlayer().getUnlockKeyMap().isEmpty()) {
							return new Response("Keys", "You do not currently own any keys which are used for unlocking certain items of clothing.", null);
							
						} else if(Main.game.getCurrentDialogueNode()==INVENTORY_MENU_KEYS) {
							return new Response("Keys", "You are already viewing a list of all the keys that you own which are used for unlocking certain items of clothing.", null);
							
						} else {
							return new Response("Keys", "View a list of all the keys that you currently own which are used for unlocking certain items of clothing.", INVENTORY_MENU_KEYS);
						}
						
					} else {
						return null;
					}
				case SEX:
					if(index == 1) {
						return new Response("Take all", "Pick up everything on the ground.", null);

					} else if (index == 2) {
						if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()) {
							return new Response("Displace all", "You aren't wearing any clothing, so there's nothing to displace!", null);

						} else {
							return new Response("Displace all", "Displace as much of your clothing as possible.", Main.sex.SEX_DIALOGUE){
								@Override
								public void effects(){
									responseSB.setLength(0);

									for(AbstractClothing c : Main.game.getPlayer().getClothingCurrentlyEquipped()) {
										for(BlockedParts bp : c.getBlockedPartsMap(Main.game.getPlayer(), c.getSlotEquippedTo())) {
											if(bp.displacementType != DisplacementType.REMOVE_OR_EQUIP) {
												Main.game.getPlayer().isAbleToBeDisplaced(c, bp.displacementType, true, true, Main.game.getPlayer());
												responseSB.append("<p style='text-align:center;'>"+Main.game.getPlayer().getDisplaceDescription()+"</p>");
											}
										}
									}

									Main.sex.setUnequipClothingText(null, responseSB.toString());
									Main.mainController.openInventory();
									Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
									Main.sex.setSexStarted(true);
								}
							};
						}

					} else if (index == 3) {
						return new Response("Replace all", "You can't replace clothing in sex!", null);

					} else if (index == 4) {
						if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()) {
							return new Response("Unequip all", "You aren't wearing any clothing, so there's nothing to remove!", null);

						} else {
							return new Response("Unequip all", "Remove as much of your clothing as possible.", Main.sex.SEX_DIALOGUE){
								@Override
								public void effects(){
									responseSB.setLength(0);
									
									responseSB.append(unequipAll(Main.game.getPlayer()));
									
									Main.sex.setUnequipClothingText(null, responseSB.toString());
									Main.mainController.openInventory();
									Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
									Main.sex.setSexStarted(true);
								}
							};
						}

					} else if (index == 5) {
						return new Response("Equip all", "You can't equip clothing in sex!", null);

					} else if(index == 6) {
						if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
							return new Response("Store all", "You can't do this during sex...", null);
						}
						return new Response("Drop all", "You can't do this during sex...", null);

					} else if (index == 7 && inventoryNPC != null) {
						if(!Main.sex.getSexManager().isAbleToRemoveOthersClothing(Main.game.getPlayer(), null)) {
							return new Response(UtilText.parse(inventoryNPC, "Displace all ([npc.HerHim])"), UtilText.parse(inventoryNPC, "You can't displace [npc.namePos] clothing in this sex scene!"), null);

						} else if(inventoryNPC.getClothingCurrentlyEquipped().isEmpty()) {
							return new Response(UtilText.parse(inventoryNPC, "Displace all ([npc.HerHim])"), UtilText.parse(inventoryNPC, "[npc.Name] isn't wearing any clothing, so there's nothing to displace!"), null);

						} else {
							return new Response(UtilText.parse(inventoryNPC, "Displace all ([npc.HerHim])"), UtilText.parse(inventoryNPC, "Displace as much of [npc.namePos] clothing as possible."), Main.sex.SEX_DIALOGUE){
								@Override
								public void effects(){
									responseSB.setLength(0);

									for(AbstractClothing c : inventoryNPC.getClothingCurrentlyEquipped()) {
										for(BlockedParts bp : c.getBlockedPartsMap(inventoryNPC, c.getSlotEquippedTo())) {
											if(bp.displacementType != DisplacementType.REMOVE_OR_EQUIP) {
												inventoryNPC.isAbleToBeDisplaced(c, bp.displacementType, true, true, Main.game.getPlayer());
												responseSB.append("<p style='text-align:center;'>"+inventoryNPC.getDisplaceDescription()+"</p>");
											}
										}
									}

									Main.sex.setUnequipClothingText(null, responseSB.toString());
									Main.mainController.openInventory();
									Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
									Main.sex.setSexStarted(true);
								}
							};
						}

					} else if (index == 8 && inventoryNPC != null) {
						return new Response(UtilText.parse(inventoryNPC, "Replace all ([npc.HerHim])"), "You can't replace clothing in sex!", null);

					} else if (index == 9 && inventoryNPC != null) {
						if(!Main.sex.getSexManager().isAbleToRemoveOthersClothing(Main.game.getPlayer(), null)) {
							return new Response(UtilText.parse(inventoryNPC, "Unequip all ([npc.HerHim])"), UtilText.parse(inventoryNPC, "You can't unequip [npc.namePos] clothing in this sex scene!"), null);

						} else if(inventoryNPC.getClothingCurrentlyEquipped().isEmpty()) {
							return new Response(UtilText.parse(inventoryNPC, "Unequip all ([npc.HerHim])"), UtilText.parse(inventoryNPC, "[npc.Name] isn't wearing any clothing, so there's nothing to remove!"), null);

						} else {
							return new Response(UtilText.parse(inventoryNPC, "Unequip all ([npc.HerHim])"), UtilText.parse(inventoryNPC, "Remove as much of [npc.namePos] clothing as possible."), Main.sex.SEX_DIALOGUE){
								@Override
								public void effects(){
									responseSB.setLength(0);

									responseSB.append(unequipAll(inventoryNPC));

									Main.sex.setUnequipClothingText(null, responseSB.toString());
									Main.mainController.openInventory();
									Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
									Main.sex.setSexStarted(true);
								}
							};
						}

					} else if(index==11) {
						if(Main.game.getPlayer().getUnlockKeyMap().isEmpty()) {
							return new Response("Keys", "You do not currently own any keys which are used for unlocking certain items of clothing.", null);
							
						} else if(Main.game.getCurrentDialogueNode()==INVENTORY_MENU_KEYS) {
							return new Response("Keys", "You are already viewing a list of all the keys that you own which are used for unlocking certain items of clothing.", null);
							
						} else {
							return new Response("Keys", "View a list of all the keys that you currently own which are used for unlocking certain items of clothing.", INVENTORY_MENU_KEYS);
						}
						
					} else {
						return null;
					}
			}
			
			return null;
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};
	
	public static final DialogueNode INVENTORY_MENU_KEYS = new DialogueNode("Inventory", "Return to inventory menu.", true) {
		@Override
		public String getLabel() {
			if(!Main.game.isInNewWorld()) {
				return "Evening's Attire";
			}
			
			if (Main.game.getDialogueFlags().values.contains(DialogueFlagValue.quickTrade) && !Main.game.isInSex() && !Main.game.isInCombat()) {
				return "Inventory (Quick-Manage is <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</b>)";
			} else {
				return "Inventory";
			}
		}

		@Override
		public String getHeaderContent() {
			return inventoryView();
		}

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			
			UtilText.nodeContentSB.append("<p>");
			UtilText.nodeContentSB.append("[style.boldMinorGood(Keys owned:)]");
			if(Main.game.getPlayer().getUnlockKeyMap().isEmpty()) {
				UtilText.nodeContentSB.append("<br/>[style.italicsDisabled(None...)]");
				
			} else {
				for(Entry<String, List<InventorySlot>> entry : Main.game.getPlayer().getUnlockKeyMap().entrySet()) {
					try {
						GameCharacter npc = Main.game.getNPCById(entry.getKey());
						List<String> slots = new ArrayList<>();
						for(InventorySlot slot : entry.getValue()) {
							AbstractClothing slotClothing = npc.getClothingInSlot(slot);
							if(slotClothing!=null) {
								slots.add(Util.capitaliseSentence(slotClothing.getName())+" ('"+slot.getName()+"' slot)");
							}
						}
						if(!slots.isEmpty()) {
							UtilText.nodeContentSB.append(UtilText.parse(npc, "<br/><b style='color:"+npc.getFemininity().getColour().toWebHexString()+";'>[npc.Name]</b> ("+slots.size()+"): "));
							int i=0;
							for(String s : slots) {
								UtilText.nodeContentSB.append((i>0?", ":"")+s);
								i++;
							}
						}
					} catch (Exception e) {
					}
				}
			}
			UtilText.nodeContentSB.append("</p>");
			
			return UtilText.nodeContentSB.toString();
		}

		public String getResponseTabTitle(int index) {
			return getGeneralResponseTabTitle(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return INVENTORY_MENU.getResponse(responseTab, index);
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};
	
	public static final DialogueNode ITEM_INVENTORY = new DialogueNode("Item", "", true) {

		@Override
		public String getLabel() {
			if (Main.game.getDialogueFlags().values.contains(DialogueFlagValue.quickTrade) && !Main.game.isInSex() && !Main.game.isInCombat()) {
				return "Inventory (Quick-Manage is <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</b>)";
			} else {
				return "Inventory";
			}
		}

		@Override
		public String getHeaderContent() {
			return inventoryView();
		}
		
		@Override
		public String getContent() {
			return getItemDisplayPanel(item.getSVGString(),
					item.getDisplayName(true),
					item.getDescription()
					+ item.getExtraDescription(owner, owner)
					+ (owner!=null && owner.isPlayer()
							? (inventoryNPC != null && interactionType == InventoryInteraction.TRADING
									? "<p>"
										+(inventoryNPC.willBuy(item) && item.getItemType().isAbleToBeSold()
											?inventoryNPC.getName("The") + " will buy it for " + UtilText.formatAsMoney(item.getPrice(inventoryNPC.getBuyModifier())) + "."
											:inventoryNPC.getName("The") + " doesn't want to buy this.")
										+"</p>"
									: "")
							:(inventoryNPC != null && interactionType == InventoryInteraction.TRADING
								? "<p>"
										+ inventoryNPC.getName("The") + " will sell it for " + UtilText.formatAsMoney(item.getPrice(inventoryNPC.getSellModifier(item))) + "."
									+ "</p>" 
								: "")));
		}

		public String getResponseTabTitle(int index) {
			return getGeneralResponseTabTitle(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return getCloseInventoryResponse();
			}
			if(responseTab==0) {
				return INVENTORY_MENU.getResponse(responseTab, index);
			}
			// ****************************** ITEM BELONGS TO THE PLAYER ******************************
			if(owner != null && owner.isPlayer()) {
				
				// ****************************** Interacting with the ground ******************************
				if(inventoryNPC == null) {
					switch(interactionType) {
						case SEX:
							return getPlayerItemResponseDuringSex(item, responseTab, index);
						default:
							return getPlayerItemResponse(item, responseTab, index);
					}
					
				// ****************************** Interacting with an NPC ******************************
				} else {
					switch(interactionType) {
						case COMBAT:
							return getPlayerItemResponseToNPCDuringCombat(item, responseTab, index);
						case FULL_MANAGEMENT:  case CHARACTER_CREATION:
							return getPlayerItemResponseToNPCDuringManagement(item, responseTab, index);
						case SEX:
							return getPlayerItemResponseToNPCDuringSex(item, responseTab, index);
						case TRADING:
							return getPlayerItemResponseToNPCDuringTrading(item, responseTab, index);
					}
				}
				
			// ****************************** ITEM DOES NOT BELONG TO PLAYER ******************************
				
			} else {
				// ****************************** Interacting with the ground ******************************
				if(inventoryNPC == null) {
					switch(interactionType) {
						case SEX:
							return getItemResponseDuringSex(item, responseTab, index);
						default:
							return getItemResponse(item, responseTab, index);
					}
					
				// ****************************** Interacting with an NPC ******************************
				} else {
					switch(interactionType) {
						case COMBAT:
							return getItemResponseToNPCDuringCombat(responseTab, index);
						case FULL_MANAGEMENT:  case CHARACTER_CREATION:
							return getItemResponseToNPCDuringManagement(responseTab, index);
						case SEX:
							return getItemResponseToNPCDuringSex(responseTab, index);
						case TRADING:
							return getItemResponseToNPC(responseTab, index);
					}
				}
			}
			return null;
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};
	
	public static final DialogueNode WEAPON_INVENTORY = new DialogueNode("Weapon", "", true) {
		
		@Override
		public String getLabel() {
			if (Main.game.getDialogueFlags().values.contains(DialogueFlagValue.quickTrade) && !Main.game.isInSex() && !Main.game.isInCombat()) {
				return "Inventory (Quick-Manage is <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</b>)";
			} else {
				return "Inventory";
			}
		}
		
		@Override
		public String getHeaderContent() {
			return inventoryView();
		}
		
		@Override
		public String getContent() {
			StringBuilder sb = new StringBuilder();
			List<String> extraDescriptions = weapon.getExtraDescriptions(owner);
			if(!extraDescriptions.isEmpty()) {
				sb.append("<p>");
					for(int i=0 ; i<extraDescriptions.size() ; i++) {
						sb.append(extraDescriptions.get(i));
						if(i<extraDescriptions.size()-1) {
							sb.append("<br/>");
						}
					}
				sb.append("</p>");
			}
			return getItemDisplayPanel(weapon.getSVGString(),
					Util.capitaliseSentence(weapon.getDisplayName(true)),
					weapon.getDescription(owner)
					+ sb.toString()
					+ (owner!=null && owner.isPlayer()
							? (inventoryNPC != null && interactionType == InventoryInteraction.TRADING
									? "<p>" 
										+(inventoryNPC.willBuy(weapon)
											?inventoryNPC.getName("The") + " will buy it for " + UtilText.formatAsMoney(weapon.getPrice(inventoryNPC.getBuyModifier())) + "."
											:inventoryNPC.getName("The") + " doesn't want to buy this.")
										+"</p>"
									: "")
							:(inventoryNPC != null && interactionType == InventoryInteraction.TRADING
								? "<p>"
										+ inventoryNPC.getName("The") + " will sell it for " + UtilText.formatAsMoney(weapon.getPrice(inventoryNPC.getSellModifier(weapon))) + "."
									+ "</p>" 
								: "")));
		}


		public String getResponseTabTitle(int index) {
			return getGeneralResponseTabTitle(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return getCloseInventoryResponse();
			}
			if(responseTab==0) {
				return INVENTORY_MENU.getResponse(responseTab, index);
			}
			
			// ****************************** ITEM BELONGS TO THE PLAYER ******************************
			if(owner != null && owner.isPlayer()) {
				
				// ****************************** Interacting with the ground ******************************
				if(inventoryNPC == null) {
					switch(interactionType) {
						case SEX:
							return getPlayerItemResponseDuringSex(weapon, responseTab, index);
						default:
							return getPlayerItemResponse(weapon, responseTab, index);
					}
					
				// ****************************** Interacting with an NPC ******************************
				} else {
					switch(interactionType) {
						case COMBAT:
							return getPlayerItemResponseToNPCDuringCombat(weapon, responseTab, index);
						case FULL_MANAGEMENT:  case CHARACTER_CREATION:
							return getPlayerItemResponseToNPCDuringManagement(weapon, responseTab, index);
						case SEX:
							return getPlayerItemResponseToNPCDuringSex(weapon, responseTab, index);
						case TRADING:
							return getPlayerItemResponseToNPCDuringTrading(weapon, responseTab, index);
					}
				}
				
			// ****************************** ITEM DOES NOT BELONG TO PLAYER ******************************
				
			} else {
				// ****************************** Interacting with the ground ******************************
				if(inventoryNPC == null) {
					switch(interactionType) {
						case SEX:
							return getItemResponseDuringSex(weapon, responseTab, index);
						default:
							return getItemResponse(weapon, responseTab, index);
					}
					
				// ****************************** Interacting with an NPC ******************************
				} else {
					switch(interactionType) {
						case COMBAT:
							return getWeaponResponseToNPCDuringCombat(responseTab, index);
						case FULL_MANAGEMENT:  case CHARACTER_CREATION:
							return getWeaponResponseToNPCDuringManagement(responseTab, index);
						case SEX:
							return getWeaponResponseToNPCDuringSex(responseTab, index);
						case TRADING:
							return getWeaponResponseToNPCDuringTrading(responseTab, index);
					}
				}
			}
			return null;
		}
		
		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};
	
	public static final DialogueNode CLOTHING_INVENTORY = new DialogueNode("Clothing", "", true) {

		@Override
		public String getLabel() {
			if(!Main.game.isInNewWorld()) {
				return "Evening's Attire";
			}
			
			if (Main.game.getDialogueFlags().values.contains(DialogueFlagValue.quickTrade) && !Main.game.isInSex() && !Main.game.isInCombat()) {
				return "Inventory (Quick-Manage is <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</b>)";
			} else {
				return "Inventory";
			}
		}
		
		@Override
		public String getHeaderContent() {
			return inventoryView();
		}

		@Override
		public String getContent() {
			StringBuilder sb = new StringBuilder();
			sb.append(clothing.getDescription());
			sb.append("<p>");
				for(String s : clothing.getExtraDescriptions(null, null, true)) {
					sb.append(s+"<br/>");
				}
				for(InventorySlot is : clothing.getClothingType().getEquipSlots()) {
					List<String> descriptions = clothing.getExtraDescriptions(null, is, true);
					if(!descriptions.isEmpty()) {
						sb.append("<i>When equipped into the '"+is.getName()+"' slot:</i><br/>");
						for(String s : clothing.getExtraDescriptions(null, is, true)) {
							sb.append(s+"<br/>");
						}
					}
				}
			sb.append("</p>");
			sb.append((owner!=null && owner.isPlayer()
							? (inventoryNPC != null && interactionType == InventoryInteraction.TRADING
							? "<p>"
								+(inventoryNPC.willBuy(clothing)
									? inventoryNPC.getName("The") + " will buy it for " + UtilText.formatAsMoney(clothing.getPrice(inventoryNPC.getBuyModifier())) + "."
									: inventoryNPC.getName("The") + " doesn't want to buy this.")
								+"</p>"
							: "")
					:(inventoryNPC != null && interactionType == InventoryInteraction.TRADING
						? "<p>"
								+ inventoryNPC.getName("The") + " will sell it for " + UtilText.formatAsMoney(clothing.getPrice(inventoryNPC.getSellModifier(clothing))) + "."
							+ "</p>" 
						: "")));
			
			
			return getItemDisplayPanel(clothing.getSVGString(), clothing.getDisplayName(true), sb.toString())
					+(interactionType==InventoryInteraction.CHARACTER_CREATION?CharacterCreation.getCheckingClothingDescription():"");
		}

		public String getResponseTabTitle(int index) {
			return getGeneralResponseTabTitle(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return getCloseInventoryResponse();
			}
			if(responseTab==0) {
				return INVENTORY_MENU.getResponse(responseTab, index);
			}
			
			// ****************************** ITEM BELONGS TO THE PLAYER ******************************
			if(owner != null && owner.isPlayer()) {
				
				// ****************************** Interacting with the ground ******************************
				if(inventoryNPC == null) {

					switch(interactionType) {
						case SEX:
							return getPlayerItemResponseDuringSex(clothing, responseTab, index);
					default:
							return getPlayerItemResponse(clothing, responseTab, index);
					}
					
				// ****************************** Interacting with an NPC ******************************
				} else {
					switch(interactionType) {
						case COMBAT:
							return getPlayerItemResponseToNPCDuringCombat(clothing, responseTab, index);
						case FULL_MANAGEMENT: case CHARACTER_CREATION:
							return getPlayerItemResponseToNPCDuringManagement(clothing, responseTab, index);
						case SEX:
							return getPlayerItemResponseToNPCDuringSex(clothing, responseTab, index);
						case TRADING:
							return getPlayerItemResponseToNPCDuringTrading(clothing, responseTab, index);
					}
				}
				
			// ****************************** ITEM DOES NOT BELONG TO PLAYER ******************************
				
			} else {
				// ****************************** Interacting with the ground ******************************
				if(inventoryNPC == null) {
					switch(interactionType) {
						case CHARACTER_CREATION:
							return getClothingResponseDuringCharacterCreation(responseTab, index);
					case SEX:
						return getItemResponseDuringSex(clothing, responseTab, index);
					default:
						return getItemResponse(clothing, responseTab, index);
					}
					
				// ****************************** Interacting with an NPC ******************************
				} else {
					switch(interactionType) {
						case COMBAT:
							return getClothingResponseToNPCDuringCombat(responseTab, index);
						case FULL_MANAGEMENT: case CHARACTER_CREATION:
							return getClothingResponseToNPCDuringManagement(responseTab, index);
						case SEX:
							return getClothingResponseToNPCDuringSex(responseTab, index);
						case TRADING:
							return getClothingResponseToNPCDuringTrade(responseTab, index);
					}
				}
			}
			return null;
			
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};
	
	public static final DialogueNode WEAPON_EQUIPPED = new DialogueNode("Weapon equipped", "", true) {
		
		@Override
		public String getLabel() {
			if (Main.game.getDialogueFlags().values.contains(DialogueFlagValue.quickTrade) && !Main.game.isInSex() && !Main.game.isInCombat()) {
				return "Inventory (Quick-Manage is <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</b>)";
			} else {
				return "Inventory";
			}
		}
		
		@Override
		public String getHeaderContent() {
			return inventoryView();
		}

		@Override
		public String getContent() {
			StringBuilder sb = new StringBuilder();
			List<String> extraDescriptions = weapon.getExtraDescriptions(owner);
			if(!extraDescriptions.isEmpty()) {
				sb.append("<p>");
					for(int i=0 ; i<extraDescriptions.size() ; i++) {
						sb.append(extraDescriptions.get(i));
						if(i<extraDescriptions.size()-1) {
							sb.append("<br/>");
						}
					}
				sb.append("</p>");
			}
			return getItemDisplayPanel(weapon.getSVGEquippedString(owner),
					Util.capitaliseSentence(weapon.getDisplayName(true)),
					weapon.getDescription(owner)
					 	+sb.toString());
		}

		public String getResponseTabTitle(int index) {
			return getGeneralResponseTabTitle(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return getCloseInventoryResponse();
			}
			if(responseTab==0) {
				return INVENTORY_MENU.getResponse(responseTab, index);
			}
			
			// ****************************** ITEM BELONGS TO THE PLAYER ******************************
			if(owner != null && owner.isPlayer()) {
				switch(interactionType) {
					case COMBAT:
						if (index == 1) {
							if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
								return new Response("Drop", "You can't change weapons while fighting someone!", null);
								
							} else {
								return new Response("Store", "You can't change weapons while fighting someone!", null);
							}
							
						} else if (index==4) {
							return new Response("Dye/Reforge", "You can't alter the properties of "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" weapons in combat!", null);
							
						} else if(index == 5) {
							return new Response("Enchant", "You can't enchant equipped weapons!", null);
							
						} if(index == 6) {
							return new Response("Unequip", "You can't change weapons while fighting someone!", null);
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else {
							return null;
						}
						
					case FULL_MANAGEMENT: case TRADING: case CHARACTER_CREATION:
						if (index == 1) {
							boolean areaFull = Main.game.isPlayerTileFull() && !Main.game.getPlayerCell().getInventory().hasWeapon(weapon);
							if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
								if(!weapon.getWeaponType().isAbleToBeDropped()) {
									return new Response("Drop", "You cannot drop the " + weapon.getName() + "!", null);
									
								} else if(areaFull) {
									return new Response("Drop", "This area is full, so you can't drop your " + weapon.getName() + " here!", null);
								} else {
									return new Response("Drop", "Drop your " + weapon.getName() + ".", INVENTORY_MENU){
										@Override
										public void effects(){
											Main.game.getTextEndStringBuilder().append(
													"<p style='text-align:center;'>"
														+ (Main.game.getPlayer().unequipWeapon(weaponSlot, weapon, true, true))
													+ "</p>");
											resetPostAction();
										}
									};
								}
								
							} else {
								if(!weapon.getWeaponType().isAbleToBeDropped()) {
									return new Response("Store", "You cannot drop the " + weapon.getName() + "!", null);
									
								} else if(areaFull) {
									return new Response("Store", "This area is full, so you can't store your " + weapon.getName() + " here!", null);
								} else {
									return new Response("Store", "Store your " + weapon.getName() + " in this area.", INVENTORY_MENU){
										@Override
										public void effects(){
											Main.game.getTextEndStringBuilder().append(
													"<p style='text-align:center;'>"
														+ (Main.game.getPlayer().unequipWeapon(weaponSlot, weapon, true, true))
													+ "</p>");
											resetPostAction();
										}
									};
								}
							}
							
						} else if (index==4) {
							if (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH)
									|| Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER)
									|| Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
								return new Response("Dye/Reforge", 
										Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
											?"Use your proficiency with [style.colourEarth(Earth spells)] to alter this weapon's properties."
											:"Use a dye-brush or reforge hammer to alter this weapon's properties.",
										DYE_EQUIPPED_WEAPON) {
									@Override
									public void effects() {
										resetWeaponDyeColours();
									}
								};
							} else {
								return new Response("Dye", "You need a dye-brush or reforge hammer in order to alter this weapon's properties.", null);
							}
							
						} else if(index == 5) {
							return new Response("Enchant", "You can't enchant equipped weapons!", null);
							
						} else if(index == 6) {
							return new Response("Unequip", "Unequip the " + weapon.getName() + ".", INVENTORY_MENU){
								@Override
								public void effects(){
									Main.game.getTextEndStringBuilder().append(
											"<p style='text-align:center;'>"
												+ (Main.game.getPlayer().unequipWeapon(weaponSlot, weapon, false, true))
											+ "</p>");
									resetPostAction();
								}
							};
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else {
							return null;
						}
						
					case SEX:
						if (index == 1) {
							boolean areaFull = Main.game.isPlayerTileFull() && !Main.game.getPlayerCell().getInventory().hasWeapon(weapon);
							if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
								if(!weapon.getWeaponType().isAbleToBeDropped()) {
									return new Response("Drop", "You cannot drop the " + weapon.getName() + "!", null);
									
								} else if(areaFull) {
									return new Response("Drop", "This area is full, so you can't drop your " + weapon.getName() + " here!", null);
								} else {
									return new Response("Drop", "Drop your " + weapon.getName() + ".", Main.sex.SEX_DIALOGUE){
										@Override
										public void effects(){
											Main.sex.setUnequipWeaponText(weapon,
													"<p style='text-align:center;'>"
														+ (Main.game.getPlayer().unequipWeapon(weaponSlot, weapon, true, true))
													+ "</p>");
											resetPostAction();
											Main.mainController.openInventory();
											Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
											Main.sex.setSexStarted(true);
										}
									};
								}
								
							} else {
								if(!weapon.getWeaponType().isAbleToBeDropped()) {
									return new Response("Store", "You cannot drop the " + weapon.getName() + "!", null);
									
								} else if(areaFull) {
									return new Response("Store", "This area is full, so you can't store your " + weapon.getName() + " here!", null);
								} else {
									return new Response("Store", "Store your " + weapon.getName() + " in this area.", Main.sex.SEX_DIALOGUE){
										@Override
										public void effects(){
											Main.sex.setUnequipWeaponText(weapon,
													"<p style='text-align:center;'>"
														+ (Main.game.getPlayer().unequipWeapon(weaponSlot, weapon, true, true))
													+ "</p>");
											resetPostAction();
											Main.mainController.openInventory();
											Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
											Main.sex.setSexStarted(true);
										}
									};
								}
							}
							
						} else if (index==4) {
							return new Response("Dye/Reforge", "You can't alter the properties of "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" weapons in sex!", null);
							
						} else if(index == 5) {
							return new Response("Enchant", "You can't enchant equipped weapons!", null);
							
						} else if(index == 6) {
							return new Response("Unequip", "Unequip the " + weapon.getName() + ".", Main.sex.SEX_DIALOGUE){
								@Override
								public void effects(){
									Main.sex.setUnequipWeaponText(weapon,
											"<p style='text-align:center;'>"
												+ (Main.game.getPlayer().unequipWeapon(weaponSlot, weapon, false, true))
											+ "</p>");
									resetPostAction();
									Main.mainController.openInventory();
									Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
									Main.sex.setSexStarted(true);
								}
							};
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else {
							return null;
						}
				}
				
			// ****************************** ITEM DOES NOT BELONG TO PLAYER ******************************
				
			} else {
				switch(interactionType) {
					case COMBAT:
						if(index == 1) {
							return new Response("Drop", "You can't make someone drop their weapon while fighting them!", null);
							
						} else if(index == 2) {
							return new Response("Take", "You can't take someone's weapon while fighting them!", null);
							
						} else if (index==4) {
							return new Response("Dye/Reforge", "You can't alter the properties of someone else's equipped weapons while you're fighting them!", null);
							
						} else if(index == 5) {
							return new Response("Enchant", "You can't enchant someone else's equipped weapon, especially not while fighting them!", null);
							
						} else if (index == 6) {
							return new Response("Unequip", "You can't unequip someone's weapon while fighting them!", null);
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else {
							return null;
						}
						
					case FULL_MANAGEMENT:  case CHARACTER_CREATION:
						boolean inventoryFull = Main.game.getPlayer().isInventoryFull() && !Main.game.getPlayer().hasWeapon(weapon) && weapon.getRarity()!=Rarity.QUEST;
						
						if (index == 1) {
							boolean areaFull = Main.game.isPlayerTileFull() && !Main.game.getPlayerCell().getInventory().hasWeapon(weapon);
							if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
								if(!weapon.getWeaponType().isAbleToBeDropped()) {
									return new Response("Drop", UtilText.parse(inventoryNPC, "[npc.Name] cannot drop [npc.her] " + weapon.getName() + "!"), null);
									
								} else if(areaFull) {
									return new Response("Drop", UtilText.parse(inventoryNPC, "This area is full, so [npc.name] can't drop [npc.her] " + weapon.getName() + " here!"), null);
								} else {
									return new Response("Drop", UtilText.parse(inventoryNPC, "Get [npc.name] to drop [npc.her] " + weapon.getName() + "."), INVENTORY_MENU){
										@Override
										public void effects(){
											Main.game.getTextEndStringBuilder().append(
													"<p style='text-align:center;'>"
														+ (inventoryNPC.unequipWeapon(weaponSlot, weapon, true, false))
													+ "</p>");
											resetPostAction();
										}
									};
								}
								
							} else {
								if(!weapon.getWeaponType().isAbleToBeDropped()) {
									return new Response("Drop", UtilText.parse(inventoryNPC, "[npc.Name] cannot drop [npc.her] " + weapon.getName() + "!"), null);
									
								} else if(areaFull) {
									return new Response("Store", UtilText.parse(inventoryNPC, "This area is full, so [npc.name] can't store [npc.her] " + weapon.getName() + " here!"), null);
								} else {
									return new Response("Store", UtilText.parse(inventoryNPC, "Get [npc.name] to store [npc.her] " + weapon.getName() + " in this area."), INVENTORY_MENU){
										@Override
										public void effects(){
											Main.game.getTextEndStringBuilder().append(
													"<p style='text-align:center;'>"
														+ (inventoryNPC.unequipWeapon(weaponSlot, weapon, true, false))
													+ "</p>");
											resetPostAction();
										}
									};
								}
							}
							
						} else if (index == 2) {
							if(inventoryFull) {
								return new Response("Take", "Your inventory is full, so you can't take this!", null);
								
							} else {
								return new Response("Take",
										UtilText.parse(inventoryNPC, "Take [npc.namePos] " + weapon.getName() + " and add it to your inventory."),
										INVENTORY_MENU){
									@Override
									public void effects(){
										inventoryNPC.unequipWeaponIntoVoid(weaponSlot, weapon, true);
										Main.game.getTextEndStringBuilder().append(
												"<p style='text-align:center;'>"
													+ (Main.game.getPlayer().addWeapon(weapon, false))
												+ "</p>");
										resetPostAction();
									}
								};
							}
							
						} else if (index==4) {
							if (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH)
									|| Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER)
									|| Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
								return new Response("Dye/Reforge", 
										Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
											?"Use your proficiency with [style.colourEarth(Earth spells)] to alter this weapon's properties."
											:"Use a dye-brush or reforge hammer to alter this weapon's properties.",
										DYE_EQUIPPED_WEAPON) {
									@Override
									public void effects() {
										resetWeaponDyeColours();
									}
								};
							} else {
								return new Response("Dye", UtilText.parse(inventoryNPC, "You'll need to find a dye-brush or reforge hammer if you want to alter the properties of [npc.namePos] weapons."), null);
							}
							
						} else if(index == 5) {
							return new Response("Enchant", "You can't enchant equipped weapons!", null);
							
						} else if(index == 6) {
							if(!weapon.getWeaponType().isAbleToBeDropped()) {
								return new Response("Unequip", UtilText.parse(inventoryNPC, "As it's a unique weapon, the " + weapon.getName() + " cannot be unequipped into [npc.namePos] inventory."), null);
							}
							return new Response("Unequip", UtilText.parse(inventoryNPC, "Get [npc.name] to unequip [npc.her] " + weapon.getName() + "."), INVENTORY_MENU){
								@Override
								public void effects(){
									Main.game.getTextEndStringBuilder().append(
											"<p style='text-align:center;'>"
												+ (inventoryNPC.unequipWeapon(weaponSlot, weapon, false, false))
											+ "</p>");
									resetPostAction();
								}
							};
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else {
							return null;
						}
						
					case SEX:
						if(index == 1) {
							return new Response("Drop", "You can't unequip someone's weapon while having sex with them!", null);
							
						} else if (index == 2) {
							return new Response("Take", "You can't take someone's weapon while having sex with them!", null);
							
						} else if (index==4) {
							return new Response("Dye/Reforge", UtilText.parse(inventoryNPC, "You can't alter the properties of [npc.namePos] weapons in sex!"), null);
						
						} else if(index == 5) {
							return new Response("Enchant", "You can't enchant equipped weapons!", null);
							
						} else if(index == 6) {
							return new Response("Unequip", "You can't unequip someone's weapon while having sex with them!", null);
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else {
							return null;
						}
						
					case TRADING:
						if(index == 1) {
							return new Response("Drop", "You can't make someone drop their weapon!", null);
							
						} else if (index == 2) {
							return new Response("Take", "You can't take someone else's weapon!", null);
							
						} else if (index==4) {
							return new Response("Dye/Reforge", UtilText.parse(inventoryNPC, "You can't alter the properties of [npc.namePos] weapons!"), null);
							
						} else if(index == 5) {
							return new Response("Enchant", "You can't enchant someone else's equipped weapon!", null);
							
						} else if(index == 6) {
							return new Response("Unequip", "You can't unequip someone else's weapon!", null);
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else {
							return null;
						}
					}
				
				}
				return null;
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};
	public static final DialogueNode CLOTHING_EQUIPPED = new DialogueNode("Clothing equipped", "", true) {

		@Override
		public String getLabel() {
			if(!Main.game.isInNewWorld()) {
				return "Evening's Attire";
			}
			
			if (Main.game.getDialogueFlags().values.contains(DialogueFlagValue.quickTrade) && !Main.game.isInSex() && !Main.game.isInCombat()) {
				return "Inventory (Quick-Manage is <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</b>)";
			} else {
				return "Inventory";
			}
		}
		
		@Override
		public String getHeaderContent() {
			return inventoryView();
		}

		@Override
		public String getContent() {
			StringBuilder sb = new StringBuilder();
			sb.append(clothing.getDescription());
			sb.append("<p>");
				GameCharacter descriptionTarget = owner; //Main.game.isInSex()?owner:Main.game.getPlayer()
				for(String s : clothing.getExtraDescriptions(descriptionTarget, null, true)) {
					sb.append(s+"<br/>");
				}
				for(String s : clothing.getExtraDescriptions(descriptionTarget, clothing.getSlotEquippedTo(), true)) {
					sb.append(s+"<br/>");
				}
			sb.append("</p>");
			sb.append(Main.game.isInSex()||Main.game.isInCombat()?clothing.getDisplacementBlockingDescriptions(owner):"");
			
			return getItemDisplayPanel(clothing.getSVGEquippedString(owner), clothing.getDisplayName(true), sb.toString())
						+(interactionType==InventoryInteraction.CHARACTER_CREATION?CharacterCreation.getCheckingClothingDescription():"");
		}

		public String getResponseTabTitle(int index) {
			return getGeneralResponseTabTitle(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return getCloseInventoryResponse();
			}
			if(responseTab==0) {
				return INVENTORY_MENU.getResponse(responseTab, index);
			}
			
			// ****************************** ITEM BELONGS TO THE PLAYER ******************************
			if(owner != null && owner.isPlayer()) {
				InventorySlot slotEquippedTo = clothing.getSlotEquippedTo();
				switch(interactionType) {
					case COMBAT:
						if (index == 1) {
							if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
								return new Response("Drop", "You cannot drop the " + clothing.getName() + " while fighting someone!", null);
								
							} else {
								return new Response("Store", "You cannot drop the " + clothing.getName() + " while fighting someone!", null);
							}
							
						} else if (index==4) {
							return new Response("Dye", "You can't dye "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" clothes in combat!", null);
							
						} else if(index == 5) {
							if(clothing.isSealed()) {
								return getJinxRemovalResponse(true);
									
							} else {
								if(clothing.isCondom()) {
									if(clothing.getCondomEffect().getPotency().isNegative()) {
										return new Response("Repair (<i>1 Essence</i>)", "You can't repair equipped condoms!", null);
									}
									return new Response("Sabotage", "You can't sabotage equipped condoms!", null);
								}
								return new Response("Enchant", "You can't enchant equipped clothing!", null);
							}
							
						} else if(index == 6 && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
							return new Response("Unequip", "You can't unequip the " + clothing.getName() + " during combat!", null);
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else if (index > 10 && index - 11 < clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).size()){
								return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index -11).getDescription()),
										"You can't "+clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index -11).getDescription()
										+ " the " + clothing.getName() + " during combat!", null);
								
							
						} else {
							
							return null;
						}
						
					case FULL_MANAGEMENT: case TRADING:
						if (index == 1) {
							boolean areaFull = Main.game.isPlayerTileFull() && !Main.game.getPlayerCell().getInventory().hasClothing(clothing);
							if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
								if(!clothing.getClothingType().isAbleToBeDropped()) {
									return new Response("Drop", "You cannot drop the " + clothing.getName() + "!", null);
									
								} else if(areaFull && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
									return new Response("Drop", "This area is full, so you can't drop "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " here!", null);
								} else {
									return new Response((clothing.isDiscardedOnUnequip(slotEquippedTo)?"Discard":"Drop"),
											(clothing.isDiscardedOnUnequip(slotEquippedTo)
													?"Take off "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " and throw it away."
													:"Drop "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + "."),
											INVENTORY_MENU){
										@Override
										public void effects(){
											Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>" + unequipClothingToFloor(Main.game.getPlayer(), clothing) + "</p>");
										}
									};
								}
								
							} else {
								if(!clothing.getClothingType().isAbleToBeDropped()) {
									return new Response("Store", "You cannot drop the " + clothing.getName() + "!", null);
									
								} else if(areaFull && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
									return new Response("Store", "This area is full, so you can't store "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " here!", null);
								} else {
									return new Response((clothing.isDiscardedOnUnequip(slotEquippedTo)?"Discard":"Store"),
											(clothing.isDiscardedOnUnequip(slotEquippedTo)
													?"Take off "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " and throw it away."
													:"Store "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " in this area."),
											INVENTORY_MENU){
										@Override
										public void effects(){
											Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>" + unequipClothingToFloor(Main.game.getPlayer(), clothing) + "</p>");
										}
									};
								}
							}
							
						} else if (index==4) {
							if (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
								return new Response("Dye", 
										Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
											?"Use your proficiency with [style.colourEarth(Earth spells)] to dye this item."
											:"Use a dye-brush to dye this item of clothing.",
										DYE_EQUIPPED_CLOTHING) {
									@Override
									public void effects() {
										resetClothingDyeColours();
									}
								};
							} else {
								return new Response("Dye", "You need a dye-brush in order to dye this item of clothing.", null);
							}
							
						} else if(index == 5) {
							if(clothing.isSealed()) {
								return getJinxRemovalResponse(true);
								
							} else {
								if(clothing.isCondom()) {
									if(clothing.getCondomEffect().getPotency().isNegative()) {
										return new Response("Repair (<i>1 Essence</i>)", "You can't repair equipped condoms!", null);
									}
									return new Response("Sabotage", "You can't sabotage equipped condoms!", null);
								}
								return new Response("Enchant", "You can't enchant equipped clothing!", null);
							}
							
						} else if(index == 6 && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
							if (owner.isAbleToUnequip(clothing, true, Main.game.getPlayer())) {
								return new Response("Unequip", "Unequip the " + clothing.getName() + ".", INVENTORY_MENU){
									@Override
									public void effects(){
										Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>" + unequipClothingToInventory(Main.game.getPlayer(), clothing) + "</p>");
									}
								};
								
							} else {
								return new Response("Unequip", getClothingBlockingRemovalText(owner, "unequip"), null);
							}
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else if (index > 10 && index - 11 < clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).size()){
							
							if (clothing.getDisplacedList().contains(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11))) {
								return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11).getOppositeDescription()),
										Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11).getOppositeDescription()) + " the " + clothing.getName() + ". "
												+ clothing.getClothingBlockingDescription(
														clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11),
														Main.game.getPlayer(),
														clothing.getSlotEquippedTo(),
														" <span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>This will cover "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" ",
														".</span>"),
												CLOTHING_EQUIPPED){
									@Override
									public void effects(){
										Main.game.getPlayer().isAbleToBeReplaced(clothing, clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11), true, true, Main.game.getPlayer());
									}
								};
							} else {
								return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11).getDescription()),
										Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11).getDescription()) + " the " + clothing.getName() + ". "
												+ clothing.getClothingBlockingDescription(
														clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11),
														Main.game.getPlayer(),
														clothing.getSlotEquippedTo(),
														" <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>This will expose "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" ",
														".</span>"),
												CLOTHING_EQUIPPED){
									@Override
									public void effects(){
										Main.game.getPlayer().isAbleToBeDisplaced(clothing, clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11), true, true, Main.game.getPlayer());
									}
								};
							}
							
						} else {
							return null;
						}
						
					case CHARACTER_CREATION:
						if (index == 1) {
							if(Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.NIPPLES)
									|| Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.ANUS)
									|| Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.PENIS)
									|| Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.VAGINA)
									|| (Main.game.getPlayer().getClothingInSlot(InventorySlot.FOOT)==null && Main.game.getPlayer().getLegType().equals(LegType.HUMAN))) {
								return new Response("To the stage", "You need to be wearing clothing that covers your body, as well as a pair of shoes.", null);
								
							} else {
								return new Response("To the stage", "You're ready to approach the stage now.", CharacterCreation.CHOOSE_BACKGROUND) {
									@Override
									public void effects() {
										CharacterCreation.moveNPCIntoPlayerTile();
									}
								};
							}
							
						} else if(index == 4){
							if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty()){
								return new Response("Unequip all", "You're currently naked, there's nothing to be unequipped.", null);
							}
							else{
								return new Response("Unequip all", "Remove as much of your clothing as possible.", INVENTORY_MENU){
									@Override
									public void effects(){
										Main.game.getTextEndStringBuilder().append(unequipAll(Main.game.getPlayer()));
									}
								};
							}
							
						} else if(index == 5) {
							return new Response("Change colour", "Change the colour of this item of clothing.", DYE_EQUIPPED_CLOTHING_CHARACTER_CREATION) {
								@Override
								public void effects() {
									resetClothingDyeColours();
								}
							};
							
						} else if(index == 6) {
							return new Response("Unequip", "Unequip the " + clothing.getName() + ".", INVENTORY_MENU){
								@Override
								public void effects(){
									unequipClothingToFloor(Main.game.getPlayer(), clothing);
								}
							};
								
						} else {
							return null;
						}
						
					case SEX:
						if (index == 1) {
							if(clothing.isDiscardedOnUnequip(slotEquippedTo) && !Main.sex.getSexManager().isAbleToRemoveSelfClothing(Main.game.getPlayer())) {
								return new Response("Discard", "You can't unequip the " + clothing.getName() + " in this sex scene!", null);
							}
							boolean areaFull = Main.game.isPlayerTileFull() && !Main.game.getPlayerCell().getInventory().hasClothing(clothing);
							if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
								if(!clothing.getClothingType().isAbleToBeDropped()) {
									return new Response("Drop", "You cannot drop the " + clothing.getName() + "!", null);
									
								} else if(areaFull && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
									return new Response("Drop", "This area is full, so you can't drop "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " here!", null);
									
								} else {
									if (owner.isAbleToUnequip(clothing, false, Main.game.getPlayer())) {
										return new Response((clothing.isDiscardedOnUnequip(slotEquippedTo)?"Discard":"Drop"),
												(clothing.isDiscardedOnUnequip(slotEquippedTo)
														?"Take off "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " and throw it away."
														:"Drop "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + "."),
												Main.sex.SEX_DIALOGUE){
											@Override
											public void effects(){
												GameCharacter unequipOwner = owner;
												AbstractClothing c = clothing;
												unequipClothingToFloor(Main.game.getPlayer(), clothing);
												Main.sex.setUnequipClothingText(c, unequipOwner.getUnequipDescription());
												Main.mainController.openInventory();
												Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
												Main.sex.setSexStarted(true);
											}
										};
									} else {
										return new Response("Drop", getClothingBlockingRemovalText(owner, "unequip"), null);
									}
								}
								
							} else {
								if(!clothing.getClothingType().isAbleToBeDropped()) {
									return new Response("Store", "You cannot drop the " + clothing.getName() + "!", null);
									
								} else if(areaFull && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
									return new Response("Store", "This area is full, so you can't store "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " here!", null);
									
								} else {
									if (owner.isAbleToUnequip(clothing, false, Main.game.getPlayer())) {
										return new Response((clothing.isDiscardedOnUnequip(slotEquippedTo)?"Discard":"Store"),
												(clothing.isDiscardedOnUnequip(slotEquippedTo)
														?"Take off "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " and throw it away."
														:"Drop "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + "."),
												Main.sex.SEX_DIALOGUE){
											@Override
											public void effects(){
												GameCharacter unequipOwner = owner;
												AbstractClothing c = clothing;
												unequipClothingToFloor(Main.game.getPlayer(), clothing);
												Main.sex.setUnequipClothingText(c, unequipOwner.getUnequipDescription());
												Main.mainController.openInventory();
												Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
												Main.sex.setSexStarted(true);
											}
										};
									} else {
										return new Response("Store", getClothingBlockingRemovalText(owner, "unequip"), null);
									}
								}
							}
							
						} else if (index==4) {
							return new Response("Dye", "You can't dye "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" clothes in sex!", null);
							
						} else if(index == 5) {
							if(clothing.isSealed()) {
								return getJinxRemovalResponse(true);
								
							} else {
								if(clothing.isCondom()) {
									if(clothing.getCondomEffect().getPotency().isNegative()) {
										return new Response("Repair (<i>1 Essence</i>)", "You can't repair equipped condoms!", null);
									}
									return new Response("Sabotage", "You can't sabotage equipped condoms!", null);
								}
								return new Response("Enchant", "You can't enchant equipped clothing!", null);
							}
							
						} else if(index == 6 && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
							if(!Main.sex.getSexManager().isAbleToRemoveSelfClothing(Main.game.getPlayer())) {
								return new Response("Unequip", "You can't unequip the " + clothing.getName() + " in this sex scene!", null);
							}
							
							if (owner.isAbleToUnequip(clothing, false, Main.game.getPlayer())) {
								return new Response("Unequip", "Unequip the " + clothing.getName() + ".", Main.sex.SEX_DIALOGUE){
									@Override
									public void effects(){
										AbstractClothing c = clothing;
										unequipClothingToInventory(Main.game.getPlayer(), clothing);
										Main.sex.setUnequipClothingText(c, owner.getUnequipDescription());
										Main.mainController.openInventory();
										Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
										Main.sex.setSexStarted(true);
									}
								};
							} else {
								return new Response("Unequip", getClothingBlockingRemovalText(owner, "unequip"), null);
							}
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else if (index > 10 && index - 11 < clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).size()) {
							if (clothing.getDisplacedList().contains(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11))) {
								return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index -11).getDescription()),
										"The "+ clothing.getName()+ " "
										+(clothing.getClothingType().isPlural()?"have":"has")+" already been "
												+ clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index -11).getDescriptionPast() + "!", null);
								
							} else {
								if(!Main.sex.getSexManager().isAbleToRemoveSelfClothing(Main.game.getPlayer())) {
									return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11).getDescription()),
											"You can't can't "+clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index -11).getDescription()
											+ " "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" " + clothing.getName() + " in this sex scene!", null);
								}
								
								if(owner.isAbleToBeDisplaced(clothing, clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index -11), false, false, Main.game.getPlayer())) {
									return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11).getDescription()),
											Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11).getDescription()) + " the " + clothing.getName() + ". "
													+ clothing.getClothingBlockingDescription(
															clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11),
															Main.game.getPlayer(),
															clothing.getSlotEquippedTo(),
															" <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>This will expose "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" ",
															".</span>"),
													Main.sex.SEX_DIALOGUE){
										@Override
										public void effects(){
											Main.game.getPlayer().isAbleToBeDisplaced(clothing, clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index - 11), true, true, Main.game.getPlayer());
											Main.sex.setDisplaceClothingText(clothing, owner.getDisplaceDescription());
											Main.mainController.openInventory();
											Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
											Main.sex.setSexStarted(true);
										}
									};
								
								} else {
									return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index -11).getDescription()),
											"You can't "+clothing.getBlockedPartsKeysAsListWithoutNONE(owner, clothing.getSlotEquippedTo()).get(index -11).getDescription()
											+ " the " + clothing.getName() + ", as "+UtilText.parse(owner, "[npc.namePos] ")
											+owner.getBlockingClothing().getName()+" "+(owner.getBlockingClothing().getClothingType().isPlural()?"are":"is")+" in the way!", null);
								}
							}
							
						} else {
							return null;
						}
				}
				
			// ****************************** ITEM DOES NOT BELONG TO PLAYER ******************************
				
			} else {
				InventorySlot slotEquippedTo = clothing.getSlotEquippedTo();
				switch(interactionType) {
					case COMBAT:
						if (index == 1) {
							return new Response("Drop", "You can't make someone drop their clothing while fighting them!", null);
							
						} else if (index==4) {
							return new Response("Dye", "You can't dye someone else's equipped clothing while you're fighting them!", null);
							
						} else if(index == 5) {
							if(clothing.isSealed()) {
								return getJinxRemovalResponse(false);
								
							} else {
								if(clothing.isCondom()) {
									if(clothing.getCondomEffect().getPotency().isNegative()) {
										return new Response("Repair (<i>1 Essence</i>)", "You can't repair equipped condoms!", null);
									}
									return new Response("Sabotage", "You can't sabotage equipped condoms!", null);
								}
								return new Response("Enchant", "You can't enchant equipped clothing!", null);
							}
							
						} else if(index == 6) {
							return new Response("Unequip", "You can't unequip someone's clothing while fighting them!", null);
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else if (index > 10 && index - 11 < clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).size()){
							if (clothing.getDisplacedList().contains(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11))) {
								return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription()),
										"The "+ clothing.getName()+ " "
										+(clothing.getClothingType().isPlural()?"have":"has")+" already been "
												+ clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescriptionPast() + "!", null);
								
							} else {
								return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription()),
										"You can't "+clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription() + " the " + clothing.getName() + " while in a fight!", null);
							}
							
						} else {
							return null;
						}
						
					case FULL_MANAGEMENT: case CHARACTER_CREATION:
						boolean inventoryFull = Main.game.getPlayer().isInventoryFull() && !Main.game.getPlayer().hasClothing(clothing) && clothing.getRarity()!=Rarity.QUEST;
						
						if (index == 1) {
							boolean areaFull = Main.game.isPlayerTileFull() && !Main.game.getPlayerCell().getInventory().hasClothing(clothing);
							if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
								if(!clothing.getClothingType().isAbleToBeDropped()) {
									return new Response("Drop", "You cannot drop the " + clothing.getName() + "!", null);
									
								} else if(areaFull && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
									return new Response("Drop", UtilText.parse(inventoryNPC, "This area is full, so you can't drop [npc.namePos] " + clothing.getName() + " here!"), null);
								} else {
									return new Response((clothing.isDiscardedOnUnequip(slotEquippedTo)?"Discard":"Drop"),
											(clothing.isDiscardedOnUnequip(slotEquippedTo)
													?UtilText.parse(inventoryNPC, "Take off [npc.namePos] " + clothing.getName() + " and throw it away.")
													:UtilText.parse(inventoryNPC, "Drop [npc.namePos] " + clothing.getName() + ".")),
											INVENTORY_MENU){
										@Override
										public void effects(){
											Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>" + unequipClothingToFloor(Main.game.getPlayer(), clothing) + "</p>");
										}
									};
								}
								
							} else {
								if(!clothing.getClothingType().isAbleToBeDropped()) {
									return new Response("Store", "You cannot drop the " + clothing.getName() + "!", null);
									
								} else if(areaFull && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
									return new Response("Store", UtilText.parse(inventoryNPC, "This area is full, so you can't store [npc.namePos] " + clothing.getName() + " here!"), null);
								} else {
									return new Response((clothing.isDiscardedOnUnequip(slotEquippedTo)?"Discard":"Store"),
											(clothing.isDiscardedOnUnequip(slotEquippedTo)
													?UtilText.parse(inventoryNPC, "Take off [npc.namePos] " + clothing.getName() + " and throw it away.")
													:UtilText.parse(inventoryNPC, "Store [npc.namePos] " + clothing.getName() + " in this area.")),
											INVENTORY_MENU){
										@Override
										public void effects(){
											Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>" + unequipClothingToFloor(Main.game.getPlayer(), clothing) + "</p>");
										}
									};
								}
							}
							
						} else if(index==2) {
							if(inventoryFull) {
								return new Response("Take", "Your inventory is full, so you can't take this!", null);
								
							} else if(clothing.isDiscardedOnUnequip(slotEquippedTo)) {
								return new Response("Take", "This piece of clothing is automatically discarded when unequipped, so you can't take it!", null);
								
							} else {
								return new Response("Take",
										UtilText.parse(inventoryNPC, "Take [npc.namePos] " + clothing.getName() + " and add it to your inventory."),
										INVENTORY_MENU){
									@Override
									public void effects(){
										Main.game.getTextEndStringBuilder().append(
												"<p style='text-align:center;'>"
													+ unequipClothingToUnequippersInventory(Main.game.getPlayer(), clothing)
												+ "</p>");
									}
								};
							}
							
						} else if (index==4) {
							if (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
								return new Response("Dye", 
										Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
											?"Use your proficiency with [style.colourEarth(Earth spells)] to dye this item."
											:"Use a dye-brush to dye this item of clothing.",
										DYE_EQUIPPED_CLOTHING) {
									@Override
									public void effects() {
										resetClothingDyeColours();
									}
								};
								
							} else {
								return new Response("Dye", UtilText.parse(inventoryNPC, "You'll need to find a dye-brush if you want to dye [npc.namePos] clothes."), null);
							}
							
						} else if(index == 5) {
							if(clothing.isSealed()) {
								return getJinxRemovalResponse(false);
								
							} else {
								if(clothing.isCondom()) {
									if(clothing.getCondomEffect().getPotency().isNegative()) {
										return new Response("Repair (<i>1 Essence</i>)", "You can't repair equipped condoms!", null);
									}
									return new Response("Sabotage", "You can't sabotage equipped condoms!", null);
								}
								return new Response("Enchant", "You can't enchant equipped clothing!", null);
							}
							
						} else if(index == 6 && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
							if(!clothing.getClothingType().isAbleToBeDropped()) {
								return new Response("Unequip", UtilText.parse(inventoryNPC, "As it's a unique item, the " + clothing.getName() + " cannot be unequipped into [npc.namePos] inventory."), null);
							}
							return new Response("Unequip", "Unequip the " + clothing.getName() + ".", INVENTORY_MENU){
								@Override
								public void effects(){
									Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>" + unequipClothingToInventory(Main.game.getPlayer(), clothing) + "</p>");
								}
							};
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else if (index > 10 && index - 11 < clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).size()){
							
							if (clothing.getDisplacedList().contains(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11))) {
								return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11).getOppositeDescription()),
										Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11).getOppositeDescription()) + " the " + clothing.getName() + ". "
												+ clothing.getClothingBlockingDescription(
														clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11),
														owner,
														clothing.getSlotEquippedTo(),
														" <span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>This will cover "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" ",
														".</span>"),
												CLOTHING_EQUIPPED){
									@Override
									public void effects(){
										owner.isAbleToBeReplaced(clothing, clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11), true, true, Main.game.getPlayer());
									}
								};
							} else {
								return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11).getDescription()),
										Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11).getDescription()) + " the " + clothing.getName() + ". "
												+ clothing.getClothingBlockingDescription(
														clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11),
														owner,
														clothing.getSlotEquippedTo(),
														" <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>This will expose "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" ",
														".</span>"),
												CLOTHING_EQUIPPED){
									@Override
									public void effects(){
										owner.isAbleToBeDisplaced(clothing, clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11), true, true, Main.game.getPlayer());
										Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>" + owner.getDisplaceDescription() + "</p>");
									}
								};
							}
							
						} else {
							return null;
						}
						
					case SEX:
						if (index == 1) {
							if(clothing.isDiscardedOnUnequip(slotEquippedTo) && !Main.sex.getSexManager().isAbleToRemoveOthersClothing(Main.game.getPlayer(), clothing)) {
								return new Response("Discard", "You can't unequip the " + clothing.getName() + " in this sex scene!", null);
							}
							boolean areaFull = Main.game.isPlayerTileFull() && !Main.game.getPlayerCell().getInventory().hasClothing(clothing);
							if(Main.game.getPlayer().getLocationPlace().isItemsDisappear()) {
								if(!clothing.getClothingType().isAbleToBeDropped()) {
									return new Response("Drop", "You cannot drop the " + clothing.getName() + "!", null);
									
								} else if(!Main.sex.getSexManager().isAbleToRemoveOthersClothing(Main.game.getPlayer(), clothing)) {
									return new Response("Drop", UtilText.parse(inventoryNPC, "You can't unequip the " + clothing.getName() + " in this sex scene!"), null);
									
								} else if(areaFull && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
									return new Response("Drop", UtilText.parse(inventoryNPC, "This area is full, so you can't drop [npc.namePos] " + clothing.getName() + " here!"), null);
									
								} else {
									if (owner.isAbleToUnequip(clothing, false, Main.game.getPlayer())) {
										return new Response((clothing.isDiscardedOnUnequip(slotEquippedTo)?"Discard":"Drop"),
											(clothing.isDiscardedOnUnequip(slotEquippedTo)
													?UtilText.parse(inventoryNPC, "Take off [npc.namePos] " + clothing.getName() + " and throw it away.")
													:UtilText.parse(inventoryNPC, "Drop [npc.namePos] " + clothing.getName() + ".")),
												Main.sex.SEX_DIALOGUE){
											@Override
											public void effects(){
												AbstractClothing c = clothing;
												Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>" + unequipClothingToFloor(Main.game.getPlayer(), clothing) + "</p>");
												Main.sex.setUnequipClothingText(c, inventoryNPC.getUnequipDescription());
												Main.mainController.openInventory();
												Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
												Main.sex.setSexStarted(true);
											}
										};
									} else {
										return new Response("Drop", getClothingBlockingRemovalText(owner, "unequip"), null);
									}
								}
								
							} else {
								if(!clothing.getClothingType().isAbleToBeDropped()) {
									return new Response("Store", "You cannot drop the " + clothing.getName() + "!", null);
									
								} else if(!Main.sex.getSexManager().isAbleToRemoveOthersClothing(Main.game.getPlayer(), clothing)) {
									return new Response("Store", UtilText.parse(inventoryNPC, "You can't unequip the " + clothing.getName() + " in this sex scene!"), null);
									
								} else if(areaFull && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
									return new Response("Store", UtilText.parse(inventoryNPC, "This area is full, so you can't store [npc.namePos] " + clothing.getName() + " here!"), null);
								} else {
									if (owner.isAbleToUnequip(clothing, false, Main.game.getPlayer())) {
										return new Response((clothing.isDiscardedOnUnequip(slotEquippedTo)?"Discard":"Store"),
												(clothing.isDiscardedOnUnequip(slotEquippedTo)
														?UtilText.parse(inventoryNPC, "Take off [npc.namePos] " + clothing.getName() + " and throw it away.")
														:UtilText.parse(inventoryNPC, "Store [npc.namePos] " + clothing.getName() + " in this area.")),
												Main.sex.SEX_DIALOGUE){
											@Override
											public void effects(){
												AbstractClothing c = clothing;
												Main.game.getTextEndStringBuilder().append("<p style='text-align:center;'>" + unequipClothingToFloor(Main.game.getPlayer(), clothing) + "</p>");
												Main.sex.setUnequipClothingText(c, inventoryNPC.getUnequipDescription());
												Main.mainController.openInventory();
												Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
												Main.sex.setSexStarted(true);
											}
										};
									} else {
										return new Response("Store", getClothingBlockingRemovalText(owner, "unequip"), null);
									}
								}
							}
							
						} else if (index==4) {
							return new Response("Dye", UtilText.parse(inventoryNPC, "You can't dye [npc.namePos] clothes in sex!"), null);
							
						} else if(index == 5) {
							if(clothing.isSealed()) {
								return getJinxRemovalResponse(false);
								
							} else {
								if(clothing.isCondom()) {
									if(clothing.getCondomEffect().getPotency().isNegative()) {
										return new Response("Repair (<i>1 Essence</i>)", "You can't repair equipped condoms!", null);
									}
									return new Response("Sabotage", "You can't sabotage equipped condoms!", null);
								}
								return new Response("Enchant", "You can't enchant equipped clothing!", null);
							}
							
						} else if(index == 6 && !clothing.isDiscardedOnUnequip(slotEquippedTo)) {
							if(!Main.sex.getSexManager().isAbleToRemoveOthersClothing(Main.game.getPlayer(), clothing)) {
								return new Response("Unequip", "You can't unequip the " + clothing.getName() + " in this sex scene!", null);
							}
							
							if (owner.isAbleToUnequip(clothing, false, Main.game.getPlayer())) {
								return new Response("Unequip", "Unequip the " + clothing.getName() + ".", Main.sex.SEX_DIALOGUE){
									@Override
									public void effects(){
										AbstractClothing c = clothing;
										unequipClothingToInventory(Main.game.getPlayer(), clothing);
										Main.sex.setUnequipClothingText(c, inventoryNPC.getUnequipDescription());
										Main.mainController.openInventory();
										Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
										Main.sex.setSexStarted(true);
									}
								};
							} else {
								return new Response("Unequip", getClothingBlockingRemovalText(owner, "unequip"), null);
							}
							
						} else if (index == 10) {
							return getQuickTradeResponse();
							
						} else if (index > 10 && index - 11 < clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).size()){
							if (clothing.getDisplacedList().contains(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11))) {
								return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription()),
										"The "+ clothing.getName()+ " "
										+(clothing.getClothingType().isPlural()?"have":"has")+" already been "
												+ clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescriptionPast() + "!", null);
								
							} else {
								if(owner.isAbleToBeDisplaced(clothing, clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11), false, false, Main.game.getPlayer())){
									
									if(!Main.sex.getSexManager().isAbleToRemoveOthersClothing(Main.game.getPlayer(), clothing)) {
										return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11).getDescription()),
												"You "+clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription() + " the " + clothing.getName() + " in this sex scene!", null);
									}
									
									return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11).getDescription()),
											Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11).getDescription()) + " the " + clothing.getName() + ". "
													+ clothing.getClothingBlockingDescription(
															clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11),
															owner,
															clothing.getSlotEquippedTo(),
															" <span style='color:" + PresetColour.GENERIC_SEX.toWebHexString() + ";'>This will expose "+(owner.isPlayer()?"your":owner.getName("")+"'s")+" ",
															".</span>"),
													Main.sex.SEX_DIALOGUE){
										@Override
										public void effects(){
											owner.isAbleToBeDisplaced(clothing, clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11), true, true, Main.game.getPlayer());
											Main.sex.setDisplaceClothingText(clothing, owner.getDisplaceDescription());
											Main.mainController.openInventory();
											Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
											Main.sex.setSexStarted(true);
										}
									};
								
								} else {
									return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription()),
											"You can't "+clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription()
											+ " the " + clothing.getName() + ", as "+UtilText.parse(owner, "[npc.namePos] ")
											+owner.getBlockingClothing().getName()+" "+(owner.getBlockingClothing().getClothingType().isPlural()?"are":"is")+" in the way!", null);
								}
							}
							
						} else {
							return null;
						}
						
						case TRADING:
							if (index == 1) {
								return new Response("Drop", UtilText.parse(inventoryNPC, "You can't make [npc.name] drop [npc.her] clothing!"), null);
								
							} else if (index==4) {
								return new Response("Dye", UtilText.parse(inventoryNPC, "You can't dye [npc.namePos] clothes!"), null);
								
							}  else if(index == 5) {
								if(clothing.isCondom()) {
									if(clothing.getCondomEffect().getPotency().isNegative()) {
										return new Response("Repair (<i>1 Essence</i>)", "You can't repair [npc.namePos] condom!", null);
									}
									return new Response("Sabotage", "You can't sabotage [npc.namePos] condom!", null);
								}
								return new Response("Enchant", UtilText.parse(inventoryNPC, "You can't enchant [npc.namePos] clothing!"), null);
								
							} else if(index == 6) {
								return new Response("Unequip", UtilText.parse(inventoryNPC, "You can't unequip [npc.namePos] clothing!"), null);
								
							} else if (index == 10) {
								return getQuickTradeResponse();
								
							} else if (index > 10 && index - 11 < clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).size()){
								if (clothing.getDisplacedList().contains(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index - 11))) {
									return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription()),
											"The "+ clothing.getName()+ " "
													+(clothing.getClothingType().isPlural()?"have":"has")+" already been "
													+ clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescriptionPast() + "!", null);
									
								} else {
									return new Response(Util.capitaliseSentence(clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription()),
											"You can't "+clothing.getBlockedPartsKeysAsListWithoutNONE(inventoryNPC, clothing.getSlotEquippedTo()).get(index -11).getDescription() + " the " + clothing.getName() + "!", null);
								}
								
							} else {
								return null;
							}
					}
				
				}
				return null;
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};
	
	private static String getClothingDyeUI() {
		InventorySlot slotEquippedTo = clothing.getSlotEquippedTo();
		if(slotEquippedTo==null) {
			slotEquippedTo = clothing.getClothingType().getEquipSlots().get(0);
		}
		
		inventorySB = new StringBuilder(
				"<div class='container-full-width'>"
					+ "<div class='inventoryImage'>"
						+ "<div class='inventoryImage-content'>"
							+ clothing.getSVGString()
						+ "</div>"
					+ "</div>"
					+ "<h3 style='text-align:center;'><b>"+clothing.getDisplayName(true)+"</b></h3>"
					+ "<p>"
						+ "Select the desired colours from the coloured buttons below, and after using the preview to see how the new clothing will look, press the 'Dye' option at the bottom of the screen to apply your changes."
					+ "</p>"
				+ "</div>"
					
				+ "<br/>"
				
				+ "<div class='container-full-width'>"
					+ "<div class='inventoryImage'>"
						+ "<div class='inventoryImage-content'>"
							+ clothing.getClothingType().getSVGImage(slotEquippedTo,
									dyePreviews,
									dyePreviewPattern,
									dyePreviewPatterns,
									getDyePreviewStickersAsStrings())
						+ "</div>"
					+ "</div>");
		
		inventorySB.append("<h3 style='text-align:center;'><b>Dye & Preview</b></h3>");
		
		if(!clothing.getClothingType().getStickers().isEmpty()) { //TODO test stickers
			StringBuilder stickerSB = new StringBuilder();
			boolean stickerFound = false;
			List<StickerCategory> orderedCategories = new ArrayList<>(clothing.getClothingType().getStickers().keySet());
			Collections.sort(orderedCategories, (s1, s2)->s1.getPriority()-s2.getPriority());
			
			for(StickerCategory cat : orderedCategories) {
				stickerSB.append("<div class='container-quarter-width' style='width:calc(75% - 16px); margin:0 8px; padding:0;'>");
					stickerSB.append("<div class='container-quarter-width' style='margin:0; padding-top:6px; width:20%;'>");
						stickerSB.append(Util.capitaliseSentence(cat.getName())+":"); // Category name
					stickerSB.append("</div>");
					
					stickerSB.append("<div class='container-quarter-width' style='margin:0; padding:0; width:80%;'>");
						List<Sticker> orderedStickers = new ArrayList<>(clothing.getClothingType().getStickers().get(cat));
						Collections.sort(orderedStickers, (s1, s2)->s1.getPriority()-s2.getPriority());
						for(Sticker sticker : orderedStickers) {
							String requirements = UtilText.parse(sticker.getUnavailabilityText()).trim();
							if(requirements.isEmpty() || sticker.isShowDisabledButton()) {
								boolean specialSticker = !sticker.getAvailabilityText().isEmpty() || !sticker.getTagsApplied().isEmpty() || !sticker.getTagsRemoved().isEmpty();
								stickerFound = true;
								String id = "ITEM_STICKER_"+cat.getId()+sticker.getId();
								if(!requirements.isEmpty()) {
									stickerSB.append(
											"<div id='"+id+"' class='cosmetics-button disabled'>"
													+ "<b style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>" + Util.capitaliseSentence(sticker.getName()) + (specialSticker?"*":"") + "</b>"
											+ "</div>");
									
								} else if(dyePreviewStickers.get(cat)==sticker) {
									stickerSB.append(
											"<div id='"+id+"' class='cosmetics-button active'>"
													+ "<b style='color:" + sticker.getColourSelected().toWebHexString() + ";'>" + Util.capitaliseSentence(sticker.getName()) + (specialSticker?"*":"") + "</b>"
											+ "</div>");
								} else {
									stickerSB.append(
											"<div id='"+id+"' class='cosmetics-button'>"
													+ "<span style='color:"+sticker.getColourDisabled().toWebHexString()+";'>" + Util.capitaliseSentence(sticker.getName()) + (specialSticker?"*":"") + "</span>"
											+ "</div>");
								}
							}
						}
					stickerSB.append("</div>");
				stickerSB.append("</div>");
				
				if(stickerFound) {
					stickerFound = false;
					inventorySB.append(stickerSB.toString());
					stickerSB = new StringBuilder();
				}
			}
		}
		
		for(int i=0; i<clothing.getClothingType().getColourReplacements().size(); i++) {
			ColourReplacement cr = clothing.getClothingType().getColourReplacement(i);
			if(!cr.getAllColours().isEmpty()) {
				inventorySB.append("<div class='container-quarter-width' style='width:calc(75% - 16px);'>"
							+ Util.capitaliseSentence(Util.intToPrimarySequence(i+1))+" Colour"+(cr.isRecolouringAllowed()?"":" ([style.italicsBad(cannot be changed)])")+":<br/>");
				
				for(Colour c : cr.getAllColours()) {
					inventorySB.append("<div class='normal-button"+(dyePreviews.size()>i && dyePreviews.get(i)==c?" selected":"")+"' id='DYE_CLOTHING_"+i+"_"+c.getId()+"'"
										+ " style='width:auto; margin-right:4px; border-width:1px;"
											+(cr.getDefaultColours().contains(c)
												?"border-color:"+PresetColour.TEXT_GREY.toWebHexString()+";"
												:"")
											+(dyePreviews.size()>i && dyePreviews.get(i)==c
												?" background-color:"+PresetColour.BASE_GREEN.getShades()[4]+";"
												:"")+"'>"
									+ "<div class='phone-item-colour' style='"
										+ (c.isMetallic()
												?"background: repeating-linear-gradient(135deg, " + c.toWebHexString() + ", " + c.getShades()[4] + " 10px);"
												:"background-color:" + c.toWebHexString() + ";")
										+ "'></div>"
						+ "</div>");
				}
				inventorySB.append("</div>");
			}
		}
		
		if(clothing.getClothingType().isPatternAvailable()){
			inventorySB.append(
					"<br/>"
					+ "<div class='container-full-width'>"
					+ "Pattern:<br/>");
	 
			for (Pattern pattern : Pattern.getAllPatterns()) {
				if (dyePreviewPattern.equals(pattern.getId())) {
					inventorySB.append(
							"<div class='cosmetics-button active'>"
								+ "<b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>" + Util.capitaliseSentence(pattern.getNiceName()) + "</b>"
							+ "</div>");
				} else {
					inventorySB.append(
							"<div id='ITEM_PATTERN_"+pattern.getId()+"' class='cosmetics-button'>"
							+ "<span style='color:"+PresetColour.TRANSFORMATION_GENERIC.getShades()[0]+";'>" + Util.capitaliseSentence(pattern.getNiceName()) + "</span>"
							+ "</div>");
				}
			}
			inventorySB.append("</div>");

			for(int i=0; i<clothing.getClothingType().getPatternColourReplacements().size(); i++) {
				ColourReplacement cr = clothing.getClothingType().getPatternColourReplacement(i);
				if(!cr.getAllColours().isEmpty() && Pattern.getPattern(dyePreviewPattern).isRecolourAvailable(cr)) {
					inventorySB.append("<div class='container-quarter-width' style='width:calc(75% - 16px);'>"
								+ "Pattern "+Util.capitaliseSentence(Util.intToPrimarySequence(i+1))+" Colour:<br/>");
					
					for (Colour c : cr.getAllColours()) {
						inventorySB.append("<div class='normal-button"+(dyePreviewPatterns.size()>i && dyePreviewPatterns.get(i)==c?" selected":"")+"' id='DYE_CLOTHING_PATTERN_"+i+"_"+c.getId()+"'"
											+ " style='width:auto; margin-right:4px;"+(dyePreviewPatterns.size()>i && dyePreviewPatterns.get(i)==c?" background-color:"+PresetColour.BASE_GREEN.getShades()[4]+";":"")+"'>"
										+ "<div class='phone-item-colour' style='"
											+ (c.isMetallic()
													?"background: repeating-linear-gradient(135deg, " + c.toWebHexString() + ", " + c.getShades()[4] + " 10px);"
													:"background-color:" + c.toWebHexString() + ";")
											+ "'></div>"
							+ "</div>");
					}
					inventorySB.append("</div>");
				}
			}
		}
		inventorySB.append("</div>");
		
		return inventorySB.toString();
	}
	
	private static String getWeaponDyeUI() {
		inventorySB = new StringBuilder(
				"<div class='container-full-width'>"
					+ "<div class='inventoryImage'>"
						+ "<div class='inventoryImage-content'>"
							+ weapon.getSVGString()
						+ "</div>"
					+ "</div>"
					+ "<h3 style='text-align:center;'><b>"+weapon.getDisplayName(true)+"</b></h3>"
					+ "<p>"
						+ "Select the desired colours from the coloured buttons below, and after using the preview to see how the new weapon will look, press the 'Dye' option at the bottom of the screen to apply your changes."
					+ "</p>"
				+ "</div>"
				+ "<br/>"
				+ "<div class='container-full-width'>"
					+ "<div class='container-full-width' style='text-align:center; width:calc(25% - 16px); float:right;'>"
						+ "<div class='inventoryImage' style='width:100%;'>"
							+ (weapon.getWeaponType().isEquippedSVGImageDifferent()
								?"Unequipped"
								:"")
							+ "<div class='inventoryImage-content'>"
								+ weapon.getWeaponType().getSVGImage(damageTypePreview, dyePreviews)
							+ "</div>"
						+ "</div>"
						+(weapon.getWeaponType().isEquippedSVGImageDifferent()
							?"<div class='inventoryImage' style='width:100%;'>"
								+ "Equipped"
									+ "<div class='inventoryImage-content'>"
										+ weapon.getWeaponType().getSVGEquippedImage(Main.game.getPlayer(), damageTypePreview, dyePreviews)
									+ "</div>"
								+ "</div>"
							:"")
					+ "</div>"
					+ "<h3 style='text-align:center;'><b>Dye & Preview</b></h3>");
		
		
		inventorySB.append("<div class='container-quarter-width' style='text-align:center;width:calc(75% - 16px);'>"
				+ "<b>Damage type:</b><br/>");
		for(DamageType dt : weapon.getWeaponType().getAvailableDamageTypes()) {
			inventorySB.append("<div class='normal-button"+(damageTypePreview==dt?" selected":"")+"' id='DAMAGE_TYPE_"+dt.toString()+"'"
							+ "style='width:20%; margin:0 2.5%; color:"+(damageTypePreview==dt?dt.getColour().toWebHexString():dt.getColour().getShades(8)[0])+";'>"
						+ Util.capitaliseSentence(dt.getName())
					+ "</div>");
		}
		inventorySB.append("</div>");

		boolean colourOptions = false;

		for(int i=0; i<weapon.getWeaponType().getColourReplacements(false).size(); i++) {
			colourOptions = true;
			ColourReplacement cr = weapon.getWeaponType().getColourReplacement(false, i);
			if(!cr.getAllColours().isEmpty()) {
				inventorySB.append("<div class='container-quarter-width' style='width:calc(75% - 16px);'>"
						+ Util.capitaliseSentence(Util.intToPrimarySequence(i+1))+" Colour"+(cr.isRecolouringAllowed()?"":" ([style.italicsBad(cannot be changed)])")+":<br/>");
				
				for(Colour c : cr.getAllColours()) {
					inventorySB.append("<div class='normal-button"+(dyePreviews.size()>i && dyePreviews.get(i)==c?" selected":"")+"' id='DYE_WEAPON_"+i+"_"+c.getId()+"'"
										+ " style='width:auto; margin-right:4px; border-width:1px;"
											+(cr.getDefaultColours().contains(c)
												?"border-color:"+PresetColour.TEXT_GREY.toWebHexString()+";"
												:"")
											+(dyePreviews.size()>i && dyePreviews.get(i)==c
												?" background-color:"+PresetColour.BASE_GREEN.getShades()[4]+";"
												:"")
										+"'>"
									+ "<div class='phone-item-colour' style='"
										+ (c.isMetallic()
												?"background: repeating-linear-gradient(135deg, " + c.toWebHexString() + ", " + c.getShades()[4] + " 10px);"
												:"background-color:" + c.toWebHexString() + ";")
										+ "'></div>"
						+ "</div>");
				}
				inventorySB.append("</div>");
			}
		}
		
		if(!colourOptions) {
			inventorySB.append("<div class='container-half-width' style='text-align:center;'>"
					+ "[style.colourDisabled(No dye options available...)]"
					+ "</div>");
		}

		inventorySB.append("</div>");
		
		return inventorySB.toString();
	}
	
	public static final DialogueNode DYE_CLOTHING = new DialogueNode("Dye clothing", "", true) {

		@Override
		public String getContent() {
			return getClothingDyeUI();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("Back", "Return to the previous menu.", INVENTORY_MENU);

			} else if (index == 1) {
				if(dyePreviews.equals(clothing.getColours())
						&& dyePreviewPattern.equals(clothing.getPattern())
						&& dyePreviewPatterns.equals(clothing.getPatternColours())
						&& dyePreviewStickers.equals(clothing.getStickersAsObjects())) {
					return new Response("Dye",
							"You need to choose different colours before being able to dye the " + clothing.getName() + "!",
							null); 
				}
				
				return new Response("Dye",
						"Dye the " + clothing.getName() + " in the colours you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye it a different colour at any time."
										:" This action is permanent, and you'll need another dye-brush if you want to change its colour again."),
						INVENTORY_MENU) {
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), owner, false);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(clothing, dyePreviews.get(0))
									+ "</p>"
									+ "<p>"
										+ "<b>The " + clothing.getName() + " " + (clothing.getClothingType().isPlural() ? "have been" : "has been") + " dyed</b>!"
									+ "</p>"
									+ "<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye the " + clothing.getName() + " without needing to use a dye-brush!"
										+ "</p>");
						}
						
						if(owner!=null) {
							owner.removeClothing(clothing);
							AbstractClothing dyedClothing = new AbstractClothing(clothing) {};
							dyedClothing.setColours(dyePreviews);
							dyedClothing.setPattern(dyePreviewPattern);
							dyedClothing.setPatternColours(dyePreviewPatterns);
							dyedClothing.setStickersAsObjects(dyePreviewStickers);
							owner.addClothing(dyedClothing, false);
							Main.game.addEvent(new EventLogEntry("Dyed", dyedClothing.getDisplayName(true)), false);

						} else {
							Main.game.getPlayerCell().getInventory().removeClothing(clothing);
							AbstractClothing dyedClothing = new AbstractClothing(clothing) {};
							dyedClothing.setColours(dyePreviews);
							dyedClothing.setPattern(dyePreviewPattern);
							dyedClothing.setPatternColours(dyePreviewPatterns);
							dyedClothing.setStickersAsObjects(dyePreviewStickers);
							Main.game.getPlayerCell().getInventory().addClothing(dyedClothing);
							Main.game.addEvent(new EventLogEntry("Dyed", dyedClothing.getDisplayName(true)), false);
						}
						
					}
				};

			} else if (index == 6) {
				if(dyePreviews.equals(clothing.getColours())
						&& dyePreviewPattern.equals(clothing.getPattern())
						&& dyePreviewPatterns.equals(clothing.getPatternColours())
						&& dyePreviewStickers.equals(clothing.getStickersAsObjects())) {
					return new Response("Dye all (stack)",
							"You need to choose different colours before being able to dye the " + clothing.getName() + "!",
							null); 
				}
				
				int stackCount = 0;
				if(owner!=null) {
					stackCount = owner.getClothingCount(clothing);
				} else {
					stackCount = Main.game.getPlayerCell().getInventory().getClothingCount(clothing);
				}

				int finalCount = stackCount;
				
				if(stackCount==1) {
					return new Response("Dye all (stack)",
							"You only have one "+clothing.getName()+", so you should dye it using the individual action...",
							null); 
				}
				
				if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					int dyeBrushCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH));
					
					if(dyeBrushCount<stackCount) {
						return new Response("Dye all (stack)",
								"You do not have enough dye brushes to dye all the " + clothing.getNamePlural() + "! You have "+dyeBrushCount+" dye brushes, but there are "+stackCount+" "+clothing.getNamePlural()+" in this stack...",
								null); 
					}
				}
				
				return new Response("Dye all (stack)",
						"Dye all " + clothing.getNamePlural() + " which are in this clothing stack ("+stackCount+" in total) in the colours you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye them a different colour at any time."
										:" This action is permanent, and you'll need another dye-brush if you want to change their colour again."),
						INVENTORY_MENU) {
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().removeItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), finalCount);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(clothing, dyePreviews.get(0))
									+ "</p>"
									+ "<p>"
										+ "<b>The "+clothing.getName()+(clothing.getClothingType().isPlural()?"have":"has")+" been dyed</b>!"
									+ "</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>You then repeat this for the other "+Util.intToString(finalCount-1)+" "+clothing.getNamePlural()+"...</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye "
												+ (finalCount==2
													?"both"
													:"all "+Util.intToString(finalCount))
												+" of the " + clothing.getNamePlural() + " without needing to use a single dye-brush!"
										+ "</p>");
						}
						
						if(owner!=null) {
							owner.removeClothing(clothing, finalCount);
							AbstractClothing dyedClothing = new AbstractClothing(clothing) {};
							dyedClothing.setColours(dyePreviews);
							dyedClothing.setPattern(dyePreviewPattern);
							dyedClothing.setPatternColours(dyePreviewPatterns);
							dyedClothing.setStickersAsObjects(dyePreviewStickers);
							owner.addClothing(dyedClothing, finalCount, false, false);
							Main.game.addEvent(new EventLogEntry("Dyed", dyedClothing.getDisplayName(true)), false);

						} else {
							Main.game.getPlayerCell().getInventory().removeClothing(clothing, finalCount);
							AbstractClothing dyedClothing = new AbstractClothing(clothing) {};
							dyedClothing.setColours(dyePreviews);
							dyedClothing.setPattern(dyePreviewPattern);
							dyedClothing.setPatternColours(dyePreviewPatterns);
							dyedClothing.setStickersAsObjects(dyePreviewStickers);
							Main.game.getPlayerCell().getInventory().addClothing(dyedClothing, finalCount);
							Main.game.addEvent(new EventLogEntry("Dyed", dyedClothing.getDisplayName(true)), false);
						}
					}
				};

			} else if (index == 11) {
				if(dyePreviews.equals(clothing.getColours())
						&& dyePreviewPattern.equals(clothing.getPattern())
						&& dyePreviewPatterns.equals(clothing.getPatternColours())
						&& dyePreviewStickers.equals(clothing.getStickersAsObjects())) {
					return new Response("Dye all",
							"You need to choose different colours before being able to dye the " + clothing.getName() + "!",
							null); 
				}
				
				List<AbstractClothing> clothingMatches = new ArrayList<>();
				int stackCount = 0;
				if(owner!=null) {
					for(Entry<AbstractClothing, Integer> entry : owner.getAllClothingInInventory().entrySet()) {
						if(entry.getKey().getClothingType().equals(clothing.getClothingType())) {
							clothingMatches.add(entry.getKey());
							stackCount += entry.getValue();
						}
					}
				} else {
					for(Entry<AbstractClothing, Integer> entry : Main.game.getPlayerCell().getInventory().getAllClothingInInventory().entrySet()) {
						if(entry.getKey().getClothingType().equals(clothing.getClothingType())) {
							clothingMatches.add(entry.getKey());
							stackCount += entry.getValue();
						}
					}
				}
				
				int finalCount = stackCount;
				
				if(stackCount==1) {
					return new Response("Dye all",
							"You only have one "+clothing.getName()+", so you should dye it using the individual action...",
							null); 
				}
				
				if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					int dyeBrushCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH));
					
					if(dyeBrushCount<stackCount) {
						return new Response("Dye all",
								"You do not have enough dye brushes to dye all the " + clothing.getNamePlural() + "! You have "+dyeBrushCount+" dye brushes, but there are "+stackCount+" "+clothing.getNamePlural()+" in total...",
								null); 
					}
				}
				
				return new Response("Dye all",
						"Dye all " + clothing.getNamePlural() + " which are in this clothing stack ("+stackCount+" in total) in the colours you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye them a different colour at any time."
										:" This action is permanent, and you'll need another dye-brush if you want to change their colour again."),
						INVENTORY_MENU) {
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().removeItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), finalCount);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(clothing, dyePreviews.get(0))
									+ "</p>"
									+ "<p>"
									+ "<b>The "+clothing.getName()+(clothing.getClothingType().isPlural()?"have":"has")+" been dyed</b>!"
									+ "</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>You then repeat this for the other "+Util.intToString(finalCount-1)+" "+clothing.getNamePlural()+"...</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye "
												+ (finalCount==2
													?"both"
													:"all "+Util.intToString(finalCount))
												+" of the " + clothing.getNamePlural() + " without needing to use a single dye-brush!"
										+ "</p>");
						}
						
						if(owner!=null) {
							for(AbstractClothing c : clothingMatches) {
								int clothingCount = owner.getAllClothingInInventory().get(c);
								owner.removeClothing(c, clothingCount);
								AbstractClothing dyedClothing = new AbstractClothing(c) {};
								dyedClothing.setColours(dyePreviews);
								dyedClothing.setPattern(dyePreviewPattern);
								dyedClothing.setPatternColours(dyePreviewPatterns);
								dyedClothing.setStickersAsObjects(dyePreviewStickers);
								owner.addClothing(dyedClothing, clothingCount, false, false);
								Main.game.addEvent(new EventLogEntry("Dyed", dyedClothing.getDisplayName(true)), false);
							}
							
						} else {
							for(AbstractClothing c : clothingMatches) {
								int clothingCount = Main.game.getPlayerCell().getInventory().getAllClothingInInventory().get(c);
								Main.game.getPlayerCell().getInventory().removeClothing(c, clothingCount);
								AbstractClothing dyedClothing = new AbstractClothing(c) {};
								dyedClothing.setColours(dyePreviews);
								dyedClothing.setPattern(dyePreviewPattern);
								dyedClothing.setPatternColours(dyePreviewPatterns);
								dyedClothing.setStickersAsObjects(dyePreviewStickers);
								Main.game.getPlayerCell().getInventory().addClothing(dyedClothing, clothingCount);
								Main.game.addEvent(new EventLogEntry("Dyed", dyedClothing.getDisplayName(true)), false);
							}
						}
					}
				};

			} else {
				return null;
			}
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};

	public static final DialogueNode DYE_EQUIPPED_CLOTHING = new DialogueNode("Dye clothing", "", true) {

		@Override
		public String getContent() {
			return getClothingDyeUI();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("Back", "Return to the previous menu.", INVENTORY_MENU);

			} else if (index == 1) {
				if(dyePreviews.equals(clothing.getColours())
						&& dyePreviewPattern.equals(clothing.getPattern())
						&& dyePreviewPatterns.equals(clothing.getPatternColours())
						&& dyePreviewStickers.equals(clothing.getStickersAsObjects())) {
					return new Response("Dye",
							"You need to choose different colours before being able to dye the " + clothing.getName() + "!",
							null); 
				}
				
				return new Response("Dye",
								"Dye the " + clothing.getName() + " in the colours you have chosen."
										+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye it a different colour at any time."
												:" This action is permanent, and you'll need another dye-brush if you want to change its colour again."),
								INVENTORY_MENU){
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), owner, false);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(clothing, dyePreviews.get(0))
									+ "</p>"
									+ "<p>"
										+ "<b>The " + clothing.getName() + " " + (clothing.getClothingType().isPlural() ? "have been" : "has been") + " dyed</b>!"
									+ "</p>"
									+ "<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye the " + clothing.getName() + " without needing to use a dye-brush!"
										+ "</p>");
						}
						
						clothing.setColours(dyePreviews);
						clothing.setPattern(dyePreviewPattern);
						clothing.setPatternColours(dyePreviewPatterns);
						clothing.setStickersAsObjects(dyePreviewStickers);
						
						Main.game.addEvent(new EventLogEntry("Dyed", clothing.getDisplayName(true)), false);
					}
				};

			} else
				return null;
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};
	
	
	public static final DialogueNode DYE_CLOTHING_CHARACTER_CREATION = new DialogueNode("Choose Colour", "", true) {

		@Override
		public String getContent() {
			return getClothingDyeUI();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("Back", "Return to the previous menu.", CLOTHING_INVENTORY);

			} else if (index == 1) {
				if(dyePreviews.equals(clothing.getColours())
						&& dyePreviewPattern.equals(clothing.getPattern())
						&& dyePreviewPatterns.equals(clothing.getPatternColours())
						&& dyePreviewStickers.equals(clothing.getStickersAsObjects())) {
					return new Response("Dye",
							"You need to choose different colours before being able to dye the " + clothing.getName() + "!",
							null); 
				}
				
				return new Response("Dye",
						"Change the colour of the " + clothing.getName() + " to the colours you have chosen.",
						INVENTORY_MENU){
					@Override
					public void effects(){
						Main.game.getPlayerCell().getInventory().removeClothing(clothing);
						AbstractClothing dyedClothing = new AbstractClothing(clothing) {};
						dyedClothing.setColours(dyePreviews);
						dyedClothing.setPattern(dyePreviewPattern);
						dyedClothing.setPatternColours(dyePreviewPatterns);
						dyedClothing.setStickersAsObjects(dyePreviewStickers);
						clothing = dyedClothing;
						Main.game.getPlayerCell().getInventory().addClothing(dyedClothing);
					}
				};

			} else
				return null;
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};

	public static final DialogueNode DYE_EQUIPPED_CLOTHING_CHARACTER_CREATION = new DialogueNode("Choose Colour", "", true) {

		@Override
		public String getContent() {
			return getClothingDyeUI();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("Back", "Return to the previous menu.", CLOTHING_EQUIPPED);

			} else if (index  == 1) {
				if(dyePreviews.equals(clothing.getColours())
						&& dyePreviewPattern.equals(clothing.getPattern())
						&& dyePreviewPatterns.equals(clothing.getPatternColours())
						&& dyePreviewStickers.equals(clothing.getStickersAsObjects())) {
					return new Response("Dye",
							"You need to choose different colours before being able to dye the " + clothing.getName() + "!",
							null); 
				}
				
				return new Response("Dye",
						"Change the colour of the " + clothing.getName() + " to the colours you have chosen.",
						CLOTHING_EQUIPPED){
					@Override
					public void effects(){
						clothing.setColours(dyePreviews);
						clothing.setPattern(dyePreviewPattern);
						clothing.setPatternColours(dyePreviewPatterns);
						clothing.setStickersAsObjects(dyePreviewStickers);
					}
				};

			} else
				return null;
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};

	public static final DialogueNode DYE_WEAPON = new DialogueNode("Dye Weapon", "", true) {

		@Override
		public String getContent() {
			return getWeaponDyeUI();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("Back", "Return to the previous menu.", INVENTORY_MENU);

			} else if (index == 1) {
				if (!Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH)
						&& !Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					return new Response("Dye",
							"You do not have a dye-brush, so cannot change the colours of the " + weapon.getName() + "...",
							null); 
				}
				
				if(dyePreviews.equals(weapon.getColours())) {
					return new Response("Dye",
							"You need to choose different colours before being able to dye the " + weapon.getName() + "!",
							null); 
				}
				
				return new Response("Dye",
						"Dye the " + weapon.getName() + " in the colours you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye it a different colour at any time."
										:" This action is permanent, and you'll need another dye-brush if you want to change its colour again."),
						INVENTORY_MENU){
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), owner, false);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(weapon, dyePreviews.get(0))
									+ "</p>"
									+ "<p>"
										+ "<b>The " + weapon.getName() + " " + (weapon.getWeaponType().isPlural() ? "have been" : "has been") + " dyed</b>!"
									+ "</p>"
									+ "<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
										+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye the " + weapon.getName() + " without needing to use a dye-brush!"
									+ "</p>");
						}
						 
						if(owner!=null) {
							owner.removeWeapon(weapon);
							AbstractWeapon dyedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							dyedWeapon.setColours(dyePreviews);
							owner.addWeapon(dyedWeapon, false);
							Main.game.addEvent(new EventLogEntry("Dyed", dyedWeapon.getDisplayName(true)), false);
							weapon = dyedWeapon;

						} else {
							Main.game.getPlayerCell().getInventory().removeWeapon(weapon);
							AbstractWeapon dyedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							dyedWeapon.setColours(dyePreviews);
							Main.game.getPlayerCell().getInventory().addWeapon(dyedWeapon);
							Main.game.addEvent(new EventLogEntry("Dyed", dyedWeapon.getDisplayName(true)), false);
							weapon = dyedWeapon;
						}
					}
				};

			} else if (index == 2) {
				if (!Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER)
						&& !Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					return new Response("Reforge",
							"You do not have a reforging hammer, so cannot change the damage type of the " + weapon.getName() + "...",
							null); 
				}
				
				if(damageTypePreview == weapon.getDamageType()) {
					return new Response("Reforge",
							"You need to choose a different damage type before being able to reforge the " + weapon.getName() + "!",
							null); 
				}
				
				return new Response("Reforge",
						"Reforge the " + weapon.getName() + " into the damage type you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can reforge it at any time."
										:" This action is permanent, and you'll need another reforging hammer if you want to change its damage type again."),
						INVENTORY_MENU){
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER), owner, false);
							
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getReforgeHammerEffects(weapon, damageTypePreview)
									+ "</p>"
									+ "<p>"
										+ "<b>The " + weapon.getName() + " " + (weapon.getWeaponType().isPlural() ? "have been" : "has been") + " reforged</b>!"
									+ "</p>"
									+ "<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER))
														+ "</b> reforging " + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER)) == 1 ? "hammer" : "hammers") + " left!"
												:"You have <b>0</b> reforging hammers left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to reforge the " + weapon.getName() + " without needing to use a reforging hammer!"
										+ "</p>");
						}
						 
						if(owner!=null) {
							owner.removeWeapon(weapon);
							AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							modifiedWeapon.setDamageType(damageTypePreview);
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), false);
							Main.game.addEvent(new EventLogEntry("Reforged", modifiedWeapon.getDisplayName(true)), false);

						} else {
							Main.game.getPlayerCell().getInventory().removeWeapon(weapon);
							AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							modifiedWeapon.setDamageType(damageTypePreview);
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							Main.game.getPlayerCell().getInventory().addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon));
							Main.game.addEvent(new EventLogEntry("Reforged", modifiedWeapon.getDisplayName(true)), false);
						}
					}
				};

			} else if (index == 3) {
				if ((!Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER) || !Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH))
						&& !Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					return new Response("Dye & reforge",
							"You do not have both a dye brush and a reforging hammer, so cannot dye and reforge the " + weapon.getName() + "...",
							null); 
				}
				
				if(damageTypePreview == weapon.getDamageType()) {
					return new Response("Dye & reforge",
							"You need to choose a different damage type before being able to reforge the " + weapon.getName() + "!",
							null); 
				}
				
				if(dyePreviews.equals(weapon.getColours())) {
					return new Response("Dye & reforge",
							"You need to choose different colours before being able to dye the " + weapon.getName() + "!",
							null); 
				}
				
				return new Response("Dye & reforge",
						"Dye and reforge the " + weapon.getName() + " into the colours and damage type you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye and reforge it at any time."
										:" This action is permanent, and you'll need another dye-brush and another reforging hammer if you want to change its colours and damage type again."),
						INVENTORY_MENU){
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), owner, false);
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER), owner, false);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(weapon, dyePreviews.get(0))
									+ "</p>"
									+ "<p style='text-align:center;'>"
										+ getReforgeHammerEffects(weapon, damageTypePreview)
									+ "</p>"
									+ "<p>"
										+ "<b>The " + weapon.getName() + " " + (weapon.getWeaponType().isPlural() ? "have been" : "has been") + " dyed and reforged</b>!"
									+ "</p>"
									+ "<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
										+"<br/>"
										+ (Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER))
														+ "</b> reforging " + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER)) == 1 ? "hammer" : "hammers") + " left!"
												:"You have <b>0</b> reforging hammers left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye and reforge the " + weapon.getName() + " without needing to use a dye-brush or reforging hammer!"
										+ "</p>");
						}
						
						if(owner!=null) {
							owner.removeWeapon(weapon);
							AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							modifiedWeapon.setDamageType(damageTypePreview);
							modifiedWeapon.setColours(dyePreviews);
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), false);
							Main.game.addEvent(new EventLogEntry("Dyed & Reforged", modifiedWeapon.getDisplayName(true)), false);

						} else {
							Main.game.getPlayerCell().getInventory().removeWeapon(weapon);
							AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							modifiedWeapon.setDamageType(damageTypePreview);
							modifiedWeapon.setColours(dyePreviews);
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							Main.game.getPlayerCell().getInventory().addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon));
							Main.game.addEvent(new EventLogEntry("Dyed & Reforged", modifiedWeapon.getDisplayName(true)), false);
						}
					}
				};

			} else if (index == 6) {
				if(dyePreviews.equals(weapon.getColours())) {
					return new Response("Dye all (stack)",
							"You need to choose different colours before being able to dye the " + weapon.getName() + "!",
							null); 
				}
				
				int stackCount = 0;
				if(owner!=null) {
					stackCount = owner.getWeaponCount(weapon);
				} else {
					stackCount = Main.game.getPlayerCell().getInventory().getWeaponCount(weapon);
				}

				int finalCount = stackCount;
				
				if(stackCount==1) {
					return new Response("Dye all (stack)",
							"You only have one "+weapon.getName()+", so you should dye it using the individual action...",
							null); 
				}
				
				if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					int dyeBrushCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH));
					
					if(dyeBrushCount<stackCount) {
						return new Response("Dye all (stack)",
								"You do not have enough dye brushes to dye all the " + weapon.getNamePlural() + "! You have "+dyeBrushCount+" dye brushes, but there are "+stackCount+" "+weapon.getNamePlural()+" in this stack...",
								null); 
					}
				}
				
				return new Response("Dye all (stack)",
						"Dye all " + weapon.getNamePlural() + " which are in this weapon stack ("+stackCount+" in total) in the colours you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye them a different colour at any time."
										:" This action is permanent, and you'll need another dye-brush if you want to change their colour again."),
						INVENTORY_MENU) {
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().removeItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), finalCount);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(weapon, dyePreviews.get(0))
									+ "</p>"
									+ "<p>"
									+ "<b>The "+weapon.getName()+(weapon.getWeaponType().isPlural()?"have":"has")+" been dyed</b>!"
									+ "</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>You then repeat this for the other "+Util.intToString(finalCount-1)+" "+weapon.getNamePlural()+"...</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye "
												+ (finalCount==2
													?"both"
													:"all "+Util.intToString(finalCount))
												+" of the " + weapon.getNamePlural() + " without needing to use a single dye-brush!"
										+ "</p>");
						}
						
						if(owner!=null) {
							owner.removeWeapon(weapon, finalCount);
							AbstractWeapon dyedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							dyedWeapon.setColours(dyePreviews);
							owner.addWeapon(dyedWeapon, finalCount, false, false);
							Main.game.addEvent(new EventLogEntry("Dyed", dyedWeapon.getDisplayName(true)), false);

						} else {
							Main.game.getPlayerCell().getInventory().removeWeapon(weapon, finalCount);
							AbstractWeapon dyedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							dyedWeapon.setColours(dyePreviews);
							Main.game.getPlayerCell().getInventory().addWeapon(dyedWeapon, finalCount);
							Main.game.addEvent(new EventLogEntry("Dyed", dyedWeapon.getDisplayName(true)), false);
						}
					}
				};

			} else if (index == 7) {
				if(damageTypePreview == weapon.getDamageType()) {
					return new Response("Reforge all (stack)",
							"You need to choose a different damage type before being able to reforge the " + weapon.getName() + "!",
							null); 
				}
				
				int stackCount = 0;
				if(owner!=null) {
					stackCount = owner.getWeaponCount(weapon);
				} else {
					stackCount = Main.game.getPlayerCell().getInventory().getWeaponCount(weapon);
				}

				int finalCount = stackCount;
				
				if(stackCount==1) {
					return new Response("Reforge all (stack)",
							"You only have one "+weapon.getName()+", so you should reforge it using the individual action...",
							null); 
				}
				
				if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					int reforgeHammerCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER));
					
					if(reforgeHammerCount<stackCount) {
						return new Response("Reforge all (stack)",
								"You do not have enough reforging hammers to dye all the " + weapon.getNamePlural() + "! You have "+reforgeHammerCount+" reforging hammers, but there are "+stackCount+" "+weapon.getNamePlural()+" in this stack...",
								null); 
					}
				}
				
				return new Response("Reforge all (stack)",
						"Reforge all " + weapon.getNamePlural() + " which are in this weapon stack ("+stackCount+" in total) into the damage type you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can reforge them at any time."
										:" This action is permanent, and you'll need another reforging hammer if you want to change their damage type again."),
						INVENTORY_MENU) {
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().removeItem(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER), finalCount);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getReforgeHammerEffects(weapon, damageTypePreview)
									+ "</p>"
									+ "<p>"
									+ "<b>The "+weapon.getName()+(weapon.getWeaponType().isPlural()?"have":"has")+" been reforged</b>!"
									+ "</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>You then repeat this for the other "+Util.intToString(finalCount-1)+" "+weapon.getNamePlural()+"...</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER))
														+ "</b> reforging hammer" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER)) == 1 ? "" : "s") + " left!"
												:"You have <b>0</b> reforging hammers left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to reforge "
												+ (finalCount==2
													?"both"
													:"all "+Util.intToString(finalCount))
												+" of the " + weapon.getNamePlural() + " without needing to use a single reforging hammer!"
										+ "</p>");
						}
						
						if(owner!=null) {
							owner.removeWeapon(weapon, finalCount);
							AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							modifiedWeapon.setDamageType(damageTypePreview);
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), finalCount, false, false);
							Main.game.addEvent(new EventLogEntry("Reforged", modifiedWeapon.getDisplayName(true)), false);

						} else {
							Main.game.getPlayerCell().getInventory().removeWeapon(weapon, finalCount);
							AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							modifiedWeapon.setDamageType(damageTypePreview);
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							Main.game.getPlayerCell().getInventory().addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), finalCount);
							Main.game.addEvent(new EventLogEntry("Reforged", modifiedWeapon.getDisplayName(true)), false);
						}
					}
				};

			} else if (index == 8) {
				if(damageTypePreview == weapon.getDamageType()) {
					return new Response("Dye & reforge all (stack)",
							"You need to choose a different damage type before being able to reforge the " + weapon.getName() + "!",
							null); 
				}
				
				if(dyePreviews.equals(weapon.getColours())) {
					return new Response("Dye & reforge all (stack)",
							"You need to choose different colours before being able to dye the " + weapon.getName() + "!",
							null); 
				}
				
				
				int stackCount = 0;
				if(owner!=null) {
					stackCount = owner.getWeaponCount(weapon);
				} else {
					stackCount = Main.game.getPlayerCell().getInventory().getWeaponCount(weapon);
				}

				int finalCount = stackCount;
				
				if(stackCount==1) {
					return new Response("Dye & reforge all (stack)",
							"You only have one "+weapon.getName()+", so you should dye & reforge it using the individual action...",
							null); 
				}
				
				if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					int dyeBrushCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH));
					int reforgeHammerCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER));
					
					if(dyeBrushCount<stackCount || reforgeHammerCount<stackCount) {
						return new Response("Dye & reforge all (stack)",
								"You do not have enough dye brushes or reforge hammers to dye & reforge all "+stackCount+" "+weapon.getNamePlural()+" in this stack...",
								null); 
					}
				}
				
				return new Response("Dye & reforge all (stack)",
						"Dye & reforge all " + weapon.getNamePlural() + " which are in this weapon stack ("+stackCount+" in total) in the colours and damage type you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can do this at any time."
										:" This action is permanent, and you'll need another dye-brush and reforging hammer if you want to do this again."),
						INVENTORY_MENU) {
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().removeItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), finalCount);
							Main.game.getPlayer().removeItem(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER), finalCount);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(weapon, dyePreviews.get(0))
									+ "</p>"
									+ "<p style='text-align:center;'>"
										+ getReforgeHammerEffects(weapon, damageTypePreview)
									+ "</p>"
									+ "<p>"
									+ "<b>The "+weapon.getName()+(weapon.getWeaponType().isPlural()?"have":"has")+" been dyed and reforged</b>!"
									+ "</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>You then repeat this for the other "+Util.intToString(finalCount-1)+" "+weapon.getNamePlural()+"...</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");

							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER))
														+ "</b> reforging hammer" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER)) == 1 ? "" : "s") + " left!"
												:"You have <b>0</b> reforging hammers left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye "
												+ (finalCount==2
													?"both"
													:"all "+Util.intToString(finalCount))
												+" of the " + weapon.getNamePlural() + " without needing to use a single dye-brush or reforging hammer!"
										+ "</p>");
						}
						
						if(owner!=null) {
							owner.removeWeapon(weapon, finalCount);
							AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							modifiedWeapon.setDamageType(damageTypePreview);
							modifiedWeapon.setColours(dyePreviews);
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), finalCount, false, false);
							Main.game.addEvent(new EventLogEntry("Dyed & Reforged", modifiedWeapon.getDisplayName(true)), false);

						} else {
							Main.game.getPlayerCell().getInventory().removeWeapon(weapon, finalCount);
							AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
							modifiedWeapon.setDamageType(damageTypePreview);
							modifiedWeapon.setColours(dyePreviews);
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							Main.game.getPlayerCell().getInventory().addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), finalCount);
							Main.game.addEvent(new EventLogEntry("Dyed & Reforged", modifiedWeapon.getDisplayName(true)), false);
						}
					}
				};

			} else if (index == 11) {
				if(dyePreviews.equals(weapon.getColours())) {
					return new Response("Dye all",
							"You need to choose different colours before being able to dye the " + weapon.getName() + "!",
							null); 
				}
				
				List<AbstractWeapon> weaponMatches = new ArrayList<>();
				int stackCount = 0;
				if(owner!=null) {
					for(Entry<AbstractWeapon, Integer> entry : owner.getAllWeaponsInInventory().entrySet()) {
						if(entry.getKey().getWeaponType().equals(weapon.getWeaponType())) {
							weaponMatches.add(entry.getKey());
							stackCount += entry.getValue();
						}
					}
				} else {
					for(Entry<AbstractWeapon, Integer> entry : Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().entrySet()) {
						if(entry.getKey().getWeaponType().equals(weapon.getWeaponType())) {
							weaponMatches.add(entry.getKey());
							stackCount += entry.getValue();
						}
					}
				}
				
				int finalCount = stackCount;
				
				if(stackCount==1) {
					return new Response("Dye all",
							"You only have one "+weapon.getName()+", so you should dye it using the individual action...",
							null); 
				}
				
				if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					int dyeBrushCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH));
					
					if(dyeBrushCount<stackCount) {
						return new Response("Dye all",
								"You do not have enough dye brushes to dye all the " + weapon.getNamePlural() + "! You have "+dyeBrushCount+" dye brushes, but there are "+stackCount+" "+weapon.getNamePlural()+" in total...",
								null); 
					}
				}
				
				return new Response("Dye all",
						"Dye all " + weapon.getNamePlural() + " which are in this clothing stack ("+stackCount+" in total) in the colours you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye them a different colour at any time."
										:" This action is permanent, and you'll need another dye-brush if you want to change their colour again."),
						INVENTORY_MENU) {
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().removeItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), finalCount);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(weapon, dyePreviews.get(0))
									+ "</p>"
									+ "<p>"
									+ "<b>The "+weapon.getName()+(weapon.getWeaponType().isPlural()?"have":"has")+" been dyed</b>!"
									+ "</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>You then repeat this for the other "+Util.intToString(finalCount-1)+" "+weapon.getNamePlural()+"...</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye "
												+ (finalCount==2
													?"both"
													:"all "+Util.intToString(finalCount))
												+" of the " + weapon.getNamePlural() + " without needing to use a single dye-brush!"
										+ "</p>");
						}
						

						if(owner!=null) {
							for(AbstractWeapon w : weaponMatches) {
								int weaponCount = owner.getAllWeaponsInInventory().get(w);
								owner.removeWeapon(w, weaponCount);
								AbstractWeapon dyedWeapon = Main.game.getItemGen().generateWeapon(w);
								dyedWeapon.setColours(dyePreviews);
								owner.addWeapon(dyedWeapon, weaponCount, false, false);
								Main.game.addEvent(new EventLogEntry("Dyed", dyedWeapon.getDisplayName(true)), false);
							}
							
						} else {
							for(AbstractWeapon w : weaponMatches) {
								int weaponCount = Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().get(w);
								Main.game.getPlayerCell().getInventory().removeWeapon(w, weaponCount);
								AbstractWeapon dyedWeapon = Main.game.getItemGen().generateWeapon(w);
								dyedWeapon.setColours(dyePreviews);
								Main.game.getPlayerCell().getInventory().addWeapon(dyedWeapon, weaponCount);
								Main.game.addEvent(new EventLogEntry("Dyed", dyedWeapon.getDisplayName(true)), false);
							}
						}
					}
				};

			} else if (index == 12) {
				if(damageTypePreview == weapon.getDamageType()) {
					return new Response("Reforge all",
							"You need to choose a different damage type before being able to reforge the " + weapon.getName() + "!",
							null); 
				}
				
				List<AbstractWeapon> weaponMatches = new ArrayList<>();
				int stackCount = 0;
				if(owner!=null) {
					for(Entry<AbstractWeapon, Integer> entry : owner.getAllWeaponsInInventory().entrySet()) {
						if(entry.getKey().getWeaponType().equals(weapon.getWeaponType())) {
							weaponMatches.add(entry.getKey());
							stackCount += entry.getValue();
						}
					}
				} else {
					for(Entry<AbstractWeapon, Integer> entry : Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().entrySet()) {
						if(entry.getKey().getWeaponType().equals(weapon.getWeaponType())) {
							weaponMatches.add(entry.getKey());
							stackCount += entry.getValue();
						}
					}
				}
				
				int finalCount = stackCount;
				
				if(stackCount==1) {
					return new Response("Reforge all",
							"You only have one "+weapon.getName()+", so you should reforge it using the individual action...",
							null); 
				}
				
				if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					int reforgeHammerCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER));
					
					if(reforgeHammerCount<stackCount) {
						return new Response("Reforge all",
								"You do not have enough reforging hammers to dye all the " + weapon.getNamePlural() + "! You have "+reforgeHammerCount+" reforging hammers, but there are "+stackCount+" "+weapon.getNamePlural()+" in total...",
								null); 
					}
				}
				
				return new Response("Reforge all",
						"Reforge all " + weapon.getNamePlural() + " which are in this weapon stack ("+stackCount+" in total) into the damage type you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can reforge them at any time."
										:" This action is permanent, and you'll need another reforging hammer if you want to change their damage type again."),
						INVENTORY_MENU) {
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().removeItem(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER), finalCount);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getReforgeHammerEffects(weapon, damageTypePreview)
									+ "</p>"
									+ "<p>"
									+ "<b>The "+weapon.getName()+(weapon.getWeaponType().isPlural()?"have":"has")+" been reforged</b>!"
									+ "</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>You then repeat this for the other "+Util.intToString(finalCount-1)+" "+weapon.getNamePlural()+"...</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER))
														+ "</b> reforging hammer" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER)) == 1 ? "" : "s") + " left!"
												:"You have <b>0</b> reforging hammers left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to reforge "
												+ (finalCount==2
													?"both"
													:"all "+Util.intToString(finalCount))
												+" of the " + weapon.getNamePlural() + " without needing to use a single reforging hammer!"
										+ "</p>");
						}

						if(owner!=null) {
							for(AbstractWeapon w : weaponMatches) {
								int weaponCount = owner.getAllWeaponsInInventory().get(w);
								owner.removeWeapon(w, weaponCount);
								AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(w);
								modifiedWeapon.setDamageType(damageTypePreview);
								// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
								owner.addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), weaponCount, false, false);
								Main.game.addEvent(new EventLogEntry("Reforged", modifiedWeapon.getDisplayName(true)), false);
							}

						} else {
							for(AbstractWeapon w : weaponMatches) {
								int weaponCount = Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().get(w);
								Main.game.getPlayerCell().getInventory().removeWeapon(w, weaponCount);
								AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(w);
								modifiedWeapon.setDamageType(damageTypePreview);
								// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
								Main.game.getPlayerCell().getInventory().addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), weaponCount);
								Main.game.addEvent(new EventLogEntry("Reforged", modifiedWeapon.getDisplayName(true)), false);
							}
						}
					}
				};

			} else if (index == 13) {
				if(damageTypePreview == weapon.getDamageType()) {
					return new Response("Dye & reforge all",
							"You need to choose a different damage type before being able to reforge the " + weapon.getName() + "!",
							null); 
				}
				
				if(dyePreviews.equals(weapon.getColours())) {
					return new Response("Dye & reforge all",
							"You need to choose different colours before being able to dye the " + weapon.getName() + "!",
							null); 
				}
				
				List<AbstractWeapon> weaponMatches = new ArrayList<>();
				int stackCount = 0;
				if(owner!=null) {
					for(Entry<AbstractWeapon, Integer> entry : owner.getAllWeaponsInInventory().entrySet()) {
						if(entry.getKey().getWeaponType().equals(weapon.getWeaponType())) {
							weaponMatches.add(entry.getKey());
							stackCount += entry.getValue();
						}
					}
				} else {
					for(Entry<AbstractWeapon, Integer> entry : Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().entrySet()) {
						if(entry.getKey().getWeaponType().equals(weapon.getWeaponType())) {
							weaponMatches.add(entry.getKey());
							stackCount += entry.getValue();
						}
					}
				}
				
				int finalCount = stackCount;
				
				if(stackCount==1) {
					return new Response("Dye & reforge all",
							"You only have one "+weapon.getName()+", so you should dye & reforge it using the individual action...",
							null); 
				}
				
				if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					int dyeBrushCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH));
					int reforgeHammerCount = Main.game.getPlayer().getItemCount(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER));
					
					if(dyeBrushCount<stackCount || reforgeHammerCount<stackCount) {
						return new Response("Dye & reforge all",
								"You do not have enough dye brushes or reforge hammers to dye & reforge all "+stackCount+" "+weapon.getNamePlural()+" in this stack...",
								null); 
					}
				}
				
				return new Response("Dye & reforge all",
						"Dye & reforge all " + weapon.getNamePlural() + " which are in this weapon stack ("+stackCount+" in total) in the colours and damage type you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can do this at any time."
										:" This action is permanent, and you'll need another dye-brush and reforging hammer if you want to do this again."),
						INVENTORY_MENU) {
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().removeItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), finalCount);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(weapon, dyePreviews.get(0))
									+ "</p>"
									+ "<p style='text-align:center;'>"
										+ getReforgeHammerEffects(weapon, damageTypePreview)
									+ "</p>"
									+ "<p>"
									+ "<b>The "+weapon.getName()+(weapon.getWeaponType().isPlural()?"have":"has")+" been dyed and reforged</b>!"
									+ "</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>You then repeat this for the other "+Util.intToString(finalCount-1)+" "+weapon.getNamePlural()+"...</p>");
							
							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");

							Main.game.getTextEndStringBuilder().append("<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER))
														+ "</b> reforging hammer" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER)) == 1 ? "" : "s") + " left!"
												:"You have <b>0</b> reforging hammers left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye and reforge "
												+ (finalCount==2
													?"both"
													:"all "+Util.intToString(finalCount))
												+" of the " + weapon.getNamePlural() + " without needing to use a single dye-brush or reforging hammer!"
										+ "</p>");
						}
						
						if(owner!=null) {
							for(AbstractWeapon w : weaponMatches) {
								int weaponCount = owner.getAllWeaponsInInventory().get(w);
								owner.removeWeapon(w, weaponCount);
								AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(w);
								modifiedWeapon.setDamageType(damageTypePreview);
								modifiedWeapon.setColours(dyePreviews);
								// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
								owner.addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), weaponCount, false, false);
								Main.game.addEvent(new EventLogEntry("Dyed & Reforged", modifiedWeapon.getDisplayName(true)), false);
							}
							
						} else {
							for(AbstractWeapon w : weaponMatches) {
								int weaponCount = Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().get(w);
								Main.game.getPlayerCell().getInventory().removeWeapon(w, weaponCount);
								AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(w);
								modifiedWeapon.setDamageType(damageTypePreview);
								modifiedWeapon.setColours(dyePreviews);
								// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
								Main.game.getPlayerCell().getInventory().addWeapon(Main.game.getItemGen().generateWeapon(modifiedWeapon), weaponCount);
								Main.game.addEvent(new EventLogEntry("Dyed & Reforged", modifiedWeapon.getDisplayName(true)), false);
							}
						}
					}
				};

			} else {
				return null;
			}
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};

	public static final DialogueNode DYE_EQUIPPED_WEAPON = new DialogueNode("Dye Weapon", "", true) {

		@Override
		public String getContent() {
			return getWeaponDyeUI();
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 0) {
				return new Response("Back", "Return to the previous menu.", INVENTORY_MENU);

			} else if (index == 1) {
				if (!Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH)
						&& !Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					return new Response("Dye",
							"You do not have a dye-brush, so cannot change the colours of the " + weapon.getName() + "...",
							null); 
				}
				
				if(dyePreviews.equals(weapon.getColours())) {
					return new Response("Dye",
							"You need to choose different colours before being able to dye the " + weapon.getName() + "!",
							null); 
				}
				
				return new Response("Dye",
						"Dye the " + weapon.getName() + " in the colours you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye it a different colour at any time."
										:" This action is permanent, and you'll need another dye-brush if you want to change its colour again."),
						INVENTORY_MENU){
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), owner, false);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(weapon, dyePreviews.get(0))
									+ "</p>"
									+ "<p>"
										+ "<b>The " + weapon.getName() + " " + (weapon.getWeaponType().isPlural() ? "have been" : "has been") + " dyed</b>!"
									+ "</p>"
									+ "<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye the " + weapon.getName() + " without needing to use a dye-brush!"
										+ "</p>");
						}
						
						

						owner.unequipWeaponIntoVoid(weaponSlot, weapon, true);
						AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
						modifiedWeapon.setColours(dyePreviews);
						
						if(weaponSlot==InventorySlot.WEAPON_MAIN_1
								|| weaponSlot==InventorySlot.WEAPON_MAIN_2
								|| weaponSlot==InventorySlot.WEAPON_MAIN_3) {
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon(modifiedWeapon));
							
						} else {
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.equipOffhandWeaponFromNowhere(Main.game.getItemGen().generateWeapon(modifiedWeapon));
						}

//						weapon.setPrimaryColour(dyePreviewPrimary);
//						weapon.setSecondaryColour(dyePreviewSecondary);
						Main.game.addEvent(new EventLogEntry("Dyed", weapon.getDisplayName(true)), false);
					}
				};

			} else if (index == 2) {
				if (!Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER)
						&& !Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					return new Response("Reforge",
							"You do not have a reforging hammer, so cannot change the damage type of the " + weapon.getName() + "...",
							null); 
				}
				
				if(damageTypePreview == weapon.getDamageType()) {
					return new Response("Reforge",
							"You need to choose a different damage type before being able to reforge the " + weapon.getName() + "!",
							null); 
				}
				
				return new Response("Reforge",
						"Reforge the " + weapon.getName() + " into the damage type you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can reforge it at any time."
										:" This action is permanent, and you'll need another reforging hammer if you want to change its damage type again."),
						INVENTORY_MENU){
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER), owner, false);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getReforgeHammerEffects(weapon, damageTypePreview)
									+ "</p>"
									+ "<p>"
										+ "<b>The " + weapon.getName() + " " + (weapon.getWeaponType().isPlural() ? "have been" : "has been") + " reforged</b>!"
									+ "</p>"
									+ "<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER))
														+ "</b> reforging " + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER)) == 1 ? "hammer" : "hammers") + " left!"
												:"You have <b>0</b> reforging hammers left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to reforge the " + weapon.getName() + " without needing to use a reforging hammer!"
										+ "</p>");
						}

						owner.unequipWeaponIntoVoid(weaponSlot, weapon, true);
						AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
						modifiedWeapon.setDamageType(damageTypePreview);
						
						if(weaponSlot==InventorySlot.WEAPON_MAIN_1
								|| weaponSlot==InventorySlot.WEAPON_MAIN_2
								|| weaponSlot==InventorySlot.WEAPON_MAIN_3) {
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon(modifiedWeapon));
							
						} else {
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.equipOffhandWeaponFromNowhere(Main.game.getItemGen().generateWeapon(modifiedWeapon));
						}
						
						Main.game.addEvent(new EventLogEntry("Reforged", weapon.getDisplayName(true)), false);
					}
				};

			} else if (index == 3) {
				if ((!Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER) || !Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH))
						&& !Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
					return new Response("Dye & reforge",
							"You do not have both a dye brush and a reforging hammer, so cannot dye and reforge the " + weapon.getName() + "...",
							null); 
				}
				
				if(damageTypePreview == weapon.getDamageType()) {
					return new Response("Dye & reforge",
							"You need to choose a different damage type before being able to reforge the " + weapon.getName() + "!",
							null); 
				}
				
				if(dyePreviews.equals(weapon.getColours())) {
					return new Response("Dye & reforge",
							"You need to choose different colours before being able to dye the " + weapon.getName() + "!",
							null); 
				}
				
				return new Response("Dye & reforge",
						"Dye and reforge the " + weapon.getName() + " into the colours and damage type you have chosen."
								+ (Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
										?" This action is permanent, but thanks to your proficiency with [style.boldEarth(Earth spells)], you can dye and reforge it at any time."
										:" This action is permanent, and you'll need another dye-brush and another reforging hammer if you want to change its colours and damage type again."),
						INVENTORY_MENU){
					@Override
					public void effects(){
						if(!Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)) {
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), owner, false);
							Main.game.getPlayer().useItem(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER), owner, false);
							Main.game.getTextEndStringBuilder().append(
									"<p style='text-align:center;'>"
										+ getDyeBrushEffects(weapon, dyePreviews.get(0))
									+ "</p>"
									+ "<p style='text-align:center;'>"
										+ getReforgeHammerEffects(weapon, damageTypePreview)
									+ "</p>"
									+ "<p>"
										+ "<b>The " + weapon.getName() + " " + (weapon.getWeaponType().isPlural() ? "have been" : "has been") + " reforged</b>!"
									+ "</p>"
									+ "<p>"
										+ (Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH))
														+ "</b> dye-brush" + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH)) == 1 ? "" : "es") + " left!"
												:"You have <b>0</b> dye-brushes left!")
										+"<br/>"
										+ (Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER) || Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
												?"You have <b>" + Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER))
														+ "</b> reforging " + (Main.game.getPlayer().getAllItemsInInventory().get(Main.game.getItemGen().generateItem(ItemType.REFORGE_HAMMER)) == 1 ? "hammer" : "hammers") + " left!"
												:"You have <b>0</b> reforging hammers left!")
									+ "</p>");
							
						} else {
							Main.game.getTextEndStringBuilder().append(
									"<p>"
											+ "Thanks to your proficiency with [style.boldEarth(Earth spells)], you are able to dye and reforge the " + weapon.getName() + " without needing to use a dye-brush or reforging hammer!"
										+ "</p>");
						}

						owner.unequipWeaponIntoVoid(weaponSlot, weapon, true);
						AbstractWeapon modifiedWeapon = Main.game.getItemGen().generateWeapon(weapon);
						modifiedWeapon.setColours(dyePreviews);
						modifiedWeapon.setDamageType(damageTypePreview);
						
						if(weaponSlot==InventorySlot.WEAPON_MAIN_1
								|| weaponSlot==InventorySlot.WEAPON_MAIN_2
								|| weaponSlot==InventorySlot.WEAPON_MAIN_3) {
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon(modifiedWeapon));
							
						} else {
							// For some reason, if you add the modifiedWeapon directly, it won't stack with other identical weapons... Have to generateWeapon(modifiedWeapon) again to get it to start stacking properly:
							owner.equipOffhandWeaponFromNowhere(Main.game.getItemGen().generateWeapon(modifiedWeapon));
						}
						
//						weapon.setDamageType(damageTypePreview);
//						weapon.setPrimaryColour(dyePreviewPrimary);
//						weapon.setSecondaryColour(dyePreviewSecondary);
						Main.game.addEvent(new EventLogEntry("Dyed & Reforged", weapon.getDisplayName(true)), false);
					}
				};

			} else {
				return null;
			}
		}

		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.INVENTORY;
		}
	};
	
	
	
	// Utility methods:
	
	private static String getDyeBrushEffects(AbstractClothing clothing, Colour colour) {
		return "<p>"
					+ "As you take hold of the Dye-brush, you see the purple glow around the tip growing in strength."
					+ " The closer you move it to your " + clothing.getName() + ", the brighter the glow becomes, until suddenly, images of different colours start flashing through your mind."
					+ " As you touch the bristles to the " + clothing.getName() + "'s surface, the Dye-brush instantly evaporates!"
					+ " You see that the arcane enchantment has dyed the " + clothing.getName() + " " + colour.getName() + "."
				+ "</p>";
	}
	
	private static String getDyeBrushEffects(AbstractWeapon weapon, Colour colour) {
		return "<p>"
					+ "As you take hold of the Dye-brush, you see the purple glow around the tip growing in strength."
					+ " The closer you move it to your " + weapon.getName() + ", the brighter the glow becomes, until suddenly, images of different colours start flashing through your mind."
					+ " As you touch the bristles to the " + weapon.getName() + "'s surface, the Dye-brush instantly evaporates!"
					+ " You see that the arcane enchantment has dyed the " + weapon.getName() + " " + colour.getName() + "."
				+ "</p>";
	}
	
	private static String getReforgeHammerEffects(AbstractWeapon weapon, DamageType damageType) {
		return "<p>"
					+ "As you take hold of the reforging hammer, you see the metal head start to emit a deep purple glow."
					+ " The closer you move it to your " + weapon.getName() + ", the brighter this glow becomes, until suddenly, images of different damage types start flashing through your mind."
					+ " As you touch the metal head  to the " + weapon.getName() + ", the reforge hammer instantly evaporates!"
					+ " You see that the arcane enchantment has reforged the " + weapon.getName() + " so that it now deals " + damageType.getName() + " damage."
				+ "</p>";
	}
	
	private static String getItemDisplayPanel(String SVGString, String title, String description) {
		return "<div class='inventoryImage'>" // style='width: calc(50% - 4px);'
					+ "<div class='inventoryImage-content'>"
						+ SVGString
					+ "</div>"
				+ "</div>"
				+ "<h5 style='margin-bottom:0; padding-bottom:0;'><b>"+title+"</b></h5>"
				+ "<p style='margin-top:0; padding-top:0;'>"
					+ description
				+ "</p>";
	}
	
	private static String getGeneralResponseTabTitle(int index) {
		if(index==0) {
			return "Overview";
		} else if(index==1) {
			return "Selected item";
		} else {
			return null;
		}
	}
	
	private static Response getCloseInventoryResponse() {
		if(interactionType == InventoryInteraction.CHARACTER_CREATION) {
			return new Response("Back", "Return to looking in the mirror at your appearance.", CharacterCreation.CHOOSE_ADVANCED_APPEARANCE){
				@Override
				public int getSecondsPassed() {
					return -CharacterCreation.TIME_TO_CLOTHING;
				}
				@Override
				public void effects(){
					item = null;
					clothing = null;
					weapon = null;
				}
			};
			
		} else {
			return new ResponseEffectsOnly("Close Inventory", "Close the Inventory menu."){
				@Override
				public void effects(){
					item = null;
					clothing = null;
					weapon = null;
					Main.mainController.openInventory();
				}
			};
		}
	}
	
	private static Response getBuybackResponse() {
		if (buyback) {
			return new Response("Normal trade", "Switch back to the normal trade menu.", INVENTORY_MENU){
				@Override
				public void effects(){
					buyback = !buyback;
				}
			};
		} else {
			if(Main.game.getPlayer().getBuybackStack()==null || Main.game.getPlayer().getBuybackStack().isEmpty()) {
				return new Response("Buyback", "You have not sold any items, so cannot buy anything back...", null);
			} else {
				return new Response("Buyback", "Switch to viewing the buyback menu.", INVENTORY_MENU){
					@Override
					public void effects(){
						buyback = !buyback;
					}
				};
			}
		}
	}
	
	private static Response getQuickTradeResponse() { //TODO move this into options
		
		return null;
		
//		if (Main.game.getDialogueFlags().quickTrade) {
//			return new Response("Quick-Manage: <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</b>",
//					"Quick-Manage is turned <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>ON</b>!<br/>"
//							+ "That means you can buy and sell items with a single click when trading, and pick-up and drop items with a single click when in normal inventory mode.", INVENTORY_MENU){
//				
//				@Override
//				public DialogueNodeOld getNextDialogue() {
//					return Main.game.getCurrentDialogueNode();
//				}
//				
//				@Override
//				public void effects(){
//					Main.game.getDialogueFlags().quickTrade = !Main.game.getDialogueFlags().quickTrade;
//				}
//			};
//			
//		} else {
//			return new Response("Quick-Manage: <b style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>OFF</b>",
//					"Quick-Manage is turned <b style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>OFF</b>.<br/>"
//							+ "That means when you click on an item, you get a detailed view of the item before deciding whether to buy/sell or pick-up/drop it.", INVENTORY_MENU){
//
//				@Override
//				public DialogueNodeOld getNextDialogue() {
//					return Main.game.getCurrentDialogueNode();
//				}
//				
//				@Override
//				public void effects(){
//					Main.game.getDialogueFlags().quickTrade = !Main.game.getDialogueFlags().quickTrade;
//				}
//			};
//		}
	}
	
	private static Response getJinxRemovalResponse(boolean selfUnseal) {
		boolean ownsKey = Main.game.getPlayer().getUnlockKeyMap().containsKey(owner.getId()) && Main.game.getPlayer().getUnlockKeyMap().get(owner.getId()).contains(clothing.getSlotEquippedTo());
		int removalCost = clothing.getJinxRemovalCost(Main.game.getPlayer(), selfUnseal);
		
		if(interactionType==InventoryInteraction.COMBAT) {
			return new Response("Unseal"+(ownsKey?"(Use key)":"(<i>"+removalCost+" Essences</i>)"),
					"You can't unseal clothing in combat!",
					null);
		}

		if(interactionType==InventoryInteraction.SEX && !Main.sex.getInitialSexManager().isAbleToRemoveClothingSeals(Main.game.getPlayer())) {
			return new Response("Unseal"+(ownsKey?"(Use key)":"(<i>"+removalCost+" Essences</i>)"),
					"You can't unseal clothing in this sex scene!",
					null);
		}
		
		
		if(!ownsKey) {
			if(!Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)) {
				return new Response("Unseal", "You don't know how to unseal clothing! Perhaps you should pay Lilaya a visit and ask her about it...", null);
			}
			if(Main.game.getPlayer().getClothingCurrentlyEquipped().stream().anyMatch(c -> c.isSelfTransformationInhibiting())) {
				return new Response("Unseal",
						"Although you are normally able to unseal clothing, you cannot do so due to an enchantment on one or more pieces of your equipped clothing!"
						+ "<br/>[style.italicsArcane(Visit Lilaya to get your sealed clothing removed!)]",
						null);
			}
			if(Main.game.getPlayer().getTattoos().values().stream().anyMatch(c -> c.isSelfTransformationInhibiting())) {
				return new Response("Unseal",
						"Although you are normally able to unseal clothing, you cannot do so due to an enchantment on one or more of your tattoos!"
								+ "<br/>[style.italicsArcane(Visit Kate to get the tattoo removed!)]",
						null);
			}
		}
		
		if(ownsKey || Main.game.getPlayer().getEssenceCount()>=removalCost) {
			return new Response("Unseal "+(ownsKey?"([style.italicsGood(Use key)])":"([style.italicsArcane("+removalCost+" Essences)])"),
						ownsKey
							?"As you own the key which unlocks this piece of clothing, you can remove it without having to spend any arcane essences!"
							:"Spend "+removalCost+" arcane essences on unsealing from this piece of clothing.",
						interactionType==InventoryInteraction.SEX
							?Main.sex.SEX_DIALOGUE
							:INVENTORY_MENU) {
				@Override
				public void effects() {
					String s = "";
					if(ownsKey) {
						if(!Main.game.isInSex()) {
							Main.game.getPlayer().removeFromUnlockKeyMap(owner.getId(), clothing.getSlotEquippedTo());
						}
						s = "<p>"
								+ "Using the key which is in your possession, you unlock the "+clothing.getName()+"!"
							+ "</p>";
						
					} else {
						Main.game.getPlayer().incrementEssenceCount(-removalCost, false);
						s = UtilText.parse(owner,
								"<p>"
									+ "You channel the power of your arcane essences into [npc.namePos] "+clothing.getName()+", and with a bright purple flash, you manage to remove the seal!"
								+ "</p>"
								+ "<p style='text-align:center;'>"
									+ "Removing the seal has cost you [style.boldBad("+removalCost+")] [style.boldArcane(Arcane Essences)]!"
								+ "</p>");
					}
					
					// Have to remove and then re-add the clothing as setting the sealed status affects the clothing's hashCode
					List<DisplacementType> clothingDisplacementTypes = new ArrayList<>();
					if(Main.game.isInSex() && Main.sex.getAllParticipants().contains(owner)) {
						clothingDisplacementTypes.addAll(Main.sex.getClothingPreSexMap().get(owner).get(clothing.getSlotEquippedTo()).get(clothing));
						Main.sex.getClothingPreSexMap().get(owner).get(clothing.getSlotEquippedTo()).remove(clothing);
					}
					clothing.setSealed(false);
					if(Main.game.isInSex() && Main.sex.getAllParticipants().contains(owner)) {
						Main.sex.getClothingPreSexMap().get(owner).get(clothing.getSlotEquippedTo()).put(clothing, clothingDisplacementTypes);
					}
					
					if(interactionType==InventoryInteraction.SEX) {
						Main.sex.setUnequipClothingText(clothing, s);
						Main.mainController.openInventory();
						Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
						Main.sex.setSexStarted(true);
						
					} else {
						Main.game.getTextEndStringBuilder().append(s);
					}
				}
			};
			
		} else {
			return new Response("Unseal (<i>"+removalCost+" Essences</i>)",
					"You need at least "+removalCost+" arcane essences in order to unseal this piece of clothing!",
					null);
		}
	}

	// Items:

	private static Response getPlayerItemResponseDuringSex(AbstractCoreItem x, int ignoredResponseTab, int index) {
		var player = Main.game.getPlayer();
		if(index == 1 || index == 2 || index == 3) {
			var title = (owner.getLocationPlace().isItemsDisappear() ? "Drop" : "Store")
					+ (index == 1 ? "(1)" : index == 2 ? "(5)" : "(All)");
			var itemString = x instanceof AbstractClothing ? "clothing" : x instanceof AbstractWeapon ? "weapons" : "items";
			return new Response(title, "You can't drop " + itemString + " while masturbating.", null);
		}
		if(index == 4 && x instanceof AbstractClothing)
			return new Response("Dye", "You can't dye your clothing while masturbating.", null);
		if(index == 4 && x instanceof AbstractWeapon)
			return new Response("Dye/Reforge", "You can't dye or reforge your weapons while masturbating.", null);
		if(index == 5) {
			if(x instanceof AbstractClothing c && c.isCondom()) {
				boolean broken = c.getCondomEffect().getPotency().isNegative();
				return new Response(
						broken ? "Repair (<i>1 Essence</i>)" : "Sabotage",
						"You can't " + (broken ? "repair" : "sabotage") + " the condom while masturbating.",
						null);
			}
			var itemString = x instanceof AbstractClothing ? "clothing" : x instanceof AbstractWeapon ? "weapons" : "items";
			return new Response("Enchant", "You can't enchant " + itemString + " while masturbating.", null);
		}
		if((index == 6 || index == 7) && x instanceof AbstractItem i) {
			boolean once = index == 6;
			var title = Util.capitaliseSentence(i.getItemType().getUseName()) + (once ? "" : " all") + " (Self)";
			if(!once)
				return new Response(title, "You can only use one item at a time during sex!", null);
			if(!Main.sex.isItemUseAvailable())
				return new Response(title, "Items cannot be used during this sex scene!", null);
			if(!i.isAbleToBeUsedInSex())
				return new Response(title, "You cannot use this during sex!", null);
			if(!i.isAbleToBeUsedFromInventory())
				return new Response(title, i.getUnableToBeUsedFromInventoryDescription(), null);
			if(!i.isAbleToBeUsed(player))
				return new Response(title, i.getUnableToBeUsedDescription(player), null);
			return new Response(
						title,
						i.getItemType().getUseTooltipDescription(owner, owner),
						Main.sex.SEX_DIALOGUE){
					@Override
					public void effects(){
						Main.sex.setUsingItemText(player.useItem(i, player, false));
						resetPostAction();
						Main.mainController.openInventory();
						Main.sex.endSexTurn(SexActionUtility.PLAYER_USE_ITEM);
						Main.sex.setSexStarted(true);
					}
				};
		}
		if(index >= 6 && index <= 9 && x instanceof AbstractClothing c
				&& index - 6 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index - 6);
			var title = "Equip: " + Util.capitaliseSentence(slot.getName());
			if(!c.isCanBeEquipped(player, slot))
				return new Response(title, c.getCannotBeEquippedText(player, slot), null);
			if(!c.isAbleToBeEquippedDuringSex(slot).getKey())
				return new Response(title, c.isAbleToBeEquippedDuringSex(slot).getValue(), null);
			if(!Main.sex.getInitialSexManager().isAbleToEquipSexClothing(player, player, c))
				return new Response(title, "As this is a special sex scene, you cannot equip clothing during it!", null);
			if(!Main.sex.isClothingEquipAvailable(player, slot, c))
				return new Response(title, "This slot is involved with an ongoing sex action, so you cannot equip clothing into it!", null);
			if(!player.isAbleToEquip(c, slot, false, player))
				return new Response(title, getClothingBlockingRemovalText(player, "equip"), null);
			return new Response(title, "Equip the " + c.getName() + ".", Main.sex.SEX_DIALOGUE) {
				@Override
				public void effects() {
					equipClothingFromInventory(player, slot, player, c);
					Main.sex.setEquipClothingText(c, player.getUnequipDescription());
					Main.mainController.openInventory();
					Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
					Main.sex.setSexStarted(true);
				}
			};
		}
		if((index == 6 || index == 7) && x instanceof AbstractWeapon) {
			boolean main = index == 6;
			var title = main ? "Equip Main (Self)" : "Equip Offhand (Self)";
			return new Response(title, "You can't equip weapons while masturbating.", null);
		}
		if(index == 10)
			return getQuickTradeResponse();
		return null;
	}

	private static Response getPlayerItemResponse(AbstractCoreItem x, int ignoredResponseTab, int index) {
		var player = Main.game.getPlayer();
		if(index == 1 || index == 2 || index == 3) {
			boolean drop = owner.getLocationPlace().isItemsDisappear();
			var title = (drop ? "Drop (" : "Store (") + (index == 1 ? "1)" : index == 2 ? "5)" : "All)");
			var name = index == 1 ? x.getName() : x.getNamePlural();
			if(index == 2 && owner.getItemCount(x) < 5)
				return new Response(title, "You don't have five " + name + " to give!", null);
			if(!x.getType().isAbleToBeDropped())
				return new Response(title, "You cannot " + (drop ? "drop" : "store") + " the " + name + "!", null);
			if(Main.game.isPlayerTileFull() && !Main.game.getPlayerCell().getInventory().hasItem(x))
				return new Response(
						title,
						"This area is full, so you can't " + (drop ? "drop" : "store") + " your " + name + " here!",
						null);
			return new Response(
					title,
					(drop ? "Drop " : "Store ")
							+ (index == 1 ? "your " : index == 2 ? "five of your " : "all of your ")
							+ name
							+ (drop ? "." : " in this area."),
					INVENTORY_MENU) {
				@Override
				public void effects() {
					int count = index == 1 ? 1 : index == 2 ? 5 : owner.getItemCount(x);
					dropItems(owner, x, count);
				}
			};
		}
		if(index == 4 && x instanceof AbstractClothing c) {
			if(!player.hasItemType(ItemType.DYE_BRUSH)
					&& !player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response("Dye", "You'll need to find a dye-brush if you want to dye your clothes.", null);
			if(player.getAllClothingInInventory().get(c) > 1
					&& player.isInventoryFull()
					&& c.getRarity() != Rarity.QUEST)
				return new Response("Dye", "Your inventory is full, so you can't dye this item of clothing.", null);
			return new Response("Dye",
					player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							? "Use your proficiency with [style.colourEarth(Earth spells)] to dye this item of clothing."
							: "Use a dye-brush to dye this item of clothing.",
					DYE_CLOTHING) {
				@Override
				public void effects() {
					resetClothingDyeColours();
				}
			};
		}
		if(index == 4 && x instanceof AbstractWeapon w) {
			if(!player.hasItemType(ItemType.DYE_BRUSH)
					&& !player.hasItemType(ItemType.REFORGE_HAMMER)
					&& !player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response("Dye/Reforge", "You'll need to find a dye-brush or reforge hammer if you want to alter this weapon's properties.", null);
			if(player.getAllWeaponsInInventory().get(w) > 1
					&& player.isInventoryFull()
					&& w.getRarity() != Rarity.QUEST)
				return new Response("Dye/Reforge", "Your inventory is full, so you can't alter this weapon's properties.", null);
			return new Response("Dye/Reforge",
					player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							? "Use your proficiency with [style.colourEarth(Earth spells)] to dye or reforge this item."
							: "Use a dye-brush or reforge hammer to alter this weapon's properties.",
					DYE_WEAPON) {
				@Override
				public void effects() {
					resetWeaponDyeColours();
				}
			};
		}
		if(index == 5 && x instanceof AbstractClothing c) {
			if(c.isCondom())
				return getCondomSabotageResponse(c);
			if(!Main.game.isDebugMode()
					&& (!player.hasQuest(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)
							|| !player.isQuestCompleted(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)))
				return new Response("Enchant", getEnchantmentNotDiscoveredText("clothing"), null);
			if(c.getEnchantmentItemType(null) == null || c.getItemTags().contains(ItemTag.UNENCHANTABLE))
				return new Response("Enchant", "This clothing cannot be enchanted!", null);
			if(!c.isEnchantmentKnown()) {
				int essencePrice = IDENTIFICATION_ESSENCE_PRICE;
				boolean sufficient = player.getEssenceCount() >= essencePrice;
				var description = "To identify the " + c.getName()
						+ ", you can either spend " + essencePrice
						+ " arcane essences to do it yourself"
						+ (sufficient ? "" : " ([style.italicsBad(which you don't have)])")
						+ ", or go to a vendor and pay " + IDENTIFICATION_PRICE
						+ " flames to have them do it for you.";
				if(!sufficient)
					return new Response("Identify (<i>" + essencePrice + " Essences</i>)", description, null);
				return new Response(
						"Identify ([style.italicsArcane(" + essencePrice + " Essences)])",
						description,
						CLOTHING_INVENTORY) {
					@Override
					public void effects() {
						player.incrementEssenceCount(-essencePrice, false);
						String enchantmentRemovedString = c.setEnchantmentKnown(owner, true);
						clothing = AbstractClothing.enchantmentRemovedClothing;
						Main.game.getTextEndStringBuilder()
								.append("<p>")
								.append("You channel the power of ")
								.append(Util.intToString(essencePrice))
								.append(" of your arcane essences into the ")
								.append(c.getName())
								.append(", and as it emits a faint purple glow,")
								.append(" you find yourself able to detect what sort of enchantment it has!")
								.append("</p>")
								.append(enchantmentRemovedString)
								.append("<p style='text-align:center;'>")
								.append("Identifying the ")
								.append(clothing.getName())
								.append(" has cost you [style.boldBad(")
								.append(Util.intToString(essencePrice))
								.append(")] [style.boldArcane(Arcane Essences)]!")
								.append("</p>");
						RenderingEngine.setPage(player, clothing);
					}
				};
			}
			return new Response("Enchant", "Enchant this clothing.", EnchantmentDialogue.ENCHANTMENT_MENU) {
				@Override
				public DialogueNode getNextDialogue() {
					return EnchantmentDialogue.getEnchantmentMenu(c);
				}
			};
		}
		if(index == 5) {
			String itemString = x instanceof AbstractWeapon ? "weapon" : "item";
			if(x.getEnchantmentItemType(null)==null || x.getItemTags().contains(ItemTag.UNENCHANTABLE))
				return new Response("Enchant", "This " + itemString + " cannot be enchanted!", null);
			if(!Main.game.isDebugMode()
					&& (!player.hasQuest(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)
							|| !player.isQuestCompleted(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)))
				return new Response("Enchant", getEnchantmentNotDiscoveredText(itemString + "s"), null);
			return new Response("Enchant", "Enchant this " + itemString + ".", EnchantmentDialogue.ENCHANTMENT_MENU) {
				@Override
				public DialogueNode getNextDialogue() {
					return EnchantmentDialogue.getEnchantmentMenu(x);
				}
			};
		}
		if((index == 6 || index == 7) && x instanceof AbstractItem i) {
			boolean once = index == 6;
			var title = Util.capitaliseSentence(i.getItemType().getUseName()) + (once ? " (Self)" : " all (Self)");
			if(!i.isAbleToBeUsedFromInventory())
				return new Response(title, i.getUnableToBeUsedFromInventoryDescription(), null);
			if(!i.isAbleToBeUsed(player))
				return new Response(title, i.getUnableToBeUsedDescription(player), null);
			boolean breakOut = i.isBreakOutOfInventory();
			if(!once && breakOut)
				return new Response(title, "As this item has special effects, you can only use one at a time!", null);
			if(breakOut)
				return new ResponseEffectsOnly(
						title,
						i.getItemType().getUseTooltipDescription(owner, owner)) {
					@Override
					public void effects() {
						player.useItem(i, player, false);
						resetPostAction();
					}
				};
			return new Response(
					title,
					i.getItemType().getUseTooltipDescription(owner, owner)
							+ (once ? ""
									: "<br/>[style.italicsMinorGood(Repeat this for all of the "
											+ i.getNamePlural()
											+ " which are in your inventory.)]"),
					INVENTORY_MENU) {
				@Override
				public void effects() {
					int itemCount = once ? 1 : player.getItemCount(i);
					for(int j = 0; j < itemCount; j++)
						Main.game.getTextEndStringBuilder()
								.append("<p style='text-align:center;'>")
								.append(player.useItem(i, player, false))
								.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index >= 6 && index <= 9 && x instanceof AbstractClothing c
				&& index - 6 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index - 6);
			var title = "Equip: " + Util.capitaliseSentence(slot.getName());
			if(!c.isCanBeEquipped(player, slot))
				return new Response(title, c.getCannotBeEquippedText(player, slot), null);
			return new Response(title, "Equip the " + c.getName() + ".", INVENTORY_MENU) {
				@Override
				public void effects() {
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(equipClothingFromInventory(player, slot, player, c))
							.append("</p>");
				}
			};
		}
		if((index == 6 || index == 7) && x instanceof AbstractWeapon w) {
			boolean main = index == 6;
			var title = main ? "Equip Main (Self)" : "Equip Offhand (Self)";
			if(!main && w.getWeaponType().isTwoHanded())
				return new Response(title,
						w.getWeaponType().isPlural()
								? "As the " + w.getName() + " require two hands to wield, they can only be equipped in the main slot!"
								: "As the " + w.getName() + " is a two-handed weapon, it can only be equipped in the main slot!",
						null);
			var slot = InventorySlot.mainWeaponSlots[player.getMainWeaponIndexToEquipTo(w)];
			if(!w.isCanBeEquipped(player, slot))
				return new Response(title, w.getCannotBeEquippedText(player, slot), null);
			return new Response(title, "Equip the " + w.getName() + ".", INVENTORY_MENU) {
				@Override
				public void effects() {
					var text = main
							? player.equipMainWeaponFromInventory(w, player)
							: player.equipOffhandWeaponFromInventory(w, player);
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(text)
							.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index == 10)
			return getQuickTradeResponse();
		return null;
	}

	private static Response getPlayerItemResponseToNPCDuringCombat(AbstractCoreItem x, int ignoredResponseTab, int index) {
		if(index == 1 || index == 2 || index == 3) {
			String title = index == 1 ? "Give (1)" : index == 2 ? "Give (5)" : "Give (All)";
			var itemString = x instanceof AbstractClothing ? "clothing" : x instanceof AbstractWeapon ? "weapons" : "items";
			return new Response(title, "You can't give someone " + itemString + " while fighting them!", null);
		}
		if(index == 4 && x instanceof AbstractClothing)
			return new Response("Dye", "You can't dye your clothing while fighting someone!", null);
		if(index == 4 && x instanceof AbstractWeapon)
			return new Response("Dye", "You can't dye your weapons while fighting someone!", null);
		if(index == 5) {
			if(x instanceof AbstractClothing c && c.isCondom()) {
				boolean broken = c.getCondomEffect().getPotency().isNegative();
				return new Response(
						broken ? "Repair (<i>1 Essence</i>)" : "Sabotage",
						"You can't " + (broken ? "repair" : "sabotage") + " the condom while fighting someone!",
						null);
			}
			var itemString = x instanceof AbstractClothing ? "clothing" : x instanceof AbstractWeapon ? "weapons" : "items";
			return new Response("Enchant", "You can't enchant " + itemString + " while fighting someone!", null);
		}
		if((index == 6 || index == 7 || index == 11 || index == 12) && x instanceof AbstractItem i) {//TODO on ally though???
			boolean self = index == 6 || index == 7;
			boolean once = index == 6 || index == 11;
			var player = Main.game.getPlayer();
			var title = Util.capitaliseSentence(i.getItemType().getUseName())
					+ (once ? "" : " all")
					+ (self ? " (Self)" : " (Opponent)");
			if(!once)
				return new Response(title, "You can only use one item at a time during combat!", null);
			if(player.isStunned())
				return new Response(title, "You cannot use any items while you're stunned!", null);
			if(Main.combat.isCombatantDefeated(player))
				return new Response(title, "You cannot use any items while you're defeated!", null);
			int cost = CombatMove.ITEM_USAGE.getAPcost(player);
			if(player.getRemainingAP() < cost)
				return new Response(title, "You need at least " + cost + " AP to use this actions!", null);
			if(self ? !i.isAbleToBeUsedInCombatAllies() : !i.isAbleToBeUsedInCombatEnemies())
				return new Response(title, "You cannot use this during combat!", null);
			if(!i.isAbleToBeUsedFromInventory())
				return new Response(title, i.getUnableToBeUsedFromInventoryDescription(), null);
			if(!i.isAbleToBeUsed(player))
				return new Response(title, i.getUnableToBeUsedDescription(self ? player : inventoryNPC), null);
			var fetish = self ? null
					: i.getItemType().isFetishGiving() ? Fetish.FETISH_KINK_GIVING
							: i.getItemType().isTransformative() ? Fetish.FETISH_TRANSFORMATION_GIVING : null;
			return new Response(
					title,
					i.getItemType().getUseTooltipDescription(owner, self ? owner : inventoryNPC),
					Main.combat.ENEMY_ATTACK,
					fetish == null ? null : List.of(fetish),
					fetish == null ? null : fetish.getAssociatedCorruptionLevel(),
					null,
					null,
					null) {
				@Override
				public void effects() {
					Main.combat.addItemToBeUsed(owner, self ? owner : inventoryNPC, i);
					resetPostAction();
					Main.mainController.openInventory();
				}
			};
		}
		if(index >= 6 && index <= 9 && x instanceof AbstractClothing c
				&& index - 6 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index-6);
			return new Response(
					"Equip: " + Util.capitaliseSentence(slot.getName()),
					"You cannot change your clothes while fighting someone!",
					null);
		}
		if((index == 6 || index == 7) && x instanceof AbstractWeapon) {
			boolean main = index == 6;
			var title = main ? "Equip Main (Self)" : "Equip Offhand (Self)";
			return new Response(title, "You can't change weapons while fighting someone!", null);
		}
		if(index == 10)
			return getQuickTradeResponse();
		if(index >= 11 && index <= 14 && x instanceof AbstractClothing c
				&& index - 11 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index-11);
			return new Response(
					"Equip: " + Util.capitaliseSentence(slot.getName()) + " (Opponent)",
					"You can't make your opponent equip clothing while fighting them!",
					null);
		}
		if(index == 11 && x instanceof AbstractWeapon)
			return new Response("Equip (Opponent)", "You can't make your opponent equip a weapon!", null);
		return null;
	}

	private static Response getPlayerItemResponseToNPCDuringManagement(AbstractCoreItem x, int ignoredResponseTab, int index) {
		var player = Main.game.getPlayer();
		var itemString = x instanceof AbstractWeapon ? "weapon" : "item";
		if(index == 1 || index == 2 || index == 3) {
			var title = index == 1 ? "Give (1)" : index == 2 ? "Give (5)" : "Give (All)";
			var name = index == 1 ? x.getName() : x.getNamePlural();
			int availableCount = player.getItemCount(x);
			if(index == 2 && availableCount < 5)
				return new Response(title, "You don't have five " + name + " to give!", null);
			if(!x.getType().isAbleToBeDropped())
				return new Response(title, "You cannot give away the " + name + "!", null);
			if(inventoryNPC.isInventoryFull() && !inventoryNPC.hasItem(x))
				return new Response(
						title,
						UtilText.parse(inventoryNPC, "[npc.NamePos] inventory is already full!"),
						null);
			var someOf = index == 1 ? "" : index == 2 ? "five of " : "all of ";
			return new Response(
					title,
					UtilText.parse(inventoryNPC, "Give [npc.name] " + someOf + "your " + name + "."),
					INVENTORY_MENU) {
				@Override
				public void effects() {
					int count = index == 1 ? 1 : index == 2 ? 5 : availableCount;
					transferItems(player, inventoryNPC, x, count);
				}
			};
		}
		if(index == 4 && x instanceof AbstractClothing c) {
			if(!player.hasItemType(ItemType.DYE_BRUSH)
					&& !player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response("Dye", "You'll need to find a dye-brush if you want to dye your clothes.", null);
			boolean hasFullInventory = player.isInventoryFull();
			boolean isDyeingStackItem = player.getAllClothingInInventory().get(c) > 1;
			if (isDyeingStackItem && hasFullInventory)
				return new Response("Dye", "Your inventory is full, so you can't dye this item of clothing.", null);
			return new Response("Dye",
					player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							? "Use your proficiency with [style.colourEarth(Earth spells)] to dye this item."
							: "Use a dye-brush to dye this item of clothing.",
					DYE_CLOTHING) {
				@Override
				public void effects() {
					resetClothingDyeColours();
				}
			};
		}
		if(index == 4 && x instanceof AbstractWeapon w) {
			if(!player.hasItemType(ItemType.DYE_BRUSH)
					&& !player.hasItemType(ItemType.REFORGE_HAMMER)
					&& !player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response(
						"Dye/Reforge",
						"You'll need to find a dye-brush or reforge hammer"
								+ " if you want to alter this weapon's properties.",
						null);
			if(player.getAllWeaponsInInventory().get(w) > 1
					&& player.isInventoryFull()
					&& w.getRarity() != Rarity.QUEST)
				return new Response(
						"Dye/Reforge",
						"Your inventory is full, so you can't alter this weapon's properties.",
						null);
			return new Response("Dye/Reforge",
					player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							? "Use your proficiency with [style.colourEarth(Earth spells)] to dye or reforge this item."
							: "Use a dye-brush or reforge hammer to alter this weapon's properties.",
					DYE_WEAPON) {
				@Override
				public void effects() {
					resetWeaponDyeColours();
				}
			};
		}
		if(index == 5 && x instanceof AbstractClothing c) {
			if(c.isCondom())
				return getCondomSabotageResponse(c);
			if(!Main.game.isDebugMode()
					&& (!player.hasQuest(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)
							|| !player.isQuestCompleted(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)))
				return new Response("Enchant", getEnchantmentNotDiscoveredText("clothing"), null);
			if(c.getEnchantmentItemType(null) == null || c.getItemTags().contains(ItemTag.UNENCHANTABLE))
				return new Response("Enchant", "This clothing cannot be enchanted!", null);
			if(!c.isEnchantmentKnown()) {
				int essencePrice = IDENTIFICATION_ESSENCE_PRICE;
				boolean sufficient = player.getEssenceCount() >= essencePrice;
				var description = "To identify the " + c.getName() + ", you can either spend " + essencePrice
						+ " arcane essences to do it yourself"
						+ (sufficient ? "" : " ([style.italicsBad(which you don't have)])")
						+ ", or go to a vendor and pay " + IDENTIFICATION_PRICE + " flames to have them do it for you.";
				if(!sufficient)
					return new Response("Identify (<i>"+essencePrice+" Essences</i>)", description, null);
				return new Response(
						"Identify ([style.italicsArcane("+essencePrice+" Essences)])",
						description,
						CLOTHING_INVENTORY) {
					@Override
					public void effects() {
						player.incrementEssenceCount(-essencePrice, false);
						String enchantmentRemovedString = c.setEnchantmentKnown(owner, true);
						clothing = AbstractClothing.enchantmentRemovedClothing;
						Main.game.getTextEndStringBuilder()
								.append("<p>")
								.append("You channel the power of ")
								.append(Util.intToString(essencePrice))
								.append(" of your arcane essences into the ")
								.append(c.getName())
								.append(", and as it emits a faint purple glow,")
								.append(" you find yourself able to detect what sort of enchantment it has!")
								.append("</p>")
								.append(enchantmentRemovedString)
								.append("<p style='text-align:center;'>")
								.append("Identifying the ")
								.append(clothing.getName())
								.append(" has cost you [style.boldBad(")
								.append(Util.intToString(essencePrice))
								.append(")] [style.boldArcane(Arcane Essences)]!")
								.append("</p>");
						RenderingEngine.setPage(player, clothing);
					}
				};
			}
			return new Response("Enchant", "Enchant this clothing.", EnchantmentDialogue.ENCHANTMENT_MENU) {
				@Override
				public DialogueNode getNextDialogue() {
					return EnchantmentDialogue.getEnchantmentMenu(clothing);
				}
			};
		}
		if(index == 5) {
			if(x.getEnchantmentItemType(null) == null || x.getItemTags().contains(ItemTag.UNENCHANTABLE))
				return new Response("Enchant", "This " + itemString + " cannot be enchanted!", null);
			if(Main.game.isDebugMode()
					|| (player.hasQuest(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)
							&& player.isQuestCompleted(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)))
				return new Response("Enchant", getEnchantmentNotDiscoveredText(itemString + "s"), null);
			return new Response("Enchant", "Enchant this " + itemString + ".", EnchantmentDialogue.ENCHANTMENT_MENU) {
				@Override
				public DialogueNode getNextDialogue() {
					return EnchantmentDialogue.getEnchantmentMenu(x);
				}
			};
		}
		if((index == 6 || index == 7 || index == 11 || index == 12) && x instanceof AbstractItem i) {
			boolean once = index == 6 || index == 11;
			boolean self = index == 6 || index == 7;
			String title = Util.capitaliseSentence(i.getItemType().getUseName())
					+ (once ? "" : " all")
					+ (self ? " (Self)" : UtilText.parse(inventoryNPC, " ([npc.HerHim])"));
			if(!i.isAbleToBeUsedFromInventory())
				return new Response(title, i.getUnableToBeUsedFromInventoryDescription(), null);
			var target = self ? player : inventoryNPC;
			if(!i.isAbleToBeUsed(target))
				return new Response(title, i.getUnableToBeUsedDescription(target), null);
			boolean breakOut = i.isBreakOutOfInventory();
			if(!once && breakOut)
				return new Response(title, "As this item has special effects, you can only use one at a time!", null);
			if(breakOut)
				return new ResponseEffectsOnly(title, i.getItemType().getUseTooltipDescription(owner, owner)) {
					@Override
					public void effects() {
						player.useItem(i, target, false);
						resetPostAction();
					}
				};
			var fetish = self ? null
					: i.getItemType().isFetishGiving() ? Fetish.FETISH_KINK_GIVING
					: i.getItemType().isTransformative() ? Fetish.FETISH_TRANSFORMATION_GIVING : null;
			return new Response(
					title,
					i.getItemType().getUseTooltipDescription(owner, self ? owner : inventoryNPC)
							+ (once ? ""
									: "<br/>[style.italicsMinorGood(Repeat this for all of the "
											+ i.getNamePlural()
											+ " which are in your inventory.)]"),
					INVENTORY_MENU,
					fetish == null ? null : List.of(fetish),
					fetish == null ? null : fetish.getAssociatedCorruptionLevel(),
					null,
					null,
					null) {
				@Override
				public void effects() {
					int itemCount = once ? 1 : player.getItemCount(i);
					for(int j = 0; j < itemCount; j++) {
						if (self)
							Main.game.getTextEndStringBuilder()
									.append("<p style='text-align:center;'>")
									.append(player.useItem(i, player, false))
									.append("</p>");
						else
							Main.game.getTextEndStringBuilder()
									.append(inventoryNPC.getItemUseEffects(i, owner, player, inventoryNPC).getValue());
					}
					resetPostAction();
				}
			};
		}
		if(index >= 6 && index <= 9 && x instanceof AbstractClothing c
				&& index - 6 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index - 6);
			var title = "Equip: " + Util.capitaliseSentence(slot.getName());
			if(!c.isCanBeEquipped(player, slot))
				return new Response(title, c.getCannotBeEquippedText(player, slot), null);
			return new Response(title, "Equip the " + c.getName() + ".", INVENTORY_MENU) {
				@Override
				public void effects() {
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(equipClothingFromInventory(player, slot, player, c))
							.append("</p>");
				}
			};
		}
		if((index == 6 || index == 7 || index == 11 || index == 12) && x instanceof AbstractWeapon w) {
			boolean main = index == 6 || index == 11;
			boolean self = index == 6 || index == 7;
			var title = (main ? "Equip Main " : "Equip Offhand ")
					+ (self ? " (Self)" : UtilText.parse(inventoryNPC, " ([npc.HerHim])"));
//			if(!w.getWeaponType().isAbleToBeDropped())
//				return new Response(title, "You cannot give away the " + w.getName() + "!", null);
			if(!main && w.getWeaponType().isTwoHanded())
				return new Response(title,
						"As the " + w.getName() + (w.getWeaponType().isPlural()
								? " require two hands to wield, they can only be equipped in the main slot!"
								: " is a two-handed weapon, it can only be equipped in the main slot!"),
						null);
			var target = self ? player : inventoryNPC;
			var slot = main
					? InventorySlot.mainWeaponSlots[target.getMainWeaponIndexToEquipTo(w)]
					: InventorySlot.offhandWeaponSlots[target.getOffhandWeaponIndexToEquipTo(w)];
			if(!w.isCanBeEquipped(target, slot))
				return new Response(title, w.getCannotBeEquippedText(target, slot), null);
			var description = (self
					? "Equip the " + w.getName() + " as your "
					: UtilText.parse(inventoryNPC, "Make [npc.name] equip the " + w.getName() + " as [npc.her] "))
					+ (main ? "main" : "offhand") + " weapon.";
			return new Response(
					title,
					description,
					INVENTORY_MENU) {
				@Override
				public void effects() {
					var text = main
							? target.equipMainWeaponFromInventory(w, player)
							: target.equipOffhandWeaponFromInventory(w, player);
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(text)
							.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index == 10)
			return getQuickTradeResponse();
		if(index >= 11 && index <= 14 && x instanceof AbstractClothing c
				&& index - 11 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index-11);
			var title = UtilText.parse(inventoryNPC,
					"Equip: " + Util.capitaliseSentence(slot.getName()) + " ([npc.HerHim])");
//			if(!c.getClothingType().isAbleToBeDropped())
//				return new Response(title, "You cannot give away the " + clothing.getName() + "!", null);
			var equipAllowed = inventoryNPC.isInventoryEquipAllowed(clothing, slot);
			if(!equipAllowed.getKey())
				return new Response(title, UtilText.parse(inventoryNPC, equipAllowed.getValue()), null);
			if(!clothing.isCanBeEquipped(inventoryNPC, slot))
				return new Response(title, clothing.getCannotBeEquippedText(inventoryNPC, slot), null);
			if(!inventoryNPC.isAbleToEquip(clothing, slot, true, player)
					|| !clothing.isEnslavementClothing()
					|| (inventoryNPC.isSlave() && inventoryNPC.getOwner().isPlayer()))
				return new Response(
						title,
						UtilText.parse(inventoryNPC, "Make [npc.name] equip the "+clothing.getName()+"!"),
						INVENTORY_MENU) {
					@Override
					public void effects() {
						Main.game.getTextEndStringBuilder()
								.append("<p style='text-align:center;'>")
								.append(equipClothingFromInventory(inventoryNPC, slot, player, clothing))
								.append("</p>");
					}
				};
			boolean willEnslave = !inventoryNPC.isSlave()
					&& inventoryNPC.isAbleToBeEnslaved()
					&& player.isHasSlaverLicense();
			var description = "Make [npc.name] equip the "+clothing.getName()+".<br/><i>"
					+ (willEnslave ? "Thanks to" : "Despite")
					+ "the fact that the " + clothing.getName() + " "
					+ (clothing.getClothingType().isPlural()
							? "have an enslavement enchantment, they"
							: "has an enslavement enchantment, it")
					+ (willEnslave
							? " [style.colourArcane(will enslave [npc.name])], making you [npc.her] new owner"
							: " [style.colourMinorBad(will not enslave [npc.name])], as"
									+ (player.isHasSlaverLicense()
											? " [npc.sheIsFull] not a suitable target for enslavement"
											: " you do not possess a slaver license"))
					+ "!</i>";
			return new Response(
					UtilText.parse(inventoryNPC,
							(willEnslave ? "[style.colourArcane(Equip: " : "[style.colourMinorBad(Equip: ")
									+ Util.capitaliseSentence(slot.getName())+" ([npc.HerHim]))]"),
					UtilText.parse(inventoryNPC, description),
					INVENTORY_MENU) {
				@Override
				public DialogueNode getNextDialogue() {
					var enslavementDialogue = inventoryNPC.getEnslavementDialogue(clothing);
					return Objects.requireNonNullElse(enslavementDialogue, INVENTORY_MENU);
				}
				@Override
				public void effects() {
					var enslavementTargets = new ArrayList<>(Main.game.getNonCompanionCharactersPresent());
//					enslavementTargets.removeIf(npc -> player.getFriendlyOccupants().contains(npc));
					enslavementTargets.removeIf(npc -> !Main.combat.getEnemies(player).contains(npc));
					SlaveDialogue.setFollowupEnslavementDialogue(enslavementTargets.size() <= 1
							? Main.game.getDefaultDialogue(false)
							: Main.game.getSavedDialogueNode());
					var description = equipClothingFromInventory(inventoryNPC, slot, player, clothing);
					if(inventoryNPC.getEnslavementDialogue(clothing) == null)
						Main.game.getTextEndStringBuilder()
								.append("<p style='text-align:center;'>")
								.append(description)
								.append("</p>");
				}
			};
		}
		return null;
	}

	private static Response getPlayerItemResponseToNPCDuringSex(AbstractCoreItem x, int ignoredResponseTab, int index) {
		if(index == 1 || index == 2 || index == 3) {
			var itemString = x instanceof AbstractClothing ? "clothing" : x instanceof AbstractWeapon ? "weapons" : "items";
			var title = index == 1 ? "Give (1)" : index == 2 ? "Give (5)" : "Give (All)";
			return new Response(title, "You can't give someone " + itemString + " while having sex with them!", null);
		}
		if(index == 4 && x instanceof AbstractClothing)
			return new Response("Dye", "You can't dye your clothing while having sex with someone!", null);
		if(index == 4 && x instanceof AbstractWeapon)
			return new Response("Dye", "You can't dye your weapons while having sex with someone!", null);
		if(index == 5) {
			if(x instanceof AbstractClothing c && c.isCondom()) {
				boolean broken = c.getCondomEffect().getPotency().isNegative();
				return new Response(
						broken ? "Repair (<i>1 Essence</i>)" : "Sabotage",
						"You can't " + (broken ? "repair" : "sabotage") + " the condom while having sex with someone!",
						null);
			}
			var itemString = x instanceof AbstractClothing ? "clothing" : x instanceof AbstractWeapon ? "weapons" : "items";
			return new Response("Enchant", "You can't enchant " + itemString + " while having sex with someone!", null);
		}
		if((index == 6 || index == 11) && x instanceof AbstractItem i) {
			boolean self = index == 6;
			var title = Util.capitaliseSentence(i.getItemType().getUseName())
					+ (self ? " (Self)" : " (partner)");
			if(!Main.sex.isItemUseAvailable())
				return new Response(title, "Items cannot be used during this sex scene!", null);
			if(!i.isAbleToBeUsedInSex())
				return new Response(title, "You cannot use this during sex!", null);
			if(!i.isAbleToBeUsedFromInventory())
				return new Response(title, i.getUnableToBeUsedFromInventoryDescription(), null);
			var target = self ? Main.game.getPlayer() : inventoryNPC;
			if(!i.isAbleToBeUsed(target))
				return new Response(title, i.getUnableToBeUsedDescription(target), null);
			var fetish = self ? null
					: i.getItemType().isFetishGiving() ? Fetish.FETISH_KINK_GIVING
					: i.getItemType().isTransformative() ? Fetish.FETISH_TRANSFORMATION_GIVING : null;
			return new Response(
					title,
					i.getItemType().getUseTooltipDescription(owner, self ? owner : inventoryNPC),
					Main.sex.SEX_DIALOGUE,
					fetish == null ? null : List.of(fetish),
					fetish == null ? null : fetish.getAssociatedCorruptionLevel(),
					null,
					null,
					null) {
				@Override
				public void effects() {
					var otherCharacter = self ? (NPC) Main.sex.getTargetedPartner(Main.game.getPlayer()) : inventoryNPC;
					var reaction = otherCharacter.getItemUseEffects(i, owner, Main.game.getPlayer(), target);
					Main.sex.setUsingItemText(reaction.getValue());
					resetPostAction();
					Main.mainController.openInventory();
					Main.sex.endSexTurn(SexActionUtility.PLAYER_USE_ITEM);
					Main.sex.setSexStarted(true);
				}
			};
		}
		if(index >= 6 && index <= 9 && x instanceof AbstractClothing c
				&& index - 6 < c.getClothingType().getEquipSlots().size()) {
			var player = Main.game.getPlayer();
			var slot = c.getClothingType().getEquipSlots().get(index-6);
			var title = "Equip: "+Util.capitaliseSentence(slot.getName());
			if(!c.isCanBeEquipped(player, slot))
				return new Response(title, c.getCannotBeEquippedText(player, slot), null);
			if(!c.isAbleToBeEquippedDuringSex(slot).getKey())
				return new Response(title, c.isAbleToBeEquippedDuringSex(slot).getValue(), null);
			if(!Main.sex.getInitialSexManager().isAbleToEquipSexClothing(player, player, c))
				return new Response(
						title,
						"As this is a special sex scene, you cannot equip clothing during it!",
						null);
			if(!Main.sex.isClothingEquipAvailable(player, slot, c))
				return new Response(
						title,
						"This slot is involved with an ongoing sex action, so you cannot equip clothing into it!",
						null);
			if(!player.isAbleToEquip(c, slot, false, player))
				return new Response(title, getClothingBlockingRemovalText(player, "equip"), null);
			return new Response(title, "Equip the " + c.getName() + ".", Main.sex.SEX_DIALOGUE) {
				@Override
				public void effects() {
					equipClothingFromInventory(player, slot, player, c);
					Main.sex.setEquipClothingText(c, player.getUnequipDescription());
					Main.mainController.openInventory();
					Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
					Main.sex.setSexStarted(true);
				}
			};
		}
		if((index == 6 || index == 7 || index == 11 || index == 12) && x instanceof AbstractWeapon) {
			boolean self = index == 6 || index == 7;
			boolean main = index == 6 || index == 11;
			var title = (main ? "Equip Main" : "Equip Offhand")
					+ (self ? " (Self)" : UtilText.parse(inventoryNPC, " ([npc.HerHim])"));
			return new Response(title, "You can't equip weapons while having sex with someone!", null);
		}
		if((index == 7 || index == 12) && x instanceof AbstractItem i) {
			boolean self = index == 7;
			return new Response(
					Util.capitaliseSentence(i.getItemType().getUseName()) + (self ? " all (Self)" : " all (partner)"),
					"You can only use one item at a time during sex!",
					null);
		}
		if(index == 10)
			return getQuickTradeResponse();
		if(index >= 11 && index <= 14 && x instanceof AbstractClothing c
				&& index - 11 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index-11);
			var title = UtilText.parse(inventoryNPC,
					"Equip: " + Util.capitaliseSentence(slot.getName()) + " ([npc.HerHim])");
//			if(!c.getClothingType().isAbleToBeDropped())
//				return new Response(title, "You cannot give away the " + c.getName() + "!", null);
			var equipAllowed = inventoryNPC.isInventoryEquipAllowed(c, slot);
			if(!equipAllowed.getKey())
				return new Response(title, UtilText.parse(inventoryNPC, equipAllowed.getValue()), null);
			if(!c.isCanBeEquipped(inventoryNPC, slot))
				return new Response(title, c.getCannotBeEquippedText(inventoryNPC, slot), null);
			if(!c.isAbleToBeEquippedDuringSex(slot).getKey())
				return new Response(title, c.isAbleToBeEquippedDuringSex(slot).getValue(), null);
			if(!Main.sex.getInitialSexManager().isAbleToEquipSexClothing(Main.game.getPlayer(), inventoryNPC, c))
				return new Response(
						title,
						"As this is a special sex scene, you cannot equip clothing during it!",
						null);
			if(!Main.sex.isClothingEquipAvailable(inventoryNPC, slot, c))
				return new Response(
						title,
						"This slot is involved with an ongoing sex action, so you cannot equip clothing into it!",
						null);
			if(!inventoryNPC.isAbleToEquip(c, slot, false, Main.game.getPlayer()))
				return new Response(
						title,
						UtilText.parse(inventoryNPC,
								"[npc.Name] can't equip the " + c.getName()
										+ ", as other clothing is blocking [npc.herHim] from doing so!"),
						null);
			return new Response(
					title,
					UtilText.parse(inventoryNPC, "Get [npc.name] to equip the " + c.getName() + "."),
					Main.sex.SEX_DIALOGUE) {
				@Override
				public void effects() {
					equipClothingFromInventory(inventoryNPC, slot, Main.game.getPlayer(), c);
					Main.sex.setEquipClothingText(c, inventoryNPC.getUnequipDescription());
					Main.mainController.openInventory();
					Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
					Main.sex.setSexStarted(true);
				}
			};
		}
		return null;
	}

	private static Response getPlayerItemResponseToNPCDuringTrading(AbstractCoreItem x, int ignoredResponseTab, int index) {
		var player = Main.game.getPlayer();
		if(index == 1 || index == 2 || index == 3) {
			var title = index == 1 ? "Sell (1)" : index == 2 ? "Sell (5)" : "Sell (All)";
			var name = index == 1 ? x.getName() : x.getNamePlural();
			int availableCount = player.getItemCount(x);
			if(index == 2 && availableCount < 5)
				return new Response(title, "You don't have five " + name + " to sell!", null);
			if(!x.getType().isAbleToBeSold())
				return new Response(title, "You cannot sell the " + name + "!", null);
			if(!inventoryNPC.willBuy(x))
				return new Response(title, inventoryNPC.getName("The") + " doesn't want to buy this.", null);
			int sellPrice = x.getPrice(inventoryNPC.getBuyModifier());
			int count = index == 1 ? 1 : index == 2 ? 5 : availableCount;
			return new Response(
					title + " (" + UtilText.formatAsMoney(sellPrice * count, "span") + ")",
					"Sell "
							+ (index == 1 ? "the " : index == 2 ? "five of your " : "all of your ")
							+ name
							+ " for "
							+ UtilText.formatAsMoney(sellPrice * count)
							+ ".",
					INVENTORY_MENU) {
				@Override
				public void effects() {
					sellItems(player, inventoryNPC, x, count, sellPrice);
				}
			};
		}
		if(index == 4 && x instanceof AbstractClothing c) {
			if(!player.hasItemType(ItemType.DYE_BRUSH)
					&& !player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response("Dye", "You'll need to find a dye-brush if you want to dye your clothes.", null);
			if (player.getAllClothingInInventory().get(c) > 1
					&& player.isInventoryFull()
					&& c.getRarity() != Rarity.QUEST)
				return new Response("Dye", "Your inventory is full, so you can't dye this item of clothing.", null);
			return new Response("Dye",
					player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							? "Use your proficiency with [style.colourEarth(Earth spells)] to dye this item."
							: "Use a dye-brush to dye this item of clothing.",
					DYE_CLOTHING) {
				@Override
				public void effects() {
					resetClothingDyeColours();
				}
			};
		}
		if(index == 4 && x instanceof AbstractWeapon w) {
			if (!player.hasItemType(ItemType.DYE_BRUSH)
					&& !player.hasItemType(ItemType.REFORGE_HAMMER)
					&& !player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response(
						"Dye/Reforge",
						"You'll need to find a dye-brush or reforge hammer"
								+ " if you want to alter this weapon's properties.",
						null);
			if(player.getAllWeaponsInInventory().get(w) > 1
					&& player.isInventoryFull()
					&& w.getRarity() != Rarity.QUEST)
				return new Response(
						"Dye/Reforge",
						"Your inventory is full, so you can't alter this weapon's properties.",
						null);
			return new Response("Dye/Reforge",
					player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							? "Use your proficiency with [style.colourEarth(Earth spells)] to dye or reforge this item."
							: "Use a dye-brush or reforge hammer to alter this weapon's properties.",
					DYE_WEAPON) {
				@Override
				public void effects() {
					resetWeaponDyeColours();
				}
			};
		}
		if(index == 5 && x instanceof AbstractClothing c) {
			if(c.isCondom())
				return getCondomSabotageResponse(c);
			if(!Main.game.isDebugMode()
					&& (!player.hasQuest(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)
							|| !player.isQuestCompleted(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)))
				return new Response("Enchant", getEnchantmentNotDiscoveredText("clothing"), null);
			if(c.getEnchantmentItemType(null) == null || c.getItemTags().contains(ItemTag.UNENCHANTABLE))
				return new Response("Enchant", "This clothing cannot be enchanted!", null);
			if(!c.isEnchantmentKnown()) {
				int essencePrice = IDENTIFICATION_ESSENCE_PRICE;
				boolean sufficient = player.getEssenceCount() >= essencePrice;
				var description = "To identify the " + c.getName() + ", you can either spend " + essencePrice
						+ " arcane essences to do it yourself"
						+ (sufficient ? "" : " ([style.italicsBad(which you don't have)])")
						+ ", or go to a vendor and pay " + IDENTIFICATION_PRICE + " flames to have them do it for you.";
				if(!sufficient)
					return new Response("Identify (<i>" + essencePrice + " Essences</i>)", description, null);
				return new Response(
						"Identify ([style.italicsArcane(" + essencePrice + " Essences)])",
						description,
						CLOTHING_INVENTORY) {
					@Override
					public void effects() {
						player.incrementEssenceCount(-essencePrice, false);
						String enchantmentRemovedString = c.setEnchantmentKnown(owner, true);
						clothing = AbstractClothing.enchantmentRemovedClothing;
						Main.game.getTextEndStringBuilder()
								.append("<p>")
								.append("You channel the power of ")
								.append(Util.intToString(essencePrice))
								.append(" of your arcane essences into the ")
								.append(c.getName())
								.append(", and as it emits a faint purple glow,")
								.append(" you find yourself able to detect what sort of enchantment it has!")
								.append("</p>")
								.append(enchantmentRemovedString)
								.append("<p style='text-align:center;'>")
								.append("Identifying the ")
								.append(clothing.getName())
								.append(" has cost you [style.boldBad(")
								.append(Util.intToString(essencePrice))
								.append(")] [style.boldArcane(Arcane Essences)]!")
								.append("</p>");
						RenderingEngine.setPage(player, clothing);
					}
				};
			}
			return new Response("Enchant", "Enchant this clothing.", EnchantmentDialogue.ENCHANTMENT_MENU) {
				@Override
				public DialogueNode getNextDialogue() {
					return EnchantmentDialogue.getEnchantmentMenu(c);
				}
			};
		}
		if(index == 5) {
			var itemString = x instanceof AbstractWeapon ? "weapon" : "item";
			if(x.getEnchantmentItemType(null) == null || x.getItemTags().contains(ItemTag.UNENCHANTABLE))
				return new Response("Enchant", "This " + itemString + " cannot be enchanted!", null);
			if(!Main.game.isDebugMode()
					&& (!player.hasQuest(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)
							|| !player.isQuestCompleted(QuestLine.SIDE_ENCHANTMENT_DISCOVERY)))
				return new Response("Enchant", getEnchantmentNotDiscoveredText(itemString + "s"), null);
			return new Response("Enchant", "Enchant this " + itemString + ".", EnchantmentDialogue.ENCHANTMENT_MENU) {
				@Override
				public DialogueNode getNextDialogue() {
					return EnchantmentDialogue.getEnchantmentMenu(x);
				}
			};
		}
		if((index == 6 || index == 7) && x instanceof AbstractItem i) {
			boolean once = index == 6;
			var title = Util.capitaliseSentence(i.getItemType().getUseName()) + (once ? " (Self)" : " all (Self)");
			if(!i.isAbleToBeUsedFromInventory())
				return new Response(title, i.getUnableToBeUsedFromInventoryDescription(), null);
			if(!i.isAbleToBeUsed(player))
				return new Response(title, i.getUnableToBeUsedDescription(player), null);
			boolean breakOut = i.isBreakOutOfInventory();
			if(!once && breakOut)
				return new Response(title, "As this item has special effects, you can only use one at a time!", null);
			if(breakOut)
				return new ResponseEffectsOnly(
						title,
						i.getItemType().getUseTooltipDescription(owner, owner)) {
					@Override
					public void effects() {
						player.useItem(i, player, false);
						resetPostAction();
					}
				};
			return new Response(
					title,
					i.getItemType().getUseTooltipDescription(owner, owner)
							+ (once ? ""
									: "<br/>[style.italicsMinorGood(Repeat this for all of the "
											+ i.getNamePlural()
											+ " which are in your inventory.)]"),
					INVENTORY_MENU) {
				@Override
				public void effects() {
					int itemCount = once ? 1 : player.getItemCount(i);
					for(int j = 0; j < itemCount; j++)
						Main.game.getTextEndStringBuilder()
								.append("<p style='text-align:center;'>")
								.append(player.useItem(i, player, false))
								.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index >= 6 && index <= 9 && x instanceof AbstractClothing c
				&& index - 6 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index - 6);
			var title = "Equip: "+Util.capitaliseSentence(slot.getName());
			if(!c.isCanBeEquipped(player, slot))
				return new Response(title, c.getCannotBeEquippedText(player, slot), null);
			return new Response(title, "Equip the " + c.getName() + ".", INVENTORY_MENU){
				@Override
				public void effects(){
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(equipClothingFromInventory(player, slot, player, c))
							.append("</p>");
				}
			};
		}
		if((index == 6 || index == 7) && x instanceof AbstractWeapon w) {
			boolean main = index == 6;
			var title = main ? "Equip Main (Self)" : "Equip Offhand (Self)";
			if(!main && w.getWeaponType().isTwoHanded())
				return new Response(title,
						"As the " + w.getName() + (w.getWeaponType().isPlural()
								? " require two hands to wield, they can only be equipped in the main slot!"
								: " is a two-handed weapon, it can only be equipped in the main slot!"),
						null);
			var slot = main
					? InventorySlot.mainWeaponSlots[player.getMainWeaponIndexToEquipTo(w)]
					: InventorySlot.offhandWeaponSlots[player.getOffhandWeaponIndexToEquipTo(w)];
			if(!w.isCanBeEquipped(player, slot))
				return new Response(title, w.getCannotBeEquippedText(player, slot), null);
			return new Response(
					title,
					"Equip the " + w.getName() + " as your " + (main ? "main" : "offhand") + " weapon.",
					INVENTORY_MENU) {
				@Override
				public void effects() {
					var text = main
							? player.equipMainWeaponFromInventory(w, player)
							: player.equipOffhandWeaponFromInventory(w, player);
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(text)
							.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index == 9)
			return getBuybackResponse();
		if(index == 10 && x instanceof AbstractClothing c && !c.isEnchantmentKnown()) {
			if(!inventoryNPC.willBuy(c))
				return new Response("Identify", inventoryNPC.getName("The") + " can't identify clothing!", null);
			if(player.getMoney() < IDENTIFICATION_PRICE)
				// don't format as money because we don't want to highlight non-selectable choices
				return new Response(
						"Identify (" + UtilText.formatAsMoneyUncoloured(IDENTIFICATION_PRICE, "span") + ")",
						"You don't have enough money!",
						null);
			var priceString = UtilText.formatAsMoney(IDENTIFICATION_PRICE, "span");
			return new Response(
					"Identify (" + priceString + ")",
					"Have the " + c.getName() + " identified for " + priceString + ".",
					CLOTHING_INVENTORY) {
				@Override
				public void effects() {
					player.incrementMoney(-IDENTIFICATION_PRICE);
					String enchantmentRemovedString = c.setEnchantmentKnown(owner, true);
					clothing = AbstractClothing.enchantmentRemovedClothing;
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(UtilText.parse(inventoryNPC,
									"You hand over " + UtilText.formatAsMoney(IDENTIFICATION_PRICE) + " to [npc.name],"
											+ " who promptly feeds several bottles of arcane essence"
											+ " into a specialist identification device,"
											+ " before using it to reveal the enchantment on your "
											+ clothing.getName() + "."))
							.append("</p>")
							.append(enchantmentRemovedString);
					RenderingEngine.setPage(player, clothing);
				}
			};
		}
		if(index == 10)
			return getQuickTradeResponse();
		if((index == 11 || index == 12) && x instanceof AbstractItem i) {
			boolean once = index == 11;
			var title = Util.capitaliseSentence(i.getItemType().getUseName())
					+ (once ? "" : " all")
					+ UtilText.parse(inventoryNPC, " ([npc.HerHim])");
			return new Response(
					title,
					UtilText.parse(inventoryNPC, "[npc.Name] doesn't want to use your items."),
					null);
		}
		if(index >= 11 && index <= 14 && x instanceof AbstractClothing c
				&& index - 11 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index - 11);
			return new Response(
					UtilText.parse(inventoryNPC, "Equip: "+Util.capitaliseSentence(slot.getName())+" ([npc.HerHim])"),
					UtilText.parse(inventoryNPC, "[npc.Name] doesn't want to wear your clothing."),
					null);
		}
		if((index == 11 || index == 12) && x instanceof AbstractWeapon w) {
			boolean main = index == 11;
			return new Response(
					UtilText.parse(inventoryNPC, "Equip " + (main ? "Main" : "Offhand") + " ([npc.HerHim])"),
					UtilText.parse(inventoryNPC, "[npc.Name] doesn't want to use your weapons."),
					null);
		}
		return null;
	}

	private static Response getItemResponseDuringSex(AbstractCoreItem x, int ignoredResponseTab, int index) {
		if(index == 1 || index == 2 || index == 3) {
			var itemString = x instanceof AbstractClothing ? "clothing" : x instanceof AbstractWeapon ? "weapons" : "items";
			var title = index == 1 ? "Take (1)" : index == 2 ? "Take (5)" : "Take (All)";
			return new Response(title, "You can't pick up " + itemString + " while masturbating.", null);
		}
		if(index == 4 && x instanceof AbstractClothing)
			return new Response("Dye", "You can't dye your clothing while masturbating.", null);
		if(index == 4 && x instanceof AbstractWeapon)
			return new Response("Dye", "You can't dye weapons while masturbating.", null);
		if(index == 5) {
			if(x instanceof AbstractClothing c && c.isCondom()) {
				boolean broken = c.getCondomEffect().getPotency().isNegative();
				return new Response(
						broken ? "Repair (<i>1 Essence</i>)" : "Sabotage",
						"You can't " + (broken ? "repair" : "sabotage") + " condoms on the ground!",
						null);
			}
			var itemString = x instanceof AbstractClothing ? "clothing" : x instanceof AbstractWeapon ? "weapons" : "items";
			return new Response("Enchant", "You can't enchant " + itemString + " while masturbating.", null);
		}
		if((index == 6 || index == 7) && x instanceof AbstractItem i) {
			var player = Main.game.getPlayer();
			boolean once = index == 6;
			var title = Util.capitaliseSentence(i.getItemType().getUseName()) + (once ? "" : " all") + " (Self)";
			if(!once)
				return new Response(title, "You can only use one item at a time during sex!", null);
			if(!Main.sex.isItemUseAvailable())
				return new Response(title, "Items cannot be used during this sex scene!", null);
			if(!i.isAbleToBeUsedInSex())
				return new Response(title, "You cannot use this during sex!", null);
			if(!i.isAbleToBeUsedFromInventory())
				return new Response(title, i.getUnableToBeUsedFromInventoryDescription(), null);
			if(!i.isAbleToBeUsed(player))
				return new Response(title, i.getUnableToBeUsedDescription(player), null);
			return new Response(
					title,
					i.getItemType().getUseTooltipDescription(player, player),
					Main.sex.SEX_DIALOGUE) {
				@Override
				public void effects() {
					Main.sex.setUsingItemText(player.useItem(i, player, true));
					resetPostAction();
					Main.mainController.openInventory();
					Main.sex.endSexTurn(SexActionUtility.PLAYER_USE_ITEM);
					Main.sex.setSexStarted(true);
				}
			};
		}
		if(index >= 6 && index <= 9 && x instanceof AbstractClothing c
				&& index - 6 < c.getClothingType().getEquipSlots().size()) {
			var player = Main.game.getPlayer();
			var slot = c.getClothingType().getEquipSlots().get(index-6);
			var title = "Equip: " + Util.capitaliseSentence(slot.getName());
			if(!c.isCanBeEquipped(player, slot))
				return new Response(title, c.getCannotBeEquippedText(player, slot), null);
			if(!c.isAbleToBeEquippedDuringSex(slot).getKey())
				return new Response(title, c.isAbleToBeEquippedDuringSex(slot).getValue(), null);
			if(!Main.sex.getInitialSexManager().isAbleToEquipSexClothing(player, player, c))
				return new Response(
						title,
						"As this is a special sex scene, you cannot equip clothing during it!",
						null);
			if(!Main.sex.isClothingEquipAvailable(player, slot, c))
				return new Response(
						title,
						"This slot is involved with an ongoing sex action, so you cannot equip clothing into it!",
						null);
			if(!player.isAbleToEquip(c, slot, false, player))
				return new Response(title, getClothingBlockingRemovalText(player, "equip"), null);
			return new Response(title, "Equip the " + c.getName() + ".", Main.sex.SEX_DIALOGUE) {
				@Override
				public void effects() {
					equipClothingFromGround(player, slot, player, c);
					Main.sex.setEquipClothingText(c, player.getUnequipDescription());
					Main.mainController.openInventory();
					Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
					Main.sex.setSexStarted(true);
				}
			};
		}
		if((index == 6 || index == 7) && x instanceof AbstractWeapon) {
			boolean main = index == 6;
			var title = main ? "Equip Main (Self)" : "Equip Offhand (Self)";
			return new Response(title, "You can't equip weapons while masturbating.", null);
		}
		if(index == 10)
			return getQuickTradeResponse();
		return null;
	}

	private static Response getItemResponse(AbstractCoreItem x, int ignoredResponseTab, int index) {
		var player = Main.game.getPlayer();
		if(index == 1 || index == 2 || index == 3) {
			var title = index == 1 ? "Take (1)" : index == 2 ? "Take (5)" : "Take (All)";
			var name = index == 1 ? x.getName() : x.getNamePlural();
			int availableCount = Main.game.getPlayerCell().getInventory().getItemCount(x);
			if(index == 2 && availableCount < 5)
				return new Response(title, "There aren't five " + name + " on the ground!", null);
			if(player.isInventoryFull()
					&& !player.hasItem(x)
					&& x.getRarity() != Rarity.QUEST)
				return new Response(title, "Your inventory is already full!", null);
			return new Response(
					title,
					String.format("Take %s %s from the ground.",
							index == 1 ? "one" : index == 2 ? "five of your" : "all of your",
							name),
					INVENTORY_MENU) {
				@Override
				public void effects() {
					int count = index == 1 ? 1 : index == 2 ? 5 : availableCount;
					pickUpItems(player, x, count);
				}
			};
		}
		if(index == 4 && x instanceof AbstractClothing c) {
			if(!player.hasItemType(ItemType.DYE_BRUSH)
					&& !player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response("Dye", "You'll need to find a dye-brush if you want to dye your clothes.", null);
			if(Main.game.getPlayerCell().getInventory().getAllClothingInInventory().get(clothing) > 1
					&& Main.game.getPlayerCell().getInventory().isInventoryFull())
				return new Response("Dye", "Your inventory is full, so you can't dye this item of clothing.", null);
			return new Response("Dye",
					player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							? "Use your proficiency with [style.colourEarth(Earth spells)] to dye this item."
							: "Use a dye-brush to dye this item of clothing.",
					DYE_CLOTHING) {
				@Override
				public void effects() {
					resetClothingDyeColours();
				}
			};
		}
		if(index == 4 && x instanceof AbstractWeapon w) {
			if(!player.hasItemType(ItemType.DYE_BRUSH)
					&& !player.hasItemType(ItemType.REFORGE_HAMMER)
					&& !player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response(
						"Dye/Reforge",
						"You'll need to find a dye-brush or reforge hammer"
								+ " if you want to alter this weapon's properties.",
						null);
			if(Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().get(w) > 1
					&& Main.game.getPlayerCell().getInventory().isInventoryFull()
					&& w.getRarity() != Rarity.QUEST)
				return new Response(
						"Dye/Reforge",
						"Your inventory is full, so you can't alter this weapon's properties.",
						null);
			return new Response("Dye/Reforge",
					player.isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							? "Use your proficiency with [style.colourEarth(Earth spells)] to dye or reforge this item."
							: "Use a dye-brush or reforge hammer to alter this weapon's properties.",
					DYE_WEAPON) {
				@Override
				public void effects() {
					resetWeaponDyeColours();
				}
			};
		}
		if(index == 5) {
			if(x instanceof AbstractClothing c && c.isCondom()) {
				boolean broken = c.getCondomEffect().getPotency().isNegative();
				return new Response(
						broken ? "Repair (<i>1 Essence</i>)" : "Sabotage",
						"You can't " + (broken ? "repair" : "sabotage") + " condoms on the ground!",
						null);
			}
			var itemString = x instanceof AbstractClothing ? "clothing" : x instanceof AbstractWeapon ? "weapons" : "items";
			return new Response("Enchant", "You can't enchant " + itemString + " on the ground!", null);
		}
		if((index == 6 || index == 7) && x instanceof AbstractItem i) {
			boolean once = index == 6;
			var title = Util.capitaliseSentence(i.getItemType().getUseName()) + (once ? "" : " all") + " (Self)";
			if(!i.isAbleToBeUsedFromInventory())
				return new Response(title, i.getUnableToBeUsedFromInventoryDescription(), null);
			if(!i.isAbleToBeUsed(player))
				return new Response(title, i.getUnableToBeUsedDescription(player), null);
			boolean breakOut = i.isBreakOutOfInventory();
			if(!once && breakOut)
				return new Response(title, "As this item has special effects, you can only use one at a time!", null);
			if(breakOut)
				return new ResponseEffectsOnly(
						title,
						i.getItemType().getUseTooltipDescription(player, player)) {
					@Override
					public void effects() {
						player.useItem(i, player, true);
						resetPostAction();
					}
				};
			return new Response(
					title,
					i.getItemType().getUseTooltipDescription(player, player)
							+ (once ? ""
									: "<br/>[style.italicsMinorGood(Repeat this for all of the "
											+ i.getNamePlural()
											+ " which are in this area.)]"),
					INVENTORY_MENU) {
				@Override
				public void effects() {
					int itemCount = once ? 1 : Main.game.getPlayerCell().getInventory().getItemCount(i);
					for(int j = 0; j < itemCount; j++)
						Main.game.getTextEndStringBuilder()
								.append("<p style='text-align:center;'>")
								.append(player.useItem(i, player, true))
								.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index >= 6 && index <= 9 && x instanceof AbstractClothing c
				&& index - 6 < c.getClothingType().getEquipSlots().size()) {
			var slot = c.getClothingType().getEquipSlots().get(index-6);
			var title = "Equip: " + Util.capitaliseSentence(slot.getName());
			if(!c.isCanBeEquipped(player, slot))
				return new Response(title, c.getCannotBeEquippedText(player, slot), null);
			return new Response(title, "Equip the " + c.getName() + ".", INVENTORY_MENU) {
				@Override
				public void effects() {
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(equipClothingFromGround(player, slot, player, c))
							.append("</p>");
				}
			};
		}
		if((index == 6 || index == 7) && x instanceof AbstractWeapon w) {
			boolean main = index == 6;
			var title = main ? "Equip Main (Self)" : "Equip Offhand (Self)";
			var slot = main
					? InventorySlot.mainWeaponSlots[player.getMainWeaponIndexToEquipTo(w)]
					: InventorySlot.offhandWeaponSlots[player.getOffhandWeaponIndexToEquipTo(w)];
			if(!w.isCanBeEquipped(player, slot))
				return new Response(title, w.getCannotBeEquippedText(player, slot), null);
			if(!main && w.getWeaponType().isTwoHanded())
				return new Response(
						title,
						"As the " + w.getName() + (w.getWeaponType().isPlural()
								? " require two hands to wield, they can only be equipped in the main slot!"
								: " is a two-handed weapon, it can only be equipped in the main slot!"),
						null);
			return new Response(title, "Equip the " + w.getName() + " as your main weapon.", INVENTORY_MENU) {
				@Override
				public void effects() {
					var text = main ? player.equipMainWeaponFromFloor(w) : player.equipOffhandWeaponFromFloor(w);
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(text)
							.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index == 10)
			return getQuickTradeResponse();
		return null;
	}

	private static Response getItemResponseToNPCDuringCombat(int ignoredResponseTab, int index) {
		if(index == 1)
			return new Response("Take (1)", "You can't take someone items while fighting them!", null);
		if(index == 2)
			return new Response("Take (5)", "You can't take someone items while fighting them!", null);
		if(index == 3)
			return new Response("Take (All)", "You can't take someone items while fighting them!", null);
		if(index == 5)
			return new Response(
					"Enchant",
					"You can't enchant someone else's items, especially not while fighting them!",
					null);
		if(index == 6)
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName()) + " (Self)",
					"You can't use someone else's items while fighting them!",
					null);
		if(index == 7)
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName()) + " all (Self)",
					"You can't use someone else's items while fighting them!",
					null);
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11)
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName()) + " (Opponent)",
					"You can't use make someone use an item while fighting them!",
					null);
		if(index == 12)
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName()) + " all (Opponent)",
					"You can't use make someone use an item while fighting them!",
					null);
		return null;
	}

	private static Response getItemResponseToNPCDuringManagement(int ignoredResponseTab, int index) {
		var player = Main.game.getPlayer();
		if(index == 1 || index == 2 || index == 3) {
			String title = index == 1 ? "Take (1)" : index == 2 ? "Take (5)" : "Take (All)";
			if(index == 2 && inventoryNPC.getItemCount(item) < 5)
				return new Response(
						title,
						UtilText.parse(inventoryNPC, "[npc.Name] doesn't have five " + item.getNamePlural() + "!"),
						null);
			if(player.isInventoryFull()
					&& !Main.game.getPlayer().hasItem(item)
					&& item.getRarity()!=Rarity.QUEST)
				return new Response(title, "Your inventory is already full!", null);
			String description = (index == 1 ? "Take"
					: (index == 2 ? "Take five"
							: "Take all " + Util.intToString(inventoryNPC.getItemCount(item))) + " of [npc.namePos] ")
					+ item.getNamePlural() + ".";
			return new Response(
					title,
					UtilText.parse(inventoryNPC, description),
					INVENTORY_MENU) {
				@Override
				public void effects() {
					int count = index == 1 ? 1 : index == 2 ? 5 : inventoryNPC.getItemCount(item);
					transferItems(inventoryNPC, Main.game.getPlayer(), item, count);
				}
			};
		}
		if(index == 5)
			return new Response("Enchant", "You can't enchant items owned by someone else!", null);
		if(index == 6) {
			String title = Util.capitaliseSentence(item.getItemType().getUseName()) + " (Self)";
			if(!item.isAbleToBeUsedFromInventory())
				return new Response(title, item.getUnableToBeUsedFromInventoryDescription(), null);
			if(!item.isAbleToBeUsed(Main.game.getPlayer()))
				return new Response(title, item.getUnableToBeUsedDescription(Main.game.getPlayer()), null);
			return new Response(
					title,
					item.getItemType().getUseTooltipDescription(Main.game.getPlayer(), Main.game.getPlayer()),
					INVENTORY_MENU) {
				@Override
				public void effects() {
					Main.game.getTextEndStringBuilder()
							.append("<p style='text-align:center;'>")
							.append(Main.game.getPlayer().useItem(item, Main.game.getPlayer(), false, false, false))
							.append("</p>");
					if(item.isConsumedOnUse())
						inventoryNPC.getInventory().removeItem(item);
					resetPostAction();
				}
			};
		}
		if(index == 7) {
			String title = Util.capitaliseSentence(item.getItemType().getUseName()) + " all (Self)";
			if(!item.isAbleToBeUsedFromInventory())
				return new Response(title, item.getUnableToBeUsedFromInventoryDescription(), null);
			if(!item.isAbleToBeUsed(Main.game.getPlayer()))
				return new Response(title, item.getUnableToBeUsedDescription(Main.game.getPlayer()), null);
			return new Response(
					title,
					item.getItemType().getUseTooltipDescription(Main.game.getPlayer(), Main.game.getPlayer())
							+ "<br/>[style.italicsMinorGood(Repeat this for all of the "
							+ item.getNamePlural()
							+ " which are in [npc.namePos] inventory.)]",
					INVENTORY_MENU) {
				@Override
				public void effects() {
					int itemCount = inventoryNPC.getItemCount(item);
					for(int i = 0; i < itemCount; i++)
						Main.game.getTextEndStringBuilder()
								.append("<p style='text-align:center;'>")
								.append(Main.game.getPlayer().useItem(item, Main.game.getPlayer(), false, false, false))
								.append("</p>");
					if(item.isConsumedOnUse())
						inventoryNPC.getInventory().removeItem(item, itemCount);
					resetPostAction();
				}
			};
		}
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11) {
			String title = Util.capitaliseSentence(item.getItemType().getUseName())
					+ UtilText.parse(inventoryNPC, " ([npc.HerHim])");
			if(!item.isAbleToBeUsedFromInventory())
				return new Response(title, item.getUnableToBeUsedFromInventoryDescription(), null);
			if(!item.isAbleToBeUsed(inventoryNPC))
				return new Response(title, item.getUnableToBeUsedDescription(inventoryNPC), null);
			if(item.isBreakOutOfInventory())
				return new ResponseEffectsOnly(
						title,
						item.getItemType().getUseTooltipDescription(owner, owner)) {
					@Override
					public void effects() {
						Main.game.getPlayer().useItem(item, inventoryNPC, false);
						resetPostAction();
					}
				};
			var fetish = item.getItemType().isFetishGiving() ? Fetish.FETISH_KINK_GIVING
					: item.getItemType().isTransformative() ? Fetish.FETISH_TRANSFORMATION_GIVING : null;
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName())
							+ UtilText.parse(inventoryNPC, " ([npc.HerHim])"),
					item.getItemType().getUseTooltipDescription(Main.game.getPlayer(), inventoryNPC),
					INVENTORY_MENU,
					fetish == null ? null : List.of(fetish),
					fetish == null ? null : fetish.getAssociatedCorruptionLevel(),
					null,
					null,
					null) {
				@Override
				public void effects() {
					var reaction = inventoryNPC.getItemUseEffects(item, owner, Main.game.getPlayer(), inventoryNPC);
					Main.game.getTextEndStringBuilder().append(reaction.getValue());
					resetPostAction();
				}
			};
		}
		if(index == 12) {
			String title = Util.capitaliseSentence(item.getItemType().getUseName())
					+ UtilText.parse(inventoryNPC, " all ([npc.HerHim])");
			if(!item.isAbleToBeUsedFromInventory())
				return new Response(title, item.getUnableToBeUsedFromInventoryDescription(), null);
			if(!item.isAbleToBeUsed(inventoryNPC))
				return new Response(title, item.getUnableToBeUsedDescription(inventoryNPC), null);
			if(item.isBreakOutOfInventory())
				return new Response(title, "As this item has special effects, you can only use one at a time!", null);
			var fetish = item.getItemType().isFetishGiving() ? Fetish.FETISH_KINK_GIVING
					: item.getItemType().isTransformative() ? Fetish.FETISH_TRANSFORMATION_GIVING : null;
			return new Response(
					title,
					item.getItemType().getUseTooltipDescription(Main.game.getPlayer(), inventoryNPC)
							+ "<br/>[style.italicsMinorGood(Repeat this for all of the "
							+ item.getNamePlural()
							+ " which are in [npc.namePos] inventory.)]",
					INVENTORY_MENU,
					fetish == null ? null : List.of(fetish),
					fetish == null ? null : fetish.getAssociatedCorruptionLevel(),
					null,
					null,
					null) {
				@Override
				public void effects() {
					int itemCount = inventoryNPC.getItemCount(item);
					var message = inventoryNPC.getItemUseEffects(item, owner, Main.game.getPlayer(), inventoryNPC);
					for(int i = 0; i < itemCount; i++)
						Main.game.getTextEndStringBuilder().append(message.getValue());
					resetPostAction();
				}
			};
		}
		return null;
	}

	private static Response getItemResponseToNPCDuringSex(int ignoredResponseTab, int index) {
		if(index == 1)
			return new Response("Take (1)", "You can't take someone's items while having sex with them!", null);
		if(index == 2)
			return new Response("Take (5)", "You can't take someone's items while having sex with them!", null);
		if(index == 3)
			return new Response("Take (All)", "You can't take someone's items while having sex with them!", null);
		if(index == 5)
			return new Response(
					"Enchant",
					"You can't enchant someone else's items, especially not while having sex with them!",
					null);
		if(index == 6) {
			String title = Util.capitaliseSentence(item.getItemType().getUseName()) + " (Self)";
			return new Response(title, "You can't use your partner's items during sex!", null);
			//TODO
//			if(!item.isAbleToBeUsedInSex())
//				return new Response(title, "This cannot be used during sex!", null);
//			if(!item.isAbleToBeUsedFromInventory())
//				return new Response(title, item.getUnableToBeUsedFromInventoryDescription(), null);
//			if(!item.isAbleToBeUsed(Main.game.getPlayer()))
//				return new Response(title, item.getUnableToBeUsedDescription(Main.game.getPlayer()), null);
//			return new Response(
//					title,
//					Util.capitaliseSentence(item.getItemType().getUseName()) + " the " + item.getName() + ".",
//					Main.sex.SEX_DIALOGUE) {
//				@Override
//				public void effects() {
//					Main.sex.setUsingItemText(
//							Main.sex.getPartner().getItemUseEffects(item, owner, inventoryNPC, Main.game.getPlayer()));
//					resetPostAction();
//					Main.mainController.openInventory();
//					Main.sex.endSexTurn(SexActionUtility.PLAYER_USE_ITEM);
//					Main.sex.setSexStarted(true);
//				}
//			};
		}
		if(index == 7)
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName()) + " all (Self)",
					"You can only use one item at a time during sex!",
					null);
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11) {
			//TODO
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName()) + " (partner)",
					"You can't use your partner's items during sex!",
					null);
//			if(!item.isAbleToBeUsedInSex())
//				return new Response(
//						Util.capitaliseSentence(item.getItemType().getUseName()) + " (partner)",
//						"This cannot be used during sex!",
//						null);
//			if(!item.isAbleToBeUsedFromInventory())
//				return new Response(
//						Util.capitaliseSentence(item.getItemType().getUseName()) + " (partner)",
//						item.getUnableToBeUsedFromInventoryDescription(),
//						null);
//			if(!item.isAbleToBeUsed(inventoryNPC))
//				return new Response(
//						Util.capitaliseSentence(item.getItemType().getUseName()) + " (partner)",
//						item.getUnableToBeUsedDescription(inventoryNPC),
//						null);
//			var fetish = item.getItemType().isFetishGiving() ? Fetish.FETISH_KINK_GIVING
//					: item.getItemType().isTransformative() ? Fetish.FETISH_TRANSFORMATION_GIVING : null;
//			return new Response(Util.capitaliseSentence(item.getItemType().getUseName()) +" (partner)",
//					String.format("Get %s to %s the %s.",
//							inventoryNPC.getName("the"),
//							item.getItemType().getUseName(),
//							item.getName()),
//					Main.sex.SEX_DIALOGUE,
//					fetish == null ? null : List.of(fetish),
//					fetish == null ? null : fetish.getAssociatedCorruptionLevel(),
//					null,
//					null,
//					null) {
//				@Override
//				public void effects() {
//					Main.sex.setUsingItemText(
//							Main.sex.getPartner().getItemUseEffects(item, owner, inventoryNPC, inventoryNPC));
//					resetPostAction();
//					Main.mainController.openInventory();
//					Main.sex.endSexTurn(SexActionUtility.PLAYER_USE_ITEM);
//					Main.sex.setSexStarted(true);
//				}
//			};
		}
		if(index == 12)
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName()) + " all (partner)",
					"You can only use one item at a time during sex!",
					null);
		return null;
	}

	private static Response getItemResponseToNPC(int ignoredResponseTab, int index) {
		if(index == 1 || index == 2 || index == 3) {
			String title = index == 1 ? "Buy (1) (" : index == 2 ? "Buy (5) (" : "Buy (All) (";
			String name = index == 1 ? item.getName() : item.getNamePlural();
			int sellPrice = buyback
					? Main.game.getPlayer().getBuybackStack().get(buyBackIndex).getPrice()
					: item.getPrice(inventoryNPC.getSellModifier(item));
			int count = buyback
					? Main.game.getPlayer().getBuybackStack().get(buyBackIndex).getCount()
					: inventoryNPC.getItemCount(item);
			if(index == 2 && count < 5)
				return new Response(
						title + UtilText.formatAsMoneyUncoloured(sellPrice*5, "span") + ")",
						UtilText.parse(inventoryNPC, "[npc.Name] doesn't have five " + name + "."),
						null);
			if(Main.game.getPlayer().isInventoryFull()
					&& !Main.game.getPlayer().hasItem(item)
					&& item.getRarity()!=Rarity.QUEST)
				return new Response(
						title + UtilText.formatAsMoneyUncoloured(sellPrice, "span") + ")",
						"Your inventory is already full!",
						null);
			int affordableCount = Integer.min(count, Main.game.getPlayer().getMoney() / sellPrice);
			if(affordableCount == 0)
				return new Response(
						title + UtilText.formatAsMoneyUncoloured(sellPrice, "span") + ")",
						"You can't afford to buy this!",
						null);
			return new Response(
					(affordableCount == count ? title : "Buy (Max " + affordableCount + ") (")
							+ UtilText.formatAsMoney(sellPrice * affordableCount, "span") + ")",
					"Buy the " + name + " for " + UtilText.formatAsMoney(sellPrice * affordableCount) + ".",
					INVENTORY_MENU) {
				@Override
				public void effects() {
					sellItems(inventoryNPC, Main.game.getPlayer(), item, affordableCount, sellPrice);
				}
			};
		}
		if(index == 5)
			return new Response("Enchant", "You can't enchant someone else's item!", null);
		if(index == 6)
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName()) + " (Self)",
					UtilText.parse(inventoryNPC,
							"[npc.Name] isn't going to let you use [npc.her] items without buying them first."),
					null);
		if(index == 7)
			return new Response(
					Util.capitaliseSentence(item.getItemType().getUseName()) + " all (Self)",
					UtilText.parse(inventoryNPC,
							"[npc.Name] isn't going to let you use [npc.her] items without buying them first."),
					null);
		if(index == 9)
			return getBuybackResponse();
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11)
			return new Response(
					Util.capitaliseSentence(
							item.getItemType().getUseName()) + UtilText.parse(inventoryNPC, " ([npc.HerHim])"),
					UtilText.parse(inventoryNPC,
							"[npc.Name] isn't going to use the items that [npc.sheIs] trying to sell!"),
					null);
		if(index == 12)
			return new Response(
					Util.capitaliseSentence(
							item.getItemType().getUseName()) + UtilText.parse(inventoryNPC, " all ([npc.HerHim])"),
					UtilText.parse(inventoryNPC,
							"[npc.Name] isn't going to use the items that [npc.sheIs] trying to sell!"),
					null);
		return null;
	}

	private static void transferItems(GameCharacter from, GameCharacter to, AbstractCoreItem item, int count) {
		if (!to.isInventoryFull() || to.hasItem(item) || item.getRarity()==Rarity.QUEST) {
			from.removeItem(item, count);
			to.addItem(item, count, false, to.isPlayer());
		}
		resetPostAction();
	}
	
	private static void dropItems(GameCharacter from, AbstractCoreItem item, int count) {
		if (!Main.game.getPlayerCell().getInventory().isInventoryFull() || Main.game.getPlayerCell().getInventory().hasItem(item)) {
			from.dropItem(item, count, from.isPlayer());
		}
		resetPostAction();
	}
	
	private static void pickUpItems(GameCharacter to, AbstractCoreItem item, int count) {
		if (!to.isInventoryFull() || to.hasItem(item) || item.getRarity()==Rarity.QUEST) {
			to.addItem(item, count, true, to.isPlayer());
		}
		resetPostAction();
	}
	
	private static void sellItems(GameCharacter from, GameCharacter to, AbstractCoreItem item, int count, int itemPrice) {
		Main.game.getDialogueFlags().setFlag(DialogueFlagValue.removeTraderDescription, false);
		if(!to.isPlayer() || !to.isInventoryFull() || to.hasItem(item) || item.getRarity() == Rarity.QUEST) {
			from.incrementMoney(itemPrice*count);
			to.incrementMoney(-itemPrice*count);
			var itemCopy = buyback && to.isPlayer() || !from.isPlayer()
					? item instanceof AbstractClothing c ? new AbstractClothing(c) {}
							: item instanceof AbstractWeapon w ? Main.game.getItemGen().generateWeapon(w) : item
					: null;
			if(buyback && to.isPlayer()) {
				Main.game.getPlayer().addItem(itemCopy, count, false, true);
				Main.game.getPlayer().getBuybackStack().get(buyBackIndex).incrementCount(-count);
				if(Main.game.getPlayer().getBuybackStack().get(buyBackIndex).getCount()<=0) {	
					Main.game.getPlayer().getBuybackStack().remove(buyBackIndex);
				}
			} else {
				if(from.isPlayer()) {
					Main.game.getPlayer().getBuybackStack().push(new ShopTransaction(item, itemPrice, count));
				} else {
					to.addItem(itemCopy, count, false, true);
				}
				from.removeItem(item, count);
			}
			if(from.isPlayer()) {
				var description = count + "x <span style='color:" + item.getRarity().getColour().toWebHexString()
						+ ";'>" + (count == 1 ? item.getName() : item.getNamePlural()) + "</span> for "
						+ UtilText.formatAsMoney(itemPrice*count);
				Main.game.addEvent(new EventLogEntry("Sold", description), false);
			}
			if(to.isPlayer()) {
				//((NPC) from).handleSellingEffects(item, count, itemPrice); // Delete: This was replaced by applyItemTransactionEffects
				var description = count + "x <span style='color:" + item.getRarity().getColour().toWebHexString()
						+ ";'>" + (count == 1 ? item.getName() : item.getNamePlural()) + "</span> for "
						+ UtilText.formatAsMoney(itemPrice*count);
				Main.game.addEvent(new EventLogEntry("Bought", description), false);
			}
			var receiver = (NPC) (from.isPlayer() ? to : from);
			receiver.applyItemTransactionEffects(item, count, itemPrice, !from.isPlayer());
		}
		resetPostAction();
	}
	
	
	// Weapons:
	
	private static void transferWeapons(GameCharacter from, GameCharacter to, AbstractWeapon weapon, int count) {
		if (!to.isInventoryFull() || to.hasWeapon(weapon) || weapon.getRarity()==Rarity.QUEST) {
			from.removeWeapon(weapon, count);
			to.addWeapon(weapon, count, false, to.isPlayer());
		}
		resetPostAction();
	}
	
	private static void dropWeapons(GameCharacter from, AbstractWeapon weapon, int count) {
		if (!Main.game.getPlayerCell().getInventory().isInventoryFull() || Main.game.getPlayerCell().getInventory().hasWeapon(weapon)) {
			from.dropWeapon(weapon, count, from.isPlayer());
		}
		resetPostAction();
	}
	
	private static void pickUpWeapons(GameCharacter to, AbstractWeapon weapon, int count) {
		if (!to.isInventoryFull() || to.hasWeapon(weapon) || weapon.getRarity()==Rarity.QUEST) {
			to.addWeapon(weapon, count, true, to.isPlayer());
		}
		resetPostAction();
	}
	
	private static void sellWeapons(GameCharacter from, GameCharacter to, AbstractWeapon weapon, int count, int itemPrice) {
		Main.game.getDialogueFlags().setFlag(DialogueFlagValue.removeTraderDescription, false);
		if (!to.isPlayer() || !to.isInventoryFull() || to.hasWeapon(weapon) || weapon.getRarity()==Rarity.QUEST) {

			from.incrementMoney(itemPrice*count);
			to.incrementMoney(-itemPrice*count);
			
			if(buyback && to.isPlayer()) {
				Main.game.getPlayer().addWeapon(Main.game.getItemGen().generateWeapon(weapon), count, false, true);
				Main.game.getPlayer().getBuybackStack().get(buyBackIndex).incrementCount(-count);
				if(Main.game.getPlayer().getBuybackStack().get(buyBackIndex).getCount()<=0) {	
					Main.game.getPlayer().getBuybackStack().remove(buyBackIndex);
				}
				
			} else {
				if(from.isPlayer()) {
					Main.game.getPlayer().getBuybackStack().push(new ShopTransaction(weapon, itemPrice, count));
				} else {
					to.addWeapon(Main.game.getItemGen().generateWeapon(weapon), count, false, true);
				}
				from.removeWeapon(weapon, count);
			}
			
			if(from.isPlayer()) {
				Main.game.addEvent(new EventLogEntry("Sold",
								count+"x <span style='color:"+weapon.getRarity().getColour().toWebHexString()+";'>"+(count==1?weapon.getName():weapon.getNamePlural())+"</span> for "+UtilText.formatAsMoney(itemPrice*count)),
						false);
			}
			
			if(to.isPlayer()) {
				//((NPC) from).handleSellingEffects(weapon, count, itemPrice); // Delete: This was replaced by applyItemTransactionEffects
				Main.game.addEvent(new EventLogEntry("Bought",
								count+"x <span style='color:"+weapon.getRarity().getColour().toWebHexString()+";'>"+(count==1?weapon.getName():weapon.getNamePlural())+"</span> for "+UtilText.formatAsMoney(itemPrice*count)),
						false);
			}
			
			if(!from.isPlayer()) {
				((NPC)from).applyItemTransactionEffects(weapon, count, itemPrice, true);
				
			} else {
				((NPC)to).applyItemTransactionEffects(weapon, count, itemPrice, false);
			}
		}
		
		
		resetPostAction();
	}

	private static Response getWeaponResponseToNPCDuringCombat(int ignoredResponseTab, int index) {
		if(index == 1)
			return new Response("Take (1)", "You can't take someone weapons while fighting them!", null);
		if(index == 2)
			return new Response("Take (5)", "You can't take someone weapons while fighting them!", null);
		if(index == 3)
			return new Response("Take (All)", "You can't take someone weapons while fighting them!", null);
		if(index == 4)
			return new Response("Dye", "You can't dye someone's weapons while fighting them!", null);
		if(index == 5)
			return new Response("Enchant", "You can't enchant someone else's weapons, especially not while fighting them!", null);
		if(index == 6)
			return new Response("Equip Main (Self)", "You can't use someone else's weapons while fighting them!", null);
		if(index == 7)
			return new Response("Equip Offhand (Self)", "You can't use someone else's weapons while fighting them!", null);
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11)
			return new Response("Equip Main (Opponent)", "You can't use make someone use a weapon while fighting them!", null);
		if(index == 12)
			return new Response("Equip Offhand (Opponent)", "You can't use make someone use a weapon while fighting them!", null);
		return null;
	}

	private static Response getWeaponResponseToNPCDuringManagement(int ignoredResponseTab, int index) {
		if(index <= 3 && index >= 1) {
			boolean inventoryFull = Main.game.getPlayer().isInventoryFull()
					&& !Main.game.getPlayer().hasWeapon(weapon)
					&& weapon.getRarity()!=Rarity.QUEST;
			var title = index == 1 ? "Take (1)" : index == 2 ? "Take (5)" : "Take (All)";
			if(inventoryFull)
				return new Response(title, "Your inventory is already full!", null);
			if(index == 2 && inventoryNPC.getWeaponCount(weapon) < 5)
				return new Response(title, UtilText.parse(inventoryNPC, "[npc.Name] doesn't have five " + weapon.getNamePlural() + "!"), null);
			return new Response(
					title,
					UtilText.parse(
							inventoryNPC,
							"Take " + (index == 1 ? "one " : index == 2 ? "five of [npc.namePos] " : "all of [npc.namePos] ")
									+ (index == 1 ? weapon.getName() + " from [npc.name]." : weapon.getNamePlural() + ".")),
					INVENTORY_MENU) {
				@Override
				public void effects() {
					int count = index == 1 ? 1 : index == 2 ? 5 : inventoryNPC.getWeaponCount(weapon);
					transferWeapons(inventoryNPC, Main.game.getPlayer(), weapon, count);
				}
			};
		}
		if(index == 4) {
			if(!Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH)
					&& !Main.game.getPlayer().hasItemType(ItemType.REFORGE_HAMMER)
					&& !Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response("Dye/Reforge", UtilText.parse(inventoryNPC, "You'll need to find a dye-brush or reforge hammer if you want to alter the properties of [npc.namePos] weapons."), null);
			boolean hasFullInventory = inventoryNPC.isInventoryFull() && weapon.getRarity()!=Rarity.QUEST;
			boolean isDyeingStackItem = inventoryNPC.getAllWeaponsInInventory().get(weapon) > 1;
			if(isDyeingStackItem && hasFullInventory)
				return new Response("Dye/Reforge", UtilText.parse(inventoryNPC, "[npc.NamePos] inventory is full, so you can't alter this weapon's properties."), null);
			return new Response("Dye/Reforge",
					Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							?"Use your proficiency with [style.colourEarth(Earth spells)] to dye or reforge this item."
							:"Use a dye-brush or reforge hammer to alter this weapon's properties.",
					DYE_WEAPON) {
				@Override
				public void effects() {
					resetWeaponDyeColours();
				}
			};
		}
		if(index == 5)
			return new Response("Enchant", "You can't enchant weapons owned by someone else!", null);
		if(index == 6) {
			var slot = InventorySlot.mainWeaponSlots[Main.game.getPlayer().getMainWeaponIndexToEquipTo(weapon)];
			if(!weapon.isCanBeEquipped(Main.game.getPlayer(), slot))
				return new Response("Equip Main (Self)", weapon.getCannotBeEquippedText(Main.game.getPlayer(), slot), null);
			return new Response("Equip Main (Self)", "Equip the " + weapon.getName() + " as your main weapon.", INVENTORY_MENU) {
				@Override
				public void effects() {
					Main.game.getTextEndStringBuilder()
					.append("<p style='text-align:center;'>")
							.append(Main.game.getPlayer().equipMainWeaponFromInventory(weapon, inventoryNPC))
					.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index == 7) {
			if(weapon.getWeaponType().isTwoHanded())
				return new Response("Equip Offhand (Self)",
						"As the " + weapon.getName() + (weapon.getWeaponType().isPlural()
								? " require two hands to wield, they can only be equipped in the main slot!"
								: " is a two-handed weapon, it can only be equipped in the main slot!"),
						null);
			var slot = InventorySlot.offhandWeaponSlots[Main.game.getPlayer().getOffhandWeaponIndexToEquipTo(weapon)];
			if(!weapon.isCanBeEquipped(Main.game.getPlayer(), slot))
				return new Response("Equip Offhand (Self)", weapon.getCannotBeEquippedText(Main.game.getPlayer(), slot), null);
			return new Response("Equip Offhand (Self)", "Equip the " + weapon.getName() + " as your offhand weapon.", INVENTORY_MENU){
				@Override
				public void effects(){
					Main.game.getTextEndStringBuilder()
					.append("<p style='text-align:center;'>")
							.append(Main.game.getPlayer().equipOffhandWeaponFromInventory(weapon, inventoryNPC))
					.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11) {
			InventorySlot slot = InventorySlot.mainWeaponSlots[inventoryNPC.getMainWeaponIndexToEquipTo(weapon)];
			if(!weapon.isCanBeEquipped(inventoryNPC, slot))
				return new Response(UtilText.parse(inventoryNPC, "Equip Main ([npc.HerHim])"), weapon.getCannotBeEquippedText(inventoryNPC, slot), null);
			return new Response(UtilText.parse(inventoryNPC, "Equip Main ([npc.HerHim])"), UtilText.parse(inventoryNPC, "Get [npc.name] to equip the " + weapon.getName() + " as [npc.her] main weapon."), INVENTORY_MENU){
				@Override
				public void effects(){
					Main.game.getTextEndStringBuilder()
					.append("<p style='text-align:center;'>")
							.append(inventoryNPC.equipMainWeaponFromInventory(weapon, inventoryNPC))
					.append("</p>");
					resetPostAction();
				}
			};
		}
		if(index == 12) {
			if(weapon.getWeaponType().isTwoHanded())
				return new Response(UtilText.parse(inventoryNPC, "Equip Offhand ([npc.HerHim])"),
						"As the " + weapon.getName() + (weapon.getWeaponType().isPlural()
								? " require two hands to wield, they can only be equipped in the main slot!"
								: " is a two-handed weapon, it can only be equipped in the main slot!"),
						null);
			var slot = InventorySlot.offhandWeaponSlots[Main.game.getPlayer().getOffhandWeaponIndexToEquipTo(weapon)];
			if(!weapon.isCanBeEquipped(inventoryNPC, slot))
				return new Response(UtilText.parse(inventoryNPC, "Equip Offhand ([npc.HerHim])"), weapon.getCannotBeEquippedText(inventoryNPC, slot), null);
			return new Response(UtilText.parse(inventoryNPC, "Equip Offhand ([npc.HerHim])"), UtilText.parse(inventoryNPC, "Get [npc.name] to equip the " + weapon.getName() + " as [npc.her] offhand weapon."), INVENTORY_MENU){
				@Override
				public void effects(){
					Main.game.getTextEndStringBuilder()
					.append("<p style='text-align:center;'>")
							.append(inventoryNPC.equipOffhandWeaponFromInventory(weapon, inventoryNPC))
					.append("</p>");
					resetPostAction();
				}
			};
		}
		return null;
	}

	private static Response getWeaponResponseToNPCDuringSex(int ignoredResponseTab, int index) {
		if(index == 1)
			return new Response("Take (1)", "You can't take someone's weapons while having sex with them!", null);
		if(index == 2)
			return new Response("Take (5)", "You can't take someone's weapons while having sex with them!", null);
		if(index == 3)
			return new Response("Take (All)", "You can't take someone's weapons while having sex with them!", null);
		if(index == 4)
			return new Response("Dye", "You can't dye someone's weapons while having sex with them!", null);
		if(index == 5)
			return new Response("Enchant", "You can't enchant someone else's weapons, especially not while having sex with them!", null);
		if(index == 6)
			return new Response("Equip Main (Self)", "You can't use someone else's weapons while having sex with them!", null);
		if(index == 7)
			return new Response("Equip Offhand (Self)", "You can't use someone else's weapons while having sex with them!", null);
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11)
			return new Response("Equip Main (Opponent)", "You can't use make someone use a weapon while having sex with them!", null);
		if(index == 12)
			return new Response("Equip Offhand (Opponent)", "You can't use make someone use a weapon while having sex with them!", null);
		return null;
	}

	private static Response getWeaponResponseToNPCDuringTrading(int ignoredResponseTab, int index) {
		if(index <= 3 && index >= 1) {
			boolean inventoryFull = Main.game.getPlayer().isInventoryFull()
					&& !Main.game.getPlayer().hasWeapon(weapon)
					&& weapon.getRarity()!=Rarity.QUEST;
			int sellPrice = buyback?Main.game.getPlayer().getBuybackStack().get(buyBackIndex).getPrice():weapon.getPrice(inventoryNPC.getSellModifier(weapon));
			int available = buyback
					? Main.game.getPlayer().getBuybackStack().get(buyBackIndex).getCount()
					: inventoryNPC.getWeaponCount(weapon);
			int count = index == 1 ? 1 : index == 2 ? 5 : available;
			var titlePrefix = index == 1 ? "Buy (1) (" : index == 2 ? "Buy (5) (" : "Buy (All) (";
			if(index == 2 && available < 5)
				return new Response(
						titlePrefix+UtilText.formatAsMoneyUncoloured(sellPrice*5, "span")+")",
						UtilText.parse(inventoryNPC, "[npc.Name] doesn't have five "+weapon.getNamePlural()+"."),
						null);
			if(inventoryFull)
				return new Response(
						titlePrefix+UtilText.formatAsMoneyUncoloured(sellPrice*count, "span")+")",
						"Your inventory is already full!",
						null);
			if(Main.game.getPlayer().getMoney() < sellPrice*count) {
				int affordableCount = Main.game.getPlayer().getMoney() / sellPrice;
				if(affordableCount <= 0)
					return new Response(titlePrefix+UtilText.formatAsMoneyUncoloured(sellPrice*count, "span")+")", "You can't afford to buy this!", null);
				return new Response("Buy (Max " + affordableCount + ") (" + UtilText.formatAsMoney(sellPrice * affordableCount, "span") + ")",
						"Buy the " + weapon.getName() + " for " + UtilText.formatAsMoney(sellPrice * affordableCount) + ".", INVENTORY_MENU) {
					@Override
					public void effects() {
						sellWeapons(inventoryNPC, Main.game.getPlayer(), weapon, affordableCount, sellPrice);
					}
				};
			}
			return new Response(
					titlePrefix + UtilText.formatAsMoney(sellPrice*count, "span") + ")",
					"Buy the " + (count == 1 ? weapon.getName() : weapon.getNamePlural()) + " for " + UtilText.formatAsMoney(sellPrice*count) + ".",
					INVENTORY_MENU){
				@Override
				public void effects(){
					sellWeapons(inventoryNPC, Main.game.getPlayer(), weapon, count, sellPrice);
				}
			};
		}
		if(index == 4)
			return new Response("Dye", UtilText.parse(inventoryNPC, "[npc.Name] isn't going to let you dye the weapon that [npc.sheIs] trying to sell!"), null);
		if(index == 5)
			return new Response("Enchant", "You can't enchant someone else's weapon!", null);
		if(index == 6)
			return new Response("Equip Main (Self)", UtilText.parse(inventoryNPC, "[npc.Name] isn't going to let you equip [npc.her] weapons without buying them first."), null);
		if(index == 7)
			return new Response("Equip Offhand (Self)", UtilText.parse(inventoryNPC, "[npc.Name] isn't going to let you equip [npc.her] weapons without buying them first."), null);
		if(index == 9)
			return getBuybackResponse();
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11)
			return new Response(UtilText.parse(inventoryNPC, "Equip Main ([npc.HerHim])"), UtilText.parse(inventoryNPC, "[npc.Name] isn't going to equip the weapons that [npc.sheIs] trying to sell!"), null);
		if(index == 12)
			return new Response(UtilText.parse(inventoryNPC, "Equip Offhand ([npc.HerHim])"), UtilText.parse(inventoryNPC, "[npc.Name] isn't going to equip the weapons that [npc.sheIs] trying to sell!"), null);
		return null;
	}

	// Clothing:
	
	private static void transferClothing(GameCharacter from, GameCharacter to, AbstractClothing clothing, int count) {
		if (!to.isInventoryFull() || to.hasClothing(clothing) || clothing.getRarity()==Rarity.QUEST) {
			from.removeClothing(clothing, count);
			to.addClothing(clothing, count, false, to.isPlayer());
			owner = to;
		}
		resetPostAction();
	}
	
	
	private static void dropClothing(GameCharacter from, AbstractClothing clothing, int count) {
		if (!Main.game.getPlayerCell().getInventory().isInventoryFull() || Main.game.getPlayerCell().getInventory().hasClothing(clothing)) {
			from.dropClothing(clothing, count, from.isPlayer());
			
			if(from.getClothingCount(clothing) == 0) {
				owner = null;
			}
		}
		resetPostAction();
	}
	
	private static void pickUpClothing(GameCharacter to, AbstractClothing clothing, int count) {
		if (!to.isInventoryFull() || to.hasClothing(clothing) || clothing.getRarity()==Rarity.QUEST) {
			to.addClothing(clothing, count, true, to.isPlayer());
			
			owner = to;
		}
		resetPostAction();
	}
	
	private static void sellClothing(GameCharacter from, GameCharacter to, AbstractClothing clothing, int count, int itemPrice) {
		Main.game.getDialogueFlags().setFlag(DialogueFlagValue.removeTraderDescription, false);
		if (!to.isPlayer() || !to.isInventoryFull() || to.hasClothing(clothing) || clothing.getRarity()==Rarity.QUEST) {
			from.incrementMoney(itemPrice*count);
			to.incrementMoney(-itemPrice*count);
			
			if(buyback && to.isPlayer()) {
				Main.game.getPlayer().addClothing(new AbstractClothing(clothing) {}, count, false, true);
				Main.game.getPlayer().getBuybackStack().get(buyBackIndex).incrementCount(-count);
				if(Main.game.getPlayer().getBuybackStack().get(buyBackIndex).getCount()<=0) {	
					Main.game.getPlayer().getBuybackStack().remove(buyBackIndex);
				}
				
			} else {
				if(from.isPlayer()) {
					Main.game.getPlayer().getBuybackStack().push(new ShopTransaction(clothing, itemPrice, count));
				} else {
					to.addClothing(new AbstractClothing(clothing) {}, count, false, true);
				}
				from.removeClothing(clothing, count);
			}
			
			if(from.isPlayer()) {
				Main.game.addEvent(new EventLogEntry("Sold",
								count+"x <span style='color:"+clothing.getRarity().getColour().toWebHexString()+";'>"+(count==1?clothing.getName():clothing.getNamePlural())+"</span> for "+UtilText.formatAsMoney(itemPrice*count)),
						false);
			}
			
			if(to.isPlayer()) {
				//((NPC) from).handleSellingEffects(clothing, count, itemPrice); // Delete: This was replaced by applyItemTransactionEffects
				Main.game.addEvent(new EventLogEntry("Bought",
								count+"x <span style='color:"+clothing.getRarity().getColour().toWebHexString()+";'>"+(count==1?clothing.getName():clothing.getNamePlural())+"</span> for "+UtilText.formatAsMoney(itemPrice*count)),
						false);
			}
			
			if(!from.isPlayer()) {
				((NPC)from).applyItemTransactionEffects(clothing, count, itemPrice, true);
				
			} else {
				((NPC)to).applyItemTransactionEffects(clothing, count, itemPrice, false);
			}
		}
		
		resetPostAction();
	}
	
	private static String unequipClothingToFloor(GameCharacter unequipper, AbstractClothing clothing) {
		String unequipDescription = "";
		if(clothing.isDiscardedOnUnequip(clothing.getSlotEquippedTo())) {
			unequipDescription = owner.unequipClothingIntoVoid(clothing, true, unequipper);
		} else {
			unequipDescription = owner.unequipClothingOntoFloor(clothing, true, unequipper);
		}
		owner = null;
		resetPostAction();
		
		return unequipDescription;
	}
	
	private static String unequipClothingToUnequippersInventory(GameCharacter unequipper, AbstractClothing clothing) {
		String unequipDescription = "";
		if(clothing.isDiscardedOnUnequip(clothing.getSlotEquippedTo())) {
			unequipDescription = owner.unequipClothingIntoVoid(clothing, true, unequipper);
		} else {
			unequipDescription = owner.unequipClothingIntoUnequippersInventory(clothing, true, unequipper);
		}
		resetPostAction();
		
		return unequipDescription;
	}
	
	private static String unequipClothingToInventory(GameCharacter unequipper, AbstractClothing clothing) {
		String unequipDescription = "";
		if(clothing.isDiscardedOnUnequip(clothing.getSlotEquippedTo())) {
			unequipDescription = owner.unequipClothingIntoVoid(clothing, true, unequipper);
		} else {
			unequipDescription = owner.unequipClothingIntoInventory(clothing, true, unequipper);
		}
		resetPostAction();
		
		return unequipDescription;
	}
	
	private static String equipClothingFromInventory(GameCharacter to, InventorySlot slot, GameCharacter equipper, AbstractClothing clothing) {
		String equipDescription = to.equipClothingFromInventory(clothing, slot, true, equipper, owner);
		owner = to;
		resetPostAction();
		return equipDescription;
	}
	
	private static String equipClothingFromGround(GameCharacter to, InventorySlot slot, GameCharacter equipper, AbstractClothing clothing) {
		owner = to;
		resetPostAction();
		return to.equipClothingFromGround(clothing, slot, true, equipper);
	}
	
	private static Response getCondomSabotageResponse(AbstractClothing clothing) {
		if(clothing.getCondomEffect().getPotency().isNegative()) {
			if(Main.game.getPlayer().getEssenceCount() >= 1) {
				return new Response("Repair ([style.italicsArcane(1 Essence)])",
						"Spend 1 arcane essence to repair the condom.", CLOTHING_INVENTORY) {
					@Override
					public void effects() {
						Main.game.getPlayer().incrementEssenceCount(-1, false);
						Main.game.getTextEndStringBuilder().append(
								"<p>"
									+ "You channel the power of an arcane essence into the condom, and, after emitting a faint purple glow, it is repaired!"
								+ "</p>"
								+ "<p style='text-align:center;'>"
									+ "Repairing the condom has cost you [style.boldBad(1)] [style.boldArcane(Arcane Essence)]!"
								+ "</p>");
						AbstractClothing c = (AbstractClothing) EnchantmentDialogue.craftAndApplyFullInventoryEffects(clothing, clothing.getClothingType().getEffects());

						Main.game.getPlayer().removeClothing(c);
						c.setName(c.getClothingType().getName());
						setClothing(c);
						Main.game.getPlayer().addClothing(c, false);
						
						RenderingEngine.setPage(Main.game.getPlayer(), c);
					}
				};
			} else {
				return new Response("Repair (<i>1 Essence</i>)", "You need at least 1 arcane essence in order to repair the condom!", null);
			}
			
		} else {
			return new Response("Sabotage", "By making a small tear in the end of this condom, you can ensure that it will break at the moment of orgasm!", CLOTHING_INVENTORY) {
				@Override
				public void effects(){
					EnchantmentDialogue.setOutputName(clothing.getClothingType().getName());
					AbstractClothing c = (AbstractClothing) EnchantmentDialogue.craftAndApplyFullInventoryEffects(clothing,
							Util.newArrayListOfValues(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_CONDOM, TFModifier.ARCANE_BOOST, TFPotency.MAJOR_DRAIN, 0)),
							false);

					Main.game.getPlayer().removeClothing(c);
					setClothing(c);
					Main.game.getPlayer().addClothing(c, false);

					RenderingEngine.setPage(Main.game.getPlayer(), c);
					Main.game.getTextEndStringBuilder().append(
							"<p>"
								+ "By making a tiny, near-invisible tear in the end of the condom, you ensure that it will split when filled with cum..."
							+ "</p>"
							+ "<p style='text-align:center;'>"
								+ "[style.italicsBad(The condom is now guaranteed to break upon orgasm)]!"
							+ "</p>");
				}
			};
		}
	}
	
	private static void resetPostAction() {
		Main.game.setResponseTab(0);
		resetItems();
	}
	
	public static void resetItems() {
		item = null;
		clothing = null;
		weapon = null;
	}

	public static AbstractItem getItem() {
		return item;
	}

	public static void setItem(AbstractItem item) {
		resetItems();
		InventoryDialogue.item = item;
		if(Main.getProperties().addItemDiscovered(item.getItemType())) {
			Main.game.addEvent(new EventLogEntryEncyclopediaUnlock(item.getItemType().getName(false), item.getRarity().getColour()), true);
		}
	}

	public static AbstractWeapon getWeapon() {
		return weapon;
	}

	public static void setWeapon(InventorySlot slot, AbstractWeapon weapon) {
		resetItems();
		InventoryDialogue.weaponSlot = slot;
		InventoryDialogue.weapon = weapon;
		if (Main.getProperties().addWeaponDiscovered(weapon.getWeaponType())) {
			Main.game.addEvent(new EventLogEntryEncyclopediaUnlock(weapon.getWeaponType().getName(), weapon.getWeaponType().getRarity().getColour()), true);
		}
	}

	public static AbstractClothing getClothing() {
		return clothing;
	}

	public static void setClothing(AbstractClothing clothing) {
		resetItems();
		InventoryDialogue.clothing = clothing;
		if(Main.getProperties().addClothingDiscovered(clothing.getClothingType())) {
			Main.game.addEvent(new EventLogEntryEncyclopediaUnlock(clothing.getClothingType().getName(), clothing.getClothingType().getRarity().getColour()), true);
		}
	}

	public static boolean isBuyback() {
		return buyback;
	}

	public static void setBuyback(boolean buyback) {
		InventoryDialogue.buyback = buyback;
	}

	public static int getBuyBackPrice() {
		return buyBackPrice;
	}

	public static void setBuyBackPrice(int buyBackPrice) {
		InventoryDialogue.buyBackPrice = buyBackPrice;
	}

	public static int getBuyBackIndex() {
		return buyBackIndex;
	}

	public static void setBuyBackIndex(int buyBackIndex) {
		InventoryDialogue.buyBackIndex = buyBackIndex;
	}

	public static GameCharacter getOwner() {
		return owner;
	}

	public static void setOwner(GameCharacter owner) {
		InventoryDialogue.owner = owner;
	}

	public static NPC getInventoryNPC() {
		return inventoryNPC;
	}

	public static void setInventoryNPC(NPC inventoryNPC) {
		InventoryDialogue.inventoryNPC = inventoryNPC;
	}

	public static InventoryInteraction getNPCInventoryInteraction() {
		return interactionType;
	}

	public static void setNPCInventoryInteraction(InventoryInteraction npcInventoryInteraction) {
		interactionType = npcInventoryInteraction;
	}


	// Clothing Inventory

	private static Response getClothingResponseDuringCharacterCreation(int ignoredResponseTab, int index) {
		if(index == 1) {
			if(Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.NIPPLES)
					|| Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.ANUS)
					|| Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.PENIS)
					|| Main.game.getPlayer().isCoverableAreaVisible(CoverableArea.VAGINA)
					|| (Main.game.getPlayer().getClothingInSlot(InventorySlot.FOOT) == null
							&& Main.game.getPlayer().getLegType().equals(LegType.HUMAN)))
				return new Response("To the stage", "You need to be wearing clothing that covers your body, as well as a pair of shoes.", null);
			return new Response("To the stage", "You're ready to approach the stage now.", CharacterCreation.CHOOSE_BACKGROUND) {
				@Override
				public void effects() {
					CharacterCreation.moveNPCIntoPlayerTile();
				}
			};
		}
		if(index == 4) {
			if(Main.game.getPlayer().getClothingCurrentlyEquipped().isEmpty())
				return new Response("Unequip all", "You're currently naked, there's nothing to be unequipped.", null);
			return new Response("Unequip all", "Remove as much of your clothing as possible.", INVENTORY_MENU) {
				@Override
				public void effects() {
					Main.game.getTextEndStringBuilder().append(unequipAll(Main.game.getPlayer()));
				}
			};
		}
		if(index == 5)
			return new Response("Change colour", "Change the colour of this item of clothing.", DYE_CLOTHING_CHARACTER_CREATION) {
				@Override
				public void effects() {
					resetClothingDyeColours();
				}
			};
		if(index >= 6 && index <= 9 && index-6<clothing.getClothingType().getEquipSlots().size()) {
			var slot = clothing.getClothingType().getEquipSlots().get(index-6);
			var title = "Equip: "+Util.capitaliseSentence(slot.getName());
			if(!clothing.isCanBeEquipped(Main.game.getPlayer(), slot))
				return new Response(title, clothing.getCannotBeEquippedText(Main.game.getPlayer(), slot), null);
			return new Response(title, "Equip the " + clothing.getName() + ".", INVENTORY_MENU) {
				@Override
				public void effects() {
					equipClothingFromGround(Main.game.getPlayer(), slot, Main.game.getPlayer(), clothing);
				}
			};
		}
		return null;
	}

	private static Response getClothingResponseToNPCDuringCombat(int ignoredResponseTab, int index) {
		if(index == 1 || index == 2 || index == 3)
			return new Response(index == 1 ? "Take (1)" : index == 2 ? "Take (5)" : "Take (All)", "You can't take someone clothing while fighting them!", null);
		if(index == 4)
			return new Response("Dye", "You can't dye someone's clothing while fighting them!", null);
		if(index == 5) {
			if(clothing.isCondom()) {
				boolean broken = clothing.getCondomEffect().getPotency().isNegative();
				return new Response(
						broken ? "Repair (<i>1 Essence</i>)" : "Sabotage",
						"You can't " + (broken ? "repair" : "sabotage") + " someone else's condom, especially not while fighting them!",
						null);
			}
			return new Response("Enchant", "You can't enchant someone else's clothing, especially not while fighting them!", null);
		}
		if(index == 6)
			return new Response("Equip (Self)", "You can't use someone else's clothing while fighting them!", null);
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11)
			return new Response(UtilText.parse(inventoryNPC, "Equip: ([npc.HerHim])"), "You can't make someone wear clothing while fighting them!", null);
		return null;
	}

	private static Response getClothingResponseToNPCDuringManagement(int ignoredResponseTab, int index) {
		if(index == 1 || index == 2 || index == 3) {
			boolean inventoryFull = Main.game.getPlayer().isInventoryFull() && !Main.game.getPlayer().hasClothing(clothing) && clothing.getRarity()!=Rarity.QUEST;
			var title = index == 1 ? "Take (1)" : index == 2 ? "Take (5)" : "Take (All)";
			if(inventoryFull)
				return new Response(title, "Your inventory is already full!", null);
			if(index == 2 && inventoryNPC.getClothingCount(clothing) < 5)
				return new Response(title, UtilText.parse(inventoryNPC, "[npc.Name] doesn't have five " + clothing.getNamePlural() + "!"), null);
			return new Response(title, UtilText.parse(inventoryNPC, "Take " + (index == 1 ? "the " : index == 2 ? "five of [npc.namePos] " : "all of [npc.namePos] ") + clothing.getName() + (index != 1 ? "." : " from [npc.name].")), INVENTORY_MENU) {
				@Override
				public void effects() {
					var count = index == 1 ? 1 : index == 2 ? 5 : inventoryNPC.getClothingCount(clothing);
					transferClothing(inventoryNPC, Main.game.getPlayer(), clothing, count);
				}
			};
		}
		if(index == 4) {
			if(!Main.game.getPlayer().hasItemType(ItemType.DYE_BRUSH)
					&& !Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH))
				return new Response("Dye", UtilText.parse(inventoryNPC, "You'll need to find another dye-brush if you want to dye [npc.namePos] clothes."), null);
			boolean hasFullInventory = inventoryNPC.isInventoryFull();
			boolean isDyeingStackItem = clothing!=null && inventoryNPC.getAllClothingInInventory().get(clothing) > 1;
			if(isDyeingStackItem && hasFullInventory)
				return new Response("Dye", UtilText.parse(inventoryNPC, "[npc.NamePos] inventory is full, so you can't dye this item of clothing."), null);
			return new Response("Dye",
					Main.game.getPlayer().isSpellSchoolSpecialAbilityUnlocked(SpellSchool.EARTH)
							? "Use your proficiency with [style.colourEarth(Earth spells)] to dye this item."
							: "Use a dye-brush to dye this item of clothing.",
					DYE_CLOTHING) {
				@Override
				public void effects() {
					resetClothingDyeColours();
				}
			};
		}
		if(index == 5) {
			if(clothing.isCondom()) {
				boolean broken = clothing.getCondomEffect().getPotency().isNegative();
				return new Response(
						broken ? "Repair (<i>1 Essence</i>)" : "Sabotage",
						"You can't " + (broken ? "repair" : "sabotage") + " condoms owned by someone else!",
						null);
			}
			return new Response("Enchant", "You can't enchant clothing owned by someone else!", null);
		}
		if(index >= 6 && index <= 9 && index - 6 < clothing.getClothingType().getEquipSlots().size()) {
			var slot = clothing.getClothingType().getEquipSlots().get(index-6);
			var title = "Equip: "+Util.capitaliseSentence(slot.getName());
			if(!clothing.isCanBeEquipped(Main.game.getPlayer(), slot))
				return new Response(title, clothing.getCannotBeEquippedText(Main.game.getPlayer(), slot), null);
			return new Response(title, "Equip the " + clothing.getName() + ".", INVENTORY_MENU) {
				@Override
				public void effects() {
					Main.game.getTextEndStringBuilder()
					.append("<p style='text-align:center;'>")
							.append(equipClothingFromInventory(Main.game.getPlayer(), slot, Main.game.getPlayer(), clothing))
					.append("</p>");
				}
			};
		}
		if(index == 10)
			return getQuickTradeResponse();
		if(index >= 11 && index <= 14 && index - 11 < clothing.getClothingType().getEquipSlots().size()) {
			var slot = clothing.getClothingType().getEquipSlots().get(index-11);
			var equipAllowed = inventoryNPC.isInventoryEquipAllowed(clothing, slot);
			if(!equipAllowed.getKey())
				return new Response(
						UtilText.parse(inventoryNPC, "Equip: "+Util.capitaliseSentence(slot.getName())+" ([npc.HerHim])"),
						UtilText.parse(inventoryNPC, equipAllowed.getValue()),
						null);
			if(!clothing.isCanBeEquipped(inventoryNPC, slot))
				return new Response(
						UtilText.parse(inventoryNPC, "Equip: "+Util.capitaliseSentence(slot.getName())+" ([npc.HerHim])"),
						clothing.getCannotBeEquippedText(inventoryNPC, slot),
						null);
			if(!inventoryNPC.isAbleToEquip(clothing, slot, true, Main.game.getPlayer())
					|| !clothing.isEnslavementClothing()
					|| inventoryNPC.isSlave() && inventoryNPC.getOwner().isPlayer())
				return new Response(
						UtilText.parse(inventoryNPC, "Equip: "+Util.capitaliseSentence(slot.getName())+" ([npc.HerHim])"),
						UtilText.parse(inventoryNPC, "Get [npc.name] to equip the " + clothing.getName() + "."),
						INVENTORY_MENU){
					@Override
					public void effects(){
						Main.game.getTextEndStringBuilder()
						.append("<p style='text-align:center;'>")
								.append(equipClothingFromInventory(inventoryNPC, slot, Main.game.getPlayer(), clothing))
						.append("</p>");
					}
				};
			boolean willEnslave = !inventoryNPC.isSlave() && inventoryNPC.isAbleToBeEnslaved() && Main.game.getPlayer().isHasSlaverLicense();
			return new Response(
					UtilText.parse(inventoryNPC,
							!willEnslave
									?"[style.colourMinorBad(Equip: "+Util.capitaliseSentence(slot.getName())+" ([npc.HerHim]))]"
									:"[style.colourArcane(Equip: "+Util.capitaliseSentence(slot.getName())+" ([npc.HerHim]))]"),
					UtilText.parse(inventoryNPC,
							"Make [npc.name] equip the "+clothing.getName()+"."
							+(!willEnslave
									?"<br/><i>Despite the fact that the "+clothing.getName()+" "
											+(clothing.getClothingType().isPlural()?"have an enslavement enchantment, they":"has an enslavement enchantment, it")
											+" [style.colourMinorBad(will not enslave [npc.name])], as"
											+ (Main.game.getPlayer().isHasSlaverLicense()
											?" [npc.sheIsFull] not a suitable target for enslavement"
											:" you do not possess a slaver license")
											+"!</i>"
									:"<br/><i>Thanks to the fact that the "+clothing.getName()+" "
											+(clothing.getClothingType().isPlural()?"have an enslavement enchantment, they":"has an enslavement enchantment, it")
											+" [style.colourArcane(will enslave [npc.name])], making you [npc.her] new owner!</i>")),
					INVENTORY_MENU) {
				@Override
				public DialogueNode getNextDialogue() {
					var enslavementDialogue = inventoryNPC.getEnslavementDialogue(clothing);
					return Objects.requireNonNullElse(enslavementDialogue, INVENTORY_MENU);
				}
				@Override
				public void effects() {
					var enslavementTargets = new ArrayList<>(Main.game.getNonCompanionCharactersPresent());
//					enslavementTargets.removeIf((npc) -> Main.game.getPlayer().getFriendlyOccupants().contains(npc.getId()));
					enslavementTargets.removeIf((npc) -> !Main.combat.getEnemies(Main.game.getPlayer()).contains(npc));
					SlaveDialogue.setFollowupEnslavementDialogue(enslavementTargets.size() <= 1 ? Main.game.getDefaultDialogue(false) : Main.game.getSavedDialogueNode());
					var text = equipClothingFromInventory(inventoryNPC, slot, Main.game.getPlayer(), clothing);
					if(inventoryNPC.getEnslavementDialogue(clothing) == null)
						Main.game.getTextEndStringBuilder()
						.append("<p style='text-align:center;'>")
								.append(text)
						.append("</p>");
				}
			};
		}
		return null;
	}

	private static Response getClothingResponseToNPCDuringSex(int ignoredResponseTab, int index) {
		if(index == 1 || index == 2 || index == 3)
			return new Response(
					index == 1 ? "Take (1)" : index == 2 ? "Take (5)" : "Take (All)",
					"You can't take someone's clothing while having sex with them!",
					null);
		if(index == 4)
			return new Response("Dye", "You can't dye someone's clothing while having sex with them!", null);
		if(index == 5) {
			if(clothing.isCondom()) {
				boolean broken = clothing.getCondomEffect().getPotency().isNegative();
				return new Response(
						broken ? "Repair (<i>1 Essence</i>)" : "Sabotage",
						"You can't " + (broken ? "repair" : "sabotage") + " someone else's condom, especially not while having sex with them!",
						null);
			}
			return new Response("Enchant", "You can't enchant someone else's clothing, especially not while having sex with them!", null);
		}
		if(index >= 6 && index <= 9 && index - 6 < clothing.getClothingType().getEquipSlots().size()) { //TODO ???
			var slot = clothing.getClothingType().getEquipSlots().get(index-6);
			var title = "Equip: " + Util.capitaliseSentence(slot.getName());
			if(!clothing.isCanBeEquipped(Main.game.getPlayer(), slot))
				return new Response(title, clothing.getCannotBeEquippedText(Main.game.getPlayer(), slot), null);
			if(!clothing.isAbleToBeEquippedDuringSex(slot).getKey() && !inventoryNPC.isTrader())
				return new Response(title, clothing.isAbleToBeEquippedDuringSex(slot).getValue(), null);
			if(!Main.sex.getInitialSexManager().isAbleToEquipSexClothing(Main.game.getPlayer(), Main.game.getPlayer(), clothing))
				return new Response(title, "As this is a special sex scene, you cannot equip clothing during it!", null);
			if(!Main.sex.isClothingEquipAvailable(Main.game.getPlayer(), slot, clothing))
				return new Response(title, "This slot is involved with an ongoing sex action, so you cannot equip clothing into it!", null);
			if (!Main.game.getPlayer().isAbleToEquip(clothing, slot, false, Main.game.getPlayer()))
				return new Response(title, getClothingBlockingRemovalText(Main.game.getPlayer(), "equip"), null);
			return new Response(title, "Equip the " + clothing.getName() + ".", Main.sex.SEX_DIALOGUE){
				@Override
				public void effects(){
					AbstractClothing c = clothing;
					equipClothingFromInventory(Main.game.getPlayer(), slot, Main.game.getPlayer(), clothing);
					Main.sex.setEquipClothingText(c, Main.game.getPlayer().getUnequipDescription());
					Main.mainController.openInventory();
					Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
					Main.sex.setSexStarted(true);
				}
			};
		}
		if(index == 10)
			return getQuickTradeResponse();
		if(index >= 11 && index <= 14 && index - 11 < clothing.getClothingType().getEquipSlots().size()) {
			var slot = clothing.getClothingType().getEquipSlots().get(index-11);
			var equipAllowed = inventoryNPC.isInventoryEquipAllowed(clothing, slot);
			var title = UtilText.parse(inventoryNPC, "Equip: "+Util.capitaliseSentence(slot.getName())+" ([npc.HerHim])");
			if(!equipAllowed.getKey())
				return new Response(title, UtilText.parse(inventoryNPC, equipAllowed.getValue()), null);
			if(!clothing.isCanBeEquipped(inventoryNPC, slot))
				return new Response(title, clothing.getCannotBeEquippedText(inventoryNPC, slot), null);
			if(!clothing.isAbleToBeEquippedDuringSex(slot).getKey() && !inventoryNPC.isTrader())
				return new Response(title, clothing.isAbleToBeEquippedDuringSex(slot).getValue(), null);
			if(!Main.sex.getInitialSexManager().isAbleToEquipSexClothing(Main.game.getPlayer(), inventoryNPC, clothing))
				return new Response(title, "As this is a special sex scene, you cannot equip clothing during it!", null);
			if(!Main.sex.isClothingEquipAvailable(inventoryNPC, slot, clothing))
				return new Response(title, "This slot is involved with an ongoing sex action, so you cannot equip clothing into it!", null);
			if (!inventoryNPC.isAbleToEquip(clothing, slot, false, Main.game.getPlayer()))
				return new Response(
						title,
						UtilText.parse(inventoryNPC, "[npc.Name] can't equip the " + clothing.getName()
								+ ", as other clothing is blocking [npc.herHim] from doing so!"),
						null);
			return new Response(
					title,
					UtilText.parse(inventoryNPC, "Get [npc.name] to equip the " + clothing.getName() + "."),
					Main.sex.SEX_DIALOGUE) {
				@Override
				public void effects() {
					var c = clothing;
					equipClothingFromInventory(inventoryNPC, slot, Main.game.getPlayer(), clothing);
					Main.sex.setEquipClothingText(c, inventoryNPC.getUnequipDescription());
					Main.mainController.openInventory();
					Main.sex.endSexTurn(SexActionUtility.CLOTHING_REMOVAL);
					Main.sex.setSexStarted(true);
				}
			};
		}
		return null;
	}

	private static Response getClothingResponseToNPCDuringTrade(int ignoredResponseTab, int index) {
		if(index == 1 || index == 2 || index == 3) {
			boolean inventoryFull = Main.game.getPlayer().isInventoryFull()
					&& !Main.game.getPlayer().hasClothing(clothing)
					&& clothing.getRarity() != Rarity.QUEST;
			int sellPrice = buyback
					? Main.game.getPlayer().getBuybackStack().get(buyBackIndex).getPrice()
					: clothing.getPrice(inventoryNPC.getSellModifier(clothing));
			int availableCount = buyback
					? Main.game.getPlayer().getBuybackStack().get(buyBackIndex).getCount()
					: inventoryNPC.getClothingCount(clothing);
			int count = index == 1 ? 1 : index == 2 ? 5 : availableCount;
			var title = index == 1 ? "1" : index == 2 ? "5" : "All";
			if(inventoryFull)
				return new Response(
						"Buy (" + title + ") ("+UtilText.formatAsMoneyUncoloured(sellPrice*count, "span")+")",
						"Your inventory is already full!",
						null);
			if(index == 2 && availableCount < count)
				return new Response("Buy (5) ("+UtilText.formatAsMoneyUncoloured(sellPrice*5, "span")+")", UtilText.parse(inventoryNPC, "[npc.Name] doesn't have five "+clothing.getNamePlural()+"."), null);
			int money = Main.game.getPlayer().getMoney();
			if(money < sellPrice*count) {
				int affordableCount = money / sellPrice;
				if(index != 3 || affordableCount > 0)
					return new Response(
							"Buy (Max " + affordableCount + ") (" + UtilText.formatAsMoney(sellPrice * affordableCount, "span") + ")",
							"Buy the " + clothing.getName() + " for " + UtilText.formatAsMoney(sellPrice * affordableCount) + ".",
							INVENTORY_MENU) {
						@Override
						public void effects() {
							sellClothing(inventoryNPC, Main.game.getPlayer(), clothing, affordableCount, sellPrice);
						}
					};
				return new Response("Buy (" + title + ") ("+UtilText.formatAsMoneyUncoloured(sellPrice*count, "span")+")", "You can't afford to buy this!", null);
			}
			return new Response(
					"Buy (" + title + ") (" + UtilText.formatAsMoney(sellPrice*count, "span") + ")",
					"Buy the " + clothing.getName() + " for " + UtilText.formatAsMoney(sellPrice*count) + ".",
					INVENTORY_MENU) {
				@Override
				public void effects() {
					sellClothing(inventoryNPC, Main.game.getPlayer(), clothing, count, sellPrice);
				}
			};
		}
		if(index == 4)
			return new Response(
					"Dye",
					UtilText.parse(inventoryNPC, "[npc.Name] isn't going to let you dye the clothing that [npc.sheIs] trying to sell!"),
					null);
		if(index == 5) {
			if(clothing.isCondom()) {
				boolean broken = clothing.getCondomEffect().getPotency().isNegative();
				return new Response(
						broken ? "Repair (<i>1 Essence</i>)" : "Sabotage",
						"You can't " + (broken ? "repair" : "sabotage") + " someone else's condom!",
						null);
			}
			return new Response("Enchant", "You can't enchant someone else's clothing!", null);
		}
		if(index == 6)
			return new Response(
					"Equip (Self)",
					UtilText.parse(inventoryNPC, "[npc.Name] isn't going to let you use [npc.her] clothing without buying them first."),
					null);
		if(index == 9)
			return getBuybackResponse();
		if(index == 10)
			return getQuickTradeResponse();
		if(index == 11)
			return new Response(
					UtilText.parse(inventoryNPC, "Equip ([npc.HerHim])"),
					UtilText.parse(inventoryNPC, "[npc.Name] isn't going to use the clothing that [npc.sheIs] trying to sell!"),
					null);
		return null;
	}
}