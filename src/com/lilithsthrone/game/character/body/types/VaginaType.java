package com.lilithsthrone.game.character.body.types;

import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractVaginaType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.3.8.9
 * @author Innoxia
 */
public interface VaginaType extends BodyPartTypeInterface {

	boolean isPubicHairAllowed();

	FluidType getFluidType();

	boolean isEggLayer();

	List<OrificeModifier> getDefaultRacialOrificeModifiers();

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	/**
	 * This method is called immediately before and immediately after the target's vagina type is changed into this type.
	 * When before, applicationAfterChangeApplied is false, and when after, applicationAfterChangeApplied is true.
	 * It is not called if owner is null.
	 */
	default String applyAdditionalTransformationEffects(GameCharacter owner, boolean applicationAfterChangeApplied) {
		return "";
	}

	@Override
	default String getDeterminer(GameCharacter gc) {
		return "";
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return false;
	}

	public static AbstractVaginaType NONE = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_HUMAN,
			Race.NONE,
			false,
			"[npc.She] moans and squirms about as [npc.she] [npc.verb(feel)] [npc.her] pussy suddenly tighten and squeeze shut, and with a desperate cry, a strange pressure washes through [npc.her] lower abdomen."
				+ " Thankfully, the feeling fades almost as quickly as it came, and when the discomfort subsides, [npc.she] [npc.verb(gasp)] as [npc.she] suddenly [npc.verb(realise)] that [npc.her] vagina [npc.has] totally vanished!"
				+ "<br/>[npc.Name] [npc.has] [style.boldSex(lost [npc.her] vagina)].",
			"no pussy.", // Shouldn't need a description of no vagina.
			null) {
		@Override
		public String applyAdditionalTransformationEffects(GameCharacter owner, boolean applicationAfterChangeApplied) {
			if(applicationAfterChangeApplied) {
				owner.setHymen(true);
				owner.setPiercedVagina(false);
				if(owner.isPlayer() && owner.hasFetish(Fetish.FETISH_PURE_VIRGIN)) {
					if(!owner.isVaginaVirgin()) {
						return UtilText.parse(owner,
								"<p style='text-align:center;'>"
									+ "[style.boldGood(Unbroken Virgin)]"
									+ "<br/><i>"
										+ "As the reality of losing [npc.her] worthless cunt sinks in, [npc.name] [npc.verb(start)] to think of [npc.herself] as something more than a dirty slut."
										+ " After all, if [npc.she] [npc.do]n't have a pussy, it's technically not possible for [npc.herHim] to have lost [npc.her] virginity!"
										+ " Letting out a deep sigh, [npc.she] [npc.verb(manage)] to convince [npc.herself] that [npc.she] no longer [npc.has] to worry about being a broken virgin."
									+ "<br/>"
										+ "Despite being able to finally see [npc.herself] as a dignified individual once again, the fact that [npc.sheIs] lacking the familiar feeling of having a pussy is making [npc.her] restless."
										+ " Perhaps if [npc.she] [npc.was] able to grow a new pussy, [npc.she]'d gain an unbroken hymen, thereby making [npc.herHim] a pure 'virgin' once again..."
									+ "</i><br/>"
									+ "[npc.NameIsFull] [style.boldGood(no longer a broken virgin)]!"
								+ "</p>");
					} else {
						return UtilText.parse(owner,
								"<p style='text-align:center;'>"
									+ "[style.boldBad(Pure Virgin?)]"
									+ "<br/><i>"
									+ "As the reality of losing [npc.her] vagina sinks in, [npc.name] [npc.verb(start)] to feel a rising panic in the back of [npc.her] mind."
									+ " After all, if [npc.she] [npc.do]n't have a pussy, [npc.she] can't be considered a pure virgin!"
									+ "</i><br/>"
									+ "Until [npc.name] can [npc.verb(regain)] [npc.her] lost vagina, [npc.she] will [style.boldBad(no longer be considered a pure virgin)]!"
								+ "</p>");
					}
				}
			}
			return "";
		}
	};
	
	public static AbstractVaginaType ONAHOLE = new Special(BodyCoveringType.DILDO,
			FluidType.GIRL_CUM_HUMAN,
			Race.NONE,
			false,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("toy", "artificial"),
			"You have somehow transformed your vagina into an onahole, which is a bug. (Please let Innoxia know!)", // Onaholes are not a transformable option
			"[npc.she] [npc.has] an onahole inserted into [npc.her] vagina, which is made out of [npc.vaginaFullDescription(true)].",
			null) {
	};

	public static AbstractVaginaType HUMAN = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_HUMAN,
			Race.HUMAN,
			false,
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
				+ " Within moments, the feeling starts to fade away, and with a surprised gasp, [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] shaped itself into a regular human vagina."
				+ " A warm, tingling feeling spreads up through [npc.her] lower abdomen, and as the transformation comes to an end, [npc.she] [npc.verb(realise)] that [npc.her] internal reproductive organs have also transformed into those of a human."
				+ "<br/>[npc.Name] now [npc.has] a [style.boldHuman(human vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced,#ELSEa#ENDIF human pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			null) {
	};

	public static AbstractVaginaType ANGEL = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_ANGEL,
			Race.ANGEL,
			false,
			Util.newArrayListOfValues("angel-"),
			Util.newArrayListOfValues("angel-"),
			Util.newArrayListOfValues("irresistible", "perfect"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
				+ " Within moments, the feeling starts to fade away, and with a surprised gasp, [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] shaped itself into an absolutely perfect-looking angelic vagina."
				+ " A warm, tingling feeling spreads up through [npc.her] lower abdomen, and as the transformation comes to an end, [npc.she] [npc.verb(realise)] that [npc.her] internal reproductive organs have also transformed into those of an angel."
				+ "<br/>[npc.Name] now [npc.has] an [style.boldAngel(angel vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced,#ELSEan#ENDIF angelic pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			Util.newArrayListOfValues()) {
	};

	public static AbstractVaginaType DEMON_COMMON = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_DEMON,
			Race.DEMON,
			false,
			Util.newArrayListOfValues("succubus-"),
			Util.newArrayListOfValues("succubus-"),
			Util.newArrayListOfValues("irresistible", "perfect"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " A strange, bubbling sensation starts running down deep into [npc.her] cunt,"
						+ " and [npc.she] [npc.verb(let)] out a lewd moan as [npc.she] [npc.verb(feel)] a new set of muscles forming all along the inner-walls of [npc.her] pussy."
					+ " With an experimental squeeze, [npc.she] quickly [npc.verb(discover)] that [npc.she] [npc.has] an incredible amount of control over [npc.her] pussy's new muscles."
					+ " With one last shiver of pleasure, [npc.her] pussy reshapes its exterior into an absolutely perfect-looking vagina."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy's new muscles involuntarily clench down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of fat demonic cocks slamming deep into [npc.her] new cunt flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their hot, virile seed deep into [npc.her] demonic womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>"
					+ "#IF(npc.isShortStature())"
						+ "[npc.Name] now [npc.has] an [style.boldImp(impish vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls."
					+ "#ELSE"
						+ "[npc.Name] now [npc.has] a [style.boldDemon(demonic vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls."
					+"#ENDIF",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced,#ELSEa#ENDIF demonic pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			Util.newArrayListOfValues(
				OrificeModifier.MUSCLE_CONTROL)) {
	};

