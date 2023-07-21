package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;


/**
 * An array indexed collection of objects with non-null elements and allowed duplicates.
 * @author Fani
 *
 */
public class ArrayIndexedCollection extends Collection {
	/**
	 * The size of the ArrayIndexedCollection.
	 */
	private int size;
	
	/**
	 * An array of objects stored inside the ArrayIndexedCollection.
	 */
	private Object[] elements;
	
	/**
	 * The default capacity of a newly created ArrayIndexedCollection.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Creates an instance of ArrayIndexedCollection with set internal capacity.
	 * 
	 * @param initialCapacity the capacity of the created array, implemented internally
	 * @throws IllegalArgumentException if initialCpacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) 
			throw new IllegalArgumentException("The initial capacity is set to " + initialCapacity + "but should be equal to or larger than 1!");
		
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * Creates an instance of ArrayIndexedCollection with default internal capacity.
	 * 
	 * @param initialCapacity the capacity of the created array, implemented internally
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Creates an instance of ArrayIndexedCollection with set internal capacity and copies the specified other collection into the new instance.
	 * 
	 * @param other collection copied into the newly created ArrayIndexedCollection
	 * @param initialCapacity the capacity of the created array, implemented internally
	 * @throws NullPointerException if other collection is null
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		
		if(other == null)
			throw new NullPointerException("The other Collection can't be null!");
		
		addAll(other);
	}
	
	/**
	 * Creates an instance of ArrayIndexedCollection with default internal capacity and copies the specified other collection into the new instance.
	 * 
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Determines how many objects are stored in the collection at the moment.
	 * 
	 * @return number of currently stored objects in the collection.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds the specified object into this collection.
     * If the internal array is already full, it's size is doubled.
     *<p> Average complexity: O(1) if no enlargement needed,O(n) if it needs to be enlarged </p>
     * 
     * @param value object to be added in the collection
     * @throws NullPointerException if the object trying to be added is <code>null</code> 
	 */
	@Override
	public void add(Object value) {
		if(value == null) 
			throw new NullPointerException("You can't add null to the ArrayIndexedCollection!");
		
		if(size + 1 > elements.length)
			elements = Arrays.copyOf(elements, 2 * elements.length);
		
		elements[size] = value;
		size++;
	}
	
	/**
	 * Determines if the specified object is included in the collection. 
	 * 
	 * @param value value to be checked whether is contained in the collection, <code>null</code> is allowed
	 * @return <code>true</code> if the collection contains the given value, <code>false</code> otherwise.
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}
	
	/**
	 * Removes one occurrence of the specified object inside the collection.
	 * 
	 * @param value value to be removed if included in the collection
	 * @return <code>true</code> if the value has been removed, <code>false</code> otherwise.
	 */
	@Override
	public boolean remove(Object value) {
		if(indexOf(value) == -1)
			return false;
		
		remove(indexOf(value)); 
		return true;
	}
	
	/**
	 * Returns a new array containing all of the elements in this collection.
	 * 
	 * @return an array containing all the elements in this collection, never <code>null</code>.
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);		
	}
	
	/**
	 * Calls <code>processor.process(.)</code> for each element of this collection.
	 * The order of elements sent is undefined.
	 * @param processor instance of processor whose process is used
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Removes all elements from this collection. 
	 */
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) 
			elements[i] = null;
		
		size = 0;
	}
	
	/**
	 * Returns an object that is stored in the array at the specified index. 
	 * <p>Average complexity: 0(1)</p>
	 * @param index position at which is the object which is returned
	 * @return object that is stored in the array at position index.
	 * @throws IndexOutOfBoundsException if index is less than zero and greater or equal to the size of the collection
	 */
	
	public Object get(int index) {
		if(index < 0 || index >= size) 
			throw new IndexOutOfBoundsException("The index " + index + " is out of bounds. It should be between 0 and " + (size - 1));
		
		return elements[index];
	}
	
	/**
	 * Places the specified object at the specified position in the array. Objects at bigger index than the specified position move forward to make space for the new object.
	 * <p>Average complexity: O(n/2)</p>
	 * @param value object to be put on the position inside the collection
	 * @param position index inside the collection at which the value is put
	 * @throws NullPointerException if value is <code>null</code> 
	 * @throws IndexOutOfBoundsException if position is less than zero or larger than the size of the collection
	 */
	public void insert(Object value, int position) {
		if(value == null) 
			throw new NullPointerException("You can't insert null to the ArrayIndexedCollection!");
		
		if(position < 0 || position > size) 
			throw new IndexOutOfBoundsException("The position" + position + "is out of bounds. It should be between 0 and " + size);
		
		if(size + 1 > elements.length)
			elements = Arrays.copyOf(elements, 2 * elements.length);
		
		for(int i = size; i > position; i--) 
			elements[i] = elements[i - 1]; 
		
		
		elements[position] = value;
		size++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the specified object.
	 * <p>Average complexity: O(n/2)</p>
	 * @param value value whose index is searched for in the collection
	 * @return index of the specified value if it exists inside the collection, -1 if there is no such value in the collection.
	 */
	public int indexOf(Object value) {
		if(value == null) return -1;
		
		for(int i = 0; i < size; i++) {
			if(value.equals(elements[i]))
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Removes the object at the specified position in the array. Objects at bigger index than specified move back to remove "holes" inside the array.
	 * @param index position of the object to be removed
	 * @throws IndexOutOfBoundsException if position is less than zero or larger or equal to the size of the collection
	 */
	public void remove(int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("The index" + index + "is out of bounds. It should be between 0 and " + (size - 1));
		
		int i;
		for(i = index; i < size - 1; i++) 
			elements[i] = elements[i + 1]; 
		
		elements[i] = null;
		size--;	
	}
	
}
