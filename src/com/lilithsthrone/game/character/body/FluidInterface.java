package com.lilithsthrone.game.character.body;

import java.util.List;
import java.util.Set;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.types.FluidType;
import com.lilithsthrone.game.character.body.valueEnums.FluidFlavour;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.utils.XMLSaving;

/**
 * @since 0.2.6
 * @version 0.3.8.2
 * @author Innoxia
 */
public interface FluidInterface extends BodyPartInterface, XMLSaving {

	@Override
	FluidType getType();

	FluidFlavour getFlavour();

	String setFlavour(GameCharacter owner, FluidFlavour flavour);

	Set<FluidModifier> getFluidModifiers();

	boolean hasFluidModifier(FluidModifier fluidModifier);

	String addFluidModifier(GameCharacter owner, FluidModifier fluidModifier);

	String removeFluidModifier(GameCharacter owner, FluidModifier fluidModifier);

	void clearFluidModifiers();

	List<ItemEffect> getTransformativeEffects();

	void addTransformativeEffect(ItemEffect ie);

	float getValuePerMl();
}
