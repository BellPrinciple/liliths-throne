package com.lilithsthrone.controller.xmlParsing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;

import com.lilithsthrone.main.Main;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

/**
 * @author BlazingMagpie@gmail.com (or ping BlazingMagpie in Discord)
 * @since 0.2.5.8
 * @version 0.2.12
 * 
 * <p>Wrapper for org.w3c.dom.Element that's supposed to simplify mod support</p>
 */
public class Element {

	private final org.w3c.dom.Element innerElement;//contains wrapped element
	private final String fileDirectory;//contains location of the file to show in case of error
	private final org.w3c.dom.Document document;//document the element is in, legacy

	/**
	 * <p>
	 * Construct new instance with given {@linkplain  org.w3c.dom.Element} and
	 * location of XML document as String (used only for error reporting).</p>
	 * <p>
	 * Should only be made by other Element or {@link Element#getDocumentRootElement(java.io.File) }.</p>
	 *
	 * @param w3cElement {@link org.w3c.dom.Element} to wrap
	 * @param fileDirectory String of a location of original file
	 */
	private Element(org.w3c.dom.Element w3cElement, String fileDirectory, org.w3c.dom.Document document) {
		innerElement = w3cElement;
		this.fileDirectory = fileDirectory;
		this.document = document;
	}
	
	/**
	 * Loads and prepares document for reading and returns root element, for example {@code <clothing>} in clothes mods.
	 *
	 * @param xmlFile File in XML format. Throws an exception with necessary details if something goes wrong. {@link XMLLoadException} for mode details.
	 *
	 * @return Element at the root of the document
	 * @throws XMLLoadException
	 */
	public static Element getDocumentRootElement(File xmlFile) throws XMLLoadException{
		try{
			String fileDirectory = xmlFile.getAbsolutePath();
			Document parsedDocument = Main.getDocBuilder().parse(xmlFile);
			parsedDocument.getDocumentElement().normalize();
			return new Element(parsedDocument.getDocumentElement(), fileDirectory, parsedDocument);
			
		} catch(Exception e){
			throw new XMLLoadException(e);
		}
	}

	public static Element getElement(org.w3c.dom.Element w3cElement, String fileDirectory, org.w3c.dom.Document document) throws XMLLoadException{
		try{
			return new Element(w3cElement, "", document);
			
		} catch(Exception e){
			throw new XMLLoadException(e);
		}
	}
	
	/**
	 * <p>
	 * Returns tag name of the element</p>
	 *
	 * @return Tag name as String, for example: {@code <element></element>}
	 * returns "element"
	 */
	public String getTagName() {
		return innerElement.getTagName();
	}

	/**
	 * <p>
	 * Returns value of attribute requested by name</p>.
	 *
	 * @param name String name of the attribute to return value of
	 *
	 * @return Attribute as String, for example:
	 * {@code <element attribute = "value"></element>} returns "value". Returns an empty String if no such attribute was present.
	 */
	public String getAttribute(String name) {
		return innerElement.getAttribute(name);
	}

	/**
	 * <p>
	 * Returns raw text inside the element.</p>
	 *
	 * <p>
	 * If text content size exceeds allowed by internal method, error will be
	 * output, but method will continue and return empty string.</p>
	 *
	 * @return Text content as String, for example:
	 * {@code <element>Inner text</element>} returns "Inner text".
	 */
	public String getTextContent() {
		try {
			return innerElement.getTextContent();
			
		} catch (DOMException ex) {
			System.err.println(String.format("DOM exception: text content in element \"%s\" probably exceeds max allowed."
			+ "XML parsing will try to continue with empty text content",
			getTagName()));
			return "";
		}
	}

	/**
	 * Returns the raw text inside a child element.
	 * @param tagName String tag of the child element.
	 * @return The content of the specified child, or an empty string.
	 */
	public String getTextContent(String tagName) {
		return getOptionalFirstOf(tagName).map(Element::getTextContent).orElse("");
	}

	/**
	 * Returns inner element. Used to support legacy code
	 * 
	 * @return w3c DOM Element that wrapper contains
	 * @deprecated 
	 */
	@Deprecated
	public org.w3c.dom.Element getInnerElement(){
		return innerElement;
	}

	/**
	 * Returns the document. Used for legacy code
	 *
	 * @return w3c DOM Document element is in.
	 * @deprecated 
	 */
	@Deprecated
	public org.w3c.dom.Document getDocument(){
		return document;
	}
	
