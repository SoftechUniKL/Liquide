import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;

public class MenüScreenController {
	/** die ID´s der einzelnen Inhalte
	 * aus dem Scene Builder übernommen
	 */
	@FXML
    private Label datum_menue;

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
    private Button postenverwaltung_button_menue;

    @FXML
    private Button datenverwaltung_button_menue;
    /**
     * initialisiert das Fenster mit dynamischen Inhalten
     * notwendig, da ich es nicht anders hinbekommen habe
     * @throws SQLException wenn Fehler bei der SQL-Anfrage
     */
    public void initialize() throws SQLException {
    	BudgetPlanModel model = new BudgetPlanModel();
    	DateTime heute = new DateTime();
    	double rest = 0; // beim rest werden alle Daueraufträge addiert oder subtrahiert
    	/*Datum wird in String umgewandelt
    	 * Datum wird im Fenster ausgegeben
    	 */
    	Date heute1 = new Date();
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	String heutes = df.format(heute1);
    	datum_menue.setText(heutes);
    	/*alle Daueraufträge werden gesammelt
    	 * ind positive und negative Daueraufträge getrennt
    	 * positive --> bringen Geld ein
    	 * negative --> kosten Geld
    	 * 
    	 */
    	ArrayList<Posten> data = model.transcribe();
		ArrayList<Posten> dataDA = new ArrayList<Posten>();
		ArrayList<Posten> dataDApos = new ArrayList<Posten>();
		ArrayList<Posten> dataDAneg = new ArrayList<Posten>();
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getDauerauftrag() <= 0)
				continue;
			else
				dataDA.add(data.get(i));
		}	
		for (int i = 0; i < dataDA.size(); i++) {
			if (dataDA.get(i).getKategorie_bezeichnung()
					.equals("Einkommen"))
				dataDApos.add(dataDA.get(i));
			else
				dataDAneg.add(dataDA.get(i));
		}
		/* vergleicht jedesmal wieviel Daueraufträge seit dem Eintrag und bis zum heutigen Tag abgebucht wurden
		 * ein wenig umständlich, aber mit Quatz Scheduler hab ich es nicht hinbekommen
		 */
		for (int i = 0 ;i< dataDApos.size(); i ++) {
			DateTime obj = new DateTime (dataDApos.get(i).getDatum()) ;
			   for (int j = 1 ; heute.compareTo(obj.plusMonths(dataDApos.get(i).getDauerauftrag()*j)) > 0; j++ ){
				   rest = rest + dataDApos.get(i).getPreis();	   
			   }
		}
		for (int i = 0 ;i< dataDAneg.size(); i ++) {
			DateTime obj = new DateTime (dataDAneg.get(i).getDatum()) ;
			   for (int j = 1 ; heute.compareTo(obj.plusMonths(dataDAneg.get(i).getDauerauftrag()*j)) > 0; j++ ){
				   rest = rest - dataDAneg.get(i).getPreis();	  	   
			   }
		}
		
		String s = String.valueOf(model.getBudget() + rest);
    	aktuellesBudgetOutput_Menü.setText(s);
    	
		
		
		
		
		
    	
    }
    /*die einzelne Buttons, die zum nächsten Fenster führen
     * es werden jeweils Views gebildet zu jedem Fenster
     */
    @FXML
    void bA_neuerPosten_menue(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage)neuerPosten_button_menue.getScene().getWindow();
        restlicheViews.neuerPosten newPost = new restlicheViews.neuerPosten();
        newPost.setPrimaryStage(stage);
        newPost.startUp_neuerPosten();

    }

    @FXML
    void bA_ausgaben_menue(ActionEvent event) throws IOException {
    	Stage stage=(Stage)ausgaben_button_menue.getScene().getWindow();
        restlicheViews.ausgaben ag = new restlicheViews.ausgaben();
        ag.setPrimaryStage(stage);
        ag.startUp_ausgaben();

    }

    @FXML
    void bA_postenverwaltung_menue(ActionEvent event) throws IOException {
    	Stage stage=(Stage)postenverwaltung_button_menue.getScene().getWindow();
        restlicheViews.postenverwaltung pw = new restlicheViews.postenverwaltung();
        pw.setPrimaryStage(stage);
        pw.startUp_postenverwaltung();

    }

    @FXML
    void bA_datenverwaltung_menue(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage)datenverwaltung_button_menue.getScene().getWindow();
        restlicheViews.datenverwaltung dv = new restlicheViews.datenverwaltung();
        dv.setPrimaryStage(stage);
        dv.startUp_datenverwaltung();

    }

    @FXML
    void bA_optionen_menue(ActionEvent event) throws IOException, SQLException  {
    	Stage stage=(Stage)datenverwaltung_button_menue.getScene().getWindow();
        restlicheViews.optionen op = new restlicheViews.optionen();
        op.setPrimaryStage(stage);
        op.startUp_optionen();

    }

    @FXML
    void bA_Einkommen_Menü(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage)datenverwaltung_button_menue.getScene().getWindow();
        restlicheViews.einkommen ek = new restlicheViews.einkommen();
        ek.setPrimaryStage(stage);
        ek.startUp_datenverwaltung();

    }
    
    
   

}
