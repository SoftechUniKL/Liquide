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
/**
 * @author Shahin
 *
 */
public class BudgetPlanModel
{
	private Connection dbConnection; //global definiert
	public BudgetPlanModel() { // Konstruktor
		
	}
	public Connection getConnection() {
		return this.dbConnection;
	}
	public void setConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}
	public void closeConnection() {
		try{
		dbConnection.close();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
    
    public void initiateDatabase(String Nutzername, String Password) {
    	try {
    		Class.forName("org.h2.Driver");
            Connection dbConnection = DriverManager.getConnection("jdbc:h2:~/BudgetPlanerDaten"+"_"+Nutzername, Nutzername, Password);
            DatabaseMetaData databaseMetaData = dbConnection.getMetaData();
            ResultSet result = databaseMetaData.getTables(null, null, null, new String[] {"TABLE"}); //Testen ob Tabelle schon vorhanden
            boolean firstUse = true;
            if(result.next()) { //Bestimung ob erste Benutzung
            	firstUse = false; 
            	//Hier Authentifikationsfunktion die Korrektheit des Passwortsp rüft. Wenn falsch, dann return
            }
            Statement statement = dbConnection.createStatement();
            String query;
            statement = dbConnection.createStatement();
            query = "CREATE TABLE IF NOT EXISTS " + "Kategorie" + " (" //Tabellenname = Posten?
            		+ "Kategorie_ID int NOT NULL AUTO_INCREMENT,"
            		+ "Bezeichnung varchar(50) NOT NULL UNIQUE," //Bezeichnung der der Kategorie
            		+ "PRIMARY KEY (Kategorie_ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            query = "CREATE TABLE IF NOT EXISTS " + "Subkategorie" + " (" 
            		+ "SubK_ID int NOT NULL AUTO_INCREMENT,"
            		+ "Kategorie int NOT NULL,"
            		+ "Bezeichnung varchar(50) NOT NULL," //Bezeichnung der Subkategorie, nicht unique. Nur in Kombination mit Kategorie unique
            		+ "FOREIGN KEY (Kategorie) REFERENCES Kategorie(Kategorie_ID),"
            		+ "UNIQUE KEY SubK_ID (Bezeichnung, Kategorie)," //Da SubK_ID automatisch inkrementiert wird muss man hier mit Bezeichnung arbeiten
            		+ "PRIMARY KEY (SubK_ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            query = "CREATE TABLE IF NOT EXISTS " + "Nutzer" + " (" 
            		+ "User_ID int NOT NULL AUTO_INCREMENT,"
            		+ "Budget double,"// Kann verändert werden
            		+ "Passwort varchar(50),"
            		+ "Name varchar(50) NOT NULL," //Name des Nutzers
            		+ "PRIMARY KEY (User_ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            query = "CREATE TABLE IF NOT EXISTS " + "Posten" + " (" //Tabellenname = Posten?
            		+ "ID int NOT NULL AUTO_INCREMENT,"
            		+ "Datum datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),"
            		+ "Bezeichnung varchar(50) NOT NULL," //Bezeichnung der Position
            		+ "Preis double NOT NULL," 
            		+ "Kategorie int DEFAULT 1," //Weil unten hier die Standartwerte zugeordnet werden
            		+ "Subkategorie int DEFAULT 1,"
            		+ "Anzahl int DEFAULT 1,"
            		+ "Nutzer int,"
            		+ "FOREIGN KEY (Subkategorie) REFERENCES Subkategorie(SubK_ID),"
            		+ "FOREIGN KEY (Kategorie) REFERENCES Kategorie(Kategorie_ID),"
            		+ "FOREIGN KEY (Nutzer) REFERENCES Nutzer(User_ID) ON DELETE CASCADE," // User nicht löschbar!!!. Wenn Drop Table und neue Initialisierung
            		+ "PRIMARY KEY (ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            statement.close();
            setConnection(dbConnection);
            if (firstUse) {
            insert_User(Nutzername, Password, 0.0);
            insert_Kategorie("Allgemein");
            }
            //System.out.println(firstUse);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
    }
 /*   public void select(String column_name, String table_name, String condition) { //Datenbankanfrage
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
    } */
    
    public void insert_Kategorie(String Bezeichnung) {
    	try {
    	Statement statement = dbConnection.createStatement();
    	String query = "INSERT INTO Kategorie(Bezeichnung) VALUES('" + Bezeichnung +"')";
    	statement.executeUpdate(query+";");
    	statement.close();
    	insert_Subkategorie("-", Bezeichnung); //Damit automatisch die default Subkategorie erstellt wird
    	}
    	
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    }

    public void insert_Subkategorie(String Bezeichnung_Subkategorie, String Bezeichnung_Kategorie) {
    	try {
        	Statement statement = dbConnection.createStatement();
        	String query = "SELECT DISTINCT * FROM Kategorie WHERE '" + Bezeichnung_Kategorie + "' = Bezeichnung";
        	ResultSet ergebnis = statement.executeQuery(query + ";");
        	ergebnis.next();
        	int Kategorie_ID = ergebnis.getInt("Kategorie_ID");
        	statement.close();
        	statement = dbConnection.createStatement();
        	query = "INSERT INTO Subkategorie(Bezeichnung, Kategorie) VALUES('" + Bezeichnung_Subkategorie + "', " + Kategorie_ID + ")";
        	statement.executeUpdate(query+";");
        	statement.close();
        	}
        	
        	catch(Exception e) {
        		System.out.println(e.getMessage());
        	}    	
    }
    
    private void insert_User(String Name, String Passwort, double Budget) {
    	try {
        	Statement statement = dbConnection.createStatement();
        	String query = "INSERT INTO Nutzer(Budget, Passwort, Name) VALUES("+Budget+", '" + Passwort + "', '" + Name +"')";
        	statement.executeUpdate(query+";");
        	statement.close();
        	}
        	
        	catch(Exception e) {
        		System.out.println(e.getMessage());
        	}
    	
    }
    
    public void update_User(double Budget) {
    	try {
    	Statement statement = dbConnection.createStatement();
    	String query = "SELECT * FROM Nutzer";
    	ResultSet ergebnis = statement.executeQuery(query + ";");//Es sollte sowieso nur ein einziger Nutzer existieren
    	ergebnis.next();
    	int user_id = ergebnis.getInt("User_ID");
    	query = "UPDATE Nutzer SET Budget = " + Budget + " WHERE User_ID = " + user_id;
    	statement.executeUpdate(query + ";");
    	statement.close();
    	
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public void insert_Posten(String Posten_Bezeichnung, String Kategorie_Bezeichnung, String Subkategorie_Bezeichnung, double Preis, int Anzahl) { //Die Frage ist welche Daten verwendet werden
    	try {
    		if (Posten_Bezeichnung == "" || Double.isNaN(Preis))
    			throw new IllegalArgumentException("Bitte geben Sie einen Posten mit dazugehörigem Preis an.");
    	Statement statement = dbConnection.createStatement();
    	int Kategorie_ID;
    	String query;
    	ResultSet ergebnis;
    	if(Kategorie_Bezeichnung != "") {
    	query = "SELECT DISTINCT Kategorie_ID FROM Kategorie WHERE '" + Kategorie_Bezeichnung + "' = Bezeichnung";
    	ergebnis = statement.executeQuery(query + ";");
    	ergebnis.next();
    	Kategorie_ID = ergebnis.getInt("Kategorie_ID");
    	}
    	else
    		Kategorie_ID = 1;
    	int SubK_ID;
    	if (Subkategorie_Bezeichnung != "") {
    	query = "SELECT DISTINCT SubK_ID FROM Subkategorie WHERE '" + Subkategorie_Bezeichnung + "' = Bezeichnung";
    	ergebnis = statement.executeQuery(query + ";");
    	ergebnis.next();
    	SubK_ID = ergebnis.getInt("SubK_ID");
    	}
    	else
    		SubK_ID = 1; 
    	query = "Select DISTINCT * FROM Nutzer";
    	ergebnis = statement.executeQuery(query + ";");
    	ergebnis.next();
    	int User_ID = ergebnis.getInt("User_ID");//Sollte nur ein einziger existieren
    	statement.close();
    	if (Anzahl <= 0)
    		Anzahl = 1;
        statement = dbConnection.createStatement(); //Hier werden alle Daten zu Posten geschrieben
        query = "INSERT INTO Posten(Bezeichnung, Preis, Kategorie, Subkategorie, Anzahl, Nutzer)" //Andere Werte werden automatisch belegt.
        		+ " VALUES('"+Posten_Bezeichnung+"', " + Preis + ", " + Kategorie_ID + ", " + SubK_ID + ", " + Anzahl + ", " + User_ID + ")";
        statement.executeUpdate(query+";");
        statement.close();
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    }


    public String[] return_Kategorien() {
    	try {
    	Statement statement = dbConnection.createStatement();
    	String query = "SELECT COUNT (*) FROM Kategorie";
    	ResultSet ergebnis = statement.executeQuery(query+";");
    	ergebnis.next();
    	int anzahl = ergebnis.getInt(1);
    /*	if (!(anzahl > 0)) { //Dieser Fall sollte nie vorkommen.
    		String [] Standard = {"Allgemein"};
    		return Standard;
    	} */
    	String[] Kategorien = new String[anzahl];
    	int i = 0;
    	query = "SELECT Bezeichnung FROM Kategorie";
    	ergebnis = statement.executeQuery(query+";");
    	while(ergebnis.next())
    		Kategorien[i++] = ergebnis.getString("Bezeichnung");
    	statement.close();
    	return Kategorien;
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
    }
    
    public String[] return_Subkategorien(String Bezeichnung_Kategorie) {
    	try {
    		Statement statement = dbConnection.createStatement();
    		String query = "SELECT COUNT(*) FROM Kategorie, Subkategorie WHERE Kategorie.Bezeichnung = '" + Bezeichnung_Kategorie + "'"
    				+ " AND Kategorie.Kategorie_ID = Subkategorie.Kategorie";
    		ResultSet ergebnis = statement.executeQuery(query + ";");
    		ergebnis.next();
    		int anzahl = ergebnis.getInt(1);
    	/*	if (!(anzahl > 0)) {
    			String [] Standard = {"-"};
        		return Standard;
    		} */
    		String[] Subkategorien = new String[anzahl];
    		int i = 0;  		
    		query = "SELECT Subkategorie.Bezeichnung FROM Kategorie, Subkategorie WHERE Kategorie.Bezeichnung = '" + Bezeichnung_Kategorie + "'"
    				+ " AND Kategorie.Kategorie_ID = Subkategorie.Kategorie";
    		ergebnis = statement.executeQuery(query + ";");
    		while(ergebnis.next())
    			Subkategorien[i++] = ergebnis.getString("Subkategorie.Bezeichnung");
    		statement.close();
    		return Subkategorien;
    		
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		return null;
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