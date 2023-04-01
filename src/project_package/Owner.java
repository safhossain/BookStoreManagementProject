package project_package;

public class Owner //singleton-pattern design
{
    private static Owner instance;
    private String ownerName;
    
    private Owner(String name){
        this.ownerName = name;
    }
    
    public static Owner getInstance(String name){
        if (instance == null)
            instance = new Owner(name);
        return instance;
    }
    
    public void setOwnerName(String name) {
        ownerName = name;
    }

    public String getOwnerName() {
        return ownerName;
    }
}