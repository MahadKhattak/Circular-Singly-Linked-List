//--------------------------------------------------
// Assignment 4
// Part 1
// Written by: Mahad Khattak
//--------------------------------------------------

package org.example;

import java.util.*;
import java.io.*;
public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//1. Read all incorrect records and place them in an ArrayList of Books
		
		ArrayList<Book> arrLst = new ArrayList<Book>();
		BookList bkLst = new BookList();
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new FileInputStream("Books.txt"));
			pw = new PrintWriter(new FileOutputStream("YearErr.txt"));
		} catch(FileNotFoundException ex) {
			System.out.println("File was not found! Program wil terminate.");
			System.exit(0);
		} catch(IOException ex) {
			System.out.println("Error. Program will terminate.");
			System.exit(0);
			}
		//Setup the loop to check the file's contents.
		String line = null; //Setup a variable to store the current line.
		String[] linesplit = null; //Setup a variable to split each Book line.
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			linesplit = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			if(Integer.parseInt(linesplit[5])>2024) {
				Book error = new Book(linesplit[0], linesplit[1], Double.parseDouble(linesplit[2]), Long.parseLong(linesplit[3]), linesplit[4], Integer.parseInt(linesplit[5]));
				arrLst.add(error); //Initialize a Book with the error line's properties and add them to the ArrayList.
				continue;//We do this to avoid having the invalid records being placed in the Linked List of valid books.
			}
			bkLst.addToEnd(new Book(linesplit[0], linesplit[1], Double.parseDouble(linesplit[2]), Long.parseLong(linesplit[3]), linesplit[4], Integer.parseInt(linesplit[5])));
		}
		//Now we place all the valid records in the BookList

		//Now we read everything from the ArrayList and store them in YearErr.txt
		if(arrLst.size()!=0) {
			for (int i = 0; i < arrLst.size(); i++) {
				pw.write(arrLst.get(i).toString() + "\n");
			}
		}
		pw.close();

		//Now we make the user interface
		Boolean b = true; //Used to break out of the loop
		Scanner keyIn = new Scanner(System.in); //For the user's choice
		System.out.println("YearErr file created.");
		while(b){
			System.out.println("Here are the contents of the list.");
			System.out.println("==================================");
			bkLst.displayContent();
			System.out.println("Tell me what you want to do? Let's Chat since this is trending now! Here are the options: ");
			System.out.println("\t" + "1) Give me a year # and I would extract all records of that year and store them in a file for that year;");
			System.out.println("\t" + "2) Ask me to delete all consecutive repeated records;");
			System.out.println("\t" + "3) Give me an author name and I will create a new list with the records of this author and display them;");
			System.out.println("\t" + "4) Give me an ISBN number and a Book object, and I will insert a Node with the book before the record with this ISBN;");
			System.out.println("\t" + "5) Give me 2 ISBN numbers and a Book object, and I will insert a Node between them, if I find them!");
			System.out.println("\t" + "6) Give me 2 ISBN numbers and I will swap them in the list for rearrangement of records; of course if they exist!");
			System.out.println("\t" + "7) Tell me to COMMIT! Your command is my wish. I will commit your list to a file called Updated_Books;");
			System.out.println("\t" + "8) Tell me to STOP TALKING. Remember, if you do not commit, I will not!");
			System.out.println("Enter your selection: ");
			int choice = keyIn.nextInt(); //User's choice for first menu
			switch(choice){
				case 1:{
					System.out.println("Please enter the year for which we should extract the books: ");
					int yr = keyIn.nextInt();
					if(yr>2024) {
						System.out.println("You have entered an invalid year. Program will terminate.");
						System.exit(0);
					}
					bkLst.storeRecordsByYear(yr);
					System.out.println("File for " + yr + " has been created.");
					bkLst.displayContent();

				}
				break;
				case 2: {
					bkLst.delConsecutiveRepeatedRecords();
					System.out.println("Here are the contents of the list after removing consecutive duplicates: ");
					bkLst.displayContent();
				}
				break;
				case 3:{
					System.out.println("Please enter the name of the author to create an extracted list: ");
					keyIn.nextLine();
					String s = keyIn.nextLine();
					BookList auth = bkLst.extractAuthList(s);
					System.out.println("Here are the contents of the author list for " + s);
					System.out.println("=====================================================");
					auth.displayContent();
				}
				break;
				case 4:{
					System.out.println("Enter an ISBN and then a Book in the format [title author price isbn genre year] with an Enter after every field to insert before the ISBN: ");
					long isbn = keyIn.nextLong();
					String title = keyIn.nextLine();
					keyIn.nextLine();
					String author = keyIn.nextLine();
					int price = keyIn.nextInt();
					long isbn1 = keyIn.nextLong();
					String genre = keyIn.nextLine();
					keyIn.nextLine();
					int year = keyIn.nextInt();
					Book b1 = new Book(title, author, price, isbn1, genre, year);
					bkLst.insertBefore(isbn, b1);
					bkLst.displayContent();
				}
				break;
				case 5:{
					System.out.println("Enter 2 ISBNs and then a Book in the format [title author price isbn genre year] with an Enter after every field to insert between the ISBNs: ");
					long isbn1 = keyIn.nextLong();
					long isbn2 = keyIn.nextLong();
					keyIn.nextLine();
					String title = keyIn.nextLine();
					String author = keyIn.nextLine();
					int price = keyIn.nextInt();
					long isbn = keyIn.nextLong();
					keyIn.nextLine();
					String genre = keyIn.nextLine();
					int year = keyIn.nextInt();
					Book b1 = new Book(title, author, price, isbn, genre, year);
					bkLst.insertBetween(isbn1, isbn2, b1);
					bkLst.displayContent();
				}
				break;
				case 6:{
						System.out.println("Enter two ISBNs to swap their locations: ");
						long isbn1 = keyIn.nextLong();
						long isbn2 = keyIn.nextLong();
						bkLst.swap(isbn1, isbn2);
						bkLst.displayContent();
				}
				break;
				case 7:{
					bkLst.commit();
				}
				break;
				case 8:{
					b = false; //Exit the menu generation
				}
				break;
			}
		}
	}
}
