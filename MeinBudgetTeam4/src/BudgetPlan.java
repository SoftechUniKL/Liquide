
/**
 * Anwendung BudgetPlan
 * 
 */
import java.sql.*;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BudgetPlan extends Application {
	public static void main(String[] args) {
		launch();
	/*	BudgetPlanModel budget = new BudgetPlanModel(); // Modell
		new BudgetPlanGUI(budget); // View und Controller
		Application.launch();
		BudgetPlanModel magic = new BudgetPlanModel();
		magic.initiateDatabase("test", "test"); //Tabellen werden erfolgreich erstellt
		magic.insert_Posten("Auto", "", "", 300, 2);
		magic.insert_Posten("Melone", "", "", 2.55, 1);
		magic.insert_Posten("Spülmittel", "", "", 0.9, 5); 
		ArrayList<Posten> alles = new ArrayList<Posten>();		//KANN GELÖSCHT WERDEN, HATTE NUR TESTZWECK
		
		alles = magic.transcribe();
		String listString = "";
		for (Posten s : alles)
		{
		    listString += s.getBezeichnung() +"---" + s.getAnzahl() + "---" +  s.getKategorie_bezeichnung() + "---" +  s.getKategorie_id() +"---" +  s.getPreis() +"---" +  s.getProdukt_ID() +"---" +  s.getSubkategorie_bezeichnung() +"---" +  s.getSubkategorie_id() +"---" +  s.getDatum() +"---" +  "\n";
		}

		System.out.println(listString);
		magic.insert_Kategorie("Lebensmittel"); 
		magic.insert_Kategorie("Beste Produkte");
		magic.insert_Subkategorie("Milchprodukte", "Lebensmittel");
		magic.insert_Subkategorie("Fleisch", "Lebensmittel");
		magic.insert_Subkategorie("Gemüse", "Lebensmittel");
		String[] Kats = magic.return_Subkategorien("Lebensmittel");
		for(int i = 0; i < Kats.length; i++)
			System.out.println(Kats[i] + "------------> length: " + Kats.length);
	*/
		
		
	//	magic.DROP_ALL(); //Löscht alle Objekte der DB
	//	magic.closeConnection(); //Trennt Verbindung zur DB
		
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Liquide wie Friede");
			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
			Scene myScene = new Scene(myPane);
			primaryStage.setScene(myScene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @see: https://docs.oracle.com/javafx/2/api/javafx/application/Application.html
	 */
	@Override
	public void stop() { 
		//Hier Verbindung schließen
	}

}
