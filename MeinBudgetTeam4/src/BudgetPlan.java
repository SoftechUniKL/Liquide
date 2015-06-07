/**
 * Anwendung BudgetPlan
 * 
 */
import java.sql.*;
import java.util.*;

public class BudgetPlan {
	public static void main(String[] args) {
		BudgetPlanModel budget = new BudgetPlanModel(); // Modell
		new BudgetPlanGUI(budget); // View und Controller
		BudgetPlanModel magic = new BudgetPlanModel();
		magic.initiateDatabase("test", "test"); //Tabellen werden erfolgreich erstellt
		magic.insert_Posten("Auto", "", "", 300, 2);
		magic.insert_Posten("Melone", "", "", 2.55, 1);
		magic.insert_Posten("Spülmittel", "", "", 0.9, 5);
/*		ArrayList<Posten> alles = new ArrayList<Posten>();		KANN GELÖSCHT WERDEN, HATTE NUR TESTZWECK
		
		alles = magic.transcribe();
		String listString = "";
		for (Posten s : alles)
		{
		    listString += s.getBezeichnung() +"---" + s.getAnzahl() + "---" +  s.getKategorie_bezeichnung() + "---" +  s.getKategorie_id() +"---" +  s.getPreis() +"---" +  s.getProdukt_ID() +"---" +  s.getSubkategorie_bezeichnung() +"---" +  s.getSubkategorie_id() +"---" +  s.getDatum() +"---" +  "\n";
		}

		System.out.println(listString); */
/*		magic.insert_Kategorie("Lebensmittel"); 
		magic.insert_Kategorie("Beste Produkte");
		magic.insert_Subkategorie("Milchprodukte", "Lebensmittel");
		magic.insert_Subkategorie("Fleisch", "Lebensmittel");
		magic.insert_Subkategorie("Gemüse", "Lebensmittel");
		String[] Kats = magic.return_Subkategorien("Lebensmittel");
		for(int i = 0; i < Kats.length; i++)
			System.out.println(Kats[i] + "------------> length: " + Kats.length);
	*/
		
		
	//	magic.DROP_ALL(); //Löscht alle Objekte der DB
		magic.closeConnection(); //Trennt Verbindung zur DB
	}

}
