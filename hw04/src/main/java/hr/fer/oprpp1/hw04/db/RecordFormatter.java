package hr.fer.oprpp1.hw04.db;

import java.util.LinkedList;
import java.util.List;

public class RecordFormatter {
	
	/**
	 * Formats a string of one student record.
	 * @param student the student record to be formatted
	 * @return a string in a shape of a table for the given student record.
	 */
	public static String format(StudentRecord student) {
		String result = "";
		var tableLine = tableLine(student.getLastName().length(), student.getFirstName().length());
		
		result += tableLine+ "\n"; 
		
		result += "| " 
		  + student.getJmbag() 
		  + " | " 
		  + student.getLastName() 
		  + " | " 
		  + student.getFirstName() 
		  + " | " 
		  + student.getFinalGrade() 
		  + " |\n"; 
		
		result += tableLine;
		
		return result;
	}
	
	/**
	 * Formats a list of student records.
	 * @param records the list of student records to be formatted
	 * @return a list of strings that shape a table for the given student records.
	 */
	public static List<String> format(List<StudentRecord> records){
		int longestFirstName = 0;
		int longestLastName = 0;
		
		for (StudentRecord student : records) {
			if(student.getFirstName().length() > longestFirstName)
				longestFirstName = student.getFirstName().length();
			
			if(student.getLastName().length() > longestLastName)
				longestLastName = student.getLastName().length();
		}
		
		List<String> strings = new LinkedList<>();
		
		var tableLine = tableLine(longestLastName, longestFirstName); 

		strings.add(tableLine);
		
		for (StudentRecord student : records) {
			String studentString  = "";
			studentString += "| "
						   + student.getJmbag() 
						   + " | "
						   + student.getLastName() 
						   + spaces(longestLastName - student.getLastName().length()) 
						   + " | " 
						   + student.getFirstName() + spaces(longestFirstName - student.getFirstName().length()) 
						   + " | "
						   + student.getFinalGrade() 
						   + " |";
			
			strings.add(studentString);
		}
		
		strings.add(tableLine);
		
		return strings;
	}
	
	/**
	 * Produces a line for the table of student records depending on the length of the given record(s). 
	 * @param lastNameLen length of the last name of an authoritative record
	 * @param firstNameLen length of the first name of an authoritative record
	 * @return a line for the table of student records depending on the length of the given record(s).
	 */
	private static String tableLine(int lastNameLen, int firstNameLen) {
		String result = "";
		
		result += "+" 
		       + tableLineHelper(12) 
		       + tableLineHelper(lastNameLen+2) 
		       + tableLineHelper(firstNameLen+2) 
		       + tableLineHelper(3);
		
		return result;
	}
	
	/**
	 * Produces a string of a given number of "=" characters and one "+" character at the end.
	 * @param length number of "=" characters in the resulting string
	 * @return
	 */
	private static String tableLineHelper(int length) {
		String result = "";
		
		for(int i = 0; i < length; i++) 
			result += "=";
		
		return result + "+";
	}
	
	/**
	 * Produces a string with the specified number of spaces.
	 * @param num number of wanted spaces
	 * @return a string with the specified number of spaces.
	 */
	private static String spaces(int num) {
		String result = "";
		
		for (int i = 0; i < num; i++) 
			result += " ";
		
		return result;
	}
	
}
