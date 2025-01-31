package com.lilithsthrone.game.inventory.weapon;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.lilithsthrone.main.Main;
import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.controller.xmlParsing.XMLMissingTagException;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.combat.DamageVariance;
import com.lilithsthrone.game.combat.moves.CombatMove;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.ColourReplacement;
import com.lilithsthrone.game.inventory.ItemTag;
import com.lilithsthrone.game.inventory.Rarity;
import com.lilithsthrone.game.inventory.SetBonus;
import com.lilithsthrone.game.inventory.enchanting.ItemEffect;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.ColourListPresets;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.1.84
 * @version 0.4.2.1
 * @author Innoxia
 */
public abstract class AbstractWeaponType implements WeaponType {

	String id;
	private int baseValue;
	private boolean mod;
	
	private boolean melee;
	private boolean twoHanded;
	
	private boolean oneShot;
	private float oneShotChanceToRecoverAfterTurn;
	private float oneShotChanceToRecoverAfterCombat;
	
	private boolean appendDamageName;
	private String determiner;
	boolean plural;
	private String name;
	private String namePlural;
	private String attackDescriptor;
	private String attackDescriptionPrefix;
	private String attackTooltipDescription;
	private String description;

	private SetBonus clothingSet;
	private Rarity rarity;
	private float physicalResistance;
	
	private String equipText;
	private String unequipText;
	private List<String> hitDescriptions;
	private List<String> hitCriticalDescriptions;
	private List<String> missDescriptions;
	private List<String> oneShotEndTurnRecoveryDescriptions;
	protected String hitEffect;
	protected String criticalHitEffect;

	private String pathNamePrefix;
	private String pathName;
	private String pathNameEquipped;
	
	private String authorDescription;
	
	protected int damage;
	/** Values corresponding to chance to trigger (from 0->100) and damage. */
	protected List<Value<Integer, Integer>> aoeDamage;
	protected int arcaneCost;
	protected DamageVariance damageVariance;
	private List<DamageType> availableDamageTypes;
	
	private boolean spellRegenOnDamageTypeChange;
	private Map<DamageType, List<Spell>> spells;

	private boolean combatMovesRegenOnDamageTypeChange;
	private Map<DamageType,List<CombatMove>> combatMoves;

	protected List<ItemEffect> effects;
	protected List<String> extraEffects;

	private Map<String, String> SVGStringMap;
	private Map<String, String> SVGStringEquippedMap;
	
	private String SVGStringDesaturated;
	private String SVGStringEquippedDesaturated;

	private List<ColourReplacement> colourReplacements;
	/** Key is the colour index which should copy another colour upon weapon generation. Value is the colour index which should be copied. */
	public Map<Integer, Integer> copyGenerationColours;

	private List<ItemTag> itemTags;

	@SuppressWarnings("deprecation")
	public AbstractWeaponType(File weaponXMLFile, String author, boolean mod) {
		this.itemTags = new ArrayList<>();

		if (weaponXMLFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(weaponXMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element weaponElement = Element.getDocumentRootElement(weaponXMLFile); // Loads the document and returns the root element - in clothing mods it's <clothing>
				Element coreAttributes = null;
				try {
					coreAttributes = weaponElement.getMandatoryFirstOf("coreAtributes");
				} catch (XMLMissingTagException ex) {
					coreAttributes = weaponElement.getMandatoryFirstOf("coreAttributes");
				}
				
				this.itemTags = Util.toEnumList(coreAttributes.getMandatoryFirstOf("itemTags").getAllOf("tag"), ItemTag.class);
				
				this.mod = mod;
				
				this.baseValue = Integer.valueOf(coreAttributes.getMandatoryFirstOf("value").getTextContent());
				this.melee = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("melee").getTextContent());
				this.twoHanded = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("twoHanded").getTextContent());
				
				if(coreAttributes.getOptionalFirstOf("oneShotWeapon").isPresent()) {
					this.oneShot = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("oneShotWeapon").getTextContent());
				} else {
					this.oneShot = false;
				}
				if(coreAttributes.getOptionalFirstOf("oneShotWeaponChanceToRecoverAfterTurn").isPresent()) {
					this.oneShotChanceToRecoverAfterTurn = Float.valueOf(coreAttributes.getMandatoryFirstOf("oneShotWeaponChanceToRecoverAfterTurn").getTextContent());
				} else {
					this.oneShotChanceToRecoverAfterTurn = 0;
				}
				if(coreAttributes.getOptionalFirstOf("oneShotWeaponChanceToRecoverAfterCombat").isPresent()) {
					this.oneShotChanceToRecoverAfterCombat = Float.valueOf(coreAttributes.getMandatoryFirstOf("oneShotWeaponChanceToRecoverAfterCombat").getTextContent());
				} else {
					this.oneShotChanceToRecoverAfterCombat = 0;
				}
				
