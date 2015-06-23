import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RegistrationScreen_Controller {

    @FXML
    private Button registrationButton;
    
    @FXML
    private Button backButton;

    @FXML
    void ButtonAction_registrationButton(ActionEvent event) {

    }
    
    @FXML
    void ButtonAction_backButton(ActionEvent event) throws IOException {
    	System.out.println("Ich bin der Back-Button ");
        Stage stage; 
        Parent root;
        stage=(Stage) backButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}