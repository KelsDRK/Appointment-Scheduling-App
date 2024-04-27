package model;

public class ReportsMonth {
    public String appointmentMonth;
    public String appointmentType;
    public int appointmentCount;

    public ReportsMonth(String appointmentMonth, String appointmentType, int appointmentCount) {
        this.appointmentMonth = appointmentMonth;
        this.appointmentType = appointmentType;
        this.appointmentCount = appointmentCount;
    }

    public String getAppointmentMonth() {
        return appointmentMonth;
    }

    public int getAppointmentCount() {
        return appointmentCount;
    }

    public String getAppointmentType() {
        return appointmentType;
    }
}
