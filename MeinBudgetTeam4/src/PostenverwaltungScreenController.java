import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;

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
    /* Konstrukt aus scene builder übernommen*/
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
    /* löschen Button soll nicht betätigt werden können.
     * nur wenn eine Zeile in der Tabelle ausgewählt wird, kann man den Button löschen betätigen
     */
	public void initialize() {
		löschen_button_postenverwaltung.setDisable(true);
		tabelle1.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldSelection, newSelection) -> {
					if (newSelection != null) {
						löschen_button_postenverwaltung.setDisable(false);
					}
				});

	}
    /* Radio Button für Ausgaben
     * beim Betätigen wird die Tabelle für die (negativen Posten) geladen
     * wenn kein Zeitraum oder ein falscher Zeitraum gewählt wird, kommt eine Fehlermeldung
     */
	@FXML
	void rbA_ausgaben(ActionEvent event) throws SQLException {
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
									.equals("Einkommen"))
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
									.equals("Einkommen"))              // wurden
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
		model.deletePosten(p.getProdukt_ID());
		if (ausgaben_rb_postenverwaltung.isSelected())// wenn es sich um Ausgaben handelt, wird das Geld zum
			model.update_User(model.getBudget()       // Budget wieder dazuaddiert
					+ (p.getAnzahl() * p.getPreis()));// ansonsten wird es abgezogen
		else
			model.update_User(model.getBudget()
					- (p.getAnzahl() * p.getPreis()));

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
										.equals("Einkommen"))
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

		} else {
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
										.equals("Einkommen"))
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

}