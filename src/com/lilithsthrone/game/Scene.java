package com.lilithsthrone.game;

import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNodeType;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.main.Main;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Narrative position in the story.
 *
 * Most usual implementation will be {@link com.lilithsthrone.game.dialogue.DialogueNode DialogueNode}.
 */
public interface Scene {

	default String getId() {
		return null;
	}

	/**
	 * @return
	 * Creator of this scene.
	 */
	String getAuthor();

	/**
	 * @return
	 * The label for the button proceeding to this dialogue node.
	 * @see Response#getTitle()
	 */
	String getLabel();

	/**
	 * @return
	 * The description for the action proceeding to this dialogue node, to be displayed in the action tooltip.
	 * @see Response#getTooltipText()
	 */
	String getDescription();

	/**
	 * The main content for this DialogueNode.
	 * If enabled in the options, it is affected by fade-in effects.
	 */
	String getContent();

	/**
	 * Collects available responses of the player to this node.
	 * @return
	 * Collection of responses, grouped into titled tabs.
	 */
	List<ResponseTab> getResponses();

	/**
	 * Content that is set above the main content.
	 * It is not affected by fade-in effects.
	 * <br/>
	 * <b>Append to {@code Main.game.getTextStartStringBuilder()} instead of using this!</b>
	 */
	//@Deprecated
	default String getHeaderContent() {
		return null;
	}

	/**
	 * If this DialogueNode is accessed through a Response,
	 * then this method may be overwritten by the Response's own getSecondsPassed method.
	 * Due to this,
	 * it cannot be guaranteed that this method is used when determining how much time passes in a scene.
	 * @return
	 * The number of seconds that pass when entering into this dialogue node.
	 */
	default int getSecondsPassed() {
		return 0;
	}

	/**
	 * @return
	 * This dialogue node should append to the current dialogue,
	 * instead of clearing the screen and displaying it as a new scene.
	 */
	default boolean isContinuesDialogue() {
		return false;
	}

	/**
	 * @return
	 * This dialogue node disables map movement.
	 */
	default boolean isTravelDisabled() {
		return false;
	}

	/**
	 * @return
	 * This dialogue node disables inventory management.
	 * By default, this is disabled if {@code isTravelDisabled()} returns true.
	 */
	default boolean isInventoryDisabled() {
		return isTravelDisabled();
	}

	/**
	 * @return
	 * Health and aura regeneration is disabled in this dialogue node.
	 */
	default boolean isRegenerationDisabled() {
		return false;
	}

	/**
	 * @return
	 * This dialogue should display the actions title as part of a continuation of the scene, in this format:<br/><br/>
	 * <i>...sunt in culpa qui officia deserunt mollit anim id est laborum.<br/>
	 * <hr/>
	 * > Accept<br/>
	 * Lorem ipsum dolor sit amet, consectetur adipiscing elit...</i>
	 */
	default boolean isDisplaysActionTitleOnContinuesDialogue() {
		return true;
	}

	/**
	 * @return
	 * The type of dialogue that this is.
	 * Will almost always be {@link DialogueNodeType#NORMAL NORMAL}.
	 */
	default DialogueNodeType getDialogueNodeType() {
		return DialogueNodeType.NORMAL;
	}

	/**
	 * @return
	 * Whether the content of the dialogue should run through the parser.
	 * Will almost always be {@code true}.
	 */
	default boolean isContentParsed() {
		return true;
	}

	/**
	 * @return
	 * True if the header should not be run through the parsing engine.
	 */
	default boolean disableHeaderParsing() {
		return false;
	}

	/**
	 * @return
	 * True if this dialogue node should be re-run through the parser when it is restored
	 * (i.e. loaded from a saved dialogue node).
	 */
	default boolean reloadOnRestore() {
		return false;
	}

	/**
	 * This method is called before {@link #getContent()}.
	 */
	default void applyPreParsingEffects() {
	}

	default void specialPreParsingEffects() {
		if(Main.game.isStarted())
			Main.game.getDialogueFlags().setFlag(DialogueFlagValue.coveringChangeListenersRequired,false);
	}

	/**
	 * @param r
	 * Array of nullable player responses to a scene.
	 * @return
	 * Collection of a single response tab with passed
	 */
	static List<ResponseTab> singleTab(Response... r) {
		return List.of(new ResponseTab("General",r));
	}

	/**
	 * Titled group of player-side responses to a dialogue.
	 */
	final class ResponseTab {
		public final String title;
		public final ArrayList<Response> response = new ArrayList<>();
		public ResponseTab(String t, Response... r) {
			title = requireNonNull(t);
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
}
