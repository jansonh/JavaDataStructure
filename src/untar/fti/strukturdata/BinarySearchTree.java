package untar.fti.strukturdata;

public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> implements SearchTreeInterface<T>, java.io.Serializable {
	
	public BinarySearchTree() {
		super();
	}
	
	public BinarySearchTree(T rootEntry) {
		super();
		setRootNode(new BinaryNode<T>(rootEntry));
	}
	
	public void setTree(T rootData) { // disable setTree (Carrano, pp.703)
		throw new UnsupportedOperationException();
	}
	
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) { // disable setTree (Carrano, pp.703)
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(T entry) {
		return getEntry(entry) != null;
	}

	@Override
	public T getEntry(T entry) {
		return findEntry(getRootNode(), entry);
	}

	@Override
	public T add(T newEntry) {
		T result = null;
		
		if (isEmpty())
			setRootNode(new BinaryNode<T>(newEntry));
		else
			result = addEntry(getRootNode(), newEntry);
		
		return result;
	}

	@Override
	public T remove(T entry) {
		T result = null;
		
		// locate node (and its parent that contains a match for entry)
		NodePair pair = findNode(entry);
		BinaryNodeInterface<T> currentNode = pair.getFirst();
		BinaryNodeInterface<T> parentNode = pair.getSecond();
		
		if (currentNode != null) { // entry is found
			result = currentNode.getData(); // get entry to be removed
			
			// Case 1: currentNode has two children
			if (currentNode.hasLeftChild() && currentNode.hasRightChild()) {
				// replace entry in currentNode with the entry in another node
				// that has at most one child; that node can be deleted
				
				// get node to remove (contains inorder predecessor; has at most
				// one child) and its parent
				pair = getNodeToRemove(currentNode);
				BinaryNodeInterface<T> nodeToRemove = pair.getFirst();
				parentNode = pair.getSecond();
				
				// copy entry from nodeToRemove to currentNode
				currentNode.setData(nodeToRemove.getData());
				
				currentNode = nodeToRemove;
			}
			
			// Case 2: currentNode has at most on child; delete it
			removeNode(currentNode, parentNode);
		}
		
		return result;
	}
	
	private T findEntry(BinaryNodeInterface<T> rootNode, T entry) {
		T result = null;
		
		if (rootNode != null) {
			T rootEntry = rootNode.getData();
			
			if (entry.equals(rootEntry))
				result = rootEntry;
			else if (entry.compareTo(rootEntry) < 0)
				result = findEntry(rootNode.getLeftChild(), entry);
			else
				result = findEntry(rootNode.getRightChild(), entry);
		}
		
		return result;
	}
	
	private T addEntry(BinaryNodeInterface<T> rootNode, T newEntry) {
		assert rootNode != null;
		
		T result = null;
		int comparison = newEntry.compareTo(rootNode.getData());
		
		if (comparison == 0) {
			result = rootNode.getData();
			rootNode.setData(newEntry);
		}
		else if (comparison < 0) {
			if (rootNode.hasLeftChild())
				result = addEntry(rootNode.getLeftChild(), newEntry);
			else
				rootNode.setLeftChild(new BinaryNode<T>(newEntry));
		}
		else {
			assert comparison > 0;
			
			if (rootNode.hasRightChild())
				result = addEntry(rootNode.getRightChild(), newEntry);
			else
				rootNode.setRightChild(new BinaryNode<T>(newEntry));
		}
		
		return result;
	}
	
	private NodePair findNode(T entry) {
		NodePair result = new NodePair();
		boolean found = false;
		
		BinaryNodeInterface<T> currentNode = getRootNode();
		BinaryNodeInterface<T> parentNode = null;
		
		while ((currentNode != null) && (currentNode.getData() != entry)) {
			int cmp = currentNode.getData().compareTo(entry);
			
			if (cmp != 0) {
				parentNode = currentNode;
				if (cmp < 0)
					currentNode = currentNode.getLeftChild();
				else
					currentNode = currentNode.getRightChild();
			}
				
		}
		
		found = (currentNode.getData() == entry);
		if (found)
			result = new NodePair(currentNode, parentNode);
		
		return result;
	}
	
	private NodePair getNodeToRemove(BinaryNodeInterface<T> currentNode) {
		// find node with largest entry in left subtree by 
		// moving as far right in the subtree as possible
		BinaryNodeInterface<T> leftSubtreeRoot = currentNode.getLeftChild();
		BinaryNodeInterface<T> rightChild = leftSubtreeRoot;
		BinaryNodeInterface<T> priorNode = currentNode;
		
		while (rightChild.hasRightChild()) {
			priorNode = rightChild;
			rightChild = rightChild.getRightChild();
		}
		
		return new NodePair(rightChild, priorNode);
	}
	
	private void removeNode(BinaryNodeInterface<T> nodeToRemove, BinaryNodeInterface<T> parentNode) {
		BinaryNodeInterface<T> childNode;
		
		if (nodeToRemove.hasLeftChild())
			childNode = nodeToRemove.getLeftChild();
		else
			childNode = nodeToRemove.getRightChild();
		
		assert (nodeToRemove.isLeaf() && childNode == null) || !nodeToRemove.isLeaf();
		
		if (nodeToRemove == getRootNode())
			setRootNode(childNode);
		else if (parentNode.getLeftChild() == nodeToRemove)
			parentNode.setLeftChild(childNode);
		else
			parentNode.setRightChild(childNode);
	}
	
	private class NodePair {
		private BinaryNodeInterface<T> first;
		private BinaryNodeInterface<T> second;
		
		public NodePair() {
		}
		
		public NodePair(BinaryNodeInterface<T> first, BinaryNodeInterface<T> second) {
			this.first = first;
			this.second = second;
		}
		
		public BinaryNodeInterface<T> getFirst() {
			return first;
		}
		
		public BinaryNodeInterface<T> getSecond() {
			return second;
		}
	}
}
