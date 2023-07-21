package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/**
 * A linked list-backed collection of objects with non-null elements and allowed duplicates.
 * 
 * @param <E> type of elements in the list
 * @author Fani
 *
 */
public class LinkedListIndexedCollection<E> implements List<E> {
	
	/**
	 * A node like structure of a doubly linked list consisting of value & references to the previous and the next node.
	 * 
	 * @author Fani
	 *
	 */
	private static class ListNode<E> {
		/**
		 * The previous node before the current one in the list.
		 */
		private ListNode<E> previous;
		
		/**
		 * The next node after the current one in the list.
		 */
		private ListNode<E> next;
		
		/**
		 * The value of the node.
		 * 
		 */
		private E value;
	}
	
	/**
	 * The size of the linked list.
	 */
	private int size;
	
	/**
	 * The first node in the list.
	 */
	private ListNode<E> first;
	
	/**
	 * The last node in the list.
	 */
	private ListNode<E> last;
	
	
	/**
	 * Used to check number of modifications in case of changing the LinkedListIndexedCollection
	 *  from the outside during iteration through ElementsGetter.
	 */
	private long modificationCount = 0;
	
	
	/**
	 * A default constructor which creates an empty LinkedListCollection.
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
	}
	
	/**
	 * A constructor which creates a LinkedListCollection 
	 * by copying the elements of a specified other collection.
	 * @param other the collection whose elements are copied into the newly created linked list.
	 * @throws NullPointerException if other collection is null
	 */
	public LinkedListIndexedCollection(Collection<? extends E> other) {
		this();
		
		if(other == null)
			throw new NullPointerException("The other collection can't be null!");
		
		addAll(other);
	}
	
	/**
	 * Determines how many objects are stored in the linked list at the moment.
	 * 
	 * @return number of currently stored objects in the linked list.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds the specified object at the end of the linked list.
	 * 
	 * @param value object to be added in the linked list
	 */
	@Override
	public void add(E value) {
		if(value == null) 
			throw new NullPointerException("You can't add null to the LinkedListIndexedCollection!");
		
		ListNode<E> newNode = new ListNode<>();
		
		//set references:
		newNode.previous = last;  //last is showing the previous last element
		newNode.next = null;      //since it is the new last node, there is no next element
		
		//set value:
		newNode.value = value;
		
		//set first and last references:
		if(size == 0)  		//if the collection was empty
			first = newNode;
		else					//collection wasn't empty
			last.next = newNode; 
		
		// new node is now the last
		last = newNode;		
		size++;
		modificationCount++;
	}
	
	/**
	 * Determines if the specified object exists inside the linked list. 
	 * 
	 * @param value value to be checked whether is contained in the linked list, <code>null</code> is allowed
	 * @return <code>true</code> if the linked list contains the given value, <code>false</code> otherwise.
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}
	
	/**
	 * Removes one occurrence of the specified object inside the linked list.
	 * 
	 * <p><i>Note: Increases modification count</i></p>
	 * 
	 * @param value value to be removed if included in the linked list
	 * @return <code>true</code> if the value has been removed, <code>false</code> otherwise.
	 */
	@Override
	public boolean remove(E value) {
		if(indexOf(value) == -1)
			return false;
		
		remove(indexOf(value)); 
		return true;
	}
	
	/**
	 * Returns a new array containing all elements of this linked list.
	 * 
	 * @return an array containing all elements of this linked list, never <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray() {
		E[] array = (E[]) new Object[size];
		
		if(size == 0) 
			return array;
		
		int i = 0;
		for(ListNode<E> node = first; node != null; node = node.next, i++) {
			array[i] = (E) node.value;
		}
		
		return array;		
	}

	
	/**
	 * Removes all elements from this linked list. 
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Returns an object that is stored in the linked list at the specified index. 
	 *
	 * @param index position at which is the object which is returned
	 * @return object that is stored in the linked list at position index.
	 * @throws IndexOutOfBoundsException if index is less than zero and greater or equal to the size of the linked list
	 */
	@Override
	public E get(int index) {
		
		if(index < 0 || index >= size) 
			throw new IndexOutOfBoundsException("The index " + index + " is out of bounds. It should be between 0 and " + (size - 1));	
		
		int i, indexOfMedium = (size - 1)/2;
		
		//should iterate starting from first
		if(index <= indexOfMedium) { 
			i = 0;
			for(ListNode<E> node = first; i <= indexOfMedium ; node = node.next, i++) {
				if(i == index) {
					return (E) node.value;
				}
			}
		
		//should iterate starting from last
		} else {				  
			i = size - 1;
			for(ListNode<E> node = last; i > indexOfMedium ; node = node.previous, i--) {
				if(i == index) {
					return (E) node.value;
				}
			}
		}
		
		//this will never execute
		return null;
	}
	
