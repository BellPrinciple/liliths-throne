package com.lilithsthrone.utils;

import com.lilithsthrone.utils.colours.Colour;

import java.util.ArrayList;

import static com.lilithsthrone.utils.colours.PresetColour.*;

public interface MarkupWriter <T extends MarkupWriter<T>> {

	T skip();

	T text(Object first, Object... content);

	T open(String tagname);

	T close(String tagname);

	T element(String tagname);

	T attr(String key, Object... value);

	static ToString string() {
		return new ToString(new StringBuilder());
	}

	static ToOther buffer() {
		return new ToOther();
	}

	default T span(Colour colour, Object first, Object... content) {
		return open("span").attr("style","color:",colour.toWebHexString()).text(first,content).close("span");
	}

	default T bold(Object first, Object... content) {
		return open("b").text(first,content).close("b");
	}

	default T bold(Colour colour, Object first, Object... content) {
		return open("b").attr("style","color:",colour.toWebHexString()).text(first,content).close("b");
	}

	default T center(Object first, Object... content) {
		return open("p").attr("style","text-align:center").text(first,content).close("p");
	}

	default T br() {
		return element("br");
	}

	default Context center() {
		var c = new Context(this,"p");
		c.attr("style","text-align:center");
		return c;
	}

	default T bad(Object first, Object... content) {
		return span(GENERIC_BAD,first,content);
	}

	default T badBold(Object first, Object... content) {
		return bold(GENERIC_BAD,first,content);
	}

	default T badMinor(Object first, Object... content) {
		return span(GENERIC_MINOR_BAD,first,content);
	}

	default T good(Object first, Object... content) {
		return span(GENERIC_GOOD,first,content);
	}

	default T goodBold(Object first, Object... content) {
		return bold(GENERIC_GOOD,first,content);
	}

	default T goodMinor(Object first, Object... content) {
		return span(GENERIC_MINOR_GOOD,first,content);
	}

	default T terrible(Object first, Object... content) {
		return span(GENERIC_TERRIBLE,first,content);
	}

	default T terribleBold(Object first, Object... content) {
		return bold(GENERIC_TERRIBLE,first,content);
	}

	default T append(ToOther o) {
		for(var c: o.command)
			c.run(this);
		return skip();
	}

	final class Context implements AutoCloseable, MarkupWriter<Context> {

		private final MarkupWriter<?> writer;
		private final String tagname;

		public Context(MarkupWriter<?> w, String n) {
			writer = w;
			tagname = n;
			writer.open(tagname);
		}

		@Override
		public void close() {
			writer.close(tagname);
		}

		@Override
		public Context skip() {
			return this;
		}

		@Override
		public Context text(Object first, Object... content) {
			writer.text(first,content);
			return this;
		}

		@Override
		public Context open(String tagname) {
			writer.open(tagname);
			return this;
		}

		@Override
		public Context close(String tagname) {
			writer.close(tagname);
			return this;
		}

		@Override
		public Context element(String tagname) {
			writer.element(tagname);
			return this;
		}

		@Override
		public Context attr(String key, Object... value) {
			writer.attr(key,value);
			return this;
		}
	}

	final class ToString implements MarkupWriter<ToString> {

		private final StringBuilder b;
		private Boolean tag;

		private ToString(StringBuilder builder) {
			b = builder;
		}

		public String build() {
			prepare();
			return b.toString();
		}

		@Override
		public ToString skip() {
			return this;
		}

		@Override
		public ToString text(Object first, Object... content) {
			prepare();
			b.append(first);
			for(Object c: content)
				b.append(c);
			return this;
		}

		@Override
		public ToString open(String tagname) {
			prepare();
			b.append('<').append(tagname);
			tag = Boolean.TRUE;
			return this;
		}

		@Override
		public ToString close(String tagname) {
			prepare();
			b.append('<').append('/').append(tagname).append('>');
			return this;
		}

		@Override
		public ToString element(String tagname) {
			prepare();
			b.append('<').append(tagname);
			tag = Boolean.FALSE;
			return this;
		}

		@Override
		public ToString attr(String key, Object... value) {
			assert tag;
			b.append(' ').append(key).append('=').append('"');
			for(var v: value)
				b.append(v);
			b.append('"');
			return this;
		}

		private void prepare() {
			if(tag == null)
				return;
			if(tag)
				b.append('/');
			b.append('>');
			tag = null;
		}
	}

	final class ToOther implements MarkupWriter<ToOther> {

		@FunctionalInterface
		private interface Command {
			void run(MarkupWriter<?> other);
		}

		private final ArrayList<Command> command = new ArrayList<>();

		private ToOther() {}

		@Override
		public ToOther skip() {
			return this;
		}

		@Override
		public ToOther text(Object first, Object... content) {
			command.add(o->o.text(first,content));
			return this;
		}

		@Override
		public ToOther open(String tagname) {
			command.add(o->o.open(tagname));
			return this;
		}

		@Override
		public ToOther close(String tagname) {
			command.add(o->o.close(tagname));
			return this;
		}

		@Override
		public ToOther element(String tagname) {
			command.add(o->o.element(tagname));
			return this;
		}

		@Override
		public ToOther attr(String key, Object... value) {
			command.add(o->o.attr(key,value));
			return this;
		}
	}
}
