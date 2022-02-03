package com.lilithsthrone.rendering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.coverings.Covering;
import com.lilithsthrone.game.inventory.ColourReplacement;
import com.lilithsthrone.game.inventory.enchanting.ItemEffectType;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.utils.SvgUtil;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.1.0
 * @version 0.3.7.5
 * @author Innoxia
 */
public enum SVGImages {
	SVG_IMAGE_PROVIDER;

	private final String
	
			flagUs,
			
			fist,
			
			displacedIcon, concealedIcon, dirtyIcon, lipstickIcon, feminineWarningIcon, masculineWarningIcon, jinxedIcon, tattooSwitchTattoo, tattooSwitchClothing, tattooSwitchClothingHighlight, scarIcon,

			menuIcon,
			inventoryIcon, inventoryIconDisabled,
			questInventoryIcon, questInventoryIconDisabled,
			journalIcon, peopleIcon, zoomInIcon, zoomOutIcon, copyIcon, exportIcon, calendarIcon, informationIcon, addIcon,

			diskSave, diskSaveDisabled, diskSaveConfirm, diskOverwrite,
			diskLoad, diskLoadConfirm, diskLoadDisabled, diskLoadQuick,
			diskDelete, diskDeleteConfirm,
			
			essence, essenceUncoloured,
			itemsOnFloorIcon,
			
			cornerGlowNight, cornerGlowTwilight, cornerGlowAlwaysLight,
			
			drinkSmall, drink,
			
			dice1, dice2, dice3, dice4, dice5, dice6, diceGlow,
			
			playerMapIconMasculine,
			playerMapIconAndrogynous,
			playerMapIconFeminine,
			playerMapDangerousIcon,

			raceBackground,
			raceBackgroundHalf,
			raceBackgroundSlime,
			raceBackgroundDoll,
			raceBackgroundDemon,
			raceUnknown,
			raceDobermann,
			raceDobermannDesaturated,
			raceWisp,
			
			perkTreeArrow, spellOverlay,

			weatherDayClear, weatherDayCloud, weatherDayRain, weatherDaySnow, weatherDayStormIncoming, weatherDayStorm, weatherDayStormProtected,
			weatherNightClear, weatherNightCloud, weatherNightRain, weatherNightSnow, weatherNightStormIncoming, weatherNightStorm, weatherNightStormProtected,

			womensWatchHourHand, womensWatchMinuteHand, mensWatchHourHand, mensWatchMinuteHand,
			
			protectionEnabled, protectionDisabled, tattoo,
			
			responseCombat, responseSex, responseLocked, responseUnlocked, responseUnlockedDisabled, responseOption, responseOptionDisabled, responseCorruptionBypass,
			responseSubResist, responseSubNormal, responseSubEager,
			responseDomGentle, responseDomNormal, responseDomRough,
			responseSexSwitch, responseSexAdditional,
			
			NPCWarningMale, NPCWarningFemale, NPCWarningDemon,

			stopwatch,
			
			counterZero, counterOne, counterTwo, counterThree, counterFour, counterFive, counterFivePlus,
			counterZeroDisabled, counterOneDisabled, counterTwoDisabled, counterThreeDisabled, counterFourDisabled, counterFiveDisabled, counterFivePlusDisabled,
			
			scaleZero, scaleOne, scaleTwo, scaleThree, scaleFour,
			scaleZeroDisabled, scaleOneDisabled, scaleTwoDisabled, scaleThreeDisabled, scaleFourDisabled,

			slaveBuy, slaveBuyDisabled, slaveSell, slaveSellDisabled, slaveInspect, slaveInspectDisabled, slaveJob, slaveJobDisabled,
			slavePermissionsDisabled, slavePermissions, slaveTransfer, slaveTransferDisabled, slaveCosmetics, slaveCosmeticsDisabled,
			
			transactionBuy, transactionBuyDisabled, transactionBid, transactionBidDisabled, transactionSell, transactionSellDisabled,
			
			// Effects:
			creampie, creampieMasochist,
			fluidIngested, fluidIngestedMasochist,
			
			// Items:
			
			hypnoWatchBase, hypnoWatchGynephilic, hypnoWatchAmbiphilic, hypnoWatchAndrophilic, hypnoWatchSpeechAdd, hypnoWatchSpeechRemove,
			
			// Sex:
			coverableAreaMouth,
			coverableAreaAnus, coverableAreaAss,
			coverableAreaBreasts, coverableAreaBreastsFlat, coverableAreaNipple,
			coverableAreaBreastsCrotch, coverableAreaUdders, coverableAreaNippleCrotch,
			coverableAreaVagina, coverableAreaUrethraVagina,
			coverableAreaSpinneret,
			coverableAreaMound,
			coverableAreaThighs,
			coverableAreaArmpits,
			coverableAreaUrethraPenis,

			eggIncubation1, eggIncubation2, eggIncubation3,
			
			penetrationTypeFinger, penetrationTypePenis, penetrationTypeTail, penetrationTypeTailSerpent, penetrationTypeTentacle, penetrationTypeTongue, penetrationTypeFoot, penetrationTypeClit,
			combinationStretching, combinationStretchRecoveryPrevented, combinationTooLoose, combinationWet, combinationDry, combinationDepthMinimum, combinationDepthMaximum,
			stretching, holeTooBig,
			activeSexBackground;

	private Map<Integer, String> youkoTailsMap = new HashMap<>();
	private Map<Integer, String> youkoTailsDesaturatedMap = new HashMap<>();
	private Map<Integer, String> youkoTailsDemonMap = new HashMap<>();
	

	private Map<Colour, String> refinedBackgroundMap = new HashMap<>();
	private Map<Colour, String> refinedSwirlsMap = new HashMap<>();

