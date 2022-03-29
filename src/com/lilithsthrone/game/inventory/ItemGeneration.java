package com.lilithsthrone.game.inventory;

import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.FluidCum;
import com.lilithsthrone.game.character.body.FluidMilk;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.dialogue.eventLog.EventLogEntryEncyclopediaUnlock;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.game.inventory.enchanting.TFPotency;
import com.lilithsthrone.game.inventory.item.AbstractFilledBreastPump;
import com.lilithsthrone.game.inventory.item.AbstractFilledCondom;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.game.inventory.weapon.AbstractWeapon;
import com.lilithsthrone.game.inventory.weapon.WeaponType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;


/**
 * @since 0.3.9
 * @version 0.4
 * @author Innoxia
 */
public class ItemGeneration {
	
	// Item generation:

	public AbstractItem generateItem(String id) {
		return new AbstractItem(ItemType.getItemTypeFromId(id)) {};
	}
	
	public AbstractItem generateItem(ItemType itemType) {
		return new AbstractItem(itemType) {};
	}
	
	public AbstractItem generateFilledCondom(ItemType filledCondomType, Colour colour, GameCharacter character, FluidCum cum, int millilitres) {
		return new AbstractFilledCondom(filledCondomType, colour, character, cum, millilitres) {};
	}

	public AbstractItem generateFilledBreastPump(Colour colour, GameCharacter character, FluidMilk milk, int quantity) {
		return new AbstractFilledBreastPump(ItemType.MOO_MILKER_FULL, colour, character, milk, quantity) {};
	}
	
	
	
	// Weapon generation:
	
	public AbstractWeapon generateWeapon(String id) {
		return generateWeapon(WeaponType.getWeaponTypeFromId(id));
	}

	public AbstractWeapon generateWeapon(WeaponType wt) {
		return this.generateWeapon(wt, wt.getAvailableDamageTypes().get(Util.random.nextInt(wt.getAvailableDamageTypes().size())));
	}
	
	public AbstractWeapon generateWeapon(WeaponType wt, DamageType dt) {
		return generateWeapon(wt, dt, null);
	}
	
	public AbstractWeapon generateWeapon(String id, DamageType dt) {
		return generateWeapon(WeaponType.getWeaponTypeFromId(id), dt, null);
	}
	
	public AbstractWeapon generateWeapon(String id, DamageType dt, List<Colour> colours) {
		return generateWeapon(WeaponType.getWeaponTypeFromId(id), dt, colours);
	}
	
	public AbstractWeapon generateWeapon(WeaponType wt, DamageType dt, List<Colour> colours) {
		if(colours==null) {
			colours = new ArrayList<>();
			
		} else {
			colours = new ArrayList<>(colours);
		}
		
		int index = 0;
		ColourReplacement cr = wt.getColourReplacement(false, index);
		while(cr!=null) {
			if(colours.size()<=index || !cr.getAllColours().contains(colours.get(index))) {
				colours.add(cr.getRandomOfDefaultColours());
			}
			index++;
			cr = wt.getColourReplacement(false, index);
		}

		wt.modifyColours(colours);

		return new AbstractWeapon(wt, dt, colours) {
			@Override
			public String onEquip(GameCharacter character) {
				if (character.isPlayer()) {
					if (Main.getProperties().addWeaponDiscovered(wt)) {
						Main.game.addEvent(new EventLogEntryEncyclopediaUnlock(wt.getName(), wt.getRarity().getColour()), true);
					}
				}
				return wt.equipText(character);
			}

			@Override
			public String onUnequip(GameCharacter character) {
				return wt.unequipText(character);
			}
		};
	}
	
