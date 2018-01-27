package application.view;

import application.Main;
import application.model.Author;
import application.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
 
public class EditController{

	private Stage dialogStage;
	private Book book;
	private boolean save = false;
	private Main main;
	private String mode;
	
	@FXML
	private TextField titleField;
	@FXML
	private TextField publisherField;
	@FXML
	private TextField yearField;
	@FXML
	private TextField pagesField;
	@FXML
	private ComboBox<String> formatBox;
	private ObservableList<String> formats = FXCollections.observableArrayList ("A4","A5 Pocket");
	private ObservableList<String> authors = FXCollections.observableArrayList();
	private ObservableList<String> chosenAuthors = FXCollections.observableArrayList();
	
	@FXML
	private ComboBox<String> authorBox;
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	public boolean isSaved() {
        return save;
    }

	@FXML
	private void addMore() {
		if(!chosenAuthors.contains(authorBox.getSelectionModel().getSelectedItem()))
		chosenAuthors.add(authorBox.getSelectionModel().getSelectedItem());
	}
	
	public void setBook(Book b) {
		this.book = b;
		if(b.getTitle().get().length()> 0) {
			titleField.setText(b.getTitle().get());
			publisherField.setText(b.getPublisher().get());
			yearField.setText(Integer.toString(b.getYear().get()));
			pagesField.setText(Integer.toString(b.getPages().get()));
			formatBox.getSelectionModel().select(b.getFormat().get());
			authorBox.getSelectionModel().select(b.getAuthors().get(0).toString());
			for(Author a: b.getAuthors()) {
				chosenAuthors.add(a.toString());
				a.deleteWrittenBook();
			}
		}
	}
	
	@FXML
    private void editBook() {
		if(isValidData()) {
			book.setTitle(titleField.getText());
			book.setPublisher(publisherField.getText());
			book.setYear(Integer.parseInt(yearField.getText()));
			book.setPages(Integer.parseInt(pagesField.getText()));
			book.setFormat(formatBox.getSelectionModel().getSelectedItem());
			if(!chosenAuthors.contains(authorBox.getSelectionModel().getSelectedItem()))
				chosenAuthors.add(authorBox.getSelectionModel().getSelectedItem()); 
			book.setAuthors(lookFor(chosenAuthors));
			save = true;
			dialogStage.close();
		}
	}
	 
	private ObservableList<Author> lookFor(ObservableList<String> auths){
		ObservableList<Author> res = FXCollections.observableArrayList();
		for(Author a: main.getAuthorsList()) {
			for(String s: auths)
				if(a.toString().equals(s)) {
					a.addWrittenBook();
					res.add(a);
				}
		}
		return res;
	}
	
	@FXML
    private void cancel() {
        dialogStage.close();
    }
	
	@FXML
	private void addNewAuthor() {
		   Author a = new Author();
	       boolean save = main.showAuthorDialog(a,"Add author");
	       if (save) {
	    	  a.addWrittenBook();
	          main.getAuthorsList().add(a);
	          extendAuthorsList(a);
	          authorBox.setItems(authors);
	       }
	}
	
    private boolean isValidData() {
    	String error = "";
    	if(mode.equals("Add book")&&main.containBook(titleField.getText()))
    			error+="You can`t add a book that already exists.";
    	if(titleField.getText()==null||titleField.getText().length()==0)
    		error+= "You can`t leave title field behind! No book without a title.";
    	if(publisherField.getText()==null||publisherField.getText().length()==0) 
    		error+= "All fields have to be filled. The publisher too, yes.";
    	if(yearField.getText()==null||yearField.getText().length()==0) // check for nums!!!!!!
    		error+= "It seem you forgot to add a publishing year.";
    	else {
    		try {
                Integer.parseInt(yearField.getText());
            } catch (NumberFormatException e) {
                error+= "Sorry, seems your year is not an integer!\n"; 
            }
    	}
    	if(pagesField.getText()==null||pagesField.getText().length()==0)
    		error+= "Please, be kind and fill 'Number of pages' with the number of pages.";
    	else {
    		try {
                Integer.parseInt(pagesField.getText());
            } catch (NumberFormatException e) {
                error+= "Sorry, seems your number of pages is not an integer!\n"; 
            }
    	}
    	if(formatBox.getSelectionModel().getSelectedItem()==null||formatBox.getSelectionModel().getSelectedItem().length()==0) {
    		error+= "Please, select format!";
    	}
    	
    	if(error.length()==0) return true;
    	else {
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Wrong data!");
            alert.setContentText(error);
            alert.showAndWait();
    		return false;
    	}
    }

	public void setAuthorsList(ObservableList<Author> auths) {
		for(Author a:auths) 
			this.authors.add(a.toString());
	}
	
	private void extendAuthorsList(Author a) {
		this.authors.add(a.toString());
	}
    
	@FXML
    private void initialize() {
		formatBox.setItems(formats);
		authorBox.setItems(authors);
    }
	
	public void setMain(Main main) {
		this.main = main;
   }

	public void setMode(String action) {
		this.mode = action;
		
	}

}