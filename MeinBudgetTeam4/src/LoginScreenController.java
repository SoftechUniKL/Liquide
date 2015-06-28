

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 
 */

/**
 * @author Shahin
 *
 */


public class LoginScreenController   {
	
	@FXML
	private TilePane loginPane;


    @FXML //fx:id = "registrationButton_login"
    private Button registrationButton_login; //wird von FXML-Loader zugewiesen
    
    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;
    
    @FXML
    private Button signInButton;
    
    @FXML
    private Label statusLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    void buttonAction_registrationButton_login(ActionEvent event) throws IOException { //Wechsel in anderes Menü
    	System.out.println("Ich bin der Button mit der id: registrationButton_login " + statusLabel.getText());
        Stage stage=(Stage) registrationButton_login.getScene().getWindow();
        RegistrationView registration = new RegistrationView();
        registration.setPrimaryStage(stage);
        registration.startUp();
    }
    
    @FXML
    void buttonAction_signInButton(ActionEvent event) throws IOException {
    	try {
    	BudgetPlanModel model = new BudgetPlanModel();
    	String u = usernameInput.getText();
    	String p = passwordInput.getText();
    	if(u.isEmpty() || p.isEmpty()) {
    		throw new IllegalArgumentException("Bitt füllen Sie beide Felder aus.");
    	}
    	//TODO Die beiden Folgenden Prozesse parallel threaden
    	progressIndicator.setVisible(true);
    	//progressIndicator.setStyle(" -fx-progress-color: red;");
		model.initiateDatabase(u, p);
		} catch (ClassNotFoundException | SQLException | IllegalArgumentException e) {
			alertView alertV = new alertView();
			alertV.startUp(e);
		}

    }
    
    
    void succesfulRegistration(String username, String password) {
    	System.out.println("Ich wurder aus der LoginScreenController Klasse aufgerufen!" );
    	statusLabel.setText("Ihre Registrierung war erfolgreich");
    	statusLabel.setTextFill(Color.web("green"));
    	usernameInput.setText(username);
    	passwordInput.setText(password);
    	
    }
    
    void addListeners() {
    	usernameInput.textProperty().addListener(new ChangeListener<String> () {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldUser, String newUser) {
    			System.out.println("oldValue: " + oldUser + " --- newValue: " + newUser);
    			check();
    		}
    	});
    	passwordInput.textProperty().addListener(new ChangeListener<String> () {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldPass, String newPass) {
    			System.out.println("pw --> oldValue: " + oldPass + " --- newValue: " + oldPass);
    			check();
    		}
    	});
    }
    
    private void check() {
    	if(usernameInput.getText().trim().isEmpty() || passwordInput.getText().trim().isEmpty()) {
    		statusLabel.setText("Eingaben falsch");
    		statusLabel.setTextFill(Color.web("red"));
    		signInButton.setDisable(true);
    	}
    	else {
    		statusLabel.setText("Gültige Eingabe");
    		statusLabel.setTextFill(Color.web("green"));
    		signInButton.setDisable(false);
    	}
    		
    }
   
    

}
