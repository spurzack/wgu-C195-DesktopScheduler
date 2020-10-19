package View_Controller;

import Model.Customer;
import Model.CustomerDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditCustomersController implements Initializable {
    @FXML TextField Name;
    @FXML TextField Phone;
    @FXML TextField Street;
    @FXML ComboBox City;
    @FXML TextField Zip;
    @FXML TextField Country;

    public Customer selectedCustomer;

    private ObservableList<String> cities = FXCollections.observableArrayList(
            "Phoenix","New York","London");

    public void onCitySelected() {
        Integer currentCity = City.getSelectionModel().getSelectedIndex();
        if(currentCity == 0 || currentCity == 1){
            Country.setText("United States");
        }
        else{
            Country.setText("England");
        }
    }


    public void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = null;
        try {
            scene = FXMLLoader.load(getClass().getResource("ViewAppointments.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    public void handleUpdateButton(ActionEvent event) throws IOException {
        String addName = Name.getText();
        String addPhone = Phone.getText();
        String addStreet = Street.getText();
        String addCity = City.getSelectionModel().toString();
        String addZip = Zip.getText();
        Integer customerID = selectedCustomer.getCustomerID();


        if (CustomerDB.validateCustomer(addName, addPhone, addStreet, addCity, addZip) == Boolean.TRUE) ;
        {
            CustomerDB.editCustomer(addName, addPhone, addStreet, addCity, addZip, customerID);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("ViewAppointments.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        City.setItems(cities);

    }

    public void setCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        Name.setText(selectedCustomer.getCustomerName());
        Phone.setText(selectedCustomer.getCustomerPhone());
        Street.setText(selectedCustomer.getCustomerAddress());
        Zip.setText(selectedCustomer.getCustomerZip());
    }
}
