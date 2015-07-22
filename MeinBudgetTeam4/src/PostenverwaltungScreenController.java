import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.*;

public class PostenverwaltungScreenController {
	BudgetPlanModel model = new BudgetPlanModel ();
	
	@FXML
    private TableColumn<Posten, Date> datumTC_postenverwaltung;

    @FXML
    private TableColumn<Posten, String> bezeichnungTC_postenverwaltung;

    @FXML
    private AnchorPane mittlererAnchor_postenverwaltung;

    @FXML
    private TableColumn<Posten, String> subkategorieTC_postenverwaltung;

    @FXML
    private DatePicker anfangInput_postenverwaltung;

    @FXML
    private Button zurück_button_postenverwaltung;

    @FXML
    private RadioButton einkommenr_rb_postenverwaltung;

    @FXML
    private TableColumn<Posten, Number> anzahlTC_postenverwaltung;

    @FXML
    private ToggleGroup Group1;

    @FXML
    private TableColumn<Posten, String> kategorieTC_postenverwaltung;

    @FXML
    private RadioButton ausgaben_rb_postenverwaltung;

    @FXML
    private TableColumn<Posten, String> kommentarTC_postenverwaltung;

    @FXML
    private TableColumn<Posten, Number> dauerauftragTC_postenverwaltung;

    @FXML
    private DatePicker endeInput_postenvewaltung;

    @FXML
    private TableView<Posten> tabelle1;

    @FXML
    private TableColumn<Posten, Number> preisTC_postenverwaltung;

    @FXML
    private Button löschen_button_postenverwaltung;
    
    public void initialize() {
    	löschen_button_postenverwaltung.setDisable(true);
    	tabelle1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    	    if (newSelection != null) {
    	    	löschen_button_postenverwaltung.setDisable(false);
    	    }
    	});
    }

    @FXML
    void rbA_ausgaben(ActionEvent event) throws SQLException {
    	try {
    		if (anfangInput_postenverwaltung.getValue() == null || endeInput_postenvewaltung.getValue() == null)
    			throw new IllegalArgumentException();
    		else {
    			ArrayList <Posten> data = model.transcribe();
    		bezeichnungTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("bezeichnung"));
    		anzahlTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
    		kategorieTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("kategorie_bezeichnung"));
    		subkategorieTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("subkategorie_bezeichnung"));
    		anzahlTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
    		preisTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("preis"));
    		kommentarTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("kommentar"));
    		datumTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("datum"));
    		tabelle1.setItems(ConvertDataForChart.getObservableListFromTo(data, anfangInput_postenverwaltung.getValue(), endeInput_postenvewaltung.getValue()));

    		}
    	}
    	catch(IllegalArgumentException e) {
    		
    	}

    }

    @FXML
    void rbA_einkommen(ActionEvent event) {

    }

    @FXML
    void bA_löschen(ActionEvent event) throws SQLException {
    	Posten p = tabelle1.getSelectionModel().getSelectedItem();
    	model.deletePosten(p.getProdukt_ID());
    	model.update_User(model.getBudget() + (p.getAnzahl() * p.getPreis()));
    	
    	try {
    		if (anfangInput_postenverwaltung.getValue() == null || endeInput_postenvewaltung.getValue() == null)
    			throw new IllegalArgumentException();
    		else {
    			ArrayList <Posten> data = model.transcribe();
    		bezeichnungTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("bezeichnung"));
    		
    		kategorieTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("kategorie_bezeichnung"));
    		subkategorieTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("subkategorie_bezeichnung"));
    		anzahlTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
    		preisTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("preis"));
    		kommentarTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("kommentar"));
    		datumTC_postenverwaltung.setCellValueFactory(new PropertyValueFactory<>("datum"));
    		tabelle1.setItems(ConvertDataForChart.getObservableListFromTo(data, anfangInput_postenverwaltung.getValue(), endeInput_postenvewaltung.getValue()));

    		}
    	}
    	catch(IllegalArgumentException e) {
    		
    	}

    }

    @FXML
    void bA_zurück(ActionEvent event) throws SQLException, IOException {
    	Stage stage=(Stage) zurück_button_postenverwaltung.getScene().getWindow();
        restlicheViews.Menue menü = new restlicheViews.Menue();
        menü.setPrimaryStage(stage);
        menü.startUp_menue();

    

    }

}