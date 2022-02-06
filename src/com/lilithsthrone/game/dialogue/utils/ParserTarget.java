package com.lilithsthrone.game.dialogue.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.dominion.Amber;
import com.lilithsthrone.game.character.npc.dominion.Angel;
import com.lilithsthrone.game.character.npc.dominion.Arthur;
import com.lilithsthrone.game.character.npc.dominion.Ashley;
import com.lilithsthrone.game.character.npc.dominion.Brax;
import com.lilithsthrone.game.character.npc.dominion.Bunny;
import com.lilithsthrone.game.character.npc.dominion.CandiReceptionist;
import com.lilithsthrone.game.character.npc.dominion.Daddy;
import com.lilithsthrone.game.character.npc.dominion.Elle;
import com.lilithsthrone.game.character.npc.dominion.Felicia;
import com.lilithsthrone.game.character.npc.dominion.Finch;
import com.lilithsthrone.game.character.npc.dominion.HarpyBimbo;
import com.lilithsthrone.game.character.npc.dominion.HarpyBimboCompanion;
import com.lilithsthrone.game.character.npc.dominion.HarpyDominant;
import com.lilithsthrone.game.character.npc.dominion.HarpyDominantCompanion;
import com.lilithsthrone.game.character.npc.dominion.HarpyNympho;
import com.lilithsthrone.game.character.npc.dominion.HarpyNymphoCompanion;
import com.lilithsthrone.game.character.npc.dominion.Helena;
import com.lilithsthrone.game.character.npc.dominion.Jules;
import com.lilithsthrone.game.character.npc.dominion.Kalahari;
import com.lilithsthrone.game.character.npc.dominion.Kate;
import com.lilithsthrone.game.character.npc.dominion.Kay;
import com.lilithsthrone.game.character.npc.dominion.Callie;
import com.lilithsthrone.game.character.npc.dominion.Kruger;
import com.lilithsthrone.game.character.npc.dominion.Lilaya;
import com.lilithsthrone.game.character.npc.dominion.Loppy;
import com.lilithsthrone.game.character.npc.dominion.Lumi;
import com.lilithsthrone.game.character.npc.dominion.Natalya;
import com.lilithsthrone.game.character.npc.dominion.Nyan;
import com.lilithsthrone.game.character.npc.dominion.NyanMum;
import com.lilithsthrone.game.character.npc.dominion.Pazu;
import com.lilithsthrone.game.character.npc.dominion.Pix;
import com.lilithsthrone.game.character.npc.dominion.Ralph;
import com.lilithsthrone.game.character.npc.dominion.RentalMommy;
import com.lilithsthrone.game.character.npc.dominion.Rose;
import com.lilithsthrone.game.character.npc.dominion.Scarlett;
import com.lilithsthrone.game.character.npc.dominion.Sean;
import com.lilithsthrone.game.character.npc.dominion.SupplierLeader;
import com.lilithsthrone.game.character.npc.dominion.SupplierPartner;
import com.lilithsthrone.game.character.npc.dominion.TestNPC;
import com.lilithsthrone.game.character.npc.dominion.Vanessa;
import com.lilithsthrone.game.character.npc.dominion.Vicky;
import com.lilithsthrone.game.character.npc.dominion.Wes;
import com.lilithsthrone.game.character.npc.dominion.Zaranix;
import com.lilithsthrone.game.character.npc.dominion.ZaranixMaidKatherine;
import com.lilithsthrone.game.character.npc.dominion.ZaranixMaidKelly;
import com.lilithsthrone.game.character.npc.fields.Arion;
import com.lilithsthrone.game.character.npc.fields.Astrapi;
import com.lilithsthrone.game.character.npc.fields.Belle;
import com.lilithsthrone.game.character.npc.fields.Ceridwen;
import com.lilithsthrone.game.character.npc.fields.Dale;
import com.lilithsthrone.game.character.npc.fields.Daphne;
import com.lilithsthrone.game.character.npc.fields.Evelyx;
import com.lilithsthrone.game.character.npc.fields.Fae;
import com.lilithsthrone.game.character.npc.fields.Farah;
import com.lilithsthrone.game.character.npc.fields.Flash;
import com.lilithsthrone.game.character.npc.fields.Hale;
import com.lilithsthrone.game.character.npc.fields.HeadlessHorseman;
import com.lilithsthrone.game.character.npc.fields.Heather;
import com.lilithsthrone.game.character.npc.fields.Imsu;
import com.lilithsthrone.game.character.npc.fields.Jess;
import com.lilithsthrone.game.character.npc.fields.Kazik;
import com.lilithsthrone.game.character.npc.fields.Kheiron;
import com.lilithsthrone.game.character.npc.fields.Minotallys;
import com.lilithsthrone.game.character.npc.fields.Monica;
import com.lilithsthrone.game.character.npc.fields.Moreno;
import com.lilithsthrone.game.character.npc.fields.Nizhoni;
import com.lilithsthrone.game.character.npc.fields.Penelope;
import com.lilithsthrone.game.character.npc.fields.Silvia;
import com.lilithsthrone.game.character.npc.fields.Vronti;
import com.lilithsthrone.game.character.npc.fields.Yui;
import com.lilithsthrone.game.character.npc.fields.Ziva;
import com.lilithsthrone.game.character.npc.misc.GenericAndrogynousNPC;
import com.lilithsthrone.game.character.npc.misc.GenericFemaleNPC;
import com.lilithsthrone.game.character.npc.misc.GenericMaleNPC;
import com.lilithsthrone.game.character.npc.misc.GenericTrader;
import com.lilithsthrone.game.character.npc.misc.PrologueFemale;
import com.lilithsthrone.game.character.npc.misc.PrologueMale;
import com.lilithsthrone.game.character.npc.submission.Axel;
import com.lilithsthrone.game.character.npc.submission.Claire;
import com.lilithsthrone.game.character.npc.submission.DarkSiren;
import com.lilithsthrone.game.character.npc.submission.Elizabeth;
import com.lilithsthrone.game.character.npc.submission.Epona;
import com.lilithsthrone.game.character.npc.submission.FortressAlphaLeader;
import com.lilithsthrone.game.character.npc.submission.FortressFemalesLeader;
import com.lilithsthrone.game.character.npc.submission.FortressMalesLeader;
import com.lilithsthrone.game.character.npc.submission.Lyssieth;
import com.lilithsthrone.game.character.npc.submission.Murk;
import com.lilithsthrone.game.character.npc.submission.Roxy;
import com.lilithsthrone.game.character.npc.submission.Shadow;
import com.lilithsthrone.game.character.npc.submission.Silence;
import com.lilithsthrone.game.character.npc.submission.SlimeGuardFire;
import com.lilithsthrone.game.character.npc.submission.SlimeGuardIce;
import com.lilithsthrone.game.character.npc.submission.SlimeQueen;
import com.lilithsthrone.game.character.npc.submission.SlimeRoyalGuard;
import com.lilithsthrone.game.character.npc.submission.Takahashi;
import com.lilithsthrone.game.character.npc.submission.Vengar;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Table;

