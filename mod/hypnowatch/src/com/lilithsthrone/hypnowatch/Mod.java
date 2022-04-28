package com.lilithsthrone.hypnowatch;

import com.lilithsthrone.game.Modification;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.CorruptionLevel;
import com.lilithsthrone.game.character.body.CoverableArea;
import com.lilithsthrone.game.character.npc.dominion.Lilaya;
import com.lilithsthrone.game.character.quests.Quest;
import com.lilithsthrone.game.character.quests.QuestLine;
import com.lilithsthrone.game.dialogue.AbstractDialogueFlagValue;
import com.lilithsthrone.game.dialogue.places.dominion.shoppingArcade.ArcaneArts;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseSex;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.Rarity;
import com.lilithsthrone.game.inventory.item.AbstractItemType;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.utils.TreeNode;
import com.lilithsthrone.utils.Units;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

import java.util.List;

import static com.lilithsthrone.game.PropertyValue.nonConContent;
import static com.lilithsthrone.game.character.attributes.CorruptionLevel.FOUR_LUSTFUL;
import static com.lilithsthrone.game.character.fetishes.Fetish.FETISH_NON_CON_SUB;
import static com.lilithsthrone.game.character.fetishes.Fetish.FETISH_SUBMISSIVE;
import static com.lilithsthrone.game.dialogue.DialogueFlagValue.vickyIntroduced;
import static com.lilithsthrone.game.dialogue.places.dominion.lilayashome.RoomArthur.ROOM_ARTHUR;
import static com.lilithsthrone.main.Main.game;
import static com.lilithsthrone.main.Main.getProperties;

public class Mod extends Modification {

	@Override
	protected void install() {
		QuestLine.table.add("SIDE_HYPNO_WATCH",QUEST_LINE);
		ArcaneArts.SHOP_WEAPONS.addResponse(Mod.class,"",Mod::requestPackageOrOfferBody);
		ArcaneArts.SHOP_WEAPONS.addResponse(Mod.class,"",Mod::nervouslyLeave);
		ItemType.table.add("ARTHURS_PACKAGE",ARTHURS_PACKAGE);
		ROOM_ARTHUR.addResponse(Mod.class,"",Mod::mentionQuest);
		//TODO add dialogueFlagValue
		var n = new TreeNode<>(QUEST_TEST_SUBJECT);
		hypnoWatchTree.addChild(n);
		n.addChild(new TreeNode<>(Quest.SIDE_UTIL_COMPLETE));
	}

	@Override
	protected void uninstall() {
		QuestLine.table.remove("SIDE_HYPNO_WATCH");
		ArcaneArts.SHOP_WEAPONS.removeResponse(Mod.class);
		ItemType.table.remove("ARTHURS_PACKAGE");
		ROOM_ARTHUR.removeResponse(Mod.class);
	}

	static final AbstractDialogueFlagValue arthursPackageObtained = new AbstractDialogueFlagValue();

	static final QuestLine QUEST_LINE = new QuestLine() {
		@Override
		public String getName() {
			return "Arthur's Experiment";
		}
		@Override
		public String getCompletedDescription() {
			return "You helped Arthur to complete his research into an orientation-changing Hypno-Watch, which is now in your possession!";
		}
		@Override
		public TreeNode<Quest> getQuestTree() {
			return hypnoWatchTree;
		}
	};

	static final Quest QUEST_VICKY = new Quest() {
		@Override
		public String getName() {
			return "Order at Arcane Arts";
		}
		@Override
		public String getDescription() {
			return "Arthur informed you that he was instructed by Zaranix to find an arcane method of changing a person's sexual orientation."
			+ " While he reassured you that he had no intention of ever using such an item himself, Arthur did express an interest in completing his research,"
			+ " and asked you to fetch a special order from the store 'Arcane Arts' in the Shopping Arcade.";
		}
		@Override
		public String getCompletedDescription() {
			return "You retrieved the package from Arcane Arts, and brought it back to Arthur.";
		}
		@Override
		public int getLevel() {
			return 1;
		}
		@Override
		public int getExperienceReward() {
			return 10;
		}
	};

	static final Quest QUEST_TEST_SUBJECT = new Quest() {
		@Override
		public String getName() {
			return "Test subject";
		}
		@Override
		public String getDescription() {
			return "After Lilaya followed Arthur's instructions to enchant the watch, she asked if it would be possible to test it on you...";
			//					+ " You could either offer yourself, or, if you own any slaves, offer one of those to Arthur instead.";
		}
		@Override
		public String getCompletedDescription() {
			return "The Hypno-Watch appeared to work, although Lilaya stopped the test before it had a permanent effect."
			+ " She warned that it will have a strong corruptive effect upon the mind of the whoever is targeted, and disenchanted it for good measure, before handing it over to you.";
		}
		@Override
		public int getLevel() {
			return 1;
		}
		@Override
		public int getExperienceReward() {
			return 10;
		}
	};

