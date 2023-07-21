package hr.fer.zemris.lsystems.impl;

/**
 * A simple model of a dictionary.
 * @author Fani
 *
 * @param <K> type of the keys stored in the dictionary
 * @param <V> type of the values stored in the dictionary
 */
public class Dictionary<K,V> {
	
	/**
	 * The internal structure which stores entries of this dictionary.
	 */
	private ArrayIndexedCollection<Entry<K,V>> internal;
	
	/**
	 * A simple model of a pair consisting of key and value. Keys can't be null.
	 * @author Fani
	 *
	 * @param <K> type of the key
	 * @param <V> type of the value
	 */
	private static class Entry<K,V> {
		/**
		 * The key of the entry.
		 */
		private K key;
		
		/**
		 * The value of the entry.
		 */
		private V value;
		
		/**
		 * Creates an instance of an entry from specified key and value.
		 * @param key the key of the entry
		 * @param value the value of the entry
		 * @throws NullPointerException if the key is null
		 */
		public Entry(K key, V value) {
			super();
			if(key == null) throw new NullPointerException("Key can't be null!");
			this.key = key;
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}
		
		/**
		 * Determines if two entries are equal. Two entries are equal if their keys are equal.
		 * @param obj the specified object that is to be determined if equal
		 * @return <code>true</code> if the entries are equal, <code>false</code> otherwise.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Entry))
				return false;
			Entry<K,V> other = (Entry<K,V>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

	}
	
	/**
	 * Creates instance of an empty dictionary. The initial capacity is set to the inital capacity of 
	 * an ArrayIndexedCollection.
	 */
	Dictionary() {
		internal = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Determines if the dictionary has no entries. 
	 * @return <code>true</code> if the dictionary has no entries, <code>false</code> otherwise.
	 */
	boolean isEmpty() {
		return internal.isEmpty();
	}
	
	/**
	 * Determines the number of entries inside the dictionary.
	 * @return the number of entires inside the dictionary.
	 */
	int size() {
		return internal.size();
	}
	
	/**
	 * Removes all entries from a dictionary.
	 */
	void clear() {
		internal.clear();
	}
	
	/**
	 * Puts an entry of the given key and value into the dictionary. If there already exists another
	 * entry with the same key, the current value is overwritten with the new one. If there is no pre-existing
	 * entry with the same key inside the dictionary, a new entry is simply added to the dictionary.
	 * @param key the key of the entry to be put inside the dictionary
	 * @param value the value of the entry to be put inside the dictionary
	 * @return the value which is put inside the dictionary.
	 */
	V put(K key, V value) {
		Entry<K, V> toBePut = new Entry<>(key, value);
		
		// if there already exists this entry, overwrite it 
		if(internal.contains(toBePut)) {
			int index = internal.indexOf(toBePut);
			internal.remove(index);
			internal.insert(toBePut, index);
			return value;
		} 
		// else add it to the dictionary
		internal.add(new Entry<>(key, value));
		return value;
	}
	
	/**
	 * Fetches the value of an entry with the specified key.
	 * @param key the key of an entry whose value is returned
	 * @return value of the entry with the specified key or <code>null</code> if there is no entry with such key.
	 */
	@SuppressWarnings("unchecked")
	V get(Object key) {
		Entry<K, V> toBeFetched = new Entry<>((K)key, null);
		
		if(internal.contains(toBeFetched)) {
			return internal.get(internal.indexOf(toBeFetched)).value; 
		}
		
		return null;
	}
	
	/**
	 * Removes an entry of a specified key.
	 * @param key
	 * @return the value of the entry that is removed or <code>null</code> if there is no entry with such key inside the dictionary.
	 */
	V remove(K key){
		Entry<K, V> toBeRemoved = new Entry<>(key, null);
		
		int indexOfRemoval = internal.indexOf(toBeRemoved);
		
		//if there's no such element
		if(indexOfRemoval < 0)
			return null;
		
		//else remember the value and remove it
		V returnValue = internal.get(indexOfRemoval).value;
		internal.remove(indexOfRemoval);
		return returnValue;
	}

}


