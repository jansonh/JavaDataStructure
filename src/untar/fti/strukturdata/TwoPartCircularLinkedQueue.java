package untar.fti.strukturdata;

/** 
 * Implementation of the Two Part Circular Linked Queue
 * @author Carrano, F. M. (2007). "Data Structures and Abstractions with Java", 2nd Edition. Pearson Education.
 *
 * @param <T>
 */
public class TwoPartCircularLinkedQueue<T> implements QueueInterface<T>, java.io.Serializable {
	private Node queueNode;
	private Node freeNode;
	
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
	
	public TwoPartCircularLinkedQueue() {
		freeNode = new Node(null, null);
		freeNode.setNextNode(freeNode);;
		queueNode = freeNode;
	}
	
	@Override
	public void enqueue(T newEntry) {
		freeNode.setData(newEntry);
		
		if (isChainFull()) {
			// allocate a new node and insert it after the node that freeNode references
			Node newNode = new Node(null, freeNode.getNextNode());
			freeNode.setNextNode(newNode);
		}
		
		freeNode = freeNode.getNextNode();
	}

	@Override
	public T dequeue() {
		T front = null;
		
		if (!isEmpty()) {
			front = queueNode.getData();
			queueNode.setData(null);;
			queueNode = queueNode.getNextNode();
		}
		
		return front;
	}

	@Override
	public T getFront() {
		T front = null;
		
		if (!isEmpty())
			front = queueNode.getData();
		
		return front;
	}

	@Override
	public boolean isEmpty() {
		return queueNode == freeNode;
	}

	@Override
	public void clear() {
		queueNode = freeNode;
	}

	private boolean isChainFull() {
		return queueNode == freeNode.getNextNode();
	}
}
