package View_Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewAppointmentsController implements Initializable {

    private Parent scene;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerId;
    @FXML private TableColumn<Customer, String> customerName;
    @FXML private Label CustomerLabel;
    @FXML private TableView<Appointment> aptTable;
    @FXML private TableColumn<Appointment, String> aptDescription;
    @FXML private TableColumn<Appointment, String> aptContact;
    @FXML private TableColumn<Appointment, String> aptLocation;
    @FXML private TableColumn<Appointment, String> aptStart;
    @FXML private TableColumn<Appointment, String> aptEnd;
    @FXML private RadioButton weekly;
    @FXML private RadioButton monthly;
    @FXML private Label Label;


    private Customer selectedCustomer;
    private Appointment selectedAppointment;

    @FXML void handleCustomerSelection() {

        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        int id = selectedCustomer.getCustomerID();

        CustomerLabel.setText((selectedCustomer.getCustomerName()));

        aptDescription.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        aptContact.setCellValueFactory(new PropertyValueFactory<>("aptContact"));
        aptLocation.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        aptStart.setCellValueFactory(cellData -> {
            return cellData.getValue().getAptStartProperty();
        });
        aptEnd.setCellValueFactory(cellData -> {
            return cellData.getValue().getAptEndProperty();
        });
        if (weekly.isSelected()){
            aptTable.setItems(AppointmentDB.getApptsWeekly(id));
        }
        if (monthly.isSelected()){
            aptTable.setItems(AppointmentDB.getApptsMonthly(id));
        }

    }
    public void handleBackButton(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML void handleAddAppointments(ActionEvent event) {
        try {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                Alerts.infoDialog("No Selection", "Please select a customer", "You have not chosen a customer to add an appointment. Please make a selection");
            } else {
                Alerts.confirmDialog("Add Appointment?", "Would you like to add an appointment for " + selectedCustomer.getCustomerName() + "?");
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
                scene = loader.load();
                AddAppointmentController controller = loader.getController();
                controller.setCustomer(selectedCustomer);
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (IOException e){
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
    }

    public void handleAddCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
        scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void handleEditAppointments(ActionEvent event) {
        try {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            selectedAppointment = aptTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null || selectedAppointment == null) {
                Alerts.infoDialog("No Selection", "Please select a customer AND appointment", "You have not chosen a customer or appointment to edit. Please make a selection");
            } else {
                Alerts.confirmDialog("Edit Appointment?", "Would you like to edit an appointment for " + selectedCustomer.getCustomerName() + "?");
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAppointment.fxml"));
                scene = loader.load();
                EditAppointmentController controller = loader.getController();
                controller.setCustomer(selectedCustomer);
                controller.setAppointment(selectedAppointment);
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (IOException e){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    @FXML void handleEditCustomer(ActionEvent event) {
        try {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                Alerts.infoDialog("No Selection", "Please select a customer to continue.", "You have not chosen a customer to edit. Please make a selection");
            } else {
                Alerts.confirmDialog("Edit Customer?", "Would you like to edit " + selectedCustomer.getCustomerName() + "?");
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCustomers.fxml"));
                scene = loader.load();
                EditCustomersController controller = loader.getController();
                controller.setCustomer(selectedCustomer);
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (IOException e){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    @FXML void radioButtonChanges() {
        if(weekly.isSelected()){
            Label.setText("Weekly Appointments");
        }
        if(monthly.isSelected()){
            Label.setText("Monthly Appointments");
        }
        if (selectedCustomer == null) return;
        else{ handleCustomerSelection();}
    }



    @FXML void handleDeleteAppointment() throws IOException {
        selectedAppointment = aptTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alerts.infoDialog("No Selection", "Please select an appointment", "An appointment must be selected for deletion");
        } else {
            Alerts.confirmDialog("Would you like to delete this appointment?", "would you like to delete the appointment with " + selectedAppointment.getAptContact() + "?");
            AppointmentDB.deleteAppointment(selectedAppointment.getAptID());
            handleCustomerSelection();
        }
    }

    @FXML void handleDeleteCustomer() {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alerts.infoDialog("No Selection", "Please select a customer", "An customer must be selected for deletion");
        } else {
            Alerts.confirmDialog("Would you like to delete this customer?", "would you like to delete " + selectedCustomer.getCustomerName() + "?");
            CustomerDB.deleteCustomer(selectedCustomer.getCustomerID());

            //Refresh Customer Table
            customerTable.setItems(CustomerDB.getAllCustomers());
        }
    }

    public void handleReports(ActionEvent event) throws IOException, SQLException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reports.fxml"));
        scene = loader.load();
        ReportsController controller = loader.getController();
        controller.handleReportsApptTypes();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable.setItems(CustomerDB.getAllCustomers());

        //Check for appointments
        System.out.println(UserDB.getCurrentUser().getUsername());
        Appointment appointment = AppointmentDB.get15MinuteApt();
        if (appointment != null) {
            Customer customer = (CustomerDB.getCustomer(appointment.getAptCustomerID()));
            String alertMessage = String.format("You have a %s appointment with %s at %s",
                    appointment.getAptTitle(),
                    customer.getCustomerName(),
                    appointment.getAptTime());
            Alerts.confirmDialog("Appointment Reminder", alertMessage);
        }
    }


}