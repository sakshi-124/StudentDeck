package com.model.user;

public class UserFactory implements IUserFactory {
    private static UserFactory instance;

    private UserFactory() {}

    public static UserFactory getInstance() {
        if (instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }

    public User makeUser(int userType) {
        if (userType == UserType.STUDENT) {return new Student();}
        if (userType == UserType.LANDLORD) {return new LandlordUser();}
        return new AdminUser();
    }

    @Override
    public UserCredentials makeUserCredentials(String email, String password) {
        return new UserCredentials(email, password);
    }

    @Override
    public BasicUserDetails makeBasicUser(String firstName, String lastName, String gender, String dateOfBirth) {
        return new BasicUserDetails(firstName, lastName, gender, dateOfBirth);
    }

    @Override
    public SecurityDetails makeSecurityDetails(String securityQuestion, String securityAnswer) {
        return new SecurityDetails(securityQuestion, securityAnswer);
    }

    @Override
    public ContactDetail makeContactDetails(String countryCode, String phone, String country) {
        return new ContactDetail(countryCode, phone, country);
    }

    @Override
    public UniversityDetail makeUniversityDetails(String course, String intake, String university) {
        return new UniversityDetail(course, intake, university);
    }

    @Override
    public StudyLocation makeStudentLocation(String studyCountry, String city) {
        return new StudyLocation(studyCountry, city);
    }
}
