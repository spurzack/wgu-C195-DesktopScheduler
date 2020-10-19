package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import utils.SQLDatabase;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class ReportsController {
    @FXML TextArea apptTypesText;
    @FXML TextArea apptTypesText1;
    @FXML TextArea schedulesText;
    @FXML TextArea schedulesText1;
    @FXML TextArea schedulesText2;
    @FXML TextArea schedulesText3;
    @FXML TextArea totalAppointments;
    @FXML TextArea totalAppointments1;

    public void handleReportsApptTypes() throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonth = now.plusMonths(1);
        String query = "SELECT description, count(*) as 'Number'\n" +
                        "FROM appointment\n" +
                        "WHERE start>='" + now + "' AND start<'" + oneMonth + "'\n" +
                        "GROUP BY description\n";

        Statement st = SQLDatabase.getConnection().createStatement();
        ResultSet rs = st.executeQuery(query);

        StringBuffer appointmentType = new StringBuffer();
        StringBuffer typeCount = new StringBuffer();
        while (rs.next()) {

            appointmentType.append(String.format("%s\n", rs.getString("description")));
            typeCount.append(String.format("%d\n",rs.getInt("Number")));

        }
        st.close();

        apptTypesText.setText(appointmentType.toString());
        apptTypesText1.setText(typeCount.toString());
    }

    public void handleSchedules() throws SQLException {
        String query = "SELECT a.lastUpdateBy, a.description, c.customerName,start, end\n" +
                        "FROM appointment a\n" +
                        "JOIN customer c on c.customerId = a.customerID\n" +
                        "WHERE start>=NOW()\n" +
                        "GROUP BY a.lastUpdateBy, month(start),start\n" +
                        "ORDER BY customerName, start";


        Statement st = SQLDatabase.getConnection().createStatement();
        ResultSet rs = st.executeQuery(query);

        StringBuffer consultant = new StringBuffer();
        StringBuffer appointment = new StringBuffer();
        StringBuffer start = new StringBuffer();
        StringBuffer end = new StringBuffer();

        while (rs.next()) {

            consultant.append(String.format("%s\n", rs.getString("customerName")));
            appointment.append(String.format("%s\n", rs.getString("description")));
            start.append(String.format("%s\n", rs.getString("start")));
            end.append(String.format("%s\n", rs.getString("end")));

        }

        st.close();
        schedulesText.setText(consultant.toString());
        schedulesText1.setText(appointment.toString());
        schedulesText2.setText(start.toString());
        schedulesText3.setText(end.toString());

    }

    public void handleCountAppts() throws SQLException {
        String query = "SELECT customer.customerName, count(appointment.customerId) as number\n" +
                        "FROM appointment\n" +
                        "JOIN customer on appointment.customerId = customer.customerID\n" +
                        "WHERE start>=NOW()\n" +
                        "GROUP BY customerName\n" +
                        "ORDER BY customerName";


        Statement st = SQLDatabase.getConnection().createStatement();
        ResultSet rs = st.executeQuery(query);

        StringBuffer customer = new StringBuffer();
        StringBuffer count = new StringBuffer();


        while (rs.next()) {

            customer.append(String.format("%s\n", rs.getString("customerName")));
            count.append(String.format("%s\n", rs.getString("number")));


        }

        st.close();
        totalAppointments.setText(customer.toString());
        totalAppointments1.setText(count.toString());

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

}
