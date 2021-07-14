package com.lilithsthrone.game.sex.sexActions;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.Map.entry;

import com.lilithsthrone.game.sex.sexActions.baseActions.ClitAnus;
import com.lilithsthrone.game.sex.sexActions.baseActions.ClitClit;
import com.lilithsthrone.game.sex.sexActions.baseActions.ClitMouth;
import com.lilithsthrone.game.sex.sexActions.baseActions.ClitVagina;
import com.lilithsthrone.game.sex.sexActions.baseActions.FingerAnus;
import com.lilithsthrone.game.sex.sexActions.baseActions.FingerBreasts;
import com.lilithsthrone.game.sex.sexActions.baseActions.FingerBreastsCrotch;
import com.lilithsthrone.game.sex.sexActions.baseActions.FingerClit;
import com.lilithsthrone.game.sex.sexActions.baseActions.FingerMouth;
import com.lilithsthrone.game.sex.sexActions.baseActions.FingerNipple;
import com.lilithsthrone.game.sex.sexActions.baseActions.FingerNippleCrotch;
import com.lilithsthrone.game.sex.sexActions.baseActions.FingerPenis;
import com.lilithsthrone.game.sex.sexActions.baseActions.FingerVagina;
import com.lilithsthrone.game.sex.sexActions.baseActions.FootMouth;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisAnus;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisArmpit;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisAss;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisBreasts;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisBreastsCrotch;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisFeet;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisFoot;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisMouth;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisNipple;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisNippleCrotch;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisSpinneret;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisThighs;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisUrethraPenis;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisUrethraVagina;
import com.lilithsthrone.game.sex.sexActions.baseActions.PenisVagina;
import com.lilithsthrone.game.sex.sexActions.baseActions.TailAnus;
import com.lilithsthrone.game.sex.sexActions.baseActions.TailMouth;
import com.lilithsthrone.game.sex.sexActions.baseActions.TailVagina;
import com.lilithsthrone.game.sex.sexActions.baseActions.TentacleAnus;
import com.lilithsthrone.game.sex.sexActions.baseActions.TentacleMouth;
import com.lilithsthrone.game.sex.sexActions.baseActions.TentacleVagina;
import com.lilithsthrone.game.sex.sexActions.baseActions.TongueAnus;
import com.lilithsthrone.game.sex.sexActions.baseActions.TongueArmpit;
import com.lilithsthrone.game.sex.sexActions.baseActions.TongueBreasts;
import com.lilithsthrone.game.sex.sexActions.baseActions.TongueBreastsCrotch;
import com.lilithsthrone.game.sex.sexActions.baseActions.TongueMound;
import com.lilithsthrone.game.sex.sexActions.baseActions.TongueMouth;
import com.lilithsthrone.game.sex.sexActions.baseActions.TongueNipple;
import com.lilithsthrone.game.sex.sexActions.baseActions.TongueNippleCrotch;
import com.lilithsthrone.game.sex.sexActions.baseActions.TongueVagina;
import com.lilithsthrone.game.sex.sexActions.baseActionsMisc.GenericActions;
import com.lilithsthrone.game.sex.sexActions.baseActionsMisc.GenericOrgasms;
import com.lilithsthrone.game.sex.sexActions.baseActionsMisc.GenericPositioning;
import com.lilithsthrone.game.sex.sexActions.baseActionsMisc.GenericTalk;
import com.lilithsthrone.game.sex.sexActions.baseActionsMisc.PartnerTalk;
import com.lilithsthrone.game.sex.sexActions.baseActionsMisc.PlayerTalk;
import com.lilithsthrone.game.sex.sexActions.baseActionsMisc.PositioningMenu;
import com.lilithsthrone.game.sex.sexActions.baseActionsMisc.SadisticActions;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfFingerAnus;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfFingerBreasts;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfFingerCrotchNipple;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfFingerMouth;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfFingerNipple;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfFingerPenis;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfFingerVagina;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfNoPen;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfPenisAnus;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfPenisMouth;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfPenisNipple;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfPenisVagina;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfTailAnus;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfTailMouth;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfTailNipple;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfTailVagina;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfTongueAnus;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfTongueMouth;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfTongueNipple;
import com.lilithsthrone.game.sex.sexActions.baseActionsSelf.SelfTongueVagina;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;

/**
 * Handles the loading and id generation of SexActions from both internal and external files.
 * 
 * @since 0.4.1
 * @version 0.4.1
 * @author Innoxia
 */