	SVGImages() {
		flagUs = load("/com/lilithsthrone/res/UIElements/flag_us.svg");
		fist = load("/com/lilithsthrone/res/UIElements/fist.svg", PresetColour.BASE_BLACK);
		displacedIcon = load("/com/lilithsthrone/res/InventoryIcons/displacedWarningIcon.svg");
		concealedIcon = load("/com/lilithsthrone/res/InventoryIcons/concealed.svg");
		dirtyIcon = load("/com/lilithsthrone/res/InventoryIcons/dirtyIcon.svg");
		lipstickIcon = load("/com/lilithsthrone/res/InventoryIcons/lipstickIcon.svg");
		feminineWarningIcon = load("/com/lilithsthrone/res/InventoryIcons/feminineWarningIcon.svg", PresetColour.FEMININE);
		masculineWarningIcon = load("/com/lilithsthrone/res/InventoryIcons/masculineWarningIcon.svg", PresetColour.BASE_BLUE_LIGHT);
		jinxedIcon = load("/com/lilithsthrone/res/InventoryIcons/jinxed.svg", PresetColour.ATTRIBUTE_CORRUPTION);
		tattooSwitchTattoo = load("/com/lilithsthrone/res/InventoryIcons/tattooSwitchTattoo.svg", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_WHITE, null);
		tattooSwitchClothing = load("/com/lilithsthrone/res/InventoryIcons/tattooSwitchClothing.svg");
		tattooSwitchClothingHighlight = load("/com/lilithsthrone/res/InventoryIcons/tattooSwitchClothing.svg", PresetColour.CLOTHING_WHITE, PresetColour.GENERIC_EXCELLENT, PresetColour.CLOTHING_BLACK);
		scarIcon = load("/com/lilithsthrone/res/InventoryIcons/scar.svg");
		menuIcon = load("/com/lilithsthrone/res/UIElements/menu.svg");
		inventoryIcon = load("/com/lilithsthrone/res/UIElements/inventory.svg", PresetColour.BASE_BLACK);
		inventoryIconDisabled = load("/com/lilithsthrone/res/UIElements/inventory.svg", PresetColour.BASE_PITCH_BLACK);
		questInventoryIcon = load("/com/lilithsthrone/res/UIElements/questInventory.svg", PresetColour.BASE_BLACK);
		questInventoryIconDisabled = load("/com/lilithsthrone/res/UIElements/questInventory.svg", PresetColour.BASE_PITCH_BLACK);
		essence = load("/com/lilithsthrone/res/crafting/essenceArcane.svg", PresetColour.GENERIC_ARCANE);
		essenceUncoloured = load("/com/lilithsthrone/res/crafting/essenceArcane.svg", PresetColour.BASE_GREY);
		itemsOnFloorIcon = load("/com/lilithsthrone/res/UIElements/itemsOnFloor.svg", PresetColour.BASE_BLACK, PresetColour.BASE_YELLOW, PresetColour.BASE_BLACK);
		cornerGlowNight = load("/com/lilithsthrone/res/UIElements/cornerGlow.svg", PresetColour.BASE_PITCH_BLACK);
		cornerGlowTwilight = load("/com/lilithsthrone/res/UIElements/cornerGlowLight.svg", PresetColour.BASE_PITCH_BLACK);
		cornerGlowAlwaysLight = load("/com/lilithsthrone/res/UIElements/cornerGlowAlwaysLight.svg", PresetColour.BASE_PITCH_BLACK);
		drinkSmall = load("/com/lilithsthrone/res/UIElements/drink_small.svg", PresetColour.BASE_WHITE);
		drink = load("/com/lilithsthrone/res/UIElements/drink.svg", PresetColour.BASE_WHITE);
		dice1 = load("/com/lilithsthrone/res/UIElements/dice1.svg", PresetColour.BASE_WHITE);
		dice2 = load("/com/lilithsthrone/res/UIElements/dice2.svg", PresetColour.BASE_WHITE);
		dice3 = load("/com/lilithsthrone/res/UIElements/dice3.svg", PresetColour.BASE_WHITE);
		dice4 = load("/com/lilithsthrone/res/UIElements/dice4.svg", PresetColour.BASE_WHITE);
		dice5 = load("/com/lilithsthrone/res/UIElements/dice5.svg", PresetColour.BASE_WHITE);
		dice6 = load("/com/lilithsthrone/res/UIElements/dice6.svg", PresetColour.BASE_WHITE);
		diceGlow = load("/com/lilithsthrone/res/UIElements/glow.svg", PresetColour.BASE_GOLD);
		journalIcon = load("/com/lilithsthrone/res/UIElements/journal.svg");
		peopleIcon = load("/com/lilithsthrone/res/UIElements/people.svg");
		zoomInIcon = load("/com/lilithsthrone/res/UIElements/zoomIn.svg");
		zoomOutIcon = load("/com/lilithsthrone/res/UIElements/zoomOut.svg");
		copyIcon = load("/com/lilithsthrone/res/UIElements/copy.svg", PresetColour.BASE_BLACK);
		exportIcon = load("/com/lilithsthrone/res/UIElements/export.svg", PresetColour.BASE_BLACK);
		informationIcon = load("/com/lilithsthrone/res/UIElements/information.svg", PresetColour.BASE_BLACK);
		addIcon = load("/com/lilithsthrone/res/UIElements/add.svg", PresetColour.BASE_BLACK);
		calendarIcon = load("/com/lilithsthrone/res/UIElements/calendar.svg", PresetColour.BASE_CRIMSON);
		diskSave = load("/com/lilithsthrone/res/UIElements/diskSave.svg", PresetColour.BASE_BLACK, PresetColour.BASE_YELLOW_LIGHT, PresetColour.BASE_GREY);
		diskSaveDisabled = load("/com/lilithsthrone/res/UIElements/diskSave.svg", PresetColour.BASE_GREY, PresetColour.BASE_GREY, PresetColour.BASE_GREY);
		diskOverwrite = load("/com/lilithsthrone/res/UIElements/diskSave.svg", PresetColour.BASE_BLACK, PresetColour.BASE_YELLOW_LIGHT, PresetColour.BASE_GREY);
		diskSaveConfirm = load("/com/lilithsthrone/res/UIElements/diskSave.svg", PresetColour.GENERIC_EXCELLENT, PresetColour.BASE_YELLOW_LIGHT, PresetColour.BASE_GREY);
		diskLoad = load("/com/lilithsthrone/res/UIElements/diskLoad.svg", PresetColour.BASE_BLUE_LIGHT);
		diskLoadConfirm = load("/com/lilithsthrone/res/UIElements/diskLoad.svg", PresetColour.GENERIC_EXCELLENT);
		diskLoadDisabled = load("/com/lilithsthrone/res/UIElements/diskLoad.svg", PresetColour.BASE_GREY);
		diskLoadQuick = load("/com/lilithsthrone/res/UIElements/diskLoad.svg", PresetColour.BASE_WHITE);
		diskDelete = load("/com/lilithsthrone/res/UIElements/diskDelete.svg", PresetColour.BASE_CRIMSON);
		diskDeleteConfirm = load("/com/lilithsthrone/res/UIElements/diskDelete.svg", PresetColour.GENERIC_EXCELLENT);
		playerMapIconMasculine = load("/com/lilithsthrone/res/map/playerIcon.svg", PresetColour.MASCULINE_PLUS);
		playerMapIconAndrogynous = load("/com/lilithsthrone/res/map/playerIcon.svg", PresetColour.ANDROGYNOUS);
		playerMapIconFeminine = load("/com/lilithsthrone/res/map/playerIcon.svg", PresetColour.FEMININE_PLUS);
		playerMapDangerousIcon = load("/com/lilithsthrone/res/map/playerIcon.svg", PresetColour.GENERIC_BAD);
		raceBackground = load("/com/lilithsthrone/res/statusEffects/race/raceBackground.svg");
		raceBackgroundHalf = load("/com/lilithsthrone/res/statusEffects/race/raceBackgroundHalf.svg");
		raceBackgroundSlime = load("/com/lilithsthrone/res/statusEffects/race/raceBackgroundSlime.svg");
		raceBackgroundDoll = load("/com/lilithsthrone/res/statusEffects/race/raceBackgroundDoll.svg");
		raceBackgroundDemon = load("/com/lilithsthrone/res/statusEffects/race/raceBackgroundDemon.svg");
		raceUnknown = load("/com/lilithsthrone/res/statusEffects/race/raceUnknown.svg", PresetColour.RACE_UNKNOWN);
		raceDobermann = load("/com/lilithsthrone/res/statusEffects/race/raceDogMorphDobermann.svg", PresetColour.RACE_DOG_MORPH);
		raceDobermannDesaturated = load("/com/lilithsthrone/res/statusEffects/race/raceDogMorphDobermann.svg", PresetColour.BASE_GREY);
		raceWisp = load("/com/lilithsthrone/res/statusEffects/race/raceWisp.svg");
		perkTreeArrow = load("/com/lilithsthrone/res/UIElements/perkTreeArrow.svg");
		spellOverlay = load("/com/lilithsthrone/res/combat/spell/spell_overlay.svg");
		womensWatchHourHand = load("/com/lilithsthrone/res/clothing/wrist_womens_watch_hourhand.svg");
		womensWatchMinuteHand = load("/com/lilithsthrone/res/clothing/wrist_womens_watch_minutehand.svg");
		mensWatchHourHand = load("/com/lilithsthrone/res/clothing/wrist_mens_watch_hourhand.svg");
		mensWatchMinuteHand = load("/com/lilithsthrone/res/clothing/wrist_mens_watch_minutehand.svg");
		weatherDayClear = load("/com/lilithsthrone/res/statusEffects/weatherDayClear.svg");
		weatherDayCloud = load("/com/lilithsthrone/res/statusEffects/weatherDayCloudy.svg");
		weatherDayRain = load("/com/lilithsthrone/res/statusEffects/weatherDayRain.svg");
		weatherDaySnow = load("/com/lilithsthrone/res/statusEffects/weatherDaySnow.svg");
		weatherDayStormIncoming = load("/com/lilithsthrone/res/statusEffects/weatherDayStormIncoming.svg");
		weatherDayStorm = load("/com/lilithsthrone/res/statusEffects/weatherDayStorm.svg");
		weatherDayStormProtected = load("/com/lilithsthrone/res/statusEffects/weatherDayStormProtected.svg", PresetColour.CLOTHING_BLUE_LIGHT);
		weatherNightClear = load("/com/lilithsthrone/res/statusEffects/weatherNightClear.svg");
		weatherNightCloud = load("/com/lilithsthrone/res/statusEffects/weatherNightCloudy.svg");
		weatherNightRain = load("/com/lilithsthrone/res/statusEffects/weatherNightRain.svg");
		weatherNightSnow = load("/com/lilithsthrone/res/statusEffects/weatherNightSnow.svg");
		weatherNightStormIncoming = load("/com/lilithsthrone/res/statusEffects/weatherNightStormIncoming.svg");
		weatherNightStorm = load("/com/lilithsthrone/res/statusEffects/weatherNightStorm.svg");
		weatherNightStormProtected = load("/com/lilithsthrone/res/statusEffects/weatherNightStormProtected.svg", PresetColour.CLOTHING_BLUE_LIGHT);
		protectionDisabled = load("/com/lilithsthrone/res/UIElements/protection.svg", PresetColour.CLOTHING_BLACK);
		protectionEnabled = load("/com/lilithsthrone/res/UIElements/protection.svg", PresetColour.GENERIC_GOOD);
		tattoo = load("/com/lilithsthrone/res/UIElements/tattoo.svg", PresetColour.CLOTHING_BLACK);
		responseCombat = load("/com/lilithsthrone/res/UIElements/responseCombat.svg");
		responseSex = load("/com/lilithsthrone/res/UIElements/responseSex.svg");
		responseLocked = load("/com/lilithsthrone/res/UIElements/responseLocked.svg", PresetColour.GENERIC_BAD);
		responseUnlocked = load("/com/lilithsthrone/res/UIElements/responseUnlocked.svg", PresetColour.GENERIC_GOOD);
		responseUnlockedDisabled = load("/com/lilithsthrone/res/UIElements/responseUnlocked.svg", PresetColour.BASE_BLACK);
		responseOption = load("/com/lilithsthrone/res/UIElements/responseOption.svg", PresetColour.GENERIC_GOOD);
		responseOptionDisabled = load("/com/lilithsthrone/res/UIElements/responseOption.svg", PresetColour.BASE_BLACK);
		responseCorruptionBypass = load("/com/lilithsthrone/res/UIElements/responseCorruptionBypass.svg", PresetColour.GENERIC_ARCANE);
		responseSubResist = load("/com/lilithsthrone/res/UIElements/responseSubResist.svg");
		responseSubNormal = load("/com/lilithsthrone/res/UIElements/responseSubNormal.svg");
		responseSubEager = load("/com/lilithsthrone/res/UIElements/responseSubEager.svg");
		responseDomGentle = load("/com/lilithsthrone/res/UIElements/responseDomGentle.svg");
		responseDomNormal = load("/com/lilithsthrone/res/UIElements/responseDomNormal.svg");
		responseDomRough = load("/com/lilithsthrone/res/UIElements/responseDomRough.svg");
		responseSexSwitch = load("/com/lilithsthrone/res/UIElements/responseSexSwitch.svg");
		responseSexAdditional = load("/com/lilithsthrone/res/UIElements/responseSexAdditional.svg");
		NPCWarningMale = load("/com/lilithsthrone/res/UIElements/responseNPC.svg", PresetColour.MASCULINE_PLUS);
		NPCWarningFemale = load("/com/lilithsthrone/res/UIElements/responseNPC.svg", PresetColour.FEMININE_PLUS);
		NPCWarningDemon = load("/com/lilithsthrone/res/UIElements/responseNPC.svg", PresetColour.GENERIC_ARCANE);
		stopwatch = load("/com/lilithsthrone/res/UIElements/stopwatch.svg", PresetColour.BASE_GREY);
		// scales:
		counterZero = load("/com/lilithsthrone/res/fetishes/overlay0.svg", PresetColour.BASE_PINK);
		counterOne = load("/com/lilithsthrone/res/fetishes/overlay1.svg", PresetColour.BASE_PINK);
		counterTwo = load("/com/lilithsthrone/res/fetishes/overlay2.svg", PresetColour.BASE_PINK);
		counterThree = load("/com/lilithsthrone/res/fetishes/overlay3.svg", PresetColour.BASE_PINK);
		counterFour = load("/com/lilithsthrone/res/fetishes/overlay4.svg", PresetColour.BASE_PINK);
		counterFive = load("/com/lilithsthrone/res/fetishes/overlay5.svg", PresetColour.BASE_PINK);
		counterFivePlus = load("/com/lilithsthrone/res/fetishes/overlay5Plus.svg", PresetColour.BASE_PINK);
		counterZeroDisabled = load("/com/lilithsthrone/res/fetishes/overlay0.svg", PresetColour.BASE_BLACK);
		counterOneDisabled = load("/com/lilithsthrone/res/fetishes/overlay1.svg", PresetColour.BASE_BLACK);
		counterTwoDisabled = load("/com/lilithsthrone/res/fetishes/overlay2.svg", PresetColour.BASE_BLACK);
		counterThreeDisabled = load("/com/lilithsthrone/res/fetishes/overlay3.svg", PresetColour.BASE_BLACK);
		counterFourDisabled = load("/com/lilithsthrone/res/fetishes/overlay4.svg", PresetColour.BASE_BLACK);
		counterFiveDisabled = load("/com/lilithsthrone/res/fetishes/overlay5.svg", PresetColour.BASE_BLACK);
		counterFivePlusDisabled = load("/com/lilithsthrone/res/fetishes/overlay5Plus.svg", PresetColour.BASE_BLACK);
		scaleZero = load("/com/lilithsthrone/res/UIElements/scale_zero.svg", PresetColour.BASE_MAGENTA);
		scaleOne = load("/com/lilithsthrone/res/UIElements/scale_one.svg", PresetColour.BASE_GREEN);
		scaleTwo = load("/com/lilithsthrone/res/UIElements/scale_two.svg", PresetColour.BASE_GREEN);
		scaleThree = load("/com/lilithsthrone/res/UIElements/scale_three.svg", PresetColour.BASE_GREEN);
		scaleFour = load("/com/lilithsthrone/res/UIElements/scale_four.svg", PresetColour.BASE_GREEN);
		scaleZeroDisabled = load("/com/lilithsthrone/res/UIElements/scale_zero.svg", PresetColour.BASE_GREY);
		scaleOneDisabled = load("/com/lilithsthrone/res/UIElements/scale_one.svg", PresetColour.BASE_GREY);
		scaleTwoDisabled = load("/com/lilithsthrone/res/UIElements/scale_two.svg", PresetColour.BASE_GREY);
		scaleThreeDisabled = load("/com/lilithsthrone/res/UIElements/scale_three.svg", PresetColour.BASE_GREY);
		scaleFourDisabled = load("/com/lilithsthrone/res/UIElements/scale_four.svg", PresetColour.BASE_GREY);
		slaveBuy = load("/com/lilithsthrone/res/UIElements/slaveBuy.svg", PresetColour.GENERIC_BAD);
		slaveBuyDisabled = load("/com/lilithsthrone/res/UIElements/slaveBuyDisabled.svg", PresetColour.BASE_GREY);
		slaveSell = load("/com/lilithsthrone/res/UIElements/slaveSell.svg", PresetColour.GENERIC_GOOD);
		slaveSellDisabled = load("/com/lilithsthrone/res/UIElements/slaveSellDisabled.svg", PresetColour.BASE_GREY);
		slaveInspect = load("/com/lilithsthrone/res/UIElements/slaveInspect.svg", PresetColour.BASE_BLUE_STEEL);
		slaveInspectDisabled = load("/com/lilithsthrone/res/UIElements/slaveInspect.svg", PresetColour.BASE_GREY);
		slaveJob = load("/com/lilithsthrone/res/UIElements/slaveJob.svg", PresetColour.BASE_BROWN_DARK);
		slaveJobDisabled = load("/com/lilithsthrone/res/UIElements/slaveJob.svg", PresetColour.BASE_GREY);
		slavePermissions = load("/com/lilithsthrone/res/UIElements/slavePermissions.svg", PresetColour.BASE_GREY);
		slavePermissionsDisabled = load("/com/lilithsthrone/res/UIElements/slavePermissionsDisabled.svg", PresetColour.BASE_GREY);
		slaveTransfer = load("/com/lilithsthrone/res/UIElements/slaveTransfer.svg", PresetColour.GENERIC_GOOD);
		slaveTransferDisabled = load("/com/lilithsthrone/res/UIElements/slaveTransfer.svg", PresetColour.BASE_GREY);
		slaveCosmetics = load("/com/lilithsthrone/res/UIElements/slaveCosmetics.svg", PresetColour.BASE_CRIMSON);
		slaveCosmeticsDisabled = load("/com/lilithsthrone/res/UIElements/slaveCosmetics.svg", PresetColour.BASE_GREY);
		transactionBuy = load("/com/lilithsthrone/res/UIElements/transactionBuy.svg");
		transactionBuyDisabled = load("/com/lilithsthrone/res/UIElements/transactionBuyDisabled.svg", PresetColour.BASE_GREY);
		transactionBid = load("/com/lilithsthrone/res/UIElements/transactionBid.svg", PresetColour.BASE_BROWN);
		transactionBidDisabled = load("/com/lilithsthrone/res/UIElements/transactionBid.svg", PresetColour.BASE_GREY);
		transactionSell = load("/com/lilithsthrone/res/UIElements/transactionSell.svg");
		transactionSellDisabled = load("/com/lilithsthrone/res/UIElements/transactionSellDisabled.svg", PresetColour.BASE_GREY);
		for(int i=1; i<=9; i++) {
			String youkoTail = load("/com/lilithsthrone/res/statusEffects/race/raceFoxTail"+i+".svg");
			String youkoTailColoured = SvgUtil.colourReplacement("foxTail"+i,
					PresetColour.RACE_FOX_MORPH,
					PresetColour.RACE_FOX_MORPH,
					PresetColour.RACE_FOX_MORPH,
					youkoTail);
			youkoTailsMap.put(i, youkoTailColoured);
			String youkoTailDemon = load("/com/lilithsthrone/res/statusEffects/race/raceFoxTail"+i+".svg");
			String youkoTailDemonColoured = SvgUtil.colourReplacement("foxTail"+i,
					PresetColour.RACE_HALF_DEMON,
					PresetColour.RACE_HALF_DEMON,
					PresetColour.RACE_HALF_DEMON,
					youkoTailDemon);
			youkoTailsDemonMap.put(i, youkoTailDemonColoured);
			String youkoTailDesaturated = load("/com/lilithsthrone/res/statusEffects/race/raceFoxTail"+i+".svg");
			String youkoTailDesaturatedColoured = SvgUtil.colourReplacement("foxTail"+i,
					PresetColour.BASE_GREY,
					PresetColour.BASE_GREY,
					PresetColour.BASE_GREY,
					youkoTailDesaturated);
			youkoTailsDesaturatedMap.put(i, youkoTailDesaturatedColoured);
		}
		// Effects:
		creampie = load("/com/lilithsthrone/res/statusEffects/creampie.svg");
		creampieMasochist = load("/com/lilithsthrone/res/statusEffects/creampieMasochist.svg");
		fluidIngested = load("/com/lilithsthrone/res/statusEffects/fluidIngested.svg");
		fluidIngestedMasochist = load("/com/lilithsthrone/res/statusEffects/fluidIngestedMasochist.svg");
		// Items:
		hypnoWatchBase = load("/com/lilithsthrone/res/items/hypnoClockBase.svg", PresetColour.BASE_GREY);
		hypnoWatchGynephilic = load("/com/lilithsthrone/res/items/hypnoClockEnchanted.svg", PresetColour.FEMININE_PLUS, PresetColour.CLOTHING_ROSE_GOLD, null);
		hypnoWatchAmbiphilic = load("/com/lilithsthrone/res/items/hypnoClockEnchanted.svg", PresetColour.ANDROGYNOUS, PresetColour.CLOTHING_SILVER, null);
		hypnoWatchAndrophilic = load("/com/lilithsthrone/res/items/hypnoClockEnchanted.svg", PresetColour.MASCULINE_PLUS, PresetColour.CLOTHING_BLACK_STEEL, null);
		hypnoWatchSpeechRemove = load("/com/lilithsthrone/res/items/hypnoClockEnchanted.svg", PresetColour.BASE_RED, PresetColour.CLOTHING_GOLD, null);
		hypnoWatchSpeechAdd = load("/com/lilithsthrone/res/items/hypnoClockEnchanted.svg", PresetColour.BASE_GREEN, PresetColour.CLOTHING_GOLD, null);
		// Sex:
		coverableAreaAnus = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaAnus.svg");
		coverableAreaAss = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaAss.svg");
		coverableAreaMouth = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaMouth.svg");
		coverableAreaBreasts = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaBreasts.svg");
		coverableAreaBreastsFlat = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaBreastsFlat.svg");
		coverableAreaNipple = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaNipple.svg");
		coverableAreaVagina = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaVagina.svg");
		coverableAreaMound = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaMound.svg");
		coverableAreaSpinneret = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaSpinneret.svg");
		coverableAreaBreastsCrotch = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaBreastsCrotch.svg");
		coverableAreaUdders = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaUdders.svg");
		coverableAreaNippleCrotch = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaNippleCrotch.svg");
		coverableAreaUrethraVagina = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaUrethraVagina.svg");
		coverableAreaUrethraPenis = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaUrethraPenis.svg");
		coverableAreaThighs = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaThighs.svg");
		coverableAreaArmpits = load("/com/lilithsthrone/res/statusEffects/sexEffects/coverableAreaArmpits.svg");
		penetrationTypeFinger = load("/com/lilithsthrone/res/statusEffects/sexEffects/penetrationTypeFinger.svg");
		penetrationTypePenis = load("/com/lilithsthrone/res/statusEffects/sexEffects/penetrationTypePenis.svg");
		penetrationTypeTail = load("/com/lilithsthrone/res/statusEffects/sexEffects/penetrationTypeTail.svg");
		penetrationTypeTailSerpent = load("/com/lilithsthrone/res/statusEffects/sexEffects/penetrationTypeTailSerpent.svg");
		penetrationTypeTentacle = load("/com/lilithsthrone/res/statusEffects/sexEffects/penetrationTypeTentacle.svg");
		penetrationTypeTongue = load("/com/lilithsthrone/res/statusEffects/sexEffects/penetrationTypeTongue.svg");
		penetrationTypeFoot = load("/com/lilithsthrone/res/statusEffects/sexEffects/penetrationTypeFoot.svg");
		penetrationTypeClit = load("/com/lilithsthrone/res/statusEffects/sexEffects/penetrationTypeClit.svg");
		eggIncubation1 = load("/com/lilithsthrone/res/statusEffects/incubation1.svg");
		eggIncubation2 = load("/com/lilithsthrone/res/statusEffects/incubation2.svg");
		eggIncubation3 = load("/com/lilithsthrone/res/statusEffects/incubation3.svg");
		combinationStretching = SvgUtil.colourReplacement("", PresetColour.BASE_PINK_DEEP, load("/com/lilithsthrone/res/statusEffects/sexEffects/combinationStretching.svg"));
		combinationStretchRecoveryPrevented = SvgUtil.colourReplacement("", PresetColour.BASE_PINK_DEEP, PresetColour.GENERIC_BAD, null, load("/com/lilithsthrone/res/statusEffects/sexEffects/combinationStretchRecoveryPrevented.svg"));
		combinationTooLoose = SvgUtil.colourReplacement("", PresetColour.BASE_RED, load("/com/lilithsthrone/res/statusEffects/sexEffects/combinationTooLoose.svg"));
		combinationWet = SvgUtil.colourReplacement("", PresetColour.BASE_BLUE_STEEL, load("/com/lilithsthrone/res/statusEffects/sexEffects/combinationWet.svg"));
		combinationDry = SvgUtil.colourReplacement("", PresetColour.BASE_BLUE_STEEL, PresetColour.BASE_RED, null, load("/com/lilithsthrone/res/statusEffects/sexEffects/combinationDry.svg"));
		combinationDepthMinimum = SvgUtil.colourReplacement("", PresetColour.BASE_PINK_LIGHT, load("/com/lilithsthrone/res/statusEffects/sexEffects/combinationDepthMinimum.svg"));
		combinationDepthMaximum = SvgUtil.colourReplacement("", PresetColour.BASE_CRIMSON, load("/com/lilithsthrone/res/statusEffects/sexEffects/combinationDepthMaximum.svg"));
		stretching = load("/com/lilithsthrone/res/statusEffects/sexEffects/stretching.svg");
		holeTooBig = load("/com/lilithsthrone/res/statusEffects/sexEffects/holeTooBig.svg");
		activeSexBackground = load("/com/lilithsthrone/res/statusEffects/sexEffects/active_background.svg");
		for(ItemEffectType effect : ItemEffectType.getAllEffectTypes()) {
			String tempString = load("/com/lilithsthrone/res/items/refined_background.svg", effect.getColour());
			refinedBackgroundMap.put(effect.getColour(), tempString);
		}
		for(TFModifier secondaryModifier : TFModifier.values()) {
			String tempString = load("/com/lilithsthrone/res/items/refined_swirls.svg", secondaryModifier.getColour());
			refinedSwirlsMap.put(secondaryModifier.getColour(), tempString);
		}
	}

