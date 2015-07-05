
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
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
 * 
 * Die Klasse BudgetPlanModel, die alle Funktionen beinhaltet, um Daten aus der Datenbank auszulesen oder diese einzufügen.
 * @author Shahin Yousfi
 *
 */
public class BudgetPlanModel
{
    private	static Connection dbConnection; //global definiert
	public BudgetPlanModel() { // Konstruktor
		
	}
	public  Connection getConnection() {
		return BudgetPlanModel.dbConnection;
	}
	public void setConnection(Connection dbConnection) {
		BudgetPlanModel.dbConnection = dbConnection;
	}
	public void closeConnection() throws SQLException {
		BudgetPlanModel.dbConnection.close();	
	}
/**
 * Für einen neuen Nutzer wird eine Datenbank mit seinem Profil mit Nutzernamen und zugehörigem Passwort angelegt. Wenn die Datenbank bereits existieren sollte wird eine Exception geworfen.
 * 
 * @param username Benutzername des des Kontos des Anwenders.
 * @param password Passwort zu dem dazugehörigen Nutzerkonto.
 * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
 * @throws ClassNotFoundException Wird geworfen, wenn Datenbankklasse nicht gefunden wird. 
 */
	public void registerUser(String username, String password) throws SQLException, ClassNotFoundException  {
//		PasswordHash hasher = new PasswordHash();
//		String hashedPassword = hasher.createHash(password); //Passwort wird von H2 bei anlegen der Datenbank mit salt gehasht. Eigenes Hashing ist damit obsolet.
		Class.forName("org.h2.Driver");
		Connection dbConnection = DriverManager.getConnection("jdbc:h2:file:./data/dbProfile/BudgetPlanerDaten"+"_"+username, username, password);
		password = null; //Vorsichtshalber überschreiben der Variable
		setConnection(dbConnection);
		closeConnection();
		//Die Verbindung wird nur aufgebaut, um Datenbank anzulegen. Danach wieder geschlossen. Tabelle für User muss hier nicht angelegt werden, da Authentifikation über DriverManager geschieht.

	}
	
	/**
	 * Der Benutzer wird validiert.<br>
	 * Kontrolliert ob das eingegebene Passwort richtig ist oder der Nutzer bereits in der Datenbank existiert. Wirft ansonsten eine Exception.
	 * @param username Benutzername des des Kontos des Anwenders.
	 * @param password Passwort zu dem dazugehörigen Nutzerkonto.
	 * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
	 * @throws ClassNotFoundException Wird geworfen, wenn Datenbankklasse nicht gefunden wird.
	 */
	
	private void validateUser(String username, String password) throws SQLException,  ClassNotFoundException {
		Class.forName("org.h2.Driver");
		Connection dbConnection = DriverManager.getConnection("jdbc:h2:file:./data/dbProfile/BudgetPlanerDaten"+"_"+username+";IFEXISTS=TRUE", username, password);
		password = null;
		setConnection(dbConnection); //Wenn davor keine Exception alles OK. Dann soll diese Verbindung für kompletten Programmaufruf aktiv sein.
	}
	
	/**
	 * Die Datenbank wird initialisiert. Davor wird der Benutzer validiert. Bei Fehlern wird eine entsprechende Exception geworfen.
	 * 
	 * @param username Benutzername des des Kontos des Anwenders.
	 * @param password Passwort zu dem dazugehörigen Nutzerkonto.
	 * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
	 * @throws ClassNotFoundException Wird geworfen, wenn Datenbankklasse nicht gefunden wird.
	 */
    
