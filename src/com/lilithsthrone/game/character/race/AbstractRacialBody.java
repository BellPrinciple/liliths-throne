package com.lilithsthrone.game.character.race;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithsthrone.main.Main;
import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.types.*;
import com.lilithsthrone.game.character.body.valueEnums.AreolaeShape;
import com.lilithsthrone.game.character.body.valueEnums.AreolaeSize;
import com.lilithsthrone.game.character.body.valueEnums.AssSize;
import com.lilithsthrone.game.character.body.valueEnums.BodyMaterial;
import com.lilithsthrone.game.character.body.valueEnums.BreastShape;
import com.lilithsthrone.game.character.body.valueEnums.Capacity;
import com.lilithsthrone.game.character.body.valueEnums.ClitorisSize;
import com.lilithsthrone.game.character.body.valueEnums.CumProduction;
import com.lilithsthrone.game.character.body.valueEnums.CupSize;
import com.lilithsthrone.game.character.body.valueEnums.GenitalArrangement;
import com.lilithsthrone.game.character.body.valueEnums.HairLength;
import com.lilithsthrone.game.character.body.valueEnums.HornLength;
import com.lilithsthrone.game.character.body.valueEnums.Lactation;
import com.lilithsthrone.game.character.body.valueEnums.LegConfiguration;
import com.lilithsthrone.game.character.body.valueEnums.LipSize;
import com.lilithsthrone.game.character.body.valueEnums.NippleShape;
import com.lilithsthrone.game.character.body.valueEnums.NippleSize;
import com.lilithsthrone.game.character.body.valueEnums.OrificeDepth;
import com.lilithsthrone.game.character.body.valueEnums.OrificeElasticity;
import com.lilithsthrone.game.character.body.valueEnums.OrificePlasticity;
import com.lilithsthrone.game.character.body.valueEnums.PenetrationGirth;
import com.lilithsthrone.game.character.body.valueEnums.TesticleSize;
import com.lilithsthrone.game.character.body.valueEnums.Wetness;
import com.lilithsthrone.game.character.body.valueEnums.WingSize;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.persona.PersonalityCategory;
import com.lilithsthrone.game.character.persona.PersonalityTrait;
import com.lilithsthrone.game.character.persona.SexualOrientation;
import com.lilithsthrone.game.character.persona.SexualOrientationPreference;

/**
 * @since 0.3.1
 * @version 0.4
 * @author Innoxia
 */
public class AbstractRacialBody implements RacialBody {

	String id;
	private boolean mod;
	private boolean fromExternalFile;
	
	// Antenna:
	private List<AntennaType> antennaTypes;
	private int maleAntennaLength;
	private int femaleAntennaLength;
	
	// Arms:
	private ArmType armType;
	private int armRows;

	// Ass:
	private AssType assType;
	private float anusCapacity;
	private int anusDepth;
	private int anusWetness;
	private int maleAssSize;
	private int femaleAssSize;
	private int maleHipSize;
	private int femaleHipSize;
	private int anusElasticity;
	private int anusPlasticity;

	// Breasts:
	private BreastType breastType;
	private List<BreastShape> breastShapes;
	private NippleShape maleNippleShape;
	private NippleShape femaleNippleShape;
	private AreolaeShape maleAreolaeShape;
	private AreolaeShape femaleAreolaeShape;
	private int noBreastSize;
	private int breastSize;
	private int maleLactationRate;
	private int femaleLactationRate;
	private float femaleBreastCapacity;
	private float maleBreastCapacity;
	private int femaleBreastDepth;
	private int maleBreastDepth;
	private int femaleBreastElasticity;
	private int maleBreastElasticity;
	private int femaleBreastPlasticity;
	private int maleBreastPlasticity;
	private int maleNippleCountPerBreast;
	private int femaleNippleCountPerBreast;
	private int maleAreolaeSize;
	private int femaleAreolaeSize;
	private int maleNippleSize;
	private int femaleNippleSize;
	private int breastCountMale;
	private int breastCountFemale;

	// BreastCrotchs/Crotch-boobs:
	private BreastType breastCrotchType;
	private List<BreastShape> breastCrotchShapes;
	private int breastCrotchSize;
	private int breastCrotchLactationRate;
	private float breastCrotchCapacity;
	private int breastCrotchDepth;
	private int breastCrotchElasticity;
	private int breastCrotchPlasticity;
	private int nippleCountPerBreastCrotch;
	private int breastCrotchAreolaeSize;
	private int breastCrotchNippleSize;
	private NippleShape breastCrotchNippleShape;
	private int breastCrotchCount;
	private AreolaeShape breastCrotchAreolaeShape;

	// Core:
	private TorsoType torsoType;
	private BodyMaterial bodyMaterial;
	private String bodyHairId;
	private BodyCoveringType bodyHairType;
	private GenitalArrangement genitalArrangement;
	private int maleHeight;
	private int femaleHeight;
	private int maleFemininity;
	private int femaleFemininity;
	private int maleBodySize;
	private int femaleBodySize;
	private int maleMuscle;
	private int femaleMuscle;
	
	// Hair:
	private HairType hairType;
	private int maleHairLength;
	private int femaleHairLength;

	// Horns:
	private List<HornType> hornTypes;
	private int maleHornLength;
	private int femaleHornLength;

