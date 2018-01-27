package application;
	
import java.io.IOException;
import application.model.Author;
import application.model.Book;
import application.view.Controller;
import application.view.EditAuthorController;
import application.view.EditController;
import application.view.FilterDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	private ObservableList<Book> storage;
	private ObservableList<Author> authors;  
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	public Main() { //Test data
		storage = FXCollections.observableArrayList();
		authors = FXCollections.observableArrayList();
		Author a1 = new Author("Fyodor","Mikhailovich","Dostoyevsky","Male","Russia");
		authors.add(a1);
		Author a2 = new Author("Jane","","Austen","Female","England");
		authors.add(a2);
		storage.add(new Book("Pride and Prejudice","Penguin Books",2010,3450,"A4",a2));
		storage.add(new Book("Idiot","Golden Collection",2018,300,"A5 Pocket",a1));
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Library");
        this.primaryStage.getIcons().add(new Image("file:resources/images/library_icon.png"));
        initRoot();
        initMainPage();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public ObservableList<Book> getBooks() {
        return storage;
    }
	
	public ObservableList<Author> getAuthorsList() {
        return authors;
    }
	
	public void initRoot() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public void initMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            rootLayout.setCenter(page);
            Controller controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public boolean showEditDialog(Book b, String action) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/BookEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(action);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:resources/images/edit_icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            EditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBook(b);
            controller.setAuthorsList(authors);
            controller.setMain(this);
            controller.setMode(action);
            dialogStage.showAndWait();
            return controller.isSaved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}

	public boolean showAuthorDialog(Author a, String action) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AuthorDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(action);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:resources/images/edit_icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            EditAuthorController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAuthor(a);
            controller.setMain(this);
            controller.setMode(action);
            dialogStage.showAndWait();
            return controller.isSaved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean showFilterDialog(String[] settings) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/FilterDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:resources/images/filter_icon.png"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            FilterDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSettings(settings);
            dialogStage.showAndWait();
            return controller.isSaved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}

	public boolean containBook(String title) {
		for(Book b: storage) 
			if(b.getTitle().get().equals(title)) return true;
		return false;
	}
	
	public boolean containAuthor(String name) {
		for(Author a: authors) 
			if(a.toString().equals(name)) return true;
		return false;
	}
}
