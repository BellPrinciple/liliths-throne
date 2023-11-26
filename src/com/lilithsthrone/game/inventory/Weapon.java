package com.lilithsthrone.game.inventory;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.AbstractAttribute;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.combat.moves.AbstractCombatMove;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.combat.spells.SpellSchool;
import com.lilithsthrone.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.weapon.AbstractWeaponType;
import com.lilithsthrone.utils.Util;

import java.util.List;

public interface Weapon extends CoreItem {

	String getId();

	String onEquip(GameCharacter character);

	String onUnequip(GameCharacter character);

	@Override
	default String getDescription() {
		return getDescription(null);
	}

	String getDescription(GameCharacter character);

	/**
	 * @param characterOwner The character who owns this item.
	 * @return A List of Strings describing extra features of this WeaponType.
	 */
	default List<String> getExtraDescriptions(GameCharacter characterOwner) {
		return getWeaponType().getItemTags().stream()
				.flatMap(itemTag -> itemTag.getClothingTooltipAdditions().stream())
				.toList();
	}

	DamageType getDamageType();

	void setDamageType(DamageType damageType);

	AbstractWeaponType getWeaponType();

	@Override
	default AbstractWeaponType getType() {
		return getWeaponType();
	}

	String getName(boolean withDeterminer, boolean withRarityColour);

	String getSVGEquippedString(GameCharacter owner);

	default List<Spell> getSpells() {
		return List.of();
	}

	default List<AbstractCombatMove> getCombatMoves() {
		return List.of();
	}

	AbstractAttribute getCoreEnchantment();

	SpellSchool getSpellSchool();

	default boolean isAbleToBeUsed(GameCharacter user, GameCharacter target) {
		return getWeaponType().isAbleToBeUsed(user, target);
	}

	default String getUnableToBeUsedDescription() {
		return getWeaponType().getUnableToBeUsedDescription();
	}

	default String applyExtraEffects(GameCharacter user, GameCharacter target, boolean isHit, boolean isCritical) {
		return getWeaponType().applyExtraEffects(user, target, isHit, isCritical).trim();
	}

	void setEffects(List<ItemEffect> effects);

	void addEffect(ItemEffect effect);

	/**
	 * @return An integer value of the 'enchantment capacity cost' for this particular weapon. Does not count negative attribute values, and values of Corruption are reversed (so reducing corruption costs enchantment stability).
	 */
	int getEnchantmentCapacityCost();

	@Override
	default int getEnchantmentLimit() {
		return getWeaponType().getEnchantmentLimit();
	}

	@Override
	default AbstractItemEffectType getEnchantmentEffect() {
		return getWeaponType().getEnchantmentEffect();
	}

	@Override
	default AbstractCoreType getEnchantmentItemType(List<ItemEffect> effects) {
		return getWeaponType().getEnchantmentItemType(effects);
	}

	boolean isCanBeEquipped(GameCharacter clothingOwner, InventorySlot slot);

	String getCannotBeEquippedText(GameCharacter clothingOwner, InventorySlot slot);

	Util.Value<Boolean, String> isAbleToBeEquipped(GameCharacter clothingOwner, InventorySlot slot);
}
