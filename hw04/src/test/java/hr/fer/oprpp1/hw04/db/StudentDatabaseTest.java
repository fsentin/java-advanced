package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	@Test
	public void testDuplicates() {
		assertThrows(IllegalDatabaseFormatException.class, () -> new StudentDatabase("./src/main/resources/databases/duplicateJMBAG.txt"));
	}
	
	@Test
	public void testIllegalGrade() {
		assertThrows(IllegalDatabaseFormatException.class, () -> new StudentDatabase("./src/main/resources/databases/illegalGrade.txt"));
	}
	
	@Test
	public void testIllegalJMBAG() {
		assertThrows(IllegalDatabaseFormatException.class, () -> new StudentDatabase("./src/main/resources/databases/illegalJMBAG.txt"));	
	}
	

	@Test
	public void testIllegalJMBAGThatIsString() {
		assertThrows(IllegalDatabaseFormatException.class, () -> new StudentDatabase("./src/main/resources/databases/jmbagIsString.txt"));
	}
	
	@Test
	public void testRegularDoesNotThrow() {
		assertDoesNotThrow(() -> new StudentDatabase("./src/main/resources/databases/database.txt"));
	}
	
	@Test
	public void testForJMBAG() {
		StudentDatabase db = new StudentDatabase("./src/main/resources/databases/database.txt");
		var expected = db.forJMBAG("0000000013");
		var actual = new StudentRecord("0000000013", "Gagić", "Mateja", "2");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFilter() {
		StudentDatabase db = new StudentDatabase("./src/main/resources/databases/database.txt");
		var expected = db.forJMBAG("0000000013");
		var actual = new StudentRecord("0000000013", "Gagić", "Mateja", "2");
		assertEquals(expected, actual);
	}

}