	public AbstractWeapon generateWeapon(AbstractWeapon weapon) {
		return new AbstractWeapon(weapon) {
			@Override
			public String onEquip(GameCharacter character) {
				if (character.isPlayer()) {
					if (Main.getProperties().addWeaponDiscovered(weapon.getWeaponType())) {
						Main.game.addEvent(new EventLogEntryEncyclopediaUnlock(weapon.getWeaponType().getName(), weapon.getWeaponType().getRarity().getColour()), true);
					}
				}
				return weapon.getWeaponType().equipText(character);
			}

			@Override
			public String onUnequip(GameCharacter character) {
				return weapon.getWeaponType().unequipText(character);
			}
		};
	}
	
	
	// Clothing generation:
	
	public AbstractClothing generateClothing(String clothingTypeId, Colour primaryColour, Colour secondaryColour, Colour tertiaryColour, boolean allowRandomEnchantment) {
		return this.generateClothing(ClothingType.getClothingTypeFromId(clothingTypeId), primaryColour, secondaryColour, tertiaryColour, allowRandomEnchantment);
	}
	
	public AbstractClothing generateClothing(ClothingType clothingType, Colour primaryColour, Colour secondaryColour, Colour tertiaryColour, boolean allowRandomEnchantment) {
		List<Colour> colours = Util.newArrayListOfValues(primaryColour, secondaryColour, tertiaryColour);
		
		int index = 0;
		ColourReplacement cr = clothingType.getColourReplacement(index);
		while(cr!=null) {
			if(colours.size()<=index || !cr.getAllColours().contains(colours.get(index))) {
				colours.add(cr.getRandomOfDefaultColours());
			}
			index++;
			cr = clothingType.getColourReplacement(index);
		}
		
		clothingType.modifyColours(colours);
		
		return new AbstractClothing(clothingType, colours, allowRandomEnchantment) {};
	}

	public AbstractClothing generateClothing(ClothingType clothingType, Colour colourShade, boolean allowRandomEnchantment) {
		return this.generateClothing(clothingType, colourShade, null, null, allowRandomEnchantment);
	}

	public AbstractClothing generateClothing(String clothingTypeId, Colour colourShade, boolean allowRandomEnchantment) {
		return this.generateClothing(ClothingType.getClothingTypeFromId(clothingTypeId), colourShade, null, null, allowRandomEnchantment);
	}

	/** Uses random colour.*/
	public AbstractClothing generateClothing(ClothingType clothingType) {
		return this.generateClothing(clothingType, null, true);
	}

	/** Allows random enchantment. Uses random colour.*/
	public AbstractClothing generateClothing(ClothingType clothingType, boolean allowRandomEnchantment) {
		return this.generateClothing(clothingType, null, allowRandomEnchantment);
	}

	/** Allows random enchantment. Uses random colour.*/
	public AbstractClothing generateClothing(String clothingTypeId, boolean allowRandomEnchantment) {
		var type = ClothingType.getClothingTypeFromId(clothingTypeId);
		return this.generateClothing(type, null, allowRandomEnchantment);
	}

	/** Allows random enchantment. Uses random colour. Restricted by slotHint.*/
	public AbstractClothing generateClothing(String clothingTypeId, boolean allowRandomEnchantment, String slotHint) {
		var type = ClothingType.getClothingTypeFromId(clothingTypeId, slotHint);
		return this.generateClothing(type, null, allowRandomEnchantment);
	}

	/** Allows random enchantment. Uses random colour. Restricted by slot.*/
	public AbstractClothing generateClothing(String clothingTypeId, boolean allowRandomEnchantment, InventorySlot slot) {
		var type = ClothingType.getClothingTypeFromId(clothingTypeId, slot.toString());
		return this.generateClothing(type, null, allowRandomEnchantment);
	}

	public AbstractClothing generateClothing(ClothingType clothingType, List<Colour> colours, List<ItemEffect> effects) {
		if(colours==null) {
			colours = new ArrayList<>();
			
		} else {
			colours = new ArrayList<>(colours);
		}
		
		int index = 0;
		ColourReplacement cr = clothingType.getColourReplacement(index);
		while(cr!=null) {
			if(colours.size()<=index || !cr.getAllColours().contains(colours.get(index))) {
				colours.add(cr.getRandomOfDefaultColours());
			}
			index++;
			cr = clothingType.getColourReplacement(index);
		}

		clothingType.modifyColours(colours);

		return new AbstractClothing(clothingType, colours, effects) {};
	}
	
