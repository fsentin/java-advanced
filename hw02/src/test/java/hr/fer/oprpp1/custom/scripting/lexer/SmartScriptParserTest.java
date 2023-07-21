package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import hr.fer.oprpp1.custom.scripting.nodes.Node;


public class SmartScriptParserTest {
	
	@Test 
	public void primjer1() {
		String text = readExample(1);
		var parser = new SmartScriptParser(text);
		
		Node document = parser.getDocumentNode();
		assertEquals(1, document.numberOfChildren());
	}
	
	@Test 
	public void primjer2() {
		String text = readExample(2);
		var parser = new SmartScriptParser(text);
		
		DocumentNode document = parser.getDocumentNode();
		assertEquals(1, document.numberOfChildren());
	}
	
	@Test 
	public void primjer3() {
		String text = readExample(3);
		var parser = new SmartScriptParser(text);
		
		DocumentNode document = parser.getDocumentNode();
		assertEquals(1, document.numberOfChildren());
	}
	
	@Test 
	public void primjer4() {
		String text = readExample(4);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test 
	public void primjer5() {
		String text = readExample(5);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test 
	public void primjer6() {
		String text = readExample(6);
		var parser = new SmartScriptParser(text);
		
		DocumentNode document = parser.getDocumentNode();
		assertEquals(1, document.numberOfChildren());
	}
	
	@Test 
	public void primjer7() throws IOException {
		//wouldn't work with readExample for some unknown reason.
		String text = new String(
				 Files.readAllBytes(Paths.get("./src/main/resources/extra/primjer7.txt")), 
				 StandardCharsets.UTF_8
				);
		var parser = new SmartScriptParser(text);
		DocumentNode document = parser.getDocumentNode();
		assertEquals(3, document.numberOfChildren());
	}
	
	@Test 
	public void primjer8() throws IOException {
		//wouldn't work with readExample for some unknown reason.
		String text = new String(
				 Files.readAllBytes(Paths.get("./src/main/resources/extra/primjer8.txt")), 
				 StandardCharsets.UTF_8
				);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test 
	public void primjer9() {
		String text = readExample(9);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
	}

}
