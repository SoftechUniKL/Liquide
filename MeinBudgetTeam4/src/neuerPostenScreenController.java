import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.*;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class neuerPostenScreenController {
	BudgetPlanModel model = new BudgetPlanModel ();
	@FXML
    private Label �bernehmen_label_neuerPosten;

    @FXML
    private Button zur�ck_neuerPosten_button;

    @FXML
    private CheckBox dauerauftragWechsel_neuerPosten;

    @FXML
    private ComboBox<String> kategoerieInput_neuerPosten = new ComboBox<String> ();

    @FXML
    private TextArea kommentarInput_neuerPosten;

    @FXML
    private ComboBox<String> dauerauftragInput_neuerposten = new ComboBox <String>() ;

    @FXML
    private TextField postenInput_neuerPosten;

    @FXML
    private AnchorPane neuerPosten_pane;

    @FXML
    private ComboBox<String> subkategorieInput_neuerPosten = new ComboBox <String>();

    @FXML
    private TextField preisInput_neuerPosten;

    @FXML
    private TextField anzahlInput_neuerPosten;

    @FXML
    private Button �bernehmen_button;
    public void initialize() throws SQLException{
    	kategoerieInput_neuerPosten.getItems().addAll(model.return_Kategorien());
    	subkategorieInput_neuerPosten.setDisable(true);
    	dauerauftragInput_neuerposten.getItems().addAll("monatlich","alle drei Monate","halbj�hrlich","j�hrlich");
    	dauerauftragInput_neuerposten.setDisable(true);
    	
    	
    	
    }

    @FXML
    void cbA_kategorieInput_neuerPosten(ActionEvent event) {
    	subkategorieInput_neuerPosten.setDisable(false);
    	subkategorieInput_neuerPosten.getItems().addAll(kategoerieInput_neuerPosten.getValue());
    	

    }
    

    @FXML
    void bA_�bernehmen(ActionEvent event) {
    	�bernehmen_label_neuerPosten.setText("neuer Posten wurde erfolgreich erstellt");

    }

    @FXML
    void cbc_dauerauftragWechsel_neuerPosten(ActionEvent event) {
    	if (dauerauftragWechsel_neuerPosten.isSelected() == true )
    		dauerauftragInput_neuerposten.setDisable(false);
    	else
    		dauerauftragInput_neuerposten.setDisable(true);

    }

    @FXML
    void bA_zur�ck_neuerPosten(ActionEvent event) throws IOException {
    	Stage stage=(Stage) zur�ck_neuerPosten_button.getScene().getWindow();
        restlicheViews.Menue men� = new restlicheViews.Menue();
        men�.setPrimaryStage(stage);
        men�.startUp_menue();

    }

}
