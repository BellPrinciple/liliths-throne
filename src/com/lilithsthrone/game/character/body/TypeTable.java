package com.lilithsthrone.game.character.body;

import com.lilithsthrone.game.character.body.types.BodyPartTypeInterface;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Table;
import com.lilithsthrone.utils.Util;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class TypeTable <T extends BodyPartTypeInterface> extends Table<T> {

	private final Map<Race,List<T>> byRace;
	private final List<T> list;

	@FunctionalInterface
	public interface Constructor <T> {
		T construct(File source, String name, String author, boolean isMod);
	}

	public TypeTable(Function<String,String> s, T[] v, String r, Constructor<?extends T> c) {
		super(s);
		forEachMod("/race","bodyParts",null,(f,n,a)->{
			if(!Util.getXmlRootElementName(f).equals(r))
				return;
			var k = n.replaceAll("bodyParts_","");
			add(k,c.construct(f,k,a,true));
		});
		forEachExternal("res/race","bodyParts",null,(f,n,a)->{
			if(!Util.getXmlRootElementName(f).equals(r))
				return;
			var k = n.replaceAll("bodyParts_","");
			add(k,c.construct(f,k,a,false));
		});
		for(T x: v)
			add(x.getId(),x);
		byRace = list().stream()
		.collect(groupingBy(BodyPartTypeInterface::getRace));
		list = list().stream()
		.sorted((t1,t2)->t1.getRace()== Race.NONE
			? -1
			: t2.getRace()==Race.NONE
				? 1
				: t1.getRace().getName(false).compareTo(t2.getRace().getName(false)))
		.collect(Collectors.toUnmodifiableList());
	}

	/**
	 * @param s
	 * Transforms an identifier before lookup.
	 * @param o
	 * Contains fields to iterate.
	 * @param t
	 * Required type for fields in {@code o}.
	 * @param r
	 * Required root tag-name for source files.
	 * @param c
	 * Accepts a file to parse, the id of the object, the name of the author, and whether it is imported from a resource in the jar.
	 * It should return an instance to be inserted into this table.
	 */
	public TypeTable(Function<String,String> s, Class<?> o, Class<? extends T> t, String r, Constructor<?extends T> c) {
		super(s);
		forEachMod("/race","bodyParts",null,(f,n,a)->{
			if(!Util.getXmlRootElementName(f).equals(r))
				return;
			var k = n.replaceAll("bodyParts_","");
			add(k,c.construct(f,k,a,true));
		});
		forEachExternal("res/race","bodyParts",null,(f,n,a)->{
			if(!Util.getXmlRootElementName(f).equals(r))
				return;
			var k = n.replaceAll("bodyParts_","");
			add(k,c.construct(f,k,a,false));
		});
		addFields(o,t);
		byRace = list().stream()
		.collect(groupingBy(BodyPartTypeInterface::getRace));
		list = list().stream()
		.sorted((t1,t2)->t1.getRace()== Race.NONE
			? -1
			: t2.getRace()==Race.NONE
				? 1
				: t1.getRace().getName(false).compareTo(t2.getRace().getName(false)))
		.collect(Collectors.toUnmodifiableList());
	}

	public Optional<List<T>> of(Race r) {
		return Optional.ofNullable(byRace.get(r));
	}

	public List<T> listByRace() {
		return list;
	}
}
