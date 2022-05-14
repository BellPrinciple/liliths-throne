package com.lilithsthrone.game.character.markings;

import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.inventory.AbstractCoreType;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.utils.colours.Colour;

/**
 * @since 0.2.6
 * @version 0.4.4.1
 * @author Innoxia
 */
public interface TattooType extends AbstractCoreType {

	String getId();

	boolean isMod();

	int getValue();

	String getName();

	String getDescription();

	String getBodyOverviewDescription();

	List<Colour> getAvailablePrimaryColours();

	List<Colour> getAvailableSecondaryColours();

	List<Colour> getAvailableTertiaryColours();

	Colour getDefaultPrimaryColour();

	Colour getDefaultSecondaryColour();

	Colour getDefaultTertiaryColour();

	List<InventorySlot> getSlotAvailability();

	boolean isLimitedSlotAvailability();

	boolean isAvailable(GameCharacter target);

	boolean isUnique();

	default int getEnchantmentLimit() {
		return 100;
	}

	default AbstractItemEffectType getEnchantmentEffect() {
		return ItemEffectType.TATTOO;
	}

	String getSVGImage(GameCharacter character);

	String getSVGImage(GameCharacter character, Colour colour, Colour colourSecondary, Colour colourTertiary);

	String getPathName();

//	public static AbstractTattooType NONE = new AbstractTattooType(
//			"none",
//			"none",
//			"This tattoo has no particular pattern, and simply displays either writing or a counter.",
//			"a tattoo which lacks any kind of image or pattern",
//			ColourListPresets.JUST_GREY,
//			null,
//			null,
//			null);
	
//	public static AbstractTattooType FLOWERS = new AbstractTattooType(
//			"flowers",
//			"flowers",
//			"This tattoo depicts a flowing series of intertwined flowers.",
//			"a floral-themed tattoo, depicting a flowing series of intertwined flowers",
//			ColourListPresets.ALL,
//			ColourListPresets.ALL,
//			ColourListPresets.ALL,
//			null);

//	public static AbstractTattooType TRIBAL = new AbstractTattooType(
//			"tribal",
//			"tribal",
//			"A series of flowing lines and intricate patterns.",
//			"a tattoo which consists of a series of flowing lines and intricate patterns",
//			ColourListPresets.ALL,
//			null,
//			null,
//			null);

//	public static AbstractTattooType BUTTERFLIES = new AbstractTattooType(
//			"butterflies",
//			"butterflies",
//			"An artistic depiction of a trio of butterflies in mid-flight.",
//			"a tattoo of a trio of butterflies in mid-flight",
//			ColourListPresets.ALL,
//			ColourListPresets.ALL,
//			ColourListPresets.ALL,
//			null);
	
//	public static AbstractTattooType LINES = new AbstractTattooType(
//			"lines",
//			"lines",
//			"A series of flowing, swirling lines.",
//			"a tattoo which consists of a series of flowing, swirling lines",
//			ColourListPresets.ALL,
//			null,
//			null,
//			null);

	public static AbstractTattooType getTattooTypeFromId(String id) {
		return table.of(id);
	}

	public static String getIdFromTattooType(AbstractTattooType tattooType) {
		return tattooType.getId();
	}

	/**
	 * @return A list of tattoos which the target has available to them.
	 */
	public static List<AbstractTattooType> getConditionalTattooTypes(GameCharacter target) {
		List<AbstractTattooType> tattoos = getAllTattooTypes();
		tattoos.removeIf(tattoo -> !tattoo.isAvailable(target));
		return tattoos;
	}
	
	public static List<AbstractTattooType> getAllTattooTypes() {
		var allTattoos = new ArrayList<>(table.list());
		
		allTattoos.sort((t1, t2) -> t1.equals(TattooType.getTattooTypeFromId("innoxia_misc_none"))?-1:(t1.getName().compareTo(t2.getName())));
		
		return allTattoos;
	}
	
	Table table = new Table();

	final class Table extends com.lilithsthrone.utils.Table<AbstractTattooType> {
		private Table() {
			super(Table::sanitize);

		// Modded tattoo types:

			forEachMod("/items/tattoos",null,null,(f,n,a)->{
				var v = new AbstractTattooType(f) {};
				v.id = n;
				add(n,v);
			});

		// External res tattoo types:

			forEachExternal("res/tattoos",null,null,(f,n,a)->{
				var v = new AbstractTattooType(f) {};
				v.id = n;
				add(n,v);
			});

		// Hard-coded tattoo types (all those up above):

			addFields(TattooType.class,AbstractTattooType.class,(k,v)->v.id=k);
		}

		private static String sanitize(String id) {
			return switch(id.toLowerCase()) {
				case "none" -> "innoxia_misc_none";
				case "flowers" -> "innoxia_plant_flowers";
				case "tribal" -> "innoxia_symbol_tribal";
				case "butterflies" -> "innoxia_animal_butterflies";
				case "lines" -> "innoxia_symbol_lines";
				default -> id;
			};
		}
	}
	
}
