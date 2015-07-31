import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.sql.*;
import org.joda.time.DateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;

public class PostenverwaltungScreenController {
	BudgetPlanModel model = new BudgetPlanModel();
    /**
     *  Konstrukt aus scene builder übernommen*/
	@FXML
    private RadioButton daueraufträge_rb_postenverwaltung;
	
	 @FXML
	    private Button stornieren_button_postenverwaltung;
	
	@FXML
	private Label Info_postenverwaltung;

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

	/* reset() leert das Label nach 10 sekunden */
	public void reset() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10),
				ev -> {
					Info_postenverwaltung.setText(null);
				}));
		timeline.play();
	}
    /** löschen Button soll nicht betätigt werden können.
     * nur wenn eine Zeile in der Tabelle ausgewählt wird, kann man den Button löschen betätigen
     * stornieren darf nur betätigt werden, wenn ein Dauerauftrag ausgewählt wurde
     */
	public void initialize() {
		löschen_button_postenverwaltung.setDisable(true);
		stornieren_button_postenverwaltung.setDisable(true);
		tabelle1.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldSelection, newSelection) -> {
					if (newSelection != null) {
						löschen_button_postenverwaltung.setDisable(false);
					}
					if(newSelection != null & newSelection.getDauerauftrag() != 0)
						stornieren_button_postenverwaltung.setDisable(false) ;
				});

	}
    /** Radio Button für Ausgaben
     * beim Betätigen wird die Tabelle für die (negativen Posten) geladen
     * wenn kein Zeitraum oder ein falscher Zeitraum gewählt wird, kommt eine Fehlermeldung
     */
	@FXML
	void rbA_ausgaben(ActionEvent event) throws SQLException {
		löschen_button_postenverwaltung.setDisable(true);
		 stornieren_button_postenverwaltung.setDisable(true);
		try {
			if (anfangInput_postenverwaltung.getValue() == null  // prüft ob in beiden Datepickern ein Wert ist
					|| endeInput_postenvewaltung.getValue() == null)
				throw new IllegalArgumentException();
			else {
				try {
					if (anfangInput_postenverwaltung.getValue().compareTo( // prüft ob das Datum am Ende nicht vor
							endeInput_postenvewaltung.getValue()) > 0)     // dem Anfang liegt
						throw new IllegalArgumentException();
					else {

						ArrayList<Posten> data0 = model.transcribe();
						ArrayList<Posten> data1 = new ArrayList<Posten>();
						for (int i = 0; i < data0.size(); i++) {  // die Posten, die in der Kategorie "Einkommen"
							if (data0.get(i).getKategorie_bezeichnung() // angelegt wurden, werden übersprungen
									.equals("Einkommen") || data0.get(i).getDauerauftrag() != 0)
								continue;
							else
								data1.add(data0.get(i));
						}
						bezeichnungTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"bezeichnung"));
						anzahlTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"anzahl"));
						kategorieTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"kategorie_bezeichnung"));
						subkategorieTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"subkategorie_bezeichnung"));
						anzahlTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"anzahl"));
						preisTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"preis"));
						kommentarTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"kommentar"));
						datumTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"datum"));
						tabelle1.setItems(ConvertDataForChart  // Tabelle wird mit den Werten gefüllt
								.getObservableListFromTo(
										data1,
										anfangInput_postenverwaltung.getValue(),
										endeInput_postenvewaltung.getValue()));

					}
					/* Fehler, dass Ende vor dem Anfang liegt, wird aufgefangen */
				} catch (IllegalArgumentException e) {
					ausgaben_rb_postenverwaltung.setSelected(false);
					Info_postenverwaltung.setTextFill(Color.web("red"));
					Info_postenverwaltung
							.setText("Das Ende des Zeitraums darf nicht vor dem Ende liegen");
					reset();
				}
			}
          /* Fehler, dass ein oder beide Datepicker nicht ausgewählt wurden, wird aufgefangen*/
		} catch (IllegalArgumentException e) {
			ausgaben_rb_postenverwaltung.setSelected(false);
			Info_postenverwaltung.setTextFill(Color.web("red"));
			Info_postenverwaltung
					.setText("Sie müssen einen Anfang und Ende des Zeitraums bestimmen");
			reset();

		}

	}
	/* Radio Button für Einkommen
     * beim Betätigen wird die Tabelle für die (positiven) Posten geladen
     * wenn kein Zeitraum oder ein falscher Zeitraum gewählt wird, kommt eine Fehlermeldung
     * bis auf einen Punkt identisch zu Radio Button Ausgaben
     */
	@FXML
	void rbA_einkommen(ActionEvent event) throws SQLException {
		löschen_button_postenverwaltung.setDisable(true);
		 stornieren_button_postenverwaltung.setDisable(true);
		try {
			if (anfangInput_postenverwaltung.getValue() == null
					|| endeInput_postenvewaltung.getValue() == null)
				throw new IllegalArgumentException();
			else {
				try {
					if (anfangInput_postenverwaltung.getValue().compareTo(
							endeInput_postenvewaltung.getValue()) > 0)
						throw new IllegalArgumentException();
					else {

						ArrayList<Posten> data0 = model.transcribe();
						ArrayList<Posten> data1 = new ArrayList<Posten>();
						for (int i = 0; i < data0.size(); i++) { // Es werden nur diejenigen Posten berücksichtigt
							if (data0.get(i).getKategorie_bezeichnung()// die in der Kategorie "Einkommen" angelegt
									.equals("Einkommen") & data0.get(i).getDauerauftrag() == 0)              // wurden
								data1.add(data0.get(i));
							else
								continue;
						}
						bezeichnungTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"bezeichnung"));
						anzahlTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"anzahl"));
						kategorieTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"kategorie_bezeichnung"));
						subkategorieTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"subkategorie_bezeichnung"));
						anzahlTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"anzahl"));
						preisTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"preis"));
						kommentarTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"kommentar"));
						datumTC_postenverwaltung
								.setCellValueFactory(new PropertyValueFactory<>(
										"datum"));
						tabelle1.setItems(ConvertDataForChart
								.getObservableListFromTo(
										data1,
										anfangInput_postenverwaltung.getValue(),
										endeInput_postenvewaltung.getValue()));

					}
				} catch (IllegalArgumentException e) {
					einkommenr_rb_postenverwaltung.setSelected(false);
					Info_postenverwaltung.setTextFill(Color.web("red"));
					Info_postenverwaltung
							.setText("Das Ende des Zeitraums darf nicht vor dem Anfang liegen");
					reset();
				}
			}

		} catch (IllegalArgumentException e) {
			einkommenr_rb_postenverwaltung.setSelected(false);
			Info_postenverwaltung.setTextFill(Color.web("red"));
			Info_postenverwaltung
					.setText("Sie müssen einen Anfang und Ende des Zeitraums bestimmen");
			reset();

		}

	}
    /*löscht den ausgewählten Posten aus der Tabelle
     * neue Tabelle wird geladen
     * Geld wird wieder zugefügt oder abgezogen
     */
	@FXML
	void bA_löschen(ActionEvent event) throws SQLException {
		Posten p = tabelle1.getSelectionModel().getSelectedItem();
		if (ausgaben_rb_postenverwaltung.isSelected())// wenn es sich um Ausgaben handelt, wird das Geld zum
			model.update_User(model.getBudget()       // Budget wieder dazuaddiert
					+ (p.getAnzahl() * p.getPreis()));// ansonsten wird es abgezogen
		else {
			if(einkommenr_rb_postenverwaltung.isSelected())
			model.update_User(model.getBudget()
					- (p.getAnzahl() * p.getPreis()));
			else {
				DateTime heute = new DateTime();
				 DateTime obj = new DateTime (p.getDatum()) ;
				for (int j = 1 ; heute.compareTo(obj.plusMonths(p.getDauerauftrag()*j)) > 0; j++ ){
					   if(p.getKategorie_bezeichnung().equals("Einkommen"))
					     model.update_User(model.getBudget() + p.getPreis());  
					   else
						     model.update_User(model.getBudget() - p.getPreis()); 
				}
				
			}
		}
		model.deletePosten(p.getProdukt_ID());
		Info_postenverwaltung.setTextFill(Color.web("green"));
		Info_postenverwaltung.setText("Der Posten wurde erfolgreich gelöscht");
		reset();
		/* 1 zu 1 von rbA_ausgaben übernommen*/
		if (ausgaben_rb_postenverwaltung.isSelected()) {
			try {
				if (anfangInput_postenverwaltung.getValue() == null
						|| endeInput_postenvewaltung.getValue() == null)
					throw new IllegalArgumentException();
				else {
					try {
						if (anfangInput_postenverwaltung.getValue().compareTo(
								endeInput_postenvewaltung.getValue()) > 0)
							throw new IllegalArgumentException();
						else {

							ArrayList<Posten> data0 = model.transcribe();
							ArrayList<Posten> data1 = new ArrayList<Posten>();
							for (int i = 0; i < data0.size(); i++) {
								if (data0.get(i).getKategorie_bezeichnung()
										.equals("Einkommen") || data0.get(i).getDauerauftrag() != 0 )
									continue;
								else
									data1.add(data0.get(i));
							}
							bezeichnungTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"bezeichnung"));
							anzahlTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"anzahl"));
							kategorieTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"kategorie_bezeichnung"));
							subkategorieTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"subkategorie_bezeichnung"));
							anzahlTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"anzahl"));
							preisTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"preis"));
							kommentarTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"kommentar"));
							datumTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"datum"));
							tabelle1.setItems(ConvertDataForChart
									.getObservableListFromTo(data1,
											anfangInput_postenverwaltung
													.getValue(),
											endeInput_postenvewaltung
													.getValue()));

						}
					} catch (IllegalArgumentException e) {
						einkommenr_rb_postenverwaltung.setSelected(false);
						Info_postenverwaltung.setTextFill(Color.web("red"));
						Info_postenverwaltung
								.setText("Das Ende des Zeitraums darf nicht vor dem Anfang liegen");
						reset();
					}
				}

			} catch (IllegalArgumentException e) {
				einkommenr_rb_postenverwaltung.setSelected(false);
				Info_postenverwaltung.setTextFill(Color.web("red"));
				Info_postenverwaltung
						.setText("Sie müssen einen Anfang und Ende des Zeitraums bestimmen");
				reset();

			}

		} else
			if (einkommenr_rb_postenverwaltung.isSelected())
		{
			/* 1 zu 1 von rbA_einkommen übernommen*/
			try {
				if (anfangInput_postenverwaltung.getValue() == null
						|| endeInput_postenvewaltung.getValue() == null)
					throw new IllegalArgumentException();
				else {
					try {
						if (anfangInput_postenverwaltung.getValue().compareTo(
								endeInput_postenvewaltung.getValue()) > 0)
							throw new IllegalArgumentException();
						else {

							ArrayList<Posten> data0 = model.transcribe();
							ArrayList<Posten> data1 = new ArrayList<Posten>();
							for (int i = 0; i < data0.size(); i++) {
								if (data0.get(i).getKategorie_bezeichnung()
										.equals("Einkommen") & data0.get(i).getDauerauftrag() == 0)
									data1.add(data0.get(i));
								else
									continue;
							}
							bezeichnungTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"bezeichnung"));
							anzahlTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"anzahl"));
							kategorieTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"kategorie_bezeichnung"));
							subkategorieTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"subkategorie_bezeichnung"));
							anzahlTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"anzahl"));
							preisTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"preis"));
							kommentarTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"kommentar"));
							datumTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"datum"));
							tabelle1.setItems(ConvertDataForChart
									.getObservableListFromTo(data1,
											anfangInput_postenverwaltung
													.getValue(),
											endeInput_postenvewaltung
													.getValue()));

						}
					} catch (IllegalArgumentException e) {
						einkommenr_rb_postenverwaltung.setSelected(false);
						Info_postenverwaltung.setTextFill(Color.web("red"));
						Info_postenverwaltung
								.setText("Das Ende des Zeitraums darf nicht vor dem Anfang liegen");
						reset();
					}
				}

			} catch (IllegalArgumentException e) {
				einkommenr_rb_postenverwaltung.setSelected(false);
				Info_postenverwaltung.setTextFill(Color.web("red"));
				Info_postenverwaltung
						.setText("Sie müssen einen Anfang und Ende des Zeitraums bestimmen");
				reset();

			}

		}
			else {
				löschen_button_postenverwaltung.setDisable(true);
				 stornieren_button_postenverwaltung.setDisable(true);
				 try {
						if (anfangInput_postenverwaltung.getValue() == null
								|| endeInput_postenvewaltung.getValue() == null)
							throw new IllegalArgumentException();
						else {
							try {
								if (anfangInput_postenverwaltung.getValue().compareTo(
										endeInput_postenvewaltung.getValue()) > 0)
									throw new IllegalArgumentException();
								else {

									ArrayList<Posten> data0 = model.transcribe();
									ArrayList<Posten> data1 = new ArrayList<Posten>();
									for (int i = 0; i < data0.size(); i++) { // Es werden nur diejenigen Posten berücksichtigt
										if (data0.get(i).getDauerauftrag() <= 0)              // wurden
											continue; 
										else
											data1.add(data0.get(i));
									}
									bezeichnungTC_postenverwaltung
											.setCellValueFactory(new PropertyValueFactory<>(
													"bezeichnung"));
									anzahlTC_postenverwaltung
											.setCellValueFactory(new PropertyValueFactory<>(
													"anzahl"));
									kategorieTC_postenverwaltung
											.setCellValueFactory(new PropertyValueFactory<>(
													"kategorie_bezeichnung"));
									subkategorieTC_postenverwaltung
											.setCellValueFactory(new PropertyValueFactory<>(
													"subkategorie_bezeichnung"));
									anzahlTC_postenverwaltung
											.setCellValueFactory(new PropertyValueFactory<>(
													"anzahl"));
									preisTC_postenverwaltung
											.setCellValueFactory(new PropertyValueFactory<>(
													"preis"));
									kommentarTC_postenverwaltung
											.setCellValueFactory(new PropertyValueFactory<>(
													"kommentar"));
									datumTC_postenverwaltung
											.setCellValueFactory(new PropertyValueFactory<>(
													"datum"));
									tabelle1.setItems(ConvertDataForChart
											.getObservableListFromTo(
													data1,
													anfangInput_postenverwaltung.getValue(),
													endeInput_postenvewaltung.getValue()));

								}
							} catch (IllegalArgumentException e) {
								einkommenr_rb_postenverwaltung.setSelected(false);
								Info_postenverwaltung.setTextFill(Color.web("red"));
								Info_postenverwaltung
										.setText("Das Ende des Zeitraums darf nicht vor dem Anfang liegen");
								reset();
							}
						}

					} catch (IllegalArgumentException e) {
						einkommenr_rb_postenverwaltung.setSelected(false);
						Info_postenverwaltung.setTextFill(Color.web("red"));
						Info_postenverwaltung
								.setText("Sie müssen einen Anfang und Ende des Zeitraums bestimmen");
						reset();

					}

			}

	}
    /* zurück zum Hauptmenü*/
	@FXML
	void bA_zurück(ActionEvent event) throws SQLException, IOException {

		Stage stage = (Stage) zurück_button_postenverwaltung.getScene()
				.getWindow();
		restlicheViews.Menue menü = new restlicheViews.Menue();
		menü.setPrimaryStage(stage);
		menü.startUp_menue();

	}
	/**
	 * durch diesen Radio Button werden die Daueraufträge in die Tabelle geladen
	 * 
	  */
	 @FXML
	    void rbA_daueraufträge(ActionEvent event) throws SQLException {
		 löschen_button_postenverwaltung.setDisable(true);
		 stornieren_button_postenverwaltung.setDisable(true);
		 try {
				if (anfangInput_postenverwaltung.getValue() == null
						|| endeInput_postenvewaltung.getValue() == null)
					throw new IllegalArgumentException();
				else {
					try {
						if (anfangInput_postenverwaltung.getValue().compareTo(
								endeInput_postenvewaltung.getValue()) > 0)
							throw new IllegalArgumentException();
						else {

							ArrayList<Posten> data0 = model.transcribe();
							ArrayList<Posten> data1 = new ArrayList<Posten>();
							for (int i = 0; i < data0.size(); i++) { // Es werden nur diejenigen Posten berücksichtigt
								if (data0.get(i).getDauerauftrag() <= 0)              // wurden
									continue; 
								else
									data1.add(data0.get(i));
							}
							bezeichnungTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"bezeichnung"));
							anzahlTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"anzahl"));
							kategorieTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"kategorie_bezeichnung"));
							subkategorieTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"subkategorie_bezeichnung"));
							anzahlTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"anzahl"));
							preisTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"preis"));
							kommentarTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"kommentar"));
							datumTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"datum"));
							tabelle1.setItems(ConvertDataForChart
									.getObservableListFromTo(
											data1,
											anfangInput_postenverwaltung.getValue(),
											endeInput_postenvewaltung.getValue()));

						}
					} catch (IllegalArgumentException e) {
						daueraufträge_rb_postenverwaltung.setSelected(false);
						Info_postenverwaltung.setTextFill(Color.web("red"));
						Info_postenverwaltung
								.setText("Das Ende des Zeitraums darf nicht vor dem Anfang liegen");
						reset();
					}
				}

			} catch (IllegalArgumentException e) {
				daueraufträge_rb_postenverwaltung.setSelected(false);
				Info_postenverwaltung.setTextFill(Color.web("red"));
				Info_postenverwaltung
						.setText("Sie müssen einen Anfang und Ende des Zeitraums bestimmen");
				reset();

			}

	    }/** kann nur bei Daueraufträgen angewendet werden
	         Daueraufträge werden für die Zukunft beendet
	         
	    */
	 
	 @FXML
	    void bA_stornieren(ActionEvent event) throws SQLException {
	     DateTime heute = new DateTime();
		 Posten p = tabelle1.getSelectionModel().getSelectedItem();
		 DateTime obj = new DateTime (p.getDatum()) ;
		   
		   Date heute1 = new Date ();
		   java.sql.Timestamp sqlheute = new java.sql.Timestamp(
					heute1.getTime());
		   java.sql.Timestamp sqlp = new java.sql.Timestamp(p.getDatum().getTime());
        model.insert_Posten(p.getBezeichnung(), p.getKategorie_bezeichnung(), p.getSubkategorie_bezeichnung(), p.getPreis(), p.getAnzahl(), p.getKommentar(), -p.getDauerauftrag(), sqlp);
        model.insert_Posten(p.getBezeichnung(), p.getKategorie_bezeichnung(), p.getSubkategorie_bezeichnung(), p.getPreis(), p.getAnzahl(), p.getKommentar(), -p.getDauerauftrag(), sqlheute);
	    model.deletePosten(p.getProdukt_ID());
	    
	    löschen_button_postenverwaltung.setDisable(true);
		 stornieren_button_postenverwaltung.setDisable(true);
		 try {
				if (anfangInput_postenverwaltung.getValue() == null
						|| endeInput_postenvewaltung.getValue() == null)
					throw new IllegalArgumentException();
				else {
					try {
						if (anfangInput_postenverwaltung.getValue().compareTo(
								endeInput_postenvewaltung.getValue()) > 0)
							throw new IllegalArgumentException();
						else {

							ArrayList<Posten> data0 = model.transcribe();
							ArrayList<Posten> data1 = new ArrayList<Posten>();
							for (int i = 0; i < data0.size(); i++) { // Es werden nur diejenigen Posten berücksichtigt
								if (data0.get(i).getDauerauftrag() <= 0)              // wurden
									continue; 
								else
									data1.add(data0.get(i));
							}
							bezeichnungTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"bezeichnung"));
							anzahlTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"anzahl"));
							kategorieTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"kategorie_bezeichnung"));
							subkategorieTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"subkategorie_bezeichnung"));
							anzahlTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"anzahl"));
							preisTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"preis"));
							kommentarTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"kommentar"));
							datumTC_postenverwaltung
									.setCellValueFactory(new PropertyValueFactory<>(
											"datum"));
							tabelle1.setItems(ConvertDataForChart
									.getObservableListFromTo(
											data1,
											anfangInput_postenverwaltung.getValue(),
											endeInput_postenvewaltung.getValue()));

						}
					} catch (IllegalArgumentException e) {
						einkommenr_rb_postenverwaltung.setSelected(false);
						Info_postenverwaltung.setTextFill(Color.web("red"));
						Info_postenverwaltung
								.setText("Das Ende des Zeitraums darf nicht vor dem Anfang liegen");
						reset();
					}
				}

			} catch (IllegalArgumentException e) {
				einkommenr_rb_postenverwaltung.setSelected(false);
				Info_postenverwaltung.setTextFill(Color.web("red"));
				Info_postenverwaltung
						.setText("Sie müssen einen Anfang und Ende des Zeitraums bestimmen");
				reset();

			}
	 }

}