	// Face:
	private FaceType faceType;
	private EyeType eyeType;
	private EarType earType;
	private int maleLipSize;
	private int femaleLipSize;

	// Legs:
	private LegType legType;
	private LegConfiguration legConfiguration;

	// Penis:
	private PenisType penisType;
	private int penisSize;
	private int penisGirth;
	private int testicleSize;
	private int testicleQuantity;
	private int cumProduction;

	// Tail:
	private List<TailType> tailTypes;
	
	// Tentacle:
	private TentacleType tentacleType;
	
	// Vagina:
	private VaginaType vaginaType;
	private float vaginaCapacity;
	private int vaginaDepth;
	private int vaginaWetness;
	private int clitSize;
	private int clitGirth;
	private int vaginaElasticity;
	private int vaginaPlasticity;

	// Wings:
	private List<WingType> wingTypes;
	private int maleWingSize;
	private int femaleWingSize;
	
	// External file variables:
	private Map<PersonalityTrait, Float> personalityChanceOverrides;
	
	private int orientationFeminineGynephilic;
	private int orientationFeminineAmbiphilic;
	private int orientationFeminineAndrophilic;
	private int orientationMasculineGynephilic;
	private int orientationMasculineAmbiphilic;
	private int orientationMasculineAndrophilic;
	
