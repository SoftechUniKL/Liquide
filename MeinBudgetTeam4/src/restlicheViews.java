import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import Charts.Barchart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
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
		public void startUp_menue() throws IOException {
			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Menue_Screen.fxml"));
			Scene myScene = new Scene(myPane);
			Menue.primaryStage.setScene(myScene);
			Menue.primaryStage.show();
			
///////////////Dummydaten//////////////
			Map<String, Double> map = new HashMap<String, Double>();
			map.put("Jan", 12.3);
			map.put("Aug", 3.3);
			map.put("Jul", 4.3);
			map.put("Jun", 2.3);
			map.put("Mai", 5.3);
			map.put("Apr", 8.3);
			////////////////////////////////
			Barchart chart = new Barchart("Ausgaben", "Wert", "Monat", map);
			
			JPanel panel = chart.getChartPanel();
			panel.setPreferredSize(new Dimension(300, 150));
			SwingNode node = new SwingNode();
			node.setLayoutX(300);
			node.setLayoutY(250);
			node.setContent(panel);
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


	
		
	
	
	
	
	
	
	

	}
