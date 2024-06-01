package com.lilithsthrone.game.character.body.abstractTypes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.controller.xmlParsing.XMLLoadException;
import com.lilithsthrone.controller.xmlParsing.XMLMissingTagException;
import com.lilithsthrone.main.Main;
import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.types.FluidType;
import com.lilithsthrone.game.character.body.valueEnums.FluidFlavour;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.character.body.valueEnums.FluidTypeBase;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;
import org.xml.sax.SAXException;

/**
 * @since 0.3.8.2
 * @version 0.4
 * @author Innoxia
 */
public class AbstractFluidType implements FluidType {

	private final String id;

	private final boolean mod;

	private final String transformationName;
	
	private final FluidTypeBase baseFluidType;
	private final FluidFlavour flavour;
	private final Race race;
	
	private final List<String> namesMasculine = new ArrayList<>();
	private final List<String> namesFeminine = new ArrayList<>();
	
	private final List<String> descriptorsMasculine = new ArrayList<>();
	private final List<String> descriptorsFeminine = new ArrayList<>();
	
	final List<FluidModifier> defaultFluidModifiers = new ArrayList<>();
	
	public AbstractFluidType(File XMLFile, String id, String author, boolean mod)
			throws XMLLoadException, IOException, SAXException, XMLMissingTagException {
		this.id = id;
		Document doc = Main.getDocBuilder().parse(XMLFile);
		// Cast magic:
		doc.getDocumentElement().normalize();
		Element coreElement = Element.getDocumentRootElement(XMLFile);
		this.mod = mod;
		this.race = Race.getRaceFromId(coreElement.getMandatoryFirstOf("race").getTextContent());
		this.baseFluidType = FluidTypeBase.valueOf(coreElement.getMandatoryFirstOf("baseFluidType").getTextContent());
		this.flavour = FluidFlavour.valueOf(coreElement.getMandatoryFirstOf("flavour").getTextContent());
		this.transformationName = coreElement.getMandatoryFirstOf("transformationName").getTextContent();
		for(Element e : coreElement.getMandatoryFirstOf("namesMasculine").getAllOf("name")) {
			namesMasculine.add(e.getTextContent());
		}
		for(Element e : coreElement.getMandatoryFirstOf("namesFeminine").getAllOf("name")) {
			namesFeminine.add(e.getTextContent());
		}
		if(coreElement.getOptionalFirstOf("descriptorsMasculine").isPresent()) {
			for(Element e : coreElement.getMandatoryFirstOf("descriptorsMasculine").getAllOf("descriptor")) {
				descriptorsMasculine.add(e.getTextContent());
			}
		}
		if(coreElement.getOptionalFirstOf("descriptorsFeminine").isPresent()) {
			for(Element e : coreElement.getMandatoryFirstOf("descriptorsFeminine").getAllOf("descriptor")) {
				descriptorsFeminine.add(e.getTextContent());
			}
		}
		if(coreElement.getOptionalFirstOf("defaultFluidModifiers").isPresent()) {
			for(Element e : coreElement.getMandatoryFirstOf("defaultFluidModifiers").getAllOf("modifier")) {
				defaultFluidModifiers.add(FluidModifier.valueOf(e.getTextContent()));
			}
		}
	}

	@Override
	public boolean isMod() {
		return mod;
	}

	@Override
	public boolean isFromExternalFile() {
		return true;
	}
	
	@Override
	public String getTransformationNameOverride() {
		return transformationName;
	}
	
	@Override
	public String toString() {
		System.err.println("Warning! AbstractFluidType is calling toString()");
		return super.toString();
	}

	public String getId() {
		return id;
	}

	@Override
	public String getNameSingular(GameCharacter gc) {
		String name;
		
		if(gc==null || gc.isFeminine()) {
			if(namesFeminine==null || namesFeminine.isEmpty()) {
				return FluidType.super.getNameSingular(gc);
			}
			name = Util.randomItemFrom(namesFeminine);
			
		} else {
			if(namesMasculine==null || namesMasculine.isEmpty()) {
				return FluidType.super.getNameSingular(gc);
			}
			name = Util.randomItemFrom(namesMasculine);
		}
		
		if(name==null || name.isEmpty()) {
			return FluidType.super.getNameSingular(gc);
		}
		if(name.endsWith("-")) {
			// 25% chance to return this '-' name.
			return (Math.random()<0.25f ? name : "") + FluidType.super.getNameSingular(gc);
		}
		return name;
	}

	@Override
	public String getDescriptor(GameCharacter gc) {
		if(gc==null || gc.isFeminine()) {
			if(descriptorsFeminine==null || descriptorsFeminine.isEmpty()) {
				return "";
			}
			return Util.randomItemFrom(descriptorsFeminine);
			
		} else {
			if(descriptorsMasculine==null || descriptorsMasculine.isEmpty()) {
				return "";
			}
			return Util.randomItemFrom(descriptorsMasculine);
		}
	}

	@Override
	public Race getRace() {
		return race;
	}

	@Override
	public FluidTypeBase getBaseType() {
		return baseFluidType;
	}

	@Override
	public FluidFlavour getFlavour() {
		return flavour;
	}

	@Override
	public List<FluidModifier> getDefaultFluidModifiers() {
		return defaultFluidModifiers;
	}
}
