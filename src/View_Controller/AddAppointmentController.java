package View_Controller;

import Model.Appointment;
import Model.AppointmentDB;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Alerts;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddAppointmentController {
    public Customer selectedCustomer;
    @FXML private TextField CustomerField;
    @FXML private ComboBox Time;
    @FXML private ComboBox Contact;
    @FXML private DatePicker Date;
    @FXML private ComboBox Locations;
    @FXML private TextField ApptType;

    private final ObservableList<String> contacts = FXCollections.observableArrayList("Ina", "John", "Zack");
    private final ObservableList<String> times = FXCollections.observableArrayList("08:00", "09:30", "11:00", "12:30", "14:00", "16:30");
    private final ObservableList<String> locations = FXCollections.observableArrayList("Teams","Room A", "Room B", "Room C");
    private String error;


    public void setCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        CustomerField.setText(selectedCustomer.getCustomerName());
        Time.setItems(times);
        Contact.setItems(contacts);
        Locations.setItems(locations);
    }

    @FXML public void handleSaveButton(ActionEvent event) throws IOException, SQLException {
        LocalDate aptDate = Date.getValue();
        int aptTime = Time.getSelectionModel().getSelectedIndex();
        int aptContact = Contact.getSelectionModel().getSelectedIndex();
        int aptLocation = Locations.getSelectionModel().getSelectedIndex();
        LocalDateTime formattedTime = AppointmentDB.convertToDateAndTime(aptDate, Time.getSelectionModel().getSelectedItem().toString());
        Integer addAppointmentID = AppointmentDB.assignAppointmentID();
        String addContact = Contact.getSelectionModel().getSelectedItem().toString();
        String addLocation = Locations.getSelectionModel().getSelectedItem().toString();
        String addType = ApptType.getText();
        int addCustomer = selectedCustomer.getCustomerID();

        //Validation check
        if (!checkDate(aptDate) || !checkTime(aptTime) || !checkContact(aptContact) || !checkLocation(aptLocation) || !checkType()) {
            System.out.println(error);
            Alerts.infoDialog("Error", "Please fix the following error:", error);
            return;
        }
        if (AppointmentDB.checkOverlappingAppointment(addCustomer, formattedTime) == Boolean.FALSE){
            return;
        }
        else {
            System.out.println("Success");
            AppointmentDB.addAppointment(addAppointmentID, addCustomer, formattedTime, addContact, addLocation, addType);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("ViewAppointments.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
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

    private boolean checkType() {
        if(ApptType.getText().isEmpty()) {
            error = "Please add an Appointment Type";
            return false;
        } else {
            return true;
        }
    }

    private boolean checkLocation(int aptLocation) {
        if(aptLocation == -1) {
            error = "Please choose a location";
            return false;
        } else {
            return true;
        }
    }

    private boolean checkContact(int aptContact) {
        if(aptContact == -1) {
            error = "Please choose a contact";
            return false;
        } else {
            return true;
        }
    }

    private boolean checkTime(int aptTime) {
        if(aptTime == -1) {
            error = "Please choose a time";
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDate(LocalDate aptDate) {
        if(aptDate == null) {
            error = "Please choose a date";
            return false;
        }
        if (aptDate.isBefore(LocalDate.now()))
        {
            error = "Please choose a future date";
            return false;
        }
        if(aptDate.getDayOfWeek() == DayOfWeek.SATURDAY || aptDate.getDayOfWeek() == DayOfWeek.SUNDAY ){
            error = "Please choose a weekday";
            return false;
        }
        else {
            return true;
        }
    }

}
