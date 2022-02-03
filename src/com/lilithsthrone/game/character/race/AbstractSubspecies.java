package com.lilithsthrone.game.character.race;

import java.io.File;
import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.Covering;
import com.lilithsthrone.game.character.body.valueEnums.Affinity;
import com.lilithsthrone.game.character.body.valueEnums.CoveringPattern;
import com.lilithsthrone.game.character.body.valueEnums.Femininity;
import com.lilithsthrone.game.character.body.valueEnums.LegConfiguration;
import com.lilithsthrone.game.character.effects.PerkCategory;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.npc.misc.Elemental;
import com.lilithsthrone.game.character.persona.PersonalityTrait;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.rendering.SVGImages;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.Season;
import com.lilithsthrone.world.WorldRegion;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

/**
 * @since 0.4
 * @version 0.4.0
 * @author Innoxia
 */
public abstract class AbstractSubspecies implements Subspecies {

	String id;
	private boolean mod;
	private boolean fromExternalFile;

	private boolean mainSubspecies;
	
	private int baseSlaveValue;
	private int subspeciesOverridePriority;
	
	private boolean shortStature;
	private boolean bipedalSubspecies;
	private boolean winged;
	private boolean doesNotAge;

	private Map<PersonalityTrait, Float> personalityChanceOverrides;
	
	private String applySubspeciesChanges;
	private String subspeciesWeighting;
	
	private String attributeItemId;
	private String transformativeItemId;
	
	private Map<LegConfiguration, String[]> anthroNames;
	private Map<LegConfiguration, String[]> anthroNamesSillyMode;
	private Map<LegConfiguration, String[]> halfDemonNames;
	
	private FeralAttributes feralAttributes;
	
	private Nocturnality nocturnality;

	private String statusEffectDescription;
	private Map<PerkCategory, Integer> perkWeightingFeminine;
	private Map<PerkCategory, Integer> perkWeightingMasculine;
	private Map<Attribute,Float> statusEffectAttributeModifiers;
	private List<String> extraEffects;

	private String bookName;
	private String bookNamePlural;
	private String bookIdFolderPath;
	private String basicDescriptionId;
	private String advancedDescriptionId;
	
	private Race race;
	private Affinity affinity;
	private SubspeciesPreference subspeciesPreferenceDefault;
	private String description;

	// SVGs:
	private Colour colour;
	private Colour secondaryColour;
	private Colour tertiaryColour;
	
	protected int iconSize;
	protected String pathName;
	protected String backgroundPathName;
	protected boolean externalFileBackground;
	
	protected String SVGString;
	protected String SVGStringUncoloured;
	protected String SVGStringUncolouredNoBackground;
	protected String SVGStringNoBackground;
	protected String SVGStringDesaturated;
	protected String slimeSVGString;
	protected String dollSVGString;
	protected String dollSVGStringDesaturated;
	protected String halfDemonSVGString;
	protected String demonSVGString;
	
	protected String bookPathName;
	protected String bookSVGString;

	private Map<WorldRegion, SubspeciesSpawnRarity> regionLocations;
	private Map<WorldType, SubspeciesSpawnRarity> worldLocations;
	private Map<PlaceType,SubspeciesSpawnRarity> placeLocations;
	
	private List<SubspeciesFlag> flags;

	protected static Map<Integer, String> youkoIconMap;
	protected static Map<Integer, String> youkoDesaturatedIconMap;
	protected static Map<Integer, String> youkoHalfDemonIconMap;
	
	public static Map<LegConfiguration, String[]> demonLegConfigurationNames = Util.newHashMapOfValues(
			new Value<>(LegConfiguration.ARACHNID,
					new String[] {
						"demonid",
						"demonids",
						"incunid",
						"succunid",
						"incunids",
						"succunids"}),
			new Value<>(LegConfiguration.BIPEDAL,
					new String[] {
						"demon",
						"demons",
						"incubus",
						"succubus",
						"incubi",
						"succubi"}),
			new Value<>(LegConfiguration.CEPHALOPOD,
					new String[] {
						"demopus",
						"demopuses",
						"incupus",
						"succupus",
						"incupuses",
						"succupuses"}),
			new Value<>(LegConfiguration.QUADRUPEDAL,
					new String[] {
						"demotaur",
						"demotaurs",
						"incutaur",
						"succutaur",
						"incutaurs",
						"succutaurs"}),
			new Value<>(LegConfiguration.TAIL,
					new String[] {
						"demomer",
						"demomers",
						"incumer",
						"succumer",
						"incumers",
						"succumers"}),
			new Value<>(LegConfiguration.TAIL_LONG,
					new String[] {
						"demomia",
						"demomias",
						"incumia",
						"succumia",
						"incumias",
						"succumias"}),
			new Value<>(LegConfiguration.AVIAN,
					new String[] {
						"demoa",
						"demoas",
						"incumoa",
						"succumoa",
						"incumoas",
						"succumoas"}));
	
	static {
		youkoIconMap = new HashMap<>();
		youkoHalfDemonIconMap = new HashMap<>();
		for(int i=1; i<=9; i++) {
			String SVGStringBackground = SvgUtil.loadFromResource(
					"/com/lilithsthrone/res/statusEffects/race/raceBackground.svg");
			SVGStringBackground = "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>"+SVGStringBackground+"</div>";
			String baseSVGString = SVGStringBackground + "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>"+SVGImages.SVG_IMAGE_PROVIDER.getFoxTail(i)+"</div>";
			youkoIconMap.put(i, baseSVGString);
			baseSVGString = SvgUtil.colourReplacement("youkohalfDemon"+i,
						PresetColour.RACE_HALF_DEMON,
						PresetColour.RACE_HALF_DEMON,
						PresetColour.RACE_HALF_DEMON,
						"<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>" + SVGImages.SVG_IMAGE_PROVIDER.getRaceBackgroundDemon()+"</div>"
							+ "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>"+SVGImages.SVG_IMAGE_PROVIDER.getFoxTailDemon(i)+"</div>");
			youkoHalfDemonIconMap.put(i, baseSVGString);
		}
		youkoDesaturatedIconMap = new HashMap<>();
		for(int i=1; i<=9; i++) {
			String SVGStringBackground = SvgUtil.loadFromResource("/com/lilithsthrone/res/statusEffects/race/raceBackground.svg");
			SVGStringBackground = "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>"+SVGStringBackground+"</div>";
			String baseSVGString = SVGStringBackground + "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>"+SVGImages.SVG_IMAGE_PROVIDER.getFoxTailDesaturated(i)+"</div>";
			baseSVGString = SvgUtil.colourReplacement("youkoGradient"+i,
					PresetColour.BASE_GREY,
					PresetColour.BASE_GREY,
					PresetColour.BASE_GREY,
					baseSVGString);
			youkoDesaturatedIconMap.put(i, baseSVGString);
		}
	}
	
