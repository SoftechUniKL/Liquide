package Charts;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class ChartFX {

	private Map<String, Double> data;
	
	public ChartFX(Map<String, Double> data) {
		this.data = data;
	}
	
	public PieChart makePieChart() {
		//Prepare Data for Chart
		ObservableList<PieChart.Data> series = FXCollections.observableArrayList();
		for(Map.Entry<String, Double> item : this.data.entrySet()) {
			series.add(new PieChart.Data(item.getKey(), item.getValue()));
		}
		
		//Create Chart
		PieChart chart = new PieChart(FXCollections.observableArrayList());
		chart.setData(series);
		chart.setLegendVisible(false);
		return chart;
	}
	
	public LineChart<String, Number> makeLineChart(String xLabel, String yLabel) {
		//Prepare Axis and Labels
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel(xLabel);
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel(yLabel);
		
		//Prepare Data for Chart
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		for(Map.Entry<String, Double> item : this.data.entrySet()) {
			series.getData().add(new XYChart.Data<String, Number>(item.getKey(), item.getValue()));
		}
		
		//Create Chart
		LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
		chart.getData().add(series);
		chart.setLegendVisible(false);
		return chart;
	}
	
	public BarChart<String, Number> makeBarChart(String xLabel, String yLabel) {
		//Prepare Axis and Labels
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel(xLabel);
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel(yLabel);
		
		//Prepare Data for Chart
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		for(Map.Entry<String, Double> item : this.data.entrySet()) {
			series.getData().add(new XYChart.Data<String, Number>(item.getKey(), item.getValue()));
		}
		
		//Create Chart
		BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
		chart.getData().add(series);
		chart.setLegendVisible(false);
		return chart;
	}
}
