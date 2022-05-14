package com.lilithsthrone.game.character.attributes;

import java.util.List;

import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;

/**
 * @since 0.4
 * @version 0.4
 * @author Innoxia
 */
public abstract class AbstractAttribute implements Attribute {

	String id;
	private boolean percentage;
	private int baseValue;
	private	int lowerLimit;
	private	int upperLimit;
	private String name;
	private String nameAbbreviation;
	private String positiveEnchantment;
	private String negativeEnchantment;
	private Colour colour;
	private List<String> extraEffects;
	private String SVGString;
	
	public AbstractAttribute(boolean percentage,
			int baseValue,
			int lowerLimit,
			int upperLimit,
			String name,
			String nameAbbreviation,
			String pathName,
			Colour colour,
			String positiveEnchantment,
			String negativeEnchantment,
			List<String> extraEffects) {
		this.percentage = percentage;
		this.baseValue = baseValue;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.name = name;
		this.nameAbbreviation = nameAbbreviation;
		this.colour = colour;
		this.positiveEnchantment = positiveEnchantment;
		this.negativeEnchantment = negativeEnchantment;
		this.extraEffects = extraEffects;
		SVGString = SvgUtil.colourReplacement(
				"att_"+name.replaceAll("\\s",""),
				colour,
				SvgUtil.loadFromResource("/com/lilithsthrone/res/UIElements/"+pathName+".svg"));
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		System.err.println("Warning: AbstractAttribute's toString() method is being called!");
//		throw new IllegalAccessError();
		return Attribute.getIdFromAttribute(this);
	}

	public boolean isPercentage() {
		return percentage;
	}
	
	public int getBaseValue() {
		return baseValue;
	}

	public int getLowerLimit() {
		return lowerLimit;
	}
	
	public int getUpperLimit() {
		return upperLimit;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getAbbreviatedName() {
		return nameAbbreviation;
	}

	@Override
	public Colour getColour() {
		return colour;
	}

	@Override
	public String getEffectsAsStringList() {
		StringBuilder descriptionSB = new StringBuilder();

		if (extraEffects != null)
			for (String s : extraEffects)
				descriptionSB.append("<br/>" + s);

		return descriptionSB.toString();
	}

	@Override
	public String getPositiveEnchantment() {
		return positiveEnchantment;
	}

	@Override
	public String getNegativeEnchantment() {
		return negativeEnchantment;
	}

	@Override
	public String getSVGString() {
		return SVGString;
	}
}
