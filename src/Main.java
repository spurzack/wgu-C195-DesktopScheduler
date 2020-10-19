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

//        Notes
//        Connection conn = SQLDatabase.getConnection();
//        SQLQuery.setStatement(conn);
//        Statement statement = SQLQuery.getStatement();
//
//        String country = "Canada";
//        String createDate = "2020-05-16 00:00:00";
//        String createdBy = "Zack";
//        String lastUpdateBy = "Zack";
//
//        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy)" +
//                    "VALUES(" +
//                "'" + country +"'," +
//                "'" + createDate +"'," +
//                "'" + createdBy + "'," +
//                "'" + lastUpdateBy + "'"
//                        +")";
//
//        statement.execute(insertStatement);
//
//        //Get Country from Input
//        Scanner keyboard = new Scanner(System.in);
//        System.out.print("Which Country would you like to pull up: ");
//        String country = keyboard.nextLine();
//
//        String selectStatement = "SELECT * FROM country WHERE " + country;
//
//        try {
//            statement.execute(selectStatement);
//            ResultSet rs = statement.getResultSet();
//
//            while (rs.next()) {
//                int countryID = rs.getInt("countryId");
//                String countryName = rs.getString("country");
//                LocalDate date = rs.getDate("createDate").toLocalDate();
//                LocalTime time = rs.getTime("createDate").toLocalTime();
//                String createdBy = rs.getString("createdBy");
//                LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();
//
//                System.out.println(countryID + " | " + countryName + " | " + date + " | " + time + " | " + createdBy + " | " + lastUpdate);
//            }
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        if(statement.getUpdateCount() > 0)
//            System.out.println(statement.getUpdateCount() + " row(s) affected");