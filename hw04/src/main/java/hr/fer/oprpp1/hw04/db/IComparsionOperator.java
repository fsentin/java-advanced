package hr.fer.oprpp1.hw04.db;
/**
 * Compares strings and delivers information if the comparison is satisfied or not.
 * @author Fani
 *
 */
@FunctionalInterface
public interface IComparsionOperator {

	public boolean satisfied(String value1, String value2);
	
}
