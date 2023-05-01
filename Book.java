//--------------------------------------------------
// Assignment 4
// Part 1
// Written by: Mahad Khattak
//--------------------------------------------------

package org.example;

import java.io.*;
public class Book{
	private String title;
	private String author;
	private double price;
	private long ISBN;
	private String genre;
	private int year;
	
	public Book() {
		this.title = null;
		this.author = null;
		this.price = 0;
		this.ISBN = 0;
		this.genre = null;
		this.year = 0;
	}
	public Book(String title, String author, double price, long ISBN, String genre, int year) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.ISBN = ISBN;
		this.genre = genre;
		this.year = year;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getISBN() {
		return ISBN;
	}
	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String toString() {
		return "This Book is titled " + title + " and is authored by " + author + " and costs " + price + " and has an ISBN of " + ISBN + " and is of genre " + genre + " and was published in the year " + year;
	}
	
}
