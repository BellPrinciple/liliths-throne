package com.lilithsthrone.game.character.body;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lilithsthrone.controller.xmlParsing.XMLUtil;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.body.types.FluidType;
import com.lilithsthrone.game.character.body.valueEnums.FluidFlavour;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.character.body.valueEnums.FluidTypeBase;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.XMLSaving;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @since 0.2.6
 * @version 0.3.8.2
 * @author Innoxia
 */
public final class Fluid implements BodyPartInterface, XMLSaving {

	FluidType type;
	private FluidFlavour flavour;
	private final Set<FluidModifier> fluidModifiers = new HashSet<>();
	private final List<ItemEffect> transformativeEffects = new ArrayList<>();
	private final boolean crotchMilk;

	public Fluid(FluidType t, boolean crotch) {
		type = t;
		crotchMilk = crotch;
		fluidModifiers.addAll(t.getDefaultFluidModifiers());
	}

	public Fluid(Fluid other) {
		type = other.type;
		flavour = other.flavour;
		fluidModifiers.addAll(other.fluidModifiers);
		transformativeEffects.addAll(other.transformativeEffects);
		crotchMilk = other.crotchMilk;
	}

	@Override
	public FluidType getType() {
		return type;
	}

	public void setType(FluidType type) {
		this.type = type;
	}

	public FluidFlavour getFlavour() {
		return flavour;
	}

	public boolean isCrotchMilk() {
		return crotchMilk;
	}

	public String setFlavour(GameCharacter owner, FluidFlavour flavour) {
		if(owner != null && this.flavour == flavour)
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		this.flavour = flavour;
		if(owner == null)
			return "";
		String bodyPart = switch(type.getBaseType()) {
			case CUM -> "[npc.balls+]";
			case GIRLCUM -> "[npc.pussy]";
			case MILK -> crotchMilk ? "[npc.crotchBoobs]" : "[npc.breasts]";
		};
		String name = switch(type.getBaseType()) {
			case CUM -> "[npc.cum] ";
			case GIRLCUM -> "[pc.girlcum] ";
			case MILK -> crotchMilk ? "[npc.crotchMilk] " : "[npc.milk] ";
		};
		String text = "<p>"
					+ "A soothing warmth spreads through [npc.namePos] "
					+ bodyPart
					+ ", causing [npc.herHim] to let out a contented little sigh.<br/>"
					+ "[npc.NamePos] "
					+ name
					+ (flavour==FluidFlavour.FLAVOURLESS ? "is now" : "now tastes of")
					+ " <b style='color:"+flavour.getColour().toWebHexString()+";'>"+flavour.getName()+"</b>"
					+ "</p>";
		return UtilText.parse(owner, text);
	}

	public Set<FluidModifier> getFluidModifiers() {
		return Collections.unmodifiableSet(fluidModifiers);
	}

	public boolean hasFluidModifier(FluidModifier fluidModifier) {
		return fluidModifiers.contains(fluidModifier);
	}

	public String addFluidModifier(GameCharacter owner, FluidModifier fluidModifier) {
		if(owner==null) {
			if(!fluidModifiers.contains(fluidModifier))
				fluidModifiers.add(fluidModifier);
			return "";
		}

		boolean missingBodyParts = switch(type.getBaseType()) {
			case CUM -> !owner.hasPenis();
			case GIRLCUM -> !owner.hasVagina();
			case MILK -> false;
		};
		if(missingBodyParts || fluidModifiers.contains(fluidModifier)) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}

		fluidModifiers.add(fluidModifier);

		List<FluidModifier> excludedModifiers = switch(fluidModifier) {
			case ALCOHOLIC -> List.of(FluidModifier.ALCOHOLIC_WEAK);
			case ALCOHOLIC_WEAK -> List.of(FluidModifier.ALCOHOLIC);
			default -> List.of();
		};
		fluidModifiers.removeAll(excludedModifiers);

