package com.lilithsthrone.game.character.body;

import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.3.1
 * @author Innoxia
 */
public enum CoverableArea {
	
	// Utility value
	NONE(false,
			"none",
			new ArrayList<>()),

	HANDS(false,
			"hands",
			List.of(InventorySlot.FINGER,
					InventorySlot.HAND,
					InventorySlot.WRIST)),
	
	ASS(true,
			"ass",
			List.of(InventorySlot.GROIN,
					InventorySlot.ANUS,
					InventorySlot.LEG,
					InventorySlot.TAIL)),
	
	ANUS(true,
			"asshole",
			List.of(InventorySlot.GROIN,
					InventorySlot.ANUS)),

	STOMACH(false,
			"stomach",
			List.of(InventorySlot.STOMACH,
					InventorySlot.TORSO_UNDER,
					InventorySlot.TORSO_OVER)),
	
	BACK(false,
			"back",
			List.of(InventorySlot.TORSO_UNDER,
					InventorySlot.TORSO_OVER,
					InventorySlot.WINGS,
					InventorySlot.TAIL)),
	
	LEGS(false,
			"legs",
			List.of(InventorySlot.LEG,
					InventorySlot.SOCK)) {
//		public boolean isPhysicallyAvailable(GameCharacter owner) {
//			return owner.hasLegs();
//		}
	},
	
	FEET(false,
			"feet",
			List.of(InventorySlot.FOOT,
					InventorySlot.ANKLE,
					InventorySlot.SOCK)) {
		public boolean isPhysicallyAvailable(GameCharacter owner) {
			return owner.hasLegs();
		}
	},
	
	THIGHS(false,
			"thighs",
			List.of(InventorySlot.LEG,
					InventorySlot.GROIN)) {
		public boolean isPhysicallyAvailable(GameCharacter owner) {
			return owner.hasLegs();
		}
	},
	
	ARMPITS(false,
			"armpits",
			List.of(InventorySlot.TORSO_UNDER,
					InventorySlot.TORSO_OVER)) {
//		public boolean isPhysicallyAvailable(GameCharacter owner) {
//			return owner.hasArms();
//		}
	},
	
	TAIL(false,
			"tail",
			List.of(InventorySlot.TAIL)) {
		public boolean isPhysicallyAvailable(GameCharacter owner) {
			return owner.hasTail();
		}
	},
	
	VAGINA(true,
			"pussy",
			List.of(InventorySlot.VAGINA,
					InventorySlot.GROIN,
					InventorySlot.LEG)) {
		public boolean isPhysicallyAvailable(GameCharacter owner) {
			return owner.hasVagina();
		}
	},
	
	MOUND(true,
			"mound",
			List.of(InventorySlot.GROIN,
					InventorySlot.LEG)) {
		public boolean isPhysicallyAvailable(GameCharacter owner) {
			return !owner.hasVagina() && !owner.hasPenis();
		}
	},
	
	PENIS(true,
			"cock",
			List.of(InventorySlot.PENIS,
					InventorySlot.GROIN,
					InventorySlot.LEG)) {
		public boolean isPhysicallyAvailable(GameCharacter owner) {
			return owner.hasPenis();
		}
	},
	
	TESTICLES(true,
			"balls",
			List.of(InventorySlot.GROIN,
					InventorySlot.LEG)) {
		public boolean isPhysicallyAvailable(GameCharacter owner) {
			return owner.hasPenis();
		}
	},
	
	BREASTS(true,
			"breasts",
			List.of(InventorySlot.CHEST,
					InventorySlot.NIPPLE,
					InventorySlot.TORSO_UNDER,
					InventorySlot.TORSO_OVER)),
	
	NIPPLES(true,
			"nipples",
			List.of(InventorySlot.CHEST,
					InventorySlot.NIPPLE,
					InventorySlot.TORSO_UNDER,
					InventorySlot.TORSO_OVER)),
	
	BREASTS_CROTCH(true,
			"crotch-breasts",
			List.of(InventorySlot.STOMACH,
					InventorySlot.GROIN,
					InventorySlot.TORSO_UNDER,
					InventorySlot.TORSO_OVER)) {
		public boolean isPhysicallyAvailable(GameCharacter owner) {
			return owner.hasBreastsCrotch();
		}
		public List<InventorySlot> getAssociatedInventorySlots(GameCharacter owner) {
			if(owner.getLegConfiguration().isBipedalPositionedCrotchBoobs()) {
				return super.getAssociatedInventorySlots(owner);
			} else {
				return List.of(InventorySlot.STOMACH,
						InventorySlot.GROIN,
						InventorySlot.LEG);
			}
		}
	},
	
	NIPPLES_CROTCH(true,
			"crotch-nipples",
			null) {
		public boolean isPhysicallyAvailable(GameCharacter owner) {
			return owner.hasBreastsCrotch();
		}
		public List<InventorySlot> getAssociatedInventorySlots(GameCharacter owner) {
			return BREASTS_CROTCH.getAssociatedInventorySlots(owner);
		}
	},

	HAIR(false,
			"hair",
			List.of(InventorySlot.HAIR,
					InventorySlot.HORNS,
					InventorySlot.HEAD)),
	
	MOUTH(true,
			"mouth",
			List.of(InventorySlot.HAIR,
					InventorySlot.HORNS,
					InventorySlot.HEAD,
					InventorySlot.EYES,
					InventorySlot.MOUTH,
					InventorySlot.NECK));

	
	private boolean saveDiscoveredStatus;
	private String name;
	private List<InventorySlot> associatedInventorySlots;

	private CoverableArea(boolean saveDiscoveredStatus, String name, List<InventorySlot> associatedInventorySlots) {
		setSaveDiscoveredStatus(saveDiscoveredStatus);
		this.name = name;
		this.associatedInventorySlots = associatedInventorySlots;
	}

	public boolean isSaveDiscoveredStatus() {
		return saveDiscoveredStatus;
	}

	public void setSaveDiscoveredStatus(boolean saveDiscoveredStatus) {
		this.saveDiscoveredStatus = saveDiscoveredStatus;
	}

	public String getName() {
		return name;
	}
	
	public List<InventorySlot> getAssociatedInventorySlots(GameCharacter owner) {
		return new ArrayList<>(associatedInventorySlots);
	}

	/**
	 * @return true if the owner has the related orifice/penetration type.
	 */
	public boolean isPhysicallyAvailable(GameCharacter owner) {
		return true;
	}
}