    public void initiateDatabase(String username, String password) throws ClassNotFoundException, SQLException {
    		validateUser(username, password);
    		password = null;
            DatabaseMetaData databaseMetaData = dbConnection.getMetaData();
            ResultSet result = databaseMetaData.getTables(null, null, null, new String[] {"TABLE"}); //Testen ob Tabelle schon vorhanden
            boolean firstUse = true;
            if(result.next()) { //Bestimung ob erste Benutzung
            	firstUse = false; 
            }
            if (firstUse) {
            Statement statement = dbConnection.createStatement();
            String query;
            statement = dbConnection.createStatement();
            query = "CREATE TABLE IF NOT EXISTS " + "Kategorie" + " ("
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
            query = "CREATE TABLE IF NOT EXISTS " + "Nutzer" + " (" //Wenn man User-Journey beachtet ist dieser Teil redundant.
            		+ "User_ID int NOT NULL AUTO_INCREMENT,"
            		+ "Budget double,"// Kann verändert werden
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
            		+ "Dauerauftrag INT DEFAULT 0," //Gibt den Turnus des Dauerauftrags in Monaten an. Normale Posten werden nicht wiederholt verrechnet.
            		+ "Kommentar varchar(50) DEFAULT ' ',"
            		+ "FOREIGN KEY (Subkategorie) REFERENCES Subkategorie(SubK_ID),"
            		+ "FOREIGN KEY (Kategorie) REFERENCES Kategorie(Kategorie_ID) ON DELETE RESTRICT," //Kategorie darf erst gelöscht werden wenn es keine Artikel dieser Kategorie mehr gibt.
            		+ "FOREIGN KEY (Nutzer) REFERENCES Nutzer(User_ID) ON DELETE CASCADE," // User nicht löschbar!!!. Wenn Drop Table und neue Initialisierung
            		+ "PRIMARY KEY (ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            statement.close();
            insert_User(username,  0.0);
            insert_Kategorie("Allgemein");
            }
    }
    
    /**
     * Fügt dem internen Datenmodell diese neue Kategorie hinzu.
     * 
     * @param Bezeichnung Bezeichnung der Kategorie
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     * 
     */
    
    public void insert_Kategorie(String Bezeichnung) throws SQLException {
    	Statement statement = dbConnection.createStatement();
    	String query = "INSERT INTO Kategorie(Bezeichnung) VALUES('" + Bezeichnung +"')";
    	statement.executeUpdate(query+";");
    	statement.close();
    	insert_Subkategorie("-", Bezeichnung); //Damit automatisch die default Subkategorie erstellt wird
    	
    }
    
    /**
     * Erstell zu einer zugehörigen Hauptkategorie eine neue Subkategorie.
     * @param Bezeichnung_Subkategorie Die Bezeichnung der zu erstellenden Subkategorie.
     * @param Bezeichnung_Kategorie Die Bezeichnung der bereits existierenden Kategorie.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */

