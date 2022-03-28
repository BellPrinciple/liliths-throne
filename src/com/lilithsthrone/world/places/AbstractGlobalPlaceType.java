package com.lilithsthrone.world.places;

import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.encounters.Encounter;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.world.WorldRegion;

/**
 * @since 0.3.1
 * @version 0.4
 * @author Innoxia
 */
public class AbstractGlobalPlaceType extends AbstractPlaceType {

	public AbstractGlobalPlaceType(WorldRegion worldRegion,
			String name,
			String SVGPath,
			String tooltipDescription,
			Colour colour,
			DialogueNode dialogue,
			Encounter encounterType,
			String virginityLossDescription) {
		this(worldRegion, name, tooltipDescription, SVGPath, colour, colour, dialogue, encounterType, virginityLossDescription);
	}
	
	public AbstractGlobalPlaceType(WorldRegion worldRegion,
			String name,
			String tooltipDescription,
			String SVGPath,
			Colour colour,
			Colour backgroundColour,
			DialogueNode dialogue,
			Encounter encounterType,
			String virginityLossDescription) {
		super(worldRegion, name, tooltipDescription, SVGPath, colour, dialogue, Darkness.DAYLIGHT, encounterType, virginityLossDescription);
		if(backgroundColour!=null) {
			this.backgroundColour = backgroundColour;
		}
		this.globalMapTile = true;
	}
}
