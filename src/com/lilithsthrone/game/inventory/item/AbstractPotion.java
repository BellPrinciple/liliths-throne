package com.lilithsthrone.game.inventory.item;

import java.util.List;

import com.lilithsthrone.game.inventory.enchanting.PossibleItemEffect;

/**
 * Container class for potions
 *
 * @since 0.3.6.4
 * @version 0.3.6.4
 * @author Stadler76
 */
public abstract class AbstractPotion {
	private final ItemType itemType;
	private final List<PossibleItemEffect> effects;

	public AbstractPotion(ItemType itemType, List<PossibleItemEffect> effects) {
		this.itemType = itemType;
		this.effects = effects;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public List<PossibleItemEffect> getEffects() {
		return effects;
	}
}
