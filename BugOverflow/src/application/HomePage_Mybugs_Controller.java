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
import javafx.scene.control.TextArea;
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

public class HomePage_Mybugs_Controller implements Initializable{
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

    @FXML
    private TextArea txt_bug;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_add;
    
    String getUseremail;
    String clicked_bug;
    
    
    public void table() {
    	DB_Connect();
    	
    	ObservableList<Bug> bugs = FXCollections.observableArrayList();
    	
    	try {
    		ps = connection.prepareStatement("select useremail, bug, debug from bugs WHERE useremail = ? ORDER BY id DESC");
			ps.setString(1, getUseremail);
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
    	
    	admin_bugs_table.setRowFactory( tv -> {
            TableRow<Bug> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event -> 
            {
               if (event.getClickCount() == 1 && (!myRow.isEmpty()))
               {
                   myIndex =  admin_bugs_table.getSelectionModel().getSelectedIndex();
        
                   txt_bug.setText(admin_bugs_table.getItems().get(myIndex).getBug());
                   clicked_bug = txt_bug.getText();
               }
            });
            
               return myRow;
            });
    	
    }
    
    @FXML
    void Add(ActionEvent event) {
    	String email, bug, debug;
    	
    	DB_Connect();
    	email = String.valueOf(getUseremail);;
    	bug = txt_bug.getText();
    	debug = "No Debug/Answer available yet";
    	
    	try {
    		
    		if(bug.length() == 0) {
	     		Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText("Failed to Add Bug!");
	            alert.setContentText("Select a row to ADD Bug");
	            alert.showAndWait();
	     	}
    		
    		else
    		{
    			ps = connection.prepareStatement("Insert into bugs(useremail, bug, debug)values(?,?,?)");
    			ps.setString(1, email);
    			ps.setString(2, bug);
    			ps.setString(3, debug);
    			ps.executeUpdate();
    			
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setHeaderText("Add a new BUG!");
    			alert.setContentText("BUG SUCCESSFULLY ADDED! Debug will be updated by Admin");
    			alert.showAndWait();
    			
    			table();
    		}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    

    @FXML
    void Update(ActionEvent event) {
       
    	String bug;
        
         myIndex = admin_bugs_table.getSelectionModel().getSelectedIndex();
           
     	bug = txt_bug.getText();
        try
        {
        	if(bug.length() == 0) {
	     		Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText("Failed to Update Bug!");
	            alert.setContentText("Select a row to Update Bug");
	            alert.showAndWait();
	     	}
        	else {
        		ps = connection.prepareStatement("update bugs set bug = ? where bug = ? ");
    			ps.setString(1, bug);
    			ps.setString(2, clicked_bug);
    			ps.executeUpdate();
    			
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
                alert.setHeaderText("Update Bug");
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();
                table();
        	}
            
        }
        catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void Delete(ActionEvent event) {
        myIndex = admin_bugs_table.getSelectionModel().getSelectedIndex();
        
        String bug = txt_bug.getText();
        
        try 
        {
        	if(bug.length() == 0) {
	     		Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText("Failed to Delete Bug!");
	            alert.setContentText("Select a row to Delete Bug");
	            alert.showAndWait();
	     	}
        	
        	else {
        		ps = connection.prepareStatement("delete from bugs where bug = ? ");
                ps.setString(1, bug);
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
    
    
	public void setUserInfo(String useremail) {
		getUseremail = String.valueOf(useremail);
		lebel_welcome.setText(useremail);
		
		table();
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
				
				DBUtils.changeScene(event, "/FXML/HomePage.fxml", "All Bugs", getUseremail);
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
