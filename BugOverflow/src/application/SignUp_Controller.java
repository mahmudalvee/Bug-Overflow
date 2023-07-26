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
import javafx.scene.control.PasswordField;
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
	private PasswordField tf_password1;
	
	private static final String EMAIL_PATTERN = 
    	    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    	    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		btn_signup.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				if(!tf_username.getText().trim().isEmpty() && !tf_useremail.getText().trim().isEmpty() && !tf_password1.getText().trim().isEmpty()) {
					
					if(!tf_useremail.getText().matches(EMAIL_PATTERN)) {
			    		Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("Invalid User-email");
						alert.setContentText("Enter a valid useremail");
						alert.showAndWait();
			    	}
			    	
			    	else if(tf_password1.getText().length() < 5) {
			    		Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("Invalid User Password");
						alert.setContentText("Password length must be 5 (minimum)");
						alert.showAndWait();
			    	}
			    	else {
						DBUtils.signupUser(event, tf_username.getText(), tf_useremail.getText(), tf_password1.getText());
			    	}
					
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
