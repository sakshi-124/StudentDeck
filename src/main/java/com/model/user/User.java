package com.model.user;

public abstract class User {
    protected Integer id;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected String confirmPassword;
    protected String gender;
    protected String dateOfBirth;
    protected String securityQuestion;
    protected String securityAnswer;
    protected int status;

    public void setUserCredentials(UserCredentials userCredentials) {
        this.id = userCredentials.getId();
        this.email = userCredentials.getEmail();
        this.password = userCredentials.getPassword();
    }

    public void setBasicInfo(BasicUserDetails basicUserDetails) {
        this.firstName = basicUserDetails.getFirstName();
        this.lastName = basicUserDetails.getLastName();
        this.gender = basicUserDetails.getGender();
        this.dateOfBirth = basicUserDetails.getDateOfBirth();
        this.status = basicUserDetails.getStatus();
    }

    public void setSecurityDetails(SecurityDetails securityDetails) {
        this.securityQuestion = securityDetails.getSecurityQuestion();
        this.securityAnswer = securityDetails.getSecurityAnswer();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
