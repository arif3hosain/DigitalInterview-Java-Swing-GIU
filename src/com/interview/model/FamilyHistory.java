package com.interview.model;

public class FamilyHistory {
    private int familyID;
    private int patientID;
    private String name;
    private String relation;
    private boolean alive;
    private boolean livesWithPatient;
    private String majorDisorder;
    private String specificTypeDisorder;
    private boolean disorderHRF;
    private boolean deleted;

    // Constructor
    public FamilyHistory() {}

    public int getFamilyID() {
        return familyID;
    }

    public void setFamilyID(int familyID) {
        this.familyID = familyID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isLivesWithPatient() {
        return livesWithPatient;
    }

    public void setLivesWithPatient(boolean livesWithPatient) {
        this.livesWithPatient = livesWithPatient;
    }

    public String getMajorDisorder() {
        return majorDisorder;
    }

    public void setMajorDisorder(String majorDisorder) {
        this.majorDisorder = majorDisorder;
    }

    public String getSpecificTypeDisorder() {
        return specificTypeDisorder;
    }

    public void setSpecificTypeDisorder(String specificTypeDisorder) {
        this.specificTypeDisorder = specificTypeDisorder;
    }

    public boolean isDisorderHRF() {
        return disorderHRF;
    }

    public void setDisorderHRF(boolean disorderHRF) {
        this.disorderHRF = disorderHRF;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "FamilyHistoryTable{" +
                "familyID=" + familyID +
                ", patientID=" + patientID +
                ", name='" + name + '\'' +
                ", relation='" + relation + '\'' +
                ", alive=" + alive +
                ", livesWithPatient=" + livesWithPatient +
                ", majorDisorder='" + majorDisorder + '\'' +
                ", specificTypeDisorder='" + specificTypeDisorder + '\'' +
                ", disorderHRF=" + disorderHRF +
                ", deleted=" + deleted +
                '}';
    }
}