	public AbstractRacialBody(
			List<AntennaType> antennaTypes,
			ArmType armType,
				int armRows,
			AssType assType,
				AssSize maleAssSize,
				AssSize femaleAssSize,
				Wetness anusWetness,
				Capacity anusCapacity,
				OrificeDepth anusDepth,
				OrificeElasticity anusElasticity,
				OrificePlasticity anusPlasticity,
			BreastType breastType,
				List<BreastShape> breastShapes,
			CupSize noBreastSize,
				int breastCountMale,
				Lactation maleLactationRate,
				Capacity maleBreastCapacity,
				OrificeDepth maleBreastDepth,
				OrificeElasticity maleBreastElasticity,
				OrificePlasticity maleBreastPlasticity,
				NippleSize maleNippleSize,
				NippleShape maleNippleShape,
				AreolaeSize maleAreolaeSize,
				int maleNippleCountPerBreast,
			CupSize breastSize,
				int breastCountFemale,
				Lactation femaleLactationRate,
				Capacity femaleBreastCapacity,
				OrificeDepth femaleBreastDepth,
				OrificeElasticity femaleBreastElasticity,
				OrificePlasticity femaleBreastPlasticity,
				NippleSize femaleNippleSize,
				NippleShape femaleNippleShape,
				AreolaeSize femaleAreolaeSize,
				int femaleNippleCountPerBreast,
			BreastType breastCrotchType,
				List<BreastShape> breastCrotchShapes,
			CupSize breastCrotchSize,
				int breastCrotchCount,
				Lactation breastCrotchLactationRate,
				Capacity breastCrotchCapacity,
				OrificeDepth breastCrotchDepth,
				OrificeElasticity breastCrotchElasticity,
				OrificePlasticity breastCrotchPlasticity,
				NippleSize breastCrotchNippleSize,
				NippleShape breastCrotchNippleShape,
				AreolaeSize breastCrotchAreolaeSize,
				int nippleCountPerBreastCrotch,
			int maleHeight,
				int maleFemininity,
				int maleBodySize,
				int maleMuscle,
			int femaleHeight,
				int femaleFemininity,
				int femaleBodySize,
				int femaleMuscle,
			EarType earType,
			EyeType eyeType,
			FaceType faceType,
				LipSize maleLipSize,
				LipSize femaleLipSize,
			HairType hairType,
				HairLength maleHairLength,
				HairLength femaleHairLength,
			LegType legType,
				LegConfiguration legConfiguration,
			TorsoType skinType,
			BodyMaterial bodyMaterial,
			BodyCoveringType bodyHairType,
			HornLength maleHornLength,
				HornLength femaleHornLength,
				List<HornType> hornTypes,
			PenisType penisType,
				int penisSize,
				PenetrationGirth penisGirth,
				TesticleSize testicleSize,
				int testicleQuantity,
				CumProduction cumProduction,
			List<TailType> tailTypes,
			TentacleType tentacleType,
			VaginaType vaginaType,
				Wetness vaginaWetness,
				Capacity vaginaCapacity,
				OrificeDepth vaginaDepth,
				ClitorisSize clitSize,
				OrificeElasticity vaginaElasticity,
				OrificePlasticity vaginaPlasticity,
			List<WingType> wingTypes,
				WingSize maleWingSize,
				WingSize femaleWingSize,
			GenitalArrangement genitalArrangement) {
		this.mod = false;
		this.fromExternalFile = false;
		
		// Antenna:
		this.antennaTypes = antennaTypes;
		this.maleAntennaLength = HornLength.TWO_LONG.getMedianValue();
		this.femaleAntennaLength = HornLength.TWO_LONG.getMedianValue();
		
		// Arms:
		this.armType = armType;
		this.armRows = armRows;
		
		// Ass:
		this.assType = assType;
		this.anusCapacity = anusCapacity.getMedianValue();
		this.anusDepth = anusDepth.getValue();
		this.anusWetness = anusWetness.getValue();
		this.maleAssSize = maleAssSize.getValue();
		this.femaleAssSize = femaleAssSize.getValue();
		this.maleHipSize = maleAssSize.getValue();
		this.femaleHipSize = femaleAssSize.getValue();
		this.anusElasticity = anusElasticity.getValue();
		this.anusPlasticity = anusPlasticity.getValue();
		
		// Breasts:
		this.breastType = breastType;
		this.breastShapes = breastShapes;
		
		this.maleAreolaeShape = AreolaeShape.NORMAL;
		this.femaleAreolaeShape = AreolaeShape.NORMAL;
		
		this.noBreastSize = noBreastSize.getMeasurement();
		this.breastCountMale = breastCountMale;
		this.maleLactationRate = maleLactationRate.getMedianValue();
		this.maleBreastCapacity = maleBreastCapacity.getMedianValue();
		this.maleBreastDepth = maleBreastDepth.getValue();
		this.maleBreastElasticity = maleBreastElasticity.getValue();
		this.maleBreastPlasticity = maleBreastPlasticity.getValue();
		this.maleNippleSize = maleNippleSize.getValue();
		this.maleNippleShape = maleNippleShape;
		this.maleAreolaeSize = maleAreolaeSize.getValue();
		this.maleNippleCountPerBreast = maleNippleCountPerBreast;
		
		this.breastSize = breastSize.getMeasurement();
		this.breastCountFemale = breastCountFemale;
		this.femaleLactationRate = femaleLactationRate.getMedianValue();
		this.femaleBreastCapacity = femaleBreastCapacity.getMedianValue();
		this.femaleBreastDepth = femaleBreastDepth.getValue();
		this.femaleBreastElasticity = femaleBreastElasticity.getValue();
		this.femaleBreastPlasticity = femaleBreastPlasticity.getValue();
		this.femaleNippleSize = femaleNippleSize.getValue();
		this.femaleNippleShape = femaleNippleShape;
		this.femaleAreolaeSize = femaleAreolaeSize.getValue();
		this.femaleNippleCountPerBreast = femaleNippleCountPerBreast;

		// BreastCrotchs/Crotch-boobs:
		this.breastCrotchType = breastCrotchType;
		this.breastCrotchShapes = breastCrotchShapes;
		this.breastCrotchSize = breastCrotchSize.getMeasurement();
		this.breastCrotchCount = breastCrotchCount;
		this.breastCrotchLactationRate = breastCrotchLactationRate.getMedianValue();
		this.breastCrotchCapacity = breastCrotchCapacity.getMedianValue();
		this.breastCrotchDepth = breastCrotchDepth.getValue();
		this.breastCrotchElasticity = breastCrotchElasticity.getValue();
		this.breastCrotchPlasticity = breastCrotchPlasticity.getValue();
		this.breastCrotchNippleSize = breastCrotchNippleSize.getValue();
		this.breastCrotchNippleShape = breastCrotchNippleShape;
		this.breastCrotchAreolaeSize = breastCrotchAreolaeSize.getValue();
		this.breastCrotchAreolaeShape = AreolaeShape.NORMAL;
		this.nippleCountPerBreastCrotch = nippleCountPerBreastCrotch;
		
		// Core:
		this.torsoType = skinType;
		this.bodyMaterial = bodyMaterial;
		this.bodyHairType= bodyHairType;
		this.genitalArrangement = genitalArrangement;
		this.maleHeight = maleHeight;
		this.maleFemininity = maleFemininity;
		this.maleBodySize = maleBodySize;
		this.maleMuscle = maleMuscle;
		this.femaleHeight = femaleHeight;
		this.femaleFemininity = femaleFemininity;
		this.femaleBodySize = femaleBodySize;
		this.femaleMuscle = femaleMuscle;

		// Face:
		this.faceType = faceType;
		this.eyeType = eyeType;
		this.earType = earType;
		this.maleLipSize = maleLipSize.getValue();
		this.femaleLipSize = femaleLipSize.getValue();

		// Hair:
		this.hairType = hairType;
		this.maleHairLength = maleHairLength.getMedianValue();
		this.femaleHairLength = femaleHairLength.getMedianValue();

		// Horns:
		this.hornTypes = hornTypes;
		this.maleHornLength = maleHornLength.getMedianValue();
		this.femaleHornLength = femaleHornLength.getMedianValue();
		
		// Leg:
		this.legType = legType;
		this.legConfiguration = legConfiguration;
		
		// Penis:
		this.penisType = penisType;
		this.penisSize = penisSize;
		this.penisGirth = penisGirth.getValue();
		this.testicleSize = testicleSize.getValue();
		this.testicleQuantity = testicleQuantity;
		this.cumProduction = cumProduction.getMedianValue();

		// Tail:
		this.tailTypes = tailTypes;
		
		// Tentacle:
		this.tentacleType = tentacleType;
		
		// Vagina:
		this.vaginaType = vaginaType;
		this.clitSize = clitSize.getMedianValue();
		this.clitGirth = PenetrationGirth.THREE_AVERAGE.getValue();
		this.vaginaCapacity = vaginaCapacity.getMedianValue();
		this.vaginaDepth = vaginaDepth.getValue();
		this.vaginaWetness = vaginaWetness.getValue();
		this.vaginaElasticity = vaginaElasticity.getValue();
		this.vaginaPlasticity = vaginaPlasticity.getValue();

		// Wings:
		this.wingTypes = wingTypes;
		this.maleWingSize = maleWingSize.getValue();
		this.femaleWingSize = femaleWingSize.getValue();
	}
	
