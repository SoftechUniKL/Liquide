import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;

import java.sql.*;
import java.io.IOException;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;




public class restlicheController {
	// ab hier LoginScreenController @author Shahin
	BudgetPlanModel model = new BudgetPlanModel();
	public String user ;
	
	public void setUser(String user){
		this.user = user;
	}
	public String getUser(){
		return user;
		
	}
	
    
	@FXML
	private TilePane loginPane;
    @FXML //fx:id = "registrationButton_login"
    private Button registrationButton_login; //wird von FXML-Loader zugewiesen 
    @FXML
    private TextField usernameInput;
    
    @FXML
    private PasswordField passwordInput;
    
    @FXML
    private Button signInButton;
    @FXML
    private Label statusLabel;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    void buttonAction_registrationButton_login(ActionEvent event) throws IOException { //Wechsel in anderes Menü
    	System.out.println("Ich bin der Button mit der id: registrationButton_login " + statusLabel.getText());
        Stage stage=(Stage) registrationButton_login.getScene().getWindow();
        RegistrationView registration = new RegistrationView();
        registration.setPrimaryStage(stage);
        registration.startUp();
    }
    @FXML
   public void
    buttonAction_signInButton(ActionEvent event) throws IOException {

    	try {
    	BudgetPlanModel model = new BudgetPlanModel();
        String u = usernameInput.getText();
        setUser(usernameInput.getText());
        String p = passwordInput.getText();
    	if(u.isEmpty() || p.isEmpty()) {
    		throw new IllegalArgumentException("Bitt füllen Sie beide Felder aus.");
    	}
    	
    	
    	//TODO Die beiden Folgenden Prozesse parallel threaden
    	progressIndicator.setVisible(true);
    	//progressIndicator.setStyle(" -fx-progress-color: red;");
		model.initiateDatabase(u, p);
		
		} catch (ClassNotFoundException | SQLException | IllegalArgumentException e) {
			alertView alertV = new alertView();
			alertV.startUp(e);
		}
    	 Stage stage=(Stage) signInButton.getScene().getWindow();
         restlicheViews.Menue menü = new restlicheViews.Menue();
         menü.setPrimaryStage(stage);
         menü.startUp_menue();
         setUser("lala");
         
    }
    
    
    void succesfulRegistration(String username, String password) {
    	System.out.println("Ich wurder aus der LoginScreenController Klasse aufgerufen!" );
    	statusLabel.setText("Ihre Registrierung war erfolgreich");
    	statusLabel.setTextFill(Color.web("green"));
    	usernameInput.setText(username);
    	passwordInput.setText(password);
    }
    
