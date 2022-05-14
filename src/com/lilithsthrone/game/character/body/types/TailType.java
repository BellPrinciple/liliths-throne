package com.lilithsthrone.game.character.body.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.game.character.body.TypeTable;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractTailType;
import com.lilithsthrone.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.body.valueEnums.PenetrationGirth;
import com.lilithsthrone.game.character.race.AbstractRace;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.enchanting.TFModifier;
import com.lilithsthrone.utils.Util;

/**
 * @since 0.1.0
 * @version 0.3.7
 * @author Innoxia
 */
public interface TailType extends BodyPartTypeInterface {

	int getDefaultGirth();

	float getDefaultLengthAsPercentageOfHeight();

	String getTailTipNameSingular(GameCharacter gc);

	String getTailTipNamePlural(GameCharacter gc);

	String getTailTipDescriptor(GameCharacter gc);

	String getBodyDescription(GameCharacter owner);

	String getTransformationDescription(GameCharacter owner);

	boolean hasSpinneret();

	/**
	 * @return
	 * A description of this tail's girth, based on the TYPE tag and the owner's girth.
	 */
	default String getGirthDescription(GameCharacter owner) {
		List<BodyPartTag> tags = getTags();
		StringBuilder sb = new StringBuilder();
		if(tags.contains(BodyPartTag.TAIL_TYPE_SKIN)
				|| tags.contains(BodyPartTag.TAIL_TYPE_SCALES)) {
			if(owner.getTailCount()>1) {
				sb.append(UtilText.parse(owner, " [npc.Her] [npc.tails] are"));
			} else {
				sb.append(UtilText.parse(owner, " [npc.Her] [npc.tail] is"));
			}
			switch(owner.getTailGirth()) {
			case ZERO_THIN:
				sb.append(UtilText.parse(owner, " very thin in proportion to the rest of [npc.her] body."));
				break;
			case ONE_SLENDER:
				sb.append(UtilText.parse(owner, " slender in proportion to the rest of [npc.her] body."));
				break;
			case TWO_NARROW:
				sb.append(UtilText.parse(owner, " quite narrow in proportion to the rest of [npc.her] body."));
				break;
			case THREE_AVERAGE:
				sb.append(UtilText.parse(owner, " of an average thickness in proportion to the rest of [npc.her] body."));
				break;
			case FOUR_GIRTHY:
				sb.append(UtilText.parse(owner, " quite thick in proportion to the rest of [npc.her] body."));
				break;
			case FIVE_THICK:
				sb.append(UtilText.parse(owner, " very thick in proportion to the rest of [npc.her] body."));
				break;
			case SIX_CHUBBY:
				sb.append(UtilText.parse(owner, " incredibly thick and girthy in proportion to the rest of [npc.her] body."));
				break;
			case SEVEN_FAT:
				sb.append(UtilText.parse(owner, " extremely thick and girthy in proportion to the rest of [npc.her] body."));
				break;
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_FUR)) {
			if(owner.getTailCount()>1) {
				sb.append(UtilText.parse(owner, " [npc.Her] [npc.tails] are"));
			} else {
				sb.append(UtilText.parse(owner, " [npc.Her] [npc.tail] is"));
			}
			switch(owner.getTailGirth()) {
			case ZERO_THIN:
				sb.append(UtilText.parse(owner, " very thin and severely lacking in fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case ONE_SLENDER:
				sb.append(UtilText.parse(owner, " slender and lacking in fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case TWO_NARROW:
				sb.append(UtilText.parse(owner, " quite narrow and a little lacking in fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case THREE_AVERAGE:
				sb.append(UtilText.parse(owner, " of an average thickness and fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case FOUR_GIRTHY:
				sb.append(UtilText.parse(owner, " quite big and very fluffy in proportion to the rest of [npc.her] body."));
				break;
			case FIVE_THICK:
				sb.append(UtilText.parse(owner, " very big and fluffy in proportion to the rest of [npc.her] body."));
				break;
			case SIX_CHUBBY:
				sb.append(UtilText.parse(owner, " incredibly thick and fluffy in proportion to the rest of [npc.her] body."));
				break;
			case SEVEN_FAT:
				sb.append(UtilText.parse(owner, " extremely thick and fluffy in proportion to the rest of [npc.her] body."));
				break;
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_TUFT)) {
			if(owner.getTailCount()>1) {
				sb.append(UtilText.parse(owner, " [npc.Her] tufted tails are"));
			} else {
				sb.append(UtilText.parse(owner, " [npc.Her] tufted tail is"));
			}
			switch(owner.getTailGirth()) {
			case ZERO_THIN:
				sb.append(UtilText.parse(owner, " very small and significantly lacking in fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case ONE_SLENDER:
				sb.append(UtilText.parse(owner, " quite small and lacking in fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case TWO_NARROW:
				sb.append(UtilText.parse(owner, " a little small and lacking in fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case THREE_AVERAGE:
				sb.append(UtilText.parse(owner, " of an average size and fluffiness in proportion to the rest of [npc.her] body."));
				break;
			case FOUR_GIRTHY:
				sb.append(UtilText.parse(owner, " quite big and very fluffy in proportion to the rest of [npc.her] body."));
				break;
			case FIVE_THICK:
				sb.append(UtilText.parse(owner, " very big and extremely fluffy in proportion to the rest of [npc.her] body."));
				break;
			case SIX_CHUBBY:
				sb.append(UtilText.parse(owner, " incredibly thick and fluffy in proportion to the rest of [npc.her] body."));
				break;
			case SEVEN_FAT:
				sb.append(UtilText.parse(owner, " extremely thick and fluffy in proportion to the rest of [npc.her] body."));
				break;
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_HAIR)) {
			if(owner.getTailCount()>1) {
				sb.append(UtilText.parse(owner, " [npc.Her] horse tails are"));
			} else {
				sb.append(UtilText.parse(owner, " [npc.Her] horse tail is"));
			}
			switch(owner.getTailGirth()) {
			case ZERO_THIN:
				sb.append(UtilText.parse(owner, " very much lacking in volume in proportion to the rest of [npc.her] body."));
				break;
			case ONE_SLENDER:
				sb.append(UtilText.parse(owner, " lacking in volume in proportion to the rest of [npc.her] body."));
				break;
			case TWO_NARROW:
				sb.append(UtilText.parse(owner, " a little lacking in volume in proportion to the rest of [npc.her] body."));
				break;
			case THREE_AVERAGE:
				sb.append(UtilText.parse(owner, " of an average volume in proportion to the rest of [npc.her] body."));
				break;
			case FOUR_GIRTHY:
				sb.append(UtilText.parse(owner, " quite voluminous in proportion to the rest of [npc.her] body."));
				break;
			case FIVE_THICK:
				sb.append(UtilText.parse(owner, " very voluminous in proportion to the rest of [npc.her] body."));
				break;
			case SIX_CHUBBY:
				sb.append(UtilText.parse(owner, " incredibly voluminous in proportion to the rest of [npc.her] body."));
				break;
			case SEVEN_FAT:
				sb.append(UtilText.parse(owner, " extremely voluminous in proportion to the rest of [npc.her] body."));
				break;
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_FEATHER)) {
			if(owner.getTailCount()>1) {
				sb.append(UtilText.parse(owner, " [npc.Her] plumes of feathers are"));
			} else {
				sb.append(UtilText.parse(owner, " [npc.Her] plume of feathers is"));
			}
			switch(owner.getTailGirth()) {
			case ZERO_THIN:
				sb.append(UtilText.parse(owner, " very small and lacking in volume in proportion to the rest of [npc.her] body."));
				break;
			case ONE_SLENDER:
				sb.append(UtilText.parse(owner, " small and somewhat lacking in volume in proportion to the rest of [npc.her] body."));
				break;
			case TWO_NARROW:
				sb.append(UtilText.parse(owner, " a little narrow and lacking in volume in proportion to the rest of [npc.her] body."));
				break;
			case THREE_AVERAGE:
				sb.append(UtilText.parse(owner, " of an average size and volume in proportion to the rest of [npc.her] body."));
				break;
			case FOUR_GIRTHY:
				sb.append(UtilText.parse(owner, " quite large and voluminous in proportion to the rest of [npc.her] body."));
				break;
			case FIVE_THICK:
				sb.append(UtilText.parse(owner, " very large and voluminous in proportion to the rest of [npc.her] body."));
				break;
			case SIX_CHUBBY:
				sb.append(UtilText.parse(owner, " incredibly voluminous in proportion to the rest of [npc.her] body."));
				break;
			case SEVEN_FAT:
				sb.append(UtilText.parse(owner, " extremely voluminous in proportion to the rest of [npc.her] body."));
				break;
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_GENERIC)) {
			if(owner.getTailCount()>1) {
				sb.append(UtilText.parse(owner, " [npc.Her] tails are"));
			} else {
				sb.append(UtilText.parse(owner, " [npc.Her] tail is"));
			}
			switch(owner.getTailGirth()) {
			case ZERO_THIN:
				sb.append(UtilText.parse(owner, " very small in proportion to the rest of [npc.her] body."));
				break;
			case ONE_SLENDER:
				sb.append(UtilText.parse(owner, " somewhat small in proportion to the rest of [npc.her] body."));
				break;
			case TWO_NARROW:
				sb.append(UtilText.parse(owner, " a little narrow in proportion to the rest of [npc.her] body."));
				break;
			case THREE_AVERAGE:
				sb.append(UtilText.parse(owner, " of an average size in proportion to the rest of [npc.her] body."));
				break;
			case FOUR_GIRTHY:
				sb.append(UtilText.parse(owner, " quite large in proportion to the rest of [npc.her] body."));
				break;
			case FIVE_THICK:
				sb.append(UtilText.parse(owner, " very large in proportion to the rest of [npc.her] body."));
				break;
			case SIX_CHUBBY:
				sb.append(UtilText.parse(owner, " incredibly large in proportion to the rest of [npc.her] body."));
				break;
			case SEVEN_FAT:
				sb.append(UtilText.parse(owner, " extremely large in proportion to the rest of [npc.her] body."));
				break;
			}
		}
		return sb.toString();
	}

	default String getGirthDescriptor(GameCharacter owner) {
		return getGirthDescriptor(owner.getTailGirth());
	}

	default String getGirthDescriptor(Body body) {
		return getGirthDescriptor(body.getTail().getGirth());
	}

	default String getGirthDescriptor(PenetrationGirth girth) {
		List<BodyPartTag> tags = getTags();
		if(tags.contains(BodyPartTag.TAIL_TYPE_SKIN)
				|| tags.contains(BodyPartTag.TAIL_TYPE_SCALES)) {
			switch(girth) {
			case ZERO_THIN:
				return "thin";
			case ONE_SLENDER:
				return "slender";
			case TWO_NARROW:
				return "narrow";
			case THREE_AVERAGE:
				return "average";
			case FOUR_GIRTHY:
				return "thick";
			case FIVE_THICK:
				return "extra-thick";
			case SIX_CHUBBY:
				return "extremely-thick";
			case SEVEN_FAT:
				return "unbelievably-thick";
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_FUR)) {
			switch(girth) {
			case ZERO_THIN:
				return "thin";
			case ONE_SLENDER:
				return "slender";
			case TWO_NARROW:
				return "narrow";
			case THREE_AVERAGE:
				return "fluffy";
			case FOUR_GIRTHY:
				return "very-fluffy";
			case FIVE_THICK:
				return "extra-fluffy";
			case SIX_CHUBBY:
				return "extremely-fluffy";
			case SEVEN_FAT:
				return "unbelievably-fluffy";
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_TUFT)) {
			switch(girth) {
			case ZERO_THIN:
				return "tiny";
			case ONE_SLENDER:
				return "small";
			case TWO_NARROW:
				return "narrow";
			case THREE_AVERAGE:
				return "fluffy";
			case FOUR_GIRTHY:
				return "very-fluffy";
			case FIVE_THICK:
				return "extra-fluffy";
			case SIX_CHUBBY:
				return "extremely-fluffy";
			case SEVEN_FAT:
				return "unbelievably-fluffy";
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_HAIR)) {
			switch(girth) {
			case ZERO_THIN:
				return "thin";
			case ONE_SLENDER:
				return "small";
			case TWO_NARROW:
				return "narrow";
			case THREE_AVERAGE:
				return "average";
			case FOUR_GIRTHY:
				return "voluminous";
			case FIVE_THICK:
				return "extra-voluminous";
			case SIX_CHUBBY:
				return "extremely-voluminous";
			case SEVEN_FAT:
				return "unbelievably-voluminous";
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_FEATHER)) {
			switch(girth) {
			case ZERO_THIN:
				return "thin";
			case ONE_SLENDER:
				return "small";
			case TWO_NARROW:
				return "narrow";
			case THREE_AVERAGE:
				return "average";
			case FOUR_GIRTHY:
				return "voluminous";
			case FIVE_THICK:
				return "extra-voluminous";
			case SIX_CHUBBY:
				return "extremely-voluminous";
			case SEVEN_FAT:
				return "unbelievably-voluminous";
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_GENERIC)) {
			switch(girth) {
			case ZERO_THIN:
				return "tiny";
			case ONE_SLENDER:
				return "small";
			case TWO_NARROW:
				return "narrow";
			case THREE_AVERAGE:
				return "average";
			case FOUR_GIRTHY:
				return "large";
			case FIVE_THICK:
				return "huge";
			case SIX_CHUBBY:
				return "massive";
			case SEVEN_FAT:
				return "colossal";
			}
		}
		return girth.getName();
	}

	default String getGirthTransformationDescription(GameCharacter owner, boolean positive) {
		List<BodyPartTag> tags = getTags();
		String tailText = owner.getTailCount()>1 ? "[npc.tailGirth] [npc.tails]" : "[npc.a_tailGirth] [npc.tail]";
		if(tags.contains(BodyPartTag.TAIL_TYPE_SKIN)
				|| tags.contains(BodyPartTag.TAIL_TYPE_SCALES)
				|| tags.contains(BodyPartTag.TAIL_TYPE_FUR)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(grow thicker)]."
				:" [npc.tail] suddenly [style.boldGrow(grows thicker)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shrink down)]."
				:" [npc.tail] suddenly [style.boldShrink(shrinks down)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_TUFT)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(fluff up and grow bigger)]."
				:" [npc.tail] suddenly [style.boldGrow(fluffs up and grows bigger)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shrink down)]."
				:" [npc.tail] suddenly [style.boldShrink(shrinks down)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_HAIR)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(fill out and expand in volume)]."
				:" [npc.tail] suddenly [style.boldGrow(fills out and expands in volume)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shrink down and lose volume)]."
				:" [npc.tail] suddenly [style.boldShrink(shrinks down and loses volume)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_FEATHER)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(fill out and expand in volume)]."
				:" [npc.tail] suddenly [style.boldGrow(fills out and expands in volume)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shrink down and lose volume)]."
				:" [npc.tail] suddenly [style.boldShrink(shrinks down and loses volume)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_GENERIC)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(grow larger)]."
				:" [npc.tail] suddenly [style.boldGrow(grows larger)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shrink down)]."
				:" [npc.tail] suddenly [style.boldShrink(shrinks down)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric("+tailText+")]!"
				+ "</p>");
			}
		}
		return "";
	}

	default String getLengthTransformationDescription(GameCharacter owner, boolean positive) {
		List<BodyPartTag> tags = getTags();
		String heightPercentageDescription = " (length is "+((int)(owner.getTailLengthAsPercentageOfHeight()*100))+"% of [npc.namePos] height)";
		if(tags.contains(BodyPartTag.TAIL_TYPE_SKIN)
				|| tags.contains(BodyPartTag.TAIL_TYPE_SCALES)
				|| tags.contains(BodyPartTag.TAIL_TYPE_FUR)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(grow longer)]."
				:" [npc.tail] suddenly [style.boldGrow(grows longer)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shorten)]."
				:" [npc.tail] suddenly [style.boldShrink(shortens)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_TUFT)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(fluff up and grow longer)]."
				:" [npc.tail] suddenly [style.boldGrow(fluffs up and grows longer)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shorten)]."
				:" [npc.tail] suddenly [style.boldShrink(shortens)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_HAIR)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(grow longer)]."
				:" [npc.tail] suddenly [style.boldGrow(grows longer)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shorten)]."
				:" [npc.tail] suddenly [style.boldShrink(shortens)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_FEATHER)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(grow longer)]."
				:" [npc.tail] suddenly [style.boldGrow(grows longer)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shorten)]."
				:" [npc.tail] suddenly [style.boldShrink(shortens)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");
			}
		}
		if(tags.contains(BodyPartTag.TAIL_TYPE_GENERIC)) {
			if(positive) {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldGrow(grow longer)]."
				:" [npc.tail] suddenly [style.boldGrow(grows longer)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");

			} else {
				return UtilText.parse(owner,
				"<p>"
				+ "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a deep throbbing sensation building up at the base of [npc.her] spine."
				+ " Without any further warning of what's to come, [npc.her]"
				+(owner.getTailCount()>1
				?" [npc.tails] suddenly [style.boldShrink(shorten)]."
				:" [npc.tail] suddenly [style.boldShrink(shortens)].")
				+ "<br/>"
				+ "[npc.She] now [npc.has] [style.boldTfGeneric([npc.a_tailLength] [npc.tail])]"+heightPercentageDescription+"!"
				+ "</p>");
			}
		}
		return "";
	}

	default boolean isPrehensile() {
		return getTags().contains(BodyPartTag.TAIL_PREHENSILE);
	}

	default boolean isSuitableForSleepHugging() {
		return getTags().contains(BodyPartTag.TAIL_SLEEP_HUGGING);
	}

	default boolean isSuitableForAttack() {
		return getTags().contains(BodyPartTag.TAIL_ATTACK);
	}

	default boolean isOvipositor() {
		return getTags().contains(BodyPartTag.TAIL_OVIPOSITOR);
	}

	@Override
	default boolean isDefaultPlural(GameCharacter gc) {
		return false;
	}

	@Override
	default String getName(GameCharacter gc) {
		if(isDefaultPlural(gc) || (gc!=null && gc.getTailCount()!=1)) {
			return getNamePlural(gc);
		} else {
			return getNameSingular(gc);
		}
	}

	@Override
	default TFModifier getTFModifier() {
		return getTFTypeModifier(TailType.getTailTypes(getRace()));
	}

	public static final AbstractTailType NONE = new Special(
			null,
			Race.NONE,
			PenetrationGirth.THREE_AVERAGE,
			0f,
			"none",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues(),
			Util.newArrayListOfValues(),
			"",
			"",
			Util.newArrayListOfValues(),
			Util.newArrayListOfValues(),
			"#IF(npc.getTailCount()==1)"
				+ " [npc.She] gasps as [npc.she] feels [npc.her] [npc.tail] shrinking down and disappearing into [npc.her] lower back."
			+ "#ELSE"
				+ " [npc.She] gasps as [npc.she] feels [npc.her] [npc.tails] shrinking down and disappearing into [npc.her] lower back."
			+ "#ENDIF"
			+ "<br/>"
			+ "[npc.Name] now [npc.has] [style.boldTfGeneric(no tail)].",
			"[style.colourDisabled([npc.She] [npc.do] not have a tail.)]",
			Util.newArrayListOfValues(), false) {
		@Override
		public TFModifier getTFModifier() {
			return TFModifier.REMOVAL;
		}
	};
	
	public static final AbstractTailType DEMON_COMMON = new Special(
			BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			PenetrationGirth.ONE_SLENDER,
			1f,
			"demonic spaded",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("spaded", "demonic"),
			Util.newArrayListOfValues("spaded", "demonic"),
			"tip",
			"tips",
			Util.newArrayListOfValues("spaded"),
			Util.newArrayListOfValues("spaded"),
			"#IF(npc.getTailCount()==1)"
				+ " A demonic, spaded tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] complete control over where it goes, allowing [npc.herHim] to use it like a third limb."
				+ "<br/>"
				+ "[npc.Name] now [npc.has]"
				+ "#IF(npc.isShortStature())"
					+ " an [style.boldImp(impish tail)]"
				+ "#ELSE"
					+ " a [style.boldDemon(demonic tail)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] demonic, spaded tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] complete control over where they go, allowing [npc.herHim] to use them like extra limbs."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount]"
				+ "#IF(npc.isShortStature())"
					+ " [style.boldImp(impish tails)]"
				+ "#ELSE"
					+ " [style.boldDemon(demonic tails)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a spaded, [npc.tailColour(true)] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tail, over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use it to grip and hold objects."
				+ "#ELSE"
					+ " [npc.tailCount] spaded, [npc.tailColour(true)] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tails, over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use them to grip and hold objects."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_SKIN,
					BodyPartTag.TAIL_TAPERING_EXPONENTIAL), false) {
	};

	public static final AbstractTailType DEMON_HAIR_TIP = new Special(
			BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			PenetrationGirth.ONE_SLENDER,
			0.5f,
			"demonic hair-tipped",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("hair-tipped", "demonic"),
			Util.newArrayListOfValues("hair-tipped", "demonic"),
			"tip",
			"tips",
			Util.newArrayListOfValues("hair-tufted"),
			Util.newArrayListOfValues("hair-tufted"),
			"#IF(npc.getTailCount()==1)"
				+ " A demonic tail, tipped with a tuft of hair, sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] complete control over where it goes, allowing [npc.herHim] to use it like a third limb."
				+ "<br/>"
				+ "[npc.Name] now [npc.has]"
				+ "#IF(npc.isShortStature())"
					+ " an [style.boldImp(impish tail)]"
				+ "#ELSE"
					+ " a [style.boldDemon(demonic tail)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tailFullDescription(true)] and tipped with [#npc.getCovering(BODY_COVERING_TYPE_HAIR_DEMON).getFullDescription(npc, true)]."
			+ "#ELSE"
				+ " [npc.TailCount] demonic tails, tipped with a tuft of hair, sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] complete control over where they go, allowing [npc.herHim] to use them like extra limbs."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount]"
				+ "#IF(npc.isShortStature())"
					+ " [style.boldImp(impish tails)]"
				+ "#ELSE"
					+ " [style.boldDemon(demonic tails)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tailFullDescription(true)] and tipped with [#npc.getCovering(BODY_COVERING_TYPE_HAIR_DEMON).getFullDescription(npc, true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a [npc.tailColour(true)], #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tail, tipped with [#npc.getCovering(BODY_COVERING_TYPE_HAIR_DEMON).getFullDescription(npc, true)],"
						+ " over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use it to grip and hold objects."
				+ "#ELSE"
					+ " [npc.tailCount] [npc.tailColour(true)], #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tails, tipped with [#npc.getCovering(BODY_COVERING_TYPE_HAIR_DEMON).getFullDescription(npc, true)],"
						+ " over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use them to grip and hold objects."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_SKIN,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};

	public static final AbstractTailType DEMON_TAPERED = new Special(
			BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			PenetrationGirth.THREE_AVERAGE,
			0.75f,
			"demonic tapered",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("tapered", "demonic"),
			Util.newArrayListOfValues("tapered", "demonic"),
			"tip",
			"tips",
			Util.newArrayListOfValues("tapered"),
			Util.newArrayListOfValues("tapered"),
			"#IF(npc.getTailCount()==1)"
				+ " A demonic, tapered tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] complete control over where it goes, allowing [npc.herHim] to use it like a third limb."
				+ "<br/>"
				+ "[npc.Name] now [npc.has]"
				+ "#IF(npc.isShortStature())"
					+ " an [style.boldImp(impish, tapered tail)]"
				+ "#ELSE"
					+ " a [style.boldDemon(demonic, tapered tail)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] demonic, tapered tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] complete control over where they go, allowing [npc.herHim] to use them like extra limbs."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount]"
				+ "#IF(npc.isShortStature())"
					+ " [style.boldImp(impish, tapered tails)]"
				+ "#ELSE"
					+ " [style.boldDemon(demonic, tapered tails)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a tapered, [npc.tailColour(true)] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tail, over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use it to grip and hold objects."
				+ "#ELSE"
					+ " [npc.tailCount] tapered, [npc.tailColour(true)] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tails, over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use them to grip and hold objects."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_SKIN,
					BodyPartTag.TAIL_TAPERING_LINEAR), false) {
	};
	
	public static final AbstractTailType DEMON_HORSE = new Special(
			BodyCoveringType.HORSE_HAIR,
			Race.DEMON,
			PenetrationGirth.THREE_AVERAGE,
			0.3f,
			"demonic horse",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("horse-like"),
			Util.newArrayListOfValues("horse-like"),
			"end",
			"ends",
			Util.newArrayListOfValues("loose"),
			Util.newArrayListOfValues("loose"),
			"#IF(npc.getTailCount()==1)"
				+ " A horse-like tail sprouts from just above [npc.her] ass, rapidly growing in length until it hangs down about [npc.tailLength] behind [npc.herHim]."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over it is limited to swishing it from side to side."
				+ "<br/>"
				+ "[npc.Name] now [npc.has]"
				+ "#IF(npc.isShortStature())"
					+ " an [style.boldImp(impish, horse-like tail)]"
				+ "#ELSE"
					+ " a [style.boldDemon(demonic, horse-like tail)]"
				+ "#ENDIF"
				+ ", made out of [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] horse-like tails sprout from just above [npc.her] ass, rapidly growing in length until they hang down about [npc.tailLength] behind [npc.herHim]."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over them is limited to swishing them from side to side."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount]"
				+ "#IF(npc.isShortStature())"
					+ " [style.boldImp(impish, horse-like tails)]"
				+ "#ELSE"
					+ " [style.boldDemon(demonic, horse-like tails)]"
				+ "#ENDIF"
				+ ", made out of [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)], #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF-horse tail,"
						+ " which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF-horse tails,"
						+ " which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over them."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_TYPE_HAIR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};

	public static final AbstractTailType DEMON_OVIPOSITOR = new Special(
			BodyCoveringType.DEMON_COMMON,
			Race.DEMON,
			PenetrationGirth.THREE_AVERAGE,
			0.75f,
			"demonic ovipositor",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("ovipositor", "demonic"),
			Util.newArrayListOfValues("ovipositor", "demonic"),
			"tip",
			"tips",
			Util.newArrayListOfValues("ovipositor"),
			Util.newArrayListOfValues("ovipositor"),
			"#IF(npc.getTailCount()==1)"
				+ " A demonic, tapered tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " The end swells out a little into a slightly bulbous, tapered bulge, before a vice-tight, cross-shaped slit forms across its tip."
				+ " Letting out [npc.a_moan], [npc.name] instinctively [npc.verb(feel)] that this new tail of [npc.hers] can be used as an ovipositor."
				+ " Turning it this way and that, [npc.she] quickly [npc.verb(discover)] that [npc.she] [npc.has] complete control over where it goes, allowing [npc.herHim] to use it like a third limb."
				+ "<br/>"
				+ "[npc.Name] now [npc.has]"
				+ "#IF(npc.isShortStature())"
					+ " an [style.boldImp(impish, ovipositor tail)]"
				+ "#ELSE"
					+ " a [style.boldDemon(demonic, ovipositor tail)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] demonic, tapered tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " The ends of each one swell out a little into slightly bulbous, tapered bulges, before vice-tight, cross-shaped slits form across their tips."
				+ " Letting out [npc.a_moan], [npc.name] instinctively [npc.verb(feel)] that these new tails of [npc.hers] can be used as ovipositors."
				+ " Turning them this way and that, [npc.she] quickly [npc.verb(discover)] that [npc.she] [npc.has] complete control over where they go, allowing [npc.herHim] to use them like extra limbs."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount]"
				+ "#IF(npc.isShortStature())"
					+ " [style.boldImp(impish, ovipositor tails)]"
				+ "#ELSE"
					+ " [style.boldDemon(demonic, ovipositor tails)]"
				+ "#ENDIF"
				+ ", [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a tapered, [npc.tailColour(true)] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tail, over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use it to grip and hold objects."
					+ "  The end has swollen out a little into a slightly bulbous, tapered bulge, while a vice-tight, cross-shaped slit has formed across its tip, allowing [npc.herHim] to use it as an ovipositor."
				+ "#ELSE"
					+ " [npc.tailCount] tapered, [npc.tailColour(true)] #IF(npc.isShortStature())impish#ELSEdemonic#ENDIF tails, over which [npc.sheHasFull] complete control, allowing [npc.herHim] to use them to grip and hold objects."
					+ "  The ends of each one have swollen out a little into slightly bulbous, tapered bulges, while vice-tight, cross-shaped slits have formed across their tips, allowing [npc.herHim] to use them as ovipositors."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_SKIN,
					BodyPartTag.TAIL_TAPERING_LINEAR,
					BodyPartTag.TAIL_OVIPOSITOR),
			false) {
	};

	public static final AbstractTailType ALLIGATOR_MORPH = new Special(
			BodyCoveringType.ALLIGATOR_SCALES,
			Race.ALLIGATOR_MORPH,
			PenetrationGirth.FIVE_THICK,
			0.6f,
			"alligator",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("alligator-like"),
			Util.newArrayListOfValues("alligator-like"),
			"tip",
			"tips",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"#IF(npc.getTailCount()==1)"
				+ " A scaly, alligator-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.she] can swish it from side to side with considerable force."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldAlligatorMorph(alligator-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] scaly, alligator-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.she] can swish them from side to side with considerable force."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldAlligatorMorph(alligator-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] alligator tail, which [npc.she] can swipe from side to side with considerable force."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] alligator tails, which [npc.she] can swipe from side to side with considerable force."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_SCALES,
					BodyPartTag.TAIL_TAPERING_LINEAR,
					BodyPartTag.TAIL_ATTACK), false) {
	};
	
	public static final AbstractTailType BAT_MORPH = new Special(
			BodyCoveringType.BAT_SKIN,
			Race.BAT_MORPH,
			PenetrationGirth.ONE_SLENDER,
			0.2f,
			"bat",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("bat-like"),
			Util.newArrayListOfValues("bat-like"),
			"tip",
			"tips",
			Util.newArrayListOfValues("furry"),
			Util.newArrayListOfValues("furry"),
			"#IF(npc.getTailCount()==1)"
				+ " A small, bat-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over it, and can twist it almost anywhere [npc.she] [npc.verb(please)]."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldBatMorph(bat-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] small, bat-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over them, and can twist them almost anywhere [npc.she] [npc.verb(please)]."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldBatMorph(bat-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] bat tail,"
						+ " which [npc.she] can rapidly move up and down to help [npc.herHim] keep [npc.her] balance and to control [npc.her] path when in flight."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] bat tails,"
						+ " which [npc.she] can rapidly move up and down to help [npc.herHim] keep [npc.her] balance and to control [npc.her] path when in flight."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_TYPE_GENERIC,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType CAT_MORPH = new Special(
			BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			PenetrationGirth.TWO_NARROW,
			0.8f,
			"feline",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("cat-like", "furry"),
			Util.newArrayListOfValues("cat-like", "furry"),
			"tip",
			"tips",
			Util.newArrayListOfValues("furry"),
			Util.newArrayListOfValues("furry"),
			"#IF(npc.getTailCount()==1)"
				+ " A furry, cat-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over it, and can twist it almost anywhere [npc.she] [npc.verb(please)]."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldCatMorph(cat-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] furry, cat-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over them, and can twist them almost anywhere [npc.she] [npc.verb(please)]."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldCatMorph(cat-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] cat tail, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] cat tails, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType CAT_MORPH_SHORT = new Special(
			BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			PenetrationGirth.THREE_AVERAGE,
			0.2f,
			"short feline",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("cat-like", "short", "furry"),
			Util.newArrayListOfValues("cat-like", "short", "furry"),
			"tip",
			"tips",
			Util.newArrayListOfValues("furry"),
			Util.newArrayListOfValues("furry"),
			"#IF(npc.getTailCount()==1)"
				+ " A short, furry, cat-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ "Although [npc.she] [npc.has] a decent amount of control over it, it's too short to really do anything with."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldCatMorph(short, cat-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] short, furry, cat-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " Although [npc.she] [npc.has] a decent amount of control over them, they're too short to really do anything with."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldCatMorph(short, cat-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)], short cat tail."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)], short cat tails."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType CAT_MORPH_TUFTED = new Special(
			BodyCoveringType.FELINE_FUR,
			Race.CAT_MORPH,
			PenetrationGirth.THREE_AVERAGE,
			0.4f,
			"tufted feline",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("cat-like", "tufted", "furry"),
			Util.newArrayListOfValues("cat-like", "tufted", "furry"),
			"tip",
			"tips",
			Util.newArrayListOfValues("tufted"),
			Util.newArrayListOfValues("tufted"),
			"#IF(npc.getTailCount()==1)"
				+ " A short, furry, cat-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " At the end of it there's a fluffy tuft of fur, and [npc.she] can control it well enough to grant [npc.herHim] significantly improved balance."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldCatMorph(tufted, cat-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] short, furry, cat-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " At the end of each one there's a fluffy tuft of fur, and [npc.she] can control them well enough to grant [npc.herHim] significantly improved balance."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldCatMorph(tufted, cat-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] cat tail, which has a tuft of fur on the end."
					+ " [npc.She] can control it well enough to grant [npc.herHim] significantly improved balance."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] cat tails, which each have a tuft of fur on the end."
					+ " [npc.She] can control them well enough to grant [npc.herHim] significantly improved balance."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType COW_MORPH = new Special(
			BodyCoveringType.BOVINE_FUR,
			Race.COW_MORPH,
			PenetrationGirth.TWO_NARROW,
			0.35f,
			"cow",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("cow-like"),
			Util.newArrayListOfValues("cow-like"),
			"end",
			"ends",
			Util.newArrayListOfValues("hair-tufted"),
			Util.newArrayListOfValues("hair-tufted"),
			"#IF(npc.getTailCount()==1)"
				+ " A cow-like tail sprouts from just above [npc.her] ass, rapidly growing in length until it hangs down about [npc.tailLength] behind [npc.herHim]."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over it is limited to swishing it from side to side."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldCowMorph(cow-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] cow-like tails sprout from just above [npc.her] ass, rapidly growing in length until they hang down about [npc.tailLength] behind [npc.herHim]."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over them is limited to swishing them from side to side."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldCowMorph(cow-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)], cow tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] cow tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over them."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType DOG_MORPH = new Special(
			BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			PenetrationGirth.THREE_AVERAGE,
			0.4f,
			"dog",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("dog-like"),
			Util.newArrayListOfValues("dog-like"),
			"tip",
			"tips",
			Util.newArrayListOfValues("furry"),
			Util.newArrayListOfValues("furry"),
			"#IF(npc.getTailCount()==1)"
				+ " A furry, dog-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] little control over it, and it wags with a mind of its own whenever [npc.she] [npc.verb(get)] excited."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldDogMorph(dog-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] furry, dog-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] little control over them, and they wags with a mind of their own whenever [npc.she] [npc.verb(get)] excited."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldDogMorph(dog-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] dog tail, which wags uncontrollably when [npc.she] [npc.verb(get)] excited."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] dog tails, which wag uncontrollably when [npc.she] [npc.verb(get)] excited."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType DOG_MORPH_STUBBY = new Special(
			BodyCoveringType.CANINE_FUR,
			Race.DOG_MORPH,
			PenetrationGirth.THREE_AVERAGE,
			0.1f,
			"stubby dog",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("stubby", "dog-like"),
			Util.newArrayListOfValues("stubby", "dog-like"),
			"tip",
			"tips",
			Util.newArrayListOfValues("stubby"),
			Util.newArrayListOfValues("stubby"),
			"#IF(npc.getTailCount()==1)"
				+ " A furry, dog-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] little control over it, and it wags with a mind of its own whenever [npc.she] [npc.verb(get)] excited."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldDogMorph(stubby, dog-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] furry, dog-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] little control over them, and they wags with a mind of their own whenever [npc.she] [npc.verb(get)] excited."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldDogMorph(stubby, dog-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF stubby, [npc.tailColour(true)] dog tail, which wags uncontrollably when [npc.she] [npc.verb(get)] excited."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF stubby, [npc.tailColour(true)] dog tails, which wag uncontrollably when [npc.she] [npc.verb(get)] excited."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType FOX_MORPH = new Special(
			BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			PenetrationGirth.FOUR_GIRTHY,
			0.6f,
			"fox",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("fox-like", "fluffy", "bushy"),
			Util.newArrayListOfValues("fox-like", "fluffy", "bushy"),
			"tip",
			"tips",
			Util.newArrayListOfValues("furry"),
			Util.newArrayListOfValues("furry"),
			"#IF(npc.getTailCount()==1)"
				+ " A bushy, fox-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over it, and [npc.is] able to wrap its fluffy length around [npc.her] lower body."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldFoxMorph(fox-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] bushy, fox-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over them, and [npc.is] able to wrap their fluffy lengths around [npc.her] lower body."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldFoxMorph(fox-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] fox tail, which [npc.she] can freely swish this way and that."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] fox tails, which [npc.she] can freely swish this way and that."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType FOX_MORPH_MAGIC = new Special(
			BodyCoveringType.FOX_FUR,
			Race.FOX_MORPH,
			PenetrationGirth.FOUR_GIRTHY,
			1f,
			"arcane fox",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("arcane", "fox-like", "fluffy"),
			Util.newArrayListOfValues("arcane", "fox-like", "fluffy"),
			"tip",
			"tips",
			Util.newArrayListOfValues("furry"),
			Util.newArrayListOfValues("furry"),
			"#IF(npc.getTailCount()==1)"
				+ " A bushy, fox-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over it, and [npc.is] able to wrap its fluffy length around [npc.her] lower body."
				+ " [npc.SheIsFull] also very much aware of the fact that it is granting [npc.herHim] [style.italicsArcane(arcane powers)], and that the more tails [npc.sheIsFull] able to earn, the more powerful [npc.she] will become!"
				+ "<br/>"
				+ "[npc.Name] now [npc.has] an [style.boldArcane(arcane)] [style.boldFoxMorph(fox-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] bushy, fox-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over them, and [npc.is] able to wrap their fluffy lengths around [npc.her] lower body."
				+ "#IF(npc.getTailCount()==9)"
					+ " [npc.SheIsFull] also very much aware of the fact that they are granting [npc.herHim] [style.italicsExcellent(immense)] [style.italicsArcane(arcane powers)]!"
				+ "#ELSE"
					+ " [npc.SheIsFull] also very much aware of the fact that they are granting [npc.herHim] [style.italicsArcane(arcane powers)], and that the more tails [npc.sheIsFull] able to earn, the more powerful [npc.she] will become!"
				+ "#ENDIF"
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldArcane(arcane)] [style.boldFoxMorph(fox-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] [style.boldArcane(arcane)] fox tail, which is granting [npc.herHim] [style.italicsArcane(arcane powers)]!"
				+ "#ELSE"
					+ "#IF(npc.getTailCount()==9)"
						+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] [style.boldArcane(arcane)] fox tails,"
							+ " which are granting [npc.herHim] [style.italicsExcellent(immense)] [style.italicsArcane(arcane powers)]!"
					+ "#ELSE"
						+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] [style.boldArcane(arcane)] fox tails, which are granting [npc.herHim] [style.italicsArcane(arcane powers)]!"
					+ "#ENDIF"
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType HARPY = new Special(
			BodyCoveringType.FEATHERS,
			Race.HARPY,
			PenetrationGirth.FOUR_GIRTHY,
			0.5f,
			"harpy plume",
			"plume of",
			"plumes of",
			"tail feathers",
			"tail feathers",
			Util.newArrayListOfValues("magnificent", "bird-like"),
			Util.newArrayListOfValues("magnificent", "bird-like"),
			"end",
			"ends",
			Util.newArrayListOfValues("feathered"),
			Util.newArrayListOfValues("feathered"),
			"#IF(npc.getTailCount()==1)"
				+ " A pretty plume of tail feathers sprouts from just above [npc.her] ass, with each feather quickly growing to be about [npc.tailLength] long."
				+ " [npc.She] [npc.verb(discover)] that [npc.she] can quickly raise and lower [npc.her] new bird-like tail, which helps [npc.herHim] to keep [npc.her] balance."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldHarpy(harpy's bird-like tail)], made out of [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] pretty plumes of tail feathers sprout from just above [npc.her] ass, with each feather quickly growing to be about [npc.tailLength] long."
				+ " [npc.She] [npc.verb(discover)] that [npc.she] can quickly raise and lower [npc.her] new bird-like tails, which helps [npc.herHim] to keep [npc.her] balance."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldHarpy(harpy's bird-like tails)], made out of [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF plume of beautiful, [npc.tailColour(true)] tail-feathers,"
						+ " which [npc.she] can rapidly move up and down to help [npc.herHim] keep [npc.her] balance and to control [npc.her] path when in flight."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF plumes of beautiful, [npc.tailColour(true)] tail-feathers,"
						+ " which [npc.she] can rapidly move up and down to help [npc.herHim] keep [npc.her] balance and to control [npc.her] path when in flight."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_FEATHER,
					BodyPartTag.TAIL_TAPERING_NONE,
					BodyPartTag.TAIL_NEVER_SUITABLE_FOR_PENETRATION), false) {
	};
	
	public static final AbstractTailType HORSE_MORPH = new Special(
			BodyCoveringType.HAIR_HORSE_HAIR,
			Race.HORSE_MORPH,
			PenetrationGirth.THREE_AVERAGE,
			0.3f,
			"horse",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("horse-like"),
			Util.newArrayListOfValues("horse-like"),
			"end",
			"ends",
			Util.newArrayListOfValues("loose"),
			Util.newArrayListOfValues("loose"),
			"#IF(npc.getTailCount()==1)"
				+ " A horse-like tail sprouts from just above [npc.her] ass, rapidly growing in length until it hangs down about [npc.tailLength] behind [npc.herHim]."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over it is limited to swishing it from side to side."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldHorseMorph(horse-like tail)], made out of [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] horse-like tails sprout from just above [npc.her] ass, rapidly growing in length until they hang down about [npc.tailLength] behind [npc.herHim]."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over them is limited to swishing them from side to side."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldHorseMorph(horse-like tails)], made out of [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)], horse tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] horse tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over them."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_TYPE_HAIR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType HORSE_MORPH_ZEBRA = new Special(
			BodyCoveringType.HAIR_HORSE_HAIR,
			Race.HORSE_MORPH,
			PenetrationGirth.TWO_NARROW,
			0.3f,
			"zebra",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("zebra-like"),
			Util.newArrayListOfValues("zebra-like"),
			"end",
			"ends",
			Util.newArrayListOfValues("hair-tipped"),
			Util.newArrayListOfValues("hair-tipped"),
			"#IF(npc.getTailCount()==1)"
				+ " A zebra-like tail sprouts from just above [npc.her] ass, rapidly growing in length until it hangs down about [npc.tailLength] behind [npc.herHim]."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over it is limited to swishing it from side to side."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldHorseMorph(zebra-like tail)], made out of [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] zebra-like tails sprout from just above [npc.her] ass, rapidly growing in length until they hang down about [npc.tailLength] behind [npc.herHim]."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over them is limited to swishing them from side to side."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldHorseMorph(zebra-like tails)], made out of [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)], zebra tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] zebra tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over them."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_TYPE_HAIR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType RAT_MORPH = new Special(
			BodyCoveringType.RAT_SKIN,
			Race.RAT_MORPH,
			PenetrationGirth.THREE_AVERAGE,
			0.75f,
			"rat",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("rat-like"),
			Util.newArrayListOfValues("rat-like"),
			"tip",
			"tips",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues(""),
			"#IF(npc.getTailCount()==1)"
				+ " A thick, rat-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over it, and can twist it almost anywhere [npc.she] [npc.verb(please)]."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldRatMorph(rat-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] thick, rat-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a decent amount of control over them, and can twist them almost anywhere [npc.she] [npc.verb(please)]."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldRatMorph(rat-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] rat tail, over which [npc.she] [npc.has] complete control, allowing [npc.herHim] to use it to grip and hold objects."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] rat tails, over which [npc.she] [npc.has] complete control, allowing [npc.herHim] to use them to grip and hold objects."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_PREHENSILE,
					BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION,
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_SKIN,
					BodyPartTag.TAIL_TAPERING_LINEAR), false) {
	};
	
	public static final AbstractTailType RABBIT_MORPH = new Special(
			BodyCoveringType.RABBIT_FUR,
			Race.RABBIT_MORPH,
			PenetrationGirth.FIVE_THICK,
			0.075f,
			"rabbit",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("rabbit-like", "fluffy"),
			Util.newArrayListOfValues("rabbit-like", "fluffy"),
			"bush",
			"bushes",
			Util.newArrayListOfValues("fluffy"),
			Util.newArrayListOfValues("fluffy"),
			"#IF(npc.getTailCount()==1)"
				+ " A furry, round, rabbit-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] no control over it whatsoever, what with it being no more than a ball of downy fluff."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldRabbitMorph(rabbit-like tail)], made out of [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] furry, round, rabbit-like tails sprout from just above [npc.her] ass, rapidly growing in size until each one is about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] no control over them whatsoever, what with them being no more than balls of downy fluff."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldRabbitMorph(rabbit-like tails)], made out of [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] rabbit tail, which really is no more than a large ball of downy fluff."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] rabbit tails, which really are no more than large balls of downy fluff."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE,
					BodyPartTag.TAIL_NEVER_SUITABLE_FOR_PENETRATION), false) {
	};
	
	public static final AbstractTailType REINDEER_MORPH = new Special(
			BodyCoveringType.REINDEER_FUR,
			Race.REINDEER_MORPH,
			PenetrationGirth.FOUR_GIRTHY,
			0.05f,
			"reindeer",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("reindeer-like"),
			Util.newArrayListOfValues("reindeer-like"),
			"tip",
			"tips",
			Util.newArrayListOfValues("furry"),
			Util.newArrayListOfValues("furry"),
			"#IF(npc.getTailCount()==1)"
				+ " A reindeer-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over it is limited to simply twitching it up and down."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldReindeerMorph(reindeer-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] reindeer-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(discover)] that [npc.her] control over them is limited to simply twitching them up and down."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldReindeerMorph(reindeer-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] reindeer tail, which [npc.she] can twitch up and down."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] reindeer tails, which [npc.she] can twitch up and down."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType SQUIRREL_MORPH = new Special(
			BodyCoveringType.SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			PenetrationGirth.FIVE_THICK,
			1f,
			"squirrel",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("squirrel-like", "fluffy", "bushy"),
			Util.newArrayListOfValues("squirrel-like", "fluffy", "bushy"),
			"tip",
			"tips",
			Util.newArrayListOfValues("furry"),
			Util.newArrayListOfValues("furry"),
			"#IF(npc.getTailCount()==1)"
				+ " A furry, squirrel-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's an impressive [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a reasonable amount of control over it, and can use it to help balance [npc.herHim] out while moving quickly."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldSquirrelMorph(squirrel-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] furry, squirrel-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each an impressive [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] a reasonable amount of control over them, and can use them to help balance [npc.herHim] out while moving quickly."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldSquirrelMorph(squirrel-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] squirrel tail, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] squirrel tails, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	public static final AbstractTailType WOLF_MORPH = new Special(
			BodyCoveringType.LYCAN_FUR,
			Race.WOLF_MORPH,
			PenetrationGirth.FOUR_GIRTHY,
			0.4f,
			"wolf",
			"",
			"",
			"tail",
			"tails",
			Util.newArrayListOfValues("wolf-like", "fluffy"),
			Util.newArrayListOfValues("wolf-like", "fluffy"),
			"tip",
			"tips",
			Util.newArrayListOfValues("furry"),
			Util.newArrayListOfValues("furry"),
			"#IF(npc.getTailCount()==1)"
				+ "  A furry, wolf-like tail sprouts from just above [npc.her] ass, rapidly growing in size until it's about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] limited control over it, and it takes a lot of effort to stop it from betraying [npc.her] emotions."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] a [style.boldWolfMorph(wolf-like tail)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ELSE"
				+ " [npc.TailCount] furry, wolf-like tails sprout from just above [npc.her] ass, rapidly growing in size until they're each about [npc.tailLength] long."
				+ " [npc.She] quickly [npc.verb(realise)] that [npc.she] [npc.has] limited control over them, and it takes a lot of effort to stop them from betraying [npc.her] emotions."
				+ "<br/>"
				+ "[npc.Name] now [npc.has] [npc.tailCount] [style.boldWolfMorph(wolf-like tails)], [npc.materialDescriptor] [npc.tailFullDescription(true)]."
			+ "#ENDIF",
			"Growing out from just above [npc.her] ass, [npc.sheHasFull]"
				+ "#IF(npc.getTailCount()==1)"
					+ " a #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] wolf tail, which swishes from side to side when [npc.she] [npc.verb(get)] excited."
				+ "#ELSE"
					+ " [npc.tailCount] #IF(npc.isTailFeral()) [style.colourFeral(feral)],#ENDIF [npc.tailColour(true)] wolf tails, which swish from side to side when [npc.she] [npc.verb(get)] excited."
				+ "#ENDIF",
			Util.newArrayListOfValues(
					BodyPartTag.TAIL_SLEEP_HUGGING,
					BodyPartTag.TAIL_TYPE_FUR,
					BodyPartTag.TAIL_TAPERING_NONE), false) {
	};
	
	class Special extends AbstractTailType {

		private String id;

		public Special(AbstractBodyCoveringType coveringType, AbstractRace race, PenetrationGirth defaultGirth, float defaultLengthAsPercentageOfHeight, String transformationName, String determiner, String determinerPlural, String name, String namePlural, List<String> descriptorsMasculine, List<String> descriptorsFeminine, String tipName, String tipNamePlural, List<String> tipDescriptorsMasculine, List<String> tipDescriptorsFeminine, String tailTransformationDescription, String tailBodyDescription, List<BodyPartTag> tags, boolean spinneret) {
			super(coveringType, race, defaultGirth, defaultLengthAsPercentageOfHeight, transformationName, determiner, determinerPlural, name, namePlural, descriptorsMasculine, descriptorsFeminine, tipName, tipNamePlural, tipDescriptorsMasculine, tipDescriptorsFeminine, tailTransformationDescription, tailBodyDescription, tags, spinneret);
		}

		@Override
		public String getId() {
			return id != null ? id : (id = Arrays.stream(TailType.class.getFields())
				.filter(f->{try{return f.get(null).equals(this);}catch(ReflectiveOperationException x){return false;}})
				.findAny().orElseThrow().getName());
		}
	}

	TypeTable<AbstractTailType> table = new TypeTable<>(
		TailType::sanitize,
		TailType.class,
		AbstractTailType.class,
		"tail",
		(f,n,a,m)->new AbstractTailType(f,a,m) {
			@Override
			public String getId() {
				return n;
			}
		});

	public static AbstractTailType getTailTypeFromId(String id) {
		return table.of(id);
	}

	private static String sanitize(String id) {
		if(id.equals("IMP")) {
			return "DEMON_COMMON";
		}
		if(id.equals("LYCAN")) {
			return "WOLF_MORPH";
		}
		return id;
	}

	public static String getIdFromTailType(AbstractTailType tailType) {
		return tailType.getId();
	}

	public static List<AbstractTailType> getAllTailTypes() {
		return table.listByRace();
	}
	
	public static List<AbstractTailType> getTailTypes(AbstractRace r) {
		return table.of(r).orElse(List.of());
	}
	
	public static List<AbstractTailType> getTailTypesSuitableForTransformation(List<AbstractTailType> options) {
		if (!options.contains(TailType.NONE)) {
			return options;
		}
		
		List<AbstractTailType> duplicatedOptions = new ArrayList<>(options);
		duplicatedOptions.remove(TailType.NONE);
		return duplicatedOptions;
	}
}