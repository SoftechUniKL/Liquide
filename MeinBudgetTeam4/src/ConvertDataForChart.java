import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ConvertDataForChart {

	public static Map<String, Double> convertData(ArrayList<Posten> dataSource) {
		Map<String, Double> result = new HashMap<String, Double>();
		for (Posten p : dataSource) {
			if(result.containsKey(p.getDatum().toString())) {
				int i = 0;
				while(true) {
					if(!result.containsKey(p.getDatum().toString()+"("+i+")")) {
						result.put(p.getDatum().toString()+"("+i+")", p.getPreis()*p.getAnzahl());
						break;
					}
					i++;
				}
			}
			result.put(p.getDatum().toString(), p.getPreis()*p.getAnzahl());
		}
		return result;
	}
	
	public static Map<String, Double> getLatest(ArrayList<Posten> dataSource) {
		Map<String, Double> result = new HashMap<String, Double>();
		int from = dataSource.size()-5 < 0 ? 0 : dataSource.size()-5;
		int to = dataSource.size() < 0? 0 : dataSource.size();
		for(Posten p: dataSource.subList(from, to)) {
			if(result.containsKey(p.getDatum().toString())) {
				int i = 0;
				while(true) {
					if(!result.containsKey(p.getDatum().toString()+"("+i+")")) {
						result.put(p.getDatum().toString()+"("+i+")", p.getPreis()*p.getAnzahl());
						break;
					}
					i++;
				}
			}
			result.put(p.getDatum().toString(), p.getPreis()*p.getAnzahl());
		}
		return result;
	}
	
}
