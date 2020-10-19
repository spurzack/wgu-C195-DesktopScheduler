package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Alerts;
import utils.SQLDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDB {
    public static Appointment get15MinuteApt() {
        Appointment apt;
        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone((zoneID));
        LocalDateTime localdt = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime localdt15 = localdt.plusMinutes(15);
        String activeuser = UserDB.getCurrentUser().getUsername();
        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE start BETWEEN '" + localdt + "' AND '" + localdt15 + "' AND " + "contact='" + activeuser + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                apt = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getString("start"),
                        rs.getString("end"), rs.getString("title"), rs.getString("description"),
                        rs.getString("location"), rs.getString("contact"));
                return apt;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Appointment> getApptsMonthly(int id) {
        ObservableList<Appointment> monthlyAppts = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate now = LocalDate.now();
        LocalDate oneMonth = LocalDate.now().plusMonths(1);
        try {
            Statement stmt = SQLDatabase.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND " + "start >= '" + now + "' AND start <= '" + oneMonth + "'\n" +
                            "ORDER BY start";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                appointment = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getString("start"),
                        rs.getString("end"), rs.getString("title"), rs.getString("description"),
                        rs.getString("location"), rs.getString("contact"));
                monthlyAppts.add(appointment);
            }
            stmt.close();
            return monthlyAppts;
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public static ObservableList<Appointment> getApptsWeekly(int id) {
        ObservableList<Appointment> monthlyAppts = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate now = LocalDate.now();
        LocalDate oneWeek = LocalDate.now().plusDays(7);
        try {
            Statement stmt = SQLDatabase.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND " + "start >= '" + now + "' AND start <= '" + oneWeek + "'\n" +
                            "ORDER BY start";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                appointment = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getString("start"),
                        rs.getString("end"), rs.getString("title"), rs.getString("description"),
                        rs.getString("location"), rs.getString("contact"));
                monthlyAppts.add(appointment);
            }
            stmt.close();
            return monthlyAppts;
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public static LocalDateTime convertToDateAndTime(LocalDate Date, String Time){
        String str = Date + " " + Time + ":00.0";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        System.out.println(dateTime);
        return dateTime;

    }

    public static Integer assignAppointmentID(){
        Integer appointmentID = 1;
        try {
            Statement stmt = SQLDatabase.getConnection().createStatement();
            String query = "SELECT appointmentId FROM `U05TGe`.`appointment` ORDER BY appointmentId;";
            ResultSet rs = stmt.executeQuery(query);


            while(rs.next()) {
                if (rs.getInt("appointmentId") == appointmentID) {
                    appointmentID++;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentID;
    }

    public static void addAppointment(int appointmentID, int customerID, LocalDateTime apptStart, String contact, String location, String type){

        ZonedDateTime ldtZoned = apptStart.atZone(ZoneId.systemDefault());
        ZonedDateTime utcStart = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = utcStart.plusHours(1);
        LocalDateTime startConvert = utcStart.toLocalDateTime();
        LocalDateTime endConvert = utcEnd.toLocalDateTime();
        int userID = getUserID(contact);
        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            String query = "INSERT INTO appointment SET appointmentId='"+appointmentID+"', customerId='"+customerID+"', title='" + type + "', description='" +
                    type + "', type='Meeting', contact='" + contact + "', location='" + location + "', start='" + startConvert + "', end='" +
                    endConvert + "', url='', createDate=NOW(), createdBy='', lastUpdate=NOW(), lastUpdateBy='', userId='" + userID + "'";
            statement.executeUpdate(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean checkOverlappingAppointment(int customer, LocalDateTime time) throws SQLException {
        ZonedDateTime ldtZoned = time.atZone(ZoneId.systemDefault());
        ZonedDateTime utcStart = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = utcStart.plusHours(1);
        LocalDateTime startConvert = utcStart.toLocalDateTime();
        LocalDateTime endConvert = utcEnd.toLocalDateTime();
        Statement statement = SQLDatabase.getConnection().createStatement();
        String query = "SELECT * FROM appointment\n" +
                "WHERE customerId=" + customer + "\n" +
                "AND ('" + startConvert + "'>= start AND '" + startConvert + "'<= end)\n" +
                "OR ('" + endConvert + "'>= start AND '" + endConvert + "'<= end)";
        ResultSet rs = statement.executeQuery(query);
        int overlappingAppts = 0;

        while (rs.next()) {
            overlappingAppts++;
        }
        if (overlappingAppts > 0) {
            System.out.println("Appointment is overlapping with another");
            Alerts.confirmDialog("Unable to create appointment", "This appointment overlaps with another. Please choose a different time or date.");
            return false;
        }
        else {
            return true;
        }
    }

    public static void updateAppointment(int appointmentID, int customerID, LocalDateTime apptStart, String contact, String location, String type){

        ZonedDateTime ldtZoned = apptStart.atZone(ZoneId.systemDefault());
        ZonedDateTime utcStart = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = utcStart.plusHours(1);
        LocalDateTime startConvert = utcStart.toLocalDateTime();
        LocalDateTime endConvert = utcEnd.toLocalDateTime();
        int userID = getUserID(contact);
        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            String query = "UPDATE appointment SET customerId='"+customerID+"', title='" + type + "', description='" +
                    type + "', type='Meeting', contact='" + contact + "', location='" + location + "', start='" + startConvert + "', end='" +
                    endConvert + "', url='', createDate=NOW(), createdBy='', lastUpdate=NOW(), lastUpdateBy='', userId='" + userID + "'\n" +
                    "WHERE appointmentId='" +appointmentID+ "'";
            statement.executeUpdate(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteAppointment(Integer apptID){
        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            String query = "DELETE FROM appointment WHERE appointmentId='" +apptID+ "'";
            statement.execute(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int getUserID(String contact){
        if (contact =="Ina"){
            return 3;
        }
        if (contact =="John"){
            return 2;
        }
        else return 1;
    }

}