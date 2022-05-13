package com.lilithsthrone.game.inventory.clothing;

import java.util.List;
import java.util.function.Function;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.ItemTag;

import static java.util.Objects.requireNonNull;

/**
 * @since 0.3.1
 * @version 0.3.9.1
 * @author Innoxia
 */
public class BodyPartClothingBlock {

	private final List<InventorySlot> blockedSlots;
	private final Race race;
	private final Function<GameCharacter,String> description;
	private final List<ItemTag> requiredTags;
	
	public BodyPartClothingBlock(List<InventorySlot> blockedSlots, Race race, Function<GameCharacter,String> description, List<ItemTag> requiredTags) {
		this.blockedSlots = requireNonNull(blockedSlots);
		this.race = race;
		this.description = requireNonNull(description);
		this.requiredTags = requireNonNull(requiredTags);
	}

	public List<InventorySlot> getBlockedSlots() {
		return blockedSlots;
	}

	public Race getRace() {
		return race;
	}

	public String getDescription(GameCharacter character) {
		return description.apply(character);
	}

	public List<ItemTag> getRequiredTags() {
		return requiredTags;
	}
	
	
}
