import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.*;

public class EinkommenScreenController {

    @FXML
    private RadioButton monatlich_rb_Einkommen;

    @FXML
    private Label Info_Einkommen;

    @FXML
    private RadioButton einmalig_rb_Einkommen;

    @FXML
    private Button �bernehmen_Button_Einkommen;

    @FXML
    private TextField EinzahlungInput;

    @FXML
    private Button zur�ck_Button_Einkommen;

    
    @FXML
    void bA_�bernehmen_Einkommen(ActionEvent event) throws SQLException {
    	try{
    		BudgetPlanModel model = new BudgetPlanModel();
    		double e = Double.parseDouble(EinzahlungInput.getText());
    		model.update_User(e);
    	}
    	catch (IllegalArgumentException e) {
    		Info_Einkommen.setText("Dies ist keine Zahl");
    	}
    
    	

    }

    @FXML
    void bA_zur�ck_Einkommen(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage) zur�ck_Button_Einkommen.getScene().getWindow();
        restlicheViews.Menue men� = new restlicheViews.Menue();
        men�.setPrimaryStage(stage);
        men�.startUp_menue();

    }

}
