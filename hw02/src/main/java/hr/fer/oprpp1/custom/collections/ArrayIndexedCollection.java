package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/**
 * An array like indexed collection of objects with non-null elements and allowed duplicates.
 * @author Fani
 *
 */
public class ArrayIndexedCollection implements List {
	
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
	 * Used to check number of modifications in case of changing the ArrayIndexedCollection from the outside during iteration through ElementsGetter.
	 */
	private long modificationCount = 0;
	
	
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
	 * Creates an instance of ArrayIndexedCollection with set internal capacity 
	 * and copies the specified other collection into the new instance.
	 * 
	 * @param other collection copied into the newly created ArrayIndexedCollection
	 * @param initialCapacity the capacity of the created array, implemented internally
	 * @throws NullPointerException if other collection is null
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		
		if(other == null)
			throw new NullPointerException("The other collection can't be null!");
			
		addAll(other);
	}
	
	/**
	 * Creates an instance of ArrayIndexedCollection with default internal capacity and copies the specified other collection into the new instance.
	 * 
	 * @param other collection copied into the newly created ArrayIndexedCollection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Determines how many objects are stored in the array list at the moment.
	 * 
	 * @return number of currently stored objects in the array list.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds the specified object into this array list.
     * If the internal array is already full, it's size is doubled.
     * <p> Average complexity: O(1) if no enlargement needed,O(n) if it needs to be enlarged </p>
     * <p><i>Note: Increases modification count when the internal array is resized</i></p>
     * 
     * @param value object to be added in the array list
     * @throws NullPointerException if the object trying to be added is <code>null</code> 
	 */
	@Override
	public void add(Object value) {
		if(value == null) 
			throw new NullPointerException("You can't add null to the ArrayIndexedCollection!");
		
		if(size + 1 > elements.length) {
			elements = Arrays.copyOf(elements, 2 * elements.length);
			modificationCount++;
		}
		
		elements[size] = value;
		size++;
	}
	
	/**
	 * Determines if the specified object is included in the array list. 
	 * 
	 * @param value value to be checked whether is contained in the array list, <code>null</code> is allowed
	 * @return <code>true</code> if the array list contains the given value, <code>false</code> otherwise.
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}
	
	/**
	 * Removes one occurrence of the specified object inside the array list.
	 * <p><i>Note: Increases modification count</i></p>
	 * 
	 * @param value value to be removed if included in the array list
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
	 * Returns a new array containing all of the elements in this array list.
	 * 
	 * @return an array containing all the elements in this array list, never <code>null</code>.
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);		
	}
	
	/**
	 * Removes all elements from this array list. 
	 * <p><i>Note: Increases modification count</i></p>
	 */
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Returns an object that is stored in the array list at the specified index. 
	 * 
	 * @param index position at which is the object which is returned
	 * @return object that is stored in the array list at position index.
	 * @throws IndexOutOfBoundsException if index is is less than zero and greater or equal to the size of the array list
	 */
	@Override
	public Object get(int index) {
		if(index < 0 || index >= size) 
			throw new IndexOutOfBoundsException("The index " + index + " is out of bounds. It should be between 0 and " + (size - 1));
		
		return elements[index];
	}
	
	/**
	 * Places the specified object at the specified position in the array list. 
	 * If there are objects at a greater index than the specified position, they move forward one place to make space for the new object.
	 * <p><i>Note: Increases modification count if inserted before the last place in list</i></p>
	 * 
	 * @param value object to be put on the position inside the collection
	 * @param position index inside the array list at which the value is put
	 * @throws NullPointerException if value is <code>null</code> 
	 * @throws IndexOutOfBoundsException if position is less than zero or larger than the size of the array list
	 */
	@Override
	public void insert(Object value, int position) {
		if(value == null) 
			throw new NullPointerException("You can't insert null to the ArrayIndexedCollection!");
		
		if(position < 0 || position > size) 
			throw new IndexOutOfBoundsException("The position" + position + "is out of bounds. It should be between 0 and " + size);
		
		if(size + 1 > elements.length)
			elements = Arrays.copyOf(elements, 2 * elements.length);
		
		for(int i = size; i > position; i--) {
			elements[i] = elements[i - 1]; 
			modificationCount++;
		}
		
		elements[position] = value;
		size++;
	}
	
	/**
	 * Searches the array list and returns the index of the first occurrence of the specified object.
	 * 
	 * @param value value whose index is searched for in the array list
	 * @return index of the specified value if it exists inside the array list, -1 if there is no such value.
	 */
	@Override
	public int indexOf(Object value) {
		if(value == null) return -1;
		
		for(int i = 0; i < size; i++) {
			if(value.equals(elements[i]))
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Removes the object at the specified position in the array list. 
	 * If there are objects at a larger index than the specified, they move back one place to fill the empty space.
	 * <p><i>Note: Increases modification count if an element before the last one is removed</i></p>
	 *
	 * @param index position of the object to be removed
	 * @throws IndexOutOfBoundsException if position is less than zero or larger or equal to the size of the array list
	 */
	@Override
	public void remove(int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("The index" + index + "is out of bounds. It should be between 0 and " + (size - 1));
		
		int i;
		for(i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1]; 
			modificationCount++;
		}
		
		elements[i] = null;
		size--;	
	}
	
	/**
	 * Returns an instance of an ElementsGetter for this collection.
	 * @return an ElementsGetter specific for ArrayIndexedCollection.
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayElementsGetter(this);
	}
	
	/**
	 * A model of an ElementsGetter specific for ArrayIndexedCollection.
	 * @author Fani
	 *
	 */
	private static class ArrayElementsGetter implements ElementsGetter {
		/**
		 * The index of the next object in the array list to be delivered.
		 */
		private int index = 0;
		
		/**
		 * The reference to the parent array list of this ElementsGetter.
		 */
		private ArrayIndexedCollection col;
		
		/**
		 * Used to check if someone changed the array list from the outside while this ElementsGetter is iterating.
		 */
		private long savedModificationCount;
		
		/**
		 * Creates the ElementsGetter specific for the ArrayIndexedCollection.
		 * @param col parent array list
		 */
		public ArrayElementsGetter(ArrayIndexedCollection col) {
			this.col = col;
			this.savedModificationCount = col.modificationCount;
		}
		
		/**
		 * Determines if there are more elements in the list to be accessed.
		 * 
		 * @return <code>true</code> if there are more elements, <code>false</code> otherwise.
		 * @throws ConcurrentModificationException if the list was changed so that it interrupts the iteration
		 */
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("An unsupported change of this list was made during iteration with elements getter!");
			
			return index < col.size();
			
		}
		
		/**
		 * Returns the next element in the array list if there is one.
		 * 
		 * @return the next object in the collection that has not yet been accessed.
		 * @throws NoSuchElementException if there are no more objects to be delivered.
		 * @throws ConcurrentModificationException if the list was changed so that it interrupts the iteration
		 */
		@Override
		public Object getNextElement() {
			if(savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("An unsupported change of this list was made during iteration with elements getter!");
			
			if(!hasNextElement())
				throw new NoSuchElementException("No more undelivered elements!");
			
			return col.elements[index++];
		}
		
	}
	
}
