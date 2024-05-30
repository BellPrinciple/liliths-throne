package com.lilithsthrone.game.character.race;

import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.LegConfigurationAffinity;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.Covering;
import com.lilithsthrone.game.character.body.types.AssType;
import com.lilithsthrone.game.character.body.types.PenisType;
import com.lilithsthrone.game.character.body.types.VaginaType;
import com.lilithsthrone.game.character.body.valueEnums.CoveringPattern;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.combat.CombatBehaviour;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

import java.util.Map;

public enum CoreRace implements Race {
	HUMAN,
	ANGEL,
	DEMON,
	DOG_MORPH,
	WOLF_MORPH,
	FOX_MORPH,
	CAT_MORPH,
	SQUIRREL_MORPH,
	RAT_MORPH,
	RABBIT_MORPH,
	BAT_MORPH,
	ALLIGATOR_MORPH,
	HORSE_MORPH,
	REINDEER_MORPH,
	COW_MORPH,
	HARPY,
	;

	@Override
	public String getId() {
		return name();
	}

	@Override
	public RacialBody getRacialBody() {
		return switch(this) {
			case HUMAN -> RacialBody.HUMAN;
			case ANGEL -> RacialBody.ANGEL;
			case DEMON -> RacialBody.DEMON;
			case DOG_MORPH -> RacialBody.DOG_MORPH;
			case WOLF_MORPH -> RacialBody.WOLF_MORPH;
			case FOX_MORPH -> RacialBody.FOX_MORPH;
			case CAT_MORPH -> RacialBody.CAT_MORPH;
			case SQUIRREL_MORPH -> RacialBody.SQUIRREL_MORPH;
			case RAT_MORPH -> RacialBody.RAT_MORPH;
			case RABBIT_MORPH -> RacialBody.RABBIT_MORPH;
			case BAT_MORPH -> RacialBody.BAT_MORPH;
			case ALLIGATOR_MORPH -> RacialBody.ALLIGATOR_MORPH;
			case HORSE_MORPH -> RacialBody.HORSE_MORPH;
			case REINDEER_MORPH -> RacialBody.REINDEER_MORPH;
			case COW_MORPH -> RacialBody.COW_MORPH;
			case HARPY -> RacialBody.HARPY;
		};
	}

