package untar.fti.strukturdata;

/**
 * Array implementation (expandable) of Linear List
 * @author Carrano, F. M. (2007). "Data Structures and Abstractions with Java", 2nd Edition. Pearson Education.
 *
 * @param <T>
 */
public class AListExpandable<T> implements ListInterface<T>, java.io.Serializable {
	// Array of list entries
	private T[] list;
	
	// Current number of entries in list
	private int	length;
	
	// Maximum length of list
	private static final int MAX_SIZE = 50;
	
	public AListExpandable() {
		this(MAX_SIZE); // call next constructor
	}
	
	public AListExpandable(int maxSize) {
		length = 0;
		list = (T[]) new Object[maxSize]; // necessary cast to generic type
	}

	@Override
	public boolean add(T newEntry) {
		if (isArrayFull())
			doubleArray();
		
		// add new entry after last current entry
		list[length] = newEntry;
		length++;
		
		return true;
	}

	@Override
	public boolean add(int newPosition, T newEntry) {
		boolean isSuccessful = true;
		
		if (!isFull() && (newPosition >= 1) && (newPosition <= length + 1)) {
			makeRoom(newPosition);
			list[newPosition - 1] = newEntry;
			length++;
		}
		else
			isSuccessful = false;
		
		return isSuccessful;
	}

	@Override
	public T remove(int givenPosition) {
		T result = null; // return value
		
		if ((givenPosition >= 1) && (givenPosition <= length)) {
			assert !isEmpty();
			
			result = list[givenPosition - 1]; // get entry to be removed
			
			// move subsequent entries toward entry to be removed,
			// unless it is last in list
			if (givenPosition < length)
				removeGap(givenPosition);
			
			length--;
		}
		
		// return reference to removed entry, or
		// null if either list is empty or givenPosition is invalid
		return result;
	}

	@Override
	public void clear() {
		length = 0;
	}

	@Override
	public boolean replace(int givenPosition, T newEntry) {
		boolean isSuccessful = true;
		
		if ((givenPosition >= 1) && (givenPosition <= length)) {
			assert !isEmpty();
			list[givenPosition - 1] = newEntry;
		}
		else
			isSuccessful = false;
		
		return isSuccessful;
	}

	@Override
	public T getEntry(int givenPosition) {
		T result = null; // result to return
		
		if ((givenPosition >= 1) && (givenPosition <= length)) {
			assert !isEmpty();
			result = list[givenPosition - 1];
		}
		
		return result;
	}

	@Override
	public boolean contains(T anEntry) {
		boolean found = false;
		
		for (int index = 0; !found && (index < length); index++) {
			if (anEntry.equals(list[index]))
				found = true;
		}
		
		return found;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public boolean isEmpty() {
		return length == 0; // or getLength() == 0
	}

	@Override
	public boolean isFull() {
		return length == list.length;
	}

	@Override
	public void display() {
		for (int index = 0; index < length; index++) {
			System.out.println(list[index]);
		}
	}

	/** Task: Makes a room for a new entry at newPosition.
	 *  Precondition: 1 <= newPosition <= length+1;
	 *                length is list's length before addition.
	 */
	private void makeRoom(int newPosition) {
		assert (newPosition >= 1) && (newPosition <= length + 1);
		
		int newIndex = newPosition - 1;
		int lastIndex = length - 1;
		
		// move each entry to next higher index, starting at end of
		// list and continuing until the entry at newIndex is moved
		for (int index = lastIndex; index >= newIndex; index--)
			list[index + 1] = list[index];
	}
	
	/** Task: Shifts entries that are beyond the entry to be removed to the next lower position
	 *  Precondition: 1 <= givenPosition <= length;
	 *                length is list's length before removal
	 */
	private void removeGap(int givenPosition) {
		assert (givenPosition >= 1) && (givenPosition < length);
		
		int removedIndex = givenPosition - 1;
		int lastIndex = length - 1;
		
		for (int index = removedIndex; index < lastIndex; index++)
			list[index] = list[index + 1];
	}
	
	/** Task: Sees whether the array is full.
	 *  @return	true if the array is full, false otherwise
	 */
	private boolean isArrayFull() {
		return length == list.length;
	}
	
	/** Task: Doubles the size of the array of list entries */
	private void doubleArray() {
		T[] oldList = list; // save reference to array of list entries
		int oldSize = oldList.length; // save old max size of array
		
		list = (T[]) new Object[2 * oldSize]; // double size of array
		
		// copy entries from old array to new bigger array
		for (int index = 0; index < oldSize; index++)
			list[index] = oldList[index];
	}
}
