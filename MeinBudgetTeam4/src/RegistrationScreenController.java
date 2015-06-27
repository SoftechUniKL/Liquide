

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
    	BudgetPlanModel model = new BudgetPlanModel();
    	String u = usernameInput.getText();
    	String p = passwordInput.getText();
    	String p2 = passwordTwoInput.getText();
    	if(u.isEmpty() || p.isEmpty() || p2.isEmpty())
    		throw new IllegalArgumentException("Bitte füllen Sie alle Felder aus.");
    	if(!(p.equals(p2)))
			throw new IllegalArgumentException("Bitte geben Sie zweimal das selbe Passwort an");
    	try {
			model.registerUser(u, p);
	        Stage stage=(Stage) registrationButton.getScene().getWindow();
	        LoginView login = new LoginView();
	        login.startUp(stage);
	        login.succesfulRegistration(u, p);
		} catch (ClassNotFoundException | SQLException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

    }
    
    @FXML
    void ButtonAction_backButton(ActionEvent event) throws IOException { //Rückkehr zum Hauptfenster
    	System.out.println("Ich bin der Back-Button");
        Stage stage=(Stage) backButton.getScene().getWindow();
        LoginView login = new LoginView();
        login.startUp(stage);
        }
}