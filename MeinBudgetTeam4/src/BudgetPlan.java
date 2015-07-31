

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BudgetPlan extends Application {
	public static void main(String[] args)  {
		launch();
		
	}

	@Override
	public void start(Stage primaryStage) {
		
		LoginView login = new LoginView();
		primaryStage.initStyle(StageStyle.UNIFIED);
		login.setPrimaryStage(primaryStage);
		login.startUp();
	}
	/**
	 * @see: <a href=https://docs.oracle.com/javafx/2/api/javafx/application/Application.html>Oracle Dokumentation</a>
	 */
	@Override
	public void stop() {
		//Hier könnte man Verbindungen schließen. Da H2 aber bereits einen Shutdown-Hook implementiert hat, sollte dies nicht nötig sein.
		System.exit(0); //Sollte nicht nötig sein aber: Wenn Application geschlossen wird, also stop() aufgerufen wird, soll JVM beendet werden, damit Shutdown-Hook von H2 ornungsgemäß aktiviert wird.
	}

}
