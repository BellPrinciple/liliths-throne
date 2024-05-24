package com.lilithsthrone.game.inventory;

import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.utils.XMLSaving;
import com.lilithsthrone.utils.colours.Colour;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CoreItem extends XMLSaving {

	// Name:

	String getName();
	void setName(String name);
	String getNamePlural();

	// UI:

	String getDisplayName(boolean withRarityColour);
	String getDisplayNamePlural(boolean withRarityColour);
	String getSVGString();
	void setSVGString(String SVGString);
	String getDescription();
	Colour getColour(int index);
	List<Colour> getColours();
	void setColours(List<Colour> colours);
	void setColour(int index, Colour colour);

	// Enchantments:

	boolean isAbleToBeEnchanted();
	int getEnchantmentLimit();
	ItemEffectType getEnchantmentEffect();
	AbstractCoreType getEnchantmentItemType(List<ItemEffect> effects);

	// Other:

	int getValue();
	int getPrice(float modifier);
	void setRarity(Rarity rarity);
	Rarity getRarity();
	Map<Attribute, Integer> getAttributeModifiers();
	void setAttributeModifiers(Map<Attribute, Integer> attributes);
	List<ItemEffect> getEffects();
	Set<ItemTag> getItemTags();
}
