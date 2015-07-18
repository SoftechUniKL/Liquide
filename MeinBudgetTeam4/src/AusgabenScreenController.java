import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.JPanel;

import Charts.Barchart;
import Charts.Chart;
import Charts.Linechart;
import Charts.Piechart;
import javafx.embed.swing.SwingNode;
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

    	Map<String, Double> map = ConvertDataForChart.convertData(new BudgetPlanModel().transcribe());
    	Chart chart;
    	if(Group1.getSelectedToggle().toString().contains("rb1")) {
        	chart = new Barchart("Ausgaben", "Wert", "Datum", map);
    	}
    	else if(Group1.getSelectedToggle().toString().contains("rb2")) {
        	chart = new Piechart("Ausgaben", map);
    	}
    	else {
        	chart = new Linechart("Ausgaben", "Wert", "Datum", map);
    	}
		
		JPanel panel = chart.getChartPanel();
		panel.setPreferredSize(new Dimension(350, 150));
		SwingNode node = new SwingNode();
		node.setLayoutX(250);
		node.setLayoutY(0);
		node.setContent(panel);
		result_pane.getChildren().add(node);
    }

    @FXML
    void bA_zurück_ausgaben(ActionEvent event) throws IOException {
    	Stage stage=(Stage) zurück_button_ausgaben.getScene().getWindow();
        restlicheViews.Menue menü = new restlicheViews.Menue();
        menü.setPrimaryStage(stage);
        menü.startUp_menue();
    }

}