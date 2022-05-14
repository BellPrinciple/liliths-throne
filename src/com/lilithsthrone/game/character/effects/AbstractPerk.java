package com.lilithsthrone.game.character.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.AbstractAttribute;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.combat.spells.SpellSchool;
import com.lilithsthrone.game.combat.spells.SpellUpgrade;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;

/**
 * @since 0.1.0
 * @version 0.4
 * @author Innoxia
 */
public abstract class AbstractPerk implements Perk {

	String id;
	private int renderingPriority;
	protected String name;
	private List<Colour> colours;
	private boolean equippableTrait;
	
	private Spell spell;
	private SpellUpgrade spellUpgrade;
	private SpellSchool school;

	// Attributes modified by this Virtue:
	private HashMap<AbstractAttribute, Integer> attributeModifiers;

	private PerkCategory perkCategory;

	protected String pathName;
	private String SVGString;

	private List<String> extraEffects;
	
	public AbstractPerk(int renderingPriority,
			boolean major,
			String name,
			PerkCategory perkCategory,
			String pathName,
			Colour colour,
			HashMap<AbstractAttribute, Integer> attributeModifiers,
			List<String> extraEffects) {
		this(renderingPriority,
				major,
				name,
				perkCategory,
				pathName,
				colour,
				attributeModifiers,
				extraEffects,
				null,
				null,
				null);
	}
	

	public AbstractPerk(int renderingPriority,
			boolean major,
			String name,
			PerkCategory perkCategory,
			String pathName,
			Colour colour,
			HashMap<AbstractAttribute, Integer> attributeModifiers,
			List<String> extraEffects,
			Spell spell,
			SpellUpgrade spellUpgrade,
			SpellSchool school) {
		this(renderingPriority,
				major,
				name,
				perkCategory,
				pathName,
				Util.newArrayListOfValues(colour),
				attributeModifiers,
				extraEffects,
				spell,
				spellUpgrade,
				school);
	}
	
	public AbstractPerk(int renderingPriority,
			boolean major,
			String name,
			PerkCategory perkCategory,
			String pathName,
			List<Colour> colours,
			HashMap<AbstractAttribute, Integer> attributeModifiers,
			List<String> extraEffects,
			Spell spell,
			SpellUpgrade spellUpgrade,
			SpellSchool school) {

		this.renderingPriority = renderingPriority;
		this.name = name;
		this.colours = colours;
		
		this.equippableTrait = major;
		
		this.perkCategory = perkCategory;

		this.attributeModifiers = attributeModifiers;

		if(extraEffects!=null) {
			this.extraEffects = extraEffects;
		} else {
			this.extraEffects = new ArrayList<>();
		}
		
		this.pathName = pathName;
		if(pathName!=null) {
			generateSVGImage(pathName, colours);
		}

		this.spell = spell;
		this.spellUpgrade = spellUpgrade;
		this.school = school;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof AbstractPerk) {
			if(((AbstractPerk)o).getName(null).equals(this.getName(null))
					&& ((AbstractPerk)o).getAttributeModifiers(null) == this.getAttributeModifiers(null)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.getName(null).hashCode();
		if(this.getAttributeModifiers(null)!=null) {
			result = 31 * result + this.getAttributeModifiers(null).hashCode();
		}
		return result;
	}
	
	protected void generateSVGImage(String pathName, List<Colour> colours) {
		SVGString = SvgUtil.colourReplacement(
				name.replaceAll(" ", ""),
				colours.get(0),
				colours.size() >= 2 ? colours.get(1) : null,
				colours.size() >= 3 ? colours.get(2) : null,
				SvgUtil.loadFromResource("/com/lilithsthrone/res/"+pathName+".svg"));
	}

	@Override
	public String getName(GameCharacter owner) {
		return name;
	}

	@Override
	public Colour getColour() {
		return colours.get(0);
	}

	@Override
	public boolean isEquippableTrait() {
		return equippableTrait;
	}

	@Override
	public HashMap<AbstractAttribute, Integer> getAttributeModifiers(GameCharacter character) {
		return attributeModifiers;
	}

	@Override
	public int getRenderingPriority() {
		return renderingPriority;
	}

	@Override
	public List<String> getExtraEffects() {
		return extraEffects;
	}

	@Override
	public String getSVGString(GameCharacter owner) {
		return SVGString;
	}

	@Override
	public PerkCategory getPerkCategory() {
		return perkCategory;
	}

	@Override
	public Spell getSpell() {
		return spell;
	}

	@Override
	public SpellUpgrade getSpellUpgrade() {
		return spellUpgrade;
	}

	@Override
	public SpellSchool getSchool() {
		return school;
	}
}
