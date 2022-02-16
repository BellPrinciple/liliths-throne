package com.lilithsthrone.utils.comparators;

import java.util.Comparator;

import com.lilithsthrone.game.inventory.weapon.WeaponType;

/**
 * Compares by rarity.
 * 
 * @since 0.1.2
 * @version 0.3.2
 * @author Innoxia
 */
public class WeaponTypeRarityComparator implements Comparator<WeaponType> {
	@Override
	public int compare(WeaponType first, WeaponType second) {
		int result = first.getRarity().compareTo(second.getRarity());
		if (result != 0) {
			return result;
		} else {
			return (int) (first.getDamage() - second.getDamage());
		}
	}
}
