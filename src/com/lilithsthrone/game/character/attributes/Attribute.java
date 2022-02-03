package com.lilithsthrone.game.character.attributes;

import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Table;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * NOTE: Racial attributes are added at the bottom of the static block in Race.java!
 * 
 * @since 0.1.0
 * @version 0.4
 * @author Innoxia
 */
public interface Attribute {

	String getId();

	default boolean hasStatusEffect() {
		return false;
	}

	boolean isPercentage();

	int getBaseValue();

	int getLowerLimit();

	int getUpperLimit();

	/**
	 * @return true if this Attribute should be treates as being 'infinite' when the upperLimit is reached. (Only used for shielding.)
	 */
	default boolean isInfiniteAtUpperLimit() {
		return false;
	}

	default String getInfiniteDescription() {
		return "";
	}

	String getName();

	default String getColouredName(String tag) {
		return "<"+tag+" style='color:"+getColour().toWebHexString()+";'>"+getName()+"</"+tag+">";
	}

	default String getFormattedValue(float value) {
		return getFormattedValue(value, null);
	}

	default String getFormattedValue(float value, String htmlTag) {
		String valueForDisplay;
		if(((int)value)==value) {
			valueForDisplay = String.valueOf(((int)value));
		} else {
			valueForDisplay = String.valueOf(value);
		}
		String returnValue = "";
		if(this.isInfiniteAtUpperLimit() && value>=this.getUpperLimit()) {
			if(!this.getInfiniteDescription().isEmpty()) {
				returnValue = this.getInfiniteDescription();
			} else {
				returnValue = "[style.colourExcellent(Infinite)] <span style='color: "+ this.getColour().toWebHexString()+ ";'>"+ Util.capitaliseSentence(this.getAbbreviatedName())+ "</span>";
			}

		} else {
			String minorColour = "";
			if(this.isPercentage()){
				minorColour = "Minor";
				valueForDisplay = valueForDisplay+"%";
			}
			returnValue = (value>0?"[style.colour"+minorColour+"Good(+":"[style.colour"+minorColour+"Bad(")+valueForDisplay+")]"
					+ " <span style='color:"+this.getColour().toWebHexString()+";'>"+Util.capitaliseSentence(this.getAbbreviatedName())+"</span>";
		}

		if(htmlTag!=null) {
			return "<"+htmlTag+">"+returnValue+"</"+htmlTag+">";
		} else {
			return returnValue;
		}
	}

	String getAbbreviatedName();

	String getDescription(GameCharacter owner);

	Colour getColour();

	default String getEffectsAsStringList() {
		return "";
	}

	String getPositiveEnchantment();

	String getNegativeEnchantment();

	default String getSVGString() {
		return null;
	}

