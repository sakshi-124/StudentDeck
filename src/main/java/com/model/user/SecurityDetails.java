package com.model.user;

public class SecurityDetails {
    private String securityQuestion;
    private String securityAnswer;

    public SecurityDetails(String securityQuestion, String securityAnswer) {
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
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
}
