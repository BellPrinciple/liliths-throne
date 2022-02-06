package com.lilithsthrone.game.dialogue.utils;

import java.util.List;

/**
 * @since 0.4.1
 * @version 0.4.1
 * @author Innoxia
 */
public abstract class AbstractParserTarget implements ParserTarget {

	String id;
	private String description;
	private List<String> tags;
	
	public AbstractParserTarget(List<String> tags, String description) {
		this.tags = tags;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public List<String> getTags() {
		return tags;
	}

	public String getDescription() {
		return description;
	}
}
