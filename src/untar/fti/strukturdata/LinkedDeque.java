package untar.fti.strukturdata;

/** 
 * Doubly linked implementation of Deque
 * @author Carrano, F. M. (2007). "Data Structures and Abstractions with Java", 2nd Edition. Pearson Education.
 *
 * @param <T>
 */
public class LinkedDeque<T> implements DequeInterface<T>, java.io.Serializable {
	
	private DLNode firstNode; // references node for front of deque
	private DLNode lastNode;  // references node for back of deque
	
	private class DLNode implements java.io.Serializable {
		private T		data; // deque entry
		private DLNode 	next; // link to next node
		private DLNode	previous; // link to previous node
		
		private DLNode(DLNode previousNode, T newData, DLNode nextNode) {
			previous = previousNode;
			data = newData;
			next = nextNode;
		}
		
		private T getData() {
			return data;
		}
		
		private void setData(T newData) {
			data = newData;
		}
		
		private DLNode getNextNode() {
			return next;
		}
		
		private void setNextNode(DLNode nextNode) {
			next = nextNode;
		}
		
		private DLNode getPreviousNode() {
			return previous;
		}
		
		private void setPreviousNode(DLNode previousNode) {
			previous = previousNode;
		}
	}
	
	public LinkedDeque() {
		firstNode = null;
		lastNode = null;
	}

	@Override
	public void addToFront(T newEntry) {
		DLNode newNode = new DLNode(null, newEntry, firstNode);
		
		if (isEmpty())
			lastNode = newNode;
		else
			firstNode.setPreviousNode(newNode);
		
		firstNode = newNode;
	}

	@Override
	public void addToBack(T newEntry) {
		DLNode newNode = new DLNode(lastNode, newEntry, null);
		
		if (isEmpty())
			firstNode = newNode;
		else
			lastNode.setNextNode(newNode);
		
		lastNode = newNode;
	}

	@Override
	public T removeFront() {
		T front = null;
		
		if (isEmpty()) {
			front = firstNode.getData();
			firstNode = firstNode.getNextNode();
			
			if (firstNode == null) 
				lastNode = null;
			else
				firstNode.setPreviousNode(null);
		}
		
		return front;
	}

	@Override
	public T removeBack() {
		T back = null;
		
		if (!isEmpty()) {
			back = lastNode.getData();
			lastNode = lastNode.getPreviousNode();
			
			if (lastNode == null)
				firstNode = null;
			else
				lastNode.setNextNode(null);
		}
		
		return back;
	}

	@Override
	public T getFront() {
		T front = null;
		
		if (!isEmpty())
			front = firstNode.getData();
		
		return front;
	}

	@Override
	public T getBack() {
		T back = null;
		
		if (!isEmpty())
			back = lastNode.getData();
		
		return back;
	}

	@Override
	public boolean isEmpty() {
		return (firstNode == null) && (lastNode == null);
	}

	@Override
	public void clear() {
		firstNode = null;
		lastNode = null;
	}

}
