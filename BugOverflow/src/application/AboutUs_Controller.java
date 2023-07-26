package application;

import java.net.URL;
import java.util.ResourceBundle;

import DB.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AboutUs_Controller implements Initializable{

    @FXML
    private Label lebel_welcome;

    @FXML
    private Button btn_logout;

    @FXML
    private Button bugs_btn;

    @FXML
    private Button mybugs_btn;

    @FXML
    private Button about_btn;
    
    @FXML
    private ImageView logo1;
    
    String getUseremail;
	public void setUserInfo(String useremail) {
		getUseremail = String.valueOf(useremail);
		lebel_welcome.setText(useremail);
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		btn_logout.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				DBUtils.changeScene(event, "/FXML/LoginPage.fxml", "Log IN", null);
			}
			
		});
		
		mybugs_btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				DBUtils.changeScene(event, "/FXML/HomePage_Mybugs.fxml", "My Bugs", getUseremail);
			}
			
		});
		
		bugs_btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				DBUtils.changeScene(event, "/FXML/HomePage.fxml", "All Bugs", getUseremail);
			}
			
		});
	}

}