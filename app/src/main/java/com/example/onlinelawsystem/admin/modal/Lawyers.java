package com.example.onlinelawsystem.admin.modal;

public class Lawyers {

    String LawyerName, LawyerAge, LawyerContact, LawyerQualification, LawyerCourt, LawyerExpertise, LawyerID;

    public Lawyers() {
    }

    public Lawyers(String lawyerName, String lawyerAge, String lawyerContact, String lawyerQualification, String lawyerCourt, String lawyerExpertise, String lawyerID) {
        LawyerName = lawyerName;
        LawyerAge = lawyerAge;
        LawyerContact = lawyerContact;
        LawyerQualification = lawyerQualification;
        LawyerCourt = lawyerCourt;
        LawyerExpertise = lawyerExpertise;
        LawyerID = lawyerID;
    }

    public String getLawyerName() {
        return LawyerName;
    }

    public void setLawyerName(String lawyerName) {
        LawyerName = lawyerName;
    }

    public String getLawyerAge() {
        return LawyerAge;
    }

    public void setLawyerAge(String lawyerAge) {
        LawyerAge = lawyerAge;
    }

    public String getLawyerContact() {
        return LawyerContact;
    }

    public void setLawyerContact(String lawyerContact) {
        LawyerContact = lawyerContact;
    }

    public String getLawyerQualification() {
        return LawyerQualification;
    }

    public void setLawyerQualification(String lawyerQualification) {
        LawyerQualification = lawyerQualification;
    }

    public String getLawyerCourt() {
        return LawyerCourt;
    }

    public void setLawyerCourt(String lawyerCourt) {
        LawyerCourt = lawyerCourt;
    }

    public String getLawyerExpertise() {
        return LawyerExpertise;
    }

    public void setLawyerExpertise(String lawyerExpertise) {
        LawyerExpertise = lawyerExpertise;
    }

    public String getLawyerID() {
        return LawyerID;
    }

    public void setLawyerID(String lawyerID) {
        LawyerID = lawyerID;
    }
}
