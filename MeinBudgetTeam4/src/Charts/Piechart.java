package Charts;

import java.util.Map;
import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;

public class Piechart implements Chart{

	private ChartPanel panel;
	
	/**
	 * 
	 * @param title Main title of the Chart
	 * @param data HashMap<String, Double> containing the data
	 */
	public Piechart(String title, Map<String, Double> data) {
		//Leeres Dataset erzeugen
		DefaultPieDataset dataset = new DefaultPieDataset();

		//Über die Daten iterieren und jedes Element zum dataset hinzufügen
		for(Map.Entry<String, Double> item : data.entrySet()) {
			dataset.setValue(item.getKey(), item.getValue());
		}
		
		//Chart aus Daten erzeugen
		JFreeChart chart = ChartFactory.createPieChart(title, dataset);
		chart.removeLegend();
		//Panel zugänglich machen
		this.panel = new ChartPanel(chart);
	}
	
	/**
	 * JPanel to add in JFrame
	 * @return ChartPanel
	 */
	public ChartPanel getChartPanel() {
		return this.panel;
	}
}
