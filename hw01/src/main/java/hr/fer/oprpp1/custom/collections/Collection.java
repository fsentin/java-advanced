package hr.fer.oprpp1.custom.collections;

/**
 * A general collection of objects.
 * 
 * @author Fani
 */

public class Collection {
	
	protected Collection() {
		super();
	}
	
	/**
	 * Determines how many objects are stored in the collection at the moment.
	 * <p>
	 * *Note: Inside the class <code>Collection</code> this method always returns 0.
	 * Child classes have to implement the correct functionality.
	 * </p>
	 * @return number of currently stored objects in the collection.
	 */
	public int size() {
		return 0;
	}
	
	
	/**
	 * Checks if the collection contains no objects.
	 * 
	 * @return <code>true</code> if the collection contains no objects, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	
	/**
	 * Adds the specified object to the collection.
	 * <p>
	 * *Note: Inside the class <code>Collection</code> this method is empty.
	 * Child classes have to implement the correct functionality.
	 * </p>
	 * @param value object to be added in the collection
	 */
	public void add(Object value) {
		// does nothing here!
	}
	
	
	/**
	 * Determines if the specified object is included in the collection. 
	 * <p>
	 * *Note: Inside the class <code>Collection</code> this method always returns false.
	 * Child classes have to implement the correct functionality.
	 * </p>
	 * @param value value to be checked whether is contained in the collection, <code>null</code> is allowed
	 * @return <code>true</code> if the collection contains the given value, <code>false</code> otherwise.
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	
	/**
	 * Removes one occurrence of the specified object inside the collection.
	 * <p>
	 * *Note: Inside the class <code>Collection</code> this method always returns false.
	 * Child classes have to implement the correct functionality.
	 * </p>
	 * @param value value to be removed if included in the collection
	 * @return <code>true</code> if the value has been removed, <code>false</code> otherwise.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	
	/**
	 * Returns a new array containing all of the elements in this collection.
	 * <p>
	 * *Note: Inside the class <code>Collection</code> this method always throws the UnsupportedOperationException.
	 * Child classes have to implement the correct functionality.
	 * </p>
	 * @return an array containing all the elements in this collection, never <code>null</code>.
	 * @throws UnsupportedOperationException always 
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	
	/**
	 * Applies an operation on each element of the collection with the help of <code>processor.process(.)</code>.
	 * The order of elements sent is undefined.
	 * <p>
	 * *Note: Inside the class <code>Collection</code> this method is empty.
	 * Child classes have to implement the correct functionality.
	 * </p>
	 * @param processor instance of processor class which defines the operation applied to the elements of the collection
	 */
	public void forEach(Processor processor) {
		// does nothing here!
	}
	
	
	/**
	 * Adds all elements from a specified collection to this collection. The specified collection remains unchanged.
	 * @param other collection whose elements are to be added to this collection
	 */
	public void addAll(Collection other) {
		
		class InternalProcessor extends Processor {
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}
		
		other.forEach(new InternalProcessor());
		
	}
	
	
	/**
	 * Removes all elements from this collection. 
	 * <p>
	 * *Note: Inside the class <code>Collection</code> this method is empty.
	 * Child classes have to implement the correct functionality.
	 * </p>
	 */
	public void clear() {
		// does nothing here!
	}
	

}
