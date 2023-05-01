//--------------------------------------------------
// Assignment 4
// Part 1
// Written by: Mahad Khattak
//--------------------------------------------------

package org.example;

import java.io.*;
public class BookList {
	
	private class Node{ //Nodes for the Linked List
		private Book b;
		private Node next;
		
		public Node() {
			this.b = null;
			this.next = null;
		}
		public Node(Book b, Node next) {
			this.b = b;
			this.next = next;
		}
		
	}
	private Node head;

	public BookList() {
		head = null;
	}

	/**
	 * This method adds a Book to the start of the linked list.
	 * <p>
	 *    It first makes the head into a node with the new book and then goes until the end of the list. To make it
	 *    circular, it checks for the end and assigns the end's next to the start; it first checks if there is a node pointing
	 *    to the node in front of head to break the loop and assigns the next of the last node to head.
	 * </p>
	 * @param b The book to add at the start of the list.
	 */
	public void addToStart(Book b) {
		head = new Node(b, head); //As we want to add to start
		Node t = head;
		while(t.next!=null) {
			if(t!=head&&t.next==head.next) //If t gets to a point where it's pointing to the second node in the list but it isn't the head
				break;
			t = t.next;
		}
		t.next = head; //To make it circular (as by this point t should be the last node in the list)
	}

