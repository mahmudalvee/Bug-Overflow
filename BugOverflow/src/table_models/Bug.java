package table_models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bug {
	private final StringProperty useremail;
	private final StringProperty bug;
	private final StringProperty debug;
	
	
	public Bug(){
        useremail = new SimpleStringProperty(this, "useremail");
        bug = new SimpleStringProperty(this, "bug");
        debug = new SimpleStringProperty(this, "debug");
    }

	public StringProperty useremailProperty() { return useremail; }
    public String getUseremail() { return useremail.get(); }
    public void setUseremail(String newUseremail) { useremail.set(newUseremail); }
    
    public StringProperty bugProperty() { return bug; }
    public String getBug() { return bug.get(); }
    public void setBug(String newBug) { bug.set(newBug); }
    
    public StringProperty debugProperty() { return debug; }
    public String getDebug() { return debug.get(); }
    public void setDebug(String newDebug) { debug.set(newDebug); }
	
	
}
