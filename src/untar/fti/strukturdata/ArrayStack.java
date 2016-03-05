package untar.fti.strukturdata;

/** 
 * Array implementation of the ADT Stack
 * @author Carrano, F. M. (2007). "Data Structures and Abstractions with Java", 2nd Edition. Pearson Education.
 *
 * @param <T>
 */
public class ArrayStack<T> implements StackInterface<T>, java.io.Serializable {
	
	private T[]	stack;
	private	int	topIndex;
	private static final int DEFAULT_INITIAL_CAPACITY = 50;
	
	public ArrayStack() {
		this(DEFAULT_INITIAL_CAPACITY);
	}
	
	public ArrayStack(int initialCapacity) {
		stack = (T[]) new Object[initialCapacity];
		topIndex = -1;
	}

	@Override
	public void push(T newEntry) {
		topIndex++;
		
		if (topIndex >= stack.length)
			doubleArray();
		
		stack[topIndex] = newEntry;
	}

	@Override
	public T pop() {
		T top = null;
		
		if (!isEmpty()) {
			top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
		}
		
		return top;
	}

	@Override
	public T peek() {
		T top = null;
		
		if (!isEmpty())
			top = stack[topIndex];
		
		return top;
	}

	@Override
	public boolean isEmpty() {
		return topIndex < 0;
	}

	@Override
	public void clear() {
		topIndex = -1;
	}
	
	/** Task: Doubles the size of the array of stack entries */
	private void doubleArray() {
		T[] oldStack = stack; // save reference to array of stack entries
		int oldSize = oldStack.length; // save old max size of array
		
		stack = (T[]) new Object[2 * oldSize]; // double size of array
		
		// copy entries from old array to new bigger array
		for (int index = 0; index < oldSize; index++)
			stack[index] = oldStack[index];
	}

}