	static AbstractItemType ARTHURS_PACKAGE = new AbstractItemType(0,
		"",
		false,
		"Arthur's Package",
		"Arthur's Packages",
		"A package that you collected from Arcane Arts. You need to deliver this to Arthur.",
		"arthursPackage",
		PresetColour.ANDROGYNOUS,
		null,
		null,
		Rarity.QUEST,
		null,
		null) {
		@Override
		public String getUseName() {
			return "inspect";
		}
		@Override
		public String getUseDescription(GameCharacter user, GameCharacter target) {
			return "The package is quite small, measuring roughly "+ Units.size(20)+" along each edge. It's constructed of brown cardboard, and sealed with packaging tape.";
		}
		@Override
		public boolean isConsumedOnUse() {
			return false;
		}
	};

	static boolean isNonconEnabled() {
		return getProperties().hasValue(nonConContent);
	}

	private static final TreeNode<Quest> hypnoWatchTree = new TreeNode<>(QUEST_VICKY);

	private static Response requestPackageOrOfferBody() {
		if(!game.getDialogueFlags().hasFlag(arthursPackageObtained)) {
			if(game.getPlayer().getQuest(QUEST_LINE)!=QUEST_VICKY)
				return null;
			if(game.getPlayer().isInventoryFull())
				return new Response("Arthur's package", "You don't have enough room in your inventory for the package!", null);
			return new Response("Arthur's package", "Tell Vicky that you're here to collect Arthur's package.", Dialogue.ARTHURS_PACKAGE) {
				@Override
				public void effects() {
					game.getDialogueFlags().setFlag(vickyIntroduced, true);
				}
			};
		}
		if((!game.isAnalContentEnabled() || !game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.ANUS, true))
				&& (!game.getPlayer().hasVagina() || !game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.VAGINA, true)))
			return new Response("Offer body",
				"Vicky needs to be able to access your "
				+ (game.isAnalContentEnabled()?"anus":"")
				+ (game.getPlayer().hasVagina()?(game.isAnalContentEnabled()?" or ":"")+"vagina":"")
				+"!",
				null);
		return new ResponseSex("Offer body", "Let Vicky use your body.",
			List.of(FETISH_SUBMISSIVE),
			null,
			CorruptionLevel.TWO_HORNY,
			null,
			null,
			null,
			true,
			false,
			new SMVickyOverDesk(false),
			null,
			null,
			ArcaneArts.VICKY_POST_SEX,
			UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts", "SHOP_WEAPONS_OFFER_BODY"));
	}

	private static Response nervouslyLeave() {
		if(!isNonconEnabled()
				|| !game.getDialogueFlags().hasFlag(arthursPackageObtained))
			return null;
		if((!game.isAnalContentEnabled() || !game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.ANUS, true))
		&& (!game.getPlayer().hasVagina() || !game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.VAGINA, true)))
			return new Response("Nervously leave",
			"Vicky needs to be able to access your "
			+ (game.isAnalContentEnabled()?"anus":"")
			+ (game.getPlayer().hasVagina()?(game.isAnalContentEnabled()?" or ":"")+"vagina":"")+"!",
			null);

		return new ResponseSex("Nervously leave", "Vicky is far too intimidating for you... Turn around and try to escape from her gaze. [style.boldBad(You get the feeling that this will result in non-consensual sex...)]",
			List.of(
				FETISH_SUBMISSIVE,
				FETISH_NON_CON_SUB),
			null,
			FOUR_LUSTFUL,
			null,
			null,
			null,
			false,
			false,
			new SMVickyOverDesk(true),
			null,
			null,
			ArcaneArts.VICKY_POST_SEX_RAPE,
			UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts", "SHOP_WEAPONS_RAPE"));
	}

	private static Response mentionQuest() {
		if(game.getPlayer().isQuestCompleted(QUEST_LINE))
			return null;
		if(!game.getPlayer().hasQuest(QUEST_LINE))
			return new Response("Experiments", "Ask Arthur about the sort of experiments he's currently running.", Dialogue.START) {
				@Override
				public void effects() {
					game.getTextEndStringBuilder().append(game.getPlayer().startQuest(QUEST_LINE));
				}
				@Override
				public Colour getHighlightColour() {
					return PresetColour.QUEST_SIDE;
				}
			};
		if(game.getPlayer().getQuest(QUEST_LINE) != QUEST_VICKY)
			return null;
		if(!game.getPlayer().hasItem(game.getItemGen().generateItem(ARTHURS_PACKAGE)))
			return new Response("Deliver package", "You need to fetch the package from Arcane Arts first!", null);
		return new Response("Deliver package", "Hand over the package that you fetched from Arcane Arts.", Dialogue.DELIVERY) {
			@Override
			public void effects() {
				game.getPlayer().removeItem(game.getItemGen().generateItem(ARTHURS_PACKAGE));
				game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(QUEST_LINE, QUEST_TEST_SUBJECT));
				game.getNpc(Lilaya.class).setLocation(game.getPlayer().getWorldLocation(), game.getPlayer().getLocation(), false);
			}
		};
	}
}
