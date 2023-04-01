package project_package;

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
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        /*
        * Heirarchy: Stage (1-->*) 
                        Scene (1-->*) 
                            StackPane
        */       
        
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
        
        Text scenetitle = new Text("Welcome to the BookStore App");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid_loginScreen.add(scenetitle, 0, 1, 3, 1); //(c0, r1), spans 2c/1r

        Label userName = new Label("Username:");
        grid_loginScreen.add(userName, 0, 2); //(c0,r2)

        TextField userTextField = new TextField();
        grid_loginScreen.add(userTextField, 1, 2); //(c1,r2)

        Label pw = new Label("Password:");
        grid_loginScreen.add(pw, 0, 3); //(c0, r3)
        PasswordField pwBox = new PasswordField();
        grid_loginScreen.add(pwBox, 1, 3); //(c1,r3)
        
        Button btn = new Button("Login");
        grid_loginScreen.add(btn, 1, 4); //(c1,r4)
        //GridPane.setHgrow(btn, Priority.ALWAYS);
        //btn.setMaxWidth(Double.MAX_VALUE);        
        
        Scene login_screen = new Scene(grid_loginScreen, Color.BEIGE);
        /********************************************************************************************************/
        
        /******************************************************************* Owner-Screen Scene *******************************************************************/
        GridPane grid_ownerScreen = new GridPane();
        
        grid_ownerScreen.setAlignment(Pos.CENTER);        
        grid_ownerScreen.setVgap(20);
        
        Button ownerScreen_btn1 = new Button("Books");
        ownerScreen_btn1.setPrefSize(150, 40);
        grid_ownerScreen.add(ownerScreen_btn1, 0, 0);
        
        Button ownerScreen_btn2 = new Button("Customer");
        ownerScreen_btn2.setPrefSize(150, 40);
        grid_ownerScreen.add(ownerScreen_btn2, 0, 1);
        
        Button ownerScreen_btn3 = new Button("Logout");
        ownerScreen_btn3.setPrefSize(150, 40);
        grid_ownerScreen.add(ownerScreen_btn3, 0, 2);       
        
        
        Scene owner_start_screen = new Scene(grid_ownerScreen, Color.BEIGE);
        /********************************************************************************************************/
        
        
        
        
        //Stage Stuff        
        primaryStage.getIcons().add(book_png);
        primaryStage.setScene(owner_start_screen);
        primaryStage.setTitle("Bookstore App");
        primaryStage.setWidth(700);
        primaryStage.setHeight(500);        
        primaryStage.show();
    }
    
}