	public static AbstractAttribute HEALTH_MAXIMUM = new AbstractAttribute(false,
			0,
			1,
			1000,
			"health",
			"Health",
			"healthIcon",
			PresetColour.ATTRIBUTE_HEALTH,
			"health",
			"sickness") {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"The amount of stamina and determination [npc.name] [npc.has]. [npc.She] will be defeated in combat if this reaches 0.<br/>"
						+ "Extra health is added to the 'bonus' value from:<br/>"
						+"<b>"+ GameCharacter.HEALTH_CALCULATION + "</b>");
		}
	};

	public static AbstractAttribute MANA_MAXIMUM = new AbstractAttribute(false,
			0,
			1,
			1000,
			"aura",
			"Aura",
			"manaIcon",
			PresetColour.ATTRIBUTE_MANA,
			"aura-boost",
			"aura-drain") {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"A measure of the amount of arcane energy [npc.name] [npc.has] in [npc.her] aura.<br/>"
						+ "Extra aura is added to the 'bonus' value from:<br/>"
						+ "<b>" + GameCharacter.MANA_CALCULATION + "</b>");
		}
	};

	public static AbstractAttribute EXPERIENCE = new AbstractAttribute(false,
			0,
			0,
			1000000,
			"experience",
			"Experience",
			"experienceIcon",
			PresetColour.GENERIC_EXPERIENCE,
			"learning",
			"forgetfulness") {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"How much progress [npc.name] [npc.has] made to the next level.");
		}
	};

	public static AbstractAttribute ACTION_POINTS = new AbstractAttribute(false,
			0,
			0,
			10,
			"action points",
			"Action points",
			"action_points",
			PresetColour.GENERIC_ACTION_POINTS,
			"initiative",
			"lethargy") {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"How many action points [npc.nameHasFull] available to spend on moves in combat.");
		}
	};
	
	public static AbstractAttribute AROUSAL = new AbstractAttribute(false,
			0,
			0,
			100,
			"arousal",
			"Arousal",
			"arousalIcon",
			PresetColour.ATTRIBUTE_AROUSAL,
			"long-lasting",
			"prematurity") {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner.isPlayer())
				return "How aroused you currently are. You will orgasm when your arousal maxes out.";
			else
				return UtilText.parse(owner,
						"How aroused [npc.name] is. [npc.She] will orgasm when [npc.her] arousal maxes out.");
		}
	};
	
	public static AbstractAttribute LUST = new AbstractAttribute(false,
			0,
			0,
			100,
			"lust",
			"Lust",
			"arousalIcon",
			PresetColour.ATTRIBUTE_LUST,
			"passion",
			"indifference") {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner.isPlayer()) {
				return "How desperate for sexual contact you are. Your lust will move towards your resting lust value over time.<br/>"
						+ "<b>Resting Lust = " + GameCharacter.RESTING_LUST_CALCULATION + "</b>";
			} else {
				return UtilText.parse(owner,
						"How desperate for sexual contact [npc.name] is.");
			}
		}
	};
	
	public static AbstractAttribute RESTING_LUST = new AbstractAttribute(false,
			0,
			0,
			80,
			"resting lust",
			"Resting lust",
			"arousalIcon",
			PresetColour.ATTRIBUTE_LUST,
			"passion",
			"indifference") {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner, "The amount of lust which [npc.name] naturally gravitates towards over a period of time.");
		}
	};

	public static AbstractAttribute MAJOR_PHYSIQUE = new AbstractAttribute(false,
			0,
			0,
			100,
			"physique",
			"Physique",
			"strengthIcon",
			PresetColour.ATTRIBUTE_PHYSIQUE,
			"power",
			"weakness") {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getEffectsAsStringList() {
			return "<br /><b>+2</b> <b style='color: "+PresetColour.ATTRIBUTE_HEALTH.toWebHexString()+"'>Energy</b> per 1 physique";
		}
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"A measure of how physically healthy [npc.name] [npc.is], physique <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>passively increases</b> [npc.her]"
							+ " <b style='color:" + PresetColour.ATTRIBUTE_HEALTH.toWebHexString() + ";'>maximum health</b>.");
		}
	};

	public static AbstractAttribute MAJOR_ARCANE = new AbstractAttribute(false,
			0,
			0,
			100,
			"arcane",
			"Arcane",
			"intelligenceIcon",
			PresetColour.ATTRIBUTE_ARCANE,
			"arcane-boost",
			"arcane-drain") {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getEffectsAsStringList() {
			return "<br /><b>+2</b> <b style='color: "+PresetColour.ATTRIBUTE_MANA.toWebHexString()+"'>Aura</b> per 1 arcane";
		}
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
						"A measure of [npc.namePos] affinity with the arcane. This <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>passively increases</b> [npc.her]"
								+ " <b style='color:" + PresetColour.ATTRIBUTE_MANA.toWebHexString() + ";'>maximum aura</b>.");
		}
	};

	public static AbstractAttribute MAJOR_CORRUPTION = new AbstractAttribute(false,
			0,
			0,
			100,
			"corruption",
			"Corruption",
			"corruptionIcon",
			PresetColour.ATTRIBUTE_CORRUPTION,
			"corruption",
			"purity") {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getEffectsAsStringList() {
			return "<br /><b>-0.5</b> <b style='color: " + PresetColour.ATTRIBUTE_MANA.toWebHexString() + "'>arousal resistance</b> per 1 physical corruption"
			+"<br /><b>+0.5</b> <b style='color: " + PresetColour.DAMAGE_TYPE_MANA.toWebHexString() + "'>arousal damage</b> per 1 physical corruption";
		}
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner.isPlayer()) {
				return "Corruption is a measure of your perversion and depravity, and affects <b style='color:" + PresetColour.ATTRIBUTE_CORRUPTION.toWebHexString() + ";'>which sex actions you are comfortable performing</b>.";
			} else {
				return UtilText.parse(owner,
						"Corruption is a measure of [npc.namePos] perversion and depravity. It does <i>not</i> reflect how good or evil [npc.she] is.");
			}
		}
	};
	
	// Miscellaneous attributes:


	public static AbstractAttribute ENCHANTMENT_LIMIT = new AbstractAttribute(false,
			0,
			0,
			1000,
			"enchantment capacity",
			"Enchantment capacity",
			"enchantmentLimitIcon",
			PresetColour.GENERIC_ENCHANTMENT,
			"harnessing",
			"clumsiness") {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
						"The total amount of clothing and tattoo enchantments [npc.nameIsFull] able to handle without incurring massive penalties.");
		}
	};
	
	public static AbstractAttribute FERTILITY = new AbstractAttribute(true, 10, -100, 100, "fertility", "Fertility", "shieldIcon", PresetColour.GENERIC_SEX, "fertility", "infertility") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases the likelihood of becoming pregnant.";
		}
	};
	
	public static AbstractAttribute VIRILITY = new AbstractAttribute(true, 10, -100, 100, "virility", "Virility", "shieldIcon", PresetColour.GENERIC_SEX, "virility", "sterility") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases the likelihood of impregnating someone.";
		}
	};
	
	public static AbstractAttribute SPELL_COST_MODIFIER = new AbstractAttribute(true, 0, 0, 80, "spell efficiency", "Spell efficiency", "shieldIcon", PresetColour.ATTRIBUTE_MANA, "proficiency", "incompetence") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces the cost of casting spells.";
		}
	};

	// Combat attributes:

	public static AbstractAttribute CRITICAL_DAMAGE = new AbstractAttribute(true, 150, 100, 500, "critical power", "Critical power", "shieldIcon", PresetColour.ATTRIBUTE_HEALTH, "impact", "failure") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Each point gives 1% extra critical power.";
		}
	};
	
	public static AbstractAttribute ENERGY_SHIELDING = new AbstractAttribute(false, 0, -100, 500, "health shielding", "Health shielding", "shieldIcon", PresetColour.ATTRIBUTE_HEALTH, "endurance", "vulnerability") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "This value is applied to health shielding at the start of each combat turn.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourHealth(all damage)]";
		}
	};

	
	// Resistances:

	public static AbstractAttribute RESISTANCE_PHYSICAL = new AbstractAttribute(false, 0, -100, 500, "physical shielding", "Physical shielding", "shieldIcon", PresetColour.DAMAGE_TYPE_PHYSICAL, "toughness", "softness") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces physical damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourPhysical(physical damage)]";
		}
	};
	
	public static AbstractAttribute RESISTANCE_LUST = new AbstractAttribute(false, 0, -100, 500, "lust shielding", "Lust shielding", "shieldIcon", PresetColour.GENERIC_SEX, "chastity", "temptation") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces lust damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourLust(lust damage)]";
		}
	};
	
	public static AbstractAttribute RESISTANCE_FIRE = new AbstractAttribute(false, 0, -100, 500, "fire shielding", "Fire shielding", "shieldIcon", PresetColour.DAMAGE_TYPE_FIRE, "extinguishing", "flammability") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces fire damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourFire(fire damage)]";
		}
	};
	
	public static AbstractAttribute RESISTANCE_ICE = new AbstractAttribute(false, 0, -100, 500, "cold shielding", "Cold shielding", "shieldIcon", PresetColour.DAMAGE_TYPE_COLD, "warmth", "frostbite") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces cold damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourIce(ice damage)]";
		}
	};
	
	public static AbstractAttribute RESISTANCE_POISON = new AbstractAttribute(false, 0, -100, 500, "poison shielding", "Poison shielding", "shieldIcon", PresetColour.DAMAGE_TYPE_POISON, "anti-venom", "sickness") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces poison damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourPoison(poison damage)]";
		}
	};

	
	// Damages:

	public static AbstractAttribute DAMAGE_UNARMED = new AbstractAttribute(true, 0, -80, 100, "unarmed damage", "Unarmed damage", "swordIcon", PresetColour.DAMAGE_TYPE_UNARMED, "martial arts", "martial incompetence") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage dealt from unarmed attacks, including special attacks obtained from non-human bodyparts.";
		}
	};
	
	public static AbstractAttribute DAMAGE_MELEE_WEAPON = new AbstractAttribute(true, 0, -80, 100, "melee weapon damage", "Melee Weapon damage", "swordIcon", PresetColour.DAMAGE_TYPE_MELEE, "melee mastery", "melee incompetence") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage dealt from attacks by melee weapons.";
		}
	};
	
	public static AbstractAttribute DAMAGE_RANGED_WEAPON = new AbstractAttribute(true, 0, -80, 100, "ranged weapon damage", "Ranged weapon damage", "swordIcon", PresetColour.DAMAGE_TYPE_RANGED, "ranged mastery", "ranged incompetence") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage dealt from attacks by ranged weapons.";
		}
	};
	
	public static AbstractAttribute DAMAGE_SPELLS = new AbstractAttribute(true, 0, -80, 100, "spell damage", "Spell damage", "swordIcon", PresetColour.ATTRIBUTE_MANA, "arcane power", "arcane dulling") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases spell damage.";
		}
	};

	public static AbstractAttribute DAMAGE_PHYSICAL = new AbstractAttribute(true, 0, -80, 100, "physical damage", "Physical damage", "swordIcon", PresetColour.DAMAGE_TYPE_PHYSICAL, "force", "softness") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases physical damage.";
		}
	};
	
	public static AbstractAttribute DAMAGE_LUST = new AbstractAttribute(true, 0, -80, 100, "lust damage", "Lust damage", "swordIcon", PresetColour.GENERIC_SEX, "seduction", "repulsion") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases lust damage.";
		}
	};
	
	public static AbstractAttribute DAMAGE_FIRE = new AbstractAttribute(true, 0, -80, 100, "fire damage", "Fire damage", "swordIcon", PresetColour.DAMAGE_TYPE_FIRE, "inferno", "dying embers") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases fire damage.";
		}
	};
	
	public static AbstractAttribute DAMAGE_ICE = new AbstractAttribute(true, 0, -80, 100, "cold damage", "Cold damage", "swordIcon", PresetColour.DAMAGE_TYPE_COLD, "blizzard", "slush") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases cold damage.";
		}
	};
	
	public static AbstractAttribute DAMAGE_POISON = new AbstractAttribute(true, 0, -80, 100, "poison damage", "Poison damage", "swordIcon", PresetColour.DAMAGE_TYPE_POISON, "venom", "dilution") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases poison damage.";
		}
	};
	
	// From v0.4, these are automatically generated in the static block at the end of the Race.java class!
