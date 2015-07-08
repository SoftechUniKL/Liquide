import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.*;

public class MenüScreenController {

    @FXML
    private Button neuerPosten_button_menue;

    @FXML
    private Label aktuellesBudgetOutput_Menü;

    @FXML
    private Button optionen_button_menue;

    @FXML
    private Button ausgaben_button_menue;

    @FXML
    private Button Einkommen_Button_Menü;

    @FXML
    private AnchorPane menue_pane_menue;

    @FXML
    private Button budgetverwaltung_button_menue;

    @FXML
    private Button datenverwaltung_button_menue;

    public void initialize() throws SQLException {
    	BudgetPlanModel model = new BudgetPlanModel();
    	String s = String.valueOf(model.getBudget());
    	aktuellesBudgetOutput_Menü.setText(s);
    	
    }
    @FXML
    void bA_neuerPosten_menue(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage)neuerPosten_button_menue.getScene().getWindow();
        restlicheViews.neuerPosten newPost = new restlicheViews.neuerPosten();
        newPost.setPrimaryStage(stage);
        newPost.startUp_neuerPosten();

    }

    @FXML
    void bA_ausgaben_menue(ActionEvent event) {

    }

    @FXML
    void bA_budgetverwaltung_menue(ActionEvent event) {

    }

    @FXML
    void bA_datenverwaltung_menue(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage)datenverwaltung_button_menue.getScene().getWindow();
        restlicheViews.datenverwaltung dv = new restlicheViews.datenverwaltung();
        dv.setPrimaryStage(stage);
        dv.startUp_datenverwaltung();

    }

    @FXML
    void bA_optionen_menue(ActionEvent event) {

    }

    @FXML
    void bA_Einkommen_Menü(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage)datenverwaltung_button_menue.getScene().getWindow();
        restlicheViews.einkommen ek = new restlicheViews.einkommen();
        ek.setPrimaryStage(stage);
        ek.startUp_datenverwaltung();

    }

}
