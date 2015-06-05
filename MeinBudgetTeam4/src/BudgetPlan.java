/**
 * Anwendung BudgetPlan
 * 
 */
import java.sql.*;

public class BudgetPlan {
	public static void main(String[] args) {
		BudgetPlanModel budget = new BudgetPlanModel(); // Modell
		new BudgetPlanGUI(budget); // View und Controller
//		BudgetPlanModel test = new BudgetPlanModel();
//		test.testFunktion();
		BudgetPlanModel magic = new BudgetPlanModel();
		magic.initiateDatabase("test", "test"); //Tabellen werden erfolgreich erstellt!
		magic.insert_Posten("Auto", "", "", 300, 2);																//Funktion die User erstellt.
	
	}

}
