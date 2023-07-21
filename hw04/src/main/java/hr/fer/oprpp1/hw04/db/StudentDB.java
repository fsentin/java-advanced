package hr.fer.oprpp1.hw04.db;

import java.util.List;
import java.util.Scanner;
/**
 * This class executes user queries on a given text file of student records.
 * @author Fani
 *
 */
public class StudentDB {
	
	public static void main(String[] args) {
		
		StudentDatabase db = new StudentDatabase("./src/main/resources/databases/database.txt");
		
		//scan setup
		String line; 
		QueryParser parser;
		System.out.print(">");
		
		
		try (Scanner sc = new Scanner(System.in)) {
			
			while(sc.hasNext()) {
				line = sc.nextLine();
				
				//end of program
				if(line.equals("exit")) {
					sc.close();
					System.out.println("Goodbye!");
					System.exit(0);
				}
				
				//parsing the line
				parser = new QueryParser(line);
				int numOfRecordsSelected = 0;
				
				//if it's using index
				if(parser.isDirectQuery()) {
					
					var studentRecord = db.forJMBAG(parser.getQueriedJMBAG());
					
					if(studentRecord != null) {
						System.out.println("Using index for record retrieval.");
						System.out.println(RecordFormatter.format(studentRecord));
						numOfRecordsSelected++;
					} 
					
				//if its a non direct query	
				} else {
					
					//filter
					List<StudentRecord> recordsSelected = db.filter(new QueryFilter(parser.getQuery()));
					numOfRecordsSelected = recordsSelected.size();
					
					//format
					List<String> output = RecordFormatter.format(recordsSelected);
					
					//print
					if(recordsSelected.size() != 0)
						output.forEach(System.out::println);
					
				}
				
				System.out.printf("Records selected: %d\n\n>", numOfRecordsSelected);
			}
			
		} catch (Exception e) {
			System.err.println("The query is improper!");
			e.printStackTrace();
		}
		
	}
}
