import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class restlicheViews {
	// ab hier View für Menü
	public static class Menue {
		
		private static Stage primaryStage;
		public static  Stage getPrimaryStage() {
			return primaryStage;
		}

		public static   void setPrimaryStage(Stage primaryStage) {
			Menue.primaryStage = primaryStage;
		}
		public void startUp_menue() throws IOException {
			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Menue_Screen.fxml"));
			Scene myScene = new Scene(myPane);
			Menue.primaryStage.setScene(myScene);
			Menue.primaryStage.show();
			
		}
		
	}
    // ab hier View für neuerPosten
public static class neuerPosten   {	
		private static Stage primaryStage;
		public static  Stage getPrimaryStage() {
			return primaryStage;
		}
		public static   void setPrimaryStage(Stage primaryStage) {
			Menue.primaryStage = primaryStage;
		}
		public void startUp_neuerPosten() throws IOException {
			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("neuerPosten_Screen.fxml"));
			Scene myScene = new Scene(myPane);
			Menue.primaryStage.setScene(myScene);
			Menue.primaryStage.show();	
			
			
		}
		
	}
      // ab hier View für Datenverwaltung
      public static class datenverwaltung {
    	  private static Stage primaryStage;
  		public static  Stage getPrimaryStage() {
  			return primaryStage;
  		}
  		public static   void setPrimaryStage(Stage primaryStage) {
  			Menue.primaryStage = primaryStage;
  		}
  		public void startUp_datenverwaltung() throws IOException {
  			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Datenverwaltung_Screen.fxml"));
  			Scene myScene = new Scene(myPane);
  			Menue.primaryStage.setScene(myScene);
  			Menue.primaryStage.show();	
  			
  			
  		}
    	  
      }


	
		
	
	
	
	
	
	
	

}
