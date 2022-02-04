package com.lilithsthrone.game.inventory;

import java.util.List;

/**
 * The game's encyclopedia keeps track which instances the player has encountered at least once.
 * @since 0.1.0
 * @version 0.3.7.7
 * @author Innoxia
 */
public interface AbstractCoreType {

	/**
	 * @return
	 * Roughly classifies the chance of finding this item in the game.
	 */
	default Rarity getRarity() {
		return Rarity.COMMON;
	}

	/**
	 * @return
	 * A List of other AbstractItemTypes which should be added to the player's Encyclopedia when this one is discovered.
	 * Used for unique items in situations where acquiring one would make it impossible to ever see other ones.
	 */
	default List<AbstractCoreType> getAdditionalDiscoveryTypes() {
		return List.of();
	}
}
