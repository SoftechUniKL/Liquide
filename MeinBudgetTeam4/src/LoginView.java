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
	
//	private LoginScreenController loginC;
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		LoginView.primaryStage = primaryStage;
	}

	public void startUp() throws IOException {
//		primaryStage.setTitle("Liquide wie Friede");
//		//Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
//		//Scene myScene = new Scene(myPane);
//		primaryStage.setScene(new Scene((Pane) loader.load()));
//		primaryStage.setResizable(false);
//		primaryStage.getIcons().add(new Image("file:data/Coin.png")); //Details: http://docs.oracle.com/javafx/2/api/javafx/scene/image/Image.html
//		primaryStage.show();
		initiate();
		
	}
	
	public void succesfulRegistration(String username, String password) throws IOException {
		initiate();
		primaryStage.setTitle("Liquide wie Friede - Willkommen " + username);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		primaryStage.setScene(new Scene((Pane) loader.load()));
        LoginScreenController loginC = loader.<LoginScreenController>getController();
        loginC.succesfulRegistration(username, password);
       // stage.show();
	}
	
	private void initiate() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		primaryStage.setScene(new Scene((Pane) loader.load()));
		primaryStage.setTitle("Liquide wie Friede");
		LoginScreenController loginC = loader.<LoginScreenController>getController();
		System.out.println("test");
		loginC.addListeners();
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("file:data/Coin.png"));
		primaryStage.show();
		
	}
	
	


	

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
