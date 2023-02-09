package com.model.user;

public class LandlordUser extends User {
    public String phone;
    public String country;
    public String countryCode;

    public void setContactDetails(ContactDetail contactDetail) {
        this.phone = contactDetail.getPhone();
        this.country = contactDetail.getCountry();
        this.countryCode = contactDetail.getCountryCode();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
