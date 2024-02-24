package com.example.onlinelawsystem.admin.modal;

public class Courts {

    String CourtName, CourtAddress, CourtContact, CourtCity, CourtType, CourtID;

    public Courts() {
    }

    public Courts(String courtName, String courtAddress, String courtContact, String courtCity, String courtType, String courtID) {
        CourtName = courtName;
        CourtAddress = courtAddress;
        CourtContact = courtContact;
        CourtCity = courtCity;
        CourtType = courtType;
        CourtID = courtID;
    }

    public String getCourtName() {
        return CourtName;
    }

    public void setCourtName(String courtName) {
        CourtName = courtName;
    }

    public String getCourtAddress() {
        return CourtAddress;
    }

    public void setCourtAddress(String courtAddress) {
        CourtAddress = courtAddress;
    }

    public String getCourtContact() {
        return CourtContact;
    }

    public void setCourtContact(String courtContact) {
        CourtContact = courtContact;
    }

    public String getCourtCity() {
        return CourtCity;
    }

    public void setCourtCity(String courtCity) {
        CourtCity = courtCity;
    }

    public String getCourtType() {
        return CourtType;
    }

    public void setCourtType(String courtType) {
        CourtType = courtType;
    }

    public String getCourtID() {
        return CourtID;
    }

    public void setCourtID(String courtID) {
        CourtID = courtID;
    }
}