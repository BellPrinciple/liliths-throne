package com.lilithsthrone.game.character.body;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.valueEnums.Capacity;
import com.lilithsthrone.game.character.body.valueEnums.OrificeDepth;
import com.lilithsthrone.game.character.body.valueEnums.OrificeElasticity;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.body.valueEnums.OrificePlasticity;
import com.lilithsthrone.game.character.body.valueEnums.Wetness;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Instances are associated with a character.
 * @since 0.1.83
 * @version 0.3.9
 * @author Innoxia
 */
public final class Orifice {

	public enum Type {
		ANUS,
		CROTCH_NIPPLES,
		MOUTH,
		NIPPLES,
		PENIS_URETHRA,
		SPINNERET,
		VAGINA,
		VAGINA_URETHRA,
	}

	private final Type type;
	int wetness;
	float capacity;
	float stretchedCapacity;
	int depth;
	int elasticity;
	int plasticity;
	boolean virgin;
	final Set<OrificeModifier> orificeModifiers;

	public Orifice(Type type, int wetness, float capacity, int depth, int elasticity, int plasticity, boolean virgin, Collection<OrificeModifier> orificeModifiers) {
		this.type = type;
		this.wetness = wetness;
		this.capacity = capacity;
		stretchedCapacity = capacity;
		this.depth = depth;
		this.elasticity = elasticity;
		this.plasticity = plasticity;
		this.virgin = virgin;
		this.orificeModifiers = new HashSet<>(orificeModifiers);
	}

	public Orifice(Orifice orificeToCopy) {
		type = orificeToCopy.type;
		wetness = orificeToCopy.wetness;
		capacity = orificeToCopy.capacity;
		stretchedCapacity = orificeToCopy.stretchedCapacity;
		depth = orificeToCopy.depth;
		elasticity = orificeToCopy.elasticity;
		plasticity = orificeToCopy.plasticity;
		virgin = orificeToCopy.virgin;
		orificeModifiers = new HashSet<>(orificeToCopy.orificeModifiers);
	}

	/**
	 * Do not use this for PENIS_URETHRA.  Urethra wetness is determined by cum production.
	 */
	public Wetness getWetness(GameCharacter owner) {
		return switch (type) {
			case CROTCH_NIPPLES, NIPPLES -> owner.getBreastMilkStorage().getAssociatedWetness();
			case PENIS_URETHRA, VAGINA_URETHRA -> Wetness.valueOf(wetness);
			default -> owner!=null && owner.getBodyMaterial().isOrificesAlwaysMaximumWetness()
					? Wetness.SEVEN_DROOLING
					: Wetness.valueOf(wetness);
		};
	}

	/**
	 * Do not use this for CROTCH_NIPPLES or NIPPLES.  Nipple wetness is determined by breast lactation.
	 * Do not use this for PENIS_URETHRA.  Urethra wetness is determined by cum production.
	 */
	public String setWetness(GameCharacter owner, int wetness) {
		String missingBodypart = switch(type) {
			case ANUS, MOUTH -> "";
			case CROTCH_NIPPLES, NIPPLES -> throw new IllegalAccessError("Nipple wetness was attempted to be set manually!");
			case PENIS_URETHRA -> owner == null || owner.hasPenis() ? "" : "penis";
			case SPINNERET -> owner == null || owner.hasSpinneret() ? "" : "spinneret";
			case VAGINA, VAGINA_URETHRA -> owner == null || owner.hasVagina() ? "" : "vagina";
		};
		if(!missingBodypart.isEmpty())
			return UtilText.parse(owner,
					String.format("<p style='text-align:center;'>[style.colourDisabled([npc.Name] [npc.verb(lack)] a %s, so nothing happens...)]</p>",
							missingBodypart));
		if(type != Type.VAGINA_URETHRA && type != Type.PENIS_URETHRA && owner != null && owner.getBodyMaterial().isOrificesAlwaysMaximumWetness())
			return UtilText.parse(owner,
					String.format("<p style='text-align:center;'>[style.colourSex(Due to being made out of %s, the wetness of [npc.namePos] %s %s can't be changed...)]</p>",
							owner.getBodyMaterial().getName(),
							Wetness.SEVEN_DROOLING.getDescriptor(),
							getDecoratedName()));
		int oldWetness = this.wetness;
		this.wetness = Math.max(0, Math.min(wetness, Wetness.SEVEN_DROOLING.getValue()));
		if(owner==null)
			return "";
		int wetnessChange = this.wetness - oldWetness;
		if(wetnessChange == 0) {
			String unchangedProperty = switch(type) {
				case ANUS -> "[npc.asshole]";
				case CROTCH_NIPPLES, NIPPLES -> "";
				case MOUTH -> "throat";
				case PENIS_URETHRA -> "precum production";
				case SPINNERET -> "spinneret";
				case VAGINA -> "[npc.pussy]";
				case VAGINA_URETHRA -> "urethral wetness";
			};
			return UtilText.parse(owner,
					String.format("<p style='text-align:center;'>[style.colourDisabled([npc.NamePos] %s doesn't change...)]</p>",
							unchangedProperty));
		}
		String changeText = switch(type) {
			case MOUTH -> wetnessChange > 0
					? "[npc.Name] suddenly [npc.verb(start)] salivating, and although [npc.she] quickly [npc.verb(swallow)] it all down, [npc.she] [npc.verb(feel)] that [npc.her] mouth and throat have permanently [style.boldGrow(got wetter)]."
					: "[npc.Name] lets out an agitated sigh as [npc.she] [npc.verb(feel)] [npc.her] mouth and throat [style.boldShrink(getting drier)].";
			case CROTCH_NIPPLES, NIPPLES -> "";
			case ANUS -> wetnessChange > 0
					? "[npc.NamePos] [npc.eyes] widen as [npc.she] [npc.verb(feel)] moisture beading around [npc.her] asshole, and [npc.she] can't help but let out a deep [npc.moan] as [npc.she] [npc.verb(realise)] that [npc.her] rear entrance is lubricating itself and [style.boldGrow(getting wetter)]."
					: "[npc.Name] [npc.verb(shift)] about uncomfortably and [npc.verb(let)] out a frustrated groan as [npc.she] [npc.verb(feel)] [npc.her] rear entrance [style.boldShrink(drying up)].";
			case PENIS_URETHRA -> wetnessChange > 0
					? "[npc.NamePos] [npc.eyes] widen as [npc.she] [npc.verb(feel)] [npc.her] [npc.cock+] suddenly grow hard, and [npc.she] [npc.verb(let)] out [npc.a_moan+] as a slick stream of precum oozes out of the tip as its production [style.boldGrow(increases)]."
					: "[npc.Name] [npc.verb(shift)] about uncomfortably and [npc.verb(let)] out a frustrated groan as [npc.she] [npc.verb(feel)] [npc.her] precum production [style.boldShrink(decrease)].";
			case SPINNERET -> wetnessChange > 0
					? "[npc.NamePos] [npc.eyes] widen as [npc.she] [npc.verb(feel)] moisture beading around [npc.her] spinneret, and [npc.she] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(realise)] that it's lubricating itself and [style.boldGrow(getting wetter)]."
					: "[npc.Name] [npc.verb(shift)] about uncomfortably and [npc.verb(let)] out a frustrated groan as [npc.she] [npc.verb(feel)] [npc.her] spinneret [style.boldShrink(getting drier)]";
			case VAGINA -> wetnessChange > 0
					? "[npc.NamePos] [npc.eyes] widen as [npc.she] [npc.verb(feel)] moisture beading around [npc.her] [npc.pussy], and [npc.she] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(realise)] that it's lubricating itself and [style.boldGrow(getting wetter)]."
					: "[npc.Name] [npc.verb(shift)] about uncomfortably and [npc.verb(let)] out a frustrated groan as [npc.she] [npc.verb(feel)] [npc.her] [npc.pussy] [style.boldShrink(getting drier)].";
			case VAGINA_URETHRA -> wetnessChange > 0
					? "[npc.NamePos] [npc.eyes] widen as [npc.she] [npc.verb(feel)] a shudder of excitement run through [npc.her] [npc.pussy+], and [npc.she] realises that [npc.her] urethra has gotten [style.boldGrow(wetter)]."
					: "[npc.Name] [npc.verb(shift)] about uncomfortably and [npc.verb(let)] out a frustrated groan as [npc.she] [npc.verb(feel)] [npc.her] urethra getting [style.boldShrink(drier)].";
		};
		String wetnessDescriptor = getWetness(owner).getDescriptor();
		String determiner = UtilText.generateSingularDeterminer(wetnessDescriptor);
		String p2 = switch(type) {
			case MOUTH, PENIS_URETHRA, VAGINA_URETHRA -> "[npc.Name] now [npc.has] [style.boldSex(%s %s %s)]!";
			default -> "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(%s %s %s)]!";
		};
		return "<p>" + UtilText.parse(owner, changeText) + "<br/>" + UtilText.parse(owner, String.format(p2, determiner, wetnessDescriptor, getName())) + "</p>";
	}

