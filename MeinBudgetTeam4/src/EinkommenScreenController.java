import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;

public class EinkommenScreenController {
	
	/** die ID´s der einzelnen Inhalte
	 * aus dem Scene Builder übernommen
	 */
    
    @FXML
    private ToggleGroup Group1;
	
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
    /** initialisiert das Fenster mit dyanamischen Werten
     * 
     */
    public void initialize() {
    	/*prüft ob die Zahl eine korrekte double Zahl ist
    	 * 1:1 aus dem neuerPostenScreenController übernommen
    	 */
    	EinzahlungInput.textProperty().addListener(
				new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> observable,
							String oldValue, String newValue) {
						if(newValue.length()< oldValue.length()){
							
						}
						else {
						if(newValue.length()>0) {
							char first = newValue.charAt(0);
							char last = newValue.charAt(newValue.length()-1);
							if(newValue.length() == 1) {
								if(last == '0'||last == '1'||last == '2'||last == '3'||last == '4'||last == '5'||last == '6'||last == '7'||last == '8'||last == '9'){
									
								}
								else {
									EinzahlungInput.setText(oldValue);
								}
							}
							else{
								if(newValue.length() == 2) {
									if(first == '0') {
										if(last == ',') {
											
										}
										else{
											EinzahlungInput.setText(oldValue);
										}
									}
									else {
										if(last == ','||last == '0'||last == '1'||last == '2'||last == '3'||last == '4'||last == '5'||last == '6'||last == '7'||last == '8'||last == '9'){
											
										}
										else{
											EinzahlungInput.setText(oldValue);
										}
									}
								}
								else {
									if(newValue.length() > 2) {
                                        if(last ==',' ||last == '0'||last == '1'||last == '2'||last == '3'||last == '4'||last == '5'||last == '6'||last == '7'||last == '8'||last == '9'){
											if(oldValue.contains(",")){
												if(newValue.length() > (oldValue.indexOf(',') + 3) || last == ',') {
													EinzahlungInput.setText(oldValue);
                                    			}
                                    			else {
                                    				
                                    				
                                    			}
											}
											else {
												
											}
										}
                                        else {
                                        	if(oldValue.contains(",")) {
                                        		if(last == ',') {
                                        			EinzahlungInput.setText(oldValue);
                                        		}
                                        		else {
                                        			if(newValue.length() > (oldValue.indexOf(',') + 3)) {
                                        				EinzahlungInput.setText(oldValue);
                                        			}
                                        			else {
                                        				EinzahlungInput.setText(oldValue);
                                        				
                                        			}
                                        		}
                                        	}
                                        	else{
                                        		EinzahlungInput.setText(oldValue);
                                        	}
                                        }
									}
								}
							}
						}
						else{
							
						}
						}
					}
				});
    }
    /**
     * Fenster wird in den Ursprungszustand gebracht
     */
    public void init (){
    	monatlich_rb_Einkommen.setSelected(false);
    	einmalig_rb_Einkommen.setSelected(false);
    	EinzahlungInput.setText("");
    	bezeichnungInput_einkommen.setText("");
    }
    /** prüft ob die Eingaben okay sind, wenn ja wird Posten übernommen
     *  prüft ob Posten einmalig ist oder monatlich
     * @throws SQLException wenn Problem mit SQL-Anfrage
     */
    
    @FXML
    void bA_übernehmen_Einkommen(ActionEvent event) throws SQLException {
    	try {
    		Date heute = new Date();
		    java.sql.Timestamp sqlheute = new java.sql.Timestamp(heute.getTime());
    		BudgetPlanModel model = new BudgetPlanModel();
    		String ein = EinzahlungInput.getText();
    		double e;
    		NumberFormat nf_in = NumberFormat
					.getNumberInstance(Locale.GERMANY); // Grüße von Shahin
			e = nf_in.parse(ein).doubleValue();
    		
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
    	catch(ParseException f) {
    		Info_Einkommen.setTextFill(Color.web("red"));
    		Info_Einkommen.setText("Sie müssen eine korrekte Zahl eingeben");
    	}
    	
    
    	

    }
    /** zurück zum Hauptmenü
     * @throws SQLException
     */
    @FXML
    void bA_zurück_Einkommen(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage) zurück_Button_Einkommen.getScene().getWindow();
        restlicheViews.Menue menü = new restlicheViews.Menue();
        menü.setPrimaryStage(stage);
        menü.startUp_menue();

    }

}
