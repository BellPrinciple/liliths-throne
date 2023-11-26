package com.lilithsthrone.game.inventory;

import com.lilithsthrone.game.character.attributes.AbstractAttribute;
import com.lilithsthrone.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.utils.XMLSaving;
import com.lilithsthrone.utils.colours.Colour;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CoreItem extends XMLSaving {

	AbstractCoreType getType();

	// Enchantments:

	boolean isAbleToBeEnchanted();

	default int getEnchantmentLimit() {
		return 100;
	}

	default AbstractItemEffectType getEnchantmentEffect() {
		return null;
	}

	default AbstractCoreType getEnchantmentItemType(List<ItemEffect> effects) {
		return null;
	}

	default AbstractCoreItem enchant(TFModifier primaryModifier, TFModifier secondaryModifier) {
		return (AbstractCoreItem) this;
	}

	// Other:

	String getName();

	void setName(String name);

	String getNamePlural();

	String getDisplayName(boolean withRarityColour);

	String getDisplayNamePlural(boolean withRarityColour);

	String getSVGString();

	void setSVGString(String SVGString);

	String getDescription();

	int getValue();

	int getPrice(float modifier);

	void setRarity(Rarity rarity);

	Rarity getRarity();

	Colour getColour(int index);

	List<Colour> getColours();

	void setColours(List<Colour> colours);

	void setColour(int index, Colour colour);

	default Map<AbstractAttribute, Integer> getAttributeModifiers() {
		return Map.of();
	}

	void setAttributeModifiers(Map<AbstractAttribute, Integer> attributeModifiers);

	default List<ItemEffect> getEffects() {
		return List.of();
	}

	default Set<ItemTag> getItemTags() {
		return Set.of();
	}
}