public class SexActionManager {
	
	private static List<SexAction> allSexActions = new ArrayList<>();
	private static Map<SexAction, String> sexActionToIdMap = new HashMap<>();
	private static Map<String, SexAction> idToSexActionMap = new HashMap<>();
	
	public static List<SexAction> getAllSexActions() {
		return allSexActions;
	}
	
	public static SexAction getSexActionFromId(String id) {
		id = id.trim(); // Just make sure that any parsed ids have been trimmed
		id = Util.getClosestStringMatch(id, idToSexActionMap.keySet());
		return idToSexActionMap.get(id);
	}
	
	public static String getIdFromSexAction(SexAction sexAction) {
		return sexActionToIdMap.get(sexAction);
	}
	
	static {
		// Modded sexActions:
		
		Map<String, Map<String, File>> moddedFilesMap = Util.getExternalModFilesById("/sex/actions");
		for(Entry<String, Map<String, File>> entry : moddedFilesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				try {
					SexActionExternal sexAction = new SexActionExternal(innerEntry.getValue(), innerEntry.getKey(), false);
					String id = innerEntry.getKey();
					id = id.replaceAll("sex_actions_", "");
					allSexActions.add(sexAction);
					sexActionToIdMap.put(sexAction, id);
					idToSexActionMap.put(id, sexAction);
//					System.out.println("modded sexAction: "+innerEntry.getKey());
				} catch(Exception ex) {
					System.err.println("Loading modded sexAction type failed at 'SexAction'. File path: "+innerEntry.getValue().getAbsolutePath());
					System.err.println("Actual exception: ");
					ex.printStackTrace(System.err);
				}
			}
		}
		
		// External res SexActions:
		
