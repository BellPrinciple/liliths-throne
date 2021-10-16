package com.lilithsthrone.world;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.places.PlaceType;

/**
 * @since 0.2.12
 * @version 0.4
 * @author Innoxia
 */
public abstract class AbstractWorldType implements WorldType {

	String id;
	private boolean mod;
	private boolean fromExternalFile;
	private String author;
	
	private WorldRegion worldRegion;
	
	private String name;
	private String fileLocation;
	private Colour colour;

	private String sexBlockedReason;
	
	private boolean usesFile;
	private boolean loiteringEnabled;
	private boolean flightEnabled;
	private boolean discoveredOnStart;
	private boolean revealedOnStart;
	private boolean furniturePresent;
	private String deskName;
	private boolean wallsPresent;
	private String wallName;

	private PlaceType globalMapLocation;
	private PlaceType standardPlace;
	private PlaceType entryFromGlobalMapLocation;
	
	private TeleportPermissions teleportPermissions;
	
	private Map<Color,PlaceType> placesMap;
	
	public AbstractWorldType(WorldRegion worldRegion,
			String name,
			Colour colour,
			boolean loiteringEnabled,
			boolean flightEnabled,
			TeleportPermissions teleportPermissions,
			String fileLocation,
			PlaceType globalMapLocation,
			PlaceType entryFromGlobalMapLocation,
			Map<Color,PlaceType> placesMap) {
		this.worldRegion = worldRegion;
		
		this.name = name;
		this.colour = colour;

		standardPlace = null;
		
		this.sexBlockedReason = "";
		
		this.globalMapLocation = globalMapLocation;
		this.entryFromGlobalMapLocation = entryFromGlobalMapLocation;
		
		this.loiteringEnabled = loiteringEnabled;
		this.flightEnabled = flightEnabled;
		this.discoveredOnStart = false;
		this.revealedOnStart = false;
		this.furniturePresent = false;
		this.deskName = "desk";
		this.wallsPresent = true; // Default to true for hard coded values, as these are all Dominion/Submission (which obviously have walls)
		this.wallName = "wall";
		
		this.teleportPermissions = teleportPermissions;
		
		this.fileLocation = fileLocation;
		this.usesFile = true;
		this.placesMap = placesMap;
	}
	
