package DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Admin_HomePage_Controller;
import application.HomePage_Controller;
import application.HomePage_Mybugs_Controller;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;

public class DBUtils {
	
	public static void changeScene(ActionEvent event, String fxmlfile, String title, String useremail) {

		Parent root = null;
		FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlfile));
		
		if(useremail != null) {
			System.out.println("Useremail: "+useremail);
			try {
				if(useremail.equals("admin@gmail.com")) {
					root = loader.load();
					Admin_HomePage_Controller adminhomepagecontroller = loader.getController();
					adminhomepagecontroller.setUserInfo(useremail);
				}
				
				else if(fxmlfile.equals("/FXML/HomePage_Mybugs.fxml")) {
					root = loader.load();
					HomePage_Mybugs_Controller homepagemybugs_controller = loader.getController();
					homepagemybugs_controller.setUserInfo(useremail);
				}
				
				else {
					root = loader.load();
					HomePage_Controller homepagecontroller = loader.getController();
					homepagecontroller.setUserInfo(useremail);
				}

			} catch(ClassCastException e) {
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		else {
			try {
				root = FXMLLoader.load(DBUtils.class.getResource(fxmlfile));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root, 600, 400));
		stage.show();
	}
	
	public static void signupUser(ActionEvent event, String username, String useremail, String password) {
		Connection connection = null;
		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultset = null;
    	
    	try {
    		
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bug-quest", "root", "Realmadrid14");
			psCheckUserExists = connection.prepareStatement("Select * from users WHERE useremail = ?");
			psCheckUserExists.setString(1, useremail);
			resultset = psCheckUserExists.executeQuery();
			
			if(resultset.isBeforeFirst()) {
				System.out.println("User already exists!");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("User already exists! Choose another User-email");
				alert.show();
			}
			
			else {
				psInsert = connection.prepareStatement("Insert INTO users (username, useremail, password) VALUES (?, ?, ?)");
				psInsert.setString(1, username);
				psInsert.setString(2, useremail);
				psInsert.setString(3, password);
				psInsert.executeUpdate();
				
				changeScene(event, "/FXML/HomePage.fxml", "Welcome to Bug-Overflow!", useremail);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(resultset != null) {
				try {
					resultset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(psCheckUserExists != null) {
				try {
					psCheckUserExists.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(psInsert != null) {
				try {
					psInsert.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void loginUser(ActionEvent event, String useremail, String password) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bug-quest", "root", "Realmadrid14");
		
			preparedStatement = connection.prepareStatement("SELECT username, password FROM users WHERE useremail = ?");
			preparedStatement.setString(1, useremail);
			resultset = preparedStatement.executeQuery();
		
			if(!resultset.isBeforeFirst()) {
				System.out.println("Useremail not found in database!");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Invalid user e-mail! Re-enter correct information");
				alert.show();
			}
			else {
				while (resultset.next()) {
					String retrivedPassword = resultset.getString("password");

					if(retrivedPassword.equals(password)) {
						if(useremail.equals("admin@gmail.com")) {
							changeScene(event, "/FXML/Admin_HomePage.fxml", "Bug-Overflow Admin Dashboard", useremail);
						}
						
						else {
							changeScene(event, "/FXML/HomePage.fxml", "Welcome to Bug-Overflow!", useremail);
						}
					}
					
					else {
						System.out.println("Password did not match!");
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Password did not match!");
						alert.show();
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(resultset != null) {
				try {
					resultset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
