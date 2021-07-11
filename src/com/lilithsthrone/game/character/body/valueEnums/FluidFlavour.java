package com.lilithsthrone.game.character.body.valueEnums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.1.83
 * @version 0.3.9
 * @author Innoxia
 */
public enum FluidFlavour {
	
	CUM("cum", PresetColour.CUM,
			List.of("salty")),
	
	MILK("milk", PresetColour.MILK,
			List.of("creamy")),
	
	GIRL_CUM("girlcum", PresetColour.GIRLCUM,
			List.of("sweet")),

	BUBBLEGUM("bubblegum", PresetColour.BASE_PINK_LIGHT,
			List.of("sweet")),
	
	BEER("beer", PresetColour.BASE_TAN,
			List.of("yeasty",
					"beer-flavoured")),
	
	VANILLA("vanilla", PresetColour.BASE_YELLOW_PALE,
			List.of("sweet",
					"vanilla-flavoured")),
	
	STRAWBERRY("strawberry", PresetColour.BASE_CRIMSON,
			List.of("sweet",
					"strawberry-flavoured")),
	
	CHOCOLATE("chocolate", PresetColour.BASE_BROWN,
			List.of("chocolatey",
					"chocolate-flavoured")),
	
	PINEAPPLE("pineapple", PresetColour.BASE_YELLOW_LIGHT,
			List.of("tart",
					"sour",
					"tangy",
					"pineapple-flavoured")),
	
	HONEY("honey", PresetColour.BASE_YELLOW,
			List.of("sweet",
					"honey-flavoured")),
	
	MINT("mint", PresetColour.BASE_GREEN_LIME,
			List.of("minty")),
	
	CHERRY("cherry", PresetColour.BASE_RED_DARK,
			List.of("sweet",
					"cherry-flavoured")),
	
	// ------ Icons for these made by 'Charisma is my Dump Stat': ------ //
	
	COFFEE("coffee", PresetColour.BASE_BROWN_DARK,
			List.of("bitter",
					"coffee-flavoured")),
	
	TEA("tea", PresetColour.BASE_GREEN,
			List.of("tea-flavoured")),
	
	MAPLE("maple", PresetColour.BASE_RED,
			List.of("sweet",
					"maple-flavoured")),
	
	CINNAMON("cinnamon", PresetColour.BASE_BROWN,
			List.of("cinnamon-flavoured")),

	LEMON("lemon", PresetColour.BASE_YELLOW,
			List.of("sour",
					"lemon-flavoured")),
	
	// ------------ //
	
	// ------ Icons for these made by 'DSG': ------ //
	
	ORANGE("orange", PresetColour.BASE_ORANGE,
			List.of("orange-flavoured")),
	
	GRAPE("grape", PresetColour.BASE_PURPLE,
			List.of("grape-flavoured")),
	
	MELON("melon", PresetColour.BASE_GREEN_LIGHT,
			List.of("melon-flavoured")),
	
	COCONUT("coconut", PresetColour.BASE_BROWN_DARK,
			List.of("coconut-flavoured")),
	
	BLUEBERRY("blueberry", PresetColour.BASE_BLUE_DARK,
			List.of("blueberry-flavoured"))
	
	// ------------ //
	
	;
	
	private String name;
	private Colour colour;
	private List<String> flavourDescriptors;

	private FluidFlavour(String name, Colour colour, List<String> flavourDescriptors) {
		this.name = name;
		this.colour=colour;
		this.flavourDescriptors = flavourDescriptors;
	}
	
	/**
	 * To go into: "You can't get the rich strawberry taste out of your mouth."<br/>
	 * Or: "Strawberry-flavoured"
	 */
	public String getName() {
		return name;
	}
	
	public Colour getColour() {
		return colour;
	}

	public List<String> getFlavourDescriptors() {
		return flavourDescriptors;
	}
	
	public String getRandomFlavourDescriptor() {
		return flavourDescriptors.get(Util.random.nextInt(flavourDescriptors.size()));
	}
	
	public static List<FluidFlavour> getUnnaturalFlavourings() {
		List<FluidFlavour> list = new ArrayList<>(Arrays.asList(FluidFlavour.values()));
		list.remove(CUM);
		list.remove(MILK);
		list.remove(GIRL_CUM);
		return list;
	}
}
