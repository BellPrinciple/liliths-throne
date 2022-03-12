package com.lilithsthrone.game.dialogue;

import java.io.File;
import java.util.*;
import java.util.function.Supplier;

import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseCombat;
import com.lilithsthrone.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithsthrone.game.dialogue.responses.ResponseSex;
import com.lilithsthrone.game.dialogue.responses.ResponseTag;
import com.lilithsthrone.game.dialogue.responses.ResponseTrade;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.InitialSexActionInformation;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;

import static com.lilithsthrone.controller.MainController.RESPONSE_COUNT;
import static java.util.stream.IntStream.range;

/**
 * @since 0.1.0
 * @version 0.4.1
 * @author Innoxia
 */
public abstract class DialogueNode {

	private boolean mod;
	private boolean fromExternalFile;
	private String author;
	
	private String id;
	
	private String label;
	private String description;
	
	private int secondsPassed;
	
	private boolean travelDisabled;
	private boolean continuesDialogue;

	private LinkedHashMap<Object,Mod> mods;
	private static final String DEFAULT_TAB_TITLE = "Default";

	public DialogueNode(String label, String description, boolean travelDisabled) {
		this(label, description, travelDisabled, false);
	}

	public DialogueNode(String label, String description, boolean travelDisabled, boolean continuesDialogue) {
		this.author = "Innoxia";
		
		this.label = label;
		this.description = description;
		
		this.secondsPassed = 0;
		
		this.travelDisabled = travelDisabled;
		this.continuesDialogue = continuesDialogue;
	}

