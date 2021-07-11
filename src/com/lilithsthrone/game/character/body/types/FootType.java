package com.lilithsthrone.game.character.body.types;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractFootType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.FootStructure;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.2.10
 * @version 0.4
 * @author Innoxia
 */
public class FootType {

	public static AbstractFootType NONE = new AbstractFootType("none",
			"none",
			"none",
			List.of(""),
			List.of(""),
			"none",
			"none",
			List.of(""),
			List.of(""),
			"footjob",
			"[npc.SheHasFull] no feet.",
			List.of(FootStructure.NONE)) {
				@Override
				public String getFootNailPolishDescription(GameCharacter owner) {
					return "";
				}
	};
	
	public static AbstractFootType HUMANOID = new AbstractFootType("humanoid",
			"foot",
			"feet",
			List.of("masculine"),
			List.of("feminine", "soft", "delicate", "slender"),
			"toe",
			"toes",
			List.of("masculine"),
			List.of("feminine", "soft", "delicate", "slender"),
			"footjob",
			"[npc.SheHasFull] human-like feet.",
			List.of(FootStructure.PLANTIGRADE)) {
		@Override
		public String getFootNailPolishDescription(GameCharacter owner) {
			return " [npc.Her] toenails have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".";
		}
	};

	public static AbstractFootType PAWS = new AbstractFootType("paw-like",
			"paw",
			"paws",
			List.of("masculine","padded"),
			List.of("feminine", "soft", "padded", "delicate", "slender"),
			"toe",
			"toes",
			List.of("masculine", "padded"),
			List.of("feminine", "soft", "padded", "delicate", "slender"),
			"footjob",
			"[npc.SheHasFull] paw-like feet.",
			List.of(FootStructure.PLANTIGRADE,
					FootStructure.DIGITIGRADE)) {
		@Override
		public String getFootNailPolishDescription(GameCharacter owner) {
			return " [npc.Her] toenails have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".";
		}
	};

	public static AbstractFootType HOOFS = new AbstractFootType("hoof-like",
			"hoof",
			"hoofs",
			List.of("masculine","hard"),
			List.of("feminine", "delicate", "hard"),
			"hoof",
			"hoofs",
			List.of("masculine", "hard"),
			List.of("feminine", "hard", "delicate"),
			"hoofjob",
			"[npc.SheHasFull] hoofs in place of feet.",
			List.of(FootStructure.UNGULIGRADE)) {
		@Override
		public String getFootNailPolishDescription(GameCharacter owner) {
			return " [npc.Her] hoofs have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".";
		}
	};

	public static AbstractFootType REPTILIAN = new AbstractFootType("reptilian",
			"foot",
			"feet",
			List.of("masculine","clawed"),
			List.of("feminine", "clawed", "slender"),
			"toe",
			"toes",
			List.of("masculine", "clawed"),
			List.of("feminine", "clawed", "slender"),
			"footjob",
			"[npc.SheHasFull] reptilian feet.",
			List.of(FootStructure.PLANTIGRADE,
					FootStructure.DIGITIGRADE)) {
		@Override
		public String getFootNailPolishDescription(GameCharacter owner) {
			return " The claws at the end of [npc.her] stout toes have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".";
		}
	};

	public static AbstractFootType AMPHIBIAN = new AbstractFootType("amphibian",
			"foot",
			"feet",
			List.of("masculine", "webbed"),
			List.of("feminine", "webbed", "slender"),
			"toe",
			"toes",
			List.of("masculine", "webbed"),
			List.of("feminine", "webbed", "slender"),
			"footjob",
			"[npc.SheHasFull] amphibian feet.",
			List.of(FootStructure.PLANTIGRADE,
					FootStructure.DIGITIGRADE)) {
		@Override
		public String getFootNailPolishDescription(GameCharacter owner) {
			return " The ends of [npc.her] toes have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".";
		}
	};

	public static AbstractFootType TALONS = new AbstractFootType("bird-like",
			"talon",
			"talons",
			List.of("masculine","clawed"),
			List.of("feminine", "clawed", "slender"),
			"claw",
			"claws",
			List.of("masculine", "sharp"),
			List.of("feminine", "sharp", "slender"),
			"clawjob",
			"[npc.SheHasFull] bird-like talons in place of feet.",
			List.of(FootStructure.DIGITIGRADE)) {
		@Override
		public String getFootNailPolishDescription(GameCharacter owner) {
			return " The claws on [npc.her] talons have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".";
		}
	};

	public static AbstractFootType ARACHNID = new AbstractFootType("arachnid",
			"foot",
			"feet",
			List.of("masculine","segmented"),
			List.of("feminine", "segmented", "slender"),
			"claw",
			"claws",
			List.of("masculine", "sharp"),
			List.of("feminine", "sharp", "slender"),
			"clawjob",
			"[npc.SheHasFull] arachnid claws in place of feet.",
			List.of(FootStructure.PLANTIGRADE,
					FootStructure.DIGITIGRADE)) {
		@Override
		public String getFootNailPolishDescription(GameCharacter owner) {
			return " The claws on [npc.her] arachnid legs have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".";
		}
	};

	public static AbstractFootType TENTACLE = new AbstractFootType("tentacle",
			"tentacle",
			"tentacles",
			List.of("masculine","strong"),
			List.of("feminine", "strong", "slender"),
			"sucker",
			"suckers",
			List.of("strong"),
			List.of("strong"),
			"tentaclejob",
			"The ends of [npc.her] tentacles are used in much the same way as feet.",
			List.of(FootStructure.TENTACLED)) {
		@Override
		public String getFootNailPolishDescription(GameCharacter owner) {
			return " The tips of [npc.her] tentacles have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".";
		}
	};
	
	

	private static List<AbstractFootType> allFootTypes;
	private static Map<AbstractFootType, String> footToIdMap = new HashMap<>();
	private static Map<String, AbstractFootType> idToFootMap = new HashMap<>();
	
	static {
		allFootTypes = new ArrayList<>();
		
		// Add in hard-coded foot types:
		Field[] fields = FootType.class.getFields();
		
		for(Field f : fields){
			if (AbstractFootType.class.isAssignableFrom(f.getType())) {
				
				AbstractFootType ct;
				try {
					ct = ((AbstractFootType) f.get(null));

					footToIdMap.put(ct, f.getName());
					idToFootMap.put(f.getName(), ct);
					
					allFootTypes.add(ct);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static AbstractFootType getFootTypeFromId(String id) {
		id = Util.getClosestStringMatch(id, idToFootMap.keySet());
		return idToFootMap.get(id);
	}
	
	public static String getIdFromFootType(AbstractFootType footType) {
		return footToIdMap.get(footType);
	}
	
	public static List<AbstractFootType> getAllFootTypes() {
		return allFootTypes;
	}
}