	public AbstractRacialBody(File XMLFile, String author, boolean mod) {
		if (XMLFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(XMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in statusEffect files it's <statusEffect>

				this.mod = mod;
				this.fromExternalFile = true;
				
				// Core:
				this.bodyMaterial = BodyMaterial.valueOf(coreElement.getMandatoryFirstOf("bodyMaterial").getTextContent());
				this.bodyHairId = coreElement.getMandatoryFirstOf("bodyHair").getTextContent();
				this.genitalArrangement = GenitalArrangement.valueOf(coreElement.getMandatoryFirstOf("genitalArrangement").getTextContent());
				this.maleHeight = Integer.valueOf(coreElement.getMandatoryFirstOf("maleHeight").getTextContent());
				this.maleFemininity = Integer.valueOf(coreElement.getMandatoryFirstOf("maleFemininity").getTextContent());
				this.maleBodySize = Integer.valueOf(coreElement.getMandatoryFirstOf("maleBodySize").getTextContent());
				this.maleMuscle = Integer.valueOf(coreElement.getMandatoryFirstOf("maleMuscle").getTextContent());
				this.femaleHeight = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleHeight").getTextContent());
				this.femaleFemininity = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleFemininity").getTextContent());
				this.femaleBodySize = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleBodySize").getTextContent());
				this.femaleMuscle = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleMuscle").getTextContent());
				
				// Antenna:
				this.antennaTypes = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("antennaTypes").getAllOf("type")) {
					antennaTypes.add(AntennaType.table.of(e.getTextContent()));
				}
				this.maleAntennaLength = Integer.valueOf(coreElement.getMandatoryFirstOf("maleAntennaLength").getTextContent());
				this.femaleAntennaLength = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleAntennaLength").getTextContent());
				
				// Arms:
				this.armType = ArmType.table.of(coreElement.getMandatoryFirstOf("armType").getTextContent());
				this.armRows = Integer.valueOf(coreElement.getMandatoryFirstOf("armRows").getTextContent());
				
				// Ass:
				this.assType = AssType.table.of(coreElement.getMandatoryFirstOf("assType").getTextContent());
				this.anusCapacity = Float.valueOf(coreElement.getMandatoryFirstOf("anusCapacity").getTextContent());
				this.anusDepth = Integer.valueOf(coreElement.getMandatoryFirstOf("anusDepth").getTextContent());
				this.anusWetness = Integer.valueOf(coreElement.getMandatoryFirstOf("anusWetness").getTextContent());
				this.maleAssSize = Integer.valueOf(coreElement.getMandatoryFirstOf("maleAssSize").getTextContent());
				this.femaleAssSize = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleAssSize").getTextContent());
				if(coreElement.getOptionalFirstOf("maleHipSize").isPresent()) {
					this.maleHipSize = Integer.valueOf(coreElement.getMandatoryFirstOf("maleHipSize").getTextContent());
				} else {
					this.maleHipSize = this.maleAssSize;
				}
				if(coreElement.getOptionalFirstOf("femaleHipSize").isPresent()) {
					this.femaleHipSize = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleHipSize").getTextContent());
				} else {
					this.femaleHipSize = this.femaleAssSize;
				}
				this.anusElasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("anusElasticity").getTextContent());
				this.anusPlasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("anusPlasticity").getTextContent());
				
				// Breasts:
				this.breastType = BreastType.table.of(coreElement.getMandatoryFirstOf("breastType").getTextContent());
				this.breastShapes = new ArrayList<>();
				String udderShapes = coreElement.getMandatoryFirstOf("breastShapes").getAttribute("udderShapes");
				if(!udderShapes.isEmpty()) {
					if(Boolean.valueOf(udderShapes)) {
						breastShapes = BreastShape.getUdderBreastShapes();
					} else {
						breastShapes = BreastShape.getNonUdderBreastShapes();
					}
				} else {
					for(Element e : coreElement.getMandatoryFirstOf("breastShapes").getAllOf("shape")) {
						breastShapes.add(BreastShape.valueOf(e.getTextContent()));
					}
				}
				
