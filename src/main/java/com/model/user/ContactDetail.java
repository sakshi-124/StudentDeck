package com.model.user;

public class ContactDetail {
    private String phone;
    private String country;
    private String countryCode;

    public ContactDetail(String countryCode, String phone, String country) {
        this.phone = phone;
        this.country = country;
        this.countryCode = countryCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
