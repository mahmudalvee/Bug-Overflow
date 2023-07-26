package table_models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	private final StringProperty useremail;
	private final StringProperty username;
	private final StringProperty password;
	
	
	public User(){
        useremail = new SimpleStringProperty(this, "useremail");
        username = new SimpleStringProperty(this, "username");
        password = new SimpleStringProperty(this, "password");
    }

	public StringProperty useremailProperty() { return useremail; }
    public String getUseremail() { return useremail.get(); }
    public void setUseremail(String newUseremail) { useremail.set(newUseremail); }
    
    public StringProperty usernameProperty() { return username; }
    public String getusername() { return username.get(); }
    public void setusername(String newusername) { username.set(newusername); }
    
    public StringProperty passwordProperty() { return password; }
    public String getpassword() { return password.get(); }
    public void setpassword(String newpassword) { password.set(newpassword); }
	
	
}
