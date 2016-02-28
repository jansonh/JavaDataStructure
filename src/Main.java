import untar.fti.strukturdata.*;

public class Main {
	public static void main(String[] args) {
		testAList();
		testAListExpandable();
		testLList();
	}

	public static void testAList() {
		System.out.println("Testing AList");
		ListInterface<String> runnerList = new AList<String>();
		
		runnerList.add("16");
		runnerList.add(" 4");
		runnerList.add("33");
		runnerList.add("27");
		runnerList.display();
	}
	
	public static void testAListExpandable() {
		System.out.println("Testing AListExpandable");

		ListInterface<String> runnerList = new AListExpandable<String>();
		
		runnerList.add("16");
		runnerList.add(" 4");
		runnerList.add("33");
		runnerList.add("27");
		runnerList.display();
	}
	
	public static void testLList() {
		System.out.println("Testing LList");

		ListInterface<String> myList = new LList();
		
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
}