	/**
	 * Generates clothing with the provided enchantments.
	 */
	public AbstractClothing generateClothing(String clothingTypeId, Colour primaryColour, Colour secondaryColour, Colour tertiaryColour, List<ItemEffect> effects) {
		return generateClothing(ClothingType.getClothingTypeFromId(clothingTypeId), Util.newArrayListOfValues(primaryColour, secondaryColour, tertiaryColour), effects);
	}
	
	/**
	 * Generates clothing with the provided enchantments.
	 */
	public AbstractClothing generateClothing(ClothingType clothingType, Colour primaryColour, Colour secondaryColour, Colour tertiaryColour, List<ItemEffect> effects) {
		return generateClothing(clothingType, Util.newArrayListOfValues(primaryColour, secondaryColour, tertiaryColour), effects);
	}
	
	/**
	 * Generates clothing with the provided enchantments.
	 */
	public AbstractClothing generateClothing(ClothingType clothingType, Colour colour, List<ItemEffect> effects) {
		return generateClothing(clothingType, colour, null, null, effects);
	}
	
	public AbstractClothing generateClothing(String clothingTypeId, Colour colour, List<ItemEffect> effects) {
		return generateClothing(ClothingType.getClothingTypeFromId(clothingTypeId), colour, null, null, effects);
	}
	
	/**
	 * Uses random colour.
	 */
	public AbstractClothing generateClothing(ClothingType clothingType, List<ItemEffect> effects) {
		List<Colour> colours = new ArrayList<>();
		for(ColourReplacement cr : clothingType.getColourReplacements()) {
			colours.add(cr.getRandomOfDefaultColours());
		}
		return this.generateClothing(clothingType, colours, effects);
	}
	
	/**
	 * Generates clothing with a random enchantment.
	 */
	public AbstractClothing generateClothingWithEnchantment(ClothingType clothingType, Colour colour) {
		List<ItemEffect> effects = new ArrayList<>();

		TFModifier rndMod = TFModifier.getClothingAttributeList().get(Util.random.nextInt(TFModifier.getClothingAttributeList().size()));
		effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, rndMod, TFPotency.getRandomWeightedPositivePotency(), 0));
		
		return generateClothing(clothingType, colour, effects);
	}
	
	/**
	 * Uses random colour.
	 */
	public AbstractClothing generateClothingWithEnchantment(ClothingType clothingType) {
		return this.generateClothingWithEnchantment(clothingType, null);
	}

	public AbstractClothing generateClothingWithNegativeEnchantment(ClothingType clothingType, Colour colour) {
		List<ItemEffect> effects = new ArrayList<>();

		TFModifier rndMod = TFModifier.getClothingAttributeList().get(Util.random.nextInt(TFModifier.getClothingAttributeList().size()));
		effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, rndMod, TFPotency.getRandomWeightedNegativePotency(), 0));
		
		return generateClothing(clothingType, colour, effects);
	}
	
	public AbstractClothing generateClothingWithNegativeEnchantment(ClothingType clothingType) {
		return this.generateClothingWithNegativeEnchantment(clothingType, null);
	}
	
	public AbstractClothing generateRareClothing(ClothingType type) {
		List<ItemEffect> effects = new ArrayList<>();
		
		List<TFModifier> attributeMods = new ArrayList<>(TFModifier.getClothingAttributeList());
		
		TFModifier rndMod = attributeMods.get(Util.random.nextInt(attributeMods.size()));
		attributeMods.remove(rndMod);
		TFModifier rndMod2 = attributeMods.get(Util.random.nextInt(attributeMods.size()));
		
		effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, rndMod, TFPotency.MAJOR_BOOST, 0));
		effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, rndMod2, TFPotency.MAJOR_BOOST, 0));
		
		return this.generateClothing(type, effects);
	}
}