    public void insert_Subkategorie(String Bezeichnung_Subkategorie, String Bezeichnung_Kategorie) throws SQLException {
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
    
    /**
     * Ein neuer Benutzer wird angelegt. 
     * @param name Der Name des Benutzerts.
     * @param password Das verschlüsselte Passwort des benutzers.
     * @param budget Das initiale Budget.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    
    private void insert_User(String name, double budget) throws SQLException {
        	Statement statement = dbConnection.createStatement();
        	String query = "INSERT INTO Nutzer(Budget, Name) VALUES("+budget+", '" + name +"')";
        	statement.executeUpdate(query+";");
        	statement.close();
    	
    }
    /**
     * Das Budget für den Nutzer wird aktualisiert beziehungsweise gesetzt.
     * @param Budget Das Budget des Nutzers.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public void update_User(double Budget) throws SQLException {
    	Statement statement = dbConnection.createStatement();
    	String query = "SELECT * FROM Nutzer";
    	ResultSet ergebnis = statement.executeQuery(query + ";");//Es sollte sowieso nur ein einziger Nutzer existieren
    	ergebnis.next();
    	int user_id = ergebnis.getInt("User_ID");
    	query = "UPDATE Nutzer SET Budget = " + Budget + " WHERE User_ID = " + user_id;
    	statement.executeUpdate(query + ";");
    	statement.close();
    }
    /**
     * Ein neuer Posten wird in die Datenbank eingefügt.
     * @param Posten_Bezeichnung Bezeichnung des einzufügenden Postens.
     * @param Kategorie_Bezeichnung Bezeichnung der zugehörigen Kategorie. Muss bereits in Posten-Tabelle existieren.
     * @param Subkategorie_Bezeichnung Bezeichnung der zur Hauptkategorie zugehörigen Subkategorie. Muss bereits in Subkategorie-Tabelle existieren.
     * @param Preis Preis des entsprechenden Postens für eine Einheit.
     * @param Anzahl Anzahl dieses Postens.
     * @param Kommentar Ein Kommentar zum zugehörigen Artikel. Nicht mehr als 100 Zeichen.
     * @param Dauerauftrag Gibt an ob es sich um einen Dauerauftrag handelt. Die genau Zahl gibt den Turnus an.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public void insert_Posten(String Posten_Bezeichnung, String Kategorie_Bezeichnung, String Subkategorie_Bezeichnung, double Preis, int Anzahl, String Kommentar, int Dauerauftrag) throws SQLException { //Die Frage ist welche Daten verwendet werden
    		if (Posten_Bezeichnung.isEmpty() || Double.isNaN(Preis))
    			throw new IllegalArgumentException("Bitte geben Sie einen Posten mit dazugehörigem Preis an.");
    	Statement statement = dbConnection.createStatement();
    	int Kategorie_ID;
    	String query;
    	ResultSet ergebnis;
    	if(!(Kategorie_Bezeichnung.isEmpty())) {
    	query = "SELECT DISTINCT Kategorie_ID FROM Kategorie WHERE '" + Kategorie_Bezeichnung + "' = Bezeichnung";
    	ergebnis = statement.executeQuery(query + ";");
    	ergebnis.next();
    	Kategorie_ID = ergebnis.getInt("Kategorie_ID");
    	}
    	else
    		Kategorie_ID = 1;
    	int SubK_ID;
    	if (!(Subkategorie_Bezeichnung.isEmpty())) {
    	query = "SELECT DISTINCT SubK_ID FROM Subkategorie WHERE '" + Subkategorie_Bezeichnung + "' = Bezeichnung AND " + Kategorie_ID + " = Kategorie"; //Da Subkategoriebezeichnung nicht unique sein muss
    	ergebnis = statement.executeQuery(query + ";");
    	ergebnis.next();
    	SubK_ID = ergebnis.getInt("SubK_ID");
    	}
    	else
    		SubK_ID = 1; 
    	query = "Select DISTINCT * FROM Nutzer";
    	ergebnis = statement.executeQuery(query + ";");
    	ergebnis.next();
    	int User_ID = ergebnis.getInt("User_ID");//Sollte nur ein einziger existieren, könnte daher auch über DEFAULT 1 gelöst werden.
    	statement.close();
    	if (Anzahl <= 0)
    		Anzahl = 1;
        statement = dbConnection.createStatement(); //Hier werden alle Daten zu Posten geschrieben
        query = "INSERT INTO Posten(Bezeichnung, Preis, Kategorie, Subkategorie, Anzahl, Nutzer, Kommentar, Dauerauftrag)" //Andere Werte werden automatisch belegt.
        		+ " VALUES('"+Posten_Bezeichnung+"', " + Preis + ", " + Kategorie_ID + ", " + SubK_ID + ", " + Anzahl + ", " + User_ID +", '" + Kommentar +"', " + Dauerauftrag + ")";
        statement.executeUpdate(query+";");
        statement.close();
    	
    	
    }
    /**
     * Ein neuer Posten wird in die Datenbank eingefügt.
     * @param Posten_Bezeichnung Bezeichnung des einzufügenden Postens.
     * @param kategorieId Die ID der zum Posten zugehörigen Kategorie.
     * @param subKategorieId Die ID der zur Kategorie zugehörigen Subkategorie.
     * @param Preis Preis des entsprechenden Postens für eine Einheit.
     * @param Anzahl Anzahl dieses Postens.
     * @param Kommentar Ein Kommentar zum zugehörigen Artikel. Nicht mehr als 100 Zeichen.
     * @param Dauerauftrag Gibt an ob es sich um einen Dauerauftrag handelt. Die genau Zahl gibt den Turnus an.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    
    public void insert_Posten(String Posten_Bezeichnung, int kategorieId, int subKategorieId, double Preis, int Anzahl, String Kommentar, int Dauerauftrag) throws SQLException { //Die Frage ist welche Daten verwendet werden
		if (Posten_Bezeichnung.isEmpty() || Double.isNaN(Preis))
			throw new IllegalArgumentException("Bitte geben Sie einen Posten mit dazugehörigem Preis an.");
	Statement statement = dbConnection.createStatement();
	String query = "Select DISTINCT * FROM Nutzer";
	ResultSet ergebnis = statement.executeQuery(query + ";");
	ergebnis.next();
	int User_ID = ergebnis.getInt("User_ID");//Sollte nur ein einziger existieren, könnte daher auch über DEFAULT 1 gelöst werden.
	statement.close();
	if (Anzahl <= 0)
		Anzahl = 1;
    statement = dbConnection.createStatement(); //Hier werden alle Daten zu Posten geschrieben
    query = "INSERT INTO Posten(Bezeichnung, Preis, Kategorie, Subkategorie, Anzahl, Nutzer, Kommentar, Dauerauftrag)" //Andere Werte werden automatisch belegt.
    		+ " VALUES('"+Posten_Bezeichnung+"', " + Preis + ", " + kategorieId + ", " + subKategorieId + ", " + Anzahl + ", " + User_ID +", '" + Kommentar +"', " + Dauerauftrag + ")";
    statement.executeUpdate(query+";");
    statement.close();
	
	
}

    /**
     * Gibt alle Kategorien zurück.
     * @return Gibt String-Array mit allen Kategorien zurück.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */

    public String[] return_Kategorien() throws SQLException {
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
    /**
     * Gibt Subkategorien zurück.
     * @param Bezeichnung_Kategorie Bezeichnung der Kategorie zu der die Subkategorien zurückgegeben werden sollen.
     * @return String-Array mit allen Subkategorien zu der zugehörigen Hauptkategorie.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    
    public String[] return_Subkategorien(String Bezeichnung_Kategorie) throws SQLException {
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
    
    /**
     * Gibt Höhe des Gesamt Budgetszurück.
     * @return Double des Budgets 
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public double getBudget() throws SQLException {
    		Statement statement = dbConnection.createStatement();
    		ResultSet ergebnis = statement.executeQuery("Select Budget FROM Nutzer");
    		ergebnis.next();
    		double budget = ergebnis.getDouble(1);
    		statement.close();
    		return(budget);
    		
    	
    }
    
    /**
     * Löscht alles.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public void DROP_ALL() throws SQLException {
    		Statement statement = dbConnection.createStatement();
    		statement.executeUpdate("DROP ALL OBJECTS;");
    		statement.close();
    }
    /**
     * Löscht einen bestimmten Posten aus der Datenbank.
     * @param postenId Die ID des zu löschenden Postens.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public void deletePosten(int postenId) throws SQLException {
    	String query = "DELETE FROM Posten WHERE ID = " + postenId;
    	Statement statement = dbConnection.createStatement();
    	statement.executeUpdate(query + ";");
    	statement.close();	
    }
    /**
     * Löscht eine Kategorie. Kategorie kann nur gelöscht werden, wenn kein Posten diese referenziert. Subkategorien dieser Kategorie werden kaskadierend gelöscht.
     * @param kategorieId Die ID der Kategorie.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public void deleteKategorie(int kategorieId) throws SQLException {
    	String query = "DELETE FROM Kategorie WHERE Kategorie_ID = " + kategorieId;
    	Statement statement = dbConnection.createStatement();
    	statement.executeUpdate(query + ";");
    	statement.close();
    }
    /**
     * Löscht eine Kategorie. Kategorie kann nur gelöscht werden, wenn kein Posten diese referenziert. Subkategorien dieser Kategorie werden kaskadierend gelöscht.
     * @param kategorieBezeichnung Die Bezeichnung der zu löschenden Kategorie.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public void deleteKategorie(String kategorieBezeichnung) throws SQLException {
    	Statement statement = dbConnection.createStatement();
    	String query ="SELECT Kategorie_ID FROM Kategorie WHERE '" + kategorieBezeichnung + "' = Bezeichnung";
    	ResultSet ergebnis;
    	ergebnis = statement.executeQuery(query + ";");
    	ergebnis.next();
    	int kategorieId = ergebnis.getInt("Kategorie_ID");
    	query = "DELETE FROM Kategorie WHERE Kategorie_ID = " + kategorieId;
    	statement.executeUpdate(query + ";");
    	statement.close();
    }
    /**
     * Löscht eine bestimmte Subkategorie.
     * @param subKategorieId Die ID der Subkategorie.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    
    public void deleteSubkategorie(int subKategorieId) throws SQLException {
    	String query = "DELETE FROM Subkategorie WHERE SubK_ID = " + subKategorieId;
    	Statement statement = dbConnection.createStatement();
    	statement.executeUpdate(query + ";");
    	statement.close();
    }
    /**
     * Löscht eine bestimmte Subkategorie.
     * @param subKategorieBezeichnung Die Bezeichnung der Subkategorie.
     * @param kategorieId Id der zugehörigen Hautkategorie.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public void deleteSubkategorie(String subKategorieBezeichnung, int kategorieId) throws SQLException {
    	Statement statement = dbConnection.createStatement();
    	String query ="SELECT DISTINCT SubK_ID FROM Subkategorie WHERE '" + subKategorieBezeichnung + "' = Bezeichnung AND " + kategorieId + " = Kategorie";
    	ResultSet ergebnis;
    	ergebnis = statement.executeQuery(query + ";");
    	ergebnis.next();
    	int subKategorieId = ergebnis.getInt("Kategorie_ID");
    	query = "DELETE FROM Subkategorie WHERE SubK_ID = " + subKategorieId;
    	statement.executeUpdate(query + ";");
    	statement.close();
    }
    /**
     * Löscht alle Posten auf die eine "ist-gleich"-Beziehung zutrifft unter Berücksichtigung der Parameter. Bsp.: Lösche alle Posten bei denen "Bezeichnung = Milch" gilt.
     * @param condition Die zu erfüllende Kategorie. Muss als Spalte in Kategorie-Tabelle existieren.
     * @param attribute Das Attribut, das condition entsprechen soll.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public void deletePostenCondition(String condition, String attribute) throws SQLException {
    	String query = "DELETE FROM Posten WHERE " + condition + " = '" + attribute + "'";
    	Statement statement = dbConnection.createStatement();
    	statement.executeUpdate(query + ";");
    	statement.close();	
    }
    /**
     * Löscht alle Posten auf die eine "ist-gleich"-Beziehung zutrifft unter Berücksichtigung der Parameter. Bsp.: Lösche alle Posten bei denen "Dauerauftrag = 5" gilt.
     * @param condition Die zu erfüllende Kategorie. Muss als Spalte in Kategorie-Tabelle existieren.
     * @param attribute Das Attribut, das condition entsprechen soll.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public void deletePostenCondition(String condition, int attribute) throws SQLException {
    	String query = "DELETE FROM Posten WHERE " + condition + " = " + attribute;
    	Statement statement = dbConnection.createStatement();
    	statement.executeUpdate(query + ";");
    	statement.close();	
    }
    /**
     * Liest Datenbank aus und überträgt sie in eine ArrayList. Es werden das Datum, die Bezeichnung, die Id, die Anzahl
     * ob es sich um einen Dauerauftrag handelt, die Bezeichnung der Kategorie, die Id der Kategorie, die Bezeichnung der Subkategorie und die Id der Subkategorie in die Objekte der ArrayList geschrieben.
     * @return  Gibt eine ArrayList bestehend aus Objekten des Typs Posten mit den Informationen zurück.
     * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
     */
    public ArrayList<Posten> transcribe() throws SQLException {
    	Statement statement = dbConnection.createStatement(); //Join über Posten, Kategorie und Subkategorie
    	String query = "SELECT * FROM Posten, Kategorie, Subkategorie WHERE Kategorie.Kategorie_ID = Posten.Kategorie AND "
    			+ "Kategorie.Kategorie_ID = Subkategorie.Kategorie AND Subkategorie.SubK_ID = Posten.Subkategorie";
    	ResultSet ergebnis = statement.executeQuery(query + ";");
    	ArrayList<Posten> alle_Posten = new ArrayList<Posten>();
    	while(ergebnis.next()) {
    		Date datum = ergebnis.getDate("Posten.Datum");
    		String bezeichnung = ergebnis.getString("Posten.Bezeichnung");
    		int produkt_id = ergebnis.getInt("Posten.ID");
    		double preis = ergebnis.getDouble("Posten.Preis");
    		int anzahl = ergebnis.getInt("Posten.Anzahl");
    		int dauerauftrag = ergebnis.getInt("Posten.Dauerauftrag");
    		String kategorie_bezeichnung = ergebnis.getString("Kategorie.Bezeichnung");
    		int kategorie_id = ergebnis.getInt("Kategorie.Kategorie_ID");
    		String subkategorie_bezeichnung = ergebnis.getString("Subkategorie.Bezeichnung");
    		int subkategorie_id = ergebnis.getInt("Subkategorie.SubK_ID");
    		String kommentar = ergebnis.getString("Kategorie.Kommentar");
    		alle_Posten.add(new Posten(datum, bezeichnung, produkt_id, preis, anzahl, dauerauftrag, kategorie_bezeichnung, kategorie_id, subkategorie_bezeichnung, subkategorie_id, kommentar));
    	}
    	statement.close();
    	return alle_Posten;
    }
    	
    
    
    

}

