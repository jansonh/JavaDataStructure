package untar.fti.strukturdata;

/** 
 * Linked list implementation of the ADT Queue
 * @author Carrano, F. M. (2007). "Data Structures and Abstractions with Java", 2nd Edition. Pearson Education.
 *
 * @param <T>
 */
public class LinkedQueue<T> implements QueueInterface<T>, java.io.Serializable {
	
	private Node firstNode; // references node at front of queue
	private Node lastNode;  // references node at back of queue
	
	// inner class
	private class Node implements java.io.Serializable {
		private T		data; // entry in list
		private Node	next; // link to next node
		
		private Node(T dataPortion) {
			data = dataPortion;
			next = null;
		}
		
		private Node(T dataPortion, Node nextNode) {
			data = dataPortion;
			next = nextNode;
		}
		
		private T getData() {
			return data;
		}
		
		private void setData(T newData) {
			data = newData;
		}
		
		private Node getNextNode() {
			return next;
		}
		
		private void setNextNode(Node nextNode) {
			next = nextNode;
		}
	}
	
	public LinkedQueue() {
		firstNode = null;
		lastNode = null;
	}

	@Override
	public void enqueue(T newEntry) {
		Node newNode = new Node(newEntry, null);
		
		if (isEmpty())
			firstNode = newNode;
		else
			lastNode.setNextNode(newNode);
		
		lastNode = newNode;
	}

	@Override
	public T dequeue() {
		T front = null;
		
		if (!isEmpty()) {
			front = firstNode.getData();
			firstNode = firstNode.getNextNode();
			
			if (firstNode == null)
				lastNode = null;
		}
		
		return front;
	}

	@Override
	public T getFront() {
		T front = null;
		
		if (!isEmpty())
			front = firstNode.getData();
		
		return front;
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