		// Ids are generated in the format 'author_folders_fileName'
		// Example: innoxia_meraxis_masturbation_orgasm_panties_cum
		Map<String, Map<String, File>> filesMap = Util.getExternalFilesById("res/sex");
		for(Entry<String, Map<String, File>> entry : filesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				if(Util.getXmlRootElementName(innerEntry.getValue()).equals("sexAction")) {
					try {
						SexActionExternal sexAction = new SexActionExternal(innerEntry.getValue(), innerEntry.getKey(), false);
						String id = innerEntry.getKey();
						id = id.replaceAll("actions_", "");
						allSexActions.add(sexAction);
						sexActionToIdMap.put(sexAction, id);
						idToSexActionMap.put(id, sexAction);
//						System.out.println("res sexAction: "+node.getId());
							
					} catch(Exception ex) {
						System.err.println("Loading sexAction type failed at 'SexAction'. File path: "+innerEntry.getValue().getAbsolutePath());
						System.err.println("Actual exception: ");
						ex.printStackTrace(System.err);
					}
				}
			}
		}

		// Add in hard-coded sex actions:
		
		Map<String, Field[]> sexActionClassIdToFields = Map.<String,Field[]>ofEntries(
				entry("PositioningMenu", PositioningMenu.class.getFields()),
				entry("GenericPositioning", GenericPositioning.class.getFields()),

				entry("GenericActions", GenericActions.class.getFields()),
				entry("GenericOrgasms", GenericOrgasms.class.getFields()),
				entry("PlayerTalk", PlayerTalk.class.getFields()),
				entry("PartnerTalk", PartnerTalk.class.getFields()),
				entry("GenericTalk", GenericTalk.class.getFields()),

				// Sadistic actions:
				entry("SadisticActions", SadisticActions.class.getFields()),

				// Finger actions:
				entry("FingerAnus", FingerAnus.class.getFields()),
				entry("FingerBreasts", FingerBreasts.class.getFields()),
				entry("FingerBreastsCrotch", FingerBreastsCrotch.class.getFields()),
				entry("FingerMouth", FingerMouth.class.getFields()),
				entry("FingerNipple", FingerNipple.class.getFields()),
				entry("FingerNippleCrotch", FingerNippleCrotch.class.getFields()),
				entry("FingerVagina", FingerVagina.class.getFields()),
				entry("FingerClit", FingerClit.class.getFields()),
				entry("FingerPenis", FingerPenis.class.getFields()),

				// Oral actions:
				entry("TongueMouth", TongueMouth.class.getFields()),
				entry("TongueAnus", TongueAnus.class.getFields()),
				entry("TongueVagina", TongueVagina.class.getFields()),
				entry("ClitMouth", ClitMouth.class.getFields()),
				entry("TongueMound", TongueMound.class.getFields()),
				entry("TongueBreasts", TongueBreasts.class.getFields()),
				entry("TongueBreastsCrotch", TongueBreastsCrotch.class.getFields()),
				entry("TongueNipple", TongueNipple.class.getFields()),
				entry("TongueNippleCrotch", TongueNippleCrotch.class.getFields()),
				entry("FootMouth", FootMouth.class.getFields()),
				entry("PenisMouth", PenisMouth.class.getFields()),
				entry("TongueArmpits", TongueArmpit.class.getFields()),

				// Tail actions:
				entry("TailAnus", TailAnus.class.getFields()),
				entry("TailVagina", TailVagina.class.getFields()),
				entry("TailMouth", TailMouth.class.getFields()),

				// Tentacle actions:
				entry("TentacleAnus", TentacleAnus.class.getFields()),
				entry("TentacleVagina", TentacleVagina.class.getFields()),
				entry("TentacleMouth", TentacleMouth.class.getFields()),

				// Penis actions:
				entry("PenisAss", PenisAss.class.getFields()),
				entry("PenisAnus", PenisAnus.class.getFields()),
				entry("PenisNipple", PenisNipple.class.getFields()),
				entry("PenisNippleCrotch", PenisNippleCrotch.class.getFields()),
				entry("PenisVagina", PenisVagina.class.getFields()),
				entry("PenisBreasts", PenisBreasts.class.getFields()),
				entry("PenisBreastsCrotch", PenisBreastsCrotch.class.getFields()),
				entry("PenisThighs", PenisThighs.class.getFields()),
				entry("PenisFoot", PenisFoot.class.getFields()),
				entry("PenisFeet", PenisFeet.class.getFields()),
				entry("PenisUrethraVagina", PenisUrethraVagina.class.getFields()),
				entry("PenisUrethraPenis", PenisUrethraPenis.class.getFields()),
				entry("PenisSpinneret", PenisSpinneret.class.getFields()),
				entry("PenisArmpit", PenisArmpit.class.getFields()),

				// Vagina/clit actions:
				entry("ClitClit", ClitClit.class.getFields()),
				entry("ClitVagina", ClitVagina.class.getFields()),
				entry("ClitAnus", ClitAnus.class.getFields()),

				// Self actions
				entry("SelfNoPen", SelfNoPen.class.getFields()),
				entry("SelfFingerAnus", SelfFingerAnus.class.getFields()),
				entry("SelfFingerBreasts", SelfFingerBreasts.class.getFields()),
				entry("SelfFingerCrotchNipple", SelfFingerCrotchNipple.class.getFields()),
				entry("SelfFingerMouth", SelfFingerMouth.class.getFields()),
				entry("SelfFingerNipple", SelfFingerNipple.class.getFields()),
				entry("SelfFingerPenis", SelfFingerPenis.class.getFields()),
				entry("SelfFingerVagina", SelfFingerVagina.class.getFields()),

				entry("SelfPenisAnus", SelfPenisAnus.class.getFields()),
				entry("SelfPenisMouth", SelfPenisMouth.class.getFields()),
				entry("SelfPenisNipple", SelfPenisNipple.class.getFields()),
				entry("SelfPenisVagina", SelfPenisVagina.class.getFields()),

				entry("SelfTailAnus", SelfTailAnus.class.getFields()),
				entry("SelfTailMouth", SelfTailMouth.class.getFields()),
				entry("SelfTailNipple", SelfTailNipple.class.getFields()),
				entry("SelfTailVagina", SelfTailVagina.class.getFields()),

				entry("SelfTongueAnus", SelfTongueAnus.class.getFields()),
				entry("SelfTongueMouth", SelfTongueMouth.class.getFields()),
				entry("SelfTongueNipple", SelfTongueNipple.class.getFields()),
				entry("SelfTongueVagina", SelfTongueVagina.class.getFields()));
				
		for(Entry<String, Field[]> entry : sexActionClassIdToFields.entrySet()) {
			for(Field f : entry.getValue()) {
				if (SexAction.class.isAssignableFrom(f.getType())) {
					SexAction sexAction;
					try {
						sexAction = ((SexAction) f.get(null));
						
						String id = entry.getKey()+"_"+f.getName();
						
						sexActionToIdMap.put(sexAction, id);
						idToSexActionMap.put(id, sexAction);
						
						allSexActions.add(sexAction);
						
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
