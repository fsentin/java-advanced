package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
/**
 * Demonstrates the program for any curve whose configuration is given as a correctly formatted file.
 * @author Fani
 *
 */
public class Glavni3 {
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
