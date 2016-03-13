package untar.fti.strukturdata;

/** 
 * Array implementation of the ADT Queue
 * @author Carrano, F. M. (2007). "Data Structures and Abstractions with Java", 2nd Edition. Pearson Education.
 *
 * @param <T>
 */
public class ArrayQueue<T> implements QueueInterface<T>, java.io.Serializable {
	private T[]	queue;
	
	private int frontIndex;
	private int backIndex;
	private static final int DEFAULT_INITIAL_CAPACITY = 50;
	
	public ArrayQueue() {
		this(DEFAULT_INITIAL_CAPACITY);
	}
	
	public ArrayQueue(int initialCapacity) {
		queue = (T[]) new Object[initialCapacity + 1];
		frontIndex = 0;
		backIndex = initialCapacity;
	}

	@Override
	public void enqueue(T newEntry) {
		if (isArrayFull()) 
			doubleArray();
		
		backIndex = (backIndex + 1) % queue.length;
		queue[backIndex] = newEntry;
	}

	@Override
	public T dequeue() {
		T front = null;
		
		if (!isEmpty()) {
			front = queue[frontIndex];
			queue[frontIndex] = null;
			frontIndex = (frontIndex + 1) % queue.length;
		}
		
		return front;
	}

	@Override
	public T getFront() {
		T front = null;
		
		if (!isEmpty()) 
			front = queue[frontIndex];
		
		return front;
	}

	@Override
	public boolean isEmpty() {
		return frontIndex == ((backIndex + 1) % queue.length);
	}

	@Override
	public void clear() {
		frontIndex = 0;
		backIndex = queue.length - 1;
	}
	
	private boolean isArrayFull() {
		return frontIndex == ((backIndex + 2) % queue.length);
	}
	
	private void doubleArray() {
		T[] oldQueue = queue;
		int oldSize = oldQueue.length;
		
		queue = (T[]) new Object[2 * oldSize];
		
		for (int index = 0; index < oldSize - 1; index++) {
			queue[index] = oldQueue[frontIndex];
			frontIndex = (frontIndex + 1) % oldSize;
		}
		
		frontIndex = 0;
		backIndex = oldSize - 2;
	}
}
