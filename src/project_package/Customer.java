package project_package;

public class Customer
{    
    private String UserName;
    private String Password;
    private int points;
    private String status;

    public Customer(String username, String password){
        this.UserName = username;
        this.Password = password;
        this.points = 0;
        this.status = "Silver";
    }
    
    public Customer(String username, String password, int points){
        this.UserName = username;
        this.Password = password;
        this.points = points;
        UpdateStatus();
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
    
    private void UpdateStatus() {
        if (this.points > 1000)
            status = "Gold"; //gold
        else 
            status = "Silver"; //silver
    }

    public String getUserName() {return UserName;}
    public void setUserName(String newUserName) {this.UserName = newUserName;}

    public String getPassword() {return Password;}
    public void setPassword(String newPassword) {this.Password = newPassword;}

    public int getPoints() {return points;}
    //public void setPoints(int points) {this.points = points;}

    public String getStatus() {return status;}
    //public void setStatus(String status) {this.status = status;}
    
    @Override
    public String toString(){
        return ("Customer={UserName: " + UserName + ", points: " + points + ", Status: " + status + "}");
    }
}