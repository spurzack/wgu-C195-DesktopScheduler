package View_Controller;

import Model.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Alerts;
import utils.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML TextField username;
    @FXML TextField password;
    @FXML Label labelUN;
    @FXML Label labelPW;
    @FXML Button buttonLogin;
    @FXML Button buttonExit;

    private String alertTitle;
    private String alertHeader;
    private String alertContext;

    public void loginPushed(ActionEvent event) throws IOException {
        String usernameI = username.getText();
        String passwordI = password.getText();
        boolean validUser = UserDB.login(usernameI, passwordI);
        if(validUser == true){
            // Launch Main Screen
            Logger.log(usernameI, true, "Login Attempt");

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("ViewAppointments.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(alertTitle);
            alert.setHeaderText(alertHeader);
            alert.setContentText(alertContext);
            alert.showAndWait();
        }
    }

    @FXML public void exitButton(ActionEvent event) throws IOException {
        Alerts.confirmDialog("Exit", "Are you sure you would like to exit the program?");
        {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("resources/Languages", locale);
        labelUN.setText(rb.getString("Username"));
        labelPW.setText(rb.getString("Password"));
        buttonLogin.setText(rb.getString("login"));
        alertTitle = rb.getString("alertTitle");
        alertHeader = rb.getString("alertHeader");
        alertContext = rb.getString("alertContext");
        buttonExit.setText(rb.getString("Exit"));

    }
}
