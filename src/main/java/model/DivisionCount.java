package model;

public class DivisionCount {
    public String divisionName;
    public int customerCount;

    public DivisionCount(String divisionName, int customerCount) {
        this.divisionName = divisionName;
        this.customerCount = customerCount;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public int getCustomerCount() {
        return customerCount;
    }
}
