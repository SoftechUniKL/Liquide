import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class alertView {

	
	public void startUp(Exception exc) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("AlertDialog.fxml"));
		Stage primaryStage = new Stage(StageStyle.DECORATED);
		primaryStage.setScene(new Scene((Pane) loader.load()));
		primaryStage.setTitle("Liquide wie Friede - Fehler");
		alertController alertC = loader.<alertController>getController();
		alertC.errorOcurred(exc);
		//primaryStage.setScene(myScene);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("file:data/Coin.png"));
		primaryStage.show();
		
	}
}