	public static List<DialogueNode> loadDialogueNodesFromFile(String idPrefix, File XMLFile, String author, boolean mod) {
		if (XMLFile.exists()) {
			List<DialogueNode> loadedNodes = new ArrayList<>();
			
			try {
				Document doc = Main.getDocBuilder().parse(XMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in DialogueNode files it's <dialogueNodes>

				for(Element node : coreElement.getAllOf("scene")) {
					
					String idTag = node.getAttribute("id");
					
					boolean isMod = mod;
					
					String getAuthor = Util.capitaliseSentence(author); // folder name
					if(node.getOptionalFirstOf("author").isPresent()) {
						getAuthor = node.getMandatoryFirstOf("author").getTextContent();
					}
					// Thanks, Java!
					String finalAuthor = getAuthor;

					String title = "";
					if(node.getOptionalFirstOf("title").isPresent()) {
						title = node.getMandatoryFirstOf("title").getTextContent();
					}
					
					String tooltip = "";
					if(node.getOptionalFirstOf("tooltip").isPresent()) {
						tooltip = node.getMandatoryFirstOf("tooltip").getTextContent();
					}
					
					String preParsingEffects = "";
					if(node.getOptionalFirstOf("preParsingEffects").isPresent()) {
						preParsingEffects = node.getMandatoryFirstOf("preParsingEffects").getTextContent();
					}
					// Thanks, Java!
					String finalPreParsingEffects = preParsingEffects;

					String folderPath = "";
					String dialogueTag = "";
					if(node.getOptionalFirstOf("content").isPresent()) {
						folderPath = node.getMandatoryFirstOf("content").getAttribute("folderPath");//.replaceAll("/", System.getProperty("file.separator"));
						dialogueTag = node.getMandatoryFirstOf("content").getTextContent();
					}
					// Thanks, Java!
					String finalFolderPath = folderPath;
					String finalDialogueTag = dialogueTag;
					
					
					String secondsPassed = node.getMandatoryFirstOf("secondsPassed").getTextContent().trim();
					boolean minutes = Boolean.valueOf(node.getMandatoryFirstOf("secondsPassed").getAttribute("minutes"));
					
					String continuesDialogue = "false";
					if(node.getOptionalFirstOf("continuesDialogue").isPresent()) {
						continuesDialogue = node.getMandatoryFirstOf("continuesDialogue").getTextContent();
					}
					// Thanks, Java!
					String finalContinuesDialogue = continuesDialogue;
					
					String travelDisabled = "false";
					if(node.getOptionalFirstOf("travelDisabled").isPresent()) {
						travelDisabled = node.getMandatoryFirstOf("travelDisabled").getTextContent();
					}
					// Thanks, Java!
					String finalTravelDisabled = travelDisabled;
					
					String inventoryDisabled = "";
					if(node.getOptionalFirstOf("inventoryDisabled").isPresent()) {
						inventoryDisabled = node.getMandatoryFirstOf("inventoryDisabled").getTextContent();
					}
					// Thanks, Java!
					String finalInventoryDisabled = inventoryDisabled;

					String regenerationDisabled = "";
					if(node.getOptionalFirstOf("regenerationDisabled").isPresent()) {
						regenerationDisabled = node.getMandatoryFirstOf("regenerationDisabled").getTextContent();
					}
					// Thanks, Java!
					String finalRegenerationDisabled = regenerationDisabled;
					
					// Response tabs:
					
					Map<Integer, String> responseTabs = new HashMap<>();
					if(node.getOptionalFirstOf("responseTabs").isPresent()) {
						for(Element tab : node.getMandatoryFirstOf("responseTabs").getAllOf("tab")) {
							responseTabs.put(
									Integer.valueOf(tab.getAttribute("index").trim()),
									tab.getTextContent());
						}
					}
					
					
					// Responses:
					
					String copyFromDialogueId = "";
					Map<Integer, Map<String, List<Response>>> loadedResponses = new HashMap<>();
					if(node.getOptionalFirstOf("responses").isPresent()) {
						if(!node.getMandatoryFirstOf("responses").getAttribute("copyFromDialogueId").isEmpty()) {
							copyFromDialogueId = node.getMandatoryFirstOf("responses").getAttribute("copyFromDialogueId");
						}
//						} else {
							for(Element response : node.getMandatoryFirstOf("responses").getAllOf("response")) {
								String availabilityConditional = "true";
								if(response.getOptionalFirstOf("availabilityConditional").isPresent()) {
									availabilityConditional = response.getMandatoryFirstOf("availabilityConditional").getTextContent();
								}
								
								int responseTabIndex = Integer.valueOf(response.getMandatoryFirstOf("responseTabIndex").getTextContent());
								String index = response.getMandatoryFirstOf("index").getTextContent();
	
								String responseTitle = response.getMandatoryFirstOf("responseTitle").getTextContent();
								String responseTooltip = response.getMandatoryFirstOf("responseTooltip").getTextContent();
								
								String nextDialogueId = "";
								String defaultPlaceTypeForNextDialogue = "";
								boolean stripContentForNextDialogue = false;
								boolean forceContinueForNextDialogue = false;
								if(response.getOptionalFirstOf("nextDialogue").isPresent()) {
									nextDialogueId = response.getMandatoryFirstOf("nextDialogue").getTextContent();
									defaultPlaceTypeForNextDialogue = response.getMandatoryFirstOf("nextDialogue").getAttribute("defaultPlaceType");
									stripContentForNextDialogue = Boolean.valueOf(response.getMandatoryFirstOf("nextDialogue").getAttribute("stripContent"));
									forceContinueForNextDialogue = Boolean.valueOf(response.getMandatoryFirstOf("nextDialogue").getAttribute("forceContinue"));
								}
								// Thanks, Java!
								String finalDefaultPlaceTypeForNextDialogue = defaultPlaceTypeForNextDialogue;
								
								String colourResponse = "";
								if(response.getOptionalFirstOf("colour").isPresent()) {
									colourResponse = response.getMandatoryFirstOf("colour").getTextContent();
								}
								
								String secondsPassedResponse = "";
								boolean asMinutes = false;
								if(response.getOptionalFirstOf("secondsPassed").isPresent()) {
									secondsPassedResponse = response.getMandatoryFirstOf("secondsPassed").getTextContent();
									asMinutes = Boolean.valueOf(response.getMandatoryFirstOf("secondsPassed").getAttribute("minutes"));
								}
								
								String effectsResponse = "";
								if(response.getOptionalFirstOf("effects").isPresent()) {
									effectsResponse = response.getMandatoryFirstOf("effects").getTextContent();
								}
								
								List<String> requiredFetishes = new ArrayList<>();
								if(response.getOptionalFirstOf("requiredFetishes").isPresent()) {
									for(Element fetish : response.getMandatoryFirstOf("requiredFetishes").getAllOf("fetish")) {
										requiredFetishes.add(fetish.getTextContent());
									}
								}
	
								String corruptionLevel = "";
								if(response.getOptionalFirstOf("corruptionLevel").isPresent()) {
									corruptionLevel = response.getMandatoryFirstOf("corruptionLevel").getTextContent();
								}
	
								String requiredFemininity = "";
								if(response.getOptionalFirstOf("requiredFemininity").isPresent()) {
									requiredFemininity = response.getMandatoryFirstOf("requiredFemininity").getTextContent();
								}
								
								
								List<String> requiredPerks = new ArrayList<>();
								if(response.getOptionalFirstOf("requiredPerks").isPresent()) {
									for(Element perk : response.getMandatoryFirstOf("requiredPerks").getAllOf("perk")) {
										requiredPerks.add(perk.getTextContent());
									}
								}
	
								List<String> subspeciesRequired = new ArrayList<>();
								if(response.getOptionalFirstOf("requiredSubspecies").isPresent()) {
									for(Element subspecies : response.getMandatoryFirstOf("requiredSubspecies").getAllOf("subspecies")) {
										subspeciesRequired.add(subspecies.getTextContent());
									}
								}
								
								
								// Trading:
								if(response.getOptionalFirstOf("tradingVariables").isPresent() && Boolean.valueOf(response.getMandatoryFirstOf("tradingVariables").getAttribute("enabled"))) {
									String tradeTarget = response.getMandatoryFirstOf("tradingVariables").getMandatoryFirstOf("tradePartner").getTextContent();
									ResponseTrade tradeResponse = new ResponseTrade(responseTitle, responseTooltip, tradeTarget, effectsResponse);
									tradeResponse.setConditional(availabilityConditional);
									
									loadedResponses.putIfAbsent(responseTabIndex, new HashMap<>());
									loadedResponses.get(responseTabIndex).putIfAbsent(index, new ArrayList<>());
									loadedResponses.get(responseTabIndex).get(index).add(tradeResponse);
								}
								
								// Combat:
								else if(response.getOptionalFirstOf("combatVariables").isPresent() && Boolean.valueOf(response.getMandatoryFirstOf("combatVariables").getAttribute("enabled"))) {
									Element combatElement = response.getMandatoryFirstOf("combatVariables");
									
									String nextDialoguePlayerVictory = combatElement.getMandatoryFirstOf("nextDialoguePlayerVictory").getTextContent();
									String nextDialoguePlayerDefeat = combatElement.getMandatoryFirstOf("nextDialoguePlayerDefeat").getTextContent();
									
									List<String> alliesIds = new ArrayList<>();
									boolean companionsAreAllies = false;
									if(combatElement.getOptionalFirstOf("allies").isPresent()) {
										companionsAreAllies = Boolean.valueOf(combatElement.getMandatoryFirstOf("allies").getAttribute("companionsAreAllies"));
										for(Element ally : combatElement.getMandatoryFirstOf("allies").getAllOf("ally")) {
											alliesIds.add(ally.getTextContent());
										}
									}
	
									List<String> enemiesIds = new ArrayList<>();
									String enemyLeaderId = null;
									if(combatElement.getOptionalFirstOf("enemies").isPresent()) {
										for(Element enemy : combatElement.getMandatoryFirstOf("enemies").getAllOf("enemy")) {
											String enemyCharacter = enemy.getTextContent();
											if(Boolean.valueOf(enemy.getAttribute("leader"))) {
												enemyLeaderId = enemyCharacter;
											}
											enemiesIds.add(enemyCharacter);
										}
									}
									if(enemyLeaderId==null) {
										enemyLeaderId = enemiesIds.get(0);
									}
									
									Map<String, String> openingDescriptions = new HashMap<>();
									if(combatElement.getOptionalFirstOf("openingDescriptions").isPresent()) {
										for(Element combatant : combatElement.getMandatoryFirstOf("openingDescriptions").getAllOf("combatant")) {
											openingDescriptions.put(combatant.getAttribute("id").trim(), combatant.getTextContent());
										}
									}
									
									ResponseCombat combatResponse = new ResponseCombat(responseTitle, responseTooltip, alliesIds, companionsAreAllies, enemyLeaderId, enemiesIds, openingDescriptions, effectsResponse);
									combatResponse.setNextDialoguePlayerVictoryId(nextDialoguePlayerVictory);
									combatResponse.setNextDialoguePlayerDefeatId(nextDialoguePlayerDefeat);
									combatResponse.setConditional(availabilityConditional);
									
									loadedResponses.putIfAbsent(responseTabIndex, new HashMap<>());
									loadedResponses.get(responseTabIndex).putIfAbsent(index, new ArrayList<>());
									loadedResponses.get(responseTabIndex).get(index).add(combatResponse);
								}
								
								// Sex:
								else if(response.getOptionalFirstOf("sexVariables").isPresent() && Boolean.valueOf(response.getMandatoryFirstOf("sexVariables").getAttribute("enabled"))) {
									Element sexElement = response.getMandatoryFirstOf("sexVariables");
									
									String consensual = sexElement.getMandatoryFirstOf("consensual").getTextContent();
									String subHasEqualControl = sexElement.getMandatoryFirstOf("subHasEqualControl").getTextContent();
									
									List<String> dominantIds = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("dominants").isPresent()) {
										for(Element character : sexElement.getMandatoryFirstOf("dominants").getAllOf("character")) {
											dominantIds.add(character.getTextContent());
										}
									}
	
									List<String> submissiveIds = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("submissives").isPresent()) {
										for(Element character : sexElement.getMandatoryFirstOf("submissives").getAllOf("character")) {
											submissiveIds.add(character.getTextContent());
										}
									}
	
									List<String> dominantSpectatorIds = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("dominantSpectators").isPresent()) {
										for(Element character : sexElement.getMandatoryFirstOf("dominantSpectators").getAllOf("character")) {
											dominantSpectatorIds.add(character.getTextContent());
										}
									}
	
									List<String> submissiveSpectatorIds = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("submissiveSpectators").isPresent()) {
										for(Element character : sexElement.getMandatoryFirstOf("submissiveSpectators").getAllOf("character")) {
											submissiveSpectatorIds.add(character.getTextContent());
										}
									}
									
									String postSexDialogueId = sexElement.getMandatoryFirstOf("postSexDialogue").getTextContent();
									
	
									String folderPathSex = "";
									String dialogueTagSex = "";
									if(sexElement.getOptionalFirstOf("sexStartContent").isPresent()) {
										folderPathSex = sexElement.getMandatoryFirstOf("sexStartContent").getAttribute("folderPath");//.replaceAll("/", System.getProperty("file.separator"));
										dialogueTagSex = sexElement.getMandatoryFirstOf("sexStartContent").getTextContent();
									}
	
									List<ResponseTag> tags = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("tags").isPresent()) {
										for(Element tagElement : sexElement.getMandatoryFirstOf("tags").getAllOf("tag")) {
											tags.add(ResponseTag.valueOf(tagElement.getTextContent()));
										}
									}
									
									List<InitialSexActionInformation> initialActions = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("ongoingActionsAtStart").isPresent()) {
										for(Element actionElement : sexElement.getMandatoryFirstOf("ongoingActionsAtStart").getAllOf("action")) {
											initialActions.add(
													new InitialSexActionInformation(
															actionElement.getMandatoryFirstOf("condition").getTextContent(),
															actionElement.getMandatoryFirstOf("performer").getTextContent(),
															actionElement.getMandatoryFirstOf("target").getTextContent(),
															actionElement.getMandatoryFirstOf("id").getTextContent(),
															actionElement.getMandatoryFirstOf("showDescription").getTextContent(),
															actionElement.getMandatoryFirstOf("showEffects").getTextContent()));
										}
									}
									
									ResponseSex sexResponse = new ResponseSex(responseTitle,
											responseTooltip,
											secondsPassedResponse,
											asMinutes,
											colourResponse,
											effectsResponse,
											requiredFetishes,
											corruptionLevel,
											requiredPerks,
											requiredFemininity,
											subspeciesRequired,
											consensual,
											subHasEqualControl,
											dominantIds,
											submissiveIds,
											dominantSpectatorIds,
											submissiveSpectatorIds,
											postSexDialogueId,
											folderPathSex,
											dialogueTagSex,
											tags) {
										@Override
										public List<InitialSexActionInformation> getInitialSexActions() {
											return initialActions;
										}
									};
									sexResponse.setConditional(availabilityConditional);
									
									loadedResponses.putIfAbsent(responseTabIndex, new HashMap<>());
									loadedResponses.get(responseTabIndex).putIfAbsent(index, new ArrayList<>());
									loadedResponses.get(responseTabIndex).get(index).add(sexResponse);
								}
								
								// Sex with manager:
								else if(response.getOptionalFirstOf("sexVariablesWithManager").isPresent() && Boolean.valueOf(response.getMandatoryFirstOf("sexVariablesWithManager").getAttribute("enabled"))) {
									Element sexElement = response.getMandatoryFirstOf("sexVariablesWithManager");
									
									String sexManagerId = sexElement.getMandatoryFirstOf("id").getTextContent();
									String sexPositionId = sexElement.getMandatoryFirstOf("startingPosition").getTextContent();
									
									Map<String, String> dominantPositionIds = new HashMap<>();
									if(sexElement.getOptionalFirstOf("dominants").isPresent()) {
										for(Element character : sexElement.getMandatoryFirstOf("dominants").getAllOf("character")) {
											dominantPositionIds.put(character.getMandatoryFirstOf("id").getTextContent(), character.getMandatoryFirstOf("slot").getTextContent());
										}
									}
	
									Map<String, String> submissivePositionIds = new HashMap<>();
									if(sexElement.getOptionalFirstOf("submissives").isPresent()) {
										for(Element character : sexElement.getMandatoryFirstOf("submissives").getAllOf("character")) {
											submissivePositionIds.put(character.getMandatoryFirstOf("id").getTextContent(), character.getMandatoryFirstOf("slot").getTextContent());
										}
									}
	
									List<String> dominantSpectatorIds = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("dominantSpectators").isPresent()) {
										for(Element character : sexElement.getMandatoryFirstOf("dominantSpectators").getAllOf("character")) {
											dominantSpectatorIds.add(character.getTextContent());
										}
									}
	
									List<String> submissiveSpectatorIds = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("submissiveSpectators").isPresent()) {
										for(Element character : sexElement.getMandatoryFirstOf("submissiveSpectators").getAllOf("character")) {
											submissiveSpectatorIds.add(character.getTextContent());
										}
									}
									