//	// Racial:
//	
//	public static AbstractAttribute DAMAGE_ANGEL = new AbstractAttribute(true, 0, -100, 100, "angelic damage", "Angelic damage", "swordIcon", PresetColour.RACE_ANGEL, "angelic-obliteration", "angelic-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs angels.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_CAT_MORPH = new AbstractAttribute(true, 0, -100, 100, "cat-morph damage", "Cat-morph damage", "swordIcon", PresetColour.RACE_CAT_MORPH, "cat-morph-obliteration", "cat-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs cat-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_COW_MORPH = new AbstractAttribute(true, 0, -100, 100, "cow-morph damage", "Cow-morph damage", "swordIcon", PresetColour.RACE_COW_MORPH, "cow-morph-obliteration", "cow-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs cow-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_DEMON = new AbstractAttribute(true, 0, -100, 100, "demonic damage", "Demonic damage", "swordIcon", PresetColour.RACE_DEMON, "demonic-obliteration", "demonic-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs demons.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_DOG_MORPH = new AbstractAttribute(true, 0, -100, 100, "dog-morph damage", "Dog-morph damage", "swordIcon", PresetColour.RACE_DOG_MORPH, "dog-morph-obliteration", "dog-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs dog-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_HARPY = new AbstractAttribute(true, 0, -100, 100, "harpy damage", "Harpy damage", "swordIcon", PresetColour.RACE_HARPY, "harpy-obliteration", "harpy-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs harpies.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_HORSE_MORPH = new AbstractAttribute(true, 0, -100, 100, "horse-morph damage", "Horse-morph damage", "swordIcon", PresetColour.RACE_HORSE_MORPH, "horse-morph-obliteration", "horse-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs horse-morphs.";
//		}
//	};
	public static AbstractAttribute DAMAGE_IMP = new AbstractAttribute(true, 0, -100, 100, "imp damage", "Imp damage", "swordIcon", PresetColour.RACE_IMP, "impish-obliteration", "impish-mercy") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage vs imps.";
		}
	};

	public static AbstractAttribute DAMAGE_LILIN = new AbstractAttribute(true, 0, -100, 100, "lilin damage", "Lilin damage", "swordIcon", PresetColour.RACE_LILIN, "lilin-obliteration", "lilin-mercy") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage vs lilin.";
		}
	};

	public static AbstractAttribute DAMAGE_ELDER_LILIN = new AbstractAttribute(true, 0, -100, 100, "elder lilin damage", "Elder lilin damage", "swordIcon", PresetColour.RACE_LILIN, "elder-lilin-obliteration", "elder-lilin-mercy") {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage vs elder lilin.";
		}
	};
