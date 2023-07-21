package hr.fer.oprpp1.hw04.db;

public class Main {

	public static void main(String[] args) {
		Lexer l = new Lexer("query jmbag=\"0000000003\" or ");
		System.out.println(l.nextToken());
		while(l.getToken().getType()!=TokenType.EOF) {
			System.out.println(l.nextToken());
		}

	}

}
