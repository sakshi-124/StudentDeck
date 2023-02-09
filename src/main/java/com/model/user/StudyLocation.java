package com.model.user;

public class StudyLocation {
    private String studyCountry;
    private String city;

    public StudyLocation(String studyCountry, String city) {
        this.studyCountry = studyCountry;
        this.city = city;
    }

    public String getStudyCountry() {
        return studyCountry;
    }

    public void setStudyCountry(String studyCountry) {
        this.studyCountry = studyCountry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