//	public static AbstractVaginaType DEMON_EGGS = new Special(BodyCoveringType.VAGINA,
//			FluidType.GIRL_CUM_DEMON,
//			Race.DEMON,
//			true,
//			Util.newArrayListOfValues("succubus-"),
//			Util.newArrayListOfValues("succubus-"),
//			Util.newArrayListOfValues("irresistible", "perfect"),
//			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
//					+ " A strange, bubbling sensation starts running down deep into [npc.her] cunt,"
//						+ " and [npc.she] [npc.verb(let)] out a lewd moan as [npc.she] [npc.verb(feel)] a new set of muscles forming all along the inner-walls of [npc.her] pussy."
//					+ " With an experimental squeeze, [npc.she] quickly [npc.verb(discover)] that [npc.she] [npc.has] an incredible amount of control over [npc.her] pussy's new muscles."
//					+ " With one last shiver of pleasure, [npc.her] pussy reshapes its exterior into an absolutely perfect-looking vagina."
//				+ "</p>"
//				+ "<p>"
//					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy's new muscles involuntarily clench down,"
//						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
//					+ " Images of fat demonic cocks slamming deep into [npc.her] new cunt flash before [npc.her] eyes,"
//						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their hot, virile seed deep into [npc.her] demonic womb."
//					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
//						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
//					+ "<br/>"
//					+ "#IF(npc.isShortStature())"
//						+ "[npc.Name] now [npc.has] an [style.boldImp(impish, egg-laying vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls."
//					+ "#ELSE"
//						+ "[npc.Name] now [npc.has] a [style.boldDemon(demonic, egg-laying vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls."
//					+"#ENDIF",
//			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced,#ELSEa#ENDIF demonic, egg-laying pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
//			Util.newArrayListOfValues(
//				OrificeModifier.MUSCLE_CONTROL)) {
//		@Override
//		public String getTransformName() {
//			return "demonic (egg-laying)";
//		}
//	};

	public static AbstractVaginaType DOG_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_DOG_MORPH,
			Race.DOG_MORPH,
			false,
			Util.newArrayListOfValues("dog-", "bitch-"),
			Util.newArrayListOfValues("dog-", "bitch-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed and puffed up into a distinctly dog-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of red, knotted dog-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their potent seed deep into [npc.her] canine womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldDogMorph(dog vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced,#ELSEa#ENDIF dog-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			Util.newArrayListOfValues(
				OrificeModifier.PUFFY)) {
	};

	public static AbstractVaginaType WOLF_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_WOLF_MORPH,
			Race.WOLF_MORPH,
			false,
			Util.newArrayListOfValues("wolf-"),
			Util.newArrayListOfValues("wolf-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed and puffed up into a distinctly wolf-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of red, knotted wolf-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their potent seed deep into [npc.her] lupine womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldWolfMorph(wolf vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF wolf-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			Util.newArrayListOfValues(
				OrificeModifier.PUFFY)) {
	};
	
	public static AbstractVaginaType FOX_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_FOX_MORPH,
			Race.FOX_MORPH,
			false,
			Util.newArrayListOfValues("fox-", "vixen-"),
			Util.newArrayListOfValues("fox-", "vixen-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed and puffed up into a distinctly fox-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of red, knotted fox-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their potent seed deep into [npc.her] vulpine womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldFoxMorph(fox vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF fox-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			Util.newArrayListOfValues(
				OrificeModifier.PUFFY)) {
	};
	
	public static AbstractVaginaType CAT_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_CAT_MORPH,
			Race.CAT_MORPH,
			false,
			Util.newArrayListOfValues("cat-"),
			Util.newArrayListOfValues("cat-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed into a distinctly cat-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of barbed cat-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their potent seed deep into [npc.her] feline womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldCatMorph(cat vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF cat-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			null) {
	};

	public static AbstractVaginaType SQUIRREL_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_SQUIRREL_MORPH,
			Race.SQUIRREL_MORPH,
			false,
			Util.newArrayListOfValues("squirrel-"),
			Util.newArrayListOfValues("squirrel-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed into a distinctly squirrel-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of massive squirrel-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their potent seed deep into [npc.her] new womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldSquirrelMorph(squirrel vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF squirrel-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			null) {
	};

	public static AbstractVaginaType RAT_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_RAT_MORPH,
			Race.RAT_MORPH,
			false,
			Util.newArrayListOfValues("rat-"),
			Util.newArrayListOfValues("rat-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed into a distinctly rat-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of throbbing rat-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their potent seed deep into [npc.her] rodent womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldRatMorph(rat vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF rat-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			null) {
	};

	public static AbstractVaginaType RABBIT_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_RABBIT_MORPH,
			Race.RABBIT_MORPH,
			false,
			Util.newArrayListOfValues("rabbit-"),
			Util.newArrayListOfValues("rabbit-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed into a distinctly rabbit-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of thick rabbit-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their virile seed deep into [npc.her] new womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldRabbitMorph(rabbit vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF rabbit-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			null) {
	};

	public static AbstractVaginaType BAT_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_BAT_MORPH,
			Race.BAT_MORPH,
			false,
			Util.newArrayListOfValues("bat-"),
			Util.newArrayListOfValues("bat-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed into a distinctly bat-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of throbbing bat-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their hot seed deep into [npc.her] new womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldBatMorph(bat vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF bat-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			null) {
	};
	
	public static AbstractVaginaType ALLIGATOR_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_ALLIGATOR_MORPH,
			Race.ALLIGATOR_MORPH,
			true,
			Util.newArrayListOfValues("alligator-"),
			Util.newArrayListOfValues("alligator-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed into a distinctly alligator-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of fat alligator-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their hot seed deep into [npc.her] new womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] an [style.boldAlligatorMorph(alligator vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEan#ENDIF alligator-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			null) {
	};

	public static AbstractVaginaType COW_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_COW_MORPH,
			Race.COW_MORPH,
			false,
			Util.newArrayListOfValues("cow-"),
			Util.newArrayListOfValues("cow-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed into a distinctly cow-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of fat bull-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their hot seed deep into [npc.her] new womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldCowMorph(cow vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF cow-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			null) {
	};

	public static AbstractVaginaType HORSE_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_HORSE_MORPH,
			Race.HORSE_MORPH,
			false,
			Util.newArrayListOfValues("#IF(npc.getRace()==npc.getVaginaRace())[npc.raceFeral]#ELSE[npc.pussyRaceFeral]#ENDIF-", "mare-"),
			Util.newArrayListOfValues("#IF(npc.getRace()==npc.getVaginaRace())[npc.raceFeral]#ELSE[npc.pussyRaceFeral]#ENDIF-", "mare-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " A strange, bubbling sensation starts running down deep into [npc.her] cunt,"
						+ " and [npc.she] [npc.verb(let)] out a lewd moan as [npc.she] [npc.verb(feel)] a new set of muscles forming all along the inner-walls of [npc.her] pussy."
					+ " With an experimental squeeze, [npc.she] quickly [npc.verb(discover)] that [npc.she] [npc.has] an incredible amount of control over [npc.her] pussy's new muscles."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed itself and puffed up into a distinctly horse-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of flared stallion-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their hot seed deep into [npc.her] new womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldHorseMorph(horse vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF horse-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			Util.newArrayListOfValues(
				OrificeModifier.PUFFY,
				OrificeModifier.MUSCLE_CONTROL)) {
	};

	public static AbstractVaginaType REINDEER_MORPH = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_REINDEER_MORPH,
			Race.REINDEER_MORPH,
			false,
			Util.newArrayListOfValues("reindeer-"),
			Util.newArrayListOfValues("reindeer-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed itself and puffed up into a distinctly reindeer-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of throbbing reindeer-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their hot seed deep into [npc.her] new womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldReindeerMorph(reindeer vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF reindeer-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			Util.newArrayListOfValues(
				OrificeModifier.PUFFY)) {
	};

	public static AbstractVaginaType HARPY = new Special(BodyCoveringType.VAGINA,
			FluidType.GIRL_CUM_HARPY,
			Race.HARPY,
			true,
			Util.newArrayListOfValues("bird-"),
			Util.newArrayListOfValues("bird-"),
			Util.newArrayListOfValues("hot"),
			"[npc.She] [npc.verb(let)] out a squeal of excitement as a wave of pleasure runs up through [npc.her] groin, which increases in intensity as [npc.she] [npc.verb(feel)] [npc.her] slit uncontrollably shifting and contracting."
					+ " Within moments, the feeling fades away, and [npc.she] [npc.verb(discover)] that [npc.her] pussy [npc.has] reformed itself into a distinctly bird-like form."
				+ "</p>"
				+ "<p>"
					+ "Just as [npc.she] [npc.verb(start)] think that the transformation [npc.has] come to an end, [npc.her] pussy involuntarily clenches down,"
						+ " and a desperate squeal escapes from between [npc.her] [npc.lips+] as a warm, tingling feeling spreads up through [npc.her] lower abdomen."
					+ " Images of throbbing bird-cocks slamming deep into [npc.her] new pussy flash before [npc.her] eyes,"
						+ " and [npc.her] squeal turns into a satisfied moan as [npc.she] [npc.verb(imagine)] them pumping their hot seed deep into [npc.her] avian womb."
					+ " Just as quickly as they came, the images fade from [npc.her] mind, and as one last wave of tingling pleasure washes through [npc.her] body,"
						+ " [npc.she] [npc.verb(feel)] [npc.her] female reproductive organs finishing their transformation."
					+ "<br/>[npc.Name] now [npc.has] a [style.boldHarpy(bird vagina)], with [npc.pussyColourPrimary(true)] labia and [npc.pussyColourSecondary(true)] internal walls.",
			"[npc.she] [npc.has] #IF(npc.isPiercedVagina())a pierced#ELSEa#ENDIF bird-pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.",
			null) {
	};
	
	class Special extends AbstractVaginaType {

		private String id;

		public Special(BodyCoveringType coveringType, FluidType fluidType, Race race, boolean eggLayer, List<String> names, List<String> namesPlural, List<String> descriptors, String transformationDescription, String bodyDescription, List<OrificeModifier> defaultRacialOrificeModifiers) {
			super(coveringType, fluidType, race, eggLayer, names, namesPlural, descriptors, transformationDescription, bodyDescription, defaultRacialOrificeModifiers);
		}

		public Special(BodyCoveringType skinType, FluidType fluidType, Race race, boolean eggLayer, String transformationDescription, String bodyDescription, List<OrificeModifier> defaultRacialOrificeModifiers) {
			super(skinType, fluidType, race, eggLayer, transformationDescription, bodyDescription, defaultRacialOrificeModifiers);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(VaginaType.class.getFields())
			.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
			.findAny().orElseThrow().getName());
		}
	}

	TypeTable<VaginaType> table = new TypeTable<>(
		VaginaType::sanitize,
		VaginaType.class,
		AbstractVaginaType.class,
		"vagina",
		(f,n,a,m)->new AbstractVaginaType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	static VaginaType getVaginaTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		return switch(id) {
			case "IMP", "DEMON_EGGS" -> "DEMON_COMMON";
			case "NoStepOnSnek_snake_vagina_e" -> "NoStepOnSnek_snake_vagina";
			default -> id;
		};
	}

	static String getIdFromVaginaType(VaginaType vaginaType) {
		return vaginaType.getId();
	}

	static List<VaginaType> getAllVaginaTypes() {
		return table.listByRace();
	}

	static List<VaginaType> getVaginaTypes(Race r) {
		return table.of(r).orElse(List.of());
	}
}