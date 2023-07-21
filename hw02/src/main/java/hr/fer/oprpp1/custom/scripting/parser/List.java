package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Models a list of objects that can fetch an object from, insert an object at, and remove an object from a certain index. It can also find an index of the object inside the list.
 * @author Fani
 *
 */
public interface List extends Collection {
	
	/**
	 * Returns an object that is stored in the list at the specified index. 
	 * 
	 * @param index position at which is the object which is returned
	 * @return object that is stored in the list at position index.
	 * @throws IndexOutOfBoundsException if index is not in the proper range:  <code>0 <= index < sizeOfTheList</code>.
	 */
	Object get(int index);
	
	/**
	 * Places the specified object at the specified position in the list. If there are objects at larger index inside the list than the specified position, they move forward one position to make space for the new object.
	 * 
	 * @param value object to be put on the position inside the collection
	 * @param position index inside the collection at which the value is put
	 * @throws NullPointerException if value is <code>null</code> 
	 * @throws IndexOutOfBoundsException if position is not in the proper range: 0 <= index < sizeOfTheList.
	 */
	void insert(Object value, int position);
	
	/**
	 * Searches the list and returns the index of the first occurrence of the specified object.
	 * 
	 * @param value value whose index is searched for in the collection
	 * @return index of the specified object inside the list if it exists inside the collection, -1 if there is no such value in the collection.
	 */
	int indexOf(Object value);
	

	/**
	 * Removes the object at the specified position in the list.  If there are objects at larger index inside the list than the specified position, they move backwards to fill the new empty position.
	 * @param index position of the object to be removed
	 * @throws IndexOutOfBoundsException if position is not in the proper range: 0 <= index < sizeOfTheList.
	 */
	void remove(int index);

}
