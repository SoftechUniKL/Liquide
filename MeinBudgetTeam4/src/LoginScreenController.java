

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	
	 @FXML
	 private StackPane loginStackPane;

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
    
    private Task<Void> task;
    
    private Thread thread2;

    @FXML
    void buttonAction_registrationButton_login(ActionEvent event) throws IOException { //Wechsel in anderes Menü
    	task.cancel();
        Stage stage=(Stage) registrationButton_login.getScene().getWindow();
        RegistrationView registration = new RegistrationView();
        registration.setPrimaryStage(stage);
        registration.startUp();
    }
    
    @FXML
    void buttonAction_signInButton(ActionEvent event) {
    	try {
    	task.cancel();
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
    	statusLabel.setText("Ihre Registrierung war erfolgreich");
    	statusLabel.setTextFill(Color.web("green"));
    	usernameInput.setText(username);
    	passwordInput.setText(password);
    	
    }
    
    void addListeners() {
    	usernameInput.textProperty().addListener(new ChangeListener<String> () {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldUser, String newUser) {
    			check();
    		}
    	});
    	passwordInput.textProperty().addListener(new ChangeListener<String> () {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldPass, String newPass) {
    			check();
    		}
    	});
    	passwordInput.setOnKeyPressed(new EventHandler<KeyEvent> () 
    			{
    		@Override
    		public void handle(KeyEvent ke)
    		{
    			if(ke.getCode().equals(KeyCode.ENTER)) {
    				if (!signInButton.isDisabled()) {
    					buttonAction_signInButton(new ActionEvent());
    				}
    					
    					
    			}
    				
    		}
    			});
    }
    
    private void check() {
    	if(usernameInput.getText().trim().isEmpty() || passwordInput.getText().trim().isEmpty()) {
    		statusLabel.setText("Üngültige Eingabe");
    		statusLabel.setTextFill(Color.web("red"));
    		signInButton.setDisable(true);
    	}
    	else {
    		statusLabel.setText("Gültige Eingabe");
    		statusLabel.setTextFill(Color.web("green"));
    		signInButton.setDisable(false);
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
		loginStackPane.getChildren().add(coin);
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
