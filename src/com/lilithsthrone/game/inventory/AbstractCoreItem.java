package com.lilithsthrone.game.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithsthrone.game.character.attributes.AbstractAttribute;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;

/**
 * @since 0.1.0
 * @version 0.3.9
 * @author Innoxia
 */
public abstract class AbstractCoreItem implements CoreItem {

	protected String name;
	protected String namePlural;
	protected String SVGString;
	protected Rarity rarity;

	protected List<Colour> colours;
	
	protected Map<AbstractAttribute, Integer> attributeModifiers;
	
	protected Set<ItemTag> itemTags;

	public AbstractCoreItem(String name,
			String namePlural,
			String SVGString,
			Colour colour,
			Rarity rarity,
			Map<AbstractAttribute, Integer> attributeModifiers) {
		this(name,
				namePlural,
				SVGString,
				colour,
				rarity,
				attributeModifiers,
				new HashSet<>());
	}
	
	public AbstractCoreItem(String name,
			String namePlural,
			String SVGString,
			Colour colour,
			Rarity rarity,
			Map<AbstractAttribute, Integer> attributeModifiers,
			Set<ItemTag> itemTags) {
		super();
		this.name = name;
		this.namePlural = namePlural;
		this.colours = Util.newArrayListOfValues(colour);
		this.rarity = rarity;
		this.SVGString = SVGString;

		this.attributeModifiers = new HashMap<>();
		this.itemTags = new HashSet<>();
		
		if (attributeModifiers != null) {
			for (Entry<AbstractAttribute, Integer> e : attributeModifiers.entrySet()) {
				this.attributeModifiers.put(e.getKey(), e.getValue());
			}
		}
		
		if(itemTags != null) {
			this.itemTags.addAll(itemTags);
		}
	}
	
	public Element saveAsXML(Element parentElement, Document doc) {
		System.err.print("Error: Tried to export an abstract item!");
		return null;
	}
	
	public static AbstractCoreItem loadFromXML(Element parentElement, Document doc) {
		System.err.print("Error: Tried to import an abstract item!");
		return null;
	}

	// Enchantments:

	@Override
	public boolean isAbleToBeEnchanted() {
		return getEnchantmentEffect() != null
				&& getEnchantmentItemType(null) != null;
	}
	
	// Other:
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof AbstractCoreItem){
			if(((AbstractCoreItem)o).getName().equals(this.getName())
				&& ((AbstractCoreItem)o).getColours().equals(this.getColours())
				&& ((AbstractCoreItem)o).getRarity() == this.getRarity()
				&& ((AbstractCoreItem)o).getAttributeModifiers().equals(this.getAttributeModifiers())
				&& ((AbstractCoreItem)o).getEnchantmentEffect() == getEnchantmentEffect()
				&& ((AbstractCoreItem)o).getEnchantmentItemType(null) == getEnchantmentItemType(null)
				&& ((AbstractCoreItem)o).getItemTags().equals(getItemTags())){
					return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.getName().hashCode();
		result = 31 * result + this.getColours().hashCode();
		result = 31 * result + this.getRarity().hashCode();
		result = 31 * result + this.getAttributeModifiers().hashCode();
		if(getEnchantmentEffect()!=null) {
			result = 31 * result + getEnchantmentEffect().hashCode();
		}
		if(getEnchantmentItemType(null)!=null) {
			result = 31 * result + getEnchantmentItemType(null).hashCode();
		}
		if(getItemTags()!=null) {
			result = 31 * result + getItemTags().hashCode();
		}
		return result;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getNamePlural() {
		return namePlural;
	}

	@Override
	public String getDisplayName(boolean withRarityColour) {
		return Util.capitaliseSentence(UtilText.generateSingularDeterminer(name))+ " "+ (withRarityColour ? ("<span style='color: " + rarity.getColour().toWebHexString() + ";'>" + name + "</span>") : name);
	}

	@Override
	public String getDisplayNamePlural(boolean withRarityColour) {
		return Util.capitaliseSentence((withRarityColour ? ("<span style='color: " + rarity.getColour().toWebHexString() + ";'>" + namePlural + "</span>") : namePlural));
	}

	@Override
	public String getSVGString() {
		return SVGString;
	}
	
	@Override
	public void setSVGString(String SVGString) {
		this.SVGString = SVGString;
	}

	@Override
	public int getPrice(float modifier) {
		return (int) (getValue() * modifier);
	}

	@Override
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public Colour getColour(int index) {
		try {
			return colours.get(index);
		} catch(Exception ex) {
			return null;
		}
	}
	
	@Override
	public List<Colour> getColours() {
		return colours;
	}

	@Override
	public void setColours(List<Colour> colours) {
		this.colours = new ArrayList<>(colours);
	}
	
	@Override
	public void setColour(int index, Colour colour) {
		if(colours.size()>index) {
			colours.remove(index);
		}
		colours.add(index, colour);
	}

	@Override
	public Map<AbstractAttribute, Integer> getAttributeModifiers() {
		return attributeModifiers;
	}

	@Override
	public void setAttributeModifiers(Map<AbstractAttribute, Integer> attributeModifiers) {
		this.attributeModifiers = attributeModifiers;
	}
	
	@Override
	public List<ItemEffect> getEffects() {
		return new ArrayList<ItemEffect>();
	}

	@Override
	public Set<ItemTag> getItemTags() {
		return itemTags;
	}
}
