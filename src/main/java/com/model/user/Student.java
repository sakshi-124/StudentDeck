package com.model.user;

public class Student extends User {
    public String university;
    public String course;
    public String intake;

    public String countryCode;
    public String phone;
    public String studyCountry;
    public String country;

    public String city;

    public void setContactDetail(ContactDetail contactDetail) {
        this.phone = contactDetail.getPhone();
        this.country = contactDetail.getCountry();
        this.countryCode = contactDetail.getCountryCode();
    }

    public void setUniversityDetail(UniversityDetail universityDetail) {
        this.course = universityDetail.getCourse();
        this.intake = universityDetail.getIntake();
        this.university = universityDetail.getUniversity();
    }

    public void setStudyLocation(StudyLocation studyLocation) {
        this.studyCountry = studyLocation.getStudyCountry();
        this.city = studyLocation.getCity();
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStudyCountry() {
        return studyCountry;
    }

    public void setStudyCountry(String studyCountry) {
        this.studyCountry = studyCountry;
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

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
