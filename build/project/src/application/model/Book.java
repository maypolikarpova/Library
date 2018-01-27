package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Book {

	private final StringProperty title;
	private final StringProperty publisher;
	private final IntegerProperty year;
	private final IntegerProperty pages;
	private final StringProperty format;
	private ObservableList<Author> authors  = FXCollections.observableArrayList();

	public Book(String title,String publisher,int year,int pages,String format,ObservableList<Author>  authors) {
		this.title = new SimpleStringProperty(title);
		this.publisher = new SimpleStringProperty(publisher);
		this.year = new SimpleIntegerProperty(year);
		this.pages = new SimpleIntegerProperty(pages);
		this.format = new SimpleStringProperty(format);
		this.authors = authors;
	}
	
	public Book() {
		this.title = new SimpleStringProperty("");
		this.publisher = new SimpleStringProperty("");
		this.year = new SimpleIntegerProperty();
		this.pages = new SimpleIntegerProperty();
		this.format = new SimpleStringProperty("");
	}
	
	public Book(String title,String publisher,int year,int pages,String format,Author author) {
		this.title = new SimpleStringProperty(title);
		this.publisher = new SimpleStringProperty(publisher);
		this.year = new SimpleIntegerProperty(year);
		this.pages = new SimpleIntegerProperty(pages);
		this.format = new SimpleStringProperty(format);
		this.authors.add(author);
	}
	
	public StringProperty getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public StringProperty getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher.set(publisher);
	}

	public IntegerProperty getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year.set(year);
	}

	public IntegerProperty getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages.set(pages);
	}

	public StringProperty getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format.set(format);
	}

	public String authorsToString() {
		String str = "";
		for(Author a: authors)
			str+= a.toString() + "\n";
		return str;
	}
	
	public ObservableList<Author>  getAuthors(){
		return authors;
	}

	public void setAuthors(ObservableList<Author> authors) {
		this.authors = authors;
	}

}