package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractFaceType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.3.9.1
 * @author Innoxia
 */
public interface FaceType extends BodyPartTypeInterface {

	boolean isFacialHairAllowed();

	MouthType getMouthType();

	String getNoseNameSingular(GameCharacter gc);

	String getNoseNamePlural(GameCharacter gc);

	String getNoseDescriptor(GameCharacter gc);

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	@Override
	default String getDeterminer(GameCharacter gc) {
		return "";
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return false;
	}

	@Override
	default TFModifier getTFModifier() {
		return getTFTypeModifier(FaceType.getFaceTypes(getRace()));
	}

	public static AbstractFaceType HUMAN = new Special(BodyCoveringType.HUMAN,
			Race.HUMAN,
			MouthType.HUMAN,
			null,
			null,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"Thankfully#IF(!npc.isPlayer())for [npc.herHim]#ENDIF, the transformation only lasts a matter of moments, leaving [npc.herHim] with a normal human face, [npc.materialDescriptor] [npc.faceSkin+].<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldHuman(human face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldHuman(human tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], human face, [npc.materialDescriptor] [npc.faceFullDescription(true)].",
			Util.newArrayListOfValues()){
	};

	public static AbstractFaceType ANGEL = new Special(BodyCoveringType.ANGEL,
			Race.ANGEL,
			MouthType.ANGEL,
			null,
			null,
			Util.newArrayListOfValues("perfect", "flawless", "angelic"),
			Util.newArrayListOfValues("perfect", "flawless", "angelic"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"Thankfully#IF(!npc.isPlayer())for [npc.herHim]#ENDIF, the transformation only lasts a matter of moments, leaving [npc.herHim] with an angelic, human-looking face, [npc.materialDescriptor] [npc.faceSkin+].<br/>"
					+ "[npc.Name] now [npc.has] an [style.boldAngel(angelic face)], [npc.materialDescriptor] [npc.faceFullDescription]."
					+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] an [style.boldAngel(angelic tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], angelic face, [npc.materialDescriptor] [npc.faceFullDescription(true)].",
			Util.newArrayListOfValues()){
	};

	public static AbstractFaceType DEMON_COMMON = new Special(BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			MouthType.DEMON_COMMON,
			null,
			null,
			Util.newArrayListOfValues("perfect", "flawless", "demonic"),
			Util.newArrayListOfValues("perfect", "flawless", "demonic"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"#IF(npc.isShortStature())"
				+ "Thankfully#IF(!npc.isPlayer())for [npc.herHim]#ENDIF, the transformation only lasts a matter of moments, leaving [npc.herHim] with an impish, human-looking face, [npc.materialDescriptor] [npc.faceSkin+].<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldImp(impish face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldImp(impish tongue)]."
			+ "#ELSE"
				+ "Thankfully#IF(!npc.isPlayer())for [npc.herHim]#ENDIF, the transformation only lasts a matter of moments, leaving [npc.herHim] with a demonic, human-looking face, [npc.materialDescriptor] [npc.faceSkin+].<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldDemon(demonic face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldDemon(demonic tongue)]."
			+ "#ENDIF",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF face, [npc.materialDescriptor] [npc.faceFullDescription(true)].",
			Util.newArrayListOfValues()){
	};

	public static AbstractFaceType ALLIGATOR_MORPH = new Special(BodyCoveringType.ALLIGATOR_SCALES,
			Race.ALLIGATOR_MORPH,
			MouthType.ALLIGATOR_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic alligator-like", "alligator-like", "reptile"),
			Util.newArrayListOfValues("anthropomorphic alligator-like", "alligator-like", "reptile"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic reptilian muzzle, and [npc.her] tongue transforms into a strong, alligator-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldAlligatorMorph(alligator-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldAlligatorMorph(strong, alligator-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, alligator-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a long, flat muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a long, flat muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_FANGS,
					BodyPartTag.FACE_NATURAL_BALDNESS_SCALY
				)){
	};

	public static AbstractFaceType BAT_MORPH = new Special(BodyCoveringType.BAT_FUR,
			Race.BAT_MORPH,
			MouthType.BAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic bat-like", "bat-like"),
			Util.newArrayListOfValues("anthropomorphic bat-like", "bat-like"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic bat-like muzzle, and [npc.her] tongue transforms into a thin, bat-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldBatMorph(bat-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldBatMorph(thin, bat-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, bat-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a short muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a short muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_FANGS,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};

	public static AbstractFaceType CAT_MORPH = new Special(BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			MouthType.CAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic cat-like", "cat-like", "feline"),
			Util.newArrayListOfValues("anthropomorphic cat-like", "cat-like", "feline"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic feline muzzle, and [npc.her] tongue flattens and transforms into a cat-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldCatMorph(cat-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldCatMorph(flat, cat-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, cat-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a cute little feline muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a cute little feline muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_FANGS,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};

//	public static AbstractFaceType CAT_MORPH_PANTHER = new Special(BodyCoveringType.FELINE_FUR,
//			Race.CAT_MORPH,
//			MouthType.CAT_MORPH,
//			null,
//			null,
//			Util.newArrayListOfValues("anthropomorphic panther-like", "panther-like", "panther"),
//			Util.newArrayListOfValues("anthropomorphic panther-like", "panther-like", "panther"),
//			"nose",
//			"noses",
//			Util.newArrayListOfValues(""),
//			Util.newArrayListOfValues(""),
//			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic panther-like muzzle, and [npc.her] tongue flattens and transforms into a cat-like one."
//				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
//					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
//				+ "#ELSE"
//					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
//				+ "#ENDIF"
//					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
//				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldCatMorph(panther-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
//				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldCatMorph(flat, cat-like tongue)].",
//			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, panther-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a powerful, toothy muzzle, big nose, and strong jawline.",
//			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a powerful, toothy muzzle, big nose, and strong jawline.",
//			Util.newArrayListOfValues(
//					BodyPartTag.FACE_MUZZLE,
//					BodyPartTag.FACE_FANGS,
//					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
//				)){
//	};

	public static AbstractFaceType COW_MORPH = new Special(BodyCoveringType.BOVINE_FUR,
			Race.COW_MORPH,
			MouthType.COW_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic cow-like", "cow-like", "bovine"),
			Util.newArrayListOfValues("anthropomorphic cow-like", "cow-like", "bovine"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic bovine-like muzzle, and [npc.her] tongue transforms into a strong, cow-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldCowMorph(cow-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldCowMorph(strong, cow-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, cow-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a bovine muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a bovine muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};

	public static AbstractFaceType DOG_MORPH = new Special(BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			MouthType.DOG_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic dog-like", "dog-like", "canine"),
			Util.newArrayListOfValues("anthropomorphic dog-like", "dog-like", "canine"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic canine muzzle, and [npc.her] tongue flattens and grows wider, turning into a dog-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldDogMorph(dog-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldDogMorph(flat, dog-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, dog-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a canine muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a canine muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_FANGS,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};

	public static AbstractFaceType FOX_MORPH = new Special(BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			MouthType.FOX_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic fox-like", "fox-like"),
			Util.newArrayListOfValues("anthropomorphic fox-like", "fox-like"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic vulpine muzzle, and [npc.her] tongue flattens and transforms into a fox-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldFoxMorph(fox-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldFoxMorph(flat, fox-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, fox-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a slender, vulpine muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a slender, vulpine muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_FANGS,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};

	public static AbstractFaceType HARPY = new Special(BodyCoveringType.FEATHERS,
			Race.HARPY,
			MouthType.HARPY,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic bird-like", "bird-like"),
			Util.newArrayListOfValues("anthropomorphic bird-like", "bird-like"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they fuse together and push out into a short beak, and [npc.her] tongue thins down, turning into a bird-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face,"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+],"
				+ "#ENDIF"
					+ " and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath."
				+ " [npc.She] [npc.verb(find)], much to [npc.her] relief, that [npc.sheIs] able to harden or soften the edges of [npc.her] beak at will,"
					+ " allowing [npc.herHim] to portray facial emotions as well as to wrap [npc.her] beak's edges around anything [npc.she] might want to put in [npc.her] mouth.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldHarpy(bird-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldHarpy(thin, bird-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, bird-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a beak.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a beak.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_BEAK,
					BodyPartTag.FACE_NATURAL_BALDNESS_AVIAN
				)){
	};

	public static AbstractFaceType HORSE_MORPH = new Special(BodyCoveringType.HORSE_HAIR,
			Race.HORSE_MORPH,
			MouthType.HORSE_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic horse-like", "horse-like", "equine"),
			Util.newArrayListOfValues("anthropomorphic horse-like", "horse-like", "equine"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic equine muzzle, and [npc.her] tongue transforms into a strong, horse-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldHorseMorph(horse-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldHorseMorph(strong, horse-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, horse-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a long, equine muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a long, equine muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY // Note: Some horse races only have hair on the neck aka a mane so its not totally unnatural to have a bald face
				)){
	};

	public static AbstractFaceType RABBIT_MORPH = new Special(BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			MouthType.RABBIT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic rabbit-like", "rabbit-like"),
			Util.newArrayListOfValues("anthropomorphic rabbit-like", "rabbit-like"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic rabbit-like muzzle, and [npc.her] tongue transforms into a thin, rabbit-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldRabbitMorph(rabbit-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldRabbitMorph(thin, rabbit-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, rabbit-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a short muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a short muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};

	public static AbstractFaceType RAT_MORPH = new Special(BodyCoveringType.RAT_FUR,
			Race.RAT_MORPH,
			MouthType.RAT_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic rat-like", "rat-like", "rodent"),
			Util.newArrayListOfValues("anthropomorphic rat-like", "rat-like", "rodent"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic rat-like muzzle, and [npc.her] tongue transforms into a thin, rat-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldRatMorph(rat-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldRatMorph(thin, rat-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, rat-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a long, rodent muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a long, rodent muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};

	public static AbstractFaceType REINDEER_MORPH = new Special(BodyCoveringType.REINDEER_FUR,
			Race.REINDEER_MORPH,
			MouthType.REINDEER_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic reindeer-like", "reindeer-like", "reindeer"),
			Util.newArrayListOfValues("anthropomorphic reindeer-like", "reindeer-like", "reindeer"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic reindeer muzzle, and [npc.her] tongue transforms into a strong, reindeer-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldReindeerMorph(horse-reindeer face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldReindeerMorph(strong, reindeer-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, reindeer-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a long muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a long muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};

	public static AbstractFaceType SQUIRREL_MORPH = new Special(BodyCoveringType.SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			MouthType.SQUIRREL_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic squirrel-like", "squirrel-like", "rodent"),
			Util.newArrayListOfValues("anthropomorphic squirrel-like", "squirrel-like", "rodent"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic squirrel-like muzzle, and [npc.her] tongue transforms into a thin, squirrel-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldSquirrelMorph(squirrel-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldSquirrelMorph(thin, squirrel-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, squirrel-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a short muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a short muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};

	public static AbstractFaceType WOLF_MORPH = new Special(BodyCoveringType.LYCAN_FUR,
			Race.WOLF_MORPH,
			MouthType.WOLF_MORPH,
			null,
			null,
			Util.newArrayListOfValues("anthropomorphic wolf-like", "wolf-like"),
			Util.newArrayListOfValues("anthropomorphic wolf-like", "wolf-like"),
			"nose",
			"noses",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.Her] nose and mouth twitch and transform as they push out into an anthropomorphic lupine muzzle, and [npc.her] tongue flattens and grows wider, turning into a wolf-like one."
				+ "#IF(npc.getBodyMaterial()==BODY_MATERIAL_FLESH)"
					+ " A layer of [npc.faceSkin+] quickly grows to cover [npc.her] new face"
				+ "#ELSE"
					+ " Just like the rest of [npc.her] body, [npc.her] new face is [npc.materialDescriptor] [npc.faceSkin+]"
				+ "#ENDIF"
					+ ", and as the transformation finally comes to an end, [npc.sheIs] left panting and sighing as [npc.she] [npc.verb(try)] to catch [npc.her] breath.<br/>"
				+ "[npc.Name] now [npc.has] an anthropomorphic [style.boldWolfMorph(wolf-like face)], [npc.materialDescriptor] [npc.faceFullDescription]."
				+ " Within [npc.her] [npc.mouth], [npc.sheHasFull] a [style.boldWolfMorph(flat, wolf-like tongue)].",
			"[npc.SheHasFull] [npc.a_feminineDescriptor(true)], anthropomorphic, wolf-like face [npc.materialCompositionDescriptor] [npc.faceFullDescription(true)] and complete with a long muzzle.",
			"[npc.SheHasFull] the [npc.feminineDescriptor(true)] face of a feral [npc.legRace], which is [npc.materialDescriptor] [npc.faceFullDescription(true)] and complete with a long muzzle.",
			Util.newArrayListOfValues(
					BodyPartTag.FACE_MUZZLE,
					BodyPartTag.FACE_FANGS,
					BodyPartTag.FACE_NATURAL_BALDNESS_FURRY
				)){
	};
	
	class Special extends AbstractFaceType {

		private String id;

		public Special(BodyCoveringType coveringType, Race race, MouthType mouthType, List<String> names, List<String> namesPlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String noseName, String noseNamePlural, List<String> noseDescriptorsMasculine, List<String> noseDescriptorsFeminine, String faceTransformationDescription, String faceBodyDescription, List<BodyPartTag> tags) {
			super(coveringType, race, mouthType, names, namesPlural, descriptorsMasculine, descriptorsFeminine, noseName, noseNamePlural, noseDescriptorsMasculine, noseDescriptorsFeminine, faceTransformationDescription, faceBodyDescription, tags);
		}

		public Special(BodyCoveringType coveringType, Race race, MouthType mouthType, List<String> names, List<String> namesPlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String noseName, String noseNamePlural, List<String> noseDescriptorsMasculine, List<String> noseDescriptorsFeminine, String faceTransformationDescription, String faceBodyDescription, String faceBodyDescriptionFeral, List<BodyPartTag> tags) {
			super(coveringType, race, mouthType, names, namesPlural, descriptorsMasculine, descriptorsFeminine, noseName, noseNamePlural, noseDescriptorsMasculine, noseDescriptorsFeminine, faceTransformationDescription, faceBodyDescription, faceBodyDescriptionFeral, tags);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(FaceType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<FaceType> table = new TypeTable<>(
		FaceType::sanitize,
		FaceType.class,
		AbstractFaceType.class,
		"face",
		(f,n,a,m)->new AbstractFaceType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	static FaceType getFaceTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP")) {
			return "DEMON_COMMON";
		}
		if(id.equals("LYCAN")) {
			return "WOLF_MORPH";
		}
		if(id.equals("TENGU")) {
			return "HARPY";
		}
		if(id.equals("CAT_MORPH_PANTHER")) {
			return "innoxia_panther_face";
		}

		return id;
	}

	static String getIdFromFaceType(FaceType faceType) {
		return faceType.getId();
	}

	static List<FaceType> getAllFaceTypes() {
		return table.listByRace();
	}

	static List<FaceType> getFaceTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
}