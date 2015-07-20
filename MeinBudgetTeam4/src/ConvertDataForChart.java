import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConvertDataForChart {

	/**
	 * Convert whole data from DB to a Map in order to visualize in chart
	 * @param dataSource
	 * @return Map<String, double>
	 */
	public static Map<String, Double> convertData(ArrayList<Posten> dataSource) {
		Map<String, Double> result = new HashMap<String, Double>();
		
		//elemente der ergebnismap hinzuf�gen
		for (Posten p : dataSource) {
			//einzahlungen �berspringen
			if(p.getKategorie_bezeichnung().toLowerCase().equals("einzahlung")) {
				continue;
			}
			//wenn bereits ein key mit dem datum existiert den n�chsten DATUM(0), DATUM(1), ..., DATUM(N) nennen
			if(result.containsKey(p.getDatum().toString())) {
				int i = 0;
				while(true) {
					//n�chste freie zahl finden
					if(!result.containsKey(p.getDatum().toString()+"("+i+")")) {
						result.put(p.getDatum().toString()+"("+i+")", p.getPreis()*p.getAnzahl());
						break;
					}
					i++;
				}
			}
			//element der ergebnismap hinzuf�gen
			result.put(p.getDatum().toString(), p.getPreis()*p.getAnzahl());
		}
		return result;
	}
	
	/**
	 * Select last (max) 5 elements of the data and convert itto a Map
	 * @param dataSource
	 * @return Map<String, Double>
	 */
	public static Map<String, Double> getLatest(ArrayList<Posten> dataSource) {
		//Leere Hashmap als ergebnis
		Map<String, Double> result = new HashMap<String, Double>();
		
		//Wenn es weniger als 5 elemente sind alle nehmen, keine negativen from und to Werte
		int from = dataSource.size()-5 < 0 ? 0 : dataSource.size()-5;
		int to = dataSource.size() < 0? 0 : dataSource.size();
		
		//die letzten (max) 5 elemente der ergebnis map hinzuf�gen
		for(Posten p: dataSource.subList(from, to)) {
			//einzahlungen �berspringen
			if(p.getKategorie_bezeichnung().toLowerCase().equals("einzahlung")) {
				continue;
			}
			//wenn bereits ein key mit dem datum existiert den n�chsten DATUM(0), DATUM(1), ..., DATUM(N) nennen
			if(result.containsKey(p.getDatum().toString())) {
				int i = 0;
				while(true) {
					//n�chste freie zahl finden
					if(!result.containsKey(p.getDatum().toString()+"("+i+")")) {
						result.put(p.getDatum().toString()+"("+i+")", p.getPreis()*p.getAnzahl());
						break;
					}
					i++;
				}
			}
			//element der ergebnismap hinzuf�gen
			result.put(p.getDatum().toString(), p.getPreis()*p.getAnzahl());
		}
		return result;
	}
	
	/**
	 * Select all elements in the given timerange and convert them into a Map
	 * @param dataSource
	 * @param from
	 * @param to
	 * @return Map<String, Double>
	 */
	public static Map<String, Double> getFromTo(ArrayList<Posten> dataSource, LocalDate from, LocalDate to) {
		Map<String, Double> result = new HashMap<String, Double>();
		ArrayList<Posten> tmp = new ArrayList<Posten>();
		
		//LocalDate in Date konvertieren
		Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		// Alle Posten deren from datum nach dem ausgew�hlten Beginn liegt und deren to datum vor dem ausgew�hlten ende liegt in eine tempor�re liste
		for(Posten p : dataSource) {
			if(p.getDatum().compareTo(fromDate)>= 0 && p.getDatum().compareTo(toDate) <= 0) {
				tmp.add(p);
			}
		}
		
		// alle elemente aus der tempor�ren Liste der ergebnismap hinzuf�gen
		for(Posten p: tmp) {
			//einzahlungen �berspringen
			if(p.getKategorie_bezeichnung().toLowerCase().equals("einzahlung")) {
				continue;
			}
			//wenn bereits ein key mit dem datum existiert den n�chsten DATUM(0), DATUM(1), ..., DATUM(N) nennen
			if(result.containsKey(p.getDatum().toString())) {
				int i = 0;
				while(true) {
					//n�chste freie zahl finden
					if(!result.containsKey(p.getDatum().toString()+"("+i+")")) {
						result.put(p.getDatum().toString()+"("+i+")", p.getPreis()*p.getAnzahl());
						break;
					}
					i++;
				}
			}
			//element der ergebnismap hinzuf�gen
			result.put(p.getDatum().toString(), p.getPreis()*p.getAnzahl());
		}
		return result;
	}
	
	public static ObservableList<Posten> getObservableListFromTo(ArrayList<Posten> dataSource, LocalDate from, LocalDate to) {
		ObservableList<Posten> result = FXCollections.observableArrayList();
		
		//LocalDate in Date konvertieren
		Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		// Alle Posten deren from datum nach dem ausgew�hlten Beginn liegt und deren to datum vor dem ausgew�hlten ende liegt in eine tempor�re liste
		for(Posten p : dataSource) {
			//einzahlungen �berspringen
			if(p.getKategorie_bezeichnung().toLowerCase().equals("einzahlung")) {
				continue;
			}
			if(p.getDatum().compareTo(fromDate)>= 0 && p.getDatum().compareTo(toDate) <= 0) {
				result.add(p);
			}
		}
		return result;
	}
}
