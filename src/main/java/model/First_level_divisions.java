package model;

public class First_level_divisions {
    private int divisionId;
    private String divisionName;
    private int countryId;

    public First_level_divisions(int divisionId, String divisionName, int countryId) {
        this.countryId = countryId;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }

    public int getDivisionId() {return divisionId;}

    public void setDivisionId(int divisionId) {this.divisionId = divisionId;}

    public String getDivisionName() {return divisionName;}

    public void setDivisionName(String divisionName) {this.divisionName = divisionName;}

    public int getCountryId() {return countryId;}

    public void setCountryId(int countryId) {this.countryId = countryId;}
}
