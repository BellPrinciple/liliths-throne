package com.lilithsthrone.game.character;

import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithsthrone.controller.xmlParsing.XMLUtil;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.Fluid;
import com.lilithsthrone.game.character.body.valueEnums.FluidTypeBase;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.race.RaceStage;
import com.lilithsthrone.game.character.race.Subspecies;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.XMLSaving;

/**
 * @since 0.2.7
 * @version 0.3.1
 * @author Innoxia
 */
public class FluidStored implements XMLSaving {
	
	private final String charactersFluidID;
//	private Subspecies cumSubspecies; // used for calculating pregnancy.
//	private Subspecies cumHalfDemonSubspecies; // used for calculating pregnancy.
	private final Body body; // Body has to be stored as otherwise the character who provided the cum could be transformed, and their new body would no longer reflect the genetics of this FluidStored
	private final boolean cumVirile;
	private final float virility;
	private final boolean feral;
	private final Fluid fluid;
	private float millilitres;

	public FluidStored(GameCharacter character, Fluid fluid, float millilitres) {
		this.charactersFluidID = character == null ? "" : character.getId();
		this.body = character == null ? null : new Body(character.getBody()); //TODO
		this.cumVirile = character == null || character.isVirile(Attribute.VIRILITY);
		this.virility = character == null ? 25f : character.getAttributeValue(Attribute.VIRILITY);
		this.feral = fluid.isFeral(character);
		this.fluid = new Fluid(fluid);
		this.millilitres = millilitres;
	}

	public FluidStored(String charactersFluidID, Body body, Fluid fluid, float millilitres) {
		GameCharacter owner = getNPCById(charactersFluidID);
		this.charactersFluidID = charactersFluidID;
		this.body = body;
		this.cumVirile = charactersFluidID != null && !charactersFluidID.isEmpty() && (owner == null || owner.isVirile(Attribute.VIRILITY));
		this.virility = charactersFluidID == null || charactersFluidID.isEmpty() ? 0f : owner == null ? 25f : owner.getAttributeValue(Attribute.VIRILITY);
		this.feral = fluid.isFeral(owner);
		this.fluid = new Fluid(fluid);
		this.millilitres = millilitres;
	}

	public FluidStored(String charactersFluidID, Fluid fluid, float millilitres) {
		GameCharacter owner = getNPCById(charactersFluidID);
		this.charactersFluidID = charactersFluidID;
		this.body = null;
		this.cumVirile = false;
		this.virility = 0;
		this.feral = fluid.isFeral(owner);
		this.fluid = new Fluid(fluid);
		this.millilitres = millilitres;
	}

	private FluidStored(
			String charactersFluidID,
			Body body,
			Fluid fluid,
			boolean feral,
			boolean cumVirile,
			float virility,
			float millilitres) {
		this.charactersFluidID = charactersFluidID;
		this.fluid = new Fluid(fluid);
		this.body = body;
		this.cumVirile = cumVirile;
		this.virility = virility;
		this.feral = feral;
		this.millilitres = millilitres;
	}

	private static GameCharacter getNPCById(String id) {
		if(id == null || id.isEmpty())
			return null;
		try {
			return Main.game.getNPCById(id);
		}
		catch(Exception x) {
			return null;
		}
	}

