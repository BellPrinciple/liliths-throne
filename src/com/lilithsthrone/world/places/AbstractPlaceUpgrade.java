package com.lilithsthrone.world.places;

import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.world.Cell;

/**
 * @since 0.3.9
 * @version 0.3.9
 * @author Innoxia
 */
public abstract class AbstractPlaceUpgrade implements PlaceUpgrade {

	String id;
	private boolean isCoreRoomUpgrade;
	
	protected String name;
	protected String descriptionForPurchase;
	protected String descriptionAfterPurchase;
	protected String roomDescription;
	
	private int installCost;
	private int removalCost;
	private int upkeep;
	private int capacity;
	
	private Colour colour;
	
	private float affectionGain;
	private float obedienceGain;
	
	private List<AbstractPlaceUpgrade> prerequisites;
	
	public AbstractPlaceUpgrade(boolean isCoreRoomUpgrade,
			Colour colour,
			String name,
			String descriptionForPurchase,
			String descriptionAfterPurchase,
			String roomDescription,
			int installCost,
			int removalCost,
			int upkeep,
			int capacity,
			float affectionGain,
			float obedienceGain,
			List<AbstractPlaceUpgrade> prerequisites) {
		
		this.isCoreRoomUpgrade = isCoreRoomUpgrade;
		this.colour = colour;
		this.name = name;
		this.descriptionForPurchase = descriptionForPurchase;
		this.descriptionAfterPurchase = descriptionAfterPurchase;
		this.roomDescription = roomDescription;
		
		this.installCost = installCost;
		this.removalCost = removalCost;
		this.upkeep = upkeep;
		this.capacity = capacity;
		
		this.affectionGain = affectionGain;
		
		this.obedienceGain = obedienceGain;
		
		if(prerequisites==null) {
			this.prerequisites = new ArrayList<>();
			
		} else {
			this.prerequisites = prerequisites;
		}
	}

	/**
	 * @param cell The cell to check for this upgrade's availability.
	 * @return A value representing availability and reasoning of availability of this upgrade. If the key is false, and the value is an empty string, then this upgrade is not added to any of the available upgrade lists which are displayed in-game.
	 */
	protected Value<Boolean, String> getExtraConditionalAvailability(Cell cell) {
		return new Value<>(true, "");
	}

	@Override
	public String getId() {
		return id;
	}

	/**
	 * @param cell The cell to check for this upgrade's availability.
	 * @return A value representing availability and reasoning of availability of this upgrade. If the key is false, and the value is an empty string, then this upgrade is not added to any of the available upgrade lists which are displayed in-game.
	 */
	@Override
	public Value<Boolean, String> getAvailability(Cell cell) {
		if(!Main.game.getPlayer().isHasSlaverLicense() && this.isSlaverUpgrade()) {
			return new Value<>(false, "You are unable to purchase this upgrade without a slaver license!");
		}
		
		return getExtraConditionalAvailability(cell);
	}

	/**
	 * @param cell The cell to check for this upgrade's availability.
	 * @return A value representing availability of removal and reasoning of availability removal of this upgrade.
	 */
	@Override
	public Value<Boolean, String> getRemovalAvailability(Cell cell) {
		if(this.isCoreRoomUpgrade()) {
			return new Value<>(false, "You cannot directly remove core upgrades. Instead, you'll have to purchase a different core modification in order to remove the current one.");
		}
		return new Value<>(true, "");
	}

	@Override
	public boolean isCoreRoomUpgrade() {
		return isCoreRoomUpgrade;
	}

	@Override
	public Colour getColour() {
		return colour;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getRoomDescription(Cell c) {
		return roomDescription;
	}

	@Override
	public String getDescriptionForPurchase() {
		return descriptionForPurchase;
	}

	@Override
	public String getDescriptionAfterPurchase() {
		return descriptionAfterPurchase;
	}

	@Override
	public int getInstallCost() {
		if(Main.game.getPlayer().hasTrait(Perk.JOB_PLAYER_CONSTRUCTION_WORKER, true)) {
			return installCost/2;
		}
		return installCost;
	}

	@Override
	public int getRemovalCost() {
		if(Main.game.getPlayer().hasTrait(Perk.JOB_PLAYER_CONSTRUCTION_WORKER, true)) {
			if(removalCost>0) {
				return removalCost;
			}
			return Math.max(-(installCost/2), removalCost);
		}
		return removalCost;
	}

	@Override
	public int getUpkeep() {
		return upkeep;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public float getHourlyAffectionGain() {
		return affectionGain;
	}

	@Override
	public float getHourlyObedienceGain() {
		return obedienceGain;
	}
}