	public AbstractWorldType(File XMLFile, String author, boolean mod) {
		if (XMLFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(XMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in AbstractWorldType files it's <worldType>
				
				this.fileLocation = XMLFile.getAbsolutePath().replace("worldType.xml", "map.png");
				
				this.mod = mod;
				this.fromExternalFile = true;
				this.author = author;
				
				this.worldRegion = WorldRegion.valueOf(coreElement.getMandatoryFirstOf("worldRegion").getTextContent());

				this.name = coreElement.getMandatoryFirstOf("name").getTextContent();
				
				String colourId = coreElement.getMandatoryFirstOf("colour").getTextContent();
				if(colourId.startsWith("#")) {
					this.colour = new Colour(false, Util.newColour(colourId), Util.newColour(colourId), "");
				} else {
					this.colour = PresetColour.getColourFromId(colourId);
				}
				
				if(coreElement.getOptionalFirstOf("sexBlockedReason").isPresent()) {
					sexBlockedReason = coreElement.getMandatoryFirstOf("sexBlockedReason").getTextContent();
				} else {
					sexBlockedReason = null;
				}

				this.usesFile = true;
				
				this.furniturePresent = false;
				this.deskName = "desk";
				if(coreElement.getOptionalFirstOf("furniturePresent").isPresent()) {
					this.furniturePresent = Boolean.valueOf(coreElement.getMandatoryFirstOf("furniturePresent").getTextContent().trim());
					if(!coreElement.getMandatoryFirstOf("furniturePresent").getAttribute("deskName").isEmpty()) {
						this.deskName = coreElement.getMandatoryFirstOf("furniturePresent").getAttribute("deskName");
					}
				}

				this.wallsPresent = false;
				this.wallName = "wall";
				if(coreElement.getOptionalFirstOf("wallsPresent").isPresent()) {
					this.wallsPresent = Boolean.valueOf(coreElement.getMandatoryFirstOf("wallsPresent").getTextContent().trim());
					if(!coreElement.getMandatoryFirstOf("wallsPresent").getAttribute("wallName").isEmpty()) {
						this.wallName = coreElement.getMandatoryFirstOf("wallsPresent").getAttribute("wallName");
					}
				}
				
				this.loiteringEnabled = Boolean.valueOf(coreElement.getMandatoryFirstOf("loiteringEnabled").getTextContent().trim());
				this.flightEnabled = Boolean.valueOf(coreElement.getMandatoryFirstOf("flightEnabled").getTextContent().trim());
				this.discoveredOnStart = Boolean.valueOf(coreElement.getMandatoryFirstOf("visibleFromStart").getTextContent().trim());
				this.revealedOnStart = Boolean.valueOf(coreElement.getMandatoryFirstOf("fullyRevealedFromStart").getTextContent().trim());
				
				this.globalMapLocation = PlaceType.getPlaceTypeFromId(coreElement.getMandatoryFirstOf("globalMapLocation").getTextContent().trim());
				this.standardPlace = PlaceType.getPlaceTypeFromId(coreElement.getMandatoryFirstOf("standardPlace").getTextContent().trim());
				this.entryFromGlobalMapLocation = PlaceType.getPlaceTypeFromId(coreElement.getMandatoryFirstOf("entryFromGlobalMapLocation").getTextContent().trim());

				this.teleportPermissions = TeleportPermissions.valueOf(coreElement.getMandatoryFirstOf("teleportPermissions").getTextContent());
				
				this.placesMap = new HashMap<>();
				for(Element e : coreElement.getMandatoryFirstOf("places").getAllOf("place")) {
					try {
						placesMap.put(Color.decode(e.getAttribute("colour")), PlaceType.getPlaceTypeFromId(e.getTextContent()));
					} catch(Exception ex) {
						System.err.println("WorldType loading error in '"+XMLFile.getName()+"': PlaceType '"+e.getTextContent()+"' not recognised! (Not added)");
					}
				}
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("WorldType was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
			}
		}
	}
	
	@Override
	public boolean equals(Object o) { // Just placesMap and fileLocation should be enough to check for equality.
		if(super.equals(o)){
			if(o instanceof AbstractWorldType){
				if(((AbstractWorldType)o).getPlacesMap().equals(getPlacesMap())
						&& ((AbstractWorldType)o).getFileLocation().equals(getFileLocation())){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() { // Just placesMap and fileLocation should be enough to check for equality.
		int result = 17;
		result = 31 * result + getFileLocation().hashCode();
		result = 31 * result + getPlacesMap().hashCode();
		return result;
	}
	
	@Override
	public String toString() {
//		throw new IllegalAccessError();
		System.err.println("Warning: AbstractWorldType's toString() method is being called!");
		return super.toString();
	}

	public boolean isMod() {
		return mod;
	}

	@Override
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
	public Colour getColour() {
		return colour;
	}

	@Override
	public boolean isLoiteringEnabled() {
		return loiteringEnabled;
	}

	@Override
	public boolean isDiscoveredOnStart() {
		return discoveredOnStart;
	}

	@Override
	public boolean isRevealedOnStart() {
		return revealedOnStart;
	}

	@Override
	public PlaceType getStandardPlace() {
		return standardPlace;
	}

	@Override
	public PlaceType getGlobalMapLocation() {
		return globalMapLocation;
	}

	@Override
	public PlaceType getEntryFromGlobalMapLocation() {
		return entryFromGlobalMapLocation;
	}

	@Override
	public String getFileLocation() {
		return fileLocation;
	}

	@Override
	public boolean isUsesFile() {
		return usesFile;
	}

	@Override
	public Map<Color,PlaceType> getPlacesMap() {
		return placesMap;
	}

	@Override
	public TeleportPermissions getTeleportPermissions() {
		return teleportPermissions;
	}

	@Override
	public boolean isFlightEnabled() {
		return flightEnabled;
	}

	@Override
	public String getSexBlockedReason(GameCharacter character) {
		if(this.isFromExternalFile()) {
			return UtilText.parse(character, sexBlockedReason);
		}
		return sexBlockedReason;
	}

	@Override
	public boolean isFurniturePresent() {
		return furniturePresent;
	}

	@Override
	public String getDeskName() {
		return deskName;
	}

	@Override
	public boolean isWallsPresent() {
		return wallsPresent;
	}

	@Override
	public String getWallName() {
		return wallName;
	}
}
