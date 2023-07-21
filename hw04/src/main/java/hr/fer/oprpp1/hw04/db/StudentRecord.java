package hr.fer.oprpp1.hw04.db;

/**
 * A model of a student record.
 * @author Fani
 *
 */
public class StudentRecord {

	/**
	 * Identificator of a student.
	 */
	private String jmbag;
	
	/**
	 * 	The last name of a student.
	 */
	private String lastName;
	
	/**
	 * The first name of a student.
	 */
	private String firstName;
	
	/**
	 * The grade of a student.
	 */
	private String finalGrade;
	
	/**
	 * Constructs a student record with given parameters.
	 * @param jmbag identificator of a student
	 * @param lastName the last name of a student
	 * @param firstName the first name of a student
	 * @param finalGrade the grade of a student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Two students are equal if their jmbags are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	/**
	 * Returns the jmbag of a student.
	 * @return the jmbag of a student.
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Returns the last name of a student.
	 * @return the last name of a student.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Returns the first name of a student.
	 * @return the first name of a student.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Returns the grade of a student.
	 * @return the grade of a student.
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Produces a string representation of a student record.
	 * @return string representing a student record.
	 */
	@Override
	public String toString() {
		return  jmbag + " " + lastName + " " + firstName + " " + finalGrade;
	}

}
