package com.lilithsthrone.game.inventory.item;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.controller.xmlParsing.XMLLoadException;
import com.lilithsthrone.controller.xmlParsing.XMLMissingTagException;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.Rarity;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.1.84
 * @version 0.4.0
 * @author Innoxia
 */
public abstract class AbstractItemType implements ItemType {

	String id;
	private String determiner;
	private String name;
	private String namePlural;
	private String description;
	private String useDescriptor;
	private String authorDescription;

	private boolean sexUse;
	private boolean combatUseAllies;
	private boolean combatUseEnemies;
	private boolean consumedOnUse;
	
	private Rarity rarity;
	
	private int value;
	
	private boolean plural;
	private boolean mod;
	private boolean fromExternalFile;

	private List<SvgInformation> svgPathInformation;
	private List<Colour> colourShades;
	
	protected String SVGString;
	protected List<String> effectTooltipLines;
	protected String specialEffect;

	protected List<String> useDescriptionsSelf;
	protected List<String> useDescriptionsOther;
	
	protected List<ItemEffect> effects;
	
	/** Maps Status effect -> conditional and time applied*/
	protected Map<StatusEffect, Value<String, Integer>> appliedStatusEffects;

	// For use in enchanting into a different item:
	protected String potionDescriptor;
	protected Race associatedRace;
	protected String enchantmentEffectId;
	protected String enchantmentItemTypeId;
	
	protected Set<ItemTag> itemTags;

	public AbstractItemType(
			int value,
			String determiner,
			boolean plural,
			String name,
			String namePlural,
			String description,
			String pathName,
			Colour colourPrimary,
			Colour colourSecondary,
			Colour colourTertiary,
			Rarity rarity,
			List<ItemEffect> effects,
			List<ItemTag> itemTags) {
		this(value,
				determiner,
				plural,
				name,
				namePlural,
				description,
				pathName,
				initNewColourShades(
					colourPrimary,
					colourSecondary,
					colourTertiary),
				rarity,
				effects,
				itemTags);
	}

	public AbstractItemType(
			int value,
			String determiner,
			boolean plural,
			String name,
			String namePlural,
			String description,
			String pathName,
			List<Colour> colourShades,
			Rarity rarity,
			List<ItemEffect> effects,
			List<ItemTag> itemTags) {
		this.determiner = determiner;
		this.plural = plural;
		this.mod = false;
		this.fromExternalFile = false;
		this.name = name;
		this.namePlural = namePlural;
		this.description = description;
		this.useDescriptor = "use";
		this.svgPathInformation = Util.newArrayListOfValues(new SvgInformation(1, pathName==null?"":pathName, 100, 0, new HashMap<>()));
		this.authorDescription = "";

		this.sexUse = true;
		this.combatUseAllies = true;
		this.combatUseEnemies = false;
		this.consumedOnUse = true;
		
		this.value = value;
		this.rarity = rarity;
		
		this.itemTags = new HashSet<>();
		if(itemTags!=null) {
			this.itemTags.addAll(itemTags);
		}
		
		this.effectTooltipLines = new ArrayList<>();
		
		this.useDescriptionsSelf = Util.newArrayListOfValues("[npc.Name] [npc.verb(use)] the item.");
		this.useDescriptionsOther = Util.newArrayListOfValues("[npc.Name] [npc.verb(use)] the item on [npc2.name].");
		
		specialEffect = "";
		
		if(effects==null) {
			this.effects = new ArrayList<>();
		} else {
			this.effects=effects;
		}
		
		potionDescriptor = "";
		associatedRace = Race.NONE;
		enchantmentEffectId = null;
		enchantmentItemTypeId = null;
		
		this.colourShades = colourShades;

		SVGString = null;
	}

	private static List<Colour> initNewColourShades(Colour colourPrimary, Colour colourSecondary, Colour colourTertiary) {
		List<Colour> newColourShades = new ArrayList<>();
		
		if (colourPrimary == null) {
			newColourShades.add(PresetColour.CLOTHING_BLACK);
		} else {
			newColourShades.add(colourPrimary);
		}
		if (colourSecondary == null) {
			newColourShades.add(PresetColour.CLOTHING_BLACK);
		} else {
			newColourShades.add(colourSecondary);
		}
		if (colourTertiary == null) {
			newColourShades.add(PresetColour.CLOTHING_BLACK);
		} else {
			newColourShades.add(colourTertiary);
		}
		
		return newColourShades;
	}

