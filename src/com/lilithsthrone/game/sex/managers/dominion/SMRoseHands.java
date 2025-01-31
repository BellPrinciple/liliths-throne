package com.lilithsthrone.game.sex.managers.dominion;

import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.sex.managers.SexManagerDefault;
import com.lilithsthrone.game.sex.positions.SexPosition;
import com.lilithsthrone.game.sex.positions.SexPositionUnique;
import com.lilithsthrone.game.sex.positions.slots.SexSlot;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.7?
 * @version 0.3.5.5
 * @author Innoxia
 */
public class SMRoseHands extends SexManagerDefault {

	public SMRoseHands(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
		super(false,
				SexPositionUnique.HANDS_ROSE,
				dominants,
				submissives);
	}
	
	@Override
	public boolean isPlayerAbleToStopSex() {
		return true;
	}
	
	@Override
	public boolean isItemUseAvailable() {
		return false;
	}
	
	@Override
	public boolean isAbleToRemoveSelfClothing(GameCharacter character){
		return false;
	}
	
	@Override
	public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing){
		return false;
	}

	@Override
	public List<SexPosition> getAllowedSexPositions() {
		return Util.newArrayListOfValues(
				SexPositionUnique.HANDS_ROSE);
	}
	
	@Override
	public boolean isPositionChangingAllowed(GameCharacter character) {
		return false;
	}
	
	@Override
	public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
		return false;
	}

}
