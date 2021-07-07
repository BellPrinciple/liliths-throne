package com.lilithsthrone.game.sex;

import com.lilithsthrone.game.character.GameCharacter;

/**
A pair of {@link GameCharacter} and {@link SexAreaInterface}.
*/
public final class SexArea {

	private final GameCharacter character;
	private final SexAreaInterface type;

	public SexArea(GameCharacter c, SexAreaInterface a) {
		assert null!=c && null!=a;
		character = c;
		type = a;
	}

	public GameCharacter character() {
		return character;
	}

	public SexAreaInterface type() {
		return type;
	}

	@Override
	public int hashCode() {
		return character.hashCode() ^ type.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof SexArea
		&& character.equals(((SexArea)o).character)
		&& type.equals(((SexArea)o).type);
	}
}
