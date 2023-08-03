package com.lilithsthrone.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Stack;

public interface Markup <T extends Markup<T>> {

	T comment(Object... text);

	T text(Object... text);

	T attribute(Object key, Object... value);

	T child(Object tagname);

	T push(Object tagname);

	T pop();

	T print(Printable<T> printable);

	default Element element(Object tagname) {
		return new Element(this, tagname);
	}

	default UIMarkup ui() {
		return new UIMarkup(this);
	}

	@FunctionalInterface
	interface Printable <M extends Markup<M>> {

		void printInto(M out);

		default Printable<M> onlyIf(boolean condition) {
			return condition ? this : o -> {};
		}

		default Printable<M> andThen(Printable<M> other) {
			return o -> o.print(this).print(other);
		}
	}

	final class Element implements Markup<Element>, AutoCloseable {
		private final Markup<?> parent;
		private final Object tagname;
		private int depth;
		private Element(Markup<?> p, Object t) {
			parent = Objects.requireNonNull(p);
			tagname = Objects.requireNonNull(t);
			p.push(t);
		}
		@Override
		public Element comment(Object... text) {
			parent.comment(text);
			return this;
		}
		@Override
		public Element text(Object... text) {
			parent.text(text);
			return this;
		}
		@Override
		public Element attribute(Object key, Object... value) {
			parent.attribute(key, value);
			return this;
		}
		@Override
		public Element child(Object tagname) {
			parent.child(tagname);
			return this;
		}
		@Override
		public Element push(Object tagname) {
			parent.push(tagname);
			++depth;
			return this;
		}
		@Override
		public Element pop() {
			checkState(depth != 0, "Popped too many elements in <%s>.", tagname);
			--depth;
			parent.pop();
			return this;
		}
		@Override
		public Element print(Printable<Element> printable) {
			int d = depth;
			printable.printInto(this);
			checkState(d == depth, "Printable popped too %s elements in <%s>", depth < d ? "many" : "few", tagname);
			return this;
		}
		@Override
		public void close() {
			assert depth >= 0 : "'pop()' should have properly checked for underflow";
			checkState(depth == 0, "Popped too few elements in <%s>.", tagname);
			parent.pop();
		}
		private static void checkState(boolean condition, String message, Object... arguments) {
			if(!condition)
				throw new IllegalStateException(String.format(message, arguments));
		}
	}

	final class Null implements Markup<Null> {
		private int depth;
		public void check() {
			assert depth >= 0;
			Element.checkState(depth == 0, "Popped to few elements.");
		}
		@Override
		public Null comment(Object... text) {
			return this;
		}
		@Override
		public Null text(Object... text) {
			return this;
		}
		@Override
		public Null attribute(Object key, Object... value) {
			return this;
		}
		@Override
		public Null child(Object tagname) {
			return this;
		}
		@Override
		public Null push(Object tagname) {
			++depth;
			return this;
		}
		@Override
		public Null pop() {
			if (depth == 0) {
				throw new IllegalStateException("Popped too many elements.");
			}
			--depth;
			return this;
		}
		@Override
		public Null print(Printable<Null> printable) {
			return this;
		}
	}

	final class ToString implements Markup<ToString> {
		private final StringBuilder out;
		private final LinkedHashMap<String, ArrayList<Object>> attributes = new LinkedHashMap<>();
		private final Stack<String> stack = new Stack<>();
		public ToString() {
			this(new StringBuilder());
		}
		public ToString(StringBuilder o) {
			out = o;
		}
		public String asString() {
			if (!stack.empty()) {
				throw new IllegalStateException("Popped too few elements.");
			}
			return out.toString();
		}
		@Override
		public ToString comment(Object... text) {
			for (Object o : text) {
				out.append(o);
			}
			return this;
		}
		@Override
		public ToString text(Object... text) {
			for (Object o : text) {
				out.append(o);
			}
			return this;
		}
		@Override
		public ToString attribute(Object key, Object... value) {
			Collections.addAll(attributes.computeIfAbsent(String.valueOf(key), k -> new ArrayList<>()), value);
			return this;
		}
		@Override
		public ToString child(Object tagname) {
			writeTagWithAttributes(tagname);
			out.append("/>");
			return this;
		}
		@Override
		public ToString push(Object tagname) {
			stack.push(String.valueOf(tagname));
			writeTagWithAttributes(tagname);
			out.append('>');
			return this;
		}
		@Override
		public ToString pop() {
			if (stack.empty()) {
				throw new IllegalStateException("Popped to many elements.");
			}
			return this;
		}
		@Override
		public ToString print(Printable<ToString> printable) {
			var d = stack.toArray(String[]::new);
			stack.clear();
			printable.printInto(this);
			for (int i = d.length - 1; i >= 0; --i)
				stack.add(d[i]);
			return this;
		}
		private void writeTagWithAttributes(Object tagname) {
			out.append('<').append(tagname);
			for(var entry : attributes.entrySet()) {
				out.append(' ').append(entry.getKey()).append("=\"");
				for(Object v : entry.getValue())
					//TODO escape quotes
					out.append(v);
				out.append("\"");
			}
		}
	}

	final class ToList <M extends Markup<M>> implements Markup<ToList<M>>, Printable<M> {
		private final ArrayList<Printable<M>> list = new ArrayList<>();
		@Override
		public void printInto(M out) {
			for (Printable<M> p : list) {
				p.printInto(out);
			}
		}
		@Override
		public ToList<M> comment(Object... text) {
			list.add(out -> out.comment(text));
			return this;
		}
		@Override
		public ToList<M> text(Object... text) {
			list.add(out -> out.text(text));
			return this;
		}
		@Override
		public ToList<M> attribute(Object key, Object... value) {
			list.add(out -> out.attribute(key, value));
			return this;
		}
		@Override
		public ToList<M> child(Object tagname) {
			list.add(out -> out.child(tagname));
			return this;
		}
		@Override
		public ToList<M> push(Object tagname) {
			list.add(out -> out.push(tagname));
			return this;
		}
		@Override
		public ToList<M> pop() {
			list.add(Markup::pop);
			return this;
		}
		@Override
		public ToList<M> print(Printable<ToList<M>> printable) {
			list.add(out -> {
				var proxy = new ToList<M>();
				printable.printInto(proxy);
				proxy.printInto(out);
			});
			return this;
		}
	}
}