	/**
	 * Places the specified object at the specified position in the linked list. 
	 * If there are objects at a greater index than the specified position, they move forward one place to make space for the new object.
	 *
	 *<p>Average complexity: 0(1)</p>
	 *
	 *<p><i>Note: Increases modification count</i></p>
	 *
	 * @param value object to be put in the specified position inside the linked list
	 * @param position index inside the collection at which the value is put
	 * @throws NullPointerException if value is <code>null</code> 
	 * @throws IndexOutOfBoundsException if position is less than zero or larger than the size of the linked list
	 */
	@Override
	public void insert(E value, int position) {
		
		if(value == null) 
			throw new NullPointerException("You can't insert null to the ArrayIndexedCollection!");
		
		if(position < 0 || position > size) 
			throw new IndexOutOfBoundsException("The position" + position + "is out of bounds. It should be between 0 and " + size);
		
		ListNode<E> newNode = new ListNode<>();
		newNode.value = value;
		
		// empty collection
		if(first == null) {
			first = last = newNode;
			newNode.previous = newNode.next = null;
			size++;
			return;
		} 
		
		// storage of node at the wanted position 
		ListNode<E> node = first;	
		int i = 0;
		while(i < position) {
			node = node.next;
			i++;
		}
		
		//insertion at the beginning of the collection which is not empty
		if(node == first) {
			newNode.previous = null;
			newNode.next = node;
			node.previous = newNode;
			first = newNode;
			
		//insertion at the end of the collection which is not empty
		} else if(node == null) {	
			node = last;
			node.next = newNode;
			newNode.previous = node;
			newNode.next = null;
			last = newNode;
		
		//insertion in the middle of collection
		} else if(node.previous != null && node.next != null) {	
			node.previous.next = newNode; //1
			node.previous = newNode; //2
			newNode.previous = node.previous;
			newNode.next = node;
		}
		
		modificationCount++;
		size++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the specified object.
	 * <p>Average complexity: 0(n) </p>
	 *
	 * @param value value whose index is searched for in the collection
	 * @return index of the specified value if it exists inside the collection, -1 if there is no such value in the collection.
	 */
	@Override
	public int indexOf(Object value) {
		if(value == null) return -1;
		
		//iterate until you find it or reach the end
		int i; ListNode<E> node = first;
		for(i = 0; node != null && node.value != value; node = node.next, i++); 
		
		//reached the end without finding it
		if(node == null) return -1;
		
		//found it
		return i;
		
	}
	
	/**
	 * Removes the object at the specified position in the collection. Objects at bigger index than specified move back to remove "holes" inside the array.
	 * 
	 * @param index position of the object to be removed
	 * @throws IndexOutOfBoundsException if position is not in the range <code>[0, size-1]</code>
	 */
	@Override
	public void remove(int index) {
		
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("The index " + index + " is out of bounds. It should be between 0 and " + (size - 1));
		
		ListNode<E> node = first; int i = 0;
		while(i < index && node.next != null) {
			node = node.next;
			i++;
		}

		if(node == first && node == last) {
			first = null;
			last = null;
		} else if(node == first) {
			first = node.next;
			node.next.previous = null;
		} else if(node == last) {
			last = node.previous;
			node.previous.next = null;
		} else {
			node.next.previous = node.previous;
			node.previous.next = node.next;
		}
		
		size--;	
		modificationCount++;
	}
	
	/**
	 * Returns an instance of an ElementsGetter for this collection.
	 * @return an ElementsGetter specific for LinkedListIndexedCollection.
	 */
	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new LinkedElementsGetter<>(this);
	}
	
	/**
	 * A special ElementsGetter for the LinkedListIndexedCollection used for getting objects one by one.
	 * @author Fani
	 *
	 */
	private static class LinkedElementsGetter<E> implements ElementsGetter<E> {
		
		/**
		 * The reference used for remembering the next element to be delivered.
		 */
		private ListNode<E> current;
		
		/**
		 * The reference to the parent linked list of this ElementsGetter.
		 */
		private LinkedListIndexedCollection<E> col;
		
		/**
		 * Saves the modification count from the start of iteration. Used to prevent dangers of the outside changes to the list during iteration.
		 */
		private long savedModificationCount;
		
		/**
		 * Creates the ElementsGetter specific for the LinkedListIndexedCollection.
		 * 
		 * @param col parent linked list
		 */
		public LinkedElementsGetter(LinkedListIndexedCollection<E> col) {
			this.current = col.first;
			this.col = col;
			this.savedModificationCount = col.modificationCount;
		}
		
		/**
		 * Determines if there are more elements in the collection to be accessed.
		 * 
		 * @return <code>true</code> if there are more elements, <code>false</code> otherwise.
		 */
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("An unsupported change of this list was made during iteration with elements getter!");
			
			return current != null;
		}
		
		/**
		 * Returns the next element in the collection if there is one.
		 * 
		 * @return the next object in the collection that has not yet been accessed.
		 * @throws NoSuchElementException if there are no more objects to be delivered.
		 */
		@Override
		public E getNextElement() {
			if(savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("An unsupported change of this list was made during iteration with elements getter!");
			
			if(!hasNextElement())
				throw new NoSuchElementException("No more non delivered elements!");
			
			ListNode<E> temp = current;
			current = current.next;
			return (E) temp.value;
			
		}
		
	}
	
}
