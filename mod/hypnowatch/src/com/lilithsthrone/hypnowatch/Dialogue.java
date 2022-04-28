package com.lilithsthrone.hypnowatch;

import com.lilithsthrone.game.Scene;
import com.lilithsthrone.game.character.npc.dominion.Arthur;
import com.lilithsthrone.game.character.npc.dominion.Lilaya;
import com.lilithsthrone.game.character.npc.dominion.Rose;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseSex;
import com.lilithsthrone.game.dialogue.utils.UtilText;

import java.util.List;

import static com.lilithsthrone.game.character.attributes.CorruptionLevel.FOUR_LUSTFUL;
import static com.lilithsthrone.game.character.attributes.CorruptionLevel.TWO_HORNY;
import static com.lilithsthrone.game.character.body.CoverableArea.ANUS;
import static com.lilithsthrone.game.character.body.CoverableArea.VAGINA;
import static com.lilithsthrone.game.character.fetishes.Fetish.FETISH_NON_CON_SUB;
import static com.lilithsthrone.game.character.fetishes.Fetish.FETISH_SUBMISSIVE;
import static com.lilithsthrone.game.character.quests.Quest.SIDE_UTIL_COMPLETE;
import static com.lilithsthrone.game.dialogue.DialogueFlagValue.vickyIntroduced;
import static com.lilithsthrone.game.dialogue.places.dominion.lilayashome.LilayaHomeGeneric.CORRIDOR;
import static com.lilithsthrone.game.dialogue.places.dominion.lilayashome.RoomArthur.ROOM_ARTHUR;
import static com.lilithsthrone.game.dialogue.places.dominion.shoppingArcade.ArcaneArts.EXTERIOR;
import static com.lilithsthrone.game.dialogue.places.dominion.shoppingArcade.ArcaneArts.SHOP_WEAPONS;
import static com.lilithsthrone.game.inventory.item.ItemType.ORIENTATION_HYPNO_WATCH;
import static com.lilithsthrone.main.Main.game;
import static com.lilithsthrone.world.WorldType.LILAYAS_HOUSE_GROUND_FLOOR;
import static com.lilithsthrone.world.places.PlaceType.LILAYA_HOME_CORRIDOR;
import static com.lilithsthrone.world.places.PlaceType.LILAYA_HOME_LAB;

public enum Dialogue implements Scene {
	START,
	DELIVERY,
	OFFER_SELF,
	WAKE_UP,
	OFFER_REFUSED,
	WAKE_ROSE,
	ARTHURS_PACKAGE,
	ARTHURS_PACKAGE_BOUGHT,
	VICKY_POST_SEX_PACKAGE,
	VICKY_POST_SEX_RAPE_PACKAGE,
	;

	@Override
	public String getAuthor() {
		return null;
	}

