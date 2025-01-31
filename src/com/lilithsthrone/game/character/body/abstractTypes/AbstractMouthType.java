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
import com.lilithsthrone.game.character.body.types.MouthType;
import com.lilithsthrone.game.character.body.types.TongueType;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.3.7
 * @version 0.4
 * @author Innoxia
 */
public abstract class AbstractMouthType implements MouthType {

	private boolean mod;
	private boolean fromExternalFile;
	
	private BodyCoveringType coveringType;
	private Race race;
	private TongueType tongueType;
	
	private List<String> names;
	private List<String> namesPlural;
	
	private List<String> descriptorsMasculine;
	private List<String> descriptorsFeminine;
	
	private List<String> lipNames;
	private List<String> lipNamesPlural;
	
	private boolean lipDescriptorsMasculineSizeAllowed;
	private boolean lipDescriptorsFeminineSizeAllowed;
	private List<String> lipDescriptorsMasculine;
	private List<String> lipDescriptorsFeminine;
	
	private String mouthBodyDescription;
	
	List<OrificeModifier> defaultRacialOrificeModifiers;

	public AbstractMouthType(Race race, TongueType tongueType) {
		this(BodyCoveringType.MOUTH,
				race,
				tongueType,
				null,
				null,
				Util.newArrayListOfValues(""),
				Util.newArrayListOfValues(""),
				Util.newArrayListOfValues("lip"),
				Util.newArrayListOfValues("lips"),
				Util.newArrayListOfValues(""),
				Util.newArrayListOfValues("soft", "delicate"),
				null,
				Util.newArrayListOfValues());
	}
	
	/**
	 * @param coveringType What covers this mouth type (i.e skin/fur/feather type). This is never used, as skin type covering mouth is determined by torso covering.
	 * @param race What race has this mouth type.
	 * @param tongueType The type of tongue this mouth contains.
	 * @param names A list of singular names for this mouth type. Pass in null to use generic names.
	 * @param namesPlural A list of plural names for this mouth type. Pass in null to use generic names.
	 * @param descriptorsMasculine The descriptors that can be used to describe a masculine form of this mouth type.
	 * @param mouthBodyDescription A sentence or two to describe this mouth type, as seen in the character view screen. It should follow the same format as all of the other entries in the MouthType class. Pass in null to use a generic description.
	 * @param descriptorsFeminine The descriptors that can be used to describe a feminine form of this mouth type.
	 */
	public AbstractMouthType(BodyCoveringType coveringType,
			Race race,
			TongueType tongueType,
			List<String> names,
			List<String> namesPlural,
			List<String> descriptorsMasculine,
			List<String> descriptorsFeminine,
			List<String> lipNames,
			List<String> lipNamesPlural,
			List<String> lipDescriptorsMasculine,
			List<String> lipDescriptorsFeminine,
			String mouthBodyDescription,
			List<OrificeModifier> defaultRacialOrificeModifiers) {
		
		this.coveringType = coveringType;
		this.race = race;
		this.tongueType = tongueType;
		
		this.names = names;
		this.namesPlural = namesPlural;
		
		this.descriptorsMasculine = descriptorsMasculine;
		this.descriptorsFeminine = descriptorsFeminine;
		
		this.lipNames = lipNames;
		this.lipNamesPlural = lipNamesPlural;

		this.lipDescriptorsMasculineSizeAllowed = true;
		this.lipDescriptorsFeminineSizeAllowed = true;
		
		this.lipDescriptorsMasculine = lipDescriptorsMasculine;
		this.lipDescriptorsFeminine = lipDescriptorsFeminine;
		
		if(mouthBodyDescription==null || mouthBodyDescription.isEmpty()) {
			this.mouthBodyDescription = "[npc.SheHasFull] [npc.lipSize], [npc.mouthColourPrimary(true)] [npc.lips]"
						+ "#IF(npc.isWearingLipstick())"
							+ "#IF(npc.isPiercedLip()), which have been pierced, and#ELSE, which#ENDIF"
							+ " are currently [npc.materialCompositionDescriptor]"
							+ "#IF(npc.isHeavyMakeup(BODY_COVERING_TYPE_MAKEUP_LIPSTICK) && game.isLipstickMarkingEnabled())"
								+ " a [style.colourPinkDeep(heavy layer)] of"
							+ "#ENDIF"
							+ " [#npc.getLipstick().getFullDescription(npc, true)]."
						+ "#ELSE"
							+ "#IF(npc.isPiercedLip()), which have been pierced#ENDIF."
						+ "#ENDIF"
						+ " [npc.Her] throat is [npc.mouthColourSecondary(true)] in colour.";
		} else {
			this.mouthBodyDescription = mouthBodyDescription;
		}

		if(defaultRacialOrificeModifiers==null) {
			this.defaultRacialOrificeModifiers = new ArrayList<>();
		} else {
			this.defaultRacialOrificeModifiers = defaultRacialOrificeModifiers;
		}
	}
	