									String postSexDialogueId = sexElement.getMandatoryFirstOf("postSexDialogue").getTextContent();
									
	
									String folderPathSex = "";
									String dialogueTagSex = "";
									if(sexElement.getOptionalFirstOf("sexStartContent").isPresent()) {
										folderPathSex = sexElement.getMandatoryFirstOf("sexStartContent").getAttribute("folderPath");//.replaceAll("/", System.getProperty("file.separator"));
										dialogueTagSex = sexElement.getMandatoryFirstOf("sexStartContent").getTextContent();
									}
	
									List<ResponseTag> tags = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("tags").isPresent()) {
										for(Element tagElement : sexElement.getMandatoryFirstOf("tags").getAllOf("tag")) {
											tags.add(ResponseTag.valueOf(tagElement.getTextContent()));
										}
									}
									
									List<InitialSexActionInformation> initialActions = new ArrayList<>();
									if(sexElement.getOptionalFirstOf("ongoingActionsAtStart").isPresent()) {
										for(Element actionElement : sexElement.getMandatoryFirstOf("ongoingActionsAtStart").getAllOf("action")) {
											initialActions.add(
													new InitialSexActionInformation(
															actionElement.getOptionalFirstOf("condition").isPresent() && !actionElement.getMandatoryFirstOf("condition").getTextContent().isEmpty()
																?actionElement.getMandatoryFirstOf("condition").getTextContent()
																:"true",
															actionElement.getMandatoryFirstOf("performer").getTextContent(),
															actionElement.getMandatoryFirstOf("target").getTextContent(),
															actionElement.getMandatoryFirstOf("id").getTextContent(),
															actionElement.getOptionalFirstOf("showDescription").isPresent() && !actionElement.getMandatoryFirstOf("showDescription").getTextContent().isEmpty()
																?actionElement.getMandatoryFirstOf("showDescription").getTextContent()
																:"true",
															actionElement.getOptionalFirstOf("showEffects").isPresent() && !actionElement.getMandatoryFirstOf("showEffects").getTextContent().isEmpty()
																?actionElement.getMandatoryFirstOf("showEffects").getTextContent()
																:"true"));
										}
									}
									
									ResponseSex sexResponse = new ResponseSex(responseTitle,
											responseTooltip,
											secondsPassedResponse,
											asMinutes,
											colourResponse,
											effectsResponse,
											requiredFetishes,
											corruptionLevel,
											requiredPerks,
											requiredFemininity,
											subspeciesRequired,
											sexManagerId,
											sexPositionId,
											dominantPositionIds,
											submissivePositionIds,
											dominantSpectatorIds,
											submissiveSpectatorIds,
											postSexDialogueId,
											folderPathSex,
											dialogueTagSex) {
										@Override
										public List<InitialSexActionInformation> getInitialSexActions() {
											return initialActions;
										}
									};
									sexResponse.setConditional(availabilityConditional);
									
									loadedResponses.putIfAbsent(responseTabIndex, new HashMap<>());
									loadedResponses.get(responseTabIndex).putIfAbsent(index, new ArrayList<>());
									loadedResponses.get(responseTabIndex).get(index).add(sexResponse);
								}
								
								// NoEffects response:
								else if(nextDialogueId.isEmpty() && finalDefaultPlaceTypeForNextDialogue.isEmpty()) {
									Response onlyEffectsResponse = new ResponseEffectsOnly(responseTitle,
											responseTooltip,
											secondsPassedResponse,
											asMinutes,
											colourResponse,
											effectsResponse,
											requiredFetishes,
											corruptionLevel,
											requiredPerks,
											requiredFemininity,
											subspeciesRequired);
									onlyEffectsResponse.setConditional(availabilityConditional);
									
									loadedResponses.putIfAbsent(responseTabIndex, new HashMap<>());
									loadedResponses.get(responseTabIndex).putIfAbsent(index, new ArrayList<>());
									loadedResponses.get(responseTabIndex).get(index).add(onlyEffectsResponse);
								
								// Normal response:
								} else {
									Response standardResponse = new Response(responseTitle,
											responseTooltip,
											nextDialogueId,
											secondsPassedResponse,
											asMinutes,
											colourResponse,
											effectsResponse,
											requiredFetishes,
											corruptionLevel,
											requiredPerks,
											requiredFemininity,
											subspeciesRequired) {
										@Override
										public String getDefaultPlaceTypeForNextDialogue() {
											return finalDefaultPlaceTypeForNextDialogue;
										}
									};
									standardResponse.setConditional(availabilityConditional);
									standardResponse.setStripContent(stripContentForNextDialogue);
									standardResponse.setForceContinue(forceContinueForNextDialogue);
									
									loadedResponses.putIfAbsent(responseTabIndex, new HashMap<>());
									loadedResponses.get(responseTabIndex).putIfAbsent(index, new ArrayList<>());
									loadedResponses.get(responseTabIndex).get(index).add(standardResponse);
								}
							}
