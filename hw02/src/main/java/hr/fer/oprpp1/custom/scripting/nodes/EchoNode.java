package hr.fer.oprpp1.custom.scripting.nodes;
import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * A node with sub elements. 
 * @author Fani
 *
 */
public class EchoNode extends Node {
	
	/**
	 * An array of elements that belong to this node.
	 */
	private Element[] elements;

	/**
	 * Creates a new EchoNode with specified array of elements.
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * Returns an array of elements that belong to this node.
	 * @return an array of elements that belong to this node.
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Returns String representation of this document node and its elements.
	 * @return String representation of this document node and its elements.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < elements.length; i++) {
			s += elements[i].asText();
		}
		
		return "{$ = " + s + " $}";
	}
	
	
}