	@Override
	public boolean equals(Object o) {
		// Does not take into account quantity on purpose.
		if(o instanceof FluidStored){
			if(((FluidStored)o).getFluid().equals(this.getFluid())
					&& ((FluidStored)o).getCharactersFluidID().equals(this.getCharactersFluidID())
					&& ((FluidStored)o).isFeral() == this.isFeral()
					// For the purposes of FluidStored, it's good enough to make a very basic equality check for Body:
					&& (this.getBody()!=null && this.body!=null
						?Objects.equals(((FluidStored)o).getBody().getSubspecies(), this.body.getSubspecies())
								&& Objects.equals(((FluidStored)o).getBody().getGender(), this.body.getGender())
						:this.getBody()==null && this.body==null)
					&& ((FluidStored)o).isCumVirile() == this.isCumVirile()
					&& ((FluidStored)o).getVirility() == this.getVirility()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		// Does not take into account quantity on purpose.
		int result = 17;
		result = 31 * result + this.getFluid().hashCode();
		result = 31 * result + this.getCharactersFluidID().hashCode();
		result = 31 * result + (this.isFeral() ? 1 : 0);
		if(this.getBody()!=null) {
			result = 31 * result + this.getBody().getSubspecies().hashCode();
			result = 31 * result + this.getBody().getGender().hashCode();
		}
		result = 31 * result + (this.isCumVirile() ? 1 : 0);
		result = 31 * result + Float.floatToIntBits(this.getVirility());
		return result;
	}
	
	@Override
	public Element saveAsXML(Element parentElement, Document doc) {
		// Core:
		Element fluidStoredElement = doc.createElement("fluidStored");
		parentElement.appendChild(fluidStoredElement);
		XMLUtil.addAttribute(doc, fluidStoredElement, "charactersFluidID", charactersFluidID);
		XMLUtil.addAttribute(doc, fluidStoredElement, "bestial", String.valueOf(feral));
		XMLUtil.addAttribute(doc, fluidStoredElement, "cumVirile", String.valueOf(cumVirile));
		XMLUtil.addAttribute(doc, fluidStoredElement, "virility", String.valueOf(virility));
		XMLUtil.addAttribute(doc, fluidStoredElement, "millilitres", String.valueOf(millilitres));
		
		if(isCum()) {
//			XMLUtil.addAttribute(doc, fluidStoredElement, "cumSubspecies", Subspecies.getIdFromSubspecies(cumSubspecies));
//			if(cumHalfDemonSubspecies!=null) {
//				XMLUtil.addAttribute(doc, fluidStoredElement, "cumHalfDemonSubspecies", Subspecies.getIdFromSubspecies(cumHalfDemonSubspecies));
//			}
			//TODO need custom names for cum and girlcum
			Element bodyElement = doc.createElement("body");
			fluidStoredElement.appendChild(bodyElement);
			body.saveAsXML(bodyElement, doc);
			fluid.saveAsXML("fluidCum", fluidStoredElement, doc);
		}
		if(isMilk()) {
			fluid.saveAsXML("fluidMilk", fluidStoredElement, doc);
		}
		if(isGirlCum()) {
			fluid.saveAsXML("fluidGirlCum", fluidStoredElement, doc);
		}
		
		return fluidStoredElement;
	}
	
	public static FluidStored loadFromXML(StringBuilder log, Element parentElement, Document doc) {
		String ID = parentElement.getAttribute("charactersFluidID");
		float millilitres = Float.parseFloat(parentElement.getAttribute("millilitres"));
		boolean feral = false;
		boolean cumVirile = true;
		float virility = 25;
		try {
			feral = Boolean.parseBoolean(parentElement.getAttribute("bestial"));
			virility = Float.parseFloat(parentElement.getAttribute("virility"));
		} catch(Exception ex) {
		}
		if(!parentElement.getAttribute("cumVirile").isEmpty()) {
			cumVirile = Boolean.parseBoolean(parentElement.getAttribute("cumVirile"));
		}

		if(parentElement.getElementsByTagName("body").item(0)!=null) {
			// Cum:
			if(parentElement.getElementsByTagName("fluidCum").item(0)!=null) {
				Body body = Body.loadFromXML(log, (Element) parentElement.getElementsByTagName("body").item(0), doc);
				Fluid fluid = Fluid.loadFromXML("fluidCum", parentElement, doc);
				return new FluidStored(ID, body, fluid, feral, cumVirile, virility, millilitres);
			}

		} else { // Old version support:
			// Milk:
			if(parentElement.getElementsByTagName("milk").item(0)!=null) {
				Fluid fluid = Fluid.loadFromXML("milk", parentElement, doc);
				return new FluidStored(ID, null, fluid, feral, false, 0, millilitres);

			} else if(parentElement.getElementsByTagName("fluidMilk").item(0)!=null) {
				Fluid fluid = Fluid.loadFromXML("fluidMilk", parentElement, doc);
				return new FluidStored(ID, null, fluid, feral, false, 0, millilitres);
			}

			// Girlcum:
			if(parentElement.getElementsByTagName("girlcum").item(0)!=null) {
				Fluid fluid = Fluid.loadFromXML("girlcum", parentElement, doc);
				return new FluidStored(ID, null, fluid, feral, false, 0, millilitres);

			} else if(parentElement.getElementsByTagName("fluidGirlCum").item(0)!=null) {
				Fluid fluid = Fluid.loadFromXML("fluidGirlCum", parentElement, doc);
				return new FluidStored(ID, null, fluid, feral, false, 0, millilitres);
			}

			// Cum:
			if(parentElement.getElementsByTagName("cum").item(0)!=null) {
			Subspecies subspecies = Subspecies.HUMAN;
			Subspecies halfDemonSubspecies = Subspecies.HUMAN;
				try {
					subspecies = Subspecies.getSubspeciesFromId(parentElement.getAttribute("cumSubspecies"));
					halfDemonSubspecies = Subspecies.getSubspeciesFromId(parentElement.getAttribute("cumHalfDemonSubspecies"));
				} catch(Exception ex) {
				}
				Body body = subspecies==Subspecies.HALF_DEMON
							?Main.game.getCharacterUtils().generateHalfDemonBody(null, Gender.M_P_MALE, halfDemonSubspecies, false)
							:Main.game.getCharacterUtils().generateBody(null, Gender.M_P_MALE, subspecies, RaceStage.GREATER);
				Fluid fluid = Fluid.loadFromXML("cum", parentElement, doc);
				return new FluidStored(ID, body, fluid, feral, cumVirile, virility, millilitres);
			}
		}




		System.err.println("WARNING: FluidStored failed to load!");
		new Exception().printStackTrace();

		return null;
	}
	
	
	public String getCharactersFluidID() {
		return charactersFluidID;
	}
	
	/**
	 * @return The character whose fluid this is.
	 * @throws Exception A NullPointerException if the character does not exist.
	 */
	public GameCharacter getFluidCharacter() throws Exception {
		if(charactersFluidID.equals(Main.game.getPlayer().getId())) {
			return Main.game.getPlayer();
		}
		return Main.game.getNPCById(charactersFluidID);
	}
	
	public boolean isCum() {
		return fluid.getType().getBaseType() == FluidTypeBase.CUM;
	}

	public boolean isMilk() {
		return fluid.getType().getBaseType() == FluidTypeBase.MILK;
	}

	public boolean isGirlCum() {
		return fluid.getType().getBaseType() == FluidTypeBase.GIRLCUM;
	}
	
	public Fluid getFluid() {
		return fluid;
	}

	/**
	 * @return Body associated with this fluid for the purposes of impregnation.
	 */
	public Body getBody() {
		return body;
	}
	
	public boolean isFeral() {
		return feral;
	}

	public boolean isCumVirile() {
		return cumVirile;
	}

	public float getVirility() {
		return virility;
	}

	public float getMillilitres() {
		return millilitres;
	}
	
	public void setMillilitres(float millilitres) {
		this.millilitres = Math.max(0, millilitres);
	}
	
	public void incrementMillilitres(float increment) {
		setMillilitres(this.millilitres + increment);
	}
	
}
