import java.io.IOException;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class alertController {
	
	@FXML
    private ImageView alertImage;

    @FXML
    private Label messageLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private HBox actionParent;

    @FXML
    private Button cancelButton;

    @FXML
    private HBox okParent;

    @FXML
    private Button okButton;
    
    private Stage thisStage;
    
    private Task<Void> task;
    

    @FXML
    void cancelButtonAction(ActionEvent event) {

    }
    

    @FXML
    void okButtonAction(ActionEvent event) throws IOException {
    	task.cancel();
    	thisStage.close();
    }
    
 
    private void setStage() {
    	thisStage = (Stage)  okButton.getScene().getWindow();
    	thisStage.setOnCloseRequest(new EventHandler<WindowEvent>() { //Da Thread ansonsten weiterlaufen würde
    		public void handle(WindowEvent we) {
    			task.cancel();
    		}
    		
    	});
    }
    
    
    void errorOcurred(Exception exc) {
    	setStage();
    	detailsLabel.setText(exc.getLocalizedMessage()); 	
    	task = new Task<Void> () {
			@Override
			protected Void call() throws Exception
			{
				for(double i = 0; i%361 < 361; ++i) 
				{
					double rnd = Math.random() * 360;
	    			MotionBlur mtb = new MotionBlur();
	    			alertImage.setEffect(mtb);
	    			mtb.setAngle(rnd);
	    			mtb.setRadius((i / 100) % 64 + 8.0);
					alertImage.setRotate(i%361);
					Thread.sleep(20);
	    			if(isCancelled())
	    			{
	    				break;
	    			}
				}
				return null;
			}
    	}; 
    	new Thread(task).start();
    }

}

/*	Alternativ über "normalen" Thread
thread2 = new Thread () {
public void run() {
	condition = true;
	while (condition) {
	double rnd = Math.random() * 360;
	MotionBlur mtb = new MotionBlur();
	alertImage.setEffect(mtb);
	mtb.setAngle(rnd);
	System.out.println(rnd);
	try {
		sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	}
}
}; */

//thread2.start(); 
