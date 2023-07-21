package hr.fer.oprpp1.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter {
	/**
	 * List of conditional expressions of a certain query.
	 */
	private List<ConditionalExpression> conds;
	
	/**
	 * Constructs a new filter that is used to filter student records according to the list of conditional expressions. 
	 * @param conds list of conditional expressions which are used to filter the database.
	 */
	public QueryFilter(List<ConditionalExpression> conds) {
		this.conds = conds;
	}
	
	/**
	 * Returns true if a student record satisfies all conditional expressions of a certain query.
	 * @return <code>true</code> if a student record satisfies all conditional expressions of a certain query, <code>false</code> otherwise.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		int satisfiedConds = 0; 
		
		for (ConditionalExpression exp : conds) {
			var f = exp.getField();
			var o = exp.getOperator();
			var l = exp.getLiteral();
			
			if(o.satisfied(f.get(record), l))
				satisfiedConds++;
		}
		
		return satisfiedConds == conds.size() ? true : false;
	}
	
}
