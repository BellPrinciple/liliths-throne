package com.lilithsthrone.game.inventory;

import java.util.List;

import com.lilithsthrone.utils.Table;

/**
 * @since 0.1.0
 * @version 0.3.8.2
 * @author Innoxia
 */
public interface SetBonus {

	String getId();

	/**
	 * @param id Will be in the format of: 'innoxia_maid'.
	 */
	@Deprecated
	public static AbstractSetBonus getSetBonusFromId(String id) {
		return table.of(id);
	}

	static String sanitize(String id) {
		if(id.equals("SLUTTY_ENFORCER")) {
			id = "innoxia_slutty_enforcer";
		}
		if(id.equals("MAID")) {
			id = "innoxia_maid";
		}
		if(id.equals("BUTLER")) {
			id = "innoxia_butler";
		}
		if(id.equals("WITCH")) {
			id = "innoxia_witch";
		}
		if(id.equals("SCIENTIST")) {
			id = "innoxia_scientist";
		}
		if(id.equals("MILK_MAID")) {
			id = "innoxia_milk_maid";
		}
		if(id.equals("BDSM")) {
			id = "innoxia_bdsm";
		}
		if(id.equals("CATTLE")) {
			id = "innoxia_cattle";
		}
		if(id.equals("GEISHA")) {
			id = "innoxia_geisha";
		}
		if(id.equals("RONIN")) {
			id = "innoxia_ronin";
		}
		if(id.equals("WEAPON_DAISHO")) {
			id = "innoxia_daisho";
		}
		if(id.equals("JOLNIR")) {
			id = "innoxia_jolnir";
		}
		if(id.equals("SUN")) {
			id = "innoxia_sun";
		}
		if(id.equals("SNOWFLAKE")) {
			id = "innoxia_snowflake";
		}
		if(id.equals("RAINBOW")) {
			id = "innoxia_rainbow";
		}
		if(id.equals("DARK_SIREN")) {
			id = "innoxia_dark_siren";
		}
		if(id.equals("LYSSIETH_GUARD")) {
			id = "innoxia_lyssieth_guard";
		}
		return id;
	}

	@Deprecated
	public static String getIdFromSetBonus(AbstractSetBonus setBonus) {
		return setBonus.getId();
	}

	Table<AbstractSetBonus> table = new Table<>(SetBonus::sanitize) {{

		// Modded set bonus types:
		forEachMod("/setBonuses",null,null,(f,n,a)->{
			var v = new AbstractSetBonus(f,a,true) {};
			v.id = n;
			add(n,v);
		});

		// External res outfit types:
		forEachExternal("res/setBonuses",null,null,(f,n,a)->{
			var v = new AbstractSetBonus(f,a,false) {};
			v.id = n;
			add(n,v);
		});
	}};

	@Deprecated
	public static List<AbstractSetBonus> getAllSetBonuses() {
		return table.list();
	}

}
