package project_package;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class CustomerList
{
    private ArrayList<Customer> customerList = new ArrayList<Customer>();
    
    public void addCustomer(Customer newCustomer) {
        customerList.add(newCustomer);
    }
    public void addCustomer(String name, String password){
        Customer newCustomer = new Customer(name, password);
    }
    
    public void removeCustomer(int i){
        customerList.remove(i);
    }
    public void removeCustomer(Customer customerToRemove){
        customerList.remove(customerToRemove);
    }
    
    public String getCustomerInfo(int j){
        return customerList.get(j).toString();
    }
    public String getCustomerUsername(int j){
        return customerList.get(j).getUserName();
    }
    public int getCustomerPoints(int j){
        return customerList.get(j).getPoints();
    }
    public String getCustomerStatus(int j){
        return customerList.get(j).getStatus();
    }
    
    public int getCustomerListSize(){
        return customerList.size();
    }
    
    @Override
    public String toString(){
        String str = "";
        for (int i = 0; i < customerList.size(); i++) {
            str += customerList.get(i).toString()+"\n";
        }
        return str;
    }
    
    public void LoadCustomersFromFile(String fileName)throws FileNotFoundException, IOException{
        File customerFile = new File(fileName);
        FileReader fr = new FileReader(customerFile);        
        
        try(BufferedReader br = new BufferedReader(fr)){
            String line;
            while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t", 3); // Split using the tab delimiter
            String username = parts[0];
            String password = parts[1];
            int points = Integer.parseInt(parts[2]);
            Customer newCustomer = new Customer(username, password, points);
            addCustomer(newCustomer);
            } 
        } 
        catch(IOException e){
            e.printStackTrace();
        } 
    }
    
    public void UpdateCustomerFile(String fileName) throws FileNotFoundException, IOException{
        File customerFile = new File(fileName);
        FileWriter fw = new FileWriter(customerFile);
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            for (Customer customer : customerList) {                
                String line = customer.getUserName() + "\t" + customer.getPassword() + "\t" + customer.getPoints();
                bw.write(line);
                bw.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } 
    }    
    
    public static void main(String[] args) throws IOException {
        CustomerList allCustomers = new CustomerList();
        allCustomers.LoadCustomersFromFile("customers.txt");
        System.out.println(allCustomers.toString());
    }    
}