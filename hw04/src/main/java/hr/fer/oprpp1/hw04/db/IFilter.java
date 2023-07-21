package hr.fer.oprpp1.hw04.db;

/**
 * Used to filter student records according to a certain condition.
 * @author Fani
 *
 */
@FunctionalInterface
public interface IFilter {
	/**
	 * Accepts a student record.
	 * @param record
	 * @return <code>true</code> if the filter accepts it, <code>false</code> otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
