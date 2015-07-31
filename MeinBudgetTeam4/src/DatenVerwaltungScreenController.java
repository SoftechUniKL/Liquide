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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;



public class DatenVerwaltungScreenController {
	BudgetPlanModel model = new BudgetPlanModel ();
	
    /** alle Elemente mit ID´s aus dem Scenebuilder übernommen*/
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
    	String [] s = new String [model.return_Kategorien().length - 1] ;
    	for (int i = 0 ; i < s.length ; i++)
    		s[i] = model.return_Kategorien()[i+1];
    	Arrays.sort(s);
    	KategorieInputKl_Datenverwaltung.getItems().addAll(s);
    	KategorieInputSe_Datenverwaltung.getItems().addAll(s);
    	KategorieInputSl_Datenverwaltung.getItems().addAll(s);
    	KategorieErstellen_button_Datenverwaltung.setDisable(true);
    	KategorieLöschen_button_Datenverwaltung.setDisable(true);
    	subKategorieInput_Datenverwaltung.setDisable(true);
    	subkategorieInputSl_Datenverwaltung.setDisable(true);
    	subKategorieErstellen_button_Datenverwaltung.setDisable(true);
    	subKategorieLöschen_button_Datenverwaltung.setDisable(true);
    	subKategorieInput_Datenverwaltung.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    	    if(newValue.length() > 0) 
		    	    	subKategorieErstellen_button_Datenverwaltung.setDisable(false);
		    	    else
		    	    	subKategorieErstellen_button_Datenverwaltung.setDisable(true);	    	
		    }
		});
    	KategorieInput_Datenverwaltung.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    	    if(newValue.length() > 0) 
		    	    	KategorieErstellen_button_Datenverwaltung.setDisable(false);
		    	    else
		    	    	KategorieErstellen_button_Datenverwaltung.setDisable(true);	    	
		    }
		});
    }
    /* init() versetzt das Fenster in den Ursprungszustand*/
    public void init(){
    	KategorieInputKl_Datenverwaltung.setValue(null);
    	KategorieInputSe_Datenverwaltung.setValue(null);
    	KategorieInputSl_Datenverwaltung.setValue(null);
    	KategorieInput_Datenverwaltung.setText("");
    	subKategorieInput_Datenverwaltung.setText("");
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
    /**
     *  Button für Kategorie löschen 
     *  kann kaum was schief gehen, das Weg festgelegt
     */
    @FXML
    void bA_KategorieLöschen_Datenverwaltung(ActionEvent event) throws SQLException {
    	String [] s =  model.return_Subkategorien(KategorieInputKl_Datenverwaltung.getValue());
    	for(int i= 0; i< model.return_Subkategorien(KategorieInputKl_Datenverwaltung.getValue()).length ;i++)
    		model.deleteSubkategorie(s [i] ,KategorieInputKl_Datenverwaltung.getValue());
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
/** Wenn Kategorie bereits besteht, kommt eine Fehlermeldung. Ansonsten wird Kategorie aufgenommen. 
 * Die neue Kategorie wird in die restlichen comboboxen eingefügt
 */
    @FXML
    void bA_KategorieErstellen_Datenverwaltung(ActionEvent event) throws SQLException {
    	try{
    	if( Arrays.asList(model.return_Kategorien()).contains(KategorieInput_Datenverwaltung.getText()))
    		throw new IllegalArgumentException ();
    	else{
    	model.insert_Kategorie(KategorieInput_Datenverwaltung.getText());
    	KategorieInput_Datenverwaltung.setText("");
    	KategorieInputKl_Datenverwaltung.getItems().clear();
    	KategorieInputSe_Datenverwaltung.getItems().clear();
    	KategorieInputSl_Datenverwaltung.getItems().clear();
    	initialize();
    	Info_Datenverwaltung.setTextFill(Color.web("green"));
    	Info_Datenverwaltung.setText("Ihre Kategorie wurde erfolgreich erstellt");
    	reset();
    	
    	
    	}
    	}
    	catch (IllegalArgumentException e) {
    		Info_Datenverwaltung.setTextFill(Color.web("red"));
    		Info_Datenverwaltung.setText("Diese Kategorie gibt es bereits");
    		reset();
    		
    	}

    }

   
    /** Button wir aktiviert, wenn Kategorie ausgewählt wurde
     */
    @FXML
    void cbA_KategorieInputKl_Datenverwaltung(ActionEvent event) {
    	KategorieLöschen_button_Datenverwaltung.setDisable(false);
    	

    }
    /** erstellt Subkategorie, falls sie noch nicht vorhanden ist
     * @throws SQLException
     */
    @FXML
    void bA_subKategorieErstellen_Datenverwaltung(ActionEvent event) throws SQLException {
    	try{
        	if( Arrays.asList(model.return_Subkategorien(KategorieInputSe_Datenverwaltung.getValue())).contains(subKategorieInput_Datenverwaltung.getText()))
        		throw new IllegalArgumentException ();
        	else{
        		model.insert_Subkategorie(subKategorieInput_Datenverwaltung.getText(), KategorieInputSe_Datenverwaltung.getValue());
            	subkategorieInputSl_Datenverwaltung.getItems().clear();
            	String [] s = model.return_Subkategorien(KategorieInputSl_Datenverwaltung.getValue());
            	Arrays.sort(s);
            	subkategorieInputSl_Datenverwaltung.getItems().addAll(s);
            	Info_Datenverwaltung.setTextFill(Color.web("green"));
            	Info_Datenverwaltung.setText("Ihre Subkategorie wurde erfolgreich angelegt");
            	reset();
            	init();
        	}
        	}
    	    /* Fehler, falls Subkategorie bereits besteht
    	     */
        	catch (IllegalArgumentException e) {
        		Info_Datenverwaltung.setTextFill(Color.web("red"));
        		Info_Datenverwaltung.setText("Diese Subkategorie gibt es bereits");
        		reset();
        	}	
    }
    
    /** löscht die Ausgewählt Subkategorie
     *  kann kaum was schief gehen, da der Weg bis zum löschen festgelegt ist
     * @throws SQLException
     */
    @FXML
    void bA_subKategorieLöschen_Datenverwaltung(ActionEvent event) throws SQLException {
    	model.deleteSubkategorie(subkategorieInputSl_Datenverwaltung.getValue(),KategorieInputSl_Datenverwaltung.getValue() );
    	subkategorieInputSl_Datenverwaltung.getItems().clear();
    	subkategorieInputSl_Datenverwaltung.getItems().addAll(model.return_Subkategorien(KategorieInputSl_Datenverwaltung.getValue()));
    	init();
    	Info_Datenverwaltung.setTextFill(Color.web("green"));
    	Info_Datenverwaltung.setText("Ihre Subkategorie wurde erfolgreich gelöscht");
    	reset();   
    }
    
    /** Wenn Kategorie ausgewählt wurde, wird die Combobox der Subkategorie aktiviert
     * @throws SQLException
     */
    @FXML
    void cbA_KategorieInputSe_Datenverwaltung(ActionEvent event) throws SQLException {
    	subKategorieInput_Datenverwaltung.setDisable(false);
    }
  
    /** Wenn neue Kategorie ausgewählt wurde, wird die Combobox der Subkategorie mit den
     *  richtigen Werten gefüllt
     * @throws SQLException
     */
    @FXML
    void cbA_KategorieInputSl_Datenverwaltung(ActionEvent event) throws SQLException {
    	subkategorieInputSl_Datenverwaltung.getItems().clear();
    	subkategorieInputSl_Datenverwaltung.setDisable(false);
    	String [] s = model.return_Subkategorien(KategorieInputSl_Datenverwaltung.getValue());
    	Arrays.sort(s);
    	subkategorieInputSl_Datenverwaltung.getItems().addAll(s);
    }
    
    /**wenn Subkategorie ausgewählt wurde, dann wird Button aktiviert
     */
    @FXML
    void cbA_SubkategorieInputSl_Datenverwaltung(ActionEvent event) {
    	subKategorieLöschen_button_Datenverwaltung.setDisable(false);
    }
    /** identisch zu allen anderen zurück-Buttons
     * @throws SQLException
     */
    @FXML
    void bA_zurück_Datenverwaltung(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage) zurück_button_Datenverwaltung.getScene().getWindow();
        restlicheViews.Menue menü = new restlicheViews.Menue();
        menü.setPrimaryStage(stage);
        menü.startUp_menue();
    }
}
