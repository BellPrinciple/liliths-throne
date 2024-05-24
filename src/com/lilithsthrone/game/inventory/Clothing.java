package com.lilithsthrone.game.inventory;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.body.CoverableArea;
import com.lilithsthrone.game.inventory.clothing.BlockedParts;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.game.inventory.clothing.DisplacementType;
import com.lilithsthrone.game.inventory.clothing.Sticker;
import com.lilithsthrone.game.inventory.clothing.StickerCategory;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.game.inventory.enchanting.TFPotency;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.Colour;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Clothing extends CoreItem {

	String getId();
	boolean equalsWithoutEquippedSlot(Object o);

	String getPattern();
	void setPattern(String pattern);
	Colour getPatternColour(int index);
	List<Colour> getPatternColours();
	void setPatternColours(List<Colour> patternColours);
	void setPatternColour(int index, Colour colour);

	void setSticker(StickerCategory stickerCategory, Sticker sticker);
	void setSticker(String stickerCategoryId, String stickerId);
	void removeSticker(StickerCategory stickerCategory);
	void removeSticker(String stickerCategoryId);
	Map<String, String> getStickers();
	Map<StickerCategory, Sticker> getStickersAsObjects();
	void setStickers(Map<String, String> stickers);
	void setStickersAsObjects(Map<StickerCategory, Sticker> stickers);

	String getTypeDescription();
	ClothingType getClothingType();
	InventorySlot getSlotEquippedTo();
	void setSlotEquippedTo(InventorySlot slotEquippedTo);
	boolean isCanBeEquipped(GameCharacter clothingOwner, InventorySlot slot);
	String getCannotBeEquippedText(GameCharacter clothingOwner, InventorySlot slot);

	String getBaseName();
	String getColourName();
	String getName(boolean withDeterminer);
	String getName(boolean withDeterminer, boolean withRarityColour);
	String getDisplayName(boolean withRarityColour, boolean withEnchantmentPostFix);
	String getSVGEquippedString(GameCharacter character);

	String onEquipApplyEffects(GameCharacter owner, GameCharacter equipper, boolean rough);
	String onEquipText(GameCharacter owner, GameCharacter equipper, boolean rough);
	String onUnequipApplyEffects(GameCharacter owner, GameCharacter equipper, boolean rough);
	String onUnequipText(GameCharacter owner, GameCharacter equipper, boolean rough);

	String getDisplacementBlockingDescriptions(GameCharacter equippedToCharacter);
	List<String> getExtraDescriptions(GameCharacter equippedToCharacter, InventorySlot slotToBeEquippedTo, boolean verbose);
	String getClothingBlockingDescription(DisplacementType displacementType, GameCharacter owner, InventorySlot slotToBeEquippedTo, String preFix, String postFix);
	void removeBadEnchantments();
	void removeServitudeEnchantment();

	boolean isSealed();
	void setSealed(boolean sealed);

	boolean isUnlocked();
	void setUnlocked(boolean unlocked);

	int getJinxRemovalCost(GameCharacter remover, boolean selfUnseal);

	TFPotency getVibratorIntensity();
	boolean isVibrator();

	boolean isDirty();
	void setDirty(GameCharacter owner, boolean dirty);

	List<DisplacementType> getDisplacedList();
	void clearDisplacementList();

	boolean isEnchantmentKnown();
	String setEnchantmentKnown(GameCharacter owner, boolean enchantmentKnown);
	Attribute getCoreEnchantment();
	String getEnchantmentPostfix(boolean coloured, String tag);
	boolean isBadEnchantment();

	boolean isEnslavementClothing();

	boolean isSelfTransformationInhibiting();

	boolean isJinxRemovalInhibiting();

	Value<Boolean, String> isAbleToBeEquippedDuringSex(InventorySlot slotEquippedTo);
	Value<Boolean, String> isAbleToBeEquippedDuringSexInAnySlot();

	void addEffect(ItemEffect effect);
	void removeEffect(ItemEffect effect);
	void clearEffects();

	int getEnchantmentCapacityCost();
	ItemEffect getCondomEffect();
	boolean isMilkingEquipment();
	Set<ItemTag> getItemTags(InventorySlot slot);
	boolean isCondom(InventorySlot slotEquippedTo);
	boolean isCondom();
	boolean isSexToy(InventorySlot slotEquippedTo);
	boolean isTransparent(InventorySlot slotEquippedTo);
	boolean isMufflesSpeech(InventorySlot slotEquippedTo);
	boolean isHindersSight(InventorySlot slotEquippedTo);
	boolean isHindersLegMovement(InventorySlot slotEquippedTo);
	boolean isHindersArmMovement(InventorySlot slotEquippedTo);
	boolean isDiscardedOnUnequip(InventorySlot slotEquippedTo);
	List<String> getReasonsUnequippable(GameCharacter clothingOwner, InventorySlot slot);
	List<BlockedParts> getBlockedPartsMap(GameCharacter character, InventorySlot slotEquippedTo);
	boolean isConcealsSlot(GameCharacter character, InventorySlot slotEquippedTo, InventorySlot slotToCheck);
	boolean isConcealsCoverableArea(GameCharacter character, InventorySlot slotEquippedTo, CoverableArea area);
	List<InventorySlot> getIncompatibleSlots(GameCharacter character, InventorySlot slotEquippedTo);
	List<DisplacementType> getBlockedPartsKeysAsListWithoutNONE(GameCharacter character, InventorySlot slotEquippedTo);
}
