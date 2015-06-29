import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
public class stackedbarchart extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
           
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
           //Monate
        xAxis.setLabel("Monat");
        xAxis.setCategories(FXCollections.<String> observableArrayList(Arrays.asList(
                "Januar", 
                "Februar",
                "März",
                "April",
                "Mai",
                "Juni",
                "Juli",
                "August",
                "September",
                "Oktober",
                "November",
                "Dezember")));
                
        yAxis.setLabel("Wert");
           
        final StackedBarChart<String,Number> stackedBarChart = new StackedBarChart<String,Number>(xAxis,yAxis);
        stackedBarChart.setTitle("StackedBarChart");
        
          //erste Eigenschaft
        XYChart.Series<String,Number> series1 = new XYChart.Series();
        series1.setName("Eigenschaft 1");
           
        series1.getData().add(new XYChart.Data("Januar", 100));
        series1.getData().add(new XYChart.Data("Februar", 200));
        series1.getData().add(new XYChart.Data("März", 50));
        series1.getData().add(new XYChart.Data("April", 100));
        series1.getData().add(new XYChart.Data("Mai", 200));
        series1.getData().add(new XYChart.Data("Juni", 50));
        series1.getData().add(new XYChart.Data("Juli", 100));
        series1.getData().add(new XYChart.Data("August", 200));
        series1.getData().add(new XYChart.Data("September", 50));
        series1.getData().add(new XYChart.Data("Oktober", 100));
        series1.getData().add(new XYChart.Data("November", 200));
        series1.getData().add(new XYChart.Data("Dezember", 50));
          //zweite Eigenschaft
        XYChart.Series<String,Number> series2 = new XYChart.Series();
        series2.setName("Eigenschaft 2");
           
        series2.getData().add(new XYChart.Data("Januar", 150));
        series2.getData().add(new XYChart.Data("Februar", 100));
        series2.getData().add(new XYChart.Data("März", 60));
        series2.getData().add(new XYChart.Data("April", 100));
        series2.getData().add(new XYChart.Data("Mai", 200));
        series2.getData().add(new XYChart.Data("Juni", 50));
        series2.getData().add(new XYChart.Data("Juli", 100));
        series2.getData().add(new XYChart.Data("August", 200));
        series2.getData().add(new XYChart.Data("September", 50));
        series2.getData().add(new XYChart.Data("Oktober", 100));
        series2.getData().add(new XYChart.Data("November", 200));
        series2.getData().add(new XYChart.Data("Dezember", 50));
          
        stackedBarChart.getData().addAll(series1, series2);
               
        root.getChildren().addAll(stackedBarChart);
   
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }
}

   
