package com.interview.model;

public class GeneralMedicalHistory {
    private int generalMedicalHistoryID;
    private int patientID;
    private String tobacco;
    private String tobaccoQuantity;
    private String tobaccoDuration;
    private String alcohol;
    private String alcoholQuantity;
    private String alcoholDuration;
    private String drug;
    private String drugType;
    private String drugDuration;
    private String bloodType;
    private String rh;
    private boolean deleted;

    public GeneralMedicalHistory() {

    }

    // Constructor, getters, and setters


    public int getGeneralMedicalHistoryID() {
        return generalMedicalHistoryID;
    }

    public void setGeneralMedicalHistoryID(int generalMedicalHistoryID) {
        this.generalMedicalHistoryID = generalMedicalHistoryID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getTobacco() {
        return tobacco;
    }

    public void setTobacco(String tobacco) {
        this.tobacco = tobacco;
    }

    public String getTobaccoQuantity() {
        return tobaccoQuantity;
    }

    public void setTobaccoQuantity(String tobaccoQuantity) {
        this.tobaccoQuantity = tobaccoQuantity;
    }

    public String getTobaccoDuration() {
        return tobaccoDuration;
    }

    public void setTobaccoDuration(String tobaccoDuration) {
        this.tobaccoDuration = tobaccoDuration;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getAlcoholQuantity() {
        return alcoholQuantity;
    }

    public void setAlcoholQuantity(String alcoholQuantity) {
        this.alcoholQuantity = alcoholQuantity;
    }

    public String getAlcoholDuration() {
        return alcoholDuration;
    }

    public void setAlcoholDuration(String alcoholDuration) {
        this.alcoholDuration = alcoholDuration;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugDuration() {
        return drugDuration;
    }

    public void setDrugDuration(String drugDuration) {
        this.drugDuration = drugDuration;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "GeneralMedicalHistory{" +
                "generalMedicalHistoryID=" + generalMedicalHistoryID +
                ", patientID=" + patientID +
                ", tobacco='" + tobacco + '\'' +
                ", tobaccoQuantity='" + tobaccoQuantity + '\'' +
                ", tobaccoDuration='" + tobaccoDuration + '\'' +
                ", alcohol='" + alcohol + '\'' +
                ", alcoholQuantity='" + alcoholQuantity + '\'' +
                ", alcoholDuration='" + alcoholDuration + '\'' +
                ", drug='" + drug + '\'' +
                ", drugType='" + drugType + '\'' +
                ", drugDuration='" + drugDuration + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", rh='" + rh + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
