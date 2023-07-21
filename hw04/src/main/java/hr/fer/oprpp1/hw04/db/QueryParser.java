package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * A model of a parser for queries of a student database.
 * @author Fani
 *
 */
public class QueryParser {
	
	/**
	 * The internal lexer which this parser uses for tokenization.
	 */
	private Lexer tokenizer;
	
	/**
	 * List of tokens produced by the lexer.
	 */
	private List<Token> tokens; 
	
	/**
	 * Constructs a parser for a query.
	 * @param query string to be parsed
	 */
	public QueryParser(String query) {
		tokens = new ArrayList<>();
		
		tokenizer = new Lexer(query);
		
		Token token = tokenizer.nextToken();
		
		while(tokenizer.getToken().getType() != TokenType.EOF) {
			tokens.add(token);
			token = tokenizer.nextToken();
		}
		
	}
	
	/**
	 * Determines if a query is direct.
	 * @return <code>true</code> if it is a direct query, <code>false</code> otherwise.
	 * 
	 */
	public boolean isDirectQuery(){
		return tokens.get(0).getType() == TokenType.QUERY
				&& tokens.get(1).getValue().equals("jmbag")
				&& tokens.get(2).getValue().equals("=")
				&& tokens.get(3).getType() == TokenType.STRING_LITERATOR
				&& tokens.size() == 4;
	}
	
	/**
	 * Returns a jmbag of the current query if a query is direct.
	 * @return jmbag of the current query if a query is direct.
	 * @throws IllegalStateException if the query is not direct.
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery())
			throw new IllegalStateException("It is not a direct query");
		return tokens.get(3).getValue();
	}
	
	/**
	 * Returns a list of conditional expressions from the query.
	 * @return list of conditional expressions from the query.
	 * 
	 */
	public List<ConditionalExpression> getQuery(){
		var result = new ArrayList<ConditionalExpression>();
		if(!tokens.get(0).getValue().equals("query"))
			throw new IllegalArgumentException();
		
		for (int j = 1; j < tokens.size(); j = j + 4) {
			var t1 = tokens.get(j);
			var t2 = tokens.get(j + 1);
			var t3 = tokens.get(j + 2);
			if(t1.getType() == TokenType.ATTRIBUTE && ( t1.getValue().equals("firstName") ||  t1.getValue().equals("lastName") ||  t1.getValue().equals("jmbag"))
			&& t2.getType() == TokenType.OPERATOR
			&& t3.getType() == TokenType.STRING_LITERATOR) 
				result.add(new ConditionalExpression(determineField(t1.getValue()), t3.getValue(), determineOperator(t2.getValue())));
			if((j + 3 < tokens.size()) && j + 4 < tokens.size() && tokens.get(j + 3).getType() == TokenType.AND)
				continue;
			else if(j + 3 < tokens.size() && j + 4 >= tokens.size())
				throw new IllegalArgumentException();
			break;
		}
		
		return result;
	}
	
	/**
	 * Returns the field getter based on the given string. 
	 * @param field the given string
	 * @return the field getter based on the given string.
	 */
	private IFieldValueGetter determineField(String field) {
		switch(field) {
		case "firstName": return FieldValueGetters.FIRST_NAME;
		case "lastName": return FieldValueGetters.LAST_NAME;
		case "jmbag": return FieldValueGetters.JMBAG;
		default:  return FieldValueGetters.JMBAG;
		}
	}
	
	/**
	 * Returns the comparison operator lambda based on the given string.
	 * @param operator the given string.
	 * @return the comparison operator lambda based on the given string.
	 */
	private IComparsionOperator determineOperator(String operator) {
		switch(operator) {
		case "<": return ComparisonOperators.LESS;
		case "<=": return ComparisonOperators.LESS_OR_EQUALS;
		case ">": return ComparisonOperators.GREATER;
		case ">=": return ComparisonOperators.GREATER_OR_EQUALS;
		case "=": return ComparisonOperators.EQUALS;
		case "!=": return ComparisonOperators.NOT_EQUALS;
		case "LIKE": return ComparisonOperators.LIKE;
		default: return ComparisonOperators.EQUALS;
		}
	}

}