	private static String load(String path) {
		return SvgUtil.loadFromResource(path);
	}

	private static String load(String path, Colour colourShade) {
		String s = SvgUtil.loadFromResource(path);
		return SvgUtil.colourReplacement(null, colourShade, s);
	}

	private static String load(String path, Colour colour1, Colour colour2, Colour colour3) {
		String s = SvgUtil.loadFromResource(path);
		return SvgUtil.colourReplacement(null, colour1, colour2, colour3, s);
	}
	
	public String getFlagUs() {
		return flagUs;
	}
	
	public String getFist() {
		return fist;
	}
	
	public String getDisplacedIcon() {
		return displacedIcon;
	}

	public String getConcealedIcon() {
		return concealedIcon;
	}
	
	public String getDirtyIcon() {
		return dirtyIcon;
	}
	
	public String getLipstickIcon(Covering covering) {
		return SvgUtil.colourReplacement(null, covering.getPrimaryColour(), lipstickIcon);
	}

	public String getFeminineWarningIcon() {
		return feminineWarningIcon;
	}

	public String getMasculineWarningIcon() {
		return masculineWarningIcon;
	}

	public String getJinxedIcon() {
		return jinxedIcon;
	}

	public String getTattooSwitchTattoo() {
		return tattooSwitchTattoo;
	}

