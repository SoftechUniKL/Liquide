import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginView implements Observer {
	
	
	private static Stage primaryStage;
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		LoginView.primaryStage = primaryStage;
	}

	public void startUp(Stage primaryStage) throws IOException {
		setPrimaryStage(primaryStage);
		primaryStage.setTitle("Liquide wie Friede");
		Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("file:data/Coin.png")); //Details: http://docs.oracle.com/javafx/2/api/javafx/scene/image/Image.html
		primaryStage.show();
		
	}
	
	public void succesfulRegistration(String username, String password) throws IOException {
		primaryStage.setTitle("Liquide wie Friede - Willkommen " + username);
		Stage stage = primaryStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		stage.setScene(new Scene((Pane) loader.load()));
        LoginScreenController loginC = loader.<LoginScreenController>getController();
        loginC.succesfulRegistration(username, password);
        stage.show();
	}
	
	


	

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
