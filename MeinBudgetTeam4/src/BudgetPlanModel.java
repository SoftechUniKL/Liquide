import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.opencsv.CSVReader;

/**
 * Datenmodell des Budgetplaners
 * 
 * Die Daten werden in der Datei data/budget.csv abgespeichert als CSV-Datei.
 * Bzw. in unserem Fall über H2 in einer lokalen Datenbank.
 * 
 */


import java.sql.*;
import java.sql.*;
/**
 * Hello world!
 *
 */
public class BudgetPlanModel
{
	public BudgetPlanModel() { // Konstruktor
		
	}
    public void testFunktion()
    {
        try {
            Class.forName("org.h2.Driver");
            Connection dbConnection = DriverManager.getConnection("jdbc:h2:~/h2testdb");
            Statement statement = dbConnection.createStatement();
            statement.execute("DROP TABLE IF EXISTS Test;");
            statement.close();
            statement = dbConnection.createStatement();
            statement.executeUpdate("CREATE TABLE Test (Test_ID INTEGER, Message VARCHAR(250));");
            statement.close();

            statement = dbConnection.createStatement();
            statement.executeUpdate("INSERT INTO Test VALUES (4711, 'Hello World!');");
            statement.close();
            
            statement = dbConnection.createStatement();
            statement.executeUpdate("INSERT INTO Test VALUES (1, 'Hier könnten Daten aus dem Budgetplaner stehen!');");
            statement.close();

            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Test;");
            while (resultSet.next()) {
                String msg = resultSet.getString("Message");
                System.out.println(msg);
            }
            statement.close();

            dbConnection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load the driver class!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






/*public class BudgetPlanModel {
	List<Posten> ausgaben;

	public BudgetPlanModel() {
		this.ausgaben = new ArrayList<Posten>();
		try {
			// Zeilenweises Einlesen der Daten
			CSVReader reader = new CSVReader(new FileReader("data/budget.csv"));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
				Date datum = df.parse(nextLine[0]);
				String bezeichnung = nextLine[1];
				double betrag = Double.parseDouble(nextLine[2]);
				ausgaben.add(new Posten(datum, bezeichnung, betrag));
			}
			reader.close();

		} catch (FileNotFoundException e) {
			System.err
					.println("Die Datei data/budget.csv wurde nicht gefunden!");
			System.exit(1);
		} catch (IOException e) {
			System.err
					.println("Probleme beim Oeffnen der Datei data/budget.csv!");
			System.exit(1);
		} catch (ParseException e) {
			System.err
					.println("Formatfehler: Die Datei konnte nicht eingelesen werden!");
			System.exit(1);
		}
	}
} */