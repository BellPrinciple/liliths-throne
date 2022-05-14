package com.lilithsthrone.game.combat.moves;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.attributes.CorruptionLevel;
import com.lilithsthrone.game.character.attributes.LustLevel;
import com.lilithsthrone.game.character.effects.AbstractStatusEffect;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.combat.Attack;
import com.lilithsthrone.game.combat.CombatBehaviour;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.combat.DamageVariance;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.weapon.AbstractWeapon;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * A class containing logic for Combat Moves.
 * 
 * @since 0.4
 * @version 0.4
 * @author Irbynx, Innoxia
 */
public abstract class AbstractCombatMove implements CombatMove {

	String id;
	private boolean mod;
	private boolean fromExternalFile;
	
	private CombatMoveCategory category;
    private CombatMoveType type;
	
    private String name;
    private String description;

    private String damageTypeString = "";
    private String baseDamageString = "";
    private String cooldownString = "";
    private String APCostString = "";
    private String blockString = "";

    private String availabilityCondition = "";
    private String availabilityDescription = "";
    
    private String criticalCondition = "";
    private String criticalDescription = "";

    private String movePredictionDescriptionWithTarget = "";
    private String movePredictionDescriptionNoTarget = "";

    private String performingText = "";
    private String performingCritDescText = "";
    private String performingCritText = "";

    private String weightingText = "";
    
    private DamageType damageType;
    private int baseDamage;
	private DamageVariance damageVariance;
    
    private int cooldown;
    private int APcost;
    private int equipWeighting;
    
    private boolean canTargetEnemies;
    private boolean canTargetAllies;
    private boolean canTargetSelf;
    
    private String SVGString;

	private Map<AbstractStatusEffect, Integer> statusEffects;
	private Map<AbstractStatusEffect, Integer> statusEffectsCritical;

    private Spell associatedSpell;
    
    public AbstractCombatMove(CombatMoveCategory category,
    		String name,
    		int cooldown,
    		int APcost,
    		CombatMoveType type,
    		DamageType damageType,
    		String pathName,
    		boolean canTargetAllies,
    		boolean canTargetEnemies,
    		boolean canTargetSelf,
    		Map<AbstractStatusEffect, Integer> statusEffects) {
    	this(category, name, cooldown, APcost, 1, type, damageType, DamageVariance.NONE, pathName, null, canTargetAllies, canTargetEnemies, canTargetSelf, statusEffects, statusEffects);
    }

	public AbstractCombatMove(CombatMoveCategory category,
    		String name,
    		int cooldown,
    		int APcost,
    		CombatMoveType type,
    		DamageType damageType,
			DamageVariance damageVariance,
    		String pathName,
    		boolean canTargetAllies,
    		boolean canTargetEnemies,
    		boolean canTargetSelf,
    		Map<AbstractStatusEffect, Integer> statusEffects) {
    	this(category, name, cooldown, APcost, 1, type, damageType, damageVariance, pathName, null, canTargetAllies, canTargetEnemies, canTargetSelf, statusEffects, statusEffects);
    }

	public AbstractCombatMove(CombatMoveCategory category,
			String name,
			int cooldown,
			int APcost,
			CombatMoveType type,
			DamageType damageType,
			String pathName,
			List<Colour> iconColours,
			boolean canTargetAllies,
			boolean canTargetEnemies,
			boolean canTargetSelf,
			Map<AbstractStatusEffect, Integer> statusEffects) {
		this(category, name, cooldown, APcost, 1, type, damageType, DamageVariance.NONE, pathName, iconColours, canTargetAllies, canTargetEnemies, canTargetSelf, statusEffects, statusEffects);
	}
    
