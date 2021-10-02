package com.lilithsthrone.utils;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

/**
 * Decorates a pseudorandom number generator.
 * @version 0.4.2
 */
public class Random extends java.util.Random {

	Random() {
	}

	public Random(int seed) {
		super(seed);
	}

	/**
	 * Selects some value from an array.
	 * @param values Nonempty array of candidate values.
	 * @return Some candidate.
	 * @throws IllegalArgumentException {@code values} is empty.
	 */
	@SafeVarargs
	public final <Any> Any ofValues(Any... values) {
		return values[nextInt(values.length)];
	}

	/**
	 * Selects some value from a collection.
	 * @param values Nonempty collection of candidate values.
	 * @return Some candidate.
	 * @throws IllegalArgumentException {@code values} is empty.
	 */
	public <Any> Any of(Collection<Any> values) {
		int index = nextInt(values.size());
		for(var element : values) {
			if(index == 0)
				return element;
			--index;
		}
		throw new ConcurrentModificationException();
	}

	/**
	 * Selects some value from a list.
	 * @param values Nonempty list of candidate values.
	 * @return Some candidate.
	 * @throws IllegalArgumentException {@code values} is empty.
	 */
	public <Any> Any of(List<Any> values) {
		return values.get(nextInt(values.size()));
	}

	/**
	 * Selects some value from a weighted collection.
	 * @param weight Nonempty collection of candidate values, each associated with a weight.
	 * @return Some candidate.
	 * @throws IllegalArgumentException {@code weight} is empty.
	 */
	public <Any> Any of(Map<Any,?extends Number> weight) {
		double sum = nextDouble() * weight.values().stream().mapToDouble(Number::doubleValue).sum();
		for(var entry : weight.entrySet()) {
			sum -= entry.getValue().doubleValue();
			if(sum <= 0)
				return entry.getKey();
		}
		throw new IllegalArgumentException();
	}
}
