package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Location {
    private String postcode;
    private String country;
    private String countryabbreviation;
    ArrayList<Place> places;

    public String getPostCOde() {
        return postcode;
    }
@JsonProperty("post code")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryAbbreviation() {
        return countryabbreviation;
    }
@JsonProperty("country abbreviation")
    public void setCountryAbbreviation(String countryabbreviation) {
        this.countryabbreviation = countryabbreviation;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Location{" +
                "postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", countryabbreviation='" + countryabbreviation + '\'' +
                ", places=" + places +
                '}';
    }
}
