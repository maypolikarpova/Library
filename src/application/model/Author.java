package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Author {

	private final StringProperty firstName;
	private final StringProperty allNames;
	private final StringProperty lastName;
	private final StringProperty sex;
	private final StringProperty country;
	private int booksWritten;
	
	public Author(String firstName,String allNames,String lastName,String sex,String country) {
		this.firstName = new SimpleStringProperty(firstName);
		this.allNames = new SimpleStringProperty(allNames);
		this.lastName = new SimpleStringProperty(lastName);
		this.sex = new SimpleStringProperty(sex);
		this.country = new SimpleStringProperty(country);
		this.booksWritten = 0;
	}

	public Author() {
		this.firstName = new SimpleStringProperty("");
		this.allNames = new SimpleStringProperty("");
		this.lastName = new SimpleStringProperty("");
		this.sex = new SimpleStringProperty("");
		this.country = new SimpleStringProperty("");
		this.booksWritten = 0;
	}

	public StringProperty getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty getAllNames() {
		return allNames;
	}

	public void setAllNames(String allNames) {
		this.allNames.set(allNames);
	}

	public StringProperty getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex.set(sex);
	}

	public StringProperty getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country.set(country);
	}
	
	public String toString() {
		return firstName.get() + " " + lastName.get();
	}

	public StringProperty getName() {
		return new SimpleStringProperty(this.toString());
	}

	public int getBooksWritten() {
		return booksWritten;
	}

	public void deleteWrittenBook() {
		this.booksWritten--;
	}

	public void addWrittenBook() {
		this.booksWritten++;
	}
	
}
