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
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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

	/* initialisiert das Fenster mit den notwendigen Werten beim Öffnen */
	public void initialize() throws SQLException {
		String[] s = new String[model.return_Kategorien().length - 1];
		for (int i = 0; i < s.length; i++)
			s[i] = model.return_Kategorien()[i + 1];
		Arrays.sort(s);
		kategoerieInput_neuerPosten.getItems().addAll(s);
		subkategorieInput_neuerPosten.setDisable(true);
		dauerauftragInput_neuerposten.setDisable(true);
        /* prüft ob die Eingabe des Preises mit einer double Zahl übereinstimmt
         * Komma darf nur einmal gesetzt werden
         * nach einem Komma erfolgen nur zwei Stellen
         */
		preisInput_neuerPosten.textProperty().addListener(
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
									preisInput_neuerPosten.setText(oldValue);
								}
							}
							else{
								if(newValue.length() == 2) {
									if(first == '0') {
										if(last == ',') {
											
										}
										else{
											preisInput_neuerPosten.setText(oldValue);
										}
									}
									else {
										if(last == ','||last == '0'||last == '1'||last == '2'||last == '3'||last == '4'||last == '5'||last == '6'||last == '7'||last == '8'||last == '9'){
											
										}
										else{
											preisInput_neuerPosten.setText(oldValue);
										}
									}
								}
								else {
									if(newValue.length() > 2) {
                                        if(last ==',' ||last == '0'||last == '1'||last == '2'||last == '3'||last == '4'||last == '5'||last == '6'||last == '7'||last == '8'||last == '9'){
											if(oldValue.contains(",")){
												if(newValue.length() > (oldValue.indexOf(',') + 3) || last == ',') {
                                    				preisInput_neuerPosten.setText(oldValue);
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
                                        			preisInput_neuerPosten.setText(oldValue);
                                        		}
                                        		else {
                                        			if(newValue.length() > (oldValue.indexOf(',') + 3)) {
                                        				preisInput_neuerPosten.setText(oldValue);
                                        			}
                                        			else {
                                        				preisInput_neuerPosten.setText(oldValue);
                                        				
                                        			}
                                        		}
                                        	}
                                        	else{
                                        		preisInput_neuerPosten.setText(oldValue);
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

		anzahlInput_neuerPosten.textProperty().addListener(
				new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> observable,
							String oldValue, String newValue) {
						if (newValue.length() > 0) {
							char eins = newValue.charAt(0);
							char last = newValue.charAt(newValue.length() - 1);
							if (eins == '0') {
								anzahlInput_neuerPosten.setText(oldValue);
								übernehmen_label_neuerPosten.setTextFill(Color
										.web("red"));
								übernehmen_label_neuerPosten
										.setText("Die Zahl darf nicht mit einer '0' beginnen");
								reset();

							} else {
								if (last == '0' || last == '1' || last == '2'
										|| last == '3' || last == '4'
										|| last == '5' || last == '6'
										|| last == '7' || last == '8'
										|| last == '9') {
									anzahlInput_neuerPosten.setText(newValue);
									übernehmen_label_neuerPosten.setText("");
								} else {
									anzahlInput_neuerPosten.setText(oldValue);
									übernehmen_label_neuerPosten
											.setTextFill(Color.web("red"));
									übernehmen_label_neuerPosten
											.setText("Sie dürfen nur Zahlen benutzen");
									reset();
								}
							}
						}
					}

				});

	}

	/* versetzt das Fenster in den Urpsrung */
	public void init() {
		kategoerieInput_neuerPosten.setValue(null);
		subkategorieInput_neuerPosten.setValue(null);
		dauerauftragInput_neuerposten.setValue(null);
		postenInput_neuerPosten.setText(null);
		anzahlInput_neuerPosten.setText(null);
		preisInput_neuerPosten.setText(null);
		kommentarInput_neuerPosten.setText(null);
		dauerauftragWechsel_neuerPosten.setSelected(false);
		subkategorieInput_neuerPosten.setDisable(true);
		dauerauftragInput_neuerposten.setDisable(true);
	}

	/* reset() leert das Label nach 10 sekunden */
	public void reset() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10),
				ev -> {
					übernehmen_label_neuerPosten.setText(null);
				}));
		timeline.play();
	}

	/*
	 * beim auswählen eines wertes in der Kategorie combobox wird die
	 * Subkategorie combobox aktiviert und die jeweiligen Daten sortiert
	 * eingetragen
	 */
	@FXML
	void cbA_kategorieInput_neuerPosten(ActionEvent event) throws SQLException {
		String[] s = model.return_Subkategorien(kategoerieInput_neuerPosten
				.getValue());
		Arrays.sort(s);
		subkategorieInput_neuerPosten.getItems().clear();
		subkategorieInput_neuerPosten.setDisable(false);
		subkategorieInput_neuerPosten.getItems().addAll(s);

	}

	/* Button um den Posten zu übernehmen */
	@FXML
	void bA_übernehmen(ActionEvent event) throws SQLException {
		try {
			String p = postenInput_neuerPosten.getText();
			String a = anzahlInput_neuerPosten.getText();
			Integer anzahl;
			String pr = preisInput_neuerPosten.getText();
			double preis;
			String kat = kategoerieInput_neuerPosten.getValue();
			String s = subkategorieInput_neuerPosten.getValue();
			String kom = kommentarInput_neuerPosten.getText();
			String dauer = dauerauftragInput_neuerposten.getValue();
			Integer d = 0;

			Date heute = new Date();
			java.sql.Timestamp sqlheute = new java.sql.Timestamp(
					heute.getTime());

			if (p.isEmpty() || a.isEmpty() || pr.isEmpty() || kat == null
					|| kat == null) {

				/*
				 * prüft ob in den feldern etwas steht, wenn nicht, dann wird
				 * ein rotes "*" bei diesem feld angezeigt
				 */
				if (p.isEmpty()) {
					posten_labelred_neuerPosten.setText("*");
				} else
					posten_labelred_neuerPosten.setText("");
				if (a.isEmpty()) {
					anzahl_labelred_neuerPosten.setText("*");
				} else
					anzahl_labelred_neuerPosten.setText("");
				if (pr.isEmpty()) {
					preis_labelred_neuerPosten.setText("*");
				} else
					preis_labelred_neuerPosten.setText("");
				if (kat == null) {
					kategorie_labelred_neuerPosten.setText("*");
				} else
					kategorie_labelred_neuerPosten.setText("");
				if (s == null) {
					subkategorie_labelred_neuerPosten.setTextFill(Color
							.web("red"));
					subkategorie_labelred_neuerPosten.setText("*");
				} else
					subkategorie_labelred_neuerPosten.setText("");
				throw new IllegalArgumentException();
			}

			/* prüft ob die Anzahl eine Integer Zahl ist */
			try {
				anzahl = Integer.parseInt(a);
			} catch (NumberFormatException e) {
				posten_labelred_neuerPosten.setText("");
				preis_labelred_neuerPosten.setText("");
				kategorie_labelred_neuerPosten.setText("");
				subkategorie_labelred_neuerPosten.setText("");
				übernehmen_label_neuerPosten.setTextFill(Color.web("red"));
				übernehmen_label_neuerPosten
						.setText("Die Anzahlangabe ist falsch");
				anzahl_labelred_neuerPosten.setText("*");
				return;

			}

			/* prüft ob der Preis eine double Zahl ist */
			try {
				// preis = Double.parseDouble(pr);
				NumberFormat nf_in = NumberFormat
						.getNumberInstance(Locale.GERMANY); // Grüße von Shahin
				preis = nf_in.parse(pr).doubleValue();

			} catch (NumberFormatException e) {
				posten_labelred_neuerPosten.setText("");
				anzahl_labelred_neuerPosten.setText("");
				kategorie_labelred_neuerPosten.setText("");
				subkategorie_labelred_neuerPosten.setText("");
				übernehmen_label_neuerPosten.setTextFill(Color.web("red"));
				übernehmen_label_neuerPosten
						.setText("Die Preisangabe ist falsch");
				anzahl_labelred_neuerPosten.setText("*");
				return;
			} catch (ParseException e) {
				posten_labelred_neuerPosten.setText("");
				anzahl_labelred_neuerPosten.setText("");
				kategorie_labelred_neuerPosten.setText("");
				subkategorie_labelred_neuerPosten.setText("");
				übernehmen_label_neuerPosten.setTextFill(Color.web("red"));
				übernehmen_label_neuerPosten
						.setText("Bitte nutzen sie Kommata oder Punkte für Dezimalzahlen");
				anzahl_labelred_neuerPosten.setText("*");
				return;
			}
			/*
			 * gibt jedem Wert in der Dauerauftrag combobox eine Integer Zahl zu
			 * (Anzahl der Monate)
			 */
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
			/*
			 * prüft ob das Budget noch ausreicht. Wenn es ausreicht, wird der
			 * Posten übernommen ansonsten kommt eine Fehlermeldung
			 */
			if (model.getBudget() > anzahl * preis) {
				model.insert_Posten(p, kat, s, preis, anzahl, kom, d, sqlheute);
				model.update_User(model.getBudget() - anzahl * preis);
				übernehmen_label_neuerPosten.setTextFill(Color.web("green"));
				übernehmen_label_neuerPosten
						.setText("Ihr Posten wurde erfolgreich übernommen");
				reset();
				init();
			} else {
				übernehmen_label_neuerPosten.setTextFill(Color.web("red"));
				übernehmen_label_neuerPosten
						.setText("Ihr Budget reicht nicht aus");
			}
		} catch (IllegalArgumentException e) {
			übernehmen_label_neuerPosten.setTextFill(Color.web("red"));
			übernehmen_label_neuerPosten
					.setText("Ihre Angaben sind nicht vollständig");
		}
	}

	/* wenn checkbox betätigt wird, wird die combobox sichtbar, bzw. unsichtbar */
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

	/* zurück zum Hauptmenü Button */
	@FXML
	void bA_zurück_neuerPosten(ActionEvent event) throws IOException,
			SQLException {
		/*
		 * ArrayList a = model.transcribe(); for(int i=0 ; i<a.size(); i++)
		 * System.out.println(a.get(0));
		 */
		Stage stage = (Stage) zurück_neuerPosten_button.getScene().getWindow();
		restlicheViews.Menue menü = new restlicheViews.Menue();
		menü.setPrimaryStage(stage);
		menü.startUp_menue();

	}

}
