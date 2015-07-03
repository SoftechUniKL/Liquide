import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginView implements Observer {
	
	
	private static Stage primaryStage;
	
	private static boolean changed;
//	private LoginScreenController loginC;
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		LoginView.primaryStage = primaryStage;
	}

	public void startUp() {
//		primaryStage.setTitle("Liquide wie Friede");
//		//Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
//		//Scene myScene = new Scene(myPane);
//		primaryStage.setScene(new Scene((Pane) loader.load()));
//		primaryStage.setResizable(false);
//		primaryStage.getIcons().add(new Image("file:data/Coin.png")); //Details: http://docs.oracle.com/javafx/2/api/javafx/scene/image/Image.html
//		primaryStage.show();
		initiate();
	//	resizeListener();
		
	}
	
	public void succesfulRegistration(String username, String password) {
		primaryStage.setTitle("Liquide wie Friede - Willkommen " + username);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		try {
			primaryStage.setScene(new Scene((Pane) loader.load()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LoginScreenController loginC = loader.<LoginScreenController>getController();
        loginC.addListeners();
        loginC.succesfulRegistration(username, password);
	}
	
	private void initiate() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		try {
			primaryStage.setScene(new Scene((Pane) loader.load()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		primaryStage.setTitle("Liquide wie Friede");
		LoginScreenController loginC = loader.<LoginScreenController>getController();
		loginC.addListeners();
		primaryStage.getIcons().add(new Image("file:data/Coin.png"));
		//primaryStage.setResizable(false);
		primaryStage.show();
		loginC.createAnimation(); //Animation wird hinzugefügt
		
	}

	
	public void resizeListener() {
		
	primaryStage.heightProperty().addListener(new ChangeListener<Number> () {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldHeight, Number newHeight) {
			if(changed) {
			changed = false;
			primaryStage.setWidth((double) newHeight * (400.0/650.0));
			}
			else
				changed = true;
			System.out.println("HeightListener:" + oldHeight + "-----" + newHeight + "changed: " + changed);
		}
	});
	primaryStage.widthProperty().addListener(new ChangeListener<Number> () {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
			if(changed) {
				changed = false;
			primaryStage.setHeight((double)newWidth * (650.0/400.0));
			}
			else
				changed = true;
			System.out.println("WidthListener:" + oldWidth + "-----" + newWidth + "changed: " + changed);
		}
	});
	
	}
	
	void resize() {
	primaryStage.minHeightProperty().bind(LoginView.primaryStage.getScene().widthProperty().multiply(2.0));
	primaryStage.minWidthProperty().bind(LoginView.primaryStage.getScene().heightProperty().divide(2));
	System.out.println("geht");
	}
	
	


	

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
