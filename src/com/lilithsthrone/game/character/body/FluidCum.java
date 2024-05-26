package com.lilithsthrone.game.character.body;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lilithsthrone.controller.xmlParsing.XMLUtil;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.body.types.FluidType;
import com.lilithsthrone.game.character.body.valueEnums.FluidFlavour;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.4.9.7
 * @author Innoxia
 */
public class FluidCum implements Fluid {

	protected FluidType type;
	protected FluidFlavour flavour;
	protected Set<FluidModifier> fluidModifiers;
	protected List<ItemEffect> transformativeEffects;

	public FluidCum(FluidType type) {
		this.type = type;
		this.flavour = type.getFlavour();
		transformativeEffects = new ArrayList<>();
		
		fluidModifiers = new HashSet<>();
		fluidModifiers.addAll(type.getDefaultFluidModifiers());
	}

	public FluidCum(FluidCum cumToCopy) {
		this.type = cumToCopy.type;
		this.flavour = cumToCopy.flavour;
		this.fluidModifiers = new HashSet<>(cumToCopy.fluidModifiers);
		this.transformativeEffects = new ArrayList<>(cumToCopy.transformativeEffects);
	}

	public Element saveAsXML(String rootElementName, Element parentElement, Document doc) {
		Element element = doc.createElement(rootElementName);
		parentElement.appendChild(element);

		XMLUtil.addAttribute(doc, element, "type", FluidType.getIdFromFluidType(this.type));
		XMLUtil.addAttribute(doc, element, "flavour", this.flavour.toString());

		for(FluidModifier fm : this.getFluidModifiers()) {
			Element mod = doc.createElement("mod");
			mod.setTextContent(fm.toString());
			element.appendChild(mod);
		}
		
		return element;
	}

	public static FluidCum loadFromXML(String rootElementName, Element parentElement, Document doc) {
		return loadFromXML(rootElementName, parentElement, doc, null);
	}
	
