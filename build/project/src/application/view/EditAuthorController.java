package application.view;

import application.Main;
import application.model.Author;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditAuthorController {

	private Stage dialogStage;
	private boolean save;
	private Main main;
	private ObservableList<String> sex = FXCollections.observableArrayList ("Male","Female");
	private Author author;
	private String mode;
	
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField allNamesField;
	@FXML
	private TextField lastNameField;
	@FXML
	private ComboBox<String> sexBox;
	@FXML
	private TextField countryField;
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	public boolean isSaved() {
        return save;
    }

	public void setMain(Main main) {
		this.main = main;
	}
	
	public void setAuthor(Author a) {
		this.author = a;
		if(a.getFirstName().get().length()> 0) {
			firstNameField.setText(a.getFirstName().get());
			allNamesField.setText(a.getAllNames().get());
			lastNameField.setText(a.getLastName().get());
			sexBox.getSelectionModel().select(a.getSex().get());
			countryField.setText(a.getCountry().get());
		}
	}
	
	@FXML
	private void cancel() {
		this.dialogStage.close();
	}
	
	@FXML
    private void editAuthor() {
		if(isValidData()) {
			author.setFirstName(firstNameField.getText());
			author.setAllNames(allNamesField.getText());
			author.setLastName(lastNameField.getText());
			author.setSex(sexBox.getSelectionModel().getSelectedItem());
			author.setCountry(countryField.getText());
			author.addWrittenBook();
			
			save = true;
			dialogStage.close();
		}
	}

	private boolean isValidData() {
		String error = "";
		if(mode.equals("Add author")&&main.containAuthor(firstNameField.getText() + " " + lastNameField.getText() ))
			error+="You can`t create author that already exists.";
    	if(firstNameField.getText()==null||firstNameField.getText().length()==0)
    		error+= "You can`t leave the first name field behind! No author without a name."+"\n";
    	if(lastNameField.getText()==null||lastNameField.getText().length()==0) 
    		error+= "It seem you forgot to add a last name."+"\n";
    	if(countryField.getText()==null||countryField.getText().length()==0)
    		error+= "Please, be kind and fill 'Country' with the name of the country author lives in."+"\n";
    	if(sexBox.getSelectionModel().getSelectedItem()==null||sexBox.getSelectionModel().getSelectedItem().length()==0) {
    		error+= "Please, select sex of the writer!"+"\n";
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
	
	@FXML
    private void initialize() {
		sexBox.setItems(sex);
    }

	public void setMode(String action) {
		this.mode = action;
	}
	
}