	@Override
	public String getLabel() {
		switch(this) {
		case ARTHURS_PACKAGE:
		case ARTHURS_PACKAGE_BOUGHT:
		case VICKY_POST_SEX_PACKAGE:
		case VICKY_POST_SEX_RAPE_PACKAGE:
			return "Arcane Arts";
		default:
			return "";
		}
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public void applyPreParsingEffects() {
		switch(this) {
		case OFFER_SELF:
			game.getTextEndStringBuilder()
			.append(game.getNpc(Lilaya.class).incrementAffection(game.getPlayer(),10))
			.append(game.getNpc(Arthur.class).incrementAffection(game.getPlayer(),5));
			break;
		case OFFER_REFUSED:
			game.getNpc(Rose.class).setLocation(game.getPlayer(),false);
		case VICKY_POST_SEX_PACKAGE:
		case VICKY_POST_SEX_RAPE_PACKAGE:
			if(!game.getDialogueFlags().hasFlag(Mod.arthursPackageObtained)) {
				game.getDialogueFlags().setFlag(Mod.arthursPackageObtained,true);
				game.getTextEndStringBuilder().append(game.getPlayer().addItem(game.getItemGen().generateItem(Mod.ARTHURS_PACKAGE),false,true));
			}
		}
	}

	@Override
	public String getContent() {
		switch(this) {
		case START:
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom","ROOM_ARTHUR_HYPNO_WATCH_START");
		case DELIVERY:
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom","ROOM_ARTHUR_HYPNO_WATCH_DELIVERY");
		case OFFER_SELF:
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom","ROOM_ARTHUR_HYPNO_WATCH_OFFER_SELF");
		case WAKE_UP:
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom","ROOM_ARTHUR_HYPNO_WATCH_OFFER_SELF_WAKE_UP");
		case OFFER_REFUSED:
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom","ROOM_ARTHUR_HYPNO_WATCH_OFFER_REFUSED");
		case WAKE_ROSE:
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/arthursRoom","ROOM_ARTHUR_HYPNO_WATCH_OFFER_REFUSED_WAKE_ROSE");
		case ARTHURS_PACKAGE:
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts","ARTHURS_PACKAGE");
		case ARTHURS_PACKAGE_BOUGHT:
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts","ARTHURS_PACKAGE_BOUGHT");
		case VICKY_POST_SEX_PACKAGE:
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts","VICKY_POST_SEX_PACKAGE");
		case VICKY_POST_SEX_RAPE_PACKAGE:
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts","VICKY_POST_SEX_RAPE_PACKAGE");
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ResponseTab> getResponses() {
		Response[] r = new Response[4];
		switch(this) {
		case START:
			return ROOM_ARTHUR.getResponses();
		case DELIVERY:
			r[1] = new Response("Agree","You trust Lilaya enough to agree with her request.",OFFER_SELF);
			r[2] = new Response("Refuse","There's no way that you're going to agree to be Lilaya's and Arthur's test subject! Perhaps Rose could be volunteered in your place...",OFFER_REFUSED);
			break;
		case OFFER_SELF:
			r[1] = new Response("Wake up","You suddenly snap out of your trance.",WAKE_UP) {
				@Override
				public void effects() {
					game.getTextEndStringBuilder()
					.append(game.getPlayer().addItem(game.getItemGen().generateItem(ORIENTATION_HYPNO_WATCH),false,true))
					.append(game.getPlayer().setQuestProgress(Mod.QUEST_LINE,SIDE_UTIL_COMPLETE));
					game.getNpc(Lilaya.class).setLocation(LILAYAS_HOUSE_GROUND_FLOOR,LILAYA_HOME_LAB,true);
				}
			};
			break;
		case WAKE_UP:
		case WAKE_ROSE:
			r[1] = new Response("Continue","Let Arthur continue with his other experiments.",CORRIDOR) {
				@Override
				public void effects() {
					game.getPlayer().setNearestLocation(game.getPlayer().getWorldLocation(),LILAYA_HOME_CORRIDOR,false);
				}
			};
			break;
		case OFFER_REFUSED:
			r[1] = new Response("Wake Rose","Help Lilaya to wake Rose up.",WAKE_ROSE) {
				@Override
				public void effects() {
					game.getTextEndStringBuilder().append(game.getPlayer().addItem(game.getItemGen().generateItem(ORIENTATION_HYPNO_WATCH),false,true));
					game.getTextEndStringBuilder().append(game.getPlayer().setQuestProgress(Mod.QUEST_LINE,SIDE_UTIL_COMPLETE));
					game.getNpc(Lilaya.class).setLocation(LILAYAS_HOUSE_GROUND_FLOOR,LILAYA_HOME_LAB,false);
					game.getNpc(Rose.class).setLocation(LILAYAS_HOUSE_GROUND_FLOOR,LILAYA_HOME_LAB,false);
				}
			};
			break;
		case ARTHURS_PACKAGE:
			r[0] = new Response("Leave","Leave Arcane Arts and head back out into the arcade.",EXTERIOR) {
				@Override
				public void effects() {
					game.getDialogueFlags().setFlag(vickyIntroduced,true);
					game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts","ARTHURS_PACKAGE_LEAVE"));
				}
			};
			r[1] = game.getPlayer().getMoney()>=100
			? new Response("Pay ("+UtilText.formatAsMoney(100,"span")+")","Pay Vicky 100 flames.",ARTHURS_PACKAGE_BOUGHT) {
				@Override
				public void effects() {
					game.getDialogueFlags().setFlag(Mod.arthursPackageObtained,true);
					game.getTextEndStringBuilder().append(game.getPlayer().addItem(game.getItemGen().generateItem(Mod.ARTHURS_PACKAGE),false,true));
					game.getPlayer().incrementMoney(-100);
				}
			}
			: new Response("Pay ("+UtilText.formatAsMoneyUncoloured(100,"span")+")","You don't have enough money to pay the fee!",null);
				if((!game.isAnalContentEnabled() || !game.getPlayer().isAbleToAccessCoverableArea(ANUS,true))
				&& (!game.getPlayer().hasVagina() || !game.getPlayer().isAbleToAccessCoverableArea(VAGINA,true))) {
					r[2] = new Response("Offer body",
					"Vicky needs to be able to access your "
					+ (game.isAnalContentEnabled()?"anus":"")
					+ (game.getPlayer().hasVagina()?(game.isAnalContentEnabled()?" or ":"")+"vagina":"")+"!",
					null);
				} else {
					r[2] = new ResponseSex("Offer body","Let Vicky use your body as payment for the fee.",
						List.of(FETISH_SUBMISSIVE),
						null,
						TWO_HORNY,
						null,
						null,
						null,
						true,
						false,
						new SMVickyOverDesk(false),
						null,
						null,
						VICKY_POST_SEX_PACKAGE,
						UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts","ARTHURS_PACKAGE_SEX"));
				}
			if(Mod.isNonconEnabled()) {
				r[3] = (!game.isAnalContentEnabled() || !game.getPlayer().isAbleToAccessCoverableArea(ANUS,true))
						&& (!game.getPlayer().hasVagina() || !game.getPlayer().isAbleToAccessCoverableArea(VAGINA,true))
				? new Response("Weakly refuse",
					"Vicky needs to be able to access your "
					+ (game.isAnalContentEnabled()?"anus":"")
					+ (game.getPlayer().hasVagina()?(game.isAnalContentEnabled()?" or ":"")+"vagina":"")+"!",
					null)
				: new ResponseSex(
					"Weakly refuse",
					"You can't bring yourself to say no to such an intimidating person... Try to wriggle free and leave..."
					+ "<br/>[style.boldBad(You get the feeling that this will result in non-consensual sex...)]",
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
					VICKY_POST_SEX_RAPE_PACKAGE,
					UtilText.parseFromXMLFile("places/dominion/shoppingArcade/arcaneArts","ARTHURS_PACKAGE_RAPE"));
			}
			break;
		case ARTHURS_PACKAGE_BOUGHT:
		case VICKY_POST_SEX_PACKAGE:
		case VICKY_POST_SEX_RAPE_PACKAGE:
			return SHOP_WEAPONS.getResponses();
		}
		return List.of(new ResponseTab("",r));
	}

	@Override
	public int getSecondsPassed() {
		switch(this) {
		case WAKE_UP:
			return 15 * 60;
		case ARTHURS_PACKAGE:
		case ARTHURS_PACKAGE_BOUGHT:
		case VICKY_POST_SEX_RAPE_PACKAGE:
		case VICKY_POST_SEX_PACKAGE:
			return Scene.super.getSecondsPassed();
		default:
			return 5 * 60;
		}
	}

	@Override
	public boolean isContinuesDialogue() {
		switch(this) {
		case START:
		case DELIVERY:
		case VICKY_POST_SEX_PACKAGE:
		case VICKY_POST_SEX_RAPE_PACKAGE:
			return false;
		default:
			return true;
		}
	}

	@Override
	public boolean isTravelDisabled() {
		return this != START;
	}
}