				this.determiner = coreAttributes.getMandatoryFirstOf("determiner").getTextContent();
				this.plural = Boolean.valueOf(((Element)coreAttributes.getMandatoryFirstOf("namePlural")).getAttribute("pluralByDefault"));
				this.name = coreAttributes.getMandatoryFirstOf("name").getTextContent();
				this.namePlural = coreAttributes.getMandatoryFirstOf("namePlural").getTextContent();
				this.description = coreAttributes.getMandatoryFirstOf("description").getTextContent();
				this.attackDescriptor = coreAttributes.getMandatoryFirstOf("attackDescriptor").getTextContent();
				if(coreAttributes.getOptionalFirstOf("attackDescriptionPrefix").isPresent()) {
					this.attackDescriptionPrefix = coreAttributes.getMandatoryFirstOf("attackDescriptionPrefix").getTextContent();
				} else {
					this.attackDescriptionPrefix = this.attackDescriptor;
				}
				this.attackTooltipDescription = coreAttributes.getMandatoryFirstOf("attackTooltipDescription").getTextContent();
				
				if(!coreAttributes.getMandatoryFirstOf("name").getAttribute("appendDamageName").isEmpty()) {
					this.appendDamageName  =  Boolean.valueOf(coreAttributes.getMandatoryFirstOf("name").getAttribute("appendDamageName"));
				} else {
					this.appendDamageName = true;
				}
				
				if(coreAttributes.getOptionalFirstOf("weaponAuthorTag").isPresent()) {
					this.authorDescription = coreAttributes.getMandatoryFirstOf("weaponAuthorTag").getTextContent();
				} else if(coreAttributes.getOptionalFirstOf("authorTag").isPresent()) {
					this.authorDescription = coreAttributes.getMandatoryFirstOf("authorTag").getTextContent();
				} else if(!author.equalsIgnoreCase("innoxia")){
					this.authorDescription = "A discreet inscription on the surface of the "+(plural?namePlural:name)+" informs you that "+(plural?"they were":"it was")+" made by a certain '"+Util.capitaliseSentence(author)+"'.";
				} else {
					this.authorDescription = "";
				}

				this.equipText = coreAttributes.getMandatoryFirstOf("equipText").getTextContent();
				this.unequipText = coreAttributes.getMandatoryFirstOf("unequipText").getTextContent();
				
				this.pathNamePrefix = weaponXMLFile.getParentFile().getAbsolutePath() + "/";

				this.pathName = pathNamePrefix + coreAttributes.getMandatoryFirstOf("imageName").getTextContent();

				Predicate<Element> filterEmptyElements = element -> !element.getTextContent().isEmpty(); //helper function to filter out empty elements.
				
				this.pathNameEquipped = coreAttributes.getOptionalFirstOf("imageEquippedName")
					.filter(filterEmptyElements)
					.map(o -> o.getTextContent()) // weaponXMLFile.getParentFile().getAbsolutePath() + "/" +
					.orElse(pathName);

				this.damage = Integer.valueOf(coreAttributes.getMandatoryFirstOf("damage").getTextContent());
				this.arcaneCost = Integer.valueOf(coreAttributes.getMandatoryFirstOf("arcaneCost").getTextContent());
				this.damageVariance = DamageVariance.valueOf(coreAttributes.getMandatoryFirstOf("damageVariance").getTextContent());
				
				this.aoeDamage = new ArrayList<>();
				if(coreAttributes.getOptionalFirstOf("aoe").isPresent()) {
					for(Element e : coreAttributes.getAllOf("aoe")) {
						aoeDamage.add(new Value<>(Integer.valueOf(e.getAttribute("chance")), Integer.valueOf(e.getTextContent())));
					}
				}
				
				if(coreAttributes.getOptionalFirstOf("availableDamageTypes").isPresent()) {
					this.availableDamageTypes = coreAttributes
							.getMandatoryFirstOf("availableDamageTypes")
							.getAllOf("damageType").stream()
							.map(Element::getTextContent).map(DamageType::valueOf)
							.collect(Collectors.toList());
				} else {
					this.availableDamageTypes = new ArrayList<>();
				}

				this.spells = new HashMap<>();
				if(coreAttributes.getOptionalFirstOf("spells").isPresent()) {
					this.spellRegenOnDamageTypeChange = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("spells").getAttribute("changeOnReforge"));
					
