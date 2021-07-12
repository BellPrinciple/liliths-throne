package com.lilithsthrone.game.character.body.coverings;

import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.body.valueEnums.CoveringModifier;
import com.lilithsthrone.game.character.body.valueEnums.CoveringPattern;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.2.8
 * @version 0.3.7
 * @author Pimgd, Innoxia
 */
public class BodyCoveringTemplateFactory {
	
	public static BodyCoveringTemplate createSkin(Map<CoveringPattern, Integer> coverPatterns, List<Colour> naturalPrimaryColors, List<Colour> naturalSecondaryColours, List<Colour> dyeColours) {
		return new BodyCoveringTemplate("a layer of",
				false,
				"skin",
				"skin",
				List.of(CoveringModifier.SMOOTH),
				null,
				coverPatterns,
				CoveringPattern.allStandardCoveringPatterns,
				naturalPrimaryColors,
				dyeColours,
				naturalSecondaryColours,
				dyeColours);
	}
	
	public static BodyCoveringTemplate createTopSkin(Map<CoveringPattern, Integer> coverPatterns, List<Colour> skinColors) {
		return createSkin(coverPatterns, skinColors, skinColors, null);
	}
	
	public static BodyCoveringTemplate createBottomSkin(List<Colour> skinColors) {
		return createSkin(Map.of(CoveringPattern.NONE,1), skinColors, null, PresetColour.allSkinColours);
	}
	
	public static BodyCoveringTemplate createSlime(CoveringPattern basePattern, Map<CoveringPattern, Integer> coverPatterns) {
		return new BodyCoveringTemplate("a layer of",
				false,
				"slime",
				"slime",
				List.of(CoveringModifier.GOOEY),
				null,
				Map.of(basePattern,1),
				coverPatterns,
				PresetColour.naturalSlimeColours,
				PresetColour.dyeSlimeColours,
				PresetColour.naturalSlimeColours,
				PresetColour.dyeSlimeColours);
	}
	
	public static BodyCoveringTemplate createFurSkin(List<CoveringModifier> modifiers, Map<CoveringPattern, Integer> patterns) {
		return createFur("a layer of", "fur", modifiers, patterns);
	}
	
	private static BodyCoveringTemplate createFur(String determiner, String name, List<CoveringModifier> modifiers, Map<CoveringPattern, Integer> patterns) {
		return new BodyCoveringTemplate(determiner,
				false,
				name,
				name,
				modifiers,
				null,
				patterns,
				CoveringPattern.allStandardCoveringPatterns,
				PresetColour.naturalFurColours,
				PresetColour.allCoveringColours,
				PresetColour.naturalFurColours,
				PresetColour.allCoveringColours);
	}
	
	private static BodyCoveringTemplate createHair(String determiner, String name, List<CoveringModifier> modifiers, Map<CoveringPattern, Integer> patterns) {
		return new BodyCoveringTemplate(determiner,
				false,
				name,
				name,
				modifiers,
				null,
				patterns,
				CoveringPattern.allHairCoveringPatterns,
				PresetColour.naturalHairColours,
				PresetColour.allCoveringColours,
				PresetColour.naturalHairColours,
				PresetColour.allCoveringColours);
	}
	
	private static BodyCoveringTemplate createHairWithoutPatterns(String determiner, String name, CoveringModifier modifier) {
		return createHair(determiner, name, List.of(modifier), Map.of(CoveringPattern.NONE,1));
	}
	
	public static BodyCoveringTemplate createHeadHair(CoveringModifier modifier) {
		return createHairWithoutPatterns("a head of", "hair", modifier);
	}
	
	public static BodyCoveringTemplate createFurHeadHair(CoveringModifier modifier) {
		return createHairWithoutPatterns("a layer of", "hair", modifier);
	}
	
	public static BodyCoveringTemplate createBodyHair(CoveringModifier modifier) {
		return createHairWithoutPatterns("a layer of", "hair", modifier);
	}
	
	public static BodyCoveringTemplate createElemental(String name, CoveringModifier modifier, CoveringPattern pattern, List<Colour> naturalColours) {
		return new BodyCoveringTemplate("",
				false,
				name,
				name,
				List.of(modifier),
				null,
				Map.of(pattern,1),
				null,
				naturalColours,
				PresetColour.allCoveringColours,
				naturalColours,
				PresetColour.allCoveringColours);
	}
	
	public static BodyCoveringTemplate createOrificeSkin(CoveringPattern pattern) {
		return new BodyCoveringTemplate("a layer of",
				false,
				"skin",
				"skin",
				List.of(CoveringModifier.SMOOTH),
				null,
				pattern==null
					?null
					:Map.of(pattern,1),
				null,
				PresetColour.allSkinColours,
				null,
				List.of(PresetColour.ORIFICE_INTERIOR),
				PresetColour.allSkinColours);
	}
	
	public static BodyCoveringTemplate createPenisSkin() {
		return new BodyCoveringTemplate("a layer of",
				false,
				"skin",
				"skin",
				List.of(CoveringModifier.SMOOTH),
				null,
				Map.of(CoveringPattern.NONE,10),
				Map.of(CoveringPattern.MARKED,1,CoveringPattern.MOTTLED,1,CoveringPattern.SPOTTED,1,CoveringPattern.STRIPED,1),
				PresetColour.allSkinColours,
				null,
				List.of(PresetColour.ORIFICE_INTERIOR),
				PresetColour.allSkinColours);
	}
	
	public static BodyCoveringTemplate createEyeIrisesWithCustomColors(List<Colour> naturalIrisColors, List<Colour> dyeIrisColours, boolean heteroIsExtra) {
		Map<CoveringPattern, Integer> natural = Map.of(CoveringPattern.EYE_IRISES,5,CoveringPattern.EYE_IRISES_HETEROCHROMATIC,1);
		
		Map<CoveringPattern, Integer> extra = null;
		
		if(heteroIsExtra) {
			natural = Map.of(CoveringPattern.EYE_IRISES,1);
			extra = Map.of(CoveringPattern.EYE_IRISES_HETEROCHROMATIC,1);
		}
		return new BodyCoveringTemplate("a pair of",
				true,
				"eyes",
				"eye",
				List.of(CoveringModifier.EYE),
				null,
				natural,
				extra,
				naturalIrisColors,
				dyeIrisColours,
				naturalIrisColors,
				dyeIrisColours);
	}
	
	public static BodyCoveringTemplate createEyeIrisesWithCustomColors(List<Colour> naturalIrisColours, List<Colour> dyeIrisColours) {
		return createEyeIrisesWithCustomColors(naturalIrisColours, dyeIrisColours, true);
	}
	
	public static BodyCoveringTemplate createEyeIrises() {
		return createEyeIrisesWithCustomColors(PresetColour.naturalIrisColours, PresetColour.dyeIrisColours, true);
	}
	
	public static BodyCoveringTemplate createEyeIrisesHeterochromiaNaturallyOccurring() {
		return createEyeIrisesWithCustomColors(PresetColour.naturalIrisColours, PresetColour.dyeIrisColours, false);
	}
}
