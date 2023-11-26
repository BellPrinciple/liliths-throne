package com.lilithsthrone.game.inventory;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.item.AbstractItemType;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.game.inventory.item.SvgInformation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface Item extends CoreItem {

	AbstractItemType getItemType();

	@Override
	default AbstractItemType getType() {
		return getItemType();
	}

	default boolean isBreakOutOfInventory() {
		return getEffects().stream().anyMatch(effect -> effect.getItemEffectType().isBreakOutOfInventory());
	}

	void setItemEffects(List<ItemEffect> itemEffects);

	String applyEffect(GameCharacter user, GameCharacter target);

	// Enchantments:

	@Override
	default int getEnchantmentLimit() {
		return getItemType().getEnchantmentLimit();
	}

	@Override
	default AbstractItemEffectType getEnchantmentEffect() {
		return getItemType().getEnchantmentEffect();
	}

	@Override
	default AbstractCoreType getEnchantmentItemType(List<ItemEffect> effects) {
		return getItemType().getEnchantmentItemType(effects);
	}

	// Getters & setters:

	String getName(boolean withDeterminer, boolean withRarityColour);

	default boolean isAppendItemEffectLinesToTooltip() {
		return getItemType().isAppendItemEffectLinesToTooltip();
	}

	default List<String> getEffectTooltipLines() {
		return getItemType().getEffectTooltipLines();
	}

	String getExtraDescription(GameCharacter user, GameCharacter target);

	/**
	 * @param characterOwner The character who owns this item.
	 * @return A List of Strings describing extra features of this ItemType.
	 */
	default List<String> getExtraDescriptions(GameCharacter characterOwner) {
		return getItemType().getItemTags().stream()
				.flatMap(itemTag -> itemTag.getClothingTooltipAdditions().stream())
				.toList();
	}

	default List<SvgInformation> getPathNameInformation() {
		return getItemType().getPathNameInformation();
	}

	default boolean isConsumedOnUse() {
		return getItemType().isConsumedOnUse();
	}

	default String getUseDescription(GameCharacter user, GameCharacter target) {
		return getItemType().getUseDescription(user, target);
	}

	default boolean isAbleToBeUsedFromInventory() {
		return getItemType().isAbleToBeUsedFromInventory();
	}

	default String getUnableToBeUsedFromInventoryDescription() {
		return getItemType().getUnableToBeUsedFromInventoryDescription();
	}

	default boolean isAbleToBeUsed(GameCharacter target) {
		return getItemType().isAbleToBeUsed(target);
	}

	default String getUnableToBeUsedDescription(GameCharacter target) {
		return getItemType().getUnableToBeUsedDescription(target);
	}

	default boolean isAbleToBeUsedInCombatAllies() {
		return !this.isBreakOutOfInventory() && getItemType().isAbleToBeUsedInCombatAllies();
	}

	default boolean isAbleToBeUsedInCombatEnemies() {
		return !this.isBreakOutOfInventory() && getItemType().isAbleToBeUsedInCombatEnemies();
	}

	default boolean isAbleToBeUsedInSex() {
		return !this.isBreakOutOfInventory() && getItemType().isAbleToBeUsedInSex();
	}

	default boolean isGift() {
		return getItemType().isGift();
	}

	default boolean isTypeOneOf(String ... itemType) {
		return Stream.of(itemType).anyMatch(it -> (getItemType().equals(ItemType.getItemTypeFromId(it))));
	}

	@Override
	default Set<ItemTag> getItemTags() {
		return new HashSet<>(getItemType().getItemTags());
	}
}
