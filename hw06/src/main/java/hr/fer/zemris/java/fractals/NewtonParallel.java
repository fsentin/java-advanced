package hr.fer.zemris.java.fractals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.mandelbrot.Mandelbrot;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Draws a fractal with multiple threads for given base polynomial.
 * @author Fani
 *
 */
public class NewtonParallel {
	//private volatile static int poslovi = 0;
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		Integer workers = (Integer) null;
		Integer tracks = (Integer) null;
		
		for (int i = 0; i < args.length; i++) {
			try {
				if(args[i].contains("-w=") || args[i].contains("--workers=")) {
					workers = Integer.parseInt(args[i].substring(args[i].indexOf("=") + 1));
				} else if( args[i].contains("-t=") || args[i].contains("--tracks=")) {
					tracks = Integer.parseInt(args[i].substring(args[i].indexOf("=") + 1));
				}
			} catch(Exception e) {
				System.out.println("Unable to configure the system with given arguments!");
				System.exit(1);
			}
		}
		
		if(workers == (Integer) null) {
			workers = Runtime.getRuntime().availableProcessors();
		}
		
		if(tracks == (Integer) null) {
			tracks = 4 * workers;
		}
		
		Complex[] roots = ReadingUtils.readInputRoots();
		var polynomial = new ComplexRootedPolynomial(Complex.ONE, roots);
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MyProducer(polynomial, workers, tracks));
	}
	
	/**
	 * Represents the work which a thread needs to execute.
	 * @author Fani
	 *
	 */
	public static class CalculationJob implements Runnable {
		double reMin;
		double reMax;
		double imMin;	
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		//Object object;
		
		ComplexRootedPolynomial rooted;
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		
		public static CalculationJob NO_JOB = new CalculationJob();
	
		private CalculationJob() {
			
		}
		
		public CalculationJob(double reMin, double reMax, double imMin,double imMax, int width, int height, int yMin, int yMax, 
			int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial rooted) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			//this.object = object;
			
			this.rooted = rooted;
			this.polynomial = rooted.toComplexPolynom();
			this.derived = polynomial.derive();
		}
	
		@Override
		public void run() {
			/*synchronized (object) {
				poslovi++;
			}
			
			System.out.println("Posao pokrenut. "+ poslovi + " poslova aktivnih. " + Thread.activeCount() + " dretvi aktivno.");
			*/
			Mandelbrot.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel);
			
			int offset = yMin * width;
			
			for(int y = yMin; y < yMax + 1; y++) {
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
			/*synchronized (object) {
				poslovi--;
			}*/
		}
	}

	/**
	 * Newton-Raphson iteration-based fractal producer.
	 * @author Fani
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		
		ComplexRootedPolynomial rooted;
		ComplexPolynomial polynomial;
		ComplexPolynomial derived;
		
		int numOfWorkers;
		int numOfJobs;
		
		public MyProducer(ComplexRootedPolynomial rooted, int numOfWorkers, int numOfJobs) {
			this.rooted = rooted;
			this.polynomial = rooted.toComplexPolynom();
			this.derived = polynomial.derive();
			this.numOfWorkers = numOfWorkers;
			this.numOfJobs = numOfJobs;
		}
		
		/**
		 * The method will be called whenever fractal data needs to be generated. 
		 * The data is expected in the form of a field of short numbers that has exactly as many elements as the raster has image elements (width * height). 
		 * When returning the results, it is necessary to return the data to the submitted observer object, stating the request identifier that the method received.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun... Zadano " + numOfWorkers + " dretvi i " + numOfJobs + " poslova." );
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			final int brojTraka = numOfJobs;
			int brojYPoTraci = height / brojTraka;
		
			final BlockingQueue<CalculationJob> queue = new LinkedBlockingQueue<>();

			Thread[] workers = new Thread[numOfWorkers];
			for(int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							CalculationJob p = null;
							try {
								p = queue.take();
								if(p == CalculationJob.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			
			for(int i = 0; i < workers.length; i++) {
				workers[i].start();
			}
			
			//Object vratar = new Object();
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;
				if(i == brojTraka - 1) {
					yMax = height - 1;
				}
				
				CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, rooted); //vratar);
				while(true) {
					try {
						queue.put(job);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						queue.put(CalculationJob.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
		
			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			System.out.println("Cijeli racun gotov. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
	
}
