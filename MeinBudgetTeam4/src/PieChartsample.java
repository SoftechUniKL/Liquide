import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;
 
public class PieChartsample extends Application {
 
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Pie Chart");
        stage.setWidth(500);
        stage.setHeight(500);
        
 //Werte und Eigenschaften können hier eingefügt werden
        
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("", 1 ),
                new PieChart.Data("", 2),
                new PieChart.Data("", 3),
                new PieChart.Data("", 4),
                new PieChart.Data("", 5));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Pie Chart");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}