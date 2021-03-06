import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginView {
	
	
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
		initiate();
		
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
        loginC.createAnimation();
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
		primaryStage.setResizable(false);
		primaryStage.show();
		loginC.createAnimation(); //Animation wird hinzugefügt
		
	}

/*	
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
	primaryStage.minHeightProperty().bind(LoginView.primaryStage.getScene().widthProperty().multiply(400/650));
	primaryStage.minWidthProperty().bind(LoginView.primaryStage.getScene().heightProperty().multiply(650/400));
	System.out.println("geht");
	}
	
	


	

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	*/

}