/**
 * @since 0.1.69.9
 * @version 0.4.2
 * @author Innoxia
 */
public interface ParserTarget {

	String getId();

	List<String> getTags();

	String getDescription();

	GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList);

	Special STYLE = Special.STYLE;
	Special UNIT = Special.UNIT;
	Special PC = Special.PC;
	Special NPC = Special.NPC;
	Special COMPANION = Special.COMPANION;
	Special NON_COMPANION = Special.NON_COMPANION;
	Special ELEMENTAL = Special.ELEMENTAL;

	enum Special implements ParserTarget {

		STYLE(List.of(
			"style",
			"game",
			"util"),
			"Returns the same as 'pc', but should be used for style methods such as style.bold or style.italics or conditional methods such as game.isArcaneStorm.") {
				@Override
				public GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) {
					return Main.game.getPlayer();
				}
		},

		UNIT(List.of(
			"unit",
			"units",
			"game"),
			"Returns the same as 'pc', but should be used for unit methods such as unit.size.") {
		@Override
		public GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) {
			return Main.game.getPlayer();
		}
		},

		PC(List.of(
			"pc",
			"player"),
			"The player character.") {
				@Override
				public GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) {
					return Main.game.getPlayer();
				}
		},
	
	/**
	 * The main parser tag for getting a hook on npcs when using UtilText's {@code parseFromXMLFile()} methods.
	 * <br/><b>Important note:</b> When trying to access npcs for parsing in external files, this method is likely to be unreliable and return npcs which you did not want.
	 * You should instead use the 'ncom' (standing for Non-COMpanion) parser tags to access npcs which are not members of the player's party, and 'com' (standing for COMpanion) tags for npcs which are members of the player's party.
	 * These parser tags will always return characters in the same order, so they are far safer to use than this 'npc' tag, which should only be used in the context of UtilText's {@code parseFromXMLFile()} method.
	 */
		NPC(List.of(
			"npc",
			"npc1",
			"npc2",
			"npc3",
			"npc4",
			"npc5",
			"npc6"),
			"The currently 'active' NPC.<br/>"
			+"<b>The tag 'npc' can be extended with a number, starting at 1, to signify which npc in the scene it is referring to!</b> e.g. 'npc1' is the first npc, 'npc2' is the second, etc.<br/>"
			+ "If in <b>combat</b>, it returns your opponent.<br/>"
			+ "If in <b>sex</b>, it returns your partner.<br/>"
			+ "<b>Otherwise</b>, it returns the most important NPC in the scene.") {
				@Override
				public GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) throws NullPointerException {
					if(specialNPCList!=null && !specialNPCList.isEmpty()) {
						if(tag.equalsIgnoreCase("npc")) {
							return specialNPCList.get(0);
						} else {
							return specialNPCList.get(Math.max(0, Integer.parseInt(tag.substring(3))-1));
						}
						
					} else if(Main.game.isInCombat()) {
						return Main.combat.getActiveNPC();
						
					} else if (Main.game.isInSex()) {
						return Main.sex.getTargetedPartner(Main.game.getPlayer());
						
					} else if (Main.game.getCurrentDialogueNode()!=null) {
						if(Main.game.getCurrentDialogueNode()==CharactersPresentDialogue.MENU
								 || Main.game.getCurrentDialogueNode()==PhoneDialogue.CONTACTS
								 || Main.game.getCurrentDialogueNode()==PhoneDialogue.CONTACTS_CHARACTER) {
							return CharactersPresentDialogue.characterViewed;
							
						} else if(Main.game.getActiveNPC()!=null) {
							return Main.game.getActiveNPC();
							
						} else if (!Main.game.getCharactersPresent().isEmpty()) {
							List<NPC> charactersPresent = Main.game.getCharactersPresent();
							if(tag.equalsIgnoreCase("npc")) {
								return charactersPresent.get(0);
							} else {
								return charactersPresent.get(Math.min(charactersPresent.size()-1, Math.max(0, Integer.parseInt(tag.substring(3))-1)));
							}
							
						} else {
							throw new NullPointerException();
						}
						
					} else {
						throw new NullPointerException();
					}
				}
		},
	
	/**
	 * Returns npcs which are members of the player's party.
	 * Ordering is based on the order in which companions were added to the party.
	 */
		COMPANION(List.of(
			"com",
			"com1",
			"com2",
			"com3",
			"com4",
			"com5",
			"com6"),
			"The companions of the player.<br/>"
			+"<b>The tag 'com' can be extended with a number, starting at 1, to signify which companion it is referring to!</b> e.g. 'com1' is the first companion, 'com2' is the second, etc.") {
				@Override
				public GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) throws NullPointerException {
					if(Main.game.getPlayer().getCompanions().size()>=1) {
						if(tag.equalsIgnoreCase("com")) {
							return Main.game.getPlayer().getCompanions().get(0);
							
						} else {
							int index = Integer.parseInt(tag.substring(tag.length()-1));
							if(Main.game.getPlayer().getCompanions().size()>=index) {
								return Main.game.getPlayer().getCompanions().get(Math.max(0, index-1));
							}
						}
					}
					throw new NullPointerException();
				}
		},

	/**
	 * Returns npcs which are not members of the player's party and which are present in the player's cell.
	 * Ordering is based on the npcs' id, so ordering will remain consistent across multiple parsing calls.
	 */
		NON_COMPANION(List.of(
			"ncom",
			"ncom1",
			"ncom2",
			"ncom3",
			"ncom4",
			"ncom5",
			"ncom6"),
			"The non-unique npcs who are in the player's cell and who are not members of the player's party.<br/>"
			+"<b>The tag 'ncom' can be extended with a number, starting at 1, to signify which npc it is referring to!</b> e.g. 'ncom1' is the first npc, 'ncom2' is the second, etc.") {
				@Override
				public GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) throws NullPointerException {
					if(!Main.game.getNonCompanionCharactersPresent().isEmpty()) {
						List<NPC> npcs = new ArrayList<>(Main.game.getNonCompanionCharactersPresent());
						npcs.removeIf(npc->npc.isUnique());
						Collections.sort(npcs, (n1, n2)->n1.getId().compareTo(n2.getId()));
						if(tag.equalsIgnoreCase("ncom")) {
							return npcs.get(0);
							
						} else {
							int index = Integer.parseInt(tag.substring(tag.length()-1));
							if(npcs.size()>=index) {
								return npcs.get(Math.max(0, index-1));
							}
						}
					}
					throw new NullPointerException();
				}
		},

		ELEMENTAL(List.of(
			"el",
			"elemental"),
			"The player's elemental. <b>Should only ever be used when you know for certain that the player's elemental has been created!</b>") {
		@Override
		public GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) {
			if(!Main.game.getPlayer().hasDiscoveredElemental()) {
//				System.err.println("Warning: Player's elemental not found when accessing ParserTarget.ELEMENTAL!");
				return Main.game.getNpc(GenericAndrogynousNPC.class);
			}
			return Main.game.getPlayer().getElemental();
		}
		};

		private final List<String> tags;
		private final String description;

		Special(List<String> t, String d) {
			tags = t;
			description = d;
		}

		@Override
		public String getId() {
			return toString();
		}

		@Override
		public List<String> getTags() {
			return tags;
		}

		@Override
		public String getDescription() {
			return description;
		}
	}
	enum CoreCharacter implements ParserTarget {

		PROLOGUE_MALE(PrologueMale.class,
			"prologueMale"),

		PROLOGUE_FEMALE(PrologueFemale.class,
			"prologueFemale"),

		NPC_MALE(GenericMaleNPC.class,
			"NPCmale",
			"maleNPC",
			"genericMale",
			"maleGeneric"),

		NPC_FEMALE(GenericFemaleNPC.class,
			"NPCfemale",
			"femaleNPC",
			"genericFemale",
			"femaleGeneric"),

		NPC_ANDROGYNOUS(GenericAndrogynousNPC.class,
			"NPCandrogynous",
			"androgynousNPC",
			"NPCambiguous",
			"ambiguousNPC"),

		NPC_TRADER(GenericTrader.class,
			"trader",
			"genericTrader"),

		TEST_NPC(TestNPC.class,
			"testNPC",
			"test"),

		LILAYA(Lilaya.class,
			"lilaya",
			"aunt"),

		ROSE(Rose.class,
			"rose"),

		KATE(Kate.class,
			"kate"),

		RALPH(Ralph.class,
			"ralph"),

		NYAN(Nyan.class,
			"nyan"),

		NYAN_MUM(NyanMum.class,
			"nyanmum",
			"nyanmom",
			"leotie"),

		VICKY(Vicky.class,
			"vicky"),

		PIX(Pix.class,
			"pix"),

		BRAX(Brax.class,
			"brax"),

		CANDI(CandiReceptionist.class,
			"candi"),

		VANESSA(Vanessa.class,
			"vanessa"),

		SCARLETT(Scarlett.class,
			"scarlett"),

		HELENA(Helena.class,
			"helena"),

		HARPY_BIMBO(HarpyBimbo.class,
			"brittany",
			"bimboHarpy",
			"harpyBimbo"),

		HARPY_BIMBO_COMPANION(HarpyBimboCompanion.class,
			"lauren",
			"bimboHarpyCompanion",
			"harpyBimboCompanion"),

		HARPY_DOMINANT(HarpyDominant.class,
			"diana",
			"dominantHarpy",
			"harpyDominant"),

		HARPY_DOMINANT_COMPANION(HarpyDominantCompanion.class,
			"harley",
			"dominantHarpyCompanion",
			"harpyDominantCompanion"),

		HARPY_NYMPHO(HarpyNympho.class,
			"lexi",
			"nymphoHarpy",
			"harpyNympho"),

		HARPY_NYMPHO_COMPANION(HarpyNymphoCompanion.class,
			"max",
			"nymphoHarpyCompanion",
			"harpyNymphoCompanion"),

		PAZU(Pazu.class,
			"pazu"),

		FINCH(Finch.class,
			"finch"),

		ZARANIX(Zaranix.class,
			"zaranix"),

		AMBER(Amber.class,
			"amber"),

		FELICIA(Felicia.class,
			"felicia"),

		ARTHUR(Arthur.class,
			"arthur"),

		KELLY(ZaranixMaidKelly.class,
			"kelly"),

		KATHERINE(ZaranixMaidKatherine.class,
			"katherine"),

		ASHLEY(Ashley.class,
			"ashley"),

		WOLFGANG(SupplierLeader.class,
			"wolfgang",
			"supplierLeader"),

		KARL(SupplierPartner.class,
			"karl",
			"supplierPartner"),

		ANGEL(Angel.class,
			"angel"),

		BUNNY(Bunny.class,
			"bunny"),

		LOPPY(Loppy.class,
			"loppy"),
	
		LUMI(Lumi.class,
			"lumi"),

		CLAIRE(Claire.class,
			"claire"),
	
		SLIME_QUEEN(SlimeQueen.class,
			"slimeQueen"),

		SLIME_GUARD_ICE(SlimeGuardIce.class,
			"slimeGuardIce", "slimeIce", "crystal"),
	
		SLIME_GUARD_FIRE(SlimeGuardFire.class,
			"slimeGuardFire", "slimeFire", "blaze"),

		SLIME_ROYAL_GUARD(SlimeRoyalGuard.class,
			"slimeRoyalGuard", "royalGuardSlime"),
	
		ROXY(Roxy.class,
			"roxy"),

		AXEL(Axel.class,
			"axel", "lexa"),
	
		EPONA(Epona.class,
			"epona"),

		JULES(Jules.class,
			"jules"),
	
		KRUGER(Kruger.class,
			"kruger"),

		KALAHARI(Kalahari.class,
			"kalahari"),

		RENTAL_MOMMY(RentalMommy.class,
			"rentalMommy"),

		DADDY(Daddy.class,
			"daddy", "desryth"),
	

	// Submission:
	
		IMP_FORTRESS_ALPHA_LEADER(FortressAlphaLeader.class,
			"impAlphaLeader"),

		IMP_FORTRESS_FEMALES_LEADER(FortressFemalesLeader.class,
			"impFemalesLeader", "impFemaleLeader"),

		IMP_FORTRESS_MALES_LEADER(FortressMalesLeader.class,
			"impMalesLeader", "impMaleLeader"),

		DARK_SIREN(DarkSiren.class,
			"darkSiren", "siren", "meraxis"),

		CITADEL_ARCANIST(Takahashi.class,
			"citadelArcanist", "youkoGuide", "hitomi", "takahashi"),

		LYSSIETH(Lyssieth.class,
			"lyssieth"),

		ELIZABETH(Elizabeth.class,
			"elizabeth"),

		VENGAR(Vengar.class,
			"vengar"),
	
		SHADOW(Shadow.class,
			"shadow"),

		SILENCE(Silence.class,
			"silence"),

		MURK(Murk.class,
			"murk"),

		SEAN(Sean.class,
			"sean"),
	
		NATALYA(Natalya.class,
			"natalya"),

		WES(Wes.class,
			"wes", "wesley"),
	
		ELLE(Elle.class,
			"elle", "aellasys"),

		KAY(Kay.class,
			"kay"),
	
		FLASH(Flash.class,
			"flash"),

		JESS(Jess.class,
			"jess"),
	
		ASTRAPI(Astrapi.class,
			"astrapi"),

		VRONTI(Vronti.class,
			"vronti"),
	
		ARION(Arion.class,
			"arion"),

		MINOTALLYS(Minotallys.class,
			"minotallys"),
	
		FAE(Fae.class,
			"fae"),

		SILVIA(Silvia.class,
			"silvia"),
	
		KHEIRON(Kheiron.class,
			"kheiron"),

		KAZIK(Kazik.class,
			"kazik"),
	
		YUI(Yui.class,
			"yui"),

		NIZHONI(Nizhoni.class,
			"nizhoni"),
	
		MORENO(Moreno.class,
			"moreno"),

		HEATHER(Heather.class,
			"heather"),
	
		ZIVA(Ziva.class,
			"ziva"),

		MONICA(Monica.class,
			"monica"),

		EVELYX(Evelyx.class,
			"evelyx"),

		DALE(Dale.class,
			"dale"),

		HEADLESS_HORSEMAN(HeadlessHorseman.class,
			"headlessHorseman", "headHorse"),

		CERIDWEN(Ceridwen.class,
			"ceridwen"),

		IMSU(Imsu.class,
			"imsu"),

		HALE(Hale.class,
			"hale"),

		PENELOPE(Penelope.class,
			"penelope"),

		BELLE(Belle.class,
			"belle"),

		DAPHNE(Daphne.class,
			"daphne"),

		FARAH(Farah.class,
			"farah"),
		CALLIE(Callie.class,
			"callie");

		private final Class<?extends NPC> character;
		private final List<String> tags;

		CoreCharacter(Class<?extends NPC> c, String... t) {
			character = c;
			tags = List.of(t);
		}

		@Override
		public String getId() {
			return toString();
		}

		@Override
		public List<String> getTags() {
			return tags;
		}

		@Override
		public String getDescription() {
			return Main.game.getNpc(character).getDescription();
		}

		@Override
		public GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) {
			return Main.game.getNpc(character);
		}
	}

	CoreCharacter PROLOGUE_MALE = CoreCharacter.PROLOGUE_MALE;
	CoreCharacter PROLOGUE_FEMALE = CoreCharacter.PROLOGUE_FEMALE;
	CoreCharacter NPC_MALE = CoreCharacter.NPC_MALE;
	CoreCharacter NPC_FEMALE = CoreCharacter.NPC_FEMALE;
	CoreCharacter NPC_ANDROGYNOUS = CoreCharacter.NPC_ANDROGYNOUS;
	CoreCharacter NPC_TRADER = CoreCharacter.NPC_TRADER;
	CoreCharacter TEST_NPC = CoreCharacter.TEST_NPC;
	CoreCharacter LILAYA = CoreCharacter.LILAYA;
	CoreCharacter ROSE = CoreCharacter.ROSE;
	CoreCharacter KATE = CoreCharacter.KATE;
	CoreCharacter RALPH = CoreCharacter.RALPH;
	CoreCharacter NYAN = CoreCharacter.NYAN;
	CoreCharacter NYAN_MUM = CoreCharacter.NYAN_MUM;
	CoreCharacter VICKY = CoreCharacter.VICKY;
	CoreCharacter PIX = CoreCharacter.PIX;
	CoreCharacter BRAX = CoreCharacter.BRAX;
	CoreCharacter CANDI = CoreCharacter.CANDI;
	CoreCharacter VANESSA = CoreCharacter.VANESSA;
	CoreCharacter SCARLETT = CoreCharacter.SCARLETT;
	CoreCharacter HELENA = CoreCharacter.HELENA;
	CoreCharacter HARPY_BIMBO = CoreCharacter.HARPY_BIMBO;
	CoreCharacter HARPY_BIMBO_COMPANION = CoreCharacter.HARPY_BIMBO_COMPANION;
	CoreCharacter HARPY_DOMINANT = CoreCharacter.HARPY_DOMINANT;
	CoreCharacter HARPY_DOMINANT_COMPANION = CoreCharacter.HARPY_DOMINANT_COMPANION;
	CoreCharacter HARPY_NYMPHO = CoreCharacter.HARPY_NYMPHO;
	CoreCharacter HARPY_NYMPHO_COMPANION = CoreCharacter.HARPY_NYMPHO_COMPANION;
	CoreCharacter PAZU = CoreCharacter.PAZU;
	CoreCharacter FINCH = CoreCharacter.FINCH;
	CoreCharacter ZARANIX = CoreCharacter.ZARANIX;
	CoreCharacter AMBER = CoreCharacter.AMBER;
	CoreCharacter FELICIA = CoreCharacter.FELICIA;
	CoreCharacter ARTHUR = CoreCharacter.ARTHUR;
	CoreCharacter KELLY = CoreCharacter.KELLY;
	CoreCharacter KATHERINE = CoreCharacter.KATHERINE;
	CoreCharacter ASHLEY = CoreCharacter.ASHLEY;
	CoreCharacter WOLFGANG = CoreCharacter.WOLFGANG;
	CoreCharacter KARL = CoreCharacter.KARL;
	CoreCharacter ANGEL = CoreCharacter.ANGEL;
	CoreCharacter BUNNY = CoreCharacter.BUNNY;
	CoreCharacter LOPPY = CoreCharacter.LOPPY;
	CoreCharacter LUMI = CoreCharacter.LUMI;
	CoreCharacter CLAIRE = CoreCharacter.CLAIRE;
	CoreCharacter SLIME_QUEEN = CoreCharacter.SLIME_QUEEN;
	CoreCharacter SLIME_GUARD_ICE = CoreCharacter.SLIME_GUARD_ICE;
	CoreCharacter SLIME_GUARD_FIRE = CoreCharacter.SLIME_GUARD_FIRE;
	CoreCharacter SLIME_ROYAL_GUARD = CoreCharacter.SLIME_ROYAL_GUARD;
	CoreCharacter ROXY = CoreCharacter.ROXY;
	CoreCharacter AXEL = CoreCharacter.AXEL;
	CoreCharacter EPONA = CoreCharacter.EPONA;
	CoreCharacter JULES = CoreCharacter.JULES;
	CoreCharacter KRUGER = CoreCharacter.KRUGER;
	CoreCharacter KALAHARI = CoreCharacter.KALAHARI;
	CoreCharacter RENTAL_MOMMY = CoreCharacter.RENTAL_MOMMY;
	CoreCharacter DADDY = CoreCharacter.DADDY;
	CoreCharacter IMP_FORTRESS_ALPHA_LEADER = CoreCharacter.IMP_FORTRESS_ALPHA_LEADER;
	CoreCharacter IMP_FORTRESS_FEMALES_LEADER = CoreCharacter.IMP_FORTRESS_FEMALES_LEADER;
	CoreCharacter IMP_FORTRESS_MALES_LEADER = CoreCharacter.IMP_FORTRESS_MALES_LEADER;
	CoreCharacter DARK_SIREN = CoreCharacter.DARK_SIREN;
	CoreCharacter CITADEL_ARCANIST = CoreCharacter.CITADEL_ARCANIST;
	CoreCharacter LYSSIETH = CoreCharacter.LYSSIETH;
	CoreCharacter ELIZABETH = CoreCharacter.ELIZABETH;
	CoreCharacter VENGAR = CoreCharacter.VENGAR;
	CoreCharacter SHADOW = CoreCharacter.SHADOW;
	CoreCharacter SILENCE = CoreCharacter.SILENCE;
	CoreCharacter MURK = CoreCharacter.MURK;
	CoreCharacter SEAN = CoreCharacter.SEAN;
	CoreCharacter NATALYA = CoreCharacter.NATALYA;
	CoreCharacter WES = CoreCharacter.WES;
	CoreCharacter ELLE = CoreCharacter.ELLE;
	CoreCharacter KAY = CoreCharacter.KAY;
	CoreCharacter FLASH = CoreCharacter.FLASH;
	CoreCharacter JESS = CoreCharacter.JESS;
	CoreCharacter ASTRAPI = CoreCharacter.ASTRAPI;
	CoreCharacter VRONTI = CoreCharacter.VRONTI;
	CoreCharacter ARION = CoreCharacter.ARION;
	CoreCharacter MINOTALLYS = CoreCharacter.MINOTALLYS;
	CoreCharacter FAE = CoreCharacter.FAE;
	CoreCharacter SILVIA = CoreCharacter.SILVIA;
	CoreCharacter KHEIRON = CoreCharacter.KHEIRON;
	CoreCharacter KAZIK = CoreCharacter.KAZIK;
	CoreCharacter YUI = CoreCharacter.YUI;
	CoreCharacter NIZHONI = CoreCharacter.NIZHONI;
	CoreCharacter MORENO = CoreCharacter.MORENO;
	CoreCharacter HEATHER = CoreCharacter.HEATHER;
	CoreCharacter ZIVA = CoreCharacter.ZIVA;
	CoreCharacter MONICA = CoreCharacter.MONICA;
	CoreCharacter EVELYX = CoreCharacter.EVELYX;
	CoreCharacter DALE = CoreCharacter.DALE;
	CoreCharacter HEADLESS_HORSEMAN = CoreCharacter.HEADLESS_HORSEMAN;
	CoreCharacter CERIDWEN = CoreCharacter.CERIDWEN;
	CoreCharacter IMSU = CoreCharacter.IMSU;
	CoreCharacter HALE = CoreCharacter.HALE;
	CoreCharacter PENELOPE = CoreCharacter.PENELOPE;
	CoreCharacter BELLE = CoreCharacter.BELLE;
	CoreCharacter DAPHNE = CoreCharacter.DAPHNE;
	CoreCharacter FARAH = CoreCharacter.FARAH;
	CoreCharacter CALLIE = CoreCharacter.CALLIE;

	@Deprecated
	public static List<ParserTarget> getAllParserTargets() {
		return table.list();
	}

	@Deprecated
	public static ParserTarget getParserTargetFromId(String id) {
		return table.of(id);
	}

	@Deprecated
	public static String getIdFromParserTarget(ParserTarget parserTarget) {
		return parserTarget.getId();
	}

	/**
	 * Adds an associated between the tag and the target for parsing.
	 */
	public static void addAdditionalParserTarget(String tag, NPC target) {
		var newParserTarget = new ParserTarget() {
			@Override
			public String getId() {
				return tag;
			}
			@Override
			public List<String> getTags() {
				return List.of(tag);
			}
			@Override
			public String getDescription() {
				return target.getDescription();
			}
			@Override
			public GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) {
				return target;
			}
		};
		var existing = table.exact(tag);
		if(existing.isPresent()) {
			System.err.println("Warning: Parser target of '"+tag+"' has been replaced!");
			removeAdditionalParserTarget((NPC)existing.get().getCharacter(null, null));
		}
		
		table.add(tag, newParserTarget);

		UtilText.addNewParserTarget(tag, target); // Add this parser target to the scripting engine
	}
	
	/**
	 * Removes map references to the specified NPC.
	 */
	public static void removeAdditionalParserTarget(NPC target) {
		ParserTarget targetToRemove = null;
		
		for(var parserTarget : table.list()) {
			if(!table.coreParserTargets.contains(parserTarget)) { // Do not remove core parser targets
				GameCharacter targetFound = parserTarget.getCharacter("", new ArrayList<>());
				if(targetFound!=null && targetFound.equals(target)) {
					targetToRemove = parserTarget;
					break;
				}
			}
		}
		
		if(targetToRemove!=null) {
			var idToRemove = targetToRemove.getId();
			table.remove(idToRemove);
			UtilText.removeParserTarget(idToRemove); // Remove this parser target from the scripting engine
		}
	}
	
	Collection table = new Collection();

	final class Collection extends Table<ParserTarget> {

		// A list of the hard-coded parser targets above.
		private final List<ParserTarget> coreParserTargets = new ArrayList<>();

		private Collection() {
			super(s->s);
			for(var v : Special.values())
				add(v.getId(),v);
			for(var v : CoreCharacter.values())
				add(v.getId(),v);
			coreParserTargets.addAll(list());
		}
	}
}
