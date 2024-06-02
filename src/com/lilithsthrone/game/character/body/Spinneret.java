package com.lilithsthrone.game.character.body;

import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.valueEnums.Capacity;
import com.lilithsthrone.game.character.body.valueEnums.OrificeDepth;
import com.lilithsthrone.game.character.body.valueEnums.OrificeElasticity;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.body.valueEnums.OrificePlasticity;
import com.lilithsthrone.game.character.body.valueEnums.Wetness;
import com.lilithsthrone.game.sex.SexAreaOrifice;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.4
 * @version 0.4.9.7
 * @author Innoxia
 */
public final class Spinneret {

	final Orifice orifice;

	public Spinneret() {
		orifice = new Orifice(Orifice.Type.SPINNERET, Wetness.ONE_SLIGHTLY_MOIST.getValue(), Capacity.ONE_EXTREMELY_TIGHT.getMedianValue(), OrificeDepth.TWO_AVERAGE.getValue(), OrificeElasticity.FOUR_LIMBER.getValue(), OrificePlasticity.THREE_RESILIENT.getValue(), true, List.of());
	}

	public Spinneret(Spinneret spinneretToCopy) {
		orifice = new Orifice(spinneretToCopy.orifice);
	}
	
	public Orifice getOrifice() {
		return orifice;
	}

	public String getDescriptor(GameCharacter owner) {
		List<String> descriptorList = new ArrayList<>();
		
		for(OrificeModifier om : orifice.getOrificeModifiers()) {
			descriptorList.add(om.getName());
		}
		
		String wetnessDescriptor = orifice.getWetness(owner).getDescriptor();
		if(Main.game.isInSex() && Main.sex.getAllParticipants().contains(owner)) {
			if(Main.sex.hasLubricationTypeFromAnyone(owner, SexAreaOrifice.VAGINA)) {
				wetnessDescriptor = "wet";
			}
		}
		descriptorList.add(wetnessDescriptor);
		
		descriptorList.add(Capacity.getCapacityFromValue(orifice.getStretchedCapacity()).getDescriptor().replaceAll(" ", "-"));

		return Util.randomItemFrom(descriptorList);
	}
}