//						}
					}
					String copyFromDialogueFinalThanksJava = copyFromDialogueId;
					
					DialogueNode newNode = new DialogueNode(title, tooltip, false) {
						@Override
						public boolean isMod() {
							return isMod;
						}
						@Override
						public boolean isFromExternalFile() {
							return true;
						}
						@Override
						public String getId() {
							return idPrefix + "_" + idTag;
						}
						@Override
						public String getAuthor() {
							return finalAuthor;
						}
						@Override
						public void applyPreParsingEffects() {
							UtilText.parse(finalPreParsingEffects);
						}
						@Override
						public int getSecondsPassed() {
							return Integer.valueOf(UtilText.parse(secondsPassed).trim())* (minutes?60 : 1);
						}
						@Override
						public String getContent() {
							if(finalFolderPath.isEmpty() || finalDialogueTag.isEmpty()) {
								return "";
							}
							return UtilText.parseFromXMLFile(new ArrayList<>(), "res", finalFolderPath, finalDialogueTag, new ArrayList<>());
						}
						@Override
						protected List<ResponseTab> responses() {
							var failback = DialogueManager.getDialogueFromId(copyFromDialogueFinalThanksJava).getResponses();
							var cap = Math.max(failback.size(),responseTabs.keySet().stream().mapToInt(i->i).max().orElse(1));
							var result = new ResponseTab[cap];
							for(var e : responseTabs.entrySet())
								result[e.getKey()] = new ResponseTab(e.getValue());
							for(int i = 0; i < failback.size(); i++)
								if(result[i] == null)
									result[i] = failback.get(i);
							for(var e : loadedResponses.entrySet()) {
								var r = result[e.getKey()];
								for(var entry : e.getValue().entrySet()) {
									int parsedIndex = Integer.valueOf(UtilText.parse(entry.getKey()).trim());
									for(var response : entry.getValue()) {
										if(!response.isAvailableFromConditional())
											continue;
										r.set(parsedIndex,response);
									}
								}
							}
							return List.of(result);
						}
						@Override
						public boolean isContinuesDialogue() {
							return Boolean.valueOf(UtilText.parse(finalContinuesDialogue).trim());
						}
						@Override
						public boolean isTravelDisabled() {
							return Boolean.valueOf(UtilText.parse(finalTravelDisabled).trim());
						}
						@Override
						public boolean isInventoryDisabled() {
							return isTravelDisabled() || Boolean.valueOf(UtilText.parse(finalInventoryDisabled).trim());
						}
						@Override
						public boolean isRegenerationDisabled() {
							return Boolean.valueOf(UtilText.parse(finalRegenerationDisabled).trim());
						}
					};
					
					loadedNodes.add(newNode);
				}
				
				return loadedNodes;
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("DialogueNode was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
				return null;
			}
		}

		System.err.println("DialogueNode file does not exist! (" + XMLFile.getName() + ")");
		return null;
	}
	
	public boolean isMod() {
		return mod;
	}

	public boolean isFromExternalFile() {
		return fromExternalFile;
	}

	public String getId() {
		return id;
	}

	/**
	 * If this DialogueNode is accessed through a Response, then this method may be overwritten by the Response's own getSecondsPassed method.
	 *  Due to this, it cannot be guaranteed that this method is used when determining how much time passes in a scene.
	 * 
	 * @return The number of seconds that pass when entering into this dialogue node.
	 */
	public int getSecondsPassed() {
		return secondsPassed;
	}

	/**
	 * Content that is set above the main content. It is not affected by fade-in effects.<br/>
	 * <b>Append to Main.game.getTextStartStringBuilder() instead of using this!</b>
	 */
