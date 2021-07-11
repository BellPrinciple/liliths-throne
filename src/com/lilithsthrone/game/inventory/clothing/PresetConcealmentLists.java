package com.lilithsthrone.game.inventory.clothing;

import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.2.7
 * @version 0.2.7
 * @author Innoxia
 */
public enum PresetConcealmentLists {

	NONE(new ArrayList<>()),
	
	CONCEALED_PARTIAL_TORSO(List.of(InventorySlot.STOMACH,
			InventorySlot.CHEST,
			InventorySlot.NIPPLE,
			InventorySlot.PIERCING_NIPPLE,
			InventorySlot.PIERCING_STOMACH)),

	CONCEALED_PARTIAL_TORSO_STOMACH_VISIBLE(List.of(InventorySlot.CHEST,
			InventorySlot.NIPPLE,
			InventorySlot.PIERCING_NIPPLE)),

	CONCEALED_STOMACH(List.of(InventorySlot.PIERCING_STOMACH)),

	CONCEALED_BREASTS(List.of(InventorySlot.NIPPLE,
			InventorySlot.PIERCING_NIPPLE)),

	CONCEALED_FULL_TORSO(List.of(InventorySlot.TORSO_UNDER,
			InventorySlot.STOMACH,
			InventorySlot.CHEST,
			InventorySlot.NIPPLE,
			InventorySlot.PIERCING_NIPPLE,
			InventorySlot.PIERCING_STOMACH)),

	CONCEALED_GENITALS(List.of(InventorySlot.VAGINA,
			InventorySlot.ANUS,
			InventorySlot.PENIS,
			InventorySlot.PIERCING_PENIS,
			InventorySlot.PIERCING_VAGINA)),

	CONCEALED_GROIN(List.of(InventorySlot.GROIN,
			InventorySlot.VAGINA,
			InventorySlot.ANUS,
			InventorySlot.PENIS,
			InventorySlot.PIERCING_PENIS,
			InventorySlot.PIERCING_VAGINA)),

	CONCEALED_TAUR(List.of(InventorySlot.STOMACH,
			InventorySlot.PIERCING_STOMACH,
			InventorySlot.GROIN,
			InventorySlot.VAGINA,
			InventorySlot.ANUS,
			InventorySlot.PENIS,
			InventorySlot.PIERCING_PENIS,
			InventorySlot.PIERCING_VAGINA)),
	
	CONCEALED_DRESS_FRONT_FULL(List.of(InventorySlot.STOMACH,
			InventorySlot.CHEST,
			InventorySlot.NIPPLE,
			InventorySlot.PIERCING_NIPPLE,
			InventorySlot.PIERCING_STOMACH,
			InventorySlot.GROIN,
			InventorySlot.VAGINA,
			InventorySlot.PENIS,
			InventorySlot.PIERCING_PENIS,
			InventorySlot.PIERCING_VAGINA)),

	CONCEALED_UNZIPS_GROIN(List.of(InventorySlot.GROIN,
			InventorySlot.PENIS,
			InventorySlot.PIERCING_PENIS)),

	CONCEALED_ANKLES_FROM_TROUSERS(List.of(InventorySlot.ANKLE,
			InventorySlot.SOCK));
	
	private List<InventorySlot> presetInventorySlotList;
	
	public List<InventorySlot> getPresetInventorySlotList() {
		return presetInventorySlotList;
	}

	private PresetConcealmentLists(List<InventorySlot> presetInventorySlotList) {
		this.presetInventorySlotList = presetInventorySlotList;
	}

	
	
}
