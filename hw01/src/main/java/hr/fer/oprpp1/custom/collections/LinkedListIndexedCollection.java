package hr.fer.oprpp1.custom.collections;


/**
 * A linked list-backed collection of objects with non-null elements and allowed duplicates.
 * @author Fani
 *
 */
public class LinkedListIndexedCollection extends Collection {
	
	/**
	 * A node like structure of a doubly linked list consisting of value & references to the previous and the next node.
	 * 
	 * @author Fani
	 *
	 */
	private static class ListNode {
		
		/**
		 * The previous node before the current one in the list.
		 */
		private ListNode previous;
		
		/**
		 * The next node after the current one in the list.
		 */
		private ListNode next;
		
		/**
		 * The value of this current node.
		 */
		private Object value;
	}
	
	/**
	 * The size of the LinkedListIndexedCollection.
	 */
	private int size;
	
	/**
	 * The first node in the list.
	 */
	private ListNode first;
	
	/**
	 * The last node in the list.
	 */
	private ListNode last;
	
	
	/**
	 * Creates an empty linked list.
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
	}
	
	/**
	 * Creates a LinkedListCollection by copying the elements of a specified other collection.
	 * @param other the collection whose elements are copied into the newly created linked list.
	 * @throws NullPointerException if other collection is null
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		
		if(other == null)
			throw new NullPointerException("The other collection can't be null!");
		
		addAll(other);
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
	 * Adds the specified object at the end of the linked list.
	 * 
	 * @param value object to be added in the linked list
	 */
	@Override
	public void add(Object value) {
		if(value == null) 
			throw new NullPointerException("You can't add null to the LinkedListIndexedCollection!");
		
		ListNode newNode = new ListNode();
		
		//set references:
		newNode.previous = last;  //last is showing the previous last element
		newNode.next = null;      //since it is the new last node, there is no next element
		
		//set value:
		newNode.value = value;
		
		//set first and last references:
		if(size == 0)  			//if the collection was empty
			first = newNode;
		else					//collection wasn't empty
			last.next = newNode; 
		
		// new node is now the last
		last = newNode;		
		size++;
	}
	
	/**
	 * Determines if the specified object exists inside the linked list. 
	 * 
	 * @param value value to be checked whether is contained in the collection, <code>null</code> is allowed
	 * @return <code>true</code> if the linked list contains the given value, <code>false</code> otherwise.
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}
	
	/**
	 * Removes one occurrence of the specified object inside the linked list.
	 * 
	 * @param value value to be removed if included in the linked list
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
	 * Returns a new array containing all elements of this linked list.
	 * 
	 * @return an array containing all elements of this linked list, never <code>null</code>.
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		if(size == 0) 
			return array;
		
		int i = 0;
		for(ListNode node = first; node != null; node = node.next, i++) {
			array[i] = node.value;
		}
		
		return array;		
	}
	
	/**
	 * Applies an operation on each element of the collection with the help of <code>processor.process(.)</code>.
	 * The order of elements sent is undefined.
	 *
	 * @param processor instance of processor class which defines the operation applied to the elements of the collection
	 */
	@Override
	public void forEach(Processor processor) {
		if(first == null) //nothing to do
			return;
		
		for(ListNode node = first; node != null; node = node.next) {
			processor.process(node.value);
		}
	}
	
	/**
	 * Removes all elements from this linked list. 
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Returns an object that is stored in the linked list at the specified index. 
	 *
	 * @param index position at which is the object which is returned
	 * @return object that is stored in the linked list at position index.
	 * @throws IndexOutOfBoundsException if index is less than zero and greater or equal to the size of the linked list.
	 */
	public Object get(int index) {
		
		if(index < 0 || index >= size) 
			throw new IndexOutOfBoundsException("The index " + index + " is out of bounds. It should be between 0 and " + (size - 1));	
		
		int i, indexOfMedium = (size - 1)/2;
		
		// should iterate starting from first
		if(index <= indexOfMedium) { 
			i = 0;
			for(ListNode node = first; i <= indexOfMedium ; node = node.next, i++) {
				if(i == index) {
					return node.value;
				}
			}
			
		//should iterate starting from last	
		} else {				  
			i = size - 1;
			for(ListNode node = last; i > indexOfMedium ; node = node.previous, i--) {
				if(i == index) {
					return node.value;
				}
			}
		}
		//never executes
		return null;
	}
	
	/**
	 * Places the specified object at the specified position in the linked list. 
	 *
	 *<p>Average complexity: 0(1)</p>
	 *
	 * @param value object to be put in the specified position inside the linked list
	 * @param position index inside the collection at which the value is put
	 * @throws NullPointerException if value is <code>null</code> 
	 * @throws IndexOutOfBoundsException if position is less than zero or larger than the size of the linked list
	 */
	public void insert(Object value, int position) {
		
		if(value == null) 
			throw new NullPointerException("You can't insert null to the LinkedListIndexedCollection!");
		
		if(position < 0 || position > size) 
			throw new IndexOutOfBoundsException("The position" + position + "is out of bounds. It should be between 0 and " + size);
		
		ListNode newNode = new ListNode();
		newNode.value = value;
		
		// empty collection
		if(first == null) {		
			first = last = newNode;
			newNode.previous = newNode.next = null;
			size++;
			return;
		} 
		
		// storage of node at the wanted position
		ListNode node = first;	  
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
			node.previous.next = newNode; 
			node.previous = newNode; 
			newNode.previous = node.previous;
			newNode.next = node;
		}

		size++;
	}
	
	/**
	 * Searches the linked list and returns the index of the first occurrence of the specified object if existing.
	 * <p>Average complexity: 0(n) </p>
	 *
	 * @param value value whose index is searched for in the collection
	 * @return index of the specified value if it exists inside the collection, -1 if there is no such value in the collection.
	 */
	public int indexOf(Object value) {
		//this is a non null collection
		if(value == null) return -1;
		
		//iterate until you find it or reach the end
		int i; ListNode node = first;
		for(i = 0; node != null && node.value != value; node = node.next, i++); 
		
		
		//reached the end without finding it
		if (node == null) return -1;
		
		//found it
		else  return i;
		
	}
	
	/**
	 * Removes the object at the specified position in the linked list. 
	 * 
	 * @param index position of the object to be removed
	 * @throws IndexOutOfBoundsException if position is less than zero or larger or equal to the size of the linked list.
	 */
	public void remove(int index) {
		
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("The index " + index + " is out of bounds. It should be between 0 and " + (size - 1));
		
		ListNode node = first; int i = 0;
		while(i < index && node.next != null) {
			node = node.next;
			i++;
		}

		if(node == first && node == last) {
			first = last = null;
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
	}
	
}
