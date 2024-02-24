package com.example.onlinelawsystem.admin.modal;

public class Laws {

    String LawTitle, LawSection, LawDescription, LawID, LawJurisdiction;

    public Laws() {
    }

    public Laws(String lawTitle, String lawSection, String lawDescription, String lawID, String lawJurisdiction) {
        LawTitle = lawTitle;
        LawSection = lawSection;
        LawDescription = lawDescription;
        LawID = lawID;
        LawJurisdiction = lawJurisdiction;
    }

    public String getLawTitle() {
        return LawTitle;
    }

    public void setLawTitle(String lawTitle) {
        LawTitle = lawTitle;
    }

    public String getLawSection() {
        return LawSection;
    }

    public void setLawSection(String lawSection) {
        LawSection = lawSection;
    }

    public String getLawDescription() {
        return LawDescription;
    }

    public void setLawDescription(String lawDescription) {
        LawDescription = lawDescription;
    }

    public String getLawID() {
        return LawID;
    }

    public void setLawID(String lawID) {
        LawID = lawID;
    }

    public String getLawJurisdiction() {
        return LawJurisdiction;
    }

    public void setLawJurisdiction(String lawJurisdiction) {
        LawJurisdiction = lawJurisdiction;
    }
}