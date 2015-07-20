import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.*;

public class EinkommenScreenController {
	
	@FXML
	private TextField bezeichnungInput_einkommen;

    @FXML
    private RadioButton monatlich_rb_Einkommen;

    @FXML
    private Label Info_Einkommen;

    @FXML
    private RadioButton einmalig_rb_Einkommen;

    @FXML
    private Button übernehmen_Button_Einkommen;

    @FXML
    private TextField EinzahlungInput;

    @FXML
    private Button zurück_Button_Einkommen;
    
    public void init (){
    	monatlich_rb_Einkommen.setSelected(false);
    	einmalig_rb_Einkommen.setSelected(false);
    	EinzahlungInput.setText("");
    	bezeichnungInput_einkommen.setText("");
    }

    
    @FXML
    void bA_übernehmen_Einkommen(ActionEvent event) throws SQLException {
    	try{
    		Date heute = new Date();
		    java.sql.Timestamp sqlheute = new java.sql.Timestamp(heute.getTime());
    		BudgetPlanModel model = new BudgetPlanModel();
    		double e = Double.parseDouble(EinzahlungInput.getText());
    		String bez = bezeichnungInput_einkommen.getText();
    		if(bez.isEmpty()){
    			Info_Einkommen.setTextFill(Color.web("red"));
    			Info_Einkommen.setText("Es fehlt eine Bezeichnung");
    		}
    		else{
    			if(monatlich_rb_Einkommen.isSelected() == false & einmalig_rb_Einkommen.isSelected() == false) {
    				Info_Einkommen.setTextFill(Color.web("red"));
        			Info_Einkommen.setText("Sie müssen zwischen 'monatlich' und 'einmalig' wählen");
    			}
    			else {
    				if(monatlich_rb_Einkommen.isSelected()){
    					model.insert_Posten(bez, "Einkommen","-", e, 1, "", 1, sqlheute);	
    		    		model.update_User(model.getBudget() + e);
    		    		Info_Einkommen.setTextFill(Color.web("green"));
    		    		Info_Einkommen.setText("Die Einzahlung war erfolgreich");
    		    		init();
    				}
    				else {
    					model.insert_Posten(bez, "Einkommen","-", e, 1, "", 0, sqlheute);	
    		    		model.update_User(model.getBudget() + e);
    		    		Info_Einkommen.setTextFill(Color.web("green"));
    		    		Info_Einkommen.setText("Die Einzahlung war erfolgreich");
    		    		init();
    				}
    			}
    		
    		}
    	}
    	/* wenn keine Zahl eingegeben wird, kommt eine Fehlermeldung */
    	catch (IllegalArgumentException e) {
    		Info_Einkommen.setTextFill(Color.web("red"));
    		Info_Einkommen.setText("Dies ist keine Zahl");
    	}
    
    	

    }

    @FXML
    void bA_zurück_Einkommen(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage) zurück_Button_Einkommen.getScene().getWindow();
        restlicheViews.Menue menü = new restlicheViews.Menue();
        menü.setPrimaryStage(stage);
        menü.startUp_menue();

    }

}