//	@Deprecated
	public String getHeaderContent() {
		return null;
	}

	/** The main content for this DialogueNode. If enabled in the options, it is affected by fade-in effects. */
	public abstract String getContent();

	@Override
	public String toString() {
		return label + ":" + description.substring(0, description.length() <= 20 ? description.length() : 20);
	}
	
	/**
	 * @return The label for the button proceeding to this dialogue node.<br/>
	 * <b>Note:</b> In almost all instances, this is overridden by the Response class's getTitle() method.
	 */
	public String getLabel() {
		if(label==null || label.isEmpty()) {
			try {
				return Main.game.getPlayerCell().getPlace().getName();
			} catch(Exception ex) {
			}
		}
		return label;
	}

	public DialogueNode setLabel(String label) {
		this.label = label;
		return this;
	}
	
	/**
	 * @return The description for the action proceeding to this dialogue node, to be displayed in the action tooltip.<br/>
	 * <b>Note:</b> In almost all instances, this is overridden by the Response class's getTooltipText() method.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return True if this dialogue node should append to the current dialogue, instead of clearing the screen and displaying it as a new scene.
	 */
	public boolean isContinuesDialogue() {
		return continuesDialogue;
	}

	/**
	 * @return True if this dialogue node disables map movement.
	 */
	public boolean isTravelDisabled() {
		return travelDisabled;
	}

	/**
	 * @return True if this dialogue node disables inventory management. By default, this is disabled if {@code isTravelDisabled()} returns true.
	 */
	public boolean isInventoryDisabled() {
		return isTravelDisabled();
	}

	/**
	 * @return True if health and aura regeneration is disabled in this dialogue node.
	 */
	public boolean isRegenerationDisabled() {
		return false;
	}
	
	/**
	 * @return True if this dialogue should display the actions title as part of a continuation of the scene, in this format:<br/><br/>
	 * <i>...sunt in culpa qui officia deserunt mollit anim id est laborum.<br/>
	 * <hr/>
	 * > Accept<br/>
	 * Lorem ipsum dolor sit amet, consectetur adipiscing elit...</i>
	 */
	public boolean isDisplaysActionTitleOnContinuesDialogue() {
		return true;
	}
	
	/**
	 * Index starts at 0.
	 * @return The title to be displayed on the response tab. Only indices that are defined as returning a title are displayed, so just return null as the fallback option.
	 * @deprecated
	 * Use or implement {@link #responses()} instead.
	 */
	@Deprecated
	public String getResponseTabTitle(int index) {
		return null;
	}
	
	/**
	 * The responses that lead on from this dialogue node.
	 * @param responseTab The tab in which the response is to be found.
	 * @param index The index of the response.
	 * @return A response, if a response is returned at the specified responseTab & index, or, if not, then null.
	 * @deprecated
	 * Use {@link #getResponses()} and implement {@link #responses()} instead.
	 */
	@Deprecated
	public Response getResponse(int responseTab, int index) {
		return null;
	}

	/**
	 * Collects available responses of the player to this node.
	 * @return
	 * Collection of responses, grouped into titled tabs.
	 */
	public final List<ResponseTab> getResponses() {
		var r = responses();
		for(Mod m : mods==null ? List.<Mod>of() : mods.values()) {
			var n = m.response.get();
			var t = r.stream()
			.filter(s->m.tab.equals(s.title))
			.findAny();
			if(t.isEmpty()) {
				ResponseTab tab = new ResponseTab(m.tab,n);
				r.add(tab);
				continue;
			}
			var a = t.get().response;
			range(0,a.size())
			.filter(i->a.get(i)==null)
			.findFirst()
			.ifPresentOrElse(i->a.set(i,n),()->a.add(n));
		}
		return r;
	}

	/**
	 * Collects available responses of the player to this node.
	 * Override this or {@link #responseTab()}.
	 * This method is to be called only by {@link #getResponses()} or overriders.
	 * @return
	 * Collection of responses, grouped into titled tabs.
	 * @see #getResponses()
	 */
	protected List<ResponseTab> responses() {
		var r0 = responseTab();
		if(r0 != null)
			return List.of(new ResponseTab(DEFAULT_TAB_TITLE,r0));
		var t0 = getResponseTabTitle(0);
		var a0 = new LinkedList<Response>();
		int e = RESPONSE_COUNT;
		for(int j = 0; j < e; j++) {
			var c = getResponse(0,j);
			if(c != null && j + RESPONSE_COUNT >= e)
				e += RESPONSE_COUNT;
			a0.add(c);
		}
		var b0 = a0.toArray(Response[]::new);
		if(t0 == null)
			return List.of(new ResponseTab(DEFAULT_TAB_TITLE,b0));
		var r = new LinkedList<ResponseTab>();
		r.add(new ResponseTab(t0,b0));
		for(int i = 1;; i++) {
			var t = getResponseTabTitle(i);
			if(t == null)
				break;
			var a = new LinkedList<Response>();
			e = RESPONSE_COUNT;
			for(int j = 0; j < e; j++) {
				var c = getResponse(i,j);
				if(c != null && j + RESPONSE_COUNT >= e)
					e += RESPONSE_COUNT;
				a.add(c);
			}
			r.add(new ResponseTab(t,a.toArray(Response[]::new)));
		}
		return r;
	}

	/**
	 * Convenience method for when this node defines only one response tab.
	 */
	protected Response[] responseTab() {
		return null;
	}

	/**
	 * @return The type of dialogue that this is. Will almost always be {@code DialogueNodeType.NORMAL}.
	 */
	public DialogueNodeType getDialogueNodeType() {
		return DialogueNodeType.NORMAL;
	}

	/**
	 * @return Whether the content of the dialogue should run through the parser. Will almost always be {@code true}.
	 */
	public boolean isContentParsed() {
		return true;
	}

	/**
	 * @return The author of the scene.
	 */
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return True if the header should not be run through the parsing engine.
	 */
	public boolean disableHeaderParsing() {
		return false;
	}

	/**
	 * @return True if this dialogue node should be re-run through the parser when it is restored (i.e. loaded from a saved dialogue node).
	 */
	public boolean reloadOnRestore() {
		return false;
	}
	
	/**
	 * This method is called before the getContent() method is called.
	 */
	public void applyPreParsingEffects() {
	}
	
	public final void specialPreParsingEffects() {
		if(Main.game.isStarted()) {
			Main.game.getDialogueFlags().setFlag(DialogueFlagValue.coveringChangeListenersRequired, false);
		}
	}

	/**
	 * @param key
	 * Identifies the entry for removal.
	 * @param tab
	 * Index of the tab to add the created response.
	 * @param value
	 * Produces a response anytime if needed.
	 */
	public final void addResponse(Object key, String tab, Supplier<Response> value) {
		if(mods == null)
			mods = new LinkedHashMap<>();
		Object old = mods.putIfAbsent(key,new Mod(tab,value));
		assert old == null;
	}

	/**
	 * @param key
	 * Used in {@link #addResponse(Object,String,Supplier)}.
	 */
	public final void removeResponse(Object key) {
		Object old = mods.remove(key);
		assert old != null;
		if(mods.isEmpty())
			mods = null;
	}

	public static final class ResponseTab {
		public final String title;
		public final ArrayList<Response> response = new ArrayList<>();
		public ResponseTab(String t, Response... r) {
			title = Objects.requireNonNull(t);
			response.addAll(List.of(r));
		}
		public void set(int i, Response r) {
			if(i < response.size()) {
				response.set(i,r);
				return;
			}
			while(response.size() < i)
				response.add(null);
			response.add(r);
		}
	}

	private static final class Mod {
		final String tab;
		final Supplier<Response> response;
		Mod(String t, Supplier<Response> r) {
			tab = t;
			response = r;
		}
	}
}
