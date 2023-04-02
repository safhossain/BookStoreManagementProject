package project_package;

public class Owner extends User //singleton-pattern design
{
    private static Owner instance;   
    //protected String userName;
    //protected String password;
    
    private Owner(String userName, String password){
        super(userName, password);
    }
    
    public static Owner getInstance(String userName, String newPassword){
        if (instance == null)
            instance = new Owner(userName, newPassword);
        return instance;
    }        

    public String getUserName() {return userName;}
    public void setUserName(String newOwnerUserName) {this.userName = newOwnerUserName;}

    public String getPassword() {return password;}
    public void setPassword(String newOwnerPassword) {this.password = newOwnerPassword;}
}