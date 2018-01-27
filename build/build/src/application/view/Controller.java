package application.view;

import application.Main;
import application.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
 
public class Controller{
 
   @FXML
   private Button addButton;
   @FXML
   private Button deleteButton;
   @FXML
   private Button editButton;
   @FXML
   private TableView<Book> table;
   @FXML
   private TableColumn<Book, String> titleColumn;
   
   @FXML
   private Label titleLabel;
   @FXML
   private Label publisherLabel;
   @FXML
   private Label yearLabel;
   @FXML
   private Label pagesLabel;
   @FXML
   private Label formatLabel;
   
   @FXML
   private Label changeLabel1;
   @FXML
   private Label changeLabel2;
   @FXML
   private Label changeLabel3;
   @FXML
   private Label changeLabel4;
   @FXML
   private Label changeLabel5;


   @FXML
   private TextField searchField;


   @FXML
   private TableView<Author> authorsTable;
   @FXML
   private TableColumn<Author, String> authorsColumn;
  
 
   private Main main;
   private Book currentBook;
   private String[] searchSettings = new String[3];

   public Controller() {
   }
   
   @FXML
   private void initialize() {
       titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
       table.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> about(newValue));
       searchSettings[0] = "Title";
       searchSettings[1] = "Inclusions on the beginning";
       searchSettings[2] = "Title";
   }
   
   
   public void setMain(Main main) {
		this.main = main;
		table.setItems(main.getBooks());
   }

   @FXML
   private void addBook() {
	   Book b = new Book();
       boolean save = main.showEditDialog(b,"Add book");
       if (save) {
    	   main.getBooks().add(b);
       }
   }

   @FXML
   private void editBook() {
       Book b = table.getSelectionModel().getSelectedItem();
       if (b!= null) {
    	   boolean saved = main.showEditDialog(b, "Edit book");
           if (saved) about(b);
       } 
       else {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(main.getPrimaryStage());
	        alert.setTitle("Can`t do!");
	        alert.setContentText("You cannot edit something you didn`t choose. Please, choose a book and then press 'Edit'.");
	        alert.showAndWait();
       }
   }

   private void aboutAuthor(Author a) {
	   if(a!=null) {
			changeLabel1.setText("First name");
			changeLabel2.setText("All names");
			changeLabel3.setText("Last name");
			changeLabel4.setText("Sex");
			changeLabel5.setText("Country");
			
		   	titleLabel.setText(a.getFirstName().get());
	        publisherLabel.setText(a.getAllNames().get());
	        yearLabel.setText(a.getLastName().get());
	        pagesLabel.setText(a.getSex().get());
	        formatLabel.setText(a.getCountry().get());
	     
	        table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	            @Override
	            public void handle(MouseEvent t) {
	                if(t.getButton() == MouseButton.PRIMARY) {
	                	int i = table.getSelectionModel().getSelectedIndex();
	                    table.getSelectionModel().clearAndSelect(i);
	                    about(table.getSelectionModel().getSelectedItem());
	        	        
	                }
	            }
	        });
	   }
   }
   
   @FXML
   private void addAuthor() {
	   Author a = new Author();
       boolean save = main.showAuthorDialog(a,"Add author");
       if (save) {
          main.getAuthorsList().add(a);
          currentBook.getAuthors().add(a);
          about(currentBook);
       }
   }
   
   @FXML
   private void deleteAuthor() {
	   int ind = authorsTable.getSelectionModel().getSelectedIndex();
	   if (ind >= 0&&authorsTable.getItems().size()>1) {
		    authorsTable.getItems().remove(ind);
		    if(authorsTable.getSelectionModel().getSelectedItem().getBooksWritten()==1) main.getAuthorsList().remove(ind);
	   }
	   else {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(main.getPrimaryStage());
	        alert.setTitle("Can`t do!");
	        alert.setContentText("You cannot delete something you didn`t choose or the only author of this book. Please,choose an author and then press 'Delete', or try to edit the only author.");
	        alert.showAndWait();
	    }
	}
   
   @FXML
   private void editAuthor() {
	   Author a = authorsTable.getSelectionModel().getSelectedItem();
       if (a!= null) {
    	   boolean saved = main.showAuthorDialog(a, "Edit author");
           if (saved) aboutAuthor(a);
       } 
       else {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(main.getPrimaryStage());
	        alert.setTitle("Can`t do!");
	        alert.setContentText("You cannot edit something you didn`t choose. Please, choose an author and then press 'Edit'.");
	        alert.showAndWait();
       }
   }
   
   private void about(Book b) {
	    if (b != null) {
	    	changeLabel1.setText("Title");
			changeLabel2.setText("Publisher");
			changeLabel3.setText("Publishing year");
			changeLabel4.setText("Number of pages");
			changeLabel5.setText("Format");
	    	
	        titleLabel.setText(b.getTitle().get());
	        publisherLabel.setText(b.getPublisher().get());
	        yearLabel.setText(Integer.toString(b.getYear().get()));
	        pagesLabel.setText(Integer.toString(b.getPages().get()));
	        formatLabel.setText(b.getFormat().get());
	        authorsTable.setItems(b.getAuthors());
	        authorsColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
	        currentBook = b;
	        authorsTable.getSelectionModel().selectedItemProperty().addListener(
	                (observable, oldValue, newValue) -> aboutAuthor(newValue));
	    } 
	    
	    else {
	    	titleLabel.setText("");
	        publisherLabel.setText("");
	        yearLabel.setText("");
	        pagesLabel.setText("");
	        formatLabel.setText("");
	        authorsTable.setItems(null);
	    }
	}
	
	@FXML
	private void deleteBook() {
	    int ind = table.getSelectionModel().getSelectedIndex();
	    Book b = table.getSelectionModel().getSelectedItem();
	    if (ind >= 0) {
	    	deleteBookAuthors(b.getAuthors());
	    	main.getBooks().remove(ind);
	    }
	    else {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(main.getPrimaryStage());
	        alert.setTitle("Can`t do!");
	        alert.setContentText("You cannot delete something you didn`t choose. Please,choose a book and then press 'Delete'.");
	        alert.showAndWait();
	    }
	}
	
	private void deleteBookAuthors(ObservableList<Author> authors) {
		for(Author a: authors) {
			for(int i=0;i<main.getAuthorsList().size();i++) {
				if(a.getBooksWritten()<=1&&a.toString().equals(main.getAuthorsList().get(i).toString()))
					main.getAuthorsList().remove(i);
			}
		}
	}

	@FXML
	private void search() { 
		String query = searchField.getText().toLowerCase();
		if (query.length()==0) //get all books by entering no query
			table.setItems(sort(main.getBooks(),searchSettings[2]));
		else {
			if(searchSettings[1].equals("Whole word")) 
				searchWhole(searchSettings[0],query.toString());
			else if (searchSettings[1].equals("Any inclusions")) 
				searchAny(searchSettings[0],query.toString());
			else searchBeginning(searchSettings[0],query);
		}
	}
	
	private void searchBeginning(String mode,String query) {
		ObservableList<Book> books = main.getBooks();
		ObservableList<Book> res = FXCollections.observableArrayList();
		for(int i = 0;i< books.size();i++) {
			String lookIn;
			if(mode.equals("Title")) lookIn = books.get(i).getTitle().get().toLowerCase();
			else if (mode.equals("Publisher")) lookIn = books.get(i).getPublisher().get().toLowerCase();
			else if (mode.equals("Number of pages")) {
				int a = books.get(i).getPages().get();
				lookIn = Integer.toString(a).toLowerCase();
			}
			else if (mode.equals("Format")) lookIn = books.get(i).getFormat().get().toLowerCase();
			else {
				int a = books.get(i).getYear().get();
				lookIn = Integer.toString(a).toLowerCase();
			}
			if(lookIn.substring(0,query.length()).equals(query)) res.add(books.get(i));
		}
		table.setItems(sort(res,searchSettings[2]));
	}

	private ObservableList<Book> sort(ObservableList<Book> res, String mode) {
		if(mode.equals("Title")||mode.equals("Publisher")||mode.equals("Format")) return sortStrings(res, mode);
		else return sortInts(res, mode);
	}

	private ObservableList<Book> sortInts(ObservableList<Book> res, String mode) {
		SortedList<Book> sortedList = new SortedList<Book>(res, 
			      (Book b1, Book b2) -> {
			    	if(mode.equals("Publishing year")) {
				        if(b1.getYear().get()<b2.getYear().get()) return -1;
				        else if(b1.getYear().get()>b2.getYear().get()) return 1;
				        else return 0;
			    	}
			        else {
		        		if (b1.getPages().get()<b2.getPages().get()) return -1;
				        else if (b1.getPages().get()>b2.getPages().get()) return 1;
				        else return 0;
			        	}
			    });
		return sortedList;
	}

	private ObservableList<Book> sortStrings(ObservableList<Book> res, String mode) {
		SortedList<Book> sortedList = new SortedList<Book>(res, 
			      (Book b1, Book b2) -> {
			    	if(mode.equals("Title")) {
				        if(b1.getTitle().get().toLowerCase().compareTo(b2.getTitle().get().toLowerCase())<0) return -1;
				        else if(b1.getTitle().get().toLowerCase().compareTo(b2.getTitle().get().toLowerCase())>0) return 1;
				        else return 0;
			    	}
			        else if (mode.equals("Publisher")){
			        	if(b1.getPublisher().get().toLowerCase().compareTo(b2.getPublisher().get().toLowerCase())<0) return -1;
				        else if(b1.getPublisher().get().toLowerCase().compareTo(b2.getPublisher().get().toLowerCase())>0) return 1;
				        else return 0;
		        	}
			        else {
			        	if(b1.getFormat().get().toLowerCase().compareTo(b2.getFormat().get().toLowerCase())<0) return -1;
				        else if(b1.getFormat().get().toLowerCase().compareTo(b2.getFormat().get().toLowerCase())>0) return 1;
				        else return 0;
			        }
			    });
		return sortedList;
	}

	private void searchAny(String mode,String query) {
		ObservableList<Book> books = main.getBooks();
		ObservableList<Book> res = FXCollections.observableArrayList();
		for(int i = 0;i< books.size();i++) {
			String lookIn;
			if(mode.equals("Title")) lookIn = books.get(i).getTitle().get().toLowerCase();
			else if (mode.equals("Publisher")) lookIn = books.get(i).getPublisher().get().toLowerCase();
			else if (mode.equals("Number of pages")) {
				int a = books.get(i).getPages().get();
				lookIn = Integer.toString(a).toLowerCase();
			}
			else if (mode.equals("Format")) lookIn = books.get(i).getFormat().get().toLowerCase();
			else {
				int a = books.get(i).getYear().get();
				lookIn = Integer.toString(a).toLowerCase();
			}
			
			if(lookIn.contains(query)) res.add(books.get(i));
			}
		table.setItems(sort(res,searchSettings[2]));
	}

	private void searchWhole(String mode,String query) {
		ObservableList<Book> books = main.getBooks();
		ObservableList<Book> res = FXCollections.observableArrayList();
		for(int i = 0;i< books.size();i++) {
			String lookIn;
			if(mode.equals("Title")) lookIn = books.get(i).getTitle().get().toLowerCase();
			else if (mode.equals("Publisher")) lookIn = books.get(i).getPublisher().get().toLowerCase();
			else if (mode.equals("Number of pages")) {
				int a = books.get(i).getPages().get();
				lookIn = Integer.toString(a).toLowerCase();
			}
			else if (mode.equals("Format")) lookIn = books.get(i).getFormat().get().toLowerCase();
			else {
				int a = books.get(i).getYear().get();
				lookIn = Integer.toString(a).toLowerCase();
			}
			if(lookIn.equals(query)) res.add(books.get(i));
		}
		table.setItems(sort(res,searchSettings[2]));
	}

	@FXML
	private void filter() {
		main.showFilterDialog(searchSettings);
   }
}
   