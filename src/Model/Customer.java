package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private final SimpleIntegerProperty customerID = new SimpleIntegerProperty();
    private final SimpleStringProperty customerName = new SimpleStringProperty();
    private final SimpleStringProperty customerAddress = new SimpleStringProperty();
    private final SimpleStringProperty customerCity = new SimpleStringProperty();
    private final SimpleStringProperty customerZip = new SimpleStringProperty();
    private final SimpleStringProperty customerPhone = new SimpleStringProperty();

    public Customer() {
    }

    public Customer(int id, String name, String address,String city, String zip, String phone) {
        setCustomerID(id);
        setCustomerName(name);
        setCustomerAddress(address);
        setCustomerCity(city);
        setCustomerZip(zip);
        setCustomerPhone(phone);
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public SimpleIntegerProperty customerIDProperty() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getCustomerAddress() {
        return customerAddress.get();
    }

    public SimpleStringProperty customerAddressProperty() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress.set(customerAddress);
    }

    public String getCustomerCity() {
        return customerCity.get();
    }

    public SimpleStringProperty customerCityProperty() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity.set(customerCity);
    }

    public String getCustomerZip() {
        return customerZip.get();
    }

    public SimpleStringProperty customerZipProperty() {
        return customerZip;
    }

    public void setCustomerZip(String customerZip) {
        this.customerZip.set(customerZip);
    }

    public String getCustomerPhone() {
        return customerPhone.get();
    }

    public SimpleStringProperty customerPhoneProperty() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone.set(customerPhone);
    }
}
