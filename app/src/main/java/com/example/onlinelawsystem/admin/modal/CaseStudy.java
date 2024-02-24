package com.example.onlinelawsystem.admin.modal;

public class CaseStudy {
    String CaseStudyTitle, CaseStudyDescription, CaseStudyIssues, CaseStudyCourt, CaseStudyVerdict, CaseStudyID;

    public CaseStudy() {
    }

    public CaseStudy(String caseStudyTitle, String caseStudyDescription, String caseStudyIssues, String caseStudyCourt, String caseStudyVerdict, String caseStudyID) {
        CaseStudyTitle = caseStudyTitle;
        CaseStudyDescription = caseStudyDescription;
        CaseStudyIssues = caseStudyIssues;
        CaseStudyCourt = caseStudyCourt;
        CaseStudyVerdict = caseStudyVerdict;
        CaseStudyID = caseStudyID;
    }

    public String getCaseStudyTitle() {
        return CaseStudyTitle;
    }

    public void setCaseStudyTitle(String caseStudyTitle) {
        CaseStudyTitle = caseStudyTitle;
    }

    public String getCaseStudyDescription() {
        return CaseStudyDescription;
    }

    public void setCaseStudyDescription(String caseStudyDescription) {
        CaseStudyDescription = caseStudyDescription;
    }

    public String getCaseStudyIssues() {
        return CaseStudyIssues;
    }

    public void setCaseStudyIssues(String caseStudyIssues) {
        CaseStudyIssues = caseStudyIssues;
    }

    public String getCaseStudyCourt() {
        return CaseStudyCourt;
    }

    public void setCaseStudyCourt(String caseStudyCourt) {
        CaseStudyCourt = caseStudyCourt;
    }

    public String getCaseStudyVerdict() {
        return CaseStudyVerdict;
    }

    public void setCaseStudyVerdict(String caseStudyVerdict) {
        CaseStudyVerdict = caseStudyVerdict;
    }

    public String getCaseStudyID() {
        return CaseStudyID;
    }

    public void setCaseStudyID(String caseStudyID) {
        CaseStudyID = caseStudyID;
    }
}