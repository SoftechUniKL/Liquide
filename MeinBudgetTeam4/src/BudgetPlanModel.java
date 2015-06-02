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
    
    public Connection initiateDatabase(String Nutzername, String Password) {
    	try {
    		Class.forName("org.h2.Driver");
            Connection dbConnection = DriverManager.getConnection("jdbc:h2:~/BudgetPlanerDaten"+"_"+Nutzername, Nutzername, Password);
            Statement statement = dbConnection.createStatement();
            String query = "";// "DROP TABLE IF EXISTS " + "Tabellenname"; //Tabellenname ersetzen
           /* statement.execute(query + ";");
            statement.close(); */ //Für Testzwecke
            statement = dbConnection.createStatement();
            query = "CREATE TABLE IF NOT EXISTS " + "Subkategorie" + " (" 
            		+ "SubP_ID int NOT NULL AUTO_INCREMENT,"
            		+ "Bezeichnung varchar(50) NOT NULL," //Bezeichnung der Subkategorie
            		+ "PRIMARY KEY (SubP_ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            query = "CREATE TABLE IF NOT EXISTS " + "Kategorie" + " (" //Tabellenname = Posten?
            		+ "Kategorie_ID int NOT NULL AUTO_INCREMENT,"
            		+ "Bezeichnung varchar(50) NOT NULL," //Bezeichnung der der Kategorie
            		+ "Subkategorie varchar(50),"
            		+ "FOREIGN KEY (Subkategorie) REFERENCES Subkategorie(SubP_ID),"
            		+ "PRIMARY KEY (Kategorie_ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            query = "CREATE TABLE IF NOT EXISTS " + "Nutzer" + " (" 
            		+ "User_ID int NOT NULL AUTO_INCREMENT,"
            		+ "Budget double,"
            		+ "Passwort varchar(50), "
            		+ "Name varchar(50) NOT NULL," //Name des Nutzers
            		+ "PRIMARY KEY (User_ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            query = "CREATE TABLE IF NOT EXISTS " + "Posten" + " (" //Tabellenname = Posten?
            		+ "ID int NOT NULL AUTO_INCREMENT,"
            		+ "Datum datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),"
            		+ "Bezeichnung varchar(50) NOT NULL," //Bezeichnung der Position
            		+ "Preis float NOT NULL," 
            		+ "Kategorie varchar(50)," //Sollte man eigentlich mit Enum machen
            		+ "Subkategorie varchar(50)," //s.o.
            		+ "Anzahl int DEFAULT 1,"
            		+ "Nutzer varchar(50),"
            		+ "FOREIGN KEY (Kategorie) REFERENCES Kategorie(Kategorie_ID),"
            		+ "FOREIGN KEY (Nutzer) REFERENCES Nutzer(User_ID),"
            		+ "PRIMARY KEY (ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            statement.close();
            return dbConnection;
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
    }
    public void select(String column_name, String table_name, String condition, Connection dbConnection) { //Datenbankanfrage
    	try{
    	Statement statement = dbConnection.createStatement();
    	String query ="SELECT " + column_name + " FROM " + table_name + " WHERE " + condition;
    	ResultSet resultSet = statement.executeQuery(query + ";");
    	while(resultSet.next()) {
    		String output = resultSet.getString(column_name);
    		System.out.println(output); //Ersetzen durch GUI bzw. ändern in return
    	}
        statement.close();
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage()); //Ersetzen durch GUI-Verarbeitung
    	}
    }
    
    public void insert(String table, Connection dbConnection, String...values) { //Die Frage ist welche Daten verwendet werden
    	try {
    		if (values == null || values.length == 0)
    			throw new IllegalArgumentException("Die einzulesenden Daten wurden nicht korrekt übergeben");
    	Statement statement = dbConnection.createStatement();
        statement = dbConnection.createStatement();
        statement.executeUpdate("INSERT INTO Test VALUES (1, 'Hier könnten Daten aus dem Budgetplaner stehen!');");
        statement.close();
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
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