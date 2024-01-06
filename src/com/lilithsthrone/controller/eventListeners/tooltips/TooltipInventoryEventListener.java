package com.lilithsthrone.controller.eventListeners.tooltips;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lilithsthrone.utils.Markup;
import com.lilithsthrone.utils.UIMarkup;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import com.lilithsthrone.controller.TooltipUpdateThread;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.AbstractAttribute;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.body.CoverableArea;
import com.lilithsthrone.game.character.body.coverings.Covering;
import com.lilithsthrone.game.character.body.types.HornType;
import com.lilithsthrone.game.character.body.types.PenisType;
import com.lilithsthrone.game.character.body.types.TailType;
import com.lilithsthrone.game.character.body.types.VaginaType;
import com.lilithsthrone.game.character.body.types.WingType;
import com.lilithsthrone.game.character.body.valueEnums.Femininity;
import com.lilithsthrone.game.character.markings.AbstractTattooType;
import com.lilithsthrone.game.character.markings.Scar;
import com.lilithsthrone.game.character.markings.Tattoo;
import com.lilithsthrone.game.character.markings.TattooCountType;
import com.lilithsthrone.game.character.markings.TattooCounter;
import com.lilithsthrone.game.character.markings.TattooCounterType;
import com.lilithsthrone.game.character.markings.TattooWriting;
import com.lilithsthrone.game.character.markings.TattooWritingStyle;
import com.lilithsthrone.game.combat.Attack;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.combat.moves.AbstractCombatMove;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.dialogue.utils.EnchantmentDialogue;
import com.lilithsthrone.game.dialogue.utils.InventoryDialogue;
import com.lilithsthrone.game.dialogue.utils.InventoryInteraction;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.AbstractCoreItem;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.ShopTransaction;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.inventory.clothing.AbstractClothingType;
import com.lilithsthrone.game.inventory.clothing.BodyPartClothingBlock;
import com.lilithsthrone.game.inventory.enchanting.EnchantingUtils;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.game.inventory.enchanting.TFPotency;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.inventory.item.AbstractItemType;
import com.lilithsthrone.game.inventory.weapon.AbstractWeapon;
import com.lilithsthrone.game.inventory.weapon.AbstractWeaponType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.rendering.Pattern;
import com.lilithsthrone.rendering.RenderingEngine;
import com.lilithsthrone.utils.SizedStack;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * Shows the tooltip at the given element's position.
 *
 * @since 0.1.0
 * @version 0.3.9
 * @author Innoxia
 */
public class TooltipInventoryEventListener implements EventListener {
	private GameCharacter owner;
	private GameCharacter equippedToCharacter;

	private AbstractCoreItem coreItem;
	private InventorySlot invSlot;

	private AbstractItem item;
	private AbstractItemType genericItem;

	private AbstractWeapon weapon;
	private AbstractWeaponType genericWeapon;
	private DamageType dt;
	private AbstractWeapon dyeWeapon;
	private DamageType damageType;

	private AbstractClothing clothing;
	private AbstractClothingType genericClothing;
	private AbstractClothing dyeClothing;

	private Tattoo tattoo;
	private AbstractTattooType genericTattoo;

	private int colourIndex;

	private Colour patternColour;
	private Colour colour;

	private TFModifier enchantmentModifier;
	private TFPotency potency;

	private static StringBuilder tooltipSB = new StringBuilder();

	private static final int LINE_HEIGHT = 17;
	private static final int TOOLTIP_WIDTH = 400;

