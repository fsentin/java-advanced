package hr.fer.oprpp1.hw02.prob1;

//import static java.lang.Character.isDigit;

public class Main {

	public static void main(String[] args) {
		Lexer lexer = new Lexer("  ab\\123cd ab\\2\\1cd\\4\\\\ \r\n\t   ");
		
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		
	}
	
	

	
}
