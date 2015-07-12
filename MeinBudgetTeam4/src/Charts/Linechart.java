package Charts;

import org.jfree.chart.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFactory;

public class Linechart{

	private ChartPanel panel;
	
	/**
	 * 
	 * @param title Main title of the Chart
	 * @param valueAxis Value Axis title
	 * @param categoryAxis Category Axis title
	 * @param data HashMap<String, Double> containing the data
	 */
	public Linechart(String title, String valueAxis, String categoryAxis, Map<String, Double> data) {
		//Leeres Dataset erzeugen
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		//Über die Daten iterieren
		Iterator it = data.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			//Wertepaar dem Dataset hinzufügen
			dataset.addValue((Number)pair.getValue(), title, (String)pair.getKey());
		}
		
		//Chart aus Daten erzeugen
		JFreeChart chart = ChartFactory.createLineChart(title, categoryAxis, valueAxis, dataset);
		
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
