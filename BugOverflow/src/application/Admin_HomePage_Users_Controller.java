package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
import table_models.User;

public class Admin_HomePage_Users_Controller  implements Initializable{

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
    private Button users_btn;

    @FXML
    private TableView<User> admin_users_table;

    @FXML
    private TableColumn<User, String> useremail_col;

    @FXML
    private TableColumn<User, String> username_col;

    @FXML
    private TextField txt_useremail;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_add;
    
    String clicked_useremail;
    private static final String EMAIL_PATTERN = 
    	    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    	    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public void table() {
    	DB_Connect();
    	
    	ObservableList<User> users = FXCollections.observableArrayList();
    	
    	try {
			ps = connection.prepareStatement("select useremail, username from users");
			ResultSet rs = ps.executeQuery();
	    	
	    	while(rs.next())
	    	{
	    		User user_obj = new User();
	    		
	    		user_obj.setUseremail(rs.getString("useremail"));
	    		user_obj.setusername(rs.getString("username"));
	    		users.add(user_obj);
	    	}
	    	
	    	admin_users_table.setItems(users);
	    	useremail_col.setCellValueFactory(f -> f.getValue().useremailProperty());
	    	username_col.setCellValueFactory(f -> f.getValue().usernameProperty());
	    	
	    	
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	admin_users_table.setRowFactory( tv -> {
            TableRow<User> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event -> 
            {
               if (event.getClickCount() == 1 && (!myRow.isEmpty()))
               {
                   myIndex =  admin_users_table.getSelectionModel().getSelectedIndex();
        
                   clicked_useremail = String.valueOf(admin_users_table.getItems().get(myIndex).getUseremail());
               }
            });
            
               return myRow;
            });
    	
    }
    
    @FXML
    void Add(ActionEvent event) {
    	String email, name, password;
    	
    	DB_Connect();
    	email = txt_useremail.getText();
    	name = txt_username.getText();
    	password = txt_password.getText();
    	
    	
    	if(email.length() == 0) {
	     		Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText("Failed to Add User");
	            alert.setContentText("Fill-up the required fields to ADD User");
	            alert.showAndWait();
	     }
    	
    	else if(!email.matches(EMAIL_PATTERN)) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Invalid User-email");
			alert.setContentText("Enter a valid useremail");
			alert.showAndWait();
    	}
    	
    	else if(password.length() < 5) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Invalid User Password");
			alert.setContentText("Password length must be 5 (minimum)");
			alert.showAndWait();
    	}
    		
    	else
    		{
    		try {
        		
    			ps = connection.prepareStatement("Insert into users(useremail, username, password) values (?,?,?)");
    			ps.setString(1, email);
    			ps.setString(2, name);
    			ps.setString(3, password);
    			ps.executeUpdate();
    			
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setHeaderText("Add a new User!");
    			alert.setContentText("USER CREATED SUCCESSFULLY!");
    			alert.showAndWait();
    			
    			table();
    		}
    		
    		catch (SQLIntegrityConstraintViolationException e) {
    			e.printStackTrace();
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setHeaderText("User-email already exists");
    			alert.setContentText("Enter a new User-email to add User");
    			alert.showAndWait();
    		}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    }

    
    @FXML
    void Delete(ActionEvent event) {
        myIndex = admin_users_table.getSelectionModel().getSelectedIndex();
        System.out.println(myIndex);
        
        try 
        {
        	if(myIndex == -1) {
	     		Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText("Failed to Delete User");
	            alert.setContentText("Select a row to to Delete User");
	            alert.showAndWait();
	     	}
        	else
        	{
        		ps = connection.prepareStatement("delete from users where useremail = ? ");
                ps.setString(1, clicked_useremail);
                ps.executeUpdate();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
                alert.setHeaderText("Delete Bug");
                alert.setContentText("Deleted Successfully!");
                alert.showAndWait();
                table();
        	}
        	
        } 
        catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		bugs_btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {	
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				DBUtils.changeScene(event, "/FXML/Admin_HomePage.fxml", "Bug-Overflow Admin Dashboard", null);
			}
			
		});
	}
	
	public void setUserInfo(String useremail) {
		//lebel_welcome.setText(useremail);
	}
	
}
