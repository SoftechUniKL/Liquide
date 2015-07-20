import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AusgabenDetailsPopup {

	public AusgabenDetailsPopup(ArrayList<Posten> data, LocalDate from, LocalDate to) {
		//Fenster
		Stage popup = new Stage();
		//Anker, damit sich die Tabelle beim ändern der Fenstergröße anpasst
		AnchorPane layout = new AnchorPane();
		
		//Tabellenspalten
		
		//Spalte erstellen
		TableColumn<Posten, String> name = new TableColumn<>("Bezeichnung");
		//Wertefeld aus Posten zuweisen, das Feld muss dort genauso heißen wie hier der String
		name.setCellValueFactory(new PropertyValueFactory<>("bezeichnung"));
		
		TableColumn<Posten, Number> anz = new TableColumn<>("Anzahl");
		anz.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
		
		TableColumn<Posten, Number> preis = new TableColumn<>("Preis");
		preis.setCellValueFactory(new PropertyValueFactory<>("preis"));
		
		TableColumn<Posten, String> kat = new TableColumn<>("Kategorie");
		kat.setCellValueFactory(new PropertyValueFactory<>("kategorie_bezeichnung"));
		
		TableColumn<Posten, String> subkat = new TableColumn<>("Subkategorie");
		subkat.setCellValueFactory(new PropertyValueFactory<>("subkategorie_bezeichnung"));
		
		TableColumn<Posten, String> komm = new TableColumn<>("Kommentar");
		komm.setCellValueFactory(new PropertyValueFactory<>("kommentar"));
		
		TableColumn<Posten, Date> datum = new TableColumn<>("Datum");
		datum.setCellValueFactory(new PropertyValueFactory<>("datum"));
		
		//Tabelle
		TableView<Posten> table = new TableView<>();
		
		//Zellen nicht editierbar
		table.setEditable(false);
		
		//Spalten zur Tabelle
		table.getColumns().addAll(name, anz, preis, kat, subkat, komm, datum);
		
		//Daten in die Tabelle
		table.setItems(ConvertDataForChart.getObservableListFromTo(data, from, to));
		
		//Tabelle zum Anker und an den Seiten angeheftet
		layout.getChildren().add(table);
		AnchorPane.setTopAnchor(table, 0.0);
		AnchorPane.setLeftAnchor(table, 0.0);
		AnchorPane.setRightAnchor(table, 0.0);
		AnchorPane.setBottomAnchor(table, 0.0);
		
		//Fenstertitel mit Datum
		popup.setTitle("Ausgaben vom " + from.toString() + " bis " + to.toString());
		
		//Anker mit Tabelle zum Fenster
		popup.setScene(new Scene(layout));
		//Fenstericon
		popup.getIcons().add(new Image("file:data/Coin.png"));
		//Fenster öffnen
		popup.show();
	}
}