	public AbstractMouthType(File XMLFile, String author, boolean mod) {
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
				
				this.tongueType = TongueType.table.of(coreElement.getMandatoryFirstOf("tongueType").getTextContent());
				
				this.names = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("names").getAllOf("name")) {
					names.add(e.getTextContent());
				}
				
				this.namesPlural = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("namesPlural").getAllOf("name")) {
					namesPlural.add(e.getTextContent());
				}
				
				this.descriptorsMasculine = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("descriptorsMasculine").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("descriptorsMasculine").getAllOf("descriptor")) {
						descriptorsMasculine.add(e.getTextContent());
					}
				}
				
				this.descriptorsFeminine = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("descriptorsFeminine").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("descriptorsFeminine").getAllOf("descriptor")) {
						descriptorsFeminine.add(e.getTextContent());
					}
				}
				
				// Lips:
				
				this.lipNames = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("lipNames").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("lipNames").getAllOf("name")) {
						lipNames.add(e.getTextContent());
					}
				} else {
					this.lipNames.add("lip");
				}
				
				this.lipNamesPlural = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("lipNamesPlural").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("lipNamesPlural").getAllOf("name")) {
						lipNamesPlural.add(e.getTextContent());
					}
				} else {
					this.lipNamesPlural.add("lips");
				}
				

				this.lipDescriptorsMasculine = new ArrayList<>();
				this.lipDescriptorsMasculineSizeAllowed = true;
				if(coreElement.getOptionalFirstOf("lipDescriptorsMasculine").isPresent()) {
					this.lipDescriptorsMasculineSizeAllowed = Boolean.valueOf(coreElement.getMandatoryFirstOf("lipDescriptorsMasculine").getAttribute("allowSizeDescriptors"));
					for(Element e : coreElement.getMandatoryFirstOf("lipDescriptorsMasculine").getAllOf("descriptor")) {
						lipDescriptorsMasculine.add(e.getTextContent());
					}
				}
				
				this.lipDescriptorsFeminine = new ArrayList<>();
				this.lipDescriptorsFeminineSizeAllowed = true;
				if(coreElement.getOptionalFirstOf("lipDescriptorsFeminine").isPresent()) {
					this.lipDescriptorsFeminineSizeAllowed = Boolean.valueOf(coreElement.getMandatoryFirstOf("lipDescriptorsFeminine").getAttribute("allowSizeDescriptors"));
					for(Element e : coreElement.getMandatoryFirstOf("lipDescriptorsFeminine").getAllOf("descriptor")) {
						lipDescriptorsFeminine.add(e.getTextContent());
					}
				} else {
					this.lipDescriptorsFeminine = Util.newArrayListOfValues("soft", "delicate");
				}
				
				// Other:
				
				this.mouthBodyDescription = coreElement.getMandatoryFirstOf("bodyDescription").getTextContent();
				
				this.defaultRacialOrificeModifiers = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("defaultOrificeModifiers").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("defaultOrificeModifiers").getAllOf("modifier")) {
						defaultRacialOrificeModifiers.add(OrificeModifier.valueOf(e.getTextContent()));
					}
				}
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("AbstractAnusType was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
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
	public TongueType getTongueType() {
		return tongueType;
	}

	@Override
	public String getNameSingular(GameCharacter gc) {
		if(names==null || names.isEmpty()) {
			return UtilText.returnStringAtRandom("mouth");
		}
		return Util.randomItemFrom(names);
	}
	
	@Override
	public String getNamePlural(GameCharacter gc) {
		if(namesPlural==null || names.isEmpty()) {
			return UtilText.returnStringAtRandom("mouths");
		}
		return Util.randomItemFrom(namesPlural);
	}

	@Override
	public String getDescriptor(GameCharacter gc) {
		if (gc.isFeminine()) {
			return Util.randomItemFrom(descriptorsFeminine);
		} else {
			return Util.randomItemFrom(descriptorsMasculine);
		}
	}

	@Override
	public String getLipsNameSingular(GameCharacter gc) {
		return Util.randomItemFrom(lipNames);
	}

	@Override
	public String getLipsNamePlural(GameCharacter gc) {
		return Util.randomItemFrom(lipNamesPlural);
	}

	@Override
	public boolean isLipsDescriptorSizeAllowed(GameCharacter gc) {
		if (gc.isFeminine()) {
			return lipDescriptorsFeminineSizeAllowed;
		} else {
			return lipDescriptorsMasculineSizeAllowed;
		}
	}

	@Override
	public List<String> getLipsDescriptors(GameCharacter gc) {
		if (gc.isFeminine()) {
			return (lipDescriptorsFeminine);
		} else {
			return (lipDescriptorsMasculine);
		}
	}

	@Override
	public BodyCoveringType getBodyCoveringType(Body body) {
		return coveringType;
	}

	@Override
	public Race getRace() {
		return race;
	}

	@Override
	public String getBodyDescription(GameCharacter owner) {
		return UtilText.parse(owner, mouthBodyDescription);
	}
	
	public List<OrificeModifier> getDefaultRacialOrificeModifiers() {
		return defaultRacialOrificeModifiers;
	}
}
