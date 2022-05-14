package com.lilithsthrone.game.sex.positions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.BodyPartInterface;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.SexActionInteractions;
import com.lilithsthrone.game.sex.SexAreaOrifice;
import com.lilithsthrone.game.sex.positions.slots.SexSlot;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;

/**
 * SexPositions determine what actions are available for each AbstractSexSlot.<br/><br/>
 * 
 * Each value holds a map, <i>slotTargets</i>, which maps AbstractSexSlots to a map of AbstractSexSlots, which in turn maps to positions available.
 *  By providing a character's position in sex, along with the position of the partner they're targeting, this map is used to fetch available actions.<br/><br/>
 *  
 *  <b>Example:</b><br/>
 *  <i>getSexInteractions(character1AbstractSexSlot, character2AbstractSexSlot)</i><br/>returns the <i>SexActionPresetPair</i> which holds all available actions.<br/><br/>
 *  
 *  If character1AbstractSexSlot is SexPositionSlot.DOGGY_ON_ALL_FOURS, and character2SexPositionSlot is SexPositionSlot.DOGGY_BEHIND, then the returned actions would be those that
 *   are available for the character on all fours, in relation to a character kneeling behind them.
 * 
 * @since 0.1.97
 * @version 0.3.4.5
 * @author Innoxia
 */
public abstract class AbstractSexPosition implements SexPosition {

	String id;
	private String name;
	private int maximumSlots;
	private boolean addStandardActions;
	
	private List<Class<?>> positioningClasses;
	private List<Class<?>> specialClasses;
	
	public static List<SexAreaOrifice> genericGroinForceCreampieAreas = Util.newArrayListOfValues(SexAreaOrifice.ANUS, SexAreaOrifice.VAGINA, SexAreaOrifice.URETHRA_VAGINA, SexAreaOrifice.URETHRA_PENIS, SexAreaOrifice.SPINNERET);
	public static List<SexAreaOrifice> genericFaceForceCreampieAreas = Util.newArrayListOfValues(SexAreaOrifice.MOUTH);
	
	public AbstractSexPosition(String name,
			int maximumSlots,
			boolean addStandardActions,
			List<Class<?>> positioningClasses,
			List<Class<?>> specialClasses) {
		this.name = name;
		this.maximumSlots = maximumSlots;
		this.addStandardActions = addStandardActions;
		this.positioningClasses = positioningClasses;
		this.specialClasses = specialClasses;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return UtilText.parse(name);
	}

	@Override
	public boolean isAddStandardActions() {
		return addStandardActions;
	}

	@Override
	public List<Class<?>> getPositioningClasses() {
		return positioningClasses;
	}

	@Override
	public List<Class<?>> getSpecialClasses() {
		return specialClasses;
	}

	@Override
	public int getMaximumSlots() {
		return maximumSlots;
	}
//		Set<SexSlot> uniqueSlots = new HashSet<>();
//		
//		for(Entry<SexSlot, Map<SexSlot, SexActionInteractions>> e : getSlotTargets().entrySet()) {
//			uniqueSlots.add(e.getKey());
//			uniqueSlots.addAll(e.getValue().keySet());
//		}
//		
//		return uniqueSlots.size();
//	}
	
	protected static Map<SexSlot, Map<SexSlot, SexActionInteractions>> generateSlotTargetsMap(List<Value<SexSlot, Map<SexSlot, SexActionInteractions>>> values) {
		Map<SexSlot, Map<SexSlot, SexActionInteractions>> returnMap = new HashMap<>();
		
		for(Value<SexSlot, Map<SexSlot, SexActionInteractions>> value : values) {
			returnMap.putIfAbsent(value.getKey(), new HashMap<>());
			
			for(Entry<SexSlot, SexActionInteractions> innerValue : value.getValue().entrySet()) {
				returnMap.get(value.getKey()).put(innerValue.getKey(), innerValue.getValue());
			}
		}
//		System.out.println("size: "+returnMap.size());
		return returnMap;
	}

	/**
	 * The underlying map of body parts to orifice lists which is used in the public method isForcedCreampieEnabled(). This is overridden in SexPositionTypes that need to define forced creampies.
	 * 
	 * @param cumTarget The character who is both receiving and forcing the creampie.
	 * @param cumProvider The one who is being forced to cum inside the cumTarget.
	 * @return A map containing keys of body parts, which then map to lists of orifices.
	 * The key represents the body part that can be used by the cumTarget in order to force the cumProvider to cum inside any of the orifices in the value list.
	 */
	protected Map<Class<? extends BodyPartInterface>, List<SexAreaOrifice>> getForcedCreampieMap(GameCharacter cumTarget, GameCharacter cumProvider) {
		return null;
	}
	
	@Override
	public boolean isForcedCreampieEnabled(Class<? extends BodyPartInterface> bodyPartUsed, SexAreaOrifice orifice, GameCharacter cumTarget, GameCharacter cumProvider) {
		if(getForcedCreampieMap(cumTarget, cumProvider)!=null && getForcedCreampieMap(cumTarget, cumProvider).containsKey(bodyPartUsed)) {
			return getForcedCreampieMap(cumTarget, cumProvider).get(bodyPartUsed).contains(orifice);
		}
		return false;
	}
}
