import java.util.Iterator;

import untar.fti.strukturdata.*;

public class Main {
	public static void main(String[] args) {
		testAList();
		testAListExpandable();
		testLList();
		
		testLinkedStack();
		testArrayStack();
		
		testLinkedQueue();
		testArrayQueue();
		
		testBinaryTree();
		testBinarySearchTree();
	}

	public static void testAList() {
		System.out.println("\n== Testing AList");
		ListInterface<String> runnerList = new AList<String>();
		
		runnerList.add("16");
		runnerList.add(" 4");
		runnerList.add("33");
		runnerList.add("27");
		runnerList.display();
	}
	
	public static void testAListExpandable() {
		System.out.println("\n== Testing AListExpandable");

		ListInterface<String> runnerList = new AListExpandable<String>();
		
		runnerList.add("16");
		runnerList.add(" 4");
		runnerList.add("33");
		runnerList.add("27");
		runnerList.display();
	}
	
	public static void testLList() {
		System.out.println("\n== Testing LList");

		ListInterface<String> myList = new LList<String>();
		
		System.out.println("List should be empty; isEmpty() returns " + myList.isEmpty());
		
		System.out.println("Testing add to end:");
		System.out.println("Add 15: returns " + myList.add("15"));
		System.out.println("List should not be empty; isEmpty() returns " + myList.isEmpty());
		
		System.out.println("Add 25: returns " + myList.add("25"));
		System.out.println("Add 35: returns " + myList.add("35"));
		System.out.println("Add 45: returns " + myList.add("45"));
		System.out.println("List should not be empty; isEmpty() returns " + myList.isEmpty());
		
		System.out.println("List should contain 15 25 35 45");
		System.out.println("Testing display()");
		myList.display();
		
		System.out.println("Testing clear()");
		myList.clear();
		
		System.out.println("List should be empty; isEmpty() returns " + myList.isEmpty());

		System.out.println("Testing display()");
		myList.display();
	}
	
	public static void testLinkedStack() {
		System.out.println("\n== Testing LinkedStack");
		
		StackInterface<String> myStack = new LinkedStack<String>();
		myStack.push("Jim");
		myStack.push("Jess");
		myStack.push("Jill");
		myStack.push("Jane");
		myStack.push("Joe");
		
		String top = myStack.peek(); // Joe
		System.out.println(top + " is at the top of the stack.");
		
		top = myStack.pop(); // Joe
		System.out.println(top + "is removed from the stack.");
		
		top = myStack.peek(); // Jane
		System.out.println(top + " is at the top of the stack.");
		
		top = myStack.pop(); // Jane
		System.out.println(top + " is removed from the stack.");
	}
	
	public static void testArrayStack() {
		System.out.println("\n== Testing ArrayStack");
		
		StackInterface<String> myStack = new ArrayStack<String>();
		myStack.push("Jim");
		myStack.push("Jess");
		myStack.push("Jill");
		myStack.push("Jane");
		myStack.push("Joe");
		
		String top = myStack.peek(); // Joe
		System.out.println(top + " is at the top of the stack.");
		
		top = myStack.pop(); // Joe
		System.out.println(top + "is removed from the stack.");
		
		top = myStack.peek(); // Jane
		System.out.println(top + " is at the top of the stack.");
		
		top = myStack.pop(); // Jane
		System.out.println(top + " is removed from the stack.");
	}
	
	public static void testLinkedQueue() {
		System.out.println("\n== Testing LinkedQueue");
		
		QueueInterface<String> queue = new LinkedQueue<String>();
		queue.enqueue("Jim");
		queue.enqueue("Jess");
		queue.enqueue("Jill");
		queue.enqueue("Jane");
		queue.enqueue("Joe");
   
		String front = queue.getFront(); // Jim
		System.out.println(front + " is at the front of the queue.");
   
		front = queue.dequeue(); // Jim
		System.out.println(front + " is removed from the queue");
	}
	
	public static void testArrayQueue() {
		System.out.println("\n== Testing ArrayQueue");
		
		QueueInterface<String> queue = new ArrayQueue<String>();
		queue.enqueue("Jim");
		queue.enqueue("Jess");
		queue.enqueue("Jill");
		queue.enqueue("Jane");
		queue.enqueue("Joe");
   
		String front = queue.getFront(); // Jim
		System.out.println(front + " is at the front of the queue.");
   
		front = queue.dequeue(); // Jim
		System.out.println(front + " is removed from the queue");
	}
	
	public static void testBinaryTree() { // see visualization in Carrano pp.653
		System.out.println("\n== Testing BinaryTree");
		
		// represent each leaf as a one-node tree
		BinaryTreeInterface<String> dTree = new BinaryTree<String>();
		dTree.setTree("D");
		BinaryTreeInterface<String> fTree = new BinaryTree<String>();
		fTree.setTree("F");
		BinaryTreeInterface<String> gTree = new BinaryTree<String>();
		gTree.setTree("G");
		BinaryTreeInterface<String> hTree = new BinaryTree<String>();
		hTree.setTree("H");
   
		BinaryTreeInterface<String> emptyTree = new BinaryTree<String>();
   
		// form larger subtrees
		BinaryTreeInterface<String> eTree = new BinaryTree<String>();
		eTree.setTree("E", fTree, gTree);
   
		BinaryTreeInterface<String> bTree = new BinaryTree<String>();
		bTree.setTree("B", dTree, eTree);
   
		BinaryTreeInterface<String> cTree = new BinaryTree<String>();
		cTree.setTree("C", emptyTree, hTree);
   
		BinaryTreeInterface<String> aTree = new BinaryTree<String>();
		aTree.setTree("A", bTree, cTree);
   
		// display root, height, number of nodes
		System.out.println("Root of tree contains " + aTree.getRootData());
		System.out.println("Height of tree is " + aTree.getHeight());
		System.out.println("Tree has " + aTree.getNumberOfNodes() + " nodes");
   
		// display nodes in inorder
		System.out.print("A inorder traversal visits nodes in this order: ");
		Iterator<String> inorder = aTree.getInorderIterator();
		while (inorder.hasNext())
			System.out.print(inorder.next() + " ");
		System.out.println();
	}
	
	public static void testBinarySearchTree() {
		System.out.println("\n== Testing BinarySearchTree");
		SearchTreeInterface<Integer> myTree = new BinarySearchTree<Integer>();
		myTree.add(1);
		myTree.add(2);
		
		System.out.println("Searching for 10 in the BST returns " + myTree.contains(10));
	}
}
