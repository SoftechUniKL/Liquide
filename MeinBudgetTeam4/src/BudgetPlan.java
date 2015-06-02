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
		Connection dbConnection = magic.initiateDatabase("test", "test"); //Tabellen werden erfolgreich erstellt!
																		//Funktion die User erstellt.
	}

}