		String bodyPart = switch(type.getBaseType()) {
			case CUM -> "[npc.balls]";
			case GIRLCUM -> "[npc.pussy]";
			case MILK -> crotchMilk ? "" : "[npc.breasts]";
		};
		String cumText = switch(fluidModifier) {
			case ADDICTIVE -> "A strange, pulsating heat takes root deep within";
			case ALCOHOLIC, ALCOHOLIC_WEAK -> "A strange, soothing warmth takes root deep within";
			case BUBBLING -> "A light, bubbly feeling rises up into";
			case HALLUCINOGENIC -> "A series of strange pulses shoot down into";
			case MINERAL_OIL -> "A soothing warmth flows into";
			case MUSKY -> "A slow, creeping warmth rises up into";
			case SLIMY -> "A strange, soothing warmth flows up into";
			case STICKY -> "A thick, sickly warmth flows up into";
			case VISCOUS -> "A heavy heat slowly rises up into";
		};
		String milkText = switch(fluidModifier) {
			case ADDICTIVE -> "A strange, pulsating heat spreads through";
			case ALCOHOLIC, ALCOHOLIC_WEAK -> "A strange, soothing warmth spreads up through";
			case BUBBLING -> "A light, bubbly feeling spreads up through";
			case HALLUCINOGENIC -> "A series of strange pulses shoot up through";
			case MINERAL_OIL -> "A soothing warmth flows into";
			case MUSKY -> "A slow, creeping warmth rises up into";
			case SLIMY -> "A strange, soothing warmth flows up into";
			case STICKY -> "A thick, sickly warmth flows up into";
			case VISCOUS -> "A heavy heat slowly rises up into";
		};
		String text = switch(type.getBaseType()) {
			case CUM, GIRLCUM -> cumText;
			case MILK -> milkText;
		};
		String fluid = switch(type.getBaseType()) {
			case CUM -> "[npc.cum]";
			case GIRLCUM -> "[npc.girlcum]";
			case MILK -> crotchMilk ? "[npc.crotchMilk]" : "[npc.milk]";
		};
		String attribute = switch(fluidModifier) {
			case ADDICTIVE -> "[style.boldGrow(addictive)]";
			case ALCOHOLIC -> "[style.boldGrow(strongly alcoholic)]";
			case ALCOHOLIC_WEAK -> "[style.boldGrow(alcoholic)]";
			case BUBBLING -> "[style.boldGrow(bubbly)]";
			case HALLUCINOGENIC -> "[style.boldGrow(psychoactive)]";
			case MINERAL_OIL -> "imbued with [style.boldGrow(mineral oil)], and can melt condoms";
			case MUSKY -> "[style.boldGrow(musky)]";
			case SLIMY -> "[style.boldGrow(slimy)]";
			case STICKY -> "[style.boldGrow(sticky)]";
			case VISCOUS -> "[style.boldGrow(viscous)]";
		};
		return UtilText.parse(owner, "<p>"+text+" [npc.namePos] "+bodyPart+", causing [npc.herHim] to let out [npc.a_moan+].<br/>[npc.NamePos] "+fluid+" is now "+attribute+"!</p>");
	}

	public String removeFluidModifier(GameCharacter owner, FluidModifier fluidModifier) {
		if(owner == null) {
			fluidModifiers.remove(fluidModifier);
			return "";
		}

		boolean missingBodyPart = switch(type.getBaseType()) {
			case CUM -> !owner.hasPenis();
			case GIRLCUM -> !owner.hasVagina();
			case MILK -> false;
		};
		if(missingBodyPart || !fluidModifiers.contains(fluidModifier)) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}

		fluidModifiers.remove(fluidModifier);

		String different = switch(fluidModifier) {
			case ADDICTIVE -> "[style.boldShrink(no longer addictive)]";
			case ALCOHOLIC, ALCOHOLIC_WEAK -> "[style.boldShrink(no longer alcoholic)]";
			case BUBBLING -> "[style.boldShrink(no longer bubbly)]";
			case HALLUCINOGENIC -> "[style.boldShrink(no longer psychoactive)]";
			case MUSKY -> "[style.boldShrink(no longer musky)]";
			case SLIMY -> "[style.boldShrink(no longer slimy)]";
			case STICKY -> "[style.boldShrink(no longer sticky)]";
			case MINERAL_OIL -> "[style.boldShrink(no longer mineral oil)]";
			case VISCOUS -> "[style.boldShrink(no longer viscous)]";
		};
		String fluid = switch(type.getBaseType()) {
			case CUM -> "[npc.cum]";
			case GIRLCUM -> "[npc.girlcum]";
			case MILK -> crotchMilk ? "[npc.crotchMilk]" : "[npc.milk]";
		};
		String bodyPart = switch(type.getBaseType()) {
			case CUM -> "[npc.balls]";
			case GIRLCUM -> "[npc.pussy]";
			case MILK -> crotchMilk ? "[npc.crotchBoobs]" : "[npc.breasts]";
		};
		String cumText = switch(fluidModifier) {
			case ADDICTIVE, ALCOHOLIC, ALCOHOLIC_WEAK -> "A soft coolness spreads up into";
			case BUBBLING -> "A calm, settling feeling spreads up into";
			case HALLUCINOGENIC -> "A series of soothing waves wash up into";
			case MUSKY -> "A gentle coolness rises up into";
			case SLIMY -> "A calming coolness flows up into";
			case STICKY -> "A soft warmth flows up into";
			case VISCOUS -> "A soft coolness rises up into";
			case MINERAL_OIL -> "A short relief flows up into";
		};
		String milkText = switch(fluidModifier) {
			case ADDICTIVE, ALCOHOLIC, ALCOHOLIC_WEAK -> "A soft coolness spreads up through";
			case BUBBLING -> "A calm, settling feeling spreads up through";
			case HALLUCINOGENIC -> "A series of soothing waves wash up through";
			case MUSKY -> "A gentle coolness rises up into";
			case SLIMY -> "A calming coolness flows up into";
			case STICKY -> "A soft warmth flows up into";
			case VISCOUS -> "A soft coolness rises up into";
			case MINERAL_OIL -> "A short relief flows up into";
		};
		String gentle = fluidModifier == FluidModifier.MUSKY ? "soft" : "gentle";
		String somethingHappensTo = switch(type.getBaseType()) {
			case CUM, GIRLCUM -> cumText;
			case MILK -> milkText;
		};

		return UtilText.parse(owner, "<p>"+somethingHappensTo+" [npc.namePos] "+bodyPart+", causing [npc.herHim] to let out a "+gentle+" sigh.<br/>[npc.NamePos] "+fluid+" is "+different+"!</p>");
	}

	public void clearFluidModifiers() {
		transformativeEffects.clear();
	}

	public List<ItemEffect> getTransformativeEffects() {
		return transformativeEffects;
	}

	public void addTransformativeEffect(ItemEffect ie) {
		transformativeEffects.add(ie);
	}

	public float getValuePerMl() {
		float factor = switch(type.getBaseType()) {
			case CUM -> 0.1f;
			case MILK -> 0.01f;
			case GIRLCUM -> 1f;
		};
		return factor * type.getValueModifier();
	}

	public Element saveAsXML(Element parentElement, Document doc) {
		String tagName = switch(type.getBaseType()) {
			case CUM -> "cum";
			case GIRLCUM -> "girlcum";
			case MILK -> crotchMilk ? "milkCrotch" : "milk";
		};
		String modifierTagName = switch(type.getBaseType()) {
			case CUM -> "cumModifiers";
			case GIRLCUM -> "girlcumModifiers";
			case MILK -> "milkModifiers";
		};
		Element element = doc.createElement(tagName);
		parentElement.appendChild(element);
		XMLUtil.addAttribute(doc, element, "type", FluidType.getIdFromFluidType(type));
		XMLUtil.addAttribute(doc, element, "flavour", flavour.toString());
		Element modifiers = doc.createElement(modifierTagName);
		element.appendChild(modifiers);
		for(FluidModifier fm : fluidModifiers)
			XMLUtil.addAttribute(doc, modifiers, fm.toString(), "true");
		return element;
	}

	/**
	 * @param rootElementName Desired tagName of the XML child element containing the properties to be loaded.
	 * @param parentElement Element of a XML document.
	 * @param fluidType If you pass in a baseType, this method will ignore the saved type in parentElement.
	 * @param crotchMilk If {@code fluidType} is a milk type, whether this milk is to be produced in crotch breasts.
	 */
	public static Fluid loadFromXML(String rootElementName, Element parentElement, FluidType fluidType, boolean crotchMilk) {
		String baseName = baseElementName(rootElementName, fluidType.getBaseType(), crotchMilk);
		Element element = (Element) parentElement.getElementsByTagName(baseName).item(0);
		return loadFromXML(element, fluidType, crotchMilk);
	}

	public static Fluid loadFromXML(String rootElementName, Element parentElement, FluidTypeBase baseType, boolean crotchMilk) {
		String baseName = baseElementName(rootElementName, baseType, crotchMilk);
		Element element = (Element) parentElement.getElementsByTagName(baseName).item(0);
		String attributeType = element == null ? "" : element.getAttribute("type");
		FluidType defaultType = switch(baseType) {
			case CUM -> FluidType.CUM_HUMAN;
			case GIRLCUM -> FluidType.GIRL_CUM_HUMAN;
			case MILK -> FluidType.MILK_HUMAN;
		};
		FluidType fluidType = attributeType.isEmpty() ? defaultType : FluidType.getFluidTypeFromId(attributeType);
		return loadFromXML(element, fluidType, crotchMilk);
	}

	public static Fluid loadFromXML(Element element, FluidType fluidType, boolean crotchMilk) {
		Fluid fluid = new Fluid(fluidType, crotchMilk);
		if(element == null)
			return fluid;
		String flavourId = element.getAttribute("flavour");
		fluid.flavour = flavourId.equalsIgnoreCase("SLIME") ? FluidFlavour.BUBBLEGUM : FluidFlavour.valueOf(flavourId);
		String modifierTagName = switch(fluidType.getBaseType()) {
			case CUM -> "cumModifiers";
			case GIRLCUM -> "girlcumModifiers";
			case MILK -> "milkModifiers";
		};
		Element milkModifiersElement = (Element) element.getElementsByTagName(modifierTagName).item(0);
		fluid.fluidModifiers.clear();
		if(milkModifiersElement!=null)
			Body.handleLoadingOfModifiers(FluidModifier.values(), null, milkModifiersElement, fluid.fluidModifiers);
		return fluid;
	}

	private static String baseElementName(String name, FluidTypeBase baseType, boolean crotchMilk) {
		if(name != null)
			return name;
		return switch(baseType) {
			case CUM -> "cum";
			case GIRLCUM -> "girlcum";
			case MILK -> crotchMilk ? "crotchMilk" : "milk";
		};
	}

	@Override
	public String getDeterminer(GameCharacter gc) {
		return type.getDeterminer(gc);
	}

	@Override
	public String getName(GameCharacter gc) {
		return switch(type.getBaseType()) {
			case CUM -> type.getName(false, gc);
			case MILK, GIRLCUM -> type.getName(gc);
		};
	}

	@Override
	public String getNameSingular(GameCharacter gc) {
		return type.getNameSingular(gc);
	}

	@Override
	public String getNamePlural(GameCharacter gc) {
		return type.getNamePlural(gc);
	}

	@Override
	public String getDescriptor(GameCharacter gc) {
		boolean isCum = switch(type.getBaseType()) {
			case CUM -> true;
			case MILK, GIRLCUM -> false;
		};
		FluidModifier modifier = Util.randomItemFrom(fluidModifiers);
		return UtilText.returnStringAtRandom(
				isCum && gc.getAttributeValue(Attribute.VIRILITY) >= 20 ? "potent" : "",
				isCum ? "hot" : "",
				modifier == null ? "" : modifier.getName(),
				flavour.getRandomFlavourDescriptor(),
				type.getDescriptor(gc));
	}

	@Override
	public boolean isFeral(GameCharacter owner) {
		if(owner==null)
			return false;
		return owner.isFeral() || owner.getLegConfiguration().getFeralParts().contains(Fluid.class) && getType().getRace().isFeralPartsAvailable();
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Fluid o
				&& type.equals(o.type)
				&& flavour.equals(o.flavour)
				&& fluidModifiers.equals(o.fluidModifiers)
				&& transformativeEffects.equals(o.transformativeEffects);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + type.hashCode();
		result = 31 * result + flavour.hashCode();
		result = 31 * result + fluidModifiers.hashCode();
		result = 31 * result + transformativeEffects.hashCode();
		return result;
	}
}