    /**
     * Default constructor
     * @param identifier = Internal name that the game will refer to when looking up the item. Must be unique for proper searching.
     * @param name = Human friendly name of the action
     * @param cooldown = Amount of turns this action takes to become available. 0 means it's available always, 1 means it is available only once per turn.
     * @param APcost = Cost in action points to perform this action.
     * @param type = Combat action type. Used for various interactions.
     * @param statusEffects = Status effects that are applied when this move is used.
     */
    public AbstractCombatMove(CombatMoveCategory category,
    		String name,
    		int cooldown,
    		int APcost,
    		int equipWeighting,
    		CombatMoveType type,
    		DamageType damageType,
			DamageVariance damageVariance,
    		String pathName,
    		List<Colour> iconColours,
    		boolean canTargetAllies,
    		boolean canTargetEnemies,
    		boolean canTargetSelf,
			Map<AbstractStatusEffect, Integer> statusEffects,
			Map<AbstractStatusEffect, Integer> statusEffectsCritical) {
		this.fromExternalFile = false;
		this.mod = false;

		this.category = category;
		this.name = name;
		this.description = "This action does nothing.";
		this.performingText = "The move is performed.";

		this.cooldown = cooldown;
		this.APcost = APcost;
		this.equipWeighting = equipWeighting;
		this.type = type;
		this.baseDamage = 0;
		this.damageType = damageType;
		this.damageVariance = damageVariance;
		this.canTargetEnemies = canTargetEnemies;
		this.canTargetAllies = canTargetAllies;
		this.canTargetSelf = canTargetSelf;

		this.statusEffects = statusEffects;
		this.statusEffectsCritical = statusEffectsCritical;

		this.associatedSpell = null;
		String image = SvgUtil.loadFromResource("/com/lilithsthrone/res/" + pathName + ".svg");
		SVGString = iconColours != null
				? SvgUtil.colourReplacement("CM", iconColours.get(0), iconColours.size() > 1 ? iconColours.get(1) : null, iconColours.size() > 2 ? iconColours.get(2) : null, image)
				: SvgUtil.colourReplacement("CM", type.getColour(), image);
	}

