package com.lilithsthrone.utils;

import com.lilithsthrone.controller.xmlParsing.XMLLoadException;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;

/**
 * Collection of named elements.
 * Usually instantiated in static fields.
 */
public class Table <T> {

	private final HashMap<String,T> map = new HashMap<>();
	private final Function<String,String> sanitize;

	/**
	 * @param s
	 * Modifies the names passed to {@link #of(String)}.
	 */
	public Table(Function<String,String> s) {
		sanitize = s;
	}

	/**
	 * Looks up an element by name, using the closest match by Levenshtein distance.
	 * @param key
	 * Identifies an element in this collection.
	 * @return
	 * The identified element.
	 * {@code null} if and only if this is empty.
	 */
	public T of(String key) {
		return map.get(Util.getClosestStringMatch(sanitize.apply(key),map.keySet()));
	}

	/**
	 * Looks up an element by name.
	 * @param key
	 * Identifies an element in this collection.
	 * @return
	 * The identified element, if found, else {@code empty()}.
	 */
	public Optional<T> exact(String key) {
		return Optional.ofNullable(map.get(key));
	}

	/**
	 * Iterates all currently registered elements.
	 * @return
	 * Copy of the complete collection, in unspecified order.
	 */
	public List<T> list() {
		return List.copyOf(map.values());
	}

	/**
	 * Inserts a new element into this collection.
	 * @param key
	 * Identifies the element.
	 * @param value
	 * Element to insert.
	 * @return
	 * This collection changed in this operation.
	 * Otherwise, this collection was unchanged.
	 */
	public boolean add(String key, T value) {
		return null==map.putIfAbsent(key,value);
	}

	/**
	 * Erases an element from this collection.
	 * @param key
	 * Identifies the element to remove.
	 * @return
	 * Removed element or {@code null}, if non existed.
	 */
	public T remove(String key) {
		return map.remove(key);
	}

	/**
	 * Inserts all instances that are stored in {@code static final} fields.
	 * @param owner
	 * Contains the fields.
	 * @param target
	 * Class of elements to be inserted.
	 */
	protected void addFields(Class<?> owner, Class<?extends T> target) {
		addFields(owner,target,(k,v)->{});
	}

	/**
	 * Inserts all instances that are stored in {@code static final} fields.
	 * @param owner
	 * Contains the fields.
	 * @param target
	 * Class of elements to be inserted.
	 * @param action
	 * Accepts field name and stored instance.
	 */
	protected <S extends T> void addFields(Class<?> owner, Class<S> target, BiConsumer<String,?super S> action) {
		for(Field f : owner.getFields()) {
			if(!target.isAssignableFrom(f.getType()))
				continue;
			if(!isStatic(f.getModifiers()) || !isFinal(f.getModifiers()))
				continue;
			try {
				var key = f.getName();
				var value = target.cast(f.get(null));
				action.accept(key,value);
				add(key,value);
			} catch (IllegalArgumentException | IllegalAccessException x) {
				x.printStackTrace();
			}
		}
	}

	@FunctionalInterface
	protected interface Constructor {
		void construct(File file, String name, String author) throws XMLLoadException;
	}

	/**
	 * @param containingFolderId
	 * @param filterFolderName
	 * @param filterPathName
	 * @param c
	 * Processed input file, name and author name.
	 */
	protected static void forEachMod(String containingFolderId, String filterFolderName, String filterPathName, Constructor c) {
		for(var entry : Util.getExternalModFilesById(containingFolderId,filterFolderName,filterPathName).entrySet()) {
			for(var innerEntry : entry.getValue().entrySet()) {
				var file = innerEntry.getValue();
				try {
					c.construct(file,innerEntry.getKey(),entry.getKey());
				}
				catch(Exception x) {
					System.err.printf("Failed loading mod %s%nActual exception:%n",file.getAbsolutePath());
					x.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param containingFolderId
	 * @param filterFolderName
	 * @param filterPathName
	 * @param c
	 * Processes input file, name and author name.
	 */
	protected static void forEachExternal(String containingFolderId, String filterFolderName, String filterPathName, Constructor c) {
		for(var entry : Util.getExternalFilesById(containingFolderId,filterFolderName,filterPathName).entrySet()) {
			for(var innerEntry : entry.getValue().entrySet()) {
				var file = innerEntry.getValue();
				try {
					c.construct(file,innerEntry.getKey(),entry.getKey());
				}
				catch(Exception x) {
					System.err.printf("Failed loading external file %s%nActual exception:%n",file.getAbsolutePath());
					x.printStackTrace();
				}
			}
		}
	}
}
