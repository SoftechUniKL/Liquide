import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * 
 */

/**
 * @author Shahin
 *
 */


public class LoginScreen_Controller   {
	
	@FXML
	private TilePane loginPane;


    @FXML //fx:id = "registrationButton_login"
    private Button registrationButton_login; //wird von FXML-Loader zugewiesen
    
    
    @FXML
    private Button signInButton;

    @FXML
    void buttonAction_registrationButton_login(ActionEvent event) throws IOException {
    	System.out.println("Ich bin der Button mit der id: registrationButton_login ");
        Stage stage; 
        Parent root;
        stage=(Stage) registrationButton_login.getScene().getWindow();
    	//loginPane = (Pane)FXMLLoader.load(getClass().getResource("Registration_Screen.fxml"));
        root = FXMLLoader.load(getClass().getResource("Registration_Screen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    
    @FXML
    void buttonAction_signInButton(ActionEvent event) throws IOException {
    	System.out.println("Ich bin ein Anmeldebutton");
    	

    }
    

    
   /* @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert registrationButton_login != null : "fx:id=\"registrationButton_login\"wurde nicht übergeben: kontrollieren Sie Ihre FXML-Datei.";
        assert signInButton != null : "fx:id=\"signInButton\"wurde nicht übergeben: kontrollieren Sie Ihre FXML-Datei.";
      //  registrationButton_login.setOnAction(new EventHandler<ActionEvent>() {

     /*       @Override
            public void handle(ActionEvent event) {

        //    	content.getChildren().clear();
        //    	content.getChildren().add(FXMLLoader.load(getClass().getResource("Content2.fxml"));
               System.out.println("That was easy, wasn't it?");
            } */
   //     }); */

    

}
