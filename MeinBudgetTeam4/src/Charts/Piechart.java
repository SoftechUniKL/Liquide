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
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ChartFactory;

public class Piechart{

	private ChartPanel panel;
	
	/**
	 * 
	 * @param title Main title of the Chart
	 * @param valueAxis Value Axis title
	 * @param categoryAxis Category Axis title
	 * @param data HashMap<String, Double> containing the data
	 */
	public Piechart(String title, Map<String, Double> data) {
		//Leeres Dataset erzeugen
		DefaultPieDataset dataset = new DefaultPieDataset();

		//Über die Daten iterieren
		Iterator it = data.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			//Wertepaar dem Dataset hinzufügen
			dataset.setValue((String)pair.getKey(),(Number)pair.getValue());
		}
		
		//Chart aus Daten erzeugen
		JFreeChart chart = ChartFactory.createPieChart(title, dataset);
		
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
