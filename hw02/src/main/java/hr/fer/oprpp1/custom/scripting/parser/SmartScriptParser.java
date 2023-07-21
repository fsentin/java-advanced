package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;
/**
 * A parser. Finally implemented.
 *
 * @author Fani
 *
 */
public class SmartScriptParser {
	
	/**
	 * Lexer for the tokenization of text.
	 */
	private SmartScriptLexer tokenizer;
	
	/**
	 * Reference to the head node of this document.
	 */
	private DocumentNode tree;
	
	/**
	 * Parses the given input into tags and text.
	 * @param input the input to be parsed
	 * @throws SmartScriptParserException if the input string can't be parsed.
	 */
	public SmartScriptParser(String input) {
		try {
			if(input == null) throw new NullPointerException();
			
			tokenizer = new SmartScriptLexer(input);
			tree = parse();
			
		} catch (RuntimeException e) {
			throw new SmartScriptParserException("Cannot parse :(");
		}
	}
	
	/**
	 * Returns the head node of the document.
	 * @return the head node of the document.
	 */
	public DocumentNode getDocumentNode() {
		return tree;
	} 
	
	/**
	 * Actually parses the input text.
	 * @return the DocumentNode of the document.
	 */
	private DocumentNode parse() {
		
		ObjectStack o = new ObjectStack();
		tree = new DocumentNode();
		
		o.push(tree);
		
		SmartToken current = tokenizer.nextToken();
		
		while(current.getType() != SmartTokenType.EOF) {
			
			// A TEXT NODE
			if(current.getType() == SmartTokenType.STRING) {
				TextNode text = new TextNode(current.getValue().toString());
				Node parent = (Node) o.peek();
				parent.addChildNode(text);
			}
			
			//A TAG NODE
			if(current.getType() == SmartTokenType.OPEN_TAG) {

				current = tokenizer.nextToken();
				
				if(current.getType() == SmartTokenType.EOF) {
					throw new SmartScriptParserException("The tag hasn't been closed in this file!");
						
				//} else if(current.getType() == SmartTokenType.CLOSE_TAG) {
				
				// AN EMPTY TAG - ECHO NODE
				} else if(current.getType() == SmartTokenType.SYMBOL && current.getValue().toString().equals("=")) {
					
					current = tokenizer.nextToken();
					
					var elements = new ArrayIndexedCollection();
					Element newElement = null;
					
					while(current.getType() != SmartTokenType.CLOSE_TAG) {
						
						newElement = createElement(current);
						elements.add(newElement);
						
						current = tokenizer.nextToken();
					}
					
					Element[] arrayOfElems = new Element[elements.size()];
					var collection =  elements.toArray();
					for (int i = 0; i < arrayOfElems.length; i++) {
						arrayOfElems[i] = (Element) collection[i];
					}
					
					EchoNode echo = new EchoNode(arrayOfElems);//(Element[]) elements.toArray());
					Node parent = (Node) o.peek();
					parent.addChildNode(echo);
					
				//FOR TAG
				} else if(current.getType() == SmartTokenType.IDENTIFICATOR && current.getValue().toString().equalsIgnoreCase("FOR")) {
					
					current = tokenizer.nextToken();
					
					if(current.getType() != SmartTokenType.IDENTIFICATOR) 
						throw new SmartScriptParserException("The first argument inside the for loop should be a variable!");
					
					ElementVariable variable = (ElementVariable) createElement(current);
					
					current = tokenizer.nextToken();
					SmartTokenType type = current.getType();
					if(!(type == SmartTokenType.IDENTIFICATOR
							|| type == SmartTokenType.DOUBLE
							|| type == SmartTokenType.INTEGER
							|| type == SmartTokenType.STRING))
						throw new SmartScriptParserException("Wrong startExpression of the for loop given!");
					
					Element startExpression = createElement(current);
					
					current = tokenizer.nextToken();
					type = current.getType();
					if(!(type == SmartTokenType.IDENTIFICATOR
							|| type == SmartTokenType.DOUBLE
							|| type == SmartTokenType.INTEGER
							|| type == SmartTokenType.STRING))
						throw new SmartScriptParserException("Wrong endExpression of the for loop given!");
					
					Element endExpression = createElement(current);
					
					current = tokenizer.nextToken();
					type = current.getType();
					if(!(type == SmartTokenType.IDENTIFICATOR
							|| type == SmartTokenType.DOUBLE
							|| type == SmartTokenType.INTEGER
							|| type == SmartTokenType.STRING) &&  ! (type == SmartTokenType.CLOSE_TAG)) 
						throw new SmartScriptParserException("Wrong for loop stepExpression!");
					
					Element stepExpression = null;
					if(type != SmartTokenType.CLOSE_TAG) {
						stepExpression = createElement(current);
					}
				
					
					
					ForLoopNode loop = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
					
					Node parent = (Node) o.peek();
					parent.addChildNode(loop);
					o.push(loop);
					
				} else if(current.getType() == SmartTokenType.IDENTIFICATOR && current.getValue().toString().equalsIgnoreCase("END")) {
					o.pop();
					current = tokenizer.nextToken();
					
					if(current.getType() != SmartTokenType.CLOSE_TAG) 
						throw new SmartScriptParserException("Unclosed END tag!");
					
				}
				
			} 
			
			current = tokenizer.nextToken();
		}
		
		return tree;
	}
	
	/**
	 * Creates a new Element according to the type of the current token.
	 * @param current the current token.
	 * @return a new Element of type that is in correlation with the type of the current token.
	 * @throws SmartScriptParserException if the type of the current token is such that is not allowed.
	 */
	private Element createElement(SmartToken current) {
		Element newElement;
		switch(current.getType()) {
			case DOUBLE:
					newElement =  new ElementConstantDouble((double) current.getValue());
					break;
					
			case INTEGER: 
					newElement =  new ElementConstantInteger((int) current.getValue());
					break;
					
			case FUNCTION:
					newElement =  new ElementFunction(current.getValue().toString());
					break;
					
			case SYMBOL:
					newElement =  new ElementOperator(current.getValue().toString());
					break;
					
			case STRING:
					newElement =  new ElementString(current.getValue().toString());
					break;
					
			case IDENTIFICATOR:
					newElement =  new ElementVariable(current.getValue().toString());
					break;
					
			default:  throw new SmartScriptParserException("This type is incorrect at this place in the document!");
		}
		
		return newElement;
	}
	
}