					for(Element e : coreAttributes.getMandatoryFirstOf("spells").getAllOf("spell")) {
						String spellId = e.getTextContent();
						spellId = spellId.replaceAll("DARK_SIREN_BANEFUL_FISSURE", "DARK_SIREN_SIRENS_CALL");
						Spell s = Spell.valueOf(spellId);
						
						DamageType dt = null;
						if(!e.getAttribute("damageType").isEmpty()) {
							dt = DamageType.valueOf(e.getAttribute("damageType"));
						}
						
						this.spells.putIfAbsent(dt, new ArrayList<>());
						this.spells.get(dt).add(s);
					}
				}

				this.combatMoves = new HashMap<>();
				if(coreAttributes.getOptionalFirstOf("combatMoves").isPresent()) {
					this.combatMovesRegenOnDamageTypeChange = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("combatMoves").getAttribute("changeOnReforge"));
					
					for(Element e : coreAttributes.getMandatoryFirstOf("combatMoves").getAllOf("move")) {
						var cm = CombatMove.getCombatMoveFromId(e.getTextContent());
						
						DamageType dt = null;
						if(!e.getAttribute("damageType").isEmpty()) {
							dt = DamageType.valueOf(e.getAttribute("damageType"));
						}
						
						this.combatMoves.putIfAbsent(dt, new ArrayList<>());
						this.combatMoves.get(dt).add(cm);
					}
				}
				
				this.clothingSet = coreAttributes.getOptionalFirstOf("weaponSet")
					.filter(filterEmptyElements)
					.map(Element::getTextContent).map(SetBonus::getSetBonusFromId)
					.orElse(null);

				this.effects = coreAttributes
					.getMandatoryFirstOf("effects")
					.getAllOf("effect") // Get all child elements with this tag (checking only contents of parent element) and return them as List<Element>
					.stream() // Convert this list to Stream<Element>, which lets us do some nifty operations on every element at once
					.map( e -> ItemEffect.loadFromXML(e.getInnerElement(), e.getDocument())) // Take every element and do something with them, return a Stream of results after this action. Here we load item effects and get Stream<ItemEffect>
					.filter(Objects::nonNull) // Ensure that we only add non-null effects
					.collect(Collectors.toList()); // Collect stream back into a list, but this time we get List<ItemEffect> we need! 

				this.extraEffects = new ArrayList<>();
				if(coreAttributes.getOptionalFirstOf("extraEffects").isPresent()) {
					for(Element e : coreAttributes.getMandatoryFirstOf("extraEffects").getAllOf("effect")) {
						extraEffects.add(e.getTextContent());
					}
				}
				
				this.hitEffect = "";
				if(coreAttributes.getOptionalFirstOf("onHitEffect").isPresent()) {
					hitEffect = coreAttributes.getMandatoryFirstOf("onHitEffect").getTextContent();
				}

				this.criticalHitEffect = "";
				if(coreAttributes.getOptionalFirstOf("onCriticalHitEffect").isPresent()) {
					criticalHitEffect = coreAttributes.getMandatoryFirstOf("onCriticalHitEffect").getTextContent();
				}
				
				this.rarity = Rarity.valueOf(coreAttributes.getMandatoryFirstOf("rarity").getTextContent());
				
				if(coreAttributes.getOptionalFirstOf("physicalResistance").isPresent()) {
					this.physicalResistance = Float.valueOf(coreAttributes.getMandatoryFirstOf("physicalResistance").getTextContent());
				}
				
				// Hit/miss descriptions:
				
				if(weaponElement.getOptionalFirstOf("hitDescriptions").isPresent()) {
					this.hitDescriptions = weaponElement
							.getMandatoryFirstOf("hitDescriptions")
							.getAllOf("hitText").stream()
							.map(o -> o.getTextContent())
							.collect(Collectors.toList());
					this.hitCriticalDescriptions = weaponElement
							.getMandatoryFirstOf("hitDescriptions")
							.getAllOf("criticalHitText").stream()
							.map(o -> o.getTextContent())
							.collect(Collectors.toList());
					
				} else {
					this.hitDescriptions = new ArrayList<>();
					this.hitCriticalDescriptions = new ArrayList<>();
				}
				
				if(weaponElement.getOptionalFirstOf("missDescriptions").isPresent()) {
					this.missDescriptions = weaponElement
							.getMandatoryFirstOf("missDescriptions")
							.getAllOf("missText").stream()
							.map(o -> o.getTextContent())
							.collect(Collectors.toList());
				} else {
					this.missDescriptions = Util.newArrayListOfValues("[npc.Name] [npc.verb(recover)] [npc.her] "+this.name+"!");
				}
				
				if(weaponElement.getOptionalFirstOf("oneShotEndTurnRecoveryDescriptions").isPresent()) {
					this.oneShotEndTurnRecoveryDescriptions = weaponElement
							.getMandatoryFirstOf("oneShotEndTurnRecoveryDescriptions")
							.getAllOf("recoveryText").stream()
							.map(o -> o.getTextContent())
							.collect(Collectors.toList());
				} else {
					this.oneShotEndTurnRecoveryDescriptions = new ArrayList<>();
				}
				

				Function< Element, List<Colour> > getColoursFromElement = (colorsElement) -> { //Helper function to get the colors depending on if it's a specified group or a list of individual colors
					String values = colorsElement.getAttribute("values");
					try {
						if(values.isEmpty()) {
							return colorsElement.getAllOf("colour").stream()
									.map(Element::getTextContent).map(PresetColour::getColourFromId)
									.collect(Collectors.toList());
						} else {
							return ColourListPresets.getColourListFromId(values);
						}
					} catch (Exception e) {
						printHelpfulErrorForEnumValueMismatches(e);
						throw new IllegalStateException("Colour tag reading failure: "+colorsElement.getTagName()+" " + e.getMessage(), e);
					}
				};
				
				
				copyGenerationColours = new HashMap<>();
				
				boolean primaryRecolourAllowed = true;
				if(coreAttributes.getOptionalFirstOf("primaryColours").isPresent() && !coreAttributes.getMandatoryFirstOf("primaryColours").getAttribute("recolouringAllowed").isEmpty()) {
					primaryRecolourAllowed = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("primaryColours").getAttribute("recolouringAllowed"));
				}
				if(coreAttributes.getOptionalFirstOf("primaryColours").isPresent() && !coreAttributes.getMandatoryFirstOf("primaryColours").getAttribute("copyColourIndex").isEmpty()) {
					copyGenerationColours.put(0, Integer.valueOf(coreAttributes.getMandatoryFirstOf("primaryColours").getAttribute("copyColourIndex")));
				}
				List<Colour> importedPrimaryColours = coreAttributes.getOptionalFirstOf("primaryColours")
					.map(getColoursFromElement::apply)
					.orElseGet(ArrayList::new);
				List<Colour> importedPrimaryColoursDye = coreAttributes.getOptionalFirstOf("primaryColoursDye")
					.map(getColoursFromElement::apply)
					.orElseGet(ArrayList::new);

				boolean secondaryRecolourAllowed = true;
				if(coreAttributes.getOptionalFirstOf("secondaryColours").isPresent() && !coreAttributes.getMandatoryFirstOf("secondaryColours").getAttribute("recolouringAllowed").isEmpty()) {
					secondaryRecolourAllowed = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("secondaryColours").getAttribute("recolouringAllowed"));
				}
				if(coreAttributes.getOptionalFirstOf("secondaryColours").isPresent() && !coreAttributes.getMandatoryFirstOf("secondaryColours").getAttribute("copyColourIndex").isEmpty()) {
					copyGenerationColours.put(1, Integer.valueOf(coreAttributes.getMandatoryFirstOf("secondaryColours").getAttribute("copyColourIndex")));
				}
				List<Colour> importedSecondaryColours = coreAttributes.getOptionalFirstOf("secondaryColours")
					.map(getColoursFromElement::apply)
					.orElseGet(ArrayList::new);
				List<Colour> importedSecondaryColoursDye = coreAttributes.getOptionalFirstOf("secondaryColoursDye")
					.map(getColoursFromElement::apply)
					.orElseGet(ArrayList::new);

				boolean tertiaryRecolourAllowed = true;
				if(coreAttributes.getOptionalFirstOf("tertiaryColours").isPresent() && !coreAttributes.getMandatoryFirstOf("tertiaryColours").getAttribute("recolouringAllowed").isEmpty()) {
					tertiaryRecolourAllowed = Boolean.valueOf(coreAttributes.getMandatoryFirstOf("tertiaryColours").getAttribute("recolouringAllowed"));
				}
				if(coreAttributes.getOptionalFirstOf("tertiaryColours").isPresent() && !coreAttributes.getMandatoryFirstOf("tertiaryColours").getAttribute("copyColourIndex").isEmpty()) {
					copyGenerationColours.put(2, Integer.valueOf(coreAttributes.getMandatoryFirstOf("tertiaryColours").getAttribute("copyColourIndex")));
				}
				List<Colour> importedTertiaryColours = coreAttributes.getOptionalFirstOf("tertiaryColours")
					.map(getColoursFromElement::apply)
					.orElseGet(ArrayList::new);
				List<Colour> importedTertiaryColoursDye = coreAttributes.getOptionalFirstOf("tertiaryColoursDye")
					.map(getColoursFromElement::apply)
					.orElseGet(ArrayList::new);

				setUpColours(primaryRecolourAllowed,
					importedPrimaryColours,
					importedPrimaryColoursDye,
					secondaryRecolourAllowed,
					importedSecondaryColours,
					importedSecondaryColoursDye,
					tertiaryRecolourAllowed,
					importedTertiaryColours,
					importedTertiaryColoursDye);

				if(weaponElement.getOptionalFirstOf("customColours").isPresent()) {
					Element customColoursElement = weaponElement.getMandatoryFirstOf("customColours");
					for(Element e : customColoursElement.getAllOf("customColour")) {
						try {
							List<String> replacementStrings = new ArrayList<>();
							for(int i=0;;i++) {
								if(e.getAttribute("c"+i).isEmpty()) {
									break;
								} else {
									replacementStrings.add(e.getAttribute("c"+i));
								}
							}

							boolean recolourAllowed = true;
							if(!e.getAttribute("recolouringAllowed").isEmpty()) {
								recolourAllowed = Boolean.valueOf(e.getAttribute("recolouringAllowed"));
							}
							if(!e.getAttribute("copyColourIndex").isEmpty()) {
								copyGenerationColours.put(this.colourReplacements.size()-1, Integer.valueOf(e.getAttribute("copyColourIndex"))); // -1 from size, as damage type replacement is in index 0
							}
							
							List<Colour> defaultColours = getColoursFromElement
								.apply(e.getMandatoryFirstOf("defaultColours"));
							List<Colour> extraColours = getColoursFromElement
								.apply(e.getMandatoryFirstOf("extraColours"));	
							
							this.colourReplacements.add(new ColourReplacement(recolourAllowed, replacementStrings, defaultColours, extraColours));
							
						} catch(Exception ex) {
							System.err.println("Error in loading customColours from weapon: "+this.getName());
						}
					}
				}
				
				if(this.colourReplacements.isEmpty()) {
					throw new Exception("AbstractWeaponType (" + weaponXMLFile.getName() + "): colourReplacements is empty!");
				}
				
				this.SVGStringMap = new HashMap<>();
				this.SVGStringEquippedMap = new HashMap<>();
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("WeaponType was unable to be loaded from file! (" + weaponXMLFile.getName() + ")\n" + ex);
			}
		}
	}
	
	@Override
	public boolean equals(Object o) { // I know it doesn't include everything, but this should be enough to check for equality.
		if(super.equals(o)){
			if(o instanceof AbstractWeaponType){
				if(((AbstractWeaponType)o).getName().equals(getName())
						&& ((AbstractWeaponType)o).isMelee() == isMelee()
						&& ((AbstractWeaponType)o).isTwoHanded() == isTwoHanded()
						&& ((AbstractWeaponType)o).getPathName().equals(getPathName())
						&& ((AbstractWeaponType)o).getPhysicalResistance() == getPhysicalResistance()
						&& ((AbstractWeaponType)o).getDamage() == getDamage()
						&& ((AbstractWeaponType)o).getDamageVariance() == getDamageVariance()
						&& ((AbstractWeaponType)o).getRarity() == getRarity()
						&& ((AbstractWeaponType)o).getAvailableDamageTypes().equals(getAvailableDamageTypes())
						&& ((AbstractWeaponType)o).getSpells().equals(getSpells())
						&& ((AbstractWeaponType)o).getEffects().equals(getEffects())
						&& ((AbstractWeaponType)o).getClothingSet() == getClothingSet()
						){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() { // I know it doesn't include everything, but this should be enough to check for equality.
		int result = super.hashCode();
		result = 31 * result + getName().hashCode();
		result = 31 * result + getPathName().hashCode();
		result = 31 * result + Float.floatToIntBits(getPhysicalResistance());
		result = 31 * result + getDamage();
		result = 31 * result + getDamageVariance().hashCode();
		result = 31 * result + (melee ? 1 : 0);
		result = 31 * result + (twoHanded ? 1 : 0);
		result = 31 * result + (oneShot ? 1 : 0);
		result = 31 * result + getRarity().hashCode();
		result = 31 * result + getAvailableDamageTypes().hashCode();
		result = 31 * result + getSpells().hashCode();
		result = 31 * result + getEffects().hashCode();
		if(getClothingSet()!=null) {
			result = 31 * result + getClothingSet().hashCode();
		}
		return result;
	}

	private void setUpColours(
			boolean primaryRecolouringAllowed,
			List<Colour> availablePrimaryColours,
			List<Colour> availablePrimaryDyeColours,
			boolean secondaryRecolouringAllowed,
			List<Colour> availableSecondaryColours,
			List<Colour> availableSecondaryDyeColours,
			boolean tertiaryRecolouringAllowed,
			List<Colour> availableTertiaryColours,
			List<Colour> availableTertiaryDyeColours) {
		colourReplacements = new ArrayList<>();
		colourReplacements.add(new ColourReplacement(true, ColourReplacement.DEFAULT_PRIMARY_REPLACEMENTS, PresetColour.getAllPresetColours(), null)); // Damage type colour
		if((availablePrimaryColours!=null && !availablePrimaryColours.isEmpty()) || (availablePrimaryDyeColours!=null && !availablePrimaryDyeColours.isEmpty())) {
			colourReplacements.add(new ColourReplacement(primaryRecolouringAllowed, ColourReplacement.DEFAULT_SECONDARY_REPLACEMENTS, availablePrimaryColours, availablePrimaryDyeColours));
		}
		if((availableSecondaryColours!=null && !availableSecondaryColours.isEmpty()) || (availableSecondaryDyeColours!=null && !availableSecondaryDyeColours.isEmpty())) {
			colourReplacements.add(new ColourReplacement(secondaryRecolouringAllowed, ColourReplacement.DEFAULT_TERTIARY_REPLACEMENTS, availableSecondaryColours, availableSecondaryDyeColours));
		}
		if((availableTertiaryColours!=null && !availableTertiaryColours.isEmpty()) || (availableTertiaryDyeColours!=null && !availableTertiaryDyeColours.isEmpty())) {
			colourReplacements.add(new ColourReplacement(tertiaryRecolouringAllowed, ColourReplacement.DEFAULT_QUATERNARY_REPLACEMENTS, availableTertiaryColours, availableTertiaryDyeColours));
		}
	}

	@SuppressWarnings("rawtypes")
	private static void printHelpfulErrorForEnumValueMismatches(Exception ex) {
		Map<Class, Set<String>> possibleEnumValues = new HashMap<>();
		possibleEnumValues.put(ColourListPresets.class, ColourListPresets.getIdToColourListMap().keySet());
		String exMessage = ex.getMessage();
		if (exMessage.startsWith("No ColourListPreset constant")){
			for (Entry<Class, Set<String>> possibleMatch : possibleEnumValues.entrySet()) {
				if (exMessage.contains(possibleMatch.getKey().getCanonicalName())) {
					StringJoiner valueLister = new StringJoiner(",");
					Arrays.asList(possibleMatch.getValue()).forEach(enumValue -> valueLister.add(enumValue.toString()));
					System.err.println("Possible values for "+possibleMatch.getKey().getSimpleName()+" are " + valueLister.toString());
				}
			}
		}
	}

	@Override
	public boolean isMod() {
		return mod;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String equipText(GameCharacter character) {
		return UtilText.parse(character, equipText);
	}

	@Override
	public String unequipText(GameCharacter character) {
		return UtilText.parse(character, unequipText);
	}

	@Override
	public String getHitText(GameCharacter character, GameCharacter target, boolean critical) {
		if(critical && !hitCriticalDescriptions.isEmpty()) {
			return UtilText.parse(character, target, Util.randomItemFrom(hitCriticalDescriptions));
		} else {
			return UtilText.parse(character, target, Util.randomItemFrom(hitDescriptions));
		}
	}

	@Override
	public String getMissText(GameCharacter character, GameCharacter target) {
		return UtilText.parse(character, target, Util.randomItemFrom(missDescriptions));
	}

	@Override
	public String getOneShotEndTurnRecoveryDescription(GameCharacter character) {
		return UtilText.parse(character, Util.randomItemFrom(oneShotEndTurnRecoveryDescriptions));
	}
	
	protected static String getDescriptions(GameCharacter character, GameCharacter target, boolean isHit,
			String playerStrikingNPC,
			String NPCStrikingPlayer,
			String NPCStrikingNPC,
			String playerMissingNPC,
			String NPCMissingPlayer,
			String NPCMissingNPC) {
		if(isHit) {
			if(character.isPlayer()) {
				return UtilText.parse(target, playerStrikingNPC);
				
			} else {
				if(target.isPlayer()) {
					return UtilText.parse(character, NPCStrikingPlayer);
				} else {
					return UtilText.parse(character, target, NPCStrikingNPC);
				}
			}
			
		} else {
			if(character.isPlayer()) {
				return UtilText.parse(target, playerMissingNPC);
				
			} else {
				if(target.isPlayer()) {
					return UtilText.parse(character, NPCMissingPlayer);
				} else {
					return UtilText.parse(character, target, NPCMissingNPC);
				}
			}
		}
	}
	
	public static String genericMeleeAttackDescription(GameCharacter character, GameCharacter target, boolean isHit) {
		if(isHit) {
			if(character.isFeral()) {
				return UtilText.parse(character, target,
						UtilText.returnStringAtRandom(
							"Darting forwards, [npc.name] [npc.verb(rear)] up [npc.verb(deliver)] a solid kick to [npc2.namePos] torso.",
							"Striking out at [npc2.name], [npc.name] [npc.verb(manage)] to land a solid kick on [npc2.her] [npc2.leg]!",
							"[npc.Name] [npc.verb(strike)] out at [npc2.name] in unarmed combat, and [npc.verb(manage)] to land a solid kick on [npc2.her] torso."));
				
			} else {
				return UtilText.parse(character, target,
						UtilText.returnStringAtRandom(
							"Darting forwards, [npc.name] [npc.verb(deliver)] a solid punch to [npc2.namePos] [npc2.arm].",
							"Striking out at [npc2.name], [npc.name] [npc.verb(manage)] to land a solid punch on [npc2.her] [npc2.arm]!",
							"[npc.Name] [npc.verb(strike)] out at [npc2.name] in unarmed combat, and [npc.verb(manage)] to land a solid hit on [npc2.her] torso."));
			}
			
		} else {
			return UtilText.parse(character, target,
					UtilText.returnStringAtRandom(
						"Darting forwards, [npc.name] [npc.verb(try)] to deliver a punch to [npc2.namePos] [npc2.arm], but [npc2.she] [npc2.verb(manage)] to step out of the way in time.",
						"[npc.Name] [npc.verb(throw)] a punch at [npc2.name], but fails to make contact with any part of [npc2.her] body.",
						"[npc.Name] [npc.verb(strike)] out at [npc2.name] in unarmed combat, but [npc.she] [npc.verb(end)] up missing."));
		}
	}

	@Override
	public String applyExtraEffects(GameCharacter user, GameCharacter target, boolean isHit, boolean isCritical) {
		StringBuilder sb = new StringBuilder();
		
		if(this.getArcaneCost()>0) {
			user.incrementEssenceCount(-this.getArcaneCost(), false);
			sb.append(UtilText.parse(user, (this.isMelee()?"Using":"Firing")+" the "+this.getName()+" drains [style.boldBad("+Util.intToString(this.getArcaneCost())+")] [style.boldArcane(arcane essence)] from [npc.namePos] aura!"));
		}
		if(isHit) {
			String hitText = isCritical?this.criticalHitEffect:this.hitEffect;
			if(!hitText.isEmpty()) {
				if(sb.length()>0) {
					sb.append("<br/>");
				}
				sb.append(UtilText.parse(user, target, hitText));
			}
		}
		
		return sb.toString();
	}

	@Override
	public int getBaseValue() {
		return baseValue;
	}

	@Override
	public boolean isMelee() {
		return melee;
	}

	@Override
	public boolean isTwoHanded() {
		return twoHanded;
	}

	@Override
	public boolean isOneShot() {
		return oneShot;
	}

	@Override
	public float getOneShotChanceToRecoverAfterTurn() {
		return oneShotChanceToRecoverAfterTurn;
	}

	@Override
	public float getOneShotChanceToRecoverAfterCombat() {
		return oneShotChanceToRecoverAfterCombat;
	}

	@Override
	public boolean isAppendDamageName() {
		return appendDamageName;
	}

	@Override
	public String getDeterminer() {
		return determiner;
	}

	@Override
	public boolean isPlural() {
		return plural;
	}

	@Override
	public String getName() {
		if(isPlural()) {
			return namePlural;
		}
		return name;
	}

	@Override
	public String getNamePlural() {
		return namePlural;
	}

	@Override
	public String getAttackDescriptor() {
		return attackDescriptor;
	}

	@Override
	public String getAttackDescriptionPrefix(GameCharacter user, GameCharacter target) {
		return attackDescriptionPrefix;
	}

	@Override
	public String getAttackDescription(GameCharacter user, GameCharacter target) {
		return UtilText.parse(user, target, attackTooltipDescription);
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<String> getExtraEffects() {
		return extraEffects;
	}

	@Override
	public String getAuthorDescription() {
		return authorDescription;
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public float getPhysicalResistance() {
		return physicalResistance;
	}

	@Override
	public SetBonus getClothingSet() {
		return clothingSet;
	}

	@Override
	public String getPathName() {
		return pathName;
	}

	@Override
	public String getEquippedPathName(GameCharacter characterEquippedTo) {
		String parsedPath = UtilText.parse(characterEquippedTo, pathNameEquipped).trim();
		if(parsedPath.isEmpty()) {
			return null;
		}
		return pathNamePrefix + parsedPath;
	}
	
	@Override
	public boolean isEquippedSVGImageDifferent() {
		return !getPathName().equals(pathNameEquipped);
	}
	
	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public DamageVariance getDamageVariance() {
		return damageVariance;
	}

	@Override
	public int getArcaneCost() {
		return arcaneCost;
	}

	@Override
	public List<Value<Integer, Integer>> getAoeDamage() {
		return aoeDamage;
	}

	@Override
	public List<DamageType> getAvailableDamageTypes() {
		return availableDamageTypes;
	}

	@Override
	public boolean isSpellRegenOnDamageTypeChange() {
		return spellRegenOnDamageTypeChange;
	}

	@Override
	public Map<DamageType, List<Spell>> getSpells() {
		return spells;
	}

	@Override
	public List<Spell> getSpells(DamageType damageType) {
		List<Spell> damageTypeSpells = new ArrayList<>();
		if(spells.containsKey(null)) {
			damageTypeSpells.addAll(spells.get(null));
		}
		if(spells.containsKey(damageType)) {
			damageTypeSpells.addAll(spells.get(damageType));
		}
		return damageTypeSpells;
	}

	@Override
	public boolean isCombatMoveRegenOnDamageTypeChange() {
		return combatMovesRegenOnDamageTypeChange;
	}

	@Override
	public Map<DamageType, List<CombatMove>> getCombatMoves() {
		return combatMoves;
	}

	@Override
	public List<ColourReplacement> getColourReplacements(boolean includeDamageTypeReplacement) {
		if(includeDamageTypeReplacement) {
			return colourReplacements;
		}
		List<ColourReplacement> removeZeroList = new ArrayList<>(colourReplacements);
		removeZeroList.remove(0);
		return removeZeroList;
	}

	@Override
	public void applyGenerationColourReplacement(List<Colour> list) {
		for(var entry : copyGenerationColours.entrySet()) {
			Colour replacement = list.get(entry.getValue());
			list.remove((int)entry.getKey());
			list.add(entry.getKey(),replacement);
		}
	}

	private static String generateIdentifier(DamageType dt, List<Colour> colours) {
		return generateIdentifier(null, dt, colours);
	}

	private static String generateIdentifier(GameCharacter character, DamageType dt, List<Colour> colours) {
		StringBuilder sb = new StringBuilder(dt.toString());
		if(character!=null) {
			sb.append(character.getId());
		}
		for(Colour c : colours) {
			sb.append(c.getId());
		}
		return sb.toString();
	}

	private void addSVGStringMapping(DamageType dt, List<Colour> colours, String s) {
		SVGStringMap.put(generateIdentifier(dt, colours), s);
	}
	
	private String getSVGStringFromMap(DamageType dt, List<Colour> colours) {
		return SVGStringMap.get(generateIdentifier(dt, colours));
	}

	@Override
	public String getSVGImage() {
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

	@Override
	public String getSVGImage(DamageType dt, List<Colour> colours) {
		if(!getAvailableDamageTypes().contains(dt))
			return "";
		String stringFromMap = getSVGStringFromMap(dt, colours);
		if(stringFromMap!=null)
			return stringFromMap;
		String s = Util.getFileContent(pathName);
		List<Colour> coloursPlusDT = Util.newArrayListOfValues(dt.getColour());
		coloursPlusDT.addAll(colours);
		s = SvgUtil.colourReplacement(getId(), coloursPlusDT, getColourReplacements(true), s);
		addSVGStringMapping(dt, colours, s);
		return s;
	}

	@Override
	public String getSVGImageDesaturated() {
		if(SVGStringDesaturated!=null)
			return SVGStringDesaturated;
		String s = Util.getFileContent(pathName);
		s = SvgUtil.colourReplacement(this.getId()+"DS", Util.newArrayListOfValues(PresetColour.BASE_GREY, PresetColour.BASE_GREY, PresetColour.BASE_GREY), this.getColourReplacements(true), s);
		return SVGStringDesaturated = s;
	}
	
//	private void addSVGStringEquippedMapping(GameCharacter character, DamageType dt, List<Colour> colours, String s) {
//		SVGStringEquippedMap.put(generateIdentifier(character, dt, colours), s);
//	}
	
	private String getSVGStringEquippedFromMap(GameCharacter character, DamageType dt, List<Colour> colours) {
		return SVGStringEquippedMap.get(generateIdentifier(character, dt, colours));
	}
	
	@Override
	public String getSVGEquippedImage(GameCharacter characterEquipped) {
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
		
		return getSVGEquippedImage(characterEquipped, dt, colours);
	}

	@Override
	public String getSVGEquippedImage(GameCharacter characterEquipped, DamageType dt, List<Colour> colours) {
		if(!isEquippedSVGImageDifferent())
			return getSVGImage(dt, colours);
		if(!getAvailableDamageTypes().contains(dt))
			return "";
		String stringFromMap = getSVGStringEquippedFromMap(characterEquipped, dt, colours);
		if(stringFromMap!=null)
			return stringFromMap;
		String s = Util.getFileContent(getEquippedPathName(characterEquipped));
		List<Colour> coloursPlusDT = Util.newArrayListOfValues(dt.getColour());
		coloursPlusDT.addAll(colours);
		s = SvgUtil.colourReplacement(getId()+"Equipped", coloursPlusDT, getColourReplacements(true), s);
		// Don't save icon as it can vary based on character changes...
//		addSVGStringEquippedMapping(characterEquipped, dt, colours, s);
		return s;
	}

	@Override
	public String getSVGEquippedImageDesaturated(GameCharacter characterEquipped) {
		if(SVGStringEquippedDesaturated!=null)
			return SVGStringEquippedDesaturated;
		return SVGStringEquippedDesaturated = SvgUtil.colourReplacement(
				getId()+"EquippedDS",
				Util.newArrayListOfValues(PresetColour.BASE_GREY, PresetColour.BASE_GREY, PresetColour.BASE_GREY),
				getColourReplacements(true),
				Util.getFileContent(getEquippedPathName(characterEquipped)));
	}
	
	// Enchantments:

	@Override
	public List<ItemEffect> getEffects() {
		return effects;
	}

	@Override
	public List<ItemTag> getItemTags() {
		return itemTags;
	}
}
