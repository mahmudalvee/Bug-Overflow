package application;

import java.net.URL;
import java.util.ResourceBundle;

import DB.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login_Controller implements Initializable{
	
	@FXML
	private Button btn_login;
	
	@FXML
	private Button btn_signup;
	
	@FXML
	private TextField tf_useremail;

	@FXML
	private TextField tf_password;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		btn_login.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				DBUtils.loginUser(event, tf_useremail.getText(), tf_password.getText());
			}
			
		});
		
		btn_signup.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				DBUtils.changeScene(event, "/FXML/SignupPage.fxml", "Sign Up", null);
			}
			
		});
	}

}