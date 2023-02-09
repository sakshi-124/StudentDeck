package com.model.user;

public class UniversityDetail {
    private String course;
    private String intake;
    private String university;

    public UniversityDetail(String course, String intake, String university) {
        this.course = course;
        this.intake = intake;
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

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