//	public static AbstractAttribute DAMAGE_REINDEER_MORPH = new AbstractAttribute(true, 0, -100, 100, "reindeer-morph damage", "Reindeer-morph damage", "swordIcon", PresetColour.RACE_REINDEER_MORPH, "reindeer-morph-obliteration", "reindeer-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs reindeer-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_HUMAN = new AbstractAttribute(true, 0, -100, 100, "human damage", "Human damage", "swordIcon", PresetColour.RACE_HUMAN, "human-obliteration", "human-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs humans.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_SQUIRREL_MORPH = new AbstractAttribute(true, 0, -100, 100, "squirrel-morph damage", "Squirrel-morph damage", "swordIcon", PresetColour.RACE_SQUIRREL_MORPH, "squirrel-morph-obliteration", "squirrel-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs squirrel-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_RAT_MORPH = new AbstractAttribute(true, 0, -100, 100, "rat-morph damage", "Rat-morph damage", "swordIcon", PresetColour.RACE_RAT_MORPH, "rat-morph-obliteration", "rat-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs rat-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_RABBIT_MORPH = new AbstractAttribute(true, 0, -100, 100, "rabbit-morph damage", "Rabbit-morph damage", "swordIcon", PresetColour.RACE_RABBIT_MORPH, "rabbit-morph-obliteration", "rabbit-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs rabbit-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_BAT_MORPH = new AbstractAttribute(true, 0, -100, 100, "bat-morph damage", "Bat-morph damage", "swordIcon", PresetColour.RACE_BAT_MORPH, "bat-morph-obliteration", "bat-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs bat-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_ALLIGATOR_MORPH = new AbstractAttribute(true, 0, -100, 100, "alligator-morph damage", "Alligator-morph damage", "swordIcon", PresetColour.RACE_ALLIGATOR_MORPH, "alligator-morph-obliteration", "alligator-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs alligator-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_WOLF_MORPH = new AbstractAttribute(true, 0, -100, 100, "wolf-morph damage", "Wolf-morph damage", "swordIcon", PresetColour.RACE_WOLF_MORPH, "wolf-morph-obliteration", "wolf-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs wolf-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_FOX_MORPH = new AbstractAttribute(true, 0, -100, 100, "fox-morph damage", "Fox-morph damage", "swordIcon", PresetColour.RACE_FOX_MORPH, "fox-morph-obliteration", "fox-morph-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs fox-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_SLIME = new AbstractAttribute(true, 0, -100, 100, "slime damage", "Slime damage", "swordIcon", PresetColour.RACE_SLIME, "slime-obliteration", "slime-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs slimes.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_ELEMENTAL = new AbstractAttribute(true, 0, -100, 100, "elemental damage", "Elemental damage", "swordIcon", PresetColour.SPELL_SCHOOL_ARCANE, "elemental-obliteration", "elemental-mercy") {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs elementals.";
//		}
//	};

	/**
	 * @return The Attribute that has an id closest to the supplied attributeId.
	 *  <b>Will return null</b> if the matching distance is greater than 3 (which typically will be more than enough to catch spelling errors, indicating that the flag has been removed).
	 */
	public static AbstractAttribute getAttributeFromId(String attributeId) {
		return table.of(attributeId);
	}

	static String sanitize(String attributeId) {
		if(attributeId.startsWith("RESISTANCE_ELEMENTAL")) {
			attributeId = "RESISTANCE_ELEMENTAL";
		} else if(attributeId.startsWith("DAMAGE_ELEMENTAL")) {
			attributeId = "DAMAGE_ELEMENTAL";
		} else if(attributeId.startsWith("CRITICAL_CHANCE")) { // Critical chance was removed, so return damage instead as a replacement for old saves
			attributeId = "CRITICAL_DAMAGE";
		}

		//old conversions
		switch(attributeId) {
		case "CORRUPTION":
			return "MAJOR_CORRUPTION";
		case "STRENGTH":
		case "MAJOR_STRENGTH":
			return "MAJOR_PHYSIQUE";
		case "INTELLIGENCE":
			return "MAJOR_ARCANE";
		case "RESISTANCE_ATTACK":
			return "RESISTANCE_PHYSICAL";
		case "RESISTANCE_MANA":
			return "RESISTANCE_LUST";
		case "RESISTANCE_PURE":
			return "ENERGY_SHIELDING";
		}
		return attributeId;
	}

	@Deprecated
	static String getIdFromAttribute(AbstractAttribute attribute) {
		return attribute.id;
	}

	@Deprecated
	static List<AbstractAttribute> getAllAttributes() {
		return table.list();
	}

	@Deprecated
	public static AbstractAttribute getRacialDamageAttribute(Race race) {
		return Race.DamageAttribute.of(race);
	}

	Table<AbstractAttribute> table = new Table<>(Attribute::sanitize) {{

		// Hard-coded attributes (all those up above):
		addFields(Attribute.class,AbstractAttribute.class,(k,v)->v.id=k);

		// NOTE: Racial attributes are added at the bottom of the static block in Race.java!
	}};

	@Deprecated
	static void register(String k, AbstractAttribute v) {
		v.id = k;
		table.add(k,v);
	}
}
