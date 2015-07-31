import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.* ;

public class OptionenScreenController {

    @FXML
    private Button nein_button_optionen;

    @FXML
    private Button zurück_button_optionen;

    @FXML
    private Button reset_button_optionen;

    @FXML
    private Button ja_button_optionen;

    @FXML
    private Label info_optionen;

    @FXML
    private Label frage_optionen;
    
    /* reset() leert das Label nach 10 sekunden */
	public void reset() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10),
				ev -> {
					info_optionen.setText(null);
				}));
		timeline.play();
	}

    @FXML
    void bA_reset_optionen(ActionEvent event) {
    	frage_optionen.setVisible(true);
    	ja_button_optionen.setVisible(true);
        nein_button_optionen.setVisible(true); 
    }

    @FXML
    void bA_ja_optionen(ActionEvent event) throws SQLException {
    	BudgetPlanModel model = new BudgetPlanModel();
    	model.DROP_ALL();
    	frage_optionen.setVisible(false);
    	ja_button_optionen.setVisible(false);
        nein_button_optionen.setVisible(false); 
        info_optionen.setTextFill(Color.web("green"));
        info_optionen.setText("Die Löschung war erfolgreich") ;
        reset();

    }

    @FXML
    void bA_nein_optionen(ActionEvent event) {
    	frage_optionen.setVisible(false);
    	ja_button_optionen.setVisible(false);
        nein_button_optionen.setVisible(false); 
        info_optionen.setTextFill(Color.web("red"));
        info_optionen.setText("Sie konnten das Löschen noch abwenden") ;
        reset();
    }

    @FXML
    void bA_zurück_optionen(ActionEvent event) throws SQLException, IOException {
		Stage stage = (Stage) zurück_button_optionen.getScene()
				.getWindow();
		restlicheViews.Menue menü = new restlicheViews.Menue();
		menü.setPrimaryStage(stage);
		menü.startUp_menue();

    }

}