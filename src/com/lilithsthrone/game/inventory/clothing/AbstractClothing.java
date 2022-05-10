package com.lilithsthrone.game.inventory.clothing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lilithsthrone.controller.xmlParsing.XMLUtil;
import com.lilithsthrone.game.Game;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.body.CoverableArea;
import com.lilithsthrone.game.character.body.Penis;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.body.types.FootType;
import com.lilithsthrone.game.character.body.types.HornType;
import com.lilithsthrone.game.character.body.types.PenisType;
import com.lilithsthrone.game.character.body.types.TailType;
import com.lilithsthrone.game.character.body.types.VaginaType;
import com.lilithsthrone.game.character.body.types.WingType;
import com.lilithsthrone.game.character.body.valueEnums.Capacity;
import com.lilithsthrone.game.character.body.valueEnums.LegConfiguration;
import com.lilithsthrone.game.character.body.valueEnums.OrificeElasticity;
import com.lilithsthrone.game.character.body.valueEnums.OrificePlasticity;
import com.lilithsthrone.game.character.body.valueEnums.PenetrationGirth;
import com.lilithsthrone.game.character.body.valueEnums.PenisLength;
import com.lilithsthrone.game.character.body.valueEnums.Wetness;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.AbstractCoreItem;
import com.lilithsthrone.game.inventory.AbstractCoreType;
import com.lilithsthrone.game.inventory.ColourReplacement;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.Rarity;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.game.inventory.enchanting.TFPotency;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.rendering.Pattern;
import com.lilithsthrone.utils.MarkupWriter;
import com.lilithsthrone.utils.Units;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.XMLSaving;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

import static com.lilithsthrone.game.dialogue.PronounUtility.*;

/**
 * @since 0.1.0
 * @version 0.3.9.5
 * @author Innoxia
 */
public abstract class AbstractClothing extends AbstractCoreItem implements XMLSaving {

	private ClothingType clothingType;
	
	private InventorySlot slotEquippedTo;
	
	protected List<ItemEffect> effects;
	
	private String pattern; // id of the pattern.
	private List<Colour> patternColours;

	private Map<String, String> stickers; // Mapping StickerCategory id to Sticker id
	
	private boolean dirty;
	private boolean enchantmentKnown;
	private boolean unlocked;
	
	private List<DisplacementType> displacedList;
	