	/**
	 * This method makes a file called yr.txt that stores the books of the specific year in the linked list.
	 * <p>
	 *     This method simply loops through the list, checking for every occurence of the year and then writes the Books with the specified year to the file.
	 * </p>
	 * @param yr The year of which to extract the records.
	 */
	public void storeRecordsByYear(int yr) {
		if(this.head==null) {
			System.out.println("The list is empty. Program will terminate.");
			System.exit(0);
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(Integer.toString(yr) + ".txt")); //Create PrintWriter to write to file
		} catch(FileNotFoundException ex) {
			System.out.println("File was not found! Program will terminate.");
			System.exit(0);
		}
		Node t = head;
		if(t.b.getYear()==yr) { //Check if the year of the Node matches (only head for now)
			pw.println(t.b.toString());
		}
		t = t.next;
		while(t!=head) {//Traverse the linked list
			if(t.b.getYear()==yr) //Check for all other Nodes
				pw.println(t.b.toString());
			t=t.next;
		}
		pw.close();
	}

	/**
	 * This method inserts a Book before a Node with the specified ISBN (first occurence)
	 * <p>
	 *     The method loops through the linked list, gets the Node with the specified ISBN and makes a new node with the Book b inserting it before ISBN's Book.
	 * </p>
	 *
	 * @param isbn The ISBN of the book which we want to insert before.
	 * @param b	The book which we want to insert before.
	 * @return	Returns true if the operation was successful; otherwise returns false.
	 */
	public boolean insertBefore(long isbn, Book b) {
		if(this.head==null) {
			System.out.println("The list is empty. Program will terminate.");
			return false;
		}
		Node t = head; //Head does not move when inserting before head!
		if(t.b.getISBN()==isbn) {
			while(t.next!=head) { //Set t to the last Node if head is the one with the specified ISBN
				t = t.next;
			}
			t.next = new Node(b, head); //New last Node is the inserted Book
			return true;
		}
		while(t.next!=head&&t.next.b.getISBN()!=isbn)
			t = t.next; //If head doesn't have ISBN, check for any other Node in the list for the ISBN
		if(t.next==head) {
			System.out.println("There is no book with the matching ISBN.");
			return false;
		}
		t.next = new Node(b, t.next); //Insert the new Book before the Node with matched ISBN
		return true;
	}

	/**
	 * This method inserts a Node with Book b between 2 adjacent Nodes with the specified ISBNs.
	 * <p>
	 *     It loops through the Linked List and if it finds two consecutive Nodes with the matching ISBNs, it makes a new Node with Book b after the isbn1 Node.
	 * </p>
	 * @param isbn1 The first ISBN between which the Book must be inserted.
	 * @param isbn2 The second ISBN between which the Book must be inserted.
	 * @param b The Book that must be inserted.
	 * @return Returns true if the operation was successful; otherwise returns false.
	 */
	public boolean insertBetween(long isbn1, long isbn2, Book b) {
		if(head==null) {
			System.out.println("The list is empty. Program will terminate.");
			return false;
		}
		Node t = head;
		while(t.next!=head) {
			if(t.b.getISBN()==isbn1&&t.next.b.getISBN()==isbn2) {
				t.next = new Node(b, t.next);
				return true;
			} //Check for 2 consecutive Nodes with ISBN1 and ISBN2
			t = t.next; //Traverse the list if that is not the case, loop until t reaches the end
		}
		Node t2 = head;
		while(t2.next!=head) {
			t2 = t2.next;
		}//Setting t2 to the last node
		//Deal with tail-head case
		if(t2.b.getISBN()==isbn1&&head.b.getISBN()==isbn2) {
			t2.next = new Node(b, head);
			return true;
		}
		else
			return false; //If the 2 consecutive ISBNs are not found.
	}

	/**
	 * This method finds the first 2 consecutively repeated records and deletes every repetition after the first one.
	 * <p>
	 *     The method first searches for the first occurence of the record and its last occurence only if there are 2 repeated Books consecutively;
	 *     it then makes the next of the first record and makes it point to the next of the last record.
	 * </p>
	 * @return Returns true if the operation was successful; otherwise returns false.
	 */
	public boolean delConsecutiveRepeatedRecords() {
		Node t = head;
		//Let's set one of them to the first occurrence and the other to the last
		while(!t.next.b.toString().equals(t.b.toString())) {
			t = t.next;
		}//First occurrence
		Node t2 = t;
		while(t2.next.b.toString().equals(t2.b.toString())) {
			t2 = t2.next;
		}//Last occurrence
		if(t2==t) //If there is only one occurrence
			return false;
		Node tprev = head;
		while(tprev.next!=t)
			tprev=tprev.next; //Set a Node before t
		if(t.next==head) {
			tprev.next = head;
			return true;
		} //If the first occurrence is the tail, it is removed and the before-last Node now points to head
		if(t2==head) { //If the last occurrence is the Head, we reassign head to it's next Node and the first occurrence's next is now the new head.
			head = head.next;
			t.next = head;
			return true;
		}
		t.next = t2.next; //If it is not a head-tail case, we simply assign the next of the first occurrence to the next of the second occurrence, effectively leaving only the first occurrence in the list.
		return true;
	}

	/**
	 * This method simply displays the content of the linked list.
	 * <p>
	 *     The method loops through the Linked List and displays the information of the book every time it moves to the next Node.
	 * </p>
	 */
	public void displayContent() {
		Node t = head;
		System.out.println(t.b.toString() + " ==>");
		t = t.next;
		while(t!=head) { //Simply loop through the list and dispplay its contents with toString
			System.out.println(t.b.toString() + " ==>");
			t = t.next;
		}
		System.out.println("==> head");
	}

	/**
	 * This method makes a new Linked List with only the records that are authored by aut.
	 * <p>
	 *     It loops through the Linked List and every time the author is found inside the Book object, used the addToEnd() method to add the book to the end of a new Linked List.
	 * </p>
	 * @param aut Author of whose books we want to make a Linked List.
	 * @return Returns a Linked List with all the books authored by the specified author.
	 */
	public BookList extractAuthList(String aut) {
		Node t = head;
		BookList bl = new BookList();
		while (t.next!=head) {
			if(t.b.getAuthor().equals(aut))
				bl.addToEnd(t.b); //AddtoEnd so we can add in order, we simply traverse the list and check for the author in each Node.
			t=t.next;
		}
		if(t.b.getAuthor().equals(aut))
			bl.addToEnd(t.b); //If tail has the author, as the first loop doesn't include the case where t is the tail.
		return bl;
	}

	/**
	 * This method swaps the two Books in the Linked List with the specified ISBNs.
	 * <p>
	 *     It first records the next and previous of each Node with Book objects with the specified ISBNs. It then assigns
	 *     the .next of the previous Nodes to the other ISBNs (previous Node of ISBN1 .next becomes ISBN2 and vice-versa). Then it assigns the .next
	 *     of the ISBN books to the recorded next of the other ISBN in a similar fashion. A special case is made when the head and tail are to be swapped.
	 * </p>
	 * @param isbn1 ISBN of the first book to swap.
	 * @param isbn2 ISBN of the second book to swap.
	 * @return	Returns true if the operation was successful; otherwise returns false.
	 */
	public boolean swap(long isbn1, long isbn2){
		Node t = head;
		Node t2 = head;
		while(t.b.getISBN()!=isbn1){
			t = t.next;
			if(t==head)
				return false;
		}
		while(t2.b.getISBN()!=isbn2){
			t2 = t2.next;
			if(t2==head)
				return false;
		} //We first set t and t2 to the specified ISBNs; if one of them is not found, return false.
		//For later, we will define Nodes that point to the Nodes before and each specified ISBN's node.
		Node tprev = head;
		Node t2prev = head;
		Node tnext = head;
		Node t2next = head;
		Node last = head;
		while(tprev.next!=t)
			tprev = tprev.next;
		while(t2prev.next!=t2)
			t2prev = t2prev.next;
		while(tnext!=t.next)
			tnext = tnext.next;
		while(t2next!=t2.next)
			t2next = t2next.next;
		while(last.next!=head) //Tail node
			last = last.next;
		if(t==head&&t2==last){ //For the special case where we want to swap the head and the tail
			t2.next = tnext; //Tail's next is now head's next
			head = t2; //Tail is now the head
			t2prev.next = t; //The Tail is now essentially the previous Head
			t.next = head; //The previous Head's next is now the previous Tail, effectively swapping the two.
			return true;
		}
		tprev.next = t2; //Second to First's place
		t2.next = tnext; //Second's next is now First's next
		t2prev.next = t; //First to Second's place
		t.next = t2next; //First's next is now Second's previous next
		if(t==head) //If First was Head we must set Second to head as we swap the two
			head = t2;
		return true;
	}

	/**
	 * Commits all the Linked List to a file called Update_Books with each line displaying the information for a Book object.
	 * <p>
	 *     Loops through the Linked List and calls the toString() method to display the information of each Book of the Linked List.
	 * </p>
	 */
	public void commit(){
		PrintWriter pw = null;
		try{
			 pw = new PrintWriter(new FileOutputStream("Update_Books.txt")); //Open PrintWriter
		}
		catch(FileNotFoundException ex){
			System.out.println("File was not found! Program will terminate."); //Error handling
			System.exit(0);
		}
		Node t = head;
		pw.println(t.b.toString());
		t = t.next;
		while(t!=head) {
			pw.println(t.b.toString());
			t = t.next; //Loop through Linked List, print every Book in each Node.
		}
		pw.close();
	}

	/**
	 * Adds a Book to the end of the Linked List.
	 * <p>
	 *     A special case is made if the list is empty by calling the addToStart() method to add a Book to the start.
	 * </p>
	 * @param b Book to be added at the end of the list.
	 */
	public void addToEnd(Book b){
		if(head==null)
			this.addToStart(b);
		else {
			Node t = head;
			while (t.next != head)
				t = t.next; //Loop until the tail
			t.next = new Node(b, head); //Adds to end of list
		}
	}
}
