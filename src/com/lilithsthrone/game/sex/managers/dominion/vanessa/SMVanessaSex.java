package com.lilithsthrone.game.sex.managers.dominion.vanessa;

import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.CoverableArea;
import com.lilithsthrone.game.character.npc.dominion.Vanessa;
import com.lilithsthrone.game.sex.managers.SexManagerDefault;
import com.lilithsthrone.game.sex.positions.AbstractSexPosition;
import com.lilithsthrone.game.sex.positions.slots.SexSlot;
import com.lilithsthrone.main.Main;

/**
 * @since 0.3.2
 * @version 0.3.4
 * @author Innoxia
 */
public class SMVanessaSex extends SexManagerDefault {
	
	public SMVanessaSex(AbstractSexPosition position, Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
		super(position,
				dominants,
				submissives);
	}

	@Override
	public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
		return Map.of(Main.game.getPlayer(),List.of(CoverableArea.PENIS),Main.game.getNpc(Vanessa.class),List.of(CoverableArea.VAGINA));
	}
	
	@Override
	public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
		return false;
	}
	
	@Override
	public boolean isPositionChangingAllowed(GameCharacter character) {
		return false;
	}
	
	@Override
	public boolean isPartnerWantingToStopSex(GameCharacter partner) {
		// She stops after both of you are satisfied:
		return Main.sex.isSatisfiedFromOrgasms(Main.game.getPlayer(), true) && Main.sex.isSatisfiedFromOrgasms(Main.game.getNpc(Vanessa.class), true);
	}
	
}