	public AbstractCombatMove(File XMLFile, String author, boolean mod) {
		if (XMLFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(XMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in combatMove files it's <combatMove>
				
				this.mod = mod;
				this.fromExternalFile = true;
				
				this.category = CombatMoveCategory.SPECIAL;
				if(coreElement.getOptionalFirstOf("category").isPresent()) {
					try {
						this.category = CombatMoveCategory.valueOf(coreElement.getMandatoryFirstOf("category").getTextContent());
					} catch(Exception ex) {
						System.err.println("CombatMove loading error in '"+XMLFile.getName()+"': category not recognised! (Set to SPECIAL)");
					}
				}
				
				this.type = CombatMoveType.ATTACK;
				if(coreElement.getOptionalFirstOf("type").isPresent()) {
					try {
						this.type = CombatMoveType.valueOf(coreElement.getMandatoryFirstOf("type").getTextContent());
					} catch(Exception ex) {
						System.err.println("CombatMove loading error in '"+XMLFile.getName()+"': type not recognised! (Set to ATTACK)");
					}
				}
				
				this.damageVariance = DamageVariance.NONE;
				if(coreElement.getOptionalFirstOf("damageVariance").isPresent()) {
					try {
						String variance = coreElement.getMandatoryFirstOf("damageVariance").getTextContent();
						if (!variance.isEmpty()) {
							this.damageVariance = DamageVariance.valueOf(variance);
						}
					} catch(Exception ex) {
						System.err.println("CombatMove loading error in '"+XMLFile.getName()+"': variance not recognised! (Set to NONE)");
					}
				}
				
				this.equipWeighting = Integer.valueOf(coreElement.getMandatoryFirstOf("equipWeighting").getTextContent().trim());

				this.name = coreElement.getMandatoryFirstOf("name").getTextContent();
				this.description = coreElement.getMandatoryFirstOf("description").getTextContent();
				this.damageTypeString = coreElement.getMandatoryFirstOf("damageType").getTextContent();
				this.baseDamageString = coreElement.getMandatoryFirstOf("baseDamage").getTextContent();
				this.blockString = coreElement.getMandatoryFirstOf("blockAmount").getTextContent();
				this.cooldown = 1; // Backup in case cooldownString fails to load
				this.cooldownString = coreElement.getMandatoryFirstOf("cooldown").getTextContent();
				this.APcost = 1; // Backup in case APCostString fails to load
				this.APCostString = coreElement.getMandatoryFirstOf("APcost").getTextContent();
				
				this.canTargetAllies = Boolean.valueOf(coreElement.getMandatoryFirstOf("canTargetAllies").getTextContent().trim());
				this.canTargetEnemies = Boolean.valueOf(coreElement.getMandatoryFirstOf("canTargetEnemies").getTextContent().trim());
				this.canTargetSelf = Boolean.valueOf(coreElement.getMandatoryFirstOf("canTargetSelf").getTextContent().trim());
				
				
				String pathName = XMLFile.getParentFile().getAbsolutePath() + "/" + coreElement.getMandatoryFirstOf("imageName").getTextContent();
				SVGString = null;
				
				Colour colourShade = PresetColour.getColourFromId(coreElement.getMandatoryFirstOf("colourPrimary").getTextContent());
				Colour colourShadeSecondary = null;
				if(coreElement.getOptionalFirstOf("colourSecondary").isPresent() && !coreElement.getMandatoryFirstOf("colourSecondary").getTextContent().isEmpty()) {
					colourShadeSecondary = PresetColour.getColourFromId(coreElement.getMandatoryFirstOf("colourSecondary").getTextContent());
				}
				Colour colourShadeTertiary = null;
				if(coreElement.getOptionalFirstOf("colourTertiary").isPresent() && !coreElement.getMandatoryFirstOf("colourTertiary").getTextContent().isEmpty()) {
					colourShadeTertiary = PresetColour.getColourFromId(coreElement.getMandatoryFirstOf("colourTertiary").getTextContent());
				}
				List<Colour> colourShades = Util.newArrayListOfValues(colourShade, colourShadeSecondary, colourShadeTertiary);
				SVGString = Util.getFileContent(pathName);
				SVGString = SvgUtil.colourReplacement(getIdentifier(), colourShades, null, SVGString);
				this.statusEffects = new HashMap<>();
				this.statusEffectsCritical = new HashMap<>();
				if(coreElement.getOptionalFirstOf("statusEffects").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("statusEffects").getAllOf("effect")) {
						try {
							int length = Integer.valueOf(e.getAttribute("turnLength"));
							AbstractStatusEffect se = StatusEffect.getStatusEffectFromId(e.getTextContent());
							if(Boolean.valueOf(e.getAttribute("onCrit"))) {
								statusEffectsCritical.put(se, length);
							} else {
								statusEffects.put(se, length);
							}
						} catch (Exception ex) {
				            ex.printStackTrace();
				        }
					}
				}
				
				this.availabilityCondition = coreElement.getMandatoryFirstOf("availabilityCondition").getTextContent();
				this.availabilityDescription = coreElement.getMandatoryFirstOf("availabilityDescription").getTextContent();
				
				if(coreElement.getOptionalFirstOf("weighting").isPresent()) {
					this.weightingText = coreElement.getMandatoryFirstOf("weighting").getTextContent();
				} else {
					this.weightingText = "";
				}
				this.criticalCondition = coreElement.getMandatoryFirstOf("criticalCondition").getTextContent();
				this.criticalDescription = coreElement.getMandatoryFirstOf("criticalDescription").getTextContent();
				this.movePredictionDescriptionWithTarget = coreElement.getMandatoryFirstOf("movePredictionDescriptionWithTarget").getTextContent();
				this.movePredictionDescriptionNoTarget = coreElement.getMandatoryFirstOf("movePredictionDescriptionNoTarget").getTextContent();
				
				this.performingText = coreElement.getMandatoryFirstOf("performMove").getMandatoryFirstOf("execute").getTextContent();
				this.performingCritDescText = coreElement.getMandatoryFirstOf("performMove").getMandatoryFirstOf("critDescription").getTextContent();
				this.performingCritText = coreElement.getMandatoryFirstOf("performMove").getMandatoryFirstOf("critEffectDescription").getTextContent();
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("SetBonus was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
			}
		}
	}

