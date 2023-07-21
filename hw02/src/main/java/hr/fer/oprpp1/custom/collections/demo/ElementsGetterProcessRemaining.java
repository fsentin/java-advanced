package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.Collection.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

/**
 * Demonstrates the functionality of the <code>processRemaining</code> method of the ElementsGetter class.
 * @author Fani
 *
 */
public class ElementsGetterProcessRemaining {
	public static void main(String[] args) {
		System.out.println("Array:");
		arrayDemo();
		
		System.out.println("Linked list:");
		linkedListDemo();
	}
	
	public static void arrayDemo() {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		getter.processRemaining(System.out::println);
	}
	
	public static void linkedListDemo() {
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		getter.processRemaining(System.out::println);
	}


}
