package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.math.Complex;

public class ReadingUtils {
	
	/**
	 * Parses the given string to a complex number.
	 * @param line the given string
	 * @return complex number which the given string represents.
	 */
	public static Complex parseComplex(String line) {
		if(line.isEmpty()) throw new IllegalArgumentException();
		line = line.trim();
		
		//pure real
		if(!line.contains("i")) {
			return Complex.fromReal(Double.parseDouble(line));
			
		} else {
			boolean negIm = false;
			
			//pure imaginary
			if(line.startsWith("i") || line.startsWith("-i")) {
				String substring; 
				if(line.startsWith("-")) {
					substring = line.substring(2);
					negIm = true;
				} else {
					substring = line.substring(1);
				}
				
				double im = substring.isEmpty() ? 1.0 : Double.parseDouble(substring);
				
				return Complex.fromImaginary(negIm ? -im : im);
				
			//mixed
			} else {
				int endRe;
				
				if(line.contains("+")) {
					endRe = line.indexOf("+");

				} else {
					endRe = line.indexOf("-", 1);
					negIm = true;
				}
				
				double re = Double.parseDouble(line.substring(0, endRe));
				
				String substring = line.substring(line.indexOf("i") + 1);
				double im = substring.isEmpty() ? 1.0 : Double.parseDouble(substring);
				
				return new Complex(re, negIm ? -im : im);
			}
			
		}

	}
	
	/**
	 * Reads the user's input of roots. Ends the reading when user enters "done" to the system input.
	 * Exits the program if the reading is not successfull.
	 * 
	 * @return array of complex number roots.
	 */
	public static Complex[] readInputRoots() {
		List<Complex> roots = new ArrayList<>();
		
		int index = 1; String line = "";
		try(Scanner sc = new Scanner(System.in)) {
			while(true) {
				System.out.print("Root " + index++ + "> ");

				if(sc.hasNextLine()) 
					line = sc.nextLine();
				
				if(line.equals("done")) {
					if(roots.size() > 2) {
						break;
					} else {
						System.out.println("Not enough roots provided!");
						index--; 
						continue;
					}
				}
			
				roots.add(parseComplex(line));
			}
		} catch(RuntimeException e) {
			System.out.println("The input can't be parsed to a complex number.");
			System.exit(1);
		}
		
		return (Complex[]) roots.toArray(new Complex[roots.size()]);
	}
}
