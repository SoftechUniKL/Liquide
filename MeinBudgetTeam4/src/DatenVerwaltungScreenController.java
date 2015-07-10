import java.awt.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.event.*;



public class DatenVerwaltungScreenController {
	BudgetPlanModel model = new BudgetPlanModel ();
	
    /* alle Elemente mit ID´s aus dem Scenebuilder übernommen*/
    @FXML
    private TextField KategorieInput_Datenverwaltung;

    @FXML
    private ComboBox<String> subkategorieInputSl_Datenverwaltung = new ComboBox<String> ();

    @FXML
    private ComboBox<String> KategorieInputKl_Datenverwaltung = new ComboBox<String> ();

    @FXML
    private Button KategorieLöschen_button_Datenverwaltung;

    @FXML
    private Button subKategorieLöschen_button_Datenverwaltung;

    @FXML
    private Button subKategorieErstellen_button_Datenverwaltung;

    @FXML
    private ComboBox<String> KategorieInputSe_Datenverwaltung = new ComboBox<String> ();

    @FXML
    private ComboBox<String> KategorieInputSl_Datenverwaltung = new ComboBox<String> ();

    @FXML
    private Button KategorieErstellen_button_Datenverwaltung;

    @FXML
    private Label Info_Datenverwaltung;

    @FXML
    private TextField subKategorieInput_Datenverwaltung;

    @FXML
    private Button zurück_button_Datenverwaltung;
    
    /* initialize() füllt das Fenster, die Comboxen ... etc.
     * notwendig, da Comboboxen sich nicht anders dynamisch initialisieren ließen
     */
    public void initialize() throws SQLException{
    	KategorieInputKl_Datenverwaltung.getItems().addAll(model.return_Kategorien());
    	KategorieInputSe_Datenverwaltung.getItems().addAll(model.return_Kategorien());
    	KategorieInputSl_Datenverwaltung.getItems().addAll(model.return_Kategorien());
    	KategorieErstellen_button_Datenverwaltung.setDisable(true);
    	KategorieLöschen_button_Datenverwaltung.setDisable(true);
    	subKategorieInput_Datenverwaltung.setDisable(true);
    	subkategorieInputSl_Datenverwaltung.setDisable(true);
    	subKategorieErstellen_button_Datenverwaltung.setDisable(true);
    	subKategorieLöschen_button_Datenverwaltung.setDisable(true);
    }
    /* init() versetzt das Fenster in den Ursprungszustand*/
    public void init(){
    	KategorieInputKl_Datenverwaltung.setValue(null);
    	KategorieInputSe_Datenverwaltung.setValue(null);
    	KategorieInputSl_Datenverwaltung.setValue(null);
    	KategorieInput_Datenverwaltung.setText(null);
    	subKategorieInput_Datenverwaltung.setText(null);
    	KategorieErstellen_button_Datenverwaltung.setDisable(true);
    	KategorieLöschen_button_Datenverwaltung.setDisable(true);
    	subKategorieInput_Datenverwaltung.setDisable(true);
    	subkategorieInputSl_Datenverwaltung.setDisable(true);
    	subKategorieErstellen_button_Datenverwaltung.setDisable(true);
    	subKategorieLöschen_button_Datenverwaltung.setDisable(true);
    	
    	
    }
    
   
    
    /* reset() leert das Label nach 10 sekunden*/
    public void reset() {
    	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), ev -> {
            Info_Datenverwaltung.setText(null);
        }));
        
        timeline.play();
    }
    
    @FXML
    void bA_KategorieLöschen_Datenverwaltung(ActionEvent event) throws SQLException {
    	model.deleteKategorie(KategorieInputKl_Datenverwaltung.getValue());
    	KategorieInputKl_Datenverwaltung.getItems().clear();
    	KategorieInputSe_Datenverwaltung.getItems().clear();
    	KategorieInputSl_Datenverwaltung.getItems().clear();
    	initialize();
    	init();
    	Info_Datenverwaltung.setTextFill(Color.web("green"));
    	Info_Datenverwaltung.setText("Ihre Kategorie wurde erfolgreich gelöscht");
    	reset();

    }
