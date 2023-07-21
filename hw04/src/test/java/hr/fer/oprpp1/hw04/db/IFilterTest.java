package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class IFilterTest {
	
	@Test
	public void filterAcceptsAll() {
		StudentDatabase db = new StudentDatabase("./src/main/resources/databases/database.txt");
		IFilter filter = new IFilter() {
			
			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
		};
		
		List<StudentRecord> filtered = db.filter(filter);
		assertEquals(63, filtered.size());
	}
	
	@Test
	public void filterAcceptsNone() {
		StudentDatabase db = new StudentDatabase("./src/main/resources/databases/database.txt");
		IFilter filter = new IFilter() {
			
			@Override
			public boolean accepts(StudentRecord record) {
				return false;
			}
		};
		
		List<StudentRecord> filtered = db.filter(filter);
		assertEquals(0, filtered.size());
	}

}
