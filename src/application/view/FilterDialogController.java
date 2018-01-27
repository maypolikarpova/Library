package application.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FilterDialogController {

	@FXML
	private ComboBox<String> filterBox;
	@FXML
	private ComboBox<String> inclusionsBox;
	@FXML
	private ComboBox<String> sortBox;
	private ObservableList<String> filters = FXCollections.observableArrayList ("Title","Publisher","Publishing year","Number of pages","Format");
	private ObservableList<String> inclusions = FXCollections.observableArrayList ("Any inclusions","Inclusions on the beginning","Whole word");
	private ObservableList<String> sort = FXCollections.observableArrayList ("Title","Publisher","Publishing year","Number of pages","Format");
	private Stage dialogStage;
	private boolean save;
	private String[] settings;
	
	@FXML
	private void cancel() {
		 dialogStage.close();
	}
	
	@FXML
	private void saveSettings() {
		if(isValidData()) {
			settings[0] = filterBox.getSelectionModel().getSelectedItem();
			settings[1] = inclusionsBox.getSelectionModel().getSelectedItem();
			settings[2] = sortBox.getSelectionModel().getSelectedItem();
			save = true;
			dialogStage.close();
		}
	}
	
	private boolean isValidData() {
		String error = "";
    	if(filterBox.getSelectionModel().getSelectedItem()==null||filterBox.getSelectionModel().getSelectedItem().length()==0) {
    		error+= "Please, select filter!";
    	}
    	if(inclusionsBox.getSelectionModel().getSelectedItem()==null||inclusionsBox.getSelectionModel().getSelectedItem().length()==0) {
    		error+= "Please, select inclusion mode!";
    	}
    	if(sortBox.getSelectionModel().getSelectedItem()==null||sortBox.getSelectionModel().getSelectedItem().length()==0) {
    		error+= "Please, select sorting type!";
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

	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	public boolean isSaved() {
        return save;
    }
	
	@FXML
    private void initialize() {
		filterBox.setItems(filters);
		inclusionsBox.setItems(inclusions);
		sortBox.setItems(sort);
    }

	public void setSettings(String[] s) {
		this.settings = s;
		if(settings[0].length()>0) {
			filterBox.getSelectionModel().select(settings[0]);
			inclusionsBox.getSelectionModel().select(settings[1]);
			sortBox.getSelectionModel().select(settings[2]);
		}
	}
}