    void addListeners() {
    	usernameInput.textProperty().addListener(new ChangeListener<String> () {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldUser, String newUser) {
    			System.out.println("oldValue: " + oldUser + " --- newValue: " + newUser);
    			check();
    		}
    	});
    	passwordInput.textProperty().addListener(new ChangeListener<String> () {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldPass, String newPass) {
    			System.out.println("pw --> oldValue: " + oldPass + " --- newValue: " + oldPass);
    			check();
    		}
    	});
    }
    
    private void check() {
    	if(usernameInput.getText().trim().isEmpty() || passwordInput.getText().trim().isEmpty()) {
    		statusLabel.setText("Eingaben falsch");
    		statusLabel.setTextFill(Color.web("red"));
    		signInButton.setDisable(true);
    	}
    	else {
    		statusLabel.setText("Gültige Eingabe");
    		statusLabel.setTextFill(Color.web("green"));
    		signInButton.setDisable(false);
    	}
    		
    }
    
    
	

	
	@FXML
    private Button ausgaben_button_menue;

    @FXML
    private Button optionen_button_menue;

    @FXML
    private AnchorPane menue_pane_menue;

    @FXML
    private Button datenverwaltung_button_menue;

    @FXML
    private Button budgetverwaltung_menue;

    @FXML
    private Button neuerPosten_button_menue;
    
    @FXML
    private Button zurück_neuerPosten_button;

    @FXML
    private  ComboBox<String> kategoerieInput_neuerPosten = new ComboBox <String>   () ;
  /* public  void initialize() throws ClassNotFoundException, SQLException{
	   model.registerUser("b", "123");
   	   model.initiateDatabase("b", "123");
    	
    	kategoerieInput_neuerPosten.getItems().addAll(model.return_Kategorien());
    	kategorieInputCb.getItems().addAll(model.return_Kategorien());
    	System.out.println("init");
    }*/
   
   
    
   
    	
    

    

    @FXML
    private TextArea kommentarInput_neuerPosten;

    @FXML
    private ComboBox<String> dauerauftragInput_neuerposten = new ComboBox <String> ();
    

    @FXML
    private TextField postenInput_neuerPosten;

    @FXML
    private AnchorPane neuerPosten_pane;

    @FXML
    private ComboBox<?> subkategorieInput_neuerPosten;

    @FXML
    private TextField preisInput_neuerPosten;

    @FXML
    private TextField anzahlInput_neuerPosten;

    @FXML
    private Button übernehmen_button;

    @FXML
    void bA_neuerPosten_menue(ActionEvent event) throws IOException {
    	 Stage stage=(Stage)neuerPosten_button_menue.getScene().getWindow();
         restlicheViews.neuerPosten newPost = new restlicheViews.neuerPosten();
         newPost.setPrimaryStage(stage);
         newPost.startUp_neuerPosten();
         
         System.out.println(getUser());
     
         
         
         
    }

    @FXML
    void bA_ausgaben_menue(ActionEvent event) {

    }

    @FXML
    void bA_budgetverwaltung_menue(ActionEvent event) {

    }

    @FXML
    void bA_datenverwaltung_menue(ActionEvent event) throws IOException {
    	Stage stage=(Stage)datenverwaltung_button_menue.getScene().getWindow();
        restlicheViews.datenverwaltung dv = new restlicheViews.datenverwaltung();
        dv.setPrimaryStage(stage);
        dv.startUp_datenverwaltung();

    }

    @FXML
    void bA_optionen_menue(ActionEvent event) {

    }
    
    @FXML
    void bA_übernehmen(ActionEvent event) {

    }

    @FXML
    void bA_zurück_neuerPosten(ActionEvent event) {
  
    	

    }
    @FXML
    void cbc_dauerauftragWechsel_neuerPosten(ActionEvent event) {
    	dauerauftragInput_neuerposten.getItems().addAll("bla");

    }
    // ab hier folgt der Controller der datenverwaltung
    @FXML
    private Button kategorieLöschen_button_datenverwaltung;

    @FXML
    private Button zurück_button_datenverwaltung;

    @FXML
    private ComboBox<String> kategorieInputCb = new ComboBox <String> ();

    @FXML
    private Button subKategorieErstellen_button_datenverwaltung;

    @FXML
    private TextField subKategorieInput;

    @FXML
    private Button subKategorieLöschen_button_datenverwaltung;

    @FXML
    private Button kategorieErstellen_button_datenverwaltung;

    @FXML
    private TextField kategorieInput_datenverwaltung;

    @FXML
    void bA_kategorieLöschen_datenverwaltung(ActionEvent event) {

    }

    @FXML
    void bA_kategorieErstellen_datenverwaltung(ActionEvent event) throws ClassNotFoundException,SQLException {
    	
    	
    	model.insert_Kategorie(kategorieInput_datenverwaltung.getText());
    	//initialize();
    	kategorieInput_datenverwaltung.setText(null);

    }

    @FXML
    void bA_subKategorieErstellen_datenverwaltung(ActionEvent event) {

    }

    @FXML
    void bA_subKategorieLöschen_datenverwaltung(ActionEvent event) {

    }

    @FXML
    void bA_zurück_datenverwaltung(ActionEvent event) {

    }
    
    
    

}



