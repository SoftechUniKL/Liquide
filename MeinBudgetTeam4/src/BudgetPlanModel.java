
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
/**
 * Für einen neuen Nutzer wird eine Datenbank mit seinem Profil mit Nutzernamen und zugehörigem Passwort angelegt. Wenn die Datenbank bereits existieren sollte wird eine Exception geworfen.
 * 
 * @param username Benutzername des des Kontos des Anwenders.
 * @param password Passwort zu dem dazugehörigen Nutzerkonto.
 * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
 * @throws NoSuchAlgorithmException Wird geworfen, wenn Kryptographiealgorithmus nicht gefunden wird.
 * @throws InvalidKeySpecException Wird geworfen, wenn die eingegebene Zeichenkette nicht valide ist.
 * @throws ClassNotFoundException Wird geworfen, wenn Datenbankklasse nicht gefunden wird.
 */
	public void registerUser(String username, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException  {
		PasswordHash hasher = new PasswordHash();
		String hashedPassword = hasher.createHash(password);
		Class.forName("org.h2.Driver");
		Connection dbConnection = DriverManager.getConnection("jdbc:h2:~/BudgetPlanerDaten"+"_"+username+";IFEXISTS=FALSE", username, password);
		Statement statement = dbConnection.createStatement();
		String query = "CREATE TABLE IF NOT EXISTS " + "Nutzer" + " (" 
         		+ "User_ID int NOT NULL AUTO_INCREMENT,"
         		+ "Budget double,"// Kann verändert werden
         		+ "Passwort varchar(50),"
         		+ "Name varchar(50) NOT NULL," //Name des Nutzers
         		+ "PRIMARY KEY (User_ID)"
         		+ ")";
		statement.executeQuery(query);
		insert_User(username, hashedPassword, 0.0);
		
	}
	
	/**
	 * Der Benutzer wird validiert.<br>
	 * Kontrolliert ob das eingegebene Passwort richtig ist oder der Nutzer bereits in der Datenbank existiert. Wirft ansonsten eine Exception.
	 * @param username Benutzername des des Kontos des Anwenders.
	 * @param password Passwort zu dem dazugehörigen Nutzerkonto.
	 * @throws NoSuchAlgorithmException  Wird geworfen, wenn Kryptographiealgorithmus nicht gefunden wird.
	 * @throws InvalidKeySpecException Wird geworfen, wenn die eingegebene Zeichenkette nicht valide ist.
	 * @throws SQLException Wird geworfen, wenn irgendeine SQL-Anfrage nicht funktioniert hat.
	 * @throws InvalidParameterSpecException Wird geworfen, wenn das eingegebene Passwort nicht mit dem in der Datenbank hinterlegten übereinstimmt.
	 * @throws ClassNotFoundException Wird geworfen, wenn Datenbankklasse nicht gefunden wird.
	 */
	
	private void validateUser(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterSpecException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PasswordHash hasher = new PasswordHash();
		String hashedPassword = hasher.createHash(password);
		Connection dbConnection = DriverManager.getConnection("jdbc:h2:~/BudgetPlanerDaten"+"_"+username+";IFEXISTS=TRUE", username, password);
		Statement statement = dbConnection.createStatement();
		String query = "SELECT Passwort FROM Nutzer";
		ResultSet ergebnis = statement.executeQuery(query+";");
    	ergebnis.next(); //Sollte nur 1 Nutzer existieren
    	password = ergebnis.getString(1);
    	if(hashedPassword != password)
    		throw new InvalidParameterSpecException("Das eingegebene Passwort war falsch.");
	}
    
    public void initiateDatabase(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidParameterSpecException {
    		validateUser(username, password);
    		Class.forName("org.h2.Driver");
            Connection dbConnection = DriverManager.getConnection("jdbc:h2:~/BudgetPlanerDaten"+"_"+username+";IFEXISTS=TRUE", username, password);
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
            		+ "Dauerauftrag BOOLEAN DEFAULT FALSE,"
            		+ "FOREIGN KEY (Subkategorie) REFERENCES Subkategorie(SubK_ID),"
            		+ "FOREIGN KEY (Kategorie) REFERENCES Kategorie(Kategorie_ID),"
            		+ "FOREIGN KEY (Nutzer) REFERENCES Nutzer(User_ID) ON DELETE CASCADE," // User nicht löschbar!!!. Wenn Drop Table und neue Initialisierung
            		+ "PRIMARY KEY (ID)"
            		+ ")";
            statement.executeUpdate(query + ";");
            statement.close();
            setConnection(dbConnection);
            if (firstUse) {
            insert_User(username, password, 0.0);
            insert_Kategorie("Allgemein");
            }
    }
    
    /**
     * Fügt dem internen Datenmodell diese neue Kategorie hinzu.
     * 
     * @param Bezeichnung Bezeichnung der Kategorie
     * 
     */
    
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
    
    /**
     * Erstell zu einer zugehörigen Hauptkategorie eine neue Subkategorie.
     * @param Bezeichnung_Subkategorie Die Bezeichnung der zu erstellenden Subkategorie.
     * @param Bezeichnung_Kategorie Die Bezeichnung der bereits existierenden Kategorie.
     */

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
    
    /**
     * Ein neuer Benutzer wird angelegt. 
     * @param name Der Name des Benutzerts.
     * @param password Das verschlüsselte Passwort des benutzers.
     * @param budget Das initiale Budget.
     */
    
    private void insert_User(String name, String password, double budget) {
    	try {
        	Statement statement = dbConnection.createStatement();
        	String query = "INSERT INTO Nutzer(Budget, Passwort, Name) VALUES("+budget+", '" + password + "', '" + name +"')";
        	statement.executeUpdate(query+";");
        	statement.close();
        	}
        	
        	catch(Exception e) {
        		System.out.println(e.getMessage());
        	}
    	
    }
    /**
     * Das Budget für den Nutzer wird aktualisiert beziehungsweise gesetzt.
     * @param Budget Das Budget des Nutzers.
     */
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
    /**
     * Ein neuer Posten wird in die Datenbank eingefügt.
     * @param Posten_Bezeichnung Bezeichnung des einzufügenden Postens.
     * @param Kategorie_Bezeichnung Bezeichnung der zugehörigen Kategorie. Muss bereits in Posten-Tabelle existieren.
     * @param Subkategorie_Bezeichnung Bezeichnung der zur Hauptkategorie zugehörigen Subkategorie. Muss bereits in Subkategorie-Tabelle existieren.
     * @param Preis Preis des entsprechenden Postens für eine Einheit.
     * @param Anzahl Anzahl dieses Postens.
     */
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
    		e.printStackTrace();
    	}
    	
    }

    /**
     * Gibt alle Kategorien zurück.
     * @return Gibt String-Array mit allen Kategorien zurück.
     */

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
    /**
     * Gibt Subkategorien zurück.
     * @param Bezeichnung_Kategorie Bezeichnung der Kategorie zu der die Subkategorien zurückgegeben werden sollen.
     * @return String-Array mit allen Subkategorien zu der zugehörigen Hauptkategorie.
     */
    
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
    
    /**
     * Gibt Höhe des Gesamt Budgetszurück.
     * @return Double des Budgets 
     */
    public double getBudget() {
    	try {
    		Statement statement = dbConnection.createStatement();
    		ResultSet ergebnis = statement.executeQuery("Select Budget FROM Nutzer");
    		ergebnis.next();
    		double budget = ergebnis.getDouble(1);
    		statement.close();
    		return(budget);
    		
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		return -1;
    	}
    }
    
    /**
     * Löscht alles.
     */
    public void DROP_ALL() {
    	try {
    		Statement statement = dbConnection.createStatement();
    		statement.executeUpdate("DROP ALL OBJECTS;");
    		statement.close();
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
    }
    /**
     * Liest Datenbank aus und überträgt sie in eine ArrayList. Es werden das Datum, die Bezeichnung, die Id, die Anzahl
     * ob es sich um einen Dauerauftrag handelt, die Bezeichnung der Kategorie, die Id der Kategorie, die Bezeichnung der Subkategorie und die Id der Subkategorie in die Objekte der ArrayList geschrieben.
     * @return  Gibt eine ArrayList bestehend aus Objekten des Typs Posten mit den Informationen zurück.
     */
    public ArrayList<Posten> transcribe() {
    	try {
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
    		boolean dauerauftrag = ergebnis.getBoolean("Posten.Dauerauftrag");
    		String kategorie_bezeichnung = ergebnis.getString("Kategorie.Bezeichnung");
    		int kategorie_id = ergebnis.getInt("Kategorie.Kategorie_ID");
    		String subkategorie_bezeichnung = ergebnis.getString("Subkategorie.Bezeichnung");
    		int subkategorie_id = ergebnis.getInt("Subkategorie.SubK_ID");
    		alle_Posten.add(new Posten(datum, bezeichnung, produkt_id, preis, anzahl, dauerauftrag, kategorie_bezeichnung, kategorie_id, subkategorie_bezeichnung, subkategorie_id));
    	}
    	statement.close();
    	return alle_Posten;
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		return null;
    	}
    	
    }
    
    
    

}

