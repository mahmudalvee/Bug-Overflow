package DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.HomePage_Controller;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;

public class DBUtils {
	
	public static void changeScene(ActionEvent event, String fxmlfile, String title, String username) {
		
		Parent root = null;
		
		if(username != null) {
			try {
				FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlfile));
				root = loader.load();
				HomePage_Controller homepagecontroller = loader.getController();
				homepagecontroller.setUserInfo(username);
			} catch(IOException e) {
				e.printStackTrace();
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
			psCheckUserExists = connection.prepareStatement("Select * from users WHERE username = ?");
			psCheckUserExists.setString(1, username);
			resultset = psCheckUserExists.executeQuery();
			
			if(resultset.isBeforeFirst()) {
				System.out.println("User already exists!");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("User already exists! Choose another username");
				alert.show();
			}
			
			else {
				psInsert = connection.prepareStatement("Insert INTO users (username, useremail, password) VALUES (?, ?, ?)");
				psInsert.setString(1, username);
				psInsert.setString(2, useremail);
				psInsert.setString(3, password);
				psInsert.executeUpdate();
				
				changeScene(event, "/FXML/HomePage.fxml", "Welcome to Bug-Overflow!", username);
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
				alert.setContentText("User does not exist by this email!");
				alert.show();
			}
			else {
				while (resultset.next()) {
					String retrivedPassword = resultset.getString("password");
					String retrivedUsername = resultset.getString("username");

					if(retrivedPassword.equals(password)) {
						changeScene(event, "/FXML/HomePage.fxml", "Welcome to Bug-Overflow!", retrivedUsername);
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
