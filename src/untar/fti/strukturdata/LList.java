package untar.fti.strukturdata;

/**
 * Linked implementation of Linear List
 * @author Carrano, F. M. (2007). "Data Structures and Abstractions with Java", 2nd Edition. Pearson Education.
 *
 * @param <T>
 */
public class LList<T> implements ListInterface<T>, java.io.Serializable {
	
	private Node	firstNode; // head reference to first node
	private Node	lastNode;  // tail reference to last node
	private int		length;	   // number of entries in list
	
	// inner class
	private class Node {
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
	
	public LList() {
		clear();
	}

	@Override
	public boolean add(T newEntry) {
		Node newNode = new Node(newEntry);
		
		if (isEmpty()) 
			firstNode = newNode;
		else {
			lastNode.setNextNode(newNode);
		}
		
		lastNode = newNode;
		length++;
		
		return true;
	}

	@Override
	public boolean add(int newPosition, T newEntry) {
		boolean isSuccessful = true;
		
		if ((newPosition >= 1) && (newPosition <= length+1)) {
			Node newNode = new Node(newEntry);
			
			if (isEmpty()) {
				firstNode = newNode;
				lastNode = newNode;
			}
			else if (newPosition == 1) {
				newNode.setNextNode(firstNode);
				firstNode = newNode;
			}
			else if (newPosition == length+1) {
				lastNode.setNextNode(newNode);
				lastNode = newNode;
			}
			else {
				Node nodeBefore = getNodeAt(newPosition - 1);
				Node nodeAfter = nodeBefore.next;
				newNode.setNextNode(nodeAfter);
				nodeBefore.setNextNode(newNode);
			}
			
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
			
			if (givenPosition == 1) { // case 1: remove first entry
				result = firstNode.getData(); // save entry to be removed
				firstNode = firstNode.next;
				if (length == 1)
					lastNode = null;
			}
			else { // case 2: givenPosition > 1
				Node nodeBefore = getNodeAt(givenPosition - 1);
				Node nodeToRemove = nodeBefore.next;
				Node nodeAfter = nodeToRemove.next;
				
				nodeBefore.setNextNode(nodeAfter); // disconnect the node to be removed
				result = nodeToRemove.data; // save entry to be removed
				
				if (givenPosition == length)
					lastNode = nodeBefore;
			}
			
			length--;
		}
		
		return result;
	}

	@Override
	public void clear() {
		firstNode = null;
		lastNode = null;
		length = 0;
	}

	@Override
	public boolean replace(int givenPosition, T newEntry) {
		boolean isSuccessful = true;
		
		if ((givenPosition >= 1) && (givenPosition <= length)) {
			assert !isEmpty();
			
			Node desiredNode = getNodeAt(givenPosition);
			desiredNode.setData(newEntry);
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
			
			result = getNodeAt(givenPosition).data;
		}
		
		return result;
	}

	@Override
	public boolean contains(T anEntry) {
		boolean found = false;
		Node currentNode = firstNode;
		
		while (!found && (currentNode != null)) {
			if (anEntry.equals(currentNode.data))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		}
		
		return found;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		boolean result;
		
		if (length == 0) {
			assert firstNode == null;
			result = true;
		}
		else {
			assert firstNode != null;
			result = false;
		}
		
		return result;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public void display() {
		Node currentNode = firstNode;
		
		while (currentNode != null) {
			System.out.println(currentNode.data);
			currentNode = currentNode.getNextNode();
		}
	}

	/** Task: Returns a reference to the node at a givenPosition.
	 *  Precondition: List is not empty; 1 <= givenPosition <= length.
	 */
	private Node getNodeAt(int givenPosition) {
		assert !isEmpty() && (1 <= givenPosition) && (givenPosition <= length);
		
		Node currentNode = firstNode;
		
		// traverse the list to locate the desired node
		for (int counter = 1; counter < givenPosition; counter++)
			currentNode = currentNode.getNextNode();
		
		assert currentNode != null;
		
		return currentNode;
	}
}