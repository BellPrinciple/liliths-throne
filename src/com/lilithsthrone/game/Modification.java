package com.lilithsthrone.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.ServiceLoader.load;

/**
 * Manages a collection of game elements that can be plugged into the game.
 */
public abstract class Modification {

	/**
	 * Makes the elements of this modification reachable in game.
	 */
	protected abstract void install();

	/**
	 * Cleans up the entries inserted by {@link #install()}.
	 * May be called because installation failed,
	 * therefore it should not assume that the elements exist.
	 * Failure is treated as fatal.
	 */
	protected abstract void uninstall();

	/**
	 * Lists other modifications that have to be installed before this.
	 * The resulting dependency graph shall be acyclic.
	 * @return
	 * Each element identifies a modification that this modification is directly dependent on.
	 */
	public Stream<Class<?extends Modification>> dependencies() {
		return Stream.of();
	}

	/**
	 * @return
	 * This modification is marked as installed.
	 */
	public final boolean installed() {
		return installed;
	}

	/**
	 * Installs this mod and all its direct and indirect dependencies.
	 */
	public final void installAll() {
		if(installed)
			return;
		var g = dag(m->m.dependencies().map(all::get).filter(d->!d.installed));
		int i = g.length - 1;
		try {
			for(; i >= 0; --i)
				g[i].install();
		}
		catch(RuntimeException x) {
			for(; i < g.length; ++i)
				g[i].uninstall();
			throw x;
		}
		for(var m : g)
			m.installed = true;
	}

	/**
	 * Uninstalls this mod and all its direct and indirect dependants.
	 */
	public final void uninstallAll() {
		if(!installed)
			return;
		var g = dag(m->m.dependents.stream().filter(d->d.installed));
		for(int i = g.length - 1; i >= 0; --i) {
			g[i].uninstall();
			g[i].installed = false;
		}
	}

	/**
	 * Updates the runtime database.
	 * @return
	 * All modifications.
	 */
	public static Stream<Modification> all() {
		var a = new LinkedList<Modification>();
		load(Modification.class).stream().forEach(p->all.computeIfAbsent(p.type(),k->{
			var m = p.get();
			a.add(m);
			return m;
		}));
		for(var m : a)
			m.dependencies().map(all::get).forEach(d->d.dependents.add(m));
		return all.values().stream();
	}

	/**
	 * Looks up a modification.
	 * @param c
	 * Identifies the target modification.
	 * @return
	 * Target instance if found, else {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Modification> T of(Class<T> c) {
		return (T)all.get(c);
	}

	private boolean installed = false;
	private final LinkedList<Modification> dependents = new LinkedList<>();
	private static final HashMap<Class<?extends Modification>,Modification> all = new HashMap<>();

	private Modification[] dag(Function<Modification,Stream<Modification>> edges) {
		var s = new LinkedList<Modification>();
		var q = new LinkedList<Modification>();
		q.add(this);
		s.add(this);
		while(!q.isEmpty())
			edges.apply(q.remove()).forEach(d->{
				q.add(d);
				//if d is already contained, it is reinserted as the last element
				s.remove(d);
				s.add(d);
			});
		return s.toArray(Modification[]::new);
	}
}
