package com.lilithsthrone.game.inventory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.utils.Table;

/**
 * @since 0.1.0
 * @version 0.3.8.2
 * @author Innoxia
 */
public interface SetBonus {

	String getId();

	boolean isMod();

	String getName();

	int getNumberRequiredForCompleteSet();

	StatusEffect getAssociatedStatusEffect();

	List<InventorySlot> getBlockedSlotsCountingTowardsFullSet();

	default boolean isCharacterWearingCompleteSet(GameCharacter target) {
		long countBlocked = getBlockedSlotsCountingTowardsFullSet().stream()
		.filter(s->s.getBodyPartClothingBlock(target)!=null)
		.count();
		long countEquipped = target.getClothingCurrentlyEquipped().stream()
		.filter(c->c.getClothingType().getClothingSet()==this)
		.count();
		long countWeapon = Stream.of(target.getMainWeaponArray(),target.getOffhandWeaponArray()).flatMap(Arrays::stream)
		.filter(w->w!=null && w.getWeaponType().getClothingSet()==this)
		.count();
		return countEquipped + countWeapon > 0
		&& countBlocked + countEquipped + Math.min(2, countWeapon) >= getNumberRequiredForCompleteSet();
	}

	/**
	 * @param id Will be in the format of: 'innoxia_maid'.
	 */
	public static AbstractSetBonus getSetBonusFromId(String id) {
		return table.of(id);
	}

	static String sanitize(String id) {
		return switch(id) {
			case "SLUTTY_ENFORCER" -> "innoxia_slutty_enforcer";
			case "MAID" -> "innoxia_maid";
			case "BUTLER" -> "innoxia_butler";
			case "WITCH" -> "innoxia_witch";
			case "SCIENTIST" -> "innoxia_scientist";
			case "MILK_MAID" -> "innoxia_milk_maid";
			case "BDSM" -> "innoxia_bdsm";
			case "CATTLE" -> "innoxia_cattle";
			case "GEISHA" -> "innoxia_geisha";
			case "RONIN" -> "innoxia_ronin";
			case "WEAPON_DAISHO" -> "innoxia_daisho";
			case "JOLNIR" -> "innoxia_jolnir";
			case "SUN" -> "innoxia_sun";
			case "SNOWFLAKE" -> "innoxia_snowflake";
			case "RAINBOW" -> "innoxia_rainbow";
			case "DARK_SIREN" -> "innoxia_dark_siren";
			case "LYSSIETH_GUARD" -> "innoxia_lyssieth_guard";
			default -> id;
		};
	}

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

	public static List<AbstractSetBonus> getAllSetBonuses() {
		return table.list();
	}

}
