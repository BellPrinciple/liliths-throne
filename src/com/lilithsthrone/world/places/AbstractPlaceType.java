package com.lilithsthrone.world.places;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.dialogue.DialogueManager;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.encounters.Encounter;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.CharacterInventory;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.Cell;
import com.lilithsthrone.world.TeleportPermissions;
import com.lilithsthrone.world.Weather;
import com.lilithsthrone.world.WorldRegion;
import com.lilithsthrone.world.population.AbstractPopulationType;
import com.lilithsthrone.world.population.Population;
import com.lilithsthrone.world.population.PopulationDensity;
import com.lilithsthrone.world.population.PopulationType;

/**
 * @since 0.3.1
 * @version 0.4
 * @author Innoxia
 */
public class AbstractPlaceType implements PlaceType {

	String id;
	private boolean mod;
	private boolean fromExternalFile;
	private String author;

	protected WorldRegion worldRegion;
	
	protected String name;
	protected String tooltipDescription;
	protected String SVGString;
	protected Colour colour;
	protected Colour backgroundColour;
	protected DialogueNode dialogue;
	protected Encounter encounterType;

	protected boolean globalMapTile;
	protected boolean dangerous;
	protected boolean itemsDisappear;
	private boolean furniturePresentOverride;
	private boolean furniturePresent;
	private boolean deskNameOverride;
	private String deskName;
	private boolean loiteringEnabledOverride;
	private boolean loiteringEnabled;
	private boolean wallsPresentOverride;
	private boolean wallsPresent;
	private boolean wallNameOverride;
	private String wallName;
	
	private String sexBlockedReason;
	private boolean sexBlockedFromCharacterPresent;
	
	protected Aquatic aquatic;
	protected Darkness darkness;
	protected TeleportPermissions teleportPermissions;
	
	protected List<Weather> weatherImmunities;
	protected static List<Weather> allWeatherImmunities = new ArrayList<>(Arrays.asList(Weather.values()));
	
	protected String virginityLossDescription;

	protected static Map<String, String> SVGOverrides = new HashMap<>(); 
	
	protected static int colourReplacementId = 0;

	// Used in place types from external files:
	
	protected String dangerousString;
	protected String itemsDisappearString;
	protected String aquaticString;
	protected String darknessString;
	protected String inventoryInitString;
	
	protected List<Population> populations = new ArrayList<>();
	
	protected String copyPlaceTypePopulationId;
	
	public AbstractPlaceType(WorldRegion worldRegion,
			String name,
			String tooltipDescription,
			String SVGPath,
			Colour colour,
			DialogueNode dialogue,
			Darkness darkness,
			Encounter encounterType,
			String virginityLossDescription) {
		this.worldRegion = worldRegion;
		
		this.name = name;
		this.tooltipDescription = tooltipDescription;
		this.colour = colour;

		this.backgroundColour = PresetColour.MAP_BACKGROUND;
		
		this.dialogue = dialogue;
		this.encounterType = encounterType;
		this.weatherImmunities = new ArrayList<>();
		this.virginityLossDescription = virginityLossDescription;

		this.dangerous = false;
		this.itemsDisappear = true;
		this.globalMapTile = false;
		
		this.furniturePresentOverride = false;
		this.furniturePresent = false;

		this.loiteringEnabledOverride = false;
		this.loiteringEnabled = false;

		this.wallsPresentOverride = false;
		this.wallsPresent = false;
		this.wallName = "wall";
		
		this.sexBlockedReason = "";
		this.sexBlockedFromCharacterPresent = true;
		
		this.aquatic = Aquatic.LAND;
		this.darkness = darkness;
		this.teleportPermissions = TeleportPermissions.BOTH;
		
		if(SVGPath!=null) {
			String s = SvgUtil.loadFromResource("/com/lilithsthrone/res/map/"+SVGPath+".svg");
			SVGString = SvgUtil.colourReplacement("placeColour"+colourReplacementId, colour, s);
			colourReplacementId++;
		} else {
			SVGString = null;
		}
	}
	
