package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Draws a fractal with a single thread for given base polynomial.
 * @author Fani
 *
 */
public class Newton {
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		Complex[] roots = ReadingUtils.readInputRoots();
		var polynomial = new ComplexRootedPolynomial(Complex.ONE, roots);
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MyProducer(polynomial));
	}
	
	
	/**
	 * Newton-Raphson iteration-based fractal producer.
	 * @author Fani
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		/**
		 * Polynomial in root based notation of a complex number.
		 */
		ComplexRootedPolynomial rooted;
		
		/**
		 * Polynomial in order based notation of a complex number.
		 */
		ComplexPolynomial polynomial;
		
		/**
		 * First derivation of the polynomial for this fractal producer.
		 */
		ComplexPolynomial derived;
		
		/**
		 * Creates a new Newton-Rahpson fractal producer.
		 * @param rooted the polynom of this fractal producer in root based notation.
		 */
		public MyProducer(ComplexRootedPolynomial rooted) {
			this.rooted = rooted;
			this.polynomial = rooted.toComplexPolynom();
			this.derived = polynomial.derive();
		}
		
		/**
		 * The method will be called whenever fractal data needs to be generated. 
		 * The data is expected in the form of a field of short numbers that has exactly as many elements as the raster has image elements (width * height). 
		 * When returning the results, it is necessary to return the data to the submitted observer object, stating the request identifier that the method received.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			int offset = 0;
			
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					double module = 0;
					int iter = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;
					} while(iter < m && module > 0.001);
					
					int index = rooted.indexOfClosestRootFor(zn, 0.001);
					data[offset++] = (short) (index + 1);
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}

}
