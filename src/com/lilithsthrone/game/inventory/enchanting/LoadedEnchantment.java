package com.lilithsthrone.game.inventory.enchanting;

import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.markings.Tattoo;
import com.lilithsthrone.game.character.markings.TattooType;
import com.lilithsthrone.game.dialogue.utils.EnchantmentDialogue;
import com.lilithsthrone.game.inventory.AbstractCoreItem;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.game.inventory.weapon.AbstractWeapon;
import com.lilithsthrone.game.inventory.weapon.WeaponType;
import com.lilithsthrone.main.Main;

/**
 * @since 0.2.5
 * @version 0.2.5
 * @author Innoxia
 */
public class LoadedEnchantment {
	
	private String name;
	private ItemType itemType;
	private ClothingType clothingType;
	private WeaponType weaponType;
	private TattooType tattooType;
	private List<ItemEffect> effects;
	
	public LoadedEnchantment(String name, ItemType itemType, List<ItemEffect> effects) {
		this.name = name;
		this.itemType = itemType;
		this.clothingType = null;
		this.weaponType = null;
		this.tattooType = null;
		this.effects = effects;
	}
	
	public LoadedEnchantment(String name, ClothingType clothingType, List<ItemEffect> effects) {
		this.name = name;
		this.itemType = null;
		this.clothingType = clothingType;
		this.weaponType = null;
		this.tattooType = null;
		this.effects = effects;
	}
	
	public LoadedEnchantment(String name, WeaponType weaponType, List<ItemEffect> effects) {
		this.name = name;
		this.itemType = null;
		this.clothingType = null;
		this.weaponType = weaponType;
		this.tattooType = null;
		this.effects = effects;
	}
	
	public LoadedEnchantment(String name, TattooType tattooType, List<ItemEffect> effects) {
		this.name = name;
		this.itemType = null;
		this.clothingType = null;
		this.weaponType = null;
		this.tattooType = tattooType;
		this.effects = effects;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isSuitableItemAvailable() {
		return getSuitableItem()!=null;
	}
	
	public AbstractCoreItem getSuitableItem() {
		if(itemType!=null) {
			for(AbstractItem item : Main.game.getPlayer().getAllItemsInInventory().keySet()) {
				if(item.getItemType().equals(itemType)) {
					return item;
				}
			}
			
		} else if(clothingType!=null) {
			List<AbstractClothing> clothingList = new ArrayList<>();
			for(AbstractClothing c : Main.game.getPlayer().getAllClothingInInventory().keySet()) {
				if(c.getClothingType().equals(clothingType) && c.isEnchantmentKnown()) {
					if(c.getEffects().isEmpty()) {
						return c;
					} else {
						clothingList.add(c);
					}
				}
			}
			if(!clothingList.isEmpty()) {
				return clothingList.get(0);
			}
			
		} else if(weaponType!=null) {
			List<AbstractWeapon> weaponList = new ArrayList<>();
			for(AbstractWeapon w : Main.game.getPlayer().getAllWeaponsInInventory().keySet()) {
				if(w.getWeaponType().equals(weaponType) ) {
					if (w.getEffects().isEmpty()) {
						return w;
					} else {
						weaponList.add(w);
					}
				}
			}
			if(!weaponList.isEmpty()) {
				return weaponList.get(0);
			}
			
		} else if(tattooType!=null) {
			return EnchantmentDialogue.getIngredient();
		}
		
		return null;
	}

	public String getSVGString() {
		AbstractCoreItem item = getSuitableItem();
		
		if(itemType!=null) {
			if(item!=null) {
				return ((AbstractItem)item).getSVGString();
			}
			return itemType.getSVGString();
			
		} else if(clothingType!=null) {
			if(item!=null) {
				return ((AbstractClothing)item).getSVGString();
			}
			return clothingType.getSVGImage();
			
		} else if(weaponType!=null) {
			if(item!=null) {
				return ((AbstractWeapon)item).getSVGString();
			}
			return weaponType.getSVGImage();
			
		} else if(tattooType!=null) {
			return ((Tattoo)item).getSVGString();
		}
		
		return "";
	}
	
	public ItemType getItemType() {
		return itemType;
	}

	public ClothingType getClothingType() {
		return clothingType;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public TattooType getTattooType() {
		return tattooType;
	}

	public List<ItemEffect> getEffects() {
		return effects;
	}
}
