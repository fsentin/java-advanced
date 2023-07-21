package hr.fer.oprpp1.hw04.db;

/**
 * A class which models a conditional expression. 
 * Queries are broken up into several conditional expressions in order for the database to deliver a query.
 * @author Fani
 *
 */
public class ConditionalExpression {
	
	/**
	 * A functional interface used to fetch a field of the student record.
	 */
	private IFieldValueGetter field;
	
	/**
	 * A string literal given in a query to be checked against the fields of student records.
	 */
	private String literal;
	
	/**
	 * A functional interface used to execute a comparison between two strings.
	 */
	private IComparsionOperator operator;
	
	/**
	 * Constructs a new conditional expression with specified string literal, field getter and operator functional interfaces.
	 * @param field <code>IFieldValueGetter</code> functional interface used to fetch a field of the student record
	 * @param literal string literal given in a query to be checked against the fields of student record
	 * @param operator <code>IComparsionOperator</code> functional interface used to execute a comparison between two strings
	 */
	public ConditionalExpression(IFieldValueGetter field, String literal, IComparsionOperator operator) {
		this.field = field;
		this.literal = literal;
		this.operator = operator;
	}
	
	/**
	 * Returns the <code>IFieldValueGetter</code> of this conditional expression.
	 * @return the <code>IFieldValueGetter</code> of this conditional expression.
	 */
	public IFieldValueGetter getField() {
		return field;
	}
	
	/**
	 * Returns a string literal given in a query to be checked against the fields of student records.
	 * @return a string literal given in a query to be checked against the fields of student records.
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns <code>IComparsionOperator</code> functional interface used to execute a comparison between two strings.
	 * @return <code>IComparsionOperator</code> functional interface used to execute a comparison between two strings.
	 */
	public IComparsionOperator getOperator() {
		return operator;
	}
	
}
