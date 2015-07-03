

import java.io.IOException;
import java.sql.SQLException;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RegistrationScreenController {
	
    @FXML
    private ImageView backImg;
    
	@FXML
	private StackPane registrationStackPane;

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
    
    Task<Void> task;
    
    Thread thread2;

    @FXML
    void ButtonAction_registrationButton(ActionEvent event) { //Registrierung des Nutzers
    	try {
    	BudgetPlanModel model = new BudgetPlanModel();
    	String u = usernameInput.getText();
    	String p = passwordInput.getText();
    	String p2 = passwordTwoInput.getText();
    	if(u.isEmpty() || p.isEmpty() || p2.isEmpty())
    		throw new IllegalArgumentException("Bitte füllen Sie alle Felder aus.");
    	if(!(p.equals(p2)))
			throw new IllegalArgumentException("Bitte geben Sie zweimal das selbe Passwort an");
    	if(u.trim() != u || p.trim() != p)
    		throw new IllegalArgumentException("Bitte verwenden Sie weder führende noch schließende Leerzeichen.");
			model.registerUser(u, p);
	        LoginView login = new LoginView();
	        login.startUp();
	        login.succesfulRegistration(u, p);
		} catch (ClassNotFoundException | SQLException | IllegalArgumentException e) {
			alertView alertV = new alertView();
			alertV.startUp(e);
		}
    	task.cancel();

    }
    
    @FXML
    void ButtonAction_backButton(ActionEvent event) throws IOException { //Rückkehr zum Hauptfenster
    	task.cancel();
        LoginView login = new LoginView();
			login.startUp();
        }
    
	void addListeners() {
		usernameInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldUser, String newUser) {
				check();
			}
		});
		passwordInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldPass, String newPass) {
				check();
			}
		});
		passwordTwoInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldPass, String newPass) {
				check();
			}
		});
		passwordTwoInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					if (!registrationButton.isDisabled()) {
						ButtonAction_registrationButton(new ActionEvent());
					}

				}

			}
		});
	}
    
    private void check() {
    	if(usernameInput.getText().trim().isEmpty() || passwordInput.getText().trim().isEmpty() || passwordTwoInput.getText().trim().isEmpty()) {
    		statusLabel.setText("Üngültige Eingabe");
    		statusLabel.setTextFill(Color.web("red"));
    		registrationButton.setDisable(true);
    	}
    	else {
    		statusLabel.setText("Gültige Eingabe");
    		statusLabel.setTextFill(Color.web("green"));
    		registrationButton.setDisable(false);
    	}
    		
    }
    
	private void animation() {
		ImageView coin = new ImageView("file:data/Coin_117.png");
		double rnd = Math.random();
		coin.setMouseTransparent(true);
		coin.setScaleX(0.1 + 0.7 * rnd);
		coin.setScaleY(0.1 + 0.7 * rnd);
		coin.setOpacity(0.2 + 0.5 * Math.random());
		coin.setTranslateX((Math.random() - 0.5) * 2 * 300); //Sollte eigentlich mit einer inversen Normalverteilung gelöst werden.
		Platform.runLater(()->{  //Ohne diese Zeile landet Das Programm in einem unendlichen Lock/Unlock Zyklus bie der Future Task
			registrationStackPane.getChildren().add(coin);
		});
		TranslateTransition transition = new TranslateTransition(Duration.seconds( 5 + Math.random() * 5), coin);
		transition.setFromY(-360 - Math.random() * 10);
		transition.setToY(380);
		//transition.setCycleCount(transition.INDEFINITE);
		transition.play();
	}
	
	public void createAnimation() {
		task = new Task<Void> () {
			@Override
			protected Void call() throws Exception {
				while(true) {
					Thread.sleep(500);
					animation();
				if(isCancelled()) {
					break;
				}
				}
				return null;
			}
		};			
			thread2 = new Thread(task);
			thread2.setDaemon(true);
			thread2.start();
			
	}
}