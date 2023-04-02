package project_package;

public abstract class User
{
    protected String userName;
    protected String password;
    
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }       
    
    public String getUserName() {return userName;}
    public void setUserName(String newUserName) {this.userName = newUserName;}
    
    public String getPassword() {return password;}
    public void setPassword(String newPassword) {this.password = newPassword;}
}