	public Capacity getCapacity() {
		return Capacity.getCapacityFromValue(capacity);
	}

	public float getRawCapacityValue() {
		return capacity;
	}

	public String setCapacity(GameCharacter owner, float capacity, boolean setStretchedValueToNewValue) {
		if(type == Type.PENIS_URETHRA && owner!=null && !owner.hasPenis())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] [npc.verb(lack)] a penis, so nothing happens...)]</p>");
		if(type == Type.SPINNERET && owner!=null && !owner.hasSpinneret())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] [npc.verb(lack)] a spinneret, so nothing happens...)]</p>");
		if(type == Type.VAGINA && owner!=null && !owner.hasVagina())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] [npc.verb(lack)] a vagina, so nothing happens...)]</p>");
		if(type == Type.VAGINA_URETHRA && owner != null && !owner.hasVagina())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] [npc.verb(lack)] a vagina, so nothing happens...)]</p>");
		float oldCapacity = this.capacity;
		this.capacity = Math.max(0, Math.min(capacity, Capacity.SEVEN_GAPING.getMaximumValue(false)));
		if(setStretchedValueToNewValue)
			this.stretchedCapacity = this.capacity;
		if(owner==null)
			return "";
		float capacityChange = this.capacity - oldCapacity;
		if(capacityChange == 0)
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The capacity of [npc.namePos] " + getDecoratedName() + " doesn't change...)]</p>");
		String capacityDescriptor = getCapacity().getDescriptor();
		String nipplesString = type == Type.CROTCH_NIPPLES ? "[npc.crotchNipples]" : "[npc.nipples]";
		String breastsString = type == Type.CROTCH_NIPPLES ? "[npc.crotchBoobs]" : "[npc.breasts]";
		if((type == Type.CROTCH_NIPPLES || type == Type.NIPPLES) && capacityChange > 0 && oldCapacity == 0) {
			// Getting fuckable nipples:
			return UtilText.parse(owner,
					"<p>"
						+ "[npc.Name] [npc.verb(squirm)] about uncomfortably as [npc.her] "+nipplesString+" grow unusually hard and sensitive."
						+ " A strange pressure starts to build up within [npc.her] torso, and [npc.she] [npc.verb(let)] out a deep sigh as a drastic transformation takes place within [npc.her] "+breastsString+"."
						+ " Quickly overwhelmed by the growing intensity of the pressure building up within [npc.her] "+breastsString+", [npc.namePos] sigh turns into [npc.a_moan+],"
							+ " which bursts out of [npc.her] mouth as [npc.her] "+nipplesString+" suddenly [style.boldGrow(spread open)], revealing deep, fuckable passages that have formed behind them.<br/>"
						+ "[npc.Name] now [npc.has] [style.boldSex(" + capacityDescriptor + ", fuckable "+nipplesString+")]!"
					+ "</p>");
		}
		if((type == Type.CROTCH_NIPPLES || type == Type.NIPPLES) && capacityChange < 0 && capacity == 0) {
			// Losing fuckable nipples:
			return UtilText.parse(owner,
					"<p>"
						+ "[npc.Name] [npc.verb(squirm)] about uncomfortably as [npc.her] "+nipplesString+" grow unusually hard and sensitive."
						+ " An intense pressure starts to build up within [npc.her] torso, and [npc.she] [npc.verb(let)] out a deep sigh as [npc.her] "+nipplesString+" suddenly [style.boldShrink(clench shut)], removing the ability for them to be fucked.<br/>"
						+ "[npc.Name] now [npc.has] [style.boldSex(" + capacityDescriptor + ", non-fuckable "+nipplesString+")]!"
					+ "</p>");
		}
		String increaseText = switch(type) {
			case ANUS -> "An involuntary, shocked gasp escapes from [npc.namePos] mouth as [npc.she] [npc.verb(feel)] [npc.her] asshole dilate and stretch.";
			case CROTCH_NIPPLES, NIPPLES -> "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a familiar pressure building up behind [npc.her] fuckable "+nipplesString+", before they suddenly [style.boldGrow(grow)] both wider and deeper.";
			case MOUTH -> "An involuntary, shocked gasp escapes from [npc.namePos] mouth as [npc.she] [npc.verb(feel)] [npc.her] throat relaxing and stretching out.";
			case PENIS_URETHRA -> "An involuntary, shocked gasp escapes from [npc.namePos] mouth as [npc.she] [npc.verb(feel)] [npc.her] penis's urethra relaxing and stretching out.";
			case SPINNERET -> "An involuntary, shocked gasp escapes from [npc.namePos] mouth as [npc.she] [npc.verb(feel)] [npc.her] spinneret uncontrollably dilate and stretch.";
			case VAGINA -> "An involuntary, shocked gasp escapes from [npc.namePos] mouth as [npc.she] [npc.verb(feel)] [npc.her] cunt uncontrollably dilate and stretch.";
			case VAGINA_URETHRA -> "An involuntary, shocked gasp escapes from [npc.namePos] mouth as [npc.she] [npc.verb(feel)] [npc.her] pussy's urethra relaxing and stretching out.";
		};
		String decreaseText = switch(type) {
			case ANUS -> "[npc.Name] [npc.verb(let)] out a cry as [npc.she] [npc.verb(feel)] [npc.her] asshole uncontrollably tighten and clench.";
			case CROTCH_NIPPLES, NIPPLES -> "[npc.Name] [npc.verb(let)] out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a familiar pressure building up behind [npc.her] fuckable "+nipplesString+", before they suddenly [style.boldShrink(shrink)] and become tighter.";
			case MOUTH -> "[npc.Name] [npc.verb(let)] out a cry as [npc.she] [npc.verb(feel)] [npc.her] throat uncontrollably tightening and clenching.";
			case PENIS_URETHRA -> "[npc.Name] [npc.verb(let)] out a cry as [npc.she] [npc.verb(feel)] [npc.her] penis's urethra uncontrollably tightening and clenching.";
			case SPINNERET -> "[npc.Name] [npc.verb(let)] out a cry as [npc.she] [npc.verb(feel)] [npc.her] spinneret uncontrollably tighten and clench.";
			case VAGINA -> "[npc.Name] [npc.verb(let)] out a cry as [npc.she] [npc.verb(feel)] [npc.her] cunt uncontrollably tighten and clench.";
			case VAGINA_URETHRA -> "[npc.Name] [npc.verb(let)] out a cry as [npc.she] [npc.verb(feel)] [npc.her] pussy's urethra uncontrollably tightening and clenching.";
		};
		String p1 = capacityChange > 0 ? increaseText : decreaseText;
		String name = getName();
		String style = capacityChange > 0 ? "style.boldGrow" : "style.boldShrink";
		String changed = capacityChange > 0 ? "increased" : "decreased";
		String p2 = switch(type) {
			case CROTCH_NIPPLES, NIPPLES -> "";
			case MOUTH, PENIS_URETHRA, VAGINA_URETHRA -> " Within moments, the alarming feeling has passed, and [npc.she] very quickly [npc.verb(realise)] that [npc.her] " + name + "'s internal [" + style + "(capacity has " + changed + ")].";
			default -> " Within moments, the feeling has passed, and [npc.she] very quickly [npc.verb(realise)] that [npc.her] " + name + "'s internal [" + style + "(capacity has " + changed + ")].";
		};
		String determiner = UtilText.generateSingularDeterminer(capacityDescriptor);
		String p3 = "[npc.Name] now [npc.has] [style.boldSex(" + determiner + " " + capacityDescriptor + " " + name + ")]!";
		return UtilText.parse(owner, "<p>" + p1 + p2 + "<br>" + p3 + "</p>");
	}

	public float getStretchedCapacity() {
		return stretchedCapacity;
	}

	public boolean setStretchedCapacity(float stretchedCapacity) {
		float oldStretchedCapacity = this.stretchedCapacity;
		this.stretchedCapacity = Math.max(0, Math.min(stretchedCapacity, Capacity.SEVEN_GAPING.getMaximumValue(false)));
		return oldStretchedCapacity != this.stretchedCapacity;
	}

	/**
	 * @return The minimum OrificeDepth which this orifice needs in order to comfortably accommodate the provided insertionSize.
	 */
	public OrificeDepth getMinimumDepthForSizeComfortable(GameCharacter owner, int insertionSize) {
		OrificeDepth depth = OrificeDepth.ONE_SHALLOW;
		while(getMaximumPenetrationDepthComfortable(owner, depth)<insertionSize) {
			if(depth == OrificeDepth.SEVEN_FATHOMLESS) {
				return depth;
			}
			depth = OrificeDepth.getDepthFromInt(depth.getValue()+1);
		}
		return depth;
	}

	/**
	 * @return The minimum OrificeDepth which this orifice needs in order to uncomfortably accommodate the provided insertionSize.
	 */
	public OrificeDepth getMinimumDepthForSizeUncomfortable(GameCharacter owner, int insertionSize) {
		OrificeDepth depth = OrificeDepth.ONE_SHALLOW;
		while(getMaximumPenetrationDepthUncomfortable(owner, depth)<insertionSize) {
			if(depth == OrificeDepth.SEVEN_FATHOMLESS) {
				return depth;
			}
			depth = OrificeDepth.getDepthFromInt(depth.getValue()+1);
		}
		return depth;
	}

	/**
	 * @return The maximum depth at which penetration size can be considered comfortable. (Uses the getDepth(owner) depth for calculation.)
	 */
	public int getMaximumPenetrationDepthComfortable(GameCharacter owner) {
		return getMaximumPenetrationDepthComfortable(owner, getDepth(owner));
	}

	/**
	 * @return The maximum depth at which penetration size can be considered comfortable when this orifice has the provided depth.<br/>
	 * <b>You should probably be using getMaximumPenetrationDepthComfortable(GameCharacter owner)</b>
	 */
	public int getMaximumPenetrationDepthComfortable(GameCharacter owner, OrificeDepth depth) {
		float multiplier = switch(type) {
			case ANUS -> 0.12f;
			case CROTCH_NIPPLES, NIPPLES -> 0.5f;
			// 0.08 might be a little more realistic, but give it a little extra so that it's not annoying for people with large cocks
			case MOUTH, SPINNERET, VAGINA -> 0.1f;
			case PENIS_URETHRA -> 1.5f;
			case VAGINA_URETHRA -> 0.05f;
		};
		int max = switch(type) {
			case ANUS, MOUTH, SPINNERET, VAGINA, VAGINA_URETHRA -> owner.getHeightValue();
			case CROTCH_NIPPLES -> owner.getBreastCrotchSize().getMeasurement();
			case NIPPLES -> owner.getBreastSize().getMeasurement();
			case PENIS_URETHRA -> owner.getPenisRawSizeValue();
		};
		return (int) (max * multiplier * depth.getDepthPercentage());
	}

	/**
	 * @return The maximum depth at which penetration size can be accommodated. (Uses the getDepth(owner) depth for calculation.)
	 */
	public int getMaximumPenetrationDepthUncomfortable(GameCharacter owner) {
		return getMaximumPenetrationDepthUncomfortable(owner, getDepth(owner));
	}
	
	/**
	 * @return The maximum depth at which penetration size can be accommodated when this orifice has the provided depth.<br/>
	 * <b>You should probably be using getMaximumPenetrationDepthUncomfortable(GameCharacter owner)</b>
	 */
	public int getMaximumPenetrationDepthUncomfortable(GameCharacter owner, OrificeDepth depth) {
		boolean useElasticity = Main.game.isElasticityAffectDepthEnabled()
				&& OrificeElasticity.getElasticityFromInt(elasticity).isExtendingUncomfortableDepth();
		int comfort = getMaximumPenetrationDepthComfortable(owner, depth);
		float multiplier = switch(type) {
			case ANUS, CROTCH_NIPPLES, MOUTH, NIPPLES -> useElasticity ? (float) elasticity / 1.5f : 2f;
			case PENIS_URETHRA, SPINNERET, VAGINA, VAGINA_URETHRA -> useElasticity ? (float) elasticity / 1.8f : 1.5f;
		};
		return (int) (comfort * multiplier);
	}

	public OrificeDepth getDepth(GameCharacter owner) {
		if(owner!=null && !owner.getBodyMaterial().isOrificesLimitedDepth())
			return OrificeDepth.SEVEN_FATHOMLESS;
		return OrificeDepth.getDepthFromInt(depth);
	}

	public String setDepth(GameCharacter owner, int depth) {
		String missingPart = switch(type) {
			case PENIS_URETHRA -> owner == null || owner.hasPenis() ? "" : "penis";
			case SPINNERET -> owner == null || owner.hasSpinneret() ? "" : "spinneret";
			case VAGINA, VAGINA_URETHRA -> owner == null || owner.hasVagina() ? "" : "vagina";
			default -> "";
		};
		if(!missingPart.isEmpty())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] [npc.verb(lack)] a " + missingPart + ", so nothing happens...)]</p>");
		if(owner!=null && !owner.getBodyMaterial().isOrificesLimitedDepth())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourSex(Due to being made out of " + owner.getBodyMaterial().getName() + ", the depth of [npc.namePos] " + OrificeDepth.SEVEN_FATHOMLESS.getDescriptor() + " " + getDecoratedName() + " can't be changed...)]</p>");
		int oldDepth = this.depth;
		this.depth = Math.max(0, Math.min(depth, OrificeDepth.SEVEN_FATHOMLESS.getValue()));
		if(owner==null)
			return "";
		int depthChange = this.depth - oldDepth;
		if(depthChange == 0)
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The depth of [npc.namePos] " + getDecoratedName() + " doesn't change...)]</p>");
		String name = getName();
		String deepening = switch(type) {
			case ANUS -> "pressure pulsating up deep into [npc.her] ass";
			case CROTCH_NIPPLES -> "pressure pulsating deep within [npc.her] [npc.crotchBoobs]";
			case MOUTH -> "pressure pulsating from the back of [npc.her] throat deep down into [npc.her] torso";
			case NIPPLES -> "pressure pulsating deep within [npc.her] [npc.breasts]";
			case PENIS_URETHRA -> "pressure pulsating from the base of [npc.her] cock up into [npc.her] groin";
			case SPINNERET -> "pressure pulsating up from [npc.her] spinneret deep into [npc.her] lower abdomen";
			case VAGINA -> "pressure pulsating up from [npc.her] pussy deep into [npc.her] lower abdomen";
			case VAGINA_URETHRA -> "pressure pulsating from [npc.her] pussy up into [npc.her] lower abdomen";
		};
		String shallowing = switch(type) {
			case ANUS -> "tightening sensation moving its way down from the depths of [npc.her] ass";
			case CROTCH_NIPPLES -> "tightening sensation deep within [npc.her] [npc.crotchBoobs]";
			case MOUTH -> "tightening sensation moving its way up from [npc.her] torso into the back of [npc.her] throat";
			case NIPPLES -> "tightening sensation deep within [npc.her] [npc.breasts]";
			case PENIS_URETHRA -> "tightening sensation moving its way down from [npc.her] lower abdomen into the base of [npc.her] cock";
			case SPINNERET -> "tightening sensation moving its way down [npc.her] lower abdomen into [npc.her] spinneret";
			case VAGINA -> "tightening sensation moving its way down [npc.her] lower abdomen into [npc.her] pussy";
			case VAGINA_URETHRA -> "tightening sensation moving its way down from [npc.her] lower abdomen into [npc.her] pussy";
		};
		String sensation = depthChange > 0 ? deepening : shallowing;
		String feeling = depthChange > 0 ? "pressure" : "feeling";
		String style = depthChange > 0 ? "style.boldGrow" : "style.boldShrink";
		String changed = depthChange > 0 ? "deepened" : "become shallower";
		String descriptor = getDepth(owner).getDescriptor();
		String determiner = UtilText.generateSingularDeterminer(descriptor);
		String part = switch(type) {
			case ANUS, MOUTH, SPINNERET -> name;
			case CROTCH_NIPPLES, NIPPLES -> getDecoratedName();
			case PENIS_URETHRA -> "penis's urethra";
			case VAGINA -> "cunt";
			case VAGINA_URETHRA -> "pussy's urethra";
		};
		String has = switch (type) {
			case ANUS, MOUTH, PENIS_URETHRA, SPINNERET, VAGINA, VAGINA_URETHRA -> "has";
			case CROTCH_NIPPLES, NIPPLES -> "have";
		};
		String toBeParsed = """
				<p>
					[npc.Name] can't help but let out a surprised gasp as [npc.she] [npc.verb(feel)] an alarming %s.
					Before [npc.her] gasp can turn into a distressed cry, the %s suddenly fades away, leaving [npc.herHim] instinctively knowing that [npc.her] %s [%s(%s %s)].
					<br>
					[npc.Name] now [npc.has] [style.boldSex(%s %s %s)]!
				</p>
				""";
		return UtilText.parse(owner, String.format(toBeParsed, sensation, feeling, part, style, has, changed, determiner, descriptor, name));
	}

	public OrificeElasticity getElasticity() {
		return OrificeElasticity.getElasticityFromInt(elasticity);
	}

	public String setElasticity(GameCharacter owner, int elasticity) {
		String missingPart = switch(type) {
			case PENIS_URETHRA -> owner == null || owner.hasPenis() ? "" : "penis";
			case SPINNERET -> owner == null || owner.hasSpinneret() ? "" : "spinneret";
			case VAGINA, VAGINA_URETHRA -> owner == null || owner.hasVagina() ? "" : "vagina";
			default -> "";
		};
		if(!missingPart.isEmpty())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] [npc.verb(lack)] a " + missingPart + ", so nothing happens...)]</p>");
		int oldElasticity = this.elasticity;
		this.elasticity = Math.max(0, Math.min(elasticity, OrificeElasticity.SEVEN_ELASTIC.getValue()));
		if(owner==null)
			return "";
		int elasticityChange = this.elasticity - oldElasticity;
		if(elasticityChange == 0)
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The elasticity of [npc.namePos] " + getDecoratedName() + " doesn't change...)]</p>");
		String name = getName();
		String part = switch(type) {
			case CROTCH_NIPPLES -> "[npc.crotchBoobs]";
			case NIPPLES -> "[npc.breasts]";
			case PENIS_URETHRA -> "cock";
			case VAGINA_URETHRA -> "pussy";
			default -> name;
		};
		String altName = type == Type.VAGINA ? "cunt" : name;
		String slackening = elasticityChange > 0 ? "slackening" : "clenching";
		String style = elasticityChange > 0 ? "style.boldGrow" : "style.boldShrink";
		String descriptor = getElasticity().getDescriptor();
		String determiner = UtilText.generateSingularDeterminer(descriptor);
		String possessive = switch(type) {
			case CROTCH_NIPPLES, NIPPLES -> "";
			default -> "'s";
		};
		String hasChanged = switch(type) {
			case CROTCH_NIPPLES, NIPPLES -> elasticityChange > 0 ? "have gained some elasticity" : "have lost some elasticity";
			default -> elasticityChange > 0 ? "elasticity has increased" : "elasticity has decreased";
		};
		String toBeParsed = """
				<p>
					[npc.Name] can't help but let out a surprised gasp as [npc.she] [npc.verb(feel)] a strange %s sensation pulsating deep within [npc.her] %s.
					Just as quickly as it started, the feeling passes, and [npc.she] very quickly [npc.verb(realise)] that [npc.her] %s%s [%s(%s)].
					<br>
					[npc.Name] now [npc.has] [style.boldSex(%s %s %s)]!
				""";
		return UtilText.parse(owner, String.format(toBeParsed, slackening, part, altName, possessive, style, hasChanged, determiner, descriptor, name));
	}
	
	public OrificePlasticity getPlasticity() {
		return OrificePlasticity.getElasticityFromInt(plasticity);
	}

	public String setPlasticity(GameCharacter owner, int plasticity) {
		String missingPart = switch(type) {
			case PENIS_URETHRA -> owner == null || owner.hasPenis() ? "" : "penis";
			case SPINNERET -> owner == null || owner.hasSpinneret() ? "" : "spinneret";
			case VAGINA, VAGINA_URETHRA -> owner == null || owner.hasVagina() ? "" : "vagina";
			default -> "";
		};
		if(!missingPart.isEmpty())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] [npc.verb(lack)] a " + missingPart + ", so nothing happens...)]</p>");
		int oldPlasticity = this.plasticity;
		this.plasticity = Math.max(0, Math.min(plasticity, OrificePlasticity.SEVEN_MOULDABLE.getValue()));
		if(owner==null)
			return "";
		int plasticityChange = this.plasticity - oldPlasticity;
		if(plasticityChange == 0)
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The plasticity of [npc.namePos] " + getDecoratedName() + " doesn't change...)]</p>");
		String name = getName();
		String part = switch(type) {
			case ANUS -> "ass";
			case CROTCH_NIPPLES -> "[npc.crotchBoobs]";
			case NIPPLES -> "[npc.breasts]";
			case PENIS_URETHRA -> "cock";
			case VAGINA, VAGINA_URETHRA -> "pussy";
			default -> name;
		};
		String altName = type == Type.VAGINA ? "cunt" : name;
		String possessive = switch(type) {
			case CROTCH_NIPPLES, NIPPLES -> "";
			default -> "'s";
		};
		String hardening = plasticityChange > 0 ? "hardening" : "softening";
		String plasticityDescriptor = getPlasticity().getDescriptor();
		String determiner = UtilText.generateSingularDeterminer(plasticityDescriptor);
		String style = plasticityChange > 0 ? "style.boldGrow" : "style.boldShrink";
		String hasChanged = switch(type) {
			case CROTCH_NIPPLES, NIPPLES -> plasticityChange > 0 ? "have gained some plasticity" : "have lost some plasticity";
			default -> plasticityChange > 0 ? "plasticity has increased" : "plasticity has decreased";
		};
		String toBeParsed = """
				<p>
					[npc.Name] [npc.verb(let)] out a shocked gasp as [npc.she] suddenly [npc.verb(feel)] a strange %s sensation pulsating deep within [npc.her] %s.
					Before [npc.she] [npc.has] any time to panic, the feeling quickly fades away, leaving [npc.herHim] instinctively knowing that [npc.her] %s%s [%s(%s)].
					<br/>
					[npc.Name] now [npc.has] [style.boldSex(%s %s %s)]!
				</p>
				""";
		return UtilText.parse(owner, String.format(toBeParsed, hardening, part, altName, possessive, style, hasChanged, determiner, plasticityDescriptor, name));
	}

	public boolean isVirgin() {
		return virgin;
	}

	public void setVirgin(boolean virgin) {
		this.virgin = virgin;
	}

	public boolean hasOrificeModifier(OrificeModifier modifier) {
		return orificeModifiers.contains(modifier);
	}

	public String addOrificeModifier(GameCharacter owner, OrificeModifier modifier) {
		if(hasOrificeModifier(modifier)) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		if(type == Type.PENIS_URETHRA && owner != null && !owner.hasPenis()) {
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] [npc.verb(lack)] a penis, so nothing happens...)]</p>");
		}
		orificeModifiers.add(modifier);
		if(owner == null)
			return "";
		if(type == Type.SPINNERET && owner.getBody() == null)
			return "";
		if(type == Type.VAGINA && owner.getBody() == null) {
			return "";
		}
		if(type == Type.SPINNERET && !owner.hasSpinneret())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(Nothing happens, as [npc.name] [npc.verb(lack)] a spinneret...)]</p>");
		if(type == Type.VAGINA && !owner.hasVagina())
			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(Nothing happens, as [npc.name] [npc.verb(lack)] a vagina...)]</p>");
		boolean disabled = switch(type) {
			case CROTCH_NIPPLES -> !owner.isBreastCrotchFuckableNipplePenetration();
			case NIPPLES -> !owner.isBreastFuckableNipplePenetration();
			case PENIS_URETHRA -> !owner.isUrethraFuckable();
			case VAGINA_URETHRA -> !owner.isVaginaUrethraFuckable();
			default -> false;
		};
		String text = switch(type) {
			case ANUS -> switch(modifier) {
				case MUSCLE_CONTROL ->
						"[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] ass, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the inner walls of [npc.her] asshole are now lined with [style.boldGrow(extra muscles)],"
								+ " which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex([npc.NamePos] asshole is now lined with an intricate series of muscles!)]";
				case RIBBED ->
						"[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] ass, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting around a little, [npc.she] [npc.verb(discover)] that the inside of [npc.her] asshole is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
								+ " which provide extreme pleasure when stimulated.<br/>"
								+ "[style.boldSex([npc.NamePos] asshole is now lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						"[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] ass, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the inside of [npc.her] asshole is now lined with [style.boldGrow(little wriggling tentacles)], over which [npc.sheHasFull] limited control.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] asshole is now filled with small tentacles, which wriggle and caress any intruding object with a mind of their own!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out a cry as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] [npc.ass],"
								+ " before the rim of [npc.her] [npc.asshole] [style.boldGrow(puffs up)] into a doughnut-like ring.<br/>"
								+ "[style.boldSex(The rim of [npc.namePos] asshole is now swollen and puffy!)]";
			};
			case CROTCH_NIPPLES -> switch(modifier) {
				case MUSCLE_CONTROL ->
						disabled ? "" : "An intense pressure suddenly swells up deep within [npc.namePos] " + "[npc.crotchBoobs]" + ", and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a series of [style.boldGrow(extra muscles)]"
								+ " growing down into the lining of [npc.her] " + "[npc.crotchNipples]" + "."
								+ " [npc.sheIs] shocked to discover that [npc.sheHasFull] an incredible amount of control over them, allowing [npc.herHim] to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.crotchNipples]" + " are now lined with an intricate series of muscles!)]";
				case RIBBED ->
						disabled ? "" : "An intense pressure suddenly swells up deep within [npc.namePos] " + "[npc.crotchBoobs]" + ", and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a series of [style.boldGrow(fleshy, highly-sensitive ribs)]"
								+ " growing down into the lining of [npc.her] " + "[npc.crotchNipples]" + "."
								+ " Shifting [npc.her] " + "[npc.crotchBoobs]" + " around a little, a jolt of pleasure shoots through [npc.her] torso as [npc.she] feels [npc.her] new additions rub over one another, causing [npc.herHim] to let out another [npc.moan+].<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.crotchNipples]" + " are now lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						disabled ? "" : "An intense pressure suddenly swells up deep within [npc.namePos] " + "[npc.crotchBoobs]" + ", and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a strange tingling sensation deep down in [npc.her] " + "[npc.crotchNipples]" + "."
								+ " The tingling sensation grows stronger, and a surprised cry bursts out from [npc.her] mouth as [npc.she] suddenly discovers that the insides of [npc.her] " + "[npc.crotchNipples]" + " are now filled with"
								+ " [style.boldGrow(a series of little wriggling tentacles)], over which [npc.sheHasFull] limited control.<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.crotchNipples]" + " are now filled with little tentacles, which wriggle with a mind of their own!)]";
				case PUFFY ->
						"[npc.Name] [npc.verb(let)] out a little cry as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] " + "[npc.crotchNipples]" + ", before they suddenly swell out and [style.boldGrow(puff up)].<br/>"
								+ "[style.boldSex([npc.NamePos] " + "[npc.crotchNipples]" + " are now extremely puffy!)]";
			};
			case MOUTH -> switch(modifier) {
				case MUSCLE_CONTROL ->
						"[npc.Name] can't help but let out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the interior of [npc.her] throat is now lined with [style.boldGrow(extra muscles)],"
								+ " which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex([npc.NamePos] throat is now lined with an intricate series of muscles!)]";
				case RIBBED ->
						"[npc.Name] can't help but let out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting [npc.her] throat around a little, [npc.she] [npc.verb(discover)] that the inside of [npc.her] throat is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
								+ " which provide extreme pleasure when stimulated.<br/>"
								+ "[style.boldSex([npc.NamePos] throat is now lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						"[npc.Name] can't help but let out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the inside of [npc.her] throat is now filled with [style.boldGrow(a series of little wriggling tentacles)], over which [npc.sheHasFull] limited control.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] throat is now filled with little tentacles, which wriggle with a mind of their own!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out a little cry as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] mouth, before [npc.her] lips swell out and [style.boldGrow(puff up)].<br/>"
								+ "[style.boldSex([npc.NamePos] lips are now extremely puffy!)]";
			};
			case NIPPLES -> switch(modifier) {
				case MUSCLE_CONTROL ->
						disabled ? "" : "An intense pressure suddenly swells up deep within [npc.namePos] " + "[npc.breasts]" + ", and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a series of [style.boldGrow(extra muscles)]"
								+ " growing down into the lining of [npc.her] " + "[npc.nipples]" + "."
								+ " [npc.sheIs] shocked to discover that [npc.sheHasFull] an incredible amount of control over them, allowing [npc.herHim] to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.nipples]" + " are now lined with an intricate series of muscles!)]";
				case RIBBED ->
						disabled ? "" : "An intense pressure suddenly swells up deep within [npc.namePos] " + "[npc.breasts]" + ", and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a series of [style.boldGrow(fleshy, highly-sensitive ribs)]"
								+ " growing down into the lining of [npc.her] " + "[npc.nipples]" + "."
								+ " Shifting [npc.her] " + "[npc.breasts]" + " around a little, a jolt of pleasure shoots through [npc.her] torso as [npc.she] feels [npc.her] new additions rub over one another, causing [npc.herHim] to let out another [npc.moan+].<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.nipples]" + " are now lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						disabled ? "" : "An intense pressure suddenly swells up deep within [npc.namePos] " + "[npc.breasts]" + ", and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a strange tingling sensation deep down in [npc.her] " + "[npc.nipples]" + "."
								+ " The tingling sensation grows stronger, and a surprised cry bursts out from [npc.her] mouth as [npc.she] suddenly discovers that the insides of [npc.her] " + "[npc.nipples]" + " are now filled with"
								+ " [style.boldGrow(a series of little wriggling tentacles)], over which [npc.sheHasFull] limited control.<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.nipples]" + " are now filled with little tentacles, which wriggle with a mind of their own!)]";
				case PUFFY ->
						"[npc.Name] [npc.verb(let)] out a little cry as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] " + "[npc.nipples]" + ", before they suddenly swell out and [style.boldGrow(puff up)].<br/>"
								+ "[style.boldSex([npc.NamePos] " + "[npc.nipples]" + " are now extremely puffy!)]";
			};
			case PENIS_URETHRA -> switch(modifier) {
				case MUSCLE_CONTROL ->
						disabled ? "" : "[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up at the base of [npc.her] [npc.cock], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the interior of [npc.her] urethra is now lined with [style.boldGrow(muscles)],"
								+ " which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex([npc.NamePos] penile urethra is now lined with an intricate series of muscles!)]";
				case RIBBED ->
						disabled ? "" : "[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up at the base of [npc.her] [npc.cock], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting around a little, [npc.she] [npc.verb(discover)] that the inside of [npc.her] urethra is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
								+ " which provide extreme pleasure when stimulated.<br/>"
								+ "[style.boldSex([npc.NamePos] penile urethra is now lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						disabled ? "" : "[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up at the base of [npc.her] [npc.cock], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the inside of [npc.her] urethra is now lined with [style.boldGrow(little wriggling tentacles)], over which [npc.sheHasFull] limited control.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] penile urethra is now filled with small tentacles, which wriggle and caress any intruding object with a mind of their own!)]";
				case PUFFY ->
						disabled ? "" : "[npc.Name] can't help but let out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a tingling sensation running up the length of [npc.her] [npc.cock],"
								+ " before the rim of [npc.her] urethra suddenly [style.boldGrow(puffs up)] into a doughnut-like ring.<br/>"
								+ "[style.boldSex(The rim of [npc.namePos] penile urethra is now swollen and puffy!)]";
			};
			case SPINNERET -> switch(modifier) {
				case MUSCLE_CONTROL ->
						"[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] spinneret, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the inner walls of [npc.her] spinneret are now lined with [style.boldGrow(extra muscles)],"
								+ " which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex([npc.NamePos] spinneret is now lined with an intricate series of muscles!)]";
				case RIBBED ->
						"[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] spinneret, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting around a little, [npc.she] [npc.verb(discover)] that the inside of [npc.her] spinneret is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
								+ " which provide extreme pleasure when stimulated.<br/>"
								+ "[style.boldSex([npc.NamePos] spinneret is now lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						"[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] spinneret, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the inside of [npc.her] spinneret is now lined with [style.boldGrow(little wriggling tentacles)], over which [npc.sheHasFull] limited control.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] spinneret is now filled with small tentacles, which wriggle and caress any intruding object with a mind of their own!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] spinneret, before it suddenly [style.boldGrow(puffs up)].<br/>"
								+ "[style.boldSex([npc.NamePos] spinneret is now extremely swollen and puffy!)]";
			};
			case VAGINA -> switch(modifier) {
				case MUSCLE_CONTROL ->
						"[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the inner walls of [npc.her] pussy are now lined with [style.boldGrow(extra muscles)],"
								+ " which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex([npc.NamePos] pussy is now lined with an intricate series of muscles!)]";
				case RIBBED ->
						"[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting around a little, [npc.she] [npc.verb(discover)] that the inside of [npc.her] pussy is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
								+ " which provide extreme pleasure when stimulated.<br/>"
								+ "[style.boldSex([npc.NamePos] pussy is now lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						"[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the inside of [npc.her] pussy is now lined with [style.boldGrow(little wriggling tentacles)], over which [npc.sheHasFull] limited control.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] pussy is now filled with small tentacles, which wriggle and caress any intruding object with a mind of their own!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] [npc.pussy], before [npc.her] labia [style.boldGrow(puff up)] into big, swollen pussy lips.<br/>"
								+ "[style.boldSex([npc.NamePos] labia are now extremely swollen and puffy!)]";
			};
			case VAGINA_URETHRA -> switch(modifier) {
				case MUSCLE_CONTROL ->
						disabled ? "" : "[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the interior of [npc.her] urethra is now lined with [style.boldGrow(muscles)],"
								+ " which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex([npc.NamePos] vaginal urethra is now lined with an intricate series of muscles!)]";
				case RIBBED ->
						disabled ? "" : "[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting [npc.her] [npc.pussy] around a little, [npc.she] [npc.verb(discover)] that the inside of [npc.her] urethra is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
								+ " which provide extreme pleasure when stimulated.<br/>"
								+ "[style.boldSex([npc.NamePos] vaginal urethra is now lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						disabled ? "" : "[npc.Name] can't help but let out [npc.a_moan+] as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the inside of [npc.her] urethra is now lined with [style.boldGrow(little wriggling tentacles)], over which [npc.sheHasFull] limited control.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] vaginal urethra is now filled with small tentacles, which wriggle and caress any intruding object with a mind of their own!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out [npc.a_moan+] as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] [npc.pussy],"
								+ " before the rim of [npc.her] urethra suddenly [style.boldGrow(puffs up)] into a doughnut-like ring.<br/>"
								+ "[style.boldSex(The rim of [npc.namePos] urethra is now swollen and puffy!)]";
			};
		};
		if(!text.isEmpty())
			return UtilText.parse(owner, "<p>" + text + "</p>");
		// Catch:
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}

	public String removeOrificeModifier(GameCharacter owner, OrificeModifier modifier) {
		if(!hasOrificeModifier(modifier))
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		orificeModifiers.remove(modifier);
		if(owner == null)
			return "";
		boolean disabled = switch(type) {
			case CROTCH_NIPPLES -> !owner.isBreastCrotchFuckableNipplePenetration();
			case NIPPLES -> !owner.isBreastFuckableNipplePenetration();
			case PENIS_URETHRA -> !owner.isUrethraFuckable();
			case VAGINA_URETHRA -> !owner.isVaginaUrethraFuckable();
			default -> false;
		};
		String text = switch(type) {
			case ANUS -> switch(modifier) {
				case MUSCLE_CONTROL ->
						"[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] ass, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the interior of [npc.her] asshole has [style.boldShrink(lost its extra muscles)].<br/>"
								+ "[style.boldSex([npc.NamePos] asshole is no longer lined with an intricate series of muscles!)]";
				case RIBBED ->
						"[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] ass, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting around a little, [npc.she] [npc.verb(discover)] that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined the walls of [npc.her] asshole [style.boldShrink(have vanished)].<br/>"
								+ "[style.boldSex([npc.NamePos] asshole is no longer lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						"[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] ass, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the [style.boldShrink(wriggling tentacles)] within [npc.her] asshole [style.boldShrink(have all disappeared)].<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] asshole is no longer filled with tentacles!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out a cry as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] [npc.ass],"
								+ " before the puffy rim of [npc.her] [npc.asshole] [style.boldShrink(deflates)] into a more normal-looking shape.<br/>"
								+ "[style.boldSex(The rim of [npc.namePos] asshole is no longer swollen and puffy!)]";
			};
			case CROTCH_NIPPLES -> switch(modifier) {
				case MUSCLE_CONTROL ->
						disabled ? "" : "A soothing warmth slowly washes up through [npc.namePos] torso, and an involuntary [pc.moan] drifts out from between [npc.her] [npc.lips] as [npc.she] [npc.verb(feel)] [npc.her] [style.boldShrink(extra muscles)]"
								+ " melt back into the flesh of [npc.her] " + "[npc.crotchBoobs]" + ".<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.crotchNipples]" + " are no longer lined with an intricate series of muscles!)]";
				case RIBBED ->
						disabled ? "" : "A soothing warmth slowly washes up through [npc.namePos] torso, and an involuntary [pc.moan] drifts out from between [npc.her] [npc.lips] as"
								+ " [npc.she] [npc.verb(feel)] [npc.her] [style.boldShrink(fleshy, highly-sensitive ribs)] melt back into the flesh of [npc.her] " + "[npc.crotchBoobs]" + ".<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.crotchNipples]" + " are no longer ribbed!)]";
				case TENTACLED ->
						disabled ? "" : "A soothing warmth slowly washes up through [npc.namePos] torso, and an involuntary [npc.moan] drifts out from between [npc.her] [npc.lips]"
								+ " as [npc.she] [npc.verb(feel)] [npc.her] [style.boldShrink(little wriggling tentacles)] melt back into the flesh of [npc.her] " + "[npc.crotchBoobs]" + ".<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.crotchNipples]" + " are no longer filled with little tentacles!)]";
				case PUFFY ->
						"[npc.Name] [npc.verb(let)] out a sigh as [npc.her] " + "[npc.crotchNipples]" + " [style.boldShrink(shrink down)] and lose their puffiness.<br/>"
								+ "[style.boldSex([npc.NamePos] " + "[npc.crotchNipples]" + " are no longer extremely puffy!)]";
			};
			case MOUTH -> switch(modifier) {
				case MUSCLE_CONTROL ->
						"[npc.Name] can't help but let out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the interior of [npc.her] throat has lost its [style.boldShrink(extra muscles)].<br/>"
								+ "[style.boldSex([npc.NamePos] throat is no longer lined with an intricate series of muscles!)]";
				case RIBBED ->
						"[npc.Name] can't help but let out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting [npc.her] throat around a little, [npc.she] [npc.verb(discover)] that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined [npc.her] throat have vanished.<br/>"
								+ "[style.boldSex([npc.NamePos] throat is no longer lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						"[npc.Name] can't help but let out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the [style.boldShrink(series of little wriggling tentacles)] within [npc.her] throat have all disappeared.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] throat is no longer filled with little tentacles!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out a little cry as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] lips, before they suddenly [style.boldShrink(deflate)] into a more normal-looking size.<br/>"
								+ "[style.boldSex([npc.NamePos] lips are no longer extremely puffy!)]";
			};
			case NIPPLES -> switch(modifier) {
				case MUSCLE_CONTROL ->
						disabled ? "" : "A soothing warmth slowly washes up through [npc.namePos] torso, and an involuntary [pc.moan] drifts out from between [npc.her] [npc.lips] as [npc.she] [npc.verb(feel)] [npc.her] [style.boldShrink(extra muscles)]"
								+ " melt back into the flesh of [npc.her] " + "[npc.breasts]" + ".<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.nipples]" + " are no longer lined with an intricate series of muscles!)]";
				case RIBBED ->
						disabled ? "" : "A soothing warmth slowly washes up through [npc.namePos] torso, and an involuntary [pc.moan] drifts out from between [npc.her] [npc.lips] as"
								+ " [npc.she] [npc.verb(feel)] [npc.her] [style.boldShrink(fleshy, highly-sensitive ribs)] melt back into the flesh of [npc.her] " + "[npc.breasts]" + ".<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.nipples]" + " are no longer ribbed!)]";
				case TENTACLED ->
						disabled ? "" : "A soothing warmth slowly washes up through [npc.namePos] torso, and an involuntary [npc.moan] drifts out from between [npc.her] [npc.lips]"
								+ " as [npc.she] [npc.verb(feel)] [npc.her] [style.boldShrink(little wriggling tentacles)] melt back into the flesh of [npc.her] " + "[npc.breasts]" + ".<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] " + "[npc.nipples]" + " are no longer filled with little tentacles!)]";
				case PUFFY ->
						"[npc.Name] [npc.verb(let)] out a sigh as [npc.her] " + "[npc.nipples]" + " [style.boldShrink(shrink down)] and lose their puffiness.<br/>"
								+ "[style.boldSex([npc.NamePos] " + "[npc.nipples]" + " are no longer extremely puffy!)]";
			};
			case PENIS_URETHRA -> switch(modifier) {
				case MUSCLE_CONTROL ->
						disabled ? "" : "[npc.Name] can't help but let out a startled cry as an intense pressure swells up within [npc.her] [npc.cock], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the interior of [npc.her] urethra has [style.boldShrink(lost its extra muscles)].<br/>"
								+ "[style.boldSex([npc.NamePos] penile urethra is no longer lined with an intricate series of muscles!)]";
				case RIBBED ->
						disabled ? "" : "[npc.Name] can't help but let out a startled cry as an intense pressure swells up within [npc.her] [npc.cock], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting [npc.her] [npc.pussy] around a little, [npc.she] [npc.verb(discover)] that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined [npc.her] urethra [style.boldShrink(have vanished)].<br/>"
								+ "[style.boldSex([npc.NamePos] penile urethra is no longer lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						disabled ? "" : "[npc.Name] can't help but let out a startled cry as an intense pressure swells up within [npc.her] [npc.cock], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the [style.boldShrink(wriggling tentacles)] within [npc.her] urethra [style.boldShrink(have all disappeared)].<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] penile urethra is no longer filled with tentacles!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out a startled cry as [npc.she] feels a tingling sensation running up the length of [npc.her] [npc.cock],"
								+ " before the [style.boldShrink(puffy rim)] of [npc.her] urethra [style.boldShrink(deflates)] into a more normal-looking shape.<br/>"
								+ "[style.boldSex(The rim of [npc.namePos] penile urethra is no longer swollen and puffy!)]";
			};
			case SPINNERET -> switch(modifier) {
				case MUSCLE_CONTROL ->
						"[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] spinneret, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the interior of [npc.her] spinneret has [style.boldShrink(lost its extra muscles)].<br/>"
								+ "[style.boldSex([npc.NamePos] spinneret is no longer lined with an intricate series of muscles!)]";
				case RIBBED ->
						"[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] spinneret, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting around a little, [npc.she] [npc.verb(discover)] that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined the walls of [npc.her] spinneret [style.boldShrink(have vanished)].<br/>"
								+ "[style.boldSex([npc.NamePos] spinneret is no longer lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						"[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] spinneret, but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the [style.boldShrink(wriggling tentacles)] within [npc.her] spinneret [style.boldShrink(have all disappeared)].<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] spinneret is no longer filled with tentacles!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out a startled cry as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] spinneret,"
								+ " before it suddenly [style.boldShrink(shrinks down)] to take on a more average size.<br/>"
								+ "[style.boldSex([npc.NamePos] spinneret is no longer extra puffy!)]";
			};
			case VAGINA -> switch(modifier) {
				case MUSCLE_CONTROL ->
						"[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the interior of [npc.her] [npc.pussy] has [style.boldShrink(lost its extra muscles)].<br/>"
								+ "[style.boldSex([npc.NamePos] pussy is no longer lined with an intricate series of muscles!)]";
				case RIBBED ->
						"[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting around a little, [npc.she] [npc.verb(discover)] that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined the walls of [npc.her] pussy [style.boldShrink(have vanished)].<br/>"
								+ "[style.boldSex([npc.NamePos] pussy is no longer lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						"[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the [style.boldShrink(wriggling tentacles)] within [npc.her] pussy [style.boldShrink(have all disappeared)].<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] pussy is no longer filled with tentacles!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out a startled cry as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] [npc.pussy],"
								+ " before [npc.her] [style.boldShrink(extra-puffy labia shrink down)] to take on a more average shape.<br/>"
								+ "[style.boldSex([npc.NamePos] labia are no longer extra puffy!)]";
			};
			case VAGINA_URETHRA -> switch(modifier) {
				case MUSCLE_CONTROL ->
						disabled ? "" : "[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the interior of [npc.her] urethra has [style.boldShrink(lost its extra muscles)].<br/>"
								+ "[style.boldSex([npc.NamePos] urethra is no longer lined with an intricate series of muscles!)]";
				case RIBBED ->
						disabled ? "" : "[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " Shifting [npc.her] [npc.pussy] around a little, [npc.she] [npc.verb(discover)] that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined [npc.her] urethra [style.boldShrink(have vanished)].<br/>"
								+ "[style.boldSex([npc.NamePos] urethra is no longer lined with fleshy, pleasure-inducing ribs!)]";
				case TENTACLED ->
						disabled ? "" : "[npc.Name] can't help but let out a startled cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.sheHasFull] any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] [npc.verb(discover)] that the [style.boldShrink(wriggling tentacles)] within [npc.her] urethra [style.boldShrink(have all disappeared)].<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] urethra is no longer filled with tentacles!)]";
				case PUFFY ->
						"[npc.Name] can't help but let out a startled cry as [npc.she] [npc.verb(feel)] a tingling sensation running over [npc.her] [npc.pussy],"
								+ " before the [style.boldShrink(puffy rim)] of [npc.her] urethra [style.boldShrink(deflates)] into a more normal-looking shape.<br/>"
								+ "[style.boldSex(The rim of [npc.namePos] urethra is no longer swollen and puffy!)]";
			};
		};
		if(text.isEmpty())
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		return UtilText.parse(owner, "<p>" + text + "</p>");
	}

	public Set<OrificeModifier> getOrificeModifiers() {
		return orificeModifiers;
	}

	public void clearOrificeModifiers() {
		orificeModifiers.clear();
	}

	private String getDecoratedName() {
		return switch(type) {
			case ANUS -> "[npc.asshole]";
			case CROTCH_NIPPLES -> "[npc.crotchNipples]";
			case NIPPLES -> "[npc.nipples]";
			case VAGINA -> "[npc.pussy]";
			default -> getName();
		};
	}

	private String getName() {
		return switch(type) {
			case ANUS -> "asshole";
			case CROTCH_NIPPLES, NIPPLES -> "nipples";
			case MOUTH -> "throat";
			case PENIS_URETHRA, VAGINA_URETHRA -> "urethra";
			case SPINNERET -> "spinneret";
			case VAGINA -> "pussy";
		};
	}
}