	public AbstractPlaceType(File XMLFile, String author, String id, boolean mod) {
		this.id = id;
		if (XMLFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(XMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in AbstractWorldType files it's <worldType>
				
				this.mod = mod;
				this.fromExternalFile = true;
				this.author = author;
				
				this.worldRegion = WorldRegion.valueOf(coreElement.getMandatoryFirstOf("worldRegion").getTextContent());

				this.name = coreElement.getMandatoryFirstOf("name").getTextContent();
				if(!Boolean.valueOf(coreElement.getMandatoryFirstOf("name").getAttribute("noCapitalisation"))) {
					String[] nameSplit = this.name.split(" ");
					StringBuilder sb = new StringBuilder();
					for(String s : nameSplit) {
						sb.append(Util.capitaliseSentence(s));
						sb.append(" ");
					}
					this.name = sb.toString();
				}
				
				this.tooltipDescription = coreElement.getMandatoryFirstOf("tooltipDescription").getTextContent();
				this.virginityLossDescription = coreElement.getMandatoryFirstOf("virginityLossDescription").getTextContent();

				if(coreElement.getOptionalFirstOf("sexBlockedReason").isPresent()) {
					sexBlockedReason = coreElement.getMandatoryFirstOf("sexBlockedReason").getTextContent();
				} else {
					sexBlockedReason = null;
				}

				this.sexBlockedFromCharacterPresent = Boolean.valueOf(coreElement.getMandatoryFirstOf("sexBlockedFromCharacterPresent").getTextContent().trim());
				
				String colourId = coreElement.getMandatoryFirstOf("colour").getTextContent();
				if(colourId.startsWith("#")) {
					this.colour = new Colour(false, Util.newColour(colourId), Util.newColour(colourId), "");
				} else {
					this.colour = PresetColour.getColourFromId(colourId);
				}

				colourId = coreElement.getMandatoryFirstOf("backgroundColour").getTextContent();
				if(colourId.startsWith("#")) {
					this.backgroundColour = new Colour(false, Util.newColour(colourId), Util.newColour(colourId), "");
				} else {
					this.backgroundColour = PresetColour.getColourFromId(colourId);
				}
				
				this.encounterType = null;
				if(coreElement.getOptionalFirstOf("encounterType").isPresent()) {
					String encounterId = coreElement.getMandatoryFirstOf("encounterType").getTextContent().trim();
					if(!encounterId.equals("null") && !encounterId.isEmpty()) {
						this.encounterType = Encounter.table.of(coreElement.getMandatoryFirstOf("encounterType").getTextContent().trim());
					}
				}
				
				this.dialogue = DialogueManager.getDialogueFromId(coreElement.getMandatoryFirstOf("dialogue").getTextContent());

				populations = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("populationPresent").isPresent()) {
					if(!coreElement.getMandatoryFirstOf("populationPresent").getAttribute("copyPlaceType").isEmpty()) {
						copyPlaceTypePopulationId = coreElement.getMandatoryFirstOf("populationPresent").getAttribute("copyPlaceType");
						
					} else {
						for(Element population : coreElement.getMandatoryFirstOf("populationPresent").getAllOf("population")) {
							int startMinutes = -1;
							int endMinutes = -1;
							boolean usingDaylightHours = false;
							boolean inclusiveRange = true;
							if(Boolean.valueOf(population.getAttribute("night"))) {
								usingDaylightHours = true;
								inclusiveRange = false;
								
							} else if(Boolean.valueOf(population.getAttribute("day"))) {
								usingDaylightHours = true;
								
							} else {
								if(!population.getAttribute("startMinutes").isEmpty()) {
									startMinutes = Integer.valueOf(population.getAttribute("startMinutes"));
								}
								if(!population.getAttribute("endMinutes").isEmpty()) {
									endMinutes = Integer.valueOf(population.getAttribute("endMinutes"));
								}
								if(!population.getAttribute("inclusiveRange").isEmpty()) {
									inclusiveRange = Boolean.valueOf(population.getAttribute("inclusiveRange"));
								}
							}
							String conditional = "";
							if(population.getOptionalFirstOf("conditional").isPresent()) {
								conditional = population.getMandatoryFirstOf("conditional").getTextContent();
							}
							
							String populationTypeString = population.getMandatoryFirstOf("populationType").getTextContent();
							boolean plural = Boolean.valueOf(population.getMandatoryFirstOf("populationType").getAttribute("plural"));
							PopulationDensity density = PopulationDensity.valueOf(population.getMandatoryFirstOf("populationType").getAttribute("density"));
							AbstractPopulationType popType;
							if(PopulationType.hasId(populationTypeString)) {
								popType = PopulationType.getPopulationTypeFromId(populationTypeString);
							} else {
								popType = new AbstractPopulationType(populationTypeString, populationTypeString) {};
							}
							
							if(population.getMandatoryFirstOf("subspeciesPresent").getAttribute("worldType").isEmpty()) {
								List<String> subspeciesIds = new ArrayList<>();
								for(Element subspecies : population.getMandatoryFirstOf("subspeciesPresent").getAllOf("subspecies")) {
									String subId = subspecies.getTextContent();
									subspeciesIds.add(subId);
								}
								
								Population pop = new Population(plural, popType, density, null);
								pop.setConditional(conditional);
								pop.setSubspeciesIdToAdd(subspeciesIds);
								
								pop.setDayStartOverride(startMinutes);
								pop.setDayEndOverride(endMinutes);
								pop.setUsingDaylightHours(usingDaylightHours);
								pop.setInclusiveTimeRange(inclusiveRange);
								
								populations.add(pop);
								
							} else {
								String popDaySubspeciesWorldTypeId = population.getMandatoryFirstOf("subspeciesPresent").getAttribute("worldType");
								List<String> subspeciesIds = new ArrayList<>();
								for(Element subspecies : population.getMandatoryFirstOf("subspeciesPresent").getAllOf("subspeciesToRemove")) {
									subspeciesIds.add(subspecies.getTextContent());
								}
								
								Population pop = new Population(plural, popType, density, null);
								pop.setConditional(conditional);
								pop.setSubspeciesPlaceTypeId(id);
								pop.setSubspeciesWorldTypeId(popDaySubspeciesWorldTypeId);
								pop.setSubspeciesIdToRemove(subspeciesIds);
								
								pop.setDayStartOverride(startMinutes);
								pop.setDayEndOverride(endMinutes);
								pop.setUsingDaylightHours(usingDaylightHours);
								pop.setInclusiveTimeRange(inclusiveRange);
								
								populations.add(pop);
							}
						}
					}
				}
				
				if(coreElement.getOptionalFirstOf("furniturePresent").isPresent()) {
					this.furniturePresentOverride = true;
					this.furniturePresent = Boolean.valueOf(coreElement.getMandatoryFirstOf("furniturePresent").getTextContent().trim());
					if(!coreElement.getMandatoryFirstOf("furniturePresent").getAttribute("deskName").isEmpty()) {
						this.deskNameOverride = true;
						this.deskName = coreElement.getMandatoryFirstOf("furniturePresent").getAttribute("deskName");
					}
				} else {
					this.furniturePresentOverride = false;
					this.furniturePresent = false;
					this.deskNameOverride = false;
					this.deskName = "desk";
				}
				
				if(coreElement.getOptionalFirstOf("loiteringEnabled").isPresent()) {
					this.loiteringEnabledOverride = true;
					this.loiteringEnabled = Boolean.valueOf(coreElement.getMandatoryFirstOf("loiteringEnabled").getTextContent().trim());
				} else {
					this.loiteringEnabledOverride = false;
					this.loiteringEnabled = false;
				}

				if(coreElement.getOptionalFirstOf("wallsPresent").isPresent()) {
					this.wallsPresentOverride = true;
					this.wallsPresent = Boolean.valueOf(coreElement.getMandatoryFirstOf("wallsPresent").getTextContent().trim());
					if(!coreElement.getMandatoryFirstOf("wallsPresent").getAttribute("wallName").isEmpty()) {
						this.wallNameOverride = true;
						this.wallName = coreElement.getMandatoryFirstOf("wallsPresent").getAttribute("wallName");
					}
				} else {
					this.wallsPresentOverride = false;
					this.wallsPresent = false;
					this.wallNameOverride = false;
					this.wallName = "wall";
				}
				
				this.globalMapTile = Boolean.valueOf(coreElement.getMandatoryFirstOf("globalMapTile").getTextContent().trim());
				this.dangerousString = coreElement.getMandatoryFirstOf("dangerous").getTextContent().trim();
				this.itemsDisappearString = coreElement.getMandatoryFirstOf("itemsDisappear").getTextContent().trim();
				this.aquaticString = coreElement.getMandatoryFirstOf("aquatic").getTextContent().trim();
				this.darknessString = coreElement.getMandatoryFirstOf("darkness").getTextContent().trim();

				if(coreElement.getOptionalFirstOf("teleportPermissions").isPresent() && !coreElement.getMandatoryFirstOf("teleportPermissions").getTextContent().isEmpty()) {
					this.teleportPermissions = TeleportPermissions.valueOf(coreElement.getMandatoryFirstOf("teleportPermissions").getTextContent());
				} else {
					this.teleportPermissions = TeleportPermissions.BOTH;
				}
				
				this.weatherImmunities = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("weatherImmunities").isPresent()) {
					if(Boolean.valueOf(coreElement.getMandatoryFirstOf("weatherImmunities").getAttribute("immuneToAll").trim())) {
						initWeatherImmune();
					} else {
						for(Element e : coreElement.getMandatoryFirstOf("weatherImmunities").getAllOf("weather")) {
							try {
								weatherImmunities.add(Weather.valueOf(e.getTextContent().trim()));
							} catch(Exception ex) {
								System.err.println("PlaceType loading error in '"+XMLFile.getName()+"': Weather '"+e.getTextContent()+"' not recognised! (Not added)");
							}
						}
					}
				}
				
				if(coreElement.getOptionalFirstOf("applyInventoryInit").isPresent()) {
					inventoryInitString = coreElement.getMandatoryFirstOf("applyInventoryInit").getTextContent();
				} else {
					inventoryInitString = "";
				}
				
				String svgPath = XMLFile.getAbsolutePath().replace(".xml", ".svg");
				if(coreElement.getOptionalFirstOf("svgName").isPresent() && !coreElement.getMandatoryFirstOf("svgName").getTextContent().isEmpty()) {
					svgPath = XMLFile.getAbsolutePath().replace(XMLFile.getName(), coreElement.getMandatoryFirstOf("svgName").getTextContent()+".svg");
//					System.out.println("1: "+svgPath + " | "+XMLFile.getName());
				}
//				
				if(new File(svgPath).exists()) {
					SVGString = "<div style='width:100%;height:100%;position:absolute;left:0;top:0;margin:0;padding:0;'>"
							// Background circle:
							+ SvgUtil.loadFromResource("/com/lilithsthrone/res/map/iconBackground.svg")
							+ "</div>"
							+ "<div style='width:70%;height:70%;position:absolute;left:15%;top:15%;margin:0;padding:0;'>"
							// Icon:
							+ Util.getFileContent(svgPath)
							+ "</div>";
					SVGString = SvgUtil.colourReplacement(XMLFile.getName().replace("\\.svg", ""), colour, PresetColour.BASE_WHITE, null, SVGString);
				} else {
					SVGString = null;
				}
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("PlaceType was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
			}
		}
	}
	
	public AbstractPlaceType initDangerous() {
		this.dangerous = true;
		return this;
	}
	
	public AbstractPlaceType initItemsPersistInTile() {
		this.itemsDisappear = false;
		return this;
	}
	
	public AbstractPlaceType initMapBackgroundColour(Colour backgroundColour) {
		this.backgroundColour = backgroundColour;
		return this;
	}
	
	/**
	 * Sets weather immunity to all Weather values.
	 */
	public AbstractPlaceType initWeatherImmune() {
		this.weatherImmunities = allWeatherImmunities;
		return this;
	}

	/**
	 * Define weather immunity for this place.
	 */
	public AbstractPlaceType initWeatherImmune(Weather... weatherImmunities) {
		this.weatherImmunities = new ArrayList<>(Arrays.asList(weatherImmunities));
		return this;
	}
	
	public AbstractPlaceType initAquatic(Aquatic aquatic) {
		this.aquatic = aquatic;
		return this;
	}

	/**
	 * Define teleport permissions for this tile.
	 * TeleportPermissions are also defined in WorldType, so this will only work in special cases where a world allows teleporting, but not on some tiles (such as Lyssieth's palace in Submission).
	 */
	public AbstractPlaceType initTeleportPermissions(TeleportPermissions teleportPermissions) {
		this.teleportPermissions = teleportPermissions;
		return this;
	}

	public AbstractPlaceType initSexNotBlockedFromCharacterPresent() {
		this.sexBlockedFromCharacterPresent = false;
		return this;
	}

	public boolean isMod() {
		return mod;
	}

	public boolean isFromExternalFile() {
		return fromExternalFile;
	}

	public String getAuthor() {
		return author;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public WorldRegion getWorldRegion() {
		return worldRegion;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getTooltipDescription() {
		return UtilText.parse(tooltipDescription);
	}

	@Override
	public Colour getColour() {
		return colour;
	}

	@Override
	public Colour getBackgroundColour() {
		if(backgroundColour==PresetColour.MAP_BACKGROUND && this.isDangerous()) {
			return PresetColour.MAP_BACKGROUND_DANGEROUS;
		}
		return backgroundColour;
	}

	@Override
	public Encounter getEncounterType() {
		Map<Encounter,Float> possibleEncountersMap = new HashMap<>();
		
		if(encounterType!=null && encounterType.getTotalChanceValue()>0) {
			possibleEncountersMap.put(encounterType, encounterType.getTotalChanceValue());
		}
		for(Encounter enc : Encounter.getAddedEncounters(this.getId())) {
			if(enc.getTotalChanceValue()>0) {
				possibleEncountersMap.put(enc, enc.getTotalChanceValue());
			}
		}
		
		if(possibleEncountersMap.isEmpty()) {
			return null;
		}
		
		// If a value of >100 is used for the encounter chance, then all other encounters with chances of <=100 are discarded
		if(possibleEncountersMap.keySet().stream().anyMatch(en->en.isAnyBaseTriggerChanceOverOneHundred())) {
			possibleEncountersMap.keySet().removeIf(en->!en.isAnyBaseTriggerChanceOverOneHundred());
		}
		
		return Util.getRandomObjectFromWeightedFloatMap(possibleEncountersMap);
	}
	
	protected DialogueNode getBaseDialogue(Cell cell) {
		return dialogue;
	}

	@Override
	public DialogueNode getDialogue(Cell cell, boolean withRandomEncounter, boolean forceEncounter) {
		if(withRandomEncounter && Main.game.isStarted()) {
			Encounter encounterType = getEncounterType();
			if(encounterType!=null) {
				DialogueNode dn = encounterType.getRandomEncounter(forceEncounter);
				if (dn != null) {
					return dn;
				}
			}
		}
		return getBaseDialogue(cell);
	}

	@Override
	public List<Population> getPopulation() {
		List<Population> returnPopulation = new ArrayList<>();
		
		if(fromExternalFile) {
			if(copyPlaceTypePopulationId!=null && !copyPlaceTypePopulationId.isEmpty()) {
				return PlaceType.getPlaceTypeFromId(copyPlaceTypePopulationId).getPopulation();
				
			} else {
				for(Population pop : populations) {
					if(pop.isAvailableFromConditional() && pop.isAvailableFromCurrentTime()) {
						returnPopulation.add(pop);
					}
				}
			}
		}
		
		return returnPopulation;
	}

	@Override
	public boolean isStormImmune() {
		return weatherImmunities.contains(Weather.MAGIC_STORM);
	}

	@Override
	public boolean isDangerous() {
		if(this.isFromExternalFile()) {
			return Boolean.valueOf(UtilText.parse(dangerousString));
		}
		return dangerous;
	}

	@Override
	public boolean isItemsDisappear() {
		if(this.isFromExternalFile()) {
			return Boolean.valueOf(UtilText.parse(itemsDisappearString));
		}
		return itemsDisappear;
	}

	@Override
	public Aquatic getAquatic() {
		if(this.isFromExternalFile()) {
			return Aquatic.valueOf(UtilText.parse(aquaticString));
		}
		return aquatic;
	}

	@Override
	public Darkness getDarkness() {
		if(this.isFromExternalFile()) {
			String parsedDarkness = UtilText.parse(darknessString);
			if(parsedDarkness.isEmpty()) {
				return Darkness.DAYLIGHT;
			}
			return Darkness.valueOf(parsedDarkness);
		}
		return darkness;
	}

	public static String getSVGOverride(String pathName, Colour colour) {
		return getSVGOverride(pathName, colour, colour, colour);
	}
	
	public static String getSVGOverride(String pathName, Colour colourPrimary, Colour colourSecondary, Colour colourTertiary) {
		String find = SVGOverrides.get(pathName+colourPrimary.getId());
		if(find != null)
			return find;
		String newGraphic = SvgUtil.colourReplacement("placeColour"+colourReplacementId, colourPrimary,
				SvgUtil.loadFromResource("com/lilithsthrone/res/map/"+pathName+".svg"));
		colourReplacementId++;
		SVGOverrides.put(pathName+colourPrimary.getId(), newGraphic);
		return newGraphic;
	}

	@Override
	public String getSVGString(Set<PlaceUpgrade> upgrades) {
		var s = PlaceType.super.getSVGString(upgrades);
		return s != null ? s : SVGString;
	}

	@Override
	public void applyInventoryInit(CharacterInventory inventory) {
		if(this.isFromExternalFile() && !inventoryInitString.isEmpty()) {
			UtilText.setInventoryForParsing("inventory", inventory);
			UtilText.parse(inventoryInitString);
		}
	}

	@Override
	public String getSexBlockedReason(GameCharacter character) {
		if(this.isFromExternalFile() && sexBlockedReason!=null) {
			return UtilText.parse(character, sexBlockedReason);
		}
		return sexBlockedReason;
	}

	@Override
	public String getVirginityLossDescription() {
		return virginityLossDescription;
	}

	@Override
	public boolean isGlobalMapTile() {
		return globalMapTile;
	}

	@Override
	public TeleportPermissions getTeleportPermissions() {
		return teleportPermissions;
	}

	@Override
	public boolean isFurniturePresentOverride() {
		return furniturePresentOverride;
	}

	@Override
	public boolean isFurniturePresent() {
		return furniturePresent;
	}

	@Override
	public boolean isDeskNameOverride() {
		return deskNameOverride;
	}

	@Override
	public String getDeskName() {
		return deskName;
	}

	@Override
	public boolean isLoiteringEnabledOverride() {
		return loiteringEnabledOverride;
	}

	@Override
	public boolean isLoiteringEnabled() {
		return loiteringEnabled;
	}

	@Override
	public boolean isSexBlockedFromCharacterPresent() {
		return sexBlockedFromCharacterPresent;
	}

	@Override
	public boolean isWallsPresentOverride() {
		return wallsPresentOverride;
	}

	@Override
	public boolean isWallsPresent() {
		return wallsPresent;
	}

	@Override
	public boolean isWallNameOverride() {
		return wallNameOverride;
	}

	@Override
	public String getWallName() {
		return wallName;
	}
}
