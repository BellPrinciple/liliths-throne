package com.lilithsthrone.game.character.body.abstractTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.main.Main;
import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.types.TesticleType;
import com.lilithsthrone.game.character.body.types.FluidType;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.3.8.8
 * @version 0.4
 * @author Innoxia
 */
public abstract class AbstractTesticleType implements TesticleType {

	private boolean mod;
	private boolean fromExternalFile;

	private BodyCoveringType coveringType;
	private Race race;
	private FluidType fluidType;
	private boolean internal;
	
	private List<String> names;
	private List<String> namesPlural;
	private List<String> descriptors;
	
	/**
	 * @param coveringType What covers this testicle type (i.e skin/fur/feather type).
	 * @param race What race has this testicle type.
	 * @param fluidType The type of cum that these testicles produce.
	 * @param internal If these testicles are internal by default.
	 * @param names A list of singular names for this testicle type. Pass in null to use generic names.
	 * @param namesPlural A list of plural names for this testicle type. Pass in null to use generic names.
	 * @param descriptors The descriptors that can be used for this testicle type.
	 */
	public AbstractTesticleType(BodyCoveringType coveringType,
			Race race,
			FluidType fluidType,
			boolean internal,
			List<String> names,
			List<String> namesPlural,
			List<String> descriptors) {
		
		this.coveringType = coveringType;
		this.race = race;
		this.fluidType = fluidType;
		this.internal = internal;
		
		this.names = names;
		this.namesPlural = namesPlural;
		this.descriptors = descriptors;
	}
	
	public AbstractTesticleType(BodyCoveringType skinType,
			Race race,
			FluidType fluidType,
			boolean internal) {
		this(skinType,
				race,
				fluidType,
				internal,
				null, null, null);
	}
	
	public AbstractTesticleType(File XMLFile, String author, boolean mod) {
		if (XMLFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(XMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element coreElement = Element.getDocumentRootElement(XMLFile);

				this.mod = mod;
				this.fromExternalFile = true;

				this.race = Race.table.of(coreElement.getMandatoryFirstOf("race").getTextContent());
				this.coveringType = BodyCoveringType.table.of(coreElement.getMandatoryFirstOf("coveringType").getTextContent());
				
				this.fluidType = FluidType.getFluidTypeFromId(coreElement.getMandatoryFirstOf("fluidType").getTextContent());

				this.internal = Boolean.valueOf(coreElement.getMandatoryFirstOf("internal").getTextContent());
				
				this.names = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("names").getAllOf("name")) {
					names.add(e.getTextContent());
				}
				
				this.namesPlural = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("namesPlural").getAllOf("name")) {
					namesPlural.add(e.getTextContent());
				}

				this.descriptors = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("descriptors").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("descriptors").getAllOf("descriptor")) {
						descriptors.add(e.getTextContent());
					}
				}
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("AbstractTesticleType was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
			}
		}
	}

	@Override
	public boolean isMod() {
		return mod;
	}

	@Override
	public boolean isFromExternalFile() {
		return fromExternalFile;
	}

	@Override
	public FluidType getFluidType() {
		return fluidType;
	}

	@Override
	public boolean isInternal() {
		return internal;
	}

	@Override
	public String getNameSingular(GameCharacter gc) {
		if(names==null || names.isEmpty()) {
			return UtilText.returnStringAtRandom("ball", "testicle");
		}
		return Util.randomItemFrom(names);
	}
	
	@Override
	public String getNamePlural(GameCharacter gc) {
		if(namesPlural==null || namesPlural.isEmpty()) {
			return UtilText.returnStringAtRandom("balls", "testicles");
		}
		return Util.randomItemFrom(namesPlural);
	}

	@Override
	public String getDescriptor(GameCharacter gc) {
		if(descriptors==null || descriptors.isEmpty()) {
			return "";
		}
		return Util.randomItemFrom(descriptors);
	}

	@Override
	public BodyCoveringType getBodyCoveringType(Body body) {
//		if(body!=null) {
//			return body.getTorso().getBodyCoveringType(body);
//		}
		return coveringType;
	}

	@Override
	public Race getRace() {
		return race;
	}
	
}
