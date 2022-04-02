package com.lilithsthrone.rebelbase;

import com.lilithsthrone.utils.TreeNode;

public enum QuestLine implements com.lilithsthrone.game.character.quests.QuestLine {
	SIDE(Quest.EXPLORATION),
	FIREBOMBS(Quest.FIREBOMBS_START);

	@Override
	public String getName() {
		switch(this) {
		case SIDE:
			return "Grave Robbing";
		case FIREBOMBS:
			return "Spicy Meatballs";
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCompletedDescription() {
		switch(this) {
		case SIDE:
			return "You managed to escape the abandoned rebel hideout.";
		case FIREBOMBS:
			return "You've gotten yourself a steady supply of Arcane Firebombs. At the usual premium, of course.";
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public TreeNode<com.lilithsthrone.game.character.quests.Quest> getQuestTree() {
		return questTree;
	}

	final TreeNode<com.lilithsthrone.game.character.quests.Quest> questTree;

	QuestLine(Quest start) {
		questTree = new TreeNode<>(start);
	}

	String getId() {
		return "REBEL_BASE_"+name();
	}
}
