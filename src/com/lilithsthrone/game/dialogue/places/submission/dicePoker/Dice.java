package com.lilithsthrone.game.dialogue.places.submission.dicePoker;

import java.util.EnumMap;
import java.util.Map;

import com.lilithsthrone.utils.Util;

/**
 * @since 0.2.6
 * @version 0.2.6
 * @author Innoxia
 */
public class Dice {
	
	private DiceFace face;
	private final EnumMap<DiceFace, Float> diceBias = new EnumMap<>(DiceFace.class);
	
	public Dice() {
		face = DiceFace.SIX;
		for(DiceFace face : DiceFace.values()) {
			diceBias.put(face, 1f);
		}
	}
	
	public Dice(Map<DiceFace, Float> diceBias) {
		this();
		this.diceBias.putAll(diceBias);
	}

	public void roll() {
		face = Util.random.of(diceBias);
	}
	
	public DiceFace getFace() {
		return face;
	}
	
	public void setFace(DiceFace face) {
		this.face = face;
	}
	
	public Map<DiceFace, Float> getDiceBias() {
		return diceBias;
	}
}
