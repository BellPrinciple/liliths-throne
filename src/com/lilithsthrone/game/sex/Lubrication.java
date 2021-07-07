package com.lilithsthrone.game.sex;

import com.lilithsthrone.game.character.GameCharacter;

import java.util.Objects;

public final class Lubrication {

	private final GameCharacter character;
	private final SexAreaInterface area;
	private final GameCharacter owner;
	private final LubricationType type;

	public Lubrication(GameCharacter c, SexAreaInterface a, GameCharacter o, LubricationType t) {
		assert null!=c && null!=a && null!=t;
		character = c;
		area = a;
		owner = o;
		type = t;
	}

	public GameCharacter character() {
		return character;
	}

	public SexAreaInterface area() {
		return area;
	}

	public GameCharacter owner() {
		return owner;
	}

	public LubricationType type() {
		return type;
	}

	public boolean hasOwner() {
		return null!=owner;
	}

	@Override
	public int hashCode() {
		return character.hashCode() ^ area.hashCode() ^ Objects.hashCode(owner) ^ type.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Lubrication
			&& character.equals(((Lubrication)o).character)
			&& area.equals(((Lubrication)o).area)
			&& Objects.equals(owner,((Lubrication)o).owner)
			&& type.equals(((Lubrication)o).type);
	}
}