	public AbstractSubspecies(
			boolean mainSubspecies,
			int baseSlaveValue,
			String attributeItemId,
			String transformativeItemId,
			String pathName,
			String backgroundPathName,
			String name,
			String namePlural,
			String singularMaleName,
			String singularFemaleName,
			String pluralMaleName,
			String pluralFemaleName,
			FeralAttributes feralAttributes,
			Nocturnality nocturnality,
			String statusEffectDescription,
			Map<Attribute,Float> statusEffectAttributeModifiers,
			List<String> extraEffects,
			String bookName,
			String bookNamePlural,
			String basicDescription,
			String advancedDescription,
			Race race,
			Map<PerkCategory, Integer> perkWeightingFeminine,
			Map<PerkCategory, Integer> perkWeightingMasculine,
			Colour colour,
			SubspeciesPreference subspeciesPreferenceDefault,
			String description,
			Map<WorldRegion, SubspeciesSpawnRarity> regionLocations,
			Map<WorldType, SubspeciesSpawnRarity> worldLocations,
			Map<PlaceType,SubspeciesSpawnRarity> placeLocations, List<SubspeciesFlag> flags) {
		
		this.mainSubspecies = mainSubspecies;
		
		this.baseSlaveValue = baseSlaveValue;
		this.subspeciesOverridePriority = 0;
		
		this.winged = false;
		this.shortStature = false;
		this.bipedalSubspecies = true;
		this.doesNotAge = false;

		this.attributeItemId = attributeItemId;
		this.transformativeItemId = transformativeItemId;

		this.personalityChanceOverrides = new HashMap<>();
		
		this.anthroNames = new HashMap<>();
		this.anthroNames.put(null, new String[] {
				name,
				namePlural,
				singularMaleName,
				singularFemaleName,
				pluralMaleName,
				pluralFemaleName
		});
		this.anthroNamesSillyMode = new HashMap<>();
		this.halfDemonNames = new HashMap<>();
		
		this.feralAttributes = feralAttributes;
		this.nocturnality = nocturnality;

		this.statusEffectDescription = statusEffectDescription;
		
		this.statusEffectAttributeModifiers = statusEffectAttributeModifiers;
		if(this.statusEffectAttributeModifiers!=null) {
			this.statusEffectAttributeModifiers.entrySet().removeIf((entry) -> entry.getValue()==0);
		}
		
		if(perkWeightingFeminine!=null) {
			this.perkWeightingFeminine = perkWeightingFeminine;
		} else {
			this.perkWeightingFeminine = new HashMap<>();
		}
		if(perkWeightingMasculine!=null) {
			this.perkWeightingMasculine = perkWeightingMasculine;
		} else {
			this.perkWeightingMasculine = new HashMap<>();
		}

		if(extraEffects == null) {
			this.extraEffects = new ArrayList<>();
		} else {
			this.extraEffects = extraEffects;
		}
		
		this.bookName = bookName;
		this.bookNamePlural = bookNamePlural;
		
		this.bookIdFolderPath = "";
		this.basicDescriptionId = basicDescription;
		this.advancedDescriptionId = advancedDescription;

		this.colour = colour;
		this.secondaryColour = colour;
		this.tertiaryColour = colour;
		
		this.race = race;
		this.affinity = Affinity.AMPHIBIOUS;
		this.subspeciesPreferenceDefault = subspeciesPreferenceDefault;
		this.description = description;
		
		if(regionLocations == null) {
			this.regionLocations = new HashMap<>();
		} else {
			this.regionLocations = regionLocations;
		}
		
		if(worldLocations == null) {
			this.worldLocations = new HashMap<>();
		} else {
			this.worldLocations = worldLocations;
		}
		
		if(placeLocations == null) {
			this.placeLocations = new HashMap<>();
		} else {
			this.placeLocations = placeLocations;
		}
		
		if(flags == null) {
			this.flags = new ArrayList<>();
		} else {
			this.flags = flags;
		}
		
		this.externalFileBackground = false;
		this.pathName = "/com/lilithsthrone/res/" + pathName;
		this.bookPathName = "/com/lilithsthrone/res/" + pathName;
		this.backgroundPathName = "/com/lilithsthrone/res/" + backgroundPathName;
		this.SVGString = null;
		this.iconSize = 80;
	}
	
