package hr.fer.oprpp1.hw04.db;
/**
 * Fetches a field of a student record.
 * @author Fani
 *
 */
@FunctionalInterface
public interface IFieldValueGetter {
	
	public String get(StudentRecord record);
	
}
