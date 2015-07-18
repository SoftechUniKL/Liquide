package Charts;

import java.util.Map;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

public class Barchart implements Chart{

	private ChartPanel panel;
	
	/**
	 * 
	 * @param title Main title of the Chart
	 * @param valueAxis Value Axis title
	 * @param categoryAxis Category Axis title
	 * @param data HashMap<String, Double> containing the data
	 */
	public Barchart(String title, String valueAxis, String categoryAxis, Map<String, Double> data) {
		//Leeres Dataset erzeugen
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		//Über die Daten iterieren und jedes Element zum dataset hinzufügen
		for(Map.Entry<String, Double> item : data.entrySet()) {
			dataset.addValue(item.getValue(), title, item.getKey());
		}
		
		//Chart aus Daten erzeugen
		JFreeChart chart = ChartFactory.createBarChart(title, categoryAxis, valueAxis, dataset);
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