	@Override
	public void handleEvent(Event event) {
		tooltipSB.setLength(0);
		int height;
		try(var body = new Markup.ToString(tooltipSB).element("body")) {
			height = handleEvent(body.ui());
		}
		if(height > 0) {
			Main.mainController.setTooltipSize(TOOLTIP_WIDTH, height);
			Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));
		}
		TooltipUpdateThread.updateToolTip(-1, -1);
		// Main.mainController.getTooltip().show(Main.primaryStage);
	}

	private int handleEvent(UIMarkup body) {
		switch(coreItem) {
			case AbstractItem i -> item = i;
			case AbstractWeapon w -> weapon = w;
			case AbstractClothing c -> clothing = c;
		}
		if(item != null)
			return itemTooltip(body, item);
		if(weapon != null)
			return weaponTooltip(body, weapon);
		if(clothing != null)
			return clothingTooltip(body, clothing);
		if(tattoo != null)
			return tattooTooltip(body, tattoo);
		if(dyeClothing != null) {
			Colour subtitleColour = dyeClothing.isEnchantmentKnown()
					? dyeClothing.getRarity().getColour()
					: PresetColour.RARITY_UNKNOWN;
			InventorySlot slotEquippedTo = Objects.requireNonNullElseGet(dyeClothing.getSlotEquippedTo(),
					() -> dyeClothing.getClothingType().getEquipSlots().get(0));
			if(colour != null || patternColour != null) {
				List<Colour> dyeColours = new ArrayList<>(colour != null ? InventoryDialogue.dyePreviews : InventoryDialogue.dyePreviewPatterns);
				dyeColours.set(colourIndex, colour != null ? colour : patternColour);
				try(var div = body.div("title")) {
					div.ui().color(subtitleColour).text(Util.capitaliseSentence(dyeClothing.getName()));
					try(var d = div.ui().div("subTitle")) {
						d.ui().text(Util.capitaliseSentence(
								colour != null ? colour.getName()
										: Pattern.getPattern(InventoryDialogue.dyePreviewPattern).getNiceName()));
						if(colour != null)
							d.text(dyeClothing.getClothingType().getColourReplacement(colourIndex)
											.getDefaultColours().contains(colour)
									? " (Standard Colour)"
									: " (Non-standard Colour)");
					}
					div.ui().div(d -> d.cls("picture full").relativePosition().margin(8).padding(0)
							.style("width:calc(", TOOLTIP_WIDTH, "px - 24px);height:calc(", TOOLTIP_WIDTH, "px - 24px);")
							.text(dyeClothing.getClothingType().getSVGImage(
									slotEquippedTo,
									colour != null ? dyeColours : InventoryDialogue.dyePreviews,
									InventoryDialogue.dyePreviewPattern,
									colour != null ? InventoryDialogue.dyePreviewPatterns : dyeColours,
									InventoryDialogue.getDyePreviewStickersAsStrings())));
				}
			}
			return 480;
		}

		if(dyeWeapon != null) {
			try(var div = body.div("title")) {
				div.ui().color(dyeWeapon.getRarity().getColour())
						.text(Util.capitaliseSentence(dyeWeapon.getName()));
			}
			boolean hasColour = colour != null;
			if(hasColour || damageType != null) {
				List<Colour> dyeColours = hasColour ? new ArrayList<>(InventoryDialogue.dyePreviews) : null;
				if(hasColour)
					dyeColours.set(colourIndex, colour);
				try(var div = body.div("subTitle")) {
					div.ui().text(Util.capitaliseSentence(hasColour ? colour.getName() : damageType.getName()));
					boolean standard = hasColour && dyeWeapon.getWeaponType().getColourReplacement(true, colourIndex).getDefaultColours().contains(colour);
					if(hasColour)
						div.text(standard ? " (S" : " (Non-s", "tandard Colour)");
				}
				try(var div = body.div("picure full")) {
					div.ui().relativePosition().margin(8).padding(0)
							.style("width:calc(", TOOLTIP_WIDTH, "px - 24px);height:calc(", TOOLTIP_WIDTH, "px - 24px);")
							.text(dyeWeapon.getWeaponType().getSVGImage(
									hasColour ? dyeWeapon.getDamageType() : damageType,
									hasColour ? dyeColours : InventoryDialogue.dyePreviews));
				}
			}
			return 480;
		} else if(genericItem != null) {
			return itemTooltip(body, Main.game.getItemGen().generateItem(genericItem));

		} else if(genericClothing != null) {
			return clothingTooltip(body, Main.game.getItemGen().generateClothing(genericClothing, colour, false));

		} else if(genericTattoo != null) {
			return tattooTooltip(body, new Tattoo(
					genericTattoo,
					false,
					new TattooWriting(
							"The quick brown fox jumps over the lazy dog.",
							genericTattoo.getAvailablePrimaryColours().get(0),
							false),
					new TattooCounter(
							TattooCounterType.UNIQUE_SEX_PARTNERS,
							TattooCountType.NUMBERS,
							genericTattoo.getAvailablePrimaryColours().get(0),
							false)));

		} else if(genericWeapon != null) {
			return weaponTooltip(body, Main.game.getItemGen().generateWeapon(genericWeapon, dt));

		} else if(invSlot != null) {
			if(invSlot == InventorySlot.WEAPON_MAIN_1) {
				if(equippedToCharacter == null) {
					body.div(d -> d.cls("title").text("Primary Weapon"));
					return 60;
				}
				if(equippedToCharacter.getMainWeapon(0) == null)
					return setUnarmedWeaponSlotTooltip(body, InventorySlot.WEAPON_MAIN_1, "Primary Weapon");
				return weaponTooltip(body, equippedToCharacter.getMainWeapon(0));
			} else if(invSlot == InventorySlot.WEAPON_MAIN_2) {
				if(equippedToCharacter == null) {
					body.div(d -> d.cls("title").text("Primary Weapon (2nd)"));
					return 60;
				}
				if(equippedToCharacter.getMainWeapon(1) != null)
					return weaponTooltip(body, equippedToCharacter.getMainWeapon(1));
				if(equippedToCharacter.getArmRows() >= 2)
					return setUnarmedWeaponSlotTooltip(body, InventorySlot.WEAPON_MAIN_2, "Primary Weapon (2nd)");
				return setBlockedTooltipContent(body, getTooltipText(equippedToCharacter,
						"You do not have a second pair of arms with which to hold another primary weapon!",
						"[npc.Name] [npc.does] not have a second pair of arms with which to hold another primary weapon!"));
			} else if(invSlot == InventorySlot.WEAPON_MAIN_3) {
				if(equippedToCharacter == null) {
					body.div(d -> d.cls("title").text("Primary Weapon (3rd)"));
					return 60;
				}
				if(equippedToCharacter.getMainWeapon(2) != null)
					return weaponTooltip(body, equippedToCharacter.getMainWeapon(2));
				if(equippedToCharacter.getArmRows() >= 3)
					return setUnarmedWeaponSlotTooltip(body, InventorySlot.WEAPON_MAIN_3, "Primary Weapon (3rd)");
				return setBlockedTooltipContent(body, getTooltipText(equippedToCharacter,
						"You do not have a third pair of arms with which to hold another primary weapon!",
						"[npc.Name] [npc.does] not have a third pair of arms with which to hold another primary weapon!"));
			} else if(invSlot == InventorySlot.WEAPON_OFFHAND_1) {
				if(equippedToCharacter == null) {
					body.div(d -> d.cls("title").text("Secondary Weapon"));
					return 60;
				}
				if(equippedToCharacter.getOffhandWeapon(0) != null)
					return weaponTooltip(body, equippedToCharacter.getOffhandWeapon(0));
				AbstractWeapon primary = equippedToCharacter.getMainWeapon(0);
				if(primary == null || !primary.getWeaponType().isTwoHanded())
					return setUnarmedWeaponSlotTooltip(body, InventorySlot.WEAPON_OFFHAND_1, "Secondary Weapon");
				return setBlockedTooltipContent(body, getTooltipText(equippedToCharacter,
						primary.getWeaponType().isPlural()
								? "As your " + primary.getName() + " require two hands to wield correctly, you are unable to equip a weapon in your off-hand."
								: "As your " + primary.getName() + " requires two hands to wield correctly, you are unable to equip a weapon in your off-hand",
						primary.getWeaponType().isPlural()
								? "As [npc.namePos] " + primary.getName() + " require two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand."
								: "As [npc.namePos] " + primary.getName() + " requires two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand"));
			} else if(invSlot == InventorySlot.WEAPON_OFFHAND_2) {
				if(equippedToCharacter == null) {
					body.div(d -> d.cls("title").text("Secondary Weapon (2nd)"));
					return 60;
				}
				if(equippedToCharacter.getOffhandWeapon(1) != null)
					return weaponTooltip(body, equippedToCharacter.getOffhandWeapon(1));
				AbstractWeapon primary = equippedToCharacter.getMainWeapon(1);
				if(primary != null && primary.getWeaponType().isTwoHanded()) {
					return setBlockedTooltipContent(body, getTooltipText(equippedToCharacter,
							primary.getWeaponType().isPlural()
									? "As your " + primary.getName() + " require two hands to wield correctly, you are unable to equip a weapon in your off-hand."
									: "As your " + primary.getName() + " requires two hands to wield correctly, you are unable to equip a weapon in your off-hand",
							primary.getWeaponType().isPlural()
									? "As [npc.namePos] " + primary.getName() + " require two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand."
									: "As [npc.namePos] " + primary.getName() + " requires two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand"));
				} else if(equippedToCharacter.getArmRows() < 2) {
					return setBlockedTooltipContent(body, getTooltipText(equippedToCharacter,
							"You do not have a second pair of arms with which to hold another secondary weapon!",
							"[npc.Name] [npc.does] not have a second pair of arms with which to hold another secondary weapon!"));
				}
				return setUnarmedWeaponSlotTooltip(body, InventorySlot.WEAPON_OFFHAND_2, "Secondary Weapon (2nd)");
			} else if(invSlot == InventorySlot.WEAPON_OFFHAND_3) {
				if(equippedToCharacter == null) {
					body.div(d -> d.cls("title").text("Secondary Weapon (3rd)"));
					return 60;
				}
				if(equippedToCharacter.getOffhandWeapon(2) != null)
					return weaponTooltip(body, equippedToCharacter.getOffhandWeapon(2));
				AbstractWeapon primary = equippedToCharacter.getMainWeapon(2);
				if(primary != null && primary.getWeaponType().isTwoHanded()) {
					return setBlockedTooltipContent(body, getTooltipText(equippedToCharacter,
							primary.getWeaponType().isPlural()
									? "As your " + primary.getName() + " require two hands to wield correctly, you are unable to equip a weapon in your off-hand."
									: "As your " + primary.getName() + " requires two hands to wield correctly, you are unable to equip a weapon in your off-hand",
							primary.getWeaponType().isPlural()
									? "As [npc.namePos] " + primary.getName() + " require two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand."
									: "As [npc.namePos] " + primary.getName() + " requires two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand"));
				} else if(equippedToCharacter.getArmRows() < 3) {
					return setBlockedTooltipContent(body, getTooltipText(equippedToCharacter,
							"You do not have a third pair of arms with which to hold another secondary weapon!",
							"[npc.Name] [npc.does] not have a third pair of arms with which to hold another secondary weapon!"));
				}
				return setUnarmedWeaponSlotTooltip(body, InventorySlot.WEAPON_OFFHAND_3, "Secondary Weapon (3rd)");
			} else {
				if(equippedToCharacter == null)
					return setEmptyInventorySlotTooltipContent(body);
				boolean renderingTattoos = equippedToCharacter.isPlayer()
						&& RenderingEngine.ENGINE.isRenderingTattoosLeft()
						|| !equippedToCharacter.isPlayer()
						&& RenderingEngine.ENGINE.isRenderingTattoosRight()
						&& !invSlot.isJewellery();
				if((renderingTattoos ? equippedToCharacter.getTattooInSlot(invSlot) : equippedToCharacter.getClothingInSlot(invSlot)) == null) {
					if(renderingTattoos && !invSlot.isJewellery()) {
						return tattooTooltip(body, equippedToCharacter.getTattooInSlot(invSlot));
					}
					return clothingTooltip(body, equippedToCharacter.getClothingInSlot(invSlot));
				}
				List<String> clothingBlockingThisSlot = new ArrayList<>();
				for(AbstractClothing c : equippedToCharacter.getClothingCurrentlyEquipped()) {
					if(c.getIncompatibleSlots(equippedToCharacter, c.getSlotEquippedTo()).contains(invSlot)) {
						clothingBlockingThisSlot.add(c.getName());
					}
				}
				BodyPartClothingBlock block = invSlot.getBodyPartClothingBlock(equippedToCharacter);
				if(!renderingTattoos && !clothingBlockingThisSlot.isEmpty()) {
					return setBlockedTooltipContent(body, UtilText.parse(equippedToCharacter, "This slot is currently <b style='color:" + PresetColour.SEALED.toWebHexString() + ";'>blocked</b> by [npc.namePos] ")
							+ Util.stringsToStringList(clothingBlockingThisSlot, false) + ".");
				} else if(!renderingTattoos && block != null) {
					return setBlockedTooltipContent(body, "<span style='color:" + PresetColour.GENERIC_MINOR_BAD.toWebHexString() + ";'>Restricted!</span>", UtilText.parse(equippedToCharacter, block.getDescription()));
				}
				boolean bypassesPiercing = !equippedToCharacter.getBody().getBodyMaterial().isRequiresPiercing();
				String blockText = switch(invSlot) {
					case PIERCING_VAGINA -> equippedToCharacter.getVaginaType() == VaginaType.NONE
							? getTooltipText(equippedToCharacter,
							"You don't have a vagina.",
							(equippedToCharacter.isAreaKnownByCharacter(CoverableArea.VAGINA, Main.game.getPlayer())
									? "[npc.Name] doesn't have a vagina."
									: "You don't know if [npc.name] has a vagina."))
							: !bypassesPiercing && !equippedToCharacter.isPiercedVagina()
									? getTooltipText(equippedToCharacter,
									"Your vagina has not been pierced.",
									(equippedToCharacter.isAreaKnownByCharacter(CoverableArea.VAGINA, Main.game.getPlayer())
											? "[npc.NamePos] vagina has not been pierced."
											: "You don't know if [npc.name] has a vagina."))
									: null;
					case PIERCING_EAR -> bypassesPiercing || equippedToCharacter.isPiercedEar() ? null
							: getTooltipText(equippedToCharacter,
									"Your ears have not been pierced.",
									"[npc.NamePos] ears have not been pierced.");
					case PIERCING_LIP -> bypassesPiercing || equippedToCharacter.isPiercedLip() ? null
							: getTooltipText(equippedToCharacter,
									"Your lips have not been pierced.",
									"[npc.NamePos] lips have not been pierced.");
					case PIERCING_NIPPLE -> bypassesPiercing || equippedToCharacter.isPiercedNipple() ? null
							: getTooltipText(equippedToCharacter,
									"Your nipples have not been pierced.",
									(equippedToCharacter.isAreaKnownByCharacter(CoverableArea.NIPPLES, Main.game.getPlayer())
											? "[npc.NamePos] nipples have not been pierced."
											: "You don't know if [npc.namePos] nipples have been pierced."));
					case PIERCING_NOSE -> bypassesPiercing || equippedToCharacter.isPiercedNose() ? null
							: getTooltipText(equippedToCharacter,
									"Your nose has not been pierced.",
									"[npc.NamePos] nose has not been pierced.");
					case PIERCING_PENIS -> equippedToCharacter.getPenisType() == PenisType.NONE
							? getTooltipText(equippedToCharacter,
							"You don't have a penis.",
							(equippedToCharacter.isAreaKnownByCharacter(CoverableArea.PENIS, Main.game.getPlayer())
									? "[npc.Name] doesn't have a penis."
									: "You don't know if [npc.name] has a penis."))
							: !bypassesPiercing && !equippedToCharacter.isPiercedPenis()
									? getTooltipText(equippedToCharacter,
									"Your penis has not been pierced.",
									(equippedToCharacter.isAreaKnownByCharacter(CoverableArea.PENIS, Main.game.getPlayer())
											? "[npc.NamePos] penis has not been pierced."
											: "You don't know if [npc.name] has a penis."))
									: null;
					case PIERCING_STOMACH -> bypassesPiercing || equippedToCharacter.isPiercedNavel() ? null
							: getTooltipText(equippedToCharacter,
									"Your navel has not been pierced.",
									"[npc.NamePos] navel has not been pierced.");
					case PIERCING_TONGUE -> bypassesPiercing || equippedToCharacter.isPiercedTongue() ? null
							: getTooltipText(equippedToCharacter,
									"Your tongue has not been pierced.",
									"[npc.NamePos] tongue has not been pierced.");
					case HORNS -> equippedToCharacter.getHornType() != HornType.NONE ? null
							: getTooltipText(equippedToCharacter,
									"You don't have any horns.",
									"[npc.Name] doesn't have any horns.");
					case PENIS -> equippedToCharacter.hasPenisIgnoreDildo() ? null
							: getTooltipText(equippedToCharacter,
									"You don't have a penis.",
									"[npc.Name] doesn't have a penis.");
					case TAIL -> equippedToCharacter.getTailType() != TailType.NONE ? null
							: getTooltipText(equippedToCharacter,
									"You don't have a tail.",
									"[npc.Name] doesn't have a tail.");
					case VAGINA -> equippedToCharacter.hasVagina() ? null
							: getTooltipText(equippedToCharacter,
									"You don't have a vagina.",
									"[npc.Name] doesn't have a vagina.");
					case WINGS -> equippedToCharacter.getWingType() != WingType.NONE ? null
							: getTooltipText(equippedToCharacter,
									"You don't have any wings.",
									"[npc.Name] doesn't have any wings.");
					default -> null;
				};
				if(blockText != null)
					return setBlockedTooltipContent(body, blockText);
				if(renderingTattoos)
					return scarTooltip(body, equippedToCharacter.getScarInSlot(invSlot));
				return setEmptyInventorySlotTooltipContent(body);
			}

		} else if(enchantmentModifier != null) {
			boolean requiresFlames = EnchantmentDialogue.getIngredient() instanceof Tattoo;
			int value = enchantmentModifier.getValue() * (requiresFlames ? EnchantingUtils.FLAME_COST_MODIFER : 1);
			body.div(d -> d.cls("title")
					.color(enchantmentModifier.getRarity().getColour())
					.text(Util.capitaliseSentence(enchantmentModifier.getName())))
			.div(d -> d.cls("description").height(48).text(enchantmentModifier.getDescription()))
			.div(d -> d.cls("subTitle").text(
					requiresFlames ? UtilText.formatAsMoney(value, "b") : UtilText.formatAsEssences(value, "b", false),
					requiresFlames ? " cost" : " essence cost"));
			return 152;
		} else if(potency != null) {
			try(var div = body.div("title")) {
				div.ui().text("Set potency to ")
				.bold(s -> s.color(potency.getColour()).text(Util.capitaliseSentence(potency.getName())));
			}
			return 60;
		}
		return 0;
	}


	private int setBlockedTooltipContent(UIMarkup body, String description) {
		return setBlockedTooltipContent(body, "<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Blocked!</span>", description);
	}

	private int setBlockedTooltipContent(UIMarkup body, String title, String description) {
		boolean dirty = equippedToCharacter.isDirtySlot(invSlot);
		String dirtyText = !dirty ? "" : "[npc.NamePos] " + invSlot.getName() + " " + (invSlot.isPlural() ? "are" : "is")
				+ " <span style='color:" + PresetColour.CUM.toWebHexString() + ";'>dirty</span>!<br/>";
		body.div(d -> d.cls("title").text(Util.capitaliseSentence(invSlot.getName()), ": ", title))
				.div(d -> d.cls("description").height(72).center().text(dirtyText, UtilText.parse(description)));
		return 144;
	}


	private int setEmptyInventorySlotTooltipContent(UIMarkup body) {
		body.div(d -> d.cls("title").text(Util.capitaliseSentence(invSlot.getName())));
		boolean dirty = equippedToCharacter.isDirtySlot(invSlot);
		if(equippedToCharacter != null && dirty) {
			String dirtyText = "[npc.NamePos] " + invSlot.getName() + " " + (invSlot.isPlural() ? "have" : "has")
					+ " been <span style='color:" + PresetColour.CUM.toWebHexString() + ";'>dirtied</span> by sexual fluids!";
			body.div(d -> d.cls("description").height(48).center().text(dirtyText));
		}
		return 60 + (dirty ? 56 : 0);
	}


	public TooltipInventoryEventListener setCoreItem(AbstractCoreItem coreItem, GameCharacter owner, GameCharacter equippedToCharacter) {
		resetVariables();
		this.coreItem = coreItem;
		this.equippedToCharacter = equippedToCharacter;
		this.owner = owner;
		return this;
	}

	public TooltipInventoryEventListener setItem(AbstractItem item, GameCharacter owner, GameCharacter equippedToCharacter) {
		resetVariables();
		this.item = item;
		this.equippedToCharacter = equippedToCharacter;
		this.owner = owner;
		return this;
	}

	public TooltipInventoryEventListener setTattoo(InventorySlot invSlot, Tattoo tattoo, GameCharacter owner, GameCharacter equippedToCharacter) {
		resetVariables();
		this.invSlot = invSlot;
		this.tattoo = tattoo;
		this.equippedToCharacter = equippedToCharacter;
		this.owner = owner;
		return this;
	}

	public TooltipInventoryEventListener setGenericItem(AbstractItemType genericItem) {
		resetVariables();
		this.genericItem = genericItem;
		return this;
	}

	public TooltipInventoryEventListener setClothing(AbstractClothing clothing, GameCharacter owner, GameCharacter equippedToCharacter) {
		resetVariables();
		this.clothing = clothing;
		this.equippedToCharacter = equippedToCharacter;
		this.owner = owner;
		return this;
	}

	public TooltipInventoryEventListener setDyeClothing(AbstractClothing dyeClothing, int colourIndex, Colour colour) {
		resetVariables();
		this.dyeClothing = dyeClothing;
		this.colourIndex = colourIndex;
		this.colour = colour;
		return this;
	}

	public TooltipInventoryEventListener setDyeClothingPattern(AbstractClothing dyeClothing, int colourIndex, Colour patternColour) {
		resetVariables();
		this.dyeClothing = dyeClothing;
		this.colourIndex = colourIndex;
		this.patternColour = patternColour;
		return this;
	}

	public TooltipInventoryEventListener setDyeWeapon(AbstractWeapon dyeWeapon, int colourIndex, Colour colour) {
		resetVariables();
		this.dyeWeapon = dyeWeapon;
		this.colourIndex = colourIndex;
		this.colour = colour;
		return this;
	}

	public TooltipInventoryEventListener setDamageTypeWeapon(AbstractWeapon dyeWeapon, DamageType damageType) {
		resetVariables();
		this.dyeWeapon = dyeWeapon;
		this.damageType = damageType;
		return this;
	}

	public TooltipInventoryEventListener setGenericClothing(AbstractClothingType genericClothing) {
		resetVariables();
		this.genericClothing = genericClothing;
		return this;
	}

	public TooltipInventoryEventListener setGenericClothing(AbstractClothingType genericClothing, Colour colour) {
		resetVariables();
		this.genericClothing = genericClothing;
		this.colour = colour;
		return this;
	}

	public TooltipInventoryEventListener setGenericTattoo(AbstractTattooType genericTattoo) {
		resetVariables();
		this.genericTattoo = genericTattoo;
		invSlot = genericTattoo.getSlotAvailability().contains(InventorySlot.TORSO_UNDER) ? InventorySlot.TORSO_UNDER : genericTattoo.getSlotAvailability().get(0);
		return this;
	}

	public TooltipInventoryEventListener setGenericWeapon(AbstractWeaponType genericWeapon, DamageType dt) {
		resetVariables();
		this.genericWeapon = genericWeapon;
		this.dt = dt;
		return this;
	}

	public TooltipInventoryEventListener setWeapon(AbstractWeapon weapon, GameCharacter owner, boolean isEquipped) {
		resetVariables();
		this.weapon = weapon;
		if(isEquipped) {
			this.equippedToCharacter = owner;
		}
		this.owner = owner;
		return this;
	}

	public TooltipInventoryEventListener setInventorySlot(InventorySlot invSlot, GameCharacter equippedToCharacter) {
		resetVariables();
		this.invSlot = invSlot;
		this.equippedToCharacter = equippedToCharacter;
		this.owner = equippedToCharacter;
		return this;
	}

	public TooltipInventoryEventListener setTFModifier(TFModifier enchantmentModifier) {
		resetVariables();
		this.enchantmentModifier = enchantmentModifier;
		return this;
	}

	public TooltipInventoryEventListener setTFPotency(TFPotency potency) {
		resetVariables();
		this.potency = potency;
		return this;
	}

	private void resetVariables() {
		owner = null;
		equippedToCharacter = null;
		coreItem = null;
		item = null;
		tattoo = null;
		genericItem = null;
		weapon = null;
		genericWeapon = null;
		dt = null;
		clothing = null;
		patternColour = null;
		colour = null;
		colourIndex = 0;
		dyeClothing = null;
		dyeWeapon = null;
		damageType = null;
		genericClothing = null;
		genericTattoo = null;
		invSlot = null;
		enchantmentModifier = null;
		potency = null;
	}

	private int itemTooltip(UIMarkup body, AbstractItem absItem) {

		int yIncrease = 0;
		int listIncrease = 0;

		if(!absItem.getEffects().isEmpty()) {
			listIncrease += 1;
		}


		// Title:
		try(var divFillWidth = body.div("container-full-width center")) {
			divFillWidth.ui().element("h5", h -> h.text(Util.capitaliseSentence(absItem.getDisplayName(true))));
		}

		// Core info:
		try(var divFullWidth = body.div("container-full-width titular")) {
			divFullWidth.ui().span(absItem.isConsumedOnUse()
					? s -> s.color(PresetColour.GENERIC_BAD).text("Consumed on use")
					: s -> s.color(PresetColour.GENERIC_GOOD).text("Infinite uses"));
		}

		try(var divFullWidth = body.div("container-full-width")) {
			try(var half = divFullWidth.ui().div("container-half-width titular")) {
				half.ui().style("width:calc(66.6% - 16px);")
				.span(s -> s.color(absItem.getRarity().getColour())
						.text(Util.capitaliseSentence(absItem.getRarity().getName())));
				if(absItem.isAppendItemEffectLinesToTooltip()) {
					for(Entry<ItemEffect, Long> entry : absItem.getEffects().stream()
							.collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet()) {
						long effectMulti = entry.getValue();
						String multiplier = effectMulti == 1 ? "" : UtilText.parse("[style.colourArcane(x" + effectMulti + ")] ");
						List<String> effects = entry.getKey().getEffectsDescription(Main.game.getPlayer(), Main.game.getPlayer());
						listIncrease += effects.size();
						for(String s : effects) {
							half.ui().br().text(multiplier, s);
							multiplier = "";
						}
					}
				}
				listIncrease += absItem.getEffectTooltipLines().size();
				for(String s : absItem.getEffectTooltipLines())
					half.ui().br().text(s);
				yIncrease += Math.max(0, listIncrease - 4);
			}

			// Picture:
			try(var itemImage = divFullWidth.ui().div("item-image")) {
				itemImage.ui().div(d -> d.cls("item-image-content").text(absItem.getSVGString()));
			}

		}

		body.div(d -> d.cls("container-full-width").padding(8).minHeight(106).text(absItem.getDescription()));


		// Extra descriptions:
		List<String> extraDescriptions = absItem.getExtraDescriptions(equippedToCharacter);

		if(!extraDescriptions.isEmpty()) {
			yIncrease += 2 + absItem.getExtraDescriptions(equippedToCharacter).size();
			try(var div = body.div("container-full-width titular")) {
				div.ui().normal().bold(b -> b.text("Status"));
				for(String s : extraDescriptions)
					div.ui().br().text(s);
			}
		}


		// Value:

		try(var div = body.div("container-full-width titular")) {
			div.ui().text("Value: ", UtilText.formatAsMoney(absItem.getValue()));
			var inventoryNPC = owner == null ? null : InventoryDialogue.getInventoryNPC();
			if(inventoryNPC != null && InventoryDialogue.getNPCInventoryInteraction() == InventoryInteraction.TRADING) {
				div.text(" | ");
				String name = inventoryNPC.getName("The");
				if(!owner.isPlayer()) {
					int price = InventoryDialogue.isBuyback()
							? getBuybackPriceFor(absItem)
							: absItem.getPrice(inventoryNPC.getSellModifier(absItem));
					div.text(name, " wants ", UtilText.formatAsMoney(price));
				} else if(inventoryNPC.willBuy(absItem)) {
					div.text(name, " offers: ", UtilText.formatAsMoney(absItem.getPrice(inventoryNPC.getBuyModifier())));
				} else {
					div.ui().span(s -> s.color(PresetColour.TEXT_GREY).text(name, " will not buy this"));
				}
			}
		}


		String author = absItem.getItemType().getAuthorDescription();
		if(!author.isEmpty()) {
			yIncrease += 4;
			body.div(d -> d.cls("description").height(52).text(author));
		}

		return 364 + yIncrease * LINE_HEIGHT;
	}

	private static Colour chanceColour(int chance) {
		//assert chance >= 0 && chance <= 100;
		return chance <= 25 ? PresetColour.GENERIC_BAD
		: chance <= 50 ? PresetColour.GENERIC_MINOR_BAD
		: chance <= 75 ? PresetColour.GENERIC_MINOR_GOOD
		: PresetColour.GENERIC_GOOD;
	}

	private int weaponTooltipAttributes(AbstractWeapon absWep, UIMarkup div) {
		int listIncrease = 0;
		div.cls("container-half-width titular").style("width:calc(66.6% - 16px);")
		.span(s -> s.color(absWep.getRarity().getColour())
				.text(Util.capitaliseSentence(absWep.getRarity().getName())))
		.text(" | ", absWep.getWeaponType().isUsingUnarmedCalculation()
				? "[style.colourUnarmed(Unarmed)]"
				: absWep.getWeaponType().isMelee()
						? "[style.colourMelee(Melee)]"
						: "[style.colourRanged(Ranged)]")
		.br()
		.text(absWep.getWeaponType().isTwoHanded() ? "Two-handed" : "One-handed",
				absWep.getWeaponType().isOneShot() ? " - [style.colourYellow(One-shot)]" : "")
		.br();

		float res = absWep.getWeaponType().getPhysicalResistance();
		if(res > 0) {
			listIncrease++;
			div.text("[style.boldGood(+", res, ")] Natural [style.boldResPhysical(",
					Util.capitaliseSentence(Attribute.RESISTANCE_PHYSICAL.getName()), ")]")
			.br();
		}

		int cost = absWep.getWeaponType().getArcaneCost();
		if(cost > 0) {
			listIncrease++;
			div.text("Costs [style.boldArcane(", cost, " Arcane essence", cost > 1 ? "s" : "", ")] ",
					absWep.getWeaponType().isMelee() ? "per attack" : "to fire")
			.br();
			if(absWep.getWeaponType().isMelee()) {
				listIncrease++; // To account for the fact that the arcane cost description for melee weapons takes two lines
			}
		}

		if(equippedToCharacter != null) {
			if(absWep.getWeaponType().isUsingUnarmedCalculation()) {
				listIncrease++;
				div.text("Includes [style.boldUnarmed(", equippedToCharacter.getUnarmedDamage(), " unarmed damage)]")
				.br();
			}

		} else if(owner != null && !owner.isPlayer()) {
			listIncrease++;
			div.text(UtilText.parse(owner, "[npc.Name]: "))
			.bold(b -> b.text(Attack.getMinimumDamage(owner, null, Attack.MAIN, absWep),
					" - ", Attack.getMaximumDamage(owner, null, Attack.MAIN, absWep)))
			.text(" ")
			.bold(b -> b.color(absWep.getDamageType().getMultiplierAttribute().getColour()).text("Damage"));
			for(Value<Integer, Integer> aoe : absWep.getWeaponType().getAoeDamage()) {
				listIncrease++;
				int aoeChance = aoe.getKey();
				div.br()
				.text("[style.boldAqua(AoE)]: (")
				.bold(b -> b.color(chanceColour(aoeChance)).text(aoeChance, "%"))
				.text("): ")
				.bold(b -> b.text(Attack.getMinimumDamage(owner, null, Attack.MAIN, absWep, aoe.getValue()),
						" - ", Attack.getMaximumDamage(owner, null, Attack.MAIN, absWep, aoe.getValue())))
				.text(" ")
				.bold(b -> b.color(absWep.getDamageType().getMultiplierAttribute().getColour()).text("Damage"));
			}
			div.br().text("You: ");
		}

		var c = equippedToCharacter != null ? equippedToCharacter : Main.game.getPlayer();

		div.bold(b -> b.text(Attack.getMinimumDamage(c, null, Attack.MAIN, absWep),
				" - ", Attack.getMaximumDamage(c, null, Attack.MAIN, absWep)))
		.text(" ")
		.bold(b -> b.color(absWep.getDamageType().getMultiplierAttribute().getColour()).text("Damage"));

		for(Value<Integer, Integer> aoe : absWep.getWeaponType().getAoeDamage()) {
			listIncrease++;
			int aoeChance = aoe.getKey();
			div.br().text("[style.boldAqua(AoE)]: (")
			.bold(b -> b.color(chanceColour(aoeChance)).text(aoeChance, "%"))
			.text("): ")
			.bold(b -> b.text(Attack.getMinimumDamage(c, null, Attack.MAIN, absWep, aoe.getValue()),
					" - ", Attack.getMaximumDamage(c, null, Attack.MAIN, absWep, aoe.getValue())))
			.text(" ")
			.bold(b -> b.color(absWep.getDamageType().getMultiplierAttribute().getColour()).text("Damage"));
		}

		if(absWep.getWeaponType().isOneShot()) {
			listIncrease++;
			listIncrease++;
			int chanceToRecoverTurn = (int) absWep.getWeaponType().getOneShotChanceToRecoverAfterTurn();
			int chanceToRecoverCombat = (int) absWep.getWeaponType().getOneShotChanceToRecoverAfterCombat();

			div.br()
			.span(s -> s.color(chanceColour(chanceToRecoverTurn)).text(chanceToRecoverTurn, "%"))
			.text(" recovery [style.colourBlueLight(after use)]")
			.br()
			.span(s -> s.color(chanceColour(chanceToRecoverCombat)).text(chanceToRecoverCombat, "%"))
			.text(" recovery [style.colourCombat(after combat)]");
		}

		for(String s : absWep.getWeaponType().getExtraEffects()) {
			div.br().bold(b -> b.text(s));
		}

		for(Entry<AbstractAttribute, Integer> entry : absWep.getAttributeModifiers().entrySet()) {
			div.br().bold(b -> b.text(entry.getKey().getFormattedValue(entry.getValue())));
		}

		for(Spell s : absWep.getSpells()) {
			div.br().text("[style.boldSpell(Spell)]").bold(b -> b.text(":")).text(" ")
			.bold(b -> b.color(s.getSpellSchool().getColour())
					.text(Util.capitaliseSentence(s.getName())));
		}

		for(AbstractCombatMove cm : absWep.getCombatMoves()) {
			div.br().text("[style.boldCombat(Move)]").bold(b -> b.text(":"))
			.text(" ", Util.capitaliseSentence(cm.getName(0, Main.game.getPlayer())));
		}

		return listIncrease;
	}

	private int weaponTooltip(UIMarkup body, AbstractWeapon absWep) {

		int yIncrease = 0;
		int listIncrease = 2 + absWep.getAttributeModifiers().size();
		listIncrease += absWep.getSpells().size();
		listIncrease += absWep.getWeaponType().getExtraEffects().size();

		String author = absWep.getWeaponType().getAuthorDescription();
		if(!author.isEmpty()) {
			yIncrease += 4;
		}
		if(!absWep.getExtraDescriptions(equippedToCharacter).isEmpty()) { //TODO
			yIncrease += 2 + absWep.getExtraDescriptions(equippedToCharacter).size();
		}

		// Title:
		body.div(d -> d.cls("container-full-width center")
		.element("h5", h -> h.text(Util.capitaliseSentence(absWep.getDisplayName(true)))));

		//Core info:
		body.div(d -> d.cls("container-half-width titular")
		.color(absWep.getDamageType().getMultiplierAttribute().getColour())
		.text(Util.capitaliseSentence(absWep.getDamageType().getName()), " damage"));
		var set = absWep.getWeaponType().getClothingSet();
		body.div(d -> d.cls("container-half-width titular")
		.span(set == null
				? s -> s.color(PresetColour.TEXT_GREY).text("Not part of a set")
				: s -> s.color(PresetColour.RARITY_EPIC).text(set.getName(), " set")));


		// Attribute modifiers:
		try(var container = body.ui().div("container-full-width")) {
			try(var d = container.ui().div()) {
				listIncrease += weaponTooltipAttributes(absWep, d.ui());
			}

			// Picture:
			container.ui().div(d -> d.cls("item-image").div(x -> x.cls("item-image-content")
			.text((owner != null && owner.hasWeaponEquipped(absWep))
					? absWep.getSVGEquippedString(owner)
					: absWep.getSVGString())));

		}

		body.div(d -> d.cls("container-full-width").padding(8).minHeight(106)
				.parse(absWep.getWeaponType().getDescription()));

		if(owner != null && owner.getEssenceCount() < absWep.getWeaponType().getArcaneCost()) {
			yIncrease += 2;
			body.div(d -> d.cls("container-full-width titular")
					.text("[style.colourBad(Not enough essences to fire!)]"));
		}

		// Extra descriptions:
		List<String> extraDescriptions = absWep.getExtraDescriptions(equippedToCharacter);

		if(!extraDescriptions.isEmpty()) {
			try(var div = body.div("container-full-width titular")) {
				div.ui().normal().bold(b -> b.text("Status"));
				for(String extraDescription : extraDescriptions)
					div.ui().br().text(extraDescription);
			}
		}

		// Value:

		UIMarkup.UIPrintable printValue = owner.isPlayer() ? InventoryDialogue.getInventoryNPC().willBuy(absWep)
				? x -> x.text(" | ", InventoryDialogue.getInventoryNPC().getName("The"), " offers: ",
				UtilText.formatAsMoney(absWep.getPrice(InventoryDialogue.getInventoryNPC().getBuyModifier())))
				: x -> x.text(" | ").span(s -> s.color(PresetColour.TEXT_GREY)
						.text(InventoryDialogue.getInventoryNPC().getName("The"), " will not buy this"))
				: x -> x.text(" | ", InventoryDialogue.getInventoryNPC().getName("The"), " wants ",
						UtilText.formatAsMoney(InventoryDialogue.isBuyback()
								? +getBuybackPriceFor(absWep)
								: absWep.getPrice(InventoryDialogue.getInventoryNPC().getSellModifier(absWep))));
		body.div(d -> d.cls("container-full-width titular")
				.text("Value: ", UtilText.formatAsMoney(absWep.getValue()))
				.print(printValue.onlyIf(InventoryDialogue.getInventoryNPC() != null
						&& InventoryDialogue.getNPCInventoryInteraction() == InventoryInteraction.TRADING)));
		if(Main.game.isEnchantmentCapacityEnabled()) {
			int enchCapacityCost = absWep.getEnchantmentCapacityCost();
			body.ui().div(d -> d.cls("container-full-width titular")
					.text(enchCapacityCost == 0
							? Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + " cost: [style.boldDisabled(" + enchCapacityCost + ")]"
							: "[style.colourEnchantment(" + Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + " cost)]: [style.boldBad(" + enchCapacityCost + ")]"));
		}
		if(!author.isEmpty())
			body.ui().div(d -> d.cls("description").height(52).text(author));

		return 364
				+ (Main.game.isEnchantmentCapacityEnabled() ? 32 : 0)
				+ (yIncrease + Math.max(0, listIncrease - 4)) * LINE_HEIGHT;
	}

	private int clothingTooltip(UIMarkup body, AbstractClothing absClothing) {
		int yIncrease = 0;

		int listIncrease = absClothing.getAttributeModifiers().size();

		float resistance = absClothing.getClothingType().getPhysicalResistance();
		if(resistance > 0) {
			listIncrease++;
		}

		InventorySlot slotEquippedTo = absClothing.getSlotEquippedTo();
		yIncrease += absClothing.getExtraDescriptions(equippedToCharacter, null, false).size();
		if(slotEquippedTo == null) {
			slotEquippedTo = absClothing.getClothingType().getEquipSlots().get(0);
			for(InventorySlot is : absClothing.getClothingType().getEquipSlots()) {
				yIncrease += absClothing.getExtraDescriptions(equippedToCharacter, is, false).size();
			}

		} else {
			yIncrease += absClothing.getExtraDescriptions(equippedToCharacter, slotEquippedTo, false).size();
		}

		for(ItemEffect ie : absClothing.getEffects()) {
			if(ie.getSecondaryModifier() == TFModifier.CLOTHING_ENSLAVEMENT
					|| ie.getSecondaryModifier() == TFModifier.CLOTHING_SEALING) {
				listIncrease += 1;

			} else if(ie.getPrimaryModifier() != TFModifier.CLOTHING_ATTRIBUTE && ie.getPrimaryModifier() != TFModifier.CLOTHING_MAJOR_ATTRIBUTE) {
				listIncrease += 2;
			}
		}
		yIncrease += Math.max(0, listIncrease - 4);

		String author = absClothing.getClothingType().getAuthorDescription();
		if(!author.isEmpty()) {
			yIncrease += 4;
		}

		//TODO all text is to be parsed with npc
		var npc = equippedToCharacter == null ? Main.game.getPlayer() : equippedToCharacter;

		// Title:
		body.div(d -> d.fullWidth().center()
				.element("h5", h -> h.text(Util.capitaliseSentence(absClothing.getDisplayName(true)))));

		// Core info:
		try(var divHalfWidth = body.div("container-half-width titular")) {
			boolean nonPiercingSlots = absClothing.getClothingType().getEquipSlots().stream().anyMatch(is -> !is.isJewellery());
			List<InventorySlot> possibleSlots = new ArrayList<>(absClothing.getClothingType().getEquipSlots());
			if(nonPiercingSlots) {
				InventorySlot equippedTo = absClothing.getSlotEquippedTo();
				possibleSlots.sort(Comparator.comparing(s -> equippedTo == s));
			}
			for(int i = 0; i < possibleSlots.size(); i++) {
				InventorySlot slot = possibleSlots.get(i);
				boolean equipped = absClothing.getSlotEquippedTo() == slot;
				// Slots are all piercings, so to abbreviate the slot names, the ' piercing' parts can all be removed, then a final ' piercing' can be appended at the end
				String slotName = Util.capitaliseSentence(nonPiercingSlots ? slot.getName() : slot.getName().replace(" piercing", ""));
				boolean notEquipped = absClothing.getSlotEquippedTo() == null;
				divHalfWidth.text(
						equipped || notEquipped ? slotName : "[style.colourDisabled(" + slotName + ")]",
						i == possibleSlots.size() - 1 ? "" : notEquipped ? "/" : "[style.colourDisabled(/)]",
						nonPiercingSlots || i != possibleSlots.size() - 1 ? "" : " piercing");
			}
		}
		try(var div = body.div("container-half-width titular")) {
			var set = absClothing.getClothingType().getClothingSet();
			div.ui().color(set == null ? PresetColour.TEXT_GREY : PresetColour.RARITY_EPIC)
					.text(set == null ? "Not part of a set" : set.getName());
		}

		// Attribute modifiers:
		try(var div = body.div("container-full-width")) {
			try(var titleDiv = div.ui().div("container-half-width titular")) {
				titleDiv.ui().style("width:calc(66.6%-16px);");

				Femininity femininityRestriction = absClothing.getClothingType().getFemininityRestriction();
				var rarity = absClothing.isEnchantmentKnown() ? absClothing.getRarity() : null;
				titleDiv.ui().span(s -> s.color(rarity != null ? rarity.getColour() : PresetColour.TEXT_GREY)
								.text(Util.capitaliseSentence(rarity != null ? rarity.getName() : "Unknown")))
						.text(" | ", femininityRestriction == null || femininityRestriction == Femininity.ANDROGYNOUS
								? "[style.boldAndrogynous(Unisex)]"
								: (femininityRestriction.isFeminine()
										? "[style.boldFeminine(Feminine)]"
										: "[style.boldMasculine(Masculine)]"));

				if(resistance > 0) {
					titleDiv.ui().br().text("[style.boldGood(+", resistance, ")] Natural [style.boldResPhysical(",
							Util.capitaliseSentence(Attribute.RESISTANCE_PHYSICAL.getName()), ")]");
				}

				if(!absClothing.getEffects().isEmpty()) {
					if(!absClothing.isEnchantmentKnown()) {
						titleDiv.ui().br().text("[style.colourDisabled(Unidentified effects!)]");
					} else {
						for(ItemEffect e : absClothing.getEffects()) {
							if(e.getPrimaryModifier() != TFModifier.CLOTHING_ATTRIBUTE && e.getPrimaryModifier() != TFModifier.CLOTHING_MAJOR_ATTRIBUTE) {
								for(String s : e.getEffectsDescription(owner, owner)) {
									titleDiv.ui().br().text(s);
								}
							}
						}
						for(Entry<AbstractAttribute, Integer> entry : absClothing.getAttributeModifiers().entrySet()) {
							titleDiv.ui().br().bold(b -> b.text(entry.getKey().getFormattedValue(entry.getValue())));
						}
					}

				} else {
					titleDiv.ui().br().text("[style.colourDisabled(No bonuses)]");
				}

			}


			// Picture:
			try(var d = div.ui().div()) {
				String content = owner != null && owner.getClothingCurrentlyEquipped().contains(absClothing) ? absClothing.getSVGEquippedString(owner) : absClothing.getSVGString();
				d.ui().cls("item-image")
						.div(c -> c.cls("item-image-content").text(content));
			}

		}

		body.div(d -> d.cls("container-full-width").padding(8).minHeight(106).text(absClothing.getTypeDescription()));


		// Extra descriptions:
		List<String> extraDescriptions = new ArrayList<>();

		try(var div = body.div()) {
			div.ui().cls("container-full-width titular").normal();

			extraDescriptions.addAll(absClothing.getExtraDescriptions(equippedToCharacter, null, false));

			if(absClothing.getSlotEquippedTo() == null && absClothing.getClothingType().getEquipSlots().size() > 1) {
				for(int i = 0; i < absClothing.getClothingType().getEquipSlots().size(); i++) {
					InventorySlot slot = absClothing.getClothingType().getEquipSlots().get(i);

					if(!absClothing.getExtraDescriptions(equippedToCharacter, slot, false).isEmpty()) {
						extraDescriptions.add("<i>When equipped into '" + slot.getName() + "' slot:</i>");
						yIncrease++;
						for(String s : absClothing.getExtraDescriptions(equippedToCharacter, slot, false)) {
							extraDescriptions.add(s);
						}
					}
				}

			} else {
				if(!absClothing.getExtraDescriptions(equippedToCharacter, slotEquippedTo, false).isEmpty()) {
					for(String s : absClothing.getExtraDescriptions(equippedToCharacter, slotEquippedTo, false)) {
						extraDescriptions.add(s);
					}
				}
			}
			if(extraDescriptions.isEmpty()) {
				div.ui().span(s -> s.color(PresetColour.TEXT_GREY).text("No Status"));
			} else {
				div.ui().bold(b -> b.text("Status"));
				for(String extraDescription : extraDescriptions)
					div.ui().br().text(UtilText.parse(npc, extraDescription));
			}
		}


		// Value:
		try(var div = body.div()) {
			div.ui().cls("container-full-width titular")
					.text("Value: ", absClothing.isEnchantmentKnown()
							? UtilText.formatAsMoney(absClothing.getValue()) : UtilText.formatAsMoney("?", "b"));
			var inventoryNPC = InventoryDialogue.getInventoryNPC();
			if(inventoryNPC != null && InventoryDialogue.getNPCInventoryInteraction() == InventoryInteraction.TRADING) {
				if(!owner.isPlayer()) {
					int actualPrice = InventoryDialogue.isBuyback() ? +getBuybackPriceFor(absClothing)
							: absClothing.getPrice(inventoryNPC.getSellModifier(absClothing));
					div.text(" | ", inventoryNPC.getName("The"), " wants ",
							UtilText.formatAsMoney(actualPrice));
				} else if(inventoryNPC.willBuy(absClothing)) {
					div.text(" | ", inventoryNPC.getName("The"), " offers ",
							UtilText.formatAsMoney(absClothing.getPrice(inventoryNPC.getBuyModifier())));
				} else {
					div.ui().text(" | ")
							.span(s -> s.color(PresetColour.TEXT_GREY)
									.text(inventoryNPC.getName("The"), " will not buy this"));
				}
			}
		}

		if(Main.game.isEnchantmentCapacityEnabled()) {
			int enchCapacityCost = absClothing.getEnchantmentCapacityCost();
			try(var div = body.div("container-full-width titular")) {
				String limitLabel = Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName());
				div.ui().text(
						!absClothing.isEnchantmentKnown() ? limitLabel + " cost: ?"
								: enchCapacityCost == 0 ? limitLabel + " cost: [style.boldDisabled(" + enchCapacityCost + ")]"
										: "[style.colourEnchantment(" + limitLabel + " cost)]: [style.boldBad(" + enchCapacityCost + ")]");
			}
		}

		if(!author.isEmpty()) {
			body.div(d -> d.cls("description").height(52).text(author));
		}

		int specialIncrease = 0;
		if(absClothing.getDisplayName(false).length() > 40) {
			specialIncrease = 26;
		}
		return 400 + (Main.game.isEnchantmentCapacityEnabled() ? 32 : 0) + (yIncrease * LINE_HEIGHT) + specialIncrease;
	}


	private int scarTooltip(UIMarkup body, Scar scar) {
		int yIncrease = 0;
		// Title:
		try(var div = body.div("container-full-width center")) {
			div.ui().element("h5", h -> h.text("No tattoo"));
		}

		// Core info:
		try(var div = body.div("container-half-width titular")) {
			div.ui().text(Util.capitaliseSentence(invSlot.getTattooSlotName()));
		}
		try(var div = body.div("container-half-width titular")) {
			div.ui().span(s -> s.color(scar == null ? PresetColour.TEXT_GREY : PresetColour.SCAR)
					.text(scar == null ? "No scars" : Util.capitaliseSentence(owner.getScarInSlot(invSlot).getName())));
		}

		SizedStack<Covering> lipsticks = owner.getLipstickMarkingsInSlot(invSlot);
		if(lipsticks != null) {
			yIncrease = 24 + (1 + lipsticks.size()) * LINE_HEIGHT;
			try(var div = body.div("container-full-width")) {
				div.ui().center().padding(8)
						.height(16 + (1 + lipsticks.size()) * LINE_HEIGHT)
						.parse(owner, "[npc.NamePos] ")
						.text(invSlot.getNameOfAssociatedPart(owner), invSlot.isPlural() ? " have" : " has", " been marked by:");
				for(int i = lipsticks.size() - 1; i >= 0; i--) {
					div.ui().br().text(Util.capitaliseSentence(lipsticks.get(i).getFullDescription(owner, true)));
				}
			}
		}

		return yIncrease + 88;
	}

	private int tattooTooltip(UIMarkup body, Tattoo tattoo) {
		int yIncrease = 0;
		int specialIncrease = 0;
		int lipstickYIncrease = 0;

		if(tattoo.getWriting() != null && !tattoo.getWriting().getText().isEmpty()) {
			yIncrease++;
		}
		if(tattoo.getCounter() != null && tattoo.getCounter().getType() != TattooCounterType.NONE) {
			yIncrease++;
		}
		int lSize = 0;
		for(ItemEffect e : tattoo.getEffects()) {
			if(e.getPrimaryModifier() == TFModifier.CLOTHING_ATTRIBUTE
					|| e.getPrimaryModifier() == TFModifier.CLOTHING_MAJOR_ATTRIBUTE
					|| e.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BEHAVIOUR
					|| e.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BODY_PART) {
				lSize++;
			} else {
				lSize += 2;
			}
		}
		lSize -= 4;
		if(lSize < 0) {
			lSize = 0;
		}

		// Title:
		try(var div = body.div("container-full-width center")) {
			String caption = Util.capitaliseSentence(tattoo.getDisplayName(true));
			div.ui().element("h5", h -> h.text(caption));
		}

		// Core info:
		try(var div = body.div("container-half-width titular")) {
			div.ui().text(invSlot.getTattooSlotName() == null
					? "[style.colourDisabled(Cannot be tattooed)]"
					: Util.capitaliseSentence(invSlot.getTattooSlotName()));
		}
		if(owner != null) {
			try(var div = body.div("container-half-width titular")) {
				var scar = owner.getScarInSlot(invSlot);
				div.ui().span(s -> s.color(scar == null ? PresetColour.TEXT_GREY : PresetColour.SCAR)
						.text(scar == null ? "No scars" : Util.capitaliseSentence(owner.getScarInSlot(invSlot).getName())));
			}
		}
		// Attribute modifiers:
		try(var divFullWidth = body.div("container-full-width")) {
			try(var divHalfWidth = divFullWidth.ui().div("container-half-width titular")) {
				divHalfWidth.ui().style("width:calc(66.6%-16px);");
				if(tattoo.getEffects().isEmpty()) {
					divHalfWidth.text("[style.colourDisabled(No bonuses)]");
				} else {
					var content = new ArrayList<Markup.Printable<UIMarkup>>();
					for(ItemEffect e : tattoo.getEffects()) {
						if(e.getPrimaryModifier() != TFModifier.CLOTHING_ATTRIBUTE && e.getPrimaryModifier() != TFModifier.CLOTHING_MAJOR_ATTRIBUTE) {
							for(String s : e.getEffectsDescription(owner, owner)) {
								content.add(UIMarkup::br);
								content.add(x -> x.text(s));
							}
						}
					}
					for(Entry<AbstractAttribute, Integer> entry : tattoo.getAttributeModifiers().entrySet()) {
						String value = entry.getKey().getFormattedValue(entry.getValue());
						content.add(UIMarkup::br);
						content.add(x -> x.bold(b -> b.text(value)));
					}
					//skip first <br>
					content.subList(1, content.size()).forEach(divHalfWidth.ui()::print);
				}
			}

			// Picture:
			try(var divItemImage = divFullWidth.ui().div("item-image")) {
				try(var divContent = divItemImage.ui().div("item-image-content")) {
					divContent.text(tattoo.getSVGImage(
							equippedToCharacter == null
									? Main.game.getPlayer()
									: equippedToCharacter));
				}
			}
		}

		try(var divFullWidth = body.div("container-full-width")) {
			divFullWidth.ui().padding(8).height(106).text(tattoo.getType().getDescription());
			if(tattoo.getWriting() != null && !tattoo.getWriting().getText().isEmpty()) {
				divFullWidth.ui().br();
				if(tattoo.getWriting().getStyles().isEmpty()) {
					divFullWidth.ui().text("Normal,");
				} else {
					int i = 0;
					for(TattooWritingStyle style : tattoo.getWriting().getStyles()) {
						divFullWidth.ui().text(i == 0 ? Util.capitaliseSentence(style.getName()) : ", " + style.getName());
						i++;
					}
				}
				divFullWidth.ui().text(" ", tattoo.getWriting().getColour().getName(),
						" writing forms part of the tattoo.");
			}

			if(tattoo.getCounter() != null && tattoo.getCounter().getType() != TattooCounterType.NONE) {
				divFullWidth.ui().br().text("An enchanted, ", tattoo.getCounter().getColour().getName(), " ",
						tattoo.getCounter().getType().getName(), " counter has been applied to the tattoo.");
			}

		}

		try(var divFullWidth = body.div("container-full-width")) {
			boolean hasWriting = tattoo.getWriting() != null && !tattoo.getWriting().getText().isEmpty();
			divFullWidth.ui().padding(4).height(hasWriting ? 42 : 28).center();
			if(hasWriting)
				divFullWidth.ui().text("The writing reads:").br().text(tattoo.getFormattedWritingOutput());
			else
				divFullWidth.ui().text("[style.colourDisabled(This tattoo doesn't have any writing.)]");
		}

		try(var divFullWidth = body.div("container-full-width")) {
			boolean hasCounter = tattoo.getCounter() != null && tattoo.getCounter().getType() != TattooCounterType.NONE;
			divFullWidth.ui().padding(hasCounter ? 4 : 8).height(hasCounter ? 42 : 28).center();
			if(hasCounter) {
				String counterOutput = tattoo.getFormattedCounterOutput(
						equippedToCharacter == null ? Main.game.getPlayer() : equippedToCharacter);
				divFullWidth.ui().text("The '", tattoo.getCounter().getType().getName(), "' counter displays:").br()
						.span(s -> s.color(tattoo.getCounter().getColour()).text(counterOutput));
			} else
				divFullWidth.ui().text("[style.colourDisabled(This tattoo doesn't have a counter.)]");
		}

		if(Main.game.isEnchantmentCapacityEnabled()) {
			int enchCapacityCost = tattoo.getEnchantmentCapacityCost();
			try(var divFullWidth = body.div("container-full-width titular")) {
				divFullWidth.text(enchCapacityCost == 0 ? "" : "[style.colourEnchantment(",
						Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()),
						enchCapacityCost == 0 ? " cost: [style.boldDisabled(" : " cost)]: [style.boldBad(",
						enchCapacityCost, ")]");
			}
		}

		//FIXME where did this belong to?
		//tooltipSB.append("</div>");

		if(owner != null) {
			SizedStack<Covering> lipsticks = owner.getLipstickMarkingsInSlot(invSlot);
			if(lipsticks != null) {
				lipstickYIncrease = 24 + (1 + lipsticks.size()) * LINE_HEIGHT;
				try(var divFullWidth = body.div("container-full-width")) {
					divFullWidth.ui().center().padding(8).height(16 + (1 + lipsticks.size()) * LINE_HEIGHT)
							.parse(owner, "[npc.NamePos] ")
							.text(invSlot.getNameOfAssociatedPart(owner), invSlot.isPlural() ? " have" : " has", " been marked by:");
					for(int i = lipsticks.size() - 1; i >= 0; i--) {
						divFullWidth.ui().br()
								.text(Util.capitaliseSentence(lipsticks.get(i).getFullDescription(owner, true)));
					}
				}
			}
		}

		if(tattoo.getDisplayName(false).length() > 40) {
			specialIncrease = 26;
		}
		return 404 + (Main.game.isEnchantmentCapacityEnabled() ? 32 : 0) + (yIncrease * LINE_HEIGHT) + lipstickYIncrease + specialIncrease;
	}

	private int getBuybackPriceFor(AbstractCoreItem item) {
		for(ShopTransaction s : Main.game.getPlayer().getBuybackStack()) {
			if(s.getAbstractItemSold() == item) {
				return s.getPrice();
			}
		}
		throw new IllegalArgumentException("That's not a buyback item");
	}

	private String getTooltipText(GameCharacter character, String playerText, String NPCText) {
		if(character.isPlayer()) {
			return playerText;
		} else {
			return UtilText.parse(character, NPCText);
		}
	}

	private int setUnarmedWeaponSlotTooltip(UIMarkup body, InventorySlot slot, String title) {
		BodyPartClothingBlock block = slot.getBodyPartClothingBlock(equippedToCharacter);
		int baseDamage = equippedToCharacter.getBaseUnarmedDamage();
		int modifiedDamage = equippedToCharacter.getUnarmedDamage();
		String description = UtilText.parse(equippedToCharacter,
				"[npc.Name] [npc.has] a base unarmed damage value of " + baseDamage
						+ ", which is modified from attributes to deal:"
						+ "<br/>[style.boldUnarmed(" + modifiedDamage + " Unarmed damage)]");
		body.div(d -> d.cls("title").text(title, " (Unarmed)"))
				.div(d -> d.cls("description").height(64).center().text(description));
		if(block != null) {
			String blockDescription = UtilText.parse(equippedToCharacter, block.getDescription());
			body.div(d -> d.cls("title").color(PresetColour.GENERIC_MINOR_BAD).text("Restricted!"))
					.div(d -> d.cls("description").height(72).center().text(blockDescription));
		}
		return 132 + (block != null ? 128 : 0);
	}
}
