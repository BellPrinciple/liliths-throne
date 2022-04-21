package com.lilithsthrone.rebelbase;

import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;

/**
 * @since 0.3.8.9
 * @version 0.3.21
 * @author DSG
 */
public class RebelBase {
	
	public static final DialogueNode REBEL_BASE_CAVED_IN_ROOM = new DialogueNode("Caved-in Room", "", false) {
		@Override
		public String getAuthor() {
			return "DSG";
		}
		@Override
		public int getSecondsPassed() {
			return 30;
		}
		@Override
		public String getContent() {
			return "<p>"
			+ "It's impossible to even hazard a guess as to what this room was once used for, as it's completely buried beneath hundreds of tonnes of rubble."
			+ " There's no sign of anything buried beneath the rock which almost completely fills this room,"
			+ " and so there's really little else to do but turn around and continue your search elsewhere..."
			+ "</p>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			return null;
		};
	};
}