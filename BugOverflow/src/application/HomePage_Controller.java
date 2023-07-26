package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DB.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import table_models.Bug;
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

	Connection connection;
    PreparedStatement ps;
    int myIndex, id;
    
    @FXML
    private Label lebel_welcome;

    @FXML
    private ImageView logo;

    @FXML
    private Button btn_logout;

    @FXML
    private Button bugs_btn;

    @FXML
    private Button mybugs_btn;

    @FXML
    private Button about_btn;

    @FXML
    private TableView<Bug> admin_bugs_table;

    @FXML
    private TableColumn<Bug, String> useremail_col;

    @FXML
    private TableColumn<Bug, String> bug_col;

    @FXML
    private TableColumn<Bug, String> debug_col;
    
    
    public void table() {
    	DB_Connect();
    	
    	ObservableList<Bug> bugs = FXCollections.observableArrayList();
    	
    	try {
			ps = connection.prepareStatement("select useremail, bug, debug from bugs ORDER BY id DESC");
			ResultSet rs = ps.executeQuery();
	    	
	    	while(rs.next())
	    	{
	    		Bug bug_obj = new Bug();
	    		
	    		bug_obj.setUseremail(rs.getString("useremail"));
	    		bug_obj.setBug(rs.getString("bug"));
	    		bug_obj.setDebug(rs.getString("debug"));
	    		bugs.add(bug_obj);
	    	}
	    	
	    	admin_bugs_table.setItems(bugs);
	    	useremail_col.setCellValueFactory(f -> f.getValue().useremailProperty());
	    	bug_col.setCellValueFactory(f -> f.getValue().bugProperty());
            debug_col.setCellValueFactory(f -> f.getValue().debugProperty());
	    	
	    	
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    String getUseremail;
	public void setUserInfo(String useremail) {
		getUseremail = String.valueOf(useremail);
		lebel_welcome.setText(useremail);
	}
	
	
    public void DB_Connect() {
    	try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bug-quest", "root", "Realmadrid14");
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		DB_Connect();
		System.out.println("Bugs connected");
		table();
		
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
		
		about_btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				DBUtils.changeScene(event, "/FXML/AboutUsPage.fxml", "About Bug-Overflow", getUseremail);
			}
			
		});
	}
	
}
