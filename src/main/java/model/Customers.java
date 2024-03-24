package model;

public class Customers {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private int customerPostalCode;
    private int customerPhoneNumber;
    private int divisionId;

    public Customers(int customerId, String customerName, String customerAddress, int customerPostalCode, int customerPhoneNumber, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionId = divisionId;
    }

    public int getCustomerId() {return customerId;}

    public void setCustomerId(int customerId) {this.customerId = customerId;}

    public String getCustomerName() {return customerName;}

    public void setCustomerName(String customerName) {this.customerName = customerName;}

    public String getCustomerAddress() {return customerAddress;}

    public void setCustomerAddress(String customerAddress) {this.customerAddress = customerAddress;}

    public int getCustomerPostalCode() {return customerPostalCode;}

    public void setCustomerPostalCode(int customerPostalCode) {this.customerPostalCode = customerPostalCode;}

    public int getCustomerPhoneNumber() {return customerPhoneNumber;}

    public void setCustomerPhoneNumber(int customerPhoneNumber) {this.customerPhoneNumber = customerPhoneNumber;}

    public int getDivisionId() {return divisionId;}

    public void setDivisionId(int divisionId) {this.divisionId = divisionId;}
}