	public String getTattooSwitchClothing(GameCharacter characterBeingRendered) {
		// Didn't look good
//		if(characterBeingRendered.hasAnyTattoos()) {
//			return tattooSwitchClothingHighlight;
//		}
		return tattooSwitchClothing;
	}

	public String getScarIcon() {
		return scarIcon;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public String getInventoryIcon() {
		return inventoryIcon;
	}

	public String getInventoryIconDisabled() {
		return inventoryIconDisabled;
	}

	public String getQuestInventoryIcon() {
		return questInventoryIcon;
	}

	public String getQuestInventoryIconDisabled() {
		return questInventoryIconDisabled;
	}

	public String getEssence() {
		return essence;
	}

	public String getEssenceUncoloured() {
		return essenceUncoloured;
	}

	public String getJournalIcon() {
		return journalIcon;
	}

	public String getPeopleIcon() {
		return peopleIcon;
	}

	public String getZoomInIcon() {
		return zoomInIcon;
	}

	public String getZoomOutIcon() {
		return zoomOutIcon;
	}

	public String getCopyIcon() {
		return copyIcon;
	}
	
	public String getExportIcon() {
		return exportIcon;
	}

	public String getInformationIcon() {
		return informationIcon;
	}

	public String getAddIcon() {
		return addIcon;
	}

	public String getCalendarIcon() {
		return calendarIcon;
	}

	public String getDiskSave() {
		return diskSave;
	}
	public String getDiskSaveDisabled() {
		return diskSaveDisabled;
	}
	
	public String getDiskSaveConfirm() {
		return diskSaveConfirm;
	}

	public String getDiskOverwrite() {
		return diskOverwrite;
	}
	
	public String getDiskLoad() {
		return diskLoad;
	}
	
	public String getDiskLoadConfirm() {
		return diskLoadConfirm;
	}

	public String getDiskLoadDisabled() {
		return diskLoadDisabled;
	}

	public String getDiskLoadQuick() {
		return diskLoadQuick;
	}

	public String getDiskDelete() {
		return diskDelete;
	}
	
	public String getDiskDeleteConfirm() {
		return diskDeleteConfirm;
	}

	public String getItemsOnFloorIcon() {
		return itemsOnFloorIcon;
	}

	public String getCornerGlowNight() {
		return cornerGlowNight;
	}

	public String getCornerGlowTwilight() {
		return cornerGlowTwilight;
	}
	
	public String getCornerGlowAlwaysLight() {
		return cornerGlowAlwaysLight;
	}
	
	public String getPlayerMapIconMasculine() {
		return playerMapIconMasculine;
	}

	public String getPlayerMapIconAndrogynous() {
		return playerMapIconAndrogynous;
	}

	public String getPlayerMapIconFeminine() {
		return playerMapIconFeminine;
	}

	public String getPlayerMapDangerousIcon() {
		return playerMapDangerousIcon;
	}

	public String getPerkTreeArrow() {
		return perkTreeArrow;
	}
	
	public String getSpellOverlay() {
		return spellOverlay;
	}

	public String getWomensWatchHourHand(List<Colour> colours, List<ColourReplacement> replacements) {
		return SvgUtil.colourReplacement("womanWatchHour", colours, replacements, womensWatchHourHand);
	}

	public String getWomensWatchMinuteHand(List<Colour> colours, List<ColourReplacement> replacements) {
		return SvgUtil.colourReplacement("womanWatchMinute", colours, replacements, womensWatchMinuteHand);
	}

	public String getMensWatchHourHand(List<Colour> colours, List<ColourReplacement> replacements) {
		return SvgUtil.colourReplacement("manWatchHour", colours, replacements, mensWatchHourHand);
	}

	public String getMensWatchMinuteHand(List<Colour> colours, List<ColourReplacement> replacements) {
		return SvgUtil.colourReplacement("manWatchMinute", colours, replacements, mensWatchMinuteHand);
	}

	public String getWeatherDayClear() {
		return weatherDayClear;
	}

	public String getWeatherDayCloud() {
		return weatherDayCloud;
	}

	public String getWeatherDayRain() {
		return weatherDayRain;
	}
	
	public String getWeatherDaySnow() {
		return weatherDaySnow;
	}

	public String getWeatherDayStormIncoming() {
		return weatherDayStormIncoming;
	}

	public String getWeatherDayStormProtected() {
		return weatherDayStormProtected;
	}

	public String getWeatherDayStorm() {
		return weatherDayStorm;
	}

	public String getWeatherNightClear() {
		return weatherNightClear;
	}

	public String getWeatherNightCloud() {
		return weatherNightCloud;
	}

	public String getWeatherNightRain() {
		return weatherNightRain;
	}
	
	public String getWeatherNightSnow() {
		return weatherNightSnow;
	}

	public String getWeatherNightStormIncoming() {
		return weatherNightStormIncoming;
	}

	public String getWeatherNightStorm() {
		return weatherNightStorm;
	}

	public String getWeatherNightStormProtected() {
		return weatherNightStormProtected;
	}

	public String getProtectionEnabled() {
		return protectionEnabled;
	}

	public String getProtectionDisabled() {
		return protectionDisabled;
	}

	public String getTattoo() {
		return tattoo;
	}

	public String getResponseCombat() {
		return responseCombat;
	}

	public String getResponseSex() {
		return responseSex;
	}

	public String getResponseLocked() {
		return responseLocked;
	}

	public String getResponseUnlocked() {
		return responseUnlocked;
	}
	
	public String getResponseUnlockedDisabled() {
		return responseUnlockedDisabled;
	}

	public String getResponseCorruptionBypass() {
		return responseCorruptionBypass;
	}

	public String getResponseSubResist() {
		return responseSubResist;
	}

	public String getResponseSubNormal() {
		return responseSubNormal;
	}

	public String getResponseSubEager() {
		return responseSubEager;
	}

	public String getResponseDomGentle() {
		return responseDomGentle;
	}

	public String getResponseDomNormal() {
		return responseDomNormal;
	}

	public String getResponseDomRough() {
		return responseDomRough;
	}

	public String getResponseSexSwitch() {
		return responseSexSwitch;
	}

	public String getResponseSexAdditional() {
		return responseSexAdditional;
	}
	
	public String getNPCWarningMale() {
		return NPCWarningMale;
	}

	public String getNPCWarningFemale() {
		return NPCWarningFemale;
	}

	public String getNPCWarningDemon() {
		return NPCWarningDemon;
	}

	public String getCoverableAreaMouth() {
		return coverableAreaMouth;
	}

	public String getCoverableAreaAnus() {
		return coverableAreaAnus;
	}

	public String getCoverableAreaAss() {
		return coverableAreaAss;
	}
	
	public String getCoverableAreaNipple() {
		return coverableAreaNipple;
	}
	
	public String getCoverableAreaBreasts() {
		return coverableAreaBreasts;
	}
	
	public String getCoverableAreaBreastsFlat() {
		return coverableAreaBreastsFlat;
	}

	public String getCoverableAreaBreastsCrotch() {
		return coverableAreaBreastsCrotch;
	}

	public String getCoverableAreaUdders() {
		return coverableAreaUdders;
	}

	public String getCoverableAreaNippleCrotch() {
		return coverableAreaNippleCrotch;
	}

	public String getCoverableAreaVagina() {
		return coverableAreaVagina;
	}
	
	public String getCoverableAreaSpinneret() {
		return coverableAreaSpinneret;
	}

	public String getCoverableAreaMound() {
		return coverableAreaMound;
	}
	
	public String getCoverableAreaUrethraVagina() {
		return coverableAreaUrethraVagina;
	}
	
	public String getCoverableAreaUrethraPenis() {
		return coverableAreaUrethraPenis;
	}
	
	public String getCoverableAreaThighs() {
		return coverableAreaThighs;
	}
	
	public String getCoverableAreaArmpits() {
		return coverableAreaArmpits;
	}
	
	public String getPenetrationTypeFinger() {
		return penetrationTypeFinger;
	}
	
	public String getPenetrationTypeTail(boolean serpentTail) {
		if(serpentTail) {
			return penetrationTypeTailSerpent;
		}
		return penetrationTypeTail;
	}
	
	public String getPenetrationTypeTentacle() {
		return penetrationTypeTentacle;
	}
	
	public String getPenetrationTypeTongue() {
		return penetrationTypeTongue;
	}
	
	public String getPenetrationTypePenis() {
		return penetrationTypePenis;
	}

	public String getPenetrationTypeFoot() {
		return penetrationTypeFoot;
	}
	
	public String getPenetrationTypeClit() {
		return penetrationTypeClit;
	}
	
	public String getCombinationStretching() {
		return combinationStretching;
	}

	public String getCombinationStretchRecoveryPrevented() {
		return combinationStretchRecoveryPrevented;
	}
	
	public String getCombinationTooLoose() {
		return combinationTooLoose;
	}

	public String getCombinationWet() {
		return combinationWet;
	}

	public String getCombinationDry() {
		return combinationDry;
	}

	public String getCombinationDepthMinimum() {
		return combinationDepthMinimum;
	}

	public String getCombinationDepthMaximum() {
		return combinationDepthMaximum;
	}
	
	public String getEggIncubation(int stage) {
		if(stage<=1) {
			return eggIncubation1;
		} else if(stage==2) {
			return eggIncubation2;
		} else {
			return eggIncubation3;
		}
	}
	
	public String getStretching() {
		return stretching;
	}
	
	public String getHoleTooBig() {
		return holeTooBig;
	}

	public String getActiveSexBackground() {
		return activeSexBackground;
	}

	public Map<Colour, String> getRefinedBackgroundMap() {
		return refinedBackgroundMap;
	}

	public Map<Colour, String> getRefinedSwirlsMap() {
		return refinedSwirlsMap;
	}
	
	public String getHypnoWatchBase() {
		return hypnoWatchBase;
	}
	
	public String getHypnoWatchGynephilic() {
		return hypnoWatchGynephilic;
	}

	public String getHypnoWatchAmbiphilic() {
		return hypnoWatchAmbiphilic;
	}

	public String getHypnoWatchAndrophilic() {
		return hypnoWatchAndrophilic;
	}

	public String getHypnoWatchSpeechAdd() {
		return hypnoWatchSpeechAdd;
	}

	public String getHypnoWatchSpeechRemove() {
		return hypnoWatchSpeechRemove;
	}
	
	public String getScaleZero() {
		return scaleZero;
	}

	public String getScaleOne() {
		return scaleOne;
	}

	public String getScaleTwo() {
		return scaleTwo;
	}

	public String getScaleThree() {
		return scaleThree;
	}

	public String getScaleFour() {
		return scaleFour;
	}

	public String getScaleZeroDisabled() {
		return scaleZeroDisabled;
	}

	public String getScaleOneDisabled() {
		return scaleOneDisabled;
	}

	public String getScaleTwoDisabled() {
		return scaleTwoDisabled;
	}

	public String getScaleThreeDisabled() {
		return scaleThreeDisabled;
	}

	public String getScaleFourDisabled() {
		return scaleFourDisabled;
	}

	public String getSlaveBuy() {
		return slaveBuy;
	}

	public String getSlaveSell() {
		return slaveSell;
	}

	public String getSlaveInspect() {
		return slaveInspect;
	}
	
	public String getSlaveInspectDisabled() {
		return slaveInspectDisabled;
	}

	public String getSlaveJob() {
		return slaveJob;
	}
	
	public String getSlaveJobDisabled() {
		return slaveJobDisabled;
	}

	public String getSlavePermissions() {
		return slavePermissions;
	}
	
	public String getSlavePermissionsDisabled() {
		return slavePermissionsDisabled;
	}

	public String getSlaveTransfer() {
		return slaveTransfer;
	}

	public String getSlaveBuyDisabled() {
		return slaveBuyDisabled;
	}

	public String getSlaveSellDisabled() {
		return slaveSellDisabled;
	}

	public String getSlaveTransferDisabled() {
		return slaveTransferDisabled;
	}
	
	public String getSlaveCosmetics() {
		return slaveCosmetics;
	}
	
	public String getSlaveCosmeticsDisabled() {
		return slaveCosmeticsDisabled;
	}

	public String getTransactionBuy() {
		return transactionBuy;
	}

	public String getTransactionBuyDisabled() {
		return transactionBuyDisabled;
	}
	
	public String getTransactionBid() {
		return transactionBid;
	}
	
	public String getTransactionBidDisabled() {
		return transactionBidDisabled;
	}

	public String getTransactionSell() {
		return transactionSell;
	}

	public String getTransactionSellDisabled() {
		return transactionSellDisabled;
	}

	public String getResponseOption() {
		return responseOption;
	}

	public String getResponseOptionDisabled() {
		return responseOptionDisabled;
	}

	public String getCreampie() {
		return creampie;
	}

	public String getCreampieMasochist() {
		return creampieMasochist;
	}

	public String getRaceBackground() {
		return raceBackground;
	}

	public String getRaceBackgroundHalf() {
		return raceBackgroundHalf;
	}

	public String getRaceBackgroundSlime() {
		return raceBackgroundSlime;
	}

	public String getRaceBackgroundDoll() {
		return raceBackgroundDoll;
	}

	public String getRaceBackgroundDemon() {
		return raceBackgroundDemon;
	}

	public String getRaceUnknown() {
		return raceUnknown;
	}

	public String getRaceDobermann() {
		return raceDobermann;
	}
	
	public String getRaceDobermannDesaturated() {
		return raceDobermannDesaturated;
	}

	public String getRaceWisp() {
		return raceWisp;
	}
	
	public String getCounterZero() {
		return counterZero;
	}

	public String getCounterOne() {
		return counterOne;
	}

	public String getCounterTwo() {
		return counterTwo;
	}

	public String getCounterThree() {
		return counterThree;
	}

	public String getCounterFour() {
		return counterFour;
	}

	public String getCounterFive() {
		return counterFive;
	}

	public String getCounterFivePlus() {
		return counterFivePlus;
	}

	public String getCounterZeroDisabled() {
		return counterZeroDisabled;
	}

	public String getCounterOneDisabled() {
		return counterOneDisabled;
	}

	public String getCounterTwoDisabled() {
		return counterTwoDisabled;
	}

	public String getCounterThreeDisabled() {
		return counterThreeDisabled;
	}

	public String getCounterFourDisabled() {
		return counterFourDisabled;
	}

	public String getCounterFiveDisabled() {
		return counterFiveDisabled;
	}

	public String getCounterFivePlusDisabled() {
		return counterFivePlusDisabled;
	}

	public String getStopwatch() {
		return stopwatch;
	}

	public String getDrinkSmall() {
		return drinkSmall;
	}
	
	public String getDrink() {
		return drink;
	}

	public String getDice1() {
		return dice1;
	}

	public String getDice2() {
		return dice2;
	}

	public String getDice3() {
		return dice3;
	}

	public String getDice4() {
		return dice4;
	}

	public String getDice5() {
		return dice5;
	}

	public String getDice6() {
		return dice6;
	}

	public String getDiceGlow() {
		return diceGlow;
	}

	public String getFluidIngested() {
		return fluidIngested;
	}

	public String getFluidIngestedMasochist() {
		return fluidIngestedMasochist;
	}

	public String getFoxTail(int i) {
		return youkoTailsMap.get(i);
	}

	public String getFoxTailDesaturated(int i) {
		return youkoTailsDesaturatedMap.get(i);
	}

	public String getFoxTailDemon(int i) {
		return youkoTailsDemonMap.get(i);
	}
	
}
