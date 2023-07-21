package hr.fer.oprpp1.custom.collections.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StackDemoTest {
	
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@Test
	public void example() {
		String[] demo = {"8 -2 / -1 *"};
		StackDemo.main(demo);

	    assertEquals("4", outputStreamCaptor.toString().trim());
	}
	@Test
	public void example2() {
		String[] demo = {"-1 8 2 / +"};
		StackDemo.main(demo);

	    assertEquals("3", outputStreamCaptor.toString().trim());
	}
	@Test
	public void example3() {
		String[] demo = {"2 3 1 * + 9 -"};
		StackDemo.main(demo);

	    assertEquals("-4", outputStreamCaptor.toString().trim());
	}
	
	@Test
	public void example4() {
		String[] demo = {"1 2 3 + * 8 -"};
		StackDemo.main(demo);

	    assertEquals("-3", outputStreamCaptor.toString().trim());
	}

}