    protected boolean isTargetAtMaximumLust(GameCharacter target) {
    	return target!=null && target.hasStatusEffect(StatusEffect.DESPERATE_FOR_SEX);
    }
    
    /**
     * @return A standard formatting of the damage.
     */
	public static String getFormattedDamage(DamageType damageType, int damage, GameCharacter target, boolean damageHasBeenApplied, boolean targetWasAtMaximumLust) {
		if(target!=null && damageType==DamageType.LUST && targetWasAtMaximumLust) {
			damageType = DamageType.HEALTH;
			if(damageHasBeenApplied) {
				damage /= 2;
			}
			return "<span style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>" + String.valueOf(damage*2) + " " + damageType.getName() + "</span>"
					+ " and <span style='color:" + PresetColour.DAMAGE_TYPE_MANA.toWebHexString() + ";'>" + String.valueOf(damage) + " " + Attribute.MANA_MAXIMUM.getName() + "</span>";
		}
		return "<span style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>" + String.valueOf(damage) + " " + damageType.getName() + "</span>";
	}

	protected static String getFormattedDamageRange(GameCharacter attacker, GameCharacter defender, DamageType damageType, Attack attackType, AbstractWeapon weapon, boolean isCritical) {
		return getFormattedDamageRange(attacker, defender, damageType, attackType, weapon, isCritical, 1);
	}
	
    /**
     * @return A standard formatting of the damage range.
     */
	protected static String getFormattedDamageRange(GameCharacter attacker, GameCharacter defender, DamageType damageType, Attack attackType, AbstractWeapon weapon, boolean isCritical, float multiplier) {
		return "<span style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>"
					+ String.valueOf(Attack.applyFinalDamageModifiers(attacker, defender, Attack.getMinimumDamage(attacker, defender, attackType, weapon)*multiplier, isCritical))
					+"-"
					+ String.valueOf(Attack.applyFinalDamageModifiers(attacker, defender, Attack.getMaximumDamage(attacker, defender, attackType, weapon)*multiplier, isCritical))
					+ " "
					+ damageType.getName()
				+ "</span>";
	}
	
    /**
     * @return A standard formatting of an attack's outcome.
     */
	protected static String formatAttackOutcome(GameCharacter source, GameCharacter target, String attackText, String attackEffectText, String criticalText, String criticalEffectText) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<p><i>"+attackText+"</i></p>");
		sb.append(attackEffectText);
		
		if(criticalText!=null) {
			sb.append("<p>"
							+ "<span style='color:"+PresetColour.CRIT.toWebHexString()+";'>Critical</span>: <i>"+criticalEffectText+"</i>"
						+ "</p>");
		}
		
