/**
 * Anwendung BudgetPlan
 * 
 */
import java.sql.*;

public class BudgetPlan {
	public static void main(String[] args) {
		BudgetPlanModel budget = new BudgetPlanModel(); // Modell
		new BudgetPlanGUI(budget); // View und Controller
		BudgetPlanModel magic = new BudgetPlanModel();
		magic.initiateDatabase("test", "test"); //Tabellen werden erfolgreich erstellt!
		magic.insert_Posten("Auto", "", "", 300, 2);
/*		magic.insert_Kategorie("Lebensmittel"); // Kann gelöscht werden, war nur zum testen.
		magic.insert_Kategorie("Beste Produkte");
		magic.insert_Subkategorie("Milchprodukte", "Lebensmittel");
		magic.insert_Subkategorie("Fleisch", "Lebensmittel");
		magic.insert_Subkategorie("Gemüse", "Lebensmittel");
		String[] Kats = magic.return_Subkategorien("Lebensmittel");
		for(int i = 0; i < Kats.length; i++)
			System.out.println(Kats[i] + "------------> length: " + Kats.length);
	*/
		
		
		
		magic.closeConnection(); //Trennt Verbindung zur DB
	}

}
