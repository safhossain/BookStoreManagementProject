package project_package;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Project_GUI extends Application
{
    public User owner = Owner.getInstance("admin", "admin");
    private CustomerList theCustomers;    
    private BookList theBooks;
    private TableView<Book> bookTable;
    private ObservableList<Book> bookListObservable;
            
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
        theBooks = new BookList();
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
        
        /******************************************************************* Owner-Book-Screen Scene *******************************************************************/
        //JavaFX-specific list implementation that allows listeners to track changes in the list
        //ObservableList<Book> bookListObservable = FXCollections.observableArrayList(theBooks.getBookComponents()); 
        bookListObservable = FXCollections.observableArrayList(theBooks.getBookComponents()); 
        
        VBox vBox_ownerBookScreen = new VBox(10); //root-node of owner_book_screen
        
        //Creating the book table//
        //TableView<Book> bookTable = new TableView<>();
        bookTable = new TableView<>();
        bookTable.setPrefSize(200, 400); 
        //Creating the book name column//
        TableColumn<Book, String> bookNameColumn = new TableColumn<>("Book Name");
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        
        //Creating the book price column//
        TableColumn<Book, Double> bookPriceColumn = new TableColumn<>("Book Price");
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        
        //Populating the table//
        bookTable.getColumns().addAll(bookNameColumn, bookPriceColumn);
        vBox_ownerBookScreen.getChildren().add(bookTable);
        
        bookTable.setItems(bookListObservable);
        
        //Prompts for creating a new book and adding it to the table//
        TextField newBookToAddTextField_name = new TextField();
        newBookToAddTextField_name.setPromptText("Name");        
        TextField newBookToAddTextField_price = new TextField();
        newBookToAddTextField_price.setPromptText("Price");
        Button newBookToAdd_addButton = new Button("Add");        
        HBox newBookToAddInputFields = new HBox(10);        
        newBookToAddInputFields.getChildren().addAll(newBookToAddTextField_name, newBookToAddTextField_price, newBookToAdd_addButton);
        
        vBox_ownerBookScreen.getChildren().add(newBookToAddInputFields);
        
        //Delete button for deleting a book from the table//                
        Button deleteButton = new Button("Delete");
        
        //Button for returning to the previous scene//
        Button backButtonOwner = new Button("Back");
        
        HBox BackAndDeleteButtons = new HBox(10);
        BackAndDeleteButtons.getChildren().addAll(deleteButton, backButtonOwner);
        vBox_ownerBookScreen.getChildren().add(BackAndDeleteButtons);
        
        Scene owner_book_screen = new Scene(vBox_ownerBookScreen, Color.BEIGE);
        
        /******************************************************************* Owner-Book-Screen Scene *******************************************************************/
        ObservableList<Customer> customerListObservable = FXCollections.observableArrayList(theCustomers.getCustomerList());
        
        VBox vBox_ownerCustomerScreen = new VBox(10); //root-node of owner_book_screen
        
        //Creating the customer table//
        TableView<Customer> customerTable = new TableView<>();        
        customerTable.setPrefSize(200, 400);
        
        //Creating the customer username column//
        TableColumn<Customer, String> customerNameColumn = new TableColumn<>("Username");
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        
        //Creating the customer password column//
        TableColumn<Customer, String> customerPasswordColumn = new TableColumn<>("Password");
        customerPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        //Creating the customer points column//
        TableColumn<Customer, Integer> customerPointsColumn = new TableColumn<>("Points");
        customerPointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        
        //Populating the table//
        customerTable.getColumns().addAll(customerNameColumn, customerPasswordColumn, customerPointsColumn);
        vBox_ownerCustomerScreen.getChildren().add(customerTable);
        
        customerTable.setItems(customerListObservable);
        
        //Prompts for creating a new user and adding it to the table//
        TextField newCustomerToAddTextField_username = new TextField();
        newCustomerToAddTextField_username.setPromptText("Username");        
        TextField newCustomerToAddTextField_password = new TextField();
        newCustomerToAddTextField_password.setPromptText("Password");
        Button newCustomerToAdd_addButton = new Button("Add");        
        HBox newCustomerToAddInputFields = new HBox(10);        
        newCustomerToAddInputFields.getChildren().addAll(newCustomerToAddTextField_username, newCustomerToAddTextField_password, newCustomerToAdd_addButton);
        
        vBox_ownerCustomerScreen.getChildren().add(newCustomerToAddInputFields);
        
        //Delete button for deleting a customer from the table//
        Button deleteButton2 = new Button("Delete");
        
        //Button for returning to the previous scene//
        Button backButtonOwner2 = new Button("Back");
        
        HBox BackAndDeleteButtons2 = new HBox(10);
        BackAndDeleteButtons2.getChildren().addAll(deleteButton2, backButtonOwner2);
        vBox_ownerCustomerScreen.getChildren().add(BackAndDeleteButtons2);
        
        Scene owner_customer_screen = new Scene(vBox_ownerCustomerScreen, Color.BEIGE);
      
        /******************************************** Stage Stuff ************************************************************/
        primaryStage.getIcons().add(book_png);
        primaryStage.setScene(login_screen);
        primaryStage.setTitle("Bookstore App");
        primaryStage.setWidth(700);
        primaryStage.setHeight(700); 
        primaryStage.show();        
        
        /********************************************************************* GUI FUNCTIONALITY *******************************************************************/
        loginBtn.setOnAction(loginEvent -> {
            processLogin(primaryStage, loginTextField, loginPasswordField, login_screen, owner_start_screen);
        }); 
        login_screen.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                processLogin(primaryStage, loginTextField, loginPasswordField, login_screen, owner_start_screen);
                event.consume();
            }
        });
        
        ownerScreen_logoutButton.setOnAction(logoutEvent -> {
            primaryStage.setScene(login_screen);
            loginTextField.clear();
            loginPasswordField.clear();
        });
        
        ownerScreen_viewBooksButton.setOnAction(booksPressEvent -> primaryStage.setScene(owner_book_screen));
        ownerScreen_viewCustomerButton.setOnAction(customersPressEvent -> primaryStage.setScene(owner_customer_screen));
        
        //Owner book table button functionalities//
        //Delete book button//
        deleteButton.setOnAction(deleteEvent -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                bookListObservable.remove(selectedBook);                
                theBooks.removeBook(selectedBook);                
                // Save the updated book list to the books text-file
                try {
                    theBooks.UpdateBookFile("books.txt");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        //Add book button//
        newBookToAdd_addButton.setOnAction(addEvent -> {
            String newBookName = newBookToAddTextField_name.getText();
            double newBookPrice;
            try {
                newBookPrice = Double.parseDouble(newBookToAddTextField_price.getText());
            } catch (NumberFormatException e) {
                System.out.println("Invalid price entered.");
                return;
            }

            Book newBook = new Book(newBookName, newBookPrice);
            theBooks.addBook(newBook);
            bookListObservable.add(newBook);
            try {
                    theBooks.UpdateBookFile("books.txt");
            } catch (IOException ex) {
                    ex.printStackTrace();
            }

            newBookToAddTextField_name.clear();
            newBookToAddTextField_price.clear();
        });
        
        //Return to previous scene button//
        backButtonOwner.setOnAction(backEvent -> {
            primaryStage.setScene(owner_start_screen);
        });
        
        //Owner customer table button functionalities///
        //Delete customer button//
        deleteButton2.setOnAction(deleteEvent -> {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                customerListObservable.remove(selectedCustomer);                
                theCustomers.removeCustomer(selectedCustomer);                
                // Save the updated book list to the books text-file
                try {
                    theCustomers.UpdateCustomerFile("customers.txt");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        //Add customer button//
        newCustomerToAdd_addButton.setOnAction(addEvent -> {
            String newUserName = newCustomerToAddTextField_username.getText();
            String newPassword = newCustomerToAddTextField_password.getText();

//            Customer newCustomer = new Customer(newUserName, newPassword);
//            newCustomer.UpdateStatus();
//            theCustomers.addCustomer(newCustomer);
//            customerListObservable.add(newCustomer);
//            try {
//                    theCustomers.UpdateCustomerFile("customers.txt");
//            } catch (IOException ex) {
//                    ex.printStackTrace();
//            }

            boolean temp = true;

            for (int i = 0; i < theCustomers.getCustomerListSize(); i++) {
                Customer existingCustomer = theCustomers.getCustomerList().get(i);
                if (newUserName.trim().equals(existingCustomer.getUserName())) {
                    System.out.println("Username already exists");
                    temp = false;
                }
                if (newPassword.length() < 5){
                    System.out.println("Password too short. Minimum 5 characters");
                    temp = false;
                }
                if (temp == false)
                    break;                
            }
            
            if (temp == true) {
                Customer newCustomer = new Customer(newUserName, newPassword);
                theCustomers.addCustomer(newCustomer);
                customerListObservable.add(newCustomer);
                try {
                        theCustomers.UpdateCustomerFile("customers.txt");
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
                newCustomerToAddTextField_username.clear();
                newCustomerToAddTextField_password.clear();
            }
            
        });
        
        //Return to previous scene button//
        backButtonOwner2.setOnAction(backEvent -> {
            primaryStage.setScene(owner_start_screen);
        });
    }
    
    /*********************************************************************** Start Method Above ********************************************************************************/
    
    private void processLogin(Stage primaryStage, TextField loginTextField, PasswordField loginPasswordField, Scene login_screen, Scene owner_start_screen) {
        String usernameInput = loginTextField.getText();
        String passwordInput = loginPasswordField.getText();

        User user = verifyUser(usernameInput, passwordInput);
        if (user != null) {
            if (user instanceof Owner) {
                primaryStage.setScene(owner_start_screen);
            } else if (user instanceof Customer) {
                Customer customerInstance = (Customer)user;                
                primaryStage.setScene(createCustomerStartScreen_Scene(customerInstance, primaryStage, login_screen));
            }
        } 
        else {System.out.println("Wrong username and/or password");}
    }
    
    void refreshOwnerBookTable() {
        bookTable.getItems().clear();
        bookListObservable = FXCollections.observableArrayList(theBooks.getBookComponents());
        bookTable.setItems(bookListObservable);
    }
    
    public Scene createCustomerStartScreen_Scene(Customer loggedOnCustomer, Stage primaryStage, Scene login_screen){        
        Customer customer = loggedOnCustomer;
        
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        
        Text customerScreenTitle = new Text("Welcome [name]. You have [P] points. Your status is [S]");
        customerScreenTitle.setText("Welcome " + customer.getUserName() + ". You have " + customer.getPoints() + " points. Your status is " + customer.getStatus());
        customerScreenTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));        
        layout.getChildren().add(customerScreenTitle);
        
        ObservableList<Book> bookListObservable = FXCollections.observableArrayList(theBooks.getBookComponents()); 
        
        //Creates the book table for customers//
        TableView<Book> customerBookTable = new TableView<>();
        
        //Creating and formatting the book name column//
        TableColumn<Book, String> bookNameColumn = new TableColumn<>("Book Name");
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        bookNameColumn.setMinWidth(150);
        bookNameColumn.setStyle("-fx-alignment: CENTER;");
        
        //Creating and formatting the book price column//
        TableColumn<Book, Double> bookPriceColumn = new TableColumn<>("Book Price");
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        bookPriceColumn.setStyle("-fx-alignment: CENTER;");
        
        //Creating and formatting the select column//
        TableColumn<Book, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        selectColumn.setEditable(true);
        
        //Gets rid of the empty space on the right side of the table//
        customerBookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //Populates the customer book table//
        customerBookTable.getColumns().addAll(bookNameColumn, bookPriceColumn, selectColumn);
        customerBookTable.setEditable(true);
        layout.getChildren().add(customerBookTable);
        
        customerBookTable.setItems(bookListObservable);
        
        HBox BuyAndRedeemButtons = new HBox(10);
        Button buyButton = new Button("Buy");
        Button redeemButton = new Button("Redeem with Points");
        BuyAndRedeemButtons.getChildren().addAll(buyButton, redeemButton);
        layout.getChildren().add(BuyAndRedeemButtons);
                
        Button customerLogOutButton = new Button("Logout");
        layout.getChildren().add(customerLogOutButton);
        
        Scene customer_start_screen = new Scene(layout, Color.BEIGE);
        
        buyButton.setOnAction(event -> {
            ArrayList<Book> selectedBooks = new ArrayList<>();
            double totalCost = 0;
            for (Book book : bookListObservable) {
                if (book.isSelected()) {
                    selectedBooks.add(book);
                    totalCost += book.getBookPrice();
                    theBooks.removeBook(book);
                    try {
                        theBooks.UpdateBookFile("books.txt");
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            refreshOwnerBookTable();
            
            Scene customer_cost_screen = createCustomerCostScreen_Scene(selectedBooks, totalCost, customer, primaryStage, login_screen);
            primaryStage.setScene(customer_cost_screen);
        });
        
        redeemButton.setOnAction(event -> {
            ArrayList<Book> selectedBooks = new ArrayList<>();
            double preDiscountCAD = 0;
            for (Book book : bookListObservable) {
                if (book.isSelected()) {
                    selectedBooks.add(book);
                    preDiscountCAD += book.getBookPrice();
                    theBooks.removeBook(book);
                    try {
                        
                        theBooks.UpdateBookFile("books.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            refreshOwnerBookTable();
            
            int pointsToCAD = customer.getPoints()/100;
            double costAfterPoints = preDiscountCAD;
            
            if (preDiscountCAD >= pointsToCAD){
                
                costAfterPoints = preDiscountCAD - pointsToCAD;
                customer.removePoints(customer.getPoints()); //all points used up in transaction
            }
            else{
                customer.removePoints(preDiscountCAD*100); //some points still remaining = preTransactionPoints - preDiscountCAD*10
                costAfterPoints = 0; //full cost handled by points
            }            
            
            Scene customer_cost_screen = createCustomerCostScreen_Scene(selectedBooks, costAfterPoints, customer, primaryStage, login_screen);
            primaryStage.setScene(customer_cost_screen);
        });
        customerLogOutButton.setOnAction(LogOut ->{
            primaryStage.setScene(login_screen);
        });
        return customer_start_screen;
    }

    private Scene createCustomerCostScreen_Scene(ArrayList<Book> selectedBooks, double totalCost, Customer customer, Stage primaryStage, Scene login_screen) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        
        Text totalCostText = new Text("Total Transaction Cost: $" + totalCost);
        totalCostText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        layout.getChildren().add(totalCostText);
        
        double earnedPoints = totalCost * 10;
        customer.addPoints(earnedPoints);
        try {
            theCustomers.UpdateCustomerFile("customers.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Text pointsEarned = new Text("Points Earned: " + earnedPoints);
        pointsEarned.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        layout.getChildren().add(pointsEarned);
        
        Text customerPointsAndStatusText = new Text("Current Points: " + customer.getPoints() + ", Status: " + customer.getStatus());
        customerPointsAndStatusText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        layout.getChildren().add(customerPointsAndStatusText);
        
        Button logOutBtnAfterTransaction = new Button("Logout");
        layout.getChildren().add(logOutBtnAfterTransaction);

        logOutBtnAfterTransaction.setOnAction(event -> {            
            primaryStage.setScene(login_screen);
        });
        
        Scene customer_cost_screen = new Scene(layout, Color.BEIGE);
        return customer_cost_screen;
    }
}
