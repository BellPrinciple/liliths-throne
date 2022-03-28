package com.lilithsthrone.game.dialogue.encounters;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.lilithsthrone.game.character.npc.misc.NPCOffspring;
import com.lilithsthrone.game.character.npc.misc.OffspringSeed;
import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.Scene;
import com.lilithsthrone.game.character.EquipClothingSetting;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.dominion.EnforcerPatrol;
import com.lilithsthrone.game.character.persona.Occupation;
import com.lilithsthrone.game.dialogue.DialogueManager;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.AbstractCoreItem;
import com.lilithsthrone.game.occupantManagement.slave.SlaveJob;
import com.lilithsthrone.game.occupantManagement.slave.SlavePermissionSetting;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

import static java.util.stream.Collectors.toMap;

/**
 * @since 0.4
 * @version 0.4
 * @author Innoxia
 */
public class AbstractEncounter implements Encounter {

	String id;
	protected static AbstractCoreItem randomItem;
	
	protected static final double INCEST_ENCOUNTER_RATE = 0.2f;
	protected static double IncestEncounterRate() { return INCEST_ENCOUNTER_RATE; }

	private boolean mod;
	private boolean fromExternalFile;
	private String author;

	private List<String> placeTypeIds;
	private List<Mod> possibleEncounters;
	
	public AbstractEncounter() {
	}
	
