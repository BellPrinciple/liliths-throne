package com.lilithsthrone.game.inventory;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.AbstractAttribute;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.body.CoverableArea;
import com.lilithsthrone.game.character.body.types.PenisType;
import com.lilithsthrone.game.character.body.types.VaginaType;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.inventory.clothing.AbstractClothingType;
import com.lilithsthrone.game.inventory.clothing.BlockedParts;
import com.lilithsthrone.game.inventory.clothing.DisplacementType;
import com.lilithsthrone.game.inventory.clothing.Sticker;
import com.lilithsthrone.game.inventory.clothing.StickerCategory;
import com.lilithsthrone.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.game.inventory.enchanting.TFPotency;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Clothing extends CoreItem {

	String getId();

	/**
	 * This equality check excludes equip slot checks, as it is intended to be used for if an unequipped item of clothing is to be compared to an equipped item of clothing.
	 */
	boolean equalsWithoutEquippedSlot(Object o);

	/**
	 * Returns the id of a pattern that the clothing has.
	 * @return
	 */
	String getPattern();

	/**
	 * Changes pattern to specified one. Will not render that pattern if it doesn't exist or the item doesn't support it anyway.
	 * @param pattern
	 */
	void setPattern(String pattern);

	Colour getPatternColour(int index);

	List<Colour> getPatternColours();

	void setPatternColours(List<Colour> patternColours);

	void setPatternColour(int index, Colour colour);

	void setSticker(StickerCategory stickerCategory, Sticker sticker);

	void setSticker(String stickerCategoryId, String stickerId);

	void removeSticker(StickerCategory stickerCategory);

	void removeSticker(String stickerCategoryId);

	default Map<String, String> getStickers() {
		return Map.of();
	}

	default Map<StickerCategory, Sticker> getStickersAsObjects() {
		Map<StickerCategory, Sticker> stickersAsObjects = new HashMap<>();

		for(Map.Entry<StickerCategory, List<Sticker>> typeStickers : this.getClothingType().getStickers().entrySet()) {
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

	void setStickers(Map<String, String> stickers);

	void setStickersAsObjects(Map<StickerCategory, Sticker> stickers);

	default String getTypeDescription() {
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

	AbstractClothingType getClothingType();

	@Override
	default AbstractClothingType getType() {
		return getClothingType();
	}

	InventorySlot getSlotEquippedTo();

	void setSlotEquippedTo(InventorySlot slotEquippedTo);

	boolean isCanBeEquipped(GameCharacter clothingOwner, InventorySlot slot);

	String getCannotBeEquippedText(GameCharacter clothingOwner, InventorySlot slot);

	String getBaseName();

	default String getColourName() {
		try {
			if(getClothingType().isColourDerivedFromPattern() && getPattern()!="none") {
				return getPatternColour(0).getName();
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
	default String getName(boolean withDeterminer) {
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

	String getName(boolean withDeterminer, boolean withRarityColour);

	/**
	 * @param withRarityColour If true, the name will be coloured to its rarity.
	 * @return A string in the format "Blue cap of frostbite" or "Gold circlet of anti-magic"
	 */
	@Override
	default String getDisplayName(boolean withRarityColour) {
		return getDisplayName(withRarityColour, true);
	}

	/**
	 * @param withRarityColour If true, the name will be coloured to its rarity.
	 * @param withEnchantmentPostFix If true, an automatically-generated enchantment postfix will be appended to the name's end.
	 * @return A string in the format "Blue cap of frostbite" or "Gold circlet of anti-magic"
	 */
	String getDisplayName(boolean withRarityColour, boolean withEnchantmentPostFix);

	String getSVGEquippedString(GameCharacter character);

	/**
	 * Applies any extra effects this clothing causes when being equipped. To be called <b>immediately after</b> equipping clothing.
	 *
	 * @return A description of this clothing being equipped.
	 */
	String onEquipApplyEffects(GameCharacter clothingOwner, GameCharacter clothingEquipper, boolean rough);

	/**
	 * @return A description of this clothing being equipped. To be called <b>immediately after</b> equipping clothing.
	 */
	default String onEquipText(GameCharacter clothingOwner, GameCharacter clothingEquipper, boolean rough) {
		return getClothingType().equipText(clothingOwner, clothingEquipper, getSlotEquippedTo(), rough, (AbstractClothing) this, false);
	}

	/**
	 * Applies any extra effects this clothing causes when being unequipped. To be called <b>immediately before</b> actually unequipping.
	 *
	 * @return A description of this clothing being unequipped.
	 */
	default String onUnequipApplyEffects(GameCharacter clothingOwner, GameCharacter clothingEquipper, boolean rough) {
		return getClothingType().unequipText(clothingOwner, clothingEquipper, getSlotEquippedTo(), rough, (AbstractClothing) this, true);
	}

	/**
	 * @return A description of this clothing being unequipped. To be called <b>immediately before</b> actually unequipping.
	 */
	default String onUnequipText(GameCharacter clothingOwner, GameCharacter clothingEquipper, boolean rough) {
		return getClothingType().unequipText(clothingOwner, clothingEquipper, getSlotEquippedTo(), rough, (AbstractClothing) this, false);
	}

	String getDisplacementBlockingDescriptions(GameCharacter equippedToCharacter);

	/**
	 * null should be passed as the argument for 'slotToBeEquippedTo' in order to return non-slot-specific descriptions.
	 *
	 * @param equippedToCharacter The character this clothing is equipped to.
	 * @param slotToBeEquippedTo The slot for which this clothing's effects effects are to be described.
	 * @param verbose true if you want a lengthy description of each effect.
	 * @return A List of Strings describing extra features of this ClothingType.
	 */
	List<String> getExtraDescriptions(GameCharacter equippedToCharacter, InventorySlot slotToBeEquippedTo, boolean verbose);

	/**
	 * @return A list of blocked body parts. e.g. "Penis, Anus and Vagina" or "Nipples"
	 */
	default String getClothingBlockingDescription(DisplacementType dt, GameCharacter owner, InventorySlot slotToBeEquippedTo, String preFix, String postFix) {
		Set<CoverableArea> coveredAreas = new HashSet<>();// EnumSet.noneOf(CoverableArea.class);

		if(dt == null) {
			for (BlockedParts bp : getBlockedPartsMap(owner, slotToBeEquippedTo)) {
				if(!getDisplacedList().contains(bp.displacementType)) {
					coveredAreas.addAll(bp.blockedBodyParts);
				}
			}
		} else {
			for (BlockedParts bp : getBlockedPartsMap(owner, slotToBeEquippedTo)) {
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

	void removeBadEnchantments();

	void removeServitudeEnchantment();

	default boolean isSealed() {
		if(isUnlocked()) {
			return false;
		}
		for(ItemEffect effect : getEffects()) {
			if(effect!=null && effect.getSecondaryModifier()==TFModifier.CLOTHING_SEALING) {
				return true;
			} else if(effect==null) {
				System.err.println("AbstractClothing.isSealed() for "+getName()+" is encountering a null ItemEffect!");
			}
		}
		return false;
	}

	/**
	 * <b>Warning:</b> If this clothing is not equipped, and is held in a character's inventory, this method will cause the Map of AbstractClothing in the character's inventory to break.
	 */
	default void setSealed(boolean sealed) {
		if(sealed) {
			addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_SPECIAL, TFModifier.CLOTHING_SEALING, TFPotency.MINOR_BOOST, 0));
		} else {
			setUnlocked(true);
//			getEffects().removeIf(e -> e.getSecondaryModifier() == TFModifier.CLOTHING_SEALING);
		}
	}

	void setUnlocked(boolean unlocked);

	boolean isUnlocked();

	default int getJinxRemovalCost(GameCharacter remover, boolean selfUnseal) {
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

	default TFPotency getVibratorIntensity() {
		for(ItemEffect effect : this.getEffects()) {
			if(effect!=null && effect.getSecondaryModifier()==TFModifier.CLOTHING_VIBRATION) {
				return effect.getPotency();

			} else if(effect==null) {
				System.err.println("AbstractClothing.getVibratorIntensity() for "+this.getName()+" is encountering a null ItemEffect!");
			}
		}
		return null;
	}

	default boolean isVibrator() {
		return getVibratorIntensity()!=null;
	}

	boolean isDirty();

	/**
	 * If this clothing returns true for <i>isMilkingEquipment()</i>, then it will not be dirtied by this method.
	 */
	void setDirty(GameCharacter owner, boolean dirty);

	default List<DisplacementType> getDisplacedList() {
		return List.of();
	}

	void clearDisplacementList();

	boolean isEnchantmentKnown();

	String setEnchantmentKnown(GameCharacter owner, boolean enchantmentKnown);

	default AbstractAttribute getCoreEnchantment() {
		AbstractAttribute att = null;
		int max = 0;
		for(Map.Entry<AbstractAttribute, Integer> entry : getAttributeModifiers().entrySet()) {
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

	default String getEnchantmentPostfix(boolean coloured, String tag) {
		if(!getEffects().isEmpty() && !isCondom(getClothingType().getEquipSlots().get(0))) {
			if(getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() == TFModifier.CLOTHING_ENSLAVEMENT)) {
				return "of "+(coloured?"<"+tag+" style='color:"+TFModifier.CLOTHING_ENSLAVEMENT.getColour().toWebHexString()+";'>enslavement</"+tag+">":"enslavement");

			} else if(getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() == TFModifier.CLOTHING_SERVITUDE)) {
				return "of "+(coloured?"<"+tag+" style='color:"+TFModifier.CLOTHING_SERVITUDE.getColour().toWebHexString()+";'>servitude</"+tag+">":"servitude");

			} else if(getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() == TFModifier.CLOTHING_ORGASM_PREVENTION)) {
				return "of "+(coloured?"<"+tag+" style='color:"+TFModifier.CLOTHING_ORGASM_PREVENTION.getColour().toWebHexString()+";'>orgasm denial</"+tag+">":"orgasm denial");

			} else if(getEffects().stream().anyMatch(ie->ie.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BEHAVIOUR || ie.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BODY_PART)) {
				ItemEffect itemEffect = getEffects().stream().filter(ie->ie.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BEHAVIOUR || ie.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BODY_PART).findFirst().get();
				return "of "+(coloured?"<"+tag+" style='color:"+PresetColour.FETISH.toWebHexString()+";'>"+itemEffect.getSecondaryModifier().getDescriptor()+"</"+tag+">":itemEffect.getSecondaryModifier().getDescriptor());

			} else if(getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() == TFModifier.CLOTHING_SEALING)) {
				return "of "+(coloured?"<"+tag+" style='color:"+PresetColour.SEALED.toWebHexString()+";'>sealing</"+tag+">":"sealing");

			} else if(getEffects().stream().anyMatch(ie->ie.getPrimaryModifier() == TFModifier.CLOTHING_ATTRIBUTE || ie.getPrimaryModifier() == TFModifier.CLOTHING_MAJOR_ATTRIBUTE)) {
				String name = isBadEnchantment() && getCoreEnchantment()!=Attribute.MAJOR_CORRUPTION
						?getCoreEnchantment().getNegativeEnchantment()
						:getCoreEnchantment().getPositiveEnchantment();
				return "of "+(coloured?"<"+tag+" style='color:"+getCoreEnchantment().getColour().toWebHexString()+";'>"+name+"</"+tag+">":name);

			} else if(this.getEffects().stream().anyMatch(ie->ie.getSecondaryModifier() != TFModifier.CLOTHING_VIBRATION)) {
				return "of "+(coloured?"<"+tag+" style='color:"+PresetColour.TRANSFORMATION_GENERIC.toWebHexString()+";'>transformation</"+tag+">":"transformation");
			}
		}
		return "";
	}

	default boolean isBadEnchantment() {
		return getEffects().stream().mapToInt(e ->
				(e.getPrimaryModifier() == TFModifier.CLOTHING_ATTRIBUTE
						|| e.getPrimaryModifier() == TFModifier.CLOTHING_MAJOR_ATTRIBUTE
						?e.getPotency().getClothingBonusValue()*(e.getSecondaryModifier()==TFModifier.CORRUPTION?-1:1)
						:0)
						+ (e.getSecondaryModifier()==TFModifier.CLOTHING_SEALING?-10:0)
						+ (e.getSecondaryModifier()==TFModifier.CLOTHING_SERVITUDE?-10:0))
				.sum()<0;
	}

	default boolean isEnslavementClothing() {
		return getEffects().stream().anyMatch(e -> e.getSecondaryModifier() == TFModifier.CLOTHING_ENSLAVEMENT);
	}

	default boolean isSelfTransformationInhibiting() {
		return getEffects().stream().anyMatch(e -> e.getSecondaryModifier() == TFModifier.CLOTHING_SERVITUDE);
	}

	default boolean isJinxRemovalInhibiting() {
		return getEffects().stream().anyMatch(e -> e.getSecondaryModifier() == TFModifier.CLOTHING_SERVITUDE);
	}

	/**
	 * @return A Value whose key is true if this clothing can be equipped during sex. If false, the Value's value is a description of why it cannot be equipped
	 */
	Util.Value<Boolean, String> isAbleToBeEquippedDuringSex(InventorySlot slotEquippedTo);

	default Util.Value<Boolean, String> isAbleToBeEquippedDuringSexInAnySlot() {
		for(InventorySlot slot : this.getClothingType().getEquipSlots()) {
			if(getItemTags(slot).contains(ItemTag.ENABLE_SEX_EQUIP)) {
				if(isEnslavementClothing()) {
					return new Util.Value<>(false, "Clothing with enslavement enchantments cannot be equipped during sex!");
				}
				return new Util.Value<>(true, "");
			}
		}
		return new Util.Value<>(false, "This item of clothing cannot be equipped during sex!");
	}

	/**
	 * <b>Do not call when equipped to someone!</b> (It will not update the wearer's attributes.)
	 */
	void addEffect(ItemEffect effect);

	/**
	 * <b>Do not call when equipped to someone!</b> (It will not update the wearer's attributes.)
	 */
	void removeEffect(ItemEffect effect);

	/**
	 * <b>Do not call when equipped to someone!</b> (It will not update the wearer's attributes.)
	 */
	void clearEffects();

	/**
	 * @return An integer value of the 'enchantment capacity cost' for this particular piece of clothing. Does not count negative attribute values, and values of Corruption are reversed (so reducing corruption costs enchantment stability).
	 */
	int getEnchantmentCapacityCost();

	@Override
	default int getEnchantmentLimit() {
		return getClothingType().getEnchantmentLimit();
	}

	@Override
	default AbstractItemEffectType getEnchantmentEffect() {
		return getClothingType().getEnchantmentEffect();
	}

	@Override
	default AbstractCoreType getEnchantmentItemType(List<ItemEffect> effects) {
		return getClothingType().getEnchantmentItemType(effects);
	}

	default ItemEffect getCondomEffect() {
		for(ItemEffect ie : this.getEffects()) {
			if(ie.getPrimaryModifier()==TFModifier.CLOTHING_CONDOM) {
				return ie;
			}
		}
		return null;
	}

	default boolean isMilkingEquipment() {
		return this.getItemTags().contains(ItemTag.MILKING_EQUIPMENT);
	}

	default Set<ItemTag> getItemTags(InventorySlot slot) {
		Set<ItemTag> clothingTags;

		if(slot==null) {
			clothingTags = new HashSet<>(getClothingType().getDefaultItemTags());
		} else {
			clothingTags = new HashSet<>(getClothingType().getItemTags(slot));
		}

		Map<StickerCategory, Sticker> stickersAsObjects = getStickersAsObjects();
		for(Sticker st : stickersAsObjects.values()) {
			clothingTags.addAll(st.getTagsApplied());
			clothingTags.removeAll(st.getTagsRemoved());
		}

		return clothingTags;
	}

	@Override
	default Set<ItemTag> getItemTags() {
		Set<ItemTag> clothingTags;

		if(getSlotEquippedTo()==null) {
			clothingTags = new HashSet<>(getClothingType().getDefaultItemTags());
		} else {
			clothingTags = new HashSet<>(getClothingType().getItemTags(getSlotEquippedTo()));
		}

		Map<StickerCategory, Sticker> stickersAsObjects = getStickersAsObjects();
		for(Sticker st : stickersAsObjects.values()) {
			clothingTags.addAll(st.getTagsApplied());
			clothingTags.removeAll(st.getTagsRemoved());
		}

		return clothingTags;
	}


	// Clothing methods which rely upon ItemTags:

	default boolean isCondom(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.CONDOM);
	}

	default boolean isCondom() {
		return getItemTags().contains(ItemTag.CONDOM);
	}

	default boolean isSexToy(InventorySlot slotEquippedTo) {
		for(ItemTag tag : getItemTags(slotEquippedTo)) {
			if(tag.isSexToy()) {
				return true;
			}
		}
		return false;
	}

	default boolean isTransparent(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.TRANSPARENT);
	}

	default boolean isMufflesSpeech(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.MUFFLES_SPEECH);
	}

	default boolean isHindersSight(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.BLOCKS_SIGHT);
	}

	default boolean isHindersLegMovement(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.HINDERS_LEG_MOVEMENT);
	}

	default boolean isHindersArmMovement(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.HINDERS_ARM_MOVEMENT);
	}

	default boolean isDiscardedOnUnequip(InventorySlot slotEquippedTo) {
		return getItemTags(slotEquippedTo).contains(ItemTag.DISCARDED_WHEN_UNEQUIPPED);
	}

	Util.Value<Boolean, String> isAbleToBeEquipped(GameCharacter owner, InventorySlot slot);

	List<BlockedParts> getBlockedPartsMap(GameCharacter character, InventorySlot slotEquippedTo);

	default boolean isConcealsSlot(GameCharacter character, InventorySlot slotEquippedTo, InventorySlot slotToCheck) {
		if(getItemTags(slotEquippedTo).contains(ItemTag.TRANSPARENT))
			return false;
		return getBlockedPartsMap(character, slotEquippedTo).stream()
				.anyMatch(blockedParts -> blockedParts.concealedSlots.contains(slotToCheck));
	}

	default boolean isConcealsCoverableArea(GameCharacter character, InventorySlot slotEquippedTo, CoverableArea area) {
		if(getItemTags(slotEquippedTo).contains(ItemTag.TRANSPARENT))
			return false;
		return getBlockedPartsMap(character, slotEquippedTo).stream()
				.anyMatch(blockedParts -> blockedParts.blockedBodyParts.contains(area));
	}

	List<InventorySlot> getIncompatibleSlots(GameCharacter character, InventorySlot slotEquippedTo);

	List<DisplacementType> getBlockedPartsKeysAsListWithoutNONE(GameCharacter character, InventorySlot slotEquippedTo);
}
