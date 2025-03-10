package model;

import java.time.LocalDateTime;

public class Appointments {
    private int appointmentId;
    private String appointmentTitle;

    public Appointments(int appointmentId, String appointmentTitle, String appointmentLocation, String appointmentType,
                        String appointmentDescription, int customerId, LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentDescription = appointmentDescription;
        this.customerId = customerId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private String appointmentLocation;
    private String appointmentType;
    private String appointmentDescription;
    private int customerId;
    private String contactName;
    private int userId;
    private int contactId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Appointments() {
    }

    public Appointments(int appointmentId, String appointmentTitle, String appointmentType, String appointmentDescription,
                        String appointmentLocation, int customerId, String contactName, int userId, int contactId, LocalDateTime startDateTime,
                        LocalDateTime endDateTime) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentType = appointmentType;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.contactName = contactName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }



}
