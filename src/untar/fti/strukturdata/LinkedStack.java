package untar.fti.strukturdata;

/** 
 * Linked list implementation of the ADT Stack
 * @author Carrano, F. M. (2007). "Data Structures and Abstractions with Java", 2nd Edition. Pearson Education.
 *
 * @param <T>
 */
public class LinkedStack<T> implements StackInterface<T>, java.io.Serializable {
	
	private Node topNode; // references the first node in the chain
	
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
	
	public LinkedStack() {
		topNode = null;
	}

	@Override
	public void push(T newEntry) {
		Node newNode = new Node(newEntry, topNode);
		topNode = newNode;
	}

	@Override
	public T pop() {
		T top = peek();
		
		if (topNode != null)
			topNode = topNode.getNextNode();
		
		return top;
	}

	@Override
	public T peek() {
		T top = null;
		
		if (topNode != null)
			top = topNode.getData();
		
		return top;
	}

	@Override
	public boolean isEmpty() {
		return topNode == null;
	}

	@Override
	public void clear() {
		topNode = null;
	}

}
