package com.lilithsthrone.game.sex;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.dialogue.utils.UtilText;

import java.util.Objects;

public final class Lubrication {

	private final SexArea area;
	private final GameCharacter owner;
	private final LubricationType type;

	public Lubrication(GameCharacter c, SexAreaInterface a, GameCharacter o, LubricationType t) {
		this(new SexArea(c, a),o,t);
	}

	public Lubrication(SexArea a, GameCharacter o, LubricationType t) {
		assert null!=a && null!=t;
		area = a;
		owner = o;
		type = t;
	}

	public GameCharacter character() {
		return null;
	}

	public SexArea area() {
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

	public String getName() {
		return null==owner ? type.nameNullOwner() : UtilText.parse(owner,(owner==area.character()?"[npc.her]":"[npc.namePos]")+" <span style='color="+type.getColour().toWebHexString()+"'>"+type.nameOwner()+"</span>");
	}

	@Override
	public int hashCode() {
		return area.hashCode() ^ Objects.hashCode(owner) ^ type.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Lubrication
			&& area.equals(((Lubrication)o).area)
			&& Objects.equals(owner,((Lubrication)o).owner)
			&& type.equals(((Lubrication)o).type);
	}
}
