package model;

public class Countries {
    private int countryId;
    private String countryName;

    public Countries (int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {return countryId;}

    public void setCountryId(int countryId) {this.countryId = countryId;}

    public String getCountryName() {return countryName;}

    public void setCountryName(String countryName) {this.countryName = countryName;}
}
