package com.lilithsthrone.game.character.body.types;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithsthrone.game.character.body.abstractTypes.AbstractNippleType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.race.AbstractRace;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.8.2
 * @author Innoxia
 */
public class NippleType {

	public static AbstractNippleType HUMAN = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.HUMAN,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType ANGEL = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.ANGEL,
			List.of("perfect", "flawless"),
			List.of("perfect", "flawless"),
			List.of()){
	};

	public static AbstractNippleType DEMON = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.DEMON,
			List.of("perfect", "flawless"),
			List.of("perfect", "flawless"),
			List.of()){
	};

	public static AbstractNippleType DOG_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.DOG_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType WOLF_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.WOLF_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType FOX_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.FOX_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType CAT_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.CAT_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType COW_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.COW_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType SQUIRREL_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.SQUIRREL_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType RAT_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.RAT_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType BAT_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.BAT_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType RABBIT_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.RABBIT_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType ALLIGATOR_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.ALLIGATOR_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType HORSE_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.HORSE_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType REINDEER_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.REINDEER_MORPH,
			List.of(""),
			List.of(""),
			List.of()){
	};

	public static AbstractNippleType HARPY = new AbstractNippleType(BodyCoveringType.NIPPLES,
			Race.HARPY,
			List.of(""),
			List.of(""),
			List.of()){
	};

	
//	/**
//	 * Use instead of <i>valueOf()</i>.
//	 */
//	public static NippleType getTypeFromString(String value) {
//		if(value.equals("IMP")) {
//			value = "DEMON_COMMON";
//		}
//		return valueOf(value);
//	}
	
	private static List<AbstractNippleType> allNippleTypes;
	private static Map<AbstractNippleType, String> nippleToIdMap = new HashMap<>();
	private static Map<String, AbstractNippleType> idToNippleMap = new HashMap<>();
	
	static {
		allNippleTypes = new ArrayList<>();
		
		// Modded types:
		
		Map<String, Map<String, File>> moddedFilesMap = Util.getExternalModFilesById("/race", "bodyParts", null);
		for(Entry<String, Map<String, File>> entry : moddedFilesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				if(Util.getXmlRootElementName(innerEntry.getValue()).equals("nipple")) {
					try {
						AbstractNippleType type = new AbstractNippleType(innerEntry.getValue(), entry.getKey(), true) {};
						String id = innerEntry.getKey().replaceAll("bodyParts_", "");
						allNippleTypes.add(type);
						nippleToIdMap.put(type, id);
						idToNippleMap.put(id, type);
					} catch(Exception ex) {
						ex.printStackTrace(System.err);
					}
				}
			}
		}
		
		// External res types:
		
		Map<String, Map<String, File>> filesMap = Util.getExternalFilesById("res/race", "bodyParts", null);
		for(Entry<String, Map<String, File>> entry : filesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				if(Util.getXmlRootElementName(innerEntry.getValue()).equals("nipple")) {
					try {
						AbstractNippleType type = new AbstractNippleType(innerEntry.getValue(), entry.getKey(), false) {};
						String id = innerEntry.getKey().replaceAll("bodyParts_", "");
						allNippleTypes.add(type);
						nippleToIdMap.put(type, id);
						idToNippleMap.put(id, type);
					} catch(Exception ex) {
						ex.printStackTrace(System.err);
					}
				}
			}
		}
		
		// Add in hard-coded nipple types:
		
		Field[] fields = NippleType.class.getFields();
		
		for(Field f : fields){
			if (AbstractNippleType.class.isAssignableFrom(f.getType())) {
				
				AbstractNippleType ct;
				try {
					ct = ((AbstractNippleType) f.get(null));

					nippleToIdMap.put(ct, f.getName());
					idToNippleMap.put(f.getName(), ct);
					
					allNippleTypes.add(ct);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		Collections.sort(allNippleTypes, (t1, t2)->
			t1.getRace()==Race.NONE
				?-1
				:(t2.getRace()==Race.NONE
					?1
					:t1.getRace().getName(false).compareTo(t2.getRace().getName(false))));
	}
	
	public static AbstractNippleType getNippleTypeFromId(String id) {
		if(id.equals("IMP") || id.equals("DEMON_COMMON")) {
			return NippleType.DEMON;
		}
		
		id = Util.getClosestStringMatch(id, idToNippleMap.keySet());
		return idToNippleMap.get(id);
	}
	
	public static String getIdFromNippleType(AbstractNippleType nippleType) {
		return nippleToIdMap.get(nippleType);
	}
	
	public static List<AbstractNippleType> getAllNippleTypes() {
		return allNippleTypes;
	}
	
	private static Map<AbstractRace, List<AbstractNippleType>> typesMap = new HashMap<>();
	public static List<AbstractNippleType> getNippleTypes(AbstractRace r) {
		if(typesMap.containsKey(r)) {
			return typesMap.get(r);
		}
		
		List<AbstractNippleType> types = new ArrayList<>();
		for(AbstractNippleType type : NippleType.getAllNippleTypes()) {
			if(type.getRace()==r) {
				types.add(type);
			}
		}
		typesMap.put(r, types);
		return types;
	}
}