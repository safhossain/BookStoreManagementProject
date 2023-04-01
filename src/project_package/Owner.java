package project_package;

public class Owner //singleton-pattern design
{
    private static Owner instance;
    //private String ownerName;
    private String ownerUserName;
    private String ownerPassword;
    
    private Owner(String userName, String password){
        this.ownerUserName = userName;
        this.ownerPassword = password;
    }
    
    public static Owner getInstance(String userName, String password){
        if (instance == null)
            instance = new Owner(userName, password);
        return instance;
    }
    
    //public String getOwnerName() {return ownerName;}
    //public void setOwnerName(String newName) {ownerName = newName;}

    public String getOwnerUserName() {return ownerUserName;}
    public void setOwnerUserName(String newOwnerUserName) {this.ownerUserName = newOwnerUserName;}

    public String getOwnerPassword() {return ownerPassword;}
    public void setOwnerPassword(String newOwnerPassword) {this.ownerPassword = newOwnerPassword;}
}