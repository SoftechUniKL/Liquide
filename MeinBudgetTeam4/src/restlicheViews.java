import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;


import Charts.ChartFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class restlicheViews {
	// View für Ausgaben
	public static class ausgaben {
  	  private static Stage primaryStage;
		public static  Stage getPrimaryStage() {
			return primaryStage;
		}
		public static  void setPrimaryStage(Stage primaryStage) {
			Menue.primaryStage = primaryStage;
		}
		public void startUp_ausgaben() throws IOException {
			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Ausgaben_Screen.fxml"));
			Scene myScene = new Scene(myPane);
			Menue.primaryStage.setScene(myScene);
			Menue.primaryStage.show();	
			
			
		}
	}
	
	// ab hier View für Menü
	public static class Menue {
		
		private static Stage primaryStage;
		public static  Stage getPrimaryStage() {
			return primaryStage;
		}

		public static   void setPrimaryStage(Stage primaryStage) {
			Menue.primaryStage = primaryStage;
		}
		public void startUp_menue() throws IOException, SQLException {
			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Menue_Screen.fxml"));
			Scene myScene = new Scene(myPane);
			Menue.primaryStage.setScene(myScene);
			Menue.primaryStage.show();
			

			Map<String, Double> map = ConvertDataForChart.getLatest(new BudgetPlanModel().transcribe());
			Chart node = new ChartFX(map).makeBarChart("Datum", "Wert");
			node.setLayoutX(250);
			node.setLayoutY(250);
			node.setPrefSize(350, 250);
			myPane.getChildren().add(node);
			
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
      /* View für Einkommen
       */
      public static class einkommen {
    	  private static Stage primaryStage;
  		public static  Stage getPrimaryStage() {
  			return primaryStage;
  		}
  		public static   void setPrimaryStage(Stage primaryStage) {
  			Menue.primaryStage = primaryStage;
  		}
  		public void startUp_datenverwaltung() throws IOException {
  			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Einkommen_Screen.fxml"));
  			Scene myScene = new Scene(myPane);
  			Menue.primaryStage.setScene(myScene);
  			Menue.primaryStage.show();					
  		}	  
      }
      /* View für Postenverwaltung
       */
      public static class postenverwaltung {
    	  private static Stage primaryStage;
  		public static  Stage getPrimaryStage() {
  			return primaryStage;
  		}
  		public static   void setPrimaryStage(Stage primaryStage) {
  			Menue.primaryStage = primaryStage;
  		}
  		public void startUp_postenverwaltung() throws IOException {
  			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Postenverwaltung_Screen.fxml"));
  			Scene myScene = new Scene(myPane);
  			Menue.primaryStage.setScene(myScene);
  			Menue.primaryStage.show();		
  		}  
      }
      /* View für Optionen
       * 
       */
      public static class optionen {
    	  private static Stage primaryStage;
  		public static  Stage getPrimaryStage() {
  			return primaryStage;
  		}
  		public static   void setPrimaryStage(Stage primaryStage) {
  			Menue.primaryStage = primaryStage;
  		}
  		public void startUp_optionen() throws IOException {
  			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Optionen_Screen.fxml"));
  			Scene myScene = new Scene(myPane);
  			Menue.primaryStage.setScene(myScene);
  			Menue.primaryStage.show();		
  		}  
      }
	}