	public AbstractEncounter(File XMLFile, String author, boolean mod) {
		if (XMLFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(XMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in AbstractEncounter files it's <encounter>
				
				this.mod = mod;
				this.fromExternalFile = true;
				this.author = author;
				
				this.placeTypeIds = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("additionalPlaceTypeTriggers").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("additionalPlaceTypeTriggers").getAllOf("placeType")) {
						this.placeTypeIds.add(e.getTextContent());
					}
				}
				
				var list = new ArrayList<Mod>();
				for(Element e : coreElement.getMandatoryFirstOf("possibleEncounters").getAllOf("encounter")) {
					String name = e.getMandatoryFirstOf("name").getTextContent();
					String chanceToTrigger = e.getMandatoryFirstOf("chanceToTrigger").getTextContent();
					boolean opportunistic = Boolean.valueOf(e.getMandatoryFirstOf("chanceToTrigger").getAttribute("opportunisticEncounter").trim());
					String dialogueId = e.getMandatoryFirstOf("dialogueReturned").getTextContent();
					list.add(new Mod(
							name,
							()->{
								try {
									return Double.parseDouble(UtilText.parse(chanceToTrigger).trim());
								}
								catch(Exception ex) {
									System.err.println("Error in AbstractEncounter's ExternalEncounterData: getTriggerChance() for '"+name+"' failed to parse!");
									ex.printStackTrace();
									return 0f;
							}},
							()->DialogueManager.getDialogueFromId(UtilText.parse(dialogueId).trim()),
							opportunistic));
				}
				if(!list.isEmpty())
					possibleEncounters = list;

			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("WorldType was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
			}
		}
	}

	public boolean isMod() {
		return mod;
	}

	public boolean isFromExternalFile() {
		return fromExternalFile;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Map<EncounterType,Float> getDialogues() {
		return possibleEncounters == null ? Map.of()
		: possibleEncounters.stream().collect(toMap(s->s,s->(float)s.chance.getAsDouble()));
	}

	protected static void spawnEnforcers() {
		List<List<String>> savedEnforcerIds = Main.game.getSavedEnforcers(WorldType.DOMINION);
		
		Main.game.getDialogueFlags().setSavedLong("enforcer_encounter_minutes", Main.game.getMinutesPassed());
		
		// Keep 4 sets of Enforcers saved
		float chanceOfNewEnforcers = 1f - (0.25f * savedEnforcerIds.size());
		if(Math.random()<chanceOfNewEnforcers) {
			try {
				List<String> enforcerIds = new ArrayList<>();
				EnforcerPatrol npc = new EnforcerPatrol(Occupation.NPC_ENFORCER_PATROL_CONSTABLE, Gender.getGenderFromUserPreferences(false, false));
				Main.game.addNPC(npc, false);
				npc.setLevel(9+Util.random.nextInt(4)); // 9-12
				((EnforcerPatrol)npc).setWeapons("dsg_eep_pbweap_pbpistol");
				enforcerIds.add(npc.getId());
				
				EnforcerPatrol npc2 = new EnforcerPatrol(Occupation.NPC_ENFORCER_PATROL_CONSTABLE, Gender.getGenderFromUserPreferences(false, false));
				Main.game.addNPC(npc2, false);
				npc2.setLevel(4+Util.random.nextInt(5)); // 4-8
				((EnforcerPatrol)npc2).setWeapons("dsg_eep_taser_taser");
				enforcerIds.add(npc2.getId());
				
				Main.game.addSavedEnforcers(WorldType.DOMINION, enforcerIds);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			List<String> enforcerIds = Util.randomItemFrom(savedEnforcerIds);
			for(String id : enforcerIds) {
				try {
					Main.game.getNPCById(id).setLocation(Main.game.getPlayer(), false);
				} catch (Exception e) {
					System.err.println("Error in Encounter.spawnEnforcers()");
					e.printStackTrace();
				}
			}
		}
	}
	
	protected static Scene SpawnAndStartChildHere(List<OffspringSeed> offspringAvailable)  {
		NPC offspring = new NPCOffspring(offspringAvailable.get(Util.random.nextInt(offspringAvailable.size())));

		offspring.setLocation(Main.game.getPlayer(), true);
		
		offspring.equipClothing(EquipClothingSetting.getAllClothingSettings());
		
		Main.game.setActiveNPC(offspring);

		return Main.game.getActiveNPC().getEncounterDialogue();
	}
	
	protected static NPC getSlaveWantingToUseYouInDominion() {
		List<NPC> slaves = new ArrayList<>();
		List<NPC> hornySlaves = new ArrayList<>();
		
		for(String id : Main.game.getPlayer().getSlavesOwned()) {
			try {
				NPC slave = (NPC) Main.game.getNPCById(id);
				if(slave.hasSlavePermissionSetting(SlavePermissionSetting.SEX_INITIATE_PLAYER)
						&& slave.getSlaveJob(Main.game.getHourOfDay())==SlaveJob.IDLE
						&& slave.getLocationPlace().getPlaceType()!=PlaceType.SLAVER_ALLEY_SLAVERY_ADMINISTRATION 
						&& slave.hasSlavePermissionSetting(SlavePermissionSetting.GENERAL_OUTSIDE_FREEDOM)
						&& (!Main.game.getPlayer().getLocationPlace().getPlaceType().isPopulated() || slave.hasFetish(Fetish.FETISH_EXHIBITIONIST))
						&& slave.isAttractedTo(Main.game.getPlayer())) {
					if(slave.getLastTimeHadSex()+60*4<Main.game.getMinutesPassed()) {
						slaves.add(slave);
					}
					if(slave.hasStatusEffect(StatusEffect.PENT_UP_SLAVE)) {
						hornySlaves.add(slave);
					}
				}
			} catch (Exception e) {
				System.err.println("Main.game.getNPCById("+id+") returning null in getSlaveWantingToUseYouInDominion()");
			}
		}
		
//		System.out.println(hornySlaves.size() +" | " + slaves.size());
		
		if(!hornySlaves.isEmpty()) {
			Collections.shuffle(hornySlaves);
			return hornySlaves.get(0);
			
		} else if(!slaves.isEmpty()) {
			Collections.shuffle(slaves);
			return slaves.get(0);
		}
		
		return null;
	}

	/**
	 * @return A Value, with the key being the dominant slave and the value being the submissive slave.
	 */
	protected static Value<NPC, NPC> getSlaveUsingOtherSlaveInDominion() {
		Map<NPC, List<NPC>> hornySlaves = new HashMap<>();
		
		for(String id : Main.game.getPlayer().getSlavesOwned()) {
			try {
				NPC slave = (NPC) Main.game.getNPCById(id);
				if(slave.hasSlavePermissionSetting(SlavePermissionSetting.SEX_INITIATE_SLAVES)
						&& slave.getSlaveJob(Main.game.getHourOfDay())==SlaveJob.IDLE
						&& slave.getLocationPlace().getPlaceType()!=PlaceType.SLAVER_ALLEY_SLAVERY_ADMINISTRATION
						&& slave.hasSlavePermissionSetting(SlavePermissionSetting.GENERAL_OUTSIDE_FREEDOM)) {
					if(slave.getLastTimeHadSex()+60*4<Main.game.getMinutesPassed()) {
						hornySlaves.put(slave, new ArrayList<>());
					}
				}
				
			} catch (Exception e) {
				System.err.println("Main.game.getNPCById("+id+") returning null in getSlaveUsingOtherSlaveInDominion() 1");
			}
		}

		for(String id : Main.game.getPlayer().getSlavesOwned()) {
			try {
				NPC slave = (NPC) Main.game.getNPCById(id);
				if(slave.hasSlavePermissionSetting(SlavePermissionSetting.SEX_RECEIVE_SLAVES)
						&& slave.getSlaveJob(Main.game.getHourOfDay())==SlaveJob.IDLE
						&& slave.getLocationPlace().getPlaceType()!=PlaceType.SLAVER_ALLEY_SLAVERY_ADMINISTRATION
						&& slave.hasSlavePermissionSetting(SlavePermissionSetting.GENERAL_OUTSIDE_FREEDOM)) {
					for(NPC horny : hornySlaves.keySet()) {
						if(!horny.equals(slave) && horny.isAttractedTo(slave)) {
							hornySlaves.get(horny).add(slave);
						}
					}
				}
				
			} catch (Exception e) {
				System.err.println("Main.game.getNPCById("+id+") returning null in getSlaveUsingOtherSlaveInDominion() 2");
			}
		}

		List<NPC> keys = new ArrayList<>(hornySlaves.keySet());
		for(NPC key : keys) {
			if(hornySlaves.get(key).isEmpty()) {
				hornySlaves.remove(key);
			}
		}
		
//		System.out.println(hornySlaves.size());

		keys = new ArrayList<>(hornySlaves.keySet());
		
		if(!hornySlaves.isEmpty()) {
			Collections.shuffle(keys);
			return new Value<>(keys.get(0), Util.randomItemFrom(hornySlaves.get(keys.get(0))));
		}
		
		return null;
	}

	/**
	 * @return A Value, with the key being the dominant slave and the value being the submissive slave.
	 */
	protected static Value<NPC, NPC> getSlaveUsingOtherSlaveInLilayaCorridor() {
		Map<NPC, List<NPC>> hornySlaves = new HashMap<>();
		
		for(String id : Main.game.getPlayer().getSlavesOwned()) {
			try {
				NPC slave = (NPC) Main.game.getNPCById(id);
				if(slave.hasSlavePermissionSetting(SlavePermissionSetting.SEX_INITIATE_SLAVES)
						&& ((slave.getSlaveJob(Main.game.getHourOfDay())==SlaveJob.IDLE
								&& !slave.getLocationPlace().getPlaceUpgrades().stream().anyMatch(upgrade->upgrade.getImmobilisationType()!=null)
								&& slave.hasSlavePermissionSetting(SlavePermissionSetting.GENERAL_HOUSE_FREEDOM))
							|| slave.getSlaveJob(Main.game.getHourOfDay())==SlaveJob.CLEANING)
						&& slave.getLocationPlace().getPlaceType()!=PlaceType.SLAVER_ALLEY_SLAVERY_ADMINISTRATION) {
					if(slave.getLastTimeHadSex()+60*4<Main.game.getMinutesPassed()) {
						hornySlaves.put(slave, new ArrayList<>());
					}
				}
				
			} catch (Exception e) {
				System.err.println("Main.game.getNPCById("+id+") returning null in getSlaveUsingOtherSlaveInLilayaCorridor() 1");
			}
		}

		for(String id : Main.game.getPlayer().getSlavesOwned()) {
			try {
				NPC slave = (NPC) Main.game.getNPCById(id);
				if(slave.hasSlavePermissionSetting(SlavePermissionSetting.SEX_RECEIVE_SLAVES)
						&& ((slave.getSlaveJob(Main.game.getHourOfDay())==SlaveJob.IDLE
								&& !slave.getLocationPlace().getPlaceUpgrades().stream().anyMatch(upgrade->upgrade.getImmobilisationType()!=null)
								&& slave.hasSlavePermissionSetting(SlavePermissionSetting.GENERAL_HOUSE_FREEDOM))
							|| slave.getSlaveJob(Main.game.getHourOfDay())==SlaveJob.CLEANING)
						&& slave.getLocationPlace().getPlaceType()!=PlaceType.SLAVER_ALLEY_SLAVERY_ADMINISTRATION) {
					for(NPC horny : hornySlaves.keySet()) {
						if(!horny.equals(slave) && horny.isAttractedTo(slave)) {
							hornySlaves.get(horny).add(slave);
						}
					}
				}
				
			} catch (Exception e) {
				System.err.println("Main.game.getNPCById("+id+") returning null in getSlaveUsingOtherSlaveInLilayaCorridor() 2");
			}
		}

		List<NPC> keys = new ArrayList<>(hornySlaves.keySet());
		for(NPC key : keys) {
			if(hornySlaves.get(key).isEmpty()) {
				hornySlaves.remove(key);
			}
		}
		
		keys = new ArrayList<>(hornySlaves.keySet());
		
		if(!hornySlaves.isEmpty()) {
			Collections.shuffle(keys);
			return new Value<>(keys.get(0), Util.randomItemFrom(hornySlaves.get(keys.get(0))));
		}
		
		return null;
	}
	
	public static AbstractCoreItem getRandomItem() {
		return randomItem;
	}

	@Override
	public List<String> getPlaceTypeIds() {
		return placeTypeIds;
	}

	@Override
	public void add(Object k, DoubleSupplier c, Supplier<Scene> v, boolean o) {
		if(possibleEncounters == null)
			possibleEncounters = new ArrayList<>();
		possibleEncounters.add(new Mod(k,c,v,o));
	}

	@Override
	public void remove(Object k) {
		possibleEncounters.removeIf(m->k.equals(m.key));
		if(possibleEncounters.isEmpty())
			possibleEncounters = null;
	}
}