	/**
	 * <p>
	 * Returns first child element with a given tag, throwing a
	 * {@link XMLMissingTagException} if no such tag found. Used when
	 * document is invalid without element.</p>
	 * <p>
	 * Thrown exception provides the information for which tag in which file
	 * causes the error, so only one catch is needed to get all of the
	 * details.</p>
	 *
	 * @param tag String tag of the element to return.
	 *
	 * @return First element found with given tag
	 * @throws XMLMissingTagException
	 */
	public Element getMandatoryFirstOf(String tag) throws XMLMissingTagException {
		org.w3c.dom.Element firstElement = (org.w3c.dom.Element) innerElement.getElementsByTagName(tag).item(0);
		if (firstElement == null) {
			throw new XMLMissingTagException(tag, fileDirectory);//throw error if there's no elements found
		}
		return new Element(firstElement, fileDirectory, document);
	}

	/**
	 * <p>
	 * Returns first child element with a given tag, or empty
	 * {@link java.util.Optional} if none are found.</p>
	 * <p>
	 * Use it when document is still valid if element is missing. Use
	 * {@link java.util.Optional#ifPresent(java.util.function.Consumer)} to
	 * create blocks that are executed only when element with a given tag is
	 * found, for example: </p>
	 * <pre>
	 *{@code
	 * parentElement.getOptionalFirstOf("extraDescription").ifPresent(
	 *		(Element e) ->{
	 *		System.out.println(e.getTextContent);
	 *		}
	 * );}
	 * </pre>
	 *
	 * @param tag String tag of the element to return.
	 *
	 * @return First element found with given tag.
	 */
	public Optional<Element> getOptionalFirstOf(String tag) {
		org.w3c.dom.Element foundElement = (org.w3c.dom.Element) innerElement.getElementsByTagName(tag).item(0);
		if (foundElement == null) {
		    return Optional.empty(); //return empty Optional if there's no elements found
		}
		return Optional.of(new Element(foundElement, fileDirectory, document));
	}

	/**
	 * <p>
	 * Returns iterable list of all child elements with a given tag</p>
	 *
	 * @param tag String tag of elements to return.
	 *
	 * @return {@link java.util.List} of Elements with given tag.
	 */
	public List<Element> getAllOf(String tag) {
		org.w3c.dom.NodeList nl = innerElement.getElementsByTagName(tag);
		List<Element> returnList = new ArrayList<>();
		for (int i = 0; i < nl.getLength(); i++) {
			returnList.add(new Element((org.w3c.dom.Element) nl.item(i), this.fileDirectory, this.document));
		}
		return returnList;
	}
	
	public List<Element> getAll(){
		org.w3c.dom.NodeList nl = innerElement.getChildNodes();
		List<Element> returnList = new ArrayList<>();
		for (int i = 0; i < nl.getLength(); i++) {
			if(nl.item(i) instanceof org.w3c.dom.Element) {
				returnList.add(new Element((org.w3c.dom.Element) nl.item(i), this.fileDirectory, this.document));
			}
		}
		return returnList;
	}

	/**
	 * Iterates the {@code <item>} elements of a single {@code <collection>} element.
	 * @param collection Names a direct child of this element.
	 * @param item Classifies the elements of interest inside the {@code collection} element.
	 * @return All specified grand-child elements in document order.
	 */
	public List<Element> getAllOf(String collection, String item) {
		Optional<Element> e = getOptionalFirstOf(collection);
		return e.isEmpty() ? List.of() : e.get().getAllOf(item);
	}

	/**
	 * Tries to parse an attribute.
	 * @param key Names the attribute of this element.
	 * @param parser Transforms strings to values.
	 * @return The parsed value, if existing and valid.
	 */
	public <Value> Optional<Value> attribute(String key, Function<String, Value> parser) {
		return parseGeneric(getAttribute(key), parser);
	}

	/**
	 * Tries to parse the text content.
	 * @param parser Transforms strings to values.
	 * @return The parsed value, if existing and valid.
	 */
	public <Value> Optional<Value> text(Function<String, Value> parser) {
		return parseGeneric(getTextContent().trim(), parser);
	}

