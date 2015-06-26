import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegistrationView {
	
	public void startUp(Stage primaryStage) throws IOException { 
		Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("RegistrationScreen.fxml"));
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}

}
