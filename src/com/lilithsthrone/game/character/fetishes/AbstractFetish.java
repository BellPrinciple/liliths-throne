package com.lilithsthrone.game.character.fetishes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.4.4.2
 * @version 0.4.4.2
 * @author Innoxia
 */
public abstract class AbstractFetish implements Fetish {

	String id;
	private int renderingPriority;
	protected String name;
	protected String shortDescriptor;
	private int experienceGainFromSexAction;
	private HashMap<Attribute, Integer> attributeModifiers;

	private String pathName;
	private String SVGString;
	private List<Colour> colourShades;

	private List<String> extraEffects;

	private List<String> modifiersList;
	
	private List<Fetish> fetishesForAutomaticUnlock;

	protected static final String bimboString = SvgUtil.colourReplacement(
			"FETISH_BIMBO",
			PresetColour.BASE_PINK,
			SvgUtil.loadFromResource("/com/lilithsthrone/res/fetishes/fetish_bimbo.svg"));
	protected static final String broString = SvgUtil.colourReplacement(
			"FETISH_BRO",
			PresetColour.BASE_BLUE,
			SvgUtil.loadFromResource("/com/lilithsthrone/res/fetishes/fetish_bro.svg"));

	public AbstractFetish(
			int renderingPriority,
			String name,
			String shortDescriptor,
			String pathName,
			FetishExperience experienceGainFromSexAction,
			Colour colourShades,
			HashMap<Attribute, Integer> attributeModifiers,
			List<String> extraEffects,
			List<Fetish> fetishesForAutomaticUnlock) {
		this(renderingPriority,
				name,
				shortDescriptor,
				pathName,
				experienceGainFromSexAction,
				Util.newArrayListOfValues(colourShades),
				attributeModifiers,
				extraEffects,
				fetishesForAutomaticUnlock);
	}
	
	public AbstractFetish(
			int renderingPriority,
			String name,
			String shortDescriptor,
			String pathName,
			FetishExperience experienceGainFromSexAction,
			List<Colour> colourShades,
			HashMap<Attribute, Integer> attributeModifiers,
			List<String> extraEffects,
			List<Fetish> fetishesForAutomaticUnlock) {

		this.renderingPriority = renderingPriority;
		this.name = name;
		this.shortDescriptor = shortDescriptor;
		this.experienceGainFromSexAction = experienceGainFromSexAction.getExperience();
		
		this.attributeModifiers = attributeModifiers;

		this.extraEffects = extraEffects;
		
		if(fetishesForAutomaticUnlock==null) {
			this.fetishesForAutomaticUnlock = new ArrayList<>();
		} else {
			this.fetishesForAutomaticUnlock = fetishesForAutomaticUnlock;
		}
		
		this.colourShades = colourShades;
		this.pathName = pathName;
		
		modifiersList = new ArrayList<>();

		if(attributeModifiers != null) {
			for (Entry<Attribute, Integer> e : attributeModifiers.entrySet()) {
				modifiersList.add("<b>"+(e.getValue() > 0 ? "+" : "") + e.getValue() + "</b> <b style='color: "+ e.getKey().getColour().toWebHexString()+ ";'>"+ Util.capitaliseSentence(e.getKey().getAbbreviatedName())+ "</b>");
			}
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<Fetish> getFetishesForAutomaticUnlock() {
		return fetishesForAutomaticUnlock;
	}

	@Override
	public String getName(GameCharacter owner) {
		return name;
	}

	@Override
	public String getShortDescriptor(GameCharacter target) {
		return shortDescriptor;
	}

	protected static String getGenericFetishDesireDescription(GameCharacter target, FetishDesire desire, String descriptor) {
		switch(desire) {
			case ZERO_HATE:
				return UtilText.parse(target, "You absolutely hate "+descriptor+".");
			case ONE_DISLIKE:
				return UtilText.parse(target, "You don't like "+descriptor+".");
			case TWO_NEUTRAL:
				return UtilText.parse(target, "You are indifferent to "+descriptor+".");
			case THREE_LIKE:
				return UtilText.parse(target, "You like "+descriptor+".");
			case FOUR_LOVE:
				return UtilText.parse(target, "You love "+descriptor+".");
		}
		return "";
	}

	@Override
	public int getExperienceGainFromSexAction() {
		return experienceGainFromSexAction;
	}

	@Override
	public List<String> getModifiersAsStringList(GameCharacter owner) {
		List<String> modList = new ArrayList<>(modifiersList);
		modList.addAll(getExtraEffects(owner));
		return modList;
	}

	@Override
	public HashMap<Attribute, Integer> getAttributeModifiers() {
		return attributeModifiers;
	}

	@Override
	public int getRenderingPriority() {
		return renderingPriority;
	}

	@Override
	public List<String> getExtraEffects(GameCharacter owner) {
		return extraEffects;
	}

	@Override
	public List<Colour> getColourShades() {
		return colourShades;
	}

	@Override
	public String getSVGString(GameCharacter owner) {
		if(SVGString==null) {
			if(pathName!=null && !pathName.isEmpty()) {
				SVGString = SvgUtil.colourReplacement(
						getId(),
						colourShades,
						null,
						SvgUtil.loadFromResource("fetishes/"+pathName));
//				SVGString = SvgUtil.colourReplacement(this.getId(), colourShades.get(0), colourShades.size()>=2?colourShades.get(1):null, colourShades.size()>=3?colourShades.get(2):null, SVGString);
			} else {
				SVGString = "";
			}
		}
		return SVGString;
	}
	
	public static int getExperienceGainFromTakingVaginalVirginity(GameCharacter owner) {
		return owner.getLevel()*2;
	}
	
	public static int getExperienceGainFromTakingOtherVirginity(GameCharacter owner) {
		return owner.getLevel();
	}
}
