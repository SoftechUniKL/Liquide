import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MenueView {
	
	public void startUp(Stage primaryStage) throws IOException { 
		Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Menue_Screen.fxml"));
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}
}
