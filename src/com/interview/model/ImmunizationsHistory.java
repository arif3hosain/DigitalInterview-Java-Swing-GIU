package com.interview.model;

import java.util.Date;

public class ImmunizationsHistory {
    private int immunizationsID;
    private int patientID;
    private String vaccine;
    private Date immunizationDate;
    private Date expirationDate;
    private String delivery;
    private String comments;
    private String hcpId;
    private boolean deleted;

    public ImmunizationsHistory() {
    }

    // Constructor, getters, and setters

    public int getImmunizationsID() {
        return immunizationsID;
    }

    public void setImmunizationsID(int immunizationsID) {
        this.immunizationsID = immunizationsID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public Date getImmunizationDate() {
        return immunizationDate;
    }

    public void setImmunizationDate(Date immunizationDate) {
        this.immunizationDate = immunizationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getHcpId() {
        return hcpId;
    }

    public void setHcpId(String hcpId) {
        this.hcpId = hcpId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
