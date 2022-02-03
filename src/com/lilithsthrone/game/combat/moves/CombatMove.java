package com.lilithsthrone.game.combat.moves;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.combat.DamageType;
import com.lilithsthrone.game.combat.DamageVariance;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.combat.spells.SpellSchool;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

import static com.lilithsthrone.game.combat.moves.AbstractCombatMove.shouldBlunder;

/**
 * A class containing logic for Combat Moves.
 * 
 * @since 0.3.4
 * @version 0.4
 * @author Irbynx, Innoxia
 */
public interface CombatMove {

	String getIdentifier();

	String getName(int turnIndex, GameCharacter source);

	String getDescription(int turnIndex, GameCharacter source);

	String getSVGString();

	int getCooldown(GameCharacter source);

	int getEquipWeighting();

	/**
	 * Gets a prediction that specifies what kind of action will be performed for the player (i.e "The catgirl will attack you for 10 damage" or "The horseboy is planning to buff his ally")
	 * @param turnIndex
	 * @param source Character that uses the action.
	 * @param source Target for the action.
	 * @param enemies Enemies of the character
	 * @param allies Allies of the character
	 * @return The string that describes the intent of the NPC that uses this action.
	 */
	String getPrediction(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies);

	/**
	 * Performs the action itself. Override to get actual abilities there.
	 * @param source Character that uses the action.
	 * @param source Target for the action.
	 * @param enemies Enemies of the character
	 * @param allies Allies of the character
	 * @return The string that describes the action that has been performed.
	 */
	String perform(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies);

	/**
	 * Performs the action itself. Override to get actual abilities there. This is performed during selection of the action and not during the turn. Use for blocks, AP damage/gains or disrupts.
	 * @param source Character that uses the action.
	 * @param source Target for the action.
	 * @param enemies Enemies of the character
	 * @param allies Allies of the character
	 * @return The string that describes the action that has been performed.
	 */
	default void performOnSelection(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
		// Nothing. Override it.
	}

	/**
	 * Applies the reverse of the performOnSelection() method. Override whenever performOnSelection() is overridden. This is performed during deselection of the action and not during the turn.
	 * @param source Character that uses the action.
	 * @param source Target for the action.
	 * @param enemies Enemies of the character
	 * @param allies Allies of the character
	 * @return The string that describes the action that has been performed.
	 */
	default void performOnDeselection(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
		// Nothing. Override it.
	}

	default CombatMoveCategory getCategory() {
		return CombatMoveCategory.BASIC;
	}

	default CombatMoveType getType() {
		return CombatMoveType.ATTACK;
	}

	default DamageType getDamageType(int turnIndex, GameCharacter source) {
		return DamageType.PHYSICAL;
	}

	default DamageVariance getDamageVariance() {
		return DamageVariance.NONE;
	}

	default int getBlock(GameCharacter source, boolean isCrit) {
		return 0;
	}

	default Spell getAssociatedSpell() {
		return null;
	}

	default boolean isCanTargetEnemies() {
		return false;
	}

	default boolean isCanTargetAllies() {
		return false;
	}

	default boolean isCanTargetSelf() {
		return false;
	}

	default int getAPcost(GameCharacter source) {
		return source.getEquippedMoves().contains(this) ? 1 : 2;
	}

	/**
	 * Returns weight of the action.
	 *  Used in calculations for AI to pick certain actions.
	 *  For every unspent AP, AI will try to select an action out of the available ones.
	 *  The AI will then pick the action with highest weight.
	 *  Randomness of action selection should be in the weight values itself!
	 * @param source Character that uses the weight function.
	 * @param enemies Enemies of the character
	 * @param allies Allies of the character
	 * @return Weight of the action. 1.0 is the expected normal weight; weigh the actions accordingly.
	 */
	default float getWeight(GameCharacter source, List<GameCharacter> enemies, List<GameCharacter> allies) {
		return 0f;
	}

