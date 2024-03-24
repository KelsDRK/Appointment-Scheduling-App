package model;

public class Appointments {
    private int appointmentId;
    private String appointmentTitle;
    private String appointmentLocation;
    private int customerId;
    private int userId;
    private int contactId;
    private double startDateTime;
    private double endDateTime;

    public Appointments(int appointmentId, String appointmentTitle, String appointmentLocation, int customerId, int userId, int contactId, double startDateTime, double endDateTime) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentLocation = appointmentLocation;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public int getAppointmentId() {return appointmentId;}

    public void setAppointmentId(int appointmentId) {this.appointmentId = appointmentId;}

    public String getAppointmentTitle() {return appointmentTitle;}

    public void setAppointmentTitle(String appointmentTitle) {this.appointmentTitle = appointmentTitle;}

    public String getAppointmentLocation() {return appointmentLocation;}

    public void setAppointmentLocation(String appointmentLocation) {this.appointmentLocation = appointmentLocation;}

    public int getCustomerId() {return customerId;}

    public void setCustomerId(int customerId) {this.customerId = customerId;}

    public int getUserId() {return userId;}

    public void setUserId(int userId) {this.userId = userId;}

    public int getContactId() {return contactId;}

    public void setContactId(int contactId) {this.contactId = contactId;}

    public double getStartDateTime() {return startDateTime;}

    public void setStartDateTime(double startDateTime) {this.startDateTime = startDateTime;}

    public double getEndDateTime() {return endDateTime;}

    public void setEndDateTime(double endDateTime) {this.endDateTime = endDateTime;}
}
