package project_package;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Project_GUI extends Application
{
    public Owner owner = Owner.getInstance("admin", "admin");
    private CustomerList theCustomers;    
    private BookStore theBooks;
            
    public static void main(String[] args){
        launch(args);
    }
    
    private User verifyUser(String userName, String password) {
        //check if user is owner
        if (userName.equals(owner.getUserName()) && password.equals(owner.getPassword())) {
            return owner;
        }
        // Check if the user is a customer
        for (int i = 0; i < theCustomers.getCustomerListSize(); i++) {
            Customer existingCustomer = theCustomers.getCustomerList().get(i);
            if (userName.equals(existingCustomer.getUserName()) && password.equals(existingCustomer.getPassword())) {
                return existingCustomer;
            }
        }
        // No matching user found
        return null;
    }

    @Override
    public void start(Stage primaryStage){
        
        /**************************** initial loading of the customer and books info *********************************/
        theCustomers = new CustomerList();
        theBooks = new BookStore();
        try {
            theCustomers.LoadCustomersFromFile("customers.txt");
            theBooks.LoadBooksFromFile("books.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(theCustomers.toString());
        System.out.println(theBooks.toString());
        
        /************************************* Login-Screen Scene *******************************************************************/
        GridPane grid_loginScreen = new GridPane();
        
        grid_loginScreen.setAlignment(Pos.CENTER); //places grid at center of scene
        grid_loginScreen.setHgap(10); //padding between columns
        grid_loginScreen.setVgap(10); //padding between rows 
        
        Image book_png = new Image("book.png");
        ImageView book_pngImageView = new ImageView(book_png);
        book_pngImageView.setFitWidth(50);
        book_pngImageView.setFitHeight(50);        
        grid_loginScreen.add(book_pngImageView, 0, 0);
        
        Text loginScreenTitle = new Text("Welcome to the BookStore App");
        loginScreenTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid_loginScreen.add(loginScreenTitle, 0, 1, 3, 1); //(c0, r1), spans 2c/1r

        Label userNameLabel = new Label("Username:");
        grid_loginScreen.add(userNameLabel, 0, 2); //(c0,r2)
        TextField loginTextField = new TextField();
        grid_loginScreen.add(loginTextField, 1, 2); //(c1,r2)

        Label passwordLabel = new Label("Password:");
        grid_loginScreen.add(passwordLabel, 0, 3); //(c0, r3)
        PasswordField loginPasswordField = new PasswordField();
        grid_loginScreen.add(loginPasswordField, 1, 3); //(c1,r3)
        
        Button loginBtn = new Button("Login");
        grid_loginScreen.add(loginBtn, 1, 4); //(c1,r4)        
        
        Scene login_screen = new Scene(grid_loginScreen, Color.BEIGE);
        /********************************************************************************************************/
        
        /******************************************************************* Owner-Screen Scene *******************************************************************/
        GridPane grid_ownerScreen = new GridPane();
        
        grid_ownerScreen.setAlignment(Pos.CENTER);        
        grid_ownerScreen.setVgap(20);
        
        Button ownerScreen_viewBooksButton = new Button("Books");
        ownerScreen_viewBooksButton.setPrefSize(150, 40);
        grid_ownerScreen.add(ownerScreen_viewBooksButton, 0, 0);
        
        Button ownerScreen_viewCustomerButton = new Button("Customer");
        ownerScreen_viewCustomerButton.setPrefSize(150, 40);
        grid_ownerScreen.add(ownerScreen_viewCustomerButton, 0, 1);
        
        Button ownerScreen_logoutButton = new Button("Logout");
        ownerScreen_logoutButton.setPrefSize(150, 40);
        grid_ownerScreen.add(ownerScreen_logoutButton, 0, 2);       
                
        Scene owner_start_screen = new Scene(grid_ownerScreen, Color.BEIGE);
        
        
        /******************************************************************* Customer-Screen Scene *******************************************************************/
        GridPane grid_customerScreen = new GridPane();
        
        grid_customerScreen.setAlignment(Pos.CENTER);        
        grid_customerScreen.setVgap(20);
                
        Text customerScreenTitle = new Text("Welcome [name]. You have [P] points. Your status is [S]");
        customerScreenTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid_customerScreen.add(customerScreenTitle, 0, 0, 2, 1);
        
        
        
        Scene customer_start_screen = new Scene(grid_customerScreen, Color.BEIGE);
        
        
        
        /******************************************** Stage Stuff ************************************************************/
        primaryStage.getIcons().add(book_png);
        primaryStage.setScene(login_screen);
        primaryStage.setTitle("Bookstore App");
        primaryStage.setWidth(700);
        primaryStage.setHeight(500);        
        primaryStage.show();
        
        
        /********************************************************************* GUI FUNCTIONALITY *******************************************************************/
        loginBtn.setOnAction(loginEvent -> {
            processLogin(primaryStage, loginTextField, loginPasswordField, owner_start_screen, customer_start_screen, customerScreenTitle);
        }); 
        login_screen.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                processLogin(primaryStage, loginTextField, loginPasswordField, owner_start_screen, customer_start_screen, customerScreenTitle);
                event.consume();
            }
        });
        
        ownerScreen_logoutButton.setOnAction(logoutEvent -> {
            primaryStage.setScene(login_screen);
        });
    }
    
    private void processLogin(Stage primaryStage, TextField loginTextField, PasswordField loginPasswordField, Scene owner_start_screen, Scene customer_start_screen, Text customerScreenTitle) {
        String usernameInput = loginTextField.getText();
        String passwordInput = loginPasswordField.getText();

        User user = verifyUser(usernameInput, passwordInput);
        if (user != null) {
            if (user instanceof Owner) {
                primaryStage.setScene(owner_start_screen);
            } else if (user instanceof Customer) {
                Customer customer = (Customer) user;
                customerScreenTitle.setText("Welcome " + customer.getUserName() + ". You have " + customer.getPoints() + " points. Your status is " + customer.getStatus());
                primaryStage.setScene(customer_start_screen);
            }
        } 
        else {System.out.println("Wrong username and/or password");}
    }
}
