package com.interview.model;

public class AllergyHistory {
    private int allergyID;
    private int patientID;
    private String allergen;
    private String allergyStartDate;
    private String allergyEndDate;
    private String allergyDescription;
    private boolean deleted;

    public AllergyHistory() {
    }

    public int getAllergyID() {
        return allergyID;
    }

    public void setAllergyID(int allergyID) {
        this.allergyID = allergyID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public String getAllergyStartDate() {
        return allergyStartDate;
    }

    public void setAllergyStartDate(String allergyStartDate) {
        this.allergyStartDate = allergyStartDate;
    }

    public String getAllergyEndDate() {
        return allergyEndDate;
    }

    public void setAllergyEndDate(String allergyEndDate) {
        this.allergyEndDate = allergyEndDate;
    }

    public String getAllergyDescription() {
        return allergyDescription;
    }

    public void setAllergyDescription(String allergyDescription) {
        this.allergyDescription = allergyDescription;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "AllergyHistory{" +
                "allergyID=" + allergyID +
                ", patientID=" + patientID +
                ", allergen='" + allergen + '\'' +
                ", allergyStartDate='" + allergyStartDate + '\'' +
                ", allergyEndDate='" + allergyEndDate + '\'' +
                ", allergyDescription='" + allergyDescription + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
