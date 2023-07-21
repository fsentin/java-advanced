package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



public class SmartScriptLexerTest {
	
	

		@Test
		public void testComplex() {
			SmartScriptLexer l = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$\"hihihi\" =@b_l 3.4444 \"sad quote\\\"je li ovo dobro?\"$}");
			String s = "=";
			// We expect the following stream of tokens
			SmartToken correctData[] = {
				new SmartToken(SmartTokenType.STRING, "Example {$=1$}. Now actually write one "),
				new SmartToken(SmartTokenType.OPEN_TAG, "{$"),   
				new SmartToken(SmartTokenType.STRING, "hihihi"),
				new SmartToken(SmartTokenType.SYMBOL, s.toCharArray()[0]),
				new SmartToken(SmartTokenType.FUNCTION, "b_l"),
				new SmartToken(SmartTokenType.DOUBLE, Double.valueOf(3.4444)),
				new SmartToken(SmartTokenType.STRING, "sad quote\"je li ovo dobro?"),
				new SmartToken(SmartTokenType.CLOSE_TAG, "$}"),
				new SmartToken(SmartTokenType.EOF, null)
			};

			checkTokenStream(l, correctData);
		}
		
		@Test
		public void testFromFerko() {
			SmartScriptLexer l = new SmartScriptLexer("{$FOR brojevi -1\"pet\" 5 $}");
			// We expect the following stream of tokens
			SmartToken correctData[] = {
				new SmartToken(SmartTokenType.OPEN_TAG, "{$"),   
				new SmartToken(SmartTokenType.IDENTIFICATOR, "FOR"),
				new SmartToken(SmartTokenType.IDENTIFICATOR, "brojevi"),
				new SmartToken(SmartTokenType.INTEGER, Integer.valueOf(-1)),
				new SmartToken(SmartTokenType.STRING, "pet"),
				new SmartToken(SmartTokenType.INTEGER, Integer.valueOf(5)),
				new SmartToken(SmartTokenType.CLOSE_TAG, "$}"),
				new SmartToken(SmartTokenType.EOF, null)
			};

			checkTokenStream(l, correctData);
		}
		
		private void checkTokenStream(SmartScriptLexer lexer, SmartToken[] correctData) {
			int counter = 0;
			for(SmartToken expected : correctData) {
				SmartToken actual = lexer.nextToken();
				String msg = "Checking token "+counter + ":";
				assertEquals(expected.getType(), actual.getType(), msg);
				assertEquals(expected.getValue(), actual.getValue(), msg);
				counter++;
			}
		}
}
