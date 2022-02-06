package com.lilithsthrone.game.inventory.weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.combat.DamageVariance;
import com.lilithsthrone.game.combat.moves.AbstractCombatMove;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.AbstractCoreType;
import com.lilithsthrone.game.inventory.ColourReplacement;
import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.Rarity;
import com.lilithsthrone.game.inventory.SetBonus;
import com.lilithsthrone.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.utils.Table;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;

/**
 * @since 0.1.84
 * @version 0.3.7.4
 * @author Innoxia
 */
public interface WeaponType extends AbstractCoreType {

	String getId();

	boolean isMod();

	String equipText(GameCharacter character);

	String unequipText(GameCharacter character);

	default String getAttackDescription(GameCharacter character, GameCharacter target, boolean isHit, boolean critical) {
		if(isHit) {
			return UtilText.parse(character, target, getHitText(character, target, critical));
		} else {
			return UtilText.parse(character, target, getMissText(character, target));
		}
	}

	String getHitText(GameCharacter character, GameCharacter target, boolean critical);

	String getMissText(GameCharacter character, GameCharacter target);

	String getOneShotEndTurnRecoveryDescription(GameCharacter character);

	default boolean isAbleToBeUsed(GameCharacter user, GameCharacter target) {
		return getArcaneCost() <= 0 || user.getEssenceCount() > 0;
	}

	default String getUnableToBeUsedDescription() {
		return getArcaneCost() <= 0 ? "" : "You need at least [style.boldBad(one)] [style.boldArcane(arcane essence)] in order to use this weapon!";
	}

	String applyExtraEffects(GameCharacter user, GameCharacter target, boolean isHit, boolean isCritical);

	int getBaseValue();

	default boolean isUsingUnarmedCalculation() {
		return getItemTags().contains(ItemTag.WEAPON_UNARMED);
	}

	boolean isMelee();

	boolean isTwoHanded();

	boolean isOneShot();

	float getOneShotChanceToRecoverAfterTurn();

	float getOneShotChanceToRecoverAfterCombat();

	boolean isAppendDamageName();

	String getDeterminer();

	boolean isPlural();

	String getName();

	String getNamePlural();

	String getAttackDescriptor();

	String getAttackDescriptionPrefix(GameCharacter user, GameCharacter target);

	String getAttackDescription(GameCharacter user, GameCharacter target);

	String getDescription();

	List<String> getExtraEffects();

	String getAuthorDescription();

	Rarity getRarity();

	float getPhysicalResistance();

	SetBonus getClothingSet();

	String getPathName();

	String getEquippedPathName();

	default boolean isEquippedSVGImageDifferent() {
		return !getPathName().equals(getEquippedPathName());
	}

	int getDamage();

	DamageVariance getDamageVariance();

	int getArcaneCost();

	List<Util.Value<Integer, Integer>> getAoeDamage();

	List<DamageType> getAvailableDamageTypes();

	boolean isSpellRegenOnDamageTypeChange();

	Map<DamageType,List<Spell>> getSpells();

	List<Spell> getSpells(DamageType damageType);

	boolean isCombatMoveRegenOnDamageTypeChange();

	Map<DamageType,List<AbstractCombatMove>> getCombatMoves();

	default List<AbstractCombatMove> getCombatMoves(DamageType damageType) {
		var combatMoves = getCombatMoves();
		var damageTypeCombatMoves = new ArrayList<AbstractCombatMove>();
		if(combatMoves.containsKey(null))
			damageTypeCombatMoves.addAll(combatMoves.get(null));
		if(combatMoves.containsKey(damageType))
			damageTypeCombatMoves.addAll(combatMoves.get(damageType));
		return damageTypeCombatMoves;
	}

	default ColourReplacement getColourReplacement(boolean includeDamageTypeReplacement, int index) {
		List<ColourReplacement> list = getColourReplacements(includeDamageTypeReplacement);
		if(index>list.size()-1) {
			return null;
		}
		return list.get(index);
	}

	List<ColourReplacement> getColourReplacements(boolean includeDamageTypeReplacement);

	default String getSVGImage() {
		DamageType dt = DamageType.PHYSICAL;
		if (this.getAvailableDamageTypes() != null) {
			if (!this.getAvailableDamageTypes().contains(dt)) {
				dt = this.getAvailableDamageTypes().get(0);
			}
		}

		List<Colour> colours = new ArrayList<>();

		for(ColourReplacement cr : this.getColourReplacements(false)) {
			colours.add(cr.getFirstOfDefaultColours());
		}

		return getSVGImage(dt, colours);
	}

	String getSVGImage(DamageType dt, List<Colour> colours);

	String getSVGImageDesaturated();

	default String getSVGEquippedImage() {
		DamageType dt = DamageType.PHYSICAL;
		if (this.getAvailableDamageTypes() != null) {
			if (!this.getAvailableDamageTypes().contains(dt)) {
				dt = this.getAvailableDamageTypes().get(0);
			}
		}

		List<Colour> colours = new ArrayList<>();

		for(ColourReplacement cr : this.getColourReplacements(false)) {
			colours.add(cr.getFirstOfDefaultColours());
		}

		return getSVGEquippedImage(dt, colours);
	}

	String getSVGEquippedImage(DamageType dt, List<Colour> colours);

	String getSVGEquippedImageDesaturated();

	// Enchantments:

	List<ItemEffect> getEffects();

	default boolean isAbleToBeSold() {
		return getRarity()!=Rarity.QUEST;
	}

	default boolean isAbleToBeDropped() {
		return getRarity()!=Rarity.QUEST;
	}

	default int getEnchantmentLimit() {
		return 100;
	}

	default AbstractItemEffectType getEnchantmentEffect() {
		return ItemEffectType.WEAPON;
	}

	default AbstractWeaponType getEnchantmentItemType(List<ItemEffect> effects) {
		return (AbstractWeaponType)this;
	}

	List<ItemTag> getItemTags();
	
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
