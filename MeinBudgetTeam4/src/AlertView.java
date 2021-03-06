import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertView {

	
	public void startUp(Exception exc) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("AlertDialog.fxml"));
		Stage primaryStage = new Stage(StageStyle.UNDECORATED);
		try {
			primaryStage.setScene(new Scene((Pane) loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		primaryStage.setTitle("Liquide wie Friede - Fehler");
		AlertController alertC = loader.<AlertController>getController();
		alertC.errorOcurred(exc);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("file:data/Coin.png"));
		primaryStage.show();
		
	}
}
