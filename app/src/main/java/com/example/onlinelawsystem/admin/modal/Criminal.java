package com.example.onlinelawsystem.admin.modal;

public class Criminal {

    String CriminalName, CriminalAlias, CriminalDOB, CriminalGender, CriminalOffense, CriminalID, CriminalAddress;

    public Criminal() {
    }

    public Criminal(String criminalName, String criminalAlias, String criminalDOB, String criminalGender, String criminalOffense, String criminalID, String criminalAddress) {
        CriminalName = criminalName;
        CriminalAlias = criminalAlias;
        CriminalDOB = criminalDOB;
        CriminalGender = criminalGender;
        CriminalOffense = criminalOffense;
        CriminalID = criminalID;
        CriminalAddress = criminalAddress;
    }

    public String getCriminalName() {
        return CriminalName;
    }

    public void setCriminalName(String criminalName) {
        CriminalName = criminalName;
    }

    public String getCriminalAlias() {
        return CriminalAlias;
    }

    public void setCriminalAlias(String criminalAlias) {
        CriminalAlias = criminalAlias;
    }

    public String getCriminalDOB() {
        return CriminalDOB;
    }

    public void setCriminalDOB(String criminalDOB) {
        CriminalDOB = criminalDOB;
    }

    public String getCriminalGender() {
        return CriminalGender;
    }

    public void setCriminalGender(String criminalGender) {
        CriminalGender = criminalGender;
    }

    public String getCriminalOffense() {
        return CriminalOffense;
    }

    public void setCriminalOffense(String criminalOffense) {
        CriminalOffense = criminalOffense;
    }

    public String getCriminalID() {
        return CriminalID;
    }

    public void setCriminalID(String criminalID) {
        CriminalID = criminalID;
    }

    public String getCriminalAddress() {
        return CriminalAddress;
    }

    public void setCriminalAddress(String criminalAddress) {
        CriminalAddress = criminalAddress;
    }
}