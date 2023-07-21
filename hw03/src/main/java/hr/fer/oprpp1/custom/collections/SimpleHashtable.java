package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A model of a simple hash table.
 * @author Fani
 *
 * @param <K> type of the keys stored inside the table 
 * @param <V> type of the values stored inside the table
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	
	/**
	 * The internal array used for storing the entries.
	 */
	TableEntry<K,V>[] hashTable;
	
	/**
	 * The default capacity of the SimpleHashtable.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * The percentage of the SimpleHashtable that needs to be filled with entries in order
	 * for it to be inefficient and require resizing.
	 */
	private static final double OCCUPANCY_TOO_LARGE = 0.75;
	
	/**
	 * Count the number of structural modifications on the hash table.
	 */
	private long modificationCount = 0;
	
	
	
	
	/**
	 * A helper class for the SimpleHashtable. Represents the entries of the table. 
	 * @author Fani
	 *
	 * @param <K> type of the key
	 * @param <V> type of the value
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * The key of the entry. Can't be <code>null</code>.
		 */
		private K key;
		
		/**
		 * The value of the entry. <code>null</code> is allowed.
		 */
		private V value;
		
		/**
		 * The next entry of a hash table if more are at the same index.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Creates an instance of table entry.
		 * @param key the key of entry
		 * @param value
		 * @param next
		 */
		@SuppressWarnings("unchecked")
		public TableEntry(Object key, Object value, TableEntry<K, V> next) {
			if(key == null) 
				throw new NullPointerException("Key can't be null!");
			
			this.key = (K) key;
			this.value = (V) value;
			this.next = next;
		}
		
		/**
		 * Returns the key of the entry.
		 * @return the key of the entry.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value of the entry.
		 * @return the value of the entry.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Sets the value of the entry to the specified value.
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Returns the string representation of this entry.
		 * @return string representation of this entry.
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}
		
	}
	
	

	
	/**
	 * Creates an instance of SimpleHashTable with the default capacity.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Creates an instance of SimpleHashTable with the given capacity.
	 * @param capacity the given capacity of the table
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) 
			throw new IllegalArgumentException("Capacity can't be " + capacity + ". It must be larger or equal to 1!");
		
		capacity = nearestPower(capacity); //the nearest bigger power of two
		
		hashTable = (TableEntry<K, V>[]) new TableEntry[capacity];
	}
	
	
	/************ PUBLIC METHODS **************/
	
	/**
	 * Places the wanted key and value pair inside SimpleHashtable. If there already exists such key, it overwrites the value and returns the old value.
	 * <p><b>Resizes the whole table if occupancy too large.</b></p>
	 * @param key the key of a new TableEntry
	 * @param value the value of the new TableEntry
	 * @return <code>null</code> if the added TableEntry has a key that has not yet been stored inside the Hashtable, 
	 * otherwise the specified key already exists inside the table and the old value of that entry is returned.
	 */
	public V put(K key, V value) {
		checkOccupancy();
		return add(key, value);
	}
	
	/**
	 * Fetches the value of an entry with the specified key if such exists.
	 * @param key the key of the TableEntry with the wanted value
	 * @return the value of a TableEntry with the specified key if it exists, <code>null</code> otherwise.
	 */
	public V get(Object key) {
		if(key == null) 
			return null;
		
		//calculate the position
		int indexOfKey = Math.abs(key.hashCode() % hashTable.length);
		
		TableEntry<K, V> current = hashTable[indexOfKey];
		
		while(current != null) {
			if(current.getKey().equals(key)) {
				return current.getValue();
			}
			current = current.next;
		}
		
		// haven't found it
		return null;
	}
	
	/**
	 * Determines the number of table entries stored inside the table.
	 * @return the number of table entries stored inside the table.
	 */
	public int size() {
		int size = 0;
		TableEntry<K, V> current;
		
		//iterate through the table
		for (int i = 0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				current = hashTable[i];
				while(current != null) {
					size++;
					current = current.next;
				}
			}
		}
	
		return size;
	}
	
	/**
	 * Checks if the table contains the specified key.
	 * @param key the searched for key 
	 * @return <code>true</code> if the table contains the given key, <code>false</code> otherwise.
	 */
	public boolean containsKey(Object key) {
		if(key == null) 
			return false;
		
		int indexOfKey = Math.abs(key.hashCode() % hashTable.length);
		TableEntry<K, V> current = hashTable[indexOfKey];
		
		while(current != null) {
			if(current.getKey().equals(key)) {
				return true;
			}
			current = current.next;
		}
		
		//haven't found it
		return false;	
	}
	
	/**
	 * Checks if the table contains the specified value. Has a much bigger complexity than containsKey.
	 * @param value the searched for value 
	 * @return <code>true</code> if the table contains the given value, <code>false</code> otherwise.
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> current;
		
		for (int i = 0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				current = hashTable[i];
				
				while(current != null) {
					if(current.getValue() == value || current.getValue() != null && current.getValue().equals(value))
						return true;
					current = current.next;
				}
				
			}
		}
		
		//haven't found it
		return false;
	}
	
	/**
	 * Removes the object from the hash table.
	 * @param key the key of the entry to be deleted
	 * @return the value of the removed entry or null if there is no such entry inside the table.
	 */ 
	public V remove(Object key) {
		return delete(key);
	}
	
	/**
	 * Determines if the hash table is empty.
	 * @return <code>true</code> if the table is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns the string representation of the SimpleHashtable.
	 * @return string representation of the SimpleHashtable.
	 */
	public String toString() {
		int ordinal = 1;
		String string = "[";
		TableEntry<K, V> current;
		
		for (int i = 0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				current = hashTable[i];
				
				while(current != null) {
					string += current.toString();
					
					if(ordinal++ != size()) 
						string += ", ";
					
					current = current.next;
				}
			}
		}
		
		return string + "]";
	}
	
	/**
	 * Returns the hash table in the form of an array.
	 * @return array representation of the hash table.
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray(){
		TableEntry<K, V> current;
		TableEntry<K,V>[] result = (TableEntry<K, V>[]) new TableEntry[size()];
		
		int j = 0; // tracks the index of the new array
		
		for (int i = 0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				current = hashTable[i];
				while(current != null) {
					result[j++] = current;
					current = current.next;
				}
			}
		}
		return result;
	}
	
	/**
	 * Removes all entries from the hash table.
	 */
	public void clear() {
		for (int i = 0; i < hashTable.length; i++) 
			hashTable[i] = null;	
	}
	
	
	
	/************ PRIVATE METHODS **************/
	
	/**
	 * Places the wanted key and value pair inside SimpleHashtable. If there already exists such key, it overwrites the value and returns the old value.
	 * @param key the key of a new TableEntry
	 * @param value the value of the new TableEntry
	 * @return <code>null</code> if the added TableEntry has a key that has not yet been stored inside the Hashtable, 
	 * else the specified key already exists inside the table and the old value of that entry is returned.
	 */
	private V add(K key, V value) {
		if(key == null)
			throw new NullPointerException("The key can't be null!");
		
		int i = Math.abs(key.hashCode() % hashTable.length);
		
		// there aren't any entries in this place yet
		if(hashTable[i] == null) {
			hashTable[i] = new TableEntry<>(key, value, null);
			modificationCount++;
			return null;
		}
		
		//there are one or more entries 
		TableEntry<K, V> current = hashTable[i];
		TableEntry<K, V> previous = hashTable[i];
		
		while(current != null) {
			//overwrite if there already exists such key
			if(current.getKey().equals(key)) {
				V oldValue = current.getValue();
				current.setValue(value);
				return oldValue;
			}
			previous = current;
			current = current.next;
		}
		
		previous.next = new TableEntry<>(key, value, null);
		modificationCount++;
		return null;
	}
	
	/**
	 * Removes the object from the hash table.
	 * @param key the key of the entry to be deleted
	 * @return the value of the removed entry or <code>null</code> if there is no such entry inside the table.
	 */ 
	private V delete(Object key) {
		int i = Math.abs(key.hashCode() % hashTable.length);
		
		TableEntry<K, V> current = hashTable[i];
		TableEntry<K, V> previous = hashTable[i];	
		
		while(current != null) {
			// if its the first one at an index
			if(current == hashTable[i] && current.getKey().equals(key)) {
				V oldValue = current.getValue();
				hashTable[i] = current.next;
				modificationCount++;
				return oldValue;
				
			//not the first one	
			} else if(current.getKey().equals(key)) {
				V oldValue = current.getValue();
				previous.next = current.next;
				modificationCount++;
				return oldValue;
			}
			
			previous = current;
			current = current.next;
		}
		
		return null;
	}
	
	/**
	 * Resizes the internal array of entries if the occupancy is too large.
	 * If occupancy is acceptable, it does nothing.
	 */
	@SuppressWarnings("unchecked")
	private void checkOccupancy() {
		if(occupancyTooLarge()) {
			TableEntry<K,V>[] oldTableArray = this.toArray();
			
			//resizing
			hashTable = (TableEntry<K, V>[]) new TableEntry[hashTable.length * 2];
			
			//adding all entries to the resized SimpleHashtable
			for (int i = 0; i < oldTableArray.length; i++) 
				add(oldTableArray[i].getKey(), oldTableArray[i].getValue());	
			
			modificationCount++;
		}
	}
	
	/**
	 * Determines if the occupancy is too large.
	 * @return <code>true</code> if the occupancy is too large, <code>false</code> otherwise.
	 */
	private boolean occupancyTooLarge() {
		return (1.0 * size()) / hashTable.length >= OCCUPANCY_TOO_LARGE * hashTable.length;
	}
	
	/**
	 * Finds the nearest bigger (or equal) power of 2.
	 * @param num the number whose nearest power of two is searched for
	 * @return the nearest power of 2 that is bigger than the given number or the given number if it itself is a power of 2.
	 */
	private static int nearestPower(int num) {
		int power = 1;
		while(power < num)
		    power *= 2;
		return power;
	}
	
	
	
	/**
	 * Returns a custom hash table iterator.
	 * @return a custom hash table iterator.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(modificationCount);
	}
	
	/**
	 * Implementation of the iterator for the SimpleHashtable.
	 * @author Fani
	 *
	 */
	private class IteratorImpl implements Iterator<TableEntry<K,V>> {
		
		/**
		 * Stores the element fetched by the method next().
		 */
		private TableEntry<K, V> currentEntry;
		
		/**
		 * Counts modifications. Used for determining if changes are happening to the hash table whilst it is iterated over.
		 */
		private long innerModCount;
		
		/**
		 * Determines the index of the currentEntry if it were an array.
		 */
		int currentIndex = 0;
		
		/**
		 * Tracks if the element fetched by next() has been removed.
		 */
		boolean usedTheNext = false;
		
		/**
		 * The current slot position.
		 */
		int slotPosition = 0;
		
		/**
		 * Number of elements stored in the beginning of iteration.
		 */
		int size;

		
		/**
		 * Creates an iterator for the hash table.
		 * @param modificationCount count of changes made on the table
		 */
		public IteratorImpl(long modificationCount) {
			this.innerModCount = modificationCount;
			findNextFilledSlot();
			this.size = size();
		}
		
		/**
		 * Returns true if the iteration has more elements.
		 * (In other words, returns true if next would return an element rather than throwing an exception.)
		 * @throws ConcurrentModificationException if the hash table is changed but not inside of this iterator
		 */
		@Override
		public boolean hasNext() {
			if(innerModCount != SimpleHashtable.this.modificationCount) 
				throw new ConcurrentModificationException("An unsupported change of this list was made during iteration with iterator!");
			
			if(currentIndex < size) 
				return true;
			
			return false;
		}
		
		/**
		 * Returns the next element in the iteration.
		 * @return the next element in the iteration.
		 * @throws NoSuchElementException if there is no more elements (the iteration is over).
		 * @throws ConcurrentModificationException if the hash table is changed but not inside of this iterator
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if(innerModCount != SimpleHashtable.this.modificationCount) 
				throw new ConcurrentModificationException("An unsupported change of this list was made during iteration with iterator!");
			
			if(!hasNext()) 
				throw new NoSuchElementException("No more elements are available.");
			
			//at this slot
			if(currentEntry != null && currentEntry.next != null) {
				currentEntry = currentEntry.next;
				
			//in a different slot
			} else if(slotPosition < SimpleHashtable.this.hashTable.length){
				findNextFilledSlot();
				currentEntry = SimpleHashtable.this.hashTable[slotPosition]; 
				slotPosition++;
			}
			
			currentIndex++;
			usedTheNext = false;
			return currentEntry;
		}
		
		/**
		 * Removes from the hash table the last element returned by this iterator. 
		 * This method can be called only once per call to next. 
		 * @throws ConcurrentModificationException if the hash table is changed but not inside of this iterator
		 * @throws IllegalStateException if it is called more than once on the same next()
		 */
		@Override
		public void remove() {
			if(innerModCount != SimpleHashtable.this.modificationCount) 
				throw new ConcurrentModificationException("An unsupported change of this list was made during iteration with iterator!");
			
			if(usedTheNext)
				throw new IllegalStateException("The last next has already been removed.");
			
			delete(currentEntry.getKey());
			usedTheNext = true;
			
			innerModCount++;
		}
		
		/**
		 * Finds the next non <code>null</code> slot inside the hashTable. 
		 * If the current slot is already not <code>null</code>, this method does nothing.
		 * 
		 * @return index of the next filled slot inside the hashTable.
		 */
		private int findNextFilledSlot() {
			while(SimpleHashtable.this.hashTable[slotPosition] == null)
				slotPosition++;
			return slotPosition;
		}
	}
	
}
