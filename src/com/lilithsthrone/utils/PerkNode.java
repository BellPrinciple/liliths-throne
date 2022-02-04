package com.lilithsthrone.utils;

import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.effects.PerkCategory;
import com.lilithsthrone.game.character.effects.TreeEntry;

/**
 * @since 0.3.4
 * @version 0.3.4
 * @author Innoxia
 */
public class PerkNode {
	private PerkNode parent;
	// f = g + h
	// g = the movement cost to move from the starting point A to this node,
	// following the path generated to get there.
	// h = the estimated movement cost to move from this node to the final
	// destination. This is often referred to as the heuristic, because it is a
	// guess.
	private TreeEntry<PerkCategory,Perk> perkTreeEntry;
	private int f, g, h;

	public PerkNode(PerkNode parent, TreeEntry<PerkCategory,Perk> perkTreeEntry, int f, int g, int h) {
		this.parent = parent;
		this.perkTreeEntry = new TreeEntry<>(perkTreeEntry.getCategory(), perkTreeEntry.getRow(), perkTreeEntry.getEntry());
		this.perkTreeEntry.setLinks(perkTreeEntry.getLinks());
		this.f = f;
		this.g = g;
		this.h = h;
	}

	public PerkNode getParent() {
		return parent;
	}

	public void setParent(PerkNode parent) {
		this.parent = parent;
	}

	public int getF() {
		return f;
	}

	public void setF() {
		f = g + h;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public TreeEntry<PerkCategory,Perk> getPerkTreeEntry() {
		return perkTreeEntry;
	}

	public void setPerkTreeEntry(TreeEntry<PerkCategory,Perk> perkTreeEntry) {
		this.perkTreeEntry = perkTreeEntry;
	}
}
