package untar.fti.strukturdata;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> implements SearchTreeInterface<T>, java.io.Serializable {
	
	public AVLTree() {
		super();
	}
	
	public AVLTree(T rootEntry) {
		super(rootEntry);
	}
	
	public T add(T newEntry) {
		T result = null;
		
		if (isEmpty())
			setRootNode(new BinaryNode<T>(newEntry));
		else {
			BinaryNodeInterface<T> rootNode = getRootNode();
			result = addEntry(rootNode, newEntry);
			setRootNode(rebalance(rootNode));
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
			if (rootNode.hasLeftChild()) {
				BinaryNodeInterface<T> leftChild = rootNode.getLeftChild();
				result = addEntry(leftChild, newEntry);
				rootNode.setLeftChild(rebalance(leftChild));
			}
			else
				rootNode.setLeftChild(new BinaryNode<T>(newEntry));
		}
		else {
			assert comparison > 0;
			
			if (rootNode.hasRightChild()) {
				BinaryNodeInterface<T> rightChild = rootNode.getRightChild();
				result = addEntry(rightChild, newEntry);
				rootNode.setRightChild(rebalance(rightChild));
			}
			else
				rootNode.setRightChild(new BinaryNode<T>(newEntry));
		}
		
		return result;
	}
	
	/**
	 * Task: Corrects an imbalance at the node closest to a structural change
	 *       in the left subtree of the node's left child.
	 * @param nodeN		a node, closest to the newly added leaf, at which
	 * 					an imbalance occurs and that has a left child.
	 * @return
	 */
	private BinaryNodeInterface<T> rotateRight(BinaryNodeInterface<T> nodeN) {
		BinaryNodeInterface<T> nodeC = nodeN.getLeftChild();
		
		nodeN.setLeftChild(nodeC.getRightChild());
		nodeC.setRightChild(nodeN);
		
		return nodeC;
	}
	
	/**
	 * Task: Corrects an imbalance at the node closest to a structural change
	 *       in the right subtree of the node's right child.
	 * @param nodeN		a node, closest to the newly added leaf, at which
	 * 					an imbalance occurs and that has a right child.
	 * @return
	 */
	private BinaryNodeInterface<T> rotateLeft(BinaryNodeInterface<T> nodeN) {
		BinaryNodeInterface<T> nodeC = nodeN.getRightChild();
		
		nodeN.setRightChild(nodeC.getLeftChild());
		nodeC.setLeftChild(nodeN);
		
		return nodeC;
	}

	/**
	 * Task: Corrects an imbalance at the node closest to a structural
	 *       change in the left subtree of the node's right child.
	 * @param nodeN		a node, closest to the newly added leaf, at which
	 * 					an imbalance occurs and that has a right child.
	 * @return
	 */
	private BinaryNodeInterface<T> rotateRightLeft(BinaryNodeInterface<T> nodeN) {
		BinaryNodeInterface<T> nodeC = nodeN.getRightChild();
		nodeN.setRightChild(rotateRight(nodeC));
		return rotateLeft(nodeN);
	}
	
	/**
	 * Task: Corrects an imbalance at the node closest to a structural
	 *       change in the right subtree of the node's left child.
	 * @param nodeN		a node, closest to the newly added leaf, at which
	 * 					an imbalance occurs and that has a left child.
	 * @return
	 */
	private BinaryNodeInterface<T> rotateLeftRight(BinaryNodeInterface<T> nodeN) {
		BinaryNodeInterface<T> nodeC = nodeN.getLeftChild();
		nodeN.setLeftChild(rotateLeft(nodeC));
		return rotateRight(nodeN);
	}
	
	private BinaryNodeInterface<T> rebalance(BinaryNodeInterface<T> nodeN) {
		int heightDifference = getHeightDifference(nodeN);
		
		if (heightDifference > 1) {
			// left subtree is taller by more than 1,
			// so addition was in left subtree
			if (getHeightDifference(nodeN.getLeftChild()) > 0)
				// addition was in left subtree of left child
				nodeN = rotateRight(nodeN);
			else
				// addition was in right subtree of left child
				nodeN = rotateLeftRight(nodeN);
		}
		else if (heightDifference < -1) {
			// right subtree is taller by more than 1
			// so addition was in right subtree
			if (getHeightDifference(nodeN.getRightChild()) < 0)
				// addition was in right subtree of right child
				nodeN = rotateLeft(nodeN);
			else
				// addition was in left subtree of right child
				nodeN = rotateRightLeft(nodeN);
		}
		
		return nodeN;
	}
	
	private int getHeightDifference(BinaryNodeInterface<T> nodeN) {
		int leftHeight = nodeN.getLeftChild().getHeight();
		int rightHeight = nodeN.getRightChild().getHeight();
		return leftHeight - rightHeight;
	}
}
