package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

public class QueryParserTest {
	
	@Test
	public void directQuery(){
		var parse = new QueryParser("query jmbag=\"0000000003\" ");
		assertTrue(parse.isDirectQuery());
		assertEquals("0000000003", parse.getQueriedJMBAG());
	}
	
	@Test
	public void directQueryParse(){
		var parse = new QueryParser("query jmbag=\"0000000003\" ");
		assertTrue(parse.isDirectQuery());
		
		ConditionalExpression c = parse.getQuery().get(0);
		assertEquals(FieldValueGetters.JMBAG, c.getField());
		assertEquals(ComparisonOperators.EQUALS, c.getOperator());
		assertEquals("0000000003", c.getLiteral());
	}
	
	@Test
	public void notDirectQuery(){
		var parse = new QueryParser("query lastName = \"Blažić\"");
		assertFalse(parse.isDirectQuery());
		//assertEquals("0000000003", parse.getQueriedJMBAG());
	}
	
	@Test
	public void notDirectQueryParse(){
		var parse = new QueryParser("query lastName = \"Blažić\"");
		
		ConditionalExpression c = parse.getQuery().get(0);
		assertEquals(FieldValueGetters.LAST_NAME, c.getField());
		assertEquals(ComparisonOperators.EQUALS, c.getOperator());
		assertEquals("Blažić", c.getLiteral());
	}
	
	@Test
	public void notDirectQueryMultiple(){
		var parse = new QueryParser("query firstName>\"A\" and lastName LIKE \"B*ć\"");
		assertFalse(parse.isDirectQuery());
	}
	
	@Test
	public void notDirectQueryMultipleThrows(){
		var parse = new QueryParser("query firstName>\"A\" and lastName LIKE \"B*ć\"");
		assertThrows(IllegalStateException.class, () -> parse.getQueriedJMBAG());
	}
	
	@Test
	public void notDirectQueryMultipleGet(){
		var parse = new QueryParser("query firstName>\"A\" and lastName LIKE \"B*ć\"");
		
		ConditionalExpression c = parse.getQuery().get(0);
		assertEquals(FieldValueGetters.FIRST_NAME, c.getField());
		assertEquals(ComparisonOperators.GREATER, c.getOperator());
		assertEquals("A", c.getLiteral());
		
		c = parse.getQuery().get(1);
		assertEquals(FieldValueGetters.LAST_NAME, c.getField());
		assertEquals(ComparisonOperators.LIKE, c.getOperator());
		assertEquals("B*ć", c.getLiteral());
		
		assertThrows(Exception.class, () -> parse.getQuery().get(2));
	}
	
	@Test
	public void notDirectQueryMultipleGet2(){
		var parse = new QueryParser("query firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		
		ConditionalExpression c = parse.getQuery().get(0);
		assertEquals(FieldValueGetters.FIRST_NAME, c.getField());
		assertEquals(ComparisonOperators.GREATER, c.getOperator());
		assertEquals("A", c.getLiteral());
		
		c = parse.getQuery().get(1);
		assertEquals(FieldValueGetters.FIRST_NAME, c.getField());
		assertEquals(ComparisonOperators.LESS, c.getOperator());
		assertEquals("C", c.getLiteral());
		
		c = parse.getQuery().get(2);
		assertEquals(FieldValueGetters.LAST_NAME, c.getField());
		assertEquals(ComparisonOperators.LIKE, c.getOperator());
		assertEquals("B*ć", c.getLiteral());
		
		c = parse.getQuery().get(3);
		assertEquals(FieldValueGetters.JMBAG, c.getField());
		assertEquals(ComparisonOperators.GREATER, c.getOperator());
		assertEquals("0000000002", c.getLiteral());
		
	}
	
	@Test
	public void notDirectQueryButContainsOne(){
		var parse = new QueryParser("query jmbag = \"0000000003\" AND lastName LIKE \"B*\"");
		
		assertFalse(parse.isDirectQuery());
		
		ConditionalExpression c = parse.getQuery().get(0);
		assertEquals(FieldValueGetters.JMBAG, c.getField());
		assertEquals(ComparisonOperators.EQUALS, c.getOperator());
		assertEquals("0000000003", c.getLiteral());
		
		c = parse.getQuery().get(1);
		assertEquals(FieldValueGetters.LAST_NAME, c.getField());
		assertEquals(ComparisonOperators.LIKE, c.getOperator());
		assertEquals("B*", c.getLiteral());
		
	}
	
	

}
