package com.lilithsthrone.hypnowatch;

import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.npc.dominion.Vicky;
import com.lilithsthrone.game.sex.SexPace;
import com.lilithsthrone.game.sex.managers.SexManagerDefault;
import com.lilithsthrone.game.sex.positions.SexPosition;
import com.lilithsthrone.utils.Util;

import static com.lilithsthrone.game.sex.positions.slots.SexSlotDesk.BETWEEN_LEGS;
import static com.lilithsthrone.game.sex.positions.slots.SexSlotDesk.OVER_DESK_ON_FRONT;
import static com.lilithsthrone.main.Main.game;

/**
 * @since 0.1.97
 * @version 0.3.8.6
 * @author Innoxia
 */
public class SMVickyOverDesk extends SexManagerDefault {

	private final boolean isNoncon;

	public SMVickyOverDesk(boolean noncon) {
		super(SexPosition.OVER_DESK,
			Map.of(game.getNpc(Vicky.class),BETWEEN_LEGS),
			Map.of(game.getPlayer(),OVER_DESK_ON_FRONT));
		isNoncon = noncon;
	}

	@Override
	public SexPace getStartingSexPaceModifier(GameCharacter character) {
		return isNoncon && character.isPlayer() ? SexPace.SUB_RESISTING : null;
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
