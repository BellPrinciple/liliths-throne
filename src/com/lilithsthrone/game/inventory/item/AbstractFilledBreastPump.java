package com.lilithsthrone.game.inventory.item;

import com.lilithsthrone.game.character.body.valueEnums.FluidTypeBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithsthrone.controller.xmlParsing.XMLUtil;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Fluid;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.sex.SexAreaOrifice;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.XMLSaving;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.2.1
 * @version 0.4.0
 * @author Innoxia
 */
public class AbstractFilledBreastPump extends AbstractItem implements XMLSaving {
	
	private String milkProvider;
	private Fluid milk;
	private int millilitresStored;
	
	public AbstractFilledBreastPump(ItemType itemType, Colour colour, GameCharacter milkProvider, Fluid milk, int millilitresStored) {
		super(itemType);
		
		this.milkProvider = milkProvider.getId();
		this.milk = new Fluid(milk.getType(), milk.isCrotchMilk());
		this.milk.setFlavour(milkProvider, milk.getFlavour());
		for(FluidModifier fm : milk.getFluidModifiers()) {
			this.milk.addFluidModifier(milkProvider, fm);
		}
		this.setColour(0, colour);
		SVGString = getSVGString(itemType.getPathNameInformation().get(0).getPathName(), colour);
		this.millilitresStored = millilitresStored;
	}
	
	public AbstractFilledBreastPump(ItemType itemType, Colour colour, String milkProviderId, Fluid milk, int millilitresStored) {
		super(itemType);
		
		this.milkProvider = milkProviderId;
		this.milk = new Fluid(milk.getType(), milk.isCrotchMilk());
		this.milk.setFlavour(null, milk.getFlavour());
		for(FluidModifier fm : milk.getFluidModifiers()) {
			this.milk.addFluidModifier(null, fm);
		}
		this.setColour(0, colour);
		SVGString = getSVGString(itemType.getPathNameInformation().get(0).getPathName(), colour);
		this.millilitresStored = millilitresStored;
	}
	
	@Override
	public boolean equals(Object o) {
		if(super.equals(o)) {
			return (o instanceof AbstractFilledBreastPump)
					&& ((AbstractFilledBreastPump)o).getMilkProviderId().equals(this.getMilkProviderId())
					&& ((AbstractFilledBreastPump)o).getMilk().equals(milk);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + milkProvider.hashCode();
		result = 31 * result + milk.hashCode();
		return result;
	}

	@Override
	public Element saveAsXML(Element parentElement, Document doc) {
		Element element = doc.createElement("item");
		parentElement.appendChild(element);
		
		XMLUtil.addAttribute(doc, element, "id", this.getItemType().getId());
		XMLUtil.addAttribute(doc, element, "colour", this.getColour(0).getId());
		XMLUtil.addAttribute(doc, element, "milkProvider", this.getMilkProviderId());
		XMLUtil.addAttribute(doc, element, "millilitresStored", String.valueOf(this.getMillilitresStored()));
		
		Element innerElement = doc.createElement("itemEffects");
		element.appendChild(innerElement);
		for(ItemEffect ie : this.getEffects()) {
			ie.saveAsXML(innerElement, doc);
		}

		innerElement = doc.createElement("milk");
		element.appendChild(innerElement);
		this.getMilk().saveAsXML(innerElement, doc);
		
		return element;
	}

	public static AbstractFilledBreastPump loadFromXML(Element parentElement, Document doc) {
		String provider = parentElement.getAttribute("milkProvider");
		if(provider.isEmpty()) {
			provider = parentElement.getAttribute("milkProvidor"); // Support for old versions in which I could not spell
		}
		return new AbstractFilledBreastPump(
				ItemType.table.exact(parentElement.getAttribute("id")).orElse(null),
				PresetColour.getColourFromId(parentElement.getAttribute("colour")),
				provider,
				Fluid.loadFromXML("milk", parentElement, FluidTypeBase.MILK, false),
				(parentElement.getAttribute("millilitresStored").isEmpty()
					?25
					:Integer.valueOf(parentElement.getAttribute("millilitresStored"))));
	}
	
	private String getSVGString(String pathName, Colour colour) {
		String s = SvgUtil.loadFromResource("/com/lilithsthrone/res/items/" + pathName + ".svg");
		s = SvgUtil.colourReplacement(String.valueOf(hashCode()), colour, s);
		return s;
	}
	
	@Override
	public String applyEffect(GameCharacter user, GameCharacter target) {
		return target.ingestFluid(getMilkProvider(), milk, SexAreaOrifice.MOUTH, millilitresStored)
				+ target.addItem(Main.game.getItemGen().generateItem(ItemType.MOO_MILKER_EMPTY), false);
	}
	
	public String getMilkProviderId() {
		return milkProvider;
	}
	
	public GameCharacter getMilkProvider() {
		try {
			return Main.game.getNPCById(milkProvider);
		} catch (Exception e) {
			Util.logGetNpcByIdError("getMilkProvider()", milkProvider);
			return null;
		}
	}

	public Fluid getMilk() {
		return milk;
	}

	public int getMillilitresStored() {
		return millilitresStored;
	}

	public void setMillilitresStored(int millilitresStored) {
		this.millilitresStored = millilitresStored;
	}
	
}