	/**
	 * 
	 * @param parentElement
	 * @param doc
	 * @param baseType If you pass in a baseType, this method will ignore the saved type in parentElement.
	 */
	public static FluidCum loadFromXML(String rootElementName, Element parentElement, Document doc, FluidType baseType) {
		Element cum = (Element)parentElement.getElementsByTagName(rootElementName).item(0);

		FluidType fluidType = FluidType.CUM_HUMAN;
		
		if(baseType!=null) {
			fluidType = baseType;
			
		} else {
			try {
				fluidType = FluidType.table.of(cum.getAttribute("type"));
			} catch(Exception ex) {
			}
		}
		
		FluidCum fluidCum = new FluidCum(fluidType);
		
		String flavourId = cum.getAttribute("flavour");
		if(flavourId.equalsIgnoreCase("SLIME")) {
			fluidCum.flavour = FluidFlavour.BUBBLEGUM;
		} else {
			fluidCum.flavour = FluidFlavour.valueOf(flavourId);
		}
		

		Element cumModifiers = (Element)cum.getElementsByTagName("cumModifiers").item(0);
		if(cumModifiers!=null) {
			fluidCum.fluidModifiers.clear();
			if(cumModifiers!=null) {
				Set<FluidModifier> fluidModifiers = fluidCum.fluidModifiers;
				Body.handleLoadingOfModifiers(FluidModifier.values(), null, cumModifiers, fluidModifiers);
			}
		} else {
			NodeList mods = cum.getElementsByTagName("mod");
			for(int i = 0; i < mods.getLength(); i++) {
				Element e = ((Element)mods.item(i));
				fluidCum.fluidModifiers.add(FluidModifier.valueOf(e.getTextContent()));
			}
		}

		return fluidCum;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof FluidCum){
			if(((FluidCum)o).getType().equals(this.getType())
				&& ((FluidCum)o).getFlavour() == this.getFlavour()
				&& ((FluidCum)o).getFluidModifiers().equals(this.getFluidModifiers())
				&& ((FluidCum)o).getTransformativeEffects().equals(this.getTransformativeEffects())){
					return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.getType().hashCode();
		result = 31 * result + this.getFlavour().hashCode();
		result = 31 * result + this.getFluidModifiers().hashCode();
		result = 31 * result + this.getTransformativeEffects().hashCode();
		return result;
	}
	
	@Override
	public String getDeterminer(GameCharacter gc) {
		return type.getDeterminer(gc);
	}

	@Override
	public String getName(GameCharacter gc) {
		return this.getType().getName(false, gc);
	}
	
	@Override
	public String getNameSingular(GameCharacter gc) {
		return this.getType().getNameSingular(gc);
	}

	@Override
	public String getNamePlural(GameCharacter gc) {
		return this.getType().getNamePlural(gc);
	}
	
	@Override
	public String getDescriptor(GameCharacter gc) {
		String modifierDescriptor = "";
		if(!fluidModifiers.isEmpty()) {
			modifierDescriptor = new ArrayList<>(fluidModifiers).get(Util.random.nextInt(fluidModifiers.size())).getName();
		}
		
		return UtilText.returnStringAtRandom(
				(gc.getAttributeValue(Attribute.VIRILITY)>=20?"potent":""),
				"hot",
				modifierDescriptor,
				flavour.getRandomFlavourDescriptor(),
				type.getDescriptor(gc));
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

	public String setFlavour(GameCharacter owner, FluidFlavour flavour) {
		if(owner==null) {
			this.flavour = flavour;
			return "";
		}
		
		if(this.flavour == flavour || !owner.hasPenis()) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		this.flavour = flavour;
		
		return UtilText.parse(owner,
				"<p>"
					+ "A soothing warmth spreads down into [npc.namePos] [npc.balls+], causing [npc.herHim] to let out an involuntary [npc.moan].<br/>"
					+ "[npc.NamePos] [npc.cum] "
					+ (flavour==FluidFlavour.FLAVOURLESS
						?"is now <b style='color:"+flavour.getColour().toWebHexString()+";'>"+flavour.getName()+"</b>"
						:"now tastes of <b style='color:"+flavour.getColour().toWebHexString()+";'>"+flavour.getName()+"</b>.")
				+ "</p>");
	}
	
	public boolean hasFluidModifier(FluidModifier fluidModifier) {
		return fluidModifiers.contains(fluidModifier);
	}
	
	public String addFluidModifier(GameCharacter owner, FluidModifier fluidModifier) {
		if(owner==null && !fluidModifiers.contains(fluidModifier)) {
			fluidModifiers.add(fluidModifier);
			return "";
		}
		
		if(fluidModifiers.contains(fluidModifier) || !owner.hasPenis()) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		fluidModifiers.add(fluidModifier);
		
		switch(fluidModifier) {
			case ADDICTIVE:
				return UtilText.parse(owner,
						"<p>"
							+ "A strange, pulsating heat takes root deep within [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now [style.boldGrow(addictive)]!"
						+ "</p>");
			case ALCOHOLIC:
				fluidModifiers.remove(FluidModifier.ALCOHOLIC_WEAK);
				return UtilText.parse(owner,
						"<p>"
							+ "A strange, soothing warmth takes root deep within [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now [style.boldGrow(strongly alcoholic)]!"
						+ "</p>");
			case ALCOHOLIC_WEAK:
				fluidModifiers.remove(FluidModifier.ALCOHOLIC);
				return UtilText.parse(owner,
						"<p>"
							+ "A strange, soothing warmth takes root deep within [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now [style.boldGrow(alcoholic)]!"
						+ "</p>");
			case BUBBLING:
				return UtilText.parse(owner,
						"<p>"
							+ "A light, bubbly feeling rises up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now [style.boldGrow(bubbly)]!"
						+ "</p>");
			case HALLUCINOGENIC:
				return UtilText.parse(owner,
						"<p>"
							+ "A series of strange pulses shoot down into [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now [style.boldGrow(psychoactive)]!"
						+ "</p>");
			case MINERAL_OIL:
				return UtilText.parse(owner,
						"<p>"
							+ "A soothing warmth flows into [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now imbued with [style.boldGrow(mineral oil)], and can melt condoms!"
						+ "</p>");
			case MUSKY:
				return UtilText.parse(owner,
						"<p>"
							+ "A slow, creeping warmth rises up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now [style.boldGrow(musky)]!"
						+ "</p>");
			case SLIMY:
				return UtilText.parse(owner,
						"<p>"
							+ "A strange, soothing warmth flows up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now [style.boldGrow(slimy)]!"
						+ "</p>");
			case STICKY:
				return UtilText.parse(owner,
						"<p>"
							+ "A thick, sickly warmth flows up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now [style.boldGrow(sticky)]!"
						+ "</p>");
			case VISCOUS:
				return UtilText.parse(owner,
						"<p>"
							+ "A heavy heat slowly rises up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out [npc.a_moan+].<br/>"
							+ "[npc.NamePos] [npc.cum] is now [style.boldGrow(viscous)]!"
						+ "</p>");
		}
		
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}
	
	public String removeFluidModifier(GameCharacter owner, FluidModifier fluidModifier) {
		if(owner==null) {
			fluidModifiers.remove(fluidModifier);
			return "";
		}
		
		if(!fluidModifiers.contains(fluidModifier) || !owner.hasPenis()) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		fluidModifiers.remove(fluidModifier);
		
		switch(fluidModifier) {
			case ADDICTIVE:
				return UtilText.parse(owner,
						"<p>"
							+ "A soft coolness spreads up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out a gentle sigh.<br/>"
							+ "[npc.NamePos] [npc.cum] is [style.boldShrink(no longer addictive)]!"
						+ "</p>");
			case ALCOHOLIC:
			case ALCOHOLIC_WEAK:
				return UtilText.parse(owner,
						"<p>"
							+ "A soft coolness spreads up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out a gentle sigh.<br/>"
							+ "[npc.NamePos] [npc.cum] is [style.boldShrink(no longer alcoholic)]!"
						+ "</p>");
			case BUBBLING:
				return UtilText.parse(owner,
						"<p>"
							+ "A calm, settling feeling spreads up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out a gentle sigh.<br/>"
							+ "[npc.NamePos] [npc.cum] is [style.boldShrink(no longer bubbly)]!"
						+ "</p>");
			case HALLUCINOGENIC:
				return UtilText.parse(owner,
						"<p>"
							+ "A series of soothing waves wash up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out a gentle sigh.<br/>"
							+ "[npc.NamePos] [npc.cum] is [style.boldShrink(no longer psychoactive)]!"
						+ "</p>");
			case MUSKY:
				return UtilText.parse(owner,
						"<p>"
							+ "A gentle coolness rises up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out a soft sigh.<br/>"
							+ "[npc.NamePos] [npc.cum] is [style.boldShrink(no longer musky)]!"
						+ "</p>");
			case SLIMY:
				return UtilText.parse(owner,
						"<p>"
							+ "A calming coolness flows up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out a gentle sigh.<br/>"
							+ "[npc.NamePos] [npc.cum] is [style.boldShrink(no longer slimy)]!"
						+ "</p>");
			case STICKY:
				return UtilText.parse(owner,
						"<p>"
							+ "A soft warmth flows up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out a gentle sigh.<br/>"
							+ "[npc.NamePos] [npc.cum] is [style.boldShrink(no longer sticky)]!"
						+ "</p>");
			case VISCOUS:
				return UtilText.parse(owner,
						"<p>"
							+ "A soft coolness rises up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out a gentle sigh.<br/>"
							+ "[npc.NamePos] [npc.cum] is [style.boldShrink(no longer viscous)]!"
						+ "</p>");
			case MINERAL_OIL:
				return UtilText.parse(owner,
						"<p>"
							+ "A short relief flows up into [npc.namePos] [npc.balls], causing [npc.herHim] to let out a gentle sigh.<br/>"
							+ "[npc.NamePos] [npc.cum] is [style.boldShrink(no longer mineral oil)]!"
						+ "</p>");
		}
		
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}
	
	public List<ItemEffect> getTransformativeEffects() {
		return transformativeEffects;
	}
	
	public void addTransformativeEffect(ItemEffect ie) {
		transformativeEffects.add(ie);
	}

	/**
	 * DO NOT MODIFY!
	 */
	public Set<FluidModifier> getFluidModifiers() {
		return fluidModifiers;
	}

	public void clearFluidModifiers() {
		fluidModifiers.clear();
	}

	public float getValuePerMl() {
		return 0.1f * type.getValueModifier();
	}

	@Override
	public boolean isFeral(GameCharacter owner) {
		if(owner==null) {
			return false;
		}
		return owner.isFeral() || (owner.getLegConfiguration().getFeralParts().contains(FluidCum.class) && getType().getRace().isFeralPartsAvailable());
	}
}
