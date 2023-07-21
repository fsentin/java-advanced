package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.Collection.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

/**
 * Demonstrates the improved functionality of an ElementsGetter.
 * @author Fani
 *
 */
public class ElementsGetterImprovedDemo {
	public static void main(String[] args) {
		
		/*Uncomment to test each one!*/
		
		arrayDemo();
		//linkedListDemo();
	}
	
	public static void arrayDemo() {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		System.out.println("Jedan element: " + getter.getNextElement());
	}
	
	public static void linkedListDemo() {
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		System.out.println("Jedan element: " + getter.getNextElement());
	}

}
