package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private final SimpleIntegerProperty aptID = new SimpleIntegerProperty();
    private final SimpleIntegerProperty aptCustomerID = new SimpleIntegerProperty();
    private final SimpleStringProperty aptStart = new SimpleStringProperty();
    private final SimpleStringProperty aptEnd = new SimpleStringProperty();
    private final SimpleStringProperty aptTitle = new SimpleStringProperty();
    private final SimpleStringProperty aptDescription = new SimpleStringProperty();
    private final SimpleStringProperty aptLocation = new SimpleStringProperty();
    private final SimpleStringProperty aptContact = new SimpleStringProperty();

    public Appointment() {}

    public Appointment(int id, int customerid, String start, String end, String title, String description, String location, String contact) {
        setAptID(id);
        setAptCustomerID(customerid);
        setAptStart(start);
        setAptEnd(end);
        setAptTitle(title);
        setAptDescription(description);
        setAptLocation(location);
        setAptContact(contact);
    }

    public int getAptID() {
        return aptID.get();
    }

    public SimpleIntegerProperty aptIDProperty() {
        return aptID;
    }

    public void setAptID(int aptID) {
        this.aptID.set(aptID);
    }

    public int getAptCustomerID() {
        return aptCustomerID.get();
    }

    public SimpleIntegerProperty aptCustomerIDProperty() {
        return aptCustomerID;
    }

    public void setAptCustomerID(int aptCustomerID) {
        this.aptCustomerID.set(aptCustomerID);
    }

    public String getAptStart() {
        return aptStart.get();
    }

    public SimpleStringProperty aptStartProperty() {
        return aptStart;
    }

    public void setAptStart(String aptStart) {
        this.aptStart.set(aptStart);
    }

    public String getAptEnd() {
        return aptEnd.get();
    }

    public SimpleStringProperty aptEndProperty() {
        return aptEnd;
    }

    public void setAptEnd(String aptEnd) {
        this.aptEnd.set(aptEnd);
    }

    public String getAptTitle() {
        return aptTitle.get();
    }

    public SimpleStringProperty aptTitleProperty() {
        return aptTitle;
    }

    public void setAptTitle(String aptTitle) {
        this.aptTitle.set(aptTitle);
    }

    public String getAptDescription() {
        return aptDescription.get();
    }

    public SimpleStringProperty aptDescriptionProperty() {
        return aptDescription;
    }

    public void setAptDescription(String aptDescription) {
        this.aptDescription.set(aptDescription);
    }

    public String getAptLocation() {
        return aptLocation.get();
    }

    public SimpleStringProperty aptLocationProperty() {
        return aptLocation;
    }

    public void setAptLocation(String aptLocation) {
        this.aptLocation.set(aptLocation);
    }

    public String getAptContact() {
        return aptContact.get();
    }

    public SimpleStringProperty aptContactProperty() {
        return aptContact;
    }

    public void setAptContact(String aptContact) {
        this.aptContact.set(aptContact);
    }

    public String getAptTime() {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime localDT = LocalDateTime.parse(this.aptStart.getValue(), dtFormat);
        ZonedDateTime utcTime = localDT.atZone(ZoneId.of("UTC"));
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime utcDate = utcTime.withZoneSameInstant(zoneID);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime localTime = LocalTime.parse(utcDate.toString().substring(11,16), timeFormatter);
        return localTime.toString();
    }

    public StringProperty getAptEndProperty() {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime localDT = LocalDateTime.parse(this.aptEnd.getValue(), dtFormat);
        ZonedDateTime utcTime = localDT.atZone(ZoneId.of("UTC"));
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime utcDate = utcTime.withZoneSameInstant(zoneID);
        StringProperty aptEnd = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return aptEnd;
    }

    public StringProperty getAptStartProperty() {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime localDT = LocalDateTime.parse(this.aptStart.getValue(), dtFormat);
        ZonedDateTime utcTime = localDT.atZone(ZoneId.of("UTC"));
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime utcDate = utcTime.withZoneSameInstant(zoneID);
        StringProperty aptStart = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return aptStart;
    }


}
