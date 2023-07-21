package hr.fer.oprpp1.custom.scripting.nodes;
/**
 * Head node of a document.
 * @author Fani
 *
 */
public class DocumentNode extends Node {
	/**
	 * Creates a new document node.
	 */
	public DocumentNode() {
		super();
	}
	
	/**
	 * Returns String representation of this document node and its kids.
	 * @return String representation of this document node and its kids.
	 */
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < this.numberOfChildren(); i++) {
			s+= getChild(i).toString();
		}
		return s;
	}
	
}