	/**
	 * Tries to parse the text content of a child element.
	 * @param tagName Classifies direct child elements.
	 * @param parser Transforms strings to values.
	 * @return The parsed value, if existing and valid.
	 */
	public <Value> Optional<Value> text(String tagName, Function<String, Value> parser) {
		return parseGeneric(getTextContent(tagName).trim(), parser);
	}

	private static <Value> Optional<Value> parseGeneric(String value, Function<String, Value> parser) {
		if(value.isEmpty())
			return Optional.empty();
		try {
			return Optional.ofNullable(parser.apply(value));
		}
		catch(RuntimeException x) {
			x.printStackTrace();
			return Optional.empty();
		}
	}

	/**
	 * Tries to parse an attribute as boolean.
	 * @param key Names the attribute of this element.
	 * @return The value is {@code "true"}, case-insensitive.
	 */
	public boolean attributeTrue(String key) {
		return Boolean.parseBoolean(getAttribute(key));
	}

	/**
	 * Tries to parse the text content as a boolean.
	 * @return The value is {@code "true"}, case-insensitive.
	 */
	public boolean textTrue() {
		return Boolean.parseBoolean(getTextContent().trim());
	}

	/**
	 * Tries to parse the text content of a child element as boolean.
	 * @param tagName Classifies direct child elements.
	 * @return The value is {@code "true"}, case-insensitive.
	 */
	public boolean textTrue(String tagName) {
		return Boolean.parseBoolean(getTextContent(tagName).trim());
	}

	/**
	 * Tries to parse an attribute as boolean.
	 * @param key Names the attribute of this element.
	 * @return The value is anything but {@code "false"}, case-insensitive.
	 */
	public boolean attributeNotFalse(String key) {
		return notFalse(getAttribute(key));
	}

	/**
	 * Tries to parse the text content as a boolean.
	 * @return The value is anything but {@code "false"}, case-insensitive.
	 */
	public boolean textNotFalse() {
		return notFalse(getTextContent().trim());
	}

	/**
	 * Tries to parse the text content of a child element as boolean.
	 * @param tagName Classifies direct child elements.
	 * @return The value is anything but {@code "false"}, case-insensitive.
	 */
	public boolean textNotFalse(String tagName) {
		return notFalse(getTextContent(tagName).trim());
	}

	private static boolean notFalse(String value) {
		return !value.isEmpty() && Boolean.parseBoolean(value);
	}

	/**
	 * Tries to parse an attribute as integer.
	 * @param key Names the attribute of this element.
	 * @return The parsed value, if existing and valid.
	 */
	public OptionalInt attributeInt(String key) {
		return parseInt(getAttribute(key));
	}

	/**
	 * Tries to parse the text content as an integer.
	 * @return The parsed value, if existing and valid.
	 */
	public OptionalInt textInt() {
		return parseInt(getTextContent().trim());
	}

	/**
	 * Tries to parse the text content of a child element as an integer.
	 * @param tagName Classifies direct child elements.
	 * @return The parsed value, if existing and valid.
	 */
	public OptionalInt textInt(String tagName) {
		return parseInt(getTextContent(tagName).trim());
	}

	private static OptionalInt parseInt(String value) {
		if(value.isEmpty())
			return OptionalInt.empty();
		try {
			return OptionalInt.of(Integer.parseInt(value));
		}
		catch(NumberFormatException x) {
			x.printStackTrace();
			return OptionalInt.empty();
		}
	}

	/**
	 * Tries to parse an attribute as floating point number.
	 * @param key Names the attribute of this element.
	 * @return The parsed value, if existing and valid.
	 */
	public OptionalDouble attributeFloat(String key) {
		return parseFloat(getAttribute(key));
	}

	/**
	 * Tries to parse the text content as a floating-point number.
	 * @return The parsed value, if existing and valid.
	 */
	public OptionalDouble textFloat() {
		return parseFloat(getTextContent().trim());
	}

	/**
	 * Tries to parse the text content of a child element as a floating-point number.
	 * @param tagName Classifies direct child elements.
	 * @return The parsed value, if existing and valid.
	 */
	public OptionalDouble textFloat(String tagName) {
		return parseFloat(getTextContent(tagName).trim());
	}

	private static OptionalDouble parseFloat(String value) {
		if(value.isEmpty())
			return OptionalDouble.empty();
		try {
			return OptionalDouble.of(Double.parseDouble(value));
		}
		catch(NumberFormatException x) {
			x.printStackTrace();
			return OptionalDouble.empty();
		}
	}
}