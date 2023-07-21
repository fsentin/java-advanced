package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Node which contains text.
 * @author Fani
 *
 */
public class TextNode extends Node {
	/**
	 * String of text that this TextNode contains.
	 */
	private String text;

	/**
	 * Creates a new TextNode with given text.
	 * @param text given string of text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	/**
	 * Returns String representation of this node.
	 * @return String representation of this node.
	 */
	@Override
	public String toString() {
		return text;
	}

}
