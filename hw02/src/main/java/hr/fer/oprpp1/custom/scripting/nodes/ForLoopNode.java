package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
/**
 * Represents a for loop.
 * @author Fani
 *
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 * Creates a new ForLoopNode with specified params:
	 * @param variable 
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	public ElementVariable getVariable() {
		return variable;
	}
	public Element getStartExpression() {
		return startExpression;
	}
	public Element getEndExpression() {
		return endExpression;
	}
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * Returns String representation of this node and its elements.
	 * @return String representation of this node and its elements.
	 */
	@Override
	public String toString() {
		String s;
		s = "{$ FOR " + variable.asText() + " " + startExpression.asText() + " " + endExpression.asText();
		if(stepExpression != null){
			s += " " + stepExpression.asText();
		}
		s += " $}";
		
		for(int i = 0; i < this.numberOfChildren(); i++) {
			s+= getChild(i).toString();
		}
		
		s += "{$ END $}";
		
		return s;
	}
	
}
