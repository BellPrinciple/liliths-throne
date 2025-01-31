package com.lilithsthrone.game.sex.managers.dominion;

import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.sex.managers.SexManagerDefault;
import com.lilithsthrone.game.sex.positions.SexPosition;
import com.lilithsthrone.game.sex.positions.slots.SexSlot;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.97
 * @version 0.3.8.6
 * @author Innoxia
 */
public class SMVickyOverDesk extends SexManagerDefault {

	public SMVickyOverDesk(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
		super(SexPosition.OVER_DESK,
				dominants,
				submissives);
	}
	
	@Override
	public boolean isPlayerAbleToStopSex() {
		return false;
	}
	
	@Override
	public List<SexPosition> getAllowedSexPositions() {
		return Util.newArrayListOfValues(
				SexPosition.OVER_DESK);
	}
	
	@Override
	public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
		return false;
	}

	@Override
	public boolean isPositionChangingAllowed(GameCharacter character) {
		return false;
	}
}
