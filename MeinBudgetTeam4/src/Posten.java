import java.util.Date;

/**
 * Posten in der Budgetplanung
 */
public class Posten {
	/**
	 * Datum, wann der Posten verbucht wurde
	 */
	private Date datum;
	/**
	 * Kurze Beschreibung
	 */
	private String bezeichnung;
	
	private int produkt_ID;
	/**
	 * Hoehe des Postens
	 */
	private double preis;
	//Anzahl des gekauften Postens
	private int anzahl;
	//Dauerauftrag ja oder nein
	private boolean dauerauftrag;
	
	private String kategorie_bezeichnung;
	
	private int kategorie_id;
	
	private String subkategorie_bezeichnung;
	
	private int subkategorie_id;
	

	/**
	 * Konstruktor
	 * 
	 * @param datum
	 *            Datum, wann der Posten verbucht wurde
	 * @param bezeichnung
	 *            Kurze Beschreibung
	 * @param betrag
	 *            Hoehe des Postens
	 */
/*	public Posten(Date datum, String bezeichnung, double preis) {
		this.bezeichnung = bezeichnung;
		this.datum = datum;
		this.preis = preis;
	} */
	 
	

	public Posten(Date datum, String bezeichnung, int produkt_ID, double preis,
			int anzahl, boolean dauerauftrag, String kategorie_bezeichnung,
			int kategorie_id, String subkategorie_bezeichnung,
			int subkategorie_id) {
		super();
		this.datum = datum;
		this.bezeichnung = bezeichnung;
		this.produkt_ID = produkt_ID;
		this.preis = preis;
		this.anzahl = anzahl;
		this.dauerauftrag = dauerauftrag;
		this.kategorie_bezeichnung = kategorie_bezeichnung;
		this.kategorie_id = kategorie_id;
		this.subkategorie_bezeichnung = subkategorie_bezeichnung;
		this.subkategorie_id = subkategorie_id;
	}

	public Date getDatum() {
		return datum;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public double getPreis() {
		return preis;
	}

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	public boolean isDauerauftrag() {
		return dauerauftrag;
	}

	public void setDauerauftrag(boolean dauerauftrag) {
		this.dauerauftrag = dauerauftrag;
	}

	public String getKategorie_bezeichnung() {
		return kategorie_bezeichnung;
	}

	public void setKategorie_bezeichnung(String kategorie_bezeichnung) {
		this.kategorie_bezeichnung = kategorie_bezeichnung;
	}

	public int getKategorie_id() {
		return kategorie_id;
	}

	public void setKategorie_id(int kategorie_id) {
		this.kategorie_id = kategorie_id;
	}

	public String getSubkategorie_bezeichnung() {
		return subkategorie_bezeichnung;
	}

	public void setSubkategorie_bezeichnung(String subkategorie_bezeichnung) {
		this.subkategorie_bezeichnung = subkategorie_bezeichnung;
	}

	public int getSubkategorie_id() {
		return subkategorie_id;
	}

	public void setSubkategorie_id(int subkategorie_id) {
		this.subkategorie_id = subkategorie_id;
	}

	public int getProdukt_ID() {
		return produkt_ID;
	}

	public void setProdukt_ID(int produkt_ID) {
		this.produkt_ID = produkt_ID;
	}
}