	public AbstractClothing(ClothingType clothingType, List<Colour> colours, boolean allowRandomEnchantment) {
		super(clothingType.getName(),
				clothingType.getNamePlural(),
				clothingType.getPathName(),
				colours.isEmpty()?ColourReplacement.DEFAULT_COLOUR_VALUE:colours.get(0),
				clothingType.getRarity(),
				null);
		
		this.slotEquippedTo = null;
		
		this.clothingType = clothingType;
		if(clothingType.getEffects()==null) {
			this.effects = new ArrayList<>();
		} else {
			this.effects = new ArrayList<>(clothingType.getEffects());
		}
		
		dirty = false;
		enchantmentKnown = true;
		unlocked = false;

		this.colours = new ArrayList<>(colours);
		if(colours.size()<clothingType.getColourReplacements().size()) {
			for(int i=colours.size(); i<clothingType.getColourReplacements().size(); i++) {
				this.setColour(i, clothingType.getColourReplacements().get(i).getFirstOfDefaultColours());
			}
		}
		
		handlePatternCreation();
		
		handleStickerCreation();

		displacedList = new ArrayList<>();

		if(effects.isEmpty() && allowRandomEnchantment && getClothingType().getRarity() == Rarity.COMMON) {
			int chance = Util.random.nextInt(100) + 1;
			
			List<TFModifier> attributeMods = new ArrayList<>(TFModifier.getClothingAttributeList());
			
			TFModifier rndMod = attributeMods.get(Util.random.nextInt(attributeMods.size()));
			attributeMods.remove(rndMod);
			TFModifier rndMod2 = attributeMods.get(Util.random.nextInt(attributeMods.size()));
			
			if(chance <= 20) { // Jinxed:
				if(chance <= 1) {
					effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_SPECIAL, TFModifier.CLOTHING_SEALING, TFPotency.MAJOR_DRAIN, 0));
				} else if(chance <= 4) {
					effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_SPECIAL, TFModifier.CLOTHING_SEALING, TFPotency.DRAIN, 0));
				} else if(chance <= 10) {
					effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_SPECIAL, TFModifier.CLOTHING_SEALING, TFPotency.MINOR_DRAIN, 0));
				} else {
					effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_SPECIAL, TFModifier.CLOTHING_SEALING, TFPotency.MINOR_BOOST, 0));
				}
				
				effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, rndMod, TFPotency.getRandomWeightedNegativePotency(), 0));
				if(chance <10) {
					effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, rndMod2, TFPotency.getRandomWeightedNegativePotency(), 0));
				}
				
				enchantmentKnown = false;
				
			} else if(chance >= 80) { // Enchanted:
				effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, rndMod, TFPotency.getRandomWeightedPositivePotency(), 0));
				if(chance > 90) {
					effects.add(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, rndMod2, TFPotency.getRandomWeightedPositivePotency(), 0));
				}
				enchantmentKnown = false;
			}

		}
	}

	public AbstractClothing(ClothingType clothingType, Colour colour, Colour secondaryColour, Colour tertiaryColour, List<ItemEffect> effects) {
		this(clothingType, Util.newArrayListOfValues(colour, secondaryColour, tertiaryColour), effects);
	}
	
	public AbstractClothing(ClothingType clothingType, List<Colour> colours, List<ItemEffect> effects) {
		super(clothingType.getName(),
				clothingType.getNamePlural(),
				clothingType.getPathName(),
				colours.isEmpty()?ColourReplacement.DEFAULT_COLOUR_VALUE:colours.get(0),
				clothingType.getRarity(),
				null);
		
		this.slotEquippedTo = null;
		
		this.clothingType = clothingType;

		dirty = false;
		enchantmentKnown = true;
		unlocked = false;

		this.colours = new ArrayList<>(colours);
		if(colours.size()<clothingType.getColourReplacements().size()) {
			for(int i=colours.size(); i<clothingType.getColourReplacements().size(); i++) {
				this.setColour(i, clothingType.getColourReplacements().get(i).getFirstOfDefaultColours());
			}
		}
		
		handlePatternCreation();
		
		handleStickerCreation();
		
		displacedList = new ArrayList<>();
		if(effects!=null) {
			this.effects = new ArrayList<>(effects);
			enchantmentKnown = false;
			
		} else {
			this.effects = new ArrayList<>();
		}
	}

	public AbstractClothing(AbstractClothing clothing) {
		this(clothing.getClothingType(), clothing.getColours(), clothing.getEffects());
		
		this.setEnchantmentKnown(null, clothing.isEnchantmentKnown());
		
		this.setPattern(clothing.getPattern());
		this.setPatternColours(clothing.getPatternColours());
		
		this.setStickers(clothing.getStickers());
		
		this.displacedList = new ArrayList<>(clothing.getDisplacedList());
		
		this.dirty = clothing.isDirty();

		this.slotEquippedTo = clothing.getSlotEquippedTo();
		this.unlocked = clothing.isUnlocked();
		
		if(!clothing.name.isEmpty()) {
			this.setName(clothing.name);
		}
	}
	
	
	private void handlePatternCreation() {
		patternColours = new ArrayList<>();
		
		if(Math.random()<clothingType.getPatternChance()) {
			pattern = Util.randomItemFrom(clothingType.getDefaultPatterns()).getId();
			
		} else {
			pattern = "none";
		}
		
		for(ColourReplacement cr : clothingType.getPatternColourReplacements()) {
			patternColours.add(cr.getRandomOfDefaultColours());
		}
	}
	
	private void handleStickerCreation() {
		stickers = new HashMap<>();
		
		for(Entry<StickerCategory, List<Sticker>> entry : this.getClothingType().getStickers().entrySet()) {
			if(!stickers.containsKey(entry.getKey().getId())) {
				List<Sticker> availableStickers = new ArrayList<>();
				for(Sticker s : entry.getValue()) {
					if(s.isDefaultSticker()) {
						availableStickers.add(s);
					}
				}
				if(availableStickers.isEmpty() && !entry.getValue().isEmpty()) {
					stickers.put(entry.getKey().getId(), entry.getValue().get(0).getId());
				} else {
					stickers.put(entry.getKey().getId(), Util.randomItemFrom(availableStickers).getId());
				}
			}
		}
	}
	
	public String getId() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(ClothingType.getIdFromClothingType(this.getClothingType()));
		for(Colour colour : this.getColours()) {
			sb.append(colour.getId());
		}

		sb.append(this.getPattern());
		for(Colour colour : this.getPatternColours()) {
			sb.append(colour.getId());
		}
		
		sb.append(this.isSealed()?"s":"n");
		sb.append(this.isDirty()?"d":"n");
		sb.append(this.isEnchantmentKnown()?"e":"n");
		sb.append(this.isBadEnchantment()?"b":"n");
		sb.append(this.getSlotEquippedTo());
		
		for(ItemEffect ie : this.getEffects()) {
			sb.append(ie.getId());
		}
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(super.equals(o)){
			if(o instanceof AbstractClothing){
				if(((AbstractClothing)o).getClothingType().equals(getClothingType())
						&& ((AbstractClothing)o).getColours().equals(getColours())
						&& ((AbstractClothing)o).getPattern().equals(getPattern())
						&& (this.getPattern()!="none"
							?((AbstractClothing)o).getPatternColours().equals(getPatternColours())
							:true)
						&& ((AbstractClothing)o).isSealed()==this.isSealed()
						&& ((AbstractClothing)o).isDirty()==this.isDirty()
						&& ((AbstractClothing)o).isEnchantmentKnown()==this.isEnchantmentKnown()
						&& ((AbstractClothing)o).isBadEnchantment()==this.isBadEnchantment()
						&& ((AbstractClothing)o).getEffects().equals(this.getEffects())
						&& ((AbstractClothing)o).getSlotEquippedTo()==this.getSlotEquippedTo()
						){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + getClothingType().hashCode();
		result = 31 * result + getColours().hashCode();
		result = 31 * result + getPattern().hashCode();
		if(this.getPattern()!="none") {
			result = 31 * result + getPatternColours().hashCode();
		}
		result = 31 * result + (this.isSealed() ? 1 : 0);
		result = 31 * result + (this.isDirty() ? 1 : 0);
		result = 31 * result + (this.isEnchantmentKnown() ? 1 : 0);
		result = 31 * result + (this.isBadEnchantment() ? 1 : 0);
		result = 31 * result + this.getEffects().hashCode();
		if(this.getSlotEquippedTo()!=null) {
			result = 31 * result + this.getSlotEquippedTo().hashCode();
		}
		return result;
	}
	
	public Element saveAsXML(Element parentElement, Document doc) {
		Element element = doc.createElement("clothing");
		parentElement.appendChild(element);
		
		XMLUtil.addAttribute(doc, element, "id", this.getClothingType().getId());
		XMLUtil.addAttribute(doc, element, "name", name);
		if(slotEquippedTo!=null) {
			XMLUtil.addAttribute(doc, element, "slotEquippedTo", slotEquippedTo.toString());
		}

		if(!this.getColours().isEmpty()) {
			Element innerElement = doc.createElement("colours");
			element.appendChild(innerElement);
			
			for(int i=0; i<this.getColours().size(); i++) {
				Element colourElement = doc.createElement("colour");
				innerElement.appendChild(colourElement);
				colourElement.setAttribute("i", String.valueOf(i));
				colourElement.setTextContent(this.getColour(i).getId());
			}
		}
		
		if(!this.getPattern().equals("none")) {
			Element innerElement = doc.createElement("pattern");
			element.appendChild(innerElement);
			innerElement.setAttribute("id", this.getPattern());
			
			for(int i=0; i<this.getPatternColours().size(); i++) {
				Element colourElement = doc.createElement("colour");
				innerElement.appendChild(colourElement);
				colourElement.setAttribute("i", String.valueOf(i));
				colourElement.setTextContent(this.getPatternColour(i).getId());
			}
		}
		
		if(!this.getStickers().isEmpty()) {
			Element innerElement = doc.createElement("stickers");
			element.appendChild(innerElement);
			
			for(Entry<String, String> entry : this.getStickers().entrySet()) {
				Element stickerElement = doc.createElement("sticker");
				innerElement.appendChild(stickerElement);
				stickerElement.setAttribute("category", String.valueOf(entry.getKey()));
				stickerElement.setTextContent(entry.getValue());
			}
		}
		
		XMLUtil.addAttribute(doc, element, "sealed", String.valueOf(this.isSealed()));
		XMLUtil.addAttribute(doc, element, "isDirty", String.valueOf(this.isDirty()));
		XMLUtil.addAttribute(doc, element, "enchantmentKnown", String.valueOf(this.isEnchantmentKnown()));
		
		if(!this.getEffects().isEmpty()) {
			Element innerElement = doc.createElement("effects");
			element.appendChild(innerElement);
			
			for(ItemEffect ie : this.getEffects()) {
				ie.saveAsXML(innerElement, doc);
			}
		}

		if(!this.getDisplacedList().isEmpty()) {
			Element innerElement = doc.createElement("displacedList");
			element.appendChild(innerElement);
			for(DisplacementType dt : this.getDisplacedList()) {
				Element displacementType = doc.createElement("displacementType");
				innerElement.appendChild(displacementType);
				XMLUtil.addAttribute(doc, displacementType, "value", dt.toString());
			}
		}
		
		return element;
	}
	
	public static AbstractClothing loadFromXML(Element parentElement, Document doc) {
		AbstractClothing clothing = null;
		String slotHint = null;
		
		try {
			slotHint = parentElement.getAttribute("slotEquippedTo");
		} catch(Exception ex) {
			// pass
		}
		
		try {
			String loadedId = parentElement.getAttribute("id");
			
			// Handle old Enforcer clothing ids (pre-sticker update):
			if(Main.isVersionOlderThan(Game.loadingVersion, "0.3.9.6")) {
				// Berets:
				if(loadedId.equals("dsg_eep_servequipset_enfberet")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfberet", false);
					clothing.setSticker("flash", "flash_patrol_dominion");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfberet_academy")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfberet", false);
					clothing.setSticker("flash", "flash_academy");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfberet_oricl")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfberet", false);
					clothing.setSticker("flash", "flash_oricl");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfberet_sword")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfberet", false);
					clothing.setSticker("flash", "flash_sword");
					return clothing;
				}
				
				// Hats:
				if(loadedId.equals("dsg_eep_ptrlequipset_bwhat")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_bwhat", false);
					clothing.setSticker("badge", "badge_dominion");
					return clothing;
				} else if(loadedId.equals("dsg_eep_ptrlequipset_pcap")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_pcap", false);
					clothing.setSticker("badge", "badge_dominion");
					return clothing;
				}
				
				// Jackets:
				if(loadedId.equals("dsg_eep_servequipset_enfdjacket_cs")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", false);
					clothing.setSticker("collar", "tab_cs");
					clothing.setSticker("name", "name_cs");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfdjacket_ip")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", false);
					clothing.setSticker("collar", "tab_ip");
					clothing.setSticker("name", "name_ip");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfdjacket_pc")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", false);
					clothing.setSticker("collar", "tab_pc");
					clothing.setSticker("name", "name_pc");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfdjacket_sg")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", false);
					clothing.setSticker("collar", "tab_sg");
					clothing.setSticker("name", "name_sg");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfdjacket_su")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", false);
					clothing.setSticker("collar", "tab_su");
					clothing.setSticker("name", "name_su");
					return clothing;
				}
				
				// Unique jackets:
				if(loadedId.equals("dsg_eep_uniques_enfdjacket_brax")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_BLUE, null, false);
					clothing.setSticker("collar", "tab_ip");
					clothing.setSticker("name", "name_brax");
					clothing.setSticker("ribbon", "ribbon_brax");
					return clothing;
				} else if(loadedId.equals("dsg_eep_uniques_enfdjacket_candi")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_PINK, null, false);
					clothing.setSticker("collar", "tab_pc");
					clothing.setSticker("name", "name_candi");
					clothing.setSticker("ribbon", "ribbon_candi");
					return clothing;
				} else if(loadedId.equals("dsg_eep_uniques_enfdjacket_claire")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_PINK, null, false);
					clothing.setSticker("collar", "tab_sg");
					clothing.setSticker("name", "name_claire");
					clothing.setSticker("ribbon", "ribbon_claire");
					return clothing;
				} else if(loadedId.equals("dsg_eep_uniques_enfdjacket_elle")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_PINK, null, false);
					clothing.setSticker("collar", "tab_su");
					clothing.setSticker("name", "name_elle");
					clothing.setSticker("ribbon", "ribbon_elle");
					clothing.setSticker("qual", "qual_flyer");
					return clothing;
				} else if(loadedId.equals("dsg_eep_uniques_enfdjacket_wesley")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_BLUE, null, false);
					clothing.setSticker("collar", "tab_ip");
					clothing.setSticker("name", "name_wesley");
					clothing.setSticker("ribbon", "ribbon_wes");
					return clothing;
				} else if(loadedId.equals("dsg_eep_uniques_enfdjacket_wesley_su")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_BLUE, null, false);
					clothing.setSticker("collar", "tab_su");
					clothing.setSticker("name", "name_wesley");
					clothing.setSticker("ribbon", "ribbon_wes");
					return clothing;
				} else if(loadedId.equals("dsg_eep_uniques_stpvest_claire")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_stpvest", false);
					clothing.setSticker("name_plate", "claire");
					return clothing;
				}
				
				// Waistcoats:
				if(loadedId.equals("dsg_eep_servequipset_enfdwaistcoat_cs")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdwaistcoat", false);
					clothing.setSticker("collar", "tab_cs");
					clothing.setSticker("name", "name_cs");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfdwaistcoat_ip")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdwaistcoat", false);
					clothing.setSticker("collar", "tab_ip");
					clothing.setSticker("name", "name_ip");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfdwaistcoat_pc")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdwaistcoat", false);
					clothing.setSticker("collar", "tab_pc");
					clothing.setSticker("name", "name_pc");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfdwaistcoat_sg")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdwaistcoat", false);
					clothing.setSticker("collar", "tab_sg");
					clothing.setSticker("name", "name_sg");
					return clothing;
				} else if(loadedId.equals("dsg_eep_servequipset_enfdwaistcoat_su")) {
					clothing = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdwaistcoat", false);
					clothing.setSticker("collar", "tab_su");
					clothing.setSticker("name", "name_su");
					return clothing;
				}
			}
			
			clothing = Main.game.getItemGen().generateClothing(ClothingType.getClothingTypeFromId(loadedId, slotHint), false);
		} catch(Exception ex) {
			System.err.println("Warning: An instance of AbstractClothing was unable to be imported. ("+parentElement.getAttribute("id")+")");
			return null;
		}
		
		if(clothing==null) {
			System.err.println("Warning: An instance of AbstractClothing was unable to be imported. ("+parentElement.getAttribute("id")+")");
			return null;
		}
		
		if(!Main.isVersionOlderThan(Game.loadingVersion, "0.3.9.6") || clothing.getClothingType().getStickers().isEmpty()) { // Reset name at version 0.3.9.6 for clothing which has had sticker support added
			if(!parentElement.getAttribute("name").isEmpty()) {
				clothing.setName(parentElement.getAttribute("name"));
			}
		}
		
		if(!parentElement.getAttribute("slotEquippedTo").isEmpty()) {
			InventorySlot slot = InventorySlot.valueOf(parentElement.getAttribute("slotEquippedTo"));
			if(!clothing.getClothingType().getEquipSlots().contains(slot)) {
				return null; // If the clothing type doens't support this slot, then something has gone wrong and the clothing should not be laoded.
			}
			clothing.setSlotEquippedTo(slot);
		}
		
		
		// Try to load colours:
		if(!Main.isVersionOlderThan(Game.loadingVersion, "0.3.7.8")) {
			boolean applySecondaryLoad = true;
			boolean applyTertiaryLoad = true;
			if(Main.isVersionOlderThan(Game.loadingVersion, "0.4.2.6")) {
				if(clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("innoxia_torsoOver_womens_leather_jacket"))
						|| clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("innoxia_stomach_overbust_corset"))
						|| clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("innoxia_stomach_underbust_corset"))) {
					applySecondaryLoad = false;
					applyTertiaryLoad = false;
				}
			}
			
			Element colourElement = (Element) parentElement.getElementsByTagName("colours").item(0);
			if(colourElement!=null) {
				NodeList nodes = colourElement.getElementsByTagName("colour");
				for(int i=0; i<nodes.getLength(); i++) {
					if((i!=1 || applySecondaryLoad) && (i!=2 || applyTertiaryLoad)) {
						Element cElement = (Element) nodes.item(i);
						clothing.setColour(Integer.valueOf(cElement.getAttribute("i")), PresetColour.getColourFromId(cElement.getTextContent()));
					}
				}
			}
			
		} else if((!Main.isVersionOlderThan(Game.loadingVersion, "0.3.7.4") || !clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("innoxia_scientist_safety_goggles")))
					&& !clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("innoxia_rainbow_gloves"))
					&& !clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("innoxia_rainbow_stockings"))) {

			if((clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("BDSM_CHOKER")) && Main.isVersionOlderThan(Game.loadingVersion, "0.2.12.6"))
					|| (clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("innoxia_ankle_shin_guards")) && Main.isVersionOlderThan(Game.loadingVersion, "0.3.0.6"))
					|| (clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("FOOT_TRAINERS")) && Main.isVersionOlderThan(Game.loadingVersion, "0.3.1.2"))
					|| (clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("innoxia_sock_toeless_striped_stockings")) && Main.isVersionOlderThan(Game.loadingVersion, "0.3.2"))) {
				try {
					clothing.setColour(0, PresetColour.getColourFromId(parentElement.getAttribute("colourSecondary")));
					clothing.setColour(1, PresetColour.getColourFromId(parentElement.getAttribute("colour")));
				} catch(Exception ex) {
				}
				
			} else if(clothing.getClothingType().equals(ClothingType.getClothingTypeFromId("FOOT_LOW_TOP_SKATER_SHOES")) && Main.isVersionOlderThan(Game.loadingVersion, "0.3.1.2")){
				try {
					clothing.setColour(1, PresetColour.CLOTHING_WHITE);
					if(!parentElement.getAttribute("colour").isEmpty()) {
						clothing.setColour(0, PresetColour.getColourFromId(parentElement.getAttribute("colour")));
					} else {
						clothing.setColour(0, AbstractClothingType.DEFAULT_COLOUR_VALUE);
					}
				} catch(Exception ex) {
				}
				
			} else {
				try {
					if(!parentElement.getAttribute("colour").isEmpty()) {
						clothing.setColour(0, PresetColour.getColourFromId(parentElement.getAttribute("colour")));
					} else {
						clothing.setColour(0, AbstractClothingType.DEFAULT_COLOUR_VALUE);
					}
				} catch(Exception ex) {
				}
				
				try {
					if(!parentElement.getAttribute("colourSecondary").isEmpty()) {
						Colour secColour = PresetColour.getColourFromId(parentElement.getAttribute("colourSecondary"));
						if(clothing.getClothingType().getPatternColourReplacement(1)!=null && clothing.getClothingType().getPatternColourReplacement(1).getAllColours().contains(secColour)) {
							clothing.setColour(1, secColour);
						}
					} else {
						clothing.setColour(1, AbstractClothingType.DEFAULT_COLOUR_VALUE);
						if(clothing.getClothingType().getPatternColourReplacement(1)!=null && !clothing.getClothingType().getPatternColourReplacement(1).getAllColours().contains(AbstractClothingType.DEFAULT_COLOUR_VALUE)) {
							clothing.setColour(1, clothing.getClothingType().getPatternColourReplacement(1).getRandomOfDefaultColours());
						}
					}
				} catch(Exception ex) {
				}
			}
			try {
				if(!parentElement.getAttribute("colourTertiary").isEmpty()) {
					Colour terColour = PresetColour.getColourFromId(parentElement.getAttribute("colourTertiary"));
					if(clothing.getClothingType().getPatternColourReplacement(2)!=null && clothing.getClothingType().getPatternColourReplacement(2).getAllColours().contains(terColour)) {
						clothing.setColour(2, terColour);
					}
				} else {
					clothing.setColour(2, AbstractClothingType.DEFAULT_COLOUR_VALUE);
					if(clothing.getClothingType().getPatternColourReplacement(2)!=null && !clothing.getClothingType().getPatternColourReplacement(2).getAllColours().contains(AbstractClothingType.DEFAULT_COLOUR_VALUE)) {
						clothing.setColour(2, clothing.getClothingType().getPatternColourReplacement(2).getRandomOfDefaultColours());
					}
				}
			} catch(Exception ex) {
			}
		}
		
		// Try to load patterns:
		if(!Main.isVersionOlderThan(Game.loadingVersion, "0.3.7.8")) {
			Element patternElement = (Element) parentElement.getElementsByTagName("pattern").item(0);
			if(patternElement!=null) {
				String patternId = patternElement.getAttribute("id");
				if(Pattern.getPattern(patternId) == null) {
					patternId = Pattern.getPatternIdByName(patternId);
				}
				clothing.setPattern(patternId);
				NodeList nodes = patternElement.getElementsByTagName("colour");
				for(int i=0; i<nodes.getLength(); i++) {
					Element cElement = (Element) nodes.item(i);
					clothing.setPatternColour(Integer.valueOf(cElement.getAttribute("i")), PresetColour.getColourFromId(cElement.getTextContent()));
				}
				
			} else {
				clothing.setPattern("none");
			}
			
		} else {
			try {
				if(!parentElement.getAttribute("pattern").isEmpty()) {
					String patternId = parentElement.getAttribute("pattern");
					if(Pattern.getPattern(patternId) == null) {
						patternId = Pattern.getPatternIdByName(patternId);
					}
					clothing.setPattern(patternId);
				} else {
					clothing.setPattern("none");
				}
				
				if(!parentElement.getAttribute("patternColour").isEmpty()) {
					Colour colour = PresetColour.getColourFromId(parentElement.getAttribute("patternColour"));
					clothing.setPatternColour(0, colour);
				} else {
					clothing.setPatternColour(0, AbstractClothingType.DEFAULT_COLOUR_VALUE);
				}
				
				if(!parentElement.getAttribute("patternColourSecondary").isEmpty()) {
					Colour secColour = PresetColour.getColourFromId(parentElement.getAttribute("patternColourSecondary"));
					clothing.setPatternColour(1, secColour);
				} else {
					clothing.setPatternColour(1, AbstractClothingType.DEFAULT_COLOUR_VALUE);
				}
				
				if(!parentElement.getAttribute("patternColourTertiary").isEmpty()) {
					Colour terColour = PresetColour.getColourFromId(parentElement.getAttribute("patternColourTertiary"));
					clothing.setPatternColour(2, terColour);
				} else {
					clothing.setPatternColour(2, AbstractClothingType.DEFAULT_COLOUR_VALUE);
				}
				
			} catch(Exception ex) {
			}
		}
		
		// Load stickers:
		Element stickersElement = (Element) parentElement.getElementsByTagName("stickers").item(0);
		if(stickersElement!=null) {
			NodeList nodes = stickersElement.getElementsByTagName("sticker");
			for(int i=0; i<nodes.getLength(); i++) {
				Element stickerElement = (Element) nodes.item(i);
				clothing.setSticker(stickerElement.getAttribute("category").toLowerCase(), stickerElement.getTextContent().toLowerCase());
			}
		}
		
		// Try to load core features:
		try {
			if(!parentElement.getAttribute("sealed").isEmpty()) {
				clothing.setSealed(Boolean.valueOf(parentElement.getAttribute("sealed")));
			}
			clothing.setDirty(null, Boolean.valueOf(parentElement.getAttribute("isDirty")));
			clothing.setEnchantmentKnown(null, Boolean.valueOf(parentElement.getAttribute("enchantmentKnown")));
		} catch(Exception ex) {
		}
		
		// Try to load attributes:
		if(!Main.isVersionOlderThan(Game.loadingVersion, "0.3.0.5") || !clothing.isCondom(clothing.getClothingType().getEquipSlots().get(0))) { // Do not load condom effects from versions prior to 0.3.0.5
			if(parentElement.getElementsByTagName("attributeModifiers")!=null && parentElement.getElementsByTagName("attributeModifiers").getLength()>0) {
				if(clothing.getClothingType().getClothingSet()==null) {
					clothing.getEffects().clear();
					
					Element element = (Element)parentElement.getElementsByTagName("attributeModifiers").item(0);
					NodeList modifierElements = element.getElementsByTagName("modifier");
					for(int i = 0; i < modifierElements.getLength(); i++){
						Element e = ((Element)modifierElements.item(i));
						try {
							var att = Attribute.getAttributeFromId(e.getAttribute("attribute"));
							int value = Integer.valueOf(e.getAttribute("value"));
							
							TFPotency pot = TFPotency.BOOST;
							if(value <= -5) {
								pot = TFPotency.MAJOR_DRAIN;
							} else if(value <= -3) {
								pot = TFPotency.DRAIN;
							} else if(value <= -1) {
								pot = TFPotency.MINOR_DRAIN;
							} else if(value <= 1) {
								pot = TFPotency.MINOR_BOOST;
							} else if(value <= 3) {
								pot = TFPotency.BOOST;
							} else {
								pot = TFPotency.MAJOR_BOOST;
							}
							
							for(TFModifier mod : TFModifier.getClothingAttributeList()) {
								if(mod.getAssociatedAttribute()==att) {
									clothing.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, mod, pot, 0));
									break;
								}
							}
							
							for(TFModifier mod : TFModifier.getClothingMajorAttributeList()) {
								if(mod.getAssociatedAttribute()==att) {
									clothing.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_MAJOR_ATTRIBUTE, mod, pot, 0));
									break;
								}
							}
							
						} catch(Exception ex) {
						}
					}
				}
				
			} else {
				try {
					clothing.getEffects().clear();
					
					Element element = (Element)parentElement.getElementsByTagName("effects").item(0);
					if(element!=null) {
						NodeList effectElements = element.getElementsByTagName("effect");
						for(int i=0; i<effectElements.getLength(); i++){
							Element e = ((Element)effectElements.item(i));
							ItemEffect ie = ItemEffect.loadFromXML(e, doc);
							if(ie!=null) {
								clothing.addEffect(ie);
							}
						}
					}
				} catch(Exception ex) {
				}
			}
		} else {
			clothing.setEnchantmentKnown(null, true);
		}
		
		// Try to load displacements:
		try {
			clothing.displacedList = new ArrayList<>();
			Element displacementElement = (Element)parentElement.getElementsByTagName("displacedList").item(0);
			if(displacementElement!=null) {
				NodeList displacementTypeElements = displacementElement.getElementsByTagName("displacementType");
				for(int i = 0; i < displacementTypeElements.getLength(); i++){
					Element e = ((Element)displacementTypeElements.item(i));
					
					DisplacementType dt = DisplacementType.valueOf(e.getAttribute("value"));
					boolean displacementTypeFound = false;
					for (BlockedParts bp : clothing.getBlockedPartsMap(null, clothing.getSlotEquippedTo())) {
						if(bp.displacementType == dt) {
							displacementTypeFound = true;
						}
					}
					if(displacementTypeFound) {
						clothing.displacedList.add(dt);
					} else {
						System.err.println("Warning: Invalid displacement");
					}
				}
			}
		} catch(Exception ex) {
		}
		
		return clothing;
	}
	
	/**
	 * Returns the id of a pattern that the clothing has.
	 * @return
	 */
	public String getPattern() {
		if(pattern == null) {
			return "none";
		}
		return pattern;
	}
	
	/**
	 * Changes pattern to specified one. Will not render that pattern if it doesn't exist or the item doesn't support it anyway.
	 * @param pattern
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Colour getPatternColour(int index) {
		try {
			return patternColours.get(index);
		} catch(Exception ex) {
			return null;
		}
	}
	
	public List<Colour> getPatternColours() {
		return patternColours;
	}

	public void setPatternColours(List<Colour> patternColours) {
		this.patternColours = new ArrayList<>(patternColours);
	}
	
	public void setPatternColour(int index, Colour colour) {
		patternColours.remove(index);
		patternColours.add(index, colour);
	}
	
	public void setSticker(StickerCategory stickerCategory, Sticker sticker) {
		stickers.put(stickerCategory.getId(), sticker.getId());
	}

	public void setSticker(String stickerCategoryId, String stickerId) {
		stickers.put(stickerCategoryId, stickerId);
	}

	public void removeSticker(StickerCategory stickerCategory) {
		stickers.remove(stickerCategory.getId());
	}

	public void removeSticker(String stickerCategoryId) {
		stickers.remove(stickerCategoryId);
	}
	
	public Map<String, String> getStickers() {
		return stickers;
	}

	public Map<StickerCategory, Sticker> getStickersAsObjects() {
		Map<StickerCategory, Sticker> stickersAsObjects = new HashMap<>();
		
		for(Entry<StickerCategory, List<Sticker>> typeStickers : this.getClothingType().getStickers().entrySet()) {
			if(getStickers().containsKey(typeStickers.getKey().getId())) {
				for(Sticker typeSticker : typeStickers.getValue()) {
					if(getStickers().get(typeStickers.getKey().getId()).equals(typeSticker.getId())) {
						stickersAsObjects.put(typeStickers.getKey(), typeSticker);
						break;
					}
				}
			}
		}
		
		return stickersAsObjects;
	}
	
	public void setStickers(Map<String, String> stickers) {
		this.stickers = new HashMap<>(stickers);
	}
	
	public void setStickersAsObjects(Map<StickerCategory, Sticker> stickers) {
		this.stickers = new HashMap<>();
		for(Entry<StickerCategory, Sticker> entry : stickers.entrySet()) {
			this.stickers.put(entry.getKey().getId(), entry.getValue().getId());
		}
	}

	private static StringBuilder descriptionSB = new StringBuilder();

	public String getTypeDescription() {
		String description = this.getClothingType().getDescription();
		
		Map<StickerCategory, Sticker> stickersAsObjects = this.getStickersAsObjects();
		List<Sticker> orderedStickers = new ArrayList<>(stickersAsObjects.values());
		Collections.sort(orderedStickers, (s1, s2)->s1.getDescriptionPriority()-s2.getDescriptionPriority());
		for(Sticker st : orderedStickers) {
			if(st.isDescriptionFullReplacement()) {
				description = st.getDescription();
				break;
			}
			description += st.getDescription();
		}
		
		return description;
	}
	
	@Override
	public String getDescription() {
		descriptionSB.setLength(0);
		
		descriptionSB.append(
				"<p>"
					+ getTypeDescription()
					+ "<br/>"
					+ (enchantmentKnown
							?(getClothingType().isPlural()?"They have":"It has")+" a value of: "+UtilText.formatAsMoney(getValue())
							:(getClothingType().isPlural()?"They have":"It has")+" an <b>unknown value</b>!")
				+ "</p>");
		
		// Physical resistance
		if(getClothingType().getPhysicalResistance()>0) {
			descriptionSB.append("<p>"
							+ (getClothingType().isPlural()
									? "They are armoured, and provide "
									: "It is armoured, and provides ")
								+ " <b>" + getClothingType().getPhysicalResistance() + "</b> [style.colourResPhysical(" + Attribute.RESISTANCE_PHYSICAL.getName() + ")]."
							+ "</p>");
		}
		if(enchantmentKnown) {
			if(!this.getEffects().isEmpty()) {
				descriptionSB.append("<p>Effects:");
				for (ItemEffect e : this.getEffects()) {
					if(e.getPrimaryModifier()!=TFModifier.CLOTHING_ATTRIBUTE
							&& e.getPrimaryModifier()!=TFModifier.CLOTHING_MAJOR_ATTRIBUTE) {
						for(String s : e.getEffectsDescription(Main.game.getPlayer(), Main.game.getPlayer())) {
							descriptionSB.append("<br/>"+ s);
						}
					}
				}
				for(var entry : this.getAttributeModifiers().entrySet()) {
					descriptionSB.append("<br/><b>"+entry.getKey().getFormattedValue(entry.getValue())+"</b>");
				}
				descriptionSB.append("</p>");
			}
		}

		if(getClothingType().getClothingSet() != null) {
			descriptionSB.append("<p>" + (getClothingType().isPlural() ? "They are" : "It is") + " part of the <b style='color:" + PresetColour.RARITY_EPIC.toWebHexString() + ";'>"
					+ getClothingType().getClothingSet().getName() + "</b> set." + "</p>");
		}

		return descriptionSB.toString();
	}

	public final ClothingType getClothingType() {
		return clothingType;
	}

	public InventorySlot getSlotEquippedTo() {
		return slotEquippedTo;
	}

	public void setSlotEquippedTo(InventorySlot slotEquippedTo) {
		this.slotEquippedTo = slotEquippedTo;
	}

	public boolean isCanBeEquipped(GameCharacter clothingOwner, InventorySlot slot) {
		return reasonUnequippable(clothingOwner,slot).isEmpty();
	}

	public String getCannotBeEquippedText(GameCharacter clothingOwner, InventorySlot slot) {
		return UtilText.parse(clothingOwner,reasonUnequippable(clothingOwner,slot));
	}
	
	@Override
	public Rarity getRarity() {
		if(this.isCondom(this.getClothingType().getEquipSlots().get(0))) {
			if(!this.getEffects().isEmpty() && this.getEffects().get(0).getPotency().isNegative()) {
				return Rarity.JINXED;
			} else {
				return rarity;
			}
		}
		
		if(rarity==Rarity.LEGENDARY || rarity==Rarity.QUEST) {
			return rarity;
		}
		if(this.getClothingType().getClothingSet()!=null || rarity==Rarity.EPIC) {
			return Rarity.EPIC;
		}
		
		if(this.isSealed() || this.isBadEnchantment()) {
			return Rarity.JINXED;
		}
		if(rarity==Rarity.COMMON) {
			if(this.getEffects().size()>1) {
				return Rarity.RARE;
			}
			if(!this.getEffects().isEmpty()) {
				return Rarity.UNCOMMON;
			}
			
			return Rarity.COMMON;
		}
		
		return rarity;
	}
	
	@Override
	public int getValue() {
		float modifier = 1;

		if(!enchantmentKnown) {
			modifier -= 0.5f;
		}
		
		if(this.getRarity()==Rarity.JINXED) {
			modifier -= 0.25f;
		}
		
		if(getColour(0)==PresetColour.CLOTHING_PLATINUM) {
			modifier += 0.2f;
			
		} else if(getColour(0)==PresetColour.CLOTHING_GOLD) {
			modifier += 0.15f;
			
		} else if(getColour(0)==PresetColour.CLOTHING_ROSE_GOLD) {
			modifier += 0.1f;
			
		} else if(getColour(0)==PresetColour.CLOTHING_SILVER) {
			modifier += 0.05f;
		}
		
		for(ItemEffect e : this.getEffects()) {
			if(e.getPrimaryModifier()==TFModifier.CLOTHING_ATTRIBUTE) {
				modifier += e.getPotency().getClothingBonusValue()*0.05f;
				
			} else if(e.getPrimaryModifier()==TFModifier.CLOTHING_MAJOR_ATTRIBUTE) {
				modifier += e.getPotency().getClothingBonusValue()*0.1f;
				
			} else {
				modifier += e.getPotency().getValue()*0.025f;
			}
		}
		
		if(getClothingType().getClothingSet()!=null) {
			modifier += 1;
		}
		
		modifier = Math.max(0.25f, modifier);
		
		return Math.max(1, (int)(this.getClothingType().getBaseValue() * modifier));
	}
	
	@Override
	public int getPrice(float modifier) {
		return super.getPrice(modifier);
	}
	
	public String getBaseName() {
		return name;
	}
	
	@Override
	public String getName() {
		String parsedName = this.getClothingType().getName();
		
		if(!this.getEffects().isEmpty() || !name.isEmpty()) {
			parsedName = name;
		}
		
		Map<StickerCategory, Sticker> stickersAsObjects = this.getStickersAsObjects();
		List<Sticker> orderedStickers = new ArrayList<>(stickersAsObjects.values());
		
		Collections.sort(orderedStickers, (s1, s2)->s1.getNamePrefixPriority()-s2.getNamePrefixPriority());
		String prefix = "";
		for(Sticker st : orderedStickers) {
			if(!st.getNamePrefix().isEmpty()) {
				prefix += st.getNamePrefix() + " ";
			}
		}
		
		Collections.sort(orderedStickers, (s1, s2)->s1.getNamePostfixPriority()-s2.getNamePostfixPriority());
		String postfix = "";
		for(Sticker st : orderedStickers) {
			if(!st.getNamePostfix().isEmpty()) {
				postfix += " " + st.getNamePostfix();
			}
		}
		
		return prefix + parsedName + postfix;
	}
	
	public String getColourName() {
		try {
			if(this.getClothingType().isColourDerivedFromPattern() && this.getPattern()!="none") {
				return this.getPatternColour(0).getName();
			}
			return getColour(0).getName();
		} catch(Exception ex) {
			System.err.println("Warning: AbstractClothing.getColourName() returning null!");
			return "";
		}
	}
	
	/**
	 * @param withDeterminer
	 *            True if you want the determiner to prefix the name
	 * @return A string in the format "blue shirt" or "a blue shirt"
	 */
	public String getName(boolean withDeterminer) {
		return (withDeterminer
					? (getClothingType().isPlural()
							? getClothingType().getDeterminer()
							: UtilText.generateSingularDeterminer(
								getClothingType().isAppendColourName()
									?getColourName()
									:getName()))
						+" "
					: "")
				+ (getClothingType().isAppendColourName()
					?getColourName()+" "
					:"")
				+ getName();
	}
	
	public String getName(boolean withDeterminer, boolean withRarityColour) {
		if(!enchantmentKnown) {
			return (withDeterminer
						? (getClothingType().isPlural()
								? getClothingType().getDeterminer()
								: UtilText.generateSingularDeterminer(
									getClothingType().isAppendColourName()
										?getColourName()
										:getName()))
							+" "
						: "")
					+ (getClothingType().isAppendColourName()
							?getColourName()+" "
							:"")
					+ (withRarityColour
							? (" <span style='color: " + PresetColour.RARITY_UNKNOWN.toWebHexString() + ";'>" + getName() + "</span>")
							: " "+getName());
		} else {
			return (withDeterminer
					? (getClothingType().isPlural()
							? getClothingType().getDeterminer()
							: UtilText.generateSingularDeterminer(
								getClothingType().isAppendColourName()
									?getColourName()
									:getName()))
						+" "
					: "")
					+ (getClothingType().isAppendColourName()
							?getColourName()+" "
							:"")
					+ (withRarityColour
							? (" <span style='color: " + this.getRarity().getColour().toWebHexString() + ";'>" + getName() + "</span>")
							: " "+getName());
		}
	}

	/**
	 * @param withRarityColour If true, the name will be coloured to its rarity.
	 * @return A string in the format "Blue cap of frostbite" or "Gold circlet of anti-magic"
	 */
	@Override
	public String getDisplayName(boolean withRarityColour) {
		return getDisplayName(withRarityColour, true);
	}

	/**
	 * @param withRarityColour If true, the name will be coloured to its rarity.
	 * @param withEnchantmentPostFix If true, an automatically-generated enchantment postfix will be appended to the name's end.
	 * @return A string in the format "Blue cap of frostbite" or "Gold circlet of anti-magic"
	 */
	public String getDisplayName(boolean withRarityColour, boolean withEnchantmentPostFix) {
		if(!this.name.replaceAll("\u00A0"," ").equalsIgnoreCase(this.getClothingType().getName().replaceAll("\u00A0"," "))) { // If this item has a custom name, just display that:
//			System.out.println(this.name+ " | "+this.getClothingType().getName());
			return (withRarityColour
					? (" <span style='color: " + (!this.isEnchantmentKnown()?PresetColour.RARITY_UNKNOWN:this.getRarity().getColour()).toWebHexString() + ";'>" + getName() + "</span>")
					: getName());
		}
		
		Colour c = !this.isEnchantmentKnown()?PresetColour.RARITY_UNKNOWN:this.getRarity().getColour();
		return Util.capitaliseSentence(
				(getClothingType().isAppendColourName()
					?
							getColourName()
					:"")
				+ (!this.getPattern().equalsIgnoreCase("none")?" "+Pattern.getPattern(this.getPattern()).getNiceName():"")
				+ (withRarityColour
					? (" <span style='color: " + c.toWebHexString() + "; "+(this.isVibrator()?"text-shadow: 2px 2px "+c.getShades()[0]+";":"")+"'>" + (this.isVibrator()?"vibrating ":"")+getName() + "</span>")
					: (this.isVibrator()?UtilText.applyVibration(" vibrating "+getName(), c):getName()))
				+ ((withEnchantmentPostFix && !this.getEffects().isEmpty() && this.isEnchantmentKnown() && this.getRarity()!=Rarity.QUEST && this.getRarity()!=Rarity.LEGENDARY && this.getRarity()!=Rarity.EPIC)
						? " "+getEnchantmentPostfix(withRarityColour, "span")
						: "")
				);
	}

	@Override
	public String getSVGString() {
		InventorySlot slotEquippedTo = this.getSlotEquippedTo();
		if(slotEquippedTo==null) {
			slotEquippedTo = this.getClothingType().getEquipSlots().get(0);
		}
		return getClothingType().getSVGImage(slotEquippedTo, getColours(), pattern, getPatternColours(), getStickers());
	}
	
	public String getSVGEquippedString(GameCharacter character) {
		InventorySlot slotEquippedTo = this.getSlotEquippedTo();
		if(slotEquippedTo==null) {
			slotEquippedTo = this.getClothingType().getEquipSlots().get(0);
		}
		return getClothingType().getSVGEquippedImage(character, slotEquippedTo, getColours(), pattern, getPatternColours(), getStickers());
	}

	/**
	 * Applies any extra effects this clothing causes when being equipped. To be called <b>immediately after</b> equipping clothing.
	 * 
	 * @return A description of this clothing being equipped.
	 */
	public String onEquipApplyEffects(GameCharacter clothingOwner, GameCharacter clothingEquipper, boolean rough) {
		var sb = MarkupWriter.string();

		if(!enchantmentKnown)
			this.setEnchantmentKnown(clothingOwner, true);

		sb.text(getClothingType().equipText(clothingOwner, clothingEquipper, this.getSlotEquippedTo(), rough, this, true));
		if(!enchantmentKnown) {
			try(var p = sb.center()) {
				if(isBadEnchantment())
					p.badBold("Negative Enchantment Revealed:");
				else
					p.goodBold("Enchantment Revealed:");
				p.br().text(getDisplayName(true));
				for(var att : getAttributeModifiers().entrySet())
					p.br().text(att.getKey().getFormattedValue(att.getValue()));
			}
		}

		if(this.getItemTags().contains(ItemTag.DILDO_SELF)) {
			int length = this.getClothingType().getPenetrationSelfLength();
			PenisLength penisLength = PenisLength.getPenisLengthFromInt(length);
			PenetrationGirth girth = PenetrationGirth.getGirthFromInt(this.getClothingType().getPenetrationSelfGirth());
			float diameter = Penis.getGenericDiameter(length, girth);

			String part;
			boolean lubed;
			boolean plural = this.getClothingType().isPlural();
			
			try(var p = sb.center()) {
				String formattedName = MarkupWriter.string()
				.span(girth.getColour(),girth.getName())
				.text(", ")
				.span(penisLength.getColour(),Units.size(length))
				.text(" ",getClothingType().getName())
				.build();
				if(this.getSlotEquippedTo()==InventorySlot.VAGINA) {
					part = clothingOwner.getVaginaName(false);
					if(clothingOwner.hasHymen()) {
						p.text("As the ",formattedName,plural?" push":" pushes"," inside of ",name(clothingOwner)," ",part,plural?", they ":", it ")
						.terrible(plural?"tear ":"tears ",their(clothingOwner)," hymen")
						.text("!");
						if(clothingOwner.hasFetish(Fetish.FETISH_PURE_VIRGIN)) {
							p.br();
							if(clothingOwner.isVaginaVirgin())
								p.text("Although ",their(clothingOwner)," pussy can no longer be considered completely 'pure', ",
										name(clothingOwner)," ",are(clothingOwner)," still considered to be a virgin, as ",
										they(clothingOwner)," ",have(clothingOwner)," never been penetrated by another person before...");
							else
								p.text("Having already had sex with someone in the past, the loss of ",name(clothingOwner),
										"'s hymen causes ",their(clothingOwner)," to now consider ",themself(clothingOwner)," a ")
								.terrible("broken virgin")
								.text("!");
						}
					}
					lubed = clothingOwner.getLust() >= clothingOwner.getVaginaWetness().getArousalNeededToGetAssWet();
					//Size:
					if(Main.game.isPenetrationLimitationsEnabled()) {
						if(clothingOwner.hasHymen()) {
							p.br();
						}
						if(length<=clothingOwner.getVaginaMaximumPenetrationDepthComfortable() || clothingOwner.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
							p.text("The full length of the ",formattedName," ")
							.goodMinor("comfortably fits")
							.text(" inside of ",name(clothingOwner)," ",part,"!");
						} else {
							p.text("The ",formattedName," is ")
							.bad("too long")
							.text(" to fit comfortable inside of ",name(clothingOwner),"'s ",part);
							if(clothingOwner.hasFetish(Fetish.FETISH_MASOCHIST))
								p.text(", but as ",theyRe(clothingOwner)," a masochist, ")
								.goodMinor(theyDo(clothingOwner),"n't mind the discomfort");
							else
								p.text(", and is causing ",them(clothingOwner)," ")
								.bad("discomfort");
							p.text("!");
						}
					}
					// Girth:
					if(Capacity.isPenetrationDiameterTooBig(clothingOwner.getVaginaElasticity(), clothingOwner.getVaginaStretchedCapacity(), diameter, lubed)) {
						if(Main.game.isPenetrationLimitationsEnabled()) {
							p.br();
						}
						p.text(plural?"Their ":"Its ")
						.span(girth.getColour(),Units.size(diameter))
						.text(" diameter is ")
						.badMinor("too wide")
						.text(" for ",name(clothingOwner),"'s ",part,", and is ")
						.bad("stretching")
						.text(" ",their(clothingOwner)," out!");
					}
					clothingOwner.setHymen(false);
				}
				else if(this.getSlotEquippedTo()==InventorySlot.ANUS) {
					part = clothingOwner.getAssName();
					lubed = clothingOwner.getLust() >= clothingOwner.getAssWetness().getArousalNeededToGetAssWet();
					//Size:
					if(Main.game.isPenetrationLimitationsEnabled()) {
						if(length<=clothingOwner.getAssMaximumPenetrationDepthComfortable() || clothingOwner.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
							p.text("The full length of the ",formattedName," ")
							.goodMinor("comfortably fits")
							.text(" inside of ",name(clothingOwner),"'s ",part,"!");
						} else {
							p.text("The ",formattedName," is ")
							.bad("too long");
							if(clothingOwner.hasFetish(Fetish.FETISH_MASOCHIST)) {
								p.text(", but as ",theyRe(clothingOwner)," a masochist, ")
								.good(theyDo(clothingOwner),"n't mind the discomfort")
								.text("!");
							} else {
								p.text(" to fit comfortably inside of ",name(clothingOwner),"'s ",part,", and is causing ",them(clothingOwner)," ")
								.bad("discomfort")
								.text("!");
							}
						}
					}
					// Girth:
					if(Capacity.isPenetrationDiameterTooBig(clothingOwner.getAssElasticity(), clothingOwner.getAssStretchedCapacity(), diameter, lubed)) {
						if(Main.game.isPenetrationLimitationsEnabled()) {
							p.br();
						}
						p.text(plural?"Their ":"Its ")
						.span(girth.getColour(),Units.size(diameter))
						.text(" diameter is ")
						.badMinor("too wide")
						.text(" for ",name(clothingOwner),"'s ",part,", and is ")
						.bad("stretching")
						.text(" ",their(clothingOwner)," out!");
					}
				}
				else if(this.getSlotEquippedTo()==InventorySlot.NIPPLE) {
					part = clothingOwner.getNippleNameSingular();
					lubed = clothingOwner.getBreastRawStoredMilkValue()>0;
					//Size:
					if(Main.game.isPenetrationLimitationsEnabled()) {
						if(length<=clothingOwner.getNippleMaximumPenetrationDepthComfortable() || clothingOwner.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
							p.text("The full length of the ",formattedName," ")
							.span(PresetColour.GENERIC_MINOR_GOOD,"comfortably fits")
							.text(" inside of ",name(clothingOwner),"'s ",part,"!");
						} else {
							p.text("The ",formattedName," is ")
							.span(PresetColour.GENERIC_BAD,"too long")
							.text(" to fit comfortably inside of ",name(clothingOwner),"'s ",part);
							if(clothingOwner.hasFetish(Fetish.FETISH_MASOCHIST))
								p.text(", but as ",theyRe(clothingOwner)," a masochist, ")
								.span(PresetColour.GENERIC_GOOD,theyDo(clothingOwner),"n't mind the discomfort")
								.text("!");
							else
								p.text(", and is causing ",them(clothingOwner)," ")
								.span(PresetColour.GENERIC_BAD,"discomfort")
								.text("!");
						}
					}
					// Girth:
					if(Capacity.isPenetrationDiameterTooBig(clothingOwner.getNippleElasticity(), clothingOwner.getNippleStretchedCapacity(), diameter, lubed)) {
						if(Main.game.isPenetrationLimitationsEnabled()) {
							p.br();
						}
						p.text(plural?"Their ":"Its ")
						.span(girth.getColour(),Units.size(diameter))
						.text(" diameter is ")
						.span(PresetColour.GENERIC_MINOR_BAD,"too wide")
						.text(" for ",name(clothingOwner),"'s ",part,", and is ")
						.span(PresetColour.GENERIC_BAD,"stretching")
						.text(" ",them(clothingOwner)," out!");
					}
				}
				else if(this.getSlotEquippedTo()==InventorySlot.MOUTH) {
					lubed = true;
					//Size:
					if(Main.game.isPenetrationLimitationsEnabled()) {
						if(length<=clothingOwner.getFaceMaximumPenetrationDepthComfortable() || clothingOwner.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
							p.text("The full length of the ",formattedName," ")
							.goodMinor("comfortably fits")
							.text(" down ",name(clothingOwner),"'s throat!");
						} else {
							p.text("The ",formattedName," is ")
							.bad("too long")
							.text(" to fit comfortably down ",name(clothingOwner),"'s throat, ");
							if(clothingOwner.hasFetish(Fetish.FETISH_MASOCHIST)) {
								p.text("but as ",theyRe(clothingOwner)," a masochist, ")
								.good(theyDo(clothingOwner),"n't mind the discomfort")
								.text("!");
							} else {
								p.text("and is causing ",them(clothingOwner)," ")
								.bad("discomfort")
								.text("!");
							}
						}
					}
					// Girth:
					if(Capacity.isPenetrationDiameterTooBig(clothingOwner.getFaceElasticity(), clothingOwner.getFaceStretchedCapacity(), diameter, lubed)) {
						if(Main.game.isPenetrationLimitationsEnabled()) {
							p.br();
						}
						p.text(plural?"Their ":"Its ")
						.span(girth.getColour(),Units.size(diameter))
						.text(" diameter is ")
						.badMinor("too wide")
						.text(" for ",name(clothingOwner),"'s throat, and is ")
						.bad("stretching")
						.text(" ",them(clothingOwner)," out!");
					}
				}
			}
		}
		if(this.getItemTags().contains(ItemTag.DILDO_OTHER)) {
			int length = this.getClothingType().getPenetrationOtherLength();
			PenisLength penisLength = PenisLength.getPenisLengthFromInt(length);
			PenetrationGirth girth = PenetrationGirth.getGirthFromInt(this.getClothingType().getPenetrationOtherGirth());
			try(var p = sb.center()) {
				p.text(Name(clothingOwner)," ",are(clothingOwner)," now able to use ",their(clothingOwner)," ")
				.span(girth.getColour(),girth.getName())
				.text(" ")
				.span(penisLength.getColour(),Units.size(length))
				.text(" ",getClothingType().getName()," as a ")
				.span(PresetColour.GENERIC_SEX,"penetrative object during sex")
				.text("!");
			}
		}
		
		//TODO append orifice text
		
		return sb.build();
	}

	/**
	 * @return A description of this clothing being equipped. To be called <b>immediately after</b> equipping clothing.
	 */
	public String onEquipText(GameCharacter clothingOwner, GameCharacter clothingEquipper, boolean rough) {
		return getClothingType().equipText(clothingOwner, clothingEquipper, this.getSlotEquippedTo(), rough, this, false);
	}

	/**
	 * Applies any extra effects this clothing causes when being unequipped. To be called <b>immediately before</b> actually unequipping.
	 * 
	 * @return A description of this clothing being unequipped.
	 */
	public String onUnequipApplyEffects(GameCharacter clothingOwner, GameCharacter clothingEquipper, boolean rough) {
		return getClothingType().unequipText(clothingOwner, clothingEquipper, this.getSlotEquippedTo(), rough, this, true);
	}

	/**
	 * @return A description of this clothing being unequipped. To be called <b>immediately before</b> actually unequipping.
	 */
	public String onUnequipText(GameCharacter clothingOwner, GameCharacter clothingEquipper, boolean rough) {
		return getClothingType().unequipText(clothingOwner, clothingEquipper, this.getSlotEquippedTo(), rough, this, false);
	}

	private static List<String> incompatibleClothing = new ArrayList<>();
	
	public String getDisplacementBlockingDescriptions(GameCharacter equippedToCharacter){
		var b = MarkupWriter.string();
		try(var p = new MarkupWriter.Context(b,"p")) {
			p.open("p").bold("Displacement types:");
			for(BlockedParts bp : getBlockedPartsMap(equippedToCharacter,getSlotEquippedTo())){
				p.br().bold(Util.capitaliseSentence(bp.displacementType.getDescription()),":").text(" ");
				if(bp.displacementType==DisplacementType.REMOVE_OR_EQUIP
						? equippedToCharacter.isAbleToUnequip(this,false,equippedToCharacter)
						: equippedToCharacter.isAbleToBeDisplaced(this,bp.displacementType,false,false,equippedToCharacter)) {
					p.goodBold("Available");
				} else {
					p.badBold("Blocked").text(" by ",equippedToCharacter.getBlockingClothing().getName());
				}
			}
		}
		return b.build();
	}

	/**
	 * null should be passed as the argument for 'slotToBeEquippedTo' in order to return non-slot-specific descriptions.
	 * 
	 * @param equippedToCharacter The character this clothing is equipped to.
	 * @param slotToBeEquippedTo The slot for which this clothing's effects effects are to be described.
	 * @param verbose true if you want a lengthy description of each effect.
	 * @return A List of Strings describing extra features of this ClothingType.
	 */
	public List<String> getExtraDescriptions(GameCharacter equippedToCharacter, InventorySlot slotToBeEquippedTo, boolean verbose) {
		List<String> descriptionsList = new ArrayList<>();
		var player = Main.game.getPlayer();
		boolean plural = clothingType.isPlural();
		String theyAre = plural ? "They are " : "It is ";
		if(slotToBeEquippedTo==null) {
			if(this.isSealed() && enchantmentKnown) {
				var b = MarkupWriter.string();
				if(verbose)
					b.text(plural?"They have":"It has"," been enchanted so as to ")
					.bold(PresetColour.SEALED,"seal ",plural?"themselves":"itself")
					.text(" onto the wearer!");
				else
					b.bold(PresetColour.SEALED,"Sealed");
				descriptionsList.add(b.build());
			}
			if(dirty) {
				var b = MarkupWriter.string();
				if(verbose)
					b.text(plural?"They have":"It has"," been ")
					.bold(PresetColour.DIRTY,"dirtied")
					.text(" by sexual fluids!");
				else
					b.bold(PresetColour.DIRTY,"Dirty");
				descriptionsList.add(b.build());
			}
		}

		if(equippedToCharacter==null) { // The clothing is not currently equipped by anyone:
			incompatibleClothing.clear();
			
			if(slotToBeEquippedTo!=null) {
				if(!getIncompatibleSlots(null, slotToBeEquippedTo).isEmpty()) {
					for (InventorySlot invSlot : getIncompatibleSlots(null, slotToBeEquippedTo)) {
						if(player.getClothingInSlot(invSlot) != null) {
							incompatibleClothing.add(player.getClothingInSlot(invSlot).getClothingType().getName());
						}
					}
				}
				for(AbstractClothing c : player.getClothingCurrentlyEquipped()) {
					for (InventorySlot invSlot : c.getIncompatibleSlots(null, c.getSlotEquippedTo())) {
						if(slotToBeEquippedTo == invSlot) {
							incompatibleClothing.add(c.getClothingType().getName());
						}
					}
				}
				
				List<InventorySlot> incompSlots = getIncompatibleSlots(null, slotToBeEquippedTo);
				if(!incompSlots.isEmpty()) {
					if(verbose) {
						descriptionsList.add(MarkupWriter.string()
							.text(plural?"They ":"It ")
							.badBold(plural?"block":"blocks")
							.text(" the ",Util.inventorySlotsToStringList(incompSlots)," slot",incompSlots.size()==1?"s":"","!")
							.build());
					} else {
						for(InventorySlot slot : incompSlots)
							descriptionsList.add(MarkupWriter.string()
								.badBold("Blocks ",Util.capitaliseSentence(slot.getName()))
								.build());
					}
				}
			}
			if(slotToBeEquippedTo==null) {
				if(clothingType.getFemininityMaximum()<player.getFemininityValue() && !player.hasFetish(Fetish.FETISH_CROSS_DRESSER)) {
					var b = MarkupWriter.string();
					if(verbose)
						b.text(theyAre)
						.span(PresetColour.MASCULINE,"too masculine")
						.text(" for you to wear without feeling embarrassed!");
					else
						b.span(PresetColour.MASCULINE,"Too masculine");
					descriptionsList.add(b.build());
				}
				if(clothingType.getFemininityMinimum()>player.getFemininityValue() && !player.hasFetish(Fetish.FETISH_CROSS_DRESSER)) {
					var b = MarkupWriter.string();
					if(verbose)
						b.text(theyAre)
						.span(PresetColour.FEMININE,"too feminine")
						.text(" for you to wear without feeling embarrassed!");
					else
						b.span(PresetColour.FEMININE,"Too feminine");
					descriptionsList.add(b.build());
				}
				if(!incompatibleClothing.isEmpty()) {
					var b = MarkupWriter.string();
					if(verbose)
						b.text(theyAre)
						.badBold("incompatible with")
						.text(" your ",Util.stringsToStringList(incompatibleClothing, false),"!");
					else
						b.badBold("Incompatible with:");
					descriptionsList.add(b.build());
					if(!verbose)
						descriptionsList.addAll(incompatibleClothing);
				}
			}

		} else { // Being worn:
			if(slotToBeEquippedTo==null) {
				if(clothingType.getFemininityMaximum()<equippedToCharacter.getFemininityValue() && !equippedToCharacter.hasFetish(Fetish.FETISH_CROSS_DRESSER)) {
					var b = MarkupWriter.string();
					if(verbose)
						b.text(theyAre)
						.bold(PresetColour.MASCULINE,"too masculine")
						.text(" for ",name(equippedToCharacter)," to wear without feeling embarrassed!");
					else
						b.bold(PresetColour.MASCULINE,"Too masculine");
					descriptionsList.add(b.build());
				}
				if(clothingType.getFemininityMinimum() > equippedToCharacter.getFemininityValue() && !player.hasFetish(Fetish.FETISH_CROSS_DRESSER)) {
					var b = MarkupWriter.string();
					if(verbose)
						b.text(theyAre)
						.bold(PresetColour.FEMININE,"too feminine")
						.text(" for ",name(equippedToCharacter)," to wear without feeling embarrassed!");
					else
						b.bold(PresetColour.FEMININE,"Too feminine");
					descriptionsList.add(b.build());
				}
			}
			if(slotToBeEquippedTo!=null) {
				List<InventorySlot> incompSlots = getIncompatibleSlots(equippedToCharacter, slotToBeEquippedTo);
				if(!incompSlots.isEmpty()) {
					if(verbose) {
						descriptionsList.add(MarkupWriter.string()
							.text(plural?"They ":"It ")
							.badBold(plural?"block":"blocks")
							.text(" the ",Util.inventorySlotsToStringList(incompSlots)," slot",(incompSlots.size()==1?"s":""),"!")
							.build());
					} else {
						for(InventorySlot slot : incompSlots) {
							descriptionsList.add(MarkupWriter.string().badBold("Blocks ",Util.capitaliseSentence(slot.getName())).build());
						}
					}
				}
			}
			if(slotToBeEquippedTo==null) {
				if(!displacedList.isEmpty()) {
					if(verbose) {
						descriptionsList.add(MarkupWriter.string()
							.text(plural?"They have been ":"It has been ")
							.bold(PresetColour.DISPLACED,Util.displacementTypesToStringList(displacedList))
							.text("!")
							.build());
					} else {
						for(DisplacementType dt : displacedList) {
							descriptionsList.add(MarkupWriter.string().bold(PresetColour.DISPLACED,Util.capitaliseSentence(dt.getDescriptionPast())).build());
						}
					}
				}
			}
		}
		
		Set<ItemTag> universalTags = new HashSet<>(this.getItemTags());
		for(int i=0; i<clothingType.getEquipSlots().size();i++) {
			Set<ItemTag> tags = this.getItemTags(clothingType.getEquipSlots().get(i));
			universalTags.removeIf((it) -> !tags.contains(it));
		}
		
		Set<ItemTag> tagsToBeDescribed;
		if(slotToBeEquippedTo==null) {
			tagsToBeDescribed = new HashSet<>(universalTags);
			
		} else {
			tagsToBeDescribed = new HashSet<>(this.getItemTags(slotToBeEquippedTo));
			tagsToBeDescribed.removeIf((it) -> universalTags.contains(it) && it!=ItemTag.DILDO_SELF);
		}
		
		for(ItemTag tag : tagsToBeDescribed) {
			if(tag.getClothingTooltipAdditions()!=null) {
				for(String description : tag.getClothingTooltipAdditions()) {
					if(tag==ItemTag.DILDO_SELF) {
						int length = clothingType.getPenetrationSelfLength();
						float diameter = Penis.getGenericDiameter(
								clothingType.getPenetrationSelfLength(),
								PenetrationGirth.getGirthFromInt(clothingType.getPenetrationSelfGirth()),
								clothingType.getPenetrationSelfModifiers());
						
						PenisLength pl = PenisLength.getPenisLengthFromInt(length);
						Capacity cap = Capacity.getCapacityFromValue(diameter);
						
						if(slotToBeEquippedTo==null) {
							descriptionsList.add(MarkupWriter.string()
								.text(description,": Length: ")
								.span(pl.getColour(),Units.size(length))
								.text(" Diameter: ")
								.span(cap.getColour(),Units.size(diameter))
								.build());
						}
						
						boolean lubed = false;
						if(slotToBeEquippedTo!=null) {
							if(equippedToCharacter==null) {
								switch(slotToBeEquippedTo) {
									case ANUS:
										if(Main.game.isPenetrationLimitationsEnabled() && !player.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
											if(length>player.getAssMaximumPenetrationDepthComfortable()) {
												var b = MarkupWriter.string();
												if(verbose)
													b.text(theyAre)
													.bad("too long")
													.text(" to be able to fit comfortably into your ass!");
												else
													b.terrible("Too long")
													.text(" for comfortable insertion");
												descriptionsList.add(b.build());
											}
										}
										lubed = player.getLust() >= player.getAssWetness().getArousalNeededToGetAssWet();
										if(Capacity.isPenetrationDiameterTooBig(player.getAssElasticity(), player.getAssStretchedCapacity(), diameter, lubed)) {
											var b = MarkupWriter.string();
											if(verbose)
												b.text(theyAre)
												.bad("too thick")
												.text(" for your ",Capacity.getCapacityFromValue(player.getAssStretchedCapacity()).getDescriptor()," asshole, and would stretch it out if inserted!");
											else
												b.bad("Too thick")
												.text(", will cause ")
												.bad("stretching");
											descriptionsList.add(b.build());
										}
										break;
									case MOUTH:
										if(Main.game.isPenetrationLimitationsEnabled() && !player.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
											if(length>player.getFaceMaximumPenetrationDepthComfortable()) {
												var b = MarkupWriter.string();
												if(verbose)
													b.text(theyAre)
													.bad("too long")
													.text(" to be able to fit comfortably down your throat!");
												else
													b.terrible("Too long")
													.text(" for comfortable insertion");
												descriptionsList.add(b.build());
											}
										}
										lubed = true;
										if(Capacity.isPenetrationDiameterTooBig(player.getFaceElasticity(), player.getFaceStretchedCapacity(), diameter, lubed)) {
											var b = MarkupWriter.string();
											if(verbose)
												b.text(theyAre)
												.bad("too thick")
												.text(" for your throat, and would stretch it out if inserted!");
											else
												b.bad("Too thick")
												.text(", will cause ")
												.bad("stretching");
											descriptionsList.add(b.build());
										}
										break;
									case NIPPLE:
										if(Main.game.isPenetrationLimitationsEnabled() && !player.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
											if(length>player.getNippleMaximumPenetrationDepthComfortable()) {
												var b = MarkupWriter.string();
												if(verbose)
													b.text(theyAre)
													.bad("too long")
													.text(" to be able to fit comfortably into your fuckable nipples!");
												else
													b.terrible("Too long")
													.text(" for comfortable insertion");
												descriptionsList.add(b.build());
											}
										}
										lubed = player.getBreastRawStoredMilkValue()>0;
										if(Capacity.isPenetrationDiameterTooBig(player.getNippleElasticity(), player.getNippleStretchedCapacity(), diameter, lubed)) {
											var b = MarkupWriter.string();
											if(verbose)
												b.text(theyAre)
												.bad("too thick")
												.text(" for your ",Capacity.getCapacityFromValue(player.getNippleStretchedCapacity()).getDescriptor()," nipples, and would stretch them out if inserted!");
											else
												b.bad("Too thick")
												.text(", will cause ")
												.bad("stretching");
											descriptionsList.add(b.build());
										}
										break;
									case VAGINA:
										{	var b = MarkupWriter.string();
											if(verbose)
												b.text(plural?"They will ":"It will ")
												.terrible("tear the hymen")
												.text(" of any pussy ",plural?"they are":"it is"," inserted into!");
											else
												b.terrible("Tears hymen")
												.text(" of virgin pussies");
											descriptionsList.add(b.build());
										}
										if(Main.game.isPenetrationLimitationsEnabled() && !player.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
											if(player.hasVagina() && length>player.getVaginaMaximumPenetrationDepthComfortable()) {
												var b = MarkupWriter.string();
												if(verbose)
													b.text(theyAre)
													.bad("too long")
													.text(" to be able to fit comfortably into your pussy!");
												else
													b.terrible("Too long")
													.text(" for comfortable insertion");
												descriptionsList.add(b.build());
											}
										}
										lubed = player.getLust() >= player.getVaginaWetness().getArousalNeededToGetAssWet();
										if(Capacity.isPenetrationDiameterTooBig(player.getVaginaElasticity(), player.getVaginaStretchedCapacity(), diameter, lubed)) {
											var b = MarkupWriter.string();
											if(verbose)
												b.text(theyAre)
												.bad("too thick")
												.text(" for your ",Capacity.getCapacityFromValue(player.getVaginaStretchedCapacity()).getDescriptor()," pussy, and would stretch it out if inserted!");
											else
												b.bad("Too thick")
												.text(", will cause ")
												.bad("stretching");
											descriptionsList.add(b.build());
										}
										break;
									default:
										break;
								}
								
							} else {
								var discomfort = equippedToCharacter.hasFetish(Fetish.FETISH_MASOCHIST)
								? MarkupWriter.buffer().goodMinor("masochistic pleasure")
								: MarkupWriter.buffer().bad("discomfort");
								switch(slotToBeEquippedTo) {
									case ANUS:
										if(Main.game.isPenetrationLimitationsEnabled() && !equippedToCharacter.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
											if(length>equippedToCharacter.getAssMaximumPenetrationDepthComfortable()) {
												var b = MarkupWriter.string();
												if(verbose)
													b.text(theyAre)
													.bad("too deep")
													.text(" in ",name(equippedToCharacter),"'s ass, and ",plural?"are":"is"," causing ",them(equippedToCharacter)," ",discomfort,"!");
												else if(equippedToCharacter.hasFetish(Fetish.FETISH_MASOCHIST))
													b.terrible("Too deep")
													.text(", giving ")
													.goodMinor("masochistic pleasure");
												else
													b.terrible("Too deep")
													.text(", causing ")
													.bad("discomfort");
												descriptionsList.add(b.build());
											}
										}
										lubed = equippedToCharacter.getLust() >= equippedToCharacter.getAssWetness().getArousalNeededToGetAssWet();
										if(Capacity.isPenetrationDiameterTooBig(equippedToCharacter.getAssElasticity(), equippedToCharacter.getAssStretchedCapacity(), diameter, lubed)) {
											var b = MarkupWriter.string();
											if(verbose)
												b.text(theyAre)
												.bad("too thick")
												.text(" for ",name(equippedToCharacter),"'s ",Capacity.getCapacityFromValue(equippedToCharacter.getAssStretchedCapacity()).getDescriptor()," asshole, and ",plural?"are ":"is ")
												.bad("stretching")
												.text(" it out!");
											else
												b.bad("Too thick")
												.text(", causing ")
												.bad("asshole to stretch");
											descriptionsList.add(b.build());
										}
										break;
									case MOUTH:
										if(Main.game.isPenetrationLimitationsEnabled() && !equippedToCharacter.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
											if(length>equippedToCharacter.getFaceMaximumPenetrationDepthComfortable()) {
												var b = MarkupWriter.string();
												if(verbose)
													b.text(theyAre)
													.bad("too deep")
													.text(" down ",name(equippedToCharacter),"'s throat, and ",plural?"are":"is"," causing ",them(equippedToCharacter)," ",discomfort,"!");
												else if(equippedToCharacter.hasFetish(Fetish.FETISH_MASOCHIST))
													b.terrible("Too deep")
													.text(", giving ")
													.goodMinor("masochistic pleasure");
												else
													b.terrible("Too deep")
													.text(", causing ")
													.bad("discomfort");
												descriptionsList.add(b.build());
											}
										}
										lubed = true;
										if(Capacity.isPenetrationDiameterTooBig(equippedToCharacter.getFaceElasticity(), equippedToCharacter.getFaceStretchedCapacity(), diameter, lubed)) {
											var b = MarkupWriter.string();
											if(verbose)
												b.text(theyAre)
												.bad("too thick")
												.text(" for ",name(equippedToCharacter),"'s throat, and ",plural?"are ":"is ")
												.bad("stretching")
												.text(" it out!");
											else
												b.bad("Too thick")
												.text(", causing ")
												.bad("throat to stretch");
											descriptionsList.add(b.build());
										}
										break;
									case NIPPLE:
										if(Main.game.isPenetrationLimitationsEnabled() && !equippedToCharacter.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
											if(length>equippedToCharacter.getNippleMaximumPenetrationDepthComfortable()) {
												var b = MarkupWriter.string();
												if(verbose)
													b.text(theyAre)
													.bad("too deep")
													.text(" in ",name(equippedToCharacter),"'s fuckable nipples, and ",plural?"are":"is"," causing ",them(equippedToCharacter)," ",discomfort,"!");
												else if(equippedToCharacter.hasFetish(Fetish.FETISH_MASOCHIST))
													b.terrible("Too deep")
													.text(", giving ")
													.goodMinor("masochistic pleasure");
												else
													b.terrible("Too deep")
													.text(", causing ")
													.bad("discomfort");
												descriptionsList.add(b.build());
											}
										}
										lubed = equippedToCharacter.getBreastRawStoredMilkValue()>0;
										if(Capacity.isPenetrationDiameterTooBig(equippedToCharacter.getNippleElasticity(), equippedToCharacter.getNippleStretchedCapacity(), diameter, lubed)) {
											var b = MarkupWriter.string();
											if(verbose)
												b.text(theyAre)
												.bad("too thick")
												.text(" for ",name(equippedToCharacter),"'s ",Capacity.getCapacityFromValue(equippedToCharacter.getNippleStretchedCapacity()).getDescriptor()," nipples, and ",plural?"are ":"is ")
												.bad("stretching")
												.text(" them out!");
											else
												b.bad("Too thick")
												.text(", causing ")
												.bad("nipples to stretch");
											descriptionsList.add(b.build());
										}
										break;
									case VAGINA:
										if(Main.game.isPenetrationLimitationsEnabled() && !equippedToCharacter.hasFetish(Fetish.FETISH_SIZE_QUEEN)) {
											if(equippedToCharacter.hasVagina() && length>equippedToCharacter.getVaginaMaximumPenetrationDepthComfortable()) {
												var b = MarkupWriter.string();
												if(verbose)
													b.text(theyAre)
													.bad("too deep")
													.text(" in ",name(equippedToCharacter),"'s pussy, and ",plural?"are":"is"," causing ",them(equippedToCharacter)," ",discomfort,"!");
												else if(equippedToCharacter.hasFetish(Fetish.FETISH_MASOCHIST))
													b.terrible("Too deep")
													.text(", giving ")
													.goodMinor("masochistic pleasure");
												else
													b.terrible("Too deep")
													.text(", causing ")
													.bad("discomfort");
												descriptionsList.add(b.build());
											}
										}
										lubed = equippedToCharacter.getLust() >= equippedToCharacter.getVaginaWetness().getArousalNeededToGetAssWet();
										if(Capacity.isPenetrationDiameterTooBig(equippedToCharacter.getVaginaElasticity(), equippedToCharacter.getVaginaStretchedCapacity(), diameter, lubed)) {
											var b = MarkupWriter.string();
											if(verbose)
												b.text(theyAre)
												.bad("too thick")
												.text(" for ",name(equippedToCharacter),"'s ",Capacity.getCapacityFromValue(equippedToCharacter.getVaginaStretchedCapacity()).getDescriptor()," pussy, and ",plural?"are ":"is ")
												.bad("stretching")
												.text(" it out!");
											else
												b.bad("Too thick")
												.text(", causing ")
												.bad("pussy to stretch");
											descriptionsList.add(b.build());
										}
										break;
									default:
										break;
								}
							}
						}
						
					} else if(tag==ItemTag.DILDO_OTHER) {
						int length = clothingType.getPenetrationOtherLength();
						float diameter = Penis.getGenericDiameter(
								clothingType.getPenetrationOtherLength(),
								PenetrationGirth.getGirthFromInt(clothingType.getPenetrationOtherGirth()),
								clothingType.getPenetrationOtherModifiers());
								
						PenisLength pl = PenisLength.getPenisLengthFromInt(length);
						Capacity cap = Capacity.getCapacityFromValue(diameter);
						
						descriptionsList.add(MarkupWriter.string()
							.text(description,": Length: ")
							.span(pl.getColour(),Units.size(length))
							.text(" Diameter: ")
							.span(cap.getColour(),Units.size(diameter))
							.build());
						
					} else if(tag==ItemTag.ONAHOLE_SELF) {//TODO requires testing
						OrificeElasticity elasticity = OrificeElasticity.getElasticityFromInt(clothingType.getOrificeSelfElasticity());
						OrificePlasticity plasticity = OrificePlasticity.getElasticityFromInt(clothingType.getOrificeSelfPlasticity());
						Wetness wetness = Wetness.valueOf(clothingType.getOrificeSelfWetness());
						descriptionsList.add(MarkupWriter.string()
							.text(description,
								": Capacity: ",Units.size(clothingType.getPenetrationOtherLength()),
								" Depth: ",Units.size(clothingType.getOrificeSelfDepth()))
							.build());
						descriptionsList.add(MarkupWriter.string()
							.text("Elasticity: ")
							.span(elasticity.getColour(),elasticity.getDescriptor())
							.text(" Plasticity: ")
							.span(plasticity.getColour(),plasticity.getDescriptor())
							.text(" Wetness: ")
							.span(wetness.getColour(),wetness.getDescriptor())
							.build());
						
					} else if(tag==ItemTag.ONAHOLE_OTHER) {//TODO requires testing
						OrificeElasticity elasticity = OrificeElasticity.getElasticityFromInt(clothingType.getOrificeOtherElasticity());
						OrificePlasticity plasticity = OrificePlasticity.getElasticityFromInt(clothingType.getOrificeOtherPlasticity());
						Wetness wetness = Wetness.valueOf(clothingType.getOrificeOtherWetness());
						descriptionsList.add(MarkupWriter.string()
							.text(description,
								": Capacity: ",Units.size(clothingType.getPenetrationOtherLength()),
								" Depth: ",Units.size(clothingType.getOrificeOtherDepth()))
							.build());
						descriptionsList.add(MarkupWriter.string()
							.text("Elasticity: ")
							.span(elasticity.getColour(),elasticity.getDescriptor())
							.text(" Plasticity: ")
							.span(plasticity.getColour(),plasticity.getDescriptor())
							.text(" Wetness: ")
							.span(wetness.getColour(),wetness.getDescriptor())
							.build());
						
					} else {
						descriptionsList.add(description);
					}
				}
			}
		}
		

		return descriptionsList;
	}

	/**
	 * @return A list of blocked body parts. e.g. "Penis, Anus and Vagina" or "Nipples"
	 */
	public String getClothingBlockingDescription(DisplacementType dt, GameCharacter owner, InventorySlot slotToBeEquippedTo, String preFix, String postFix) {
		Set<CoverableArea> coveredAreas = new HashSet<>();// EnumSet.noneOf(CoverableArea.class);

		if(dt == null) {
			for (BlockedParts bp : this.getBlockedPartsMap(owner, slotToBeEquippedTo)) {
				if(!this.getDisplacedList().contains(bp.displacementType)) {
					coveredAreas.addAll(bp.blockedBodyParts);
				}
			}
		} else {
			for (BlockedParts bp : this.getBlockedPartsMap(owner, slotToBeEquippedTo)) {
				if(bp.displacementType == dt) {
					coveredAreas.addAll(bp.blockedBodyParts);
				}
			}
		}
		
		if(owner!=null) {
			if(owner.getVaginaType() == VaginaType.NONE)
				coveredAreas.remove(CoverableArea.VAGINA);
			if(owner.getPenisType() == PenisType.NONE)
				coveredAreas.remove(CoverableArea.PENIS);
		}
		
		if(!coveredAreas.isEmpty())
			return preFix + Util.setToStringListCoverableArea(coveredAreas) + postFix;
		else
			return "";
	}

	public void removeBadEnchantments() {
		this.getEffects().removeIf(e -> (e.getPrimaryModifier() == TFModifier.CLOTHING_ATTRIBUTE || e.getPrimaryModifier() == TFModifier.CLOTHING_MAJOR_ATTRIBUTE) && e.getPotency().isNegative());
	}

	public boolean isSealed() {
		if(this.isUnlocked()) {
			return false;
		}
		for(ItemEffect effect : this.getEffects()) {
			if(effect!=null && effect.getSecondaryModifier()==TFModifier.CLOTHING_SEALING) {
				return true;
			} else if(effect==null) {
				System.err.println("AbstractClothing.isSealed() for "+this.getName()+" is encountering a null ItemEffect!");
			}
		}
		return false;
	}

	/**
	 * <b>Warning:</b> If this clothing is not equipped, and is held in a character's inventory, this method will cause the Map of AbstractClothing in the character's inventory to break.
	 */
	public void setSealed(boolean sealed) {
		if(sealed) {
			this.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_SPECIAL, TFModifier.CLOTHING_SEALING, TFPotency.MINOR_BOOST, 0));
		} else {
			setUnlocked(true);
//			this.getEffects().removeIf(e -> e.getSecondaryModifier() == TFModifier.CLOTHING_SEALING);
		}
	}

	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;
	}
	
	public boolean isUnlocked() {
		return unlocked;
	}

	public int getJinxRemovalCost(GameCharacter remover, boolean selfUnseal) {
		int cost = ItemEffect.SEALED_COST_MINOR_BOOST;
		
		for(ItemEffect effect : this.getEffects()) {
			if(effect.getSecondaryModifier()==TFModifier.CLOTHING_SEALING) {
				switch(effect.getPotency()) {
					case BOOST:
						break;
					case DRAIN:
						cost = ItemEffect.SEALED_COST_DRAIN;
						break;
					case MAJOR_BOOST:
						break;
					case MAJOR_DRAIN:
						cost = ItemEffect.SEALED_COST_MAJOR_DRAIN;
						break;
					case MINOR_BOOST:
						cost = ItemEffect.SEALED_COST_MINOR_BOOST;
						break;
					case MINOR_DRAIN:
						cost = ItemEffect.SEALED_COST_MINOR_DRAIN;
						break;
				}
			}
		}
		if(remover.hasFetish(Fetish.FETISH_BONDAGE_VICTIM) && selfUnseal) {
			cost *= 5;
		}
		return cost;
	}

	public TFPotency getVibratorIntensity() {
		for(ItemEffect effect : this.getEffects()) {
			if(effect!=null && effect.getSecondaryModifier()==TFModifier.CLOTHING_VIBRATION) {
				return effect.getPotency();
				
			} else if(effect==null) {
				System.err.println("AbstractClothing.getVibratorIntensity() for "+this.getName()+" is encountering a null ItemEffect!");
			}
		}
		return null;
	}
	
	public boolean isVibrator() {
		return getVibratorIntensity()!=null;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	/**
	 * If this clothing returns true for <i>isMilkingEquipment()</i>, then it will not be dirtied by this method.
	 */
	public void setDirty(GameCharacter owner, boolean dirty) {
		if(dirty && this.isMilkingEquipment()) {
			return;
		}
		if(owner!=null) {
			if(owner.getClothingCurrentlyEquipped().contains(this)) {
//				System.out.println("1");
				AbstractClothing c = new AbstractClothing(this) {};
				owner.forceUnequipClothingIntoVoid(owner, this);
				c.dirty = dirty;
				owner.equipClothingOverride(c, c.getSlotEquippedTo(), false, false);
				
			} else if(owner.removeClothing(this)) {
//				System.out.println("2");
				AbstractClothing c = new AbstractClothing(this) {};
				c.dirty = dirty;
				owner.addClothing(c, false);
//				enchantmentRemovedClothing = c;
				
			} else {
//				System.out.println("3");
				this.dirty = dirty;
			}
		} else {
//			System.out.println("4");
			this.dirty = dirty;
		}
		
//		if(Main.game.getPlayer()!=null) {
//			if(Main.game.getPlayer().getClothingCurrentlyEquipped().contains(this)) {
//				Main.game.getPlayer().updateInventoryListeners();
//			}
//		}
	}

	public List<DisplacementType> getDisplacedList() {
		return displacedList;
	}
	
	public void clearDisplacementList() {
		displacedList.clear();
	}

	public boolean isEnchantmentKnown() {
		return enchantmentKnown;
	}

	public static AbstractClothing enchantmentRemovedClothing;
	public String setEnchantmentKnown(GameCharacter owner, boolean enchantmentKnown) {
		StringBuilder sb = new StringBuilder();
		
		if(owner!=null) {
			if(owner.removeClothing(this)) {
				AbstractClothing c = new AbstractClothing(this) {};
				c.enchantmentKnown = enchantmentKnown;
				owner.addClothing(c, false);
				enchantmentRemovedClothing = c;
			} else {
				this.enchantmentKnown = enchantmentKnown;
			}
		} else {
			this.enchantmentKnown = enchantmentKnown;
		}
		
		if(enchantmentKnown && !getAttributeModifiers().isEmpty()){
			if(isBadEnchantment()) {
				sb.append(
						"<p style='text-align:center;'>"
								+ "<b style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Negative Enchantment Revealed:</b><br/>"
										+ "<b>"+Util.capitaliseSentence(getDisplayName(true))+"</b>");
				
			} else {
				sb.append(
						"<p style='text-align:center;'>"
								+ "<b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Enchantment Revealed:</b><br/>"
										+ "<b>"+Util.capitaliseSentence(getDisplayName(true))+"</b>");
			}
			
			for(ItemEffect ie : this.getEffects()) {
				for(String s : ie.getEffectsDescription(Main.game.getPlayer(), Main.game.getPlayer())) {
					sb.append("<br/>"+s);
				}
			}
			sb.append("</p>");
			
		} else {
			return "";
		}
		
		return sb.toString();
	}

	public Attribute getCoreEnchantment() {
		Attribute att = null;
		int max = 0;
		for(var entry : getAttributeModifiers().entrySet()) {
			att = entry.getKey();
			if(Math.abs(entry.getValue()) > max) {
				att = entry.getKey();
				max = Math.abs(entry.getValue());
			}
		}
		if(att==null) {
			return Attribute.MAJOR_PHYSIQUE;
		}
		return att;
	}
	
	public String getEnchantmentPostfix(boolean coloured, String tag) {
		if(!this.getEffects().isEmpty() && !this.isCondom(this.getClothingType().getEquipSlots().get(0))) {
			if(this.getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() == TFModifier.CLOTHING_ENSLAVEMENT)) {
				return "of "+(coloured?"<"+tag+" style='color:"+TFModifier.CLOTHING_ENSLAVEMENT.getColour().toWebHexString()+";'>enslavement</"+tag+">":"enslavement");
				
			} else if(this.getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() == TFModifier.CLOTHING_SERVITUDE)) {
				return "of "+(coloured?"<"+tag+" style='color:"+TFModifier.CLOTHING_SERVITUDE.getColour().toWebHexString()+";'>servitude</"+tag+">":"servitude");
				
			} else if(this.getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() == TFModifier.CLOTHING_ORGASM_PREVENTION)) {
				return "of "+(coloured?"<"+tag+" style='color:"+TFModifier.CLOTHING_ORGASM_PREVENTION.getColour().toWebHexString()+";'>orgasm denial</"+tag+">":"orgasm denial");
				
			} else if(this.getEffects().stream().anyMatch(ie->ie.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BEHAVIOUR || ie.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BODY_PART)) {
				ItemEffect itemEffect = this.getEffects().stream().filter(ie->ie.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BEHAVIOUR || ie.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BODY_PART).findFirst().get();
				return "of "+(coloured?"<"+tag+" style='color:"+PresetColour.FETISH.toWebHexString()+";'>"+itemEffect.getSecondaryModifier().getDescriptor()+"</"+tag+">":itemEffect.getSecondaryModifier().getDescriptor());
				
			} else if(this.getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() == TFModifier.CLOTHING_SEALING)) {
				return "of "+(coloured?"<"+tag+" style='color:"+PresetColour.SEALED.toWebHexString()+";'>sealing</"+tag+">":"sealing");
				
			} else if(this.getEffects().stream().anyMatch(ie->ie.getPrimaryModifier() == TFModifier.CLOTHING_ATTRIBUTE || ie.getPrimaryModifier() == TFModifier.CLOTHING_MAJOR_ATTRIBUTE)) {
				String name = this.isBadEnchantment() && this.getCoreEnchantment()!=Attribute.MAJOR_CORRUPTION
						?this.getCoreEnchantment().getNegativeEnchantment()
						:this.getCoreEnchantment().getPositiveEnchantment();
				return "of "+(coloured?"<"+tag+" style='color:"+this.getCoreEnchantment().getColour().toWebHexString()+";'>"+name+"</"+tag+">":name);
				
			} else if(this.getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() != TFModifier.CLOTHING_VIBRATION)) {
				return "of "+(coloured?"<"+tag+" style='color:"+PresetColour.TRANSFORMATION_GENERIC.toWebHexString()+";'>transformation</"+tag+">":"transformation");
			}
		}
		return "";
	}

	public boolean isBadEnchantment() {
		return this.getEffects().stream().mapToInt(e ->
			(((e.getPrimaryModifier() == TFModifier.CLOTHING_ATTRIBUTE || e.getPrimaryModifier() == TFModifier.CLOTHING_MAJOR_ATTRIBUTE))
					?e.getPotency().getClothingBonusValue()*(e.getSecondaryModifier()==TFModifier.CORRUPTION?-1:1)
					:0)
				+ (e.getSecondaryModifier()==TFModifier.CLOTHING_SEALING?-10:0)
				+ (e.getSecondaryModifier()==TFModifier.CLOTHING_SERVITUDE?-10:0)
			).sum()<0;
	}

	public boolean isEnslavementClothing() {
		return this.getEffects().stream().anyMatch(e -> e.getSecondaryModifier() == TFModifier.CLOTHING_ENSLAVEMENT);
	}

	public boolean isSelfTransformationInhibiting() {
		return this.getEffects().stream().anyMatch(e -> e.getSecondaryModifier() == TFModifier.CLOTHING_SERVITUDE);
	}

	public boolean isJinxRemovalInhibiting() {
		return this.getEffects().stream().anyMatch(e -> e.getSecondaryModifier() == TFModifier.CLOTHING_SERVITUDE);
	}

	/**
	 * @return A Value whose key is true if this clothing can be equipped during sex. If false, the Value's value is a description of why it cannot be equipped
	 */
	public Value<Boolean, String> isAbleToBeEquippedDuringSex(InventorySlot slotEquippedTo) {
		if(getItemTags(slotEquippedTo).contains(ItemTag.ENABLE_SEX_EQUIP)) {
			if(isEnslavementClothing()) {
				return new Value<>(false, "Clothing with enslavement enchantments cannot be equipped during sex!");
			}
			return new Value<>(true, "");
		}
		return new Value<>(false, "This item of clothing cannot be equipped during sex!");
	}
	
	@Override
	public List<ItemEffect> getEffects() {
		return effects;
	}
	
	/**
	 * <b>Do not call when equipped to someone!</b> (It will not update the wearer's attributes.)
	 */
	public void addEffect(ItemEffect effect) {
		effects.add(effect);
	}

	/**
	 * <b>Do not call when equipped to someone!</b> (It will not update the wearer's attributes.)
	 */
	public void removeEffect(ItemEffect effect) {
		effects.remove(effect);
	}

	/**
	 * <b>Do not call when equipped to someone!</b> (It will not update the wearer's attributes.)
	 */
	public void clearEffects() {
		effects.clear();
	}
	
	@Override
	public Map<Attribute,Integer> getAttributeModifiers() {
		attributeModifiers.clear();
		
		for(ItemEffect ie : getEffects()) {
			if((ie.getPrimaryModifier() == TFModifier.CLOTHING_ATTRIBUTE || ie.getPrimaryModifier() == TFModifier.CLOTHING_MAJOR_ATTRIBUTE)
					&& (Main.game.isEnchantmentCapacityEnabled() || ie.getSecondaryModifier() != TFModifier.ENCHANTMENT_LIMIT)) {
				attributeModifiers.merge(ie.getSecondaryModifier().getAssociatedAttribute(), ie.getPotency().getClothingBonusValue(), Integer::sum);
			}
		}
		
		return attributeModifiers;
	}
	
	/**
	 * @return An integer value of the 'enchantment capacity cost' for this particular piece of clothing. Does not count negative attribute values, and values of Corruption are reversed (so reducing corruption costs enchantment stability).
	 */
	public int getEnchantmentCapacityCost() {
		var noCorruption = new HashMap<Attribute,Integer>();
		getAttributeModifiers().entrySet().stream().filter(ent -> ent.getKey()!=Attribute.FERTILITY && ent.getKey()!=Attribute.VIRILITY).forEach(ent -> noCorruption.put(ent.getKey(), ent.getValue()*(ent.getKey()==Attribute.MAJOR_CORRUPTION?-1:1)));
		return noCorruption.values().stream().reduce(0, (a, b) -> a + Math.max(0, b));
	}
	
	@Override
	public int getEnchantmentLimit() {
		return clothingType.getEnchantmentLimit();
	}
	
	@Override
	public ItemEffectType getEnchantmentEffect() {
		return clothingType.getEnchantmentEffect();
	}
	
	@Override
	public AbstractCoreType getEnchantmentItemType(List<ItemEffect> effects) {
		return clothingType.getEnchantmentItemType(effects);
	}
	
	public ItemEffect getCondomEffect() {
		for(ItemEffect ie : this.getEffects()) {
			if(ie.getPrimaryModifier()==TFModifier.CLOTHING_CONDOM) {
				return ie;
			}
		}
		return null;
	}
	
	public boolean isMilkingEquipment() {
		return this.getItemTags().contains(ItemTag.MILKING_EQUIPMENT);
	}

	public Set<ItemTag> getItemTags(InventorySlot slot) {
		Set<ItemTag> clothingTags;
		
		if(slot==null) {
			clothingTags = new HashSet<>(this.getClothingType().getDefaultItemTags());
		} else {
			clothingTags = new HashSet<>(this.getClothingType().getItemTags(slot));
		}

		Map<StickerCategory, Sticker> stickersAsObjects = this.getStickersAsObjects();
		for(Sticker st : stickersAsObjects.values()) {
			clothingTags.addAll(st.getTagsApplied());
			clothingTags.removeAll(st.getTagsRemoved());
		}
		
		return clothingTags;
	}
	
	@Override
	public Set<ItemTag> getItemTags() {
		Set<ItemTag> clothingTags;
		
		if(this.getSlotEquippedTo()==null) {
			clothingTags = new HashSet<>(this.getClothingType().getDefaultItemTags());
		} else {
			clothingTags = new HashSet<>(this.getClothingType().getItemTags(this.getSlotEquippedTo()));
		}

		Map<StickerCategory, Sticker> stickersAsObjects = this.getStickersAsObjects();
		for(Sticker st : stickersAsObjects.values()) {
			clothingTags.addAll(st.getTagsApplied());
			clothingTags.removeAll(st.getTagsRemoved());
		}
		
		return clothingTags;
	}
	
	
	// Clothing methods which rely upon ItemTags:
	
	public boolean isCondom(InventorySlot slotEquippedTo) {
		return this.getItemTags(slotEquippedTo).contains(ItemTag.CONDOM);
	}
	
	public boolean isCondom() {
		return this.getItemTags().contains(ItemTag.CONDOM);
	}
	
	public boolean isSexToy(InventorySlot slotEquippedTo) {
		for(ItemTag tag : this.getItemTags(slotEquippedTo)) {
			if(tag.isSexToy()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isTransparent(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.TRANSPARENT);
	}
	
	public boolean isMufflesSpeech(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.MUFFLES_SPEECH);
	}

	public boolean isHindersSight(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.BLOCKS_SIGHT);
	}
	
	public boolean isHindersLegMovement(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.HINDERS_LEG_MOVEMENT);
	}
	
	public boolean isHindersArmMovement(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.HINDERS_ARM_MOVEMENT);
	}
	
	public boolean isDiscardedOnUnequip(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.DISCARDED_WHEN_UNEQUIPPED);
	}

	@Deprecated
	public Value<Boolean, String> isAbleToBeEquipped(GameCharacter clothingOwner, InventorySlot slot) {
		String reason = reasonUnequippable(clothingOwner,slot);
		return new Value<>(reason.isEmpty(),reason);
	}

	/**
	 * @param clothingOwner
	 * Character to equip this clothing.
	 * @param slot
	 * Specifies the area this clothing will be equipped on.
	 * @return
	 * Reason why this clothing cannot be equipped on the inventory slot.
	 * {@code ""} if it is possible.
	 * @see #isCanBeEquipped(GameCharacter, InventorySlot) isCanBeEquipped
	 */
	public String reasonUnequippable(GameCharacter clothingOwner, InventorySlot slot) {
		BodyPartClothingBlock block = slot.getBodyPartClothingBlock(clothingOwner);
		Set<ItemTag> tags = this.getItemTags(slot);
		
		boolean plural = this.getClothingType().isPlural();

		if(this.getClothingType().getItemTags(slot).contains(ItemTag.UNIQUE_NO_NPC_EQUIP) && !clothingOwner.isPlayer()) {
			return MarkupWriter.string().span(PresetColour.GENERIC_BAD,"Only you can equip this item of clothing!").build();
		}

		if(!this.getClothingType().getEquipSlots().contains(slot)) {
			return MarkupWriter.string().span(PresetColour.GENERIC_BAD,"The ",getName()," cannot be equipped into this slot!").build();
		}
		if(block!=null && Collections.disjoint(block.getRequiredTags(), tags)) {
			return MarkupWriter.string().span(PresetColour.GENERIC_BAD,UtilText.parse(clothingOwner,block.getDescription())).build();
		}

		if(tags.contains(ItemTag.FITS_TAUR_BODY) && clothingOwner.getLegConfiguration()!=LegConfiguration.QUADRUPEDAL) {
			return reasonRequiredBody(clothingOwner,plural,"taur bodies");
		}
		if(tags.contains(ItemTag.FITS_LONG_TAIL_BODY) && clothingOwner.getLegConfiguration()!=LegConfiguration.TAIL_LONG) {
			return reasonRequiredBody(clothingOwner,plural,"long-tailed bodies");
		}
		if(tags.contains(ItemTag.FITS_TAIL_BODY) && clothingOwner.getLegConfiguration()!=LegConfiguration.TAIL) {
			return reasonRequiredBody(clothingOwner,plural,"tailed bodies");
		}
		if(tags.contains(ItemTag.FITS_ARACHNID_BODY) && clothingOwner.getLegConfiguration()!=LegConfiguration.ARACHNID) {
			return reasonRequiredBody(clothingOwner,plural,"arachnid bodies");
		}
		if(tags.contains(ItemTag.FITS_CEPHALOPOD_BODY) && clothingOwner.getLegConfiguration()!=LegConfiguration.CEPHALOPOD) {
			return reasonRequiredBody(clothingOwner,plural,"cephalopod bodies");
		}
		if(tags.contains(ItemTag.FITS_AVIAN_BODY) && clothingOwner.getLegConfiguration()!=LegConfiguration.AVIAN) {
			return reasonRequiredBody(clothingOwner,plural,"avian bodies");
		}

		if(tags.contains(ItemTag.ONLY_FITS_FERAL_ALL_BODY) && !clothingOwner.isFeral()) {
			return reasonRequiredBody(clothingOwner,plural,"feral bodies");
		}
		if(tags.contains(ItemTag.ONLY_FITS_FERAL_QUADRUPED_BODY) && (!clothingOwner.isFeral() || clothingOwner.getLegConfiguration()!=LegConfiguration.QUADRUPEDAL)) {
			return reasonRequiredBody(clothingOwner,plural,"feral quadrupedal bodies");
		}
		if(tags.contains(ItemTag.ONLY_FITS_FERAL_ARACHNID_BODY) && (!clothingOwner.isFeral() || clothingOwner.getLegConfiguration()!=LegConfiguration.ARACHNID)) {
			return reasonRequiredBody(clothingOwner,plural,"feral arachnid bodies");
		}
		if(tags.contains(ItemTag.ONLY_FITS_FERAL_AVIAN_BODY) && (!clothingOwner.isFeral() || clothingOwner.getLegConfiguration()!=LegConfiguration.AVIAN)) {
			return reasonRequiredBody(clothingOwner,plural,"feral avian bodies");
		}
		if(tags.contains(ItemTag.ONLY_FITS_FERAL_CEPHALOPOD_BODY) && (!clothingOwner.isFeral() || clothingOwner.getLegConfiguration()!=LegConfiguration.CEPHALOPOD)) {
			return reasonRequiredBody(clothingOwner,plural,"feral cephalopod bodies");
		}
		if(tags.contains(ItemTag.ONLY_FITS_FERAL_TAIL_BODY) && (!clothingOwner.isFeral() || clothingOwner.getLegConfiguration()!=LegConfiguration.TAIL)) {
			return reasonRequiredBody(clothingOwner,plural,"feral tailed bodies");
		}
		if(tags.contains(ItemTag.ONLY_FITS_FERAL_LONG_TAIL_BODY) && (!clothingOwner.isFeral() || clothingOwner.getLegConfiguration()!=LegConfiguration.TAIL_LONG)) {
			return reasonRequiredBody(clothingOwner,plural,"feral long-tailed bodies");
		}
		
		if(tags.contains(ItemTag.FITS_ARM_WINGS_EXCLUSIVE) && !clothingOwner.getArmTypeTags().contains(BodyPartTag.ARM_WINGS)) {
			return reasonRequiredBody(clothingOwner,plural,"arm-wings");
		}
		if(tags.contains(ItemTag.FITS_FEATHERED_ARM_WINGS_EXCLUSIVE) && !clothingOwner.getArmTypeTags().contains(BodyPartTag.ARM_WINGS_FEATHERED)) {
			return reasonRequiredBody(clothingOwner,plural,"feathered arm-wings");
		}
		if(tags.contains(ItemTag.FITS_LEATHERY_ARM_WINGS_EXCLUSIVE) && !clothingOwner.getArmTypeTags().contains(BodyPartTag.ARM_WINGS_LEATHERY)) {
			return reasonRequiredBody(clothingOwner,plural,"leathery arm-wings");
		}
		if(tags.contains(ItemTag.FITS_HOOFS_EXCLUSIVE) && clothingOwner.getLegType().getFootType()!=FootType.HOOFS) {
			return reasonRequiredBody(clothingOwner,plural,"hoofs");
		}
		if(tags.contains(ItemTag.FITS_TALONS_EXCLUSIVE) && clothingOwner.getLegType().getFootType()!=FootType.TALONS) {
			return reasonRequiredBody(clothingOwner,plural,"talons");
		}
		if(clothingOwner.hasPenisIgnoreDildo() && tags.contains(ItemTag.REQUIRES_NO_PENIS)) {
			return reasonBlocked(clothingOwner," a penis");
		}
		if(clothingOwner.hasDildo() && tags.contains(ItemTag.REQUIRES_NO_PENIS)) {
			return reasonBlocked(clothingOwner," equipped a dildo");
		}
		if(!clothingOwner.hasPenisIgnoreDildo() && tags.contains(ItemTag.REQUIRES_PENIS)) {
			return reasonMissing(clothingOwner,"a penis");
		}
		if(clothingOwner.hasVagina() && tags.contains(ItemTag.REQUIRES_NO_VAGINA)) {
			return reasonBlocked(clothingOwner," a vagina");
		}
		if(!clothingOwner.hasVagina() && tags.contains(ItemTag.REQUIRES_VAGINA)) {
			return reasonMissing(clothingOwner,"a vagina");
		}
		if(!clothingOwner.isBreastFuckableNipplePenetration() && tags.contains(ItemTag.REQUIRES_FUCKABLE_NIPPLES)) {
			return Their(clothingOwner)+" nipples are not fuckable, so "+they(clothingOwner)+" can't wear the "+getName()+"!";
		}
		if(clothingOwner.getBody().getBodyMaterial().isRequiresPiercing()) {
			if(slot==InventorySlot.PIERCING_EAR && !clothingOwner.isPiercedEar()){
				return reasonUnpierced(clothingOwner,"ears are");
		
			} else if(slot==InventorySlot.PIERCING_LIP && !clothingOwner.isPiercedLip()){
				return reasonUnpierced(clothingOwner,"lips are");
				
			} else if(slot==InventorySlot.PIERCING_NIPPLE && !clothingOwner.isPiercedNipple()){
				return reasonUnpierced(clothingOwner,"nipples are");
				
			} else if(slot==InventorySlot.PIERCING_NOSE && !clothingOwner.isPiercedNose()){
				return reasonUnpierced(clothingOwner,"nose is");
				
			} else if(slot==InventorySlot.PIERCING_PENIS && !clothingOwner.isPiercedPenis()){
				return reasonUnpierced(clothingOwner,"penis is");
				
			} else if(slot==InventorySlot.PIERCING_STOMACH && !clothingOwner.isPiercedNavel()){
				return reasonUnpierced(clothingOwner,"navel is");
				
			} else if(slot==InventorySlot.PIERCING_TONGUE && !clothingOwner.isPiercedTongue()){
				return reasonUnpierced(clothingOwner,"tongue is");
				
			} else if(slot==InventorySlot.PIERCING_VAGINA && !clothingOwner.isPiercedVagina()){
				return reasonUnpierced(clothingOwner,"vagina is");
			}
		}
		if(slot==InventorySlot.PIERCING_PENIS && !clothingOwner.hasPenisIgnoreDildo()){
			return reasonMissing(clothingOwner,"a penis");
			
		} else if(slot==InventorySlot.PIERCING_VAGINA && !clothingOwner.hasVagina()){
			return reasonMissing(clothingOwner,"a vagina");
		}
		
		if (slot == InventorySlot.WINGS && clothingOwner.getWingType()==WingType.NONE) {
			return reasonMissing(clothingOwner,"any wings");
		}
		if (slot == InventorySlot.HORNS && clothingOwner.getHornType().equals(HornType.NONE)) {
			return reasonMissing(clothingOwner,"any horns");
		}
		if (slot == InventorySlot.TAIL && clothingOwner.getTailType()==TailType.NONE) {
			return reasonMissing(clothingOwner,"a tail");
		}
		return "";
	}

	private String reasonRequiredBody(GameCharacter owner, boolean plural, String required) {
		return "The "+getName()+(plural?" are":" is")+" only suitable for taur bodies, and as such, "+name(owner)+" cannot wear "+(plural?"them.":"it.");
	}

	private String reasonBlocked(GameCharacter owner, String part) {
		return Name(owner)+" "+have(owner)+part+", which is blocking "+them(owner)+" from wearing the "+getName()+"!";
	}

	private String reasonMissing(GameCharacter owner, String part) {
		return NameDo(owner)+"n't have "+part+", so "+they(owner)+" can't wear the "+getName()+"!";
	}

	private String reasonUnpierced(GameCharacter owner, String partIs) {
		return Name(owner)+"'s "+partIs+" not pierced, so "+they(owner)+" can't wear the "+getName()+"!";
	}

	public List<BlockedParts> getBlockedPartsMap(GameCharacter character, InventorySlot slotEquippedTo) {
		Set<ItemTag> tags = this.getItemTags(slotEquippedTo);
		
		if(character!=null) {
			boolean replaceCrotchBoobAccess = false;
			boolean replaceGroinAccess = false;
			switch(character.getLegConfiguration()) {
				case BIPEDAL:
				case TAIL:
				case TAIL_LONG:
				case CEPHALOPOD:
				case WINGED_BIPED:
					// These are all in such a position that normal clothing conceals as normal
					break;
				case ARACHNID:
					if(!tags.contains(ItemTag.FITS_ARACHNID_BODY)) { // Arachnid-specific clothing is configured to be correct.
						// Arachnid crotch boobs are on the front, so that conceals as normal. Genitalia are not concealed.
						replaceGroinAccess = true;
					}
					break;
				case AVIAN:
					if(!tags.contains(ItemTag.FITS_AVIAN_BODY)) { // Avian-specific clothing is configured to be correct.
						// Avian crotch boobs are on the front, so that conceals as normal. Genitalia are not concealed.
						replaceGroinAccess = true;
					}
					break;
				case QUADRUPEDAL:
					if(!tags.contains(ItemTag.FITS_TAUR_BODY)) { // Taur-specific clothing is configured to be correct.
						replaceCrotchBoobAccess = true;
						replaceGroinAccess = true;
					}
					break;
			}
			if(replaceGroinAccess
					&& slotEquippedTo!=InventorySlot.ANUS
					&& slotEquippedTo!=InventorySlot.PENIS
					&& slotEquippedTo!=InventorySlot.VAGINA
					&& slotEquippedTo!=InventorySlot.PIERCING_PENIS
					&& slotEquippedTo!=InventorySlot.PIERCING_VAGINA) { // Clothing in groin slots should always be fine, so don't replace their values.
				boolean cAccess = replaceCrotchBoobAccess;
				List<BlockedParts> modifiedBlockedParts = new ArrayList<>();
				for(BlockedParts blockedparts : this.clothingType.getBlockedParts(slotEquippedTo)) {
					BlockedParts copy = new BlockedParts(blockedparts);
					
					copy.blockedBodyParts = copy.blockedBodyParts.stream().filter(
						bp ->
							bp!=CoverableArea.ANUS && bp!=CoverableArea.ASS
							&& bp!=CoverableArea.FEET && bp!=CoverableArea.LEGS
							&& bp!=CoverableArea.MOUND && bp!=CoverableArea.PENIS
							&& bp!=CoverableArea.TESTICLES && bp!=CoverableArea.THIGHS
							&& bp!=CoverableArea.VAGINA && (!cAccess || (bp!=CoverableArea.BREASTS_CROTCH && bp!=CoverableArea.NIPPLES_CROTCH))
						).collect(Collectors.toList());
					
					copy.clothingAccessRequired = copy.clothingAccessRequired.stream().filter(
						ca ->
							ca!=ClothingAccess.ANUS && ca!=ClothingAccess.GROIN
							&& ca!=ClothingAccess.CALVES && ca!=ClothingAccess.FEET
							&& ca!=ClothingAccess.LEGS_UP_TO_GROIN && ca!=ClothingAccess.LEGS_UP_TO_GROIN_LOW_LEVEL
							&& ca!=ClothingAccess.WAIST
						).collect(Collectors.toList());
					
					copy.clothingAccessBlocked = copy.clothingAccessBlocked.stream().filter(
						ca ->
							ca!=ClothingAccess.ANUS && ca!=ClothingAccess.GROIN
							&& ca!=ClothingAccess.CALVES && ca!=ClothingAccess.FEET
							&& ca!=ClothingAccess.LEGS_UP_TO_GROIN && ca!=ClothingAccess.LEGS_UP_TO_GROIN_LOW_LEVEL
							&& ca!=ClothingAccess.WAIST
						).collect(Collectors.toList());
					
					copy.concealedSlots = copy.concealedSlots.stream().filter(
						cs ->
							cs!=InventorySlot.ANKLE && cs!=InventorySlot.ANUS
							&& cs!=InventorySlot.FOOT && cs!=InventorySlot.GROIN
							&& cs!=InventorySlot.LEG && cs!=InventorySlot.PENIS
							&& cs!=InventorySlot.PIERCING_PENIS && cs!=InventorySlot.PIERCING_VAGINA
							&& cs!=InventorySlot.SOCK && cs!=InventorySlot.TAIL
							&& cs!=InventorySlot.VAGINA // There is no slot for crotch boobs, and is handled in CharacterInventory.isCoverableAreaExposed()
						).collect(Collectors.toList());
					
					modifiedBlockedParts.add(copy);
				}
				return modifiedBlockedParts;
			}
		}
		return clothingType.getBlockedParts(slotEquippedTo);
	}
	
	public boolean isConcealsSlot(GameCharacter character, InventorySlot slotEquippedTo, InventorySlot slotToCheck) {
		for(BlockedParts blockedPart : this.getBlockedPartsMap(character, slotEquippedTo)) {
			if(blockedPart.concealedSlots.contains(slotToCheck) && !this.getItemTags(slotEquippedTo).contains(ItemTag.TRANSPARENT)) {
				return true;
			}
		}
		return false;
	}

	public boolean isConcealsCoverableArea(GameCharacter character, InventorySlot slotEquippedTo, CoverableArea area) {
		for(BlockedParts blockedPart : this.getBlockedPartsMap(character, slotEquippedTo)) {
			if(blockedPart.blockedBodyParts.contains(area) && !this.getItemTags(slotEquippedTo).contains(ItemTag.TRANSPARENT)) {
				return true;
			}
		}
		return false;
	}
	
	public List<InventorySlot> getIncompatibleSlots(GameCharacter character, InventorySlot slotEquippedTo) { //TODO
		if(character!=null) {
			boolean replace = false;
			switch(character.getLegConfiguration()) {
				case BIPEDAL:
				case TAIL:
				case TAIL_LONG:
				case CEPHALOPOD:
				case WINGED_BIPED:
					// These are all in such a position that normal clothing conceals as normal
					break;
				case ARACHNID:
					if(!this.getItemTags(slotEquippedTo).contains(ItemTag.FITS_ARACHNID_BODY)) { // Arachnid-specific clothing is configured to be correct.
						replace = true;
					}
					break;
				case AVIAN:
					if(!this.getItemTags(slotEquippedTo).contains(ItemTag.FITS_AVIAN_BODY)) { // Avian-specific clothing is configured to be correct.
						replace = true;
					}
					break;
				case QUADRUPEDAL:
					if(!this.getItemTags(slotEquippedTo).contains(ItemTag.FITS_TAUR_BODY)) { // Taur-specific clothing is configured to be correct.
						replace = true;
					}
					break;
			}
			if(replace) {
				List<InventorySlot> modifiedIncompatibleSlots = new ArrayList<>(clothingType.getIncompatibleSlots(slotEquippedTo));
				
				if(InventorySlot.getHumanoidSlots().contains(slotEquippedTo)) {
					modifiedIncompatibleSlots.removeIf(slot -> !InventorySlot.getHumanoidSlots().contains(slot));
				} else {
					modifiedIncompatibleSlots.removeIf(slot -> InventorySlot.getHumanoidSlots().contains(slot));
				}
				
				return modifiedIncompatibleSlots;
			}
		}
		return clothingType.getIncompatibleSlots(slotEquippedTo);
	}

	public List<DisplacementType> getBlockedPartsKeysAsListWithoutNONE(GameCharacter character, InventorySlot slotEquippedTo) {
		if(character!=null) {
			boolean replaceCrotchBoobAccess = false;
			boolean replaceGroinAccess = false;
			switch(character.getLegConfiguration()) {
				case BIPEDAL:
				case TAIL:
				case TAIL_LONG:
				case CEPHALOPOD:
				case WINGED_BIPED:
					// These are all in such a position that normal clothing conceals as normal
					break;
				case ARACHNID:
					if(!this.getItemTags(slotEquippedTo).contains(ItemTag.FITS_ARACHNID_BODY)) { // Arachnid-specific clothing is configured to be correct.
						// Arachnid crotch boobs are on the front, so that conceals as normal. Genitalia are not concealed.
						replaceGroinAccess = true;
					}
					break;
				case AVIAN:
					if(!this.getItemTags(slotEquippedTo).contains(ItemTag.FITS_AVIAN_BODY)) { // Avian-specific clothing is configured to be correct.
						// Avian crotch boobs are on the front, so that conceals as normal. Genitalia are not concealed.
						replaceGroinAccess = true;
					}
					break;
				case QUADRUPEDAL:
					if(!this.getItemTags(slotEquippedTo).contains(ItemTag.FITS_TAUR_BODY)) { // Taur-specific clothing is configured to be correct.
						replaceCrotchBoobAccess = true;
						replaceGroinAccess = true;
					}
					break;
			}
			if(replaceGroinAccess
					&& slotEquippedTo!=InventorySlot.ANUS
					&& slotEquippedTo!=InventorySlot.PENIS
					&& slotEquippedTo!=InventorySlot.VAGINA
					&& slotEquippedTo!=InventorySlot.PIERCING_PENIS
					&& slotEquippedTo!=InventorySlot.PIERCING_VAGINA) { // Clothing in groin slots should always be fine, so don't replace their values.
				boolean cAccess = replaceCrotchBoobAccess;
				List<BlockedParts> modifiedBlockedParts = new ArrayList<>();
				for(BlockedParts blockedparts : clothingType.getBlockedParts(slotEquippedTo)) {
					BlockedParts copy = new BlockedParts(blockedparts);
					
					copy.blockedBodyParts = copy.blockedBodyParts.stream().filter(
						bp ->
							bp!=CoverableArea.ANUS && bp!=CoverableArea.ASS
							&& bp!=CoverableArea.FEET && bp!=CoverableArea.LEGS
							&& bp!=CoverableArea.MOUND && bp!=CoverableArea.PENIS
							&& bp!=CoverableArea.TESTICLES && bp!=CoverableArea.THIGHS
							&& bp!=CoverableArea.VAGINA && (!cAccess || (bp!=CoverableArea.BREASTS_CROTCH && bp!=CoverableArea.NIPPLES_CROTCH))
						).collect(Collectors.toList());
					
					copy.clothingAccessRequired = copy.clothingAccessRequired.stream().filter(
						ca ->
							ca!=ClothingAccess.ANUS && ca!=ClothingAccess.GROIN
							&& ca!=ClothingAccess.CALVES && ca!=ClothingAccess.FEET
							&& ca!=ClothingAccess.LEGS_UP_TO_GROIN && ca!=ClothingAccess.LEGS_UP_TO_GROIN_LOW_LEVEL
							&& ca!=ClothingAccess.WAIST
						).collect(Collectors.toList());
					
					copy.clothingAccessBlocked = copy.clothingAccessBlocked.stream().filter(
						ca ->
							ca!=ClothingAccess.ANUS && ca!=ClothingAccess.GROIN
							&& ca!=ClothingAccess.CALVES && ca!=ClothingAccess.FEET
							&& ca!=ClothingAccess.LEGS_UP_TO_GROIN && ca!=ClothingAccess.LEGS_UP_TO_GROIN_LOW_LEVEL
							&& ca!=ClothingAccess.WAIST
						).collect(Collectors.toList());
					
					copy.concealedSlots = copy.concealedSlots.stream().filter(
						cs ->
							cs!=InventorySlot.ANKLE && cs!=InventorySlot.ANUS
							&& cs!=InventorySlot.FOOT && cs!=InventorySlot.GROIN
							&& cs!=InventorySlot.LEG && cs!=InventorySlot.PENIS
							&& cs!=InventorySlot.PIERCING_PENIS && cs!=InventorySlot.PIERCING_VAGINA
							&& cs!=InventorySlot.SOCK && cs!=InventorySlot.TAIL
							&& cs!=InventorySlot.VAGINA // There is no slot for crotch boobs, and is handled in CharacterInventory.isCoverableAreaExposed()
						).collect(Collectors.toList());
					
					modifiedBlockedParts.add(copy);
				}
				List<DisplacementType> moddedDisplacementTypesAvailableWithoutNONE = new ArrayList<>();
				for (BlockedParts bp : modifiedBlockedParts) {
					if (bp.displacementType != DisplacementType.REMOVE_OR_EQUIP) {
						moddedDisplacementTypesAvailableWithoutNONE.add(bp.displacementType);
					}
				}
				return moddedDisplacementTypesAvailableWithoutNONE;
			}
		}
		return clothingType.getDisplacementTypes(slotEquippedTo);
	}
	
}
