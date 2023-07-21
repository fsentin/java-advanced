package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * A structure used in parsing text.
 * @author Fani
 *
 */
public class Node {
	
	private ArrayIndexedCollection childNodes = null;
	
	/**
	 * Adds given child to an internally managed collection of children.
	 * 
	 * @param child the child to be added
	 */
	public void addChildNode(Node child) {
		if(childNodes == null) childNodes = new ArrayIndexedCollection();
		
		childNodes.add(child);
	}
	
	/**
	 * Returns a number of direct children.
	 * 
	 * @return number of direct children nodes.
	 */
	public int numberOfChildren() {
		return childNodes.size();
	}
	
	/**
	 * Returns selected child or throws an appropriate exception if the index is invalid.
	 * 
	 * @param index the index of the wanted child
	 * @return child node at the specified index.
	 * @throws IndexOutOfBoundsExcepion if the index is out of bounds
	 */
	Node getChild(int index) {
		return (Node) childNodes.get(index);
	}
	
	
}
