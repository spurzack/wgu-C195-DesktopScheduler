package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Alerts;
import utils.SQLDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDB {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static String error;

    public static Customer getCustomer(int id) {
        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            String query = "SELECT * FROM customer WHERE customerId='" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerName(rs.getString("customerName"));
                statement.close();
                return customer;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    public static ObservableList<Customer> getAllCustomers() {
        allCustomers.clear();
        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            String query = "SELECT customer.customerId, customer.customerName, address.address, address.phone, address.postalCode, city.city"
                    + " FROM customer INNER JOIN address ON customer.addressId = address.addressId "
                    + "INNER JOIN city ON address.cityId = city.cityId";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("customerId"),
                        rs.getString("CustomerName"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("postalCode"),
                        rs.getString("phone"));
                allCustomers.add(customer);
            }

            statement.close();
            return allCustomers;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public static void addCustomer(String name, String phone, String street, String city, String zip) {
        {
            try {
                Integer cityID = null;
                if(city == "New York"){
                    cityID = 1;
                }
                if(city == "Phoenix"){
                    cityID = 6;
                }
                else {
                    cityID = 7;
                }
                Integer addressID = assignAddressID();
                Integer customerID = assignCustomerID();
                String currentUser = UserDB.getCurrentUser().getUsername();
                Statement statement = SQLDatabase.getConnection().createStatement();
                String queryAddress = "INSERT INTO address SET addressId='" + addressID + "', address='" + street + "', address2='', phone='" + phone + "', postalCode='" + zip + "', cityId=" + cityID + ", createDate=NOW(), lastUpdate=NOW(), createdBy='" + currentUser + "', lastUpdateBy='" + currentUser + "'";
                statement.executeUpdate(queryAddress);
                System.out.println("Successfully Added Address");
                String queryCustomer = "INSERT INTO customer SET customerId='" + customerID + "', customerName='" + name + "', addressId='" + addressID + "', active='1', createDate=NOW(), lastUpdate=NOW(), createdBy='" + currentUser + "', lastUpdateBy='" + currentUser + "'";
                statement.executeUpdate(queryCustomer);
                System.out.println("Successfully Added Customer");

                } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void editCustomer(String name, String phone, String street, String city, String zip, Integer customerID) {
        {
            try {
                Integer cityID = null;
                if(city == "New York"){
                    cityID = 1;
                }
                if(city == "Phoenix"){
                    cityID = 6;
                }
                else {
                    cityID = 7;
                }
                String currentUser = UserDB.getCurrentUser().getUsername();
                Statement statement = SQLDatabase.getConnection().createStatement();
                String queryAddress = "UPDATE address SET address='" + street + "', address2='', phone='" + phone + "', postalCode='" + zip + "', cityId=" + cityID + ", lastUpdate=NOW(), lastUpdateBy='" + currentUser + "' WHERE addressId='" + customerID + "'" ;
                statement.executeUpdate(queryAddress);
                System.out.println("Successfully Updated Address");
                String queryCustomer = "UPDATE customer SET customerName='" + name + "', active='1', lastUpdate=NOW(), lastUpdateBy='" + currentUser + "' WHERE customerId='" + customerID + "'";
                statement.executeUpdate(queryCustomer);
                System.out.println("Successfully Updated Customer");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static Integer assignAddressID() {
        Integer addressID = 1;
        try {
            Statement stmt = SQLDatabase.getConnection().createStatement();
            String query = "SELECT addressId FROM `U05TGe`.`address` ORDER BY addressId;";
            ResultSet rs = stmt.executeQuery(query);


            while(rs.next()) {
                if (rs.getInt("addressId") == addressID) {
                    addressID++;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return addressID;
}

    public static Integer assignCustomerID(){
        Integer customerID = 1;
        try {
            Statement stmt = SQLDatabase.getConnection().createStatement();
            String query = "SELECT customerId FROM `U05TGe`.`customer` ORDER BY customerId;";
            ResultSet rs = stmt.executeQuery(query);


            while(rs.next()) {
                if (rs.getInt("customerId") == customerID) {
                    customerID++;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerID;
    }

    public static void deleteCustomer(Integer customerID){
        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            String query1 = "DELETE FROM customer WHERE customerId=" + customerID;
            String query2 = "DELETE FROM address WHERE addressId=" + customerID;
            statement.executeUpdate(query1);
            System.out.println("Successfully deleted customer");
            statement.executeUpdate(query2);
            System.out.println("Successfully deleted address");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Boolean validateCustomer(String name, String phone, String street, String city, String zip){
        error = "";
        if(!validName(name) || !validPhone(phone) || !validStreet(street) || !validCity(city) || !validZip(zip)){
            System.out.println(error);
            Alerts.infoDialog("Error", "Please fix the following error:", error);
            return Boolean.FALSE;
        }
        else {
            return Boolean.TRUE;
        }
    }

    private static boolean validZip(String zip) {
        if(zip.isEmpty()) {
            error = "Please enter a Zip";
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }

    private static boolean validCity(String city) {
        if(city.isEmpty()) {
            error = "Please enter a City";
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }

    private static boolean validStreet(String street) {
        if(street.isEmpty()) {
            error = "Please enter Street";
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }

    private static boolean validPhone(String phone) {
        if(phone.isEmpty()) {
            error = "Please enter Phone Number";
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }

    private static boolean validName(String name) {
        if(name.isEmpty()) {
            error = "Please enter Customer Name";
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }
}
