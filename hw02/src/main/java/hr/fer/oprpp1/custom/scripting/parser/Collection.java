package hr.fer.oprpp1.custom.scripting.parser;

//import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection.ElementsGetter;

/**
 * A general collection of objects.
 * 
 * @author Fani
 */

public interface Collection {
	
	/**
	 * Determines how many objects are stored in the collection at the moment.
	 *
	 * @return number of currently stored objects in the collection.
	 */
	public int size();
	
	
	/**
	 * Checks if the collection contains no objects.
	 * 
	 * @return <code>true</code> if the collection contains no objects, <code>false</code> otherwise.
	 */
	default public boolean isEmpty() {
		return size() == 0;
	}
	
	
	/**
	 * Adds all elements that satisfy the specified tester.
	 * @param col the collection whose elements are to be added
	 * @param tester the tester which determines if an Object is to be added to the collection
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter e = col.createElementsGetter();
		Object current;
		while(e.hasNextElement()) {
			current = e.getNextElement();
			if(tester.test(current))
				add(current);
		}
	}
	
	
	/**
	 * Adds the specified object to the collection.
	 * 
	 * @param value object to be added in the collection
	 */
	public void add(Object value);
	
	
	/**
	 * Determines if the specified object is included in the collection. 
	 * 
	 * @param value value to be checked whether is contained in the collection, <code>null</code> is allowed
	 * @return <code>true</code> if the collection contains the given value, <code>false</code> otherwise.
	 */
	public boolean contains(Object value);
	
	
	/**
	 * Removes one occurrence of the specified object inside the collection.
	 * 
	 * @param value value to be removed if included in the collection
	 * @return <code>true</code> if the value has been removed, <code>false</code> otherwise.
	 */
	public boolean remove(Object value);
	
	
	/**
	 * Returns a new array containing all of the elements in this collection.
	 * 
	 * @return an array containing all the elements in this collection, never <code>null</code>.
	 * @throws UnsupportedOperationException 
	 */
	public Object[] toArray();
	
	
	/**
	 * Applies an operation on each element of the collection with the help of <code>processor.process(.)</code>.
	 * The order of elements sent is undefined.
	 * 
	 * @param processor instance of processor class which defines the operation applied to the elements of the collection
	 */
	default public void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		Object current;
		while(getter.hasNextElement()) {
			current = getter.getNextElement();
			processor.process(current);
		}
	}
	
	
	/**
	 * Adds all elements from a specified collection to this collection. The specified collection remains unchanged.
	 * 
	 * @param other collection whose elements are to be added to this collection
	 */
	default public void addAll(Collection other) {
		class InternalProcessor implements Processor {
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
			
		}
		other.forEach(new InternalProcessor());
		
	}
	
	
	/**
	 * Removes all elements from this collection. 
	 */
	public void clear();
	
	
	/**
	 * Returns an instance of ElementsGetter used for getting elements one by one without using copies.
	 * 
	 * @return a new instance of ElementsGetter for a specific collection.
	 */
	public ElementsGetter createElementsGetter();
	
	/**
	 * A structure which allows the user to get elements of a collection, one by one, without using copies of collection's inner structures.
	 *
	 * @author Fani
	 *
	 */
	public interface ElementsGetter {
		
		/**
		 * Determines if there are more elements in the collection to be accessed.
		 * 
		 * @return <code>true</code> if there are more elements, <code>false</code> otherwise.
		 */
		public boolean hasNextElement();
		
		/**
		 * Returns the next element in the collection if there is one.
		 * 
		 * @return the next object in the collection that has not yet been accessed.
		 * @throws NoSuchElementException if there are no more objects to be delivered.
		 */
		public Object getNextElement();
		
		/**
		 * Processes the remaining elements of this ElementsGetter.
		 * @param p the processor that performs an operation on the remaining elements.
		 */
		default public void processRemaining(Processor p) {
			while(hasNextElement())
				p.process(getNextElement());
		}
	}

}
