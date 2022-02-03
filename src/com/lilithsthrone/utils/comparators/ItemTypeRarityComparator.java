package com.lilithsthrone.utils.comparators;

import java.util.Comparator;

import com.lilithsthrone.game.inventory.item.ItemType;

/**
 * Compares by rarity.
 * 
 * @since 0.1.2
 * @version 0.3.2
 * @author Innoxia
 */
public class ItemTypeRarityComparator implements Comparator<ItemType> {
	@Override
	public int compare(ItemType first, ItemType second) {
		int result = first.getRarity().compareTo(second.getRarity());
		if (result != 0) {
			return result;
		} else {
			return first.getValue() - second.getValue();
		}
	}
}
