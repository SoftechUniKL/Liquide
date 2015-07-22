import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.swing.JPanel;

import Charts.ChartFX;
import javafx.event.*;

public class AusgabenScreenController {

    @FXML
    private ToggleGroup Group1;

    @FXML
    private DatePicker datumEndeInput_dp_ausgaben;

    @FXML
    private RadioButton rb5_rb_ausgaben;

    @FXML
    private RadioButton rb4_rb_ausgaben;

    @FXML
    private DatePicker datumBeginnInput_dp_ausgaben;

    @FXML
    private RadioButton rb1_rb_ausgaben;

    @FXML
    private RadioButton rb3_rb_ausgaben;

    @FXML
    private Label Info_ausgaben;

    @FXML
    private Button visualisieren_button_ausgaben;

    @FXML
    private Button zurück_button_ausgaben;

    @FXML
    private RadioButton rb2_rb_ausgaben;
    
    @FXML
    private AnchorPane result_pane;

    @FXML
    void bA_visualisieren_ausgaben(ActionEvent event) throws SQLException {
    	if(datumBeginnInput_dp_ausgaben.getValue() == null || datumEndeInput_dp_ausgaben.getValue() == null) {
    		return;
    	}
    	Map<String, Double> map = ConvertDataForChart.getFromTo(new BudgetPlanModel().transcribe(), datumBeginnInput_dp_ausgaben.getValue(), datumEndeInput_dp_ausgaben.getValue());
    	Chart chart;
        
    	//Radiobutton 1 gewählt, säule
    	if(Group1.getSelectedToggle().toString().contains("rb1")) {
        	chart = new ChartFX(map).makeBarChart("Datum", "Wert");
    	}
    	//Radiobutton 2 gewähl, torte
    	else if(Group1.getSelectedToggle().toString().contains("rb2")) {
    		map = ConvertDataForChart.getFromToByCategory(new BudgetPlanModel().transcribe(), datumBeginnInput_dp_ausgaben.getValue(), datumEndeInput_dp_ausgaben.getValue());
        	chart = new ChartFX(map).makePieChart();
        	for(PieChart.Data data : ((PieChart) chart).getData()) {
        		data.setName(data.getName() + "\n" + data.getPieValue() + "€");
        	}
    	}
    	//Keiner oder 3. Radiobutton, linie
    	else {
        	chart = new ChartFX(map).makeLineChart("Datum", "Wert");
    	}
		
		chart.setLayoutX(250);
		chart.setLayoutY(0);
		chart.setPrefSize(350, 250);
		chart.setId("chart");
		
		for(javafx.scene.Node n: result_pane.getChildren()) {
			if(n.getId().equals("chart")) {
				result_pane.getChildren().remove(n);
				break;
			}
		}
		
		result_pane.getChildren().add(chart);
    	
		//Popupfenster mit Tabelle erstellen
		new AusgabenDetailsPopup(new BudgetPlanModel().transcribe(), datumBeginnInput_dp_ausgaben.getValue(), datumEndeInput_dp_ausgaben.getValue());
    }

    @FXML
    void bA_zurück_ausgaben(ActionEvent event) throws IOException, SQLException {
    	Stage stage=(Stage) zurück_button_ausgaben.getScene().getWindow();
        restlicheViews.Menue menü = new restlicheViews.Menue();
        menü.setPrimaryStage(stage);
        menü.startUp_menue();
    }

}