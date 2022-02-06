package com.lilithsthrone.game.inventory.weapon;

import java.util.List;

import com.lilithsthrone.game.inventory.AbstractCoreType;
import com.lilithsthrone.utils.Table;

/**
 * @since 0.1.84
 * @version 0.3.7.4
 * @author Innoxia
 */
public interface WeaponType extends AbstractCoreType {
	
	@Deprecated
	public static AbstractWeaponType getWeaponTypeFromId(String id) {
		return getWeaponTypeFromId(id, true);
	}
	
	/**
	 * @param closestMatch Pass in true if you want to get whatever WeaponType has the closest match to the provided id, even if it's not exactly the same.
	 */
	@Deprecated
	public static AbstractWeaponType getWeaponTypeFromId(String id, boolean closestMatch) {
		return closestMatch ? table.of(id) : table.exact(id).orElse(null);
	}

	private static String sanitize(String id) {
		
		if(id.equals("RANGED_MUSKET")) {	
			id = "innoxia_gun_arcane_musket";
		}
		if(id.equals("MAIN_WESTERN_KKP") || id.equals("innoxia_western_kkp_western_kkp")) {	
			id = "innoxia_gun_western_kkp";
		}
		if(id.equals("innoxia_revolver_revolver")) {	
			id = "innoxia_gun_revolver";
		}

		if(id.equals("innoxia_pistolCrossbow") || id.equals("innoxia_pistolCrossbow_pistol_crossbow")) {
			id = "innoxia_bow_pistol_crossbow";
		}
		
		if(id.equals("MAIN_FEATHER_DUSTER")) {
			id = "innoxia_cleaning_feather_duster";
		}
		if(id.equals("MAIN_WITCH_BROOM")) {
			id = "innoxia_cleaning_witch_broom";
		}
		
		if(id.equals("OFFHAND_BUCKLER") || id.equals("innoxia_buckler_buckler")) {
			id = "innoxia_shield_buckler";
		}
		if(id.equals("innoxia_crudeShield_crude_shield")) {
			id = "innoxia_shield_crude_wooden";
		}
		
		if(id.equals("OFFHAND_BOW_AND_ARROW")) {
			id = "innoxia_bow_shortbow";
		}
		
		if(id.equals("MELEE_KNIGHTLY_SWORD")) {	
			id = "innoxia_europeanSwords_arming_sword";
		}
		if(id.equals("MELEE_ZWEIHANDER")) {
			id = "innoxia_europeanSwords_zweihander";
		}
		
		if(id.equals("OFFHAND_CHAOS_RARE")) {	
			id = "innoxia_feather_rare";
		}
		if(id.equals("OFFHAND_CHAOS_EPIC")) {
			id = "innoxia_feather_epic";
		}

		if(id.equals("MELEE_CHAOS_RARE")) {	
			id = "innoxia_crystal_rare";
		}
		if(id.equals("MELEE_CHAOS_EPIC")) {	
			id = "innoxia_crystal_epic";
		}
		if(id.equals("MELEE_CHAOS_LEGENDARY")) {	
			id = "innoxia_crystal_legendary";
		}
		
		return id;
	}

	@Deprecated
	public static String getIdFromWeaponType(AbstractWeaponType weaponType) {
		return weaponType.getId();
	}

	Table<AbstractWeaponType> table = new Table<>(WeaponType::sanitize) {{

		// Modded weapon types:
		forEachMod("/items/weapons",null,null,(f,n,a)->{
			var v = new AbstractWeaponType(f,a,true) {};
			v.id = n;
			add(n,v);
		});

		// External res weapon types:
		forEachExternal("res/weapons",null,null,(f,n,a)->{
			var v = new AbstractWeaponType(f,a,false) {};
			v.id = n;
			add(n,v);
		});
	}};

	@Deprecated
	public static List<AbstractWeaponType> getAllWeapons() {
		return table.list();
	}
}
