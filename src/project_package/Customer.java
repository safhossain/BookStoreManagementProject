package project_package;

import javafx.beans.property.SimpleBooleanProperty;

public class Customer extends User
{    
    //private String userName;
    //private String password;
    private int points;
    private String status = "Silver";  
    private SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    public Customer(String username, String password){        
        super(username, password);
    }       
    
    public void addPoints(double transactionCost){
        int pointsToAdd = (int)Math.floor(transactionCost);
        this.points += pointsToAdd;
        UpdateStatus();
    }
    
    public void removePoints(double transactionCost){
        int pointsToAdd = (int)Math.floor(transactionCost);
        this.points -= pointsToAdd;
        UpdateStatus();
    }    
    
    public void UpdateStatus() {
        if (this.points > 1000)
            status = "Gold"; //gold
        else 
            status = "Silver"; //silver
    }

    public String getUserName() {return userName;}
    public void setUserName(String newUserName) {this.userName = newUserName;}
    
    public String getPassword() {return password;}
    public void setPassword(String newPassword) {this.password = newPassword;}

    public int getPoints() {return points;}
    public void setPoints(int points) {
        this.points = points;
        UpdateStatus();
    }

    public String getStatus() {return status;}
    //public void setStatus(String status) {this.status = status;}
    
    public boolean isSelected() {
        return selected.get();
    }
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }    
    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }
    
    @Override
    public String toString(){
        return ("Customer={UserName: " + userName + ", points: " + points + ", Status: " + status + "}");
    }
}
