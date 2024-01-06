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

	public UIMarkup br() {
		return child("br");
	}

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

	public Element div(String cls) {
		return element("div").attribute("class", cls);
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

	public UIMarkup bold(
			Printable<UIMarkup> printable) {
		return element("b", printable);
	}

	public UIMarkup italic(
			Printable<UIMarkup> printable) {
		return element("i", printable);
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

	public UIMarkup absolutePosition() {
		return style("position:absolute;");
	}

	public UIMarkup relativePosition() {
		return style("position:relative;");
	}

	public UIMarkup margin(
			int top,
			int right,
			int bottom,
			int left) {
		return style("margin:", top, "px ", right, "px ", bottom, "px ", left, "px;");
	}

	public UIMarkup margin(
			int vertical,
			int horizontal) {
		return style("margin:", horizontal, "px ", vertical, "px;");
	}

	public UIMarkup margin(
			int px) {
		return style("margin:", px, "px;");
	}

	public UIMarkup margin() {
		return style("margin:2px;");
	}

	public UIMarkup border(
			Colour colour,
			int px) {
		return style("border:", px, "px solid ", colour.toWebHexString(), ";");
	}

	public UIMarkup background(
			Colour colour) {
		return style("background-color:", colour.toWebHexString(), ";");
	}

	public UIMarkup padding(
			int px) {
		return style("padding:", px, "px;");
	}

	public UIMarkup maxHeight(
			int px) {
		return style("maxHeight:", px, "px;");
	}

	public UIMarkup minHeight(
			int px) {
		return style("minHeight:", px, "px;");
	}

	public UIMarkup height(
			int px) {
		return style("height:", px, "px;");
	}

	public UIMarkup width(
			int px) {
		return style("width:", px, "px;");
	}

	public UIMarkup fullWidth() {
		return style("width:100%;");
	}

	public UIMarkup halfWidth() {
		return style("width:50%;");
	}

	public UIMarkup center() {
		return style("text-align:center;");
	}

	public UIMarkup color(
			Colour colour) {
		return style("color:", colour.toWebHexString(), ";");
	}

	public UIMarkup bold() {
		return style("font-weight:bold;");
	}

	public UIMarkup normal() {
		return style("font-weight:normal;");
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