/* Wenn Kategorie bereits besteht, kommt eine Fehlermeldung. Ansonsten wird Kategorie aufgenommen. 
 * Die neue Kategorie wird in die restlichen comboboxen eingefügt
 */
    @FXML
    void bA_KategorieErstellen_Datenverwaltung(ActionEvent event) throws SQLException {
    	try{
    	if( Arrays.asList(model.return_Kategorien()).contains(KategorieInput_Datenverwaltung.getText()))
    		throw new IllegalArgumentException ();
    	else{
    	model.insert_Kategorie(KategorieInput_Datenverwaltung.getText());
    	KategorieInputKl_Datenverwaltung.getItems().addAll(KategorieInput_Datenverwaltung.getText());
    	KategorieInputSe_Datenverwaltung.getItems().addAll(KategorieInput_Datenverwaltung.getText());
    	KategorieInputSl_Datenverwaltung.getItems().addAll(KategorieInput_Datenverwaltung.getText());
    	Info_Datenverwaltung.setTextFill(Color.web("green"));
    	Info_Datenverwaltung.setText("Ihre Kategorie wurde erfolgreich erstellt");
    	reset();
    	init();
    	
    	}
    	}
    	catch (IllegalArgumentException e) {
    		Info_Datenverwaltung.setTextFill(Color.web("red"));
    		Info_Datenverwaltung.setText("Diese Kategorie gibt es bereits");
    		reset();
    		
    	}

    }

    @FXML
    void tfA_KategorieInput_Datenverwaltung(ActionEvent event) {
    	KategorieErstellen_button_Datenverwaltung.setDisable(false);
    	

    }

    @FXML
    void cbA_KategorieInputKl_Datenverwaltung(ActionEvent event) {
    	KategorieLöschen_button_Datenverwaltung.setDisable(false);
    	

    }

    @FXML
    void bA_subKategorieErstellen_Datenverwaltung(ActionEvent event) throws SQLException {
    	try{
        	if( Arrays.asList(model.return_Subkategorien(KategorieInputSe_Datenverwaltung.getValue())).contains(subKategorieInput_Datenverwaltung.getText()))
        		throw new IllegalArgumentException ();
        	else{
        		model.insert_Subkategorie(subKategorieInput_Datenverwaltung.getText(), KategorieInputSe_Datenverwaltung.getValue());
            	subkategorieInputSl_Datenverwaltung.getItems().addAll(subKategorieInput_Datenverwaltung.getText());
            	Info_Datenverwaltung.setTextFill(Color.web("green"));
            	Info_Datenverwaltung.setText("Ihre Subkategorie wurde erfolgreich angelegt");
            	reset();
            	init();
            	
        	}
        	}
        	catch (IllegalArgumentException e) {
        		Info_Datenverwaltung.setTextFill(Color.web("red"));
        		Info_Datenverwaltung.setText("Diese Subkategorie gibt es bereits");
        		reset();
        	}
    	
    }

    @FXML
    void bA_subKategorieLöschen_Datenverwaltung(ActionEvent event) throws SQLException {
    	model.deleteSubkategorie(subkategorieInputSl_Datenverwaltung.getValue(), 5);
    	subkategorieInputSl_Datenverwaltung.getItems().clear();
    	subkategorieInputSl_Datenverwaltung.getItems().addAll(model.return_Subkategorien(KategorieInputSl_Datenverwaltung.getValue()));
        
    }

    @FXML
    void cbA_KategorieInputSe_Datenverwaltung(ActionEvent event) throws SQLException {
    	subKategorieInput_Datenverwaltung.setDisable(false);
    	

    }

    @FXML
    void tfA_subKategorieInput_Datenverwaltung(ActionEvent event) {
    	subKategorieErstellen_button_Datenverwaltung.setDisable(false);

    }

    @FXML
    void cbA_KategorieInputSl_Datenverwaltung(ActionEvent event) throws SQLException {
    	subkategorieInputSl_Datenverwaltung.getItems().clear();
    	subkategorieInputSl_Datenverwaltung.setDisable(false);
    	subkategorieInputSl_Datenverwaltung.getItems().addAll(model.return_Subkategorien(KategorieInputSl_Datenverwaltung.getValue()));

    }

    @FXML
    void cbA_SubkategorieInputSl_Datenverwaltung(ActionEvent event) {
    	subKategorieLöschen_button_Datenverwaltung.setDisable(false);
    	

    }

    @FXML
    void bA_zurück_Datenverwaltung(ActionEvent event) throws IOException {
    	Stage stage=(Stage) zurück_button_Datenverwaltung.getScene().getWindow();
        restlicheViews.Menue menü = new restlicheViews.Menue();
        menü.setPrimaryStage(stage);
        menü.startUp_menue();

    }
    
    

}
