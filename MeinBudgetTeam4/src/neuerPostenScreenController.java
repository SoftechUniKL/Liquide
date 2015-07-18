import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.*;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class neuerPostenScreenController {
	BudgetPlanModel model = new BudgetPlanModel();
	
	@FXML
    private Label posten_labelred_neuerPosten;

    @FXML
    private Label preis_labelred_neuerPosten;
    
    @FXML
    private Label kategorie_labelred_neuerPosten;
    
    @FXML
    private Label anzahl_labelred_neuerPosten;
    
    @FXML
    private Label subkategorie_labelred_neuerPosten;
    
	@FXML
	private Label übernehmen_label_neuerPosten;

	@FXML
	private Button zurück_neuerPosten_button;

	@FXML
	private CheckBox dauerauftragWechsel_neuerPosten;

	@FXML
	private ComboBox<String> kategoerieInput_neuerPosten = new ComboBox<String>();

	@FXML
	private TextArea kommentarInput_neuerPosten;

	@FXML
	private ComboBox<String> dauerauftragInput_neuerposten = new ComboBox<String>();

	@FXML
	private TextField postenInput_neuerPosten;

	@FXML
	private AnchorPane neuerPosten_pane;

	@FXML
	private ComboBox<String> subkategorieInput_neuerPosten = new ComboBox<String>();

	@FXML
	private TextField preisInput_neuerPosten;

	@FXML
	private TextField anzahlInput_neuerPosten;

	@FXML
	private Button übernehmen_button;

	public void initialize() throws SQLException {
		kategoerieInput_neuerPosten.getItems()
				.addAll(model.return_Kategorien());
		subkategorieInput_neuerPosten.setDisable(true);
		dauerauftragInput_neuerposten.setDisable(true);

	}

	@FXML
	void cbA_kategorieInput_neuerPosten(ActionEvent event) throws SQLException {
		subkategorieInput_neuerPosten.getItems().clear();
		subkategorieInput_neuerPosten.setDisable(false);
		subkategorieInput_neuerPosten.getItems().addAll(
				model.return_Subkategorien(kategoerieInput_neuerPosten
						.getValue()));

	}

	@FXML
	void bA_übernehmen(ActionEvent event) throws SQLException {
		try {
			String p = postenInput_neuerPosten.getText();
			String a = anzahlInput_neuerPosten.getText();
			Integer anzahl;
			String pr = preisInput_neuerPosten.getText();
			Double preis;
			String kat = kategoerieInput_neuerPosten.getValue();
			String s = subkategorieInput_neuerPosten.getValue();
			String kom = kommentarInput_neuerPosten.getText();
			String dauer = dauerauftragInput_neuerposten.getValue();
		    Integer d = 0;
		   
		    Date heute = new Date();
		    java.sql.Timestamp sqlheute = new java.sql.Timestamp(heute.getTime());
		    
	
			
			if (p.isEmpty() || a.isEmpty() || pr.isEmpty() || kat == null
					|| kat == null)
				throw new IllegalArgumentException();
			else {
				try {
					anzahl = Integer.parseInt(a);
					preis = Double.parseDouble(pr);
					try {
						switch (dauer) {
						case "monatlich":
							d = 1;
							break;
						case "alle drei Monate":
							d = 3;
							break;
						case "halbjährlich":
							d = 6;
							break;
						case "jährlich":
							d = 12;
							break;
						default:
							d = 0;
							break;
						}
					} catch (NullPointerException e) {

					}

				}

				catch (NumberFormatException e) {
					übernehmen_label_neuerPosten.setText("Anzahl oder Preis stimmen nicht");
					anzahl_labelred_neuerPosten.setTextFill(Color.web("red"));
					anzahl_labelred_neuerPosten.setText("*");
					return;

				}
				
				model.insert_Posten(p, kat, s, preis, anzahl, kom, d, sqlheute);
					
					
				
				
				
				übernehmen_label_neuerPosten.setText("Ihr Posten wurde erfolgreich übernommen");

			}
		}

		catch (IllegalArgumentException e) {
			übernehmen_label_neuerPosten.setText("Ihre Angaben sind nicht vollständig");
            
            	
            	
			}
				
			

		}
	

	@FXML
	void cbc_dauerauftragWechsel_neuerPosten(ActionEvent event) {
		if (dauerauftragWechsel_neuerPosten.isSelected() == true) {
			dauerauftragInput_neuerposten.setDisable(false);
			dauerauftragInput_neuerposten.getItems().addAll("monatlich",
					"alle drei Monate", "halbjährlich", "jährlich");
		} else {
			dauerauftragInput_neuerposten.setDisable(true);
			dauerauftragInput_neuerposten.getItems().clear();
		}
	}

	@FXML
	void bA_zurück_neuerPosten(ActionEvent event) throws IOException, SQLException {
		/*ArrayList  a = model.transcribe();
		for(int i=0 ; i<a.size(); i++)
			System.out.println(a.get(0));*/
		Stage stage = (Stage) zurück_neuerPosten_button.getScene().getWindow();
		restlicheViews.Menue menü = new restlicheViews.Menue();
		menü.setPrimaryStage(stage);
		menü.startUp_menue();

	}

}
