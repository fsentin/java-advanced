package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LexerTest {
	
	@Test
	public void example(){
		Lexer l = new Lexer("query jmbag=\"0000000003\" ");
		
		Token[] tokens = new Token[5];
		tokens[0] = new Token(TokenType.QUERY, "query");
		tokens[1] = new Token(TokenType.ATTRIBUTE, "jmbag");
		tokens[2] = new Token(TokenType.OPERATOR, "=");
		tokens[3] = new Token(TokenType.STRING_LITERATOR, "0000000003");
		tokens[4] = new Token(TokenType.EOF, null);
		
		Token t = l.nextToken();
		assertEquals(tokens[0], t);
		
		t = l.nextToken();
		assertEquals(tokens[1], t);
		
		t = l.nextToken();
		assertEquals(tokens[2], t);
		
		t = l.nextToken();
		assertEquals(tokens[3], t);
		
		t = l.nextToken();
		assertEquals(tokens[4], t);
		
	}
	
	@Test
	public void example2(){
		Lexer l = new Lexer("query lastName = \"Blažić\"");
		
		Token[] tokens = new Token[5];
		tokens[0] = new Token(TokenType.QUERY, "query");
		tokens[1] = new Token(TokenType.ATTRIBUTE, "lastName");
		tokens[2] = new Token(TokenType.OPERATOR, "=");
		tokens[3] = new Token(TokenType.STRING_LITERATOR, "Blažić");
		tokens[4] = new Token(TokenType.EOF, null);
		
		Token t = l.nextToken();
		assertEquals(tokens[0], t);
		
		t = l.nextToken();
		assertEquals(tokens[1], t);
		
		t = l.nextToken();
		assertEquals(tokens[2], t);
		
		t = l.nextToken();
		assertEquals(tokens[3], t);
		
		t = l.nextToken();
		assertEquals(tokens[4], t);
		
	}
	
	@Test
	public void example3(){
		Lexer l = new Lexer("query firstName>\"A\" and lastName LIKE \"B*ć\"");
		
		Token[] tokens = new Token[8];
		tokens[0] = new Token(TokenType.QUERY, "query");
		tokens[1] = new Token(TokenType.ATTRIBUTE, "firstName");
		tokens[2] = new Token(TokenType.OPERATOR, ">");
		tokens[3] = new Token(TokenType.STRING_LITERATOR, "A");
		tokens[4] = new Token(TokenType.AND, "and");
		tokens[5] = new Token(TokenType.ATTRIBUTE, "lastName");
		tokens[6] = new Token(TokenType.OPERATOR, "LIKE");
		tokens[7] = new Token(TokenType.STRING_LITERATOR, "B*ć");
		
		Token t;
		for(int i = 0; i < 8; i++) {
			t = l.nextToken();
			assertEquals(tokens[i], t);
		}
		
	}
	
	@Test
	public void example4(){
		Lexer l = new Lexer("query firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		
		Token[] tokens = new Token[16];
		tokens[0] = new Token(TokenType.QUERY, "query");
		tokens[1] = new Token(TokenType.ATTRIBUTE, "firstName");
		tokens[2] = new Token(TokenType.OPERATOR, ">");
		tokens[3] = new Token(TokenType.STRING_LITERATOR, "A");
		tokens[4] = new Token(TokenType.AND, "and");
		tokens[5] = new Token(TokenType.ATTRIBUTE, "firstName");
		tokens[6] = new Token(TokenType.OPERATOR, "<");
		tokens[7] = new Token(TokenType.STRING_LITERATOR, "C");
		tokens[8] = new Token(TokenType.AND, "and");
		tokens[9] = new Token(TokenType.ATTRIBUTE, "lastName");
		tokens[10] = new Token(TokenType.OPERATOR, "LIKE");
		tokens[11] = new Token(TokenType.STRING_LITERATOR, "B*ć");
		tokens[12] = new Token(TokenType.AND, "and");
		tokens[13] = new Token(TokenType.ATTRIBUTE, "jmbag");
		tokens[14] = new Token(TokenType.OPERATOR, ">");
		tokens[15] = new Token(TokenType.STRING_LITERATOR, "0000000002");
		
		
		Token t;
		for(int i = 0; i < 16; i++) {
			t = l.nextToken();
			assertEquals(tokens[i], t);
		}
	}
	
	@Test
	public void exampleException() {
		Lexer l = new Lexer(";");
		assertThrows(IllegalArgumentException.class, () -> l.nextToken());
	}

}
