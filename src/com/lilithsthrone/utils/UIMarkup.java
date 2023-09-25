package com.lilithsthrone.utils;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.colours.Colour;

/**
 * Decorates a markup writer with common user interface instructions.
 */
public final class UIMarkup implements Markup<UIMarkup> {

	private final Markup<?> inner;

	UIMarkup(Markup<?> i) {
		inner = i;
	}

	@FunctionalInterface
	public interface UIPrintable extends Printable<UIMarkup> {
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Elements

	public Element paragraph() {
		return element("p");
	}

	public UIMarkup paragraph(
			Printable<UIMarkup> printable) {
		return element("p", printable);
	}

	public Element div() {
		return element("div");
	}

	public UIMarkup div(
			Printable<UIMarkup> printable) {
		return element("div", printable);
	}

	public Element span() {
		return element("span");
	}

	public UIMarkup span(
			Printable<UIMarkup> printable) {
		return element("span", printable);
	}

	public UIMarkup element(
			Object tagName,
			Printable<UIMarkup> printable) {
		try(var el = element(tagName)) {
			el.ui().print(printable);
		}
		return this;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Attributes

	public UIMarkup id(
			Object... id) {
		return attribute("id", id);
	}

	public UIMarkup cls(
			Object... cls) {
		return attribute("class", cls);
	}

	public UIMarkup color(
			Colour colour) {
		return style("color:", colour.toWebHexString(), ";");
	}

	public UIMarkup center() {
		return style("text-align:center;");
	}

	public UIMarkup bold() {
		return style("font-weight:bold;");
	}

	public UIMarkup italic() {
		return style("font-style:italic;");
	}

	public UIMarkup fontSize(
			int size) {
		return style("font-size:", size, "px;");
	}

	public UIMarkup lineHeight(
			int size) {
		return style("line-height:", size, "px;");
	}

	public UIMarkup style(
			Object... styles) {
		return attribute("style", styles);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Parsing

	public UIMarkup parse(String text) {
		return text(UtilText.parse(text));
	}

	public UIMarkup parse(GameCharacter npc, String text) {
		return text(UtilText.parse(npc, text));
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Overrides Markup

	@Override
	public UIMarkup comment(
			Object... text) {
		inner.comment();
		return this;
	}

	@Override
	public UIMarkup text(
			Object... text) {
		inner.text(text);
		return this;
	}

	@Override
	public UIMarkup attribute(
			Object key,
			Object... value) {
		inner.attribute(key, value);
		return this;
	}

	@Override
	public UIMarkup child(
			Object tagname) {
		inner.child(tagname);
		return this;
	}

	@Override
	public UIMarkup push(
			Object tagname) {
		inner.push(tagname);
		return this;
	}

	@Override
	public UIMarkup pop() {
		inner.pop();
		return this;
	}

	@Override
	public UIMarkup print(
			Printable<UIMarkup> printable) {
		printable.printInto(this);
		return this;
	}
}