	public AbstractSubspecies(File XMLFile, String author, boolean mod) {
		if (XMLFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(XMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element coreElement = Element.getDocumentRootElement(XMLFile);
				
				this.mod = mod;
				this.fromExternalFile = true;

				this.race = Race.table.of(coreElement.getMandatoryFirstOf("race").getTextContent());
				this.affinity = coreElement.getOptionalFirstOf("affinity").isPresent() ? Affinity.getAffinityFromId(coreElement.getMandatoryFirstOf("affinity").getTextContent()) : Affinity.AMPHIBIOUS;

				String secondaryColourText = coreElement.getMandatoryFirstOf("secondaryColour").getTextContent();
				String tertiaryColourText = coreElement.getMandatoryFirstOf("tertiaryColour").getTextContent();
				this.colour = PresetColour.getColourFromId(coreElement.getMandatoryFirstOf("colour").getTextContent());
				this.secondaryColour = secondaryColourText.isEmpty() ? this.colour : PresetColour.getColourFromId(secondaryColourText);
				this.tertiaryColour = tertiaryColourText.isEmpty() ? this.colour : PresetColour.getColourFromId(tertiaryColourText);
				
				this.mainSubspecies = Boolean.valueOf(coreElement.getMandatoryFirstOf("mainSubspecies").getTextContent());
				this.baseSlaveValue = Integer.valueOf(coreElement.getMandatoryFirstOf("baseSlaveValue").getTextContent());

				this.attributeItemId = coreElement.getMandatoryFirstOf("attributeItemId").getTextContent();
				if(this.attributeItemId.isEmpty()) {
					this.attributeItemId = "innoxia_race_human_vanilla_water";
				}
				
				this.transformativeItemId = coreElement.getMandatoryFirstOf("transformativeItemId").getTextContent();
				if(this.transformativeItemId.isEmpty()) {
					this.transformativeItemId = "innoxia_race_human_bread_roll";
				}
				
				this.subspeciesOverridePriority = Integer.valueOf(coreElement.getMandatoryFirstOf("subspeciesOverridePriority").getTextContent());
				
				this.nocturnality = Nocturnality.DIURNAL;
				if(coreElement.getOptionalFirstOf("nocturnality").isPresent()) {
					this.nocturnality = Nocturnality.valueOf(coreElement.getMandatoryFirstOf("nocturnality").getTextContent());
				}
				this.shortStature = Boolean.valueOf(coreElement.getMandatoryFirstOf("shortStature").getTextContent());
				this.bipedalSubspecies = Boolean.valueOf(coreElement.getMandatoryFirstOf("bipedalSubspecies").getTextContent());
				this.winged = coreElement.getOptionalFirstOf("winged").isPresent() ? Boolean.valueOf(coreElement.getMandatoryFirstOf("winged").getTextContent()) : false;
				this.doesNotAge = coreElement.getOptionalFirstOf("doesNotAge").isPresent() ? Boolean.valueOf(coreElement.getMandatoryFirstOf("doesNotAge").getTextContent()) : false;

				personalityChanceOverrides = new HashMap<>();
				if(coreElement.getOptionalFirstOf("personalityChances").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("personalityChances").getAllOf("entry")) {
						try {
							personalityChanceOverrides.put(PersonalityTrait.valueOf(e.getTextContent()), Float.valueOf(e.getAttribute("chance")));
						} catch(Exception ex) {
							System.err.println("AbstractSubspecies error: PersonalityTrait '"+e.getTextContent()+"' failed to load!");
						}
					}
				}
				
				this.applySubspeciesChanges = coreElement.getMandatoryFirstOf("applySubspeciesChanges").getTextContent();
				this.subspeciesWeighting = coreElement.getMandatoryFirstOf("subspeciesWeighting").getTextContent();
				
				this.pathName = XMLFile.getParentFile().getAbsolutePath() + "/"+ coreElement.getMandatoryFirstOf("iconName").getTextContent();
				if(!coreElement.getMandatoryFirstOf("iconName").getAttribute("displaySize").isEmpty()) {
					this.iconSize = Integer.valueOf(coreElement.getMandatoryFirstOf("iconName").getAttribute("displaySize"));
				} else {
					this.iconSize = 100;
				}
				
				if(coreElement.getOptionalFirstOf("backgroundName").isPresent() && !coreElement.getMandatoryFirstOf("backgroundName").getTextContent().isEmpty()) {
					this.backgroundPathName = XMLFile.getParentFile().getAbsolutePath() + "/"+ coreElement.getMandatoryFirstOf("backgroundName").getTextContent();
					this.externalFileBackground = true;
				} else {
					this.backgroundPathName = "/com/lilithsthrone/res/statusEffects/race/raceBackground";
					this.externalFileBackground = false;
				}
				this.SVGString = null;
				
				this.bookPathName = XMLFile.getParentFile().getAbsolutePath() + "/" + coreElement.getMandatoryFirstOf("bookIconName").getTextContent();
				
				this.bookName = coreElement.getMandatoryFirstOf("bookName").getTextContent();
				this.bookNamePlural = bookName; // There is no need for a plural
				
				this.bookIdFolderPath = XMLFile.getParentFile().getAbsolutePath();
				bookIdFolderPath = "res"+bookIdFolderPath.split("\\bres\\b")[1];
//				System.out.println(bookIdFolderPath);
				this.basicDescriptionId = coreElement.getMandatoryFirstOf("basicDescriptionId").getTextContent();
				this.advancedDescriptionId = coreElement.getMandatoryFirstOf("advancedDescriptionId").getTextContent();
				
				this.subspeciesPreferenceDefault = SubspeciesPreference.valueOf(coreElement.getMandatoryFirstOf("defaultPreference").getTextContent());

				this.anthroNames = new HashMap<>();
				if(!coreElement.getOptionalFirstOf("nameAnthro").isPresent()) { // Old version support
					String name = coreElement.getMandatoryFirstOf("name").getTextContent();
					String namePlural = coreElement.getMandatoryFirstOf("namePlural").getTextContent();
					String singularMaleName = coreElement.getMandatoryFirstOf("singularMaleName").getTextContent();
					String singularFemaleName = coreElement.getMandatoryFirstOf("singularFemaleName").getTextContent();
					String pluralMaleName = coreElement.getMandatoryFirstOf("pluralMaleName").getTextContent();
					String pluralFemaleName = coreElement.getMandatoryFirstOf("pluralFemaleName").getTextContent();

					this.anthroNames.put(null, new String[] {
							name,
							namePlural,
							singularMaleName,
							singularFemaleName,
							pluralMaleName,
							pluralFemaleName
					});
					
				} else {
					Element defaultNamesElement = coreElement.getMandatoryFirstOf("nameAnthro").getMandatoryFirstOf("namesDefault");
					String name = defaultNamesElement.getMandatoryFirstOf("name").getTextContent();
					String namePlural = defaultNamesElement.getMandatoryFirstOf("namePlural").getTextContent();
					String singularMaleName = defaultNamesElement.getMandatoryFirstOf("singularMaleName").getTextContent();
					String singularFemaleName = defaultNamesElement.getMandatoryFirstOf("singularFemaleName").getTextContent();
					String pluralMaleName = defaultNamesElement.getMandatoryFirstOf("pluralMaleName").getTextContent();
					String pluralFemaleName = defaultNamesElement.getMandatoryFirstOf("pluralFemaleName").getTextContent();
					this.anthroNames.put(null, new String[] {
							name,
							namePlural,
							singularMaleName,
							singularFemaleName,
							pluralMaleName,
							pluralFemaleName
					});
					
					for(Element namesElement : coreElement.getMandatoryFirstOf("nameAnthro").getAllOf("names")) {
						LegConfiguration legConfiguration = LegConfiguration.valueOf(namesElement.getAttribute("legConfiguration"));
						name = namesElement.getMandatoryFirstOf("name").getTextContent();
						namePlural = namesElement.getMandatoryFirstOf("namePlural").getTextContent();
						singularMaleName = namesElement.getMandatoryFirstOf("singularMaleName").getTextContent();
						singularFemaleName = namesElement.getMandatoryFirstOf("singularFemaleName").getTextContent();
						pluralMaleName = namesElement.getMandatoryFirstOf("pluralMaleName").getTextContent();
						pluralFemaleName = namesElement.getMandatoryFirstOf("pluralFemaleName").getTextContent();

						this.anthroNames.put(legConfiguration, new String[] {
								name,
								namePlural,
								singularMaleName,
								singularFemaleName,
								pluralMaleName,
								pluralFemaleName
						});
					}
				}
				
				this.anthroNamesSillyMode = new HashMap<>();
				if(coreElement.getOptionalFirstOf("nameAnthroSillyMode").isPresent()) {
					Element defaultNamesElement = coreElement.getMandatoryFirstOf("nameAnthroSillyMode").getMandatoryFirstOf("namesDefault");
					String name = defaultNamesElement.getMandatoryFirstOf("name").getTextContent();
					String namePlural = defaultNamesElement.getMandatoryFirstOf("namePlural").getTextContent();
					String singularMaleName = defaultNamesElement.getMandatoryFirstOf("singularMaleName").getTextContent();
					String singularFemaleName = defaultNamesElement.getMandatoryFirstOf("singularFemaleName").getTextContent();
					String pluralMaleName = defaultNamesElement.getMandatoryFirstOf("pluralMaleName").getTextContent();
					String pluralFemaleName = defaultNamesElement.getMandatoryFirstOf("pluralFemaleName").getTextContent();
					this.anthroNamesSillyMode.put(null, new String[] {
							name,
							namePlural,
							singularMaleName,
							singularFemaleName,
							pluralMaleName,
							pluralFemaleName
					});
					
					for(Element namesElement : coreElement.getMandatoryFirstOf("nameAnthroSillyMode").getAllOf("names")) {
						LegConfiguration legConfiguration = LegConfiguration.valueOf(namesElement.getAttribute("legConfiguration"));
						name = namesElement.getMandatoryFirstOf("name").getTextContent();
						namePlural = namesElement.getMandatoryFirstOf("namePlural").getTextContent();
						singularMaleName = namesElement.getMandatoryFirstOf("singularMaleName").getTextContent();
						singularFemaleName = namesElement.getMandatoryFirstOf("singularFemaleName").getTextContent();
						pluralMaleName = namesElement.getMandatoryFirstOf("pluralMaleName").getTextContent();
						pluralFemaleName = namesElement.getMandatoryFirstOf("pluralFemaleName").getTextContent();

						this.anthroNamesSillyMode.put(legConfiguration, new String[] {
								name,
								namePlural,
								singularMaleName,
								singularFemaleName,
								pluralMaleName,
								pluralFemaleName
						});
					}
				}
				
				this.description = coreElement.getMandatoryFirstOf("description").getTextContent();

				this.halfDemonNames = new HashMap<>();
				if(coreElement.getOptionalFirstOf("nameHalfDemon").isPresent()) {
					if(!coreElement.getMandatoryFirstOf("nameHalfDemon").getOptionalFirstOf("namesDefault").isPresent()) { // Old version support
						String name = coreElement.getMandatoryFirstOf("nameHalfDemon").getTextContent();
						String namePlural = coreElement.getMandatoryFirstOf("namePluralHalfDemon").getTextContent();
						String singularMaleName = coreElement.getMandatoryFirstOf("singularMaleNameHalfDemon").getTextContent();
						String singularFemaleName = coreElement.getMandatoryFirstOf("singularFemaleNameHalfDemon").getTextContent();
						String pluralMaleName = coreElement.getMandatoryFirstOf("pluralMaleNameHalfDemon").getTextContent();
						String pluralFemaleName = coreElement.getMandatoryFirstOf("pluralFemaleNameHalfDemon").getTextContent();
	
						this.halfDemonNames.put(null, new String[] {
								name,
								namePlural,
								singularMaleName,
								singularFemaleName,
								pluralMaleName,
								pluralFemaleName
						});
						
					} else {
						Element defaultNamesElement = coreElement.getMandatoryFirstOf("nameHalfDemon").getMandatoryFirstOf("namesDefault");
						String name = defaultNamesElement.getMandatoryFirstOf("name").getTextContent();
						String namePlural = defaultNamesElement.getMandatoryFirstOf("namePlural").getTextContent();
						String singularMaleName = defaultNamesElement.getMandatoryFirstOf("singularMaleName").getTextContent();
						String singularFemaleName = defaultNamesElement.getMandatoryFirstOf("singularFemaleName").getTextContent();
						String pluralMaleName = defaultNamesElement.getMandatoryFirstOf("pluralMaleName").getTextContent();
						String pluralFemaleName = defaultNamesElement.getMandatoryFirstOf("pluralFemaleName").getTextContent();
						this.halfDemonNames.put(null, new String[] {
								name,
								namePlural,
								singularMaleName,
								singularFemaleName,
								pluralMaleName,
								pluralFemaleName
						});
						
						for(Element namesElement : coreElement.getMandatoryFirstOf("nameHalfDemon").getAllOf("names")) {
							LegConfiguration legConfiguration = LegConfiguration.valueOf(namesElement.getAttribute("legConfiguration"));
							name = namesElement.getMandatoryFirstOf("name").getTextContent();
							namePlural = namesElement.getMandatoryFirstOf("namePlural").getTextContent();
							singularMaleName = namesElement.getMandatoryFirstOf("singularMaleName").getTextContent();
							singularFemaleName = namesElement.getMandatoryFirstOf("singularFemaleName").getTextContent();
							pluralMaleName = namesElement.getMandatoryFirstOf("pluralMaleName").getTextContent();
							pluralFemaleName = namesElement.getMandatoryFirstOf("pluralFemaleName").getTextContent();
	
							this.halfDemonNames.put(legConfiguration, new String[] {
									name,
									namePlural,
									singularMaleName,
									singularFemaleName,
									pluralMaleName,
									pluralFemaleName
							});
						}
					}
				}
				
				this.feralAttributes = null;
				if(coreElement.getOptionalFirstOf("feralAttributes").isPresent()
						&& (coreElement.getMandatoryFirstOf("feralAttributes").getOptionalFirstOf("feralName").isPresent() || coreElement.getMandatoryFirstOf("feralAttributes").getOptionalFirstOf("name").isPresent())) {
					try {
						Element feralElement = coreElement.getMandatoryFirstOf("feralAttributes");
						
						float serpentTailLength = 0.2f;
						if(feralElement.getOptionalFirstOf("serpentTailLength").isPresent()) {
							serpentTailLength = Float.valueOf(feralElement.getMandatoryFirstOf("serpentTailLength").getTextContent());
						}
						
						String name = "";
						String namePlural = "";
						String singularMaleName = "";
						String singularFemaleName = "";
						String pluralMaleName = "";
						String pluralFemaleName = "";
						
						if(feralElement.getOptionalFirstOf("feralName").isPresent()) { // Old version naming support:
							name = feralElement.getMandatoryFirstOf("feralName").getTextContent();
							namePlural = feralElement.getMandatoryFirstOf("feralNamePlural").getTextContent();
							singularMaleName = feralElement.getMandatoryFirstOf("feralSingularMaleName").getTextContent();
							singularFemaleName = feralElement.getMandatoryFirstOf("feralSingularFemaleName").getTextContent();
							pluralMaleName = feralElement.getMandatoryFirstOf("feralPluralMaleName").getTextContent();
							pluralFemaleName = feralElement.getMandatoryFirstOf("feralPluralFemaleName").getTextContent();
							
						} else {
							name = feralElement.getMandatoryFirstOf("name").getTextContent();
							namePlural = feralElement.getMandatoryFirstOf("namePlural").getTextContent();
							singularMaleName = feralElement.getMandatoryFirstOf("singularMaleName").getTextContent();
							singularFemaleName = feralElement.getMandatoryFirstOf("singularFemaleName").getTextContent();
							pluralMaleName = feralElement.getMandatoryFirstOf("pluralMaleName").getTextContent();
							pluralFemaleName = feralElement.getMandatoryFirstOf("pluralFemaleName").getTextContent();
						}
						
						this.feralAttributes = new FeralAttributes(
								name,
								namePlural,
								singularMaleName,
								singularFemaleName,
								pluralMaleName,
								pluralFemaleName,

								LegConfiguration.valueOf(feralElement.getMandatoryFirstOf("legConfiguration").getTextContent()),
								Boolean.valueOf(feralElement.getMandatoryFirstOf("sizeHeight").getTextContent()),
								Integer.valueOf(feralElement.getMandatoryFirstOf("size").getTextContent()),
								serpentTailLength,

								Integer.valueOf(feralElement.getMandatoryFirstOf("breastRowCount").getTextContent()),
								Integer.valueOf(feralElement.getMandatoryFirstOf("nipplesPerBreastCount").getTextContent()),
								Integer.valueOf(feralElement.getMandatoryFirstOf("crotchBreastRowCount").getTextContent()),
								Integer.valueOf(feralElement.getMandatoryFirstOf("nipplesPerCrotchBreastCount").getTextContent()),

								Boolean.valueOf(feralElement.getMandatoryFirstOf("armsOrWingsPresent").getTextContent()),
								Boolean.valueOf(feralElement.getMandatoryFirstOf("fingerActionsAvailable").getTextContent()),
								Boolean.valueOf(feralElement.getMandatoryFirstOf("hairPresent").getTextContent()));
					} catch(Exception ex) {
						System.err.println("Error in AbstractSubspecies loading: feralAttributes failed to initialise!<br/>"+ex.getMessage());
					}
				}

				this.statusEffectDescription = coreElement.getMandatoryFirstOf("statusEffectDescription").getTextContent();
				
				this.statusEffectAttributeModifiers = new LinkedHashMap<>();
				for(Element e : coreElement.getMandatoryFirstOf("statusEffectAttributeModifiers").getAllOf("attribute")) {
					statusEffectAttributeModifiers.put(Attribute.getAttributeFromId(e.getTextContent()), Float.valueOf(e.getAttribute("value")));
				}
				this.statusEffectAttributeModifiers.entrySet().removeIf((entry) -> entry.getValue()==0);
				
				this.perkWeightingFeminine = new HashMap<>();
				this.perkWeightingMasculine = new HashMap<>();
				for(Element e : coreElement.getMandatoryFirstOf("perkWeightings").getAllOf("category")) {
					PerkCategory cat = PerkCategory.valueOf(e.getTextContent());
					perkWeightingFeminine.put(cat, Integer.valueOf(e.getAttribute("feminineWeighting")));
					perkWeightingMasculine.put(cat, Integer.valueOf(e.getAttribute("masculineWeighting")));
				}
				
				this.extraEffects = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("extraEffects").getAllOf("effect")) {
					extraEffects.add(e.getTextContent());
				}

				this.regionLocations = new HashMap<>();
				if(coreElement.getOptionalFirstOf("regionLocations").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("regionLocations").getAllOf("region")) {
						regionLocations.put(WorldRegion.valueOf(e.getTextContent()), SubspeciesSpawnRarity.getSubspeciesSpawnRarityFromString(e.getAttribute("rarity")));
					}
				}
				