		return UtilText.parse(source, target, sb.toString());
	}
    
	@Override
    public float getWeight(GameCharacter source, List<GameCharacter> enemies, List<GameCharacter> allies) {
        if(isCanTargetAllies() && !isCanTargetSelf() && !isCanTargetEnemies() && allies.isEmpty()) { //TODO test?
            return 0.0f;
        }
        if(shouldBlunder()) {
            return (float)(Math.random()) - 0.2f * source.getSelectedMovesByType(type);
        }
        if(weightingText!=null && !weightingText.isEmpty()) {
        	float maxWeight = 0.0f;
        	for(GameCharacter character : Main.combat.getAllCombatants(true)) {
        		if((isCanTargetSelf() && character.equals(source))
        				|| (isCanTargetAllies() && character.isCombatAlly(source))
        				|| (isCanTargetEnemies() && character.isCombatEnemy(source))) {
        			float weight = Float.valueOf(UtilText.parse(source, character, weightingText).trim());
        			if(weight>maxWeight) {
        				maxWeight = weight;
        			}
        		}
        	}
        	return maxWeight;
        }
        int behaviourMultiplier = 1;
        // Trying to figure out best use cases
        switch(type) {
            case ATTACK:
            	if(source.getCombatBehaviour()==CombatBehaviour.ATTACK) {
            		behaviourMultiplier=10;
            	}
                for(GameCharacter character : enemies) {
                    if(character.getHealthPercentage() < 0.2) {
                        return (1.1f*behaviourMultiplier) + 0.2f*(float)(Math.random()) - 0.2f * source.getSelectedMovesByType(type); // If the enemy is low on health, chances to attack increase
                    }
                }
                return (0.8f*behaviourMultiplier) + 0.2f*(float)(Math.random()) - 0.2f * source.getSelectedMovesByType(type); // Attacks aren't sophisticated
                
            case DEFEND:
            	if(source.getCombatBehaviour()==CombatBehaviour.DEFEND) {
            		behaviourMultiplier=10;
            	}
                if(source.getHealthPercentage() < 0.2) {
                    return (1.0f*behaviourMultiplier) + 0.5f*(float)(Math.random()) - 0.2f * source.getSelectedMovesByType(type);
                }
                return (0.25f*behaviourMultiplier) + 0.75f*(float)(Math.random()) - 0.2f * source.getSelectedMovesByType(type);
                
            case TEASE:
            	if(source.getCombatBehaviour()==CombatBehaviour.SEDUCE) {
            		behaviourMultiplier=10;
            	}
                float weight = (0.8f*behaviourMultiplier) + 0.2f*(float)(Math.random()) - 0.2f * source.getSelectedMovesByType(type);
                for(GameCharacter character : enemies) {
                    if(character.getLustLevel() == LustLevel.FOUR_IMPASSIONED || character.getLustLevel() == LustLevel.FIVE_BURNING) {
                        weight += 0.2f;
                        break;
                    }
                }
                if(source.getCorruptionLevel() == CorruptionLevel.FOUR_LUSTFUL || source.getCorruptionLevel() == CorruptionLevel.FIVE_CORRUPT) {
                    weight += 0.4f;
                }
            	if(!source.isAttractedTo(this.getPreferredTarget(source, enemies, allies)) && source.getCombatBehaviour()!=CombatBehaviour.SEDUCE) {
            		weight*=0.5f; // 50% lower chance to use tease attacks if not attracted to the enemy.
            	}
                return weight; // Attacks aren't sophisticated

            case SPELL:
            	if(source.getCombatBehaviour()==CombatBehaviour.SPELLS) {
            		behaviourMultiplier=10;
            	}
                return behaviourMultiplier;
                
            default:
                return (float)(Math.random()) - 0.2f * source.getSelectedMovesByType(type); // Other types are too nuanced in themselves to have a broad weight generation apply to them
        }
    }

	@Override
    public GameCharacter getPreferredTarget(GameCharacter source, List<GameCharacter> enemies, List<GameCharacter> allies) {
		if(Main.game.isInCombat()) {
	    	GameCharacter preferredTarget = Main.combat.getPreferredTarget(source);
	    	if(preferredTarget!=null && !Main.combat.isCombatantDefeated(preferredTarget)) {
	    		return preferredTarget;
	    	}
		}

        if(weightingText!=null && !weightingText.isEmpty()) {
        	float maxWeight = 0.0f;
        	GameCharacter target = null;
        	for(GameCharacter character : Main.combat.getAllCombatants(true)) {
        		if((isCanTargetSelf() && character.equals(source))
        				|| (isCanTargetAllies() && character.isCombatAlly(source))
        				|| (isCanTargetEnemies() && character.isCombatEnemy(source))) {
        			float weight = Float.valueOf(UtilText.parse(source, character, weightingText).trim()) * (Main.combat.isCombatantDefeated(character)?0:1);
        			if(weight>maxWeight) {
        				target = character;
        				maxWeight = weight;
        			}
        		}
        	}
        	if(target!=null) {
        		return target;
        	}
        }
		//TODO copy into CombatMove.super.getPreferredTarget(source,enemies,allies);
        if(isCanTargetEnemies()) {
            if(shouldBlunder() && enemies.stream().anyMatch(enemy->!Main.combat.isCombatantDefeated(enemy))) {
            	List<GameCharacter> nonDefeatedEnemies = new ArrayList<>(enemies);
            	nonDefeatedEnemies.removeIf(enemy->Main.combat.isCombatantDefeated(enemy));
                return nonDefeatedEnemies.get(Util.random.nextInt(nonDefeatedEnemies.size()));
            } else {
                float lowestHP = -1;
                GameCharacter potentialCharacter = null;
                for(GameCharacter character : enemies) {
                    if(lowestHP == -1 || character.getHealth() < lowestHP) {
                        potentialCharacter = character;
                        lowestHP = character.getHealth();
                    }
                }
                return potentialCharacter;
            }
        }
        if(isCanTargetAllies() && !allies.isEmpty()) {
            if(shouldBlunder() && allies.stream().anyMatch(ally->!Main.combat.isCombatantDefeated(ally))) {
            	List<GameCharacter> nonDefeatedAllies = new ArrayList<>(allies);
            	nonDefeatedAllies.removeIf(ally->Main.combat.isCombatantDefeated(ally));
                return nonDefeatedAllies.get(Util.random.nextInt(nonDefeatedAllies.size()));
            }
            else {
                float lowestHP = -1;
                GameCharacter potentialCharacter = null;
                for(GameCharacter character : allies) {
                    if(lowestHP == -1 || character.getHealth() < lowestHP)
                    {
                        potentialCharacter = character;
                        lowestHP = character.getHealth();
                    }
                }
                return potentialCharacter;
            }
        }
        return source;
    }


	@Override
    public String getPrediction(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
    	String parseText;
    	if(target==null) {
            parseText = movePredictionDescriptionNoTarget;
    	} else {
    		parseText = movePredictionDescriptionWithTarget;
    	}
    	
    	if(fromExternalFile) {
	    	boolean crit = canCrit(turnIndex, source, target, enemies, allies);
	    	parseText = parseText.replaceAll("isCritical", String.valueOf(crit));
	    	parseText = parseText.replaceAll("damageInflicted", String.valueOf(getDamage(turnIndex, source, target, crit)));
//	    	parseText = parseText.replaceAll("damageInflictedNoCrit", String.valueOf(getDamage(source, target, false)));
	    	parseText = parseText.replaceAll("formattedDamageInflicted", getFormattedDamage(getDamageType(turnIndex, source), getDamage(turnIndex, source, target, crit), target, false, isTargetAtMaximumLust(target)));
//	    	parseText = parseText.replaceAll("formattedDamageInflictedNoCrit", getFormattedDamage(damageType, getDamage(source, target, false), target, false, isTargetAtMaximumLust(target)));
    	}
    	
        return UtilText.parse(source, target, parseText);
    }

	@Override
    public String perform(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
    	String parseText = performingText;
    	
    	if(fromExternalFile) {
	    	boolean crit = canCrit(turnIndex, source, target, enemies, allies);
	    	parseText = parseText.replaceAll("isCritical", String.valueOf(crit));
	    	
            DamageType damageType = getDamageType(turnIndex, source);
            boolean maxLust = isTargetAtMaximumLust(target);
            Value<String, Integer> damageValue = damageType.damageTarget(source, target, getDamage(turnIndex, source, target, crit));
	    	parseText = parseText.replaceAll("damageInflicted", String.valueOf(damageValue.getValue()));
	    	parseText = parseText.replaceAll("formattedDamageInflicted", getFormattedDamage(damageType, damageValue.getValue(), target, true, maxLust));
	    	parseText = parseText.replaceAll("formattedHealthDamage", damageValue.getKey());
	    	
            return formatAttackOutcome(source, target,
            		parseText,
            		damageValue.getValue()>0
            			?"[npc2.Name] took " + getFormattedDamage(damageType, damageValue.getValue(), target, true, maxLust) + " damage!"
            			:"",
            		crit
	            		?performingCritDescText
	            		:null,
            		performingCritText);
    	}
    	
        return UtilText.parse(source, target, parseText);
    }

	@Override
    public Value<Boolean, String> isAvailableFromSpecialCase(GameCharacter source) {
    	if(fromExternalFile) {
    		boolean condition = Boolean.valueOf(UtilText.parse(source, availabilityCondition).trim());
    		return new Value<>(condition, availabilityDescription);
    	}
        return null;
    }


    /**
     * Returns true based on user settings on how often should the AI make "mistakes" and select actions irrationally.
     * @return
     */
    public static boolean shouldBlunder() {
        return Math.random() <= Main.getProperties().AIblunderRate;
    }

	@Override
    public CombatMoveCategory getCategory() {
		return category;
	}

	@Override
	public String getIdentifier() {
        return id;
    }

	@Override
	public int getCooldown(GameCharacter source) {
		int derivedCooldown = cooldown;
		
    	if(fromExternalFile) {
    		try{
    			derivedCooldown = Integer.valueOf(UtilText.parse(source, cooldownString).trim());
    		} catch(Exception ex) {
				System.err.println("CombatMove loading error: cooldownString parsing not recognised! (Left as 'cooldown')");
    		}
    	}
    	
    	if(!source.getEquippedMoves().contains(this)) {
    		return derivedCooldown+1;
    	}
        return derivedCooldown;
    }

	@Override
    public int getBlock(GameCharacter source, boolean isCrit) {
		int block = 0;
    	if(fromExternalFile) {
    		try{
    			String parseText = blockString;
    	    	parseText = parseText.replaceAll("isCritical", String.valueOf(isCrit));
    			block = Integer.valueOf(UtilText.parse(source, parseText).trim());
    		} catch(Exception ex) {
				System.err.println("CombatMove loading error: blockString parsing not recognised! (Set to 0)");
    		}
    	}
		return block;
    }
    
	@Override
    public CombatMoveType getType() {
        return type;
    }

	@Override
    public DamageType getDamageType(int turnIndex, GameCharacter source) {
    	if(fromExternalFile) {
    		DamageType dt = DamageType.PHYSICAL;
    		try{
    			dt = DamageType.valueOf(UtilText.parse(source, damageTypeString).trim());
    		} catch(Exception ex) {
				System.err.println("CombatMove loading error: DamageType parsing not recognised! (Set to PHYSICAL)");
    		}
    		if(dt==DamageType.UNARMED) {
    			return DamageType.UNARMED.getParentDamageType(source, null);
    		}
    		return dt;
    	}
		return damageType;
	}

    protected int getBaseDamage(GameCharacter source) {
    	if(fromExternalFile) {
    		float damage = 1;
    		try{
    			String s = UtilText.parse(source, baseDamageString).trim();
//    			System.out.println(s);
    			damage = Float.valueOf(s);
    		} catch(Exception ex) {
				System.err.println("CombatMove loading error: baseDamage parsing not recognised! (Set to 1)");
    		}
    		return (int) damage;
    	}
        return baseDamage;
    }
    
    protected int getDamage(int turnIndex, GameCharacter source, GameCharacter target, boolean isCrit) {
    	if(getBaseDamage(source)==0) {
    		return 0;
    	}
        DamageType damageType = getDamageType(turnIndex, source);
        return (int) Attack.calculateSpecialAttackDamage(source, target, getType(), damageType, getBaseDamage(source), getDamageVariance(), isCrit);
    }

	@Override
	public DamageVariance getDamageVariance() {
		return damageVariance;
	}

	@Override
	public Spell getAssociatedSpell() {
        return associatedSpell;
    }

    public void setAssociatedSpell(Spell associatedSpell) {
        this.associatedSpell = associatedSpell;
    }

	@Override
    public boolean isCanTargetEnemies() {
        return canTargetEnemies;
    }

	@Override
    public boolean isCanTargetAllies() {
        return canTargetAllies;
    }

	@Override
    public boolean isCanTargetSelf() {
        return canTargetSelf;
    }
    
	@Override
    public int getAPcost(GameCharacter source) {
		int derivedAPCost = APcost;
		
    	if(fromExternalFile) {
    		try{
    			derivedAPCost = Integer.valueOf(UtilText.parse(source, APCostString).trim());
    		} catch(Exception ex) {
				System.err.println("CombatMove loading error: APCostString parsing not recognised! (Left as 'APcost')");
    		}
    	}
    	
        return derivedAPCost + (!source.getEquippedMoves().contains(this)?1:0);
    }

	@Override
    public String getName(int turnIndex, GameCharacter source) {
        return UtilText.parse(source, name);
    }

	@Override
    public String getDescription(int turnIndex, GameCharacter source) {
    	String parseText = description;
    	
    	parseText = parseText.replaceAll("damageInflicted", String.valueOf(getDamage(turnIndex, source, null, false)));
    	parseText = parseText.replaceAll("formattedDamageInflicted", getFormattedDamage(getDamageType(turnIndex, source), getBaseDamage(source), null, false, false));
    	
        return UtilText.parse(source, parseText);
    }

	@Override
    public String getSVGString() {
        return SVGString;
    }
    
	@Override
    public Map<AbstractStatusEffect, Integer> getStatusEffects(GameCharacter caster, GameCharacter target, boolean isCritical) {
    	if(isCritical) {
    		return statusEffectsCritical;
    	}
		return statusEffects;
	}

	@Override
	public List<String> getCritRequirements(GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
    	if(fromExternalFile) {
        	return Util.newArrayListOfValues(criticalDescription);
    	}
		//TODO copy this into CombatMove.super.getCritRequirements(source, target, enemies, allies);
        return Util.newArrayListOfValues("It's the third time being used this turn.");
    }
    
	@Override
    public boolean canCrit(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
    	if(fromExternalFile) {
        	String parseText = criticalCondition;
        	
        	parseText = parseText.replaceAll("turnIndex", String.valueOf(turnIndex));
        	parseText = parseText.replaceAll("damageInflicted", String.valueOf(getDamage(turnIndex, source, target, false)));
        	parseText = parseText.replaceAll("damageType", "DAMAGE_TYPE_"+this.getDamageType(turnIndex, source));
        	
            return Boolean.valueOf(UtilText.parse(source, target, parseText).trim());

    	} else {
	        // Normally moves crit on every third use per turn.
	        int thisMoveSelected = 0;
	        for(int i = 0; i < source.getSelectedMoves().size(); i++) {
	            Value<GameCharacter, AbstractCombatMove> move = source.getSelectedMoves().get(i);
	            if(Objects.equals(move.getValue().getIdentifier(), this.getIdentifier())) {
	                thisMoveSelected++;
	            }
	            if(i == turnIndex) {
	                return thisMoveSelected % 3 == 0;
	            }
	        }
	        return false;
    	}
    }

	@Override
    public Colour getColour() {
    	if(this.getAssociatedSpell() != null) {
			return this.getAssociatedSpell().getSpellSchool().getColour();
		}
		return this.getType().getColour();
    }

	public boolean isMod() {
		return mod;
	}

	@Override
	public int getEquipWeighting() {
		return equipWeighting;
	}
}
