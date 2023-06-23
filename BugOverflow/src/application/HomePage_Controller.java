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
import javafx.scene.image.ImageView;

public class HomePage_Controller implements Initializable{

	@FXML
	private Button btn_logout;
	
	@FXML
	private Label lebel_welcome;
	
	@FXML
	private ImageView logo;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		btn_logout.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				DBUtils.changeScene(event, "/FXML/LoginPage.fxml", "Log IN", null);
			}
			
		});
	}

	public void setUserInfo(String username) {
		lebel_welcome.setText(username);
	}
}
