import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegistrationView {
	
	private static Stage primaryStage;
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		RegistrationView.primaryStage = primaryStage;
	}

	public void startUp()  { 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationScreen.fxml"));
		try {
			primaryStage.setScene(new Scene((Pane) loader.load()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RegistrationScreenController registrationC = loader.<RegistrationScreenController>getController();
		registrationC.addListeners();
		//primaryStage.setScene(myScene);
		primaryStage.show();
		registrationC.createAnimation();
	}

}