				this.worldLocations = new HashMap<>();
				if(coreElement.getOptionalFirstOf("worldLocations").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("worldLocations").getAllOf("world")) {
						worldLocations.put(WorldType.getWorldTypeFromId(e.getTextContent()), SubspeciesSpawnRarity.getSubspeciesSpawnRarityFromString(e.getAttribute("rarity")));
					}
				}
				
				this.placeLocations = new HashMap<>();
				if(coreElement.getOptionalFirstOf("placeLocations").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("placeLocations").getAllOf("place")) {
						placeLocations.put(PlaceType.getPlaceTypeFromId(e.getTextContent()), SubspeciesSpawnRarity.getSubspeciesSpawnRarityFromString(e.getAttribute("rarity")));
					}
				}
				
				this.flags = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("flags").getAllOf("flag")) {
					flags.add(SubspeciesFlag.getSubspeciesFlagFromString(e.getTextContent()));
				}
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("AbstractSubspecies was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
			}
		}
	}
	
	public boolean isMod() {
		return mod;
	}

	public boolean isFromExternalFile() {
		return fromExternalFile;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		new AccessException("WARNING: AbstractSubspecies is calling toString()!").printStackTrace(System.err);
		return id;
	}

	@Override
	public Map<PersonalityTrait, Float> getPersonalityTraitChances() {
		Map<PersonalityTrait, Float> map = new HashMap<>();
		
		if(this.fromExternalFile && personalityChanceOverrides!=null) {
			for(Entry<PersonalityTrait, Float> entry : personalityChanceOverrides.entrySet()) {
				map.put(entry.getKey(), entry.getValue());
			}
		}
		
		return map;
	}

	@Override
	public void applySpeciesChanges(Body body) {
		// Removed check for Main.game.isStarted() in v0.4.2.5 as it was causing NPCs to spawn in as incorrect subspecies
		// Tested from new game and everything worked fine, but also added try/catch block to make sure that any unexpected errors don't cause the game to lock up
		if(this.isFromExternalFile()) {
			try {
				UtilText.setBodyForParsing("targetedBody", body);
				UtilText.parse(applySubspeciesChanges);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

//	/**
//	 * @return The race of this body if it were made from flesh. (i.e. The body's race ignoring slime/elemental modifiers.)
//	 */
//	public static AbstractSubspecies getFleshSubspecies(GameCharacter character) {
//		return character.getFleshSubspecies();
////		return getSubspeciesFromBody(character.getBody(), character.getBody().getRaceFromPartWeighting());
//	}

	@Override
	public int getSubspeciesWeighting(Body body, Race race) {
		if(this.isFromExternalFile() && Main.game.isStarted()) {
			UtilText.setBodyForParsing("targetedBody", body);
			UtilText.setRaceForParsing("targetedRace", race);
//			if(race==Race.HUMAN) {
//				new IllegalArgumentException().printStackTrace();
//			}
			return Integer.valueOf(UtilText.parse(subspeciesWeighting).trim());
		}
		return 0;
	}

	protected static void applyFoxColoring(Body body) {
		Colour c1 = body.getCoverings().get(BodyCoveringType.FOX_FUR).getPrimaryColour();
		Colour c2 = PresetColour.COVERING_WHITE;
		CoveringPattern pat = CoveringPattern.MARKED;
		
		if(c1==PresetColour.COVERING_BLACK) {
			c2 = c1;
			pat = CoveringPattern.NONE;
		}
		
		// Old:
//		if(c1==PresetColour.COVERING_BROWN) {
//			if(rand<0.5f) {
//				c2 = PresetColour.COVERING_BROWN;
//				pat = CoveringPattern.NONE;
//			} else {
//				c2 = PresetColour.COVERING_TAN;
//			}
//			
//		} else if(c1==PresetColour.COVERING_BROWN_DARK) {
//			if(rand<0.5f) {
//				c2 = PresetColour.COVERING_BROWN_DARK;
//				pat = CoveringPattern.NONE;
//			} else {
//				c2 = PresetColour.COVERING_BROWN;
//			}
//			
//		} else if(c1==PresetColour.COVERING_SILVER || c1==PresetColour.COVERING_GREY) {
//			if(rand<0.5f) {
//				c2 = c1;
//				pat = CoveringPattern.NONE;
//			}
//			
//		} else if(c1==PresetColour.COVERING_BLACK) {
//			if(rand<0.5f) {
//				c2 = PresetColour.COVERING_BLACK;
//				pat = CoveringPattern.NONE;
//			} else {
//				c2 = PresetColour.COVERING_GREY;
//			}
//			
//		} else if(c1==PresetColour.COVERING_TAN || c1==PresetColour.COVERING_WHITE) {
//			c2 = c1;
//			pat = CoveringPattern.NONE;
//			
//		} else {
//			// Set primary colour to GINGER if we have a colour that otherwise wouldn't be accounted for.
//			if(c1 != PresetColour.COVERING_BLONDE) {c1 = PresetColour.COVERING_GINGER;}
//			if(rand<0.025f) {
//				c2 = PresetColour.COVERING_BLACK;
//			} else if(rand<0.05f) {
//				c2 = PresetColour.COVERING_BROWN;
//			} else if(rand<0.5f) {
//				c2 = PresetColour.COVERING_GREY;
//			}
//		}
				
		body.getCoverings().put(BodyCoveringType.FOX_FUR, new Covering(BodyCoveringType.FOX_FUR, pat, c1, false, c2, false));
	}


//	public static Body getPreGeneratedBody(GameCharacter linkedCharacter, Gender startingGender, GameCharacter mother, GameCharacter father) {
//		return getPreGeneratedBody(linkedCharacter, startingGender, mother.getTrueSubspecies(), mother.getHalfDemonSubspecies(), father.getTrueSubspecies(), father.getHalfDemonSubspecies());
//	}

	/**
	 * Only used for subspecies that have special offspring generation - i.e. demons.<br/>
	 * <b>Please note:</b> If the mother is feral, this will be overridden in CharacterUtils.generateBody()!<br/><br/>
	 *
	 * <b>Demon breeding</b><br/>
	 * Lilin<br/>
	 * + lilin = lilin<br/>
	 * + demon = demon<br/>
	 * + half-demon = half-demon<br/>
	 * + human half-demon = human half-demon<br/>
	 * + non-demon = half-demon<br/>
	 * + imps = alpha-imps<br/>
	 * Demon<br/>
	 * + lilin = demon<br/>
	 * + demon = demon<br/>
	 * + half-demon = half-demon<br/>
	 * + human half-demon = human half-demon<br/>
	 * + non-demon = half-demon<br/>
	 * + imps = alpha-imps<br/>
	 * Half-demon<br/>
	 * + lilin = half-demon<br/>
	 * + demon = half-demon<br/>
	 * + half-demon = half-demon<br/>
	 * + human half-demon = human half-demon<br/>
	 * + non-demon = half-demon<br/>
	 * + imps = alpha-imps<br/>
	 * Human half-demon<br/>
	 * + lilin = human half-demon<br/>
	 * + demon = human half-demon<br/>
	 * + half-demon = human half-demon<br/>
	 * + human half-demon = imps<br/>
	 * + non-demon = imps<br/>
	 * + imps = imps<br/>
	 * Imps and alpha-imps<br/>
	 * + anything = imps<br/>
	 * @return The pre-generated body to use as an offspring's core body.
	 */
	public static Body getPreGeneratedBody(GameCharacter linkedCharacter,
			Gender startingGender,
			Body motherBody,
			Body fatherBody
//			Subspecies motherSubspecies,
//			Subspecies motherHalfDemonSubspecies,
//			Subspecies fatherSubspecies,
//			Subspecies fatherHalfDemonSubspecies
			) {

		Subspecies motherSubspecies = motherBody.getTrueSubspecies();
		Subspecies motherHalfDemonSubspecies = motherBody.getHalfDemonSubspecies();
		Subspecies fatherSubspecies = fatherBody.getTrueSubspecies();
		Subspecies fatherHalfDemonSubspecies = fatherBody.getHalfDemonSubspecies();


		if(startingGender==null) {
			startingGender = Gender.getGenderFromUserPreferences(Math.random()<motherSubspecies.getRace().getChanceForMaleOffspring()?Femininity.MASCULINE:Femininity.FEMININE);
		}

		Body preGeneratedBody = null;

		// Any type of demonic mother will result in special cases for offspring:
		if(motherSubspecies==Subspecies.ELDER_LILIN || motherSubspecies==Subspecies.LILIN || motherSubspecies==Subspecies.DEMON) {
			if(fatherSubspecies==Subspecies.ELDER_LILIN || fatherSubspecies==Subspecies.LILIN) {
				if(motherSubspecies==Subspecies.ELDER_LILIN || motherSubspecies==Subspecies.LILIN) {
					preGeneratedBody = Main.game.getCharacterUtils().generateBody(linkedCharacter, startingGender, RacialBody.DEMON, Subspecies.LILIN, RaceStage.GREATER);
				} else {
					preGeneratedBody = Main.game.getCharacterUtils().generateBody(linkedCharacter, startingGender, RacialBody.DEMON, RaceStage.GREATER);
				}

			} else if(fatherSubspecies==Subspecies.DEMON) {
				preGeneratedBody = Main.game.getCharacterUtils().generateBody(linkedCharacter, startingGender, RacialBody.DEMON, RaceStage.GREATER);

			} else if(fatherSubspecies==Subspecies.HALF_DEMON) {
				preGeneratedBody = Main.game.getCharacterUtils().generateHalfDemonBody(linkedCharacter, startingGender, fatherHalfDemonSubspecies, true);

			} else if(fatherSubspecies==Subspecies.IMP || fatherSubspecies==Subspecies.IMP_ALPHA) {
				preGeneratedBody = Main.game.getCharacterUtils().generateBody(linkedCharacter, startingGender, RacialBody.DEMON, Subspecies.IMP_ALPHA, RaceStage.GREATER);

			} else {
				preGeneratedBody = Main.game.getCharacterUtils().generateHalfDemonBody(linkedCharacter, startingGender, fatherSubspecies, true);
			}

		} else if(motherSubspecies==Subspecies.HALF_DEMON) {
			if(motherHalfDemonSubspecies==Subspecies.HUMAN) {
				if(fatherSubspecies==Subspecies.ELDER_LILIN || fatherSubspecies==Subspecies.LILIN || fatherSubspecies==Subspecies.DEMON || fatherSubspecies==Subspecies.HALF_DEMON) {
					if(fatherSubspecies==Subspecies.HALF_DEMON && fatherHalfDemonSubspecies==Subspecies.HUMAN) {
						preGeneratedBody = Main.game.getCharacterUtils().generateBody(linkedCharacter, startingGender, RacialBody.DEMON, Subspecies.IMP, RaceStage.GREATER);
					} else {
						preGeneratedBody = Main.game.getCharacterUtils().generateHalfDemonBody(linkedCharacter, startingGender, motherHalfDemonSubspecies, true);
					}
				} else {
					preGeneratedBody = Main.game.getCharacterUtils().generateBody(linkedCharacter, startingGender, RacialBody.DEMON, Subspecies.IMP, RaceStage.GREATER);
				}

			} else {
				if(fatherSubspecies==Subspecies.ELDER_LILIN || fatherSubspecies==Subspecies.LILIN || fatherSubspecies==Subspecies.DEMON) {
					preGeneratedBody = Main.game.getCharacterUtils().generateHalfDemonBody(linkedCharacter, startingGender, motherHalfDemonSubspecies, true);

				} else if(fatherSubspecies==Subspecies.HALF_DEMON) { // If both are non-human half-demons, it's random as to whose species is birthed
					if(Math.random()<0.5f || fatherHalfDemonSubspecies==Subspecies.HUMAN) {
						preGeneratedBody = Main.game.getCharacterUtils().generateHalfDemonBody(linkedCharacter, startingGender, motherHalfDemonSubspecies, true);
					} else {
						preGeneratedBody = Main.game.getCharacterUtils().generateHalfDemonBody(linkedCharacter, startingGender, fatherHalfDemonSubspecies, true);
					}

				} else if(fatherSubspecies==Subspecies.IMP || fatherSubspecies==Subspecies.IMP_ALPHA) {
					preGeneratedBody = Main.game.getCharacterUtils().generateBody(linkedCharacter, startingGender, RacialBody.DEMON, Subspecies.IMP_ALPHA, RaceStage.GREATER);

				} else {
					preGeneratedBody = Main.game.getCharacterUtils().generateHalfDemonBody(linkedCharacter, startingGender, motherHalfDemonSubspecies, true);
				}
			}

		} else if(motherSubspecies==Subspecies.IMP_ALPHA || motherSubspecies==Subspecies.IMP) {
			if(fatherSubspecies==Subspecies.IMP) {
				preGeneratedBody = Main.game.getCharacterUtils().generateBody(linkedCharacter, startingGender, RacialBody.DEMON, Subspecies.IMP, RaceStage.GREATER);
			} else {
				preGeneratedBody = Main.game.getCharacterUtils().generateBody(linkedCharacter, startingGender, RacialBody.DEMON, Subspecies.IMP_ALPHA, RaceStage.GREATER);
			}

		} else {
			if(fatherSubspecies==Subspecies.ELDER_LILIN
					|| fatherSubspecies==Subspecies.LILIN
					|| fatherSubspecies==Subspecies.DEMON
					|| fatherSubspecies==Subspecies.HALF_DEMON
					|| fatherSubspecies==Subspecies.IMP
					|| fatherSubspecies==Subspecies.IMP_ALPHA) {
					// Just return this method, but with mother & father swapped, as all demonic offspring types are unaffected by who is the mother or father:
				preGeneratedBody = getPreGeneratedBody(linkedCharacter, startingGender, fatherBody, motherBody);
			}
		}

		// Apply genetics:
		if(preGeneratedBody!=null) {
			Main.game.getCharacterUtils().applyGenetics(linkedCharacter, preGeneratedBody, motherBody, fatherBody, preGeneratedBody.getRace()==motherBody.getRace());
		}

		return preGeneratedBody;
	}

	@Override
	public boolean isShortStature() {
		return shortStature;
	}

	@Override
	public boolean isNonBiped() {
		return !bipedalSubspecies;
	}

	// Items:

	@Override
	public String getAttributeItemId() {
		return attributeItemId;
	}

	@Override
	public String getTransformativeItemId() {
		return transformativeItemId;
	}

	@Override
	public ItemType getBook() {
		return ItemType.getLoreBook(this);
	}

	@Override
	public boolean isMainSubspecies() {
		return mainSubspecies;
	}

	@Override
	public int getSubspeciesOverridePriority() {
		return subspeciesOverridePriority;
	}
	
	private String getTaurEnding() {
		return getFeralName(null).charAt(getFeralName(null).length()-1)=='t'?"-taur":"taur";
	}
	
	protected String applyNonBipedNameChange(Body body, String baseName, boolean applyFeminineForm, boolean plural) {
		switch(body.getLegConfiguration()) {
			case ARACHNID:
				return baseName+"-arachne"+(plural?"s":"");
			case AVIAN:
				return baseName+"-moa"+(plural?"s":"");
			case WINGED_BIPED:
				return baseName+"-demimoa"+(plural?"s":"");
			case BIPEDAL:
				break;
			case CEPHALOPOD:
				return baseName+"-kraken"+(plural?"s":"");
			case TAIL:
				return "mer"+baseName+(plural?"s":"");
			case TAIL_LONG:
				return baseName+"-lamia"+(plural?"s":"");
			case QUADRUPEDAL:
				return baseName+getTaurEnding()+(applyFeminineForm?"ess"+(plural?"es":""):(plural?"s":""));
		}
		return baseName;
	}
	
	private Map<LegConfiguration, String[]> getAnthroNamesMap() {
		if(Main.game!=null && Main.game.isSillyMode() && anthroNamesSillyMode!=null && !anthroNamesSillyMode.isEmpty()) {
			return anthroNamesSillyMode;
		}
		return anthroNames;
	}

 	@Override
	public String getName(Body body) {
		if(body !=null) {
			if(this.isFeralConfigurationAvailable(body) && body.isFeral()) {
				return getFeralAttributes(body).getFeralName();
			}
			LegConfiguration conf = body.getLegConfiguration();
			if(getAnthroNamesMap().containsKey(conf)) {
				return getAnthroNamesMap().get(conf)[0];
			}
			if(body.getLegConfiguration()!=LegConfiguration.BIPEDAL && !isNonBiped()) {
				return applyNonBipedNameChange(body, getNonBipedRaceName(body), body.isFeminine(), false);
			}
		}
		return getAnthroNamesMap().get(null)[0];
	}

	@Override
	public String getNamePlural(Body body) {
		if(body !=null) {
			if(this.isFeralConfigurationAvailable(body) && body.isFeral()) {
				return getFeralAttributes(body).getFeralNamePlural();
			}
			LegConfiguration conf = body.getLegConfiguration();
			if(getAnthroNamesMap().containsKey(conf)) {
				return getAnthroNamesMap().get(conf)[1];
			}
			if(body.getLegConfiguration()!=LegConfiguration.BIPEDAL && !isNonBiped()) {
				return applyNonBipedNameChange(body, getNonBipedRaceName(body), body.isFeminine(), true);
			}
		}
		return getAnthroNamesMap().get(null)[1];
	}

	@Override
	public String getSingularMaleName(Body body) {
		if(body !=null) {
			if(this.isFeralConfigurationAvailable(body) && body.isFeral()) {
				return getFeralAttributes(body).getFeralSingularMaleName();
			}
			LegConfiguration conf = body.getLegConfiguration();
			if(getAnthroNamesMap().containsKey(conf)) {
				return getAnthroNamesMap().get(conf)[2];
			}
			if(body.getLegConfiguration()!=LegConfiguration.BIPEDAL && !isNonBiped()) {
				return applyNonBipedNameChange(body, getNonBipedRaceName(body), false, false);
			}
		}
		return getAnthroNamesMap().get(null)[2];
	}

	@Override
	public String getSingularFemaleName(Body body) {
		if(body !=null) {
			if(this.isFeralConfigurationAvailable(body) && body.isFeral()) {
				return getFeralAttributes(body).getFeralSingularFemaleName();
			}
			LegConfiguration conf = body.getLegConfiguration();
			if(getAnthroNamesMap().containsKey(conf)) {
				return getAnthroNamesMap().get(conf)[3];
			}
			if(body.getLegConfiguration()!=LegConfiguration.BIPEDAL && !isNonBiped()) {
				return applyNonBipedNameChange(body, getNonBipedRaceName(body), true, false);
			}
		}
		return getAnthroNamesMap().get(null)[3];
	}

	@Override
	public String getPluralMaleName(Body body) {
		if(body !=null) {
			if(this.isFeralConfigurationAvailable(body) && body.isFeral()) {
				return getFeralAttributes(body).getFeralPluralMaleName();
			}
			LegConfiguration conf = body.getLegConfiguration();
			if(getAnthroNamesMap().containsKey(conf)) {
				return getAnthroNamesMap().get(conf)[4];
			}
			if(body.getLegConfiguration()!=LegConfiguration.BIPEDAL && !isNonBiped()) {
				return applyNonBipedNameChange(body, getNonBipedRaceName(body), false, true);
			}
		}
		return getAnthroNamesMap().get(null)[4];
	}

	@Override
	public String getPluralFemaleName(Body body) {
		if(body !=null) {
			if(this.isFeralConfigurationAvailable(body) && body.isFeral()) {
				return getFeralAttributes(body).getFeralPluralFemaleName();
			}
			LegConfiguration conf = body.getLegConfiguration();
			if(getAnthroNamesMap().containsKey(conf)) {
				return getAnthroNamesMap().get(conf)[5];
			}
			if(body.getLegConfiguration()!=LegConfiguration.BIPEDAL && !isNonBiped()) {
				return applyNonBipedNameChange(body, getNonBipedRaceName(body), true, true);
			}
		}
		return getAnthroNamesMap().get(null)[5];
	}

	@Override
	public String getFeralName(Body body) {
		if(isFeralConfigurationAvailable(body)) {
			return getFeralAttributes(body).getFeralName();
		}
		return getAnthroNamesMap().get(null)[0];
	}

	@Override
	public String getFeralNamePlural(Body body) {
		if(isFeralConfigurationAvailable(body)) {
			return getFeralAttributes(body).getFeralNamePlural();
		}
		return getAnthroNamesMap().get(null)[1];
	}

	@Override
	public FeralAttributes getFeralAttributes(Body body) {
		return feralAttributes;
	}

	@Override
	public Nocturnality getNocturnality() {
		return nocturnality;
	}

	@Override
	public String getStatusEffectDescription(GameCharacter character) {
		return UtilText.parse(character, statusEffectDescription);
	}

	@Override
	public Map<Attribute, Float> getStatusEffectAttributeModifiers(GameCharacter character) {
		return statusEffectAttributeModifiers;
	}

	@Override
	public Map<PerkCategory, Integer> getPerkWeighting(GameCharacter character) {
		if(character==null || !character.isFeminine()) {
			return perkWeightingMasculine;
		}
		return perkWeightingFeminine;
	}

	@Override
	public List<String> getExtraEffects(GameCharacter character) {
		if(character!=null) {
			List<String> effectsModified = new ArrayList<>(extraEffects);
			
			int landSpeed = character.getLandSpeedModifier();
			int waterSpeed =  character.getWaterSpeedModifier();
			if(landSpeed!=0) {
				effectsModified.add((landSpeed<0?"[style.boldExcellent("+landSpeed+"%)]":"[style.boldTerrible(+"+landSpeed+"%)]")+" travel time on land");
			}
			if(waterSpeed!=0) {
				effectsModified.add((waterSpeed<0?"[style.boldExcellent("+waterSpeed+"%)]":"[style.boldTerrible(+"+waterSpeed+"%)]")+" travel time in water");
			}
			
			if(character.getLegConfiguration()==LegConfiguration.TAIL) {
				effectsModified.add("[style.boldTan(Grows legs on land)]");
				effectsModified.add("[style.boldBlueLight(Loses legs in water)]");
			}
			
			if(character.isFeral()) {
				for(String s : getFeralEffects()) {
					effectsModified.add(s);
				}
			}
			
			return effectsModified;
		}
		return extraEffects;
	}

	@Override
	public String getBookName() {
		return bookName;
	}

	@Override
	public String getBookNamePlural() {
		return bookNamePlural;
	}

	private File getXMLBookFile() {
		return new File(bookIdFolderPath+System.getProperty("file.separator")+"bookEntries.xml");
	}

	@Override
	public String getBookAuthor() {
		File bookFile = new File("res/txt/characters/raceInfo.xml");
		if(this.isFromExternalFile()) {
			bookFile = getXMLBookFile();
		}
		if(bookFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(bookFile);
				// Cast magic:
				doc.getDocumentElement().normalize();
				Element coreElement = Element.getDocumentRootElement(bookFile); // Loads the document and returns the root element

				for(Element element : coreElement.getAllOf("htmlContent")) {
					if(element.getAttribute("tag").equals(basicDescriptionId) && !element.getAttribute("author").isEmpty()) {
						return element.getAttribute("author");
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public String getBasicDescription(GameCharacter character) {
		if(this.isFromExternalFile() && getXMLBookFile().exists()) {
			return UtilText.parseFromXMLFile(new ArrayList<>(), bookIdFolderPath, "bookEntries", getBasicDescriptionId(), new ArrayList<>());
		}
		return UtilText.parseFromXMLFile("characters/raceInfo", getBasicDescriptionId());
	}

	@Override
	public String getAdvancedDescription(GameCharacter character) {
		if(this.isFromExternalFile() && getXMLBookFile().exists()) {
			return UtilText.parseFromXMLFile(new ArrayList<>(), bookIdFolderPath, "bookEntries", getAdvancedDescriptionId(), new ArrayList<>());
		}
		return UtilText.parseFromXMLFile("characters/raceInfo", getAdvancedDescriptionId());
	}

	@Override
	public String getBasicDescriptionId() {
		return basicDescriptionId;
	}

	@Override
	public String getAdvancedDescriptionId() {
		return advancedDescriptionId;
	}

	@Override
	public Race getRace() {
		return race;
	}

	@Override
	public Affinity getAffinity() {
		return affinity;
	}

	@Override
	public Affinity getAffinity(Body body) {
		switch (body.getLegConfiguration()) {
//			case ARACHNID:
//				return Affinity.TERRESTRIAL;
			case CEPHALOPOD:
			case TAIL:
				return Affinity.AQUATIC;
			default:
				return body.getHalfDemonSubspecies() != null
					? body.getHalfDemonSubspecies().getAffinity()
					: affinity;
		}
	}

	@Override
	public Affinity getAffinity(GameCharacter character) {
		switch (character.getLegConfiguration()) {
//			case ARACHNID:
//				return Affinity.TERRESTRIAL;
			case CEPHALOPOD:
			case TAIL:
				return Affinity.AQUATIC;
			default:
				return character.getHalfDemonSubspecies() != null
					? character.getHalfDemonSubspecies().getAffinity()
					: affinity;
		}
	}

	@Override
	public Colour getColour(GameCharacter character) {
		return colour;
	}

	@Override
	public Colour getSecondaryColour() {
		return secondaryColour;
	}

	@Override
	public Colour getTertiaryColour() {
		return tertiaryColour;
	}

	@Override
	public SubspeciesPreference getSubspeciesPreferenceDefault() {
		return subspeciesPreferenceDefault;
	}

	@Override
	public String getDescription(GameCharacter character) {
		return description;
	}

	@Override
	public boolean isWinged() {
		return winged;
	}

	@Override
	public boolean isDoesNotAge() {
		return doesNotAge;
	}

	@Override
	public boolean isAquatic() {
		return getAffinity() == Affinity.AQUATIC;
	}

	/**
	 * @param body The body being checked
	 * @return true if the supplied body has a LegConfiguration of type TAIL, or if its affinity is AQUATIC.
	 */
	@Override
	public boolean isAquatic(Body body) {
		return getAffinity(body) == Affinity.AQUATIC;
	}

	@Override
	public boolean isAquatic(GameCharacter character) {
		return getAffinity(character) == Affinity.AQUATIC;
	}

	@Override
	public String getPathName() {
		return pathName;
	}

	@Override
	public int getIconSize() {
		return iconSize;
	}

	@Override
	public String getBackgroundPathName() {
		return backgroundPathName;
	}

	protected String getBipedBackground(String svg, GameCharacter character, Colour colour) {
		return getBipedBackground(svg, character, colour, colour, colour);
	}
	
	protected String getBipedBackground(String svg, GameCharacter character, Colour colour, Colour secondaryColour, Colour tertiaryColour) {
		String returnString = svg;
		
		if(character!=null) {
			//character.isTorsoFeral() 
			if(character.isFeral() || (character.isElemental() && ((Elemental)character).getSummoner()!=null && !((Elemental)character).getSummoner().isElementalActive())) {
				String feralBackground = SvgUtil.loadFromResource("/com/lilithsthrone/res/statusEffects/race/raceBackgroundFeral.svg");
				feralBackground = "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>"+feralBackground+"</div>";
				feralBackground = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this)+"FERAL",
						colour,
						secondaryColour,
						tertiaryColour,
						feralBackground);
				returnString = returnString + "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>" + feralBackground +"</div>";
			} else {
				String backgroundPath = character.getLegConfiguration().getSubspeciesStatusEffectBackgroundPath();
				if(!backgroundPath.isEmpty()) {
					String SVGStringLegConfigurationBackground = SvgUtil.loadFromResource("/com/lilithsthrone/res/"+backgroundPath+".svg");
					SVGStringLegConfigurationBackground = "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>"+SVGStringLegConfigurationBackground+"</div>";
					SVGStringLegConfigurationBackground = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this)+"NBPID",
							colour,
							secondaryColour,
							tertiaryColour,
							SVGStringLegConfigurationBackground);
					returnString = SVGStringLegConfigurationBackground + "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>" + svg +"</div>";
				}
			}
		}
		return returnString;
	}

	protected void initBookSVGString() {
		if(this.isFromExternalFile()) {
			bookSVGString = Util.getFileContent(bookPathName + ".svg");
		} else {
			bookSVGString = SvgUtil.loadFromResource(bookPathName + ".svg");
		}
		bookSVGString = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this),
				colour,
				getSecondaryColour(),
				getTertiaryColour(),
				"<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;'>"+bookSVGString+"</div>");
	}
	
	protected void initSVGStrings() {
		if(getPathName()!=null) {
			String fullDivStyle = "width:100%;height:100%;margin:0;padding:0;position:absolute;left:0;bottom:0;";

			String path = getPathName() + ".svg";
			if(this.isFromExternalFile() || getPathName().startsWith("res")) {
				String image = Util.getFileContent(path);
				float iconResizeBorder = (100-getIconSize())/2f;
				SVGStringUncoloured = "<div style='width:"+getIconSize()+"%;height:"+getIconSize()+"%;position:absolute;left:"+iconResizeBorder+"%;bottom:"+iconResizeBorder+"%;'>"+image+"</div>";
			} else {
				SVGStringUncoloured = SvgUtil.loadFromResource(path);
			}

			String backgroundPath = getBackgroundPathName() + ".svg";
			String SVGStringBackground = "";
			if(this.externalFileBackground) {
				String image = Util.getFileContent(backgroundPath);
				SVGStringBackground = "<div style='"+fullDivStyle+"'>"+image+"</div>";
			} else {
				if(!getBackgroundPathName().isEmpty()) {
					SVGStringBackground = SvgUtil.loadFromResource(backgroundPath);
					SVGStringBackground = "<div style='"+fullDivStyle+"'>"+SVGStringBackground+"</div>";
				}
			}

				initBookSVGString();
				
				SVGStringNoBackground = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this),
						colour,
						getSecondaryColour(),
						getTertiaryColour(),
						"<div style='"+fullDivStyle+"'>"+SVGStringUncoloured+"</div>");
				
                SVGStringUncolouredNoBackground = "<div style='"+fullDivStyle+"'>"+SVGStringUncoloured+"</div>";
				SVGStringUncoloured = SVGStringBackground + "<div style='"+fullDivStyle+"'>"+SVGStringUncoloured+"</div>";
				
				slimeSVGString = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this),
						PresetColour.RACE_SLIME,
						PresetColour.RACE_SLIME,
						PresetColour.RACE_SLIME,
						"<div style='"+fullDivStyle+"'>" + SVGImages.SVG_IMAGE_PROVIDER.getRaceBackgroundSlime()+"</div>"
						+ "<div style='"+fullDivStyle+"'>"+SVGStringUncolouredNoBackground+"</div>");

				dollSVGString = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this),
						PresetColour.RACE_DOLL,
						PresetColour.RACE_DOLL,
						PresetColour.RACE_DOLL,
						"<div style='"+fullDivStyle+"'>" + SVGImages.SVG_IMAGE_PROVIDER.getRaceBackgroundDoll()+"</div>"
						+ (this==Subspecies.HUMAN
							?""
							:"<div style='"+fullDivStyle+"'>"+SVGStringUncolouredNoBackground+"</div>"));

				dollSVGStringDesaturated = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this),
						PresetColour.BASE_GREY,
						PresetColour.BASE_GREY,
						PresetColour.BASE_GREY,
						"<div style='"+fullDivStyle+"'>" + SVGImages.SVG_IMAGE_PROVIDER.getRaceBackgroundDoll()+"</div>");

				halfDemonSVGString = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this),
						PresetColour.RACE_HALF_DEMON,
						PresetColour.RACE_HALF_DEMON,
						PresetColour.RACE_HALF_DEMON,
						"<div style='"+fullDivStyle+"'>" + SVGImages.SVG_IMAGE_PROVIDER.getRaceBackgroundDemon()+"</div>"
						+ "<div style='"+fullDivStyle+"'>"+SVGStringUncolouredNoBackground+"</div>");

				demonSVGString = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this),
						PresetColour.RACE_DEMON,
						PresetColour.RACE_DEMON,
						PresetColour.RACE_DEMON,
						"<div style='"+fullDivStyle+"'>" + SVGImages.SVG_IMAGE_PROVIDER.getRaceBackgroundDemon()+"</div>"
						+ "<div style='"+fullDivStyle+"'>"+SVGStringUncolouredNoBackground+"</div>");
				
				SVGStringDesaturated = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this),
						PresetColour.BASE_GREY,
						PresetColour.BASE_GREY,
						PresetColour.BASE_GREY,
						SVGStringUncoloured);
				
				SVGString = SvgUtil.colourReplacement(Subspecies.getIdFromSubspecies(this),
						colour,
						getSecondaryColour(),
						getTertiaryColour(),
						SVGStringUncoloured);

			
		} else {
			SVGString = "";
		}
	}

	@Override
	public String getBookSVGString() {
		if(bookSVGString==null) {
			initBookSVGString();
		}
		return bookSVGString;
	}

	@Override
	public String getSVGString(GameCharacter character) {
		if(SVGString==null) {
			initSVGStrings();
		}
		return getBipedBackground(SVGString, character, this.getColour(character), this.getSecondaryColour(), this.getTertiaryColour());
	}

	@Override
	public String getSVGStringNoBackground() {
		if(SVGString==null) {
			initSVGStrings();
		}
		return SVGStringNoBackground;
	}

	@Override
	public String getSVGStringDesaturated(GameCharacter character) {
		return getSVGStringDesaturated(character, PresetColour.BASE_GREY);
	}

	@Override
	public String getSVGStringDesaturated(GameCharacter character, Colour colour) {
		if(SVGString==null) {
			initSVGStrings();
		}
		return getBipedBackground(SVGStringDesaturated, character, colour);
	}

	@Override
	public String getSlimeSVGString(GameCharacter character) {
		if(SVGString==null) {
			initSVGStrings();
		}
		return getBipedBackground(slimeSVGString, character, PresetColour.RACE_SLIME);
	}

	@Override
	public String getDollSVGString(GameCharacter character) {
		if(SVGString==null) {
			initSVGStrings();
		}
		return getBipedBackground(dollSVGString, character, PresetColour.RACE_DOLL);
	}

	@Override
	public String getDollSVGStringDesaturated(GameCharacter character) {
		if(SVGString==null) {
			initSVGStrings();
		}
		return getBipedBackground(dollSVGStringDesaturated, character, PresetColour.RACE_DOLL);
	}

	@Override
	public String getHalfDemonSVGString(GameCharacter character) {
		if(SVGString==null) {
			initSVGStrings();
		}
		if(character!=null && character.getSubspeciesOverride()!=null && character.getSubspeciesOverride().equals(Subspecies.DEMON)) {
			return getBipedBackground(demonSVGString, character, PresetColour.RACE_DEMON);
		} else {
			return getBipedBackground(halfDemonSVGString, character, PresetColour.RACE_HALF_DEMON);
		}
	}

	@Override
	public Map<WorldRegion, SubspeciesSpawnRarity> getRegionLocations() {
		return regionLocations;
	}

	@Override
	public Map<WorldType, SubspeciesSpawnRarity> getWorldLocations() {
		return worldLocations;
	}

	@Override
	public Map<PlaceType, SubspeciesSpawnRarity> getPlaceLocations() {
		return placeLocations;
	}

	@Override
	public List<WorldRegion> getMostCommonWorldRegions() {
		//TODO super
		List<WorldRegion> mostCommonRegion = Util.newArrayListOfValues();
		SubspeciesSpawnRarity highestRarity = SubspeciesSpawnRarity.ONE;
		for(Map.Entry<WorldRegion, SubspeciesSpawnRarity> entry : getRegionLocations().entrySet()) {
			if(entry.getValue().getChanceMultiplier()>=highestRarity.getChanceMultiplier()) {
				if(entry.getValue().getChanceMultiplier()>highestRarity.getChanceMultiplier()) {
					mostCommonRegion.clear();
				}
				mostCommonRegion.add(entry.getKey());
				highestRarity = entry.getValue();
			}
		}
		return mostCommonRegion;
	}

	public List<SubspeciesFlag> getFlags() {
		return flags;
	}

	@Override
	public String[] getHalfDemonName(Body body) {
		String[] names = null;
		
		if(this.getRace()==Race.DEMON
				|| this.getRace()==Race.ELEMENTAL
				|| this.getRace()==Race.HUMAN) {
			
			String[] demonNames = demonLegConfigurationNames.get(body ==null?LegConfiguration.BIPEDAL: body.getLegConfiguration());
			
			names = new String[] {
				"half-"+demonNames[0],
				"half-"+demonNames[1],
				"half-"+demonNames[2],
				"half-"+demonNames[3],
				"half-"+demonNames[4],
				"half-"+demonNames[5]};	
		}
		
		if(names==null) {
			if(body !=null && halfDemonNames.containsKey(body.getLegConfiguration())) {
				return halfDemonNames.get(body.getLegConfiguration());
				
			} else if(!halfDemonNames.isEmpty()) {
				return halfDemonNames.get(null);
				
			} else if(body ==null) {
				names = new String[] {
						"demonic-"+getAnthroNamesMap().get(null)[0],
						"demonic-"+getAnthroNamesMap().get(null)[1],
						"demonic-"+getAnthroNamesMap().get(null)[2],
						"demonic-"+getAnthroNamesMap().get(null)[3],
						"demonic-"+getAnthroNamesMap().get(null)[4],
						"demonic-"+getAnthroNamesMap().get(null)[5]};
				
			} else {
				names = new String[] {
						"demonic-"+this.getName(body),
						"demonic-"+this.getNamePlural(body),
						"demonic-"+this.getSingularMaleName(body),
						"demonic-"+this.getSingularFemaleName(body),
						"demonic-"+this.getPluralMaleName(body),
						"demonic-"+this.getPluralFemaleName(body)};
			}
		}
		
		return names;
	}

	@Override
	public int getBaseSlaveValue(GameCharacter character) {
		return baseSlaveValue;
	}
}
