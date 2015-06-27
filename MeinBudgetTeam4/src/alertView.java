import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class alertView {

	
	public void startUp() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AlertDialog.fxml"));
		Stage primaryStage = new Stage(StageStyle.DECORATED);
		primaryStage.setScene(new Scene((Pane) loader.load()));
		//primaryStage.setTitle("Liquide wie Friede");
		Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("file:data/Coin.png")); //Details: http://docs.oracle.com/javafx/2/api/javafx/scene/image/Image.html
		primaryStage.show();
		
	}
}
