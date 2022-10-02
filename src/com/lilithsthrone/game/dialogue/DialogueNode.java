package com.lilithsthrone.game.dialogue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.lilithsthrone.controller.xmlParsing.XMLMissingTagException;
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
		if(!XMLFile.exists()) {
			System.err.println("DialogueNode file does not exist! (" + XMLFile.getName() + ")");
			return null;
		}
		List<DialogueNode> loadedNodes = new ArrayList<>();
		try {
			Document doc = Main.getDocBuilder().parse(XMLFile);
			// Cast magic:
			doc.getDocumentElement().normalize();
			Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in DialogueNode files it's <dialogueNodes>
			for(Element node : coreElement.getAllOf("scene")) {
				loadedNodes.add(loadDialogueNode(idPrefix, author, mod, node));
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("DialogueNode was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
			return null;
		}
		return loadedNodes;
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
		return label + ":" + description.substring(0, Math.min(description.length(), 20));
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
				ex.printStackTrace();
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
	 * @return True if this dialogue node disables inventory management. By default, this returns false. <b>This should only be used in very specific circumstances.</b>
	 */
	public boolean isInventoryForcedDisabledInSex() {
		return false;
	}

	/**
	 * @return True if this dialogue node disables phone use. By default, this returns false. <b>This should only be used in very specific circumstances.</b>
	 */
	public boolean isPhoneDisabled() {
		return false;
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
	 */
	public String getResponseTabTitle(int index) {
		return null;
	}

	/**
	 * The responses that lead on from this dialogue node.
	 * @param responseTab The tab in which the response is to be found.
	 * @param index The index of the response.
	 * @return A response, if a response is returned at the specified responseTab & index, or, if not, then null.
	 */
	public abstract Response getResponse(int responseTab, int index);

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

	private static DialogueNode loadDialogueNode(String idPrefix, String author, boolean isMod, Element node) throws XMLMissingTagException {
		String idTag = node.getAttribute("id");
		String authorAttribute = node.getTextContent("author");
		String getAuthor = authorAttribute.isEmpty() ? Util.capitaliseSentence(author) : authorAttribute; // folder name
		String title = node.getTextContent("title");
		String tooltip = node.getTextContent("tooltip");
		String preParsingEffects = node.getTextContent("preParsingEffects");
		Optional<Element> contentElement = node.getOptionalFirstOf("content");
		String folderPath = contentElement.map(e->e.getAttribute("folderPath")).orElse("");//.replaceAll("/", System.getProperty("file.separator"));
		String dialogueTag = contentElement.map(Element::getTextContent).orElse("");
		String secondsPassed = node.getMandatoryFirstOf("secondsPassed").getTextContent().trim();
		boolean minutes = node.getMandatoryFirstOf("secondsPassed").attributeTrue("minutes");
		String continuesDialogue = node.getTextContent("continuesDialogue");
		String travelDisabled = node.getTextContent("travelDisabled");
		String inventoryDisabled = node.getTextContent("inventoryDisabled");
		String regenerationDisabled = node.getTextContent("regenerationDisabled");
		Map<Integer, String> responseTabs = new HashMap<>();
		for(Element tab : node.getAllOf("responseTabs", "tab")) {
			tab.attributeInt("index").ifPresent(index -> responseTabs.put(index, tab.getTextContent()));
		}
		Optional<Element> responsesElement = node.getOptionalFirstOf("responses");
		String copyFromDialogueId = responsesElement.map(e->e.getAttribute("copyFromDialogueId")).orElse("");
		Map<Integer, Map<String, List<Response>>> loadedResponses = new HashMap<>();
		for(Element e : responsesElement.map(e->e.getAllOf("response")).orElseGet(List::of)) {
			try {
				int tab = Integer.parseInt(e.getMandatoryFirstOf("responseTabIndex").getTextContent());
				String index = e.getMandatoryFirstOf("index").getTextContent();
				Response response = loadResponse(e);
				loadedResponses.computeIfAbsent(tab, k -> new HashMap<>())
						.computeIfAbsent(index, k -> new ArrayList<>())
						.add(response);
			}
			catch(XMLMissingTagException x) {
				x.printStackTrace();
			}
		}
		return new DialogueNode(title, tooltip, false) {
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
				return getAuthor;
			}
			@Override
			public void applyPreParsingEffects() {
				UtilText.parse(preParsingEffects);
			}
			@Override
			public int getSecondsPassed() {
				return Integer.parseInt(UtilText.parse(secondsPassed).trim()) * (minutes ? 60 : 1);
			}
			@Override
			public String getContent() {
				if(folderPath.isEmpty() || dialogueTag.isEmpty()) {
					return "";
				}
				return UtilText.parseFromXMLFile(new ArrayList<>(), "res", folderPath, dialogueTag, new ArrayList<>());
			}
			@Override
			public String getResponseTabTitle(int index) {
				if(responseTabs.get(index) != null) {
					String title = UtilText.parse(responseTabs.get(index)).trim();
					if(!title.isEmpty()) {
						return title;
					}
				}
				if(!copyFromDialogueId.isEmpty()) {
					DialogueNode node = DialogueManager.getDialogueFromId(copyFromDialogueId);
					if(node != null) {
						return node.getResponseTabTitle(index);
					}
				}
				return null;
			}
			@Override
			public Response getResponse(int responseTab, int index) {
				if(loadedResponses.containsKey(responseTab)) {
					for(Entry<String,List<Response>> entry : loadedResponses.get(responseTab).entrySet()) {
						int parsedIndex = Integer.parseInt(UtilText.parse(entry.getKey()).trim());
						if(parsedIndex == index) {
							for(Response response : entry.getValue()) {
								if(response.isAvailableFromConditional()) {
									return response;
								}
							}
						}
					}
//								if(loadedResponses.get(responseTab).containsKey(index)) {
//									for(Response response : loadedResponses.get(responseTab).get(index)) {
//										if(response.isAvailableFromConditional()) {
//											return response;
//										}
//									}
//								}
				}
				if(!copyFromDialogueId.isEmpty()) {
					return DialogueManager.getDialogueFromId(copyFromDialogueId).getResponse(responseTab, index);
				}
				return null;
			}
			@Override
			public boolean isContinuesDialogue() {
				return !continuesDialogue.isEmpty() && Boolean.parseBoolean(UtilText.parse(continuesDialogue).trim());
			}
			@Override
			public boolean isTravelDisabled() {
				return !travelDisabled.isEmpty() && Boolean.parseBoolean(UtilText.parse(travelDisabled).trim());
			}
			@Override
			public boolean isInventoryDisabled() {
				return isTravelDisabled() || Boolean.parseBoolean(UtilText.parse(inventoryDisabled).trim());
			}
			@Override
			public boolean isRegenerationDisabled() {
				return Boolean.parseBoolean(UtilText.parse(regenerationDisabled).trim());
			}
		};
	}

	private static Response loadResponse(Element response) throws XMLMissingTagException {
		String availabilityConditional = response.getTextContent("availabilityConditional");
		String responseTitle = response.getMandatoryFirstOf("responseTitle").getTextContent();
		String responseTooltip = response.getMandatoryFirstOf("responseTooltip").getTextContent();
		Optional<Element> nextDialogueElement = response.getOptionalFirstOf("nextDialogue");
		String nextDialogueId = nextDialogueElement.map(Element::getTextContent).orElse("");
		String defaultPlaceTypeForNextDialogue = nextDialogueElement.map(e->e.getAttribute("defaultPlaceType")).orElse("");
		boolean stripContentForNextDialogue = nextDialogueElement.filter(e->Boolean.parseBoolean(e.getAttribute("stripContent"))).isPresent();
		boolean forceContinueForNextDialogue = nextDialogueElement.filter(e->Boolean.parseBoolean(e.getAttribute("forceContinue"))).isPresent();
		String colourResponse = response.getTextContent("colour");
		Optional<Element> secondsPassedElement = response.getOptionalFirstOf("secondsPassed");
		String secondsPassedResponse = secondsPassedElement.map(Element::getTextContent).orElse("");
		boolean asMinutes = secondsPassedElement.filter(e->e.attributeTrue("minutes")).isPresent();
		String effectsResponse = response.getTextContent("effects");
		List<String> requiredFetishes = response.getAllOf("requiresFetisches", "fetish").stream()
		.map(Element::getTextContent)
		.toList();
		String corruptionLevel = response.getTextContent("corruptionLevel");
		String requiredFemininity = response.getTextContent("requiredFemininity");
		List<String> requiredPerks = response.getAllOf("requiredPerks", "perk").stream()
		.map(Element::getTextContent)
		.toList();
		List<String> subspeciesRequired = response.getAllOf("requiredSubspecies", "subspecies").stream()
		.map(Element::getTextContent)
		.toList();
		// Trading:
		Optional<Element> tradingVariables = response.getOptionalFirstOf("tradingVariables");
		if(tradingVariables.isPresent() && Boolean.parseBoolean(tradingVariables.get().getAttribute("enabled"))) {
			String tradeTarget = tradingVariables.get().getMandatoryFirstOf("tradePartner").getTextContent();
			ResponseTrade tradeResponse = new ResponseTrade(responseTitle, responseTooltip, tradeTarget, effectsResponse);
			tradeResponse.setConditional(availabilityConditional);
			return tradeResponse;
		}
		// Combat:
		Optional<Element> combatVariables = response.getOptionalFirstOf("combatVariables");
		if(combatVariables.isPresent() && Boolean.parseBoolean(combatVariables.get().getAttribute("enabled"))) {
			Element combatElement = combatVariables.get();
			String nextDialoguePlayerVictory = combatElement.getMandatoryFirstOf("nextDialoguePlayerVictory").getTextContent();
			String nextDialoguePlayerDefeat = combatElement.getMandatoryFirstOf("nextDialoguePlayerDefeat").getTextContent();
			Optional<Element> allies = combatElement.getOptionalFirstOf("allies");
			boolean companionsAreAllies = allies.filter(e->e.attributeNotFalse("companionsAreAllies")).isEmpty();
			boolean elementalsAreAllies = allies.filter(e->e.attributeNotFalse("elementalsAreAllies")).isEmpty();
			List<String> alliesIds = allies.stream()
			.flatMap(e->e.getAllOf("ally").stream())
			.map(Element::getTextContent)
			.toList();
			List<String> enemiesIds = combatElement.getAllOf("enemies", "enemy").stream()
			.map(Element::getTextContent)
			.toList();
			String enemyLeaderId = combatElement.getOptionalFirstOf("enemies").stream()
			.flatMap(e->{
				//reverse elements such that the last enemy marked "leader" gets leader
				List<Element> l = e.getAllOf("enemy");
				int s = l.size();
				return IntStream.range(0,s).mapToObj(i->l.get(s-i-1));
			})
			.filter(e->e.attributeTrue("leader"))
			.map(Element::getTextContent)
			.findFirst().orElse(enemiesIds.get(0));
			Map<String,String> openingDescriptions = combatElement.getAllOf("openingDescriptions", "combatant").stream()
			.collect(Collectors.toMap(e->e.getAttribute("id").trim(),Element::getTextContent));
			boolean escapeBlocked = Boolean.parseBoolean(UtilText.parse(combatElement.getTextContent("escapeBlocked")));
			boolean submitBlocked = Boolean.parseBoolean(UtilText.parse(combatElement.getTextContent("submitBlocked")));
			ResponseCombat combatResponse = new ResponseCombat(
					responseTitle,
					responseTooltip,
					alliesIds,
					companionsAreAllies,
					elementalsAreAllies,
					enemyLeaderId,
					enemiesIds,
					openingDescriptions,
					effectsResponse,
					escapeBlocked, submitBlocked);
			combatResponse.setNextDialoguePlayerVictoryId(nextDialoguePlayerVictory);
			combatResponse.setNextDialoguePlayerDefeatId(nextDialoguePlayerDefeat);
			combatResponse.setConditional(availabilityConditional.isEmpty() ? "true" : availabilityConditional);
			return combatResponse;
		}
		// Sex:
		Optional<Element> sexVariables = response.getOptionalFirstOf("sexVariables");
		if(sexVariables.isPresent() && sexVariables.get().attributeTrue("enabled")) {
			Element sexElement = sexVariables.get();
			String consensual = sexElement.getMandatoryFirstOf("consensual").getTextContent();
			String subHasEqualControl = sexElement.getMandatoryFirstOf("subHasEqualControl").getTextContent();
			List<String> dominantIds = sexElement.getAllOf("dominants", "character").stream()
			.map(Element::getTextContent)
			.toList();
			List<String> submissiveIds = sexElement.getAllOf("submissives", "character").stream()
			.map(Element::getTextContent)
			.toList();
			List<String> dominantSpectatorIds = sexElement.getAllOf("dominantSpectators", "character").stream()
			.map(Element::getTextContent)
			.toList();
			List<String> submissiveSpectatorIds = sexElement.getAllOf("submissiveSpectators", "character").stream()
			.map(Element::getTextContent)
			.toList();
			String postSexDialogueId = sexElement.getMandatoryFirstOf("postSexDialogue").getTextContent();
			Optional<Element> sexStartContentElement = sexElement.getOptionalFirstOf("sexStartContent");
			String folderPathSex = sexStartContentElement.map(e->e.getAttribute("folderPath")).orElse("");//.replaceAll("/", System.getProperty("file.separator"));
			String dialogueTagSex = sexStartContentElement.map(Element::getTextContent).orElse("");
			List<ResponseTag> tags = sexElement.getAllOf("tags", "tag").stream()
			.flatMap(e->e.text(ResponseTag::valueOf).stream())
			.toList();
			List<InitialSexActionInformation> initialActions = new ArrayList<>();
			for(Element e : sexElement.getAllOf("ongoingActionsAtStart", "action")) {
				try {
					initialActions.add(new InitialSexActionInformation(
							e.getMandatoryFirstOf("condition").getTextContent(),
							e.getMandatoryFirstOf("performer").getTextContent(),
							e.getMandatoryFirstOf("target").getTextContent(),
							e.getMandatoryFirstOf("id").getTextContent(),
							e.getMandatoryFirstOf("showDescription").getTextContent(),
							e.getMandatoryFirstOf("showEffects").getTextContent()));
				}
				catch(XMLMissingTagException x) {
					x.printStackTrace();
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
			return sexResponse;
		}
		// Sex with manager:
		Optional<Element> sexVariablesWithManagerElement = response.getOptionalFirstOf("sexVariablesWithManager");
		if(sexVariablesWithManagerElement.isPresent() && sexVariablesWithManagerElement.get().attributeTrue("enabled")) {
			Element sexElement = sexVariablesWithManagerElement.get();
			String sexManagerId = sexElement.getMandatoryFirstOf("id").getTextContent();
			String sexPositionId = sexElement.getMandatoryFirstOf("startingPosition").getTextContent();
			Map<String,String> dominantPositionIds = sexElement.getAllOf("dominants", "character").stream()
			.flatMap(DialogueNode::entry)
			.collect(Collectors.toMap(Entry::getKey,Entry::getValue));
			Map<String,String> submissivePositionIds = sexElement.getAllOf("submissives", "character").stream()
			.flatMap(DialogueNode::entry)
			.collect(Collectors.toMap(Entry::getKey,Entry::getValue));
			List<String> dominantSpectatorIds = sexElement.getAllOf("dominantSpectators", "character").stream()
			.map(Element::getTextContent)
			.toList();
			List<String> submissiveSpectatorIds = sexElement.getAllOf("submissiveSpectators", "character").stream()
			.map(Element::getTextContent)
			.toList();
			String postSexDialogueId = sexElement.getMandatoryFirstOf("postSexDialogue").getTextContent();
			Optional<Element> sexStartContentElement = sexElement.getOptionalFirstOf("sexStartContent");
			String folderPathSex = sexStartContentElement.map(e->e.getAttribute("folderPath")).orElse("");//.replaceAll("/", System.getProperty("file.separator"));
			String dialogueTagSex = sexStartContentElement.map(Element::getTextContent).orElse("");
			List<ResponseTag> tags = sexElement.getAllOf("tags", "tag").stream()
			.flatMap(e->e.text(ResponseTag::valueOf).stream())
			.toList();
			List<InitialSexActionInformation> initialActions = new ArrayList<>();
			for(Element e : sexElement.getAllOf("ongoingActionsAtStart", "action")) {
				String condition = e.getTextContent("condition");
				String showDescription = e.getTextContent("showDescription");
				String showEffects = e.getTextContent("showEffects");
				try {
					initialActions.add(new InitialSexActionInformation(
							condition.isEmpty() ? "true" : condition,
							e.getMandatoryFirstOf("performer").getTextContent(),
							e.getMandatoryFirstOf("target").getTextContent(),
							e.getMandatoryFirstOf("id").getTextContent(),
							showDescription.isEmpty() ? "true" : showDescription,
							showEffects.isEmpty() ? "true" : showEffects));
				}
				catch(XMLMissingTagException x) {
					x.printStackTrace();
				}
			};
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
			return sexResponse;
		}
		// NoEffects response:
		if(nextDialogueId.isEmpty() && defaultPlaceTypeForNextDialogue.isEmpty()) {
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
			return onlyEffectsResponse;
		}
		// Normal response:
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
				return defaultPlaceTypeForNextDialogue;
			}
		};
		standardResponse.setConditional(availabilityConditional);
		standardResponse.setStripContent(stripContentForNextDialogue);
		standardResponse.setForceContinue(forceContinueForNextDialogue);
		return standardResponse;
	}

	private static Stream<Entry<String,String>> entry(Element e) {
		try {
			return Stream.of(Map.entry(e.getMandatoryFirstOf("id").getTextContent(),e.getMandatoryFirstOf("slot").getTextContent()));
		}
		catch(XMLMissingTagException x) {
			x.printStackTrace();
			return Stream.empty();
		}
	}
}
