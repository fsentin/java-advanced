package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Database model for storage of student records.
 * @author Fani
 *
 */
public class StudentDatabase {
	/**
	 * A list of lines inside the database
	 */
	private List<String> lines;
	
	/**
	 * A list of all student records.
	 */
	private List<StudentRecord> records;
	
	/**
	 * A map used for fast retrieval of a student record using jmbag.
	 */
	private Map<String,StudentRecord> index;
	
	/**
	 * Constructs a database from a formatted file.
	 * @param file
	 * @throws IllegalDatabaseFormatException if the database is in a wrong format
	 */
	public StudentDatabase(String file) {
		records = new ArrayList<StudentRecord>();
		index = new HashMap<>();
		
		initialize(file);
		String[] lineArray;
		
		for (String line : lines) {
			lineArray = line.split("\t");
			checkInputDB(lineArray);
			
			var newRecord =  new StudentRecord(lineArray[0], lineArray[1], lineArray[2], lineArray[3]);
			
			if(records.contains(newRecord))
				throw new IllegalDatabaseFormatException("Duplicates!");
			
			records.add(newRecord);
			index.put(lineArray[0], newRecord);
		}
	}
	
	/**
	 * Initializes the database by reading from a file.
	 * @param file
	 * @throws IOException
	 */
	private void initialize(String file) {
		try {
			lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a student record with a specified jmbag.
	 * @param jmbag the jmbag of a wanted student
	 * @return the student whose jmbag is specified or <code>null</code> if there is no such.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	/**
	 *  Loops through all student records in its internal list; it calls 
	 *  accepts method on given filter-object with current record; each record for which accepts returns true is
	 *  added to temporary list and this list is then returned by the filter method
	 * @param filter the filter by which the filtering is performed
	 * @return the list of students who pass through the filter
	 */
	public List<StudentRecord> filter(IFilter filter){
		var result = new ArrayList<StudentRecord>();
		
		for (StudentRecord studentRecord : records) {
			if(filter.accepts(studentRecord))
				result.add(studentRecord);
		}
	
		return result;
	}

	/**
	 * Checks if a line in the database is in the acceptable format.
	 * @param lineArray a line that is checked
	 * @throws IllegalDatabaseFormatException if the line is not acceptable
	 */
	private void checkInputDB(String[] lineArray) {
		
		//check if there are all parameters
		if(lineArray.length != 4)
			throw new IllegalDatabaseFormatException("Number of arguments in a record is illegal!");
		
		//check the jmbag
		if(lineArray[0].length() > 10  || lineArray[0].length() < 10 || !lineArray[0].matches("[0-9]+"))
			throw new IllegalDatabaseFormatException("The given JMBAG is illegal!");
		
		//check the grade
		if(!insideAllowedGradeRange(lineArray[3]))
			throw new IllegalDatabaseFormatException("The grade is illegal!");
	}
	
	/**
	 * Checks if a string is inside the allowed grade range: [1, 5].
	 * @param c the value of a grade that is tested if inside the allowed range 
	 * @return <code>true</code> if a grade is inside the allowed range, <code>false</code> otherwise.
	 */
	private boolean insideAllowedGradeRange(String c) {
		return c.equals("1") || c.equals("2") || c.equals("3") || c.equals("4") || c.equals("5"); 
	}
	
}