	public AbstractItemType(File itemXMLFile, String author, boolean mod) throws XMLLoadException { // Be sure to catch this exception correctly - if it's thrown mod is invalid and should not be continued to load
		boolean debug = false;
		
		try{
			Element itemElement = Element.getDocumentRootElement(itemXMLFile); // Loads the document and returns the root element - in item mods it's <item>
			Element coreAttributes = null;
			try {
				coreAttributes = itemElement.getMandatoryFirstOf("coreAtributes");
			} catch (XMLMissingTagException ex) {
				coreAttributes = itemElement.getMandatoryFirstOf("coreAttributes");
			}

			if(debug) {
				System.out.println("1");
			}
			
			this.mod = mod;
			this.fromExternalFile = true;

			this.name = coreAttributes.getMandatoryFirstOf("name").getTextContent();
			this.namePlural = coreAttributes.getMandatoryFirstOf("namePlural").getTextContent();
			this.description = coreAttributes.getMandatoryFirstOf("description").getTextContent();
			this.useDescriptor = coreAttributes.getMandatoryFirstOf("useDescriptor").getTextContent();
			this.plural = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("namePlural").getAttribute("pluralByDefault"));
			this.value = Integer.valueOf(coreAttributes.getMandatoryFirstOf("value").getTextContent());
			this.rarity = Rarity.valueOf(coreAttributes.getMandatoryFirstOf("rarity").getTextContent());
			
			if(coreAttributes.getOptionalFirstOf("authorTag").isPresent()) {
				this.authorDescription = coreAttributes.getMandatoryFirstOf("authorTag").getTextContent();
			} else if(!author.equalsIgnoreCase("innoxia")){
				this.authorDescription = "A tag discreetly attached to the "+(plural?namePlural:name)+" informs you that "+(plural?"they were":"it was")+" made by a certain '"+Util.capitaliseSentence(author)+"'.";
			} else {
				this.authorDescription = "";
			}
			
			this.sexUse = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("sexUse").getTextContent());
			this.combatUseAllies = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("combatUseAllies").getTextContent());
			this.combatUseEnemies = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("combatUseEnemies").getTextContent());
			this.consumedOnUse = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("consumedOnUse").getTextContent());
			
			this.svgPathInformation = new ArrayList<>();
			
			for(Element imagePathElement : coreAttributes.getAllOf("imageName")) {
				String rawPath = imagePathElement.getTextContent();
				String pathName;
				if(rawPath.contains("res/")) {
					pathName = rawPath;
				} else {
					pathName = itemXMLFile.getParentFile().getAbsolutePath() + "/"+ imagePathElement.getTextContent();
				}
				int zLayer = 1;
				int imageSize = 100;
				int imageRotation = 0;
				Map<String, String> replacements = new HashMap<>();

				if(!imagePathElement.getAttribute("zLayer").isEmpty()) {
					try {
						zLayer = Integer.valueOf(imagePathElement.getAttribute("zLayer"));
					} catch(Exception ex) {
					}
				}
				if(!imagePathElement.getAttribute("imageSize").isEmpty()) {
					try {
						imageSize = Math.min(100, Math.max(1, Integer.valueOf(imagePathElement.getAttribute("imageSize"))));
					} catch(Exception ex) {
					}
				}
				if(!imagePathElement.getAttribute("imageRotation").isEmpty()) {
					try {
						imageRotation = Math.min(360, Math.max(-360, Integer.valueOf(imagePathElement.getAttribute("imageRotation"))));
					} catch(Exception ex) {
					}
				}
				int i=1;
				while(!imagePathElement.getAttribute("target"+i).isEmpty()) {
					replacements.put(imagePathElement.getAttribute("target"+i), imagePathElement.getAttribute("replacement"+i));
					i++;
				}
				
				svgPathInformation.add(new SvgInformation(zLayer, pathName, imageSize, imageRotation, replacements));
			}
			SVGString = null;
			
			Colour colourShade = PresetColour.getColourFromId(coreAttributes.getMandatoryFirstOf("colourPrimary").getTextContent());
			Colour colourShadeSecondary = null;
			if(coreAttributes.getOptionalFirstOf("colourSecondary").isPresent() && !coreAttributes.getMandatoryFirstOf("colourSecondary").getTextContent().isEmpty()) {
				colourShadeSecondary = PresetColour.getColourFromId(coreAttributes.getMandatoryFirstOf("colourSecondary").getTextContent());
			}
			Colour colourShadeTertiary = null;
			if(coreAttributes.getOptionalFirstOf("colourTertiary").isPresent() && !coreAttributes.getMandatoryFirstOf("colourTertiary").getTextContent().isEmpty()) {
				colourShadeTertiary = PresetColour.getColourFromId(coreAttributes.getMandatoryFirstOf("colourTertiary").getTextContent());
			}
			this.colourShades = Util.newArrayListOfValues(colourShade, colourShadeSecondary, colourShadeTertiary);
			
			
			if(debug) {
				System.out.println("2");
			}

			this.effects = new ArrayList<>();

			// Item tags before checking for FOOD and DRINK
			this.itemTags = new HashSet<>(Util.toEnumList(coreAttributes.getMandatoryFirstOf("itemTags").getAllOf("tag"), ItemTag.class));

			this.appliedStatusEffects = new HashMap<>();
			// Add FOOD & DRINK first:
			if(this.getItemTags().contains(ItemTag.FOOD)) {
				appliedStatusEffects.put(StatusEffect.RECENTLY_EATEN, new Value<>("true", 6*60*60));
			}
			if(this.getItemTags().contains(ItemTag.FOOD_POOR)) {
				appliedStatusEffects.put(StatusEffect.RECENTLY_EATEN_POOR, new Value<>("true", 6*60*60));
			}
			if(this.getItemTags().contains(ItemTag.FOOD_QUALITY)) {
				appliedStatusEffects.put(StatusEffect.RECENTLY_EATEN_QUALITY, new Value<>("true", 6*60*60));
			}
			if(this.getItemTags().contains(ItemTag.DRINK)) {
				appliedStatusEffects.put(StatusEffect.THIRST_QUENCHED, new Value<>("true", 6*60*60));
			}
			if(this.getItemTags().contains(ItemTag.DRINK_POOR)) {
				appliedStatusEffects.put(StatusEffect.THIRST_QUENCHED_POOR, new Value<>("true", 6*60*60));
			}
			if(this.getItemTags().contains(ItemTag.DRINK_QUALITY)) {
				appliedStatusEffects.put(StatusEffect.THIRST_QUENCHED_QUALITY, new Value<>("true", 6*60*60));
			}
			if(coreAttributes.getOptionalFirstOf("statusEffects").isPresent()) {
				for(Element e : coreAttributes.getMandatoryFirstOf("statusEffects").getAllOf("effect")) {
					int seconds = Integer.valueOf(e.getAttribute("seconds"));
					StatusEffect se = StatusEffect.getStatusEffectFromId(e.getTextContent());
					String conditional = e.getAttribute("conditional");
					appliedStatusEffects.put(se, new Value<>(conditional.isEmpty()?"true":conditional, seconds));
				}
			}

			this.specialEffect = coreAttributes.getMandatoryFirstOf("applyEffects").getTextContent();
			
			if(coreAttributes.getOptionalFirstOf("potionDescriptor").isPresent()) {
				this.potionDescriptor = coreAttributes.getMandatoryFirstOf("potionDescriptor").getTextContent();
			} else {
				this.potionDescriptor = "";
			}
			
			if(coreAttributes.getOptionalFirstOf("associatedRace").isPresent() && !coreAttributes.getMandatoryFirstOf("associatedRace").getTextContent().isEmpty()) {
				associatedRace = Race.getRaceFromId(coreAttributes.getMandatoryFirstOf("associatedRace").getTextContent());
			} else {
				this.associatedRace = Race.NONE;
			}
			
			if(coreAttributes.getOptionalFirstOf("enchantmentEffectId").isPresent() && !coreAttributes.getMandatoryFirstOf("enchantmentEffectId").getTextContent().isEmpty()) {
				enchantmentEffectId = coreAttributes.getMandatoryFirstOf("enchantmentEffectId").getTextContent();
			} else {
				enchantmentEffectId = null;
			}
			
			if(coreAttributes.getOptionalFirstOf("enchantmentItemTypeId").isPresent() && !coreAttributes.getMandatoryFirstOf("enchantmentItemTypeId").getTextContent().isEmpty()) {
				enchantmentItemTypeId = coreAttributes.getMandatoryFirstOf("enchantmentItemTypeId").getTextContent();
			} else {
				enchantmentItemTypeId = null;
			}
			
			this.effectTooltipLines = new ArrayList<>();
			if(coreAttributes.getOptionalFirstOf("effectTooltipLines").isPresent()) {
				for(Element e : coreAttributes.getMandatoryFirstOf("effectTooltipLines").getAllOf("line")) {
					effectTooltipLines.add(e.getTextContent());
				}
			}

			if(debug) {
				System.out.println("3");
			}
			
			if(itemElement.getOptionalFirstOf("useDescriptions").isPresent()) {
				this.useDescriptionsSelf = itemElement
						.getMandatoryFirstOf("useDescriptions")
						.getAllOf("selfUse").stream()
						.map(o -> o.getTextContent())
						.collect(Collectors.toList());
				if(useDescriptionsSelf.isEmpty()) {
					this.useDescriptionsSelf = Util.newArrayListOfValues("[npc.Name] [npc.verb(use)] the item.");
				}
				
				this.useDescriptionsOther = itemElement
						.getMandatoryFirstOf("useDescriptions")
						.getAllOf("otherUse").stream()
						.map(o -> o.getTextContent())
						.collect(Collectors.toList());
				if(useDescriptionsOther.isEmpty()) {
					this.useDescriptionsOther = Util.newArrayListOfValues("[npc.Name] [npc.verb(use)] the item on [npc2.name].");
				}
				
			} else {
				this.useDescriptionsSelf = Util.newArrayListOfValues("[npc.Name] [npc.verb(use)] the item.");
				this.useDescriptionsOther = Util.newArrayListOfValues("[npc.Name] [npc.verb(use)] the item on [npc2.name].");
			}
			
		}
		catch(XMLMissingTagException ex){
			throw new XMLLoadException(ex, itemXMLFile);
		}
		catch(Exception e){
			System.out.println(e);
			throw new XMLLoadException(e, itemXMLFile);
		}
	}
	
	@Override
	public boolean equals(Object o) { // I know it doesn't include everything, but this should be enough to check for equality.
		if(super.equals(o)){
			if(o instanceof AbstractItemType){
				if(((AbstractItemType)o).getName(false).equals(getName(false))
						&& ((AbstractItemType)o).getPathNameInformation().equals(getPathNameInformation())
						&& ((AbstractItemType)o).getRarity() == getRarity()
						&& ((AbstractItemType)o).getEffects().equals(getEffects())
						){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() { // I know it doesn't include everything, but this should be enough to check for equality.
		int result = super.hashCode();
		result = 31 * result + getName(false).hashCode();
		result = 31 * result + getPathNameInformation().hashCode();
		result = 31 * result + getRarity().hashCode();
		result = 31 * result + getEffects().hashCode();
		return result;
	}

	@Override
	public String getId() {
		return id;
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
	public String getAuthorDescription() {
		return authorDescription;
	}

	@Override
	public List<ItemEffect> getEffects() {
		return effects;
	}

	@Override
	public String getSpecialEffect() {
		return specialEffect;
	}

	@Override
	public String getPotionDescriptor() {
		return potionDescriptor;
	}

	// Enchantments:

	@Override
	public ItemEffectType getEnchantmentEffect() {
		if(enchantmentEffectId==null || enchantmentEffectId.isEmpty()) {
			return null;
		}
		if(enchantmentEffectId.equalsIgnoreCase("RACE")) {
			return ItemEffectType.getRacialEffectType(associatedRace);
		} else {
			return ItemEffectType.getItemEffectTypeFromId(enchantmentEffectId);
		}
	}

	@Override
	public ItemType getEnchantmentItemType(List<ItemEffect> effects) {
		if(enchantmentItemTypeId==null || enchantmentItemTypeId.isEmpty()) {
			return null;
		}
		return ItemType.getItemTypeFromId(enchantmentItemTypeId);
	}
	
	// Getters & setters:

	@Override
	public String getDeterminer() {
		return (determiner!=null?determiner:"");
	}

	@Override
	public boolean isPlural() {
		return plural;
	}

	@Override
	public String getName(boolean displayName, boolean capitalise) {
		String out;
		if(displayName) {
			out = (determiner!=null?determiner:"") + " <span style='color: " + rarity.getColour().toWebHexString() + ";'>" + (this.isPlural()?namePlural:name) + "</span>";
		} else {
			out = (this.isPlural()?namePlural:name);
		}
		if(capitalise) {
			return Util.capitaliseSentence(out);
		} else {
			return out;
		}
	}

	@Override
	public String getNamePlural(boolean displayName, boolean capitalise) {
		String out;
		if(displayName) {
			out = (determiner!=null?determiner:"") + " <span style='color: " + rarity.getColour().toWebHexString() + ";'>" + namePlural + "</span>";
		} else {
			out = namePlural;
		}
		if(capitalise) {
			return Util.capitaliseSentence(out);
		} else {
			return out;
		}
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<String> getEffectTooltipLines() {
		List<String> parsed = new ArrayList<>();
		for(String s : effectTooltipLines) {
			parsed.add(UtilText.parse(s));
		}
		// Any status effects being applied:
		if(this.appliedStatusEffects!=null) { // If not null, then from external file
			for(Entry<StatusEffect, Value<String, Integer>> entry : this.appliedStatusEffects.entrySet()) {
				StatusEffect se = entry.getKey();
//				parsed.add("Applies <i style='color:"+se.getColour().toWebHexString()+";'>'"+Util.capitaliseSentence(se.getName(null))+"'</i>:");
//				for(Entry<AbstractAttribute, Float> attEntry : se.getAttributeModifiers(null).entrySet()) {
//					parsed.add("<i>"+attEntry.getKey().getFormattedValue(attEntry.getValue())+"</i>");
//				}
				int seconds = entry.getValue().getValue();
				int timeDisplay = seconds/60; // minutes
				String timeDesc = "minutes";
				if(timeDisplay>120) {
					timeDisplay = timeDisplay/60; // hours
					timeDesc = "hours";
				}
				if(timeDisplay>48) {
					timeDisplay = timeDisplay/24; // days
					timeDesc = "days";
				}
				for(Entry<Attribute, Float> attEntry : se.getAttributeModifiers(null).entrySet()) {
					parsed.add("<i>"+attEntry.getKey().getFormattedValue(attEntry.getValue())+"</i> for [style.italicsOrange("+timeDisplay+" "+timeDesc+")]");
				}
			}

		} else {
			for(ItemEffect ie : this.getEffects()) {
				for(Entry<StatusEffect, Integer> entry : ie.getItemEffectType().getAppliedStatusEffects().entrySet()) {
					StatusEffect se = entry.getKey();
//					parsed.add("Applies <i style='color:"+se.getColour().toWebHexString()+";'>'"+Util.capitaliseSentence(se.getName(null))+"'</i>:");
//					for(Entry<AbstractAttribute, Float> attEntry : se.getAttributeModifiers(null).entrySet()) {
//						parsed.add("<i>"+attEntry.getKey().getFormattedValue(attEntry.getValue())+"</i>");
//					}
					int seconds = entry.getValue();
					int timeDisplay = seconds/60; // minutes
					String timeDesc = "minutes";
					if(timeDisplay>120) {
						timeDisplay = timeDisplay/60; // hours
						timeDesc = "hours";
					}
					if(timeDisplay>48) {
						timeDisplay = timeDisplay/24; // days
						timeDesc = "days";
					}
					for(Entry<Attribute, Float> attEntry : se.getAttributeModifiers(null).entrySet()) {
						parsed.add("<i>"+attEntry.getKey().getFormattedValue(attEntry.getValue())+"</i> for [style.italicsOrange("+timeDisplay+" "+timeDesc+")]");
					}
				}
			}
		}
		return parsed;
	}

	@Override
	public String getDisplayName(boolean withRarityColour) {
		return Util.capitaliseSentence((determiner!=null?determiner:"") + (withRarityColour
				? (" <span style='color: " + rarity.getColour().toWebHexString() + ";'>" + (this.isPlural()?getNamePlural(false):getName(false)) + "</span>")
				: (this.isPlural()?getNamePlural(false):getName(false))));
	}

	@Override
	public List<SvgInformation> getPathNameInformation() {
		return svgPathInformation;
	}

	@Override
	public Colour getColour() {
		return colourShades.get(0);
	}

	@Override
	public List<Colour> getColourShades() {
		return colourShades;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String getSVGString() {
		if(SVGString!=null)
			return SVGString;
		if(getPathNameInformation()==null || getPathNameInformation().isEmpty())
			return SVGString = "";
		if(!isFromExternalFile()) {
			String s = SvgUtil.loadFromResource("/com/lilithsthrone/res/items/" + svgPathInformation.get(0).getPathName() + ".svg");
			return SVGString = SvgUtil.colourReplacement(this.getId(), getColourShades(), null, s);
		}
		svgPathInformation.sort(Comparator.comparingInt(SvgInformation::getZLayer));
		StringBuilder svgBuilder = new StringBuilder();
		for(SvgInformation info : getPathNameInformation()) {
			String finalSvg = Util.getFileContent(info.getPathName());
			int sizeOffset = (100-info.getImageSize())/2;
			svgBuilder.append("<div style='"
					+ "width:"+info.getImageSize()+"%;"
					+ "height:"+info.getImageSize()+"%;"
					+ "transform:rotate("+info.getImageRotation()+"deg);"
					+ "position:absolute;"
					+ "left:"+sizeOffset+"%;"
					+ "bottom:"+sizeOffset+"%;"
					+ "padding:0;"
					+ "margin:0;'>");
			for(Entry<String, String> entry : info.getReplacements().entrySet()) {
				finalSvg = finalSvg.replaceAll(entry.getKey(), entry.getValue());
			}
			svgBuilder.append(finalSvg);
			svgBuilder.append("</div>");
		}
		SVGString = svgBuilder.toString();
//		SVGString = Util.getFileContent(pathName);
//		if(!backgroundPathName.isEmpty()) {
//			String background = Util.getFileContent(backgroundPathName);
//			int sizeOffset = (100-imageSize)/2;
//			SVGString = "<div style='width:100%;height:100%;position:absolute;left:0;bottom:0;padding:0;margin:0;'>"+background+"</div>"
//					+ "<div style='width:"+imageSize+"%;height:"+imageSize+"%;transform:rotate("+imageRotation+"deg);position:absolute;left:"+sizeOffset+"%;bottom:"+sizeOffset+"%;padding:0;margin:0;'>"+SVGString+"</div>";
//		}
		return SVGString = SvgUtil.colourReplacement(this.getId(), getColourShades(), null, SVGString);
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public String getUseName() {
		return useDescriptor;
	}

	@Override
	public String getUseDescription(GameCharacter user, GameCharacter target) {
		if(user.equals(target)) {
			return "<p>"
						+ UtilText.parse(user, target, Util.randomItemFrom(useDescriptionsSelf))
					+ "</p>";
		} else {
			return "<p>"
					+ UtilText.parse(user, target, Util.randomItemFrom(useDescriptionsOther))
				+ "</p>";
		}
	}

	@Override
	public boolean isAbleToBeUsedInSex() {
		return sexUse;
	}

	@Override
	public boolean isAbleToBeUsedInCombatAllies() {
		return combatUseAllies;
	}

	@Override
	public boolean isAbleToBeUsedInCombatEnemies() {
		return combatUseEnemies;
	}

	@Override
	public boolean isConsumedOnUse() {
		return consumedOnUse;
	}

	@Override
	public Set<ItemTag> getItemTags() {
		return itemTags;
	}

	/**
	 * @return null if this ItemType is hard-coded, but will return a (potentially empty) Map if it's been generated from an xml file.
	 */
	@Override
	public Map<StatusEffect, Value<String, Integer>> getAppliedStatusEffects() {
		return appliedStatusEffects;
	}

}