	/**
	 * Returns the preferred target for the action. Prefers to aim at targets with lowest HP values if not forced to select at random. Override for custom behaviour.
	 * @param source Character that uses the target function.
	 * @param enemies Enemies of the character
	 * @param allies Allies of the character
	 * @return Character to target with this action.
	 */
	default GameCharacter getPreferredTarget(GameCharacter source, List<GameCharacter> enemies, List<GameCharacter> allies) {
		if(isCanTargetEnemies()) {
			if(shouldBlunder()) {
				return enemies.get(Util.random.nextInt(enemies.size()));
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
			if(shouldBlunder()) {
				return allies.get(Util.random.nextInt(allies.size()));
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

	//TODO is this needed when the performOnDeselection() method above exists?
	/**
	 * Cancel out the action's effects if it's disrupted or cancelled via AP loss. Is called for every action in the queue for every disruption caused; non disrupted actions get reapplied.
	 * @param source Character that uses the action.
	 * @param source Target for the action.
	 * @param enemies Enemies of the character
	 * @param allies Allies of the character
	 * @return The string that describes the action that has been performed.
	 */
	default void applyDisruption(GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
		source.setRemainingAP(source.getRemainingAP() + this.getAPcost(source) * -1, enemies, allies); // Normally this is the only thing that gets adjusted on selection.
	}

	/**
	 * Checks the source character to see if they will have to use the action already with a disruption.
	 * @param source
	 */
	default boolean isAlreadyDisrupted(GameCharacter source) {
		return source.disruptionByTypeCheck(this.getType());
	}

	/**
	 * Returns a string if the character has the move available to select even if they don't "own" it; for example, purity based moves are available to Pure Virgin fetishists without even unlocking them.
	 *
	 * String contains the reason for why the move is available to them. Otherwise returns null.
	 */
	default Value<Boolean, String> isAvailableFromSpecialCase(GameCharacter source) {
		return null;
	}

	/**
	 * Returns a string if action can't be used either due to special constraints or because of AP/cooldowns on a specified target; string specifies rejection reason. Returns null if action can be used without an issue.
	 * @param turnIndex The turn index in which this move is to be performed.
	 * @param source Character that uses the action.
	 * @param source Target for the action. Can be null.
	 * @param enemies Enemies of the character
	 * @param allies Allies of the character
	 */
	default String isUsable(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
		if(target != null) {
			if(!isCanTargetSelf() && source == target)
				return "This action can't be used on yourself!";
			if(!isCanTargetAllies() && allies.contains(target) && source != target)
				return "This action can't be used on your allies!";
			if(!isCanTargetEnemies() && enemies.contains(target))
				return "This action can't be used on your enemies!";
		}
		if(source.getMoveCooldown(this.getIdentifier()) > 0)
			return "This action can't be used since it is still on cooldown! "+String.valueOf(source.getMoveCooldown(this.getIdentifier()))+" turns remaining.";
		if(source.getRemainingAP() < this.getAPcost(source))
			return "This action can't be used since you don't have enough AP!";
		return null;
	}

	default Map<StatusEffect,Integer> getStatusEffects(GameCharacter caster, GameCharacter target, boolean isCritical) {
		return Map.of();
	}

	default List<String> getCritRequirements(GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
		return List.of("It's the third time being used.");
	}

	default boolean canCrit(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
		// Normally moves crit on three hits in a row.
		long thisMoveSelected = source.getSelectedMoves().stream().map(m->m.getValue().getIdentifier()).filter(getIdentifier()::equals).count();
		return thisMoveSelected >= 3 && turnIndex % 3 == 2;
	}

	default float getCritStatusEffectDurationMultiplier() {
		return 1;
	}

	default Colour getColour() {
		if(this.getAssociatedSpell() != null) {
			return this.getAssociatedSpell().getSpellSchool().getColour();
		}
		return this.getType().getColour();
	}

	default Colour getColourByDamageType(int turnIndex, GameCharacter source) {
		if (Util.newArrayListOfValues(CombatMoveType.SPELL, CombatMoveType.POWER).contains(getType())) {
			return getDamageType(turnIndex, source).getColour();
		}

		return getType().getColour();
	}

    public static final AbstractCombatMove ITEM_USAGE = new AbstractCombatMove(CombatMoveCategory.SPECIAL,
            "use item",
            0,
            1,
            CombatMoveType.DEFEND,
            DamageType.HEALTH,
            "moves/block",
            Util.newArrayListOfValues(PresetColour.GENERIC_MINOR_GOOD),
            false,
            false,
            true,
            null) {
    	
    	private Value<GameCharacter, AbstractItem> getItem(int turnIndex, GameCharacter source) {
            int index=0;
            int turnCount=0;
            for(var move : source.getSelectedMoves()) {
            	if(turnCount==turnIndex) {
            		break;
            	}
            	if(move.getValue().getIdentifier().equals(ITEM_USAGE.getIdentifier())) {
            		index++;
            	}
            	turnCount++;
            }
            return Main.combat.getItemsToBeUsed(source).get(index);
    	}	
    	
    	@Override
    	public String getName(int turnIndex, GameCharacter source) {
        	Value<GameCharacter, AbstractItem> itemValue = getItem(turnIndex, source);
        	
    		return Util.capitaliseSentence(itemValue.getValue().getItemType().getUseName()+" the "+itemValue.getValue().getName());
    	}
    	
    	@Override
    	public int getAPcost(GameCharacter source) {
    		return 1;
    	}

        @Override
        public Value<Boolean, String> isAvailableFromSpecialCase(GameCharacter source) {
            return new Value<>(true, "Available to everyone as a basic move.");
        }

        @Override
        public String getPrediction(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        	Value<GameCharacter, AbstractItem> itemValue = getItem(turnIndex, source);
            
            return "[style.colourMinorGood("+Util.capitaliseSentence(UtilText.parse(itemValue.getValue().getItemType().getUseName()))+")] the "+itemValue.getValue().getName()+".";
        }

        @Override
        public String getDescription(int turnIndex, GameCharacter source) {
            return "Use an item from your inventory.";
        }

        @Override
        public String perform(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        	Value<GameCharacter, AbstractItem> itemValue = getItem(turnIndex, source);
            
            return itemValue.getValue().applyEffect(source, itemValue.getKey());
        }

        @Override
        public void performOnSelection(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        	Value<GameCharacter, AbstractItem> itemValue = getItem(turnIndex, source);
        	if(itemValue.getValue().isConsumedOnUse()) {
        		source.removeItem(itemValue.getValue());
        	}
        }
        
        @Override
        public void performOnDeselection(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        	Value<GameCharacter, AbstractItem> itemValue = getItem(turnIndex, source);
        	if(itemValue.getValue().isConsumedOnUse()) {
        		source.addItem(itemValue.getValue(), false);
        	}
        }
        
        @Override
    	public List<String> getCritRequirements(GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        	return Util.newArrayListOfValues("This move can never crit.");
        }

        @Override
        public boolean canCrit(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        	return false;
        }
    };

	Table table = new Table();

	final class Table extends com.lilithsthrone.utils.Table<CombatMove> {

		private static final EnumMap<CombatMoveCategory,List<CombatMove>> category = new EnumMap<>(CombatMoveCategory.class);

		private Table() {
			super(CombatMove::sanitize);
        /*=============================================
         *             BASIC MOVES
         =============================================*/
			addFields(CombatMove.class,AbstractCombatMove.class,(k,v)->v.id=k);
        /*=============================================
         *             RACIAL & FETISH
         =============================================*/
			addFields(CMSpecialAttack.class,AbstractCombatMove.class,(k,v)->v.id=k);
			addFields(CMFetishAttack.class,AbstractCombatMove.class,(k,v)->v.id=k);
			addFields(CMWeaponSpecials.class,AbstractCombatMove.class,(k,v)->v.id=k);
	    /*=============================================
	     *                   SPELLS
	     =============================================*/
	    // Automatically generates respective moves based on the Spell class.
	    for(Spell spell : Spell.values()) {
	    	var newCombatMove = new AbstractCombatMove(CombatMoveCategory.SPELL,
	                spell.getName(),
	                spell.getCooldown(),
	                spell.getAPCost(),
	                CombatMoveType.SPELL,
	                spell.getDamageType(),
	                "combat/spell/"+spell.getPathName(),
	                spell.isCanTargetAllies(),
	                spell.isCanTargetEnemies(),
	                spell.isCanTargetSelf(),
	                spell.getStatusEffects(null, null, false)) {
	        	
	        	@Override
	        	public Spell getAssociatedSpell() {
	                return spell;
	            }
	        	
	            @Override
	            public float getCritStatusEffectDurationMultiplier() {
	            	return 2;
	            }
	
	            @Override
	            public Map<StatusEffect, Integer> getStatusEffects(GameCharacter caster, GameCharacter target, boolean isCritical) {
	            	return getAssociatedSpell().getStatusEffects(caster, target, isCritical);
	            }
	            
	            @Override
	            public Value<Boolean, String> isAvailableFromSpecialCase(GameCharacter source) {
	                if(source.hasSpell(this.getAssociatedSpell())) {
	                    return new Value<>(true, "This is a spell available to those who have learned it.");
	                    
	                } else if(source.getExtraSpells().contains(this.getAssociatedSpell())) {
	                    return new Value<>(true, "This spell is available because of your equipped weapon.");
	                    
	                } else {
	                    return null;
	                }
	            }
	
	            @Override
	            public float getWeight(GameCharacter source, List<GameCharacter> enemies, List<GameCharacter> allies) {
	                return getAssociatedSpell().getWeight(source, enemies, allies);
	            }
	
	            @Override
	            public GameCharacter getPreferredTarget(GameCharacter  source, List<GameCharacter> enemies, List<GameCharacter> allies) {
	                return getAssociatedSpell().getPreferredTarget(source, enemies, allies);
	            }
	
	            @Override
	            public String getPrediction(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
	                return getAssociatedSpell().getPrediction(turnIndex, source, target, enemies, allies);
	            }
	
	            @Override
	            public void applyDisruption(GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
	                getAssociatedSpell().applyDisruption(source, target, enemies, allies);
	            }
	
	            @Override
	            public String getDescription(int turnIndex, GameCharacter source) {
	                return getAssociatedSpell().getDescription(source);
	            }
	
	            @Override
	            public String perform(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
	                return getAssociatedSpell().perform(turnIndex, source, target, enemies, allies);
	            }
	
	            @Override
	            public void performOnSelection(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
	                getAssociatedSpell().performOnSelection(turnIndex, source, target, enemies, allies);
	            }
	            
	            @Override
	            public void performOnDeselection(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
	                getAssociatedSpell().performOnDeselection(turnIndex, source, target, enemies, allies);
	            }
	
	            @Override
	            public List<String> getCritRequirements(GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
	            	return getAssociatedSpell().getCritRequirements(source, target, enemies, allies);
	            }
	            
	            @Override
	            public boolean canCrit(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
	                return getAssociatedSpell().canCrit(turnIndex, source, target, enemies, allies);
	            }
	
	            @Override
	            public String isUsable(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
	                String reason = super.isUsable(turnIndex, source, target, enemies, allies);
	                if(reason == null) {
	                    if((getAssociatedSpell().getSpellSchool()!=SpellSchool.FIRE || !source.hasStatusEffect(StatusEffect.FIRE_MANA_BURN)) && source.getMana()<getAssociatedSpell().getModifiedCost(source)) {
	                        reason = "Not enough aura to cast this spell!";
	                    }
	                }
	                return reason;
	            }
	        };
	        newCombatMove.setAssociatedSpell(spell);
				add(newCombatMove.id="SPELL_"+spell.toString(),newCombatMove);
	    }

	    /*=============================================
	     *              EXTERNAL FILES
	     =============================================*/
		// Modded Moves:
			forEachMod("/combatMove",null,null,(f,n,a)->{
				var v = new AbstractCombatMove(f,a,true) {};
				add(v.id=n,v);
			});
		// External res Moves:
			forEachExternal("res//combatMove",null,null,(f,n,a)->{
				var v = new AbstractCombatMove(f,a,false) {};
				add(v.id=n,v);
			});
			for(CombatMoveCategory c : CombatMoveCategory.values())
				category.put(c,new ArrayList<>());
			for(var move : list())
				category.get(move.getCategory()).add(move);
		}
	}

	/**
	 * @param id Will be in the format of: 'innoxia_quadruped_kick'.
	 */
	static CombatMove getCombatMoveFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		for(Spell spell : Spell.values()) {
			if(id.equals(spell.name())) {
				return "SPELL_"+spell.toString();
			}
		}
		
		if(id.equals("item-usage")) {
			return ITEM_USAGE.getIdentifier();
		}
		
		if(id.equals("hoof-kick")) {
			return CMSpecialAttack.HORSE_KICK.getIdentifier();
		} else if(id.equals("cat-scratch")) {
			return CMSpecialAttack.CAT_SCRATCH.getIdentifier();
		} else if(id.equals("tail-swipe") || id.equals("ALLIGATOR_TAIL_SWIPE")) {
			return CMSpecialAttack.TAIL_SWIPE.getIdentifier();
		} else if(id.equals("squirrel-scratch")) {
			return CMSpecialAttack.SQUIRREL_SCRATCH.getIdentifier();
		} else if(id.equals("savage-attack")) {
			return CMSpecialAttack.WOLF_SAVAGE.getIdentifier();
		} else if(id.equals("antler-headbutt")) {
			return CMSpecialAttack.ANTLER_HEADBUTT.getIdentifier();
		} else if(id.equals("horn-headbutt")) {
			return CMSpecialAttack.COW_HEADBUTT.getIdentifier();
		} else if(id.equals("bite")) {
			return CMSpecialAttack.BITE.getIdentifier();
		}
		
		if(id.equals("strike")) {
			return CMBasicAttack.BASIC_STRIKE.getIdentifier();
		} else if(id.equals("offhand-strike")) {
			return CMBasicAttack.BASIC_OFFHAND_STRIKE.getIdentifier();
		} else if(id.equals("twin-strike")) {
			return CMBasicAttack.BASIC_TWIN_STRIKE.getIdentifier();
		} else if(id.equals("block")) {
			return CMBasicAttack.BASIC_BLOCK.getIdentifier();
		} else if(id.equals("tease")) {
			return CMBasicAttack.BASIC_TEASE.getIdentifier();
		} else if(id.equals("avert")) {
			return CMBasicAttack.BASIC_TEASE_BLOCK.getIdentifier();
		} else if(id.equals("arcane-strike")) {
			return CMBasicAttack.BASIC_ARCANE_STRIKE.getIdentifier();
		}
		
		if(id.equals("buttslut-tease")) {
			return CMFetishAttack.TEASE_ANAL_RECEIVING.getIdentifier();
		} else if(id.equals("anal-tease")) {
			return CMFetishAttack.TEASE_ANAL_GIVING.getIdentifier();
		} else if(id.equals("pussy-slut-tease")) {
			return CMFetishAttack.TEASE_VAGINAL_RECEIVING.getIdentifier();
		} else if(id.equals("vaginal-tease")) {
			return CMFetishAttack.TEASE_VAGINAL_GIVING.getIdentifier();
		} else if(id.equals("incest-tease")) {
			return CMFetishAttack.TEASE_INCEST.getIdentifier();
		} else if(id.equals("cum-stud-tease")) {
			return CMFetishAttack.TEASE_CUM_STUD.getIdentifier();
		} else if(id.equals("cum-addict-tease")) {
			return CMFetishAttack.TEASE_CUM_ADDICT.getIdentifier();
		} else if(id.equals("cock-addict-tease")) {
			return CMFetishAttack.TEASE_PENIS_RECEIVING.getIdentifier();
		} else if(id.equals("cock-stud-tease")) {
			return CMFetishAttack.TEASE_PENIS_GIVING.getIdentifier();
		} else if(id.equals("submissive-foot-tease")) {
			return CMFetishAttack.TEASE_FOOT_RECEIVING.getIdentifier();
		} else if(id.equals("dominant-foot-tease")) {
			return CMFetishAttack.TEASE_FOOT_GIVING.getIdentifier();
		} else if(id.equals("oral-tease")) {
			return CMFetishAttack.TEASE_ORAL_RECEIVING.getIdentifier();
		} else if(id.equals("oral-performer-tease")) {
			return CMFetishAttack.TEASE_ORAL_GIVING.getIdentifier();
		} else if(id.equals("breasts-lover-tease")) {
			return CMFetishAttack.TEASE_BREASTS_OTHERS.getIdentifier();
		} else if(id.equals("breasts-tease")) {
			return CMFetishAttack.TEASE_BREASTS.getIdentifier();
		} else if(id.equals("milk-lover-tease")) {
			return CMFetishAttack.TEASE_LACTATION_OTHERS.getIdentifier();
		} else if(id.equals("lactation-tease")) {
			return CMFetishAttack.TEASE_LACTATION.getIdentifier();
		} else if(id.equals("fertility-tease")) {
			return CMFetishAttack.TEASE_FERTILITY.getIdentifier();
		} else if(id.equals("virility-tease")) {
			return CMFetishAttack.TEASE_VIRILITY.getIdentifier();
		} else if(id.equals("dominant-tease")) {
			return CMFetishAttack.TEASE_DOMINANT.getIdentifier();
		} else if(id.equals("submissive-tease")) {
			return CMFetishAttack.TEASE_SUBMISSIVE.getIdentifier();
		}
		return id;
	}

	static String getIdFromCombatMove(CombatMove move) {
		return move.getIdentifier();
	}

	static List<CombatMove> getAllCombatMoves() {
		return table.list();
	}

	static List<CombatMove> getAllCombatMovesInCategory(CombatMoveCategory category) {
		return Table.category.get(category);
	}
}
