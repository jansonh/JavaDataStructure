package untar.fti.strukturdata;

public class MaxHeap<T extends Comparable<? super T>> implements MaxHeapInterface<T>, java.io.Serializable {

	private T[] heap;		// array of heap entries
	private int lastIndex;	// index of last entry
	
	private static final int DEFAULT_INITIAL_CAPACITY = 25;
	
	public MaxHeap() {
		this(DEFAULT_INITIAL_CAPACITY);
	}
	
	public MaxHeap(int initialCapacity) {
		heap = (T[]) new Comparable[initialCapacity + 1];
		lastIndex = 0;
	}
	
	public MaxHeap(T[] entries) {
		heap = (T[]) new Comparable[entries.length + 1];
		lastIndex = entries.length;
		
		// copy given array to data field
		for (int index = 0; index < entries.length; index++)
			heap[index + 1] = entries[index];
		
		// create heap
		for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--) 
			reheap(rootIndex);
	}
	
	@Override
	public void add(T newEntry) {
		lastIndex++;
		
		if (lastIndex >= heap.length)
			throw new ArrayIndexOutOfBoundsException();
			//doubleArray(); // should implement array expansion!
		
		int newIndex = lastIndex;
		int parentIndex = newIndex / 2;
		
		while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0) {
			heap[newIndex] = heap[parentIndex];
			
			newIndex = parentIndex;
			parentIndex = newIndex / 2;
		}
		
		heap[newIndex] = newEntry;
	}

	@Override
	public T removeMax() {
		T root = null;
		
		if (!isEmpty()) {
			root = heap[1];
			heap[1] = heap[lastIndex];
			lastIndex--;
			reheap(1);
		}
		
		return root;
	}

	@Override
	public T getMax() {
		T root = null;
		
		if (!isEmpty()) 
			root = heap[1];
		
		return root;
	}

	@Override
	public boolean isEmpty() {
		return lastIndex < 1;
	}

	@Override
	public int getSize() {
		return lastIndex;
	}

	@Override
	public void clear() {
		for(; lastIndex > -1; lastIndex--) {
			heap[lastIndex] = null;
		}
		lastIndex = 0;
	}
	
	private void reheap(int rootIndex) {
		boolean done = false;
		T orphan = heap[rootIndex];
		int leftChildIndex = 2 * rootIndex;
		
		while (!done && (leftChildIndex <= lastIndex)) {
			int largerChildIndex = leftChildIndex; // assume larger
			int rightChildIndex = leftChildIndex + 1;
			
			if ((rightChildIndex <= lastIndex) && heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
				largerChildIndex = rightChildIndex;
			}
			
			if (orphan.compareTo(heap[largerChildIndex]) < 0) {
				heap[rootIndex] = heap[largerChildIndex];
				rootIndex = largerChildIndex;
				leftChildIndex = 2 * rootIndex;
			}
			else
				done = true;
		}
		
		heap[rootIndex] = orphan;
	}

}
