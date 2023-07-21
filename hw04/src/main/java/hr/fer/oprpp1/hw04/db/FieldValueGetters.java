package hr.fer.oprpp1.hw04.db;

/**
 * A class of final static functional <code>IFieldValueGetter</code> interfaces.
 * <p>Includes:</p>
 * <p>FIRST_NAME</p>
 * <p>LAST_NAME</p>
 * <p>JMBAG</p>
 * @author Fani
 *
 */
public class FieldValueGetters {
	
	/**
	 * An <code>IFieldValueGetter</code> functional interface.
	 * Using a function <code>get</code>, fetches the first name of a student record.
	 */
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	
	/**
	 * An <code>IFieldValueGetter</code> functional interface.
	 * Using a function <code>get</code>, fetches the last name of a student record.
	 */
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
	
	/**
	 * An <code>IFieldValueGetter</code> functional interface.
	 * Using a function <code>get</code>, fetches the jmbag of a student record.
	 */
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();
	
}