	@Override
	public void applyRaceChanges(Body body) {
		switch(this) {
			case DOG_MORPH -> {
				if(body.getPenis().getType()== PenisType.DOG_MORPH
						|| body.getPenis().getType()==PenisType.DEMON_COMMON)
					body.getCoverings().put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, PresetColour.SKIN_RED));
			}
			case WOLF_MORPH -> {
				if(body.getPenis().getType()==PenisType.WOLF_MORPH
						|| body.getPenis().getType()==PenisType.DEMON_COMMON)
					body.getCoverings().put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, PresetColour.SKIN_RED));
			}
			case FOX_MORPH -> {
				if(body.getPenis().getType()==PenisType.FOX_MORPH
						|| body.getPenis().getType()==PenisType.DEMON_COMMON)
					body.getCoverings().put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, PresetColour.SKIN_RED));
			}
			case HORSE_MORPH -> {
				// 75% chance for genitals to be dark:
				if(Math.random()<0.75f) {
					Colour lightColour = Util.randomItemFrom(Util.newArrayListOfValues(
							PresetColour.SKIN_PALE,
							PresetColour.SKIN_LIGHT));
					Colour darkColour = Util.randomItemFrom(Util.newArrayListOfValues(
							PresetColour.SKIN_DARK,
							PresetColour.SKIN_CHOCOLATE,
							PresetColour.SKIN_EBONY));
					if(body.getPenis().getType()==PenisType.EQUINE) {
						Covering covering;
						if(Math.random()<0.66f) {
							covering = new Covering(BodyCoveringType.PENIS, darkColour);
						} else {
							covering = new Covering(BodyCoveringType.PENIS, CoveringPattern.MOTTLED, darkColour, false, lightColour, false);
						}
						body.getCoverings().put(BodyCoveringType.PENIS, covering);
					}
					if(body.getVagina().getType()== VaginaType.HORSE_MORPH)
						body.getCoverings().put(BodyCoveringType.VAGINA, new Covering(BodyCoveringType.VAGINA, darkColour, PresetColour.ORIFICE_INTERIOR));
					if(body.getAss().getType()== AssType.HORSE_MORPH)
						body.getCoverings().put(BodyCoveringType.ANUS, new Covering(BodyCoveringType.ANUS, darkColour, PresetColour.ORIFICE_INTERIOR));
				}
			}
			case RAT_MORPH -> {
				if(body.getPenis().getType()==PenisType.RAT_MORPH
						|| body.getPenis().getType()==PenisType.DEMON_COMMON)
					body.getCoverings().put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, PresetColour.SKIN_PINK_PALE));
			}
		}
		Race.super.applyRaceChanges(body);
	}

	@Override
	public boolean isFeralPartsAvailable() {
		return switch(this) {
			case HUMAN, ANGEL -> false;
			default -> true;
		};
	}

	@Override
	public boolean isFlyingRace() {
		return switch(this) {
			case BAT_MORPH, HARPY -> true;
			default -> false;
		};
	}

	@Override
	public boolean isAbleToSelfTransform() {
		return switch(this) {
			case ANGEL, DEMON -> true;
			default -> false;
		};
	}

	@Override
	public String getName(Body body, boolean feral) {
		return getName(body, feral, false);
	}

	@Override
	public String getNamePlural(Body body, boolean feral) {
		return getName(body, feral, true);
	}

	private String getName(Body body, boolean feral, boolean plural) {
		if(feral) {
			String specific = switch(this) {
				case DEMON -> {
					Subspecies halfDemonSubspecies = body == null ? null : body.getHalfDemonSubspecies();
					if(halfDemonSubspecies == null || halfDemonSubspecies == Subspecies.HUMAN)
						yield null;
					String suffix = plural ? halfDemonSubspecies.getFeralNamePlural(body) : halfDemonSubspecies.getFeralName(body);
					yield "demonic-" + suffix;
				}
				case WOLF_MORPH -> isSillyMode() ? plural ? "awoos" : "awoo" : null;
				case CAT_MORPH -> isSillyMode() ? plural ? "cattes" : "catte" : null;
				case HARPY -> isSillyMode() ? plural ? "birbs" : "birb" : null;
				default -> null;
			};
			return specific != null ? specific : getFeralName(new LegConfigurationAffinity(body), plural);
		}
		return switch(this) {
			case HUMAN -> plural ? "humans" : "human";
			case ANGEL -> plural ? "angels" : "angel";
			case DEMON -> plural ? "demons" : "demon";
			case DOG_MORPH -> plural ? "dog-morphs" : "dog-morph";
			case WOLF_MORPH -> isSillyMode()
					? plural ? "awoo-morphs" : "awoo-morph"
					: plural ? "wolf-morphs" : "wolf-morph";
			case FOX_MORPH -> plural ? "fox-morphs" : "fox-morph";
			case CAT_MORPH -> isSillyMode()
					? plural ? "catte-morphs" : "catte-morph"
					: plural ? "cat-morphs" : "cat-morph";
			case SQUIRREL_MORPH -> plural ? "squirrel-morphs" : "squirrel-morph";
			case RAT_MORPH -> plural ? "rat-morphs" : "rat-morph";
			case RABBIT_MORPH -> plural ? "rabbit-morphs" : "rabbit-morph";
			case BAT_MORPH -> plural ? "bat-morphs" : "bat-morph";
			case ALLIGATOR_MORPH -> plural ? "alligator-morphs" : "alligator-morph";
			case HORSE_MORPH -> plural ? "horse-morphs" : "horse-morph";
			case REINDEER_MORPH -> plural ? "reindeer-morphs" : "reindeer-morph";
			case COW_MORPH -> plural ? "cattle-morphs" : "cattle-morph";
			case HARPY -> isSillyMode()
					? plural ? "birbs" : "birb"
					: plural ? "harpies" : "harpy";
		};
	}

	private static boolean isSillyMode() {
		return Main.game != null && Main.game.isSillyModeEnabled();
	}

	@Override
	public String getFeralName(LegConfigurationAffinity legConfigurationAffinity, boolean plural) {
		return switch(this) {
			case HUMAN -> plural ? "humans" : "human";
			case ANGEL -> plural ? "angels" : "angel";
			case DEMON -> switch(legConfigurationAffinity.getLegConfiguration()) {
				case QUADRUPEDAL -> plural ? "demonic-horses" : "demonic-horse";
				case TAIL_LONG -> switch(legConfigurationAffinity.getAffinity()) {
					case AQUATIC -> plural ? "demonic-sea-serpents" : "demonic-sea-serpent";
					default -> plural ? "demonic-snakes" : "demonic-snake";
				};
				case TAIL -> "demonic-fish";
				case ARACHNID -> plural ? "demonic-spiders" : "demonic-spider";
				case CEPHALOPOD -> plural ? "demonic-octopuses" : "demonic-octopus";
				case AVIAN -> plural ? "demonic-eagles" : "demonic-eagle";
				default -> plural ? "demons" : "demon";
			};
			case DOG_MORPH -> plural ? "dogs" : "dog";
			case WOLF_MORPH -> plural ? "wolves" : "wolf";
			case FOX_MORPH -> plural ? "foxes" : "fox";
			case CAT_MORPH -> plural ? "cats" : "cat";
			case SQUIRREL_MORPH -> plural ? "squirrels" : "squirrel";
			case RAT_MORPH -> plural ? "rats" : "rat";
			case RABBIT_MORPH -> plural ? "rabbits" : "rabbit";
			case BAT_MORPH -> plural ? "bats" : "bat";
			case ALLIGATOR_MORPH -> plural ? "alligators" : "alligator";
			case HORSE_MORPH -> plural ? "horses" : "horse";
			case REINDEER_MORPH -> plural ? "reindeers" : "reindeer";
			case COW_MORPH -> "cattle";
			case HARPY -> plural ? "birds" : "bird";
		};
	}

	@Override
	public String getDefaultTransformName() {
		return switch(this) {
			case HUMAN -> "human";
			case ANGEL -> "angelic";
			case DEMON -> "demonic";
			case DOG_MORPH -> "dog";
			case WOLF_MORPH -> "wolf";
			case FOX_MORPH -> "fox";
			case CAT_MORPH -> "cat";
			case SQUIRREL_MORPH -> "squirrel";
			case RAT_MORPH -> "rat";
			case RABBIT_MORPH -> "rabbit";
			case BAT_MORPH -> "bat";
			case ALLIGATOR_MORPH -> "alligator";
			case HORSE_MORPH -> "horse";
			case REINDEER_MORPH -> "reindeer";
			case COW_MORPH -> "cattle";
			case HARPY -> "harpy";
		};
	}

	@Override
	public Disposition getDisposition() {
		return switch(this) {
			case WOLF_MORPH -> Disposition.SAVAGE;
			case FOX_MORPH -> Disposition.UNPREDICTABLE;
			case RAT_MORPH, RABBIT_MORPH, BAT_MORPH, ALLIGATOR_MORPH, HARPY -> Disposition.NEUTRAL;
			default -> Disposition.CIVILIZED;
		};
	}

	@Override
	public RacialClass getRacialClass() {
		return switch(this) {
			case ALLIGATOR_MORPH -> RacialClass.REPTILE;
			case HARPY -> RacialClass.BIRD;
			default -> RacialClass.MAMMAL;
		};
	}

	@Override
	public CombatBehaviour getPreferredCombatBehaviour() {
		return switch(this) {
			case ANGEL -> CombatBehaviour.SPELLS;
			case DEMON, HARPY -> CombatBehaviour.SEDUCE;
			case WOLF_MORPH -> CombatBehaviour.ATTACK;
			case FOX_MORPH, SQUIRREL_MORPH -> CombatBehaviour.SUPPORT;
			case RABBIT_MORPH, ALLIGATOR_MORPH -> CombatBehaviour.DEFEND;
			default -> CombatBehaviour.BALANCED;
		};
	}

	@Override
	public int getNumberOfOffspringLow() {
		return switch(this) {
			case DEMON, RABBIT_MORPH, HARPY -> 2;
			default -> 1;
		};
	}

	@Override
	public int getNumberOfOffspringHigh() {
		return switch(this) {
			case HUMAN, ANGEL, HORSE_MORPH, COW_MORPH -> 1;
			case DEMON -> 3;
			case RAT_MORPH, ALLIGATOR_MORPH, HARPY -> 4;
			case RABBIT_MORPH -> 8;
			default -> 2;
		};
	}

	@Override
	public Colour getColour() {
		return switch(this) {
			case HUMAN -> PresetColour.RACE_HUMAN;
			case ANGEL -> PresetColour.CLOTHING_WHITE;
			case DEMON -> PresetColour.RACE_DEMON;
			case DOG_MORPH -> PresetColour.RACE_DOG_MORPH;
			case WOLF_MORPH -> PresetColour.RACE_WOLF_MORPH;
			case FOX_MORPH -> PresetColour.RACE_FOX_MORPH;
			case CAT_MORPH -> PresetColour.RACE_CAT_MORPH;
			case SQUIRREL_MORPH -> PresetColour.RACE_SQUIRREL_MORPH;
			case RAT_MORPH -> PresetColour.RACE_RAT_MORPH;
			case RABBIT_MORPH -> PresetColour.RACE_RABBIT_MORPH;
			case BAT_MORPH -> PresetColour.RACE_BAT_MORPH;
			case ALLIGATOR_MORPH -> PresetColour.RACE_ALLIGATOR_MORPH;
			case HORSE_MORPH -> PresetColour.RACE_HORSE_MORPH;
			case REINDEER_MORPH -> PresetColour.RACE_REINDEER_MORPH;
			case COW_MORPH -> PresetColour.RACE_COW_MORPH;
			case HARPY -> PresetColour.RACE_HARPY;
		};
	}

	@Override
	public boolean isAffectedByFurryPreference() {
		return switch(this) {
			case HUMAN, ANGEL, DEMON, HARPY -> false;
			default -> true;
		};
	}

	@Override
	public float getChanceForMaleOffspring() {
		return switch(this) {
			case ANGEL -> .25f;
			case DEMON -> .1f;
			default -> .5f;
		};
	}

	@Override
	public FurryPreference getDefaultFemininePreference() {
		return switch(this) {
			case DEMON -> FurryPreference.MAXIMUM;
			default -> FurryPreference.NORMAL;
		};
	}

	@Override
	public FurryPreference getDefaultMasculinePreference() {
		return switch(this) {
			case DEMON -> FurryPreference.MAXIMUM;
			default -> FurryPreference.NORMAL;
		};
	}

	@Override
	public Map<Fetish, Map<String, Integer>> getRacialFetishModifiers() {
		return switch(this) {
			case COW_MORPH -> Map.of(
					Fetish.FETISH_BREASTS_SELF, Map.of("love", 5, "like", 5));
			case RABBIT_MORPH -> Map.of(
					Fetish.FETISH_IMPREGNATION, Map.of("love", 5, "like", 5),
					Fetish.FETISH_PREGNANCY, Map.of("love", 5, "like", 5));
			default -> Map.of();
		};
	}
}
