package application;

import java.net.URL;
import java.util.ResourceBundle;

import DB.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUp_Controller implements Initializable{
	
	@FXML
	private Button btn_signup;

	@FXML
	private Button btn_login;
	
	@FXML
	private TextField tf_username;
	
	@FXML
	private TextField tf_useremail;
	
	@FXML
	private TextField tf_password;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		btn_signup.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				if(!tf_username.getText().trim().isEmpty() && !tf_useremail.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
					DBUtils.signupUser(event, tf_username.getText(), tf_useremail.getText(), tf_password.getText());
				}
				else {
					System.out.println("Please fill up all information correctly!");
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please fill up all information correctly!");
					alert.show();
				}
			}
			
		});
		
		btn_login.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				DBUtils.changeScene(event, "/FXML/LoginPage.fxml", "Log In", null);
			}
			
		});
		
	}

}