				this.noBreastSize = Integer.valueOf(coreElement.getMandatoryFirstOf("maleBreastSize").getTextContent());
				this.breastCountMale = Integer.valueOf(coreElement.getMandatoryFirstOf("maleBreastRows").getTextContent());
				this.maleLactationRate = Integer.valueOf(coreElement.getMandatoryFirstOf("maleLactationRate").getTextContent());
				this.maleBreastCapacity = Float.valueOf(coreElement.getMandatoryFirstOf("maleBreastCapacity").getTextContent());
				this.maleBreastDepth = Integer.valueOf(coreElement.getMandatoryFirstOf("maleBreastDepth").getTextContent());
				this.maleBreastElasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("maleBreastElasticity").getTextContent());
				this.maleBreastPlasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("maleBreastPlasticity").getTextContent());
				this.maleNippleSize = Integer.valueOf(coreElement.getMandatoryFirstOf("maleNippleSize").getTextContent());
				this.maleNippleShape = NippleShape.valueOf(coreElement.getMandatoryFirstOf("maleNippleShape").getTextContent());
				this.maleAreolaeSize = Integer.valueOf(coreElement.getMandatoryFirstOf("maleAreolaeSize").getTextContent());
				this.maleAreolaeShape = AreolaeShape.valueOf(coreElement.getMandatoryFirstOf("maleAreolaeShape").getTextContent());
				this.maleNippleCountPerBreast = Integer.valueOf(coreElement.getMandatoryFirstOf("maleNippleCountPerBreast").getTextContent());
				
				this.breastSize = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleBreastSize").getTextContent());
				this.breastCountFemale = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleBreastRows").getTextContent());
				this.femaleLactationRate = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleLactationRate").getTextContent());
				this.femaleBreastCapacity = Float.valueOf(coreElement.getMandatoryFirstOf("femaleBreastCapacity").getTextContent());
				this.femaleBreastDepth = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleBreastDepth").getTextContent());
				this.femaleBreastElasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleBreastElasticity").getTextContent());
				this.femaleBreastPlasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleBreastPlasticity").getTextContent());
				this.femaleNippleSize = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleNippleSize").getTextContent());
				this.femaleNippleShape = NippleShape.valueOf(coreElement.getMandatoryFirstOf("femaleNippleShape").getTextContent());
				this.femaleAreolaeSize = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleAreolaeSize").getTextContent());
				this.femaleAreolaeShape = AreolaeShape.valueOf(coreElement.getMandatoryFirstOf("femaleAreolaeShape").getTextContent());
				this.femaleNippleCountPerBreast = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleNippleCountPerBreast").getTextContent());

				// BreastCrotchs/Crotch-boobs:
				this.breastCrotchType = BreastType.table.of(coreElement.getMandatoryFirstOf("breastCrotchType").getTextContent());
				this.breastCrotchShapes = new ArrayList<>();
				udderShapes = coreElement.getMandatoryFirstOf("breastCrotchShapes").getAttribute("udderShapes");
				if(!udderShapes.isEmpty()) {
					if(Boolean.valueOf(udderShapes)) {
						breastCrotchShapes = BreastShape.getUdderBreastShapes();
					} else {
						breastCrotchShapes = BreastShape.getNonUdderBreastShapes();
					}
				} else {
					for(Element e : coreElement.getMandatoryFirstOf("breastCrotchShapes").getAllOf("shape")) {
						breastCrotchShapes.add(BreastShape.valueOf(e.getTextContent()));
					}
				}
				
				this.breastCrotchSize = Integer.valueOf(coreElement.getMandatoryFirstOf("breastCrotchSize").getTextContent());
				this.breastCrotchCount = Integer.valueOf(coreElement.getMandatoryFirstOf("breastCrotchRows").getTextContent());
				this.breastCrotchLactationRate = Integer.valueOf(coreElement.getMandatoryFirstOf("breastCrotchLactationRate").getTextContent());
				this.breastCrotchCapacity = Float.valueOf(coreElement.getMandatoryFirstOf("breastCrotchCapacity").getTextContent());
				this.breastCrotchDepth = Integer.valueOf(coreElement.getMandatoryFirstOf("breastCrotchDepth").getTextContent());
				this.breastCrotchElasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("breastCrotchElasticity").getTextContent());
				this.breastCrotchPlasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("breastCrotchPlasticity").getTextContent());
				this.breastCrotchNippleSize = Integer.valueOf(coreElement.getMandatoryFirstOf("breastCrotchNippleSize").getTextContent());
				this.breastCrotchNippleShape = NippleShape.valueOf(coreElement.getMandatoryFirstOf("breastCrotchNippleShape").getTextContent());
				this.breastCrotchAreolaeSize = Integer.valueOf(coreElement.getMandatoryFirstOf("breastCrotchAreolaeSize").getTextContent());
				this.breastCrotchAreolaeShape = AreolaeShape.valueOf(coreElement.getMandatoryFirstOf("breastCrotchAreolaeShape").getTextContent());
				this.nippleCountPerBreastCrotch = Integer.valueOf(coreElement.getMandatoryFirstOf("nippleCountPerBreastCrotch").getTextContent());

				// Face:
				this.faceType = FaceType.table.of(coreElement.getMandatoryFirstOf("faceType").getTextContent());
				this.eyeType = EyeType.table.of(coreElement.getMandatoryFirstOf("eyeType").getTextContent());
				this.earType = EarType.table.of(coreElement.getMandatoryFirstOf("earType").getTextContent());
				this.maleLipSize = Integer.valueOf(coreElement.getMandatoryFirstOf("maleLipSize").getTextContent());
				this.femaleLipSize = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleLipSize").getTextContent());

				// Hair:
				this.hairType = HairType.table.of(coreElement.getMandatoryFirstOf("hairType").getTextContent());
				this.maleHairLength = Integer.valueOf(coreElement.getMandatoryFirstOf("maleHairLength").getTextContent());
				this.femaleHairLength = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleHairLength").getTextContent());

				// Horns:
				this.hornTypes = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("hornTypes").getAllOf("type")) {
					hornTypes.add(HornType.table.of(e.getTextContent()));
				}
				this.maleHornLength = Integer.valueOf(coreElement.getMandatoryFirstOf("maleHornLength").getTextContent());
				this.femaleHornLength = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleHornLength").getTextContent());
				
				// Leg:
				this.legType = LegType.table.of(coreElement.getMandatoryFirstOf("legType").getTextContent());
				this.legConfiguration = LegConfiguration.valueOf(coreElement.getMandatoryFirstOf("legConfiguration").getTextContent());
				
				// Penis:
				this.penisType = PenisType.table.of(coreElement.getMandatoryFirstOf("penisType").getTextContent());
				this.penisSize = Integer.valueOf(coreElement.getMandatoryFirstOf("penisLength").getTextContent());
				this.penisGirth = Integer.valueOf(coreElement.getMandatoryFirstOf("penisGirth").getTextContent());
				this.testicleSize = Integer.valueOf(coreElement.getMandatoryFirstOf("testicleSize").getTextContent());
				this.testicleQuantity = Integer.valueOf(coreElement.getMandatoryFirstOf("testicleQuantity").getTextContent());
				this.cumProduction = Integer.valueOf(coreElement.getMandatoryFirstOf("cumProduction").getTextContent());

				// Tail:
				this.tailTypes = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("tailTypes").getAllOf("type")) {
					tailTypes.add(TailType.table.of(e.getTextContent()));
				}
				
				// Tentacle:
				this.tentacleType = TentacleType.table.of(coreElement.getMandatoryFirstOf("tentacleType").getTextContent());

				// Torso:
				this.torsoType = TorsoType.table.of(coreElement.getMandatoryFirstOf("torsoType").getTextContent());
				
				// Vagina:
				this.vaginaType = VaginaType.table.of(coreElement.getMandatoryFirstOf("vaginaType").getTextContent());
				this.clitSize = Integer.valueOf(coreElement.getMandatoryFirstOf("clitSize").getTextContent());
				this.clitGirth = Integer.valueOf(coreElement.getMandatoryFirstOf("clitGirth").getTextContent());
				this.vaginaCapacity = Float.valueOf(coreElement.getMandatoryFirstOf("vaginaCapacity").getTextContent());
				this.vaginaDepth = Integer.valueOf(coreElement.getMandatoryFirstOf("vaginaDepth").getTextContent());
				this.vaginaWetness = Integer.valueOf(coreElement.getMandatoryFirstOf("vaginaWetness").getTextContent());
				this.vaginaElasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("vaginaElasticity").getTextContent());
				this.vaginaPlasticity = Integer.valueOf(coreElement.getMandatoryFirstOf("vaginaPlasticity").getTextContent());

				// Wings:
				this.wingTypes = new ArrayList<>();
				for(Element e : coreElement.getMandatoryFirstOf("wingTypes").getAllOf("type")) {
					wingTypes.add(WingType.table.of(e.getTextContent()));
				}
				this.maleWingSize = Integer.valueOf(coreElement.getMandatoryFirstOf("maleWingSize").getTextContent());
				this.femaleWingSize = Integer.valueOf(coreElement.getMandatoryFirstOf("femaleWingSize").getTextContent());
				
				// Personalities:
				personalityChanceOverrides = new HashMap<>();
				if(coreElement.getOptionalFirstOf("personalityChances").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("personalityChances").getAllOf("entry")) {
						try {
							personalityChanceOverrides.put(PersonalityTrait.valueOf(e.getTextContent()), Float.valueOf(e.getAttribute("chance")));
						} catch(Exception ex) {
							System.err.println("AbstractRacialBody error: PersonalityTrait '"+e.getTextContent()+"' failed to load!");
						}
					}
				}
				
				// Orientations:
				orientationFeminineGynephilic = Integer.valueOf(coreElement.getMandatoryFirstOf("orientationFeminineGynephilic").getTextContent());
				orientationFeminineAmbiphilic = Integer.valueOf(coreElement.getMandatoryFirstOf("orientationFeminineAmbiphilic").getTextContent());
				orientationFeminineAndrophilic = Integer.valueOf(coreElement.getMandatoryFirstOf("orientationFeminineAndrophilic").getTextContent());
				orientationMasculineGynephilic = Integer.valueOf(coreElement.getMandatoryFirstOf("orientationMasculineGynephilic").getTextContent());
				orientationMasculineAmbiphilic = Integer.valueOf(coreElement.getMandatoryFirstOf("orientationMasculineAmbiphilic").getTextContent());
				orientationMasculineAndrophilic = Integer.valueOf(coreElement.getMandatoryFirstOf("orientationMasculineAndrophilic").getTextContent());
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("AbstractRacialBody was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
			}
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Map<PersonalityTrait, Float> getPersonalityTraitChances() {
		Map<PersonalityTrait, Float> map = new HashMap<>();
		
		for(PersonalityTrait trait : PersonalityTrait.values()) {
			if(trait.getPersonalityCategory()==PersonalityCategory.SPEECH) {
				if(trait==PersonalityTrait.MUTE) {
					map.put(trait, 0.001f); // Mute should be very rare.
				} else {
					map.put(trait, 0.01f); // Speech-related traits should be rare for a normal race.
				}
				
			} else if(trait.getPersonalityCategory()==PersonalityCategory.SEX && trait!=PersonalityTrait.LEWD) {
				map.put(trait, 0.025f); // Smaller chance for people to be prude or innocent.
					
			} else {
				map.put(trait, 0.05f); // With each category having two values, it's a ~10% chance to have a special trait in each category.
			}
		}
		
		if(this.fromExternalFile) {
			for(Entry<PersonalityTrait, Float> entry : personalityChanceOverrides.entrySet()) {
				map.put(entry.getKey(), entry.getValue());
			}
		}
		
		return map;
	}

	@Override
	public SexualOrientation getSexualOrientation(Gender gender) {
		if(this.fromExternalFile) {
			if(gender.isFeminine()) {
				return SexualOrientationPreference.getSexualOrientationFromUserPreferences(orientationFeminineGynephilic, orientationFeminineAmbiphilic, orientationFeminineAndrophilic);
			} else {
				return SexualOrientationPreference.getSexualOrientationFromUserPreferences(orientationMasculineGynephilic, orientationMasculineAmbiphilic, orientationMasculineAndrophilic);
			}
			
		} else {
			if(gender.isFeminine()) {
				return SexualOrientationPreference.getSexualOrientationFromUserPreferences(20, 40, 40);
			} else {
				return SexualOrientationPreference.getSexualOrientationFromUserPreferences(60, 30, 10);
			}
		}
	}
	
	public boolean isMod() {
		return mod;
	}
	
	public boolean isFromExternalFile() {
		return fromExternalFile;
	}

	@Override
	public List<AntennaType> getAntennaTypes(boolean removeTypeNone) {
		if(removeTypeNone) {
			var antennaList = new ArrayList<>(antennaTypes);
			antennaList.remove(AntennaType.NONE);
			return antennaList;
		}
		return antennaTypes;
	}

	@Override
	public int getMaleAntennaLength() {
		return maleAntennaLength;
	}

	@Override
	public int getFemaleAntennaLength() {
		return femaleAntennaLength;
	}

	@Override
	public ArmType getArmType() {
		return armType;
	}

	@Override
	public AssType getAssType() {
		return assType;
	}

	@Override
	public BreastType getBreastType() {
		return breastType;
	}

	@Override
	public List<BreastShape> getBreastShapes() {
		return breastShapes;
	}

	@Override
	public FaceType getFaceType() {
		return faceType;
	}

	@Override
	public EyeType getEyeType() {
		return eyeType;
	}

	@Override
	public EarType getEarType() {
		return earType;
	}

	@Override
	public HairType getHairType() {
		return hairType;
	}

	@Override
	public LegType getLegType(LegConfiguration configuration) {
		return legType;
	}

	@Override
	public LegConfiguration getLegConfiguration() {
		return legConfiguration;
	}

	@Override
	public TorsoType getTorsoType() {
		return torsoType;
	}

	@Override
	public BodyMaterial getBodyMaterial() {
		return bodyMaterial;
	}

	@Override
	public BodyCoveringType getBodyHairType() {
		if(this.isFromExternalFile()) {
			return BodyCoveringType.table.of(bodyHairId);
		} else {
			return bodyHairType;
		}
	}

	@Override
	public List<HornType> getHornTypes(boolean removeTypeNone) {
		if(removeTypeNone) {
			var hornList = new ArrayList<>(hornTypes);
			hornList.remove(HornType.NONE);
			return hornList;
		}
		return hornTypes;
	}

	@Override
	public PenisType getPenisType() {
		return penisType;
	}

	@Override
	public List<TailType> getTailType() {
		return tailTypes;
	}

	@Override
	public TentacleType getTentacleType() {
		return tentacleType;
	}

	@Override
	public VaginaType getVaginaType() {
		return vaginaType;
	}

	@Override
	public List<WingType> getWingTypes() {
		return wingTypes;
	}

	@Override
	public int getArmRows() {
		return armRows;
	}

	@Override
	public float getAnusCapacity() {
		return anusCapacity;
	}

	@Override
	public int getAnusDepth() {
		return anusDepth;
	}

	@Override
	public int getAnusWetness() {
		return anusWetness;
	}

	@Override
	public int getAnusElasticity() {
		return anusElasticity;
	}

	@Override
	public int getAnusPlasticity() {
		return anusPlasticity;
	}

	@Override
	public int getMaleHeight() {
		return maleHeight;
	}

	@Override
	public int getMaleFemininity() {
		return maleFemininity;
	}

	@Override
	public int getMaleMuscle() {
		return maleMuscle;
	}

	@Override
	public int getMaleBodySize() {
		return maleBodySize;
	}

	@Override
	public int getFemaleHeight() {
		return femaleHeight;
	}

	@Override
	public int getFemaleFemininity() {
		return femaleFemininity;
	}

	@Override
	public int getFemaleBodySize() {
		return femaleBodySize;
	}

	@Override
	public int getFemaleMuscle() {
		return femaleMuscle;
	}

	@Override
	public int getMaleAssSize() {
		return maleAssSize;
	}

	@Override
	public int getFemaleAssSize() {
		return femaleAssSize;
	}

	@Override
	public int getMaleHipSize() {
		return maleHipSize;
	}

	@Override
	public int getFemaleHipSize() {
		return femaleHipSize;
	}

	@Override
	public int getMaleHairLength() {
		return maleHairLength;
	}

	@Override
	public int getFemaleHairLength() {
		return femaleHairLength;
	}

	@Override
	public int getMaleHornLength() {
		return maleHornLength;
	}

	@Override
	public int getFemaleHornLength() {
		return femaleHornLength;
	}

	@Override
	public int getNoBreastSize() {
		return noBreastSize;
	}

	@Override
	public int getBreastSize() {
		return breastSize;
	}

	@Override
	public int getMaleLactationRate() {
		return maleLactationRate;
	}

	@Override
	public int getFemaleLactationRate() {
		return femaleLactationRate;
	}

	@Override
	public int getFemaleBreastElasticity() {
		return femaleBreastElasticity;
	}

	@Override
	public int getMaleBreastElasticity() {
		return maleBreastElasticity;
	}

	@Override
	public int getFemaleBreastPlasticity() {
		return femaleBreastPlasticity;
	}

	@Override
	public int getMaleBreastPlasticity() {
		return maleBreastPlasticity;
	}

	@Override
	public float getFemaleBreastCapacity() {
		return femaleBreastCapacity;
	}

	@Override
	public float getMaleBreastCapacity() {
		return maleBreastCapacity;
	}

	@Override
	public int getFemaleBreastDepth() {
		return femaleBreastDepth;
	}

	@Override
	public int getMaleBreastDepth() {
		return maleBreastDepth;
	}

	@Override
	public int getMaleNippleSize() {
		return maleNippleSize;
	}

	@Override
	public int getFemaleNippleSize() {
		return femaleNippleSize;
	}

	@Override
	public NippleShape getMaleNippleShape() {
		return maleNippleShape;
	}

	@Override
	public NippleShape getFemaleNippleShape() {
		return femaleNippleShape;
	}

	@Override
	public int getMaleNippleCountPerBreast() {
		return maleNippleCountPerBreast;
	}

	@Override
	public int getFemaleNippleCountPerBreast() {
		return femaleNippleCountPerBreast;
	}

	@Override
	public int getMaleAreolaeSize() {
		return maleAreolaeSize;
	}

	@Override
	public int getFemaleAreolaeSize() {
		return femaleAreolaeSize;
	}

	@Override
	public AreolaeShape getMaleAreolaeShape() {
		return maleAreolaeShape;
	}

	@Override
	public AreolaeShape getFemaleAreolaeShape() {
		return femaleAreolaeShape;
	}

	@Override
	public BreastType getBreastCrotchType() {
		return breastCrotchType;
	}

	@Override
	public List<BreastShape> getBreastCrotchShapes() {
		return breastCrotchShapes;
	}

	@Override
	public int getBreastCrotchSize() {
		return breastCrotchSize;
	}

	@Override
	public int getBreastCrotchLactationRate() {
		return breastCrotchLactationRate;
	}

	@Override
	public float getBreastCrotchCapacity() {
		return breastCrotchCapacity;
	}

	@Override
	public int getBreastCrotchDepth() {
		return breastCrotchDepth;
	}

	@Override
	public int getBreastCrotchElasticity() {
		return breastCrotchElasticity;
	}

	@Override
	public int getBreastCrotchPlasticity() {
		return breastCrotchPlasticity;
	}

	@Override
	public int getNippleCountPerBreastCrotch() {
		return nippleCountPerBreastCrotch;
	}

	@Override
	public int getBreastCrotchNippleSize() {
		return breastCrotchNippleSize;
	}

	@Override
	public NippleShape getBreastCrotchNippleShape() {
		return breastCrotchNippleShape;
	}

	@Override
	public int getBreastCrotchAreolaeSize() {
		return breastCrotchAreolaeSize;
	}

	@Override
	public AreolaeShape getBreastCrotchAreolaeShape() {
		return breastCrotchAreolaeShape;
	}

	@Override
	public int getBreastCrotchCount() {
		return breastCrotchCount;
	}

	@Override
	public int getClitSize() {
		return clitSize;
	}

	@Override
	public int getClitGirth() {
		return clitGirth;
	}

	@Override
	public int getPenisSize() {
		return penisSize;
	}

	@Override
	public int getPenisGirth() {
		return penisGirth;
	}

	@Override
	public int getTesticleSize() {
		return testicleSize;
	}

	@Override
	public int getCumProduction() {
		return cumProduction;
	}

	@Override
	public float getVaginaCapacity() {
		return vaginaCapacity;
	}

	@Override
	public int getVaginaDepth() {
		return vaginaDepth;
	}

	@Override
	public int getVaginaWetness() {
		return vaginaWetness;
	}

	@Override
	public int getVaginaElasticity() {
		return vaginaElasticity;
	}

	@Override
	public int getVaginaPlasticity() {
		return vaginaPlasticity;
	}

	@Override
	public int getBreastCountMale() {
		return breastCountMale;
	}

	@Override
	public int getBreastCountFemale() {
		return breastCountFemale;
	}

	@Override
	public int getTesticleQuantity() {
		return testicleQuantity;
	}

	@Override
	public int getMaleLipSize() {
		return maleLipSize;
	}

	@Override
	public int getFemaleLipSize() {
		return femaleLipSize;
	}

	@Override
	public int getMaleWingSize() {
		return maleWingSize;
	}

	@Override
	public int getFemaleWingSize() {
		return femaleWingSize;
	}

	@Override
	public GenitalArrangement getGenitalArrangement() {
		return genitalArrangement;
	}
}
