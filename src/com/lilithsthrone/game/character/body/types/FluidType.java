package com.lilithsthrone.game.character.body.types;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithsthrone.game.character.body.abstractTypes.AbstractFluidType;
import com.lilithsthrone.game.character.body.valueEnums.FluidFlavour;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.character.body.valueEnums.FluidTypeBase;
import com.lilithsthrone.game.character.race.AbstractRace;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.83
 * @version 0.3.8.2
 * @author Innoxia
 */
public class FluidType {
	
	// Cum:
	
	 public static AbstractFluidType CUM_HUMAN = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.HUMAN,
			null,
			null,
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_ANGEL = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.ANGEL,
			List.of("angel-"),
			List.of("angel-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_DEMON = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.DEMON,
			List.of("demon-", "demonic-"),
			List.of("demon-", "demonic-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_DOG_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.DOG_MORPH,
			List.of("dog-", "canine-"),
			List.of("bitch-", "canine-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_WOLF_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.WOLF_MORPH,
			List.of("wolf-", "lupine-"),
			List.of("wolf-", "lupine-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.MUSKY,
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_FOX_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.FOX_MORPH,
			List.of("fox-", "vulpine-"),
			List.of("vixen-", "vulpine-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_CAT_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.CAT_MORPH,
			List.of("cat-", "feline-"),
			List.of("cat-", "feline-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_SQUIRREL_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.SQUIRREL_MORPH,
			List.of("squirrel-", "rodent-"),
			List.of("squirrel-", "rodent-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_RAT_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.RAT_MORPH,
			List.of("rat-", "rodent-"),
			List.of("rat-", "rodent-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_RABBIT_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.RABBIT_MORPH,
			List.of("rabbit-"),
			List.of("rabbit-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_BAT_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.BAT_MORPH,
			List.of("bat-"),
			List.of("bat-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_ALLIGATOR_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.ALLIGATOR_MORPH,
			List.of("alligator-", "reptilian-"),
			List.of("alligator-", "reptilian-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_HORSE_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.HORSE_MORPH,
			List.of("stallion-", "horse-", "equine-"),
			List.of("mare-", "horse-", "equine-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.MUSKY,
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_REINDEER_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.REINDEER_MORPH,
			List.of("reindeer-"),
			List.of("reindeer-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.MUSKY,
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_COW_MORPH = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.COW_MORPH,
			List.of("bull-", "bovine-"),
			List.of("cow-", "bovine-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.MUSKY,
					FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType CUM_HARPY = new AbstractFluidType(FluidTypeBase.CUM,
			FluidFlavour.CUM,
			Race.HARPY,
			List.of("harpy-", "avian-"),
			List.of("harpy-", "avian-"),
			List.of(""),
			List.of(""),
			List.of(FluidModifier.STICKY,
					FluidModifier.SLIMY)) {
	};

	// Girl cum:
	
	 public static AbstractFluidType GIRL_CUM_HUMAN = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.HUMAN,
			null,
			null,
			List.of(""),
			List.of(""),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_ANGEL = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.ANGEL,
			null,
			null,
			List.of("angelic"),
			List.of("angelic"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_DEMON = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.DEMON,
			null,
			null,
			List.of("demonic"),
			List.of("demonic"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_DOG_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.DOG_MORPH,
			null,
			null,
			List.of("canine"),
			List.of("canine"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_WOLF_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.WOLF_MORPH,
			null,
			null,
			List.of("lupine"),
			List.of("lupine"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_FOX_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.FOX_MORPH,
			null,
			null,
			List.of("vulpine"),
			List.of("vulpine"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_CAT_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.CAT_MORPH,
			null,
			null,
			List.of("feline"),
			List.of("feline"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_SQUIRREL_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.SQUIRREL_MORPH,
			null,
			null,
			List.of("squirrel"),
			List.of("squirrel"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_RAT_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.RAT_MORPH,
			null,
			null,
			List.of("rat"),
			List.of("rat"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_RABBIT_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.RABBIT_MORPH,
			null,
			null,
			List.of("rabbit"),
			List.of("rabbit"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_BAT_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.BAT_MORPH,
			null,
			null,
			List.of("bat"),
			List.of("bat"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_ALLIGATOR_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.ALLIGATOR_MORPH,
			null,
			null,
			List.of("alligator"),
			List.of("alligator"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_HORSE_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.HORSE_MORPH,
			null,
			null,
			List.of("equine"),
			List.of("equine"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_REINDEER_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.REINDEER_MORPH,
			null,
			null,
			List.of("reindeer"),
			List.of("reindeer"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_COW_MORPH = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.COW_MORPH,
			null,
			null,
			List.of("bovine"),
			List.of("bovine"),
			List.of(FluidModifier.SLIMY)) {
	};

	 public static AbstractFluidType GIRL_CUM_HARPY = new AbstractFluidType(FluidTypeBase.GIRLCUM,
			FluidFlavour.GIRL_CUM,
			Race.HARPY,
			null,
			null,
			List.of("avian"),
			List.of("avian"),
			List.of(FluidModifier.SLIMY)) {
	};

	// Milks:
	
	 public static AbstractFluidType MILK_HUMAN = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.HUMAN,
			null,
			null,
			List.of(""),
			List.of(""),
			List.of()) {
	};

	 public static AbstractFluidType MILK_ANGEL = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.ANGEL,
			null,
			null,
			List.of("angelic"),
			List.of("angelic"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_COW_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.COW_MORPH,
			null,
			null,
			List.of("bovine"),
			List.of("bovine"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_DEMON = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.DEMON,
			null,
			null,
			List.of("demonic"),
			List.of("demonic"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_DOG_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.DOG_MORPH,
			null,
			null,
			List.of("canine"),
			List.of("canine"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_WOLF_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.WOLF_MORPH,
			null,
			null,
			List.of("lupine"),
			List.of("lupine"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_FOX_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.FOX_MORPH,
			null,
			null,
			List.of("vulpine"),
			List.of("vulpine"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_CAT_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.CAT_MORPH,
			null,
			null,
			List.of("feline"),
			List.of("feline"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_SQUIRREL_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.SQUIRREL_MORPH,
			null,
			null,
			List.of("squirrel"),
			List.of("squirrel"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_RAT_MORPH = new AbstractFluidType(FluidTypeBase.MILK, // I don't get it. Everyone loves rats, but they don't wanna drink the rats' milk?
			FluidFlavour.MILK,
			Race.RAT_MORPH,
			null,
			null,
			List.of("rat"),
			List.of("rat"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_RABBIT_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.RABBIT_MORPH,
			null,
			null,
			List.of("rabbit"),
			List.of("rabbit"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_BAT_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.BAT_MORPH,
			null,
			null,
			List.of("bat"),
			List.of("bat"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_ALLIGATOR_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.ALLIGATOR_MORPH,
			null,
			null,
			List.of("alligator"),
			List.of("alligator"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_HORSE_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.HORSE_MORPH,
			null,
			null,
			List.of("equine"),
			List.of("equine"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_REINDEER_MORPH = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.REINDEER_MORPH,
			null,
			null,
			List.of("reindeer"),
			List.of("reindeer"),
			List.of()) {
	};

	 public static AbstractFluidType MILK_HARPY = new AbstractFluidType(FluidTypeBase.MILK,
			FluidFlavour.MILK,
			Race.HARPY,
			null,
			null,
			List.of("avian"),
			List.of("avian"),
			List.of()) {
	};
	
	
	private static List<AbstractFluidType> allFluidTypes;
	private static Map<AbstractFluidType, String> fluidToIdMap = new HashMap<>();
	private static Map<String, AbstractFluidType> idToFluidMap = new HashMap<>();
	
	static {
		allFluidTypes = new ArrayList<>();

		// Modded types:
		
		Map<String, Map<String, File>> moddedFilesMap = Util.getExternalModFilesById("/race", "bodyParts", null);
		for(Entry<String, Map<String, File>> entry : moddedFilesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				if(Util.getXmlRootElementName(innerEntry.getValue()).equals("fluid")) {
					try {
						AbstractFluidType type = new AbstractFluidType(innerEntry.getValue(), entry.getKey(), true) {};
						String id = innerEntry.getKey().replaceAll("bodyParts_", "");
						allFluidTypes.add(type);
						fluidToIdMap.put(type, id);
						idToFluidMap.put(id, type);
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
				if(Util.getXmlRootElementName(innerEntry.getValue()).equals("fluid")) {
					try {
						AbstractFluidType type = new AbstractFluidType(innerEntry.getValue(), entry.getKey(), false) {};
						String id = innerEntry.getKey().replaceAll("bodyParts_", "");
						allFluidTypes.add(type);
						fluidToIdMap.put(type, id);
						idToFluidMap.put(id, type);
					} catch(Exception ex) {
						ex.printStackTrace(System.err);
					}
				}
			}
		}
		
		// Add in hard-coded fluid types:
		
		Field[] fields = FluidType.class.getFields();
		
		for(Field f : fields){
			if (AbstractFluidType.class.isAssignableFrom(f.getType())) {
				
				AbstractFluidType ct;
				try {
					ct = ((AbstractFluidType) f.get(null));

					fluidToIdMap.put(ct, f.getName());
					idToFluidMap.put(f.getName(), ct);
					
					allFluidTypes.add(ct);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static AbstractFluidType getFluidTypeFromId(String id) {
		if(id.equals("CUM_IMP")) {
			id = "CUM_DEMON";
			
		} else if(id.equals("GIRL_CUM_IMP")) {
			id = "GIRL_CUM_DEMON";
			
		} else if(id.equals("MILK_IMP")) {
			id = "MILK_DEMON";
			
		} else if(id.equals("MILK_DEMON_COMMON")) {
			id = "MILK_DEMON";
		}
		
		id = Util.getClosestStringMatch(id, idToFluidMap.keySet());
		return idToFluidMap.get(id);
	}
	
	public static String getIdFromFluidType(AbstractFluidType fluidType) {
		return fluidToIdMap.get(fluidType);
	}
	
	public static List<AbstractFluidType> getAllFluidTypes() {
		return allFluidTypes;
	}
	
	private static Map<AbstractRace, List<AbstractFluidType>> typesMap = new HashMap<>();
	public static List<AbstractFluidType> getFluidTypes(AbstractRace r) {
		if(typesMap.containsKey(r)) {
			return typesMap.get(r);
		}
		
		List<AbstractFluidType> types = new ArrayList<>();
		for(AbstractFluidType type : FluidType.getAllFluidTypes()) {
			if(type.getRace()==r) {
				types.add(type);
			}
		}
		typesMap.put(r, types);
		return types;
	}
}