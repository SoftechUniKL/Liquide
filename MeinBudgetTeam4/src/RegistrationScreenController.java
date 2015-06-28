

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegistrationScreenController {

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button registrationButton;

    @FXML
    private PasswordField passwordTwoInput;

    @FXML
    private Button backButton;

    @FXML
    private Label statusLabel;

    @FXML
    void ButtonAction_registrationButton(ActionEvent event) throws IOException { //Registrierung des Nutzers
    	try {
    	BudgetPlanModel model = new BudgetPlanModel();
    	String u = usernameInput.getText();
    	String p = passwordInput.getText();
    	String p2 = passwordTwoInput.getText();
    	if(u.isEmpty() || p.isEmpty() || p2.isEmpty())
    		throw new IllegalArgumentException("Bitte füllen Sie alle Felder aus.");
    	if(!(p.equals(p2)))
			throw new IllegalArgumentException("Bitte geben Sie zweimal das selbe Passwort an");
			model.registerUser(u, p);
	        LoginView login = new LoginView();
	        login.startUp();
	        login.succesfulRegistration(u, p);
		} catch (ClassNotFoundException | SQLException | IllegalArgumentException e) {
			alertView alertV = new alertView();
			alertV.startUp(e);
		}

    }
    
    @FXML
    void ButtonAction_backButton(ActionEvent event) throws IOException { //Rückkehr zum Hauptfenster
    	System.out.println("Ich bin der Back-Button");
        LoginView login = new LoginView();
        try {
			login.startUp();
		} catch (IOException e) {
			alertView alertV = new alertView();
			alertV.startUp(e);
		}
        }
}