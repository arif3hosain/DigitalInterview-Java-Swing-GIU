package com.interview.model;

import java.util.Date;

public class Patient {
    private int patientID;
    private String ptLastName;
    private String ptPreviousLastName;
    private String ptFirstName;
    private String homeAddress1;
    private String homeCity;
    private String homeStateProvinceRegion;
    private String homeZip;
    private String country;
    private String citizenship;
    private String ptMobilePhone;
    private String emergencyPhoneNumber;
    private String emailAddress;
    private String ptSS;
    private Date dob;
    private String gender;
    private String ethnicAssociation;
    private String maritalStatus;
    private String currentPrimaryHCP;
    private String comments;
    private String nextOfKin;
    private String nextOfKinRelationshipToPatient;

    public Patient() {
    }

    // Constructor, getters, and setters


    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getPtLastName() {
        return ptLastName;
    }

    public void setPtLastName(String ptLastName) {
        this.ptLastName = ptLastName;
    }

    public String getPtPreviousLastName() {
        return ptPreviousLastName;
    }

    public void setPtPreviousLastName(String ptPreviousLastName) {
        this.ptPreviousLastName = ptPreviousLastName;
    }

    public String getPtFirstName() {
        return ptFirstName;
    }

    public void setPtFirstName(String ptFirstName) {
        this.ptFirstName = ptFirstName;
    }

    public String getHomeAddress1() {
        return homeAddress1;
    }

    public void setHomeAddress1(String homeAddress1) {
        this.homeAddress1 = homeAddress1;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getHomeStateProvinceRegion() {
        return homeStateProvinceRegion;
    }

    public void setHomeStateProvinceRegion(String homeStateProvinceRegion) {
        this.homeStateProvinceRegion = homeStateProvinceRegion;
    }

    public String getHomeZip() {
        return homeZip;
    }

    public void setHomeZip(String homeZip) {
        this.homeZip = homeZip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getPtMobilePhone() {
        return ptMobilePhone;
    }

    public void setPtMobilePhone(String ptMobilePhone) {
        this.ptMobilePhone = ptMobilePhone;
    }

    public String getEmergencyPhoneNumber() {
        return emergencyPhoneNumber;
    }

    public void setEmergencyPhoneNumber(String emergencyPhoneNumber) {
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPtSS() {
        return ptSS;
    }

    public void setPtSS(String ptSS) {
        this.ptSS = ptSS;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnicAssociation() {
        return ethnicAssociation;
    }

    public void setEthnicAssociation(String ethnicAssociation) {
        this.ethnicAssociation = ethnicAssociation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getCurrentPrimaryHCP() {
        return currentPrimaryHCP;
    }

    public void setCurrentPrimaryHCP(String currentPrimaryHCP) {
        this.currentPrimaryHCP = currentPrimaryHCP;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public String getNextOfKinRelationshipToPatient() {
        return nextOfKinRelationshipToPatient;
    }

    public void setNextOfKinRelationshipToPatient(String nextOfKinRelationshipToPatient) {
        this.nextOfKinRelationshipToPatient = nextOfKinRelationshipToPatient;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientID=" + patientID +
                ", ptLastName='" + ptLastName + '\'' +
                ", ptPreviousLastName='" + ptPreviousLastName + '\'' +
                ", ptFirstName='" + ptFirstName + '\'' +
                ", homeAddress1='" + homeAddress1 + '\'' +
                ", homeCity='" + homeCity + '\'' +
                ", homeStateProvinceRegion='" + homeStateProvinceRegion + '\'' +
                ", homeZip='" + homeZip + '\'' +
                ", country='" + country + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", ptMobilePhone='" + ptMobilePhone + '\'' +
                ", emergencyPhoneNumber='" + emergencyPhoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", ptSS='" + ptSS + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", ethnicAssociation='" + ethnicAssociation + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", currentPrimaryHCP='" + currentPrimaryHCP + '\'' +
                ", comments='" + comments + '\'' +
                ", nextOfKin='" + nextOfKin + '\'' +
                ", nextOfKinRelationshipToPatient='" + nextOfKinRelationshipToPatient + '\'' +
                '}';
    }
}
