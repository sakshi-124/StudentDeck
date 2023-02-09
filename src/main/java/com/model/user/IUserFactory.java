package com.model.user;

public interface IUserFactory {
    public UserCredentials makeUserCredentials(String email, String password);
    public BasicUserDetails makeBasicUser(String firstName, String lastName, String gender, String dateOfBirth);
    public SecurityDetails makeSecurityDetails(String securityQuestion, String securityAnswer);
    public ContactDetail makeContactDetails(String countryCode, String phone, String country);
    public UniversityDetail makeUniversityDetails(String course, String intake, String university);
    public StudyLocation makeStudentLocation(String studyCountry, String city);

    public User makeUser(int userType);
}
