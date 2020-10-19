import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Alerts;
import utils.SQLDatabase;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("View_Controller/Login.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Calendar System - Login");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) throws SQLException {
        SQLDatabase.connect();



//        //Manually Change Default Language
//        Scanner keyboard = new Scanner(System.in);
//        System.out.print("Enter a language code: es or de: ");
//        String languageCode = keyboard.nextLine();
//
//        //Test for Language
//        if(languageCode.equals("de"))
//                Locale.setDefault(Locale.GERMANY);
//        else if(languageCode.equals("es"))
//            Locale.setDefault(Locale.US);
//        else{
//                System.out.println("Language not supported");
//                System.exit(0);
//        }


        //Load Language Bundle
        ResourceBundle rb = ResourceBundle.getBundle("resources/Languages", Locale.getDefault());
        if (Locale.getDefault() == Locale.GERMANY) {
                System.out.println(rb.getString("hello") + " " + rb.getString("world"));
                }
        else System.out.println("hello world");

        launch(args);

        SQLDatabase.disconnect();
    }
}
